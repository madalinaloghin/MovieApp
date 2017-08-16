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
import com.madalinaloghin.movieapp.api.response.ResponseListTvSeries;
import com.madalinaloghin.movieapp.ui.adapter.AdapterSimilarTvSeriesList;
import com.madalinaloghin.util.Util;
import com.madalinaloghin.util.object.AccountState;
import com.madalinaloghin.util.object.Categories;
import com.madalinaloghin.util.object.TvSeries;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TvSeriesDetailsActivity extends AppCompatActivity {

    private TvSeries tvSeries;
    private LinearLayoutManager mLayoutManagerSimilar;
    private AdapterSimilarTvSeriesList mAdapterTvSeries;
    private boolean mIsLoading = false;
    private int mCurrentPage = 0;

    @BindView(R.id.iv_image_banner_tv_series_details)
    ImageView ivImageDetail;

    @BindView(R.id.iv_image_poster_tv_series_details)
    ImageView ivImagePoster;

    @BindView(R.id.tv_title_tv_series_details)
    TextView tvMovieSeriesTitle;

    @BindView(R.id.tv_description_tv_series_details)
    TextView tvDescriptionMovieTitle;

    @BindView(R.id.tv_vote_average_tv_series_details)
    TextView tvVoteAverage;

    @BindView(R.id.tv_release_date_tv_series_details)
    TextView tvReleaseDate;

    @BindView(R.id.tb_favorite_tv_series_details_toggle)
    ToggleButton tbFavoriteButton;

    @BindView(R.id.rb_rating_tv_series_details)
    RatingBar rbRatingTvSeries;

    @BindView(R.id.rv_similiar_tv_series_details)
    RecyclerView rvSimilarSection;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tv_series_details);

        Bundle b = getIntent().getExtras();
        ButterKnife.bind(this);

        rbRatingTvSeries.setOnRatingBarChangeListener(
                new RatingBar.OnRatingBarChangeListener() {
                    @Override
                    public void onRatingChanged(RatingBar ratingBar, float v, boolean b) {
                        updateRatingBarValue(v * 2);
                    }
                }
        );

        tvSeries = new TvSeries();
        tvSeries = (TvSeries) b.getSerializable(Categories.TV_SERIES);
        getTvSeriesAccountState(tvSeries.getId());
        updateTvSeriesInfo();
        setupRecycleViewTvSeries();
        getSimilarTvSeries(tvSeries.getId());

    }


    @OnClick(R.id.iv_image_poster_tv_series_details)
    void clickImage() {
        Toast.makeText(this.getBaseContext(), String.valueOf(tvSeries.isFavorite()), Toast.LENGTH_SHORT).show();
    }

    @OnClick(R.id.tb_favorite_tv_series_details_toggle)
    void onClickFavoriteButton() {
        updateTvSeriesStatusFavorite(tvSeries.getId(), Categories.TV_SERIES, tbFavoriteButton.isChecked());
        tbFavoriteButton.setChecked(tvSeries.isFavorite());
    }


    void getTvSeriesAccountState(int id) {
        RequestManager.getInstance(getBaseContext()).queryTvSeriesAccountState(
                id,
                Util.getApiKey,
                Util.getSessionId,
                new Callback<AccountState>() {
                    @Override
                    public void onResponse(Call<AccountState> call, Response<AccountState> response) {
                        tvSeries.setFavorite(response.body().getFavorite());
                        if (response.body().getRated() != null) {
                            tvSeries.setRated(response.body().getRated());
                        }
                        updateTvSeriesInfo();
                    }

                    @Override
                    public void onFailure(Call<AccountState> call, Throwable t) {

                    }
                }
        );
    }

    void updateRatingBarValue(float value) {
        RequestManager.getInstance(getBaseContext()).rateTvSeries(
                Util.getApiKey,
                Util.getSessionId,
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

    void updateTvSeriesStatusFavorite(int id, final String mediaType, final boolean favorite) {
        RequestManager.getInstance(getBaseContext()).markTvSeriesAsFavorite(
                Util.getApiKey,
                Util.getSessionId,
                Util.currentUser.getId(),
                mediaType,
                id,
                favorite,
                new Callback<TvSeries>() {
                    @Override
                    public void onResponse(Call<TvSeries> call, Response<TvSeries> response) {
                        tvSeries.setFavorite(favorite);
                        tbFavoriteButton.setChecked(tvSeries.isFavorite());
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
                Intent intent = new Intent(TvSeriesDetailsActivity.this, TvSeriesDetailsActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable(Categories.TV_SERIES, tvSeries);
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

    void getSimilarTvSeries(int id) {
        mCurrentPage++;
        mIsLoading = true;
        RequestManager.getInstance(getBaseContext()).querySimilarTvSeries(
                id,
                Util.getApiKey,
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


    void updateTvSeriesInfo() {
        Glide.with(this).load(tvSeries.getPosterUrl()).into(ivImagePoster);
        Glide.with(this).load(tvSeries.getImageUrl()).into(ivImageDetail);
        tvDescriptionMovieTitle.setText(tvSeries.getDescription());
        tvMovieSeriesTitle.setText(tvSeries.getTitle());
        tvReleaseDate.setText(tvSeries.getFirst_air_date());
        tvVoteAverage.setText(String.valueOf(tvSeries.getVoteAverage()));
        tbFavoriteButton.setChecked(tvSeries.isFavorite());
        if (tvSeries.getRated() != null) {
            rbRatingTvSeries.setRating((tvSeries.getRated().getValue()) / 2);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

}
