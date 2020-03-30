package com.rohan.attendo.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.rohan.attendo.MainActivity;
import com.rohan.attendo.R;
import com.rohan.attendo.api.chirp.ChirpWrapper;

public class TeacherAttendanceFragment extends Fragment {

    private static MainActivity mRef;
    private Button start;
    private ChirpWrapper chirpWrapper;
    private boolean isOn;

    public static TeacherAttendanceFragment getInstance(MainActivity activity){
        mRef = mRef == null? activity: mRef;
        TeacherAttendanceFragment attendanceFragment = new TeacherAttendanceFragment();
        return attendanceFragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.teacher_fragment, container, true);
        this.chirpWrapper = ChirpWrapper.getInstance();
        start = view.findViewById(R.id.startListening);
        start.setOnClickListener((View v)->{
            this.isOn = !isOn;
            if(this.isOn){
                this.chirpWrapper.getChirpSDK().start();
            }else{
                this.chirpWrapper.getChirpSDK().stop();
            }
        });
        return view;
    }
}
