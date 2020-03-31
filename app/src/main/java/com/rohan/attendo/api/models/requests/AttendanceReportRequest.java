package com.rohan.attendo.api.models.requests;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class AttendanceReportRequest {
    @SerializedName("teacher_lecture_ids")
    private List<Long> teacherLectureIds;


    public AttendanceReportRequest(){this.teacherLectureIds = new ArrayList<>(); }
    public AttendanceReportRequest(List<Long> teacherLectureIds) {
        this.teacherLectureIds = teacherLectureIds;
    }

    public void addToList(Long teacherLectureId){
        this.teacherLectureIds.add(teacherLectureId);
    }

    public void removeFromList(int index){
        this.teacherLectureIds.remove(index);
    }

    public List<Long> getTeacherLectureIds() {
        return teacherLectureIds;
    }

    public void setTeacherLectureIds(List<Long> teacherLectureIds) {
        this.teacherLectureIds = teacherLectureIds;
    }
}
