package com.rohan.attendo.ui;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.rohan.attendo.R;
import com.rohan.attendo.api.chirp.ChirpWrapper;
import com.rohan.attendo.api.models.requests.AttendanceBulkInsert;
import com.rohan.attendo.api.models.response.AttendanceToken;
import com.rohan.attendo.api.models.response.Lecture;
import com.rohan.attendo.api.retrofit.RetrofitApiClient;
import com.rohan.attendo.api.retrofit.Reverberator;
import com.rohan.attendo.helpers.TokenHelper;
import com.rohan.attendo.services.WhistleBlower;

import java.util.HashSet;
import java.util.Set;

public class TeacherAudioQrFragment extends Fragment {
    private Lecture mLecture;
    private TextView mAttendanceCountTextView;
    private TextView mLectureNameTextView;
    private Button mPresentSirButton;
    private Button mFinish;
    private TokenHelper tokenHelper;
    private RetrofitApiClient client;
    private boolean mSendAttendanceToken;
    private boolean mAttendanceInProgress = false;
    private AttendanceBulkInsert mAttendanceBulkInsert;
    private ChirpWrapper mChirpWrapper;
    private AsyncTask<Object, String, Object> mRollCaller;

    private static String TAG = "StudentAudioQr";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.student_audio_qr_fragment, container, false);
        this.tokenHelper = TokenHelper.getInstance();
        this.client = RetrofitApiClient.getInstance();
        mAttendanceCountTextView = view.findViewById(R.id.lectureIcon);
        mAttendanceCountTextView.setText("0");
        mLectureNameTextView = view.findViewById(R.id.lectureName);
        mLectureNameTextView.setText(mLecture.getLectureName());
        mPresentSirButton = view.findViewById(R.id.qrSend);
        mFinish = view.findViewById(R.id.finish);
        mFinish.setVisibility(View.VISIBLE);
        mFinish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendAttendanceTokens();
            }
        });
        mPresentSirButton.setText("Take Attendance");
        mChirpWrapper = ChirpWrapper.getInstance(getActivity());
        mPresentSirButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAttendanceInProgress = !mAttendanceInProgress;
                mPresentSirButton.setText(mAttendanceInProgress ? "Pause" : "Resume");
                if(mAttendanceInProgress){
                    startRollCaller();
                }
                else {
                    stopRollCaller();
                }
            }
        });
        return view;
    }

    public void setLecture(Lecture lecture) {
        mLecture = lecture;
        mAttendanceBulkInsert = new AttendanceBulkInsert(mLecture.getId());
    }

    private void startRollCaller(){
        Toast.makeText(getContext(), "Taking Roll Calls", Toast.LENGTH_SHORT).show();
        mRollCaller = new AsyncTask<Object, String, Object>() {
            @Override
            protected Object doInBackground(Object... objects) {
                ChirpWrapper.ChirpDataReceiver chirpDataReceiver = new ChirpWrapper.ChirpDataReceiver() {
                    @Override
                    public void chirpDataReceived(String receivedData) {
                        publishProgress(receivedData);
                        mChirpWrapper.sendData("RECEIVED-"+receivedData);
                    }
                };
                mChirpWrapper.addChirpDataReceiver(chirpDataReceiver);
                while (!isCancelled()){
                    Log.d(TAG, "Listening..");
                    try{
                        Thread.sleep(1000);
                    }
                    catch(InterruptedException ie){}
                }
                mChirpWrapper.removeChirpDataReceiver(chirpDataReceiver);
                return null;
            }

            @Override
            protected void onProgressUpdate(String... receivedTokens) {
                Log.d(TAG, "Received = "+receivedTokens[0]);
                mAttendanceBulkInsert.getTokens().add(receivedTokens[0]);
                mAttendanceCountTextView.setText(mAttendanceBulkInsert.getTokens().size()+"");
            }
        };
        mRollCaller.execute();
    }

    private void sendAttendanceTokens(){
        stopRollCaller();
        RetrofitApiClient.getInstance().bulkInsertAttendance(
                TokenHelper.getInstance().getToken().getAccessToken(),
                mAttendanceBulkInsert,
                new Reverberator() {
                    @Override
                    public void reverb(Object data, int httpResponseCode) {
                        if(httpResponseCode == 200){
                            Log.d(TAG, mAttendanceBulkInsert.getTokens().size()+" Students marked present");
                        }
                        else {
                            Toast.makeText(getActivity(), "Error in marking attendance.", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private void stopRollCaller(){
        if(!mRollCaller.isCancelled()){
            Log.d(TAG, "stopRollCaller Called");
            mRollCaller.cancel(true);
        }
    }
}
