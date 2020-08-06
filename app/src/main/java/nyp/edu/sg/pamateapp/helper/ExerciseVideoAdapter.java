/*
 * Copyright (c) 2020 Gwyn Bong Xiao Min.
 * All rights reserved.
 * Last Modified 4/6/20 8:25 PM
 */

package nyp.edu.sg.pamateapp.helper;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;

import nyp.edu.sg.pamateapp.R;
import nyp.edu.sg.pamateapp.models.ExerciseInstructions;

/**
 * Created by GBXM on 5/2/2018.
 */

public class ExerciseVideoAdapter extends BaseAdapter {
    private Context context;
    int resource;
    private ArrayList<ExerciseInstructions> exerciseInstructionsList;

    public ExerciseVideoAdapter(Context context, int resource,
                                ArrayList<ExerciseInstructions> exerciseInstructionsList) {
        this.context = context;
        this.resource = resource;
        this.exerciseInstructionsList = exerciseInstructionsList;
    }

    @Override
    public int getCount() {
        return (exerciseInstructionsList == null) ? 0 : exerciseInstructionsList.size();
    }

    @Override
    public Object getItem(int position) {
        return exerciseInstructionsList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        final ExerciseInstructions exerciseInstructionList =
                this.exerciseInstructionsList.get(position);

        ExercisePlanListAdapterViewHolder viewHolder;

        if (convertView == null) {
            LayoutInflater inflater =
                    (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(resource, parent, false);

            viewHolder = new ExercisePlanListAdapterViewHolder();
            viewHolder.tvVideoName = (TextView) convertView.findViewById(R.id.videoName);
            viewHolder.tvVideoDesc =
                    (TextView) convertView.findViewById(R.id.videoDescription);
            viewHolder.videoThumbnail = (ImageView) convertView.findViewById(R.id.videoThumbnail);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ExercisePlanListAdapterViewHolder) convertView.getTag();
        }

        viewHolder.tvVideoName.setText(exerciseInstructionList.getExerciseName());
        viewHolder.tvVideoDesc.setText(exerciseInstructionList.getExerciseInstructions());
        viewHolder.videoThumbnail.setImageResource(R.drawable.first);

        return convertView;
    }

    private class ExercisePlanListAdapterViewHolder {
        TextView tvVideoName;
        TextView tvVideoDesc;
        ImageView videoThumbnail;
    }
}
