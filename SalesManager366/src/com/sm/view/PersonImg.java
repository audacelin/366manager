package com.sm.view;

import java.io.Console;
import java.io.FileInputStream;
import java.io.FileOutputStream;

import com.sm.activity.R;
import com.smlib.utils.AndroidUtils;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Bitmap.Config;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.media.audiofx.Visualizer.MeasurementPeakRms;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.widget.ImageView;

public class PersonImg extends ImageView {


	private int img_Height=(int)TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 50, getResources().getDisplayMetrics());
	private int img_Width=(int)TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 50, getResources().getDisplayMetrics());
	private String img_pos="";
	private String img_Path="";
	private Paint mPaint;
	private Bitmap mBitmap;
	private int measure_h,measure_w;
	
	public PersonImg(Context context) {
		this(context,null);
		// TODO Auto-generated constructor stub
	}
	public PersonImg(Context context, AttributeSet attrs) {
		this(context, attrs,0);
		// TODO Auto-generated constructor stub
	}
	
	public PersonImg(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs,defStyle);
		// TODO Auto-generated constructor stub
		Log.e("Create", "PersonImage");
		TypedArray array=context.obtainStyledAttributes(attrs, R.styleable.PersonImg);
		int count=array.getIndexCount();
		for(int i=0;i<count;i++){
			int index=array.getIndex(i);
			switch (index) {
			case R.styleable.PersonImg_person_img_height:
				//img_Height=(int) array.getDimension(index, 
						//TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 50, getResources().getDisplayMetrics()));
				img_Height=array.getDimensionPixelSize(index, AndroidUtils.Screen.dp2pix(50));
				Log.e("ImageHeight", "image_height is "+img_Height);
				break;
			case R.styleable.PersonImg_person_img_width:
				//img_Width=(int)array.getDimension(index, 
						//TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 50, getResources().getDisplayMetrics()));
				img_Width=array.getDimensionPixelSize(index, AndroidUtils.Screen.dp2pix(50));
				break;
			case R.styleable.PersonImg_person_img_pos:
				img_pos=array.getString(index);
				break;
			default:
				break;
			}
		}
		array.recycle();
		initImg();
	}
	private void initImg(){
	   try{
		   //从本地文件中加载图片
		   
		   this.mBitmap=BitmapFactory.decodeResource(getResources(), R.drawable.ic_launcher);
		   this.mBitmap=toRoundImage(this.mBitmap);
		   
		   this.mPaint=new Paint();
	   }catch(Exception ex){
		   ex.printStackTrace();
	   }
	   
	}
	private Bitmap toRoundImage(Bitmap bitmap){
		
		Bitmap myBitmap=Bitmap.createBitmap(img_Width, img_Height, Config.ARGB_8888);
		Canvas canvas=new Canvas(myBitmap);
		Paint myPaint=new Paint();
		myPaint.setAntiAlias(true);
		
		RectF rectF=new RectF(0,0,img_Height,img_Width);
		canvas.drawRoundRect(rectF, img_Height/2, img_Height/2, myPaint);
		
		myPaint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));
		canvas.drawBitmap(bitmap, null, rectF, myPaint);
		
		return myBitmap;
	}
	public void setImgPath(String path){
		this.img_Path=path;
	}
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		// TODO Auto-generated method stub
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		
		int width_mode=MeasureSpec.getMode(widthMeasureSpec);
		int width_size=MeasureSpec.getSize(widthMeasureSpec);
		
		int height_mode=MeasureSpec.getMode(heightMeasureSpec);
		int height_size=MeasureSpec.getSize(heightMeasureSpec);
		if(width_mode==MeasureSpec.EXACTLY){
		
			measure_w=width_size;
			Log.e("EXACTLY", "measure_w is "+measure_w);
		}else{
			
		}
		if(height_mode==MeasureSpec.EXACTLY){
			measure_h=height_size;
			Log.e("AT_MOST", "measure_h is "+measure_h);
		}
		Log.e("PersonImg","width_mode is "+width_mode+"and width is "+width_size
		+" and img_width is "+img_Width);
	}
	@Override
	protected void onDraw(Canvas canvas) {
		// TODO Auto-generated method stub
		super.onDraw(canvas);
		Matrix matrix=new Matrix();
		matrix.postTranslate((measure_w - img_Width)/2, (measure_h - img_Height)/2);
		canvas.drawBitmap(this.mBitmap, matrix, mPaint);
	}
	
}
