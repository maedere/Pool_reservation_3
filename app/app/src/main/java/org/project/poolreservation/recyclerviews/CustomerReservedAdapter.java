package org.project.poolreservation.recyclerviews;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.project.poolreservation.R;
import org.project.poolreservation.models.Reserved;

import java.util.List;

public class CustomerReservedAdapter extends RecyclerView.Adapter<CustomerReservedAdapter.ViewHolder> {
    private List<Reserved> items;
    private Context context;

    public CustomerReservedAdapter(List<Reserved> items, Context context) {
        this.context = context;
        this.items = items;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.reserved_row, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CustomerReservedAdapter.ViewHolder holder, int position) {

        holder.poolName.setText(items.get(position).getPoolName());
        holder.time.setText(items.get(position).getTime());
        holder.day.setText(items.get(position).getDay());
        holder.date.setText(items.get(position).getDate());
        holder.gender.setText(items.get(position).getGender());
        holder.code.setText(items.get(position).getCode());
        holder.absence.setText(items.get(position).getAbsence());


    }


    @Override
    public int getItemCount() {
        return items.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView poolName;
        TextView time;
        TextView date;
        TextView day;
        TextView gender;
        TextView code;
        TextView absence;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            poolName = itemView.findViewById(R.id.poolName);
            time = itemView.findViewById(R.id.time);
            date = itemView.findViewById(R.id.date);
            day = itemView.findViewById(R.id.day);
            gender = itemView.findViewById(R.id.gender);
            code = itemView.findViewById(R.id.code);
            absence=itemView.findViewById(R.id.absece);


        }

    }
}