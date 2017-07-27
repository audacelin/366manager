package com.sm.view;

import com.sm.activity.R;
import com.smlib.utils.AndroidUtils;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.GradientDrawable;
import android.util.AttributeSet;
import android.widget.Button;

public class BaseButtons extends Button {

	private int mIconSize,mIconPadding,mRoundedCornerRadius;
	private boolean mRoundedCorner,mIconCenterAligned,mTransparentBackground;
	private String iconName="";
	private Bitmap mIcon=null;
	private Paint mPaint;
	private Rect mRect;
	private int color=Color.BLACK;
	public BaseButtons(Context context) {
		this(context,null);
		// TODO Auto-generated constructor stub
	}
	public BaseButtons(Context context, AttributeSet attrs) {
		this(context, attrs,0);
		// TODO Auto-generated constructor stub
	}
	public BaseButtons(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		// TODO Auto-generated constructor stub
		init(context,attrs);
		setStyle();
	}
	private void init(Context context,AttributeSet attrs){
		TypedArray array=context.obtainStyledAttributes(attrs, R.styleable.IconButton);
		setDefaultStyle();
		int count=array.getIndexCount();
		
		for(int i=0;i<count;i++){
			int index=array.getIndex(i);
			switch (index) {
			case R.styleable.IconButton_iconSize:
				mIconSize=array.getDimensionPixelSize(index, AndroidUtils.Screen.dp2pix(20.0f));
				break;
			case R.styleable.IconButton_iconCenterAligned:
				mIconCenterAligned=array.getBoolean(index, false);
				break;
			case R.styleable.IconButton_iconPadding:
				mIconPadding=array.getDimensionPixelSize(index, AndroidUtils.Screen.dp2pix(20.0f));
				break;
			case R.styleable.IconButton_roundedCornerRadius:
				mRoundedCornerRadius=array.getDimensionPixelSize(index, AndroidUtils.Screen.dp2pix(20.0f));
				break;
			case R.styleable.IconButton_roundedCorner:
				mRoundedCorner=array.getBoolean(index, false);
				break;
			case R.styleable.IconButton_iconName:
				iconName=array.getString(index);
				break;
			case R.styleable.IconButton_transparentBackground:
				mTransparentBackground=array.getBoolean(index, false);
				break;
			default:
				break;
			}
		}
		array.recycle();
	}
	@Override
	protected void onDraw(Canvas canvas) {
		// TODO Auto-generated method stub
		super.onDraw(canvas);
		
		
	}
	private void setDefaultStyle(){
		
	}
	private void setStyle(){
		setTextColor(Color.WHITE);
		setBackgroundResource(R.drawable.round_corner);
		GradientDrawable gradientDrawable=(GradientDrawable)getBackground().mutate();
		gradientDrawable.setColor(color);
		gradientDrawable.setCornerRadius(0);
		if(mRoundedCorner)
			gradientDrawable.setCornerRadius(mRoundedCornerRadius);
		if(mTransparentBackground){ 
			gradientDrawable.setColor(Color.TRANSPARENT);
			gradientDrawable.setStroke(2,getResources().getColor(R.color.dark_gray));
		}
	    gradientDrawable.invalidateSelf();
        setPadding((int)AndroidUtils.Screen.dp2pix(30.0f),(int)AndroidUtils.Screen.dp2pix(10.0f),
        		(int)AndroidUtils.Screen.dp2pix(30.0f),(int)AndroidUtils.Screen.dp2pix(10.0f));
	}
}
