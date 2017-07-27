package com.sm.Fragment;

import java.util.ArrayList;
import java.util.List;

import com.sm.activity.R;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class NewsFragment extends Fragment {
    private String title="";
    public final static String TITLE="title";
    private final static String TAG="FirstFragment";
    private ListView mListView;
    private List<String> mList;
    private LayoutInflater layoutInflater;
    private MyAdapter myAdapter;
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
	}

	@Override
	public void onAttach(Activity activity) {
		// TODO Auto-generated method stub
		super.onAttach(activity);
		Log.e(TAG,"onAttach");
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		Log.e(TAG,"firstFragment-->onCreate");
	}
    
	@Override
	public void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		Log.e(TAG,"onStart");
	}

	@Override
	public void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
		Log.e(TAG,"onStop");
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		Log.e(TAG,"onCreateView");
		layoutInflater=inflater;
		// TODO Auto-generated method stub
		View view=inflater.inflate(R.layout.news_fragment_layout, container,false);
		initData();
		initView(view);
		
		if(getArguments()!=null){
			title=getArguments().getString(TITLE);
		}
        
		return view;
	}
	private void initData(){
		mList=new ArrayList<String>();
		for(int i=0,len=20;i<len;i++){
			mList.add("message_"+i);
		}
	}
	private void initView(View view){
		mListView=(ListView) view.findViewById(R.id.listView);
		myAdapter=new MyAdapter();
		mListView.setAdapter(myAdapter);
		
	}
	

	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		Log.e(TAG,"onDestroy");
	}

	@Override
	public void onDestroyView() {
		// TODO Auto-generated method stub
		super.onDestroyView();
		Log.e(TAG,"onDestroyView");
	}
	
	public class MyAdapter extends BaseAdapter{

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return mList.size();
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return mList.get(position);
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			// TODO Auto-generated method stub
			ViewHolder vh;
			if(convertView==null){
				vh=new ViewHolder();
				convertView=layoutInflater.inflate(R.layout.listview_for_news, parent, false);
				vh.tvText=(TextView) convertView.findViewById(R.id.tv_test);
				convertView.setTag(vh);
			}else{
				vh=(ViewHolder) convertView.getTag();
			}
			vh.tvText.setText(mList.get(position));
			return convertView;
		}
		
	}
	class ViewHolder{
		TextView tvText;
	}
     
}
