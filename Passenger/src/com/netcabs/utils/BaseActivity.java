package com.netcabs.utils;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.Log;

import com.netcabs.passenger.LoginWithPinAuthActivity;

public class BaseActivity extends Activity {

	protected static final String TAG = BaseActivity.class.getName();

	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
	}

	public static boolean isAppWentToBg = false;
	public static boolean isWindowFocused = false;
	public static boolean isMenuOpened = false;
	public static boolean isBackPressed = false;
	public static boolean isLock = false;
	public static boolean authLock = false;
	@Override
	protected void onStart() {
		Log.d(TAG, "onStart isAppWentToBg " + isAppWentToBg);
		applicationWillEnterForeground();
		super.onStart();
	}

	private void applicationWillEnterForeground() {
		if (isAppWentToBg) {
			isAppWentToBg = false;
			
			if (authLock) {
				authLock = false;
				return;
			}
			
			if (!isLock) {
				//isLock = false;
				authLock = false;
				Intent intent = new Intent(BaseActivity.this, LoginWithPinAuthActivity.class);
				//intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
				//startActivity(intent);
				
			} else {
				isLock = false;
				
			}
			
//			Toast.makeText(getApplicationContext(), "App is in foreground",Toast.LENGTH_SHORT).show();
		}
	}

	@Override
	protected void onStop() {
		super.onStop();
		Log.d(TAG, "onStop ");
		applicationdidenterbackground();
	}

	public void applicationdidenterbackground() {
		if (!isWindowFocused) {
			isAppWentToBg = true;
			//startActivity(new Intent(BaseActivity.this, UserEnterPinActivity.class).putExtra("is_background", true));
			//Toast.makeText(getApplicationContext(),"App is Going to Background", Toast.LENGTH_SHORT).show();
		}
	}

	@Override
	public void onBackPressed() {
		Log.d(TAG,"onBackPressed " + isBackPressed + "" + this.getLocalClassName());
		super.onBackPressed();
	}

	@Override
	public void onWindowFocusChanged(boolean hasFocus) {
		isWindowFocused = hasFocus;
		isWindowFocused = hasFocus;

		if (isBackPressed && !hasFocus) {
			isBackPressed = false;
			isWindowFocused = true;
		}
		
		Log.d(TAG,"Window Focus " + isWindowFocused + "" + isBackPressed);
		super.onWindowFocusChanged(hasFocus);
	}
	
}

