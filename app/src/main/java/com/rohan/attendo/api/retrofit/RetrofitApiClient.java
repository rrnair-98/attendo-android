package com.rohan.attendo.api.retrofit;

import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.rohan.attendo.R;
import com.rohan.attendo.api.models.requests.AttendanceBulkInsert;
import com.rohan.attendo.api.models.requests.AttendanceReportRequest;
import com.rohan.attendo.api.models.requests.LoginRequest;
import com.rohan.attendo.api.models.response.AccessToken;
import com.rohan.attendo.api.models.response.AttendancePercent;
import com.rohan.attendo.api.models.response.AttendanceToken;
import com.rohan.attendo.api.models.response.Lecture;
import com.rohan.attendo.api.models.response.User;

import java.io.IOException;
import java.net.URLDecoder;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import okio.Buffer;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by rohan on 30/9/17.
 * this class acts as a wrapper for the retrofit client. It has a factory method which requires Context( to prevent gcing) which returns an instance of the class.
 * The context is also used to obtain the BASE_URL from the string.xml file.
 * It has the following functions
 *  1. authorizeUser() sends an object in the Reverberator provided. if the response fails it passes in null;
 *  2. getBaseURL() returns the BASE_URL which is specified in Resources/String file with id base_url
 *  3. getUserDetailsWithID() sends an object in the Reverberator provided. if the response fails it passes in null
 */

public class RetrofitApiClient {
    private static RetrofitApiClient mRetrofitApiClient;
    private Context mContext;
    private final String BASE_URL;
    private final Retrofit mRetrofitInstance;
    private final RetrofitApiInterface mRetrofitApiInterface;
    private final static String TAG="RetrofitApiClient";
    private final static int READ_TIMEOUT=60,WRITE_TIMEOUT=60;



        /*
        * An interceptor is fired before any request is made to the server. Its job is to encode the URL. This implementation is
        * for preventing special char ascii to hex conversion whenever its URLEncoded. Retrofit has no support to stop URL encoding as of now
        * This might change in future releases.*/
    private Interceptor mInterceptor=new Interceptor() {
        private final static String TAG="CustomInterceptor";
        @Override
        public okhttp3.Response intercept(Chain chain) throws IOException {
            Request req=chain.request();
            String body= this.reqToString(req.body());
            String newPostBody= URLDecoder.decode(body,"UTF-8");
            Log.d(TAG,"new post body "+newPostBody);
            RequestBody requestBody=req.body(),otherBody=null;
            if(requestBody!=null){
                otherBody= RequestBody.create(requestBody.contentType(),newPostBody);

            }
            Request request;
            if(req.method().equals("get"))
                    request=req.newBuilder()
                            .method(req.method(), req.body())
                            .get()
                            .build();
            else if(req.method().equals("post"))
                request=req.newBuilder()
                        .method(req.method(), req.body())
                        .post(requestBody)
                        .build();
            else
                request=req.newBuilder().method(req.method(),req.body()).build();

            if(request!=null)
                Log.d(TAG,request.toString());


            return chain.proceed(request);
        }

        final public String reqToString(RequestBody request){
            try{
                final RequestBody requestBodyCopy= request;
                final Buffer buffer=new Buffer();
                if(requestBodyCopy!=null)
                    requestBodyCopy.writeTo(buffer);
                else return "";
                return buffer.readUtf8();

            }catch (IOException ioe){
                ioe.printStackTrace();
                return "didnt work";
            }
        }
    };


    private final static String API_KEY=null;


    /* Context is being takes as a param to prevent the object from getting gc'd. As long as a reference to the other object is alive and is in the young pool the other object can
    * not be gc'd hence the current wont be gcd*/
    private RetrofitApiClient(Context context){
        this.mContext=context;
        this.BASE_URL=this.mContext.getResources().getString(R.string.base_url);
        Gson gson=new GsonBuilder().setLenient().create();
        OkHttpClient okHttpClient = new OkHttpClient().newBuilder()
                .connectTimeout(60, TimeUnit.SECONDS)
                .readTimeout(READ_TIMEOUT, TimeUnit.SECONDS)
                .writeTimeout(WRITE_TIMEOUT, TimeUnit.SECONDS).addInterceptor(this.mInterceptor)
                .build();
 this.mRetrofitInstance= new Retrofit.Builder().baseUrl(this.BASE_URL).client(okHttpClient).addConverterFactory(GsonConverterFactory.create(gson)).build();



        this.mRetrofitApiInterface= this.mRetrofitInstance.create(RetrofitApiInterface.class);


    }
    /* use this factory method to obtain an instance of the class*/
    public static RetrofitApiClient getInstance(Context context){
        if(RetrofitApiClient.mRetrofitApiClient==null){
            RetrofitApiClient.mRetrofitApiClient = new RetrofitApiClient(context);
        }
        return RetrofitApiClient.mRetrofitApiClient;
    }

    public static RetrofitApiClient getInstance(){
        return mRetrofitApiClient;
    }
    /*this function is a generalized one to be used for all call objects ... will pass in the value returned by response ...
    it is the responsibility of the class which implements Reverberator to cast it to his/her liking
    * */

    private void enque(Call call,final Reverberator reverberator){
        call.enqueue(new Callback() {
            @Override
            public void onResponse(Call call, Response response) {
                if(reverberator!=null) {
                    if(response!=null)
                        Log.d(TAG,response.toString());
                    reverberator.reverb(response.body(), response.code());
                }
            }

            @Override
            public void onFailure(Call call, Throwable t) {
                Log.d(TAG,"Couldnt fetch data from server ... read the stackTrace for more info");
                t.printStackTrace();
                if(reverberator!=null)
                    reverberator.reverb(null, 500);
            }
        });
    }

    public String getBaseUrl() {
        return this.BASE_URL;
    }

    /**
     * Hits the login endpoint and sends result through the Reverberator interface.
     * NOTE- AN instance of AccessToken is returned if no
     * @param loginRequest
     * @param reverberator
     */
    public void login(LoginRequest loginRequest, Reverberator reverberator){
        Call<AccessToken> accessTokenCall = this.mRetrofitApiInterface.login(loginRequest);
        this.enque(accessTokenCall, reverberator);
    }

    public void getOrCreateAttendanceToken(String authorizationToken, Reverberator reverberator){
        Call<AttendanceToken> attendanceTokenCall = this.mRetrofitApiInterface.getOrCreateAttendanceToken(authorizationToken);
        this.enque(attendanceTokenCall, reverberator);
    }

    public void getTeacherLectures(String authorizationToken, Reverberator reverberator){
        Call<List<Lecture>> listCall = this.mRetrofitApiInterface.getLecturesForTeacher(authorizationToken);
        this.enque(listCall, reverberator);
    }

    public void getStudentLectures(String authorizationToken, Reverberator reverberator){
        Call<List<Lecture>> listCall = this.mRetrofitApiInterface.getLecturesForStudent(authorizationToken);
        this.enque(listCall, reverberator);
    }


    public void bulkInsertAttendance(String authorizationToken, AttendanceBulkInsert bulkInsert, Reverberator reverberator){
        Call<String> stringCall = this.mRetrofitApiInterface.bulkInsertAttendance(authorizationToken, bulkInsert.getLectureId(), bulkInsert);
        this.enque(stringCall, reverberator);
    }

    public void getStudentInformationFromAttendanceToken(String authorizationToken, String attendanceToken, Reverberator reverberator){
        Call<User> userCall = this.mRetrofitApiInterface.getStudentByAttendanceToken(authorizationToken, attendanceToken);
        this.enque(userCall, reverberator);
    }


    public void getStudentAttendance(String authToken, Long studentLectureId, Reverberator reverberator){
        Call<List<AttendancePercent>> listCall = this.mRetrofitApiInterface.getAttendanceForStudentByStudentLectureId(authToken, studentLectureId);
        this.enque(listCall, reverberator);
    }

    public void getAttendanceForTeacherByTeacherLectureId(String authToken, Long teacherLectureId, Reverberator reverberator){
        Call<List<AttendancePercent>> listCall = this.mRetrofitApiInterface.getAttendanceForTeacherByTeacherLectureId(authToken, teacherLectureId);
        this.enque(listCall, reverberator);
    }

    public void downloadReport(String authToken, AttendanceReportRequest attendanceReportRequest, Reverberator reverberator){
        Call<ResponseBody> responseBodyCall = this.mRetrofitApiInterface.downloadReportExcel(authToken, attendanceReportRequest);
        this.enque(responseBodyCall, reverberator);
    }

}