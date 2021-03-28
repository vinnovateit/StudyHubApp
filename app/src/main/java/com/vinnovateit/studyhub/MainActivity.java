package com.vinnovateit.studyhub;
import android.os.Build;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.os.StrictMode;
public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setNavigationBarColor(ContextCompat.getColor(this, R.color.white));
        }
        int SDK_INT = Build.VERSION.SDK_INT;
        if (SDK_INT > 8) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                    .permitAll().build();
            StrictMode.setThreadPolicy(policy);

        }


    }
//   public void ca()
//   {
//        String[] branches = new String[10];
////                t.setText("");
//        String url = "https://studyhubvinapi.herokuapp.com/posts";
//        ArrayList<String> list1 = new ArrayList<String>();
//        StringRequest stringRequest = new StringRequest(url, new Response.Listener<String>() {
//            @Override
//            public void onResponse(String response) {
//                try {
//                    JSONArray mJsonArray = new JSONArray(response);
//                    JSONObject mJsonObject = mJsonArray.getJSONObject(0);
////                            JSONObject mJsonObject2 = mJsonArray.getJSONObject(1);
////                            JSONObject mJsonObject3 = mJsonArray.getJSONObject(2);
//                    JSONArray mJsonArrayProperty1 = mJsonObject.getJSONArray("branch");
//                    for (int i = 0; i < mJsonArrayProperty1.length(); i++) {
//                        String branch = mJsonArrayProperty1.getString(i);
//                        list1.add(branch);
//                        Log.i("branch: ", branch);
//
//                    }
////                            t.setText(list1.toString());
//                    JSONArray mJsonArrayProperty = mJsonObject.getJSONArray("cse");
//                    for (int i = 0; i < mJsonArrayProperty.length(); i++) {
//                        JSONObject mJsonObjectProperty = mJsonArrayProperty.getJSONObject(i);
//                        String coursename = mJsonObjectProperty.getString("coursename");
//                        String courseid = mJsonObjectProperty.getString("courseid");
//                        Log.i("Coursename: ", coursename);
//                        Log.i("Courseid: ", courseid);
//
//                    }
//
//                }catch (JSONException e) {
//                    e.printStackTrace();
//                }
//
//
//            }
//        }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                Toast.makeText(MainActivity.this, "Error", Toast.LENGTH_SHORT).show();
//            }
//        });
//        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
//        requestQueue.add(stringRequest);
//
//
//    }

}