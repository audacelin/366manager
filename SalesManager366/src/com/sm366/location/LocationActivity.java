package com.sm366.location;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationClientOption.AMapLocationMode;
import com.amap.api.location.AMapLocationClientOption.AMapLocationProtocol;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.geocoder.GeocodeSearch;
import com.smlib.activity.ShellSMActivity;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

public class LocationActivity extends ShellSMActivity implements AMapLocationListener{
    private AMapLocationClient aMapLocationClient;
    private GeocodeSearch geocodeSearch;
    private AMapLocationClientOption option;
    public static final String TAG="LOCATIONACTIVITY";
    
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		initLocation();
	}
	//初始化高德定位对象
	private void initLocation(){
		option=getOption();
		aMapLocationClient=new AMapLocationClient(this);
		aMapLocationClient.setLocationOption(option);
		aMapLocationClient.setLocationListener(this);
		
	}
	//开始定位 子类可覆盖此方法
	public void startLocation(){
		aMapLocationClient.startLocation();
	}
	//停止定位，子类可覆盖此方法
	public void stopLocation(){
		aMapLocationClient.stopLocation();
	}
	
	public void destoryLocation() {
		if(aMapLocationClient!=null){
			aMapLocationClient.onDestroy();
			aMapLocationClient=null;
			option=null;
		}
	}
	/*
	 * 获取定位的设置项
	 * 
	 */
	private AMapLocationClientOption getOption(){
		AMapLocationClientOption mOption=new AMapLocationClientOption();
		mOption.setLocationMode(AMapLocationMode.Hight_Accuracy);//可选，设置定位模式，可选的模式有高精度、仅设备、仅网络。默认为高精度模式
		mOption.setGpsFirst(true);//可选，设置是否gps优先，只在高精度模式下有效。默认关闭
		mOption.setHttpTimeOut(30000);//可选，设置网络请求超时时间。默认为30秒。在仅设备模式下无效
		mOption.setInterval(2000);//可选，设置定位间隔。默认为2秒
		mOption.setNeedAddress(true);//可选，设置是否返回逆地理地址信息。默认是ture
		mOption.setOnceLocation(false);//可选，设置是否单次定位。默认是false
		mOption.setOnceLocationLatest(false);//可选，设置是否等待wifi刷新，默认为false.如果设置为true,会自动变为单次定位，持续定位时不要使用
		AMapLocationClientOption.setLocationProtocol(AMapLocationProtocol.HTTP);//可选， 设置网络请求的协议。可选HTTP或者HTTPS。默认为HTTP
		return mOption;
	}
	//子类覆盖此方法获得定位结果    定位成功
	public void onLocationSuccess(LatLonPoint point,String address){
		Log.e(TAG,"lon is "+point.getLongitude()+" and lan is "+point.getLatitude()+" and address is "+address);
		Toast.makeText(this, "lan is "+point.getLatitude(), 1000).show();
		stopLocation();
	}
	//定位失败 
	public void onLocationFail(int errorCode,String errorStr){
		Log.e(TAG, "ERROR_CODE is "+errorCode+" errorStr is "+errorStr);
	}

	@Override
	public void onLocationChanged(AMapLocation aMapLocation) {
		// TODO Auto-generated method stub
		if(aMapLocation!=null&&aMapLocation.getErrorCode()==0){
			double lan=aMapLocation.getLatitude();
			double lon=aMapLocation.getLongitude();
			String address=aMapLocation.getAddress();
			LatLonPoint point=new LatLonPoint(lan, lon);
			onLocationSuccess(point,address);
		}else{
			onLocationFail(aMapLocation.getErrorCode(),aMapLocation.getErrorInfo());
		}
	}
}
