package com.sm.activity;

import java.util.HashMap;

import org.json.JSONObject;

import com.sm366.entity.ResponseModel;
import com.sm366.utils.HttpUtils;
import com.smlib.net.KDHttpRequest;
import com.smlib.net.KDHttpRequest.KDHttpRequestLinstener;
import com.smlib.utils.MD5;

import android.R.bool;
import android.R.string;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.View.OnFocusChangeListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class NewCompanyActivity extends Activity implements View.OnClickListener,
              TextWatcher,OnFocusChangeListener,KDHttpRequestLinstener{

	private TextView tv_title;
	private ImageView iv_back; 
	private Button bt_register;
	private EditText et_psw,et_company,et_register;
	private String phone;
	private int curFocusID=0;
	private int[] idArr=null;
	private HashMap<Integer, EditText> map=null;
	private HttpUtils hu;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.newcompany_activity_layout);
		initView();
		if(getIntent().hasExtra("Phone")){
			phone=getIntent().getStringExtra("Phone");
			Log.e("NewCompany", "phone is "+phone);
		}
		init();
	
	}
	private void initView(){
		 tv_title=(TextView)this.findViewById(R.id.header_tv_title);
    	 tv_title.setText("创建企业");
    	 
    	 iv_back=(ImageView)this.findViewById(R.id.header_iv_left);
    	 iv_back.setVisibility(View.VISIBLE);
    	 iv_back.setOnClickListener(this);
    	 
    	 bt_register=(Button)this.findViewById(R.id.bt_register);
    	 bt_register.setOnClickListener(this);
    	 
    	 et_company=(EditText)this.findViewById(R.id.et_companyname);
    	 et_company.addTextChangedListener(this);
    	 et_company.setOnFocusChangeListener(this);
    	 
    	 
    	 et_psw=(EditText)this.findViewById(R.id.et_initial_psw);
    	 et_psw.setInputType(InputType.TYPE_CLASS_TEXT|InputType.TYPE_NUMBER_VARIATION_PASSWORD);
    	 et_psw.addTextChangedListener(this);
    	 et_company.setOnFocusChangeListener(this);
    	 
    	 et_register=(EditText)this.findViewById(R.id.et_register);
    	 et_register.addTextChangedListener(this);
    	 et_register.setOnFocusChangeListener(this);
	}
	private void init(){
		
		idArr=new int[]{
				R.id.et_companyname,R.id.et_initial_psw,
				R.id.et_register
		};
		map=new HashMap<Integer,EditText>();
		map.put(R.id.et_companyname, et_company);
		map.put(R.id.et_initial_psw, et_psw);
		map.put(R.id.et_register, et_register);
		
		hu=new HttpUtils(this);
		hu.setLinstener(this);
	}
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		int id=v.getId();
		switch (id) {
		case R.id.header_iv_left:
			finish();
			break;
		case R.id.bt_register:
			if(hu!=null){
				MD5 md5=new MD5();
				hu.Register(getValueByObj(et_company), getValueByObj(et_register), phone,
						md5.getMD5ofStr(getValueByObj(et_psw)));
			}
				
			break;
		default:
			break;
		}
	}
	
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		if(hu!=null)
			hu.release();
	}
	
	@Override
	public void afterTextChanged(Editable editable) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void beforeTextChanged(CharSequence s, int start, int end,
			int arg3) {
		// TODO Auto-generated method stub
		
	}
	private String getValueByObj(EditText view){
		
		return view.getText().toString().trim();
	}
	private boolean isEnable(CharSequence text){
		boolean flag=true;
		if(text.length()<=0)
			return false;
		for (Integer key : map.keySet()) {
			Log.e("KeySet", "key is "+key+" and vlaue is "+map.get(key));
			if(key!=curFocusID){
				EditText etObj=map.get(key);
				String value=getValueByObj(etObj);
				if(value.equals("")||value.length()<=0){
					flag=false;
					break;
				}
			}
		}
		return flag;
	}
	private void setEnable(CharSequence text){
		
		if(isEnable(text)){
			bt_register.setEnabled(true);
			bt_register.setBackgroundResource(R.drawable.btn_blue);
			bt_register.setTextColor(getResources().getColor(R.color.white));
		}else{
			bt_register.setEnabled(false);
			bt_register.setBackgroundResource(R.drawable.btn_gray);
			bt_register.setTextColor(getResources().getColor(R.color.gray));
		}
	}
	@Override
	public void onTextChanged(CharSequence s, int start, int end, int count) {
		// TODO Auto-generated method stub
		  setEnable(s);
	}
	@Override
	public void onFocusChange(View view, boolean flag) {
		// TODO Auto-generated method stub
		curFocusID=view.getId();
	}
	@Override
	public void onRequsetSuccess(KDHttpRequest request) {
		// TODO Auto-generated method stub
		Log.e("NewCompanyActivity", "responseData is "+request.getResponseString());
		try{
			ResponseModel model=ResponseModel.ValueOf(new JSONObject(request.getResponseString()));
			if(model==null){
				Toast.makeText(this, "服务器端返回JSON数据错误", 1000).show();
				return;
			}
			if(request.getFlag()==HttpUtils.REGISTER_FLAG&&model.getResult()==1){
				if(model.getTotalCount()>0){
					//跳转主界面
					Intent intent=new Intent(this,MainActivity.class);
					startActivity(intent);
					finish();
				}else{
					Toast.makeText(this, model.getAlertMessage(), 1000).show();
				}
			}
		}catch(Exception ex){
			Toast.makeText(this, ex.getMessage(), 1000).show();
		}
	}
	@Override
	public void onRequsetFailed(KDHttpRequest request) {
		// TODO Auto-generated method stub
		Log.e("Failed",""+request.getResponseString());
	}
	@Override
	public void onRequsetError(KDHttpRequest request, Exception e) {
		// TODO Auto-generated method stub
		Log.e("error","error"+e.getMessage());
	}
	
}
