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
import com.madalinaloghin.movieapp.api.response.ResponseListTvSeries;
import com.madalinaloghin.movieapp.ui.adapter.AdapterSimilarMovieList;
import com.madalinaloghin.movieapp.ui.adapter.AdapterSimilarTvSeriesList;
import com.madalinaloghin.util.Util;
import com.madalinaloghin.util.object.AccountState;
import com.madalinaloghin.util.object.Movie;
import com.madalinaloghin.util.object.TvSeries;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MovieTvSeriesDetailsActivity extends AppCompatActivity {

    private Movie movie;
    private TvSeries tvSeries;
    private LinearLayoutManager mLayoutManagerSimilar;
    private AdapterSimilarTvSeriesList mAdapterTvSeries;
    private AdapterSimilarMovieList mAdapterMovies;

    private boolean mIsLoading = false;
    private int mCurrentPage = 0;

    @BindView(R.id.iv_image_details_movie_series)
    ImageView ivImageDetail;

    @BindView(R.id.iv_image_poster_movie_series)
    ImageView ivImagePoster;

    @BindView(R.id.tv_title_movie_tv_series)
    TextView tvMovieSeriesTitle;

    @BindView(R.id.tv_description_movie_tv_series)
    TextView tvDescriptionMovieTitle;

    @BindView(R.id.tv_vote_average)
    TextView tvVoteAverage;

    @BindView(R.id.tv_release_date_movie_tv_series)
    TextView tvReleaseDate;

    @BindView(R.id.tb_favorite_movie_toggle)
    ToggleButton tbFavoriteButton;

    @BindView(R.id.rb_rating_movie_tv_series)
    RatingBar rbRatingMovieSeries;

    @BindView(R.id.rv_similiar_movies_series)
    RecyclerView rvSimilarSection;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_tv_series_details);
        Bundle b = getIntent().getExtras();
        ButterKnife.bind(this);

        rbRatingMovieSeries.setOnRatingBarChangeListener(
                new RatingBar.OnRatingBarChangeListener() {
                    @Override
                    public void onRatingChanged(RatingBar ratingBar, float v, boolean b) {
                        updateRatingBarValue(v * 2);
                    }
                }
        );


        if (b.getSerializable(Util.MOVIE) != null) {
            movie = new Movie();
            movie = (Movie) b.getSerializable(Util.MOVIE);
            getMovieAccountState(movie.getId());
            updateMovieInfo();
            updateMoviesStatusFavorite(movie.getId(), Util.MOVIE, movie.isFavorite());
            setupRecycleViewMovies();
            getSimilarMovies(movie.getId());

        } else {
            tvSeries = new TvSeries();
            tvSeries = (TvSeries) b.getSerializable(Util.TV_SERIES);
            updateTvSeriesInfo();
            updateTvSeriesStatusFavorite(tvSeries.getId(), Util.TV_SERIES, tvSeries.isFavorite());
            setupRecycleViewTvSeries();
            getSimilarTvSeries(tvSeries.getId());
        }

    }

    @OnClick(R.id.iv_image_poster_movie_series)
    void clickImage() {
        if (movie == null) {
            Toast.makeText(this.getBaseContext(), String.valueOf(tvSeries.isFavorite()), Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this.getBaseContext(), String.valueOf(movie.isFavorite()), Toast.LENGTH_SHORT).show();
        }
    }


    @OnClick(R.id.tb_favorite_movie_toggle)
    void onClickFavoriteButton() {
        if (movie == null) {
            updateTvSeriesStatusFavorite(tvSeries.getId(), Util.TV_SERIES, tbFavoriteButton.isChecked());
            updateFavoriteInfo(Util.TV_SERIES);
        } else {
            updateMoviesStatusFavorite(movie.getId(), Util.MOVIE, tbFavoriteButton.isChecked());
            updateFavoriteInfo(Util.MOVIE);
        }
    }


    void updateRatingBarValue(float value) {
        if (tvSeries == null) {
            RequestManager.getInstance(getBaseContext()).rateMovie(
                    Util.API_KEY,
                    Util.SESSION_ID,
                    value,
                    movie.getId(),
                    new Callback<Movie>() {
                        @Override
                        public void onResponse(Call<Movie> call, Response<Movie> response) {
                        }

                        @Override
                        public void onFailure(Call<Movie> call, Throwable t) {

                        }
                    }
            );
        } else {
            RequestManager.getInstance(getBaseContext()).rateTvSeries(
                    Util.API_KEY,
                    Util.SESSION_ID,
                    value,
                    tvSeries.getId(),
                    new Callback<TvSeries>() {
                        @Override
                        public void onResponse(Call<TvSeries> call, Response<TvSeries> response) {
                        }

                        @Override
                        public void onFailure(Call<TvSeries> call, Throwable t) {

                        }
                    }
            );
        }
    }

    void updateFavoriteInfo(String type) {
        if (type == Util.MOVIE) {
            tbFavoriteButton.setChecked(movie.isFavorite());
        } else {
            tbFavoriteButton.setChecked(tvSeries.isFavorite());
        }
    }


    void updateTvSeriesStatusFavorite(int id, final String mediaType, final boolean favorite) {
        RequestManager.getInstance(getBaseContext()).markTvSeriesAsFavorite(
                Util.API_KEY,
                Util.SESSION_ID,
                Util.currentUser.getId(),
                mediaType,
                id,
                favorite,
                new Callback<TvSeries>() {
                    @Override
                    public void onResponse(Call<TvSeries> call, Response<TvSeries> response) {
                        tvSeries.setFavorite(favorite);
                        updateFavoriteInfo(Util.TV_SERIES);
                    }

                    @Override
                    public void onFailure(Call<TvSeries> call, Throwable t) {

                    }
                }
        );

    }

    void setupRecycleViewTvSeries() {
        mLayoutManagerSimilar = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        rvSimilarSection.setLayoutManager(mLayoutManagerSimilar);
        mAdapterTvSeries = new AdapterSimilarTvSeriesList(new AdapterSimilarTvSeriesList.OnItemClickedListener() {
            @Override
            public void onItemClick(TvSeries tvSeries) {
                Intent intent = new Intent(MovieTvSeriesDetailsActivity.this, MovieTvSeriesDetailsActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable(Util.TV_SERIES, tvSeries);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });

        rvSimilarSection.setAdapter(mAdapterTvSeries);

        rvSimilarSection.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                if (mIsLoading || dx <= 0) {
                    return;
                }
                int visibleItemCount = mLayoutManagerSimilar.getChildCount();
                int totalItemCount = mLayoutManagerSimilar.getItemCount();
                int pastVisiblesItems = mLayoutManagerSimilar.findFirstVisibleItemPosition();

                if ((visibleItemCount + pastVisiblesItems) >= totalItemCount - 5) {
                    getSimilarTvSeries(tvSeries.getId());
                }
            }
        });
    }

    void setupRecycleViewMovies() {
        mLayoutManagerSimilar = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        rvSimilarSection.setLayoutManager(mLayoutManagerSimilar);
        mAdapterMovies = new AdapterSimilarMovieList(new AdapterSimilarMovieList.OnItemClickedListener() {
            @Override
            public void onItemClick(Movie movie) {
                Intent intent = new Intent(MovieTvSeriesDetailsActivity.this, MovieTvSeriesDetailsActivity.class);
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

    void getSimilarTvSeries(int id) {
        mCurrentPage++;
        mIsLoading = true;
        RequestManager.getInstance(getBaseContext()).querySimilarTvSeries(
                id,
                Util.API_KEY,
                mCurrentPage,
                new Callback<ResponseListTvSeries>() {
                    @Override
                    public void onResponse(Call<ResponseListTvSeries> call, Response<ResponseListTvSeries> response) {
                        if (mCurrentPage == 1) {
                            mAdapterTvSeries.setItems(response.body().getResultsList());
                        } else {
                            mAdapterTvSeries.addItems(response.body().getResultsList());
                        }
                        mIsLoading = false;
                    }

                    @Override
                    public void onFailure(Call<ResponseListTvSeries> call, Throwable t) {
                        mIsLoading = false;
                    }
                }
        );
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
                        updateFavoriteInfo(Util.MOVIE);
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
        tvMovieSeriesTitle.setText(movie.getTitle());
        tvReleaseDate.setText(movie.getReleaseDate());
        tvVoteAverage.setText(String.valueOf(movie.getVoteAverage()));
        tbFavoriteButton.setChecked(movie.isFavorite());
        if (movie.getRated() != null) {
            rbRatingMovieSeries.setRating((movie.getRated().getValue()) / 2);
        }
    }


    void updateTvSeriesInfo() {
        Glide.with(this).load(tvSeries.getPosterUrl()).into(ivImagePoster);
        Glide.with(this).load(tvSeries.getImageUrl()).into(ivImageDetail);
        tvDescriptionMovieTitle.setText(tvSeries.getDescription());
        tvMovieSeriesTitle.setText(tvSeries.getTitle());
        tvReleaseDate.setText(tvSeries.getFirst_air_date());
        tvVoteAverage.setText(String.valueOf(tvSeries.getVoteAverage()));
        tbFavoriteButton.setChecked(tvSeries.isFavorite());
        //rbRatingMovieSeries.setRating(tvSeries.getVoteAverage() / 2);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }


}
