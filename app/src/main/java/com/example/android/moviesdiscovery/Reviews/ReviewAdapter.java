package com.example.android.moviesdiscovery.Reviews;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.android.moviesdiscovery.Network.Review;
import com.example.android.moviesdiscovery.R;

import java.util.List;

public class ReviewAdapter extends RecyclerView.Adapter<ReviewAdapter.ReviewViewHolder> {

    //    private String mPosterPath;
    private List<Review> mReview;

    public ReviewAdapter() {
    }


    public void setReviewData(List<Review> review) {


        mReview = review;

        notifyDataSetChanged();

    }

    @NonNull
    @Override
    public ReviewViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        Context context = viewGroup.getContext();
        int layoutIdForListItem = R.layout.review_grid_item;
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;

        View view = inflater.inflate(layoutIdForListItem, viewGroup, shouldAttachToParentImmediately);

        ReviewViewHolder viewHolder = new ReviewViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ReviewViewHolder holder, int position) {
        holder.mAuthor.setText(mReview.get(position).getAuthor());
        holder.mContent.setText(mReview.get(position).getContent());
  }

    @Override
    public int getItemCount() {
        if (null == mReview) return 0;
        return mReview.size();
    }


    class ReviewViewHolder extends RecyclerView.ViewHolder{
        TextView mAuthor;
        TextView mContent;

        //costruttore
        public ReviewViewHolder(View itemView) {


            super(itemView);
            Log.v("**costruttore vh: ", itemView.toString());
            mAuthor = (TextView) itemView.findViewById(R.id.tv_author);
            mContent=(TextView)itemView.findViewById(R.id.tv_content);

        }


        public TextView getAuthor() {
            return mAuthor;
        }
        public TextView getContent() {
            return mContent;
        }


    }


}