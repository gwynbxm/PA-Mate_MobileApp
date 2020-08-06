/*
 * Copyright (c) 2020 Gwyn Bong Xiao Min.
 * All rights reserved.
 * Last Modified 4/6/20 8:25 PM
 */

package nyp.edu.sg.pamateapp.bottom_navi;

import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.google.android.gms.vision.text.Line;

import java.sql.Time;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import nyp.edu.sg.pamateapp.R;
import nyp.edu.sg.pamateapp.helper.DbHelper;
import nyp.edu.sg.pamateapp.helper.MyApplication;
import nyp.edu.sg.pamateapp.helper.Session;
import nyp.edu.sg.pamateapp.helper.Threadings;
import nyp.edu.sg.pamateapp.models.ExercisePerformance;
import nyp.edu.sg.pamateapp.sqlite.DatabaseHandler;
import nyp.edu.sg.pamateapp.util.TimeUtils;

public class DashboardActivity extends Fragment {

    String getDate;
    ExercisePerformance p;
    //DatabaseHandler db;
    DbHelper helper;

    TextView tvTodayDate, tvHeartRate, tvSteps, tvCalories, tvDistance;
    LinearLayout layout_dashboard;

    private static final String KEY_DATE = "date";

    public static DashboardActivity newInstance(String date) {
        DashboardActivity dbFragment = new DashboardActivity();
        Bundle args = new Bundle();
        args.putString(KEY_DATE, date);
        dbFragment.setArguments(args);
        return dbFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        final View view = inflater.inflate(R.layout.activity_item_dashboard_frag, container, false);

        //db = new DatabaseHandler(getActivity());
        helper = new DbHelper(getActivity());
        p = new ExercisePerformance();

        //instantiate views
        tvHeartRate = (TextView) view.findViewById(R.id.tvHeartRateValue);
        tvSteps = (TextView) view.findViewById(R.id.tvStepsValue);
        tvCalories = (TextView) view.findViewById(R.id.tvCaloriesValue);
        tvDistance = (TextView) view.findViewById(R.id.tvDistanceValue);
        layout_dashboard = (LinearLayout) view.findViewById(R.id.layout_db_frag);

        getDate = getArguments().getString(KEY_DATE);

        Session session = ((MyApplication) this.getActivity().getApplication()).getSession();
        final String username = session.getLoggedIn();

        Threadings.runInBackground(new Runnable() {
            @Override
            public void run() {
                final boolean todayDate = helper.checkLatestExercisePerformance(String.valueOf(getDate));
                if (todayDate) {
                    p = helper.getExerciseLatestPerformance(username, String.valueOf(getDate));

                    Threadings.postRunnable(new Runnable() {
                        @Override
                        public void run() {
                            emptyEditText();

                            if (p != null) {
                                //retrieve from database and display text fields
                                tvHeartRate.setText(String.valueOf(p.getHeartRate()));
                                tvSteps.setText(String.valueOf(p.getNoOfSteps()));
                                tvCalories.setText(String.valueOf(p.getCaloriesBurnt()));
                                tvDistance.setText(String.valueOf(p.getDistanceCovered()));
                            }
                        }
                    });
                }
            }
        });
        return view;
    }

    private void emptyEditText() {
        tvHeartRate.setText(null);
        tvSteps.setText(null);
        tvCalories.setText(null);
        tvDistance.setText(null);
    }
}
