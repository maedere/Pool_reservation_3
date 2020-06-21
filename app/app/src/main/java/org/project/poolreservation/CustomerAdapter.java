package org.project.poolreservation;


import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CustomerAdapter extends ArrayAdapter {
    private List<customer> customers;
    public CustomerAdapter(@NonNull Context context, @NonNull List<customer> customers) {
        super(context, R.layout.sans_item_list, customers);
        this.customers = customers;
    }

    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        final customer san= customers.get(position);
        ViewHolder holder;
        if(convertView== null){
            LayoutInflater inflater= (LayoutInflater) getContext()
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView=inflater.inflate(R.layout.customer_list_item,null,true);
            holder=new ViewHolder(convertView);
            convertView.setTag(holder);

        }else
            holder= (ViewHolder) convertView.getTag();
        holder.absent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendJsonToserver_absence(customers.get(position).sectionId,customers.get(position).customerId,customers.get(position).token);
            }
        });
        holder.present.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendJsonToserver_present(customers.get(position).sectionId,customers.get(position).customerId,customers.get(position).token);

            }
        });
        holder.fill(san);
        return convertView;
    }
    private class ViewHolder  {
        public TextView customer_name;
        public TextView customer_phonenumber;
        public TextView reservation_code;
        public Button present;
        public Button absent;
        public ViewHolder(View converview ){
            if(converview!= null) {
                customer_name = converview.findViewById(R.id.customer_name);
                customer_phonenumber = converview.findViewById(R.id.customer_phonenumber);
                reservation_code = converview.findViewById(R.id.reservation_code);
                present = converview.findViewById(R.id.present);
                absent = converview.findViewById(R.id.absent);
            }


        }

        public void fill(customer customer){
            if(customer_name !=null)
                customer_name.setText(customer.getTime());
            if(customer_phonenumber !=null)
                customer_phonenumber.setText(customer.getSex());
            if(reservation_code!=null)
                reservation_code.setText(String.valueOf(customer.getReserves()) );


        }


    }
    private void sendJsonToserver_absence(int sectionId, String customerId, final String token){
        String url = "http://waterphone.ir/";

//create post data as JSONObject - if your are using JSONArrayRequest use obviously an JSONArray :)
        JSONObject jsonBody = null;


        try {

            jsonBody = new JSONObject("{\"name\" " +
                    ": \"setAbsent\"," +
                    "\"param\" : {" +
                    "\"section_id\"" +
                    " :" +
                    sectionId +
                    "," +
                    "\"customer_id\" " +
                    ":" +
                    Integer.valueOf(customerId) +
                    "}}");
        } catch (JSONException e) {
            e.printStackTrace();
        }

//request a json object response
        JsonObjectRequest jsonRequest = new JsonObjectRequest(com.android.volley.Request.Method.POST, url, jsonBody, new com.android.volley.Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                //now handle the response
                Toast.makeText(getContext(), response.toString(), Toast.LENGTH_SHORT).show();
                System.out.println("*********************************************************");
                System.out.println(response.toString());
                ArrayList<String> customerArr=new ArrayList<>();




            }
        }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                //handle the error
                Toast.makeText(getContext(), "An error occurred", Toast.LENGTH_SHORT).show();
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
        Volley.newRequestQueue(getContext()).add(jsonRequest);
    }

    private void sendJsonToserver_present(int sectionId, String customerId, final String token){
        String url = "http://waterphone.ir/";

//create post data as JSONObject - if your are using JSONArrayRequest use obviously an JSONArray :)
        JSONObject jsonBody = null;


        try {

            jsonBody = new JSONObject("{\"name\" " +
                    ": \"setPresent\"," +
                    "\"param\" : {" +
                    "\"customer_id\" " +
                    ":" +
                    Integer.valueOf(customerId) +
                    "," +
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
                Toast.makeText(getContext(), response.toString(), Toast.LENGTH_SHORT).show();
                System.out.println("*********************************************************");
                System.out.println(response.toString());
                ArrayList<String> customerArr=new ArrayList<>();




            }
        }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                //handle the error
                Toast.makeText(getContext(), "An error occurred", Toast.LENGTH_SHORT).show();
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
        Volley.newRequestQueue(getContext()).add(jsonRequest);
    }








}
