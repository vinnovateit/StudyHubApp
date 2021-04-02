package com.vinnovateit.studyhub;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.vinnovateit.studyhub.adapter.SearchAdapter;
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

public class SearchFragment extends Fragment implements SearchAdapter.OnSearchListener{

    RecyclerView coursesRecyler;
    SearchAdapter courseAdapter;
    List<Search> courseList;
    private EditText searchView;
    LinearLayout no_results;

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

    public SearchFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_search, container, false);
        if(isURLReachable(getContext())) {
            coursesRecyler=view.findViewById(R.id.home_recycler);
            no_results=view.findViewById(R.id.no_results);
            RecyclerView.LayoutManager layoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
            coursesRecyler.setLayoutManager(layoutManager);
            coursesRecyler.setHasFixedSize(true);
            searchView=(EditText) view.findViewById(R.id.searchText);
            Bundle bundle = this.getArguments();
            assert bundle != null;
            String searchText = bundle.getString("search");
          //  Log.i("searchText",searchText);
            searchView.setText(searchText);
            hideKeyboard();
            ConstraintLayout layout = getActivity().findViewById(R.id.progress);
            layout.setVisibility(View.VISIBLE);

            ArrayList<String> header = new ArrayList<String>();
            ArrayList<String> course = new ArrayList<String>();
            ArrayList<String> courseDetails = new ArrayList<String>();
            coursesRecyler.setVisibility(View.VISIBLE);
            no_results.setVisibility(View.GONE);
            String test = "https://studiesguide.herokuapp.com/courses/studyhubapp/" + searchText;
            StringRequest stringRequest = new StringRequest(test, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    try {

                        JSONObject j1 = new JSONObject(response);
                        JSONArray j2 = j1.getJSONArray("courses");
                        if (j2.length() > 0) {
                            for (int j = 0; j < j2.length(); j++) {

                                JSONObject mJsonObject = j2.getJSONObject(j);
                                String oneObjectsItem1 = mJsonObject.getString("name");
                                String oneObjectsItem2 = mJsonObject.getString("_id");
                                String oneObjectsItem3 = mJsonObject.getString("code");
                                String oneObjectsItem4 = mJsonObject.getString("credits");
//                              Log.i("id", oneObjectsItem2);
//                              Log.i("Course Name:", oneObjectsItem1);
                                header.add(camelCase(oneObjectsItem1));
                                courseDetails.add("https://studyhub.vinnovateit.com/courses/" + oneObjectsItem2);
                                JSONArray mJsonArrayProperty1 = mJsonObject.getJSONArray("modules");
                                Log.i("test", "Code - " + oneObjectsItem3 + "\n" + "Credits - " + oneObjectsItem4 + "\n" + "Modules - " + Integer.toString(mJsonArrayProperty1.length()));
                                String str = "Code - " + oneObjectsItem3 + "\n\n" + "Credits - " + oneObjectsItem4 + "\n\n" + "Modules - " + Integer.toString(mJsonArrayProperty1.length());
                                course.add(str);
//                                Log.i("details", header.toString());
//                                Log.i("details", courseDetails.toString());
//                                Log.i("details", course.toString());
                                //searchView.setText("");
                                layout.setVisibility(View.GONE);
                                courseList = new ArrayList<>();

                                for (int i = 0; i < course.size(); i++) {
                                    courseList.add(new Search(header.get(i), course.get(i), courseDetails.get(i), i));
                                }
                                setCourseRecycler(courseList);

                            }
                        } else {
                            layout.setVisibility(View.GONE);
                            coursesRecyler.setVisibility(View.GONE);
                            no_results.setVisibility(View.VISIBLE);
                            //Insert a pic here instead
                            //Toast.makeText(getContext(), "No course found", Toast.LENGTH_SHORT).show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.i("branch: ", "error");
                }
            });
            RequestQueue requestQueue = Volley.newRequestQueue(view.getContext());
            requestQueue.add(stringRequest);



            searchView.setImeOptions(EditorInfo.IME_ACTION_SEARCH);
            searchView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
                @Override
                public boolean onEditorAction(TextView textView, int actionId, KeyEvent keyEvent) {
                    if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                        String text = searchView.getText().toString();
                        if (text.equals("")) {
                            //Toast.makeText(getContext(), "Sorry", Toast.LENGTH_SHORT).show();
                            searchView.setError("Cannot be empty");
                        } else {
                            hideKeyboard();
                            ConstraintLayout layout = getActivity().findViewById(R.id.progress);
                            layout.setVisibility(View.VISIBLE);

                            ArrayList<String> header = new ArrayList<String>();
                            ArrayList<String> course = new ArrayList<String>();
                            ArrayList<String> courseDetails = new ArrayList<String>();
                            coursesRecyler.setVisibility(View.VISIBLE);
                            no_results.setVisibility(View.GONE);
                            String test = "https://studiesguide.herokuapp.com/courses/studyhubapp/" + text;
                            StringRequest stringRequest = new StringRequest(test, new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                    try {

                                        JSONObject j1 = new JSONObject(response);
                                        JSONArray j2 = j1.getJSONArray("courses");
                                        if (j2.length() > 0) {
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
//                                                Log.i("details", header.toString());
//                                                Log.i("details", courseDetails.toString());
//                                                Log.i("details", course.toString());
                                                //searchView.setText("");
                                                layout.setVisibility(View.GONE);

                                                courseList = new ArrayList<>();

                                                for (int i = 0; i < course.size(); i++) {
                                                    courseList.add(new Search(header.get(i), course.get(i), courseDetails.get(i), i));
                                                }
                                                setCourseRecycler(courseList);

                                            }
                                        } else {
                                            layout.setVisibility(View.GONE);
                                            coursesRecyler.setVisibility(View.GONE);
                                            no_results.setVisibility(View.VISIBLE);
                                            //Insert a pic here instead
                                            //Toast.makeText(getContext(), "No course found", Toast.LENGTH_SHORT).show();
                                        }
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
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
                        return true;
                    }
                    return false;
                }
            });
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
                Navigation.findNavController(requireView()).navigate(R.id.action_searchFragment_to_internet);
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
                Navigation.findNavController(requireView()).navigate(R.id.action_searchFragment_to_subjectFragment, bundle);
            }
        }
    }
}