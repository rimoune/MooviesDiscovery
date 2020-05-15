package com.example.android.moviesdiscovery;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class PosterAdapter extends RecyclerView.Adapter<PosterAdapter.PosterViewHolder> {

    //    private String mPosterPath;
    private List<Movie> mMovie;

    private final MoviesAdapterOnClickHandler mClickHandler;


    public PosterAdapter(MoviesAdapterOnClickHandler clickHandler) {
        mClickHandler = clickHandler;
    }


    public interface MoviesAdapterOnClickHandler {
        void onClick(Movie movie);
    }

    public void setPosterData(List<Movie> movie) {


        mMovie = movie;
        Log.v("**setPosterData: ", String.valueOf(movie.size()));
        notifyDataSetChanged();

    }

    @NonNull
    @Override
    public PosterViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        Log.v("**onCreateViewHolder: ", "entro");
        Context context = viewGroup.getContext();
        int layoutIdForListItem = R.layout.movie_grid_item;
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;

        View view = inflater.inflate(layoutIdForListItem, viewGroup, shouldAttachToParentImmediately);
        Log.v("**onCreateViewH-view: ", view.toString());

        PosterViewHolder viewHolder = new PosterViewHolder(view);
        Log.v("**onCreateVireHolder: ", viewHolder.getPoster().toString());
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull PosterViewHolder holder, int position) {
        Log.v("**onBindViewHolder", mMovie.get(position).getPosterPath());
        holder.bind("http://image.tmdb.org/t/p/w342/" + mMovie.get(position).getPosterPath());


    }

    @Override
    public int getItemCount() {
        if (null == mMovie) return 0;
        return mMovie.size();
    }


    class PosterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView mposter;

        //costruttore
        public PosterViewHolder(View itemView) {


            super(itemView);
            Log.v("**costruttore vh: ", itemView.toString());
            mposter = (ImageView) itemView.findViewById(R.id.iv_poster);
            itemView.setOnClickListener(this);

        }

        //helper method
        public void bind(String poster_path) {
//            Log.v("**bind: ", poster_path);

            Picasso.get().load(poster_path).into(mposter);
        }

        public ImageView getPoster() {
            return mposter;
        }

        @Override
        public void onClick(View v) {
            int adapterPosition = getAdapterPosition();
            Movie movie = mMovie.get(adapterPosition);
            mClickHandler.onClick(movie);
        }

    }


}