/*
 * Copyright (c) 2020 Gwyn Bong Xiao Min.
 * All rights reserved.
 * Last Modified 4/6/20 8:25 PM
 */

package nyp.edu.sg.pamateapp;

import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;

import java.util.ArrayList;

import nyp.edu.sg.pamateapp.constants.IntentName;
import nyp.edu.sg.pamateapp.helper.DbHelper;
import nyp.edu.sg.pamateapp.helper.MyApplication;
import nyp.edu.sg.pamateapp.helper.NotificationAdapter;
import nyp.edu.sg.pamateapp.helper.Session;
import nyp.edu.sg.pamateapp.helper.Threadings;
import nyp.edu.sg.pamateapp.models.Notifications;

public class NotificationHistoryActivity extends AppCompatActivity {

    RecyclerView notificationRecycler;
    DbHelper helper;
    ArrayList<Notifications> notifications;
    SwipeRefreshLayout swipeRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification_history);

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        notifications = new ArrayList<Notifications>();

        notificationRecycler = (RecyclerView) findViewById(R.id.notificationHistory);
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipeRefresh);

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                updateNotificationHistory();
            }
        });
        notificationRecycler.setLayoutManager(new LinearLayoutManager(this,
                LinearLayoutManager.VERTICAL, false));
        notificationRecycler.setHasFixedSize(true);
    }

    @Override
    protected void onStart() {
        super.onStart();
        helper = new DbHelper(this);

        swipeRefreshLayout.setRefreshing(true);
        updateNotificationHistory();

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                swipeRefreshLayout.setRefreshing(false);
            }
        }, IntentName.DELAY_TIME);
    }

    private void updateNotificationHistory() {
        Session session = ((MyApplication) this.getApplication()).getSession();
        final String username = session.getLoggedIn();

        Threadings.runInBackground(new Runnable() {
            @Override
            public void run() {
                notifications = helper.getAllNotifications(username);
                Threadings.postRunnable(new Runnable() {
                    @Override
                    public void run() {
                        NotificationAdapter notificationAdapter =
                                new NotificationAdapter(notifications,
                                        NotificationHistoryActivity.this);
                        notificationAdapter.notifyDataSetChanged();
                        notificationRecycler.setAdapter(notificationAdapter);
                        swipeRefreshLayout.setRefreshing(false);
                    }
                });
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
