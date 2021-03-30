package com.vinnovateit.studyhub;

import android.content.Context;
import android.net.ConnectivityManager;
import android.os.Bundle;


import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;


import android.os.StrictMode;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.vinnovateit.studyhub.adapter.CourseAdapter;
import com.vinnovateit.studyhub.model.Course;
import com.vinnovateit.studyhub.model.Diag;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class CoursesFragment extends Fragment implements CourseAdapter.OnCourseListener {

    TextView name, details, branchHead;
    RecyclerView coursesRecyler;
    CourseAdapter courseAdapter;
    List<Course> courseList;
    Integer len;
    String subject;

    public static boolean CheckInternet(Context context) {
        ConnectivityManager connec = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        android.net.NetworkInfo wifi = connec.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        android.net.NetworkInfo mobile = connec.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);

        return wifi.isConnected() || mobile.isConnected();
    }
    @Override
    public void onPause(){
        super.onPause();
    }
    @Override
    public void onResume(){
        super.onResume();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_courses, container, false);
        name = view.findViewById(R.id.subjectName);
            details = view.findViewById(R.id.subjectDetails);
            branchHead = view.findViewById(R.id.branchName);
            Bundle bundle = this.getArguments();
            assert bundle != null;
            String branch = bundle.getString("branch");
            String branchName = bundle.getString("name");
            branchHead.setText("BRANCH - " + branchName);
            subject = bundle.getString("subject");

            String str = "";
            ArrayList<String> header = new ArrayList<String>();
            ArrayList<String> course = new ArrayList<String>();
            ArrayList<String> courseDetails = new ArrayList<String>();
            int SDK_INT = android.os.Build.VERSION.SDK_INT;
            if (SDK_INT > 8) {
                StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                        .permitAll().build();
                StrictMode.setThreadPolicy(policy);
                try {
                    //  final ProgressDialog dialog=DialogUtils.showProgressDialog(getActivity(),"Loading...");
                    String uri = "https://studiesguide.herokuapp.com" + branch;
                    Document doc = (Document) Jsoup.connect(uri).get();
                    Elements data = doc.select("div.row");
                    Elements h6 = data.select("h6");
                    Elements h4 = data.select("h4");
                    Elements a = data.select("a");

                    int i = 0;

                    for (Element x : h4) {
                        Log.i("course", x.text());
                        header.add(camelCase(x.text()));
                    }
                    for (Element x : h6) {
                        if (i % 3 == 0 && i != 0) {
                            course.add(str.replace("Course ", ""));
                            str = "";
                        }
                        str += x.text() + "\n\n";
                        i++;
                    }
                    course.add(str.replace("Course ", ""));
                    for (Element x : a) {
                        Log.i("link", x.select("a").attr("abs:href"));
                        String link = x.select("a").attr("abs:href");
                        courseDetails.add(link);
                    }

                    len = header.size();
                    Diag.removeSimpleProgressDialog();
                } catch (Exception e) {
                    Toast.makeText(view.getContext(), "Error", Toast.LENGTH_SHORT).show();
                }

            }

            coursesRecyler = view.findViewById(R.id.coursesRecyclerView);
            courseList = new ArrayList<>();

            for (int i = 0; i < len; i++) {
                courseList.add(new Course(header.get(i), course.get(i), courseDetails.get(i), i));
            }
            setCourseRecycler(courseList);


        return view;
    }


    private String camelCase(String text) {
        String s = text;
        String line = s.toLowerCase();
        String upper_case_line = "";
        Scanner lineScan = new Scanner(line);
        while (lineScan.hasNext()) {
            String word = lineScan.next();
            upper_case_line += Character.toUpperCase(word.charAt(0)) + word.substring(1) + " ";
        }

        return (upper_case_line.trim());
    }

    private void setCourseRecycler(List<Course> courseList) {
        RecyclerView.LayoutManager layoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        coursesRecyler.setLayoutManager(layoutManager);
        coursesRecyler.setHasFixedSize(true);
        courseAdapter = new CourseAdapter(requireContext(), courseList, this);
        coursesRecyler.setAdapter(courseAdapter);
    }

    @Override
    public void onCourseClick(int position) {
        if (!CheckInternet(getContext())) {
            Navigation.findNavController(requireView()).navigate(R.id.action_coursesFragment2_to_internet);
        }
        else {
            Diag.showSimpleProgressDialog(getContext(), "STUDY HUB", "Loading", false);
            Bundle bundle = new Bundle();
            bundle.putString("detailsURL", courseList.get(position).getDescUrl());
            bundle.putString("subjectHeader", courseList.get(position).getHeader());
            bundle.putString("subjectDetails", courseList.get(position).getDetails());
            bundle.putInt("subjectPosition", courseList.get(position).getPos());
            bundle.putString("branchName", subject);
            bundle.putInt("arraySize", len);
            Navigation.findNavController(requireView()).navigate(R.id.action_coursesFragment2_to_subjectFragment, bundle);
        }
    }
}