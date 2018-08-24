package com.vdrop.firebasepoc;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

public class FirebaseAdapter extends RecyclerView.Adapter<FirebaseAdapter.FirebaseViewHolder> {
    private Context context;
    private Activity activity;
    private boolean isClickOnce = true;
    boolean isNetworkConnected;
    private ArrayList<String> channelIds;

    public FirebaseAdapter(Context context) {
    }

    @NonNull
    @Override
    public FirebaseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layout_channels_row, parent, false);
        FirebaseViewHolder viewHolder = new FirebaseViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull FirebaseViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public void setData() {

    }

    public class FirebaseViewHolder extends RecyclerView.ViewHolder {
        public FirebaseViewHolder(View itemView) {
            super(itemView);
        }
    }
}
