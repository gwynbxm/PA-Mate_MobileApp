/*
 * Copyright (c) 2020 Gwyn Bong Xiao Min.
 * All rights reserved.
 * Last Modified 4/6/20 8:25 PM
 */

package nyp.edu.sg.pamateapp.helper;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import nyp.edu.sg.pamateapp.R;
import nyp.edu.sg.pamateapp.models.ExercisePlan;

/**
 * Created by GBXM on 26/12/2017.
 */

public class ExercisePlanListAdapter extends BaseAdapter {
    private Context context;
    int resource;
    private ArrayList<ExercisePlan> exercisePlanList;

    DbHelper helper;

    public ExercisePlanListAdapter(Context context, int resource,
                                   ArrayList<ExercisePlan> exercisePlanList) {
        this.context = context;
        this.resource = resource;
        this.exercisePlanList = exercisePlanList;
    }

    @Override
    public int getCount() {
        return (exercisePlanList == null) ? 0 : exercisePlanList.size();
    }

    @Override
    public Object getItem(int position) {
        return exercisePlanList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        final ExercisePlan exercisePlanList = this.exercisePlanList.get(position);

        final ExercisePlanListAdapterViewHolder viewHolder;

        if (convertView == null) {
            LayoutInflater inflater =
                    (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(resource, parent, false);

            viewHolder = new ExercisePlanListAdapterViewHolder();
            viewHolder.tvPlanName = (TextView) convertView.findViewById(R.id.tv_plan_name);
            viewHolder.tvPlanDate = (TextView) convertView.findViewById(R.id.tv_plan_date);
            viewHolder.imgStatus = (ImageView) convertView.findViewById(R.id.imgStatus);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ExercisePlanListAdapterViewHolder) convertView.getTag();
        }

        viewHolder.tvPlanName.setText(exercisePlanList.getPlanName());
        viewHolder.tvPlanDate.setText("Complete by: " + exercisePlanList.getPlanStartDate());

        helper = new DbHelper(context);

        Threadings.runInBackground(new Runnable() {
            @Override
            public void run() {
                final boolean isPlanDone = helper.checkPlanDone(exercisePlanList.getPlanId());
                Threadings.postRunnable(new Runnable() {
                    @Override
                    public void run() {
                        if (isPlanDone) {
                            viewHolder.imgStatus.setImageResource(R.drawable.complete);
                        } else {
                            viewHolder.imgStatus.setImageResource(R.drawable.incomplete);
                        }
                    }
                });
            }
        });
        return convertView;
    }

    private class ExercisePlanListAdapterViewHolder {
        TextView tvPlanName;
        TextView tvPlanDate;
        ImageView imgStatus;
    }
}
