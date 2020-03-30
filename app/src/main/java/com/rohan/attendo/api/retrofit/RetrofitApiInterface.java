package com.rohan.attendo.api.retrofit;

import com.rohan.attendo.api.models.requests.AttendanceBulkInsert;
import com.rohan.attendo.api.models.requests.LoginRequest;
import com.rohan.attendo.api.models.response.AccessToken;
import com.rohan.attendo.api.models.response.AttendancePercent;
import com.rohan.attendo.api.models.response.AttendanceToken;
import com.rohan.attendo.api.models.response.Lecture;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface RetrofitApiInterface {
    @POST("login")
    public Call<AccessToken> login(@Body LoginRequest loginRequest);

    @DELETE("logout")
    public Call<String> logout(@Header("Authorization") String authToken);

    @POST("refresh/{refreshToken}")
    public Call<AccessToken> refresh(@Header("Authorization") String authToken,
                                     @Path("refreshToken") String refreshToken);

    @POST("attendanceToken/create")
    public Call<AttendanceToken> getOrCreateAttendanceToken(@Header("Authorization") String authToken);

    @POST("teacher/attendance/{teacherLectureId}")
    public Call<String> bulkInsertAttendance(@Header("Authorization") String token,
                                             @Path("teacherLectureId") Long teacherLectureId,
                                             @Body AttendanceBulkInsert bulkInsert);

    @GET("teacher/lectures/")
    public Call<List<Lecture>> getLecturesForTeacher(@Header("Authorization") String authToken);

    @GET("student/lectures")
    public Call<List<Lecture>> getLecturesForStudent(@Header("Authorization") String authToken);



    @GET("attendance/student/{start}/{end}/{studentId}")
    public Call<List<AttendancePercent>> getAttendanceByStudentId(@Path("start") String startDate,
                                                                  @Path("end") String endDate,
                                                                  @Path("studentId") Long studentId);


    @GET("attendance/student/{start}/{end}/{lectureId}")
    public Call<List<AttendancePercent>> getAttendanceByLectureId(@Path("start") String startDate,
                                                                  @Path("end") String endDate,
                                                                  @Path("lectureId") Long lectureId);


    @GET("attendance/student/{start}/{end}/{studentId}/{lectureId}")
    public Call<List<AttendancePercent>> getAttendanceByLectureAndStudentId(@Path("start") String startDate,
                                                                            @Path("end") String endDate,
                                                                            @Path("studentId") Long studentId,
                                                                            @Path("lectureId")Long lectureId);

}
