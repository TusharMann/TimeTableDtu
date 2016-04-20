package timetable.insectiousapp.com.timetable.fragments;


import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import mehdi.sakout.fancybuttons.FancyButton;
import timetable.insectiousapp.com.timetable.MyVolley;
import timetable.insectiousapp.com.timetable.R;
import timetable.insectiousapp.com.timetable.activities.NewClassroomDetailActivity;


public class CreateNewClassFragment extends Fragment implements View.OnClickListener, AdapterView.OnItemClickListener, AdapterView.OnItemSelectedListener {

    //this channel is made in chetan mann's account , which provides the account key of dtu's account.
    View v;
    String authorizationUrl = "https://api.thingspeak.com/channels/108367/feed/last.json";
    String authorizationKey = "blank";

    ProgressDialog progressDialog;
    FancyButton btnCreateClassroom;

    Spinner spYear, spBranch, spBatch;

    public CreateNewClassFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.fragment_create_new_client, container, false);

        btnCreateClassroom = (FancyButton) v.findViewById(R.id.fragment_create_new_client_btn_createclassroom);
        btnCreateClassroom.setOnClickListener(this);

        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setTitle("Authenticating");
        progressDialog.setMessage("Please Wait...");
        progressDialog.setCancelable(false);

        //now request for the authorization key to be fetched from server which is used for all other tasks.
        requestAuthorizationKeyFromServer();
        setAllSpinners();


        return v;
    }

    private void setAllSpinners() {
        spYear = (Spinner) v.findViewById(R.id.fragment_create_new_client_sp_year);
        spBranch = (Spinner) v.findViewById(R.id.fragment_create_new_client_sp_branch);
        spBatch = (Spinner) v.findViewById(R.id.fragment_create_new_client_sp_batch);

        spYear.setOnItemSelectedListener(this);
        spBranch.setOnItemSelectedListener(this);
        spBatch.setOnItemSelectedListener(this);

        List<String> brandlist = new ArrayList<String>();
        brandlist.add("2k12");
        brandlist.add("2k13");
        brandlist.add("2k14");
        brandlist.add("2k15");
        ArrayAdapter<String> dataAdapterBrand = new ArrayAdapter<String>(getActivity(), R.layout.spinneritem1dark, brandlist);
        dataAdapterBrand.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spYear.setAdapter(dataAdapterBrand);

        List<String> milageList = new ArrayList<String>();
        milageList.add("COE");
        milageList.add("ECE");
        milageList.add("EEE");
        milageList.add("EE");
        milageList.add("IT");
        milageList.add("MCE");
        milageList.add("SE");
        milageList.add("ME");
        milageList.add("AUT");
        milageList.add("CE");
        milageList.add("ENE");
        milageList.add("PIE");
        milageList.add("EP");
        milageList.add("PCT");
        milageList.add("BT");
        ArrayAdapter<String> dataAdapterMilage = new ArrayAdapter<String>(getActivity(), R.layout.spinneritem1dark, milageList);
        dataAdapterMilage.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spBranch.setAdapter(dataAdapterMilage);

        List<String> styleList = new ArrayList<String>();
        for(int i=0;i<26;i++)
        {
            String alpha =""+ (char)(65+i);
            styleList.add(alpha);
        }

        ArrayAdapter<String> dataAdapterStyle = new ArrayAdapter<String>(getActivity(), R.layout.spinneritem1dark, styleList);
        dataAdapterStyle.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spBatch.setAdapter(dataAdapterStyle);

    }


    /////////server request for fetching authorization key

    public void requestAuthorizationKeyFromServer() {
        int yoyo;
        progressDialog.setTitle("Authenticating");
        progressDialog.setMessage("Please Wait...");
        progressDialog.show();

        MyVolley.init(getActivity());
        RequestQueue queue = MyVolley.getRequestQueue();

        JsonObjectRequest myReq = new JsonObjectRequest(Request.Method.GET, authorizationUrl
                , reqAuthorizationKeySuccessListener(), reqAuthorizationKeyErrorListener()) {

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

        myReq.setRetryPolicy(new DefaultRetryPolicy(5000, 1, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        queue.add(myReq);

    }

    private com.android.volley.Response.Listener<JSONObject> reqAuthorizationKeySuccessListener() {
        return new com.android.volley.Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject serverResponse) {

                progressDialog.hide();
                try {
                    authorizationKey = serverResponse.getString("field1");
                    Log.i("responsechecking", "key : " + authorizationKey);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                Log.i("responsechecking", "success : " + serverResponse.toString());

            }
        };
    }

    private com.android.volley.Response.ErrorListener reqAuthorizationKeyErrorListener() {
        return new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                btnCreateClassroom.setEnabled(false);
                Toast.makeText(getActivity(), "Cannot Authorize, network connection problem", Toast.LENGTH_LONG).show();
                progressDialog.hide();
            }
        };
    }

    ////////authorization key fetching part ends here


    @Override
    public void onClick(View v) {

        requestCreateNewClientFromServer();

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }


    /////////server request for creating a new channel

    public void requestCreateNewClientFromServer() {
        progressDialog.setTitle("Creating classroom");
        progressDialog.setMessage("Please Wait...");
        progressDialog.show();

        String className=spYear.getSelectedItem().toString()+"-"+spBranch.getSelectedItem().toString()+"-"+spBatch.getSelectedItem().toString();
        String createNewClentUrl="https://api.thingspeak.com/channels.json?api_key="+authorizationKey+"&name="+className+"&field1=Field 1&field2=Field 2&field3=Field 3&field4=Field 4&field5=Field 5&field6=Field 6&field7=Field 7&field8=Field 8&public_flag=true";


        MyVolley.init(getActivity());
        RequestQueue queue = MyVolley.getRequestQueue();

        JsonObjectRequest myReq = new JsonObjectRequest(Request.Method.POST, createNewClentUrl
                , reqCreateNewAccountSuccessListener(), reqCreateNewAccountErrorListener()) {

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

    private com.android.volley.Response.Listener<JSONObject> reqCreateNewAccountSuccessListener() {
        return new com.android.volley.Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject serverResponse) {

                String savedClassName="ClassName";
                String writeKey="writekey";
                String classId="id";

                Log.i("responsecheckingg", "Server response : " + serverResponse.toString());
                progressDialog.hide();

                try {
                    JSONArray keyArray=serverResponse.getJSONArray("api_keys");
                    savedClassName=serverResponse.getString("name");
                    classId=serverResponse.getString("id");
                    JSONObject writeKeyObject=keyArray.getJSONObject(0);
                    writeKey=writeKeyObject.getString("api_key");

                    Intent i=new Intent();
                    i.setClass(getActivity(), NewClassroomDetailActivity.class);
                    i.putExtra("created_classroom_name", savedClassName);
                    i.putExtra("write_api_key", writeKey);
                    i.putExtra("class_id", classId);
                    startActivity(i);

                } catch (JSONException e) {
                    e.printStackTrace();
                }

                Toast.makeText(getActivity(), "New class room :"+savedClassName,Toast.LENGTH_LONG).show();

            }
        };
    }

    private com.android.volley.Response.ErrorListener reqCreateNewAccountErrorListener() {
        return new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Log.i("responsecheckingg", "Server Error response : " + error.toString());
                Toast.makeText(getActivity(), "Cannot create classroom, network problem", Toast.LENGTH_LONG).show();
                progressDialog.hide();
            }
        };


    }


    ////////Creating a new channel part ends here


}
