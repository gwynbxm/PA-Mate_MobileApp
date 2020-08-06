/*
 * Copyright (c) 2020 Gwyn Bong Xiao Min.
 * All rights reserved.
 * Last Modified 4/6/20 8:25 PM
 */

package nyp.edu.sg.pamateapp.bottom_navi;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import nyp.edu.sg.pamateapp.MainActivity;
import nyp.edu.sg.pamateapp.R;
import nyp.edu.sg.pamateapp.EditProfileActivity;
import nyp.edu.sg.pamateapp.models.ExercisePlan;
import nyp.edu.sg.pamateapp.models.Notifications;
import nyp.edu.sg.pamateapp.NotificationHistoryActivity;
import nyp.edu.sg.pamateapp.helper.DbHelper;
import nyp.edu.sg.pamateapp.helper.MyApplication;
import nyp.edu.sg.pamateapp.helper.Session;
import nyp.edu.sg.pamateapp.helper.Threadings;
import nyp.edu.sg.pamateapp.SettingsActivity;

public class HomeActivity extends AppCompatActivity {

    TextView tvSyncMessage;
    DbHelper helper;
    ExercisePlan ep;
    Notifications n;
    String username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        tvSyncMessage = (TextView) findViewById(R.id.tvSyncMessage);

        helper = new DbHelper(this);
        ep = new ExercisePlan();

        Session session = ((MyApplication) this.getApplication()).getSession();
        username = session.getLoggedIn();

        Threadings.runInBackground(new Runnable() {
            @Override
            public void run() {
                helper.updateLoginDT(username, getCurrentDTStamp());
            }
        });
        setMainNavigationView();
    }

    private String getCurrentDTStamp() {
        SimpleDateFormat dateFormat =
                new SimpleDateFormat("dd-MM-yyyy hh:mm:ss",
                        Locale.US);

        return dateFormat.format(new Date());
    }

    private void setMainNavigationView() {
        BottomNavigationView bottomNavigationView = (BottomNavigationView)
                findViewById(R.id.navigation);

        if (bottomNavigationView != null) {
            Menu menu = bottomNavigationView.getMenu();
            selectFragment(menu.getItem(1));
            bottomNavigationView.setOnNavigationItemSelectedListener(
                    new BottomNavigationView.OnNavigationItemSelectedListener() {
                        @Override
                        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                            selectFragment(item);
                            return false;
                        }
                    });
        }
    }

    protected void selectFragment(final MenuItem item) {
        item.setChecked(true);

        switch (item.getItemId()) {
            case R.id.item1:
                pushFragment(new ExercisePlanActivity());
                break;
            case R.id.item2:
                pushFragment(new DashboardViewPagerActivity());
                break;
            case R.id.item3:
                pushFragment(new ExerciseProgressActivity());
                break;
        }
    }

    protected void pushFragment(final Fragment fragment) {
        if (fragment == null) {
            return;
        }

        FragmentManager fragmentManager = getFragmentManager();
        if (fragmentManager != null) {
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            if (transaction != null) {
                transaction.replace(R.id.dbNavigation, fragment);
                transaction.commit();
            }
        }
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.home_options_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.sync:
                showSyncDialog();
                return true;
            case R.id.account:
                startActivity(new Intent(this, EditProfileActivity.class));
                return true;
            case R.id.notifications:
                startActivity(new Intent(this, NotificationHistoryActivity.class));
                return true;
            case R.id.settings:
                startActivity(new Intent(this, SettingsActivity.class));
                return true;
            case R.id.logout:
                final Session session = ((MyApplication) this.getApplication()).getSession();
                Threadings.runInBackground(new Runnable() {
                    @Override
                    public void run() {
                        helper.updateLogoutDT(username, getCurrentDTStamp());
                        Threadings.postRunnable(new Runnable() {
                            @Override
                            public void run() {
                                session.logout();
                                startActivity(new Intent(getApplicationContext(),
                                        MainActivity.class));
                            }
                        });
                    }
                });
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void showSyncDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Sync to watch?");

        LayoutInflater inflater = LayoutInflater.from(this);
        final View view = inflater.inflate(R.layout.alertdialog_sync, null);

        builder.setView(view);

        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                tvSyncMessage.setText("Sync in progress ...");
                startSync();
            }
        });

        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.show();
    }

    private void startSync() {
        tvSyncMessage.setText("Sync successfully!");
    }
}
