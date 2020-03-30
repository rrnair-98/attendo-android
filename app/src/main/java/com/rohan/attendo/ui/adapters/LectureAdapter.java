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
import com.rohan.attendo.ui.LectureListFragment;

import java.util.List;

public class LectureAdapter extends RecyclerView.Adapter<LectureAdapter.LectureListViewHolder> {
    private List<Lecture> mLectureList;
    private LayoutInflater mLayoutInflater;
    private LectureListFragment.LectureClickedListener mLectureClickedListener;

    public LectureAdapter(Context context, List<Lecture> lectureList,
      LectureListFragment.LectureClickedListener lectureClickedListener){
        this.mLayoutInflater= LayoutInflater.from(context);
        this.mLectureList = lectureList;
        this.mLectureClickedListener = lectureClickedListener;
    }

    @NonNull
    @Override
    public LectureListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = this.mLayoutInflater.inflate(R.layout.lecture_row, parent, false);
        return new LectureListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull LectureListViewHolder holder, int position) {
        final Lecture current = this.mLectureList.get(position);
        holder.mLectureName.setText(current.getLectureName());
        holder.mLectureIcon.setText(current.getLectureName().substring(0,1));
        holder.mMainView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mLectureClickedListener.onLectureClicked(current);
            }
        });
    }

    @Override
    public int getItemCount() {
        return this.mLectureList.size();
    }

    public static class LectureListViewHolder extends RecyclerView.ViewHolder{
        View mMainView;
        TextView mLectureName;
        TextView mLectureIcon;

        public LectureListViewHolder(View view){
            super(view);
            mMainView = view;
            mLectureIcon = view.findViewById(R.id.lectureIcon);
            this.mLectureName = view.findViewById(R.id.lectureName);
        }
    }
}

