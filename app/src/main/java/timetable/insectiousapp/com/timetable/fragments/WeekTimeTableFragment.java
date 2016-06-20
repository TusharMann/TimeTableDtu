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

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import mehdi.sakout.fancybuttons.FancyButton;
import timetable.insectiousapp.com.timetable.R;
import timetable.insectiousapp.com.timetable.others.SharedPreferencesFiles;
import timetable.insectiousapp.com.timetable.others.SpecialSymbolsAndOthers;
import timetable.insectiousapp.com.timetable.volley.MyVolley;


public class WeekTimeTableFragment extends Fragment implements View.OnClickListener{

    FancyButton btnToday, btnTomorrow;
    ProgressDialog progressDialog;
    String classId, currentDate;
    int date;
    JSONObject jsonObjectTimeTable=null;

    String field1, field2, field3, field4, field5, field6, field7, field8;

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

        getCurrentDateAndTimeFromSystem();
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
    public void getCurrentDateAndTimeFromSystem() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy_HH:mm");
        String currentDateandTime = sdf.format(new Date());

        currentDate=currentDateandTime.substring(0, 1);
        date=Integer.parseInt(currentDate);

        Log.i("dateandtimestring", "date and time string: "+currentDate);


    }

    /////////fetching timetable
    public void requestTimeTableFromServer() {
        progressDialog.setTitle("Fetching Timetable");
        progressDialog.setMessage("Please Wait...");
        progressDialog.show();

        Log.i("responsecheckingg", "Request time table from server");

        String classTimeTableUrl="https://api.thingspeak.com/channels/"+classId+"/feed/last.json";

        MyVolley.init(getActivity());
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

                Log.i("responsecheckingg", "on time table positive response");
                Log.i("responsecheckingg", "Server response : " + serverResponse.toString());

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
                ParseRecievedJsonObject(jsonObjectTimeTable);
                Toast.makeText(getActivity(), "TimeTable Fetched", Toast.LENGTH_LONG).show();

            }
        };
    }



    private com.android.volley.Response.ErrorListener reqTimeTableErrorListener() {
        return new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Log.i("responsecheckingg", "Server Error response : " + error.toString());
                Toast.makeText(getActivity(), "Cannot fetch timetable , network/class id error", Toast.LENGTH_LONG).show();
                progressDialog.hide();
            }
        };


    }
    ////////fetching timetable


    public void ParseRecievedJsonObject(JSONObject jsonObject)
    {

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

}
