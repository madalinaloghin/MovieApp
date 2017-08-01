package com.madalinaloghin.movieapp.api;

import com.madalinaloghin.movieapp.api.response.ResponsePopularMovies;
import com.madalinaloghin.movieapp.api.response.ResponsePopularPeople;
import com.madalinaloghin.movieapp.api.response.ResponsePopularTvSeries;
import com.madalinaloghin.util.object.User;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by madalina.loghin on 7/31/2017.
 */

public interface MoviesService {

    @GET("authentification/session/new")
    Call<String> querySessionID (@Query("api_key") String apiKey,
                                 @Query("request_token") String token);


    @GET("account")
    Call<User> queryUserAccount(@Query("api_key") String apiKey,
                                @Query("request_token") String token);


    @GET("person/popular")
    Call<ResponsePopularPeople> queryPopularPersons(@Query("api_key") String apiKey,
                                                    @Query("page") int page);

    @GET("movie/popular")
    Call<ResponsePopularMovies> queryPopularMovies(@Query("api_key") String apiKey,
                                                    @Query("page") int page);

    @GET("tv/popular")
    Call<ResponsePopularTvSeries> queryPopularTvSeries(@Query("api_key") String apiKey,
                                                    @Query("page") int page);


}
