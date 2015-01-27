package com.netcabs.passenger;

import java.io.IOException;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.netcabs.asynctask.CountryListAsyncTask;
import com.netcabs.asynctask.PaymentsMethodAsyncTask;
import com.netcabs.asynctask.ProfileDetailsAsyncTask;
import com.netcabs.database.PreferenceUtil;
import com.netcabs.interfacecallback.OnRequestComplete;
import com.netcabs.internetconnection.InternetConnectivity;
import com.netcabs.latlon.GPSTracker;
import com.netcabs.passengerinfo.PassengerApp;
import com.netcabs.utils.ConstantValues;
import com.netcabs.utils.Utils;

public class SplashScreenActivity extends Activity {

	private PreferenceUtil preferenceUtil;
	private Handler handler = new Handler();
	private Runnable runner;
	
	// For GCM
	public static final String EXTRA_MESSAGE = "message";
    public static final String PROPERTY_REG_ID = "registration_id";
    private static final String PROPERTY_APP_VERSION = "appVersion";
    private final static int PLAY_SERVICES_RESOLUTION_REQUEST = 9000;
    
    String SENDER_ID = "191596705067";
    
    static final String TAG = "SplashScreenActivity";
    
    private GoogleCloudMessaging gcm;
    SharedPreferences prefs;
    private String regid;
	
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_splash_screen);
		
		/*if (Utils.isGpsEnable(getApplication())) {
			loadData();
		} else {
			showSettingsAlert();
		}
		*/
		preferenceUtil = new PreferenceUtil(getApplicationContext());
		
		if (checkPlayServices()) {
			gcm = GoogleCloudMessaging.getInstance(this);
			regid = getRegistrationId(getApplicationContext());
			preferenceUtil.setRegistrationKey(regid);
			Log.i("Reg id is : ", "_____________" + regid);

			if (regid.isEmpty()) {
				registerInBackground();
			}
			
		} else {
			Log.i(TAG, "No valid Google Play Services APK found.");
		}
	}
	
	private void loadData() {

		if(!InternetConnectivity.isConnectedToInternet(SplashScreenActivity.this)) {
			Toast.makeText(SplashScreenActivity.this, ConstantValues.INTERNET_CONNECTION_FAILURE_MSG, Toast.LENGTH_SHORT).show();
			return; 
		} else {
			
			preferenceUtil = new PreferenceUtil(SplashScreenActivity.this);
			
			runner = new Runnable() {
			    public void run() {
			    	countryName();
			    }
			};
			
			handler.postDelayed(runner, 1000);
			
			new CountryListAsyncTask(SplashScreenActivity.this, new OnRequestComplete() {
				
				@Override
				public void onRequestComplete(String result) {
						try {
							if(result != null ) {
								if(result.equals("1")) {
								new PaymentsMethodAsyncTask(SplashScreenActivity.this, new OnRequestComplete() {
									@Override
									public void onRequestComplete(String result) {
										try {
											if("1".equals(result)) {
												if(!preferenceUtil.getUserID().toString().equalsIgnoreCase("")) {
													new ProfileDetailsAsyncTask(SplashScreenActivity.this, new OnRequestComplete() {
														
														@Override
														public void onRequestComplete(String result) {
															try{
																if("2001".equals(result)) {
																	if(PassengerApp.getInstance().getProfileDetailsInfo().getMobileVerified() == 1) {
																		startActivity(new Intent(SplashScreenActivity.this, LoginWithPinActivity.class));
																		finish();
																	} else {
																		startActivity(new Intent(SplashScreenActivity.this, LoginActivity.class));
																		finish();
																	}
																} else if("3001".equals(result)) {
																	
																} else if("4001".equals(result)) {
																					
																} else {
																	
																}
															} catch (Exception e) {
																	Toast.makeText(SplashScreenActivity.this, "Response Error: (" + e.getMessage() + ").  Please try again", Toast.LENGTH_SHORT).show();
															}
														}
													}).execute(ConstantValues.FUNC_ID_PROFILE_DETAILS, preferenceUtil.getUserID());
													
													} else {
														startActivity(new Intent(SplashScreenActivity.this, LoginActivity.class));
														finish();
													}
												} else {
													Toast.makeText(SplashScreenActivity.this, "Pleae Try Again", Toast.LENGTH_SHORT).show();
													finish();
												}
										} catch (Exception e) {
												Toast.makeText(SplashScreenActivity.this, "Response Error: (" + e.getMessage() + ").  Please try again", Toast.LENGTH_SHORT).show();
										}
									} 
								}).execute(ConstantValues.FUNC_ID_PAYMENT_METHOD_LIST);
							} else {
								Toast.makeText(SplashScreenActivity.this, "Pleae Try Again", Toast.LENGTH_SHORT).show();
								finish();
							}
						} else {
								Toast.makeText(SplashScreenActivity.this, "Pleae Try Again", Toast.LENGTH_SHORT).show();
								finish();
							}
					} catch (Exception e) {
							Toast.makeText(SplashScreenActivity.this, "Response Error: (" + e.getMessage() + ").  Please try again", Toast.LENGTH_SHORT).show();
					}
				}
			}).execute(ConstantValues.FUNC_ID_COUNTRY_LIST);
			
		}
		
	}

	private void countryName() {
		GPSTracker gps = new GPSTracker(SplashScreenActivity.this);
		double mcurrent_lat = gps.getLatitude();
		double mcurrent_lon = gps.getLongitude();
		
		if (Utils.getCountryName(SplashScreenActivity.this, mcurrent_lat, mcurrent_lon) != null) {
			ConstantValues.COUNTRY_NAME = Utils.getCountryName(SplashScreenActivity.this, mcurrent_lat, mcurrent_lon);
			Log.i("LOCAL COUNTRY NAME", "Country Name " + ConstantValues.COUNTRY_NAME);
		}
		
	}
	
	 public void showSettingsAlert(){
	        AlertDialog.Builder alertDialog = new AlertDialog.Builder(SplashScreenActivity.this);
	 
	        // Setting Dialog Title
	        alertDialog.setTitle("GPS is settings");
	 
	        // Setting Dialog Message
	        alertDialog.setMessage("GPS is not enabled. Do you want to go to settings menu?");
	 
	        // On pressing Settings button
	        alertDialog.setPositiveButton("Settings", new DialogInterface.OnClickListener() {
	            public void onClick(DialogInterface dialog,int which) {
	                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
	                startActivity(intent);
	            }
	        });
	 
	        // on pressing cancel button
	        alertDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
	            public void onClick(DialogInterface dialog, int which) {
	            dialog.cancel();
	            }
	        });
	 
	        // Showing Alert Message
	        alertDialog.show();
	    }
	
	@Override
	protected void onResume() {
		if (Utils.isGpsEnable(getApplication())) {
			loadData();
		} else {
			showSettingsAlert();
		}
		
		checkPlayServices();
		super.onResume();
	}
	
	private boolean checkPlayServices() {
	    int resultCode = GooglePlayServicesUtil.isGooglePlayServicesAvailable(this);
	    if (resultCode != ConnectionResult.SUCCESS) {
	        if (GooglePlayServicesUtil.isUserRecoverableError(resultCode)) {
	            GooglePlayServicesUtil.getErrorDialog(resultCode, this, PLAY_SERVICES_RESOLUTION_REQUEST).show();
	            
	        } else {
	            Log.i(TAG, "This device is not supported.");
	            finish();
	        }
	        return false;
	    }
	    return true;
	}
	
	private String getRegistrationId(Context context) {
	    final SharedPreferences prefs = getGCMPreferences(context);
	    String registrationId = prefs.getString(PROPERTY_REG_ID, "");
	    if (registrationId.isEmpty()) {
	        Log.i(TAG, "Registration not found.");
	        return "";
	    }
	    // Check if app was updated; if so, it must clear the registration ID since the existing regID is not guaranteed to work with the new app version.
	    int registeredVersion = prefs.getInt(PROPERTY_APP_VERSION, Integer.MIN_VALUE);
	    int currentVersion = getAppVersion(context);
	    if (registeredVersion != currentVersion) {
	        Log.i(TAG, "App version changed.");
	        return "";
	    }
	    return registrationId;
	}
	
	private SharedPreferences getGCMPreferences(Context context) {
	    // This sample app persists the registration ID in shared preferences, but how you store the regID in your app is up to you.
	    return getSharedPreferences(SplashScreenActivity.class.getSimpleName(), Context.MODE_PRIVATE);
	}
	
	private static int getAppVersion(Context context) {
	    try {
	        PackageInfo packageInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
	        return packageInfo.versionCode;
	    } 
	    catch (NameNotFoundException e) {
	        // should never happen
	        throw new RuntimeException("Could not get package name: " + e);
	    }
	}
	
	private void registerInBackground() {
		class APITaskGCM extends AsyncTask<Void, Void, String> {
			@Override
			protected String doInBackground(Void... params) {
				// TODO Auto-generated method stub
				 String msg = "";
		            try {
		                if (gcm == null) {
		                    gcm = GoogleCloudMessaging.getInstance(getApplicationContext());
		                }
		                regid = gcm.register(SENDER_ID);
		                preferenceUtil.setRegistrationKey(regid);
		                msg = "Device registered, registration id=" + regid;
	
		                storeRegistrationId(getApplicationContext(), regid);
		            } 
		            catch (IOException ex) {
		                msg = "Error :" + ex.getMessage();
		            }
		            return msg;
			}

			@Override
			protected void onPostExecute(String result) {
				Log.v(TAG, "Successful !");
				Log.v(TAG, "gcm_regid: " + regid);
			}
		}
		
		new APITaskGCM().execute();
	}
	
	private void storeRegistrationId(Context context, String regId) {
	    final SharedPreferences prefs = getGCMPreferences(context);
	    int appVersion = getAppVersion(context);
	    Log.i(TAG, "Saving regId on app version " + appVersion);
	    SharedPreferences.Editor editor = prefs.edit();
	    editor.putString(PROPERTY_REG_ID, regId);
	    editor.putInt(PROPERTY_APP_VERSION, appVersion);
	    editor.commit();
	}
	
}
