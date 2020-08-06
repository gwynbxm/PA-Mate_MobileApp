/*
 * Copyright (c) 2020 Gwyn Bong Xiao Min.
 * All rights reserved.
 * Last Modified 4/6/20 8:25 PM
 */

package nyp.edu.sg.pamateapp;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;

import java.util.ArrayList;

import nyp.edu.sg.pamateapp.constants.IntentName;
import nyp.edu.sg.pamateapp.helper.DbHelper;
import nyp.edu.sg.pamateapp.helper.ExercisePlanListAdapter;
import nyp.edu.sg.pamateapp.helper.ExerciseVideoAdapter;
import nyp.edu.sg.pamateapp.helper.MyApplication;
import nyp.edu.sg.pamateapp.helper.Session;
import nyp.edu.sg.pamateapp.helper.Threadings;
import nyp.edu.sg.pamateapp.models.ExerciseInstructions;

public class ExerciseIntructionsActivity extends AppCompatActivity {

    ListView lvVideos;
    ArrayList<ExerciseInstructions> instructionsArrayList;
    ExerciseVideoAdapter videoAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise_intructions);

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        lvVideos = (ListView) findViewById(R.id.lvExerciseVideos);

        lvVideos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ExerciseInstructions ei = instructionsArrayList.get(position);
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(ei.getExerciseVideoUrl()));
                startActivity(intent);
            }
        });

        registerForContextMenu(lvVideos);
    }

    private void retrieveAllExerciseVideos() {
        final DbHelper helper = new DbHelper(this);

        Threadings.runInBackground(new Runnable() {
            @Override
            public void run() {

                instructionsArrayList = helper.getAllExerciseVideos();

                Threadings.postRunnable(new Runnable() {
                    @Override
                    public void run() {
                        if (instructionsArrayList != null) {
                            videoAdapter = new ExerciseVideoAdapter(
                                    getApplicationContext(),
                                    R.layout.lv_video_instruction_item,
                                    instructionsArrayList);
                            videoAdapter.notifyDataSetChanged();
                            lvVideos.setAdapter(videoAdapter);
                        }
                    }
                });
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        retrieveAllExerciseVideos();
    }
}
