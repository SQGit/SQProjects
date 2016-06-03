package www.sqindia.net.ilerasoftadmin;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by ANANDH on 25-12-2015.
 */
public class DataBaseHandler extends SQLiteOpenHelper {


    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "ILERASOFT.db";

    //   table name
    private static final String TABLE_NAME= "statistics";


    public DataBaseHandler(Context context, String string, Object object, int i) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase arg0) {
        // TODO Auto-generated method stub

        arg0.execSQL("create table TABLE_NAME(_id integer primary key autoincrement,CATEGORY_NAME text,PERCENTAGE text)");




    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // TODO Auto-generated method stub
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

}

