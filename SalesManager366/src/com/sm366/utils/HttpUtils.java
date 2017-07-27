package com.sm366.utils;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.entity.StringEntity;
import org.apache.http.protocol.HTTP;
import org.json.JSONException;
import org.json.JSONObject;

import android.R.integer;
import android.content.Context;
import android.util.Log;

import com.sm.common.Const;
import com.smlib.net.EntranceHttpRequest;
import com.smlib.net.KDHttpRequest;
import com.smlib.net.KDHttpRequest.KDHttpRequestLinstener;

public class HttpUtils {
  /*
   * Action
   */
	
	public static final String USERINFO_ACTION="Mobile/CurrentUserInfo";//获取当前用户信息
	public static final String MENU_ACTION="Mobile/GetMenuList";//获取应用列表
	public static final String REGISTER_ACTION="Mobile/Register";//注册企业
	public static final String LOGIN_ACTION="Mobile/Login";//登录
	
	
	/*
	 * Flag 标示每一个请求
	 */
	public static final int USERINFO_FLAG=1;
	public static final int MENU_FLAG=2;
	public static final int REGISTER_FLAG=3;
	public static final int LOGIN_FLAG=4;
	
	public final static String TAG="HttpUtils";
	private KDHttpRequestLinstener linstener;
	protected Context mContext;
	protected String urlStr;
	
	private List<KDHttpRequest> stack;
	public HttpUtils(Context context){
		this.mContext=context;
		this.urlStr=Const.SM_SERVER_URL;
		this.stack=new ArrayList<KDHttpRequest>();
	}
	
	/*
	 * 析放请求
	 */
	public void release(){
		for(KDHttpRequest hr :stack){
			hr.cancel();
		}
		stack.clear();
	}
	//获取应用
	public void GetMenuList() {
		query(MENU_ACTION, MENU_FLAG);
	}
	//注册企业
	public void Register(String cropName,String registerName,String phone,String psw){
		JSONObject jObject=new JSONObject();
		try{
			jObject.put("CropName", cropName);
			jObject.put("RegisterName", registerName);
			jObject.put("Phone", phone);
			jObject.put("Psw", psw);
			postJson(jObject.toString(), REGISTER_ACTION, REGISTER_FLAG);
		}catch(JSONException ex){
			
		}		
	}
	//登录
	public void Login(String phone,String psw,String MID){
		JSONObject jObject=new JSONObject();
		try{
			jObject.put("MID", MID);
			jObject.put("Phone", phone);
			jObject.put("Psw", psw);
			postJson(jObject.toString(), LOGIN_ACTION, LOGIN_FLAG);
		}catch(JSONException ex){
			ex.printStackTrace();
		}
		
	}
	protected void query(String action,int flag){
		EntranceHttpRequest hRequest=new EntranceHttpRequest(mContext,urlStr+action,KDHttpRequest.HttpMethod.GET);
		Log.e(TAG,"url is "+urlStr+action);
		hRequest.setFlag(flag);
		if(linstener!=null)
			hRequest.setLinstener(linstener);
		hRequest.startAsynchronous();
		stack.add(hRequest);
	}
	protected void post(String action,int flag) {
		KDHttpRequest eRequest = new EntranceHttpRequest(mContext, urlStr + action,
				KDHttpRequest.HttpMethod.POST);
		eRequest.setFlag(flag);
		if(linstener!=null){
			eRequest.setLinstener(linstener);
		}
		eRequest.startAsynchronous();
		stack.add(eRequest);
	}
	protected void postJson(String params,String action,int flag){
		EntranceHttpRequest hRequest=new EntranceHttpRequest(mContext,urlStr+action,KDHttpRequest.HttpMethod.POST);
		StringEntity entity;
		try{
			entity=new StringEntity(params, HTTP.UTF_8);
			hRequest.setEntity(entity);
			hRequest.setFlag(flag);
			if(linstener!=null){
			  hRequest.setLinstener(linstener);
			}
			hRequest.startAsynchronous();
		}catch(Exception ex){
			ex.printStackTrace();
		}
		stack.add(hRequest);
	}
	public KDHttpRequestLinstener getLinstener() {
	    return linstener;	
	}
	public void setLinstener(KDHttpRequestLinstener linstener) {
		this.linstener=linstener;
	}
	
}
