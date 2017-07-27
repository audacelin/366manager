package com.sm.common;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Const {
	public static HashMap<Integer, String> hashMap;
    public static final String SM_SERVER_URL="http://172.20.62.102/smLBS/";
    
    /*
     * 底部导航数据
     */
    public static final String HEADER_TITLE_NEWS="消息";
    public static final String HEADER_TITLE_APP="应用";
    public static final String HEADER_TITLE_WORK="工作";
    public static final String HEADER_TITLE_ME="我";
    
    public static final String ACTIVITY_PACKAGE_PATH="com.sm.activity.";
    
    static{
    	hashMap=new HashMap<Integer,String>();
    	hashMap.put(1001, "SignInActivity");//考勤签到
    	hashMap.put(1002, "SignInCheckActivity");//考勤统计
    	hashMap.put(1003, "ExtendSignInActivity");//外勤签到
    	hashMap.put(1004, "ExtendCheckActivity");//外勤统计
    	
    	hashMap.put(1005, "SignInPointActivity");//外勤签到点
    	hashMap.put(1006, "WorkActivity");//工作计划
    	hashMap.put(1007, "TaskActivity");//工作任务
    	hashMap.put(1008, "CustomerActivity");//客户
    	
    	hashMap.put(1009, "DailyActivity");//日报
    	hashMap.put(1010, "MoreActivity");//更多
    }
    
    public static String getActivity(int appId) {
    	if(appId==0)
    		return "";
    	String appAction=hashMap.get(appId);
		return appAction;
	}
    
    public enum AppCellMode{
    	JUMP,//点击可以跳转
    	DEL,//点击删除按钮删除
    	ADD	//点击添加应用添加
    }
    
}
