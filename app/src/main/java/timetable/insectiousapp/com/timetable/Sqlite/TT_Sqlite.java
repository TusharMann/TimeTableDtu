package timetable.insectiousapp.com.timetable.Sqlite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Tushar on 21-06-2016.
 */
public class TT_Sqlite extends SQLiteOpenHelper {

    public final static String DATABASE_NAME = "Time_Table_Database";
    public final static String Tname = "CDETAILS";
    public final static String cid = "ID";
    public final static String api = "APIKEY";
    public final static String name = "CNAME";

    public TT_Sqlite(Context context,int version){
        super(context,DATABASE_NAME,null,version);
    }



    @Override
    public void onCreate(SQLiteDatabase db) {

        String query="CREATE TABLE "+Tname+"("+cid +" VARCHAR(50),"+name+" VARCHAR(50),"+api +" VARCHAR(50));";
        db.execSQL(query);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
