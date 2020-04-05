package com.rohan.attendo.ui;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.rohan.attendo.R;
import com.rohan.attendo.api.models.response.Lecture;
import com.rohan.attendo.api.retrofit.RetrofitApiClient;
import com.rohan.attendo.api.retrofit.Reverberator;
import com.rohan.attendo.helpers.TokenHelper;
import com.rohan.attendo.ui.adapters.LectureAdapter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class LectureListFragment extends Fragment {

    private static final String TAG = "LectureListFrag";

    private RecyclerView recyclerView;
    private RetrofitApiClient client;
    private TokenHelper tokenHelper;
    private List<Lecture> mLectureList = new ArrayList<>();;
    private LectureAdapter mLectureAdapter;

    private LectureClickedListener mLectureClickedListener;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.lecture_list_fragment, container, false);
        Bundle args = getArguments();
        this.tokenHelper = TokenHelper.getInstance();
        this.client = RetrofitApiClient.getInstance();
        this.mLectureList= new ArrayList<>();
        this.recyclerView = view.findViewById(R.id.lectureRecyclerView);
        this.recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        populateLectureList(this.mLectureList);
        this.mLectureAdapter= new LectureAdapter(view.getContext(), this.mLectureList,
          mLectureClickedListener);
        recyclerView.setAdapter(this.mLectureAdapter);
        return view;
    }

    public void setLectureClickedListener(LectureClickedListener lectureClickedListener) {
        mLectureClickedListener = lectureClickedListener;
    }

    private void populateLectureList(List<Lecture> lectureList){
        for(int i = 0; i<30; i++){
            lectureList.add(new Lecture((long)i, "Lecture: "+i));
        }
        Reverberator lectureListReverberator = new Reverberator() {
            @Override
            public void reverb(Object data, int httpResponseCode) {
                if(httpResponseCode == 200){
                    lectureList.clear();
                    lectureList.addAll((ArrayList<Lecture>) data);
                    mLectureAdapter.notifyDataSetChanged();
                }
                else {
                    Log.d(TAG, "ERROR IN FETCHING LECTURE LIST");
                }
            }
        };
        if(this.tokenHelper.getToken().isStudent()){
            RetrofitApiClient.getInstance(getActivity()).getStudentLectures(
                    this.tokenHelper.getToken().getAccessToken(),
                    lectureListReverberator
            );
        }
        else if(this.tokenHelper.getToken().isTeacher()){
            RetrofitApiClient.getInstance(getActivity()).getTeacherLectures(
                    this.tokenHelper.getToken().getAccessToken(),
                    lectureListReverberator
            );
        }
    }

    public static interface LectureClickedListener{
        void onLectureClicked(Lecture lecture);
    }
}

