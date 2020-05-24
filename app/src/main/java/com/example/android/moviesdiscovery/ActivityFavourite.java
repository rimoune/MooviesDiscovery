package com.example.android.moviesdiscovery;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.android.moviesdiscovery.Favourites.FavouriteAdapter;
import com.example.android.moviesdiscovery.LocalDB.Favourite;
import com.example.android.moviesdiscovery.LocalDB.FavouriteViewModel;

import java.util.List;

public class ActivityFavourite extends AppCompatActivity {

    private FavouriteViewModel mFavouriteViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favourite);




        RecyclerView recyclerView = findViewById(R.id.favourite_rv);
        final FavouriteAdapter adapter = new FavouriteAdapter(this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        mFavouriteViewModel = ViewModelProviders.of(this).get(FavouriteViewModel.class);

        mFavouriteViewModel.getAllFavourites().observe(this, new Observer<List<Favourite>>() {
            @Override
            public void onChanged(@Nullable final List<Favourite> favourites) {
                // Update the cached copy of the words in the adapter.
                adapter.setFavourites(favourites);
            }
        });
    }
}
