package timetable.insectiousapp.com.timetable.activities;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

import timetable.insectiousapp.com.timetable.R;
import timetable.insectiousapp.com.timetable.Sqlite.TT_Sqlite;
import timetable.insectiousapp.com.timetable.others.Class_Load;
import timetable.insectiousapp.com.timetable.others.Class_Load_Adapter;

public class LoadList extends AppCompatActivity {

    TT_Sqlite sqlite;
    SQLiteDatabase db;
    ArrayList<Class_Load> list;
    ListView listView;
    Class_Load_Adapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_load_list);
        sqlite=new TT_Sqlite(this,1);
        db=sqlite.getWritableDatabase();
        list=new ArrayList<Class_Load>();
        listView=(ListView)findViewById(R.id.listView) ;
        adapter=new Class_Load_Adapter(this,list);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Class_Load c=list.get(position);
                Intent i=new Intent();
                Log.i("TagLoad Id",c.id);
                Log.i("TagLoad Api",c.api);
                i.putExtra("Object",c);
                setResult(RESULT_OK,i);
                finish();

            }
        });


        String[] columns={TT_Sqlite.cid,TT_Sqlite.name,TT_Sqlite.api};
        Cursor cursor=db.query(TT_Sqlite.Tname,columns,null,null,null,null,null,null);
        while(cursor.moveToNext()){
            Class_Load c=new Class_Load();

            int index1=cursor.getColumnIndex(TT_Sqlite.cid);
            int index2=cursor.getColumnIndex(TT_Sqlite.name);
            int index3=cursor.getColumnIndex(TT_Sqlite.api);

            c.id=cursor.getString(index1);
            c.name=cursor.getString(index2);
            c.api=cursor.getString(index3);

            list.add(c);
        }
        adapter.notifyDataSetChanged();


    }
}
