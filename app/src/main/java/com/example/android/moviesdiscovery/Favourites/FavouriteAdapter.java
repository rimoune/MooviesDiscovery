package com.example.android.moviesdiscovery.Favourites;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.android.moviesdiscovery.LocalDB.Favourite;
import com.example.android.moviesdiscovery.R;

import java.util.List;

public class FavouriteAdapter extends RecyclerView.Adapter<FavouriteAdapter.FavouriteViewHolder> {

    private final LayoutInflater mInflater;
    private List<Favourite> mFavourite; // Cached copy of words

    public FavouriteAdapter(Context context) { mInflater = LayoutInflater.from(context); }

    @Override
    public FavouriteViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.favourite_grid_item, parent, false);
        return new FavouriteViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(FavouriteViewHolder holder, int position) {
        if (mFavourite != null) {
            Favourite current = mFavourite.get(position);
            holder.titleItemView.setText(current.getTitle());
            holder.releaseDateItemView.setText(current.getReleaseDate());
            holder.posterPathItemView.setText(current.getPosterPath());
        } else {
            // Covers the case of data not being ready yet.
            holder.titleItemView.setText("No favourite yet");
            holder.releaseDateItemView.setText("No favourite yet");
            holder.posterPathItemView.setText("No favourite yet");
        }
    }

    public void setFavourites(List<Favourite> favourites){
        mFavourite = favourites;
        notifyDataSetChanged();
    }

    // getItemCount() is called many times, and when it is first called,
    // mWords has not been updated (means initially, it's null, and we can't return null).
    @Override
    public int getItemCount() {
        if (mFavourite != null)
            return mFavourite.size();
        else return 0;
    }

    class FavouriteViewHolder extends RecyclerView.ViewHolder {
        private final TextView titleItemView;
        private final TextView posterPathItemView;
        private final TextView releaseDateItemView;

        private FavouriteViewHolder(View itemView) {
            super(itemView);
            titleItemView = itemView.findViewById(R.id.tv_title);
            posterPathItemView = itemView.findViewById(R.id.tv_poster_path);
            releaseDateItemView = itemView.findViewById(R.id.tv_releaseDate);
        }

    }
}
