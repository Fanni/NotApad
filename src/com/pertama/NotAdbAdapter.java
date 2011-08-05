package com.pertama;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class NotAdbAdapter {
	
	public static final String KEY_TITLE = "title";
	public static final String KEY_BODY = "body";
	public static final String KEY_ROWID = "_id";
	private static final String TAG = "NotAdbAdapter";
	private DatabaseHelper dBhelper;
	private SQLiteDatabase dB;
	private static final String DATABASE_CREATE = "create table notes (_id integer primary key autoincrement, " +
			"title text not null, body text not null);";
	private static final String DATABASE_NAME = "data";
	private static final int DATABASE_VERSION = 1;
	private static final String DATABASE_TABLE = "notes";
	
	private final Context cTx;
	
	public NotAdbAdapter(Context ctx){
		this.cTx = ctx;
	}
	
	private static class DatabaseHelper extends SQLiteOpenHelper{
		DatabaseHelper(Context context){
			super(context, DATABASE_NAME, null, DATABASE_VERSION);
		}
		@Override
		public void onCreate(SQLiteDatabase db){
			db.execSQL(DATABASE_CREATE);
		}
		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			// TODO Auto-generated method stub
			Log.w(TAG, "Upgrading database from version " + oldVersion + "to " + newVersion);
			db.execSQL("DROP TABLE IF EXISTS notes");
			onCreate(db);
		}
	}
	
	public NotAdbAdapter open() throws SQLException{
		dBhelper = new DatabaseHelper(cTx);
		dB = dBhelper.getReadableDatabase();
		return this;
	}
	
	public void close(){
		dBhelper.close();
	}
	
	public long createNote(String title, String body){
		ContentValues initialValues = new ContentValues();
		initialValues.put(KEY_TITLE, title);
		initialValues.put(KEY_BODY, body);
		return dB.insert(DATABASE_TABLE, null, initialValues);
	}
	
	public boolean removeNote(long rowID){
		return dB.delete(DATABASE_TABLE, KEY_ROWID + "=" + rowID, null) > 0;
	}
	
	public Cursor fetchAllNotes(){
		return dB.query(DATABASE_TABLE, new String[]{ KEY_ROWID, KEY_TITLE, KEY_BODY}, null, null, null, null, null);
	}
	
	/*public String fetchBody(){
		//public String content = NotAdbAdapter.KEY_BODY;
		//public String some = content.substring(0, content.length() / 2);
		Cursor body = dB.query(DATABASE_TABLE, new String[]{ KEY_BODY }, null, null, null, null, null);
		String[] some = new String[] body.getString(0).substring(0, body.getString(0).length()/2);
		
		return some;
	}*/
	
	public Cursor fetchNote(long rowID) throws SQLException{
		Cursor cursor = dB.query(true, DATABASE_TABLE, new String[]{ KEY_ROWID, KEY_TITLE, KEY_BODY},
				KEY_ROWID + "=" + rowID, null, null, null, null, null);
		if(cursor != null){
			cursor.moveToFirst();
		}
		return cursor;
	}
	
	public boolean updateNote(long rowID, String title, String body){
		ContentValues args = new ContentValues();
		args.put(KEY_TITLE, title);
		args.put(KEY_BODY, body);
		return dB.update(DATABASE_TABLE, args, KEY_ROWID + "=" + rowID, null) > 0;
	}

	public boolean deleteNote(long id) {
		// TODO Auto-generated method stub
		return dB.delete(DATABASE_TABLE, KEY_ROWID + "=" + id, null) > 0;
	}

}





















