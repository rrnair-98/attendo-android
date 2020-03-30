package com.rohan.attendo.ui;

import android.os.Bundle;
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
import com.rohan.attendo.api.models.response.Lecture;
import com.rohan.attendo.api.retrofit.RetrofitApiClient;
import com.rohan.attendo.helpers.TokenHelper;


public class StudentAudioQrFragment extends Fragment {

    private Lecture mLecture;
    private TextView mLectureIcon;
    private TextView mLectureNameTextView;
    private Button mPresentSirButton;
    private TokenHelper tokenHelper;
    private RetrofitApiClient client;

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
            }
        });
        return view;
    }

    public void setLecture(Lecture lecture) {
        mLecture = lecture;
    }
}
