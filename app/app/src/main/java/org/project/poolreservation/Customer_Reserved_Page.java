package org.project.poolreservation;


import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;
import org.project.poolreservation.models.Reserved;
import org.project.poolreservation.recyclerviews.CustomerReservedAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Customer_Reserved_Page extends AppCompatActivity {

    TextView absenceNum;
    String token;
    RelativeLayout reservedSanaLayout,noSansLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_reserved_page);
        absenceNum=findViewById(R.id.abseceNum);
        token=getIntent().getStringExtra("token");

        reservedSanaLayout=findViewById(R.id.reserved_sans);
        noSansLayout=findViewById(R.id.no_reserved_sans);

        sendJsonToserver();

    }


    private void sendJsonToserver(){
        String url = "http://waterphone.ir/";

//create post data as JSONObject - if your are using JSONArrayRequest use obviously an JSONArray :)
        JSONObject jsonBody = null;


        try {

            jsonBody = new JSONObject("{\"name\" " +
                    ": \"getCustomerReservations\"," +
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
                Toast.makeText(Customer_Reserved_Page.this, response.toString(), Toast.LENGTH_SHORT).show();
                System.out.println("------------------------------------------");
                System.out.println(response.toString());
                String allowed_absence_remainig="";


                ArrayList<String> startHouArr=new ArrayList<>();
                ArrayList<String> endHourArr=new ArrayList<>();
                ArrayList<String> dayArr=new ArrayList<>();
                ArrayList<String> genderArr=new ArrayList<>();
                ArrayList<String> poolNameArr=new ArrayList<>();
                ArrayList<String> reserveCodeSansArr=new ArrayList<>();
                ArrayList<String> absenceModeArr=new ArrayList<>();
                ArrayList<String> dateArr=new ArrayList<>();



                ArrayList<String> reservedSansArr=new ArrayList<>();
                if(response.toString().contains("\"status\":200")) {
                    allowed_absence_remainig=" "+response.toString().charAt(62)+" ";
                    absenceNum.setText("تعداد غیبت مجاز:"+allowed_absence_remainig);
                    int start_array_index=response.toString().indexOf("[")+1;
                    String poolItem="";
                    if(response.toString().charAt(start_array_index)!=']') {
                        while (true) {
                            poolItem += response.toString().charAt(start_array_index);
                            if (response.toString().charAt(start_array_index) == '}') {
                                reservedSansArr.add(poolItem);
                                System.out.println(poolItem);
                                poolItem = "";
                                start_array_index++;
                                if (response.toString().charAt(start_array_index) == ']')
                                    break;
                            }
                            start_array_index++;

                        }
                        System.out.println(reservedSansArr);

                    }
                    for(int i=0;i<reservedSansArr.size();i++)
                    {
                        try {
                            JSONObject jsonObject=new JSONObject(reservedSansArr.get(i));

                                genderArr.add(jsonObject.getString("gender"));
                                dayArr.add(jsonObject.getString("day"));
                                startHouArr.add(jsonObject.getString("start_hour"));
                                endHourArr.add(jsonObject.getString("end_hour"));
                                poolNameArr.add(jsonObject.getString("pool_name"));
                                reserveCodeSansArr.add(jsonObject.getString("secret_code"));
                                if(jsonObject.getString("absence").equals("0"))
                                absenceModeArr.add("عدم حضور");
                                else
                                    absenceModeArr.add("حضور");

                                dateArr.add(jsonObject.getString("date"));

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    RecyclerView recy=findViewById(R.id.recyReserved);
                    recy.setLayoutManager(new LinearLayoutManager(Customer_Reserved_Page.this));
                    ArrayList<String> persianGenderArr=new ArrayList<>();
                    List<Reserved>list=new ArrayList<>();
                    for(int i=0;i<genderArr.size();i++) {

                        if(genderArr.get(i).equals("male"))
                            persianGenderArr.add("آقایان");
                        else
                            persianGenderArr.add("بانوان");
                        list.add(new Reserved(startHouArr.get(i).replace(":00", "") + " - " + endHourArr.get(i).replace(":00", ""),
                                dayArr.get(i), dateArr.get(i), persianGenderArr.get(i), poolNameArr.get(i), reserveCodeSansArr.get(i),absenceModeArr.get(i)));
                    }
                    if(genderArr.size()==0) {
                        reservedSanaLayout.setVisibility(View.INVISIBLE);
                        noSansLayout.setVisibility(View.VISIBLE);
                    }else
                    recy.setAdapter(new CustomerReservedAdapter(list,Customer_Reserved_Page.this));


                }
            }
        }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                //handle the error
                Toast.makeText(Customer_Reserved_Page.this, "An error occurred", Toast.LENGTH_SHORT).show();
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
