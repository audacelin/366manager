package com.sm.view;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

import com.sm.activity.R;
import com.sm.interfaces.OnVertifyCodeLinstener;

import android.R.integer;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.graphics.PointF;
import android.graphics.Paint.Style;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;

public class VertifyCode extends View {
	private char[] chars={
			
			'a','b','c','d', 'e', 'f', 'g', 'h', 'j', 'k', 'l', 'm',
            'n', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z',
            '0','1','2','3','4','5','6','7','8','9',
            'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M',
            'N', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'	
	};
	private Paint textPaint;//随机文本画笔
	private Paint pointPaint;//干扰点画笔
	private Paint pathPaint;
	private ArrayList<SmallPoint> points;//干扰点集合
	private ArrayList<LinePath> paths;//干扰线集合
	private ArrayList<VertifyText> texts;//验证码
	
	private OnVertifyCodeLinstener mOnVertifyCodeLinstener;
	private int codeCount=0;
	private float codeWidth=TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_PX,
			50, getResources().getDisplayMetrics());
	private float codeHeight=TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_PX,
			40, getResources().getDisplayMetrics());
	private Random mRandom;
	private boolean canDraw=false;
	public VertifyCode(Context context) {
		this(context,null);
		// TODO Auto-generated constructor stub
	}
	public VertifyCode(Context context, AttributeSet attrs) {
		this(context, attrs,0);
		// TODO Auto-generated constructor stub
	}
	
	public VertifyCode(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		// TODO Auto-generated constructor stub
		TypedArray array=context.obtainStyledAttributes(attrs, R.styleable.VertifyCode);
		int arrayCount=array.getIndexCount();
		for(int i=0;i<arrayCount;i++){
			int index=array.getIndex(i);
			switch (index) {
			case R.styleable.VertifyCode_code_count:
				codeCount=array.getInteger(index, 4);
				break;
			case R.styleable.VertifyCode_code_width:
				codeWidth=array.getDimension(index, TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_PX,
						50, getResources().getDisplayMetrics()));
				break;
			case R.styleable.VertifyCode_code_height:
				codeHeight=array.getDimension(index, TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_PX,
			    40, getResources().getDisplayMetrics()));
				break;
			default:
				break;
			}
		}
		init();
		array.recycle();
	}
	
	private void init(){
		points=new ArrayList<SmallPoint>();
		paths=new ArrayList<LinePath>();
		texts=new ArrayList<VertifyText>();
		mRandom=new Random();
		textPaint=new Paint();//初始化文本画笔
		textPaint.setFakeBoldText(true);//是否粗体
		textPaint.setAntiAlias(true);
		textPaint.setStrokeWidth(3);
		textPaint.setTextSize(35);
		
		//初始化干扰点画笔
		pointPaint=new Paint();
		pointPaint.setStrokeCap(Paint.Cap.ROUND);
		pointPaint.setStrokeWidth(4);
		
		//初始化干扰线
		pathPaint=new Paint();
		pathPaint.setStrokeWidth(4);
		pathPaint.setColor(Color.GRAY);
	    pathPaint.setStyle(Paint.Style.STROKE);
	    pathPaint.setStrokeCap(Paint.Cap.ROUND);
	    
	}
	//初始化干扰点和干扰线
	private void initPointAndPath(){
		if(points.size()>0){
			points.clear();
		}
		if(paths.size()>0){
			paths.clear();
		}
		int width=getWidth();
		int height=getHeight();
		for(int i=0,len=80;i<len;i++){
			SmallPoint smallPoint=new SmallPoint();
			PointF pointF=new PointF(mRandom.nextInt(width),mRandom.nextInt(height));
			smallPoint.setPointF(pointF);
			int color=Color.argb(100, mRandom.nextInt(200)+10, 
		    		mRandom.nextInt(200)+10, mRandom.nextInt(200)+10);
			smallPoint.setColor(color);
			points.add(smallPoint);
		}
		for(int i=0,len=2;i<len;i++){
			LinePath linePath=new LinePath();
			Path path=new Path();
			int startX=mRandom.nextInt(width/5)+2,
				startY=mRandom.nextInt(width/5)+15*(mRandom.nextInt()==0?-1:1),
				endX=mRandom.nextInt(width/2)+70,
				endY=mRandom.nextInt(width/5)+20;
			path.moveTo(startX, startY);
			int quadX=getHeight()/2+mRandom.nextInt(10),
					quady=getHeight()/2+mRandom.nextInt(10);
			path.quadTo(quadX, quady, endX, endY);
			linePath.setPath(path);
			int color= Color.argb(100, mRandom.nextInt(220)+20,
					mRandom.nextInt(220)+20, mRandom.nextInt(230)+20);
			linePath.setColor(color);
			paths.add(linePath);
		}
		
	}
	private void createCode(){
		if(texts.size()>0)
			texts.clear();
		int charLen=chars.length;
		float charLenght=getWidth()/codeCount;
		String codeResult="";
		for(int i=0;i<codeCount;i++){
			VertifyText text=new VertifyText();
			int randomInt=mRandom.nextInt(charLen-1)+1;
			char choose=chars[randomInt];
			text.setText(choose);
			codeResult+=choose;
			
			int offDegree=mRandom.nextInt(15);
			offDegree=mRandom.nextInt(2)==1?offDegree:-offDegree;
			text.setDegree(offDegree);
			
			int color=Color.argb(255, mRandom.nextInt(210)+20, 
						mRandom.nextInt(233)+10,mRandom.nextInt(210)+24);
			text.setColor(color);
			
			Point point=new Point((int)(i * charLenght  + 7),
					(int)(getHeight() / 2.0f + (-10)*(mRandom.nextInt(1)==0?-1:1)));
			text.setPoint(point);
			texts.add(text);
		}
		
		if(mOnVertifyCodeLinstener!=null&&codeResult!=""){
			mOnVertifyCodeLinstener.OnVertifyCodeChange(codeResult.trim());
		}
	}
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		// TODO Auto-generated method stub
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		int measureWidth=MeasureSpec.getSize(widthMeasureSpec);
		int widthMode=MeasureSpec.getMode(widthMeasureSpec);
		
		int measureHeight=MeasureSpec.getSize(heightMeasureSpec);
		int heightMode=MeasureSpec.getMode(heightMeasureSpec);
		
		setMeasuredDimension(measureWidth, measureHeight);
	}
	@Override
	protected void onDraw(Canvas canvas) {
		// TODO Auto-generated method stub
		super.onDraw(canvas);
		if(canDraw){
			initPointAndPath();
			createCode();
	        canDraw=false;
		}
		drawPoint(canvas);
        drawPath(canvas);
        drawText(canvas);
	}
	//画干扰点
	private void drawPoint(Canvas mCanvas){
		for(int i=0,len=points.size();i<len;i++){
			PointF pointF=points.get(i).getPointF();
		    pointPaint.setColor(points.get(i).getColor());;	
			mCanvas.drawPoint(pointF.x, pointF.y, pointPaint);
		}
	}
	//画干扰线
	private void drawPath(Canvas mCanvas){
		for(LinePath linePath:paths){
		    pathPaint.setColor(linePath.getColor());
			mCanvas.drawPath(linePath.getPath(), pathPaint);
		}
	}
	//画随机文本
	private void drawText(Canvas mCanvas){
		if(texts.size()>0){
			for(int index=0,len=texts.size();index<len;index++){
				VertifyText text=texts.get(index);
				textPaint.setColor(text.getColor());
				mCanvas.save();
				mCanvas.rotate(text.getDegree());
				mCanvas.drawText(String.valueOf(text.getText()), text.getPoint().x * 1.0f,
						text.getPoint().y * 1.0f, textPaint);
				mCanvas.restore();
			}
			
		}
	}
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		// TODO Auto-generated method stub
	    int id=event.getAction();
	    switch (id) {
		case MotionEvent.ACTION_DOWN:
			break;
		case MotionEvent.ACTION_UP:
			setReDraw();
			break;

		default:
			break;
		}
		return true;
	}
	public void setReDraw(){
		this.canDraw=true;
		invalidate();
	}
	public void setOnVertifyCodeLinstener(OnVertifyCodeLinstener l){
		this.mOnVertifyCodeLinstener=l;
	}
    class LinePath{
    	Path path;
    	int color;
		public Path getPath() {
			return path;
		}
		public void setPath(Path path) {
			this.path = path;
		}
		public int getColor() {
			return color;
		}
		public void setColor(int color) {
			this.color = color;
		}
    }
    class SmallPoint{
    	PointF pointF;
    	int color;
		public PointF getPointF() {
			return pointF;
		}
		public void setPointF(PointF pointF) {
			this.pointF = pointF;
		}
		public int getColor() {
			return color;
		}
		public void setColor(int color) {
			this.color = color;
		}
    	
    }
    class VertifyText{
    	char text;
    	Point point;
    	int color;
    	int degree;
		public char getText() {
			return text;
		}
		public void setText(char text) {
			this.text = text;
		}
		public Point getPoint() {
			return point;
		}
		public void setPoint(Point point) {
			this.point = point;
		}
		public int getColor() {
			return color;
		}
		public void setColor(int color) {
			this.color = color;
		}
		public int getDegree() {
			return degree;
		}
		public void setDegree(int degree) {
			this.degree = degree;
		}
    }
}
