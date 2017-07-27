package com.sm366.entity;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.json.JSONObject;

import android.R.string;
import android.location.Location;

import com.amap.api.location.AMapLocation;

public class LocationModel implements Serializable{

	private String addres;
	private double lan;//¾­¶È
	private double lon;//Î³¶È
	private String signInTime;
	private static SimpleDateFormat sdf=null;
	private static final String DATAFORMAT="yyyy-MM-dd HH:mm:ss";
	public LocationModel(String address,double lan,double lon,String signInTime){
		this.addres=address;
		this.lan=lan;
		this.lon=lon;
		this.signInTime=signInTime;
	}
	public static LocationModel valueOf(AMapLocation location){
        String address=location.getAddress();
		double lan=location.getLatitude();
		double lon=location.getLongitude();
		String signInTime=formatUTC(System.currentTimeMillis(), DATAFORMAT);
		return new LocationModel(address, lan, lon, signInTime);
	}
	public static LocationModel valueOf(JSONObject object){
		try{
			double lan= object.getDouble("Lantitude");
			double lon=object.getDouble("Lontitude");
			String address=object.getString("Address");
			String signInTime=object.getString("LocDate");
			return new LocationModel(address, lan, lon, signInTime);
		}catch(Exception ex){
			ex.printStackTrace();
			return null;
		}
	}
	
	public synchronized static JSONObject toJsonObject(AMapLocation location){
		JSONObject jo=new JSONObject();
		try{
			jo.put("Lantitude", location.getLatitude());
			jo.put("Lontitude", location.getLongitude());
			jo.put("Address", location.getAddress());
			jo.put("LocDate",formatUTC(System.currentTimeMillis(),
					DATAFORMAT));
			return jo;
		}catch(Exception ex){
			ex.printStackTrace();
		}
		return null;
	}
	
	private synchronized static String formatUTC(long l,String strPattern){
		Pattern pattern=Pattern.compile("^//s*$");
		Matcher matcher=pattern.matcher(strPattern);
		if(matcher.find()){
			strPattern="yyyy-MM-dd HH:mm:ss";
		}
		if(sdf==null){
			try{
			   sdf=new SimpleDateFormat(strPattern, Locale.CHINA);	
			}catch(Exception ex){
				ex.printStackTrace();
			}
		}
		return sdf==null?"NULL":sdf.format(l);
	}
	public String getAddres() {
		return addres;
	}
	public double getLan() {
		return lan;
	}
	public double getLon() {
		return lon;
	}
	public String getSignInTime() {
		return signInTime;
	}
	public static SimpleDateFormat getSdf() {
		return sdf;
	}
	
	
	
	
	
}
