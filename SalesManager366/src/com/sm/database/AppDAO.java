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
	//�ر����ݿ�
	public void CloseDB(){
		if(dbDatabase!=null)
			dbDatabase.close();
	}
	//��������
	public void insert(){
		
	}
	//��ѯ����
	public void query(){
		
		
	}
	//ɾ������
	public void delete(){
		
	}
	
}
