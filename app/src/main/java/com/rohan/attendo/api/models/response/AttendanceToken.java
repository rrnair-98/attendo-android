package com.rohan.attendo.api.models.response;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

public class AttendanceToken {
    private Long id;
    @SerializedName("student_id")
    private Long studentId;

    private String token;
    @SerializedName("created_at")
    private Date createdAt;
    @SerializedName("updated_at")
    private Date updatedAt;

    public AttendanceToken(){}


    public AttendanceToken(Long id, String token, Date createdAt, Date updatedAt, Long studentId, String token1) {
        this.id = id;
        this.token = token;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.studentId = studentId;
        this.token = token1;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Long getStudentId() {
        return studentId;
    }

    public void setStudentId(Long studentId) {
        this.studentId = studentId;
    }
}
