package com.sm.view;

import java.io.Console;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;

import com.sm.activity.R;

import android.os.Looper;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;

public class ChangeIconText extends View {
  
	private int mColor=0xff45c01a;
	private Bitmap mIconBitmap;
	private String mText="消息";
	private int mTextSize=(int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 12, getResources().getDisplayMetrics());
	
	private Canvas mCanvas;
	private Bitmap mBitmap;
	private Paint mPaint;
	
	private float mAlpha;
	private Rect iconRect;
	private Rect textRect;
	private Paint mTextPaint;
	public ChangeIconText(Context context) {
		this(context,null);
		// TODO Auto-generated constructor stub
	}
	public ChangeIconText(Context context, AttributeSet attrs) {
		this(context, attrs,0);
		// TODO Auto-generated constructor stub
	}
   /*
    * 获取自定义属性的值
    */
	public ChangeIconText(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		// TODO Auto-generated constructor stub
		TypedArray ta=context.obtainStyledAttributes(attrs, R.styleable.ChangeIconText);
		int count=ta.getIndexCount();
		for(int i=0;i<count;i++){
			int attr=ta.getIndex(i);
			switch(attr){
			  case R.styleable.ChangeIconText_view_icon:
				  BitmapDrawable drawable=(BitmapDrawable)ta.getDrawable(attr);
				  mIconBitmap=drawable.getBitmap();
				  break;
			  case R.styleable.ChangeIconText_view_color:
				  mColor=ta.getColor(attr, 0xff45c01a);
				  break;
			  case R.styleable.ChangeIconText_text_size:
				  mTextSize=(int) ta.getDimension(attr,TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 12, 
						  getResources().getDisplayMetrics()));
				  break;
			  case R.styleable.ChangeIconText_view_text:
				  mText=ta.getString(attr);
				  break;
			
			}
			
		}
		ta.recycle();//稀放回收
		init();
	}
	//初始化一些变量
    private void init(){
    	textRect=new Rect();
    	mTextPaint=new Paint();
    	mTextPaint.setTextSize(mTextSize);
    	mTextPaint.setColor(0xff555555);
    	mTextPaint.getTextBounds(mText, 0, mText.length(), textRect);
    }
	@Override
	protected void onDraw(Canvas canvas) {
		// TODO Auto-generated method stub
		canvas.drawBitmap(mIconBitmap, null, iconRect, null);
		//canvas.drawBitmap(mIconBitmap, iconRect.left, iconRect.top, null);
		Log.e("onDraw", "bitmap width is "+mIconBitmap.getWidth()+" and height is "+mIconBitmap.getHeight()
				+" iconRect width is "+iconRect.width()+" and iconRect "+iconRect.height());
		int alpha=(int) Math.ceil(mAlpha * 255);
		getTargetBitmap(alpha);
		drawText(canvas,alpha);
		canvas.drawBitmap(mBitmap, 0,0, null);
		drawTargetText(canvas,alpha);
		super.onDraw(canvas);
	}
	
	@Override
	protected Parcelable onSaveInstanceState() {
		// TODO Auto-generated method stub
		return super.onSaveInstanceState();
	}
	@Override
	protected void onRestoreInstanceState(Parcelable state) {
		// TODO Auto-generated method stub
		super.onRestoreInstanceState(state);
	}
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		// TODO Auto-generated method stub
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		int iconWidth=Math.min(getMeasuredWidth()-getPaddingLeft()-getPaddingRight(),
				getMeasuredHeight()-getPaddingTop()-getPaddingBottom()-textRect.height());
		iconWidth=64;
		int left=getMeasuredWidth()/2 - iconWidth/2;
		int top=(getMeasuredHeight()-textRect.height())/2 - iconWidth/2;
		iconRect=new Rect(left,top,left+64,top+64);
	}
	//在内存中绘制可变色的Icon
	private void getTargetBitmap(int alpha){
		mBitmap=Bitmap.createBitmap(getMeasuredWidth(), getMeasuredHeight(), Config.ARGB_8888);
		mCanvas=new Canvas(mBitmap);
		mPaint=new Paint();
		mPaint.setColor(mColor);
		mPaint.setAntiAlias(true);
		mPaint.setDither(true);
		mPaint.setAlpha(alpha);
		
		mCanvas.drawRect(iconRect, mPaint);
		mPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_IN));
		mPaint.setAlpha(255);
		mCanvas.drawBitmap(mIconBitmap,null,iconRect,mPaint);
	}
	//绘制文本
	private void drawText(Canvas canvas,int alpha){
		int defaultColor=0xff555555;
		mTextPaint.setColor(defaultColor);
		mTextPaint.setAlpha(255 - alpha);
		int x=getMeasuredWidth()/2 - textRect.width()/2;
		int y=iconRect.bottom + textRect.height()+5;
		canvas.drawText(mText, x, y, mTextPaint);
	}
	//绘制变色文本
	private void drawTargetText(Canvas canvas,int alpha){
		mTextPaint.setColor(mColor);
		mTextPaint.setAlpha(alpha);
		int x=getMeasuredWidth()/2 - textRect.width()/2;
		int y=iconRect.bottom + textRect.height()+5;
		canvas.drawText(mText, x, y, mTextPaint);
	}
	//设置透明度
    public void setTextAlpha(float alpha){
    	this.mAlpha=alpha;
    	invalidateView();
    }
    //重绘
    private void invalidateView(){
    	if(Looper.getMainLooper()==Looper.myLooper()){
    		invalidate();
    	}else{
    		postInvalidate();
    	}
    }

	

}
