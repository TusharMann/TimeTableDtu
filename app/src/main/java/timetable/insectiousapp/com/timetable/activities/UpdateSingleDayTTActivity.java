package timetable.insectiousapp.com.timetable.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.InputFilter;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import mehdi.sakout.fancybuttons.FancyButton;
import timetable.insectiousapp.com.timetable.R;
import timetable.insectiousapp.com.timetable.others.SpecialSymbolsAndOthers;
import timetable.insectiousapp.com.timetable.volley.MyVolley;

public class UpdateSingleDayTTActivity extends AppCompatActivity {

    String classId;
    String writeKey;
    int dayNo;

    EditText et1, et2, et3, et4, et5, et6, et7, et8, et9, etReminder;
    CheckBox cb1, cb2, cb3, cb4, cb5, cb6, cb7, cb8, cb9;
    TextView tvLastUpdatedOn, tvDayName;
    ProgressDialog progressDialog;
    String defaultTimeTableString, selectedDayTimeTableString;
    FancyButton btnSave;
    String currentDate, currentTime;
    String field1, field2, field3, field4, field5, field6, field7, field8;

    JSONObject jsonObjectTimeTable=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_single_day_tt);

        progressDialog=new ProgressDialog(this);
        progressDialog.setCancelable(false);

        RecievingDataAndChecking();
        requestTimeTableFromServer();
        initializingViews();

        cb2.setChecked(true);

    }



    private void RecievingDataAndChecking() {
        Intent i=getIntent();
        classId=i.getStringExtra("class_id");
        writeKey=i.getStringExtra("write_key");
        dayNo=i.getIntExtra("day_no", 0);

        if(dayNo==0)
        {
            Toast.makeText(this, "Inappropriate option chosen", Toast.LENGTH_SHORT).show();
            finish();
        }

    }

    public void initializingViews()
    {
        et1=(EditText)findViewById(R.id.subject_slot_1);
        et2=(EditText)findViewById(R.id.subject_slot_2);
        et3=(EditText)findViewById(R.id.subject_slot_3);
        et4=(EditText)findViewById(R.id.subject_slot_4);
        et5=(EditText)findViewById(R.id.subject_slot_5);
        et6=(EditText)findViewById(R.id.subject_slot_6);
        et7=(EditText)findViewById(R.id.subject_slot_7);
        et8=(EditText)findViewById(R.id.subject_slot_8);
        et9=(EditText)findViewById(R.id.subject_slot_9);
        etReminder=(EditText)findViewById(R.id.activity_update_single_day_tt_et_reminder);

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

        btnSave=(FancyButton)findViewById(R.id.activity_update_single_day_tt_btn_save);
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getCurrentDateAndTimeFromServer();
            }
        });


        tvLastUpdatedOn=(TextView)findViewById(R.id.activity_update_single_day_tt_tv_lastupdatedon);
        tvDayName=(TextView)findViewById(R.id.activity_update_single_day_tt_tv_dayname);
        switch (dayNo)
        {
            case 1:
                tvDayName.setText("Monday");
                break;
            case 2:
                tvDayName.setText("Tuesday");
                break;
            case 3:
                tvDayName.setText("Wednesday");
                break;
            case 4:
                tvDayName.setText("Thursday");
                break;
            case 5:
                tvDayName.setText("Friday");
                break;
        }

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_activity_update_single_day_tt, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_default) {
            if(jsonObjectTimeTable!=null) {
                cb2.setChecked(true);

                setDefaultTimeTableToViews(jsonObjectTimeTable);
            }
            {
                //Toast.makeText(getApplicationContext(), "Cannot fetch timetable", Toast.LENGTH_SHORT).show();
            }
            return true;
        }
        if( id==R.id.action_lastupdated)
        {
            if(jsonObjectTimeTable!=null) {
                setSelectedDayTimeTableToViews(jsonObjectTimeTable);
            }
            {
                //Toast.makeText(getApplicationContext(), "Cannot fetch timetable", Toast.LENGTH_SHORT).show();
            }
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    private void setDefaultTimeTableToViews(JSONObject serverResponse) {


        cb1.setChecked(true);
        cb2.setChecked(true);
        cb2.setChecked(true);
        cb3.setChecked(true);
        cb4.setChecked(true);
        cb5.setChecked(true);
        cb6.setChecked(true);
        cb7.setChecked(true);
        cb8.setChecked(true);
        cb9.setChecked(true);

        Log.i("stringdetails", "Deault timetable inside function "+defaultTimeTableString);

        try {

            etReminder.setText("-");

            tvLastUpdatedOn.setText("");
            defaultTimeTableString=serverResponse.getString("field6");

            Log.i("stringdetails", "Deault timetable inside try "+defaultTimeTableString);

            SpecialSymbolsAndOthers specialSymbol=new SpecialSymbolsAndOthers();
            String[] Days=defaultTimeTableString.split(specialSymbol.getMain());

            String singleDay = null;
            String[] slots;

            switch (dayNo)
            {
                case 1:
                    singleDay=Days[1];
                    break;
                case 2:
                    singleDay=Days[2];
                    break;
                case 3:
                    singleDay=Days[3];
                    break;
                case 4:
                    singleDay=Days[4];
                    break;
                case 5:
                    singleDay=Days[5];
                    break;
            }

            slots=singleDay.split(specialSymbol.getPrimary());
            et1.setText(slots[0]);
            et2.setText(slots[1]);
            et3.setText(slots[2]);
            et4.setText(slots[3]);
            et5.setText(slots[4]);
            et6.setText(slots[5]);
            et7.setText(slots[6]);
            et8.setText(slots[7]);
            et9.setText(slots[8]);


        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    private void setSelectedDayTimeTableToViews(JSONObject serverResponse) {

        try {

            //tvLastUpdatedOn.setText("");
            selectedDayTimeTableString=serverResponse.getString("field"+dayNo);

            Log.i("stringdetails", "timetable string"+selectedDayTimeTableString);

            SpecialSymbolsAndOthers specialSymbol=new SpecialSymbolsAndOthers();
            String[] SlotsList=selectedDayTimeTableString.split(specialSymbol.getMain());
            if(SlotsList.length>=10)
            {


            Log.i("stringdetailsz", "slots list"+SlotsList);

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

        } catch (JSONException e) {
            e.printStackTrace();
        }


    }


    /////////fetching timetable
    public void requestTimeTableFromServer() {
        progressDialog.setTitle("Fetching Timetable");
        progressDialog.setMessage("Please Wait...");
        progressDialog.show();

        String classTimeTableUrl="https://api.thingspeak.com/channels/"+classId+"/feed/last.json";

        MyVolley.init(getApplicationContext());
        RequestQueue queue = MyVolley.getRequestQueue();

        JsonObjectRequest myReq = new JsonObjectRequest(Request.Method.GET, classTimeTableUrl
                , reqTimeTableSuccessListener(), reqTimeTableErrorListener()) {

            public String getBodyContentType() {
                return "application/json; charset=utf-8";
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<String, String>();

                headers.put("Content-Type", "application/json");
                return headers;
            }

            protected Map<String, String> getParams() throws com.android.volley.AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                return params;
            }
        };

        myReq.setRetryPolicy(new DefaultRetryPolicy(2000, 1, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        queue.add(myReq);

    }

    private com.android.volley.Response.Listener<JSONObject> reqTimeTableSuccessListener() {
        return new com.android.volley.Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject serverResponse) {

                progressDialog.hide();
                jsonObjectTimeTable=serverResponse;

                try {
                    field1=serverResponse.getString("field1");
                    field2=serverResponse.getString("field2");
                    field3=serverResponse.getString("field3");
                    field4=serverResponse.getString("field4");
                    field5=serverResponse.getString("field5");
                    field6=serverResponse.getString("field6");
                    field7=serverResponse.getString("field7");
                    field8=serverResponse.getString("field8");


                } catch (JSONException e) {
                    e.printStackTrace();
                }
                setDefaultTimeTableToViews(jsonObjectTimeTable);
                Log.i("responsecheckingg", "Server response : " + serverResponse.toString());


                Toast.makeText(getApplicationContext(), "TimeTable Fetched", Toast.LENGTH_LONG).show();

            }
        };
    }



    private com.android.volley.Response.ErrorListener reqTimeTableErrorListener() {
        return new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Log.i("responsecheckingg", "Server Error response : " + error.toString());
                Toast.makeText(getApplicationContext(), "Cannot fetch timetable , network/class id error", Toast.LENGTH_LONG).show();
                progressDialog.hide();
            }
        };


    }
    ////////fetching timetable


    /////////fetching date and time
    public void getCurrentDateAndTimeFromServer() {

        String classTimeTableUrl="http://api.geonames.org/timezoneJSON?formatted=true&lat=28&lng=72&username=demo&style=full";

        MyVolley.init(getApplicationContext());
        RequestQueue queue = MyVolley.getRequestQueue();

        JsonObjectRequest myReq = new JsonObjectRequest(Request.Method.GET, classTimeTableUrl
                , reqTimeAndDateSuccessListener(), reqTimeAndDateErrorListener()) {

            public String getBodyContentType() {
                return "application/json; charset=utf-8";
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<String, String>();

                headers.put("Content-Type", "application/json");
                return headers;
            }

            protected Map<String, String> getParams() throws com.android.volley.AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                return params;
            }
        };

        myReq.setRetryPolicy(new DefaultRetryPolicy(2000, 1, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        queue.add(myReq);

    }

    private com.android.volley.Response.Listener<JSONObject> reqTimeAndDateSuccessListener() {
        return new com.android.volley.Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject serverResponse) {

                Log.i("responsecheckingg", "Server response date and time : " + serverResponse);

                try {
                    String time=serverResponse.getString("time");
                    currentDate=time.substring(0,10);
                    currentTime=time.substring(11,16);
                    //after we get the date and time we can update the timetable
                    postTimeTableToServer();
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                Log.i("responsecheckingg", "Server response date and time : " + currentDate+" "+currentTime);
            }
        };
    }



    private com.android.volley.Response.ErrorListener reqTimeAndDateErrorListener() {
        return new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Log.i("responsecheckingg", "Server Error response : " + error.toString());
                Toast.makeText(getApplicationContext(), "Network error, cannot update", Toast.LENGTH_LONG).show();

            }
        };


    }
    ////////fetching dateandtime


    ////////POSTING timetable
    private void postTimeTableToServer() {

        SpecialSymbolsAndOthers sp=new SpecialSymbolsAndOthers();

        String slot_1=et1.getText().toString();
        String slot_2=et2.getText().toString();
        String slot_3=et3.getText().toString();
        String slot_4=et4.getText().toString();
        String slot_5=et5.getText().toString();
        String slot_6=et6.getText().toString();
        String slot_7=et7.getText().toString();
        String slot_8=et8.getText().toString();
        String slot_9=et9.getText().toString();

        slot_1=slot_1+sp.getPrimary()+((cb1.isChecked()==true)? 1:0);
        slot_2=slot_2+sp.getPrimary()+((cb2.isChecked()==true)? 1:0);
        slot_3=slot_3+sp.getPrimary()+((cb3.isChecked()==true)? 1:0);
        slot_4=slot_4+sp.getPrimary()+((cb4.isChecked()==true)? 1:0);
        slot_5=slot_5+sp.getPrimary()+((cb5.isChecked()==true)? 1:0);
        slot_6=slot_6+sp.getPrimary()+((cb6.isChecked()==true)? 1:0);
        slot_7=slot_7+sp.getPrimary()+((cb7.isChecked()==true)? 1:0);
        slot_8=slot_8+sp.getPrimary()+((cb8.isChecked()==true)? 1:0);
        slot_9=slot_9+sp.getPrimary()+((cb9.isChecked()==true)? 1:0);

        String s_slot_l = slot_1.replaceAll("(\\r|\\n|\\r\\n)+", "");
        String s_slot_2 = slot_2.replaceAll("(\\r|\\n|\\r\\n)+", "");
        String s_slot_3 =slot_3.replaceAll("(\\r|\\n|\\r\\n)+", "");
        String s_slot_4 =slot_4.replaceAll("(\\r|\\n|\\r\\n)+", "");
        String s_slot_5 =slot_5.replaceAll("(\\r|\\n|\\r\\n)+", "");
        String s_slot_6 =slot_6.replaceAll("(\\r|\\n|\\r\\n)+", "");
        String s_slot_7 =slot_7.replaceAll("(\\r|\\n|\\r\\n)+", "");
        String s_slot_8 =slot_8.replaceAll("(\\r|\\n|\\r\\n)+", "");
        String s_slot_9 =slot_9.replaceAll("(\\r|\\n|\\r\\n)+", "");

        String reminderString=etReminder.getText().toString();

        if(reminderString.isEmpty()||reminderString==null)
        {
            reminderString="No Reminder";
        }

        reminderString=reminderString.replaceAll("(\\r|\\n|\\r\\n)+", "");

        String fieldToUpdate=sp.getMain()+s_slot_l+sp.getMain()+s_slot_2+sp.getMain()+s_slot_3+sp.getMain()+s_slot_4+sp.getMain()+s_slot_5+sp.getMain();
        fieldToUpdate=fieldToUpdate+s_slot_6+sp.getMain()+s_slot_7+sp.getMain()+s_slot_8+sp.getMain()+s_slot_9+sp.getMain()+reminderString+sp.getMain()+currentDate+" "+currentTime+sp.getMain();

        switch (dayNo)
        {
            case 1:
                field1=fieldToUpdate;
                break;
            case 2:
                field2=fieldToUpdate;
                break;
            case 3:
                field3=fieldToUpdate;
                break;
            case 4:
                field4=fieldToUpdate;
                break;
            case 5:
                field5=fieldToUpdate;
                break;
        }

        progressDialog.setTitle("Updating Timetable");
        progressDialog.setMessage("Please Wait...");
        progressDialog.show();


        String URL="http://api.thingspeak.com/update?key="+writeKey+"&field6="+field6+"&field7="+field7+"&field1="+field1+"&field2="+field2+"&field3="+field3+"&field4="+field4+"&field5="+field5+"&field8="+field8+"";

        MyVolley.init(getApplicationContext());
        RequestQueue queue = MyVolley.getRequestQueue();

        StringRequest myReq=new StringRequest(Request.Method.POST, URL
                , reqPostTimeTableSuccessListener(), reqPostTimeTableErrorListener()) {

            public String getBodyContentType() {
                return "application/json; charset=utf-8";
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<String, String>();

                headers.put("Content-Type", "application/json");
                return headers;
            }

            protected Map<String, String> getParams() throws com.android.volley.AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                return params;
            }
        };

        myReq.setRetryPolicy(new DefaultRetryPolicy(2000, 1, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        queue.add(myReq);
    }

    private com.android.volley.Response.Listener<String> reqPostTimeTableSuccessListener() {
        return new com.android.volley.Response.Listener<String>() {
            @Override
            public void onResponse(String serverResponse) {

                progressDialog.hide();
                Log.i("responsecheckingg", "Server response : " + serverResponse.toString());

                if(Integer.parseInt(serverResponse)==0)
                {
                    Toast.makeText(getApplicationContext(), "Cannot Update, Network Error", Toast.LENGTH_LONG).show();
                }
                else
                {
                    finish();
                    Toast.makeText(getApplicationContext(), "TimeTable Updated", Toast.LENGTH_LONG).show();
                }

            }
        };
    }



    private com.android.volley.Response.ErrorListener reqPostTimeTableErrorListener() {
        return new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Log.i("responsecheckingg", "Server Error response : " + error.toString());
                Toast.makeText(getApplicationContext(), "Cannot Update, Network Error", Toast.LENGTH_LONG).show();
                progressDialog.hide();
            }
        };


    }

}
