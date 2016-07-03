package timetable.insectiousapp.com.timetable.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import timetable.insectiousapp.com.timetable.R;

public class WeekReminderFragment extends Fragment {

    View v;
    TextView reminder_slot1,reminder_slot2,reminder_slot3,reminder_slot4,reminder_slot5;
    TextView update_slot1,update_slot2,update_slot3,update_slot4,update_slot5;


    public WeekReminderFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        update_slot1=(TextView)v.findViewById(R.id.update_slot_1);
        update_slot2=(TextView)v.findViewById(R.id.update_slot_2);
        update_slot3=(TextView)v.findViewById(R.id.update_slot_3);
        update_slot4=(TextView)v.findViewById(R.id.update_slot_4);
        update_slot5=(TextView)v.findViewById(R.id.update_slot_5);

        reminder_slot1=(TextView)v.findViewById(R.id.reminder_slot_1);
        reminder_slot2=(TextView)v.findViewById(R.id.reminder_slot_2);
        reminder_slot3=(TextView)v.findViewById(R.id.reminder_slot_3);
        reminder_slot4=(TextView)v.findViewById(R.id.reminder_slot_4);
        reminder_slot5=(TextView)v.findViewById(R.id.reminder_slot_5);



       v= inflater.inflate(R.layout.fragment_week_reminder, container, false);
        return v;
    }
}
