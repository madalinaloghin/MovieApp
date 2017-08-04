package com.madalinaloghin.movieapp.ui.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.bumptech.glide.Glide;
import com.madalinaloghin.movieapp.R;
import com.madalinaloghin.util.object.Categories;
import com.madalinaloghin.util.object.Movie;
import com.madalinaloghin.util.object.TvSeries;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

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
        Toast.makeText(this.getBaseContext(), String.valueOf(tvSeries.isFavorite), Toast.LENGTH_SHORT).show();
    }

    @OnClick(R.id.tb_favorite_movie_toggle)
    void onClickFavoriteButton() {
        int id;
        String tip;
        if (movie == null) {
            id = tvSeries.getId();
            tip = Categories.TV_SERIES;
        } else {
            id = movie.getId();
            tip = Categories.MOVIE;
        }

         //apelat direct cu idul si tipul de aici
    }

    void updateMovieInfo() {
        Glide.with(this).load(movie.getPosterUrl()).into(ivImagePoster);
        Glide.with(this).load(movie.getImageUrl()).into(ivImageDetail);
        tvDescriptionMovieTitle.setText(movie.getDescription());
        tvMovieSeriesTitle.setText(movie.getTitle());
        tvReleaseDate.setText(movie.getReleaseDate());
        tvVoteAverage.setText(String.valueOf(movie.getVoteAverage()));
    }

    void updateTvSeriesInfo() {
        Glide.with(this).load(tvSeries.getPosterUrl()).into(ivImagePoster);
        Glide.with(this).load(tvSeries.getImageUrl()).into(ivImageDetail);
        tvDescriptionMovieTitle.setText(tvSeries.getDescription());
        tvMovieSeriesTitle.setText(tvSeries.getTitle());
        tvReleaseDate.setText(tvSeries.getFirst_air_date());
        tvVoteAverage.setText(String.valueOf(tvSeries.getVoteAverage()));

        if (tvSeries.isFavorite) {
            tbFavoriteButton.setChecked(true);
        } else {
            tbFavoriteButton.setChecked(false);
        }
    }
}
