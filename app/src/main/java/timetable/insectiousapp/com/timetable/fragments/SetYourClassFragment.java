package timetable.insectiousapp.com.timetable.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.InputFilter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import mehdi.sakout.fancybuttons.FancyButton;
import timetable.insectiousapp.com.timetable.R;
import timetable.insectiousapp.com.timetable.others.SharedPreferencesFiles;


public class SetYourClassFragment extends Fragment implements View.OnClickListener{

    public SetYourClassFragment() {
        // Required empty public constructor
    }


    FancyButton btnSet;
    View v;
    EditText etClassId;

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
        etClassId.setFilters(new InputFilter[] {new InputFilter.LengthFilter(6)});

        SharedPreferencesFiles sharedPreferencesFiles=new SharedPreferencesFiles();
        SharedPreferences sharedPreferences=getActivity().getSharedPreferences(sharedPreferencesFiles.getSPClassId(), 0);
        String classId=sharedPreferences.getString(sharedPreferencesFiles.getClassId(), "Not set Yet");
        etClassId.setText(classId);


        return v;
    }


    @Override
    public void onClick(View v) {

        String classId=etClassId.getText().toString();

        if(classId!=null&&!classId.isEmpty()) {
            SharedPreferencesFiles sharedPreferencesFiles = new SharedPreferencesFiles();
            SharedPreferences sharedPreferences = getActivity().getSharedPreferences(sharedPreferencesFiles.getSPClassId(), Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString(sharedPreferencesFiles.getClassId(), classId);
            editor.commit();

            Toast.makeText(getActivity(), "Class Id updated",Toast.LENGTH_SHORT).show();

        }
        else
        {
            Toast.makeText(getActivity(), "Please enter the class id",Toast.LENGTH_SHORT).show();
        }

    }



}
