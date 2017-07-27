package com.sm.common;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Const {
	public static HashMap<Integer, String> hashMap;
    public static final String SM_SERVER_URL="http://172.20.62.102/smLBS/";
    
    /*
     * �ײ���������
     */
    public static final String HEADER_TITLE_NEWS="��Ϣ";
    public static final String HEADER_TITLE_APP="Ӧ��";
    public static final String HEADER_TITLE_WORK="����";
    public static final String HEADER_TITLE_ME="��";
    
    public static final String ACTIVITY_PACKAGE_PATH="com.sm.activity.";
    
    static{
    	hashMap=new HashMap<Integer,String>();
    	hashMap.put(1001, "SignInActivity");//����ǩ��
    	hashMap.put(1002, "SignInCheckActivity");//����ͳ��
    	hashMap.put(1003, "ExtendSignInActivity");//����ǩ��
    	hashMap.put(1004, "ExtendCheckActivity");//����ͳ��
    	
    	hashMap.put(1005, "SignInPointActivity");//����ǩ����
    	hashMap.put(1006, "WorkActivity");//�����ƻ�
    	hashMap.put(1007, "TaskActivity");//��������
    	hashMap.put(1008, "CustomerActivity");//�ͻ�
    	
    	hashMap.put(1009, "DailyActivity");//�ձ�
    	hashMap.put(1010, "MoreActivity");//����
    }
    
    public static String getActivity(int appId) {
    	if(appId==0)
    		return "";
    	String appAction=hashMap.get(appId);
		return appAction;
	}
    
    public enum AppCellMode{
    	JUMP,//���������ת
    	DEL,//���ɾ����ťɾ��
    	ADD	//������Ӧ�����
    }
    
}
