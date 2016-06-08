package com.healthtracker.adapter;

import android.app.Activity;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.healthtracker.R;
import com.healthtracker.component.MyFontTextView;
import com.healthtracker.helper.PreferenceHelper;
import com.healthtracker.model.Glucose;

import java.util.ArrayList;
import java.util.List;

public class GlucoseListItemAdapter extends BaseAdapter {

    List<Glucose> glucoseArrayList;
    Activity activity;
    LayoutInflater layoutInflater;
    PreferenceHelper phelper;


    public GlucoseListItemAdapter(Activity activity, ArrayList<Glucose> glucoseArrayList) {
        this.activity = activity;
        this.glucoseArrayList = glucoseArrayList;
        layoutInflater = LayoutInflater.from(activity);
        phelper = new PreferenceHelper(activity);
    }

    @Override
    public int getCount() {
        return glucoseArrayList.size();
    }

    @Override
    public Glucose getItem(int position) {
        return glucoseArrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder holder;
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.glucose_list_item, null);
            holder = new ViewHolder();
            convertView.setTag(holder);
            holder.tvGlucoseItemDate = (MyFontTextView) convertView.findViewById(R.id.tv_glucose_item_date);
            holder.tvGlucoseItemGlucose = (MyFontTextView) convertView.findViewById(R.id.tv_glucose_item_glucose);
            holder.tvGlucoseItemGlucoseUnit = (MyFontTextView) convertView.findViewById(R.id.tv_glucose_item_glucose_unit);
            holder.tvGlucoseItemHba1c = (MyFontTextView) convertView.findViewById(R.id.tv_glucose_item_hba1c);
            holder.tvGlucoseItemHba1cUnit = (MyFontTextView) convertView.findViewById(R.id.tv_glucose_item_hba1c_unit);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        Glucose glucose = glucoseArrayList.get(position);
        holder.tvGlucoseItemDate.setText(glucose.getDate() + "");
        holder.tvGlucoseItemGlucose.setText(glucose.getGlucose() + "");
        holder.tvGlucoseItemHba1c.setText(glucose.getHba1c() + "");

        return convertView;
    }

    protected class ViewHolder {
        CardView cardView;
        MyFontTextView tvGlucoseItemDate;
        MyFontTextView tvGlucoseItemGlucose;
        MyFontTextView tvGlucoseItemGlucoseUnit;
        MyFontTextView tvGlucoseItemHba1c;
        MyFontTextView tvGlucoseItemHba1cUnit;

    }
}
