package com.rohan.attendo.api.models.response;

import com.google.gson.annotations.SerializedName;

public class Lecture {
    private Long id;
    @SerializedName("teacher_id")
    private Long teacherId;
    @SerializedName("lecture_number")
    private Integer lectureNumber;
    @SerializedName("day_of_week")
    private Integer dayOfWeek;
    @SerializedName("subject_name")
    private String subjectName;

    public Lecture(){}

    public Lecture(Long id, Long teacherId, Integer lectureNumber, Integer dayOfWeek, String subjectName) {
        this.id = id;
        this.teacherId = teacherId;
        this.lectureNumber = lectureNumber;
        this.dayOfWeek = dayOfWeek;
        this.subjectName = subjectName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(Long teacherId) {
        this.teacherId = teacherId;
    }

    public Integer getLectureNumber() {
        return lectureNumber;
    }

    public void setLectureNumber(Integer lectureNumber) {
        this.lectureNumber = lectureNumber;
    }

    public Integer getDayOfWeek() {
        return dayOfWeek;
    }

    public void setDayOfWeek(Integer dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
    }

    public String getSubjectName() {
        return subjectName;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }
}
