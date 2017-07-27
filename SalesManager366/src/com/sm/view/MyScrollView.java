package com.sm.view;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

public class MyScrollView extends ViewPager {
    private boolean canScroll=false;
	public MyScrollView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	public MyScrollView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}
	/*
	 * 设置是否可以左右滑动
	 */
	public void setCanScroll(boolean ifCanScroll){
		this.canScroll=ifCanScroll;
	}

	@Override
	public boolean onInterceptTouchEvent(MotionEvent arg0) {
		// TODO Auto-generated method stub
		if(!this.canScroll)
			return false;
		return super.onInterceptTouchEvent(arg0);
	}

	@Override
	public boolean onTouchEvent(MotionEvent arg0) {
		// TODO Auto-generated method stub
		if(!this.canScroll)
			return false;
		return super.onTouchEvent(arg0);
	}
	
	
   
}
