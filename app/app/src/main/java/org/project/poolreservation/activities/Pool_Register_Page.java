package org.project.poolreservation.activities;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;
import org.project.poolreservation.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class Pool_Register_Page extends AppCompatActivity {

    EditText poolname;
    EditText poolnumber;
    EditText capacity;
    EditText poolInfo;
    EditText addresspool;
    Button sbtbutton;
    public int selected_item=0;
    String token;
    Boolean next=false;
    String city="";

    TextView title;
    String state="";

    SharedPreferences sharedPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pool_register_page);
        getSupportActionBar().hide();
        token=getIntent().getStringExtra("token");
        city=getIntent().getStringExtra("city");
        state=getIntent().getStringExtra("state");

        sharedPreferences=getSharedPreferences("poolRegisterPagePref",MODE_PRIVATE);

    }

    private void init() {

        poolname = findViewById(R.id.editText);
        poolnumber = findViewById(R.id.editText2);
        capacity = findViewById(R.id.editText3);
        poolInfo = findViewById(R.id.editText4);
        addresspool = findViewById(R.id.editText5);
        sbtbutton = findViewById(R.id.button5);
        title = findViewById(R.id.title);

    }

    public void buttOnClick(View view) {
        init();
        if (view.getId() == sbtbutton.getId()) {
            String poolName = poolname.getText().toString();
            String poolNumber = poolnumber.getText().toString();
            String Capacity = capacity.getText().toString();
            String PoolInfo = poolInfo.getText().toString();
            String AddressPool = addresspool.getText().toString();
            if (isValidInput(poolName, poolNumber, Capacity, PoolInfo,AddressPool )) {
                Toast.makeText(this,"Valid Input.",Toast.LENGTH_SHORT).show();
                sendJsonToserver();
                showDialog(view);
            }
        }
    }

    private boolean isValidInput(String poolName, String poolNumber, String Capacity, String PoolInfo, String AddressPool) {
        if (poolName.length() < 2 ){
            Toast.makeText(this, "name should be at least 2 caracters", Toast.LENGTH_SHORT).show();
            poolname.requestFocus();
            return false;}

        if (poolNumber.length() != 11  ){
            Toast.makeText(this, "Wrong phone Number", Toast.LENGTH_SHORT).show();
            poolnumber.requestFocus();
            return false;}
        if (Capacity.length() == 0 ){
            Toast.makeText(this, "Please  enter the capacity", Toast.LENGTH_SHORT).show();
            capacity.requestFocus();
            return false;}
        if (PoolInfo.length() == 0){
            Toast.makeText(this, "\"please enter the poolInfo", Toast.LENGTH_SHORT).show();
            poolInfo.requestFocus();
            return false;}
        if(AddressPool.length()==0){
            Toast.makeText(this, "please enter the address", Toast.LENGTH_SHORT).show();
            addresspool.requestFocus();
            return false;}
        else
            return true;
        }

    public void showDialog(View view){
        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.setTitle("قصد انجام جه عملیاتی را دارید؟")
                .setCancelable(false)
                .setSingleChoiceItems(new String[]{"ایجاد سانس جدید", "مشاهده ی سانس های موجود"}, 0, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        selected_item=i;
                    }
                })
                .setPositiveButton("تایید", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if(selected_item==0)
                        {
                            Intent intent=new Intent(Pool_Register_Page.this,new_sans_Activity.class);
                            intent.putExtra("token",token);
                            startActivity(intent);
                        }
                        if(selected_item==1)
                        {
                            sendJsonToserver_DayOfWeek();

                        }


                    }
                });
        builder.show();

    }

    private void sendJsonToserver(){
        String url = "http://waterphone.ir/";

//create post data as JSONObject - if your are using JSONArrayRequest use obviously an JSONArray :)
        JSONObject jsonBody = null;
        try {
            String poolName =poolname.getText().toString();
            poolName=poolName.replaceAll(" ","-");
            System.out.println("=====poolname:=="+poolName);

            String poolNumber = poolnumber.getText().toString();
            System.out.println("=====poolNumber:=="+poolNumber);

            String Capacity = capacity.getText().toString();
            System.out.println("=====Capacity:=="+Capacity);

            String PoolInfo =poolInfo.getText().toString();
            PoolInfo=PoolInfo.replaceAll(" ","-");
            System.out.println("=====PoolInfo:=="+PoolInfo);

            String AddressPool = addresspool.getText().toString();
            AddressPool=AddressPool.replaceAll(" ","-");
            System.out.println("=====AddressPool:=="+AddressPool);

            city=city.replaceAll(" ","-");
            state=state.replaceAll(" ","-");
            jsonBody = new JSONObject("{\"name\" " +
                    ": \"addPool\"," +
                    "\"param\" : {" +
                    "\"name\"" +
                    " :" +
                    poolName  +
                    "," +
                    "\"state\" " +
                    ":" +
                    state +
                    "," +
                    "\"city\" " +
                    ":" +
                    city +
                    "," +
                    "\"phone\" " +
                    ":" +
                    poolNumber +
                    "," +
                    "\"capacity\" " +
                    ":" +
                    Capacity +
                    "," +
                    "\"address\" " +
                    ":" +
                    AddressPool +
                    "," +
                    "\"info\" " +
                    ":" +
                    PoolInfo +
                    "}}");
        } catch (JSONException e) {
            e.printStackTrace();
        }

//request a json object response
        JsonObjectRequest jsonRequest = new JsonObjectRequest(com.android.volley.Request.Method.POST, url, jsonBody, new com.android.volley.Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                //now handle the response
                Toast.makeText(Pool_Register_Page.this, response.toString(), Toast.LENGTH_SHORT).show();
                System.out.println("------------------------------------------");

                System.out.println(response.toString());
                if(response.toString().contains("200"))
                {
                    showDialog(findViewById(R.id.button5) );
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("poolStatue", "registered");
                    editor.apply();
                }
            }
        }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                //handle the error
                Toast.makeText(Pool_Register_Page.this, "An error occurred", Toast.LENGTH_SHORT).show();
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
                Toast.makeText(Pool_Register_Page.this, response.toString(), Toast.LENGTH_SHORT).show();
                System.out.println("------------------------------------------");

                System.out.println(response.toString());

                ArrayList<String> dateArray=new ArrayList<>();
                ArrayList<String> dayArray=new ArrayList<>();
                ArrayList<String> itemArray=new ArrayList<>();
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
                        System.out.println(itemArray);
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
                    System.out.println("&&&&&&&&&&&&&&&&"+dateArray+dayArray);
                    Intent intent=new Intent(Pool_Register_Page.this,DayOfWeekActivity.class);
                    intent.putExtra("token",token);
                    intent.putStringArrayListExtra("dateArray",dateArray);
                    intent.putStringArrayListExtra("dayArray",dayArray);
                    startActivity(intent);

                }

            }
        }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                //handle the error
                Toast.makeText(Pool_Register_Page.this, "An error occurred", Toast.LENGTH_SHORT).show();
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



