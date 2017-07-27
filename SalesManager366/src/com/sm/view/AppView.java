package com.sm.view;

import java.util.ArrayList;
import java.util.Currency;
import java.util.List;
import com.sm366.entity.AppsModel;
import com.sm.activity.R;
import com.sm.common.Const.AppCellMode;
import com.sm.interfaces.OnAppCellLinstener;
import android.R.bool;
import android.R.integer;
import android.R.string;
import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.AvoidXfermode.Mode;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
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
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

@SuppressLint("NewApi")
public class AppView extends View {
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
	private float bWidth=0f,bHeight=0f;//����APPCell �����תģʽ������λͼ�Ĵ�С
	
	private Vibrator mVibrator;
	private int targetRow=0,targetColumn=0;
	private LayoutInflater mLayoutInflater;
	private int mStatusHeight=0;//״̬���ĸ߶�
	private View mWindowView=null;//������ͼ
	public AppView(Context context) {
		this(context,null);
		// TODO Auto-generated constructor stub
	}
	public AppView(Context context, AttributeSet attrs) {
		this(context, attrs,0);
		// TODO Auto-generated constructor stub
	}
	public AppView(Context context, AttributeSet attrs, int defStyleAttr) {
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
	private WindowManager.LayoutParams mWindowLayoutParams;
	private void initResources(Context context){
		//��
		mVibrator=(Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
		//
		mWindowManager=(WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
		
		mLayoutInflater=LayoutInflater.from(context);
		mStatusHeight=getStatusHeight(context);
		Log.e("StatusHeight","status height is "+mStatusHeight);
		
		mResources=context.getResources();
		screenWidth=mResources.getDisplayMetrics().widthPixels;
		list=new ArrayList<AppsModel>();
		list.add(new AppsModel("����ǩ��","icon_esignin",1001));
		list.add(new AppsModel("����ͳ��","icon_statistics",1002));
		list.add(new AppsModel("����ǩ��","icon_signin",1003));
		list.add(new AppsModel("����ͳ��","icon_extend",1004));
		
		list.add(new AppsModel("����ǩ����","icon_extend",1005));
		list.add(new AppsModel("�����ƻ�","icon_work",1006));
		list.add(new AppsModel("��������","icon_schedule",1007));
		list.add(new AppsModel("�ͻ�","icon_customer",1008));
		
		list.add(new AppsModel("�ձ�","icon_daily",1009));
		list.add(new AppsModel("���ƻ�Ӧ��","icon_daily",1011));
		list.add(new AppsModel("����","icon_more",1010));
		
		//�����ǰ���ǿ�ɾ��ģʽ����ɾ����ť�Ĵ�С
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
	//����ÿ��Ӧ�ñ�����Ϣ
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
	//��դ��
	private void drawGrid(Canvas canvas){
		Paint mPaint=new Paint();
		mPaint.setColor(0xffd1cdc9);
		mPaint.setStrokeWidth(1.0f);
		
		float startX,startY,stopX,stopY;
		startX=0;stopX=cell_width * columnCount;
		//������
		for(int i=0;i<=rowsCount;i++){
			startY=cell_height * i;
			stopY=startY;
			canvas.drawLine(startX, startY, stopX, stopY, mPaint);
		}
		startY=0;
		stopY=cell_height * (list.size()/columnCount+(list.size() % columnCount==0?0:1));
		//������
		for(int i=0;i<columnCount;i++){
			startX=cell_width * i;
			stopX=startX;
			canvas.drawLine(startX, startY, stopX, stopY, mPaint);
		}
	}
	private AppCell curAppCell;
	private boolean isDrag=false;//�Ƿ�����϶�
	private float mDownX=0,mDownY=0;
	private Handler myHandler=new Handler();
	private long dragLongTime=1000;
	private Runnable mLongClickRunnable=new Runnable() {
		
		@Override
		public void run() {
			// TODO Auto-generated method stub
			isDrag=true;
			mVibrator.vibrate(50);
			invalidate();
			createDragItem();
		}
	};
	@Override
	public boolean dispatchTouchEvent(MotionEvent event) {
		// TODO Auto-generated method stub
		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN:
			mDownX=event.getX();
			mDownY=event.getY();
			if(!isValidPosition(mDownX, mDownY))
				return super.dispatchTouchEvent(event);
			//����1000�������ʱִ��
			myHandler.postDelayed(mLongClickRunnable, dragLongTime);
			break;
		case MotionEvent.ACTION_MOVE:
			
			break;
		case MotionEvent.ACTION_UP:
			myHandler.removeCallbacks(mLongClickRunnable);
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
				//�����Ӧ��ʵ�壬curAppCell��Ϊ��ֵ����������ǰ���¼�
				curAppCell=null;
				return false;
			}
		}else if(event.getAction()==MotionEvent.ACTION_UP){
			if(curAppCell!=null&&curAppCell.getAppsModel()!=null
					&&mOnAppCellLinstener!=null&&!isDrag){
				if(cellMode==AppCellMode.JUMP)
				  mOnAppCellLinstener.onCellClick(curAppCell);
				else if(cellMode==AppCellMode.DEL)
				  mOnAppCellLinstener.onDelClick(curAppCell);
				else if(cellMode==AppCellMode.ADD)
				  mOnAppCellLinstener.onAddClick(curAppCell);	
			}else{
				stopDragView();
				isDrag=false;
				createAnimationSet();
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
	@SuppressLint("NewApi")
	private void createAnimationSet(){
		AppCell appView=modelArr[0][0];
		ObjectAnimator objectAnimator=ObjectAnimator.ofFloat(appView, "transitionY", 300);
		objectAnimator.start();
	}
	
	//ֹͣ�϶����񣬲�ͨ���ػ�����ص�Ӧ�ûָ��ɼ�
	private void stopDragView(){
		
		removeDragView();
		curAppCell.setVisible(true);
		reDraw();
	}
	private void removeDragView(){
		if(mWindowView!=null){
			mWindowManager.removeView(mWindowView);
			mWindowView=null;
		}
	}
	private void createDragItem(){
		mWindowLayoutParams=new WindowManager.LayoutParams();
		mWindowLayoutParams.gravity=Gravity.TOP|Gravity.LEFT;
		mWindowLayoutParams.alpha=0.95f;
		mWindowLayoutParams.width=curAppCell.getRect().width();
		mWindowLayoutParams.height=curAppCell.getRect().height();
		mWindowLayoutParams.x=curAppCell.getRect().left+20;
		mWindowLayoutParams.y=curAppCell.getRect().top + 20 + mStatusHeight +48;
		Log.e("CreateDragItem","Top is "+(curAppCell.getRect().left+20)+" and y is "+(curAppCell.getRect().top+20));
		mWindowLayoutParams.flags=WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE|
				WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE;
		mWindowView=createItemView();
		mWindowManager.addView(mWindowView, mWindowLayoutParams);
	}
	private View createItemView(){
	    View view=mLayoutInflater.inflate(R.layout.dragitem, null);
	    view.setLayoutParams(new LinearLayout.LayoutParams(190,190));
	    ImageView imgView=(ImageView) view.findViewById(R.id.iv_dragitem_img);
	    imgView.setImageBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.icon_daily));
	    TextView textView=(TextView)view.findViewById(R.id.tv_dragitem_name);
	    textView.setText("����");
		return view;
	}
    private boolean isValidPosition(float startX,float startY){
		final int column=(int)(startX/cell_width);
		final int row=(int)(startY/cell_height);
		if(row<rowsCount&&(row*columnCount+column)<list.size()){
			curAppCell=modelArr[row][column];
			curAppCell.setVisible(false);
			return true;
		}else{
			//�����Ӧ��ʵ�壬curAppCell��Ϊ��ֵ����������ǰ���¼�
			return false;
		}
    }
    private int getStatusHeight(Context context){
    	int statusHeight=0;
    	Rect mRect=new Rect();
        ((Activity) context).getWindow().getDecorView().getWindowVisibleDisplayFrame(mRect);
        statusHeight=mRect.top;
        if(statusHeight==0){
        	Class<?> localClass;
        	try{
        		localClass=Class.forName("com.android.internal.R$dimen");
        		Object localObject=localClass.newInstance();
        		int iH=Integer.parseInt(localClass.getField("status_bar_height").get(localObject).toString());
        		statusHeight=context.getResources().getDimensionPixelOffset(iH);
        	}catch(Exception ex){
        		ex.printStackTrace();
        		return 0;
        	}
        }
    	return statusHeight;
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
	//���»���
	private void reDraw(){
		//UI���߳�
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
