package com.rohan.attendo.api.models.response;

import com.google.gson.annotations.SerializedName;

public class Lecture {
    @SerializedName("id")
    private Long id;
    @SerializedName("lecture_name")
    private String lectureName;

    @SerializedName("actual_relation_id")
    private Long relationId; // to be sent to the server to fetch attendance

    public Lecture(){}

    public Lecture(Long id, String lectureName) {
        this.id = id;
        this.lectureName = lectureName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLectureName() {
        return lectureName;
    }

    public void setLectureName(String lectureName) {
        this.lectureName = lectureName;
    }
}
