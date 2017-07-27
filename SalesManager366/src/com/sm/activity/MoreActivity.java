package com.sm.activity;

import com.amap.api.services.route.RidePath;
import com.sm.interfaces.OnAppCellLinstener;
import com.sm.view.AppCell;
import com.sm.view.AppView;
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

//更多
public class MoreActivity extends ShellSMActivity implements View.OnClickListener
             ,OnAppCellLinstener {
	private TextView tv_title,tv_loading;
    private ImageView iv_loading,iv_back;
    private AnimationDrawable animationDrawable;
    private View loadingProgress;
    
    private AppView usualAppView,unusualAppView;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.more_activity_layout);
		initView();
	}
	
	private void initView(){
		tv_title=(TextView)findViewById(R.id.header_tv_title);
		tv_title.setText("我的应用");
		
		iv_back=(ImageView)findViewById(R.id.header_iv_left);
		iv_back.setVisibility(View.VISIBLE);
		iv_back.setOnClickListener(this);
		
		loadingProgress=(View)findViewById(R.id.loading_view);
		iv_loading=(ImageView)findViewById(R.id.progress_loading);
		
		usualAppView=(AppView)findViewById(R.id.usual_appView);
		usualAppView.setOnAppCellLinstener(this);
		unusualAppView=(AppView)findViewById(R.id.unusual_appView);
		unusualAppView.setOnAppCellLinstener(this);
	}
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
	}

	@Override
	public void onClick(View view) {
		// TODO Auto-generated method stub
		switch (view.getId()) {
		case R.id.header_iv_left:
			finish();
			break;

		default:
			break;
		}
	}

	//
	@Override
	public void onCellClick(AppCell cell) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onDelClick(AppCell cell) {
		// TODO Auto-generated method stub
		Log.e("MoreActivity AppCell DEL","cell is "+cell.getAppsModel().getAppName());
	}

	@Override
	public void onAddClick(AppCell cell) {
		// TODO Auto-generated method stub
		Log.e("MoreActivity AppCelll ADD", "cell is "+cell.getAppsModel().getAppName());
	}
}
