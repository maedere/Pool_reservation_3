package org.project.poolreservation;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;

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

public class Customer_List_Page extends Activity {
    List<customer> customers;
    ListView listView;
    CustomerAdapter adapter;
    String token;
    int sectionId;
    RelativeLayout noReservedLayout,reservedListLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_list_page);
        token=getIntent().getStringExtra("token");
        sectionId=Integer.valueOf(getIntent().getStringExtra("sectionId"));


        noReservedLayout=findViewById(R.id.no_reserve);
        reservedListLayout=findViewById(R.id.reserved_list);
        listView=findViewById(R.id.listView);
        customers =new ArrayList<>();

        sendJsonToserver();

    }

    private void sendJsonToserver(){
        String url = "http://waterphone.ir/";

//create post data as JSONObject - if your are using JSONArrayRequest use obviously an JSONArray :)
        JSONObject jsonBody = null;


        try {

            jsonBody = new JSONObject("{\"name\" " +
                    ": \"getCustomersList\"," +
                    "\"param\" : {" +
                    "\"section_id\"" +
                    " :" +
                    sectionId +
                    "}}");
        } catch (JSONException e) {
            e.printStackTrace();
        }

//request a json object response
        JsonObjectRequest jsonRequest = new JsonObjectRequest(com.android.volley.Request.Method.POST, url, jsonBody, new com.android.volley.Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                //now handle the response
                Toast.makeText(Customer_List_Page.this, response.toString(), Toast.LENGTH_SHORT).show();
                System.out.println("*********************************************************");
                System.out.println(response.toString());
                ArrayList<String> customerArr=new ArrayList<>();
                if(response.toString().contains("\"status\":200")) {
                    int start_array_index=response.toString().indexOf("[")+1;
                    String poolItem="";
                    if(response.toString().charAt(start_array_index)!=']') {
                        while (true) {
                            poolItem += response.toString().charAt(start_array_index);
                            if (response.toString().charAt(start_array_index) == '}') {
                                customerArr.add(poolItem);
                                System.out.println(poolItem);
                                poolItem = "";
                                start_array_index++;
                                if (response.toString().charAt(start_array_index) == ']')
                                    break;
                            }
                            start_array_index++;

                        }
                        System.out.println(customerArr);

                    }



                    for(int i=0;i<customerArr.size();i++)
                    {
                        try {
                            JSONObject jsonObject=new JSONObject(customerArr.get(i));


                            customers.add(new customer(token,jsonObject.getString("name") ,
                                    jsonObject.getString("mobile"),
                                    Integer.valueOf(jsonObject.getString("secret_code")),
                                    jsonObject.getString("customer_id"),sectionId));

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                    if(customerArr.size()==0)
                    {
                        noReservedLayout.setVisibility(View.VISIBLE);
                        reservedListLayout.setVisibility(View.INVISIBLE);
                    }


                    adapter=new CustomerAdapter(Customer_List_Page.this, customers);
                    listView.setAdapter(adapter);
                }




            }
        }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                //handle the error
                Toast.makeText(Customer_List_Page.this, "An error occurred", Toast.LENGTH_SHORT).show();
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
