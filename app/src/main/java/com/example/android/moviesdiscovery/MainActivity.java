package com.example.android.moviesdiscovery;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.PreferenceManager;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Parcelable;
import android.os.PersistableBundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.example.android.moviesdiscovery.Network.Movie;
import com.example.android.moviesdiscovery.Movies.PosterAdapter;
import com.example.android.moviesdiscovery.Network.Result;
import com.example.android.moviesdiscovery.Network.TMDBRESTApi;

import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static java.lang.StrictMath.ceil;

public class MainActivity extends AppCompatActivity implements PosterAdapter.MoviesAdapterOnClickHandler, SharedPreferences.OnSharedPreferenceChangeListener {


    private RecyclerView mRecyclerView;

    private PosterAdapter mPosterAdapter;

    private String mOrderBy;

    private Parcelable savedRecyclerLayoutState;

    private GridLayoutManager manager;

    private static final String BUNDLE_RECYCLER_LAYOUT = "recycler_layout";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        mRecyclerView = (RecyclerView) findViewById(R.id.rv_poster);
        int span =calculateNoOfColumns(this);
        manager = new GridLayoutManager(this, span, GridLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(manager);
        mRecyclerView.setHasFixedSize(true);

        mPosterAdapter = new PosterAdapter(this);

        mRecyclerView.setAdapter(mPosterAdapter);

        setupSharedPreferences();
        makeNetworkConnection();


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // Unregister VisualizerActivity as an OnPreferenceChangedListener to avoid any memory leaks.
        PreferenceManager.getDefaultSharedPreferences(this)
                .unregisterOnSharedPreferenceChangeListener(this);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            Intent settingsIntent = new Intent(this, SettingsActivity.class);
            startActivity(settingsIntent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void makeNetworkConnection() {

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
        Call<Result> call;
//        Call<Movie> call = dummyRestApiExample.getPopular("1cf6722020fb5ceac020f7c2adb96500");
        if (mOrderBy.equals("top_rated") ) {
            call = dummyRestApiExample.getTopRated("1cf6722020fb5ceac020f7c2adb96500");
        } else {
            call = dummyRestApiExample.getPopular("1cf6722020fb5ceac020f7c2adb96500");
        }
        call.enqueue(new Callback<Result>() {


            @Override
            public void onResponse(Call<Result> call, Response<Result> response) {
                if (!response.isSuccessful()) {

                    return;
                }
                Log.v("**onResponse>", response.message());
                List<Movie> movies = response.body().getMovies();
                if(savedRecyclerLayoutState!=null){
                    manager.onRestoreInstanceState(savedRecyclerLayoutState);

                }
                mPosterAdapter.setPosterData(movies);


            }

            @Override
            public void onFailure(Call<Result> call, Throwable t) {
                Log.v("**onFailure", t.getMessage());
            }

        });
    }

    @Override
    public void onClick(Movie movie) {
        Context context = this;

        Class destinationClass = ActivityDetail.class;
        Intent intentToStartDetailActivity = new Intent(context, destinationClass);

        Bundle bundle = new Bundle();
        bundle.putSerializable("serializable", movie);
        intentToStartDetailActivity.putExtras(bundle);

        Log.v("**onClick", String.valueOf(movie));
        startActivity(intentToStartDetailActivity);
    }

    private void setupSharedPreferences() {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        mOrderBy = sharedPreferences.getString(
                getString(R.string.settings_order_by_key),
                getString(R.string.settings_order_by_default)
        );
        Log.v("**onCreate, orderBy", mOrderBy);
        sharedPreferences.registerOnSharedPreferenceChangeListener(this);

    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        Log.v("**onSharedChanged",key);
        if (key.equals(getString(R.string.settings_order_by_key))) {
            mOrderBy = sharedPreferences.getString(
                    getString(R.string.settings_order_by_key),
                    getString(R.string.settings_order_by_default));
            Log.v("**onSharedChanged",mOrderBy);
            makeNetworkConnection();
        }
    }

    // Helper method to calculate the best amount of columns to be displayed on a device
    public static int calculateNoOfColumns(Context context) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        float dpWidth = displayMetrics.widthPixels / displayMetrics.density;
        int scalingFactor = 180;
        int noOfColumns = (int) ceil (dpWidth / scalingFactor);
        return noOfColumns;
    }


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable(BUNDLE_RECYCLER_LAYOUT,
                manager.onSaveInstanceState());
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        //restore recycler view at same position
        if (savedInstanceState != null) {
            savedRecyclerLayoutState = savedInstanceState.getParcelable(BUNDLE_RECYCLER_LAYOUT);
        }
    }
}