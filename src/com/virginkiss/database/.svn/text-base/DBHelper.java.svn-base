package com.virginkiss.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;
import android.util.Log;

public class DBHelper extends SQLiteOpenHelper {

	public static final String LOG_TAG = "DBHelper";
	
	public static final String TABLE_NAME = "TimeTable";

	public static final String TIME = "time";

	public static final String TITLE = "title";
	
	public static final String HOUR = "hour";
	public static final String MINUTE = "minute";

	private static final int DB_VERSION = 2;

	private static final String TIME_NAME = "time_name";
	
	public DBHelper(Context context) {
		super(context, TABLE_NAME, null, DB_VERSION);
	}
	
	public DBHelper(Context context, String name, CursorFactory factory,
			int version) {
		super(context, name, factory, version);
	}
	

	@Override
	public void onCreate(SQLiteDatabase db) {
		Log.d(LOG_TAG, "onCreate() <<<"); 
		String sqlCommand = "CREATE TABLE " + TABLE_NAME + " (" + BaseColumns._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + TIME_NAME +" TEXT NOT NULL," + HOUR + " INTEGER," + MINUTE + " INTEGER);";
		Log.v(LOG_TAG, "onCreate() sqlCommand:"+sqlCommand);
		
		db.execSQL(sqlCommand);
		
		Log.d(LOG_TAG, "onCreate() >>>");
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		Log.d(LOG_TAG, "onUpgrade() <<<");
		
		String sqlCommand = "DROP TABLE IF EXISTS " + TABLE_NAME;
		Log.v(LOG_TAG, "onCreate() sqlCommand:"+sqlCommand);
		
		db.execSQL(sqlCommand);
		onCreate(db);
		
		Log.d(LOG_TAG, "onUpgrade() <<<");
	}

}
