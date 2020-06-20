package org.project.pool_reservation.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.project.pool_reservation.R;
import org.project.pool_reservation.models.DayOfWeek;
import org.project.pool_reservation.models.TimeOfDay;
import org.project.pool_reservation.recyclerviews.DayOfWeekAdapter;
import org.project.pool_reservation.recyclerviews.TimeOfDayAdaptor;

import java.util.ArrayList;
import java.util.List;

public class DayOfWeekActivity extends AppCompatActivity {

    List<DayOfWeek> dayOfWeekList = new ArrayList<>();
    List<TimeOfDay> timeOfDayList = new ArrayList<>();
    RecyclerView dayOfWeekRecy, timeOfDayRecy;
    TextView title;
    RelativeLayout filterLayout;
    Button topBtn, maleFilter, femaleFilter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_day_of_week);

        getSupportActionBar().hide();
        setDateFake();
        dayOfWeekRecy = findViewById(R.id.dayOfWeekRecy);
        timeOfDayRecy = findViewById(R.id.timeOfDayRecy);
        title = findViewById(R.id.title);
        topBtn = findViewById(R.id.btnTop);
        filterLayout = findViewById(R.id.filterLayout);
        maleFilter = findViewById(R.id.maleFilter);
        femaleFilter = findViewById(R.id.femaleFilter);
        dayOfWeekRecy.setLayoutManager(new LinearLayoutManager(this));
        timeOfDayRecy.setLayoutManager(new LinearLayoutManager(this));
        dayOfWeekRecy.setAdapter(new DayOfWeekAdapter(dayOfWeekList, this));


        topBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (dayOfWeekRecy.getVisibility() == View.VISIBLE) {

                } else if (timeOfDayRecy.getVisibility() == View.VISIBLE) {
                    filterLayout.setVisibility(View.VISIBLE);
                }
            }
        });

        maleFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                filterLayout.setVisibility(View.GONE);
                for (int i = 0; i < timeOfDayList.size(); i++) {
                    if (timeOfDayList.get(i).getGender() == 1) {
                        timeOfDayList.get(i).setShow(false);
                    } else {
                        timeOfDayList.get(i).setShow(true);
                    }
                }
                timeOfDayRecy.getAdapter().notifyDataSetChanged();
            }
        });

        femaleFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                filterLayout.setVisibility(View.GONE);
                for (int i = 0; i < timeOfDayList.size(); i++) {
                    if (timeOfDayList.get(i).getGender() == 0) {
                        timeOfDayList.get(i).setShow(false);
                    } else {
                        timeOfDayList.get(i).setShow(true);
                    }
                }
                timeOfDayRecy.getAdapter().notifyDataSetChanged();
            }
        });


    }

    private void setDateFake() {

        List<TimeOfDay> times = new ArrayList<>();
        times.add(new TimeOfDay("08-10", 0, 5, 20));
        times.add(new TimeOfDay("10-12", 0, 5, 20));
        times.add(new TimeOfDay("13-15", 0, 20, 50));
        times.add(new TimeOfDay("15-18", 1, 5, 10));
        times.add(new TimeOfDay("18-20", 1, 50, 0));

        dayOfWeekList.add(new DayOfWeek("شنبه", "1399/03/01", times));
        dayOfWeekList.add(new DayOfWeek("یکشنبه", "1399/03/02", times));
        dayOfWeekList.add(new DayOfWeek("دوشنبه", "1399/03/03", times));
        dayOfWeekList.add(new DayOfWeek("سه شنبه", "1399/03/04", times));
        dayOfWeekList.add(new DayOfWeek("چهارشنبه", "1399/03/05", times));
        dayOfWeekList.add(new DayOfWeek("پنجشنبه", "1399/03/06", times));
        dayOfWeekList.add(new DayOfWeek("جمعه", "1399/03/07", null));
    }


    public void showTimeOfDay(DayOfWeek dayOfWeek) {

        title.setText(dayOfWeek.getDay() + " (" + dayOfWeek.getDate() + ")");
        topBtn.setText("فیلتر");

        timeOfDayList = dayOfWeek.getTimeOfDays();
        if (timeOfDayList != null && timeOfDayList.size() > 0) {
            for (int i = 0; i < timeOfDayList.size(); i++) {
                timeOfDayList.get(i).setShow(true);
            }
            dayOfWeekRecy.setVisibility(View.GONE);
            timeOfDayRecy.setVisibility(View.VISIBLE);
            timeOfDayRecy.setAdapter(new TimeOfDayAdaptor(timeOfDayList, this));
        } else {
            Toast.makeText(this, "زمانی یافت نشد", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onBackPressed() {
        if (filterLayout.getVisibility() == View.VISIBLE) {
            filterLayout.setVisibility(View.GONE);
            return;
        }
        if (timeOfDayRecy.getVisibility() == View.VISIBLE) {
            timeOfDayRecy.setVisibility(View.GONE);
            dayOfWeekRecy.setVisibility(View.VISIBLE);
            topBtn.setText("هفته بعد");
            title.setText("انتخاب سانس");
            return;
        }
        super.onBackPressed();

    }
}
