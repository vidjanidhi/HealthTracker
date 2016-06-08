package com.healthtracker.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;

import com.healthtracker.R;
import com.healthtracker.component.MyFontTextView;
import com.healthtracker.helper.PreferenceHelper;
import com.healthtracker.model.Log;

import java.util.ArrayList;

/**
 * Created by local nidhi on 06-05-2016.
 */
public class MedListAdapter extends BaseAdapter {

    ArrayList<Log> logArrayList;
    Activity activity;
    LayoutInflater inflater;
    PreferenceHelper phelper;
    private int fragment_id;


    public MedListAdapter(Activity activity, ArrayList<Log> logArrayList, int fragment_id) {

        this.activity = activity;
        this.logArrayList = logArrayList;
        this.fragment_id = fragment_id;
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
            convertView = View.inflate(activity, R.layout.med_liist_item,
                    null);
            holder = new ViewHolder();
            convertView.setTag(holder);

            holder.tvMedName = (MyFontTextView) convertView.findViewById(R.id.tv_med_name);
            holder.chkMeditation = (CheckBox) convertView.findViewById(R.id.chk_med);

        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        Log log = logArrayList.get(position);
        holder.tvMedName.setText(log.getName() + " ( " + log.getUnit() + " )");
//        switch (fragment_id) {
//            case AppConstant.MED_FRAGMENT:
//                if (AddMeditationActivity.selectedMeditationList.size() > 0) {
//                    for (int i = 0; i < AddMeditationActivity.selectedMeditationList.size(); i++) {
//                        if (AddMeditationActivity.selectedMeditationList.get(i).getRowId() == logArrayList.get(i).getRowId()) {
//                            holder.chkMeditation.setChecked(true);
//                            android.util.Log.i("set checked",logArrayList.get(i).getRowId()+ "");
//                        }else {
//                            holder.chkMeditation.setChecked(false);
//
//                        }
//                    }
//                }
//                break;
//            case AppConstant.FOOD_FRAGMENT:
//                break;
//
//            case AppConstant.ACTIVITY_FRAGMENT:
//                break;
//
//        }

        if (logArrayList.get(position).isChecked()) {
            holder.chkMeditation.setChecked(true);
        }
        holder.chkMeditation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (holder.chkMeditation.isChecked()) {
                    logArrayList.get(position).setChecked(true);
                } else {
                    logArrayList.get(position).setChecked(false);
                }
            }
        });

        return convertView;
    }

    class ViewHolder {

        private MyFontTextView tvMedName;
        CheckBox chkMeditation;


    }

}
