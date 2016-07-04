package timetable.insectiousapp.com.timetable.fragments;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import timetable.insectiousapp.com.timetable.others.SharedPreferencesFiles;
import timetable.insectiousapp.com.timetable.others.SpecialSymbolsAndOthers;
import timetable.insectiousapp.com.timetable.volley.MyVolley;

public class WeekReminderFragment extends Fragment {

    View v;
    TextView reminder_slot1,reminder_slot2,reminder_slot3,reminder_slot4,reminder_slot5;
    TextView update_slot1,update_slot2,update_slot3,update_slot4,update_slot5;
    SpecialSymbolsAndOthers specialSymbolsAndOthers;


    ProgressDialog progressDialog;
    String classId;
    JSONObject jsonObjectTimeTable=null;

    String field1, field2, field3, field4, field5, field6, field7, field8;
    String[] mon,tue,wed,thurs,fri;
    String monrem,tuerem,wedrem,thursrem,frirem;
    String monupd,tueupd,wedupd,thursupd,friupd;



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

        specialSymbolsAndOthers=new SpecialSymbolsAndOthers();



       v= inflater.inflate(R.layout.fragment_week_reminder, container, false);

        setCredentialsFromSharedPreferences();

        return v;
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
            requestTimeTableFromServer();
        }
    }
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

                if(field1.contentEquals("null")){
                    reminder_slot1.setText("-");
                    update_slot1.setText("-");

                }

                else{
                    mon=field1.split(specialSymbolsAndOthers.getMain());

                    monrem=mon[10];
                    monupd=mon[11];

                    reminder_slot1.setText(monrem);
                    update_slot1.setText(monupd);

                }

                if(field2.contentEquals("null")){
                    reminder_slot2.setText("-");
                    update_slot2.setText("-");


                }

                else{
                    tue=field2.split(specialSymbolsAndOthers.getMain());
                    tuerem=tue[10];
                    tueupd=tue[11];

                    reminder_slot2.setText(tuerem);
                    update_slot2.setText(tueupd);


                }


                if(field3.contentEquals("null")){
                    reminder_slot3.setText("-");
                    update_slot3.setText("-");

                }

                else {
                    wed=field3.split(specialSymbolsAndOthers.getMain());

                    wedrem=wed[10];
                    wedupd=wed[11];

                    reminder_slot3.setText(wedrem);
                    update_slot3.setText(wedupd);
                }


                if(field4.contentEquals("null")){
                    reminder_slot4.setText("-");
                    update_slot4.setText("-");

                }

                else{
                    thurs=field4.split(specialSymbolsAndOthers.getMain());
                    thursrem=thurs[10];
                    thursupd=thurs[11];
                    reminder_slot4.setText(thursrem);
                    update_slot4.setText(thursupd);


                }

                if(field5.contentEquals("null")){
                    reminder_slot5.setText("-");
                    update_slot5.setText("-");

                }

                else{
                    fri=field5.split(specialSymbolsAndOthers.getMain());

                    frirem=fri[10];
                    frirem=fri[11];

                    reminder_slot5.setText(frirem);
                    update_slot5.setText(friupd);
                }
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



}
