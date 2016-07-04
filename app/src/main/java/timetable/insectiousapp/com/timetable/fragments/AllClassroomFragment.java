package timetable.insectiousapp.com.timetable.fragments;


import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import timetable.insectiousapp.com.timetable.R;
import timetable.insectiousapp.com.timetable.others.ClassroomListAdapter;
import timetable.insectiousapp.com.timetable.others.Classroom;
import timetable.insectiousapp.com.timetable.others.SharedPreferencesFiles;
import timetable.insectiousapp.com.timetable.volley.MyVolley;

/**
 * A simple {@link Fragment} subclass.
 */
public class AllClassroomFragment extends Fragment {

    String authorizationUrl = "https://api.thingspeak.com/channels/108367/feed/last.json";
    ProgressDialog progressDialog;
    String authorizationKey;
    ListView listview;
    ClassroomListAdapter adapter;
    LayoutInflater l;
    ArrayList<Classroom> data;


    public AllClassroomFragment() {
        // Required empty public constructor
    }

    View v;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v= inflater.inflate(R.layout.fragment_all_classroom, container, false);

        listview=(ListView)v.findViewById(R.id.fragment_all_classroom_lv_allclasses);
        data=new ArrayList<Classroom>();
        progressDialog=new ProgressDialog(getActivity());
        requestAuthorizationKeyFromServer();

        l=getActivity().getLayoutInflater();
        adapter=new ClassroomListAdapter(getActivity(), 0, data, l);
        listview.setAdapter(adapter);

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Classroom c=adapter.getItem(position);
                String cid=c.id;

                SharedPreferencesFiles sharedPreferencesFiles = new SharedPreferencesFiles();
                SharedPreferences sharedPreferences = getActivity().getSharedPreferences(sharedPreferencesFiles.getSPClassId(), Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString(sharedPreferencesFiles.getClassId(),cid );
                editor.commit();

                Toast.makeText(getActivity(), "Class Id updated",Toast.LENGTH_SHORT).show();

                DefaultTimetableFragment fragment=new DefaultTimetableFragment();
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.mainactivity_framelayout,fragment).commit();
                getActivity().setTitle("Default Timetable");



            }
        });

        //Log.v("checklength", String.valueOf(all_classroom_ids.length));


       // all_classroom_listview = (ListView)v.findViewById(R.id.fragment_all_classroom_lv_allclasses);
       // all_classroom_listview.setAdapter(new AllClassroomListAdapter(,all_classroom_names,all_classroom_ids));
        return v;
    }




    /////////server request for fetching authorization key

    public void requestAuthorizationKeyFromServer() {
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

        myReq.setRetryPolicy(new DefaultRetryPolicy(2000, 1, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        queue.add(myReq);

    }

    private com.android.volley.Response.Listener<JSONObject> reqAuthorizationKeySuccessListener() {
        return new com.android.volley.Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject serverResponse) {

                progressDialog.hide();
                try {
                    authorizationKey = serverResponse.getString("field1");
                    requestAuthorizationKeyFromServer_lv();
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
                //btnCreateClassroom.setEnabled(false);
                Toast.makeText(getActivity(), "Cannot Authorize, network connection problem", Toast.LENGTH_LONG).show();
                progressDialog.hide();
            }
        };
    }

    ////////authorization key fetching part ends here


    //////// new authorization hit ////////////////////////////////////////////////////////////////////////////////
    public void requestAuthorizationKeyFromServer_lv() {
        progressDialog.setTitle("Fetching classses");
        progressDialog.setMessage("Please Wait.. it may take few seconds");
        progressDialog.show();


        String URLforFetchingAllClasses="https://thingspeak.com/channels.json?api_key="+authorizationKey;

        MyVolley.init(getActivity());
        RequestQueue queue = MyVolley.getRequestQueue();

        JsonArrayRequest myReq = new JsonArrayRequest(Request.Method.GET, URLforFetchingAllClasses
                , reqAuthorizationKeySuccessListener_lv(), reqAuthorizationKeyErrorListener_lv()) {

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

    private com.android.volley.Response.Listener<JSONArray> reqAuthorizationKeySuccessListener_lv() {
        return new com.android.volley.Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray serverResponse) {

                Log.i("responsechecking", "key : " + serverResponse);

                progressDialog.hide();
                try {
                   //authorizationKey = serverResponse.getString("field1");
                    for(int i=0; i<serverResponse.length();i++)
                    {
                        JSONObject jsonObject =serverResponse.getJSONObject(i);
                        String id = jsonObject.getString("id");
                        String name = jsonObject.getString("name");

                        Classroom classroom=new Classroom(id, name);
                        data.add(classroom);
                        adapter.notifyDataSetChanged();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                Log.i("responsechecking", "success : " + serverResponse.toString());

            }
        };
    }

    private com.android.volley.Response.ErrorListener reqAuthorizationKeyErrorListener_lv() {
        return new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //btnCreateClassroom.setEnabled(false);
                Log.i("responsechecking", "error : " + error);
                Toast.makeText(getActivity(), "Cannot Authorize, network connection problem", Toast.LENGTH_LONG).show();
                progressDialog.hide();
            }
        };
    }



}
