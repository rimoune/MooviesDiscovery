package com.example.android.moviesdiscovery.LocalDB;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class FavouriteViewModel extends AndroidViewModel {
    private FavouriteRepository mRepository;
    private LiveData<List<Favourite>> mAllFavourite;


    public FavouriteViewModel(@NonNull Application application) {
        super(application);
        mRepository = new FavouriteRepository(application);
        mAllFavourite = mRepository.getAllFavourite();
    }

    public LiveData<List<Favourite>> getAllFavourites() { return mAllFavourite; }

    public void insert(Favourite favourite) { mRepository.insert(favourite); }

    public void deleteFavourite(Integer id) {mRepository.deleteFavourite(id);}



}
