package com.vinnovateit.studyhub;


import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Parcelable;
import android.util.Log;
import android.util.TypedValue;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import com.vinnovateit.studyhub.adapter.SubjectAdapter;

import com.vinnovateit.studyhub.model.Diag;
import com.vinnovateit.studyhub.model.Subject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class SubjectFragment extends Fragment {
    String branchName;
    Integer subjectPos, arraySize;
    String courseApi;


    RecyclerView subjectRecycler;
    SubjectAdapter subjectAdapter;
    List<Subject> subjectList;

    @Override
    public void onPause()
    {
        super.onPause();
    }

    @Override
    public void onResume()
    {
        super.onResume();
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
                    Log.i("Connection", "Success !");
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
    public static boolean CheckInternet(Context context) {
        ConnectivityManager connec = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        android.net.NetworkInfo wifi = connec.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        android.net.NetworkInfo mobile = connec.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);

        return wifi.isConnected() || mobile.isConnected();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_subject,
                container, false);
        if(isURLReachable(getContext())) {
            TextView t = view.findViewById(R.id.textView5);
            TextView subjectName = view.findViewById(R.id.subjectHeader);

            view.setFocusableInTouchMode(true);
            view.requestFocus();
            view.setOnKeyListener(new View.OnKeyListener() {
                @Override
                public boolean onKey(View view, int keyCode, KeyEvent keyEvent) {
                    if (keyCode == KeyEvent.KEYCODE_BACK && keyEvent.getAction() == KeyEvent.ACTION_UP) {
                        if (CheckInternet(view.getContext())) {
                            ConstraintLayout layout = getActivity().findViewById(R.id.progress);
                            layout.setVisibility(View.VISIBLE);
                            Navigation.findNavController(requireView()).navigateUp();
                            return true;
                        } else {
                            Navigation.findNavController(requireView()).navigate(R.id.action_subjectFragment_to_internet);
                            return true;
                        }
                    }
                    return false;
                }
            });


            Bundle bundle = this.getArguments();
            assert bundle != null;
            String descUrl = bundle.getString("detailsURL");
            String subHead = bundle.getString("subjectHeader");

            String subDet = bundle.getString("subjectDetails");
            String[] words = subDet.split("\\s");

            String courseCode = words[2].toLowerCase();

            subjectName.setText(subHead + " " + words[2]);

            //branchName = bundle.getString("branchName");
            //subjectPos = bundle.getInt("subjectPosition");
            //arraySize = bundle.getInt("arraySize");

            ArrayList<String> moduleNumber = new ArrayList<String>();
            ArrayList<String> moduleDesc = new ArrayList<String>();

            //This is your link to be parsed.
            courseApi = "https://studiesguide.herokuapp.com/courses/studyhubapp/" + courseCode;
            StringRequest stringRequest = new StringRequest(courseApi, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    try {
                        //   final ProgressDialog dialog= CoursesFragment.DialogUtils.showProgressDialog(getActivity(),"Loading...");
                        JSONObject j1 = new JSONObject(response);
                        JSONArray j2 = j1.getJSONArray("courses");
                        //   for(int j=0;j<9;j++) {
                        // System.out.println("SUBJECT :"+Integer.toString(j));
                        JSONObject mJsonObject = j2.getJSONObject(0);
                        String oneObjectsItem1 = mJsonObject.getString("name");
                        System.out.println("Course Name:" + oneObjectsItem1);

                        JSONArray mJsonArrayProperty1 = mJsonObject.getJSONArray("modules");
                        JSONArray mJsonArrayProperty2 = mJsonObject.getJSONArray("das");
                        JSONArray mJsonArrayProperty3 = mJsonObject.getJSONArray("pdfs");

                           for (int i = 0; i < mJsonArrayProperty1.length(); i++) {
                               JSONObject oneObject = mJsonArrayProperty1.getJSONObject(i);
                               String modno = oneObject.getString("num");
                               String oneObjectsItem = oneObject.getString("markdown");
                               String NewString = oneObjectsItem.replace("*", "");
                               if(!NewString.equals("")) {
                                   if (!modno.equals("")) {
                                       System.out.println("MODULE:" + modno);
                                       moduleNumber.add("MODULE " + modno);
                                   }
                                   String NewString1 = NewString.replace("<br>", "");
                                   moduleDesc.add(NewString1);

                               }
                               //  System.out.println("\n");


                       }
                        if(mJsonArrayProperty3.length()>0)
                        {
                            String p="";
                            moduleNumber.add("Materials");
                            for(int i=0;i<mJsonArrayProperty3.length();i++)
                            {
                                JSONObject oneObject2 = mJsonArrayProperty3.getJSONObject(i);
                                String nameda = oneObject2.getString("name");
                                String dlk = oneObject2.getString("link");
                                p=p+"\n"+nameda+"\n"+dlk;

                            }
                            moduleDesc.add(p);
                        }
                        if(mJsonArrayProperty2.length()>0) {
                            String s="";
                            moduleNumber.add("Digital Assignments");
                            for (int i = 0; i < mJsonArrayProperty2.length(); i++) {
                                JSONObject oneObject1 = mJsonArrayProperty2.getJSONObject(i);
                                String name = oneObject1.getString("name");
                                String dalink = oneObject1.getString("link");
                                Log.i("Fac name:", name);
                                Log.i("DA LINK", dalink);
                                s=s+"\n"+name+"\n"+dalink;
                            }
                            moduleDesc.add(s);
                        }
                        ConstraintLayout layout = getActivity().findViewById(R.id.progress);
                        layout.setVisibility(View.GONE);
                        //Diag.removeSimpleProgressDialog();

                    }catch (JSONException e) {
                        e.printStackTrace();
                    }

                    subjectRecycler = view.findViewById(R.id.subject_recycler);
                    subjectList = new ArrayList<>();

                    for (int i = 0; i < moduleNumber.size(); i++) {
                        subjectList.add(new Subject(moduleNumber.get(i), moduleDesc.get(i)));
                    }
                    setSubjectRecycler(subjectList);

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.i("branch: ", "error");
                }
            });
            RequestQueue requestQueue = Volley.newRequestQueue(view.getContext());
            requestQueue.add(stringRequest);

            try {
                //String uri = "https://studyhub.vinnovateit.com/courses/603f82d34b48f40004358e53";
                String uri = descUrl;
                Document doc = (Document) Jsoup.connect(uri).get();
                Elements data = doc.select("div.note");
                Elements p = data.select("p");

                CardView c = view.findViewById(R.id.notescard);
                String d = "";
                // t.setTypeface(null, Typeface.NORMAL);
                t.setTextSize(TypedValue.COMPLEX_UNIT_PX,
                        getResources().getDimension(R.dimen.content));
                for (Element x : p) {
                    d += d + x.text();
                    if (!x.text().equals("")) {
                        t.setText(t.getText() + x.text().trim());
                    }
                    t.setText(t.getText() + "\n" + x.select("a").attr("abs:href").trim() + "\n");
                }
                if (d.equals("")) {
                    c.setVisibility(View.GONE);
                }

            }catch (Exception e) {
                e.printStackTrace();
            }
        }
        return view;
    }

    private void setSubjectRecycler(List<Subject> subjectList) {
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
        subjectRecycler.setLayoutManager(layoutManager);
        subjectRecycler.setHasFixedSize(true);
        subjectRecycler.setNestedScrollingEnabled(false);
        subjectAdapter = new SubjectAdapter(requireContext(), subjectList);
        subjectRecycler.setAdapter(subjectAdapter);
    }
}