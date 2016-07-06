package timetable.insectiousapp.com.timetable.activities;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.InputFilter;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
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
import timetable.insectiousapp.com.timetable.others.SharedPreferencesFiles;
import timetable.insectiousapp.com.timetable.others.SpecialSymbolsAndOthers;
import timetable.insectiousapp.com.timetable.volley.MyVolley;

public class UpdateDefaultTimeTableActivity extends AppCompatActivity implements View.OnClickListener{

    EditText et_mon_1, et_mon_2, et_mon_3, et_mon_4, et_mon_5, et_mon_6, et_mon_7, et_mon_8,et_mon_9;
    EditText et_tue_1, et_tue_2, et_tue_3, et_tue_4, et_tue_5, et_tue_6, et_tue_7, et_tue_8, et_tue_9;
    EditText et_wed_1, et_wed_2, et_wed_3, et_wed_4, et_wed_5, et_wed_6, et_wed_7, et_wed_8, et_wed_9;
    EditText et_thu_1, et_thu_2, et_thu_3, et_thu_4, et_thu_5, et_thu_6, et_thu_7, et_thu_8, et_thu_9;
    EditText et_fri_1, et_fri_2, et_fri_3, et_fri_4, et_fri_5, et_fri_6, et_fri_7, et_fri_8, et_fri_9;

    FancyButton btnSave;
    EditText etName, etContactNo;
    String regExpression="(\\r|\\n|\\r\\n)+";

    EditText[][] etMon=new EditText[5][10];

    String[] strMon=new String[10];

    //ProgressDialog progressDialog;

    String euro="€";
    String yen="¥";
    String fixedTimeTable=euro;
    String CRDetails;
    ProgressDialog progressDialog;

    String writeKey, classId;
    String field1, field2, field3, field4, field5, field6, field7, field8;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_time_table);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        progressDialog=new ProgressDialog(this);
        progressDialog.setCancelable(false);


        Log.i("crashbug", "update default timetable OnCreate");

        //progressDialog=new ProgressDialog(this);
        //progressDialog.setCancelable(false);

        SharedPreferencesFiles SPF=new SharedPreferencesFiles();
        //for class id
        SharedPreferences sharedPreferences=getSharedPreferences(SPF.getSPClassId(), 0);
        classId=sharedPreferences.getString(SPF.getClassId(), "Not set yet");
        //
        //for write key
        SharedPreferences sharedPreferences2=getSharedPreferences(SPF.getSPWriteKey(), 0);
        writeKey=sharedPreferences2.getString(SPF.getWriteKey(), "Not set yet");
        //


        linkingAndInitializingAllEditTexts();
        requestTimeTableFromServer();
    }

    private void linkingAndInitializingAllEditTexts() {

        btnSave=(FancyButton)findViewById(R.id.activity_new_classroom_time_table_btn_save);
        btnSave.setOnClickListener(this);

        etName=(EditText)findViewById(R.id.activity_new_classroom_time_table_et_name);
        etContactNo=(EditText)findViewById(R.id.activity_new_classroom_time_table_et_contactno);

        etName.setFilters(new InputFilter[] {new InputFilter.LengthFilter(30)});
        etContactNo.setFilters(new InputFilter[] {new InputFilter.LengthFilter(10)});

        for(int i=1; i<=5; i++)
        {
            for (int j = 1; j <= 9; j++) {
                //for monday
                int ressourceId = getResources().getIdentifier(
                        "et_" + i + "_" + j,
                        "id",
                        getApplicationContext().getPackageName());
                etMon[i][j] = (EditText) findViewById(ressourceId);
            }
        }

        for(int i=1; i<=5; i++) {
            for (int j = 1; j <= 9; j++)
            {
                etMon[i][j].setFilters(new InputFilter[]{new InputFilter.AllCaps(), new InputFilter.LengthFilter(17)});
            }
        }

     
    }


    @Override
    public void onClick(View v) {

        checkforAllEditTexts();

    }

    private void checkforAllEditTexts() {
        String name=etName.getText().toString();
        String number=etContactNo.getText().toString();
        if(name.isEmpty()||name==null)
        {
            Toast.makeText(this, "Enter your name", Toast.LENGTH_SHORT).show();
        }else if(number.isEmpty()||number==null)
        {
            Toast.makeText(this, "Enter your contact no", Toast.LENGTH_SHORT).show();
        }else
        {
            //means contact deatails are entered now we need to upload the data to the server
            getInputFromEditTextsToUpload();
            requestPostDataOnServer();
        }
    }

    private void getInputFromEditTextsToUpload() {

        /////////////////  MONDAY ////////////////////////

        for(int i=1; i<=9; i++)
        {
            strMon[i]=etMon[i].getText().toString();
            strMon[i]=strMon[i].replaceAll(regExpression, "");
        }

//        String mon_1=et_mon_1.getText().toString();
//        String mon_2=et_mon_2.getText().toString();
//        String mon_3=et_mon_3.getText().toString();
//        String mon_4=et_mon_4.getText().toString();
//        String mon_5=et_mon_5.getText().toString();
//        String mon_6=et_mon_6.getText().toString();
//        String mon_7=et_mon_7.getText().toString();
//        String mon_8=et_mon_8.getText().toString();
//        String mon_9=et_mon_9.getText().toString();



//        String s_mon_l = mon_1.replaceAll(regExpression, "");
//        String s_mon_2 = mon_2.replaceAll(regExpression, "");
//        String s_mon_3 =mon_3.replaceAll(regExpression, "");
//        String s_mon_4 =mon_4.replaceAll(regExpression, "");
//        String s_mon_5 =mon_5.replaceAll(regExpression, "");
//        String s_mon_6 =mon_6.replaceAll(regExpression, "");
//        String s_mon_7 =mon_7.replaceAll(regExpression, "");
//        String s_mon_8 =mon_8.replaceAll(regExpression, "");
//        String s_mon_9 =mon_9.replaceAll(regExpression, "");

        /////////////////  TUESDAY ////////////////////////
        String tue_1=et_tue_1.getText().toString();
        String tue_2=et_tue_2.getText().toString();
        String tue_3=et_tue_3.getText().toString();
        String tue_4=et_tue_4.getText().toString();
        String tue_5=et_tue_5.getText().toString();
        String tue_6=et_tue_6.getText().toString();
        String tue_7=et_tue_7.getText().toString();
        String tue_8=et_tue_8.getText().toString();
        String tue_9=et_tue_9.getText().toString();

        String s_tue_l = tue_1.replaceAll(regExpression, "");
        String s_tue_2 = tue_2.replaceAll(regExpression, "");
        String s_tue_3 =tue_3.replaceAll(regExpression, "");
        String s_tue_4 =tue_4.replaceAll(regExpression, "");
        String s_tue_5 =tue_5.replaceAll(regExpression, "");
        String s_tue_6 =tue_6.replaceAll(regExpression, "");
        String s_tue_7 =tue_7.replaceAll(regExpression, "");
        String s_tue_8 =tue_8.replaceAll(regExpression, "");
        String s_tue_9 =tue_9.replaceAll(regExpression, "");

        /////////////////  WEDNESDAY ////////////////////////
        String wed_1=et_wed_1.getText().toString();
        String wed_2=et_wed_2.getText().toString();
        String wed_3=et_wed_3.getText().toString();
        String wed_4=et_wed_4.getText().toString();
        String wed_5=et_wed_5.getText().toString();
        String wed_6=et_wed_6.getText().toString();
        String wed_7=et_wed_7.getText().toString();
        String wed_8=et_wed_8.getText().toString();
        String wed_9=et_wed_9.getText().toString();

        String s_wed_l = wed_1.replaceAll(regExpression, "");
        String s_wed_2 = wed_2.replaceAll(regExpression, "");
        String s_wed_3 =wed_3.replaceAll(regExpression, "");
        String s_wed_4 =wed_4.replaceAll(regExpression, "");
        String s_wed_5 =wed_5.replaceAll(regExpression, "");
        String s_wed_6 =wed_6.replaceAll(regExpression, "");
        String s_wed_7 =wed_7.replaceAll(regExpression, "");
        String s_wed_8 =wed_8.replaceAll(regExpression, "");
        String s_wed_9 =wed_9.replaceAll(regExpression, "");

        /////////////////  THURSDAY ////////////////////////
        String thu_1=et_thu_1.getText().toString();
        String thu_2=et_thu_2.getText().toString();
        String thu_3=et_thu_3.getText().toString();
        String thu_4=et_thu_4.getText().toString();
        String thu_5=et_thu_5.getText().toString();
        String thu_6=et_thu_6.getText().toString();
        String thu_7=et_thu_7.getText().toString();
        String thu_8=et_thu_8.getText().toString();
        String thu_9=et_thu_9.getText().toString();

        String s_thu_l = thu_1.replaceAll(regExpression, "");
        String s_thu_2 = thu_2.replaceAll(regExpression, "");
        String s_thu_3 =thu_3.replaceAll(regExpression, "");
        String s_thu_4 =thu_4.replaceAll(regExpression, "");
        String s_thu_5 =thu_5.replaceAll(regExpression, "");
        String s_thu_6 =thu_6.replaceAll(regExpression, "");
        String s_thu_7 =thu_7.replaceAll(regExpression, "");
        String s_thu_8 =thu_8.replaceAll(regExpression, "");
        String s_thu_9 =thu_9.replaceAll(regExpression, "");

        /////////////////  FRIDAY ////////////////////////
        String fri_1=et_fri_1.getText().toString();
        String fri_2=et_fri_2.getText().toString();
        String fri_3=et_fri_3.getText().toString();
        String fri_4=et_fri_4.getText().toString();
        String fri_5=et_fri_5.getText().toString();
        String fri_6=et_fri_6.getText().toString();
        String fri_7=et_fri_7.getText().toString();
        String fri_8=et_fri_8.getText().toString();
        String fri_9=et_fri_9.getText().toString();

        String s_fri_l = fri_1.replaceAll(regExpression, "");
        String s_fri_2 = fri_2.replaceAll(regExpression, "");
        String s_fri_3 =fri_3.replaceAll(regExpression, "");
        String s_fri_4 =fri_4.replaceAll(regExpression, "");
        String s_fri_5 =fri_5.replaceAll(regExpression, "");
        String s_fri_6 =fri_6.replaceAll(regExpression, "");
        String s_fri_7 =fri_7.replaceAll(regExpression, "");
        String s_fri_8 =fri_8.replaceAll(regExpression, "");
        String s_fri_9 =fri_9.replaceAll(regExpression, "");

        int i;
        for(i=1; i<=8; i++)
        {
            fixedTimeTable=fixedTimeTable+strMon[i]+yen;
        }
        fixedTimeTable=fixedTimeTable+strMon[i]+euro;

        //fixedTimeTable+=s_mon_l+yen+s_mon_2+yen+s_mon_3+yen+s_mon_4+yen+s_mon_5+yen+s_mon_6+yen+s_mon_7+yen+s_mon_8+yen+s_mon_9+euro;

        fixedTimeTable+=s_tue_l+yen+s_tue_2+yen+s_tue_3+yen+s_tue_4+yen+s_tue_5+yen+s_tue_6+yen+s_tue_7+yen+s_tue_8+yen+s_tue_9+euro;

        fixedTimeTable+=s_wed_l+yen+s_wed_2+yen+s_wed_3+yen+s_wed_4+yen+s_wed_5+yen+s_wed_6+yen+s_wed_7+yen+s_wed_8+yen+s_wed_9+euro;

        fixedTimeTable+=s_thu_l+yen+s_thu_2+yen+s_thu_3+yen+s_thu_4+yen+s_thu_5+yen+s_thu_6+yen+s_thu_7+yen+s_thu_8+yen+s_thu_9+euro;

        fixedTimeTable+=s_fri_l+yen+s_fri_2+yen+s_fri_3+yen+s_fri_4+yen+s_fri_5+yen+s_fri_6+yen+s_fri_7+yen+s_fri_8+yen+s_fri_9+euro;

        CRDetails=etName.getText().toString()+euro+etContactNo.getText().toString();

        String[] singleTimePeriod=fixedTimeTable.split(euro);
        for(String t: singleTimePeriod) {
            Log.i("daystring", "day string"+t);
            String[] singleSlot=t.split(yen);
            for(String u: singleSlot)
            {
                Log.i("daystringg", "slot String"+u);
            }
        }


    }


    public void requestPostDataOnServer() {
        //progressDialog.setTitle("Saving timetable");
        //progressDialog.setMessage("Please Wait...");
        //progressDialog.show();

        String serverPostDataUrl="http://api.thingspeak.com/update?key="+writeKey+"&field6="+fixedTimeTable+"&field7="+CRDetails;
        Log.i("sendingtimetable", "timetable:"+fixedTimeTable);
        serverPostDataUrl=serverPostDataUrl+"&field1="+field1+"&field2="+field2+"&field3="+field3+"&field4="+field4+"&field5="+field5+"&field8="+field8;


        MyVolley.init(this);
        RequestQueue queue = MyVolley.getRequestQueue();

        StringRequest myReq = new StringRequest(Request.Method.POST, serverPostDataUrl
                , reqCreateNewAccountSuccessListener(), reqCreateNewAccountErrorListener()) {

//            public String getBodyContentType() {
//                return "application/json; charset=utf-8";
//            }
//
//            @Override
//            public Map<String, String> getHeaders() throws AuthFailureError {
//                Map<String, String> headers = new HashMap<String, String>();
//
//                headers.put("Content-Type", "application/json");
//
//                return headers;
//            }

            protected Map<String, String> getParams() throws com.android.volley.AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();

//                params.put("api_key", authorizationKey);
//                params.put("name", spYear.getSelectedItem().toString()+"-"+spBranch.getSelectedItem().toString()+"-"+spBatch.getSelectedItem().toString());
//                params.put("field1", "Field1");
//                params.put("field2", "Field2");
//                params.put("field3", "Field3");
//                params.put("field4", "Field4");
//                params.put("field5", "Field5");
//                params.put("field6", "Field6");
//                params.put("field7", "Field7");
//                params.put("field8", "Field8");
//                params.put("public_flag", "true");

                return params;
            }
        };

        myReq.setRetryPolicy(new DefaultRetryPolicy(2000, 1, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        queue.add(myReq);

    }

    private com.android.volley.Response.Listener<String> reqCreateNewAccountSuccessListener() {
        return new com.android.volley.Response.Listener<String>() {
            @Override
            public void onResponse(String serverResponse) {


                Log.i("responsecheckingg", "Server response : " + serverResponse.toString());
                if(Integer.parseInt(serverResponse)==0)
                {
                    Toast.makeText(getApplicationContext(), "Cannot save time tabl", Toast.LENGTH_LONG).show();
                }
                else
                {
                    Toast.makeText(getApplicationContext(), "Data saved successfully", Toast.LENGTH_LONG).show();
                    finish();
                }
                //progressDialog.hide();

            }
        };
    }

    private com.android.volley.Response.ErrorListener reqCreateNewAccountErrorListener() {
        return new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Log.i("responsecheckingg", "Server Error response : " + error.toString());
                Toast.makeText(getApplicationContext(), "Cannot save time table, Network Error/ Improper characters used", Toast.LENGTH_LONG).show();
                //progressDialog.hide();
            }
        };
    }




    /////////fetching timetable
    public void requestTimeTableFromServer() {

        progressDialog.setTitle("Fetching Timetable");
        progressDialog.setMessage("Please Wait...");
        progressDialog.show();

        // progressDialog.setTitle("Fetching Timetable");
        //progressDialog.setMessage("Please Wait...");
       // progressDialog.show();

        String classTimeTableUrl="https://api.thingspeak.com/channels/"+classId+"/feed/last.json";

        Log.i("classid", classId);

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

                try {
                    field1=serverResponse.getString("field1");
                    field2=serverResponse.getString("field2");
                    field3=serverResponse.getString("field3");
                    field4=serverResponse.getString("field4");
                    field5=serverResponse.getString("field5");
                    field6=serverResponse.getString("field6");
                    field7=serverResponse.getString("field7");
                    field8=serverResponse.getString("field8");
                    btnSave.setEnabled(true);
                    initializeAllEditTextsWithDefaultTimeTable(field6, field7);

                } catch (JSONException e) {
                    e.printStackTrace();
                }

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
                btnSave.setEnabled(false);
                Toast.makeText(getApplicationContext(), "Cannot fetch timetable , network/class id error", Toast.LENGTH_LONG).show();
                progressDialog.hide();
            }
        };


    }
    ////////fetching timetable

    private void initializeAllEditTextsWithDefaultTimeTable(String defaultTimeTableString, String CrDetails) {

        SpecialSymbolsAndOthers sp=new SpecialSymbolsAndOthers();
        String[] DaysList=defaultTimeTableString.split(sp.getMain());

        String singleDay;
        String[] singleDaySlotsList;

        ////monday
        singleDay=DaysList[1];
        singleDaySlotsList=singleDay.split(sp.getPrimary());

        for(int i=1; i<=9; i++)
        {
            etMon[i].setText(singleDaySlotsList[i-1]);
        }

//        et_mon_1.setText(singleDaySlotsList[0]);
//        et_mon_2.setText(singleDaySlotsList[1]);
//        et_mon_3.setText(singleDaySlotsList[2]);
//        et_mon_4.setText(singleDaySlotsList[3]);
//        et_mon_5.setText(singleDaySlotsList[4]);
//        et_mon_6.setText(singleDaySlotsList[5]);
//        et_mon_7.setText(singleDaySlotsList[6]);
//        et_mon_8.setText(singleDaySlotsList[7]);
//        et_mon_9.setText(singleDaySlotsList[8]);
//        //--monday

        ////tuesday
        singleDay=DaysList[2];
        singleDaySlotsList=singleDay.split(sp.getPrimary());
        et_tue_1.setText(singleDaySlotsList[0]);
        et_tue_2.setText(singleDaySlotsList[1]);
        et_tue_3.setText(singleDaySlotsList[2]);
        et_tue_4.setText(singleDaySlotsList[3]);
        et_tue_5.setText(singleDaySlotsList[4]);
        et_tue_6.setText(singleDaySlotsList[5]);
        et_tue_7.setText(singleDaySlotsList[6]);
        et_tue_8.setText(singleDaySlotsList[7]);
        et_tue_9.setText(singleDaySlotsList[8]);
        //--tuesday

        ////wednesday
        singleDay=DaysList[3];
        singleDaySlotsList=singleDay.split(sp.getPrimary());
        et_wed_1.setText(singleDaySlotsList[0]);
        et_wed_2.setText(singleDaySlotsList[1]);
        et_wed_3.setText(singleDaySlotsList[2]);
        et_wed_4.setText(singleDaySlotsList[3]);
        et_wed_5.setText(singleDaySlotsList[4]);
        et_wed_6.setText(singleDaySlotsList[5]);
        et_wed_7.setText(singleDaySlotsList[6]);
        et_wed_8.setText(singleDaySlotsList[7]);
        et_wed_9.setText(singleDaySlotsList[8]);
        //--wednesday

        ////thursday
        singleDay=DaysList[4];
        singleDaySlotsList=singleDay.split(sp.getPrimary());
        et_thu_1.setText(singleDaySlotsList[0]);
        et_thu_2.setText(singleDaySlotsList[1]);
        et_thu_3.setText(singleDaySlotsList[2]);
        et_thu_4.setText(singleDaySlotsList[3]);
        et_thu_5.setText(singleDaySlotsList[4]);
        et_thu_6.setText(singleDaySlotsList[5]);
        et_thu_7.setText(singleDaySlotsList[6]);
        et_thu_8.setText(singleDaySlotsList[7]);
        et_thu_9.setText(singleDaySlotsList[8]);
        //--thursday

        ////friday
        singleDay=DaysList[5];
        singleDaySlotsList=singleDay.split(sp.getPrimary());
        et_fri_1.setText(singleDaySlotsList[0]);
        et_fri_2.setText(singleDaySlotsList[1]);
        et_fri_3.setText(singleDaySlotsList[2]);
        et_fri_4.setText(singleDaySlotsList[3]);
        et_fri_5.setText(singleDaySlotsList[4]);
        et_fri_6.setText(singleDaySlotsList[5]);
        et_fri_7.setText(singleDaySlotsList[6]);
        et_fri_8.setText(singleDaySlotsList[7]);
        et_fri_9.setText(singleDaySlotsList[8]);
        //--friday

        String[] details=CrDetails.split(sp.getMain());
        etName.setText(details[0]);
        etContactNo.setText(details[1]);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_activity_update_default_timetable, menu);
        return true;
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i("crashbug", "update default timetable OnResume");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.i("crashbug", "update default timetable OnPause");
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_refresh) {
                requestTimeTableFromServer();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }




}
