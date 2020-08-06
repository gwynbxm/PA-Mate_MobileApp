/*
 * Copyright (c) 2020 Gwyn Bong Xiao Min.
 * All rights reserved.
 * Last Modified 4/6/20 8:25 PM
 */

package nyp.edu.sg.pamateapp.models;

import android.icu.text.DateFormat;

import com.google.gson.annotations.SerializedName;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by GBXM on 17/12/2017.
 */

public class Patient {

    //private variables
    @com.google.gson.annotations.SerializedName("id")
    private String patientId;
    @com.google.gson.annotations.SerializedName("patientName")
    private String patientName;
    @com.google.gson.annotations.SerializedName("patientGender")
    private String patientGender;
    @com.google.gson.annotations.SerializedName("patientDOB")
    private String patientDOB;
    @com.google.gson.annotations.SerializedName("patientHeight")
    private String patientHeight;
    @com.google.gson.annotations.SerializedName("patientWeight")
    private String patientWeight;
    @com.google.gson.annotations.SerializedName("patientEmail")
    private String patientEmail;
    @com.google.gson.annotations.SerializedName("patientUsername")
    private String patientUsername;
    @com.google.gson.annotations.SerializedName("patientPassword")
    private String patientPassword;
    @com.google.gson.annotations.SerializedName("dtLogin")
    private String dtLogin;
    @com.google.gson.annotations.SerializedName("dtLogout")
    private String dtLogout;
    @com.google.gson.annotations.SerializedName("memberSince")
    private String memberSince;

    //empty constructor
    public Patient() {
    }

    public Patient(String patientId, String patientName, String patientGender, String patientDOB, String patientHeight, String patientWeight, String patientEmail, String patientUsername, String patientPassword, String memberSince) {
        this.patientId = patientId;
        this.patientName = patientName;
        this.patientGender = patientGender;
        this.patientDOB = patientDOB;
        this.patientHeight = patientHeight;
        this.patientWeight = patientWeight;
        this.patientEmail = patientEmail;
        this.patientUsername = patientUsername;
        this.patientPassword = patientPassword;
        this.memberSince = memberSince;
    }

    public Patient(String patientName, String patientGender, String patientDOB, String patientHeight, String patientWeight, String patientEmail, String patientUsername, String patientPassword, String dtLogin, String dtLogout, String memberSince) {
        this.patientName = patientName;
        this.patientGender = patientGender;
        this.patientDOB = patientDOB;
        this.patientHeight = patientHeight;
        this.patientWeight = patientWeight;
        this.patientEmail = patientEmail;
        this.patientUsername = patientUsername;
        this.patientPassword = patientPassword;
        this.dtLogin = dtLogin;
        this.dtLogout = dtLogout;
        this.memberSince = memberSince;
    }

    public Patient(String patientName, String patientGender, String patientDOB, String patientHeight, String patientWeight, String patientEmail, String patientUsername, String patientPassword) {
        this.patientName = patientName;
        this.patientGender = patientGender;
        this.patientDOB = patientDOB;
        this.patientHeight = patientHeight;
        this.patientWeight = patientWeight;
        this.patientEmail = patientEmail;
        this.patientUsername = patientUsername;
        this.patientPassword = patientPassword;
    }

    public String getPatientId() {
        return patientId;
    }

    public void setPatientId(String patientId) {
        this.patientId = patientId;
    }

    public String getPatientName() {
        return patientName;
    }

    public void setPatientName(String patientName) {
        this.patientName = patientName;
    }

    public String getPatientGender() {
        return patientGender;
    }

    public void setPatientGender(String patientGender) {
        this.patientGender = patientGender;
    }

    public String getPatientDOB() {
        return patientDOB;
    }

    public void setPatientDOB(String patientDOB) {
        this.patientDOB = patientDOB;
    }

    public String getPatientHeight() {
        return patientHeight;
    }

    public void setPatientHeight(String patientHeight) {
        this.patientHeight = patientHeight;
    }

    public String getPatientWeight() {
        return patientWeight;
    }

    public void setPatientWeight(String patientWeight) {
        this.patientWeight = patientWeight;
    }

    public String getPatientEmail() {
        return patientEmail;
    }

    public void setPatientEmail(String patientEmail) {
        this.patientEmail = patientEmail;
    }

    public String getPatientUsername() {
        return patientUsername;
    }

    public void setPatientUsername(String patientUsername) {
        this.patientUsername = patientUsername;
    }

    public String getPatientPassword() {
        return patientPassword;
    }

    public void setPatientPassword(String patientPassword) {
        this.patientPassword = patientPassword;
    }

    public String getDtLogin() {
        return dtLogin;
    }

    public void setDtLogin(String dtLogin) {
        this.dtLogin = dtLogin;
    }

    public String getDtLogout() {
        return dtLogout;
    }

    public void setDtLogout(String dtLogout) {
        this.dtLogout = dtLogout;
    }

    public String getMemberSince() {
        return memberSince;
    }

    public void setMemberSince(String memberSince) {
        this.memberSince = memberSince;
    }

}
