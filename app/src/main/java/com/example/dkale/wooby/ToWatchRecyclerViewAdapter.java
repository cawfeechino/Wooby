package com.example.dkale.wooby;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.dkale.wooby.ToWatchFragment.OnListFragmentInteractionListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class ToWatchRecyclerViewAdapter extends RecyclerView.Adapter<ToWatchRecyclerViewAdapter.ViewHolder> {

    private final List<ToWatchListItem> mValues;
    private final OnListFragmentInteractionListener mListener;
    private Context mContext;

    public ToWatchRecyclerViewAdapter(Context context, ArrayList<ToWatchListItem> items, OnListFragmentInteractionListener listener) {
        this.mContext=context;
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
        final String name = mValues.get(position).getAnimeName();
        final String description = mValues.get(position).getAnimeDescription();
        final String image = mValues.get(position).getAnimeImage().toString();
        final String url = mValues.get(position).animeURL;
//        Log.e("OnBindViewHolder ", mValues.get(position).getAnimeImage());
        holder.mDeleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mContext instanceof MainActivity){
                    ((MainActivity)mContext).removeFromWatchLaterList(name);
                }
            }
        });
        holder.mTransferButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mContext instanceof MainActivity){
                    ((MainActivity)mContext).writeWatchedDatabase(name,description,image,url);
                    ((MainActivity)mContext).removeFromWatchLaterList(name);
                }
            }
        });
        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                    mListener.onToWatchListFragmentInteraction(holder.mItem);
                }
                Intent clicked = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                v.getContext().startActivity(clicked);
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
        public final Button mDeleteButton;
        public final Button mTransferButton;
        public ToWatchListItem mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mIdView = (TextView) view.findViewById(R.id.item_numberToWatch);
            mContentView = (TextView) view.findViewById(R.id.contentToWatch);
            mImageView = (ImageView) view.findViewById(R.id.animeImageToWatch);
            mDeleteButton = (Button) view.findViewById(R.id.deleteButtonWatchLater);
            mTransferButton = (Button) view.findViewById(R.id.transferWatchedList);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mContentView.getText() + "'";
        }
    }
}
