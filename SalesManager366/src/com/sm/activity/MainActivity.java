package com.sm.activity;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;



import com.sm.Fragment.AppsFragment;
import com.sm.Fragment.NewsFragment;
import com.sm.Fragment.SettingsFragment;
import com.sm.Fragment.WorksFragment;
import com.sm.activity.R;
import com.sm.activity.R.string;
import com.sm.common.Const;
import com.sm.plusBar.PlusActivity;
import com.sm.view.ChangeIconText;
import com.sm.view.MyScrollView;
import com.sm366.entity.AppsModel;
import com.sm366.entity.Json2Obj;
import com.sm366.entity.ResponseModel;
import com.sm366.entity.ResponseModel.JsonType;
import com.sm366.utils.HttpUtils;
import com.sm366.utils.SystemBarTintManager;
import com.smlib.activity.ShellSMActivity;
import com.smlib.net.KDHttpRequest;
import com.smlib.net.KDHttpRequest.KDHttpRequestLinstener;




/*import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;*/
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.TextureView;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.os.Build;

public class MainActivity extends ShellSMActivity implements View.OnClickListener,KDHttpRequestLinstener{
    private List<Fragment> mList=new ArrayList<Fragment>();
    private MyScrollView myScrollView; 
    private MyFragmentPagerAdapter myFragmentPagerAdapter;
    protected List<ChangeIconText> mView;
    private ChangeIconText vCIT1,vCIT2,vCIT3,vCIT4;
    private TextView tv_title;
    //
    private final static int VIEWPAGER_CUR_ONE=0,VIEWPAGER_CUR_TWO=1,VIEWPAGER_CUR_THREE=2,VIEWPAGER_CUR_FOUR=3;
    private HttpUtils hUtils;
    private ImageView header_iv_add;
    
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState); 
		setContentView(R.layout.activity_main);
        hUtils=new HttpUtils(this);
        hUtils.setLinstener(this);
        hUtils.GetMenuList();
	    initFragments();
	    initView();
	    
	    
	    myScrollView.setCurrentItem(VIEWPAGER_CUR_ONE,false);
    	vCIT1.setTextAlpha(1.0f);
	} 
    private void initFragments(){
    	NewsFragment firstFragment=new NewsFragment();
    	Bundle buddle=new Bundle();
    	buddle.putString(firstFragment.TITLE,"FirstFragment");
    	firstFragment.setArguments(buddle);
    	mList.add(firstFragment);
    	AppsFragment secondFragment=new AppsFragment();
    	Bundle buddle1=new Bundle();
    	buddle1.putString(firstFragment.TITLE,"SecondFragment");
    	secondFragment.setArguments(buddle1);
    	mList.add(secondFragment);
      	WorksFragment threeFragment=new WorksFragment();
    	Bundle buddle3=new Bundle();
    	buddle3.putString(firstFragment.TITLE,"ThreeFragment");
    	threeFragment.setArguments(buddle3);
    	mList.add(threeFragment);
      	SettingsFragment fourFragment=new SettingsFragment();
    	Bundle buddle4=new Bundle();
    	buddle4.putString(firstFragment.TITLE,"FourFragment");
    	fourFragment.setArguments(buddle4);
    	mList.add(fourFragment);
    	
    	myFragmentPagerAdapter=new MyFragmentPagerAdapter(this.getSupportFragmentManager());
    
    }
    private void initView(){
    	myScrollView=(MyScrollView) this.findViewById(R.id.myScrollView);
    	myScrollView.setAdapter(myFragmentPagerAdapter);
    	tv_title=(TextView)this.findViewById(R.id.header_tv_title);
    	tv_title.setText(Const.HEADER_TITLE_NEWS);
    	header_iv_add = (ImageView) this.findViewById(R.id.header_iv_add);
    	mView=new ArrayList<ChangeIconText>();
    	header_iv_add.setVisibility(View.VISIBLE);
    	vCIT1=(ChangeIconText) this.findViewById(R.id.ChangeIconText_One);
    	vCIT2=(ChangeIconText) this.findViewById(R.id.ChangeIconText_Two);
    	vCIT3=(ChangeIconText) this.findViewById(R.id.ChangeIconText_Three);
    	vCIT4=(ChangeIconText) this.findViewById(R.id.ChangeIconText_Four);
    	
    	mView.add(vCIT1);
    	mView.add(vCIT2);
    	mView.add(vCIT3);
    	mView.add(vCIT4);
    	
    	vCIT1.setOnClickListener(this);
    	vCIT2.setOnClickListener(this);
    	vCIT3.setOnClickListener(this);
    	vCIT4.setOnClickListener(this);
    	header_iv_add.setOnClickListener(this);
    }
    private final static String INSTANCE_STATUS="";
    
    //处理Activity销毁后的状态
    
    @Override
    protected void onSaveInstanceState(Bundle outState) {
    	// TODO Auto-generated method stub
    	super.onSaveInstanceState(outState);
    }
    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
    	// TODO Auto-generated method stub
    	super.onRestoreInstanceState(savedInstanceState);
    }
    @Override
    protected void onDestroy() {
    	// TODO Auto-generated method stub
    	super.onDestroy();
    	if(hUtils!=null){
    		hUtils.release();
    		hUtils=null;
    	}
    }
 
    private class MyFragmentPagerAdapter extends FragmentPagerAdapter{

		public MyFragmentPagerAdapter(FragmentManager fm) {
			super(fm);
			// TODO Auto-generated constructor stub
		}

		@Override
		public Fragment getItem(int position) {
			// TODO Auto-generated method stub
			return mList.get(position);
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return position;
		}

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return mList.size();
		}
		
    	
    }

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		int id=v.getId();
		resetIconText();
		switch(id){
		case R.id.ChangeIconText_One:
			vCIT1.setTextAlpha(1.0f);
			changeTitle(Const.HEADER_TITLE_NEWS);
			myScrollView.setCurrentItem(VIEWPAGER_CUR_ONE,false);
			break;
		case R.id.ChangeIconText_Two:
			vCIT2.setTextAlpha(1.0f);
			changeTitle(Const.HEADER_TITLE_APP);
			myScrollView.setCurrentItem(VIEWPAGER_CUR_TWO, false);
			break;
		case R.id.ChangeIconText_Three:
			vCIT3.setTextAlpha(1.0f);
			changeTitle(Const.HEADER_TITLE_WORK);
			myScrollView.setCurrentItem(VIEWPAGER_CUR_THREE, false);
			break;
		case R.id.ChangeIconText_Four:
			vCIT4.setTextAlpha(1.0f);
			changeTitle(Const.HEADER_TITLE_ME);
			myScrollView.setCurrentItem(VIEWPAGER_CUR_FOUR, false);
			break;
		case R.id.header_iv_add:
			Intent intent = new Intent(MainActivity.this,PlusActivity.class);
			startActivity(intent);
			break;
		}
	}
	//改变
	private void changeTitle(String title){
		tv_title.setText(title);
	}
	/*
	 * reset view of bottom
	 */
	private void resetIconText(){
		for(int i=0,len=mView.size();i<len;i++){
			ChangeIconText view=mView.get(i);
			view.setTextAlpha(0);
			
		}
	}
	private final static String TAG="MainActivity";
	@Override
	public void onRequsetSuccess(KDHttpRequest request) {
		// TODO Auto-generated method stub
		Log.e(TAG,"Success"+request.getResponseString());
		try{
			ResponseModel model=ResponseModel.ValueOf(new JSONObject(request.getResponseString()),JsonType.MULTI, 
					AppsModel.class);
			if(model.getResult()==1){
				ArrayList<Json2Obj> list=model.getEntities();
				Log.e("MainActivity", "length is "+list.size());
			}else{
				Toast.makeText(this, model.getAlertMessage(), 1000).show();
			}
		}catch(Exception ex){
			
		}
	}
	@Override
	public void onRequsetFailed(KDHttpRequest request) {
		// TODO Auto-generated method stub
		Log.e(TAG,"Failed");
	}
	@Override
	public void onRequsetError(KDHttpRequest request, Exception e) {
		// TODO Auto-generated method stub
		Log.e(TAG,"Error");
	}
	

}
