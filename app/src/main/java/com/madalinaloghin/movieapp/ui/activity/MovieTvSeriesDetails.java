package com.madalinaloghin.movieapp.ui.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.madalinaloghin.movieapp.R;
import com.madalinaloghin.util.object.Movie;
import com.madalinaloghin.util.object.TvSeries;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MovieTvSeriesDetails extends AppCompatActivity {

    private static final String KEY_MOVIE = "Movie";
    private static final String KEY_TV = "Tv";

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

    @BindView(R.id.tv_release_date_movie_tv_series)
    TextView tvReleaseDate;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_tv_series_details);
        Intent intent = getIntent();
        Bundle b = getIntent().getExtras();

        ButterKnife.bind(this);

        if(b.getSerializable(KEY_MOVIE) != null){
            movie = new Movie();
            movie = (Movie) b.getSerializable(KEY_MOVIE);
            updateMovieInfo();
        } else{
            tvSeries = new TvSeries();
            tvSeries = (TvSeries) b.getSerializable(KEY_TV);
            updateTvSeriesInfo();
        }

    }

    void updateMovieInfo(){
        Glide.with(this).load(movie.getPosterUrl()).into(ivImagePoster);
        Glide.with(this).load(movie.getImageUrl()).into(ivImageDetail);
        tvDescriptionMovieTitle.setText(movie.getDescription());
        tvMovieSeriesTitle.setText(movie.getTitle());
        tvReleaseDate.setText(movie.getReleaseDate());

    }

    void updateTvSeriesInfo(){
        Glide.with(this).load(tvSeries.getPosterUrl()).into(ivImagePoster);
        Glide.with(this).load(tvSeries.getImageUrl()).into(ivImageDetail);
        tvDescriptionMovieTitle.setText(tvSeries.getDescription());
        tvMovieSeriesTitle.setText(tvSeries.getTitle());
        tvReleaseDate.setText(tvSeries.getFirst_air_date());
    }
}
