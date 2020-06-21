package org.project.poolreservation.recyclerviews;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.project.poolreservation.R;
import org.project.poolreservation.Reservation_page;
import org.project.poolreservation.UserMainPage;
import org.project.poolreservation.models.TimeOfDay;

import java.util.ArrayList;
import java.util.List;

public class TimeOfDayAdaptor extends RecyclerView.Adapter<TimeOfDayAdaptor.ViewHolder> {
    List<TimeOfDay> items;
    private Context context;
    ArrayList<String> sectionIdArray=new ArrayList<>();
    String token;


    public TimeOfDayAdaptor(List<TimeOfDay> items, Context context, ArrayList<String> sectionArray,String token) {
        this.items = items;
        this.context = context;
        this.token=token;
        this.sectionIdArray=sectionArray;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.time_of_day_row, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {

        if (items.get(position).isShow()){
            holder.itemView.getLayoutParams().height= RelativeLayout.LayoutParams.WRAP_CONTENT;
        }else {
            holder.itemView.getLayoutParams().height=0;
        }

        holder.time.setText(items.get(position).getTime());

        if (items.get(position).getGender().equals("male")) {
            holder.gender.setText("آقایان");
        }else {
            holder.gender.setText("بانوان");
        }
        holder.capacity.setText(" ظرفیت " + items.get(position).getCapacity() + " نفر");
        holder.off.setText("قیمت " + items.get(position).getPrice() + " تومان");



        holder.off.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(context, Reservation_page.class);
                intent.putExtra("sectionId",sectionIdArray.get(position));
                intent.putExtra("token",token);
                context.startActivity(intent);
            }
        });

    }


    @Override
    public int getItemCount() {
        return items.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView time, gender, capacity, off;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            time = itemView.findViewById(R.id.time);
            gender = itemView.findViewById(R.id.gender);
            capacity = itemView.findViewById(R.id.capacity);
            off = itemView.findViewById(R.id.off);

        }
    }
}
