package timetable.insectiousapp.com.timetable.fragments;


import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import mehdi.sakout.fancybuttons.FancyButton;
import timetable.insectiousapp.com.timetable.R;
import timetable.insectiousapp.com.timetable.activities.SetWriteKeyActivity;
import timetable.insectiousapp.com.timetable.activities.UpdateDefaultTimeTableActivity;
import timetable.insectiousapp.com.timetable.activities.UpdateSingleDayTTActivity;
import timetable.insectiousapp.com.timetable.others.SharedPreferencesFiles;

/**
 * A simple {@link Fragment} subclass.
 */
public class ManageTimeTableFragment extends Fragment implements View.OnClickListener{


    public ManageTimeTableFragment() {
        // Required empty public constructor
    }

    View v;
    ImageView ivEditWriteKey;
    TextView tvClassId, tvWriteKey;
    FancyButton btnMon, btnTue, btnWed, btnThu, btnFri, btnDefault;
    int singleDayCounter=0, dayNo=0;
    String classId, writeKey;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
         v= inflater.inflate(R.layout.fragment_manage_time_table, container, false);

        ivEditWriteKey=(ImageView)v.findViewById(R.id.fragment_manage_time_table_iv_editwritekey);
        tvClassId=(TextView)v.findViewById(R.id.class_detail_layout_2_tv_classid);
        tvWriteKey=(TextView)v.findViewById(R.id.class_detail_layout_2_tv_writekey);
        btnMon=(FancyButton)v.findViewById(R.id.fragment_manage_time_table_btn_mon);
        btnTue=(FancyButton)v.findViewById(R.id.fragment_manage_time_table_btn_tue);
        btnWed=(FancyButton)v.findViewById(R.id.fragment_manage_time_table_btn_wed);
        btnThu=(FancyButton)v.findViewById(R.id.fragment_manage_time_table_btn_thu);
        btnFri=(FancyButton)v.findViewById(R.id.fragment_manage_time_table_btn_fri);
        btnDefault=(FancyButton)v.findViewById(R.id.fragment_manage_time_table_btn_default);

        ivEditWriteKey.setOnClickListener(this);
        btnMon.setOnClickListener(this);
        btnTue.setOnClickListener(this);
        btnWed.setOnClickListener(this);
        btnThu.setOnClickListener(this);
        btnFri.setOnClickListener(this);
        btnDefault.setOnClickListener(this);

        return v;
    }

    @Override
    public void onResume() {
        super.onResume();
        setCredentialsFromSharedPreferences();
    }

    private void setCredentialsFromSharedPreferences() {
        SharedPreferencesFiles SPF=new SharedPreferencesFiles();
        //for class id
        SharedPreferences sharedPreferences=getActivity().getSharedPreferences(SPF.getSPClassId(), 0);
        classId=sharedPreferences.getString(SPF.getClassId(), "Not set yet");
        //
        //for write key
        SharedPreferences sharedPreferences2=getActivity().getSharedPreferences(SPF.getSPWriteKey(), 0);
        writeKey=sharedPreferences2.getString(SPF.getWriteKey(), "Not set yet");
        //
        tvClassId.setText(classId);
        tvWriteKey.setText(writeKey);
    }

    @Override
    public void onClick(View v) {

        if (classId.contentEquals("Not set yet")) {
            Toast.makeText(getActivity(), "Set both class id & write key", Toast.LENGTH_SHORT).show();
        } else {
            switch (v.getId()) {
                case R.id.fragment_manage_time_table_iv_editwritekey:

                    Intent i = new Intent();
                    i.setClass(getActivity().getApplicationContext(), SetWriteKeyActivity.class);
                    i.putExtra("ClassId",classId);
                    startActivity(i);
                    getActivity().overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
                    break;

                case R.id.fragment_manage_time_table_btn_mon:
                    singleDayCounter = 1;
                    dayNo = 1;
                    break;
                case R.id.fragment_manage_time_table_btn_tue:
                    singleDayCounter = 1;
                    dayNo = 2;
                    break;
                case R.id.fragment_manage_time_table_btn_wed:
                    singleDayCounter = 1;
                    dayNo = 3;
                    break;
                case R.id.fragment_manage_time_table_btn_thu:
                    singleDayCounter = 1;
                    dayNo = 4;
                    break;
                case R.id.fragment_manage_time_table_btn_fri:
                    singleDayCounter = 1;
                    dayNo = 5;
                    break;
                case R.id.fragment_manage_time_table_btn_default:
                    Intent i2 = new Intent();
                    i2.setClass(getActivity().getApplicationContext(), UpdateDefaultTimeTableActivity.class);
                    startActivity(i2);
                    getActivity().overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
                    break;
            }

            if (singleDayCounter == 1) {
                //means any of the mon, tue..friday button is pressed
                Intent i1 = new Intent();
                i1.setClass(getActivity().getApplicationContext(), UpdateSingleDayTTActivity.class);
                i1.putExtra("class_id", classId);
                i1.putExtra("write_key", writeKey);
                i1.putExtra("day_no", dayNo);
                startActivity(i1);
                getActivity().overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
            }
            singleDayCounter = 0;
            dayNo = 0;
        }
    }
}
