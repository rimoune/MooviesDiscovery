package com.example.android.moviesdiscovery;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Review {

    @SerializedName("author")
    @Expose
    private String author;
    @SerializedName("content")
    @Expose
    private String content;
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("url")
    @Expose
    private String url;

    public String getAuthor() {
        return author;
    }


    public String getContent() {
        return content;
    }

    public String getId() {
        return id;
    }


    public String getUrl() {
        return url;
    }
}


