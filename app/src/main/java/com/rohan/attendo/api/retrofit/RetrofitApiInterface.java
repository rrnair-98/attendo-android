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
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface RetrofitApiInterface {
    @POST("login")
    public Call<AccessToken> login(@Body LoginRequest loginRequest);

    @POST("attendanceToken/create")
    public Call<AttendanceToken> getOrCreateAttendanceToken(@Header("Authorization") String authToken);

    @POST("attendance/{teacherId}/{lectureId}")
    public Call<String> bulkInsertAttendance(@Header("Authorization") String token,
                                             @Path("teacherId") Long teacherId,
                                             @Path("lectureId") Long lectureId,
                                             @Body AttendanceBulkInsert bulkInsert);

    @GET("lectures/teacher/{teacherId}")
    public Call<List<Lecture>> getLecturesByTeacher(@Path("teacherId") Long teacherId);

    @GET("lectures/department/{departmentId}")
    public Call<List<Lecture>> getLecturesByDepartment(@Path("departmentId") Long departmentId);


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

