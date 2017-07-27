package com.sm.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.sm.common.Const;
import com.sm.view.PersonImg;
import com.smlib.activity.ShellSMActivity;
import com.smlib.net.KDHttpRequest;
import com.smlib.net.KDHttpRequest.KDHttpRequestLinstener;

public class PersonInfoActivity extends ShellSMActivity implements KDHttpRequestLinstener,View.OnClickListener{

	private TextView tv_title,tv_title_right,tv_name;
	private ImageView iv_back;
	private PersonImg personImg;
	private LinearLayout mLinearLayout;
	private String[][] mData;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
	    setContentView(R.layout.personinfo_activity);
		init();
	}
	private void init(){
		initData();
	    initView();
	    
	}
	private void initData(){
		mData=new String[][]{
				{"性别","生日","名片"},
				{"公司","部门","职位"},
				{"手机","电话","邮箱"}
		};
	}
	private void initView(){
    	tv_title=(TextView)this.findViewById(R.id.header_tv_title);
    	tv_title.setText("个人资料");
    	
    	tv_title_right=(TextView)this.findViewById(R.id.tv_right);
    	tv_title_right.setText("编辑");
    	tv_title_right.setTextColor(getResources().getColor(R.color.white));
    	tv_title_right.setVisibility(View.VISIBLE);
    	
    	iv_back=(ImageView)this.findViewById(R.id.header_iv_left);
    	iv_back.setVisibility(View.VISIBLE);
    	iv_back.setOnClickListener(this);
    	
    	personImg=(PersonImg)this.findViewById(R.id.personinfo_img);
    	 
    	tv_name=(TextView)this.findViewById(R.id.tv_personinfo_name);
    	tv_name.setText("林玉锋");
    	
    	
    	mLinearLayout=(LinearLayout)this.findViewById(R.id.container);
    	
	}
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
	}
	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
	}
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
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
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		int id=v.getId();
		switch (id) {
		case R.id.header_iv_left:
			
			break;
		default:
			break;
		}
	}
}
