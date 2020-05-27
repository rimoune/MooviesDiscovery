package com.example.android.moviesdiscovery.LocalDB;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface FavouriteDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Favourite favourite);
    @Query("DELETE from favourite_table where id=:id")
    void delete (Integer id);
    @Query("SELECT * from favourite_table")
    LiveData<List<Favourite>> getAllFavourite();
    @Query("SELECT * FROM favourite_table WHERE id = :id")
    Favourite loadMovieById(Integer id);
}

