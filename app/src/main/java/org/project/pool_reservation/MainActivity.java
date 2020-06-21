package org.project.pool_reservation6;

import android.database.Cursor;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {

    private ListView listView;
    private CustomAdapter customAdapter;
    private ArrayList<ContactModel> contactModelArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listView = (ListView) findViewById(R.id.listView);

        contactModelArrayList = new ArrayList<>();


        ContactModel contactModel = new ContactModel();
        contactModel.setTime("12:23-13:00");
        contactModel.setSex("بانوان");
        contactModel.setReserves(20);
        contactModelArrayList.add(contactModel);
        contactModel.setTime("15:20-16:30");
        contactModel.setSex("آقایان");
        contactModel.setReserves(20);
        contactModelArrayList.add(contactModel);


        customAdapter = new CustomAdapter(this,contactModelArrayList);
        listView.setAdapter(customAdapter);

    }
}
