package tlktechnology.darmok;

import android.content.ContentValues;
import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by RSA on 17-08-2016.
 */
public class UserDataBaseAdapter {

    static final String DATABASE_NAME = "Darmok.db";
    static final int DATABASE_VERSION = 1;
    public static final int NAME_COLUMN = 1;

    // SQL Statement to create a new table.
    static final String DATABASE_CREATE = "create table " + "user" +
            "(" + "id" +
            " integer primary key autoincrement," +
            "first_name text," +
            "last_name text," +
            "middle_name text," +
            "phone_number text," +
            "address text," +
            "favorite_restaturant text," +
            "spouse_first_name text," +
            "favorite_friend text," +
            "number_of_children text," +
            "child_name text," +
            "workplace text," +
            "worklocation text," +
            "favorite_sport text," +
            "favorite_pastime text," +
            "birthday text," +
            "authorization_code text); ";


    public SQLiteDatabase db;

    // Context of the application using the database.
    private final Context context;


    // Database open/upgrade helpergg
    private DataBaseHelper dbHelper;


    public UserDataBaseAdapter(Context _context) {
        context = _context;
        dbHelper = new DataBaseHelper(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public UserDataBaseAdapter open() throws SQLException {
        db = dbHelper.getWritableDatabase();
        return this;
    }

    public void close() {
        db.close();
    }

    public void insertentry(String str_first_name, String str_last_name, String str_middle_name, String str_phone_number, String str_address, String str_favorite_restaturant, String str_spouse_first_name, String str_favorite_friend, String str_number_of_children, String str_child_name, String str_workplace, String str_work_location, String str_favorite_sport, String str_favorite_pastime, String str_birthday, String str_authorization_code) {
        ContentValues newValues = new ContentValues();
        // Assign values for each row.
        newValues.put("first_name", str_first_name);
        newValues.put("last_name", str_last_name);
        newValues.put("middle_name", str_middle_name);
        newValues.put("phone_number", str_phone_number);
        newValues.put("address", str_address);
        newValues.put("favorite_restaturant", str_favorite_restaturant);
        newValues.put("spouse_first_name", str_spouse_first_name);
        newValues.put("favorite_friend", str_favorite_friend);
        newValues.put("number_of_children", str_number_of_children);
        newValues.put("child_name", str_child_name);
        newValues.put("workplace", str_workplace);
        newValues.put("worklocation", str_work_location);
        newValues.put("favorite_sport", str_favorite_sport);
        newValues.put("favorite_pastime", str_favorite_pastime);
        newValues.put("birthday", str_birthday);
        newValues.put("authorization_code", str_authorization_code);
        db.insert("user", null, newValues);

    }

    //***************************login table*********************
    /*public void insertEntry(String str_First_Name, String str_Last_Name, String str_Middle_Name,
                            String str_Phone_number, String str_Address, String str_Favorite_Restaturant,
                            String str_Spouse_first_name, String str_Favorite_friend, String str_Number_of_children,
                            String str_Child_name, String str_Workplace, String str_Work_location,
                            String str_Favorite_sport, String str_Favorite_pastime, String str_Birthday, String str_Authorization_code) {
        ContentValues newValues = new ContentValues();
        // Assign values for each row.
        newValues.put("first_name", str_First_Name);
        newValues.put("last_name", str_Last_Name);
        newValues.put("middle_name", str_Middle_Name);
        newValues.put("phone_number", str_Phone_number);
        newValues.put("address", str_Address);
        newValues.put("favorite_restaturant", str_Favorite_Restaturant);
        newValues.put("spouse_first_name", str_Spouse_first_name);
        newValues.put("favorite_friend", str_Favorite_friend);
        newValues.put("number_of_children", str_Number_of_children);
        newValues.put("child_name", str_Child_name);
        newValues.put("workplace", str_Workplace);
        newValues.put("worklocation", str_Work_location);
        newValues.put("favorite_sport", str_Favorite_sport);
        newValues.put("favorite_pastime", str_Favorite_pastime);
        newValues.put("birthday", str_Birthday);
        newValues.put("authorization_code", str_Authorization_code);
        db.insert("user", null, newValues);
    }*/
}
