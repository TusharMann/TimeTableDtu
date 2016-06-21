package timetable.insectiousapp.com.timetable.Sqlite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Tushar on 21-06-2016.
 */
public class TT_Sqlite extends SQLiteOpenHelper {

   public final static String DATABASE_NAME = "Time_Table_Database";
    public final static String Tname = "Class_Details";
    public final static String cid = "Class_ID";
    public final static String api = "API_Key";
    public final static String name = "Class_Name";

    public TT_Sqlite(Context context,int version){
        super(context,DATABASE_NAME,null,version);
    }



    @Override
    public void onCreate(SQLiteDatabase db) {

        String query="CREATE TABLE"+Tname+"("+cid+"TEXT,"+api+"TEXT);";
        db.execSQL(query);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
