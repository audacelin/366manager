package com.sm.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

public class AppDAO {

	private AppDBHelper helper;
	private SQLiteDatabase dbDatabase;
	
	public AppDAO(Context context){
		helper=new AppDBHelper(context);
		dbDatabase=helper.getWritableDatabase();
	}
	//关闭数据库
	public void CloseDB(){
		if(dbDatabase!=null)
			dbDatabase.close();
	}
	//插入数据
	public void insert(){
		
	}
	//查询数据
	public void query(){
		
		
	}
	//删除数据
	public void delete(){
		
	}
	
}
