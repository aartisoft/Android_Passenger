package com.netcabs.facebook;

import java.util.Arrays;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings.Secure;
import android.util.Log;
import android.widget.Toast;

import com.facebook.LoggingBehavior;
import com.facebook.Request;
import com.facebook.Response;
import com.facebook.Session;
import com.facebook.SessionState;
import com.facebook.Settings;
import com.facebook.model.GraphObject;
import com.facebook.model.GraphUser;
import com.netcabs.asynctask.LoginAsyncTask;
import com.netcabs.asynctask.ProfileDetailsAsyncTask;
import com.netcabs.database.PreferenceUtil;
import com.netcabs.datamodel.FaceBookGplusLoginInfo;
import com.netcabs.interfacecallback.OnRequestComplete;
import com.netcabs.internetconnection.InternetConnectivity;
import com.netcabs.passenger.ConfirmRegistrationActivity;
import com.netcabs.passenger.LoginActivity;
import com.netcabs.passenger.RegistationActivity;
import com.netcabs.passenger.UserEnterPinActivity;
import com.netcabs.passengerinfo.PassengerApp;
import com.netcabs.utils.BaseActivity;
import com.netcabs.utils.ConstantValues;

public class FacebookModule {
	private Activity activityContext;
	private ProgressDialog dlog;
	private PreferenceUtil preferenceUtil;
	
	public FacebookModule(Activity activity, Bundle savedInstanceState) {
		this.activityContext = activity;
		preferenceUtil = new PreferenceUtil(this.activityContext);
		Settings.addLoggingBehavior(LoggingBehavior.INCLUDE_ACCESS_TOKENS);
		Session session = Session.getActiveSession();
		if (session == null) {
			if (savedInstanceState != null) {}
			if (session == null) {session = new Session(activityContext);}
			Session.setActiveSession(session);
			if (session.getState().equals(SessionState.CREATED_TOKEN_LOADED)) {}
		}
	}
	
	private String getDeviceToken() {
		return Secure.getString(activityContext.getContentResolver(), Secure.ANDROID_ID);
	}
	
	public void facebookLogin() {
		Session s = new Session(activityContext);
		Session.setActiveSession(s);
		Session.OpenRequest request = new Session.OpenRequest(activityContext);
		request.setPermissions(Arrays.asList("basic_info", "email"));
		request.setCallback( new Session.StatusCallback() {
             @Override
             public void call(final Session session, SessionState state, Exception exception) {
                 if (session.isOpened()) {
                	 dlog = ProgressDialog.show(activityContext, "Log In", "Please wait...", true, false);
                     Request.newMeRequest(session, new Request.GraphUserCallback() {
                         @Override
                         public void onCompleted(GraphUser user, Response response) {
                        	 
                        	 if (dlog.isShowing()) {
                 				dlog.dismiss();
                 			 }
                        	 
                             if (user != null) {
								try {
									Log.i("Facebook Output Final", "" + response);
									GraphObject go = response.getGraphObject();
									JSONObject jso = go.getInnerJSONObject();
									Log.i("User Name"," " + jso.getString("first_name"));
									Log.i("FB Id"," " + jso.getString("id"));
									Log.i("Device Token","==" + getDeviceToken());
									Log.i("FB Profile Pic", "" + "https://graph.facebook.com/" + jso.getString("id") + "/picture?type=large");
									String fbPic = "https://graph.facebook.com/" + jso.getString("id") + "/picture?type=large";
									
									FaceBookGplusLoginInfo fbGplusInfo = new FaceBookGplusLoginInfo();
									fbGplusInfo.setFbGplusProfileId(jso.getString("id"));
									fbGplusInfo.setFbGplusFirstName(jso.getString("first_name"));
									fbGplusInfo.setFbGplusLastName(jso.getString("last_name"));
									fbGplusInfo.setFbGplusProfilePic(fbPic);
									
									if (jso.getString("email") != null) {
										fbGplusInfo.setFbGplusEmail(jso.getString("email"));
									}
								/*	if (jso.getString("phone") !=null) {
										fbGplusInfo.setFbMobile(jso.getString("phone"));
									}*/
									
									PassengerApp.getInstance().setFbGplusLoginInfo(fbGplusInfo);
									
									if(InternetConnectivity.isConnectedToInternet(activityContext)) {
										new LoginAsyncTask(activityContext, new OnRequestComplete() {
											@Override
											public void onRequestComplete(String result) {
												session.closeAndClearTokenInformation();
												if ("2001".equals(result)) {
													//activityContext.startActivity(new Intent(activityContext, RegistationActivity.class).putExtra("loggedin_type", 2));
													new ProfileDetailsAsyncTask(activityContext, new OnRequestComplete() {
														@Override
														public void onRequestComplete(String result) {
															if("2001".equals(result)) {
																preferenceUtil.setUserID(PassengerApp.getInstance().getPassengerId());
																preferenceUtil.setLOGIN_TYPES("2");
																if (PassengerApp.getInstance().getIsMobileVerified() == 1) {
																	activityContext.finish();
																	activityContext.startActivity(new Intent(activityContext, UserEnterPinActivity.class));
																} else {
																	activityContext.finish();
																	activityContext.startActivity(new Intent(activityContext, ConfirmRegistrationActivity.class));
																}
																
															} else if("3001".equals(result)) {
																
															} else if("4001".equals(result)) {
																				
															} else {
																
															}
														} 
														
													}).execute(ConstantValues.FUNC_ID_PROFILE_DETAILS, PassengerApp.getInstance().getPassengerId());
													
												} else if ("3001".equals(result)) {
													activityContext.finish();
													activityContext.startActivity(new Intent(activityContext, RegistationActivity.class).putExtra("loggedin_type", 2));
													
//													new ProfileDetailsAsyncTask(activityContext, new OnRequestComplete() {
//														
//														@Override
//														public void onRequestComplete(String result) {
//															if("2001".equals(result)) {
//																preferenceUtil.setUserID(PassengerApp.getInstance().getPassengerId());
//																if (PassengerApp.getInstance().getIsMobileVerified() == 1) {
//																	activityContext.startActivity(new Intent(activityContext, UserEnterPinActivity.class));
//																} else {
//																	activityContext.startActivity(new Intent(activityContext, ConfirmRegistrationActivity.class));
//																}
//																
//															} else if("3001".equals(result)) {
//																
//															} else if("4001".equals(result)) {
//																				
//															} else {
//																
//															}
//														} 
//														
//													}).execute(ConstantValues.FUNC_ID_PROFILE_DETAILS, PassengerApp.getInstance().getPassengerId());
													
												} else if ("4001".equals(result)) {
													Toast.makeText(activityContext, "Duplicate Email", Toast.LENGTH_LONG).show();
													
												} else {
													
												}
											}
										}).execute(ConstantValues.FUNC_ID_LOGIN, "2", jso.getString("id").trim(), jso.getString("email").trim(), "", "2", getDeviceToken(), new PreferenceUtil(activityContext).getRegistrationKey(), "");
									} else {
										Toast.makeText(activityContext, ConstantValues.INTERNET_CONNECTION_FAILURE_MSG, Toast.LENGTH_SHORT).show();
									}
					
									//activityContext.finish();
									
								} catch (JSONException e) {
									e.printStackTrace();
								}
                             } else {
								Toast.makeText(activityContext,"Error User Null",Toast.LENGTH_SHORT).show();
							}
						}

					}).executeAsync();
                 }
             }
             
		}); // end of call;

		s.openForRead(request); //now do the request above
	}
	
	public void userLogout() {
		Session session = Session.getActiveSession();
		if (!session.isClosed()) {
			session.closeAndClearTokenInformation();
		}
	}
}
