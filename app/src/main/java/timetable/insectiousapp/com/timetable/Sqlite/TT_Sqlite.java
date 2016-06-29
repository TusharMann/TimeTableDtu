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

    public final static String tname="DEFAULT_TIME_TABLE";
    public final static String one="MONDAY";
    public final static String two="TUESDAY";
    public final static String three="WEDNESDAY";
    public final static String four="THURSDAY";
    public final static String five="FRIDAY";

    public final static String tdet="UPDATE_DETAILS";
    public final static String cname="CRNAME";
    public final static String cno="CRNUMBER";
    public final static String updon="UPDATEDON";




    public TT_Sqlite(Context context,int version){
        super(context,DATABASE_NAME,null,version);
    }



    @Override
    public void onCreate(SQLiteDatabase db) {

        String query="CREATE TABLE "+Tname+"("+cid +" VARCHAR(50),"+name+" VARCHAR(50),"+api +" VARCHAR(50));";
        db.execSQL(query);

        String query2="CREATE TABLE "+tname+"("+one +" VARCHAR(50),"+two+" VARCHAR(50),"+three+" VARCHAR(50),"+four+" VARCHAR(50),"
                +five +" VARCHAR(50));";

        db.execSQL(query2);

        String query3="CREATE TABLE "+tdet+"("+cname +" VARCHAR(50),"+cno+" VARCHAR(50),"+updon +" VARCHAR(50));";
        db.execSQL(query3);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
