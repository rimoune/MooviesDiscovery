package com.example.android.moviesdiscovery;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface TMDBRESTApi {

        @GET("popular")
        Call<Result> getPopular(@Query("api_key") String key
        );

        @GET("top_rated")
        Call<Result> getTopRated(@Query("api_key") String key
        );

}
