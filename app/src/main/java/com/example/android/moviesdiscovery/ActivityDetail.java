package com.example.android.moviesdiscovery;


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ActivityDetail extends AppCompatActivity {


    //    private TextView mtitle;
    private Movie mmovie;

    private RecyclerView mRecyclerView;

    private ReviewAdapter mReviewAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        TextView mtitle = (TextView) findViewById(R.id.tv_detail_title);

        Intent intentThatStartedThisActivity = getIntent();

        if (intentThatStartedThisActivity != null) {

            mmovie = (Movie) getIntent().getSerializableExtra("serializable");

            String title= mmovie.getTitle();
            mtitle.setText(title);

            ImageView mimagePathTextView = (ImageView) findViewById(R.id.iv_image);

            Picasso.get().load("http://image.tmdb.org/t/p/w185/"+mmovie.getPosterPath()).into(mimagePathTextView);




            TextView synopsisTextView = (TextView) findViewById(R.id.tv_synopsis);
            synopsisTextView.setText(mmovie.getOverview());
            Log.v("**UPDATE_UI",mmovie.getOverview());

            TextView ratingTextView = (TextView) findViewById(R.id.tv_rating);
            ratingTextView.setText(Double.toString(mmovie.getVoteAverage()));
            Log.v("**UPDATE_UI",String.valueOf(mmovie.getVoteAverage()));


            TextView releaseDateTextView = (TextView) findViewById(R.id.tv_releaseDate);
            releaseDateTextView.setText(mmovie.getReleaseDate());
            Log.v("**UPDATE_UI",mmovie.getReleaseDate());

            Log.v("**onCreateID", String.valueOf(mmovie.getId()));


            mRecyclerView = (RecyclerView) findViewById(R.id.rv_reviews);
            LinearLayoutManager manager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);

            mRecyclerView.setLayoutManager(manager);
            mRecyclerView.setHasFixedSize(true);

            mReviewAdapter = new ReviewAdapter();

            mRecyclerView.setAdapter(mReviewAdapter);


            makeNetworkConnectionReview();
            makeNetworkConnectionTrailer();




        }

    }


    public void makeNetworkConnectionReview() {

        Log.v("**makeNetworkConnection","entro");

        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.addInterceptor(loggingInterceptor);
        OkHttpClient okHttpClient = builder
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://api.themoviedb.org/3/movie/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build();

        TMDBRESTApi dummyRestApiExample = retrofit.create(TMDBRESTApi.class);
//        Call<Movie> call = dummyRestApiExample.getPopular("1cf6722020fb5ceac020f7c2adb96500");
            Call<ResultReview> call;

            call = dummyRestApiExample.getReviews( mmovie.getId(),"1cf6722020fb5ceac020f7c2adb96500");
            call.enqueue(new Callback<ResultReview>() {


                @Override
                public void onResponse(Call<ResultReview> call, Response<ResultReview> response) {
                    if (!response.isSuccessful()) {
                        Log.v("**onResponseReview","not successful");
                         return;
                    }
                    List<Review> reviews = response.body().getResults();
                    for (Review review:reviews){
                        Log.v("**onResponse, Review",review.getContent()+ "\n\n");

                    }
                    mReviewAdapter.setReviewData(reviews);



                }

                @Override
                public void onFailure(Call<ResultReview> call, Throwable t) {
                    Log.v("**onFailure", t.getMessage());
                }


        });
    }

    public void makeNetworkConnectionTrailer() {
        Log.v("**makeNetworkConnection","entro");
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.addInterceptor(loggingInterceptor);
        OkHttpClient okHttpClient = builder
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://api.themoviedb.org/3/movie/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build();

        TMDBRESTApi dummyRestApiExample = retrofit.create(TMDBRESTApi.class);
//        Call<Movie> call = dummyRestApiExample.getPopular("1cf6722020fb5ceac020f7c2adb96500");
        Call<ResultTrailer> call;

        call = dummyRestApiExample.getTrailers( mmovie.getId(),"1cf6722020fb5ceac020f7c2adb96500");
        call.enqueue(new Callback<ResultTrailer>() {


            @Override
            public void onResponse(Call<ResultTrailer> call, Response<ResultTrailer> response) {
                if (!response.isSuccessful()) {
                    Log.v("**onResponseTrailer","not successful");
                    return;
                }
                List<Trailer> trailers = response.body().getResults();
                for (Trailer trailer:trailers){
                    Log.v("**onResponse, Trailer",trailer.getName()+ "\n\n");

                }


            }

            @Override
            public void onFailure(Call<ResultTrailer> call, Throwable t) {
                Log.v("**onFailure", t.getMessage());
            }


        });
    }

}





