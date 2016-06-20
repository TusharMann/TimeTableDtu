package timetable.insectiousapp.com.timetable.fragments;


import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;

import mehdi.sakout.fancybuttons.FancyButton;
import timetable.insectiousapp.com.timetable.R;
import timetable.insectiousapp.com.timetable.others.SharedPreferencesFiles;


public class WeekTimeTableFragment extends Fragment implements View.OnClickListener{

    FancyButton btnToday, btnTomorrow;
    ProgressDialog progressDialog;
    String classId, currentDate;
    int date;

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
        btnToday.setOnClickListener(this);
        btnTomorrow.setOnClickListener(this);

        progressDialog=new ProgressDialog(getActivity());
        progressDialog.setCancelable(false);

        setCredentialsFromSharedPreferences();


        return v;
    }

    @Override
    public void onClick(View v) {



    }

    private void setCredentialsFromSharedPreferences() {
        SharedPreferencesFiles SPF=new SharedPreferencesFiles();
        //for class id
        SharedPreferences sharedPreferences=getActivity().getSharedPreferences(SPF.getSPClassId(), 0);
        classId=sharedPreferences.getString(SPF.getClassId(), "Not set yet");

        if(classId.contentEquals("Not set yet"))
        {
            Toast.makeText(getActivity(), "Set your class id first", Toast.LENGTH_LONG).show();
        }
        else
        {
            //it means class id is already set, you can begin working
        }
    }

    /////////fetching date and time
    public void getCurrentDateAndTimeFromServer() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy_HH:mm");
        String currentDateandTime = sdf.format(new Date());

        currentDate=currentDateandTime.substring(0, 1);
        date=Integer.parseInt(currentDate);

        Log.i("dateandtimestring", "date and time string: "+currentDate);
        int b;

    }


}
