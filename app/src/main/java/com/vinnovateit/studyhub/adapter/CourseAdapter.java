package com.vinnovateit.studyhub.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.vinnovateit.studyhub.R;
import com.vinnovateit.studyhub.model.Course;

import java.util.List;

public class CourseAdapter extends RecyclerView.Adapter<CourseAdapter.CourseViewHolder> {
    Context context;
    List<Course> courseList;

    public CourseAdapter(Context context, List<Course> courseList) {
        this.context = context;
        this.courseList = courseList;
    }

    @NonNull
    @Override
    public CourseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.courses, parent, false);
        return new CourseViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CourseAdapter.CourseViewHolder holder, int position) {
        holder.name.setText(courseList.get(position).getHeader());
        holder.details.setText(courseList.get(position).getDetails());
    }

    @Override
    public int getItemCount() {
        return courseList.size();
    }
    public static final class CourseViewHolder extends RecyclerView.ViewHolder {

        TextView name,details;

        public CourseViewHolder(@NonNull View itemView) {
            super(itemView);
            name=itemView.findViewById(R.id.subjectName);
            details=itemView.findViewById(R.id.subjectDetails);
        }
    }
}
