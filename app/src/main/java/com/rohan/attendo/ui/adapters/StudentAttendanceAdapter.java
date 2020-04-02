package com.rohan.attendo.ui.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.rohan.attendo.R;
import com.rohan.attendo.api.models.response.AttendancePercent;

import java.util.ArrayList;
import java.util.List;

public class StudentAttendanceAdapter extends RecyclerView.Adapter<StudentAttendanceAdapter.StudentAttendanceListViewHolder> {
    private List<AttendancePercent> attendancePercents;
    private LayoutInflater layoutInflater;

    public StudentAttendanceAdapter(Context context, List<AttendancePercent> attendancePercents){
        this.layoutInflater = LayoutInflater.from(context);
        this.attendancePercents = attendancePercents;

    }

    @NonNull
    @Override
    public StudentAttendanceListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = this.layoutInflater.inflate(R.layout.student_att_list_view_holder, parent, false);
        return new StudentAttendanceListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull StudentAttendanceListViewHolder holder, int position) {
        AttendancePercent current = this.attendancePercents.get(position);
        holder.percentage.setText(current.getPercentage().toString());
        holder.totalLectures.setText(current.getTotalLectures().toString());
        // TODO: Following lines give syntax error
        holder.lecturesSat.setText(current.getLecsAttended().toString());
        holder.lectureName.setText(current.getLectureName());
    }

    @Override
    public int getItemCount() {
        return this.attendancePercents.size();
    }

    public static class StudentAttendanceListViewHolder extends RecyclerView.ViewHolder{
        TextView lectureName, lecturesSat, totalLectures, percentage;

        public StudentAttendanceListViewHolder(View view){
            super(view);
            this.lectureName = view.findViewById(R.id.lectureName);
            this.lecturesSat = view.findViewById(R.id.lecturesSat);
            this.totalLectures = view.findViewById(R.id.totalLectures);
            this.percentage = view.findViewById(R.id.percent);
        }


    }
}
