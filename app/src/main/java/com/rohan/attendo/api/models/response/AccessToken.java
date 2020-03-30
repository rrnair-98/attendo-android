package com.rohan.attendo.api.models.response;

import com.google.gson.annotations.SerializedName;

public class AccessToken {

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

    private Integer role;


    public static Integer ROLE_STUDENT = 0;
    public static Integer ROLE_TEACHER = 8;
    public static Integer ROLE_HOD = 65536;

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
        return accessTokenExpiry;
    }

    public void setCreatedAt(Long createdAt) {
        this.accessTokenExpiry = createdAt;
    }

    public Long getRefreshTokenExpiresAt() {
        return refreshTokenExpiresAt;
    }

    public void setRefreshTokenExpiresAt(Long refreshTokenExpiresAt) {
        this.refreshTokenExpiresAt = refreshTokenExpiresAt;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
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
