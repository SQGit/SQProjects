package  sqindia.net.brighterdayz;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;


public class LoginDataBase {

	static final String DATABASE_NAME = "loginnew.db";
	static final int DATABASE_VERSION = 1;
	public static final int NAME_COLUMN = 6;

	static final String DATABASE_CREATE = "create table "+"LOGIN"+ "( " +"ID integer primary key autoincrement,"+" FIRSTNAME  text,"+" USERNAME  text,"+" PASSWORD text,"+" REPASSWORD text,"+" EMAIL text,"+" PHONENO text) ";

	public  SQLiteDatabase db;
	private final Context context;
	private DataBaseHelper dbHelper;

	public LoginDataBase(Context _context)
	{
		context = _context;
		dbHelper = new DataBaseHelper(context, DATABASE_NAME, null, DATABASE_VERSION);

	}
	public LoginDataBase open() throws SQLException
	{
		db = dbHelper.getWritableDatabase();
		return this;
	}
	public void close() 
	{
		db.close();
	}

	public  SQLiteDatabase getDatabaseInstance()
	{
		return db;
	}

	public void insertEntry(String first_name,String user_name,String pwd,String re_pwd,String email,String phone_no)
	{
		ContentValues newValues = new ContentValues();
		newValues.put("FIRSTNAME",first_name );
		newValues.put("USERNAME",user_name);
		newValues.put("PASSWORD", pwd);
		newValues.put("REPASSWORD",re_pwd);
		newValues.put("EMAIL",email);
		newValues.put("PHONENO",phone_no);

		db.insert("LOGIN", null, newValues);
	}

	public int deleteEntry(String password)
	{
		String where="PASSWORD=?";
		int numberOFEntriesDeleted= db.delete("LOGIN", where, new String[]{password}) ;
		return numberOFEntriesDeleted;
	}

	public String getSinlgeEntry(String password)
	{
		Cursor cursor=db.query("LOGIN", null, " PASSWORD=?", new String[]{password}, null, null, null);
		//Cursor cursor1=db.query("LOGIN", null, " USERNAME=?", new String[]{username}, null, null, null);
		if(cursor.getCount()<1) // UserName Not Exist
		{
			cursor.close();
			return "NOT EXIST";
		}
		/*else if(cursor1.getCount()<1)
		{
			cursor1.close();
			return "NOT EXIST";
		}
		else
		{
			return "check username&pwd";
		}
		*/
	
		
		cursor.moveToFirst();
		String repassword= cursor.getString(cursor.getColumnIndex("REPASSWORD"));
		cursor.close();
		return repassword;				
	}
	

	/*public String getAllTags(String a) {


		Cursor c = db.rawQuery("SELECT * FROM " + "LOGIN" + " where SECURITYHINT = '" +a + "'" , null);
		String str = null;
		if (c.moveToFirst()) {
			do {
				str = c.getString(c.getColumnIndex("PASSWORD"));
			} while (c.moveToNext());
		}
		return str;
	}*/


	/*public void  updateEntry(String password,String repassword)
	{
		ContentValues updatedValues = new ContentValues();
		updatedValues.put("PASSWORD", password);
		updatedValues.put("REPASSWORD",repassword);
		updatedValues.put("SECURITYHINT",repassword);

		String where="USERNAME = ?";
		db.update("LOGIN",updatedValues, where, new String[]{password});			   
	}	



	public HashMap<String, String> getAnimalInfo(String id) {
		HashMap<String, String> wordList = new HashMap<String, String>();
		String selectQuery = "SELECT * FROM LOGIN where SECURITYHINT='"+id+"'";
		Cursor cursor = db.rawQuery(selectQuery, null);
		if (cursor.moveToFirst()) {
			do {
				wordList.put("PASSWORD", cursor.getString(1));
			} while (cursor.moveToNext());
		}				    
		return wordList;
	}	*/
}
