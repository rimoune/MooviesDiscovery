package com.example.android.moviesdiscovery.LocalDB;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "favourite_table")
public class Favourite {
    @PrimaryKey
    @NonNull
    private Integer id;

    private String posterPath;
    private String title;
    private String releaseDate;

    public Favourite(@NonNull Integer id, String posterPath, String title, String releaseDate) {
        this.id = id;
        this.posterPath = posterPath;
        this.title = title;
        this.releaseDate = releaseDate;
     }



    //TODO add an insertion date to return favourite movies sorted
    public String getPosterPath() {
        return posterPath;
    }

    public Integer getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getReleaseDate() {
        return releaseDate;
    }
}
