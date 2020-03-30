package com.rohan.attendo.ui.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.rohan.attendo.R;
import com.rohan.attendo.api.models.response.Lecture;

import java.util.List;

public class LectureAdapter extends RecyclerView.Adapter<LectureAdapter.LectureListViewHolder> {
    private List<Lecture> mLectureList;
    private LayoutInflater mLayoutInflater;

    public LectureAdapter(Context context, List<Lecture> lectureList){
        this.mLayoutInflater= LayoutInflater.from(context);
        this.mLectureList = lectureList;

    }

    @NonNull
    @Override
    public LectureListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = this.mLayoutInflater.inflate(R.layout.lecture_row, parent, false);
        return new LectureListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull LectureListViewHolder holder, int position) {
        Lecture current = this.mLectureList.get(position);
        holder.mLectureName.setText(current.getLectureName());
    }

    @Override
    public int getItemCount() {
        return this.mLectureList.size();
    }

    public static class LectureListViewHolder extends RecyclerView.ViewHolder{
        TextView mLectureName;

        public LectureListViewHolder(View view){
            super(view);
            this.mLectureName = view.findViewById(R.id.lectureName);
        }
    }
}

