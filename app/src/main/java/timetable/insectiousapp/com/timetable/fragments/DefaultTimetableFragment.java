 package timetable.insectiousapp.com.timetable.fragments;


 import android.app.ProgressDialog;
 import android.content.ContentValues;
 import android.content.Context;
 import android.content.SharedPreferences;
 import android.database.Cursor;
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

 import mehdi.sakout.fancybuttons.FancyButton;
 import timetable.insectiousapp.com.timetable.R;
 import timetable.insectiousapp.com.timetable.Sqlite.TT_Sqlite;
 import timetable.insectiousapp.com.timetable.others.SharedPreferencesFiles;
 import timetable.insectiousapp.com.timetable.others.SpecialSymbolsAndOthers;
 import timetable.insectiousapp.com.timetable.volley.MyVolley;

/**
 * A simple {@link Fragment} subclass.
 */
public class DefaultTimetableFragment extends Fragment {


    public DefaultTimetableFragment() {
        // Required empty public constructor
    }
    int counter;
    View v;
    String classId;
    ProgressDialog progressDialog;
    String updatedOn, fixedTimetableString;
    String uDay, uMonth, uYear;
    String mondayTime, tuesdayTime, wednesdayTime, thursdayTime, fridayTime;
    String[] slots;
    FancyButton fancyButton;

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

        Log.i("Tag","onCreate");
        linkingAndInitializingAllEditTexts();
        checkFromSharedPreferencesForClassId();

        return v;
    }


    private void linkingAndInitializingAllEditTexts() {

        tvUpdatedOn=(TextView)v.findViewById(R.id.fragment_default_timetable_tv_lastupdated);
        tvCRName=(TextView)v.findViewById(R.id.fragment_default_timetable_tv_crname);
        tvCRContact=(TextView)v.findViewById(R.id.fragment_default_timetable_tv_crcontactnumber);

       fancyButton=(FancyButton)v.findViewById(R.id.fragment_default_timetable_refresh_btn);

        Log.i("Tag","function1");
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

//        SharedPreferencesFiles sf=new SharedPreferencesFiles();
//        SharedPreferences shared=getActivity().getSharedPreferences(sf.getcheck(),0);
//        String bol=shared.getString(sf.getkey(), "Not checked");
//        Log.i("Tag",bol);

        fancyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TT_Sqlite sqlite=new TT_Sqlite(getContext(),1);
                SQLiteDatabase db=sqlite.getWritableDatabase();

                db.execSQL("DELETE FROM "+TT_Sqlite.tname);
                db.execSQL("DELETE FROM "+TT_Sqlite.tdet);
                requestFetchTimeTableFromServer();
            }
        });


        SharedPreferences shared=getActivity().getSharedPreferences("CheckDatabase",Context.MODE_PRIVATE);
        Boolean bol=shared.getBoolean("CheckKey",false);

        if(classId.contentEquals("Not set Yet"))
        {
            ////-----means we haven't set any class id from which we need to fetch timetable
            Toast.makeText(getActivity(), "Please set your client id first !!", Toast.LENGTH_SHORT).show();
            ////------
        }
        else
        {
            Log.i("Tag","else");

//            if(counter!=1){
//                Log.i("Tager","counter0") ;
//                requestFetchTimeTableFromServer();
//            }


//            if(bol.contentEquals("Not checked")){
//                Log.i("Tag","before fetch");
                //requestFetchTimeTableFromServer();
         //   }

             if(bol){
                Log.i("Tager","bol is true");
                TT_Sqlite sqlite=new TT_Sqlite(getContext(),1);
                SQLiteDatabase db=sqlite.getWritableDatabase();

                String[] column={TT_Sqlite.one,TT_Sqlite.two,TT_Sqlite.three,TT_Sqlite.four,TT_Sqlite.five};
                Cursor cursor=db.query(TT_Sqlite.tname,column,null,null,null,null,null);
                int i=1;

                while (cursor.moveToNext()){
                    int index1=cursor.getColumnIndex(TT_Sqlite.one);
                    int index2=cursor.getColumnIndex(TT_Sqlite.two);
                    int index3=cursor.getColumnIndex(TT_Sqlite.three);
                    int index4=cursor.getColumnIndex(TT_Sqlite.four);
                    int index5=cursor.getColumnIndex(TT_Sqlite.five);

                    String day1,day2,day3,day4,day5;

                    day1=cursor.getString(index1);
                    day2=cursor.getString(index2);
                    day3=cursor.getString(index3);
                    day4=cursor.getString(index4);
                    day5=cursor.getString(index5);

                    Log.i("Tag",day1);

                    if(i==1){
                        et_mon_1.setText(day1);
                        et_tue_1.setText(day2);
                        et_wed_1.setText(day3);
                        et_thu_1.setText(day4);
                        et_fri_1.setText(day5);
                        i=2;
                    }

                    else if(i==2){
                        et_mon_2.setText(day1);
                        et_tue_2.setText(day2);
                        et_wed_2.setText(day3);
                        et_thu_2.setText(day4);
                        et_fri_2.setText(day5);
                        i=3;
                    }

                    else if(i==3){
                        et_mon_3.setText(day1);
                        et_tue_3.setText(day2);
                        et_wed_3.setText(day3);
                        et_thu_3.setText(day4);
                        et_fri_3.setText(day5);
                        i=4;
                    }

                    else if(i==4){
                        et_mon_4.setText(day1);
                        et_tue_4.setText(day2);
                        et_wed_4.setText(day3);
                        et_thu_4.setText(day4);
                        et_fri_4.setText(day5);
                        i=5;
                    }

                    else if(i==5){
                        et_mon_5.setText(day1);
                        et_tue_5.setText(day2);
                        et_wed_5.setText(day3);
                        et_thu_5.setText(day4);
                        et_fri_5.setText(day5);
                        i=6;
                    }

                    else if(i==6){
                        et_mon_6.setText(day1);
                        et_tue_6.setText(day2);
                        et_wed_6.setText(day3);
                        et_thu_6.setText(day4);
                        et_fri_6.setText(day5);
                        i=7;
                    }

                    else if(i==7){
                        et_mon_7.setText(day1);
                        et_tue_7.setText(day2);
                        et_wed_7.setText(day3);
                        et_thu_7.setText(day4);
                        et_fri_7.setText(day5);
                        i=8;
                    }

                    else if(i==8){
                        et_mon_8.setText(day1);
                        et_tue_8.setText(day2);
                        et_wed_8.setText(day3);
                        et_thu_8.setText(day4);
                        et_fri_8.setText(day5);
                        i=9;
                    }

                    else if(i==9){
                        et_mon_9.setText(day1);
                        et_tue_9.setText(day2);
                        et_wed_9.setText(day3);
                        et_thu_9.setText(day4);
                        et_fri_9.setText(day5);
                        i=10;
                    }

                }

                 String[] columns={TT_Sqlite.cname,TT_Sqlite.cno,TT_Sqlite.updon};
                 Cursor cursor1=db.query(TT_Sqlite.tdet,columns,null,null,null,null,null);

                 while (cursor1.moveToNext()){
                     int index1=cursor1.getColumnIndex(TT_Sqlite.cname);
                     int index2=cursor1.getColumnIndex(TT_Sqlite.cno);
                     int index3=cursor1.getColumnIndex(TT_Sqlite.updon);

                     String name,number,updateOn;
                     name=cursor1.getString(index1);
                     number=cursor1.getString(index2);
                     updateOn=cursor1.getString(index3);

                     if(i==10) {

                         tvCRName.setText(name);
                         tvCRContact.setText(number);
                         tvUpdatedOn.setText(updateOn);
                     }


                 }



             }

            else{

//            TT_Sqlite sqlite1=new TT_Sqlite(getContext(),1);
//            SQLiteDatabase sb=sqlite1.getWritableDatabase();
//
//
//            String query="DROP TABLE "+TT_Sqlite.tname+" IF EXISTS";
//            sb.execSQL(query);
//


                 requestFetchTimeTableFromServer();
             }

        }
 }




    /////////server request for fetching timetable

    public void requestFetchTimeTableFromServer() {

        progressDialog=new ProgressDialog(getActivity());
        progressDialog.setCancelable(false);

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

            TT_Sqlite sqlite=new TT_Sqlite(getContext(),1);
            SQLiteDatabase db=sqlite.getWritableDatabase();


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

                ContentValues cv2=new ContentValues();
                cv2.put(TT_Sqlite.cname,(String )tvCRName.getText());
                cv2.put(TT_Sqlite.cno,(String )tvCRContact.getText());
                cv2.put(TT_Sqlite.updon,(String )tvUpdatedOn.getText());

                db.insert(TT_Sqlite.tdet,null,cv2);
            }
            else
            {
                Toast.makeText(getActivity(), "Can't load CR Details", Toast.LENGTH_SHORT).show();
            }

//                SharedPreferencesFiles sharedPreferencesFiles = new SharedPreferencesFiles();
//                SharedPreferences sharedPreferences = getActivity().getSharedPreferences(sharedPreferencesFiles.getcheck(), Context.MODE_PRIVATE);
//                SharedPreferences.Editor editor = sharedPreferences.edit();
//                editor.putString(sharedPreferencesFiles.getkey(),"checked" );
//                editor.commit();

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

                ///////Storing Default TimeTable in the database
                Log.i("Tag", "Database Created");
                ContentValues cv1 = new ContentValues();
                cv1.put(TT_Sqlite.one, (String) et_mon_1.getText());
                cv1.put(TT_Sqlite.two, (String) et_tue_1.getText());
                cv1.put(TT_Sqlite.three, (String) et_wed_1.getText());
                cv1.put(TT_Sqlite.four, (String) et_thu_1.getText());
                cv1.put(TT_Sqlite.five, (String) et_fri_1.getText());
                db.insert(TT_Sqlite.tname, null, cv1);


                // ContentValues cv2=new ContentValues();
                cv1.put(TT_Sqlite.one, (String) et_mon_2.getText());
                cv1.put(TT_Sqlite.two, (String) et_tue_2.getText());
                cv1.put(TT_Sqlite.three, (String) et_wed_2.getText());
                cv1.put(TT_Sqlite.four, (String) et_thu_2.getText());
                cv1.put(TT_Sqlite.five, (String) et_fri_2.getText());
                db.insert(TT_Sqlite.tname, null, cv1);


                //ContentValues cv3=new ContentValues();
                cv1.put(TT_Sqlite.one, (String) et_mon_3.getText());
                cv1.put(TT_Sqlite.two, (String) et_tue_3.getText());
                cv1.put(TT_Sqlite.three, (String) et_wed_3.getText());
                cv1.put(TT_Sqlite.four, (String) et_thu_3.getText());
                cv1.put(TT_Sqlite.five, (String) et_fri_3.getText());
                db.insert(TT_Sqlite.tname, null, cv1);


                //ContentValues cv4=new ContentValues();
                cv1.put(TT_Sqlite.one, (String) et_mon_4.getText());
                cv1.put(TT_Sqlite.two, (String) et_tue_4.getText());
                cv1.put(TT_Sqlite.three, (String) et_wed_4.getText());
                cv1.put(TT_Sqlite.four, (String) et_thu_4.getText());
                cv1.put(TT_Sqlite.five, (String) et_fri_4.getText());
                db.insert(TT_Sqlite.tname, null, cv1);


                //ContentValues cv5=new ContentValues();
                cv1.put(TT_Sqlite.one, (String) et_mon_5.getText());
                cv1.put(TT_Sqlite.two, (String) et_tue_5.getText());
                cv1.put(TT_Sqlite.three, (String) et_wed_5.getText());
                cv1.put(TT_Sqlite.four, (String) et_thu_5.getText());
                cv1.put(TT_Sqlite.five, (String) et_fri_5.getText());
                db.insert(TT_Sqlite.tname, null, cv1);


                // ContentValues cv6=new ContentValues();
                cv1.put(TT_Sqlite.one, (String) et_mon_6.getText());
                cv1.put(TT_Sqlite.two, (String) et_tue_6.getText());
                cv1.put(TT_Sqlite.three, (String) et_wed_6.getText());
                cv1.put(TT_Sqlite.four, (String) et_thu_6.getText());
                cv1.put(TT_Sqlite.five, (String) et_fri_6.getText());
                db.insert(TT_Sqlite.tname, null, cv1);


                //ContentValues cv7=new ContentValues();
                cv1.put(TT_Sqlite.one, (String) et_mon_7.getText());
                cv1.put(TT_Sqlite.two, (String) et_tue_7.getText());
                cv1.put(TT_Sqlite.three, (String) et_wed_7.getText());
                cv1.put(TT_Sqlite.four, (String) et_thu_7.getText());
                cv1.put(TT_Sqlite.five, (String) et_fri_7.getText());
                db.insert(TT_Sqlite.tname, null, cv1);


                // ContentValues cv8=new ContentValues();
                cv1.put(TT_Sqlite.one, (String) et_mon_8.getText());
                cv1.put(TT_Sqlite.two, (String) et_tue_8.getText());
                cv1.put(TT_Sqlite.three, (String) et_wed_8.getText());
                cv1.put(TT_Sqlite.four, (String) et_thu_8.getText());
                cv1.put(TT_Sqlite.five, (String) et_fri_8.getText());
                db.insert(TT_Sqlite.tname, null, cv1);

                // ContentValues cv9=new ContentValues();
                cv1.put(TT_Sqlite.one, (String) et_mon_9.getText());
                cv1.put(TT_Sqlite.two, (String) et_tue_9.getText());
                cv1.put(TT_Sqlite.three, (String) et_wed_9.getText());
                cv1.put(TT_Sqlite.four, (String) et_thu_9.getText());
                cv1.put(TT_Sqlite.five, (String) et_fri_9.getText());
                db.insert(TT_Sqlite.tname, null, cv1);


                SharedPreferences sp1 = getActivity().getSharedPreferences("CheckDatabase", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sp1.edit();
                editor.putBoolean("CheckKey", true);
                editor.commit();

            }
            else
            {
                Toast.makeText(getActivity(), "No default TimeTable is found for corresponding ClassId", Toast.LENGTH_SHORT).show();
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
                Toast.makeText(getActivity(), "Network error/ wrong class Id set", Toast.LENGTH_LONG).show();
                progressDialog.hide();
            }
        };
    }

    ////////timetable fetching part ends here




}
