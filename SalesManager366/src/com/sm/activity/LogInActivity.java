package com.sm.activity;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.amap.api.col.et;
import com.sm.view.DynamicView;
import com.sm.view.PersonImg;
import com.sm366.utils.HttpUtils;
import com.smlib.net.KDHttpRequest;
import com.smlib.net.KDHttpRequest.KDHttpRequestLinstener;
import com.smlib.utils.MD5;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class LogInActivity extends Activity implements OnClickListener,
        KDHttpRequestLinstener{

	private TextView tv_name,tv_change,tv_help;
	private ImageView iv_back;
	private Button bt_login;
	private HttpUtils hu;
	private EditText et_phone,et_psw;
	private PersonImg personImg;
	private DynamicView mDynamicView;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.login_activity_layout);
		initView();
		hu=new HttpUtils(this);
		hu.setLinstener(this);
	}
	
	private void initView(){
		 tv_name=(TextView)this.findViewById(R.id.login_tv_name);
    	 tv_name.setText("林玉锋");
    	    	 
    	 bt_login=(Button)this.findViewById(R.id.bt_login);
    	 bt_login.setOnClickListener(this);

    	 personImg=(PersonImg)this.findViewById(R.id.login_person_img);
    	 
    	 et_psw=(EditText)this.findViewById(R.id.et_psw);
    	 
    	 tv_help=(TextView)this.findViewById(R.id.tv_help);
    	 tv_help.setOnClickListener(this);
    	 
    	 tv_change=(TextView)this.findViewById(R.id.tv_change);
    	 tv_change.setOnClickListener(this);
    	 
    	 mDynamicView=(DynamicView)this.findViewById(R.id.dynameicview);
	}
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		if(hu!=null){
			hu.release();
			hu=null;
		}
	}
    private String vertifyPhone(){
    	String phoneStr=et_phone.getText().toString().trim();
    	Pattern pattern=Pattern.compile("^((13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$");
 		Matcher matcher=pattern.matcher(phoneStr);
 		if(!matcher.matches()){
 			Toast.makeText(this, "电话号码格式有误！", Toast.LENGTH_LONG).show();
 			return "";
 		}
    	return phoneStr;
    }
	@Override
	public void onClick(View view) {
		// TODO Auto-generated method stub
		switch (view.getId()) {
		case R.id.header_iv_left:
			finish();
			break;
		case R.id.bt_login:
			//String phone=vertifyPhone();
			String psw=et_psw.getText().toString().trim();
			MD5 md5=new MD5();
			psw= md5.getMD5ofStr(psw);
			
			Intent intent=new Intent(this,MainActivity.class);
			this.startActivity(intent);
			this.finish();
			
			break;
		case R.id.tv_change:
			Intent loginIntent=new Intent(this,LoginWidthPhoneActivity.class);
			this.startActivity(loginIntent);
			break;
		case R.id.tv_help:
			
			break;
		default:
			break;
		}
	}

	@Override
	public void onRequsetSuccess(KDHttpRequest request) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onRequsetFailed(KDHttpRequest request) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onRequsetError(KDHttpRequest request, Exception e) {
		// TODO Auto-generated method stub
		
	}

}
