package com.virginkiss.database;

public interface IStore {
	public void saveData(Object data);
	public Object loadData();
}
