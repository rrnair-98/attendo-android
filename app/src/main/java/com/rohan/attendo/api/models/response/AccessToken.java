package com.rohan.attendo.api.models.response;

import android.util.Log;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class AccessToken implements Serializable{

    private Long id;
    @SerializedName("user_id")
    private Long userId;
    @SerializedName("access_token_expiry")
    private Long accessTokenExpiry;
    @SerializedName("refresh_token_expiry")
    private Long refreshTokenExpiresAt;
    @SerializedName("access_token")
    private String accessToken;
    @SerializedName("refresh_token")
    private String refreshToken;

    @SerializedName("role")
    private Integer role;


    public static Integer ROLE_STUDENT = 0;
    public static Integer ROLE_TEACHER = 8;
    public static Integer ROLE_HOD = 65536;
    public static Integer ROLE_ADMIN = 2147483647;

    public AccessToken(){}


    public AccessToken(Long id, Long userId, Long accessTokenExpiry, Long refreshTokenExpiresAt, String accessToken, String refreshToken) {
        this.id = id;
        this.userId = userId;
        this.accessTokenExpiry = accessTokenExpiry;
        this.refreshTokenExpiresAt = refreshTokenExpiresAt;
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
    }

    public Long getId() {
        return id == null ? -100 : id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId == null ? -100 : userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getCreatedAt() {
        return accessTokenExpiry == null ? -100 : accessTokenExpiry;
    }

    public void setCreatedAt(Long createdAt) {
        this.accessTokenExpiry = createdAt;
    }

    public Long getRefreshTokenExpiresAt() {
        return refreshTokenExpiresAt == null ? -100 : refreshTokenExpiresAt;
    }

    public void setRefreshTokenExpiresAt(Long refreshTokenExpiresAt) {
        this.refreshTokenExpiresAt = refreshTokenExpiresAt;
    }

    public String getAccessToken() {
        return accessToken == null ? "" : accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getRefreshToken() {
        return refreshToken == null ? "" : refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public boolean isTeacher(){
        return this.role.equals(ROLE_TEACHER) || this.role.equals(ROLE_ADMIN);
    }

    public boolean isStudent(){
        return this.role.equals( ROLE_STUDENT) || this.role.equals(ROLE_ADMIN);
    }

    public boolean isHOD(){return this.role.equals(ROLE_HOD) || this.role.equals(ROLE_ADMIN);}

    public Integer getRole(){ return  role; }

    public void setRole(Integer role){ this.role = role; }

}
