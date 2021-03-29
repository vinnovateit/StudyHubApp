package com.vinnovateit.studyhub;

import android.graphics.Typeface;
import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.vinnovateit.studyhub.adapter.CourseAdapter;
import com.vinnovateit.studyhub.adapter.SubjectAdapter;
import com.vinnovateit.studyhub.model.Course;
import com.vinnovateit.studyhub.model.Subject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

public class SubjectFragment extends Fragment {
    String branchName;
    Integer subjectPos,arraySize;
    String courseApi;

    RecyclerView subjectRecycler;
    SubjectAdapter subjectAdapter;
    List<Subject> subjectList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_subject,
                container, false);
        TextView t = view.findViewById(R.id.textView5);
        TextView subjectName = view.findViewById(R.id.subjectHeader);
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
                    JSONObject j1 = new JSONObject(response);
                    JSONArray j2 = j1.getJSONArray("courses");
                    //   for(int j=0;j<9;j++) {
                    // System.out.println("SUBJECT :"+Integer.toString(j));
                    JSONObject mJsonObject = j2.getJSONObject(0);
                    String oneObjectsItem1 = mJsonObject.getString("name");
                    System.out.println("Course Name:" + oneObjectsItem1);
                    JSONArray mJsonArrayProperty1 = mJsonObject.getJSONArray("modules");
                    for (int i = 0; i < mJsonArrayProperty1.length(); i++) {

                        JSONObject oneObject = mJsonArrayProperty1.getJSONObject(i);
                        String modno = oneObject.getString("num");
                        if (!modno.equals("")) {
                            System.out.println("MODULE:" + modno);
                            moduleNumber.add("MODULE " + modno);
                        }
                        String oneObjectsItem = oneObject.getString("markdown");
                        String NewString = oneObjectsItem.replace("*", "");
                        System.out.println(NewString);
                        moduleDesc.add(NewString);
                        //  System.out.println("\n");

                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }

                subjectRecycler=view.findViewById(R.id.subject_recycler);
                subjectList=new ArrayList<>();

                for(int i=0;i<moduleNumber.size();i++){
                    subjectList.add(new Subject(moduleNumber.get(i),moduleDesc.get(i)));
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

        } catch (Exception e) {
            e.printStackTrace();
        }
        return view;
    }
    private void setSubjectRecycler(List<Subject> subjectList){
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL,false);
        subjectRecycler.setLayoutManager(layoutManager);
        subjectRecycler.setHasFixedSize(true);
        //subjectRecycler.setNestedScrollingEnabled(false);
        subjectAdapter = new SubjectAdapter(requireContext(),subjectList);
        subjectRecycler.setAdapter(subjectAdapter);
    }
}