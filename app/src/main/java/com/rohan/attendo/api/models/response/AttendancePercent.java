package com.rohan.attendo.api.models.response;

import com.google.gson.annotations.SerializedName;

public class AttendancePercent {
    @SerializedName("id")
    private Long studentId;

    @SerializedName("roll_number")
    private Long rollNumber;

    @SerializedName("name")
    private String name;

    @SerializedName("email")
    private String email;

    @SerializedName("percentage")
    private Double percentage;

    @SerializedName("total_lectures")
    private Long totalLectures;

    public AttendancePercent(){}

    public AttendancePercent(Long studentId, Long rollNumber, String name, Double percentage, Long totalLectures, String email) {
        this.studentId = studentId;
        this.rollNumber = rollNumber;
        this.name = name;
        this.percentage = percentage;
        this.totalLectures = totalLectures;
        this.email = email;
    }

    public Long getStudentId() {
        return studentId;
    }

    public void setStudentId(Long studentId) {
        this.studentId = studentId;
    }

    public Long getRollNumber() {
        return rollNumber;
    }

    public void setRollNumber(Long rollNumber) {
        this.rollNumber = rollNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getPercentage() {
        return percentage;
    }

    public void setPercentage(Double percentage) {
        this.percentage = percentage;
    }

    public Long getTotalLectures() {
        return totalLectures;
    }

    public void setTotalLectures(Long totalLectures) {
        this.totalLectures = totalLectures;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
