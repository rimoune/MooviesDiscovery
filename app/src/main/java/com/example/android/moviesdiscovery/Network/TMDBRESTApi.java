package com.example.android.moviesdiscovery.Network;

import com.example.android.moviesdiscovery.Network.Result;
import com.example.android.moviesdiscovery.Network.ResultReview;
import com.example.android.moviesdiscovery.Network.ResultTrailer;

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

    @GET("{id}/reviews")
    Call<ResultReview> getReviews(@Path("id") Integer id, @Query("api_key") String key)
                                 ;

    @GET("{id}/videos")
    Call<ResultTrailer> getTrailers(@Path("id") Integer id, @Query("api_key") String key);

}
