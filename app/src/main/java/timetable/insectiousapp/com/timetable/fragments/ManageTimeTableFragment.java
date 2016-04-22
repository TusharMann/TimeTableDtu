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

import timetable.insectiousapp.com.timetable.R;
import timetable.insectiousapp.com.timetable.activities.SetWriteKeyActivity;
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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
         v= inflater.inflate(R.layout.fragment_manage_time_table, container, false);

        ivEditWriteKey=(ImageView)v.findViewById(R.id.fragment_manage_time_table_iv_editwritekey);
        tvClassId=(TextView)v.findViewById(R.id.class_detail_layout_2_tv_classid);
        tvWriteKey=(TextView)v.findViewById(R.id.class_detail_layout_2_tv_writekey);
        ivEditWriteKey.setOnClickListener(this);



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
        String classId=sharedPreferences.getString(SPF.getClassId(), "Not set yet");
        //
        //for write key
        SharedPreferences sharedPreferences2=getActivity().getSharedPreferences(SPF.getSPWriteKey(), 0);
        String writeKey=sharedPreferences2.getString(SPF.getWriteKey(), "Not set yet");
        //
        tvClassId.setText(classId);
        tvWriteKey.setText(writeKey);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId())
        {
            case R.id.fragment_manage_time_table_iv_editwritekey:

                Intent i=new Intent();
                i.setClass(getActivity().getApplicationContext(), SetWriteKeyActivity.class);
                startActivity(i);
                getActivity().overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
                break;
        }

    }
}
