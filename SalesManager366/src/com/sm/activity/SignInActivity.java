package com.sm.activity;

import com.sm366.location.LocationActivity;
import com.smlib.activity.ShellSMActivity;

import android.app.Activity;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.support.v4.widget.SimpleCursorAdapter.ViewBinder;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

//考勤签到
public class SignInActivity extends LocationActivity implements View.OnClickListener{
    private TextView tv_title,tv_loading;
    private ImageView iv_loading,iv_back;
    private Button bt_location;
    private AnimationDrawable animationDrawable;
    private View loadingProgress;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.signin_activity);
		loadView();
	}
	
	private void loadView(){
		tv_title=(TextView)findViewById(R.id.header_tv_title);
		tv_title.setText("考勤签到");
		
		iv_back=(ImageView)findViewById(R.id.header_iv_left);
		iv_back.setVisibility(View.VISIBLE);
		iv_back.setOnClickListener(this);
		
		loadingProgress=(View)findViewById(R.id.loading_view);
		iv_loading=(ImageView)findViewById(R.id.progress_loading);
		
		bt_location=(Button)findViewById(R.id.check_img);//上报位置或签到
		bt_location.setOnClickListener(this);
		
	}
	//
	private void startLoading(){
		loadingProgress.setVisibility(View.VISIBLE);
		
		iv_loading.setImageResource(R.drawable.clip_loading);
		animationDrawable=(AnimationDrawable) iv_loading.getDrawable();
		animationDrawable.start(); 
	}
	private void stopLoading(){
		
		if(animationDrawable!=null&&loadingProgress.getVisibility()==View.VISIBLE){
			animationDrawable.stop();
			loadingProgress.setVisibility(View.GONE);
		}
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		stopLoading();
		destoryLocation();
	}
	@Override
	public void onClick(View view) {
		// TODO Auto-generated method stub
		switch (view.getId()) {
		case R.id.header_iv_left:
			finish();
			break;
		case R.id.check_img://定位
			Log.e("SignInActivity","startLocation");
			startLocation();
			break;
			
		default:
			break;
		}
	}
	

}
