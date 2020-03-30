package com.rohan.attendo.api.models.requests;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class AttendanceBulkInsert {

    @SerializedName("lecture_id")
    private Long lectureId;
    @SerializedName("tokens")
    private Set<String> tokens;
    public AttendanceBulkInsert(Long lectureId){
        this.lectureId = lectureId;
        this.tokens = new HashSet<>();
    }

    public void addToken(String token){
        this.tokens.add(token);
    }

    public Long getLectureId() {
        return lectureId;
    }

    public Set<String> getTokens() {
        return tokens;
    }
}
