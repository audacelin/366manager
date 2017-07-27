package com.sm.view;

import java.util.ArrayList;
import java.util.List;

import com.sm366.entity.AppsModel;
import com.sm.activity.R;
import com.sm.common.Const.AppCellMode;
import com.sm.interfaces.OnAppCellLinstener;

import android.R.bool;
import android.R.integer;
import android.R.string;
import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.AvoidXfermode.Mode;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.os.Handler;
import android.os.Looper;
import android.os.Vibrator;
import android.text.InputFilter.LengthFilter;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;

public class AppView2 extends View {
	private List<AppsModel> list;//
	private Resources mResources; 
	private Context mContext;
	private int columnCount=0,rowsCount=0;
	private int textSize=(int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 16, getResources().getDisplayMetrics());
	private int screenWidth;
	private float cell_width,cell_height;
	private AppCell[][] modelArr;
	private OnAppCellLinstener mOnAppCellLinstener;
	private AppCellMode cellMode=AppCellMode.JUMP;
	private float bWidth=0f,bHeight=0f;//计算APPCell 里除跳转模式外其他位图的大小
	
	private Vibrator mVibrator;
	public AppView2(Context context) {
		this(context,null);
		// TODO Auto-generated constructor stub
	}
	public AppView2(Context context, AttributeSet attrs) {
		this(context, attrs,0);
		// TODO Auto-generated constructor stub
	}
	public AppView2(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		mContext=context;

		TypedArray array=context.obtainStyledAttributes(attrs, R.styleable.AppView);
		int count=array.getIndexCount();
		for(int i=0;i<count;i++){
			int id=array.getIndex(i);
			switch (id) {
			case R.styleable.AppView_column_count:
				columnCount=array.getInt(id, 0);
				break;
			case R.styleable.AppView_app_title_textsize:
				textSize=(int)array.getDimension(id, TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 16, getResources().getDisplayMetrics()));
				break;
			case R.styleable.AppView_app_mode:
				String mode=array.getString(id);
				cellMode=mode.equalsIgnoreCase("jump")?AppCellMode.JUMP
						:(mode.equalsIgnoreCase("del")?AppCellMode.DEL:AppCellMode.ADD);
				break;
			}
		}
		array.recycle();
		initResources(context);
	}
	private WindowManager mWindowManager;
	private void initResources(Context context){
		//震动
		mVibrator=(Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
		//
		mWindowManager=(WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
		
		mResources=context.getResources();
		screenWidth=mResources.getDisplayMetrics().widthPixels;
		list=new ArrayList<AppsModel>();
		list.add(new AppsModel("考勤签到","icon_esignin",1001));
		list.add(new AppsModel("考勤统计","icon_statistics",1002));
		list.add(new AppsModel("外勤签到","icon_signin",1003));
		list.add(new AppsModel("外勤统计","icon_extend",1004));
		
		list.add(new AppsModel("外勤签到点","icon_extend",1005));
		list.add(new AppsModel("工作计划","icon_work",1006));
		list.add(new AppsModel("工作任务","icon_schedule",1007));
		list.add(new AppsModel("客户","icon_customer",1008));
		
		list.add(new AppsModel("日报","icon_daily",1009));
		list.add(new AppsModel("定制化应用","icon_daily",1011));
		list.add(new AppsModel("更多","icon_more",1010));
		
		//如果当前的是可删除模式计算删除按钮的大小
		if(cellMode!=AppCellMode.JUMP){
			setBitmapWAndH(cellMode);
		}
	}
	private void setBitmapWAndH(AppCellMode mode){
		int id=mode==AppCellMode.DEL?R.drawable.delete:R.drawable.work;
		Bitmap mBitmap=BitmapFactory.decodeResource(getResources(),R.drawable.delete);
		bWidth=mBitmap.getWidth();
		bHeight=mBitmap.getHeight();
		Log.e("Caculate width and height", "bWidth is "+bWidth+" and bHeight is "+bHeight);
		mBitmap.recycle();
	}
	@Override
	protected void onLayout(boolean changed, int left, int top, int right,
			int bottom) {
		// TODO Auto-generated method stub
		int width=getWidth();
		rowsCount=list.size()/columnCount+(list.size()%columnCount!=0?1:0);
		cell_width=width/columnCount;
		cell_height=(getHeight()/rowsCount);
		cell_height=cell_width=Math.min(cell_height, cell_width);
	
		modelArr=new AppCell[rowsCount][columnCount];
		initCells(rowsCount,modelArr);
		super.onLayout(changed, left, top, right, bottom);
		
	}
	@Override
	protected void onDraw(Canvas canvas) {
		// TODO Auto-generated method stub
		super.onDraw(canvas);
		drawGrid(canvas);
		for(int i=0,len=modelArr.length;i<len;i++){
			for(int j=0;j<modelArr[i].length;j++){
				AppCell cell=modelArr[i][j];
				if(cell!=null)
				    modelArr[i][j].draw(canvas,cellMode);
			}
		}
	} 
	//配置每个应用表格的信息
	private void initCells(int rowsCount,AppCell[][] cells){
		Rect bounds=new Rect(0, 0, (int)cell_width, (int)cell_height);
		for(int i=0;i<rowsCount;i++){
			for(int j=0;j<columnCount;j++){
				if((columnCount*i+j)<list.size()){
					AppsModel model=list.get(columnCount * i + j);
					AppCell cell=new AppCell(mContext,new Rect(bounds),model,textSize);
					cells[i][j]=cell;
					bounds.offsetTo((int)(cell_width * (j+1)), bounds.top);
				}else{
					break;
				}
			}
			bounds.offsetTo(bounds.left, (int)(cell_height * (i+1)));
			bounds.left=0;
			bounds.right=(int)cell_width;
		}
		
	}
	//画栅格
	private void drawGrid(Canvas canvas){
		Paint mPaint=new Paint();
		mPaint.setColor(0xffd1cdc9);
		mPaint.setStrokeWidth(1.0f);
		
		float startX,startY,stopX,stopY;
		startX=0;stopX=cell_width * columnCount;
		//画横线
		for(int i=0;i<=rowsCount;i++){
			startY=cell_height * i;
			stopY=startY;
			canvas.drawLine(startX, startY, stopX, stopY, mPaint);
		}
		startY=0;
		stopY=cell_height * (list.size()/columnCount+(list.size() % columnCount==0?0:1));
		//话竖线
		for(int i=0;i<columnCount;i++){
			startX=cell_width * i;
			stopX=startX;
			canvas.drawLine(startX, startY, stopX, stopY, mPaint);
		}
	}
	private AppCell curAppCell;
	private boolean isDrag=false;//是否可以拖动
	private float mDownX=0,mDownY=0;
	private Handler myHandler=new Handler();
	private long dragLongTime=1000;
	private Runnable mLongClickRunnable=new Runnable() {
		
		@Override
		public void run() {
			// TODO Auto-generated method stub
			
		}
	};
	@Override
	public boolean dispatchTouchEvent(MotionEvent event) {
		// TODO Auto-generated method stub
		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN:
			mDownX=event.getX();
			mDownY=event.getY();
			
			break;

		default:
			break;
		}
		return super.dispatchTouchEvent(event);
	}
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		// TODO Auto-generated method stub
		float startX=0,startY=0;
		boolean touchMode=false;
		
		if(event.getAction()==MotionEvent.ACTION_DOWN){
			startX=event.getX();
			startY=event.getY();
			final int column=(int)(startX/cell_width);
			final int row=(int)(startY/cell_height);
			if(row<rowsCount&&(row*columnCount+column)<list.size()){
				curAppCell=modelArr[row][column];
				if(cellMode==AppCellMode.DEL){
					Rect targetBounds=curAppCell.getRect();
					Rect touchBoundRect=new Rect((int)(targetBounds.right-bWidth-10),targetBounds.top,
							targetBounds.right,(int)(targetBounds.top+bHeight+10));
					Log.e("CurCell", "bounds right is "+touchBoundRect.right
							+" and top is "+touchBoundRect.top
							+" and left is "+touchBoundRect.left+" and bottom is "+touchBoundRect.bottom);
					if(startX<touchBoundRect.right&&startX>touchBoundRect.left
							&&startY<touchBoundRect.bottom&&startY>touchBoundRect.top){
						//Log.e("Del","del is true and start x is "+startX+" and startY is "+startY);
					    touchMode=true;
					}
				}else if(cellMode==AppCellMode.ADD){
					
				}
			}else{
				//点击非应用实体，curAppCell置为空值，并结束当前的事件
				curAppCell=null;
				return false;
			}
		}else if(event.getAction()==MotionEvent.ACTION_UP){
			if(curAppCell!=null&&curAppCell.getAppsModel()!=null
					&&mOnAppCellLinstener!=null){
				if(cellMode==AppCellMode.JUMP)
				  mOnAppCellLinstener.onCellClick(curAppCell);
				else if(cellMode==AppCellMode.DEL)
				  mOnAppCellLinstener.onDelClick(curAppCell);
				else if(cellMode==AppCellMode.ADD)
				  mOnAppCellLinstener.onAddClick(curAppCell);	
			}
		}else if(event.getAction()==MotionEvent.ACTION_MOVE){
			return true;
		}else{
			return true;
		}
		return true;
	}
	private AppCell getTargetCell(){
		
		return null;
	}
    private boolean isValidPosition(){
    	
    	return false;
    }
	public void setAppsList(ArrayList<AppsModel> appsList){
		this.list=appsList;
		reDraw();
	}
	public void setAppCellMode(AppCellMode mode){
		this.cellMode=mode;
		reDraw();
	}
	public void setToReDraw(ArrayList<AppsModel> appList,AppCellMode mode){
		this.list=appList;
		this.cellMode=mode;
		reDraw();
	}
	//重新绘制
	private void reDraw(){
		//UI主线程
		if(Looper.getMainLooper()==Looper.myLooper()){
			invalidate();
		}else{
			postInvalidate();
		}
	}
	
	public void setOnAppCellLinstener(OnAppCellLinstener onAppCellLinstener) {
		this.mOnAppCellLinstener=onAppCellLinstener;
	}

}
