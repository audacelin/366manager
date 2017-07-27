package com.sm.view;

import com.sm.activity.R;
import com.sm.common.Const.AppCellMode;
import com.sm366.entity.AppsModel;

import android.R.integer;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.util.Log;

public class AppCell {
    private Drawable mDrawable;
    private Bitmap mBitmap;
    private int mTextSize;
    private Context mContext;
    private AppsModel appsModel;
    private Rect mRect;
    private int marginLeft,marginTop;
    private float imgPersent=0.85f;//图片在整个表格的占比
    private Rect textRect;
    private boolean visible;//item的可见性  默认可见
    
	
	public AppCell(Context context,Rect bounds,AppsModel model,int textSize){
		this.mContext=context;
		this.mTextSize=textSize;
		this.appsModel=model;
		this.mRect=bounds;
		this.visible=true;
	}
	//根据图标id获取Drawable R.Drawable.id
	private int GetDrawableByName(String name,String resType){
		int id=0;
		id=mContext.getResources().getIdentifier(name, resType,mContext.getPackageName());
		return id;
	}
	//绘制应用表格
	public void draw(Canvas canvas,AppCellMode mode) {
		if(!this.visible)
			return;
		int width=mRect.width();
		int height=mRect.height();
		Bitmap imgBitmap=BitmapFactory.decodeResource(mContext.getResources(), GetDrawableByName(appsModel.getAppImg(), "drawable"));
		drawBitmap(canvas, imgBitmap, mRect);
		if(mode==AppCellMode.DEL)
			drawDel(canvas, mRect);
		drawText(canvas, appsModel.getAppName(), mRect);
	}
	private void drawDel(Canvas canvas,Rect bounds){
		Bitmap delBitmap=BitmapFactory.decodeResource(mContext.getResources(), R.drawable.delete);
		canvas.drawBitmap(delBitmap, bounds.right-delBitmap.getWidth(),bounds.top+2 , null);
	}
	private void drawBitmap(Canvas canvas,Bitmap mBitmap,Rect bounds){
		marginLeft=(bounds.width() - mBitmap.getWidth())/2;
		marginTop=(int) ((bounds.width() * imgPersent-mBitmap.getWidth())/2);
		marginTop=Math.abs(marginTop);
		canvas.drawBitmap(mBitmap,bounds.left+marginLeft , bounds.top+marginTop, null);
	}
	private void drawText(Canvas canvas,String mText,Rect bounds){
		textRect=new Rect();
		Paint paint=new Paint();
		paint.setARGB(125, 0, 0, 0);
		paint.setAntiAlias(true);
		paint.setTextSize(30);
		paint.getTextBounds(mText, 0, mText.length(), textRect);
		canvas.drawText(mText, 0, mText.length(), bounds.left+(bounds.width()-textRect.width())/2
				, bounds.bottom-textRect.height()/2-(1-imgPersent)*bounds.height()/2
				, paint);
	}
	public AppsModel getAppsModel() {
		return this.appsModel;
	}
	public Rect getRect(){
		return this.mRect;
	}
	public void setVisible(boolean isVisible){
		this.visible=isVisible;
	}
	
	
}
