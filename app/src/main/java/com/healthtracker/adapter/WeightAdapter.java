package com.healthtracker.adapter;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.healthtracker.R;
import com.healthtracker.component.MyFontTextView;
import com.healthtracker.helper.PreferenceHelper;
import com.healthtracker.model.Weight;
import com.healthtracker.util.AppConstant;
import com.healthtracker.util.UnitHelper;
import com.healthtracker.util.Util;

import java.io.File;
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
            holder.imgUpload = (ImageView) convertView.findViewById(R.id.img_upload);

        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        Weight weight = weightArrayList.get(position);
        holder.tvDate.setText(Util.getFormatedDate(weight.getDate(), phelper.getInteger(AppConstant.DATE_SELECTED)));
//        byte[] ary=weight.getbyteArray();
//        Bitmap b1= BitmapFactory.decodeByteArray(ary, 0, ary.length);
        if (weight.getbyteArray() != null) {
            File f = new File(weight.getbyteArray());
            Bitmap b = BitmapFactory.decodeFile(f.getAbsolutePath());
            holder.imgUpload.setImageBitmap(b);
        }
        if (weight.getFat() != 0) {
            holder.tvFat.setText(weight.getFat() + "");
        } else {
            holder.tvFat.setText("--");

        }
        if (phelper.getInteger(AppConstant.WEIGHT_SELECTED) == AppConstant.WEIGHT_SELECTED_UNIT_KG) {
            holder.tvWeightUnit.setText("kg");
            if (weight.getWeight() != 0)
                holder.tvWeight.setText(weight.getWeight() + "");
            else holder.tvWeight.setText("--");
        } else {
            holder.tvWeightUnit.setText("lb");
            if (weight.getWeight() != 0)
                holder.tvWeight.setText(UnitHelper.kgToLbsConverter(Float.parseFloat(weight.getWeight() + "")) + "");
            else holder.tvWeight.setText("--");

        }
        if (phelper.getInteger(AppConstant.LENGTH_SELECTED) == AppConstant.LENGTH_SELECTED_UNIT_CM) {
            holder.tvAbdomenUnit.setText("cm");
            holder.tvWaistUnit.setText("cm");
            holder.tvHipsUnit.setText("cm");
            if (weight.getAbdomen() != 0)
                holder.tvAbdomen.setText(weight.getAbdomen() + "");
            else holder.tvAbdomen.setText("--");
            if (weight.getWaist() != 0)
                holder.tvWaist.setText(weight.getWaist() + "");
            else holder.tvWaist.setText("--");
            if (weight.getHips() != 0)
                holder.tvHips.setText(weight.getHips() + "");
            else holder.tvHips.setText("--");
        } else {
            holder.tvAbdomenUnit.setText("inch");
            holder.tvWaistUnit.setText("inch");
            holder.tvHipsUnit.setText("inch");
            if (weight.getAbdomen() != 0)
                holder.tvAbdomen.setText(UnitHelper.cmToInchConverter(Float.parseFloat(weight.getAbdomen() + "")) + "");
            else holder.tvAbdomen.setText("--");
            if (weight.getWaist() != 0)
                holder.tvWaist.setText(UnitHelper.cmToInchConverter(Float.parseFloat(weight.getWaist() + "")) + "");
            else holder.tvWaist.setText("--");
            if (weight.getHips() != 0)
                holder.tvHips.setText(UnitHelper.cmToInchConverter(Float.parseFloat(weight.getHips() + "")) + "");
            else holder.tvHips.setText("--");
        }

        return convertView;
    }

    class ViewHolder {
        MyFontTextView tvWeight, tvWeightUnit, tvFat, tvAbdomen, tvAbdomenUnit, tvWaist, tvWaistUnit, tvHips, tvHipsUnit, tvDate;
        public ImageView imgUpload;
    }

}
