/*
 * Copyright (c) 2020 Gwyn Bong Xiao Min.
 * All rights reserved.
 * Last Modified 4/6/20 8:25 PM
 */

package nyp.edu.sg.pamateapp.helper;

import android.app.Notification;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;

import com.evernote.android.job.Job;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Random;

import nyp.edu.sg.pamateapp.R;
import nyp.edu.sg.pamateapp.bottom_navi.ExercisePlanActivity;
import nyp.edu.sg.pamateapp.models.ExercisePlan;

/**
 * Created by GBXM on 26/1/2018.
 */

public class ShowNotificationJob extends Job {
    public static final String TAG = "job_demo_tag";
    DbHelper helper;
    private String username;

    @NonNull
    @Override
    protected Result onRunJob(@NonNull Params params) {
        helper = new DbHelper(getContext());
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getContext());
        username = preferences.getString("username", null);
        triggerAlarmNotification();
        return Result.SUCCESS;
    }

    private void triggerAlarmNotification() {
        Threadings.runInBackground(new Runnable() {
            @Override
            public void run() {
                final ExercisePlan ep = helper.getExercisePlan(username, getCurrentDate());
                Threadings.postRunnable(new Runnable() {
                    @Override
                    public void run() {
                        if (ep != null) {
                            PendingIntent pendingIntent = PendingIntent.getActivity(getContext(), 0,
                                    new Intent(getContext(), ExercisePlanActivity.class), 0);

                            Bitmap icon = BitmapFactory.decodeResource(getContext().getResources(),
                                    R.drawable.ic_reminder);

                            Notification notification = new NotificationCompat.Builder(getContext())
                                    .setSmallIcon(R.drawable.pamate)
                                    .setLargeIcon(icon)
                                    .setContentTitle("Time to Exercise!")
                                    .setContentText(ep.getPlanStartDate() + ": " + ep.getPlanName())
                                    .setAutoCancel(true)
                                    .setContentIntent(pendingIntent)
                                    .setShowWhen(true)
                                    .setColor(Color.BLUE)
                                    .setLocalOnly(true)
                                    .build();

                            NotificationManagerCompat.from(getContext())
                                    .notify(new Random().nextInt(), notification);

                            String text = "Exercise Plan, " + ep.getPlanName()
                                    + " to be completed by " + ep.getPlanStartDate();
                            addNotification(text);
                        }
                    }
                });
            }
        });
    }

    private static String getCurrentDate() {
        SimpleDateFormat dateFormat =
                new SimpleDateFormat("dd-MM-yyyy",
                        Locale.US);

        return dateFormat.format(new Date());
    }

    private static String getCurrentTime() {
        Date currentTime = Calendar.getInstance().getTime();
        SimpleDateFormat timeFormat =
                new SimpleDateFormat("hh:mm",
                        Locale.US);
        return timeFormat.format(currentTime);
    }

    private void addNotification(final String text) {
        Threadings.runInBackground(new Runnable() {
            @Override
            public void run() {
                helper.createNotifications("Exercise Reminder", "Reminder: " +
                        text, getCurrentTime(), username);
            }
        });
    }
}
