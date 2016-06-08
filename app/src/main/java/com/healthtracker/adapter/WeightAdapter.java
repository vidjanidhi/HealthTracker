package com.healthtracker.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.healthtracker.R;
import com.healthtracker.component.MyFontTextView;
import com.healthtracker.helper.PreferenceHelper;
import com.healthtracker.model.Weight;
import com.healthtracker.util.AppConstant;

import java.util.ArrayList;

/**
 * Created by local nidhi on 06-05-2016.
 */
public class WeightAdapter extends BaseAdapter {

    ArrayList<Weight> weightArrayList;
    Activity activity;
    LayoutInflater inflater;
    PreferenceHelper phelper;


    public WeightAdapter(Activity activity, ArrayList<Weight> weightArrayList) {

        this.activity = activity;
        this.weightArrayList = weightArrayList;
        inflater = LayoutInflater.from(activity);
        phelper = new PreferenceHelper(activity);

    }


    @Override
    public int getCount() {
        return weightArrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return weightArrayList.get(position);
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
            convertView = View.inflate(activity, R.layout.weight_list_item,
                    null);
            holder = new ViewHolder();
            convertView.setTag(holder);

            holder.tvWeight = (MyFontTextView) convertView
                    .findViewById(R.id.tv_weight_item_weight);
            holder.tvWeightUnit = (MyFontTextView) convertView
                    .findViewById(R.id.tv_weight_item_weight_unit);
            holder.tvFat = (MyFontTextView) convertView
                    .findViewById(R.id.tv_weight_item_fat);
            holder.tvAbdomen = (MyFontTextView) convertView
                    .findViewById(R.id.tv_weight_item_abdomen);
            holder.tvAbdomenUnit = (MyFontTextView) convertView
                    .findViewById(R.id.tv_weight_item_abdomen_unit);
            holder.tvWaist = (MyFontTextView) convertView
                    .findViewById(R.id.tv_weight_item_waist);
            holder.tvWaistUnit = (MyFontTextView) convertView
                    .findViewById(R.id.tv_weight_item_waist_unit);
            holder.tvHips = (MyFontTextView) convertView
                    .findViewById(R.id.tv_weight_item_hip);
            holder.tvHipsUnit = (MyFontTextView) convertView
                    .findViewById(R.id.tv_weight_item_hip_unit);
            holder.tvDate = (MyFontTextView) convertView.findViewById(R.id.tv_weight_item_date);

        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        Weight weight = weightArrayList.get(position);
        holder.tvWeight.setText(weight.getWeight() + "");
        holder.tvFat.setText(weight.getFat() + "");
        holder.tvAbdomen.setText(weight.getAbdomen() + "");
        holder.tvWaist.setText(weight.getWaist() + "");
        holder.tvHips.setText(weight.getHips() + "");
        holder.tvDate.setText(weight.getDate());

        if (phelper.getInteger(AppConstant.WEIGHT_SELECTED) == AppConstant.WEIGHT_SELECTED_UNIT_KG) {
            holder.tvWeightUnit.setText("kg");
        } else {
            holder.tvWeightUnit.setText("lbs");
        }
        if (phelper.getInteger(AppConstant.LENGTH_SELECTED) == AppConstant.LENGTH_SELECTED_UNIT_CM) {
            holder.tvAbdomenUnit.setText("cm");
            holder.tvWaistUnit.setText("cm");
            holder.tvHipsUnit.setText("cm");
        } else {
            holder.tvAbdomenUnit.setText("inch");
            holder.tvWaistUnit.setText("inch");
            holder.tvHipsUnit.setText("inch");
        }

        return convertView;
    }

    class ViewHolder {
        MyFontTextView tvWeight, tvWeightUnit, tvFat, tvAbdomen, tvAbdomenUnit, tvWaist, tvWaistUnit, tvHips, tvHipsUnit, tvDate;
    }

}
