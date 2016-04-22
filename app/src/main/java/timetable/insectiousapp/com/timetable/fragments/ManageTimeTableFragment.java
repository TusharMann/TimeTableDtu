package timetable.insectiousapp.com.timetable.fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import timetable.insectiousapp.com.timetable.R;
import timetable.insectiousapp.com.timetable.activities.SetWriteKeyActivity;

/**
 * A simple {@link Fragment} subclass.
 */
public class ManageTimeTableFragment extends Fragment implements View.OnClickListener{


    public ManageTimeTableFragment() {
        // Required empty public constructor
    }


   // View v;
    ImageView ivEditWriteKey;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v= inflater.inflate(R.layout.fragment_manage_time_table, container, false);

        ivEditWriteKey=(ImageView)v.findViewById(R.id.fragment_manage_time_table_iv_editwritekey);
        ivEditWriteKey.setOnClickListener(this);

        return v;
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
