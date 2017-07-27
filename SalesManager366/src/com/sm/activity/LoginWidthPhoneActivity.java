package com.sm.activity;

import android.app.Activity;
import android.content.Intent;
import android.opengl.Visibility;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.LinearLayout.LayoutParams;
import android.widget.RelativeLayout;
//ÇÐ»»ÕËºÅµÇÂ¼
public class LoginWidthPhoneActivity extends Activity implements View.OnClickListener{

	private LinearLayout registerLayout,userPhotoLayout;
	private RelativeLayout accountLayout;
	private TextView tv_register;
	private Button bt_login;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login_activity_layout);
		initView();
	}
	
	private void initView(){
		registerLayout=(LinearLayout)this.findViewById(R.id.ll_register);
		registerLayout.setVisibility(View.VISIBLE);
		
		userPhotoLayout=(LinearLayout)this.findViewById(R.id.ll_user);
		
		accountLayout=(RelativeLayout)this.findViewById(R.id.rl_account);
		accountLayout.setVisibility(View.VISIBLE);
		
		tv_register=(TextView)this.findViewById(R.id.login_register);
		tv_register.setOnClickListener(this);
		
		bt_login=(Button)this.findViewById(R.id.bt_login);
		bt_login.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		int id=v.getId();
		switch (id) {
		//×¢²á
		case R.id.login_register:
			Intent registerIntent=new Intent(this,RegisterActivity.class);
			registerIntent.putExtra("From", "Login");//Ìø×ªÀ´Ô´µÄ±êÊ¶·û
			this.startActivity(registerIntent);
			break;
		case R.id.bt_login:
			
			break;
		default:
			break;
		}
	}
}
