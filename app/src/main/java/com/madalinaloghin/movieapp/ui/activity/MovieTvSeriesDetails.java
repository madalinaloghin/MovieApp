package com.madalinaloghin.movieapp.ui.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.bumptech.glide.Glide;
import com.madalinaloghin.movieapp.R;
import com.madalinaloghin.movieapp.api.RequestManager;
import com.madalinaloghin.util.Util;
import com.madalinaloghin.util.object.Categories;
import com.madalinaloghin.util.object.Movie;
import com.madalinaloghin.util.object.TvSeries;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MovieTvSeriesDetails extends AppCompatActivity {

    private Movie movie;
    private TvSeries tvSeries;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_tv_series_details);
        Bundle b = getIntent().getExtras();
        ButterKnife.bind(this);

        if (b.getSerializable(Categories.MOVIE) != null) {
            movie = new Movie();
            movie = (Movie) b.getSerializable(Categories.MOVIE);
            updateMovieInfo();

        } else {
            tvSeries = new TvSeries();
            tvSeries = (TvSeries) b.getSerializable(Categories.TV_SERIES);
            updateTvSeriesInfo();
        }
    }

    @OnClick(R.id.iv_image_poster_movie_series)
    void clickImage() {
        if (movie == null) {
            Toast.makeText(this.getBaseContext(), String.valueOf(tvSeries.isFavorite), Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this.getBaseContext(), String.valueOf(movie.isFavorite), Toast.LENGTH_SHORT).show();
        }
    }


    @OnClick(R.id.tb_favorite_movie_toggle)
    void onClickFavoriteButton() {
        if (movie == null) {
            updateTvSeriesStatusFavorite(tvSeries.getId(), Categories.TV_SERIES, tbFavoriteButton.isChecked());
            updateFavoriteInfo(Categories.TV_SERIES);
        } else {
            updateMoviesStatusFavorite(movie.getId(), Categories.MOVIE, tbFavoriteButton.isChecked());
            updateFavoriteInfo(Categories.MOVIE);
        }
    }


    void updateFavoriteInfo(String type) {
        if (type == Categories.MOVIE) {
            tbFavoriteButton.setChecked(movie.isFavorite);
        } else {
            tbFavoriteButton.setChecked(tvSeries.isFavorite);
        }
    }


    void updateTvSeriesStatusFavorite(int id, final String mediaType, final boolean favorite) {
        RequestManager.getInstance(getBaseContext()).markTvSeriesAsFavorite(
                Util.currentUser.getId(),
                mediaType,
                id,
                favorite,
                new Callback<TvSeries>() {
                    @Override
                    public void onResponse(Call<TvSeries> call, Response<TvSeries> response) {
                        tvSeries.isFavorite = favorite;
                    }

                    @Override
                    public void onFailure(Call<TvSeries> call, Throwable t) {

                    }
                }
        );

    }


    void updateMoviesStatusFavorite(int id, final String mediaType, final boolean favorite) {
        RequestManager.getInstance(getBaseContext()).markMovieAsFavorite(
                Util.currentUser.getId(),
                mediaType,
                id,
                favorite,
                new Callback<Movie>() {
                    @Override
                    public void onResponse(Call<Movie> call, Response<Movie> response) {
                        movie.isFavorite = favorite;
                        Toast.makeText(getBaseContext(), response.message().toString(), Toast.LENGTH_SHORT).show();
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

        tbFavoriteButton.setChecked(movie.isFavorite);


    }

    void updateTvSeriesInfo() {
        Glide.with(this).load(tvSeries.getPosterUrl()).into(ivImagePoster);
        Glide.with(this).load(tvSeries.getImageUrl()).into(ivImageDetail);
        tvDescriptionMovieTitle.setText(tvSeries.getDescription());
        tvMovieSeriesTitle.setText(tvSeries.getTitle());
        tvReleaseDate.setText(tvSeries.getFirst_air_date());
        tvVoteAverage.setText(String.valueOf(tvSeries.getVoteAverage()));

        tbFavoriteButton.setChecked(tvSeries.isFavorite);
    }


    @Override
    public void onBackPressed() {

        super.onBackPressed();
    }
}
