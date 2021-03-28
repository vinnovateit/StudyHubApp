package com.vinnovateit.studyhub;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.os.StrictMode;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.vinnovateit.studyhub.adapter.CourseAdapter;
import com.vinnovateit.studyhub.model.Course;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

public class CoursesFragment extends Fragment {

    TextView name,details,branchHead;
    RecyclerView coursesRecyler;
    CourseAdapter courseAdapter;
    List<Course> courseList;
    Integer len;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_courses, container, false);

        name=view.findViewById(R.id.subjectName);
        details=view.findViewById(R.id.subjectDetails);
        branchHead=view.findViewById(R.id.branchName);
        Bundle bundle = this.getArguments();
        assert bundle != null;
        String branch = bundle.getString("branch");
        String branchName=bundle.getString("name");
        branchHead.setText("BRANCH - "+branchName);

        StringBuilder str= new StringBuilder();
        ArrayList<String> header = new ArrayList<String>();
        ArrayList<StringBuilder> course = new ArrayList<StringBuilder>();
        ArrayList<String> courseDetails = new ArrayList<String>();
        int SDK_INT = android.os.Build.VERSION.SDK_INT;
        if (SDK_INT > 8)
        {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                    .permitAll().build();
            StrictMode.setThreadPolicy(policy);
            try {
                String uri="https://studiesguide.herokuapp.com"+branch;
                Document doc= (Document) Jsoup.connect(uri).get();
                Elements data=doc.select("div.row");
                Elements h6= data.select("h6");
                Elements h4= data.select("h4");
                Elements a= data.select("a");

                int i=0;

                for(Element x: h4){
                    Log.i("course",x.text());
                    header.add(x.text());
                }
                header.add("");
                for (Element x: h6) {
                    Log.i("details",x.text());
                    if(i%3==0 && i!=0){
                        course.add(str);
                        str.setLength(0);
                    }
                    str.append(x.text());
                    i++;
                    Log.i("i", String.valueOf(i));
                    str.append("\n");
                }
                course.add(str);
                for (Element x:a) {
                    Log.i("link", x.select("a").attr("abs:href"));
                    String link=x.select("a").attr("abs:href");
                    courseDetails.add(link);
                }
                courseDetails.add("");
                len=header.size();
                //branchHead.setText(header.get(16));
            }
            catch (Exception e) {
                e.printStackTrace();
            }

        }

        coursesRecyler=view.findViewById(R.id.coursesRecyclerView);
        courseList=new ArrayList<>();

        for(int i=0;i<len-1;i++){
            courseList.add(new Course(header.get(i),course.get(i).toString(),courseDetails.get(i)));
        }
        //courseList.add(new Course(header.get(len-1),course.get(len-1).toString(),courseDetails.get(len-1)));
        setCourseRecycler(courseList);
        return view;
    }

    private void setCourseRecycler(List<Course> courseList) {
        RecyclerView.LayoutManager layoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        coursesRecyler.setLayoutManager(layoutManager);
        coursesRecyler.setHasFixedSize(true);
        courseAdapter = new CourseAdapter(requireContext(),courseList);
        coursesRecyler.setAdapter(courseAdapter);
    }
}