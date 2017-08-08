package com.madalinaloghin.movieapp.api;

import com.madalinaloghin.movieapp.api.response.ResponseListFavoriteMovies;
import com.madalinaloghin.movieapp.api.response.ResponseListFavoriteTvSeries;
import com.madalinaloghin.movieapp.api.response.ResponseListMovies;
import com.madalinaloghin.movieapp.api.response.ResponseListPeople;
import com.madalinaloghin.movieapp.api.response.ResponseListTvSeries;
import com.madalinaloghin.movieapp.api.response.ResponseUserListDetails;
import com.madalinaloghin.movieapp.api.response.ResponseUserLists;
import com.madalinaloghin.util.object.Movie;
import com.madalinaloghin.util.object.TvSeries;
import com.madalinaloghin.util.object.User;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by madalina.loghin on 7/31/2017.
 */

public interface MoviesService {

    @GET("authentification/session/new")
    Call<String> querySessionID(@Query("api_key") String apiKey,
                                @Query("request_token") String token);


    @GET("account")
    Call<User> queryUserAccount(@Query("api_key") String apiKey,
                                @Query("session_id") String sessionId);


    @GET("person/popular")
    Call<ResponseListPeople> queryPopularPersons(@Query("api_key") String apiKey,
                                                 @Query("page") int page);

    @GET("movie/popular")
    Call<ResponseListMovies> queryPopularMovies(@Query("api_key") String apiKey,
                                                @Query("page") int page);

    @GET("tv/popular")
    Call<ResponseListTvSeries> queryPopularTvSeries(@Query("api_key") String apiKey,
                                                    @Query("page") int page);


    @GET("account/{account_id}/favorite/movies")
    Call<ResponseListFavoriteMovies> queryFavoriteMovies(@Path("account_id") int accountId,
                                                         @Query("api_key") String apiKey,
                                                         @Query("session_id") String sessionId,
                                                         @Query("page") int page);

    @GET("account/{account_id}/favorite/tv")
    Call<ResponseListFavoriteTvSeries> queryFavoriteTvSeries(@Path("account_id") int accountId,
                                                             @Query("api_key") String apiKey,
                                                             @Query("session_id") String sessionId,
                                                             @Query("page") int page);


    @FormUrlEncoded
    @POST("account/{account_id}/favorite")
    Call<TvSeries> updateTvSeriesMarkAsFavorite(@Path("account_id") int accountId,
                                                @Query("api_key") String apiKey,
                                                @Query("session_id") String sessionId,
                                                @Field("media_type") String mediaType,
                                                @Field("media_id") int mediaId,
                                                @Field("favorite") boolean favorite);

    @FormUrlEncoded
    @POST("account/{account_id}/favorite")
    Call<Movie> updateMovieMarkAsFavorite(@Path("account_id") int accountId,
                                          @Query("api_key") String apiKey,
                                          @Query("session_id") String sessionId,
                                          @Field("media_type") String mediaType,
                                          @Field("media_id") int mediaId,
                                          @Field("favorite") boolean favorite);


    @GET("account/{account_id}/lists")
    Call<ResponseUserLists> queryUserLists(@Path("account_id") int accountId,
                                           @Query("api_key") String apiKey,
                                           @Query("session_id") String sessionId,
                                           @Query("page") int page);

    @GET("list/{list_id}")
    Call<ResponseUserListDetails> queryListDetails(@Path("list_id") int listId,
                                                   @Query("api_key") String apiKey);


}
