package com.madalinaloghin.movieapp.api;

import android.content.Context;
import android.content.pm.LauncherApps;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.madalinaloghin.movieapp.R;
import com.madalinaloghin.movieapp.api.response.ResponsePopularMovies;
import com.madalinaloghin.movieapp.api.response.ResponsePopularPeople;
import com.madalinaloghin.movieapp.api.response.ResponsePopularTvSeries;
import com.madalinaloghin.util.object.Movie;
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
                .baseUrl(getBaseURL())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
    }

    public String getBaseURL() {
        return mAppContext.getResources().getString(R.string.base_url);
    }

    public String getApiKey() {
        return mAppContext.getResources().getString(R.string.api_key);
    }

    public void querySessionId(@NonNull final String requestToken, @Nullable final Callback<String> callback) {
        MoviesService service = mRetrofit.create(MoviesService.class);
        Call<String> call = service.querySessionID(getApiKey(), requestToken);
        if (call != null) {
            call.enqueue(callback);
        }
    }

    public void queryUserAccount(@NonNull final String requestToken, @Nullable final Callback<User> callback) {
        MoviesService service = mRetrofit.create(MoviesService.class);
        Call<User> call = service.queryUserAccount(getApiKey(), requestToken);
        if (call != null) {
            call.enqueue(callback);
        }
    }

    public void queryPopularMovies(final int page, @Nullable final Callback<ResponsePopularMovies> callback){
        MoviesService service = mRetrofit.create(MoviesService.class);
        Call<ResponsePopularMovies> call = service.queryPopularMovies(getApiKey(), page);
        if(call !=null){
            call.enqueue(callback);
        }
    }

    public void queryPopularPeople(final int page, @Nullable final Callback<ResponsePopularPeople> callback){
        MoviesService service = mRetrofit.create(MoviesService.class);
        Call<ResponsePopularPeople> call = service.queryPopularPersons(getApiKey(),page);
        if(call !=null){
            call.enqueue(callback);
        }
    }
    public void queryPopularTvSeries(final int page, @Nullable final Callback<ResponsePopularTvSeries> callback){
        MoviesService service = mRetrofit.create(MoviesService.class);
        Call<ResponsePopularTvSeries> call = service.queryPopularTvSeries(getApiKey(),page);
        if(call !=null){
            call.enqueue(callback);
        }
    }


}
