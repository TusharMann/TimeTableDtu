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


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_week_time_table, container, false);
    }

}
