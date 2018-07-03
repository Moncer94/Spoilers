package mobile.proj.spoilers.service;

import mobile.proj.spoilers.model.TV;
import mobile.proj.spoilers.model.TVResults;
import mobile.proj.spoilers.model.VideoResults;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface TvService {

    @GET("tv/{tv_id}")
    Call<TV> tvDetails(
            @Path("tv_id") String id,
            @Query("api_key") String api_key,
            @Query("language") String lang
    );

    @GET("tv/{tv_id}/videos")
    Call<VideoResults> tvVideos(
            @Path("tv_id") String id,
            @Query("api_key") String api_key,
            @Query("language") String lang
    );

    @GET("tv/airing_today")
    Call<TVResults> airingToday(
            @Query("api_key") String api_key,
            @Query("language") String lang,
            @Query("page") int page
    );

    @GET("tv/on_the_air")
    Call<TVResults> onTheAir(
            @Query("api_key") String api_key,
            @Query("language") String lang,
            @Query("page") int page
    );

    @GET("tv/popular")
    Call<TVResults> popular(
            @Query("api_key") String api_key,
            @Query("language") String lang,
            @Query("page") int page
    );

    @GET("tv/top_rated")
    Call<TVResults> topRated(
            @Query("api_key") String api_key,
            @Query("language") String lang,
            @Query("page") int page
    );
}
