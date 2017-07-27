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
	//��ʼ���ߵ¶�λ����
	private void initLocation(){
		option=getOption();
		aMapLocationClient=new AMapLocationClient(this);
		aMapLocationClient.setLocationOption(option);
		aMapLocationClient.setLocationListener(this);
		
	}
	//��ʼ��λ ����ɸ��Ǵ˷���
	public void startLocation(){
		aMapLocationClient.startLocation();
	}
	//ֹͣ��λ������ɸ��Ǵ˷���
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
	 * ��ȡ��λ��������
	 * 
	 */
	private AMapLocationClientOption getOption(){
		AMapLocationClientOption mOption=new AMapLocationClientOption();
		mOption.setLocationMode(AMapLocationMode.Hight_Accuracy);//��ѡ�����ö�λģʽ����ѡ��ģʽ�и߾��ȡ����豸�������硣Ĭ��Ϊ�߾���ģʽ
		mOption.setGpsFirst(true);//��ѡ�������Ƿ�gps���ȣ�ֻ�ڸ߾���ģʽ����Ч��Ĭ�Ϲر�
		mOption.setHttpTimeOut(30000);//��ѡ��������������ʱʱ�䡣Ĭ��Ϊ30�롣�ڽ��豸ģʽ����Ч
		mOption.setInterval(2000);//��ѡ�����ö�λ�����Ĭ��Ϊ2��
		mOption.setNeedAddress(true);//��ѡ�������Ƿ񷵻�������ַ��Ϣ��Ĭ����ture
		mOption.setOnceLocation(false);//��ѡ�������Ƿ񵥴ζ�λ��Ĭ����false
		mOption.setOnceLocationLatest(false);//��ѡ�������Ƿ�ȴ�wifiˢ�£�Ĭ��Ϊfalse.�������Ϊtrue,���Զ���Ϊ���ζ�λ��������λʱ��Ҫʹ��
		AMapLocationClientOption.setLocationProtocol(AMapLocationProtocol.HTTP);//��ѡ�� �������������Э�顣��ѡHTTP����HTTPS��Ĭ��ΪHTTP
		return mOption;
	}
	//���า�Ǵ˷�����ö�λ���    ��λ�ɹ�
	public void onLocationSuccess(LatLonPoint point,String address){
		Log.e(TAG,"lon is "+point.getLongitude()+" and lan is "+point.getLatitude()+" and address is "+address);
		Toast.makeText(this, "lan is "+point.getLatitude(), 1000).show();
		stopLocation();
	}
	//��λʧ�� 
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
