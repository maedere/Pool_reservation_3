package org.project.poolreservation.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.RelativeLayout;
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
import org.project.poolreservation.sans.Sans;
import org.project.poolreservation.sans.SansAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Owner_visit_sansActivity extends AppCompatActivity {
    List<Sans> sans;
    ListView listView;
    SansAdapter adapter;
    String token;
    ArrayList<String> id=new ArrayList<>();
    ArrayList<String> start_hour_arr=new ArrayList<>();
    ArrayList<String> end_hour_arr=new ArrayList<>();
    ArrayList<String> gender_arr=new ArrayList<>();
    ArrayList<String> reserved_num_arr=new ArrayList<>();
    TextView title_date_edittext,date_tv;
    String date="";
    RelativeLayout no_sans;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activityowner_visit_sans);
        title_date_edittext=findViewById(R.id.title_date);
        listView=findViewById(R.id.listView);
        no_sans=findViewById(R.id.no_sans);
        date_tv=findViewById(R.id.date_tv);
        sans=new ArrayList<>();
        id=getIntent().getStringArrayListExtra("id");
        start_hour_arr=getIntent().getStringArrayListExtra("start_hour");
        end_hour_arr=getIntent().getStringArrayListExtra("end_hour");
        gender_arr=getIntent().getStringArrayListExtra("gender");
        reserved_num_arr=getIntent().getStringArrayListExtra("reserved_number");
        date=getIntent().getStringExtra("date");
        token=getIntent().getStringExtra("token");

        if(!date.equals(""))
        title_date_edittext.setText(date);
        else {
            date_tv.setVisibility(View.INVISIBLE);
            no_sans.setVisibility(View.VISIBLE);
        }
        int size=id.size();
        for(int i=0;i<size;i++)
            //sendJsonToserver(id.get(i),size,i);
        {
            sans.add(new Sans(token,Integer.valueOf(id.get(i)),start_hour_arr.get(i)+" - "+end_hour_arr.get(i),gender_arr.get(i),Integer.valueOf(reserved_num_arr.get(i)),10000));
        }
        adapter=new SansAdapter(this,sans);
        listView.setAdapter(adapter);

    }



}


