package com.sm.Fragment;

import java.io.Console;

import com.sm.activity.R;
import com.sm.activity.SignInActivity;
import com.sm.common.Const;
import com.sm.interfaces.OnAppCellLinstener;
import com.sm.view.AppCell;
import com.sm.view.AppView;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.TextView;
import android.widget.Toast;

public class AppsFragment extends Fragment implements OnAppCellLinstener{
	  private String title="";
	  private final static String TITLE="title";
	  private LayoutInflater layoutInflater;
	  private AppView appView;
	  private Context mContext;
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
	}

	@Override
	public void onAttach(Activity activity) {
		// TODO Auto-generated method stub
		super.onAttach(activity);
		mContext=activity;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
	}

	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		if(getArguments()!=null){
			title=getArguments().getString(TITLE);
		}
		layoutInflater=inflater;
		// TODO Auto-generated method stub
		View view=inflater.inflate(R.layout.apps_fragment_layou, container,false);
		appView=(AppView) view.findViewById(R.id.appView);
		appView.setOnAppCellLinstener(this);
		
		return view;
	}

	@Override
	public void onCellClick(AppCell cell) {
		// TODO Auto-generated method stub
		int appId=cell.getAppsModel().getAppId();
		String actionString=Const.getActivity(appId);
		actionString=Const.ACTIVITY_PACKAGE_PATH+actionString;
		Intent intent=new Intent(actionString);
		startActivity(intent);
	}
	
	@Override
	public void onDelClick(AppCell cell) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void onAddClick(AppCell cell) {
		// TODO Auto-generated method stub
		
	}
	
    
}
