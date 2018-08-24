package com.vdrop.firebasepoc;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.ListPopupWindow;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class FirebaseAdapter extends RecyclerView.Adapter<FirebaseAdapter.FirebaseViewHolder> {
    private Context context;
    private Activity activity;
    private boolean isClickOnce = true;
    boolean isNetworkConnected;
    private ArrayList<String> channelIds;
    private ArrayList<ChannelDetails> channelDetailsList;
    private TextView name;
    private ImageView bannerImage;
    private TextView description;

    public FirebaseAdapter(Context context) {
        this.context = context;
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
        holder.bind(channelDetailsList.get(position), position );

    }

    @Override
    public int getItemCount() {
        return channelDetailsList.size();
    }

    public void setData(ArrayList<ChannelDetails> channelDetailsList) {
        this.channelDetailsList = channelDetailsList;
    }

    public class FirebaseViewHolder extends RecyclerView.ViewHolder {
        public FirebaseViewHolder(View itemView) {
            super(itemView);
            bannerImage = itemView.findViewById(R.id.bannerImage);
            name = itemView.findViewById(R.id.vds_title);
            description = itemView.findViewById(R.id.vds_player_name);



            }

        public void bind(final ChannelDetails channel, final int position) {
            name.setText(channel.getName());
            description.setText(channel.getDescription());
            Picasso.with(context).load(channel.getBannerImage())
                    .placeholder(R.mipmap.ic_launcher)
                    .into(bannerImage);

        }
    }
}
