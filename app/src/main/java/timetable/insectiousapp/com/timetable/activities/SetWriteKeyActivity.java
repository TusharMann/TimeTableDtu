package timetable.insectiousapp.com.timetable.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.InputFilter;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import mehdi.sakout.fancybuttons.FancyButton;
import timetable.insectiousapp.com.timetable.R;
import timetable.insectiousapp.com.timetable.others.Class_Load;
import timetable.insectiousapp.com.timetable.others.SharedPreferencesFiles;

public class SetWriteKeyActivity extends AppCompatActivity {

    EditText etWriteKey;
    FancyButton btnSave,btnLoad;
    String classID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_write_key);

        Intent i=getIntent();
        classID=i.getStringExtra("ClassId");

        etWriteKey=(EditText)findViewById(R.id.fragment_set_write_key_et_writekey);
        etWriteKey.setFilters(new InputFilter[] {new InputFilter.AllCaps(), new InputFilter.LengthFilter(17)});
        btnSave=(FancyButton)findViewById(R.id.fragment_set_write_key_btn_save);
        btnLoad=(FancyButton)findViewById(R.id.fragment_set_write_key_btn_load);

        SharedPreferencesFiles sharedPreferencesFiles=new SharedPreferencesFiles();
        SharedPreferences sharedPreferences=getSharedPreferences(sharedPreferencesFiles.getSPWriteKey(), 0);
        String classId=sharedPreferences.getString(sharedPreferencesFiles.getWriteKey(), "Not set Yet");

        if(classId.contentEquals("Not set Yet")){
            etWriteKey.setHint("Not Yet Set");
        }

        else{
            etWriteKey.setText(classId);
        }


        btnLoad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i=new Intent();
                i.setClass(SetWriteKeyActivity.this,LoadList.class);
                startActivityForResult(i,0);

            }
        });



        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String writeKey=etWriteKey.getText().toString();



                if(writeKey!=null&&!writeKey.isEmpty()) {
                    SharedPreferencesFiles sharedPreferencesFiles = new SharedPreferencesFiles();
                    SharedPreferences sharedPreferences = getSharedPreferences(sharedPreferencesFiles.getSPWriteKey(), Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString(sharedPreferencesFiles.getWriteKey(), writeKey);
                    editor.commit();

                    SharedPreferencesFiles sharedPreferencesFiles1 = new SharedPreferencesFiles();
                    SharedPreferences sharedPreferences1 = getSharedPreferences(sharedPreferencesFiles1.getSPClassId(), Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor1 = sharedPreferences1.edit();
                    editor1.putString(sharedPreferencesFiles1.getClassId(), classID);
                    editor1.commit();

                    Toast.makeText(getApplicationContext(), "Write key updated",Toast.LENGTH_SHORT).show();







                }
                else
                {
                    Toast.makeText(getApplicationContext(), "Please enter the write key",Toast.LENGTH_SHORT).show();
                }

                finish();

            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode ==RESULT_OK){
            Class_Load c= (Class_Load) data.getSerializableExtra("Object");
            Log.i("TagSet","Result Ok");

            Log.i("Tag Api",c.api);
            etWriteKey.setText(c.api);
            classID=c.id;

        }

        super.onActivityResult(requestCode, resultCode, data);
    }
}
