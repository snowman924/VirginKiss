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

	public static final String TIME_NAME_TODAY_WORK_START_TIME = "today_work_start_time";
	
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
		String where = DBHelper.TIME_NAME + "='" +TIME_NAME_TODAY_WORK_START_TIME + "'";
		Cursor cursor = db.query(DBHelper.TABLE_NAME, FROM, where, null, null, null, null);
		
		TimeImpl workTime = null;
		
		if(cursor.getCount() != 0){
			cursor.moveToLast();
			int hour = cursor.getInt(0);
			int minute = cursor.getInt(1);
			
			workTime = new TimeImpl(hour, minute);	
		}
		cursor.close();
		return (ITime)workTime;
	}
	
	private void saveDataToDB(ITime time) {
		SQLiteDatabase db = dbHelper.getWritableDatabase();
		ContentValues values = new ContentValues();
		
		Log.d(LOG_TAG, "saveDataToDB() time.getHour():" + time.getHour() + ", time.getMinute():"+time.getMinute());
		
		values.put(DBHelper.TIME_NAME, TIME_NAME_TODAY_WORK_START_TIME);
		values.put(DBHelper.HOUR, time.getHour());
		values.put(DBHelper.MINUTE, time.getMinute());
		
		//db.insertOrThrow(DBHelper.TABLE_NAME, null, values);
		
		String updateWhereClause = DBHelper.TIME_NAME + "='" +TIME_NAME_TODAY_WORK_START_TIME + "'";
		int ret = db.update(DBHelper.TABLE_NAME, values, updateWhereClause, null);
		if(ret==0){
			db.insertOrThrow(DBHelper.TABLE_NAME, null, values);
		}
	}

	public void close() {
		dbHelper.close();
	}
}
