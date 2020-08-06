/*
 * Copyright (c) 2020 Gwyn Bong Xiao Min.
 * All rights reserved.
 * Last Modified 4/6/20 8:25 PM
 */

package nyp.edu.sg.pamateapp.models;

/**
 * Created by GBXM on 23/12/2017.
 */

public class ExercisePerformance {
    @com.google.gson.annotations.SerializedName("id")
    private String performanceId;
    @com.google.gson.annotations.SerializedName("nameOfExercise")
    private String nameOfExercise;
    @com.google.gson.annotations.SerializedName("heartRate")
    private String heartRate;
    @com.google.gson.annotations.SerializedName("caloriesBurnt")
    private String caloriesBurnt;
    @com.google.gson.annotations.SerializedName("noOfSteps")
    private String noOfSteps;
    @com.google.gson.annotations.SerializedName("distanceCovered")
    private String distanceCovered;
    @com.google.gson.annotations.SerializedName("durationMeasured")
    private String durationMeasured;
    @com.google.gson.annotations.SerializedName("startDate")
    private String startDate;
    @com.google.gson.annotations.SerializedName("patientUsername")
    private String patientPerf;//Patient key
    @com.google.gson.annotations.SerializedName("exercisePlanId")
    private String exercisePlanId;//exercise details key


    public ExercisePerformance() {
    }

    public ExercisePerformance(String performanceId, String nameOfExercise, String heartRate, String caloriesBurnt, String noOfSteps, String distanceCovered, String durationMeasured, String startDate) {
        this.performanceId = performanceId;
        this.nameOfExercise = nameOfExercise;
        this.heartRate = heartRate;
        this.caloriesBurnt = caloriesBurnt;
        this.noOfSteps = noOfSteps;
        this.distanceCovered = distanceCovered;
        this.durationMeasured = durationMeasured;
        this.startDate = startDate;
    }

    public ExercisePerformance(String nameOfExercise, String heartRate, String caloriesBurnt, String noOfSteps, String distanceCovered, String durationMeasured, String startDate) {
        this.nameOfExercise = nameOfExercise;
        this.heartRate = heartRate;
        this.caloriesBurnt = caloriesBurnt;
        this.noOfSteps = noOfSteps;
        this.distanceCovered = distanceCovered;
        this.durationMeasured = durationMeasured;
        this.startDate = startDate;
    }

    public ExercisePerformance(String performanceId, String nameOfExercise, String heartRate, String caloriesBurnt, String noOfSteps, String distanceCovered, String durationMeasured, String startDate, String patientPerf, String exercisePlanId) {
        this.performanceId = performanceId;
        this.nameOfExercise = nameOfExercise;
        this.heartRate = heartRate;
        this.caloriesBurnt = caloriesBurnt;
        this.noOfSteps = noOfSteps;
        this.distanceCovered = distanceCovered;
        this.durationMeasured = durationMeasured;
        this.startDate = startDate;
        this.patientPerf = patientPerf;
        this.exercisePlanId = exercisePlanId;
    }

    public ExercisePerformance(String nameOfExercise, String heartRate, String caloriesBurnt, String noOfSteps, String distanceCovered, String durationMeasured, String startDate, String patientPerf, String exercisePlanId) {
        this.nameOfExercise = nameOfExercise;
        this.heartRate = heartRate;
        this.caloriesBurnt = caloriesBurnt;
        this.noOfSteps = noOfSteps;
        this.distanceCovered = distanceCovered;
        this.durationMeasured = durationMeasured;
        this.startDate = startDate;
        this.patientPerf = patientPerf;
        this.exercisePlanId = exercisePlanId;
    }

    public String getPerformanceId() {
        return performanceId;
    }

    public void setPerformanceId(String performanceId) {
        this.performanceId = performanceId;
    }

    public String getNameOfExercise() {
        return nameOfExercise;
    }

    public void setNameOfExercise(String nameOfExercise) {
        this.nameOfExercise = nameOfExercise;
    }

    public String getHeartRate() {
        return heartRate;
    }

    public void setHeartRate(String heartRate) {
        this.heartRate = heartRate;
    }

    public String getCaloriesBurnt() {
        return caloriesBurnt;
    }

    public void setCaloriesBurnt(String caloriesBurnt) {
        this.caloriesBurnt = caloriesBurnt;
    }

    public String getNoOfSteps() {
        return noOfSteps;
    }

    public void setNoOfSteps(String noOfSteps) {
        this.noOfSteps = noOfSteps;
    }

    public String getDistanceCovered() {
        return distanceCovered;
    }

    public void setDistanceCovered(String distanceCovered) {
        this.distanceCovered = distanceCovered;
    }

    public String getDurationMeasured() {
        return durationMeasured;
    }

    public void setDurationMeasured(String durationMeasured) {
        this.durationMeasured = durationMeasured;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getPatientPerf() {
        return patientPerf;
    }

    public void setPatientPerf(String patientPerf) {
        this.patientPerf = patientPerf;
    }

    public String getExercisePlanId() {
        return exercisePlanId;
    }

    public void setExercisePlanId(String exercisePlanId) {
        this.exercisePlanId = exercisePlanId;
    }
}
