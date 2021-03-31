package com.vinnovateit.studyhub.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.vinnovateit.studyhub.R;
import com.vinnovateit.studyhub.model.Course;
import com.vinnovateit.studyhub.model.Search;

import java.util.List;

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.SearchViewHolder> {
    Context context;
    List<Search> courseList;
    private OnSearchListener mOnSearchListener;

    public SearchAdapter(Context context, List<Search> courseList, OnSearchListener mOnCourseListener) {
        this.context = context;
        this.courseList = courseList;
        this.mOnSearchListener = mOnCourseListener;
    }

    @NonNull
    @Override
    public SearchViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.courses, parent, false);
        return new SearchViewHolder(view,mOnSearchListener);
    }

    @Override
    public void onBindViewHolder(@NonNull SearchAdapter.SearchViewHolder holder, int position) {
        holder.name.setText(courseList.get(position).getHeader());
        holder.details.setText(courseList.get(position).getDetails());
    }

    @Override
    public int getItemCount() {
        return courseList.size();
    }
    public static final class SearchViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView name,details;
        CardView course_cv;
        OnSearchListener onSearchListener;

        public SearchViewHolder(@NonNull View itemView, OnSearchListener onCourseListener) {
            super(itemView);
            name=itemView.findViewById(R.id.subjectName);
            details=itemView.findViewById(R.id.subjectDetails);
            course_cv=itemView.findViewById(R.id.expandable);
            this.onSearchListener=onCourseListener;
            itemView.setOnClickListener(this);
        }
        @Override
        public void onClick(View v) {
            onSearchListener.onSearchClick(getAdapterPosition());
        }
    }
    public interface OnSearchListener{
        void onSearchClick(int position);
    }
}
