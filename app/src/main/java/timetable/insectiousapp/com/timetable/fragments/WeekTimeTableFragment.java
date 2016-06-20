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
    String currentDayName;
    String selectedDayTimeTableString;
    int currentDayNo;

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

                String day=getCurrentDayName();
                if(day.contentEquals("monday"))
                {
                    currentDayNo=1;
                    ParseRecievedJsonObject(jsonObjectTimeTable, 1);
                }
                else if(day.contentEquals("tuesday"))
                {
                    currentDayNo=2;
                    ParseRecievedJsonObject(jsonObjectTimeTable, 2);
                }
                else if(day.contentEquals("wednesday"))
                {
                    currentDayNo=3;
                    ParseRecievedJsonObject(jsonObjectTimeTable, 3);
                }
                else if(day.contentEquals("thursday"))
                {
                    currentDayNo=4;
                    ParseRecievedJsonObject(jsonObjectTimeTable, 4);
                }
                else if(day.contentEquals("friday"))
                {
                    currentDayNo=5;
                    ParseRecievedJsonObject(jsonObjectTimeTable, 5);
                }
                else if(day.contentEquals("saturday"))
                {
                    currentDayNo=6;
                    ParseRecievedJsonObject(jsonObjectTimeTable, 1);
                    Toast.makeText(getActivity(), "Showing monday's timetable", Toast.LENGTH_SHORT).show();
                }
                else if(day.contentEquals("sunday"))
                {
                    currentDayNo=7;
                    ParseRecievedJsonObject(jsonObjectTimeTable, 1);
                    Toast.makeText(getActivity(), "Showing monday's timetable", Toast.LENGTH_SHORT).show();
                }
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


    public void ParseRecievedJsonObject(JSONObject jsonObject, int day)
    {

        try {

            //tvLastUpdatedOn.setText("");
            selectedDayTimeTableString=jsonObject.getString("field"+day);

            Log.i("stringdetails", "timetable string"+selectedDayTimeTableString);

            SpecialSymbolsAndOthers specialSymbol=new SpecialSymbolsAndOthers();
            String[] SlotsList=selectedDayTimeTableString.split(specialSymbol.getMain());
            if(SlotsList.length>=10)
            {


                Log.i("stringdetailsz", "slots list"+SlotsList);

                String singleDay = null;
                String singleSlot;
                String[] singleSlotParts;

                ////setting updatedOn
                singleSlot=SlotsList[11];

                if(singleSlot==null||singleSlot.contentEquals(""))
                {
                    Toast.makeText(getActivity(), "Haven't updated yet", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    String date1=singleSlot.substring(0,1);

                    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy_HH:mm");
                    String currentDateandTime = sdf.format(new Date());
                    String date2=currentDateandTime.substring(0, 1);

                    

                }

            }

        } catch (JSONException e) {
            e.printStackTrace();
        }


    }

    public String getCurrentDayName()
    {

        return currentDayName;
    }

}
