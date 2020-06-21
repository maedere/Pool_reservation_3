package org.project.pool_reservation6;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Parsania Hardik on 11-May-17.
 */
public class CustomAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<ContactModel> contactModelArrayList;

    public CustomAdapter(Context context, ArrayList<ContactModel> contactModelArrayList) {

        this.context = context;
        this.contactModelArrayList = contactModelArrayList;
    }

    @Override
    public int getViewTypeCount() {
        return getCount();
    }
    @Override
    public int getItemViewType(int position) {

        return position;
    }

    @Override
    public int getCount() {
        return contactModelArrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return contactModelArrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        if (convertView == null) {
            holder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.lv_item, null, true);

          //  holder.tvname = (TextView) convertView.findViewById(R.id.name);
          //  holder.tvnumber = (TextView) convertView.findViewById(R.id.number);

            holder. tv_time = convertView.findViewById(R.id.time);
            holder.tv_sex = convertView.findViewById(R.id.sex);
            holder. tv_reserve = convertView.findViewById(R.id.reserve);
            holder. btn_delete = convertView.findViewById(R.id.delete);
            holder.  btn_edit = convertView.findViewById(R.id.edit);

            convertView.setTag(holder);
        }else {
            // the getTag returns the viewHolder object set as a tag to the view
            holder = (ViewHolder)convertView.getTag();
        }

        holder.tv_time.setText(contactModelArrayList.get(position).getTime());
        holder.tv_sex.setText(contactModelArrayList.get(position).getSex());
        holder.tv_reserve.setText("10");


        return convertView;
    }

    private class ViewHolder {
        protected TextView tv_time;
        protected TextView tv_sex;
        protected TextView tv_reserve;
        protected Button btn_delete;
        protected Button btn_edit;

       // protected TextView tvname, tvnumber;

    }
}
