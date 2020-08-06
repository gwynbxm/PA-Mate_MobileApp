/*
 * Copyright (c) 2020 Gwyn Bong Xiao Min.
 * All rights reserved.
 * Last Modified 4/6/20 8:25 PM
 */

package nyp.edu.sg.pamateapp.bottom_navi;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.github.aakira.expandablelayout.ExpandableLinearLayout;

import java.util.ArrayList;

import nyp.edu.sg.pamateapp.R;
import nyp.edu.sg.pamateapp.helper.DbHelper;
import nyp.edu.sg.pamateapp.helper.MyApplication;
import nyp.edu.sg.pamateapp.helper.ExerciseProgressListAdapter;
import nyp.edu.sg.pamateapp.helper.Session;
import nyp.edu.sg.pamateapp.helper.Threadings;
import nyp.edu.sg.pamateapp.models.ExercisePerformance;

public class ExerciseProgressActivity extends Fragment {

    SwipeRefreshLayout swipeRefreshLayout;
    //    ListView lvPerformances;
    RecyclerView recyclerView;
    RelativeLayout layout_progress;

    ArrayList<ExercisePerformance> performanceArrayList;
    //    RecentProgressListAdapter recentProgressListAdapter;
    ExerciseProgressListAdapter recentProgressListAdapter;
//    ExpandableLinearLayout expandableLayout;
    ExercisePerformance ep;
    //    DatabaseHandler db;
    DbHelper helper;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_item_exercise_progress, container, false);

//        db = new DatabaseHandler(this.getContext());
        helper = new DbHelper(this.getContext());
        ep = new ExercisePerformance();

        //initialize views
        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipe_progress);
//        lvPerformances = (ListView) view.findViewById(R.id.lvRecentProgress);
        recyclerView = (RecyclerView) view.findViewById(R.id.rvExerciseProgress);
        layout_progress = (RelativeLayout) view.findViewById(R.id.layout_progress);
//        expandableLayout = (ExpandableLinearLayout) view.findViewById(R.id.expandableLayout);
//
//        expandableLayout.expand();

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        retrieveAllRecentPerformance();

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                retrieveAllRecentPerformance();
                swipeRefreshLayout.setRefreshing(false);
            }
        });
        return view;
    }

    private void retrieveAllRecentPerformance() {
        Session session = ((MyApplication) this.getActivity().getApplication()).getSession();
        final String username = session.getLoggedIn();

        Threadings.runInBackground(new Runnable() {
            @Override
            public void run() {
                performanceArrayList = helper.getAllExercisePerformance(username);
                Threadings.postRunnable(new Runnable() {
                    @Override
                    public void run() {
                        recentProgressListAdapter = new ExerciseProgressListAdapter(
                                performanceArrayList);
                        recyclerView.setAdapter(recentProgressListAdapter);
                    }
                });
            }
        });
    }
}
