package com.example.android.moviesdiscovery;


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.squareup.picasso.Picasso;

public class ActivityDetail extends AppCompatActivity {


    //    private TextView mtitle;
    private Movie mmovie;

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

            //todo (5) add a recycler view for reviews, grid, in each item "author", "content"
            //todo (6) add a recycler view for trailers, list, implecit intent to open it in a new page
            //json has a "type":"Trailer", maybe I need to retrieve only those and for simplicity "site"="YouTube"
            //I don't know how to interpret the elements in the json response..id, key, name
            //https://www.youtube.com/watch?v=P6AaSMfXHbA =key, so if the site is youtube, you build like this.
            //intent is either opening the youtube app or website with that link
        }

    }
}





