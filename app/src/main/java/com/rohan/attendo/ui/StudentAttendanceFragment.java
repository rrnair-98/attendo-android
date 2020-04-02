package com.rohan.attendo.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.rohan.attendo.R;
import com.rohan.attendo.api.models.response.AttendancePercent;
import com.rohan.attendo.api.retrofit.RetrofitApiClient;
import com.rohan.attendo.helpers.TokenHelper;
import com.rohan.attendo.ui.adapters.StudentAttendanceAdapter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class StudentAttendanceFragment extends Fragment {

    private RecyclerView recyclerView;
    private RetrofitApiClient client;
    private TokenHelper tokenHelper;
    private List<AttendancePercent> attendancePercentList;
    private StudentAttendanceAdapter studentAttendanceAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.student_fragment, container, true);
        this.tokenHelper = TokenHelper.getInstance();
        this.client = RetrofitApiClient.getInstance();
        this.attendancePercentList = new ArrayList<>();
        this.recyclerView = view.findViewById(R.id.attendanceListRecycler);
        this.recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        SimpleDateFormat format = new SimpleDateFormat("yyyy-mm-dd");
        String startDate="", endDate="";
        try {
             startDate = format.parse("2020-01-01").toString();
             endDate = format.parse(new Date().toString()).toString();
        }catch (ParseException e){
            e.printStackTrace();
        }
        // TODO: following line gives syntax error
        this.client.getStudentAttendance(this.tokenHelper.getToken().getAccessToken(), 1L, (Object data, int httpResponse)->{
            if (httpResponse == 200){
                this.attendancePercentList = (List<AttendancePercent>)data;
                this.studentAttendanceAdapter.notifyDataSetChanged();
            }
        });
        this.studentAttendanceAdapter = new StudentAttendanceAdapter(view.getContext(), this.attendancePercentList);
        recyclerView.setAdapter(this.studentAttendanceAdapter);
        return view;
    }




}
