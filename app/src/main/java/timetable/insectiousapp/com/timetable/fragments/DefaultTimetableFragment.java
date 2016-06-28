 package timetable.insectiousapp.com.timetable.fragments;


import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
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

import timetable.insectiousapp.com.timetable.Sqlite.TT_Sqlite;
import timetable.insectiousapp.com.timetable.volley.MyVolley;
import timetable.insectiousapp.com.timetable.R;
import timetable.insectiousapp.com.timetable.others.SharedPreferencesFiles;
import timetable.insectiousapp.com.timetable.others.SpecialSymbolsAndOthers;

/**
 * A simple {@link Fragment} subclass.
 */
public class DefaultTimetableFragment extends Fragment {


    public DefaultTimetableFragment() {
        // Required empty public constructor
    }

    View v;
    String classId;
    ProgressDialog progressDialog;
    String updatedOn, fixedTimetableString;
    String uDay, uMonth, uYear;
    String mondayTime, tuesdayTime, wednesdayTime, thursdayTime, fridayTime;
    String[] slots;

    String euro="€";
    String yen="¥";

    /////------here et(edittexts) are tv (textviews)
    TextView et_mon_1, et_mon_2, et_mon_3, et_mon_4, et_mon_5, et_mon_6, et_mon_7, et_mon_8,et_mon_9;
    TextView et_tue_1, et_tue_2, et_tue_3, et_tue_4, et_tue_5, et_tue_6, et_tue_7, et_tue_8, et_tue_9;
    TextView et_wed_1, et_wed_2, et_wed_3, et_wed_4, et_wed_5, et_wed_6, et_wed_7, et_wed_8, et_wed_9;
    TextView et_thu_1, et_thu_2, et_thu_3, et_thu_4, et_thu_5, et_thu_6, et_thu_7, et_thu_8, et_thu_9;
    TextView et_fri_1, et_fri_2, et_fri_3, et_fri_4, et_fri_5, et_fri_6, et_fri_7, et_fri_8, et_fri_9;

    TextView tvUpdatedOn, tvCRName, tvCRContact;

    /////------

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v= inflater.inflate(R.layout.fragment_default_timetable, container, false);

        progressDialog=new ProgressDialog(getActivity());
        progressDialog.setCancelable(false);
        linkingAndInitializingAllEditTexts();
        checkFromSharedPreferencesForClassId();

        return v;
    }


    private void linkingAndInitializingAllEditTexts() {

        tvUpdatedOn=(TextView)v.findViewById(R.id.fragment_default_timetable_tv_lastupdated);
        tvCRName=(TextView)v.findViewById(R.id.fragment_default_timetable_tv_crname);
        tvCRContact=(TextView)v.findViewById(R.id.fragment_default_timetable_tv_crcontactnumber);

        et_mon_1=(TextView)v.findViewById(R.id.et_monday_1);
        et_mon_2=(TextView)v.findViewById(R.id.et_monday_2);
        et_mon_3=(TextView)v.findViewById(R.id.et_monday_3);
        et_mon_4=(TextView)v.findViewById(R.id.et_monday_4);
        et_mon_5=(TextView)v.findViewById(R.id.et_monday_5);
        et_mon_6=(TextView)v.findViewById(R.id.et_monday_6);
        et_mon_7=(TextView)v.findViewById(R.id.et_monday_7);
        et_mon_8=(TextView)v.findViewById(R.id.et_monday_8);
        et_mon_9=(TextView)v.findViewById(R.id.et_monday_9);


        et_tue_1=(TextView)v.findViewById(R.id.et_tuesday_1);
        et_tue_2=(TextView)v.findViewById(R.id.et_tuesday_2);
        et_tue_3=(TextView)v.findViewById(R.id.et_tuesday_3);
        et_tue_4=(TextView)v.findViewById(R.id.et_tuesday_4);
        et_tue_5=(TextView)v.findViewById(R.id.et_tuesday_5);
        et_tue_6=(TextView)v.findViewById(R.id.et_tuesday_6);
        et_tue_7=(TextView)v.findViewById(R.id.et_tuesday_7);
        et_tue_8=(TextView)v.findViewById(R.id.et_tuesday_8);
        et_tue_9=(TextView)v.findViewById(R.id.et_tuesday_9);

        et_wed_1=(TextView)v.findViewById(R.id.et_wednesday_1);
        et_wed_2=(TextView)v.findViewById(R.id.et_wednesday_2);
        et_wed_3=(TextView)v.findViewById(R.id.et_wednesday_3);
        et_wed_4=(TextView)v.findViewById(R.id.et_wednesday_4);
        et_wed_5=(TextView)v.findViewById(R.id.et_wednesday_5);
        et_wed_6=(TextView)v.findViewById(R.id.et_wednesday_6);
        et_wed_7=(TextView)v.findViewById(R.id.et_wednesday_7);
        et_wed_8=(TextView)v.findViewById(R.id.et_wednesday_8);
        et_wed_9=(TextView)v.findViewById(R.id.et_wednesday_9);

        et_thu_1=(TextView)v.findViewById(R.id.et_thursday_1);
        et_thu_2=(TextView)v.findViewById(R.id.et_thursday_2);
        et_thu_3=(TextView)v.findViewById(R.id.et_thursday_3);
        et_thu_4=(TextView)v.findViewById(R.id.et_thursday_4);
        et_thu_5=(TextView)v.findViewById(R.id.et_thursday_5);
        et_thu_6=(TextView)v.findViewById(R.id.et_thursday_6);
        et_thu_7=(TextView)v.findViewById(R.id.et_thursday_7);
        et_thu_8=(TextView)v.findViewById(R.id.et_thursday_8);
        et_thu_9=(TextView)v.findViewById(R.id.et_thursday_9);

        et_fri_1=(TextView)v.findViewById(R.id.et_friday_1);
        et_fri_2=(TextView)v.findViewById(R.id.et_friday_2);
        et_fri_3=(TextView)v.findViewById(R.id.et_friday_3);
        et_fri_4=(TextView)v.findViewById(R.id.et_friday_4);
        et_fri_5=(TextView)v.findViewById(R.id.et_friday_5);
        et_fri_6=(TextView)v.findViewById(R.id.et_friday_6);
        et_fri_7=(TextView)v.findViewById(R.id.et_friday_7);
        et_fri_8=(TextView)v.findViewById(R.id.et_friday_8);
        et_fri_9=(TextView)v.findViewById(R.id.et_friday_9);
        
    }

    private void checkFromSharedPreferencesForClassId() {
        SharedPreferencesFiles sharedPreferencesFiles=new SharedPreferencesFiles();
        SharedPreferences sharedPreferences=getActivity().getSharedPreferences(sharedPreferencesFiles.getSPClassId(), 0);
        classId=sharedPreferences.getString(sharedPreferencesFiles.getClassId(), "Not set Yet");
        if(classId.contentEquals("Not set Yet"))
        {
            ////-----means we haven't set any class id from which we need to fetch timetable
            Toast.makeText(getActivity(), "Please set your client id first !!", Toast.LENGTH_SHORT).show();
            ////------
        }
        else
        {
            requestFetchTimeTableFromServer();
        }
    }




    /////////server request for fetching timetable

    public void requestFetchTimeTableFromServer() {
        progressDialog.setTitle("Fetching timetable");
        progressDialog.setMessage("Please Wait...");
        progressDialog.show();

        MyVolley.init(getActivity());
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
            String[] crDetailPartList=crDetailsString.split(sp.getMain());
            tvCRName.setText(crDetailPartList[0]);
            tvCRContact.setText(crDetailPartList[1]);
            //---setting updatedOn over

            fixedTimetableString=serverResponse.getString("field6");

            String[] singleTimePeriod=fixedTimetableString.split(sp.getMain());
            mondayTime=singleTimePeriod[1];
            tuesdayTime=singleTimePeriod[2];
            wednesdayTime=singleTimePeriod[3];
            thursdayTime=singleTimePeriod[4];
            fridayTime=singleTimePeriod[5];


            TT_Sqlite sqlite1=new TT_Sqlite(getContext(),1);
            SQLiteDatabase sb=sqlite1.getWritableDatabase();


            String query="DROP TABLE "+TT_Sqlite.tname+" IF EXISTS";
            sb.execSQL(query);

            TT_Sqlite sqlite=new TT_Sqlite(getContext(),1);
            SQLiteDatabase db=sqlite.getWritableDatabase();

            ///--monday
            slots=mondayTime.split(sp.getPrimary());
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
            slots=tuesdayTime.split(sp.getPrimary());
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
            slots=wednesdayTime.split(sp.getPrimary());
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
            slots=thursdayTime.split(sp.getPrimary());
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
            slots=fridayTime.split(sp.getPrimary());
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

            ///////Storing Default TimeTable in the database
            ContentValues cv1=new ContentValues();
            cv1.put(TT_Sqlite.one, (String) et_mon_1.getText());
            cv1.put(TT_Sqlite.two, (String) et_tue_1.getText());
            cv1.put(TT_Sqlite.three, (String) et_wed_1.getText());
            cv1.put(TT_Sqlite.four, (String) et_thu_1.getText());
            cv1.put(TT_Sqlite.five, (String) et_fri_1.getText());
            db.insert(TT_Sqlite.tname,null,cv1);


            ContentValues cv2=new ContentValues();
            cv2.put(TT_Sqlite.one, (String) et_mon_2.getText());
            cv2.put(TT_Sqlite.two, (String) et_tue_2.getText());
            cv2.put(TT_Sqlite.three, (String) et_wed_2.getText());
            cv2.put(TT_Sqlite.four, (String) et_thu_2.getText());
            cv2.put(TT_Sqlite.five, (String) et_fri_2.getText());
            db.insert(TT_Sqlite.tname,null,cv2);


            ContentValues cv3=new ContentValues();
            cv3.put(TT_Sqlite.one, (String) et_mon_3.getText());
            cv3.put(TT_Sqlite.two, (String) et_tue_3.getText());
            cv3.put(TT_Sqlite.three, (String) et_wed_3.getText());
            cv3.put(TT_Sqlite.four, (String) et_thu_3.getText());
            cv3.put(TT_Sqlite.five, (String) et_fri_3.getText());
            db.insert(TT_Sqlite.tname,null,cv3);


            ContentValues cv4=new ContentValues();
            cv4.put(TT_Sqlite.one, (String) et_mon_4.getText());
            cv4.put(TT_Sqlite.two, (String) et_tue_4.getText());
            cv4.put(TT_Sqlite.three, (String) et_wed_4.getText());
            cv4.put(TT_Sqlite.four, (String) et_thu_4.getText());
            cv4.put(TT_Sqlite.five, (String) et_fri_4.getText());
            db.insert(TT_Sqlite.tname,null,cv4);


            ContentValues cv5=new ContentValues();
            cv5.put(TT_Sqlite.one, (String) et_mon_5.getText());
            cv5.put(TT_Sqlite.two, (String) et_tue_5.getText());
            cv5.put(TT_Sqlite.three, (String) et_wed_5.getText());
            cv5.put(TT_Sqlite.four, (String) et_thu_5.getText());
            cv5.put(TT_Sqlite.five, (String) et_fri_5.getText());
            db.insert(TT_Sqlite.tname,null,cv5);


            ContentValues cv6=new ContentValues();
            cv6.put(TT_Sqlite.one, (String) et_mon_6.getText());
            cv6.put(TT_Sqlite.two, (String) et_tue_6.getText());
            cv6.put(TT_Sqlite.three, (String) et_wed_6.getText());
            cv6.put(TT_Sqlite.four, (String) et_thu_6.getText());
            cv6.put(TT_Sqlite.five, (String) et_fri_6.getText());
            db.insert(TT_Sqlite.tname,null,cv6);


            ContentValues cv7=new ContentValues();
            cv7.put(TT_Sqlite.one, (String) et_mon_7.getText());
            cv7.put(TT_Sqlite.two, (String) et_tue_7.getText());
            cv7.put(TT_Sqlite.three, (String) et_wed_7.getText());
            cv7.put(TT_Sqlite.four, (String) et_thu_7.getText());
            cv7.put(TT_Sqlite.five, (String) et_fri_7.getText());
            db.insert(TT_Sqlite.tname,null,cv7);


            ContentValues cv8=new ContentValues();
            cv8.put(TT_Sqlite.one, (String) et_mon_8.getText());
            cv8.put(TT_Sqlite.two, (String) et_tue_8.getText());
            cv8.put(TT_Sqlite.three, (String) et_wed_8.getText());
            cv8.put(TT_Sqlite.four, (String) et_thu_8.getText());
            cv8.put(TT_Sqlite.five, (String) et_fri_8.getText());
            db.insert(TT_Sqlite.tname,null,cv8);

            ContentValues cv9=new ContentValues();
            cv9.put(TT_Sqlite.one, (String) et_mon_9.getText());
            cv9.put(TT_Sqlite.two, (String) et_tue_9.getText());
            cv9.put(TT_Sqlite.three, (String) et_wed_9.getText());
            cv9.put(TT_Sqlite.four, (String) et_thu_9.getText());
            cv9.put(TT_Sqlite.five, (String) et_fri_9.getText());
            db.insert(TT_Sqlite.tname,null,cv9);




        } catch (JSONException e) {
            e.printStackTrace();
        }


    }

    private com.android.volley.Response.ErrorListener reqTimeTableErrorListener() {
        return new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.i("responsechecking", "error : " + error.toString());
                Toast.makeText(getActivity(), "Network error/ wrong class Id set", Toast.LENGTH_LONG).show();
                progressDialog.hide();
            }
        };
    }

    ////////timetable fetching part ends here




}
