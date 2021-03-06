package com.example.android.moviesdiscovery;


import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.android.moviesdiscovery.LocalDB.Favourite;
import com.example.android.moviesdiscovery.LocalDB.FavouriteViewModel;
import com.example.android.moviesdiscovery.Network.Movie;
import com.example.android.moviesdiscovery.Network.ResultReview;
import com.example.android.moviesdiscovery.Network.Review;
import com.example.android.moviesdiscovery.Network.TMDBRESTApi;
import com.example.android.moviesdiscovery.Reviews.ReviewAdapter;
import com.example.android.moviesdiscovery.Network.ResultTrailer;
import com.example.android.moviesdiscovery.Network.Trailer;
import com.example.android.moviesdiscovery.Trailers.TrailerAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.squareup.picasso.Picasso;

import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class ActivityDetail extends AppCompatActivity implements TrailerAdapter.TrailerAdapterOnClickHandler{


    //    private TextView mtitle;
    private Movie mmovie;


    private RecyclerView mReviewRecyclerView;

    private ReviewAdapter mReviewAdapter;


    private RecyclerView mTrailerRecyclerView;

    private TrailerAdapter mTrailerAdapter;

    private FloatingActionButton fab;

    private FavouriteViewModel mFavouriteViewModel;

    private Parcelable savedRecyclerLayoutStatereview;
    private Parcelable savedRecyclerLayoutStatetrailer;

    private LinearLayoutManager reviewmanager;
    private LinearLayoutManager trailermanager;
    private static final String BUNDLE_RECYCLER_LAYOUT_REVIEW = "recycler_review";
    private static final String BUNDLE_RECYCLER_LAYOUT_TRAILER = "recycler_trailer";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_detail);
        setContentView(R.layout.activity_detail);

        TextView mtitle = (TextView) findViewById(R.id.tv_detail_title);

        Intent intentThatStartedThisActivity = getIntent();

        if (intentThatStartedThisActivity != null) {

            mFavouriteViewModel = ViewModelProviders.of(this).get(FavouriteViewModel.class);

            mmovie = (Movie) getIntent().getSerializableExtra("serializable");

            Favourite favourite=new Favourite(mmovie.getId(),mmovie.getPosterPath(),mmovie.getTitle(),mmovie.getReleaseDate());

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


            fab = (FloatingActionButton) findViewById(R.id.fab);
            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Log.v("**FAB", "Add the movie to favourites!");

                    mFavouriteViewModel.insert(favourite);
                }
            });

            mReviewRecyclerView = (RecyclerView) findViewById(R.id.rv_reviews);
            reviewmanager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);

            mReviewRecyclerView.setLayoutManager(reviewmanager);
            mReviewRecyclerView.setHasFixedSize(true);

            mReviewAdapter = new ReviewAdapter();

            mReviewRecyclerView.setAdapter(mReviewAdapter);

            //Trailer
            mTrailerRecyclerView = (RecyclerView) findViewById(R.id.rv_trailers);
            trailermanager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);

            mTrailerRecyclerView.setLayoutManager(trailermanager);
            mTrailerRecyclerView.setHasFixedSize(true);

            mTrailerAdapter = new TrailerAdapter(this);

            mTrailerRecyclerView.setAdapter(mTrailerAdapter);


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
                    if(savedRecyclerLayoutStatereview instanceof Parcelable){
                        Log.v("**saved..", String.valueOf(savedRecyclerLayoutStatereview.describeContents()));
                        reviewmanager.onRestoreInstanceState(savedRecyclerLayoutStatereview);

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
                if(savedRecyclerLayoutStatetrailer!=null){
                    trailermanager.onRestoreInstanceState(savedRecyclerLayoutStatetrailer);

                }
                mTrailerAdapter.setTrailerData(trailers);



            }

            @Override
            public void onFailure(Call<ResultTrailer> call, Throwable t) {
                Log.v("**onFailure", t.getMessage());
            }


        });
    }

    @Override
    public void onClick(Trailer trailer) {
        Context context = this;
        Intent intent = new Intent(Intent.ACTION_VIEW);
        Uri file =Uri.parse("https://www.youtube.com/watch?v="+trailer.getKey());
        intent.setData(file);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable(BUNDLE_RECYCLER_LAYOUT_REVIEW,
                reviewmanager.onSaveInstanceState());
        Log.v("**onSave..1",outState.toString());
        outState.putParcelable(BUNDLE_RECYCLER_LAYOUT_TRAILER,
                trailermanager.onSaveInstanceState());
        Log.v("**onSave..2",outState.toString());
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        Log.v("**onRestore..0",savedInstanceState.toString());

        //restore recycler view at same position
        if (savedInstanceState != null) {
            savedRecyclerLayoutStatetrailer = savedInstanceState.getParcelable(BUNDLE_RECYCLER_LAYOUT_TRAILER);
            Log.v("**onRestore..1",savedRecyclerLayoutStatetrailer.toString());
            savedRecyclerLayoutStatereview = savedInstanceState.getParcelable(BUNDLE_RECYCLER_LAYOUT_REVIEW);
            Log.v("**onRestore..2",savedRecyclerLayoutStatereview.toString());
        }
    }
}




