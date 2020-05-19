package com.example.android.moviesdiscovery;


import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class TrailerAdapter extends RecyclerView.Adapter<TrailerAdapter.TrailerViewHolder> {

    //    private String mPosterPath;
    private List<Trailer> mTrailer;

    public TrailerAdapter() {
    }


    public void setTrailerData(List<Trailer> trailer) {


        mTrailer = trailer;

        notifyDataSetChanged();

    }

    @NonNull
    @Override
    public TrailerViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        Context context = viewGroup.getContext();
        int layoutIdForListItem = R.layout.trailer_list_item;
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;

        View view = inflater.inflate(layoutIdForListItem, viewGroup, shouldAttachToParentImmediately);

       TrailerViewHolder viewHolder = new TrailerViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull TrailerViewHolder holder, int position) {
        holder.mName.setText(mTrailer.get(position).getName());
    }

    @Override
    public int getItemCount() {
        if (null == mTrailer) return 0;
        return mTrailer.size();
    }


    class TrailerViewHolder extends RecyclerView.ViewHolder{
        TextView mName;

        //costruttore
        public TrailerViewHolder(View itemView) {


            super(itemView);
            Log.v("**costruttore vh: ", itemView.toString());
            mName=(TextView)itemView.findViewById(R.id.tv_trailer_name);

        }


        public TextView getName() {
            return mName;
        }


    }


}
