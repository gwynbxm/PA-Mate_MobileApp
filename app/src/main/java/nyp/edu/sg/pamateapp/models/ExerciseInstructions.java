/*
 * Copyright (c) 2020 Gwyn Bong Xiao Min.
 * All rights reserved.
 * Last Modified 4/6/20 8:25 PM
 */

package nyp.edu.sg.pamateapp.models;

/**
 * Created by GBXM on 5/2/2018.
 */

public class ExerciseInstructions {
    @com.google.gson.annotations.SerializedName("id")
    private String instructionsId;
    @com.google.gson.annotations.SerializedName("exerciseName")
    private String exerciseName;
    @com.google.gson.annotations.SerializedName("exerciseInstructions")
    private String exerciseInstructions;
    @com.google.gson.annotations.SerializedName("exerciseVideoUrl")
    private String exerciseVideoUrl;
    @com.google.gson.annotations.SerializedName("videoThumbnail")
    private String videoThumbnail;

    public ExerciseInstructions() {
    }

    public ExerciseInstructions(String exerciseName, String exerciseInstructions, String exerciseVideoUrl, String videoThumbnail) {
        this.exerciseName = exerciseName;
        this.exerciseInstructions = exerciseInstructions;
        this.exerciseVideoUrl = exerciseVideoUrl;
        this.videoThumbnail = videoThumbnail;
    }

    public String getInstructionsId() {
        return instructionsId;
    }

    public void setInstructionsId(String instructionsId) {
        this.instructionsId = instructionsId;
    }

    public String getExerciseName() {
        return exerciseName;
    }

    public void setExerciseName(String exerciseName) {
        this.exerciseName = exerciseName;
    }

    public String getExerciseInstructions() {
        return exerciseInstructions;
    }

    public void setExerciseInstructions(String exerciseInstructions) {
        this.exerciseInstructions = exerciseInstructions;
    }

    public String getExerciseVideoUrl() {
        return exerciseVideoUrl;
    }

    public void setExerciseVideoUrl(String exerciseVideoUrl) {
        this.exerciseVideoUrl = exerciseVideoUrl;
    }

    public String getVideoThumbnail() {
        return videoThumbnail;
    }

    public void setVideoThumbnail(String videoThumbnail) {
        this.videoThumbnail = videoThumbnail;
    }
}
