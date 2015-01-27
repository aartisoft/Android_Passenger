package com.netcabs.database;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class PreferenceUtil {
	
	Context mContext;
	private SharedPreferences sharedPreferences;
	private SharedPreferences.Editor spEditor;
	
	private final String USER_ID = "user_id";
	private final String LOGIN_TYPES = "0";
	private final String REGISTRATION_KEY = "registration_key";
	
	public PreferenceUtil(Context mContext) {
		super();
		this.mContext = mContext;
		sharedPreferences = PreferenceManager.getDefaultSharedPreferences(mContext);
	}
	
	public String getUserID() {
		return sharedPreferences.getString(USER_ID, "");  
	}
	
	public void setUserID(String userID) {
		spEditor = sharedPreferences.edit();
		spEditor.putString(USER_ID, userID);
		spEditor.commit();
	}

	public String getLOGIN_TYPES() {
		return sharedPreferences.getString(LOGIN_TYPES, "");  
	}
	
	public void setLOGIN_TYPES(String logInTypes) {
		spEditor = sharedPreferences.edit();
		spEditor.putString(LOGIN_TYPES, logInTypes);
		spEditor.commit();
	}
	
	public String getRegistrationKey() {
		return sharedPreferences.getString(REGISTRATION_KEY, "");  
	}
	
	public void setRegistrationKey(String reg_key) {
		spEditor = sharedPreferences.edit();
		spEditor.putString(REGISTRATION_KEY, reg_key);
		spEditor.commit();
	}



	
	


}