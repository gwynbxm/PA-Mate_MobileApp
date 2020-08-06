/*
 * Copyright (c) 2020 Gwyn Bong Xiao Min.
 * All rights reserved.
 * Last Modified 4/6/20 8:25 PM
 */

package nyp.edu.sg.pamateapp.bottom_navi;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;

import java.util.ArrayList;

import nyp.edu.sg.pamateapp.ExercisePlanDetailsActivity;
import nyp.edu.sg.pamateapp.R;
import nyp.edu.sg.pamateapp.constants.IntentName;
import nyp.edu.sg.pamateapp.helper.DbHelper;
import nyp.edu.sg.pamateapp.helper.ExercisePlanListAdapter;
import nyp.edu.sg.pamateapp.helper.MyApplication;
import nyp.edu.sg.pamateapp.helper.Session;
import nyp.edu.sg.pamateapp.helper.Threadings;
import nyp.edu.sg.pamateapp.models.ExercisePlan;

public class ExercisePlanActivity extends Fragment {

    SwipeRefreshLayout swipeRefreshLayout;
    ListView lvPlans;
    ArrayList<ExercisePlan> planArraylist;
    ExercisePlanListAdapter exercisePlanListAdapter;
    DbHelper helper;
    //DatabaseHandler db;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable final Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_item_exercise_plan, container, false);

        //db = new DatabaseHandler(this.getContext());
        helper = new DbHelper(this.getContext());

        //initialize views
        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipe_plan);
        lvPlans = (ListView) view.findViewById(R.id.lvExercisePlan);

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                retrieveAllExercisePlans();
                swipeRefreshLayout.setRefreshing(false);
            }
        });
        //on click item, intent to Plan Details Activity
        lvPlans.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getActivity().getApplicationContext(),
                        ExercisePlanDetailsActivity.class);

                ExercisePlan e = planArraylist.get(position);
                //pass the position to the plan details activity
                intent.putExtra(IntentName.INTENT_EXERCISE_PLAN_ID, e.getPlanId());
                intent.putExtra(IntentName.INTENT_EXERCISE_DATE, e.getPlanStartDate());
                startActivity(intent);
            }
        });

        registerForContextMenu(lvPlans);
        //retrieveAllExercisePlans();
        return view;
    }

    private void retrieveAllExercisePlans() {
        Session session = ((MyApplication) this.getActivity().getApplication()).getSession();
        final String username = session.getLoggedIn();

        Threadings.runInBackground(new Runnable() {
            @Override
            public void run() {
                planArraylist = helper.getPatientPlans(username);

                Threadings.postRunnable(new Runnable() {
                    @Override
                    public void run() {
                        exercisePlanListAdapter = new ExercisePlanListAdapter(
                                getActivity(),
                                R.layout.lv_exercise_plan_items,
                                planArraylist);
                        exercisePlanListAdapter.notifyDataSetChanged();
                        lvPlans.setAdapter(exercisePlanListAdapter);
                    }
                });
            }
        });
    }



    @Override
    public void onResume() {
        super.onResume();
        retrieveAllExercisePlans();
    }
}
