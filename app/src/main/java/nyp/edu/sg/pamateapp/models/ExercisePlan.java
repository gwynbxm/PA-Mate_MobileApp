/*
 * Copyright (c) 2020 Gwyn Bong Xiao Min.
 * All rights reserved.
 * Last Modified 4/6/20 8:25 PM
 */

package nyp.edu.sg.pamateapp.models;

/**
 * Created by GBXM on 23/12/2017.
 */

public class ExercisePlan {
    @com.google.gson.annotations.SerializedName("id")
    private String planId;
    @com.google.gson.annotations.SerializedName("planName")
    private String planName;
    @com.google.gson.annotations.SerializedName("planStartDate")
    private String planStartDate;
    @com.google.gson.annotations.SerializedName("patientUsername")
    private String patientUsername;//Patient key
    @com.google.gson.annotations.SerializedName("dtPlanCreated")
    private String planCreatedDate;//Patient key

    public ExercisePlan() {
    }

    public ExercisePlan(String planId, String planName, String planStartDate, String patientPlan, String planCreatedDate) {
        this.planId = planId;
        this.planName = planName;
        this.planStartDate = planStartDate;
        this.patientUsername = patientPlan;
        this.planCreatedDate = planCreatedDate;
    }

    public String getPlanId() {
        return planId;
    }

    public void setPlanId(String planId) {
        this.planId = planId;
    }

    public String getPlanName() {
        return planName;
    }

    public void setPlanName(String planName) {
        this.planName = planName;
    }

    public String getPlanStartDate() {
        return planStartDate;
    }

    public void setPlanStartDate(String planStartDate) {
        this.planStartDate = planStartDate;
    }

    public String getPatientPlan() {
        return patientUsername;
    }

    public void setPatientPlan(String patientPlan) {
        this.patientUsername = patientPlan;
    }

    public String getPlanCreatedDate() {
        return planCreatedDate;
    }

    public void setPlanCreatedDate(String planCreatedDate) {
        this.planCreatedDate = planCreatedDate;
    }
}
