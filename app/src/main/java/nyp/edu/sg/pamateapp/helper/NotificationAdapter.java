/*
 * Copyright (c) 2020 Gwyn Bong Xiao Min.
 * All rights reserved.
 * Last Modified 4/6/20 8:25 PM
 */

package nyp.edu.sg.pamateapp.helper;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import nyp.edu.sg.pamateapp.R;
import nyp.edu.sg.pamateapp.models.Notifications;

/**
 * Created by GBXM on 24/1/2018.
 */

public class NotificationAdapter extends
        RecyclerView.Adapter<NotificationAdapter.NotificationHistoryHolder> {

    private ArrayList<Notifications> notificationsList;
    private Context context;

    public NotificationAdapter(ArrayList<Notifications> notificationsList, Context context) {
        this.notificationsList = notificationsList;
        this.context = context;
    }

    @Override
    public NotificationHistoryHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.rv_notification_item, parent, false);
        return new NotificationHistoryHolder(v);
    }

    @Override
    public void onBindViewHolder(NotificationHistoryHolder holder, int position) {
        try {
            holder.tvTitle.setText(notificationsList.get(position).getNotificationTitle());
            holder.tvDescription.setText(notificationsList.get(position).getNotificationDesc());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return (notificationsList == null) ? 0 : notificationsList.size();
    }

    public static class NotificationHistoryHolder extends RecyclerView.ViewHolder {
        ImageView imgNotification;
        CardView cardView;
        TextView tvTitle;
        TextView tvDescription;

        public NotificationHistoryHolder(View itemView) {
            super(itemView);

            imgNotification = (ImageView) itemView.findViewById(R.id.imgNotification);
            tvTitle = (TextView) itemView.findViewById(R.id.tvNotificationTitle);
            tvDescription = (TextView) itemView.findViewById(R.id.tvNotificationMessage);
            cardView = (CardView) itemView.findViewById(R.id.card_view);
        }
    }
}
