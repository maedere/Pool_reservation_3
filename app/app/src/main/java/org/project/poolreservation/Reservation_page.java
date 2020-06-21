package org.project.poolreservation;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;
import org.project.poolreservation.activities.DayOfWeekActivity;
import org.project.poolreservation.activities.Owner_Register_Page_First;
import org.project.poolreservation.activities.Owner_visit_sansActivity;
import org.project.poolreservation.activities.Singup_Activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Reservation_page extends AppCompatActivity {

    Button button;
    String sectionId;
    String token;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reservation_page);
        sectionId=getIntent().getStringExtra("sectionId");
        token=getIntent().getStringExtra("token");
        button=findViewById(R.id.button6);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendJsonToserver();
            }
        });

    }

    private void sendJsonToserver(){
        String url = "http://waterphone.ir/";

//create post data as JSONObject - if your are using JSONArrayRequest use obviously an JSONArray :)
        JSONObject jsonBody = null;


        try {

            jsonBody = new JSONObject("{\"name\" " +
                    ": \"reserveSection\"," +
                    "\"param\" : {" +
                    "\"section_id\"" +
                    " :" +
                    Integer.valueOf(sectionId) +
                    "}}");
        } catch (JSONException e) {
            e.printStackTrace();
        }

//request a json object response
        JsonObjectRequest jsonRequest = new JsonObjectRequest(com.android.volley.Request.Method.POST, url, jsonBody, new com.android.volley.Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                //now handle the response
                Toast.makeText(Reservation_page.this, response.toString(), Toast.LENGTH_SHORT).show();
                System.out.println("------------------------------------------");
                System.out.println(response.toString());
                String date="";
                if(response.toString().contains("\"status\":200")) {
                    Intent intent=new Intent(Reservation_page.this,Customer_Reserved_Page.class);
                    intent.putExtra("token",token);
                    startActivity(intent);
                                  }
            }
        }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                //handle the error
                Toast.makeText(Reservation_page.this, "An error occurred", Toast.LENGTH_SHORT).show();
                error.printStackTrace();

            }
        }) {    //this is the part, that adds the header to the request
            @Override
            public Map<String, String> getHeaders() {
                System.out.println("------token:---------"+token);
                Map<String, String> params = new HashMap<String, String>();
                params.put("content-type", "application/json");
                params.put("cache-control", "no-cache");
                params.put("authorization","Bearer "+token);


                return params;
            }
        };
        jsonRequest.setRetryPolicy(new DefaultRetryPolicy(
                0,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
// Add the request to the queue
        Volley.newRequestQueue(this).add(jsonRequest);
    }

}
