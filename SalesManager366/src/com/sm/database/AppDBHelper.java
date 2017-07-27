package com.sm.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;


public class AppDBHelper extends SQLiteOpenHelper {

	private final static String DATABASE_NAME="SMLBS";
	private final static int DATABASE_VERSION=1;
	private final static String TABLE_NAME="t_apps";
	
	private String sql="create table if not exists t_Apps "
	+"(_id INTEGER primary key autoincrement,AppName TEXT,appIcon TEXT,AppId INTEGER,"+
    " EmpID INTEGER);";
	public AppDBHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
		// TODO Auto-generated constructor stub
	}
	@Override
	public void onCreate(SQLiteDatabase sqLiteDatabase) {
		// TODO Auto-generated method stub
		
	}
	private void createTable(SQLiteDatabase db){
	    db.execSQL(sql);	
	}
	private void dropTable(SQLiteDatabase db){
		db.execSQL("DROP TABLE IF EXISTS t_Apps");
	}
	@Override
	public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		dropTable(sqLiteDatabase);
		createTable(sqLiteDatabase);
	}

}
