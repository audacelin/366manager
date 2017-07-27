package com.sm366.entity;

import java.io.Serializable;

import org.json.JSONException;
import org.json.JSONObject;

public class AppsModel implements Serializable,Json2Obj{
  //应用名称
  private String FName;
  //应用图标
  private String FAppImg;
  //应用id
  private int FAppId;
  public AppsModel(){
	  
  }
  public AppsModel(String appTitle,String imgName,int appId) {
	this.FName=appTitle;
	this.FAppImg=imgName;
	this.FAppId=appId;
  }
  
  public AppsModel ValueOf(JSONObject obj) {
	try {
		String appName=obj.getString("FName");
		String imgName=obj.getString("FAppImg");
		int appId=obj.getInt("FAppId");
		return new AppsModel(appName,imgName,appId);
	} catch (JSONException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	return null;
  }
public String getAppName() {
	return this.FName;
}
public void setAppTitle(String appName) {
	this.FName = appName;
}
public String getAppImg() {
	return this.FAppImg;
}
public void setImgName(String imgName) {
	this.FAppImg = imgName;
}
public int getAppId() {
	return this.FAppId;
}
public void setAppId(int appId) {
	this.FAppId = appId;
}
  
  
}
