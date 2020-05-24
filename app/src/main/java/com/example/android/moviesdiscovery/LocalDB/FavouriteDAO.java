package com.example.android.moviesdiscovery.LocalDB;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface FavouriteDAO {

    @Insert
    void insert(Favourite favourite);
    @Query("DELETE from favourite_table where id=:favourite")
    void delete (String favourite);
    @Query("SELECT * from favourite_table")
    LiveData<List<Favourite>> getAllFavourite();
}

