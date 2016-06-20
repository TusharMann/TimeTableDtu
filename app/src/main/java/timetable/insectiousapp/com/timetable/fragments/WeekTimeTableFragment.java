package timetable.insectiousapp.com.timetable.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import mehdi.sakout.fancybuttons.FancyButton;
import timetable.insectiousapp.com.timetable.R;


public class WeekTimeTableFragment extends Fragment {

    FancyButton btnToday, btnTomorrow;

    public WeekTimeTableFragment() {
        // Required empty public constructor
    }

    View v;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragmentView v=null;
        v= inflater.inflate(R.layout.fragment_week_time_table, container, false);

        btnToday=(FancyButton)v.findViewById(R.id.fragment_week_time_table_btn_today);
        btnTomorrow=(FancyButton)v.findViewById(R.id.fragment_week_time_table_btn_tomorrow);

        return v;
    }

}
