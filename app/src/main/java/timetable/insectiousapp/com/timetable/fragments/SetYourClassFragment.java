package timetable.insectiousapp.com.timetable.fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

import mehdi.sakout.fancybuttons.FancyButton;
import timetable.insectiousapp.com.timetable.R;
import timetable.insectiousapp.com.timetable.others.SharedPreferencesFiles;
import timetable.insectiousapp.com.timetable.volley.MyVolley;


public class SetYourClassFragment extends Fragment implements View.OnClickListener{

    public SetYourClassFragment() {
        // Required empty public constructor
    }


    FancyButton btnSet;
    View v;
    EditText etClassId;
    ProgressDialog progressDialog;
    String classId;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v= inflater.inflate(R.layout.fragment_set_your_class, container, false);

        btnSet=(FancyButton)v.findViewById(R.id.fragment_set_your_class_btn_set);
        etClassId=(EditText)v.findViewById(R.id.fragment_set_your_class_et_classid);
        btnSet.setOnClickListener(this);

        progressDialog=new ProgressDialog(getActivity());
        progressDialog.setCancelable(false);


        SharedPreferencesFiles sharedPreferencesFiles=new SharedPreferencesFiles();
        SharedPreferences sharedPreferences=getActivity().getSharedPreferences(sharedPreferencesFiles.getSPClassId(), 0);
        String classId=sharedPreferences.getString(sharedPreferencesFiles.getClassId(), "Not set Yet");
        etClassId.setText(classId);


        return v;
    }


    @Override
    public void onClick(View v) {

         classId=etClassId.getText().toString();

        if(classId!=null&&!classId.isEmpty())
        {
            requestTimeTableFromServer(classId);
        }
        else
        {
            Toast.makeText(getActivity(), "Please enter a valid ClassId !!!",Toast.LENGTH_SHORT).show();
        }

    }


    /////////fetching timetable
    public void requestTimeTableFromServer(String classId) {
        progressDialog.setTitle("Verifying ClassId");
        progressDialog.setMessage("Please Wait...");
        progressDialog.show();

        String classTimeTableUrl="https://api.thingspeak.com/channels/"+classId+"/feed/last.json";

        MyVolley.init(getActivity());
        RequestQueue queue = MyVolley.getRequestQueue();

        StringRequest myReq = new StringRequest(Request.Method.GET, classTimeTableUrl
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

    private com.android.volley.Response.Listener<String> reqTimeTableSuccessListener() {
        return new com.android.volley.Response.Listener<String>() {
            @Override
            public void onResponse(String serverResponse) {

                progressDialog.hide();
                setSharedPreferenceForClassIdAndApiKey(classId);
            }
        };
    }



    private com.android.volley.Response.ErrorListener reqTimeTableErrorListener() {
        return new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Log.i("responsecheckingg", "Server Error response : " + error.toString());
                Toast.makeText(getActivity(), "Invalid Class Id !", Toast.LENGTH_LONG).show();
                progressDialog.hide();
            }
        };


    }
    ////////fetching timetable

        private void setSharedPreferenceForClassIdAndApiKey(String classId)
            {
            SharedPreferencesFiles sharedPreferencesFiles = new SharedPreferencesFiles();

            SharedPreferences sharedPreferences = getActivity().getSharedPreferences(sharedPreferencesFiles.getSPClassId(), Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString(sharedPreferencesFiles.getClassId(), classId);
            editor.commit();

            SharedPreferences sharedPreferences2=getActivity().getSharedPreferences(sharedPreferencesFiles.getSPWriteKey(), Context.MODE_PRIVATE);
            SharedPreferences.Editor editor1=sharedPreferences2.edit();
            editor1.putString(sharedPreferencesFiles.getWriteKey(), null);
            editor1.commit();

            Toast.makeText(getActivity(), "Class Id updated",Toast.LENGTH_SHORT).show();

                SharedPreferences sp1 =getActivity().getSharedPreferences("CheckDatabase",Context.MODE_PRIVATE);
                SharedPreferences.Editor editor2 = sp1.edit();
                editor2.putBoolean("CheckKey", false);
                editor2.commit();


                Intent i = getActivity().getBaseContext().getPackageManager()
                        .getLaunchIntentForPackage( getActivity().getBaseContext().getPackageName() );
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                getActivity().finish();
               startActivity(i);

//            DefaultTimetableFragment fragment=new DefaultTimetableFragment();
//            getActivity().getSupportFragmentManager().beginTransaction().add(R.id.mainactivity_framelayout,fragment).commit();
//            getActivity().setTitle("Default Timetable");

            }

}
