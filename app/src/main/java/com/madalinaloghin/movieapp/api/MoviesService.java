package com.madalinaloghin.movieapp.api;

import com.madalinaloghin.movieapp.api.response.ResponseImageList;
import com.madalinaloghin.movieapp.api.response.ResponseListFavoriteMovies;
import com.madalinaloghin.movieapp.api.response.ResponseListFavoriteTvSeries;
import com.madalinaloghin.movieapp.api.response.ResponseListMovies;
import com.madalinaloghin.movieapp.api.response.ResponseListPeople;
import com.madalinaloghin.movieapp.api.response.ResponseListTvSeries;
import com.madalinaloghin.movieapp.api.response.ResponseMultiSearch;
import com.madalinaloghin.movieapp.api.response.ResponseUserListDetails;
import com.madalinaloghin.movieapp.api.response.ResponseUserLists;
import com.madalinaloghin.util.object.AccountState;
import com.madalinaloghin.util.object.Movie;
import com.madalinaloghin.util.object.Person;
import com.madalinaloghin.util.object.TvSeries;
import com.madalinaloghin.util.object.User;

import retrofit2.Call;
import retrofit2.http.DELETE;
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


    @GET("account/{account_id}/lists")
    Call<ResponseUserLists> queryUserLists(@Path("account_id") int accountId,
                                           @Query("api_key") String apiKey,
                                           @Query("session_id") String sessionId,
                                           @Query("page") int page);

    @GET("list/{list_id}")
    Call<ResponseUserListDetails> queryListDetails(@Path("list_id") int listId,
                                                   @Query("api_key") String apiKey);


    @GET("search/multi")
    Call<ResponseMultiSearch> queryMultiSearch(@Query("api_key") String apiKey,
                                               @Query("query") String query,
                                               @Query("page") int page);


    @GET("movie/{movie_id}")
    Call<Movie> queryMovieDetails(@Path("movie_id") int movieId,
                                  @Query("api_key") String apiKey);


    @GET("tv/{tv_id}")
    Call<TvSeries> queryTvSeriesDetails(@Path("tv_id") int tvId,
                                        @Query("api_key") String apiKey);


    @GET("person/{person_id}")
    Call<Person> queryPersonDetails(@Path("person_id") int personId,
                                    @Query("api_key") String apiKey);


    @GET("search/movie")
    Call<ResponseListMovies> querySearchMovies(@Query("api_key") String apiKey,
                                               @Query("page") int page,
                                               @Query("query") String query);

    @GET("search/tv")
    Call<ResponseListTvSeries> querySearchTvSeries(@Query("api_key") String apiKey,
                                                   @Query("page") int page,
                                                   @Query("query") String query);

    @GET("search/person")
    Call<ResponseListPeople> querySearchPeople(@Query("api_key") String apiKey,
                                               @Query("page") int page,
                                               @Query("query") String query);


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


    @FormUrlEncoded
    @POST("tv/{tv_id}/rating")
    Call<TvSeries> updateRateTvSeries(@Path("tv_id") int tvId,
                                      @Query("api_key") String apiKey,
                                      @Query("session_id") String sessionId,
                                      @Field("value") Number rating);


    @FormUrlEncoded
    @POST("movie/{movie_id}/rating")
    Call<Movie> updateRateMovie(@Path("movie_id") int movieId,
                                @Query("api_key") String apiKey,
                                @Query("session_id") String sessionId,
                                @Field("value") Number rating);


    @DELETE("movie/{movie_id}/rating")
    Call<Movie> deleteMovieRate(@Path("movie_id") int movieId,
                                @Query("api_key") String apiKey,
                                @Query("session_id") String sessionId);

    @DELETE("tv/{tv_id}/rating")
    Call<TvSeries> deleteTvSeriesRate(@Path("tv_id") int tvId,
                                      @Query("api_key") String apiKey,
                                      @Query("session_id") String sessionId);


    @GET("account/{account_id}/rated/movies")
    Call<ResponseListMovies> queryRatedMovieList(@Path("account_id") int accountId,
                                                 @Query("api_key") String apiKey,
                                                 @Query("session_id") String sessionId,
                                                 @Query("page") int page);


    @GET("account/{account_id}/rated/tv")
    Call<ResponseListTvSeries> queryRatedTvSeriesList(@Path("account_id") int accountId,
                                                      @Query("api_key") String apiKey,
                                                      @Query("session_id") String sessionId,
                                                      @Query("page") int page);


    @GET("movie/{movie_id}/similar")
    Call<ResponseListMovies> querySimilarMovies(@Path("movie_id") int movieId,
                                                @Query("api_key") String apiKey,
                                                @Query("page") int page);


    @GET("tv/{tv_id}/similar")
    Call<ResponseListTvSeries> querySimilarTvSeries(@Path("tv_id") int tvId,
                                                    @Query("api_key") String apiKey,
                                                    @Query("page") int page);


    @GET("person/{person_id}/images")
    Call<ResponseImageList> queryPersonImages(@Path("person_id") int personId,
                                              @Query("api_key") String apikey);


    @GET("movie/{movie_id}/account_states")
    Call<AccountState> queryMovieAccountStates(@Path("movie_id") int movieId,
                                               @Query("api_key") String apiKey,
                                               @Query("session_id") String sessionId);


    @GET("tv/{tv_id}/account_states")
    Call<AccountState> queryTvSeriesAccountStates(@Path("tv_id") int tvId,
                                               @Query("api_key") String apiKey,
                                               @Query("session_id") String sessionId);

}

