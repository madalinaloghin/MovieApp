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
import com.madalinaloghin.movieapp.api.response.ResponseMultiSearch;
import com.madalinaloghin.movieapp.api.response.ResponseUserListDetails;
import com.madalinaloghin.movieapp.api.response.ResponseUserLists;
import com.madalinaloghin.util.Util;
import com.madalinaloghin.util.object.Movie;
import com.madalinaloghin.util.object.Person;
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


    public void querySessionId(@NonNull final String apiKey, @NonNull final String requestToken, @Nullable final Callback<String> callback) {
        MoviesService service = mRetrofit.create(MoviesService.class);
        Call<String> call = service.querySessionID(apiKey, requestToken);
        if (call != null) {
            call.enqueue(callback);
        }
    }

    public void queryUserAccount(@NonNull final String apiKey, @NonNull final String sessionId, @Nullable final Callback<User> callback) {
        MoviesService service = mRetrofit.create(MoviesService.class);
        Call<User> call = service.queryUserAccount(apiKey, sessionId);
        if (call != null) {
            call.enqueue(callback);
        }
    }

    public void queryPopularMovies(@NonNull final String apiKey, @NonNull final int page, @Nullable final Callback<ResponseListMovies> callback) {
        MoviesService service = mRetrofit.create(MoviesService.class);
        Call<ResponseListMovies> call = service.queryPopularMovies(apiKey, page);
        if (call != null) {
            call.enqueue(callback);
        }
    }

    public void queryPopularPeople(@NonNull final String apiKey, @NonNull final int page, @Nullable final Callback<ResponseListPeople> callback) {
        MoviesService service = mRetrofit.create(MoviesService.class);
        Call<ResponseListPeople> call = service.queryPopularPersons(apiKey, page);
        if (call != null) {
            call.enqueue(callback);
        }
    }

    public void queryPopularTvSeries(@NonNull final String apiKey, @NonNull final int page, @Nullable final Callback<ResponseListTvSeries> callback) {
        MoviesService service = mRetrofit.create(MoviesService.class);
        Call<ResponseListTvSeries> call = service.queryPopularTvSeries(apiKey, page);
        if (call != null) {
            call.enqueue(callback);
        }
    }


    public void queryFavoriteMovies(@NonNull final String apiKey, @NonNull final String sessionId, @NonNull final int accountId, final int page, @Nullable final Callback<ResponseListFavoriteMovies> callback) {
        MoviesService service = mRetrofit.create(MoviesService.class);
        Call<ResponseListFavoriteMovies> call = service.queryFavoriteMovies(accountId, apiKey, sessionId, page);
        if (call != null) {
            call.enqueue(callback);
        }
    }

    public void queryFavoriteTvSeries(@NonNull final String apiKey, @NonNull final String sessionId, @NonNull final int accountId, final int page, @Nullable final Callback<ResponseListFavoriteTvSeries> callback) {
        MoviesService service = mRetrofit.create(MoviesService.class);
        Call<ResponseListFavoriteTvSeries> call = service.queryFavoriteTvSeries(accountId, apiKey, sessionId, page);
        if (call != null) {
            call.enqueue(callback);
        }
    }


    public void markTvSeriesAsFavorite(@NonNull final String apiKey, @NonNull final String sessionId, @NonNull final int accountId, @NonNull final String mediaType, @NonNull final int mediaId, @NonNull final boolean favorite, @Nullable final Callback<TvSeries> callback) {
        MoviesService service = mRetrofit.create(MoviesService.class);
        Call<TvSeries> call = service.updateTvSeriesMarkAsFavorite(accountId, apiKey, sessionId, mediaType, mediaId, favorite);
        if (call != null) {
            call.enqueue(callback);
        }
    }

    public void markMovieAsFavorite(@NonNull final String apiKey, @NonNull final String sessionId, @NonNull final int accountId, @NonNull final String mediaType, @NonNull final int mediaId, @NonNull final boolean favorite, @Nullable final Callback<Movie> callback) {
        MoviesService service = mRetrofit.create(MoviesService.class);
        Call<Movie> call = service.updateMovieMarkAsFavorite(accountId, apiKey, sessionId, mediaType, mediaId, favorite);
        if (call != null) {
            call.enqueue(callback);
        }
    }


    public void queryUserList(@NonNull final int userId, @NonNull final String apiKey, @NonNull final String sessionId, @NonNull final int mCurrentPage, @Nullable final Callback<ResponseUserLists> callback) {
        MoviesService service = mRetrofit.create(MoviesService.class);
        Call<ResponseUserLists> call = service.queryUserLists(userId, apiKey, sessionId, mCurrentPage);
        if (call != null) {
            call.enqueue(callback);
        }
    }

    public void queryUserListsDetails(@NonNull final String apiKey, @NonNull final int listId, @Nullable final Callback<ResponseUserListDetails> callback) {
        MoviesService service = mRetrofit.create(MoviesService.class);
        Call<ResponseUserListDetails> call = service.queryListDetails(listId, apiKey);
        if (call != null) {
            call.enqueue(callback);
        }
    }

    public void queryMultiSearch(@NonNull final String apiKey, @NonNull final String query, @NonNull final int page, @Nullable final Callback<ResponseMultiSearch> callback) {
        MoviesService service = mRetrofit.create(MoviesService.class);
        Call<ResponseMultiSearch> call = service.queryMultiSearch(apiKey, query, page);
        if (call != null) {
            call.enqueue(callback);
        }
    }


    public void queryMovieDetails(@NonNull final String apiKey, @NonNull final int movieId, @Nullable final Callback<Movie> callback) {
        MoviesService service = mRetrofit.create(MoviesService.class);
        Call<Movie> call = service.queryMovieDetails(movieId, apiKey);
        if (call != null) {
            call.enqueue(callback);
        }
    }

    public void queryTvSeriesDetail(@NonNull final String apiKey, @NonNull final int tvSeriesId, @Nullable Callback<TvSeries> callback) {
        MoviesService service = mRetrofit.create(MoviesService.class);
        Call<TvSeries> call = service.queryTvSeriesDetails(tvSeriesId, apiKey);
        if (call != null) {
            call.enqueue(callback);
        }
    }


    public void queryPersonDetail(@NonNull final String apiKey, @NonNull final int personId, @Nullable Callback<Person> callback) {
        MoviesService service = mRetrofit.create(MoviesService.class);
        Call<Person> call = service.queryPersonDetails(personId, apiKey);
        if (call != null) {
            call.enqueue(callback);
        }
    }


    public void querySearchMovies(@NonNull final String apiKey, @NonNull final String query, @NonNull final int page, @Nullable Callback<ResponseListMovies> callback) {
        MoviesService service = mRetrofit.create(MoviesService.class);
        Call<ResponseListMovies> call = service.querySearchMovies(apiKey, page, query);
        if (call != null) {
            call.enqueue(callback);
        }
    }


    public void querySearchTvSeries(@NonNull final String apiKey, @NonNull final String query, @NonNull final int page, @Nullable Callback<ResponseListTvSeries> callback) {
        MoviesService service = mRetrofit.create(MoviesService.class);
        Call<ResponseListTvSeries> call = service.querySearchTvSeries(apiKey, page, query);
        if (call != null) {
            call.enqueue(callback);
        }
    }


    public void querySearchPerson(@NonNull final String apiKey, @NonNull final String query, @NonNull final int page, @Nullable Callback<ResponseListPeople> callback) {
        MoviesService service = mRetrofit.create(MoviesService.class);
        Call<ResponseListPeople> call = service.querySearchPeople(apiKey, page, query);
        if (call != null) {
            call.enqueue(callback);
        }
    }

    public void rateMovie(@NonNull final String apiKey, @NonNull final String sessionId, @NonNull final float value, @NonNull final int movieId, @Nullable Callback<Movie> callback) {
        MoviesService service = mRetrofit.create(MoviesService.class);
        Call<Movie> call = service.updateRateMovie(movieId, apiKey, sessionId, value);
        if (call != null) {
            call.enqueue(callback);
        }
    }

    public void rateTvSeries(@NonNull final String apiKey, @NonNull final String sessionId, @NonNull final float value, @NonNull final int tvId, @Nullable Callback<TvSeries> callback) {
        MoviesService service = mRetrofit.create(MoviesService.class);
        Call<TvSeries> call = service.updateRateTvSeries(tvId, apiKey, sessionId, value);
        if (call != null) {
            call.enqueue(callback);
        }
    }

    public void queryRatedMovieList(@NonNull final int userId, @NonNull final String apiKey, @NonNull final String sessionId, @NonNull final int page, @Nullable Callback<ResponseListMovies> callback) {
        MoviesService service = mRetrofit.create(MoviesService.class);
        Call<ResponseListMovies> call = service.queryRatedMovieList(userId, apiKey, sessionId, page);
        if (call != null) {
            call.enqueue(callback);
        }
    }


}

