package com.madalinaloghin.movieapp.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.bumptech.glide.Glide;
import com.madalinaloghin.movieapp.R;
import com.madalinaloghin.movieapp.api.RequestManager;
import com.madalinaloghin.movieapp.api.response.ResponseListMovies;
import com.madalinaloghin.movieapp.ui.adapter.AdapterSimilarMovieList;
import com.madalinaloghin.util.Util;
import com.madalinaloghin.util.object.AccountState;
import com.madalinaloghin.util.object.Movie;
import com.madalinaloghin.util.object.Rated;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MovieDetailsActivity extends AppCompatActivity {

    private Movie movie;
    private LinearLayoutManager mLayoutManagerSimilar;
    private AdapterSimilarMovieList mAdapterMovies;
    private boolean mIsLoading = false;
    private int mCurrentPage = 0;

    @BindView(R.id.iv_image_banner_movie_details)
    ImageView ivImageDetail;

    @BindView(R.id.iv_image_poster_movie_details)
    ImageView ivImagePoster;

    @BindView(R.id.tv_title_movie_details)
    TextView tvMovieTitle;

    @BindView(R.id.tv_description_movie_details)
    TextView tvDescriptionMovieTitle;

    @BindView(R.id.tv_vote_average_movie_details)
    TextView tvVoteAverage;

    @BindView(R.id.tv_release_date_movie_details)
    TextView tvReleaseDate;

    @BindView(R.id.tb_favorite_movie_details_toggle)
    ToggleButton tbFavoriteButton;

    @BindView(R.id.rb_rating_movie_details)
    RatingBar rbRatingMovieDetails;

    @BindView(R.id.rv_similiar_movies_details)
    RecyclerView rvSimilarSection;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);
        Bundle b = getIntent().getExtras();
        ButterKnife.bind(this);

        rbRatingMovieDetails.setOnRatingBarChangeListener(
                new RatingBar.OnRatingBarChangeListener() {
                    @Override
                    public void onRatingChanged(RatingBar ratingBar, float v, boolean b) {
                        updateRatingBarValue(v);
                    }
                }
        );

        movie = new Movie();
        movie = (Movie) b.getSerializable(Util.MOVIE);
        getMovieAccountState(movie.getId());
        updateMovieInfo();
        setupRecycleViewMovies();
        getSimilarMovies(movie.getId());

    }


    @OnClick(R.id.iv_image_poster_movie_details)
    void clickImage() {
        Toast.makeText(this.getBaseContext(), String.valueOf(movie.isFavorite()), Toast.LENGTH_SHORT).show();
    }


    @OnClick(R.id.tb_favorite_movie_details_toggle)
    void onClickFavoriteButton() {
        updateMoviesStatusFavorite(movie.getId(), Util.MOVIE, tbFavoriteButton.isChecked());
        tbFavoriteButton.setChecked(movie.isFavorite());

    }

    void updateRatingBarValue(final float value) {
        RequestManager.getInstance(getBaseContext()).rateMovie(
                Util.API_KEY,
                Util.SESSION_ID,
                value * 2,
                movie.getId(),
                new Callback<Movie>() {
                    @Override
                    public void onResponse(Call<Movie> call, Response<Movie> response) {
                        movie.setRated(new Rated(value));
                    }

                    @Override
                    public void onFailure(Call<Movie> call, Throwable t) {

                    }
                }
        );
    }


    void setupRecycleViewMovies() {
        mLayoutManagerSimilar = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        rvSimilarSection.setLayoutManager(mLayoutManagerSimilar);
        mAdapterMovies = new AdapterSimilarMovieList(new AdapterSimilarMovieList.OnItemClickedListener() {
            @Override
            public void onItemClick(Movie movie) {
                Intent intent = new Intent(MovieDetailsActivity.this, MovieDetailsActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable(Util.MOVIE, movie);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });

        rvSimilarSection.setAdapter(mAdapterMovies);

        rvSimilarSection.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                if (mIsLoading || dx <= 0) {
                    return;
                }
                int visibleItemCount = mLayoutManagerSimilar.getChildCount();
                int totalItemCount = mLayoutManagerSimilar.getItemCount();
                int pastVisiblesItems = mLayoutManagerSimilar.findFirstVisibleItemPosition();

                if ((visibleItemCount + pastVisiblesItems) >= totalItemCount - 5
                        ) {
                    getSimilarMovies(movie.getId());
                }
            }
        });
    }


    void getSimilarMovies(int id) {
        mCurrentPage++;
        mIsLoading = true;
        RequestManager.getInstance(getBaseContext()).querySimilarMovies(
                id,
                Util.API_KEY,
                mCurrentPage,
                new Callback<ResponseListMovies>() {
                    @Override
                    public void onResponse(Call<ResponseListMovies> call, Response<ResponseListMovies> response) {
                        if (mCurrentPage == 1) {
                            mAdapterMovies.setItems(response.body().getResultsList());
                        } else {
                            mAdapterMovies.addItems(response.body().getResultsList());
                        }
                        mIsLoading = false;
                    }

                    @Override
                    public void onFailure(Call<ResponseListMovies> call, Throwable t) {
                        mIsLoading = false;
                    }
                }
        );
    }


    void getMovieAccountState(int id) {
        RequestManager.getInstance(getBaseContext()).queryMovieAccountState(
                id,
                Util.API_KEY,
                Util.SESSION_ID,
                new Callback<AccountState>() {
                    @Override
                    public void onResponse(Call<AccountState> call, Response<AccountState> response) {
                        movie.setFavorite(response.body().getFavorite());
                        if (response.body().getRated() != null) {
                            movie.setRated(response.body().getRated());
                        }
                        updateMovieInfo();
                    }

                    @Override
                    public void onFailure(Call<AccountState> call, Throwable t) {

                    }
                }
        );
    }

    void updateMoviesStatusFavorite(final int id, final String mediaType, final boolean favorite) {
        RequestManager.getInstance(getBaseContext()).markMovieAsFavorite(
                Util.API_KEY,
                Util.SESSION_ID,
                Util.currentUser.getId(),
                mediaType,
                id,
                favorite,
                new Callback<Movie>() {
                    @Override
                    public void onResponse(Call<Movie> call, Response<Movie> response) {
                        movie.setFavorite(favorite);
                        tbFavoriteButton.setChecked(movie.isFavorite());
                    }

                    @Override
                    public void onFailure(Call<Movie> call, Throwable t) {

                    }
                }
        );

    }



    void updateMovieInfo() {
        Glide.with(this).load(movie.getPosterUrl()).into(ivImagePoster);
        Glide.with(this).load(movie.getImageUrl()).into(ivImageDetail);
        tvDescriptionMovieTitle.setText(movie.getDescription());
        tvMovieTitle.setText(movie.getTitle());
        tvReleaseDate.setText(movie.getReleaseDate());
        tvVoteAverage.setText(String.valueOf(movie.getVoteAverage()));
        tbFavoriteButton.setChecked(movie.isFavorite());
        if (movie.getRated() != null) {
            rbRatingMovieDetails.setRating((movie.getRated().getValue()) / 2);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }


}
