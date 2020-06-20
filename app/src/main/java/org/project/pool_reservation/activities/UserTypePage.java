package org.project.pool_reservation.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import org.project.pool_reservation.R;
import org.project.pool_reservation.UserMainPage;

public class UserTypePage extends AppCompatActivity {
    Button button4, button5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_type_page);
        button4 = findViewById(R.id.button4);
        button5 = findViewById(R.id.button5);
        button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               startActivity(new Intent(UserTypePage.this, Owner_Register_Page.class));
            }
        });
        button5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(UserTypePage.this, UserMainPage.class));

            }
        });


    }

  /*  public void buttonClick(View view) {
        if (view.getId() == R.id.button4)
            startActivity(new Intent(UserTypePage.this, Owner_Register_Page.class));
        if (view.getId() == R.id.button5)
                    startActivity(new Intent(UserTypePage.this, UserTypePage.class));


    }
    */
}
