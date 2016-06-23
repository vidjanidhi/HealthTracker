package com.healthtracker.adapter;

import android.app.Activity;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;

import com.healthtracker.R;
import com.healthtracker.component.MyFontTextView;
import com.healthtracker.helper.DataBaseHelper;
import com.healthtracker.helper.PreferenceHelper;
import com.healthtracker.model.Log;
import com.healthtracker.model.LogEntry;

import java.util.ArrayList;

/**
 * Created by local nidhi on 06-05-2016.
 */
public class DisplayLogListAdapter extends BaseAdapter {

    ArrayList<LogEntry> logEntryArrayList;
    Activity activity;
    LayoutInflater inflater;
    PreferenceHelper phelper;


    public DisplayLogListAdapter(Activity activity, ArrayList<LogEntry> logEntryArrayList) {

        this.activity = activity;
        this.logEntryArrayList = logEntryArrayList;
        inflater = LayoutInflater.from(activity);
        phelper = new PreferenceHelper(activity);

    }


    @Override
    public int getCount() {
        return logEntryArrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return logEntryArrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder holder;
        if (convertView == null) {
            inflater = activity.getLayoutInflater();
            convertView = View.inflate(activity, R.layout.display_log_list_item,
                    null);
            holder = new ViewHolder();
            convertView.setTag(holder);

            holder.tvDateForLog = (MyFontTextView) convertView.findViewById(R.id.tv_date_for_log);
            holder.tvTimeForLog = (MyFontTextView) convertView.findViewById(R.id.tv_time_for_log);
            holder.tvMedicineName = (MyFontTextView) convertView.findViewById(R.id.tv_medicine_name);
            holder.tvMedicineQuantity = (MyFontTextView) convertView.findViewById(R.id.tv_medicine_quantity);
            holder.tvMedicineTime = (MyFontTextView) convertView.findViewById(R.id.tv_medicine_time);
            holder.ll_bg_color = (LinearLayout) convertView.findViewById(R.id.ll_bg_color);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        LogEntry logEntry = logEntryArrayList.get(position);
        holder.tvDateForLog.setText(logEntry.getDate() + "");
        holder.tvTimeForLog.setText(logEntry.getTime() + "");
        holder.tvMedicineQuantity.setText(logEntry.getQuantity() + "");
        holder.tvMedicineTime.setText(logEntry.getSelectedTime() + "");

        DataBaseHelper dataBaseHelper = new DataBaseHelper(activity);
        Log log = dataBaseHelper.getLogFromRowId(logEntry.getRowId());
        holder.tvMedicineName.setText(log.getName() + "(" + log.getUnit() + ")");
        switch (log.getLogId()) {
            case 1:
                holder.ll_bg_color.setBackgroundColor(ContextCompat.getColor(convertView.getContext(), R.color.dark_blue));
                break;
            case 2:
                holder.ll_bg_color.setBackgroundColor(ContextCompat.getColor(convertView.getContext(), R.color.green));
                break;
            case 3:
                holder.ll_bg_color.setBackgroundColor(ContextCompat.getColor(convertView.getContext(), R.color.orange));
                break;
            case 4:
                holder.tvMedicineName.setText("");
                holder.tvMedicineQuantity.setText("");
                holder.tvMedicineTime.setText("");
                holder.ll_bg_color.setBackgroundColor(ContextCompat.getColor(convertView.getContext(), R.color.grey));
                break;
        }


        return convertView;
    }

    class ViewHolder {

        private MyFontTextView tvDateForLog;
        private MyFontTextView tvTimeForLog;
        private MyFontTextView tvMedicineName;
        private MyFontTextView tvMedicineQuantity;
        private MyFontTextView tvMedicineTime;
        LinearLayout ll_bg_color;


    }

}
