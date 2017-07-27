package com.sm.activity;

import com.sm366.utils.HttpUtils;
import com.smlib.activity.ShellSplashActivity;
import com.smlib.net.KDHttpRequest;
import com.smlib.net.KDHttpRequest.KDHttpRequestLinstener;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.widget.TextView;

public class SplashActivity extends ShellSplashActivity implements KDHttpRequestLinstener{

	private HttpUtils hu;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		hu=new HttpUtils(this);
		hu.setLinstener(this);
	}
	
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		if(hu!=null){
			hu.release();
			hu=null;
		}
	}

    @Override
	protected void goToLogin() {
		// TODO Auto-generated method stub
		super.goToLogin();
		Log.e("SplashActivity","跳转到登录界面");
		Intent intent=new Intent(this,GuideActivity.class);
		this.startActivity(intent);
		this.finish();
	}
    
    @Override
    protected void Login() {
    	// TODO Auto-generated method stub
    	super.Login();
    	Log.e("SplashActivity","启动登录流程");
    }
	@Override
	public void onRequsetSuccess(KDHttpRequest request) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onRequsetFailed(KDHttpRequest request) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onRequsetError(KDHttpRequest request, Exception e) {
		// TODO Auto-generated method stub
		
	}
	
}
