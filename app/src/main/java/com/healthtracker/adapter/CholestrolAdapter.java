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
import com.healthtracker.model.cholesterol;
import com.healthtracker.util.AppConstant;
import com.healthtracker.util.Util;

import java.io.File;
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
            holder.imgUpload = (ImageView) convertView.findViewById(R.id.img_upload);

            holder.tvGlucoseItemDate = (MyFontTextView) convertView.findViewById(R.id.tv_c_item_date);
            holder.tvCholestrolItemHdl = (MyFontTextView) convertView.findViewById(R.id.tv_cholestrol_item_hdl);
            holder.tvCholestrolItemLdl = (MyFontTextView) convertView.findViewById(R.id.tv_cholestrol_item_ldl);
            holder.tvCholestrolItemTotal = (MyFontTextView) convertView.findViewById(R.id.tv_cholestrol_item_total);
            holder.tvCholestrolItemTriglyceride = (MyFontTextView) convertView.findViewById(R.id.tv_cholestrol_item_triglyceride);

        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        cholesterol cholesterol = cholesterolArrayList.get(position);
        if (cholesterol.getbyteArray() != null) {
            File f = new File(cholesterol.getbyteArray());
            Bitmap b = BitmapFactory.decodeFile(f.getAbsolutePath());
            holder.imgUpload.setImageBitmap(b);
        }

        if (cholesterol.getHdl() != 0)
            holder.tvCholestrolItemHdl.setText(cholesterol.getHdl() + "");
        else holder.tvCholestrolItemHdl.setText("--");
        if (cholesterol.getLdl() != 0)
            holder.tvCholestrolItemLdl.setText(cholesterol.getLdl() + "");
        else holder.tvCholestrolItemLdl.setText("--");
        holder.tvGlucoseItemDate.setText(Util.getFormatedDate(cholesterol.getDate(), phelper.getInteger(AppConstant.DATE_SELECTED)));
        if (cholesterol.getTotal() != 0)
            holder.tvCholestrolItemTotal.setText(cholesterol.getTotal() + "");
        else holder.tvCholestrolItemTotal.setText("--");
        if (cholesterol.getTriglyceride() != 0)
            holder.tvCholestrolItemTriglyceride.setText(cholesterol.getTriglyceride() + "");
        else holder.tvCholestrolItemTriglyceride.setText("--");

        return convertView;
    }

    class ViewHolder {

        private MyFontTextView tvGlucoseItemDate;
        private MyFontTextView tvCholestrolItemHdl;
        private MyFontTextView tvCholestrolItemLdl;
        private MyFontTextView tvCholestrolItemTotal;
        private MyFontTextView tvCholestrolItemTriglyceride;
        public ImageView imgUpload;

    }

}
