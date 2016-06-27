package timetable.insectiousapp.com.timetable.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.InputFilter;
import android.util.Log;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONObject;

import timetable.insectiousapp.com.timetable.R;
import timetable.insectiousapp.com.timetable.others.SpecialSymbolsAndOthers;

public class WeekDayTimeTableActivity extends AppCompatActivity {

    String classId;
    String writeKey;
    int dayNo;

    TextView et1, et2, et3, et4, et5, et6, et7, et8, et9, etReminder;
    CheckBox cb1, cb2, cb3, cb4, cb5, cb6, cb7, cb8, cb9;
    TextView tvLastUpdatedOn, tvDayName;
    ProgressDialog progressDialog;
    String defaultTimeTableString, selectedDayTimeTableString;
    String currentDate, currentTime;
    String field1, field2, field3, field4, field5, field6, field7, field8;
    String currentDayName;

    JSONObject jsonObjectTimeTable=null;
    String recievedDayTimeTableString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_week_day_time_table);

        setTitle("Week's Timetable");

        progressDialog=new ProgressDialog(this);
        progressDialog.setCancelable(false);


        initializingViews();
        RecievingDataAndCheckingAndSettingTimeTable();
    }



    private void RecievingDataAndCheckingAndSettingTimeTable() {
        Intent i=getIntent();
        if(i!=null)
        {
            recievedDayTimeTableString=i.getStringExtra("fromweektimetablefragment_daytimetable");
            if(recievedDayTimeTableString!=null&&!recievedDayTimeTableString.contentEquals(""))
            {
                dayNo=i.getIntExtra("fromweektimetablefragment_daytimetable_dayno", 1);
                Log.i("dayNo", "Day No :"+dayNo);
                Log.i("dayNo", "Timetabe string :"+recievedDayTimeTableString);
                setSelectedDayTimeTableToViews();
            }
            else
            {
                finish();
                Toast.makeText(this, "No timetable Recieved", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void initializingViews()
    {
        et1=(TextView)findViewById(R.id.subject_slot_1);
        et2=(TextView)findViewById(R.id.subject_slot_2);
        et3=(TextView)findViewById(R.id.subject_slot_3);
        et4=(TextView)findViewById(R.id.subject_slot_4);
        et5=(TextView)findViewById(R.id.subject_slot_5);
        et6=(TextView)findViewById(R.id.subject_slot_6);
        et7=(TextView)findViewById(R.id.subject_slot_7);
        et8=(TextView)findViewById(R.id.subject_slot_8);
        et9=(TextView)findViewById(R.id.subject_slot_9);
        etReminder=(TextView)findViewById(R.id.activity_update_single_day_tt_et_reminder);

        et1.setFilters(new InputFilter[] {new InputFilter.AllCaps(), new InputFilter.LengthFilter(17)});
        et2.setFilters(new InputFilter[] {new InputFilter.AllCaps(), new InputFilter.LengthFilter(17)});
        et3.setFilters(new InputFilter[] {new InputFilter.AllCaps(), new InputFilter.LengthFilter(17)});
        et4.setFilters(new InputFilter[] {new InputFilter.AllCaps(), new InputFilter.LengthFilter(17)});
        et5.setFilters(new InputFilter[] {new InputFilter.AllCaps(), new InputFilter.LengthFilter(17)});
        et6.setFilters(new InputFilter[] {new InputFilter.AllCaps(), new InputFilter.LengthFilter(17)});
        et7.setFilters(new InputFilter[] {new InputFilter.AllCaps(), new InputFilter.LengthFilter(17)});
        et8.setFilters(new InputFilter[] {new InputFilter.AllCaps(), new InputFilter.LengthFilter(17)});
        et9.setFilters(new InputFilter[] {new InputFilter.AllCaps(), new InputFilter.LengthFilter(17)});

        cb1=(CheckBox)findViewById(R.id.cb_slot_1);
        cb2=(CheckBox)findViewById(R.id.cb_slot_2);
        cb3=(CheckBox)findViewById(R.id.cb_slot_3);
        cb4=(CheckBox)findViewById(R.id.cb_slot_4);
        cb5=(CheckBox)findViewById(R.id.cb_slot_5);
        cb6=(CheckBox)findViewById(R.id.cb_slot_6);
        cb7=(CheckBox)findViewById(R.id.cb_slot_7);
        cb8=(CheckBox)findViewById(R.id.cb_slot_8);
        cb9=(CheckBox)findViewById(R.id.cb_slot_9);

        tvLastUpdatedOn=(TextView)findViewById(R.id.activity_update_single_day_tt_tv_lastupdatedon);
        tvDayName=(TextView)findViewById(R.id.activity_update_single_day_tt_tv_dayname);

        getDayNamefromDayNo(dayNo);
        tvDayName.setText(currentDayName);
    }

    private void getDayNamefromDayNo(int dayNumber)
    {

        if(dayNumber==1)
            currentDayName="Monday";
        else if(dayNumber==2)
            currentDayName="Tuesday";
        else if(dayNumber==3)
            currentDayName="Wednesday";
        else if(dayNumber==4)
            currentDayName="Thursday";
        else if(dayNumber==5)
            currentDayName="Friday";

    }

    private void setSelectedDayTimeTableToViews() {

        SpecialSymbolsAndOthers specialSymbol=new SpecialSymbolsAndOthers();
        String[] SlotsList=recievedDayTimeTableString.split(specialSymbol.getMain());
        if(SlotsList.length>=10)
        {
            String singleDay = null;
            String singleSlot;
            String[] singleSlotParts;

            ////slot 1
            singleSlot=SlotsList[1];
            singleSlotParts=singleSlot.split(specialSymbol.getPrimary());
            et1.setText(singleSlotParts[0]);
            cb1.setChecked((singleSlotParts[1].contentEquals("1"))?true:false);
            //--slot 1

            ////slot 2
            singleSlot=SlotsList[2];
            singleSlotParts=singleSlot.split(specialSymbol.getPrimary());
            et2.setText(singleSlotParts[0]);
            cb2.setChecked((singleSlotParts[1].contentEquals("1"))?true:false);
            //--slot 2

            ////slot 3
            singleSlot=SlotsList[3];
            singleSlotParts=singleSlot.split(specialSymbol.getPrimary());
            et3.setText(singleSlotParts[0]);
            cb3.setChecked((singleSlotParts[1].contentEquals("1"))?true:false);
            //--slot 3

            ////slot 4
            singleSlot=SlotsList[4];
            singleSlotParts=singleSlot.split(specialSymbol.getPrimary());
            et4.setText(singleSlotParts[0]);
            cb4.setChecked((singleSlotParts[1].contentEquals("1"))?true:false);
            //--slot 4

            ////slot 5
            singleSlot=SlotsList[5];
            singleSlotParts=singleSlot.split(specialSymbol.getPrimary());
            et5.setText(singleSlotParts[0]);
            cb5.setChecked((singleSlotParts[1].contentEquals("1"))?true:false);
            //--slot 5

            ////slot 6
            singleSlot=SlotsList[6];
            singleSlotParts=singleSlot.split(specialSymbol.getPrimary());
            et6.setText(singleSlotParts[0]);
            cb6.setChecked((singleSlotParts[1].contentEquals("1"))?true:false);
            //--slot 6

            ////slot 7
            singleSlot=SlotsList[7];
            singleSlotParts=singleSlot.split(specialSymbol.getPrimary());
            et7.setText(singleSlotParts[0]);
            cb7.setChecked((singleSlotParts[1].contentEquals("1"))?true:false);
            //--slot 7

            ////slot 8
            singleSlot=SlotsList[8];
            singleSlotParts=singleSlot.split(specialSymbol.getPrimary());
            et8.setText(singleSlotParts[0]);
            cb8.setChecked((singleSlotParts[1].contentEquals("1"))?true:false);
            //--slot 8

            ////slot 9
            singleSlot=SlotsList[9];
            singleSlotParts=singleSlot.split(specialSymbol.getPrimary());
            et9.setText(singleSlotParts[0]);
            cb9.setChecked((singleSlotParts[1].contentEquals("1"))?true:false);
            //--slot 9

            ////setting reminder
            singleSlot=SlotsList[10];
            etReminder.setText(singleSlot);
            //--setting reminder

            ////setting updatedOn
            singleSlot=SlotsList[11];
            tvLastUpdatedOn.setText("(Last updated On: "+singleSlot+" )");

        }


    }

}
