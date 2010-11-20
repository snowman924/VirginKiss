package com.virginkiss.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.virginkiss.time.ITime;
import com.virginkiss.time.TimeImpl;

//import com.virginkiss.ITime;

public class WorkStore implements IStore {
	private static final String LOG_TAG = "WorkStore";

	private static final String[] FROM = {DBHelper.HOUR, DBHelper.MINUTE};
	
	private DBHelper dbHelper;
	
	public WorkStore(Context context) {
		dbHelper = new DBHelper(context);
	}
	
	public void saveData(Object data) {
		if (data instanceof ITime) {
			ITime time = (ITime) data;
			saveDataToDB(time);
		}else{
			throw new IllegalArgumentException("Argument data should implements ITime");
		}
	}

	public Object loadData() {
		SQLiteDatabase db = dbHelper.getReadableDatabase();
		Cursor cursor = db.query(DBHelper.TABLE_NAME, FROM, null, null, null, null, null);
		
		cursor.moveToLast();
		int hour = cursor.getInt(0);
		int minute = cursor.getInt(1);
		
		TimeImpl workTime = new TimeImpl(hour, minute);
		
		return (ITime)workTime;
	}
	
	private void saveDataToDB(ITime time) {
		SQLiteDatabase db = dbHelper.getWritableDatabase();
		ContentValues values = new ContentValues();
		
		Log.d(LOG_TAG, "saveDataToDB() time.getHour():" + time.getHour() + ", time.getMinute():"+time.getMinute());
		
		values.put(DBHelper.HOUR, time.getHour());
		values.put(DBHelper.MINUTE, time.getMinute());
		
		db.insertOrThrow(DBHelper.TABLE_NAME, null, values);
	}
}
