package com.rohan.attendo.api.models.response;

import com.google.gson.annotations.SerializedName;

public class AccessToken {

    private Long id;
    @SerializedName("user_id")
    private Long userId;
    @SerializedName("created_at")
    private Long createdAt;
    @SerializedName("refresh_token_expires_at")
    private Long refreshTokenExpiresAt;
    @SerializedName("token")
    private String token;
    @SerializedName("refresh_token")
    private String refreshToken;

    private Integer role;


    public static Integer ROLE_STUDENT = 0;
    public static Integer ROLE_TEACHER = 8;
    public static Integer ROLE_HOD = 65536;

    public AccessToken(){}


    public AccessToken(Long id, Long userId, Long createdAt, Long refreshTokenExpiresAt, String token, String refreshToken) {
        this.id = id;
        this.userId = userId;
        this.createdAt = createdAt;
        this.refreshTokenExpiresAt = refreshTokenExpiresAt;
        this.token = token;
        this.refreshToken = refreshToken;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Long createdAt) {
        this.createdAt = createdAt;
    }

    public Long getRefreshTokenExpiresAt() {
        return refreshTokenExpiresAt;
    }

    public void setRefreshTokenExpiresAt(Long refreshTokenExpiresAt) {
        this.refreshTokenExpiresAt = refreshTokenExpiresAt;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public boolean isTeacher(){
        return this.role.equals(ROLE_TEACHER);
    }

    public boolean isStudent(){
        return this.role.equals( ROLE_STUDENT);
    }

}
