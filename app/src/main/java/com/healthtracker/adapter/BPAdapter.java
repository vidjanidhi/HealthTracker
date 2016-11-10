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
import com.healthtracker.model.BloodPresure;
import com.healthtracker.util.AppConstant;
import com.healthtracker.util.Util;

import java.io.File;
import java.util.ArrayList;

/**
 * Created by local nidhi on 06-05-2016.
 */
public class BPAdapter extends BaseAdapter {

    ArrayList<BloodPresure> bloodPresureArrayList;
    Activity activity;
    LayoutInflater inflater;
    PreferenceHelper phelper;


    public BPAdapter(Activity activity, ArrayList<BloodPresure> weightArrayList) {

        this.activity = activity;
        this.bloodPresureArrayList = weightArrayList;
        inflater = LayoutInflater.from(activity);
        phelper = new PreferenceHelper(activity);

    }


    @Override
    public int getCount() {
        return bloodPresureArrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return bloodPresureArrayList.get(position);
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
            convertView = View.inflate(activity, R.layout.b_p_list_item,
                    null);
            holder = new ViewHolder();
            convertView.setTag(holder);

            holder.tvGlucoseItemDate = (MyFontTextView) convertView.findViewById(R.id.tv_bp_item_date);
            holder.tvBpItemSystolic = (MyFontTextView) convertView.findViewById(R.id.tv_bp_item_systolic);
            holder.tvBpItemDiastolic = (MyFontTextView) convertView.findViewById(R.id.tv_bp_item_diastolic);
            holder.tvBpItemHeartRate = (MyFontTextView) convertView.findViewById(R.id.tv_bp_item_heart_rate);
            holder.imgUpload = (ImageView) convertView.findViewById(R.id.img_upload);

        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        BloodPresure bloodPresure = bloodPresureArrayList.get(position);
        if (bloodPresure.getbyteArray() != null) {
            File f = new File(bloodPresure.getbyteArray());
            Bitmap b = BitmapFactory.decodeFile(f.getAbsolutePath());
            holder.imgUpload.setImageBitmap(b);
        }
        if (bloodPresure.getSystolic() != 0)
            holder.tvBpItemSystolic.setText(bloodPresure.getSystolic() + "");
        else holder.tvBpItemSystolic.setText("--");
        if (bloodPresure.getDiastolic() != 0)
            holder.tvBpItemDiastolic.setText(bloodPresure.getDiastolic() + "");
        else holder.tvBpItemDiastolic.setText("---");

        holder.tvGlucoseItemDate.setText(Util.getFormatedDate(bloodPresure.getDate(), phelper.getInteger(AppConstant.DATE_SELECTED)) + "");
        if (bloodPresure.getHeartrate() != 0)
            holder.tvBpItemHeartRate.setText(bloodPresure.getHeartrate() + "");
        else holder.tvBpItemHeartRate.setText("--");

        return convertView;
    }

    class ViewHolder {

        private MyFontTextView tvGlucoseItemDate;
        private MyFontTextView tvBpItemSystolic;
        private MyFontTextView tvBpItemDiastolic;
        private MyFontTextView tvBpItemHeartRate;
        public ImageView imgUpload;

    }

}
