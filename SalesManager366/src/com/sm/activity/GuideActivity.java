package com.sm.activity;

import java.util.ArrayList;

import com.sm366.adapter.GuideAdapter;
import com.smlib.activity.ShellSMActivity;

import android.R.integer;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.support.v4.widget.SimpleCursorAdapter.ViewBinder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class GuideActivity extends ShellSMActivity implements OnClickListener,
               OnPageChangeListener{

	private ViewPager mViewPager;
	private Button bt_exp;
	private int[] guideArr=new int[]{
		R.drawable.guide_dot_black,R.drawable.guide_dot_white
	}; 
	private ArrayList<View> list;
	private ArrayList<ImageView> ivList;
	private LinearLayout llLayout;
    private int curPos=0,lastPos=0;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.guide_activity);
		init();
		initView();
	}
	private void initView(){
		mViewPager=(ViewPager)this.findViewById(R.id.viewPager);
		mViewPager.setAdapter(new GuideAdapter(list));
		mViewPager.setOnPageChangeListener(this);
		mViewPager.setCurrentItem(curPos);
		
		llLayout=(LinearLayout)this.findViewById(R.id.ll_dot);
		
		for(int i=0,len=ivList.size();i<len;i++){
			llLayout.addView(ivList.get(i));
		}
		
		bt_exp=(Button)this.findViewById(R.id.bt_exp);
		bt_exp.setOnClickListener(this);
		
//		bt_register=(Button)this.findViewById(R.id.bt_register);
//		bt_register.setOnClickListener(this);
	} 
	private void init(){
		list=new ArrayList<View>();
		ivList=new ArrayList<ImageView>();
		
		
		LayoutInflater inflater=getLayoutInflater();
		View view1=inflater.inflate(R.layout.guide_one, null);
		View view2=inflater.inflate(R.layout.guide_two, null);
		View view3=inflater.inflate(R.layout.guide_three, null);
		View view4=inflater.inflate(R.layout.guide_four, null);
		
		list.add(view1);
		list.add(view2);
		list.add(view3);
		list.add(view4);
		
		for(int i=0,len=list.size();i<len;i++){
			ImageView ivImageView=new ImageView(this);
			ivImageView.setImageResource(R.drawable.dot);
			ivImageView.setPadding(4, 3, 4, 3);
			ivImageView.setEnabled(false);
			ivList.add(ivImageView);
		}
		ivList.get(0).setEnabled(true);
		
	}
	private void setDotEnable(int curPos,int lastPos){
		ivList.get(lastPos).setEnabled(false);
		ivList.get(curPos).setEnabled(true);
	}
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.bt_exp:
			Intent loginIntent=new Intent(this,LogInActivity.class);
			startActivity(loginIntent);
			this.finish();
			break;
		case R.id.bt_register:
			Intent registerIntent=new Intent(this,RegisterActivity.class);
			startActivity(registerIntent);
			this.finish();
			break;
		default:
			break;
		}
	}
	@Override
	public void onPageScrollStateChanged(int state) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void onPageScrolled(int position,float positionOffset, int positionOffsetPixels) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void onPageSelected(int position) {
		// TODO Auto-generated method stub
		if(position>=0&&position<=list.size()){
			lastPos=curPos;
			curPos=position;
			setDotEnable(curPos,lastPos);
		}

	}
	
	
}
