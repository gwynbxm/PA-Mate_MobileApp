/*
 * Copyright (c) 2020 Gwyn Bong Xiao Min.
 * All rights reserved.
 * Last Modified 4/6/20 8:25 PM
 */

package nyp.edu.sg.pamateapp.models;

/**
 * Created by GBXM on 24/1/2018.
 */

public class Notifications {
    @com.google.gson.annotations.SerializedName("id")
    private String notificationId;
    @com.google.gson.annotations.SerializedName("notificationTitle")
    private String notificationTitle;
    @com.google.gson.annotations.SerializedName("notificationDesc")
    private String notificationDesc;
    @com.google.gson.annotations.SerializedName("notificationDate")
    private String notificationDate;
    @com.google.gson.annotations.SerializedName("patientUsername")
    private String patientUsername;
    @com.google.gson.annotations.SerializedName("exercisePlanId")
    private String exercisePlanId;

    public Notifications() {
    }

    public Notifications(String notificationId, String notificationTitle, String notificationDesc, String notificationDate, String patientUsername, String exercisePlanId) {
        this.notificationId = notificationId;
        this.notificationTitle = notificationTitle;
        this.notificationDesc = notificationDesc;
        this.notificationDate = notificationDate;
        this.patientUsername = patientUsername;
        this.exercisePlanId = exercisePlanId;
    }

    public Notifications(String notificationTitle, String notificationDesc, String notificationDate, String patientUsername, String exercisePlanId) {
        this.notificationTitle = notificationTitle;
        this.notificationDesc = notificationDesc;
        this.notificationDate = notificationDate;
        this.patientUsername = patientUsername;
        this.exercisePlanId = exercisePlanId;
    }

    public Notifications(String notificationTitle, String notificationDesc, String notificationDate, String patientUsername) {
        this.notificationTitle = notificationTitle;
        this.notificationDesc = notificationDesc;
        this.notificationDate = notificationDate;
        this.patientUsername = patientUsername;
    }

    public String getNotificationId() {
        return notificationId;
    }

    public void setNotificationId(String notificationId) {
        this.notificationId = notificationId;
    }

    public String getNotificationTitle() {
        return notificationTitle;
    }

    public void setNotificationTitle(String notificationTitle) {
        this.notificationTitle = notificationTitle;
    }

    public String getNotificationDesc() {
        return notificationDesc;
    }

    public void setNotificationDesc(String notificationDesc) {
        this.notificationDesc = notificationDesc;
    }

    public String getNotificationDate() {
        return notificationDate;
    }

    public void setNotificationDate(String notificationDate) {
        this.notificationDate = notificationDate;
    }

    public String getPatientUsername() {
        return patientUsername;
    }

    public void setPatientUsername(String patientUsername) {
        this.patientUsername = patientUsername;
    }

    public String getExercisePlanId() {
        return exercisePlanId;
    }

    public void setExercisePlanId(String exercisePlanId) {
        this.exercisePlanId = exercisePlanId;
    }
}
