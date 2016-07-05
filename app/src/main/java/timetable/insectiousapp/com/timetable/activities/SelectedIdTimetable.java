package timetable.insectiousapp.com.timetable.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import timetable.insectiousapp.com.timetable.R;
import timetable.insectiousapp.com.timetable.others.Classroom;
import timetable.insectiousapp.com.timetable.others.SpecialSymbolsAndOthers;
import timetable.insectiousapp.com.timetable.volley.MyVolley;

public class SelectedIdTimetable extends AppCompatActivity {

    int counter;
    View v;
    String classId;
    ProgressDialog progressDialog;
    String updatedOn, fixedTimetableString;
    String uDay, uMonth, uYear;
    String mondayTime, tuesdayTime, wednesdayTime, thursdayTime, fridayTime;
    String[] slots;
    // FancyButton fancyButton;

    String euro="€";
    String yen="¥";

    /////------here et(edittexts) are tv (textviews)
    TextView et_mon_1, et_mon_2, et_mon_3, et_mon_4, et_mon_5, et_mon_6, et_mon_7, et_mon_8,et_mon_9;
    TextView et_tue_1, et_tue_2, et_tue_3, et_tue_4, et_tue_5, et_tue_6, et_tue_7, et_tue_8, et_tue_9;
    TextView et_wed_1, et_wed_2, et_wed_3, et_wed_4, et_wed_5, et_wed_6, et_wed_7, et_wed_8, et_wed_9;
    TextView et_thu_1, et_thu_2, et_thu_3, et_thu_4, et_thu_5, et_thu_6, et_thu_7, et_thu_8, et_thu_9;
    TextView et_fri_1, et_fri_2, et_fri_3, et_fri_4, et_fri_5, et_fri_6, et_fri_7, et_fri_8, et_fri_9;

    TextView tvUpdatedOn, tvCRName, tvCRContact;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selected_id_timetable);
        setTitle("Selected Class Timetable");

        linkingAndInitializingAllEditTexts();
        requestFetchTimeTableFromServer();


    }

    private void linkingAndInitializingAllEditTexts() {



        tvUpdatedOn=(TextView)findViewById(R.id.fragment_default_timetable_tv_lastupdated);
        tvCRName=(TextView)findViewById(R.id.fragment_default_timetable_tv_crname);
        tvCRContact=(TextView)findViewById(R.id.fragment_default_timetable_tv_crcontactnumber);

        Intent i=getIntent();
        Classroom c=(Classroom)i.getSerializableExtra("ClassroomObject");
        classId=c.id;
        Log.i("SelectedId",classId);

        //   fancyButton=(FancyButton)v.findViewById(R.id.fragment_default_timetable_refresh_btn);

        Log.i("Tag","function1");
        et_mon_1=(TextView)findViewById(R.id.et_monday_1);
        et_mon_2=(TextView)findViewById(R.id.et_monday_2);
        et_mon_3=(TextView)findViewById(R.id.et_monday_3);
        et_mon_4=(TextView)findViewById(R.id.et_monday_4);
        et_mon_5=(TextView)findViewById(R.id.et_monday_5);
        et_mon_6=(TextView)findViewById(R.id.et_monday_6);
        et_mon_7=(TextView)findViewById(R.id.et_monday_7);
        et_mon_8=(TextView)findViewById(R.id.et_monday_8);
        et_mon_9=(TextView)findViewById(R.id.et_monday_9);


        et_tue_1=(TextView)findViewById(R.id.et_tuesday_1);
        et_tue_2=(TextView)findViewById(R.id.et_tuesday_2);
        et_tue_3=(TextView)findViewById(R.id.et_tuesday_3);
        et_tue_4=(TextView)findViewById(R.id.et_tuesday_4);
        et_tue_5=(TextView)findViewById(R.id.et_tuesday_5);
        et_tue_6=(TextView)findViewById(R.id.et_tuesday_6);
        et_tue_7=(TextView)findViewById(R.id.et_tuesday_7);
        et_tue_8=(TextView)findViewById(R.id.et_tuesday_8);
        et_tue_9=(TextView)findViewById(R.id.et_tuesday_9);
        et_wed_1=(TextView)findViewById(R.id.et_wednesday_1);
        et_wed_2=(TextView)findViewById(R.id.et_wednesday_2);
        et_wed_3=(TextView)findViewById(R.id.et_wednesday_3);
        et_wed_4=(TextView)findViewById(R.id.et_wednesday_4);
        et_wed_5=(TextView)findViewById(R.id.et_wednesday_5);
        et_wed_6=(TextView)findViewById(R.id.et_wednesday_6);
        et_wed_7=(TextView)findViewById(R.id.et_wednesday_7);
        et_wed_8=(TextView)findViewById(R.id.et_wednesday_8);
        et_wed_9=(TextView)findViewById(R.id.et_wednesday_9);

        et_thu_1=(TextView)findViewById(R.id.et_thursday_1);
        et_thu_2=(TextView)findViewById(R.id.et_thursday_2);
        et_thu_3=(TextView)findViewById(R.id.et_thursday_3);
        et_thu_4=(TextView)findViewById(R.id.et_thursday_4);
        et_thu_5=(TextView)findViewById(R.id.et_thursday_5);
        et_thu_6=(TextView)findViewById(R.id.et_thursday_6);
        et_thu_7=(TextView)findViewById(R.id.et_thursday_7);
        et_thu_8=(TextView)findViewById(R.id.et_thursday_8);
        et_thu_9=(TextView)findViewById(R.id.et_thursday_9);

        et_fri_1=(TextView)findViewById(R.id.et_friday_1);
        et_fri_2=(TextView)findViewById(R.id.et_friday_2);
        et_fri_3=(TextView)findViewById(R.id.et_friday_3);
        et_fri_4=(TextView)findViewById(R.id.et_friday_4);
        et_fri_5=(TextView)findViewById(R.id.et_friday_5);
        et_fri_6=(TextView)findViewById(R.id.et_friday_6);
        et_fri_7=(TextView)findViewById(R.id.et_friday_7);
        et_fri_8=(TextView)findViewById(R.id.et_friday_8);
        et_fri_9=(TextView)findViewById(R.id.et_friday_9);

    }


    public void requestFetchTimeTableFromServer() {

        progressDialog=new ProgressDialog(this);
        progressDialog.setCancelable(false);

        progressDialog.setTitle("Fetching timetable");
        progressDialog.setMessage("Please Wait...");
        progressDialog.show();


        MyVolley.init(this);
        RequestQueue queue = MyVolley.getRequestQueue();

        String TimeTableUrl="https://api.thingspeak.com/channels/"+classId+"/feed/last.json";

        JsonObjectRequest myReq = new JsonObjectRequest(Request.Method.GET, TimeTableUrl
                , reqTimeTableSuccessListener(), reqTimeTableErrorListener()) {

            public String getBodyContentType() {
                return "application/json; charset=utf-8";
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<String, String>();
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
                settingAllEditTextsWithFetchedData(serverResponse);
                Log.i("responsechecking", "success : " + serverResponse.toString());

            }
        };
    }


    private void settingAllEditTextsWithFetchedData(JSONObject serverResponse) {

        try {

            SpecialSymbolsAndOthers sp=new SpecialSymbolsAndOthers();

            //---setting updatedOn
            updatedOn=serverResponse.getString("created_at");
            updatedOn=updatedOn.substring(0, 10);
            uDay=updatedOn.substring(8,10);
            uMonth=updatedOn.substring(5,7);
            uYear=updatedOn.substring(0,4);
            tvUpdatedOn.setText(uDay+"-"+uMonth+"-"+uYear);

            String crDetailsString=serverResponse.getString("field7");

            if(!crDetailsString.contentEquals("null"))
            {
                String[] crDetailPartList=crDetailsString.split(sp.getMain());
                tvCRName.setText(crDetailPartList[0]);
                tvCRContact.setText(crDetailPartList[1]);


            }
            else
            {
                Toast.makeText(this, "Can't load CR Details", Toast.LENGTH_SHORT).show();
            }

            //---setting updatedOn over

            fixedTimetableString=serverResponse.getString("field6");
            if(!fixedTimetableString.contentEquals("null")) {
                String[] singleTimePeriod = fixedTimetableString.split(sp.getMain());
                mondayTime = singleTimePeriod[1];
                tuesdayTime = singleTimePeriod[2];
                wednesdayTime = singleTimePeriod[3];
                thursdayTime = singleTimePeriod[4];
                fridayTime = singleTimePeriod[5];

                ///--monday
                slots = mondayTime.split(sp.getPrimary());
                et_mon_1.setText(slots[0]);
                et_mon_2.setText(slots[1]);
                et_mon_3.setText(slots[2]);
                et_mon_4.setText(slots[3]);
                et_mon_5.setText(slots[4]);
                et_mon_6.setText(slots[5]);
                et_mon_7.setText(slots[6]);
                et_mon_8.setText(slots[7]);
                et_mon_9.setText(slots[8]);
                ///--monday over

                ///--tuesday
                slots = tuesdayTime.split(sp.getPrimary());
                et_tue_1.setText(slots[0]);
                et_tue_2.setText(slots[1]);
                et_tue_3.setText(slots[2]);
                et_tue_4.setText(slots[3]);
                et_tue_5.setText(slots[4]);
                et_tue_6.setText(slots[5]);
                et_tue_7.setText(slots[6]);
                et_tue_8.setText(slots[7]);
                et_tue_9.setText(slots[8]);
                ///--tuesday over

                ///--wed
                slots = wednesdayTime.split(sp.getPrimary());
                et_wed_1.setText(slots[0]);
                et_wed_2.setText(slots[1]);
                et_wed_3.setText(slots[2]);
                et_wed_4.setText(slots[3]);
                et_wed_5.setText(slots[4]);
                et_wed_6.setText(slots[5]);
                et_wed_7.setText(slots[6]);
                et_wed_8.setText(slots[7]);
                et_wed_9.setText(slots[8]);
                ///--wed over

                ///--thu
                slots = thursdayTime.split(sp.getPrimary());
                et_thu_1.setText(slots[0]);
                et_thu_2.setText(slots[1]);
                et_thu_3.setText(slots[2]);
                et_thu_4.setText(slots[3]);
                et_thu_5.setText(slots[4]);
                et_thu_6.setText(slots[5]);
                et_thu_7.setText(slots[6]);
                et_thu_8.setText(slots[7]);
                et_thu_9.setText(slots[8]);
                ///--thu over

                ///--fri
                slots = fridayTime.split(sp.getPrimary());
                et_fri_1.setText(slots[0]);
                et_fri_2.setText(slots[1]);
                et_fri_3.setText(slots[2]);
                et_fri_4.setText(slots[3]);
                et_fri_5.setText(slots[4]);
                et_fri_6.setText(slots[5]);
                et_fri_7.setText(slots[6]);
                et_fri_8.setText(slots[7]);
                et_fri_9.setText(slots[8]);
                ///--fri over

            }
            else
            {
                Toast.makeText(this, "No default TimeTable is found for corresponding ClassId", Toast.LENGTH_SHORT).show();
            }


        } catch (JSONException e) {
            e.printStackTrace();
        }


    }

    private com.android.volley.Response.ErrorListener reqTimeTableErrorListener() {
        return new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.i("responsechecking", "error : " + error.toString());
                Toast.makeText(SelectedIdTimetable.this, "Network error/ wrong class Id set", Toast.LENGTH_LONG).show();
                progressDialog.hide();
            }
        };
    }



            }
