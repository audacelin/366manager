package com.sm366.entity;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

import android.R.integer;
import android.util.Log;


public class ResponseModel {

	private final static String TAG="ResponseModel";
	//http 请求标识
	private int result;
	//请求提示
	private String alertMessage;
	private int totalCount;
	private ArrayList<Json2Obj> entities;
	private Json2Obj entity;
	private Object data;
	
	//预留扩展字段
	private ArrayList<Json2Obj> extend;
	private Object extendTwo;
	public enum JsonType{
		//
		MULTI,
		SINGLE
	}
	public ResponseModel (int result,String alertMessage,int totalCount) {
		this.result=result;
		this.alertMessage=alertMessage;
		this.totalCount=totalCount;
	}
	public ResponseModel(int result,String alertMessage,int totalCount,Object data){
	    this(result, alertMessage, totalCount);
		this.data=data;
	}
	public ResponseModel(int result,String alertMessage,int totalCount,
			Object data,ArrayList<Json2Obj> extend,Object extendTwo) {
		this(result, alertMessage, totalCount, data);
		this.extend=extend;
		this.extendTwo=extendTwo;
	}
	public ResponseModel (int result,String alertMessage,int totalCount,ArrayList<Json2Obj> entities,
			Json2Obj entity) {
		this(result, alertMessage, totalCount);
		this.entities=entities;
		this.entity=entity;
	}
	
	public static ResponseModel ValueOf(JSONObject obj) {
		try{
			int result=obj.getInt("Result");
			String alertMessage=obj.getString("AlertMessage");
			int totalCount=obj.getInt("TotalCount");
			Object data=obj.opt("Data");
			return new ResponseModel(result, alertMessage, totalCount,data);
		}catch(final Exception ex){
		    ex.printStackTrace();	
		}
		return null;
	}
	
    public static ResponseModel ValueOf(JSONObject obj,JsonType jt,Class<? extends Json2Obj> cls){
    	try{
    		int result=obj.getInt("Result");
    		String alertMessage=obj.getString("AlertMessage");
    		int totalCount=obj.getInt("TotalCount");
    		Object data=obj.opt("Data");
    		ArrayList<Json2Obj> entities=null;
    		Json2Obj entity=null;
    		if(jt==JsonType.MULTI){
    			if(data instanceof JSONArray){
    				JSONArray array=(JSONArray)data;
    				if(array!=null){
    					entities=new ArrayList<Json2Obj>();
    					for(int i=0,len=array.length();i<len;i++){
    						Json2Obj instance=cls.newInstance().ValueOf(array.getJSONObject(i));
    						if(instance!=null){
    							entities.add(instance);
    						}
    					}
    				}
    			}else{
    				Log.e(TAG,"The data is not JsonArray!");
    			}
    		}else{
    			if(data instanceof JSONObject){
    				JSONObject object=(JSONObject)data;
    				entity=cls.newInstance().ValueOf(object);
    			}else{
    				Log.e(TAG,"The Data is not JsonObject!");
    			}
    		}
    		return new ResponseModel(result,alertMessage,totalCount,entities,entity);
    	}catch(final Exception ex){
    		
    	}
    	return null;
    }
	public int getResult() {
		return result;
	}
	public String getAlertMessage() {
		return alertMessage;
	}
	public int getTotalCount() {
		return totalCount;
	}
	public ArrayList<Json2Obj> getEntities() {
		return entities;
	}
	public Json2Obj getEntity() {
		return entity;
	}
	public Object getData() {
		return data;
	}
	public ArrayList<Json2Obj> getExtend() {
		return extend;
	}
	public Object getExtendTwo() {
		return extendTwo;
	}

    
    
}
