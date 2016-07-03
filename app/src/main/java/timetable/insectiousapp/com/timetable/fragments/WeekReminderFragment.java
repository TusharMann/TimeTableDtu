package timetable.insectiousapp.com.timetable.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import timetable.insectiousapp.com.timetable.R;

public class WeekReminderFragment extends Fragment {

    View v;


    public WeekReminderFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

       v= inflater.inflate(R.layout.fragment_week_reminder, container, false);
        return v;
    }
}
