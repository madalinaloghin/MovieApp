package com.madalinaloghin.movieapp.api;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.madalinaloghin.movieapp.api.response.ResponseListFavoriteMovies;
import com.madalinaloghin.movieapp.api.response.ResponseListFavoriteTvSeries;
import com.madalinaloghin.movieapp.api.response.ResponseListMovies;
import com.madalinaloghin.movieapp.api.response.ResponseListPeople;
import com.madalinaloghin.movieapp.api.response.ResponseListTvSeries;
import com.madalinaloghin.movieapp.api.response.ResponseUserListDetails;
import com.madalinaloghin.movieapp.api.response.ResponseUserLists;
import com.madalinaloghin.util.Util;
import com.madalinaloghin.util.object.Movie;
import com.madalinaloghin.util.object.TvSeries;
import com.madalinaloghin.util.object.User;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by madalina.loghin on 7/31/2017.
 */


public class RequestManager {

    private static RequestManager instance;
    private Context mAppContext;
    private Retrofit mRetrofit;

    public static RequestManager getInstance(@NonNull final Context context) {
        if (instance == null) {
            instance = new RequestManager(context);
        }
        return instance;
    }

    public RequestManager(Context context) {
        mAppContext = context.getApplicationContext();
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        mRetrofit = new Retrofit.Builder()
                .baseUrl(Util.getBaseURL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
    }


    public void querySessionId(@NonNull final String requestToken, @Nullable final Callback<String> callback) {
        MoviesService service = mRetrofit.create(MoviesService.class);
        Call<String> call = service.querySessionID(Util.getApiKey, requestToken);
        if (call != null) {
            call.enqueue(callback);
        }
    }

    public void queryUserAccount(@NonNull final String sessionId, @Nullable final Callback<User> callback) {
        MoviesService service = mRetrofit.create(MoviesService.class);
        Call<User> call = service.queryUserAccount(Util.getApiKey, sessionId);
        if (call != null) {
            call.enqueue(callback);
        }
    }

    public void queryPopularMovies(final int page, @Nullable final Callback<ResponseListMovies> callback) {
        MoviesService service = mRetrofit.create(MoviesService.class);
        Call<ResponseListMovies> call = service.queryPopularMovies(Util.getApiKey, page);
        if (call != null) {
            call.enqueue(callback);
        }
    }

    public void queryPopularPeople(final int page, @Nullable final Callback<ResponseListPeople> callback) {
        MoviesService service = mRetrofit.create(MoviesService.class);
        Call<ResponseListPeople> call = service.queryPopularPersons(Util.getApiKey, page);
        if (call != null) {
            call.enqueue(callback);
        }
    }

    public void queryPopularTvSeries(final int page, @Nullable final Callback<ResponseListTvSeries> callback) {
        MoviesService service = mRetrofit.create(MoviesService.class);
        Call<ResponseListTvSeries> call = service.queryPopularTvSeries(Util.getApiKey, page);
        if (call != null) {
            call.enqueue(callback);
        }
    }


    public void queryFavoriteMovies(@NonNull final int accountId, final int page, @Nullable final Callback<ResponseListFavoriteMovies> callback) {
        MoviesService service = mRetrofit.create(MoviesService.class);
        Call<ResponseListFavoriteMovies> call = service.queryFavoriteMovies(accountId, Util.getApiKey, Util.getSessionId, page);
        if (call != null) {
            call.enqueue(callback);
        }
    }

    public void queryFavoriteTvSeries(@NonNull final int accountId, final int page, @Nullable final Callback<ResponseListFavoriteTvSeries> callback) {
        MoviesService service = mRetrofit.create(MoviesService.class);
        Call<ResponseListFavoriteTvSeries> call = service.queryFavoriteTvSeries(accountId, Util.getApiKey, Util.getSessionId, page);
        if (call != null) {
            call.enqueue(callback);
        }
    }

    public void markTvSeriesAsFavorite(@NonNull final int accountId, @NonNull final String mediaType, @NonNull final int mediaId, @NonNull final boolean favorite, @Nullable final Callback<TvSeries> callback) {
        MoviesService service = mRetrofit.create(MoviesService.class);
        Call<TvSeries> call = service.updateTvSeriesMarkAsFavorite(accountId, Util.getApiKey, Util.getSessionId, mediaType, mediaId, favorite);
        if (call != null) {
            call.enqueue(callback);
        }
    }

    public void markMovieAsFavorite(@NonNull final int accountId, @NonNull final String mediaType, @NonNull final int mediaId, @NonNull final boolean favorite, @Nullable final Callback<Movie> callback) {
        MoviesService service = mRetrofit.create(MoviesService.class);
        Call<Movie> call = service.updateMovieMarkAsFavorite(accountId, Util.getApiKey, Util.getSessionId, mediaType, mediaId, favorite);
        if (call != null) {
            call.enqueue(callback);
        }
    }

    public void queryUserList(int mCurrentPage, @Nullable final Callback<ResponseUserLists> callback) {
        MoviesService service = mRetrofit.create(MoviesService.class);
        Call<ResponseUserLists> call = service.queryUserLists(Util.currentUser.getId(), Util.getApiKey, Util.getSessionId, mCurrentPage);
        if (call != null) {
            call.enqueue(callback);
        }
    }

    public void queryUserListsDetails(@NonNull final int listId, @Nullable final Callback<ResponseUserListDetails> callback) {
        MoviesService service = mRetrofit.create(MoviesService.class);
        Call<ResponseUserListDetails> call = service.queryListDetails(listId, Util.getApiKey);
        if (call != null) {
            call.enqueue(callback);
        }
    }
}

