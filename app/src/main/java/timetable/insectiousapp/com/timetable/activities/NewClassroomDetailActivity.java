package timetable.insectiousapp.com.timetable.activities;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import mehdi.sakout.fancybuttons.FancyButton;
import timetable.insectiousapp.com.timetable.R;
import timetable.insectiousapp.com.timetable.Sqlite.TT_Sqlite;

public class NewClassroomDetailActivity extends AppCompatActivity implements View.OnClickListener{

    TextView tvClassName, tvWriteApiKey, tvClassId;
    String className, writeKey, classId;
    TT_Sqlite sqlite;
    SQLiteDatabase db;

    EditText etEmailId;
    FancyButton btnEmail, btnProceed;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_classroom_detail);
        sqlite=new TT_Sqlite(this,1);

        linkingandInitialSetup();

    }

    private void linkingandInitialSetup() {

        Intent i=getIntent();
        className=i.getStringExtra("created_classroom_name");
        writeKey=i.getStringExtra("write_api_key");
        classId=i.getStringExtra("class_id");

        db=sqlite.getWritableDatabase();
        ContentValues cv=new ContentValues();
        cv.put(TT_Sqlite.cid,classId);

        cv.put(TT_Sqlite.api,writeKey);

        cv.put(TT_Sqlite.name,className);

        db.insert(TT_Sqlite.Tname,null,cv);



        tvClassName=(TextView)findViewById(R.id.activity_new_classroom_detail_layout_1_tv_classname);
        tvClassId=(TextView)findViewById(R.id.activity_new_classroom_detail_layout_1_tv_classid);
        tvWriteApiKey=(TextView)findViewById(R.id.activity_new_classroom_detail_layout_1_tv_writeapikey);
        etEmailId=(EditText)findViewById(R.id.activity_new_classroom_detail_layout_2_et_emailid);
        btnEmail=(FancyButton)findViewById(R.id.activity_new_classroom_detail_layout_2_btn_email);
        btnProceed=(FancyButton)findViewById(R.id.activity_new_classroom_detail_layout_3_btn_proceed);

        tvClassName.setText(className);
        tvClassId.setText(classId);
        tvWriteApiKey.setText(writeKey);

        btnEmail.setOnClickListener(this);
        btnProceed.setOnClickListener(this);
        btnProceed.setEnabled(false);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.activity_new_classroom_detail_layout_2_btn_email:

                String semailId=etEmailId.getText().toString();
                if(semailId.isEmpty()||semailId==null)
                {
                    Toast.makeText(this, "Please enter your email id", Toast.LENGTH_SHORT).show();
                }
                else {

                    btnProceed.setEnabled(true);

                    Intent i = new Intent(Intent.ACTION_SEND);
                    i.setType("message/rfc822");
                    i.putExtra(Intent.EXTRA_EMAIL, new String[]{semailId});
                    i.putExtra(Intent.EXTRA_SUBJECT, "Key for TIME-TABLE-DTU");
                    i.putExtra(Intent.EXTRA_TEXT, "Your key for class room : '"+className+ "' having id : '"+classId+"' is "+writeKey);
                    try {
                        startActivity(Intent.createChooser(i, "Send mail..."));
                    } catch (android.content.ActivityNotFoundException ex) {
                        Toast.makeText(this, "There are no email clients installed.", Toast.LENGTH_SHORT).show();
                    }
                }

                break;

            case R.id.activity_new_classroom_detail_layout_3_btn_proceed:


                Intent i=new Intent();
                i.setClass(this, NewClassroomTimeTable.class);
                i.putExtra("write_api_key_from_detail_activity", writeKey);
                startActivity(i);

                break;

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
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


}
