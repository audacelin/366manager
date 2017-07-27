package com.sm.activity;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.json.JSONObject;

import cn.smssdk.EventHandler;
import cn.smssdk.OnSendMessageHandler;
import cn.smssdk.SMSSDK;

import com.sm.interfaces.OnVertifyCodeLinstener;
import com.sm.view.VertifyCode;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.widget.SimpleCursorAdapter.ViewBinder;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
//注册
public class RegisterActivity extends Activity implements OnClickListener,
        TextWatcher,OnVertifyCodeLinstener,OnFocusChangeListener{
	private VertifyCode vertifyCode;
	private EditText et_phone,et_vertifyCode,et_smsCode;
	private Button bt_next,bt_sms_code;
	private TextView tv_title;
	private ImageView iv_back;
	private int curId;
	
	private  String appKey="17a69f77c0ad0",
			 appSecret="8f630a03c85b82fdfdff65d0412da1cc";
	private EventHandler eventHandler;
	private Handler mHandler; 
	private String vCode="";
     @Override
    protected void onCreate(Bundle savedInstanceState) {
    	// TODO Auto-generated method stub
    	super.onCreate(savedInstanceState);
    	requestWindowFeature(Window.FEATURE_NO_TITLE);
    	setContentView(R.layout.register_activity);
    	initView();
    	init();
    }
     @Override
     protected void onDestroy() {
     	// TODO Auto-generated method stub
     	super.onDestroy();
     	SMSSDK.unregisterEventHandler(eventHandler);
     }
     private void initView(){
    	 vertifyCode=(VertifyCode) this.findViewById(R.id.vertify_code);
    	 vertifyCode.setEnabled(false);
    	 vertifyCode.setReDraw();
    	 vertifyCode.setOnVertifyCodeLinstener(this);
    	 
    	 et_phone=(EditText)this.findViewById(R.id.et_phone);
    	 et_phone.addTextChangedListener(this);
    	 et_phone.setOnFocusChangeListener(this);
    	 
    	 et_vertifyCode=(EditText)this.findViewById(R.id.et_vertifyCode);
    	 et_vertifyCode.setOnFocusChangeListener(this);
    	 et_vertifyCode.addTextChangedListener(this);
    	 
    	 et_smsCode=(EditText)this.findViewById(R.id.et_sms_code);
    	 et_smsCode.setOnFocusChangeListener(this);
    	 et_smsCode.addTextChangedListener(this);
    	 
    	 bt_next=(Button)this.findViewById(R.id.bt_next);
    	 bt_next.setEnabled(false);
    	 bt_next.setOnClickListener(this);
    	 bt_sms_code=(Button)this.findViewById(R.id.bt_sms_code);
    	 bt_sms_code.setEnabled(false);
    	 bt_sms_code.setOnClickListener(this);
    	 
    	 tv_title=(TextView)this.findViewById(R.id.header_tv_title);
    	 tv_title.setText("注册");
    	 
    	 iv_back=(ImageView)this.findViewById(R.id.header_iv_left);
    	 iv_back.setVisibility(View.VISIBLE);
    	 iv_back.setOnClickListener(this);
    	 
     }
     private void init(){
    	 //初始化短信发送SDK
    	 SMSSDK.initSDK(this, appKey, appSecret,true);
    	 mHandler=new Handler(){
    		 @Override
    		public void handleMessage(Message msg) {
    			// TODO Auto-generated method stub
    			super.handleMessage(msg);
    			int result=msg.arg1;
    			int event=msg.arg2;
    			Object data=msg.obj;
    			if(event==SMSSDK.EVENT_GET_VERIFICATION_CODE
    					&&result==SMSSDK.RESULT_COMPLETE){
    				Log.e("Handler","msg");
    			}
    			
    		}
    	 };
    	 eventHandler=new EventHandler(){
    		 @Override
    		public void afterEvent(int event, int result, Object data) {
    			// TODO Auto-generated method stub
    			super.afterEvent(event, result, data);
    			Log.e("EventHandler", "result is "+result);
    			if(result==SMSSDK.RESULT_COMPLETE){
    				Log.e("eventHandler", "data is "+data.toString());
    			
    				if(event==SMSSDK.EVENT_GET_VERIFICATION_CODE){
    					Message msg=new Message();
        				msg.arg1=result;
        				msg.arg2=event;
        				mHandler.sendMessageDelayed(msg, 1000);
    				}else if(event==SMSSDK.EVENT_SUBMIT_VERIFICATION_CODE){
    					Log.e("Submit", "短信验证成功！");
    				}
    			}else if(result==SMSSDK.RESULT_ERROR){
    				
    				try {
    				     Throwable throwable = (Throwable) data;
    				     throwable.printStackTrace();
    				     JSONObject object = new JSONObject(throwable.getMessage());
    				     String des = object.optString("detail");//错误描述
    				     int status = object.optInt("status");//错误代码
    				     
    				     Log.e("Error","result is "+data );
    				} catch (Exception e) {
    				     //do something                            
    				}
    			}
    		}
    	 };
    	 SMSSDK.registerEventHandler(eventHandler);
    	 
     }
     private boolean isValid(){
     	String phoneNum=et_phone.getText().toString().trim();
     	Pattern pattern=Pattern.compile("^((13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$");
 		Matcher matcher=pattern.matcher(phoneNum);
 		if(!matcher.matches()){
 			Toast.makeText(this, "电话号码格式有误！", Toast.LENGTH_LONG).show();
 			return false;
 		}
 		
 		String vertifyCode=et_vertifyCode.getText().toString().trim();
 		if(vertifyCode.length()<=0){
 			Toast.makeText(this, "请输入验证码！", Toast.LENGTH_LONG).show();
 			return false;
 		}else{
// 			if(!vertifyCode.equalsIgnoreCase(vCode)){
// 				Toast.makeText(this, "验证码错误!", Toast.LENGTH_LONG).show();
// 				return false;
// 			}
 		}
 		
 		String sms_code=et_smsCode.getText().toString().trim();
 		if(sms_code.length()<=0){
 			Toast.makeText(this, "请输入短信验证码！", Toast.LENGTH_LONG).show();
 			return false;
 		}else{
 			//SMSSDK.submitVerificationCode("86", phoneNum, sms_code);
 		}
 		return true;
     } 
     
	@Override
	public void onClick(View view) {
		// TODO Auto-generated method stub
		int id=view.getId();
		switch (id) {
		case R.id.bt_next:
			Log.e("View", "bt_next");
			if(isValid()){
				Intent intent=new Intent(this,NewCompanyActivity.class);
			    intent.putExtra("Phone", et_phone.getText().toString().trim());
				this.startActivity(intent);
			}
			break;
		case R.id.bt_sms_code:
			Log.e("View", "get sms code");
			SMSSDK.getVerificationCode("86", et_phone.getText().toString(),new OnSendMessageHandler() {
				@Override
				public boolean onSendMessage(String arg0, String arg1) {
					// TODO Auto-generated method stub
					return false;
				}
			});
			break;
		case R.id.header_iv_left:
			finish();
			break;
		default:
			break;
		}
	}
	@Override
	public void OnVertifyCodeChange(String code) {
		// TODO Auto-generated method stub
		vCode=code;
		Log.e("OnVertifyCode", "sms_code is "+vCode);
	}
	@Override
	public void afterTextChanged(Editable arg0) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
			int arg3) {
		// TODO Auto-generated method stub
		
	}
	
	private void setNxtEnable(boolean enable){
		if(enable){
			bt_next.setEnabled(true);
			bt_next.setBackgroundResource(R.drawable.btn_blue);
			bt_next.setTextColor(Color.WHITE);
		}else{
			bt_next.setEnabled(false);
			bt_next.setBackgroundResource(R.drawable.btn_gray);
			bt_next.setTextColor(getResources().getColor(R.color.gray));
		}
	}
	
	@Override
	public void onTextChanged(CharSequence s, int start, int before, int count) {
		// TODO Auto-generated method stub
		
		if(curId==et_phone.getId()&&s.length()>0){
			Log.e("OnTextChange","et_phone");
			if(s.length()==11){
				bt_sms_code.setBackgroundResource(R.drawable.btn_blue);
				bt_sms_code.setTextColor(Color.WHITE);
				bt_sms_code.setEnabled(true);
			}
			if(et_vertifyCode.getText().toString().trim().length()>0&&
					et_smsCode.getText().toString().trim().length()>0){
				setNxtEnable(true);
			}
		
		}else if(curId==et_smsCode.getId()&&s.length()>0){
			Log.e("OnTextChange","et_smsCode");
			if(et_phone.getText().toString().trim().length()>0&&
					et_vertifyCode.getText().toString().trim().length()>0){
				setNxtEnable(true);
			}
		}else if(curId==et_vertifyCode.getId()&&s.length()>0){
			Log.e("OnTextChange","et_vertifyCode");
			if(et_phone.getText().toString().trim().length()>0&&
					et_smsCode.getText().toString().trim().length()>0){
				setNxtEnable(true);
			}
		}
	}
	@Override
	public void onFocusChange(View view, boolean hasFocus) {
		// TODO Auto-generated method stub
		int id=view.getId();
		Log.e("onFocusChange", "curFocusId is "+id);
		curId=id;
	}
}
