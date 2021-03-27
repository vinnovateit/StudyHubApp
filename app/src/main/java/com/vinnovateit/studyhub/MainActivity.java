package com.vinnovateit.studyhub;
import android.os.Bundle;


import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;


import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.snackbar.Snackbar;
import android.app.ProgressDialog;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.TextView;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ListView;
import java.util.ArrayList;
import java.util.Arrays;

import android.widget.ArrayAdapter;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button b=findViewById(R.id.button);
        TextView t=findViewById(R.id.textView3);
        ArrayList<String> list1= new ArrayList<String>();
        String[] branches = new String[10];
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                t.setText("");
                String url="https://studyhubvinapi.herokuapp.com/posts";
                ArrayList<String> list1 = new ArrayList<String>();
                StringRequest stringRequest=new StringRequest(url, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try{
                            JSONArray mJsonArray = new JSONArray(response);
                            JSONObject mJsonObject = mJsonArray.getJSONObject(0);
//                            JSONObject mJsonObject2 = mJsonArray.getJSONObject(1);
//                            JSONObject mJsonObject3 = mJsonArray.getJSONObject(2);
                            JSONArray mJsonArrayProperty1 = mJsonObject.getJSONArray("branch");
                            for (int i = 0; i < mJsonArrayProperty1.length(); i++) {
                                String branch = mJsonArrayProperty1.getString(i);
                                list1.add(branch);
                                Log.i("branch: ",branch);

                            }
                            t.setText(list1.toString());
                            JSONArray mJsonArrayProperty = mJsonObject.getJSONArray("cse");
                            for (int i = 0; i < mJsonArrayProperty.length(); i++) {
                                JSONObject mJsonObjectProperty = mJsonArrayProperty.getJSONObject(i);
                                String coursename = mJsonObjectProperty.getString("coursename");
                                String courseid = mJsonObjectProperty.getString("courseid");
                                Log.i("Coursename: ",coursename);
                                Log.i("Courseid: ",courseid);

                            }

                        }catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                },new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(MainActivity.this,"Error", Toast.LENGTH_SHORT).show();
                    }
                });
                RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
                requestQueue.add(stringRequest);
            }
        });

    }

}