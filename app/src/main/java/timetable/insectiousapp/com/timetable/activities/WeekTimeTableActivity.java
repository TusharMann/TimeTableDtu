package timetable.insectiousapp.com.timetable.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import timetable.insectiousapp.com.timetable.R;

public class WeekTimeTableActivity extends AppCompatActivity {

    String selectedDayTimetable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_week_time_table);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Intent i=getIntent();
        if(i!=null)
        {
            selectedDayTimetable=i.getStringExtra("fromweektimetablefragment_daytimetable");
            if(selectedDayTimetable!=null&&!selectedDayTimetable.contentEquals(""))
            {
                
            }
        }

    }

}
