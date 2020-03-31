package com.rohan.attendo.api.retrofit;

import com.rohan.attendo.api.models.requests.AttendanceBulkInsert;
import com.rohan.attendo.api.models.requests.AttendanceReportRequest;
import com.rohan.attendo.api.models.requests.LoginRequest;
import com.rohan.attendo.api.models.response.AccessToken;
import com.rohan.attendo.api.models.response.AttendancePercent;
import com.rohan.attendo.api.models.response.AttendanceToken;
import com.rohan.attendo.api.models.response.Lecture;
import com.rohan.attendo.api.models.response.User;

import java.util.List;

import okhttp3.ResponseBody;
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


    @POST("teacher/attendance/{teacherLectureId}")
    public Call<String> bulkInsertAttendance(@Header("Authorization") String token,
                                             @Path("teacherLectureId") Long teacherLectureId,
                                             @Body AttendanceBulkInsert bulkInsert);

    @GET("teacher/student/att-token/{attendanceToken}")
    public Call<User> getStudentByAttendanceToken(@Header("Authorization")String authToken,
                                                  @Path("attendanceToken") String attendanceToken);

    @GET("teacher/lectures/")
    public Call<List<Lecture>> getLecturesForTeacher(@Header("Authorization") String authToken);

    @GET("student/lectures")
    public Call<List<Lecture>> getLecturesForStudent(@Header("Authorization") String authToken);

    @POST("student/attendance-token")
    public Call<AttendanceToken> getOrCreateAttendanceToken(@Header("Authorization") String authToken);



    @GET("student/attendance/{studentLectureId}")
    public Call<List<AttendancePercent>> getAttendanceForStudentByStudentLectureId(
            @Header("Authorization") String authToken,
            @Path("studentLectureId") Long studentLectureId
    );


    @GET("teacher/attendance/{teacherLectureId}")
    public Call<List<AttendancePercent>> getAttendanceForTeacherByTeacherLectureId(@Header("Authorization") String authToken,
                                                                  @Path("teacherLectureId") Long teacherLectureId);


    @POST("teacher/report")
    public Call<ResponseBody> downloadReportExcel(@Header("Authorization") String authToken,
                                                  @Body AttendanceReportRequest attendanceReportRequest);

}

