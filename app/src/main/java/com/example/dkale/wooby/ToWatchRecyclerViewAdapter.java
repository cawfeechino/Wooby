package com.example.dkale.wooby;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.dkale.wooby.ToWatchFragment.OnListFragmentInteractionListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class ToWatchRecyclerViewAdapter extends RecyclerView.Adapter<ToWatchRecyclerViewAdapter.ViewHolder> {

    private final List<ToWatchListItem> mValues;
    private final OnListFragmentInteractionListener mListener;

    public ToWatchRecyclerViewAdapter(ArrayList<ToWatchListItem> items, OnListFragmentInteractionListener listener) {
        mValues = items;
        mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_towatch, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
        holder.mIdView.setText(mValues.get(position).getAnimeName());
        holder.mContentView.setText(mValues.get(position).getAnimeDescription());
        Picasso.get().load(mValues.get(position).getAnimeImage()).into(holder.mImageView);
//        Log.e("OnBindViewHolder ", mValues.get(position).getAnimeImage());
        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                    mListener.onToWatchListFragmentInteraction(holder.mItem);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView mIdView;
        public final TextView mContentView;
        public final ImageView mImageView;
        public ToWatchListItem mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mIdView = (TextView) view.findViewById(R.id.item_numberToWatch);
            mContentView = (TextView) view.findViewById(R.id.contentToWatch);
            mImageView = (ImageView) view.findViewById(R.id.animeImageToWatch);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mContentView.getText() + "'";
        }
    }
}
