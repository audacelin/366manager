package com.sm.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;
//����ǩ��ͳ��
public class ExtendCheckActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
	}

}
