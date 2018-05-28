package com.ddd.mvvmsampleapp.model.repository;

import com.ddd.mvvmsampleapp.model.pojo.movies.DiscoverMovies;
import com.ddd.mvvmsampleapp.model.pojo.movies.movie_details.MoviesDetails;
import com.ddd.mvvmsampleapp.model.pojo.movies.upcoming_movies.UpcomingMovies;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by S.C. on 11/05/18.
 */

public interface ServiceCallback {

    @GET("movie/now_playing")
    Call<DiscoverMovies> discoverMovies(@Query("api_key") String apiKey, @Query("language") String language,
                                        @Query("sort_by") String sortBy, @Query("region") String region,
                                        @Query("page") int page);

    @GET("movie/upcoming")
    Call<UpcomingMovies> upcomingMovies(@Query("api_key") String apiKey, @Query("language") String language,
                                        @Query("region") String region, @Query("page") int page);

    @GET("movie/{id}")
    Call<MoviesDetails> getMovie(@Path("id") int movieId, @Query("api_key") String apiKey);


}