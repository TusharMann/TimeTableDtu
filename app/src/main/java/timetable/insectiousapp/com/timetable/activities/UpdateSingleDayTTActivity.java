package timetable.insectiousapp.com.timetable.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import timetable.insectiousapp.com.timetable.R;
import timetable.insectiousapp.com.timetable.volley.MyVolley;

public class UpdateSingleDayTTActivity extends AppCompatActivity {

    String classId;
    String writeKey;
    int dayNo;

    EditText et1, et2, et3, et4, et5, et6, et7, et8, et9;
    TextView tvLastUpdatedOn, tvDayName;
    ProgressDialog progressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_single_day_tt);

        progressDialog=new ProgressDialog(this);

        RecievingDataAndChecking();
        requestTimeTableFromServer();
        initializingViews();

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
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    /////////server request for creating a new channel

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


                Log.i("responsecheckingg", "Server response : " + serverResponse.toString());
                progressDialog.hide();

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


    ////////Creating a new channel part ends here


}
