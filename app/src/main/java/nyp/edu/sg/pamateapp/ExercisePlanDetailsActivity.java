/*
 * Copyright (c) 2020 Gwyn Bong Xiao Min.
 * All rights reserved.
 * Last Modified 4/6/20 8:25 PM
 */

package nyp.edu.sg.pamateapp;

import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutCompat;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import nyp.edu.sg.pamateapp.constants.IntentName;
import nyp.edu.sg.pamateapp.helper.DbHelper;
import nyp.edu.sg.pamateapp.helper.Threadings;
import nyp.edu.sg.pamateapp.models.ExerciseDetails;
import nyp.edu.sg.pamateapp.sqlite.DatabaseHandler;

public class ExercisePlanDetailsActivity extends AppCompatActivity {

    ExerciseDetails ed;

    TextView tvDate,
            tvExerciseName,
            tvDuration,
            tvHeartRate,
            tvCalories,
            tvDistance,
            tvNoOfSteps;

    LinearLayout layout_plan_details;

    //DatabaseHandler db;
    DbHelper helper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plan_details);

        //db = new DatabaseHandler(this);
        helper = new DbHelper(this);
        ed = new ExerciseDetails();

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        tvDate = (TextView) findViewById(R.id.tvPlanDate);
        tvExerciseName = (TextView) findViewById(R.id.tvExerciseName);
        tvDuration = (TextView) findViewById(R.id.tvPlannedDuration);
        tvHeartRate = (TextView) findViewById(R.id.tvPlannedHeartRate);
        tvCalories = (TextView) findViewById(R.id.tvPlannedCalories);
        tvDistance = (TextView) findViewById(R.id.tvPlannedDistance);
        tvNoOfSteps = (TextView) findViewById(R.id.tvNoOfSteps);

        layout_plan_details = (LinearLayout) findViewById(R.id.layout_plan_details);

        final String id = getIntent().getStringExtra(IntentName.INTENT_EXERCISE_PLAN_ID);
        final String date = getIntent().getStringExtra(IntentName.INTENT_EXERCISE_DATE);

        Threadings.runInBackground(new Runnable() {
            @Override
            public void run() {
                final boolean checkID = helper.checkExercisePlan(id);

                if (checkID) {
                    ed = helper.getPlanDetail(id);
                    Threadings.postRunnable(new Runnable() {
                        @Override
                        public void run() {
                            if (ed != null) {
                                emptyEditText();
                                tvDate.setText(date);
                                tvExerciseName.setText(ed.getExerciseName());
                                tvDuration.setText(ed.getDurationNeeded());
                                tvHeartRate.setText(String.valueOf(ed.getBeatsPerMin()));
                                tvCalories.setText(String.valueOf(ed.getCalories()));
                                tvDistance.setText(String.valueOf(ed.getDistance()));
                                tvNoOfSteps.setText(String.valueOf(ed.getStepsCount()));
                            } else {
                                Snackbar.make(layout_plan_details,
                                        "No records",
                                        Snackbar.LENGTH_LONG).show();
                            }
                        }
                    });
                }
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }

    private void emptyEditText() {
        tvExerciseName.setText(null);
        tvDuration.setText(null);
        tvHeartRate.setText(null);
        tvCalories.setText(null);
        tvDistance.setText(null);
        tvNoOfSteps.setText(null);
    }
}
