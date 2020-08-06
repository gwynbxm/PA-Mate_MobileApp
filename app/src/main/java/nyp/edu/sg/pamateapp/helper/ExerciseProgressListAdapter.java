/*
 * Copyright (c) 2020 Gwyn Bong Xiao Min.
 * All rights reserved.
 * Last Modified 4/6/20 8:25 PM
 */

package nyp.edu.sg.pamateapp.helper;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.OvershootInterpolator;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.aakira.expandablelayout.ExpandableLayout;
import com.github.aakira.expandablelayout.ExpandableLayoutListenerAdapter;
import com.github.aakira.expandablelayout.ExpandableLinearLayout;
import com.github.aakira.expandablelayout.Utils;

import java.util.ArrayList;

import nyp.edu.sg.pamateapp.R;
import nyp.edu.sg.pamateapp.models.ExercisePerformance;

/**
 * Created by GBXM on 9/2/2018.
 */

public class ExerciseProgressListAdapter extends RecyclerView.Adapter<ExerciseProgressListAdapter.ViewHolder> {
    private ArrayList<ExercisePerformance> exercisePerformanceList;
    private Context context;
    private SparseBooleanArray expandState = new SparseBooleanArray();

    public ExerciseProgressListAdapter(final ArrayList<ExercisePerformance> exercisePerformanceList) {
        this.exercisePerformanceList = exercisePerformanceList;
        for (int i = 0; i < exercisePerformanceList.size(); i++) {
            expandState.append(i, false);
        }
    }
    @Override
    public ViewHolder onCreateViewHolder(final ViewGroup parent, final int viewType) {
        this.context = parent.getContext();
        return new ViewHolder(LayoutInflater.from(context)
                .inflate(R.layout.rv_recent_progress_items, parent, false));
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        final ExercisePerformance item = exercisePerformanceList.get(position);
        holder.setIsRecyclable(false);
        switch (item.getNameOfExercise()) {
            case "Cycle":
                holder.imgExIcon.setImageResource(R.drawable.ic_cycling);
                break;
            case "Run":
                holder.imgExIcon.setImageResource(R.drawable.ic_running);
                break;
            case "Swim":
                holder.imgExIcon.setImageResource(R.drawable.ic_swimming);
                break;
            case "Walk":
                holder.imgExIcon.setImageResource(R.drawable.ic_walking);
                break;
        }
        holder.tvRecentExName.setText(item.getNameOfExercise());
        holder.tvRecentDate.setText("Completed on: " + item.getStartDate());

        holder.tvRecordedDuration.setText(item.getDurationMeasured());
        holder.tvRecordedHeartRate.setText(item.getHeartRate());
        holder.tvRecordedCalories.setText(item.getCaloriesBurnt());
        holder.tvRecordedSteps.setText(item.getNoOfSteps());
        holder.tvRecordedDistance.setText(item.getDistanceCovered());
        holder.expandableLayout.setInRecyclerView(true);
        holder.expandableLayout.setInterpolator(new OvershootInterpolator());
        holder.expandableLayout.setExpanded(expandState.get(position));
        holder.expandableLayout.setListener(new ExpandableLayoutListenerAdapter() {
            @Override
            public void onPreOpen() {
                createRotateAnimator(holder.buttonLayout, 0f, 180f).start();
                expandState.put(position, true);
            }

            @Override
            public void onPreClose() {
                createRotateAnimator(holder.buttonLayout, 180f, 0f).start();
                expandState.put(position, false);
            }
        });

        holder.buttonLayout.setRotation(expandState.get(position) ? 180f : 0f);
        holder.buttonLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                onClickButton(holder.expandableLayout);
            }
        });
    }

    private void onClickButton(final ExpandableLayout expandableLayout) {
        expandableLayout.toggle();
    }

    @Override
    public int getItemCount() {
        return exercisePerformanceList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public ImageView imgExIcon;
        public TextView tvRecentExName;
        public TextView tvRecentDate;
        public ImageButton buttonLayout;

        public ExpandableLinearLayout expandableLayout;
        public TextView tvRecordedDuration;
        public TextView tvRecordedHeartRate;
        public TextView tvRecordedSteps;
        public TextView tvRecordedCalories;
        public TextView tvRecordedDistance;

        public ViewHolder(View v) {
            super(v);
            imgExIcon = (ImageView) v.findViewById(R.id.imgRecentExercise);
            tvRecentExName = (TextView) v.findViewById(R.id.tvRecentExerciseName);
            tvRecentDate = (TextView) v.findViewById(R.id.tvRecentExerciseDate);
            buttonLayout = (ImageButton) v.findViewById(R.id.button_arrow);
            expandableLayout = (ExpandableLinearLayout) v.findViewById(R.id.expandableLayout);

            tvRecordedDuration = (TextView) v.findViewById(R.id.progress_duration);
            tvRecordedHeartRate = (TextView) v.findViewById(R.id.progress_heart_rate);
            tvRecordedSteps = (TextView) v.findViewById(R.id.progress_steps);
            tvRecordedCalories = (TextView) v.findViewById(R.id.progress_calories);
            tvRecordedDistance = (TextView) v.findViewById(R.id.progress_distance);
        }
    }

    public ObjectAnimator createRotateAnimator(final View target, final float from, final float to) {
        ObjectAnimator animator = ObjectAnimator.ofFloat(target, "rotation", from, to);
        animator.setDuration(300);
        animator.setInterpolator(Utils.createInterpolator(Utils.LINEAR_INTERPOLATOR));
        return animator;
    }
}
