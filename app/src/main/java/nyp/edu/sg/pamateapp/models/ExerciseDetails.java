/*
 * Copyright (c) 2020 Gwyn Bong Xiao Min.
 * All rights reserved.
 * Last Modified 4/6/20 8:25 PM
 */

package nyp.edu.sg.pamateapp.models;

/**
 * Created by GBXM on 23/12/2017.
 */

public class ExerciseDetails {
    @com.google.gson.annotations.SerializedName("id")
    private String infoId;
    @com.google.gson.annotations.SerializedName("exerciseName")
    private String exerciseName;
    @com.google.gson.annotations.SerializedName("beatsPerMin")
    private String beatsPerMin;
    @com.google.gson.annotations.SerializedName("calories")
    private String calories;
    @com.google.gson.annotations.SerializedName("stepsCount")
    private String stepsCount;
    @com.google.gson.annotations.SerializedName("distance")
    private String distance;
    @com.google.gson.annotations.SerializedName("durationNeeded")
    private String durationNeeded;

    @com.google.gson.annotations.SerializedName("patientUsername")
    private String patientPlanDetails;//Patient key
    @com.google.gson.annotations.SerializedName("exercisePlanId")
    private String planId;//plan key

    public ExerciseDetails() {
    }

    public ExerciseDetails(String infoId, String exerciseName, String beatsPerMin, String calories, String stepsCount, String distance, String durationNeeded, String patientPlanDetails, String planId) {
        this.infoId = infoId;
        this.exerciseName = exerciseName;
        this.beatsPerMin = beatsPerMin;
        this.calories = calories;
        this.stepsCount = stepsCount;
        this.distance = distance;
        this.durationNeeded = durationNeeded;
        this.patientPlanDetails = patientPlanDetails;
        this.planId = planId;
    }

    public ExerciseDetails(String exerciseName, String beatsPerMin, String calories, String stepsCount, String distance, String durationNeeded, String patientPlanDetails, String planId) {
        this.exerciseName = exerciseName;
        this.beatsPerMin = beatsPerMin;
        this.calories = calories;
        this.stepsCount = stepsCount;
        this.distance = distance;
        this.durationNeeded = durationNeeded;
        this.patientPlanDetails = patientPlanDetails;
        this.planId = planId;
    }

    public String getInfoId() {
        return infoId;
    }

    public void setInfoId(String infoId) {
        this.infoId = infoId;
    }

    public String getExerciseName() {
        return exerciseName;
    }

    public void setExerciseName(String exerciseName) {
        this.exerciseName = exerciseName;
    }

    public String getBeatsPerMin() {
        return beatsPerMin;
    }

    public void setBeatsPerMin(String beatsPerMin) {
        this.beatsPerMin = beatsPerMin;
    }

    public String getCalories() {
        return calories;
    }

    public void setCalories(String calories) {
        this.calories = calories;
    }

    public String getStepsCount() {
        return stepsCount;
    }

    public void setStepsCount(String stepsCount) {
        this.stepsCount = stepsCount;
    }

    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }

    public String getDurationNeeded() {
        return durationNeeded;
    }

    public void setDurationNeeded(String durationNeeded) {
        this.durationNeeded = durationNeeded;
    }

    public String getPatientPlanDetails() {
        return patientPlanDetails;
    }

    public void setPatientPlanDetails(String patientPlanDetails) {
        this.patientPlanDetails = patientPlanDetails;
    }

    public String getPlanId() {
        return planId;
    }

    public void setPlanId(String planId) {
        this.planId = planId;
    }
}
