package com.rohan.attendo.ui;

import android.content.Intent;
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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.rohan.attendo.R;
import com.rohan.attendo.api.chirp.ChirpWrapper;
import com.rohan.attendo.api.models.response.AccessToken;
import com.rohan.attendo.api.models.response.AttendanceToken;
import com.rohan.attendo.api.models.response.Lecture;
import com.rohan.attendo.api.retrofit.RetrofitApiClient;
import com.rohan.attendo.api.retrofit.Reverberator;
import com.rohan.attendo.helpers.TokenHelper;
import com.rohan.attendo.services.WhistleBlower;


public class StudentAudioQrFragment extends Fragment {

    private Lecture mLecture;
    private TextView mLectureIcon;
    private TextView mLectureNameTextView;
    private Button mPresentSirButton;
    private TokenHelper tokenHelper;
    private RetrofitApiClient client;
    private boolean mSendAttendanceToken;

    private static String TAG = "StudentAudioQr";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.student_audio_qr_fragment, container, false);
        this.tokenHelper = TokenHelper.getInstance();
        this.client = RetrofitApiClient.getInstance();
        // TODO:  add code to fetch present token
        mLectureIcon = view.findViewById(R.id.lectureIcon);
        mLectureIcon.setText(mLecture.getLectureName().substring(0,1));
        mLectureNameTextView = view.findViewById(R.id.lectureName);
        mLectureNameTextView.setText(mLecture.getLectureName());
        mPresentSirButton = view.findViewById(R.id.qrSend);
        mPresentSirButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "Present Sir!!!", Toast.LENGTH_SHORT).show();
                RetrofitApiClient.getInstance().getOrCreateAttendanceToken(
                        TokenHelper.getInstance().getToken().getAccessToken(),
                        new Reverberator() {
                            @Override
                            public void reverb(Object data, int httpResponseCode) {
                                if(httpResponseCode == 200){
                                    AttendanceToken attendanceToken = (AttendanceToken) data;
                                    startAttendanceTransmission(attendanceToken);
                                }
                                else {
                                    Log.e(TAG, httpResponseCode+" Failed to fetch attendance token");
                                }

                            }
                        }
                );
            }
        });
        return view;
    }

    public void setLecture(Lecture lecture) {
        mLecture = lecture;
    }

    private void startAttendanceTransmission(final AttendanceToken attendanceToken) {
        Intent intent = new Intent(getContext(), WhistleBlower.class);
        intent.setAction(WhistleBlower.ACTION_START_CHIRPING);
        getContext().startService(intent);
    }
}
