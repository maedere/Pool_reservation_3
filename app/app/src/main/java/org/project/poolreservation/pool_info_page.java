package org.project.poolreservation;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;
import org.project.poolreservation.activities.DayOfWeekActivity;
import org.project.poolreservation.activities.Pool_Register_Page;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class pool_info_page extends AppCompatActivity {
    String poolName="";
    String poolPhone="";
    String poolAddress="";
    String poolInfo="";
    String poolRate="";
    String poolCapacity="";
    String poolId="";
    String token="";
    RatingBar ratingBar;
    TextView pool_Name;
    TextView about;

    ArrayList<String> dateArray=new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pool_info_page);
        ratingBar=findViewById(R.id.ratingBar);
        pool_Name=findViewById(R.id.name_tv);
        about=findViewById(R.id.about);


        poolName=getIntent().getStringExtra("poolName");
        poolPhone=getIntent().getStringExtra("poolPhone");
        poolAddress=getIntent().getStringExtra("poolAddress");
        poolInfo=getIntent().getStringExtra("poolInfo");
        poolRate=getIntent().getStringExtra("poolRate");
        poolCapacity=getIntent().getStringExtra("poolCapacity");
        poolId=getIntent().getStringExtra("poolId");
        token=getIntent().getStringExtra("token");



        pool_Name.setText(poolName);
        if(!poolRate.equals("null"))
            ratingBar.setRating(Float.valueOf(poolRate));
        poolAddress=poolAddress.replaceAll("-"," ");
        poolInfo=poolInfo.replaceAll("-"," ");
        String about_string="آدرس: "+poolAddress+"\n"+"درباره: "+poolInfo+"\n"+"ظرفیت: "+poolCapacity+"\n"+"شماره تلفن: "+poolPhone;
        about.setText(about_string);

        Button btn=findViewById(R.id.btn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                sendJsonToserver_DayOfWeek();
            }
        });
    }
    private void sendJsonToserver_DayOfWeek(){
        String url = "http://waterphone.ir/";

//create post data as JSONObject - if your are using JSONArrayRequest use obviously an JSONArray :)
        JSONObject jsonBody = null;
        try {

            jsonBody = new JSONObject("{\"name\" " +
                    ": \"getRemainingDays\"," +
                    "\"param\" : {" +
                    "\"name\"" +
                    " :" +
                    null +
                    "}}");
        } catch (JSONException e) {
            e.printStackTrace();
        }

//request a json object response
        JsonObjectRequest jsonRequest = new JsonObjectRequest(com.android.volley.Request.Method.POST, url, jsonBody, new com.android.volley.Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                //now handle the response
                Toast.makeText(pool_info_page.this, response.toString(), Toast.LENGTH_SHORT).show();
                System.out.println("------------------------------------------");

                System.out.println(response.toString());

                ArrayList<String> itemArray=new ArrayList<>();
                ArrayList<String> dateArray=new ArrayList<>();
                ArrayList<String> dayArray=new ArrayList<>();

                if(response.toString().contains("200"))
                {
                    int start_array_index=response.toString().indexOf("[")+1;
                    String poolItem="";
                    if(response.toString().charAt(start_array_index)!=']') {
                        while (true) {
                            poolItem += response.toString().charAt(start_array_index);
                            if (response.toString().charAt(start_array_index) == '}') {
                                itemArray.add(poolItem);
                                System.out.println(poolItem);
                                poolItem = "";
                                start_array_index++;
                                if (response.toString().charAt(start_array_index) == ']')
                                    break;
                            }
                            start_array_index++;

                        }
                        System.out.println(itemArray+"llllllllllll");

                    }


                    for(int i=0;i<itemArray.size();i++)
                    {
                        try {
                            JSONObject jsonObject=new JSONObject(itemArray.get(i));

                            dateArray.add(jsonObject.getString("date"));
                            dayArray.add(jsonObject.getString("day"));

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                    System.out.println("&&&&&&&&&&&&&&&&"+dateArray);
                    Intent intent=new Intent(pool_info_page.this,DayOfWeekActivity.class);
                    intent.putExtra("token",token);
                    intent.putExtra("poolId",poolId);
                    intent.putStringArrayListExtra("dateArray",dateArray);
                    intent.putStringArrayListExtra("dayArray",dayArray);
                    startActivity(intent);

                }

            }
        }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                //handle the error
                Toast.makeText(pool_info_page.this, "An error occurred", Toast.LENGTH_SHORT).show();
                error.printStackTrace();

            }
        }) {    //this is the part, that adds the header to the request
            @Override
            public Map<String, String> getHeaders() {
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
