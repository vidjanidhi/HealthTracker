package com.healthtracker.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.healthtracker.R;
import com.healthtracker.component.MyFontTextView;
import com.healthtracker.helper.PreferenceHelper;
import com.healthtracker.model.Thyroid;

import java.util.ArrayList;

/**
 * Created by local nidhi on 06-05-2016.
 */
public class ThyroidAdapter extends BaseAdapter {

    ArrayList<Thyroid> thyroidArrayList;
    Activity activity;
    LayoutInflater inflater;
    PreferenceHelper phelper;


    public ThyroidAdapter(Activity activity, ArrayList<Thyroid> weightArrayList) {

        this.activity = activity;
        this.thyroidArrayList = weightArrayList;
        inflater = LayoutInflater.from(activity);
        phelper = new PreferenceHelper(activity);

    }


    @Override
    public int getCount() {
        return thyroidArrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return thyroidArrayList.get(position);
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
            convertView = View.inflate(activity, R.layout.thyroid_list_item,
                    null);
            holder = new ViewHolder();
            convertView.setTag(holder);

            holder.tvThyroidItemDate = (MyFontTextView) convertView.findViewById(R.id.tv_thyroid_item_date);
            holder.tvThyroidItemTshlevel = (MyFontTextView) convertView.findViewById(R.id.tv_thyroid_item_tshlevel);


        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        Thyroid thyroid = thyroidArrayList.get(position);
        holder.tvThyroidItemDate.setText(thyroid.getDate());
        holder.tvThyroidItemTshlevel.setText(thyroid.getTshLevel() + "");
        return convertView;
    }

    class ViewHolder {

        private MyFontTextView tvThyroidItemDate;
        private MyFontTextView tvThyroidItemTshlevel;

    }

}
