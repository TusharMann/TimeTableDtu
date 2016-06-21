package timetable.insectiousapp.com.timetable.activities;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.InputFilter;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import mehdi.sakout.fancybuttons.FancyButton;
import timetable.insectiousapp.com.timetable.R;
import timetable.insectiousapp.com.timetable.Sqlite.TT_Sqlite;
import timetable.insectiousapp.com.timetable.others.SharedPreferencesFiles;

public class SetWriteKeyActivity extends AppCompatActivity {

    EditText etWriteKey;
    FancyButton btnSave;
    String classID;
    TT_Sqlite sqlite;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_write_key);
        sqlite=new TT_Sqlite(this,1);

        Intent i=getIntent();
        classID=i.getStringExtra("ClassId");

        etWriteKey=(EditText)findViewById(R.id.fragment_set_write_key_et_writekey);
        etWriteKey.setFilters(new InputFilter[] {new InputFilter.AllCaps(), new InputFilter.LengthFilter(17)});
        btnSave=(FancyButton)findViewById(R.id.fragment_set_write_key_btn_save);

        SharedPreferencesFiles sharedPreferencesFiles=new SharedPreferencesFiles();
        SharedPreferences sharedPreferences=getSharedPreferences(sharedPreferencesFiles.getSPWriteKey(), 0);
        String classId=sharedPreferences.getString(sharedPreferencesFiles.getWriteKey(), "Not set Yet");
        etWriteKey.setText(classId);



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

                    Toast.makeText(getApplicationContext(), "Write key updated",Toast.LENGTH_SHORT).show();

                    SQLiteDatabase db=sqlite.getWritableDatabase();

                    ContentValues cv=new ContentValues();
                    cv.put(TT_Sqlite.cid,classID);

                    cv.put(TT_Sqlite.api,writeKey);

                    db.insert(TT_Sqlite.Tname,null,cv);





                }
                else
                {
                    Toast.makeText(getApplicationContext(), "Please enter the write key",Toast.LENGTH_SHORT).show();
                }

            }
        });

    }
}
