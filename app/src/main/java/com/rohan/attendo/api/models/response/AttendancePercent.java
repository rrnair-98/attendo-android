package com.rohan.attendo.api.models.response;

import com.google.gson.annotations.SerializedName;

public class AttendancePercent {
    @SerializedName("student_id")
    private Long studentId;

    private Double percentage;

    @SerializedName("lecture_id")
    private Long lectureId;

    @SerializedName("lecture_name")
    private String lectureName;

    @SerializedName("lecs_attended")
    private Long lecsAttended;

    @SerializedName("num")
    private Long totalLectures;

    public AttendancePercent(){}

    public AttendancePercent(Long studentId, Double percentage, Long lectureId, String lectureName, Long lecsAttended, Long totalLectures) {
        this.studentId = studentId;
        this.percentage = percentage;
        this.lectureId = lectureId;
        this.lectureName = lectureName;
        this.lecsAttended = lecsAttended;
        this.totalLectures = totalLectures;
    }

    public Long getStudentId() {
        return studentId;
    }

    public void setStudentId(Long studentId) {
        this.studentId = studentId;
    }

    public Double getPercentage() {
        return percentage;
    }

    public void setPercentage(Double percentage) {
        this.percentage = percentage;
    }

    public Long getLectureId() {
        return lectureId;
    }

    public void setLectureId(Long lectureId) {
        this.lectureId = lectureId;
    }

    public String getLectureName() {
        return lectureName;
    }

    public void setLectureName(String lectureName) {
        this.lectureName = lectureName;
    }

    public Long getLecsAttended() {
        return lecsAttended;
    }

    public void setLecsAttended(Long lecsAttended) {
        this.lecsAttended = lecsAttended;
    }

    public Long getTotalLectures() {
        return totalLectures;
    }

    public void setTotalLectures(Long totalLectures) {
        this.totalLectures = totalLectures;
    }
}
