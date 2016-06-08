package com.healthtracker.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;

import com.healthtracker.R;
import com.healthtracker.component.MyFontEdittextView;
import com.healthtracker.component.MyFontTextView;
import com.healthtracker.helper.PreferenceHelper;
import com.healthtracker.model.Log;

import java.util.ArrayList;

/**
 * Created by local nidhi on 06-05-2016.
 */
public class SelectedMedListAdapter extends BaseAdapter {

    ArrayList<Log> logArrayList;
    Activity activity;
    LayoutInflater inflater;
    PreferenceHelper phelper;


    public SelectedMedListAdapter(Activity activity, ArrayList<Log> logArrayList) {

        this.activity = activity;
        this.logArrayList = logArrayList;
        inflater = LayoutInflater.from(activity);
        phelper = new PreferenceHelper(activity);

    }


    @Override
    public int getCount() {
        return logArrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return logArrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        final ViewHolder holder;
        if (convertView == null) {
            inflater = activity.getLayoutInflater();
            convertView = View.inflate(activity, R.layout.selected_meditation_item,
                    null);
            holder = new ViewHolder();
            convertView.setTag(holder);

            holder.tvMedName = (MyFontTextView) convertView.findViewById(R.id.tv_med_name);
            holder.etSelectedItemQuantity = (MyFontEdittextView) convertView.findViewById(R.id.et_selected_med_quantity);

        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        Log log = logArrayList.get(position);
        holder.tvMedName.setText(log.getName() + " ( " + log.getUnit() + " )");
//        if (!TextUtils.isEmpty(holder.etSelectedItemQuantity.getText().toString())) {
//            log.setQuantity(Float.parseFloat(holder.etSelectedItemQuantity.getText().toString()));
//        }
        return convertView;
    }

    class ViewHolder {
        private MyFontTextView tvMedName;
        EditText etSelectedItemQuantity;
    }

}
