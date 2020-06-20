package org.project.pool_reservation.activities;

import android.content.Intent;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import org.project.pool_reservation.R;

public class Singup_Activity extends AppCompatActivity {
    EditText editText;
    Button button;
    int randomnumber;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_singup_);
        editText = (EditText) findViewById(R.id.editText);
        button=(Button) findViewById(R.id.button2);
       // StrictMode.ThreadPolicy policy=new StrictMode.ThreadPolicy.Builder().permitAll().build();
       // StrictMode.setThreadPolicy(policy);


    }
    public void click(View view){
      /*  try {
            // Construct data
            String apiKey = "apikey=" + "edgwK2yyAX0-7jFNF0seG85rfszLe1uE2e49QgQDQP";
            Random random = new Random();
            randomnumber=random.nextInt(999999);
            String message = "&message=" + "Hey your OTP is "+ randomnumber;
            String sender = "&sender=" + "TSN";
            String numbers = "&numbers=" + editText.getText().toString();

            // Send data
            HttpURLConnection conn = (HttpURLConnection) new URL("https://api.txtlocal.com/send/?").openConnection();
            String data = apiKey + numbers + message + sender;
            conn.setDoOutput(true);
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Length", Integer.toString(data.length()));
            conn.getOutputStream().write(data.getBytes("UTF-8"));
            final BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            final StringBuffer stringBuffer = new StringBuffer();
            String line;
            while ((line = rd.readLine()) != null) {
                stringBuffer.append(line);
            }
            rd.close();
            Toast.makeText(getApplicationContext(),"OTP sent successfully",Toast.LENGTH_LONG).show();
            // return stringBuffer.toString();
        } catch (Exception e) {
            // System.out.println("Error SMS "+e);
            Toast.makeText(getApplicationContext(),"ERROR SMS "+e,Toast.LENGTH_LONG).show();
            Toast.makeText(getApplicationContext(),"ERROR "+e,Toast.LENGTH_LONG).show();
            //return "Error "+e;

        }*/
        //return "";
        startActivity(new Intent(Singup_Activity.this, Verification_Activity.class));

    }
}
//https://www.youtube.com/watch?v=TB9B3fMvTNU