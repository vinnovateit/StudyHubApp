package com.vinnovateit.studyhub;

import android.app.Activity;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;

import androidx.appcompat.widget.SearchView;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.os.StrictMode;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.vinnovateit.studyhub.adapter.CourseAdapter;
import com.vinnovateit.studyhub.adapter.SearchAdapter;
import com.vinnovateit.studyhub.model.Course;
import com.vinnovateit.studyhub.model.Diag;
import com.vinnovateit.studyhub.model.Search;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static androidx.core.content.ContextCompat.getSystemService;


public class BranchFragment extends Fragment implements SearchAdapter.OnSearchListener {
    CardView it, cse, uc;
    ImageView insta, github, twitter;
    RecyclerView coursesRecyler;
    SearchAdapter courseAdapter;
    List<Search> courseList;
    ImageButton search;
    EditText searchRes;
    private long pressedTime;
    public void hideKeyboard() {
        // Check if no view has focus:
        View view = getActivity().getCurrentFocus();
        if (view != null) {
            InputMethodManager inputManager = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
            inputManager.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }
    static public boolean isURLReachable(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        if (netInfo != null && netInfo.isConnected()) {
            try {
                URL url = new URL("https://studyhub.vinnovateit.com");
                HttpURLConnection urlc = (HttpURLConnection) url.openConnection();
                urlc.setConnectTimeout(10 * 1000);          // 10 s.
                urlc.connect();
                if (urlc.getResponseCode() == 200) {        // 200 = "OK" code (http connection is fine).
                    Log.wtf("Connection", "Success !");
                    return true;
                } else {
                    return false;
                }
            } catch (MalformedURLException e1) {
                return false;
            } catch (IOException e) {
                return false;
            }
        }
        return false;
    }
    public static boolean CheckInternet(Context context)
    {
        ConnectivityManager connec = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        android.net.NetworkInfo wifi = connec.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        android.net.NetworkInfo mobile = connec.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);

        return wifi.isConnected() || mobile.isConnected();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_branch,
                container, false);
        if(isURLReachable(getContext())) {

            it = view.findViewById(R.id.it);
            cse = view.findViewById(R.id.cse);
            uc = view.findViewById(R.id.uc);

            RelativeLayout relativeLayout = view.findViewById(R.id.home_layout);
            coursesRecyler=view.findViewById(R.id.home_recycler);

            search=view.findViewById(R.id.searchButton);
            searchRes=view.findViewById(R.id.searchView);

            RecyclerView.LayoutManager layoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
            coursesRecyler.setLayoutManager(layoutManager);
            coursesRecyler.setHasFixedSize(true);

            insta = view.findViewById(R.id.instagram);
            github = view.findViewById(R.id.github);
            twitter = view.findViewById(R.id.twitter);
            insta.setOnClickListener(v -> {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.instagram.com/vinnovateit/?hl=en"));
                startActivity(browserIntent);
            });
            twitter.setOnClickListener(v -> {

                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://twitter.com/v_innovate_it?lang=en"));
                startActivity(browserIntent);
            });
            github.setOnClickListener(v -> {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://github.com/vinnovateit"));
                startActivity(browserIntent);
            });
            it.setOnClickListener(view13 -> {
                if (CheckInternet(view.getContext())) {
                    ConstraintLayout layout = getActivity().findViewById(R.id.progress);
                    layout.setVisibility(View.VISIBLE);
                    //Diag.showSimpleProgressDialog(getContext(),"STUDY HUB","Loading",true);
                    Log.i("internet", "working");
                    Bundle bundle = new Bundle();
                    bundle.putString("branch", "/branch/it");
                    bundle.putString("name", "IT");
                    bundle.putString("subject", "it");
                    Navigation.findNavController(requireView()).navigate(R.id.action_branchFragment_to_coursesFragment2, bundle);
                } else {
                    Navigation.findNavController(requireView()).navigate(R.id.action_branchFragment_to_internet);
                }

            });
            cse.setOnClickListener(view12 -> {
                if (CheckInternet(view.getContext())) {
                    ConstraintLayout layout = getActivity().findViewById(R.id.progress);
                    layout.setVisibility(View.VISIBLE);
                    //Diag.showSimpleProgressDialog(getContext(),"STUDY HUB","Loading",true);
                    Bundle bundle = new Bundle();
                    bundle.putString("branch", "/branch/cse");
                    bundle.putString("name", "CSE");
                    bundle.putString("subject", "cse");
                    Navigation.findNavController(requireView()).navigate(R.id.action_branchFragment_to_coursesFragment2, bundle);
                } else {
                    Navigation.findNavController(requireView()).navigate(R.id.action_branchFragment_to_internet);
                }
            });
            uc.setOnClickListener(view1 -> {
                if (CheckInternet(view.getContext())) {
                    ConstraintLayout layout = getActivity().findViewById(R.id.progress);
                    layout.setVisibility(View.VISIBLE);
                    //Diag.showSimpleProgressDialog(getContext(),"STUDY HUB","Loading",true);
                    Bundle bundle = new Bundle();
                    bundle.putString("branch", "/branch/uc");
                    bundle.putString("name", "U.C.");
                    bundle.putString("subject", "uc");
                    Navigation.findNavController(requireView()).navigate(R.id.action_branchFragment_to_coursesFragment2, bundle);
                } else {
                    Navigation.findNavController(requireView()).navigate(R.id.action_branchFragment_to_internet);
                }
            });


            search.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    hideKeyboard();
                    String text=searchRes.getText().toString();
                    if(text.equals("")){
                        searchRes.setError("Cannot be Empty");
                        //Toast.makeText(getContext(),"Sorry",Toast.LENGTH_SHORT).show();
                    }
                    else{
                        ConstraintLayout layout = getActivity().findViewById(R.id.progress);
                        layout.setVisibility(View.VISIBLE);
                        ArrayList<String> header = new ArrayList<String>();
                        ArrayList<String> course = new ArrayList<String>();
                        ArrayList<String> courseDetails = new ArrayList<String>();
                        relativeLayout.setVisibility(View.GONE);
                        coursesRecyler.setVisibility(View.VISIBLE);
                        Log.i("text",text);
                        String test= "https://studiesguide.herokuapp.com/courses/studyhubapp/"+text;
                        Log.i("link",test);
                        StringRequest stringRequest = new StringRequest(test, new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                try {

                                    JSONObject j1 = new JSONObject(response);
                                    JSONArray j2 = j1.getJSONArray("courses");
                                    if (j2.length() > 0)
                                    {
                                        //   for(int j=0;j<9;j++) {
                                        // System.out.println("SUBJECT :"+Integer.toString(j));
                                        for (int j = 0; j < j2.length(); j++) {

                                            JSONObject mJsonObject = j2.getJSONObject(j);
                                            String oneObjectsItem1 = mJsonObject.getString("name");
                                            String oneObjectsItem2 = mJsonObject.getString("_id");
                                            String oneObjectsItem3 = mJsonObject.getString("code");
                                            String oneObjectsItem4 = mJsonObject.getString("credits");
                                            Log.i("id", oneObjectsItem2);
                                            Log.i("Course Name:", oneObjectsItem1);

                                            header.add(camelCase(oneObjectsItem1));
                                            courseDetails.add("https://studyhub.vinnovateit.com/courses/" + oneObjectsItem2);


                                            JSONArray mJsonArrayProperty1 = mJsonObject.getJSONArray("modules");
                                            //  Log.i("Modules:",Integer.toString(mJsonArrayProperty1.length()));
                                            Log.i("test", "Code - " + oneObjectsItem3 + "\n" + "Credits - " + oneObjectsItem4 + "\n" + "Modules - " + Integer.toString(mJsonArrayProperty1.length()));
                                            String str = "Code - " + oneObjectsItem3 + "\n\n" + "Credits - " + oneObjectsItem4 + "\n\n" + "Modules - " + Integer.toString(mJsonArrayProperty1.length());

                                            course.add(str);
                                            Log.i("details", header.toString());
                                            Log.i("details", courseDetails.toString());
                                            Log.i("details", course.toString());
                                            searchRes.setText("");
                                            layout.setVisibility(View.GONE);

                                        }
                                }
                                    else
                                    {
                                        layout.setVisibility(View.GONE);
                                        coursesRecyler.setVisibility(View.GONE);
                                        Toast.makeText(getContext(), "No course found", Toast.LENGTH_SHORT).show();
                                    }
                                }catch (JSONException e) {
                                    e.printStackTrace();
                                }
                                courseList = new ArrayList<>();

                                for (int i = 0; i < course.size(); i++) {
                                    courseList.add(new Search(header.get(i), course.get(i), courseDetails.get(i), i));
                                }
                                setCourseRecycler(courseList);
                            }
                        }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Log.i("branch: ", "error");
                            }
                        });
                        RequestQueue requestQueue = Volley.newRequestQueue(view.getContext());
                        requestQueue.add(stringRequest);
                    }
                    view.setFocusableInTouchMode(true);
                    view.requestFocus();
                    view.setOnKeyListener(new View.OnKeyListener() {
                        @Override
                        public boolean onKey(View view, int keyCode, KeyEvent keyEvent) {
                            if (keyCode == KeyEvent.KEYCODE_BACK && keyEvent.getAction() == KeyEvent.ACTION_UP) {
                                if (relativeLayout.getVisibility()==View.GONE) {
                                    relativeLayout.setVisibility(View.VISIBLE);
                                    coursesRecyler.setVisibility(View.GONE);
                                    return true;
                                }
                            }
                            return false;
                        }
                    });
                }
            });
            view.setFocusableInTouchMode(true);
            view.requestFocus();
            view.setOnKeyListener(new View.OnKeyListener() {
                @Override
                public boolean onKey(View view, int keyCode, KeyEvent keyEvent) {
                    if (keyCode == KeyEvent.KEYCODE_BACK && keyEvent.getAction() == KeyEvent.ACTION_UP) {
                        if (pressedTime + 4000 > System.currentTimeMillis()) {
                            getActivity().finish();
                            return true;
                        }
                        else{
                            Toast.makeText(getContext(),"Press back again to exit!",Toast.LENGTH_SHORT).show();
                            pressedTime=System.currentTimeMillis();
                            return true;
                        }
                    }
                    return false;
                }
            });
        }
        else
        {
            Toast.makeText(getContext(), "Server is currently down!", Toast.LENGTH_SHORT).show();
        }

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

    private void setCourseRecycler(List<Search> courseList) {
        courseAdapter = new SearchAdapter(requireContext(), courseList, this);
        coursesRecyler.setAdapter(courseAdapter);
    }
    @Override
    public void onSearchClick(int position) {
        if(isURLReachable(getContext()))
        {
            if (!CheckInternet(getContext())) {
                Navigation.findNavController(requireView()).navigate(R.id.action_coursesFragment2_to_internet);
            }
            else {
                ConstraintLayout layout = getActivity().findViewById(R.id.progress);
                layout.setVisibility(View.VISIBLE);
                //Diag.showSimpleProgressDialog(getContext(), "STUDY HUB", "Loading", false);
                Bundle bundle = new Bundle();
                bundle.putString("detailsURL", courseList.get(position).getDescUrl());
                bundle.putString("subjectHeader", courseList.get(position).getHeader());
                bundle.putString("subjectDetails", courseList.get(position).getDetails());
                bundle.putInt("subjectPosition", courseList.get(position).getPos());
                Navigation.findNavController(requireView()).navigate(R.id.action_branchFragment_to_subjectFragment, bundle);
            }
        }
    }

}