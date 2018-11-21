package net.husnikamil.moviehouse;

import retrofit2.Call;

import retrofit2.http.GET;
import retrofit2.http.Query;

public interface TmdbClient {

    @GET("/3/movie/now_playing")
    Call<MovieData> getNowPlaying(@Query("api_key") String api_key);

    @GET("/3/movie/upcoming")
    Call<MovieData> getUpComing(@Query("api_key") String api_key);

    @GET("3/search/movie")
    Call<MovieData> getSearch(@Query("api_key") String api_key,
                                    @Query("query") String query);

}
