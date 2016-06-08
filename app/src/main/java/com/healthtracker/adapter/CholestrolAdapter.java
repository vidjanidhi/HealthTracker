package com.healthtracker.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.healthtracker.R;
import com.healthtracker.component.MyFontTextView;
import com.healthtracker.helper.PreferenceHelper;
import com.healthtracker.model.cholesterol;

import java.util.ArrayList;

/**
 * Created by local nidhi on 06-05-2016.
 */
public class CholestrolAdapter extends BaseAdapter {

    ArrayList<cholesterol> cholesterolArrayList;
    Activity activity;
    LayoutInflater inflater;
    PreferenceHelper phelper;


    public CholestrolAdapter(Activity activity, ArrayList<cholesterol> weightArrayList) {

        this.activity = activity;
        this.cholesterolArrayList = weightArrayList;
        inflater = LayoutInflater.from(activity);
        phelper = new PreferenceHelper(activity);

    }


    @Override
    public int getCount() {
        return cholesterolArrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return cholesterolArrayList.get(position);
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
            convertView = View.inflate(activity, R.layout.cholestrol_list_item,
                    null);
            holder = new ViewHolder();
            convertView.setTag(holder);

            holder.tvGlucoseItemDate = (MyFontTextView) convertView.findViewById(R.id.tv_c_item_date);
            holder.tvCholestrolItemHdl = (MyFontTextView) convertView.findViewById(R.id.tv_cholestrol_item_hdl);
            holder.tvCholestrolItemLdl = (MyFontTextView) convertView.findViewById(R.id.tv_cholestrol_item_ldl);
            holder.tvCholestrolItemTotal = (MyFontTextView) convertView.findViewById(R.id.tv_cholestrol_item_total);
            holder.tvCholestrolItemTriglyceride = (MyFontTextView) convertView.findViewById(R.id.tv_cholestrol_item_triglyceride);

        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        cholesterol cholesterol = cholesterolArrayList.get(position);
        holder.tvCholestrolItemHdl.setText(cholesterol.getHdl() + "");
        holder.tvCholestrolItemLdl.setText(cholesterol.getLdl() + "");
        holder.tvGlucoseItemDate.setText(cholesterol.getDate());
        holder.tvCholestrolItemTotal.setText(cholesterol.getTotal() + "");
        holder.tvCholestrolItemTriglyceride.setText(cholesterol.getTriglyceride() + "");

        return convertView;
    }

    class ViewHolder {

        private MyFontTextView tvGlucoseItemDate;
        private MyFontTextView tvCholestrolItemHdl;
        private MyFontTextView tvCholestrolItemLdl;
        private MyFontTextView tvCholestrolItemTotal;
        private MyFontTextView tvCholestrolItemTriglyceride;

    }

}
