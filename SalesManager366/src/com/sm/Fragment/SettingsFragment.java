package com.sm.Fragment;

import com.sm.activity.MainActivity;
import com.sm.activity.PersonInfoActivity;
import com.sm.activity.R;
import com.sm.view.PersonImg;
import com.sm366.adapter.GroupBaseAdapter;
import com.sm366.entity.MenuItem;

import android.R.integer;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

public class SettingsFragment extends Fragment implements View.OnClickListener{
    private String title="";
    public final static String TITLE="title";
    private PersonImg personImg;
    private TextView tv_name,tv_custName;
    private final static String TAG="SettingFragment";
    private MenuItem[][] menuItems={
    		{
    			new MenuItem(1, "系统设置", "", "test1", "001"),
    			new MenuItem(2, "管理", "", "test1", "002"),
    			new MenuItem(3, "修改密码", "", "", "003")
    		},	
    		{
    			new MenuItem(4, "版本检测", "", "test2", "002"),	
    			new MenuItem(5, "意见反馈", "", "test3", "003")
    		}	
    };
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		if(getArguments()!=null){
			title=getArguments().getString(TITLE);
		}
		View view=(View) inflater.inflate(R.layout.setting_fragment_layout,null);
		initView(view);
		return view;
	}
	private void initView(View view){
		View personInfoView=view.findViewById(R.id.llayout_info);
		personInfoView.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent=new Intent(getActivity(),PersonInfoActivity.class);
				startActivity(intent);
			}
		});
		personImg=(PersonImg)view.findViewById(R.id.iv_person);
        tv_custName=(TextView)view.findViewById(R.id.tv_custname);
        tv_name=(TextView)view.findViewById(R.id.tv_name);
        tv_name.setText("林玉锋");
        tv_custName.setText("金蝶软件有限公司");
        
	}

	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
	}

	@Override
	public void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
	}

	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
	}

	@Override
	public void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
	}

	@Override
	public void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
	}
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		
	}
	
}
