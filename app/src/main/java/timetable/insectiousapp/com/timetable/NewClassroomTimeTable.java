package timetable.insectiousapp.com.timetable;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.InputFilter;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

import mehdi.sakout.fancybuttons.FancyButton;

public class NewClassroomTimeTable extends AppCompatActivity implements View.OnClickListener{

    EditText et_mon_1, et_mon_2, et_mon_3, et_mon_4, et_mon_5, et_mon_6, et_mon_7, et_mon_8,et_mon_9;
    EditText et_tue_1, et_tue_2, et_tue_3, et_tue_4, et_tue_5, et_tue_6, et_tue_7, et_tue_8, et_tue_9;
    EditText et_wed_1, et_wed_2, et_wed_3, et_wed_4, et_wed_5, et_wed_6, et_wed_7, et_wed_8, et_wed_9;
    EditText et_thu_1, et_thu_2, et_thu_3, et_thu_4, et_thu_5, et_thu_6, et_thu_7, et_thu_8, et_thu_9;
    EditText et_fri_1, et_fri_2, et_fri_3, et_fri_4, et_fri_5, et_fri_6, et_fri_7, et_fri_8, et_fri_9;
    
    FancyButton btnSave;
    EditText etName, etContactNo;

    ProgressDialog progressDialog;

    String euro="€";
    String yen="¥";
    String fixedTimeTable=euro;
    String CRDetails;

    String writeKey;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_classroom_time_table);

        progressDialog=new ProgressDialog(this);
        progressDialog.setCancelable(false);

        Intent i=getIntent();
        writeKey=i.getStringExtra("write_api_key_from_detail_activity");
        linkingAndInitializingAllEditTexts();

    }

    private void linkingAndInitializingAllEditTexts() {

        btnSave=(FancyButton)findViewById(R.id.activity_new_classroom_time_table_btn_save);
        btnSave.setOnClickListener(this);

        etName=(EditText)findViewById(R.id.activity_new_classroom_time_table_et_name);
        etContactNo=(EditText)findViewById(R.id.activity_new_classroom_time_table_et_contactno);

        etName.setFilters(new InputFilter[] {new InputFilter.LengthFilter(30)});
        etContactNo.setFilters(new InputFilter[] {new InputFilter.LengthFilter(10)});

        et_mon_1=(EditText)findViewById(R.id.et_monday_1);
        et_mon_2=(EditText)findViewById(R.id.et_monday_2);
        et_mon_3=(EditText)findViewById(R.id.et_monday_3);
        et_mon_4=(EditText)findViewById(R.id.et_monday_4);
        et_mon_5=(EditText)findViewById(R.id.et_monday_5);
        et_mon_6=(EditText)findViewById(R.id.et_monday_6);
        et_mon_7=(EditText)findViewById(R.id.et_monday_7);
        et_mon_8=(EditText)findViewById(R.id.et_monday_8);
        et_mon_9=(EditText)findViewById(R.id.et_monday_9);


        et_tue_1=(EditText)findViewById(R.id.et_tuesday_1);
        et_tue_2=(EditText)findViewById(R.id.et_tuesday_2);
        et_tue_3=(EditText)findViewById(R.id.et_tuesday_3);
        et_tue_4=(EditText)findViewById(R.id.et_tuesday_4);
        et_tue_5=(EditText)findViewById(R.id.et_tuesday_5);
        et_tue_6=(EditText)findViewById(R.id.et_tuesday_6);
        et_tue_7=(EditText)findViewById(R.id.et_tuesday_7);
        et_tue_8=(EditText)findViewById(R.id.et_tuesday_8);
        et_tue_9=(EditText)findViewById(R.id.et_tuesday_9);

        et_wed_1=(EditText)findViewById(R.id.et_wednesday_1);
        et_wed_2=(EditText)findViewById(R.id.et_wednesday_2);
        et_wed_3=(EditText)findViewById(R.id.et_wednesday_3);
        et_wed_4=(EditText)findViewById(R.id.et_wednesday_4);
        et_wed_5=(EditText)findViewById(R.id.et_wednesday_5);
        et_wed_6=(EditText)findViewById(R.id.et_wednesday_6);
        et_wed_7=(EditText)findViewById(R.id.et_wednesday_7);
        et_wed_8=(EditText)findViewById(R.id.et_wednesday_8);
        et_wed_9=(EditText)findViewById(R.id.et_wednesday_9);

        et_thu_1=(EditText)findViewById(R.id.et_thursday_1);
        et_thu_2=(EditText)findViewById(R.id.et_thursday_2);
        et_thu_3=(EditText)findViewById(R.id.et_thursday_3);
        et_thu_4=(EditText)findViewById(R.id.et_thursday_4);
        et_thu_5=(EditText)findViewById(R.id.et_thursday_5);
        et_thu_6=(EditText)findViewById(R.id.et_thursday_6);
        et_thu_7=(EditText)findViewById(R.id.et_thursday_7);
        et_thu_8=(EditText)findViewById(R.id.et_thursday_8);
        et_thu_9=(EditText)findViewById(R.id.et_thursday_9);

        et_fri_1=(EditText)findViewById(R.id.et_friday_1);
        et_fri_2=(EditText)findViewById(R.id.et_friday_2);
        et_fri_3=(EditText)findViewById(R.id.et_friday_3);
        et_fri_4=(EditText)findViewById(R.id.et_friday_4);
        et_fri_5=(EditText)findViewById(R.id.et_friday_5);
        et_fri_6=(EditText)findViewById(R.id.et_friday_6);
        et_fri_7=(EditText)findViewById(R.id.et_friday_7);
        et_fri_8=(EditText)findViewById(R.id.et_friday_8);
        et_fri_9=(EditText)findViewById(R.id.et_friday_9);

        et_mon_1.setFilters(new InputFilter[] {new InputFilter.AllCaps(), new InputFilter.LengthFilter(17)});
        et_mon_2.setFilters(new InputFilter[] {new InputFilter.AllCaps(), new InputFilter.LengthFilter(17)});
        et_mon_3.setFilters(new InputFilter[] {new InputFilter.AllCaps(), new InputFilter.LengthFilter(17)});
        et_mon_4.setFilters(new InputFilter[] {new InputFilter.AllCaps(), new InputFilter.LengthFilter(17)});
        et_mon_5.setFilters(new InputFilter[] {new InputFilter.AllCaps(), new InputFilter.LengthFilter(17)});
        et_mon_6.setFilters(new InputFilter[] {new InputFilter.AllCaps(), new InputFilter.LengthFilter(17)});
        et_mon_7.setFilters(new InputFilter[] {new InputFilter.AllCaps(), new InputFilter.LengthFilter(17)});
        et_mon_8.setFilters(new InputFilter[] {new InputFilter.AllCaps(), new InputFilter.LengthFilter(17)});
        et_mon_9.setFilters(new InputFilter[] {new InputFilter.AllCaps(), new InputFilter.LengthFilter(17)});

        et_tue_1.setFilters(new InputFilter[] {new InputFilter.AllCaps(), new InputFilter.LengthFilter(17)});
        et_tue_2.setFilters(new InputFilter[] {new InputFilter.AllCaps(), new InputFilter.LengthFilter(17)});
        et_tue_3.setFilters(new InputFilter[] {new InputFilter.AllCaps(), new InputFilter.LengthFilter(17)});
        et_tue_4.setFilters(new InputFilter[] {new InputFilter.AllCaps(), new InputFilter.LengthFilter(17)});
        et_tue_5.setFilters(new InputFilter[] {new InputFilter.AllCaps(), new InputFilter.LengthFilter(17)});
        et_tue_6.setFilters(new InputFilter[] {new InputFilter.AllCaps(), new InputFilter.LengthFilter(17)});
        et_tue_7.setFilters(new InputFilter[] {new InputFilter.AllCaps(), new InputFilter.LengthFilter(17)});
        et_tue_8.setFilters(new InputFilter[] {new InputFilter.AllCaps(), new InputFilter.LengthFilter(17)});
        et_tue_9.setFilters(new InputFilter[] {new InputFilter.AllCaps(), new InputFilter.LengthFilter(17)});

        et_wed_1.setFilters(new InputFilter[] {new InputFilter.AllCaps(), new InputFilter.LengthFilter(17)});
        et_wed_2.setFilters(new InputFilter[] {new InputFilter.AllCaps(), new InputFilter.LengthFilter(17)});
        et_wed_3.setFilters(new InputFilter[] {new InputFilter.AllCaps(), new InputFilter.LengthFilter(17)});
        et_wed_4.setFilters(new InputFilter[] {new InputFilter.AllCaps(), new InputFilter.LengthFilter(17)});
        et_wed_5.setFilters(new InputFilter[] {new InputFilter.AllCaps(), new InputFilter.LengthFilter(17)});
        et_wed_6.setFilters(new InputFilter[] {new InputFilter.AllCaps(), new InputFilter.LengthFilter(17)});
        et_wed_7.setFilters(new InputFilter[] {new InputFilter.AllCaps(), new InputFilter.LengthFilter(17)});
        et_wed_8.setFilters(new InputFilter[] {new InputFilter.AllCaps(), new InputFilter.LengthFilter(17)});
        et_wed_9.setFilters(new InputFilter[] {new InputFilter.AllCaps(), new InputFilter.LengthFilter(17)});

        et_thu_1.setFilters(new InputFilter[] {new InputFilter.AllCaps(), new InputFilter.LengthFilter(17)});
        et_thu_2.setFilters(new InputFilter[] {new InputFilter.AllCaps(), new InputFilter.LengthFilter(17)});
        et_thu_3.setFilters(new InputFilter[] {new InputFilter.AllCaps(), new InputFilter.LengthFilter(17)});
        et_thu_4.setFilters(new InputFilter[] {new InputFilter.AllCaps(), new InputFilter.LengthFilter(17)});
        et_thu_5.setFilters(new InputFilter[] {new InputFilter.AllCaps(), new InputFilter.LengthFilter(17)});
        et_thu_6.setFilters(new InputFilter[] {new InputFilter.AllCaps(), new InputFilter.LengthFilter(17)});
        et_thu_7.setFilters(new InputFilter[] {new InputFilter.AllCaps(), new InputFilter.LengthFilter(17)});
        et_thu_8.setFilters(new InputFilter[] {new InputFilter.AllCaps(), new InputFilter.LengthFilter(17)});
        et_thu_9.setFilters(new InputFilter[] {new InputFilter.AllCaps(), new InputFilter.LengthFilter(17)});

        et_fri_1.setFilters(new InputFilter[] {new InputFilter.AllCaps(), new InputFilter.LengthFilter(17)});
        et_fri_2.setFilters(new InputFilter[] {new InputFilter.AllCaps(), new InputFilter.LengthFilter(17)});
        et_fri_3.setFilters(new InputFilter[] {new InputFilter.AllCaps(), new InputFilter.LengthFilter(17)});
        et_fri_4.setFilters(new InputFilter[] {new InputFilter.AllCaps(), new InputFilter.LengthFilter(17)});
        et_fri_5.setFilters(new InputFilter[] {new InputFilter.AllCaps(), new InputFilter.LengthFilter(17)});
        et_fri_6.setFilters(new InputFilter[] {new InputFilter.AllCaps(), new InputFilter.LengthFilter(17)});
        et_fri_7.setFilters(new InputFilter[] {new InputFilter.AllCaps(), new InputFilter.LengthFilter(17)});
        et_fri_8.setFilters(new InputFilter[] {new InputFilter.AllCaps(), new InputFilter.LengthFilter(17)});
        et_fri_9.setFilters(new InputFilter[] {new InputFilter.AllCaps(), new InputFilter.LengthFilter(17)});

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



        fixedTimeTable+=et_mon_1.getText().toString()+yen+et_mon_2.getText().toString()+yen+et_mon_3.getText().toString()+yen+et_mon_4.getText().toString()+yen+et_mon_5.getText().toString()+yen+et_mon_6.getText().toString()+yen+et_mon_7.getText().toString()+yen+et_mon_8.getText().toString()+yen+et_mon_9.getText().toString()+euro;


        fixedTimeTable+=et_tue_1.getText().toString()+yen+et_tue_2.getText().toString()+yen+et_tue_3.getText().toString()+yen+et_tue_4.getText().toString()+yen+et_tue_5.getText().toString()+yen+et_tue_6.getText().toString()+yen+et_tue_7.getText().toString()+yen+et_tue_8.getText().toString()+yen+et_tue_9.getText().toString()+euro;

        fixedTimeTable+=et_wed_1.getText().toString()+yen+et_wed_2.getText().toString()+yen+et_wed_3.getText().toString()+yen+et_wed_4.getText().toString()+yen+et_wed_5.getText().toString()+yen+et_wed_6.getText().toString()+yen+et_wed_7.getText().toString()+yen+et_wed_8.getText().toString()+yen+et_wed_9.getText().toString()+euro;

        fixedTimeTable+=et_thu_1.getText().toString()+yen+et_thu_2.getText().toString()+yen+et_thu_3.getText().toString()+yen+et_thu_4.getText().toString()+yen+et_thu_5.getText().toString()+yen+et_thu_6.getText().toString()+yen+et_thu_7.getText().toString()+yen+et_thu_8.getText().toString()+yen+et_thu_9.getText().toString()+euro;

        fixedTimeTable+=et_fri_1.getText().toString()+yen+et_fri_2.getText().toString()+yen+et_fri_3.getText().toString()+yen+et_fri_4.getText().toString()+yen+et_fri_5.getText().toString()+yen+et_fri_6.getText().toString()+yen+et_fri_7.getText().toString()+yen+et_fri_8.getText().toString()+yen+et_fri_9.getText().toString()+euro;

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
        progressDialog.setTitle("Saving timetable");
        progressDialog.setMessage("Please Wait...");
        progressDialog.show();

         String serverPostDataUrl="http://api.thingspeak.com/update?key="+writeKey+"&field6="+fixedTimeTable+"&field7="+CRDetails;


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

        myReq.setRetryPolicy(new DefaultRetryPolicy(5000, 1, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
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
                }
                progressDialog.hide();

            }
        };
    }

    private com.android.volley.Response.ErrorListener reqCreateNewAccountErrorListener() {
        return new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Log.i("responsecheckingg", "Server Error response : " + error.toString());
                Toast.makeText(getApplicationContext(), "Cannot save time table", Toast.LENGTH_LONG).show();
                progressDialog.hide();
            }
        };


    }



}
