package com.rohan.attendo.api.models.requests;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class AttendanceBulkInsert {
    private Long teacherId;
    private Long lectureId;
    private Set<String> tokens;
    public AttendanceBulkInsert(Long teacherId, Long lectureId){
        this.teacherId = teacherId;
        this.lectureId = lectureId;
        this.tokens = new HashSet<>();
    }

    public void addToken(String token){
        this.tokens.add(token);
    }

    public Long getTeacherId() {
        return teacherId;
    }

    public Long getLectureId() {
        return lectureId;
    }

    public Set<String> getTokens() {
        return tokens;
    }
}
