package org.project.pool_reservation;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import org.project.pool_reservation.models.Pool;
import org.project.pool_reservation.recyclerviews.PoolsAdapter;

import java.util.ArrayList;
import java.util.List;


public class UserMainPage extends AppCompatActivity {

    List<Pool> poolsList=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_main_page);

        poolsList.add(new Pool("استخر 1 "));
        poolsList.add(new Pool("استخر 2 "));
        poolsList.add(new Pool("استخر 3 "));
        poolsList.add(new Pool("استخر 4 "));
        poolsList.add(new Pool("استخر 5 "));
        poolsList.add(new Pool("استخر 6 "));
        poolsList.add(new Pool("استخر 7 "));
        poolsList.add(new Pool("استخر 8 "));
        poolsList.add(new Pool("استخر 9 "));
        poolsList.add(new Pool("استخر 10 "));

        RecyclerView recyPool=findViewById(R.id.recyPools);
        recyPool.setLayoutManager(new LinearLayoutManager(this));
        recyPool.setAdapter(new PoolsAdapter(poolsList,this));


    }

    public void showPoolInfo(Pool pool){
        startActivity(new Intent(this,pool_info_page.class));
    }

}
