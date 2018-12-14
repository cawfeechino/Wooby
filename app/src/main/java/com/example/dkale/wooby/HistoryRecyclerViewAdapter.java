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

import com.example.dkale.wooby.HistoryFragment.OnListFragmentInteractionListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class HistoryRecyclerViewAdapter extends RecyclerView.Adapter<HistoryRecyclerViewAdapter.ViewHolder> {

    private final List<WatchedListItem> mValues;
    private final OnListFragmentInteractionListener mListener;
    private Context mContext;

    public HistoryRecyclerViewAdapter(Context context, ArrayList<WatchedListItem> items, OnListFragmentInteractionListener listener) {
        this.mContext=context;
        mValues = items;
        mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_history, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
        holder.mIdView.setText(mValues.get(position).getAnimeName());
        holder.mContentView.setText(mValues.get(position).getAnimeDescription());
        Picasso.get().load(mValues.get(position).getAnimeImage()).into(holder.mImageView);
        final String name = holder.mIdView.getText().toString();
        final String url = mValues.get(position).animeURL;
        holder.mdeleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mContext instanceof MainActivity){
                    ((MainActivity)mContext).removeFromWatchedList(name);

                }
            }
        });
        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                    mListener.onHistoryListFragmentInteraction(holder.mItem);
                }
                Intent clicked = new Intent(Intent.ACTION_VIEW,Uri.parse(url));
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
        public final Button mdeleteButton;
        public WatchedListItem mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mIdView = (TextView) view.findViewById(R.id.item_number);
            mContentView = (TextView) view.findViewById(R.id.content);
            mImageView = (ImageView) view.findViewById(R.id.animeImage);
            mdeleteButton = (Button) view.findViewById(R.id.deleteButton);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mContentView.getText() + "'";
        }
    }
}