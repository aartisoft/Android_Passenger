package com.netcabs.passenger;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.content.IntentSender.SendIntentException;
import android.os.Bundle;
import android.provider.Settings.Secure;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.facebook.Session;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.google.android.gms.plus.Plus;
import com.google.android.gms.plus.model.people.Person;
import com.netcabs.asynctask.LoginAsyncTask;
import com.netcabs.asynctask.LostPasswordAsyncTask;
import com.netcabs.asynctask.ProfileDetailsAsyncTask;
import com.netcabs.asynctask.ResetPasswordAsyncTask;
import com.netcabs.customview.DialogController;
import com.netcabs.database.PreferenceUtil;
import com.netcabs.datamodel.FaceBookGplusLoginInfo;
import com.netcabs.facebook.FacebookModule;
import com.netcabs.interfacecallback.OnRequestComplete;
import com.netcabs.internetconnection.InternetConnectivity;
import com.netcabs.passengerinfo.PassengerApp;
import com.netcabs.utils.BaseActivity;
import com.netcabs.utils.ConstantValues;


public class LoginActivity extends Activity implements OnClickListener, ConnectionCallbacks, OnConnectionFailedListener {

	private Button btnFacebook;
	private Button btnGooglePlus;
	private Button btnLogin;
	private Button btnLostPassword;
	private Button btnRegisterNow;
	private EditText edTxtUserName;
	private EditText edTxtPassword;
	private Dialog dialogLostPassword;
	private Dialog dialogResetPassword;
	private FacebookModule fbModule;
	private PreferenceUtil preferenceUtil;
	
	//For Google Plus login integration
	private static final int RC_SIGN_IN = 0;
	private GoogleApiClient mGoogleApiClient;
	private ConnectionResult mConnectionResult;
	private boolean mIntentInProgress = false;
	private boolean mSignInClicked;
	private RelativeLayout relativeLayout;
	//private boolean isN = false;
	//End of Google plus integration
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		
		BaseActivity.authLock = true;
		initFacebook(savedInstanceState);
		initGooglePlus();
		initViews();
		setListener();
		loadData();
	}

	private void initFacebook(Bundle savedInstanceState) {
		fbModule = new FacebookModule(LoginActivity.this, savedInstanceState);
	}

	private void initGooglePlus() {
//		 Plus.PlusOptions plusOptions = new Plus.PlusOptions.Builder()
//	      .addActivityTypes("http://schema.org/AddAction", "http://schema.org/BuyAction")
//	      .build();
		
//		mGoogleApiClient = new GoogleApiClient.Builder(this)
//				.addConnectionCallbacks(this)
//				.addOnConnectionFailedListener(this)
//				.addScope(Plus.SCOPE_PLUS_LOGIN)
//				.addApi(Plus.API, plusOptions)
//				.build();
		
		
		mGoogleApiClient = new GoogleApiClient.Builder(this)
	    .addApi(Plus.API)
	    .addScope(Plus.SCOPE_PLUS_PROFILE)
	    .addConnectionCallbacks(this)
	    .addOnConnectionFailedListener(this)
	    .build();
	}

	private void initViews() {
		relativeLayout = (RelativeLayout) findViewById(R.id.relative_main);
		btnFacebook = (Button) findViewById(R.id.btn_facebook);
		btnGooglePlus = (Button) findViewById(R.id.btn_google_plus);
		btnLogin = (Button) findViewById(R.id.btn_login);
		btnLostPassword = (Button) findViewById(R.id.btn_lost_password);
		btnRegisterNow = (Button) findViewById(R.id.btn_register_now);
		
		edTxtUserName = (EditText) findViewById(R.id.ed_txt_user_name);
		edTxtPassword = (EditText) findViewById(R.id.ed_txt_password);
		
		preferenceUtil = new PreferenceUtil(LoginActivity.this);
	}

	private void setListener() {
		relativeLayout.setOnClickListener(this);
		btnFacebook.setOnClickListener(this);
		btnGooglePlus.setOnClickListener(this);
		btnLogin.setOnClickListener(this);
		btnRegisterNow.setOnClickListener(this);
		
		btnLostPassword.setOnClickListener(this);
	}

	private void loadData() {
		
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.relative_main:
			PassengerApp.getInstance().hideKeyboard(LoginActivity.this, v);
			break;
			
		case R.id.btn_facebook:
			processLoginFacebook();
			break;
			
		case R.id.btn_google_plus:
			processGooglePlusLogin();
			break;
			
		case R.id.btn_login:
			if(PassengerApp.getInstance().isEmailValid(edTxtUserName.getText().toString().trim()) && !edTxtPassword.getText().toString().trim().equalsIgnoreCase("") && edTxtPassword.getText().toString().trim().length() > 5) {
				processLogin();
			} else {
				if(!PassengerApp.getInstance().isEmailValid(edTxtUserName.getText().toString().trim())) {
					edTxtUserName.setError("Required");
				} else if(edTxtPassword.getText().toString().trim().equalsIgnoreCase("")) {
					edTxtPassword.setError("Required");
				} else if(edTxtPassword.getText().toString().trim().length() < 6) {
					edTxtPassword.setError("Minimum length is "+ConstantValues.PASSWORD_LENGTH);
				} else {
					Toast.makeText(LoginActivity.this, "Please enter user name and passwrod !", Toast.LENGTH_SHORT).show();
				}
			}
			break;
			
		case R.id.btn_register_now:
			finish();
			startActivity(new Intent(LoginActivity.this, RegistationActivity.class).putExtra("loggedin_type", 3));
			break;
			
		case R.id.btn_lost_password:
			processLostPassword();
			break;

		default:
			break;
		}
	}

	private void processLoginFacebook() {
		if(InternetConnectivity.isConnectedToInternet(LoginActivity.this)) {
			fbModule.facebookLogin();
		} else {
			Toast.makeText(LoginActivity.this, ConstantValues.INTERNET_CONNECTION_FAILURE_MSG, Toast.LENGTH_SHORT).show();
		}
		
	}

	private void processGooglePlusLogin() {
		if (!mGoogleApiClient.isConnecting()) {
			Log.e("i am in if", "---");
			mSignInClicked = true;
			getProfileInformation();
			/*if(PassengerApp.getInstance().getFbGplusLoginInfo() != null) {
				loginWithGPlus();
			}*/
			resolveSignInError();
		} else if (mGoogleApiClient.isConnected()) {
			Log.e("I am in else", "--");
			loginWithGPlus();
			Plus.AccountApi.clearDefaultAccount(mGoogleApiClient);
			mGoogleApiClient.disconnect();
			//mGoogleApiClient.connect();
		}
		else {
			
		}
	}
	
	

	private void loginWithGPlus() {
		if(InternetConnectivity.isConnectedToInternet(LoginActivity.this)) {
			new LoginAsyncTask(LoginActivity.this, new OnRequestComplete() { 
				
				@Override
				public void onRequestComplete(String result) {
					edTxtPassword.setText("");
					try {
							if ("2001".equals(result)){
								new ProfileDetailsAsyncTask(LoginActivity.this, new OnRequestComplete() {
								
								@Override
								public void onRequestComplete(String result) {
									edTxtPassword.setText("");
									try {
										if("2001".equals(result)) {
											preferenceUtil.setUserID(PassengerApp.getInstance().getPassengerId());
											preferenceUtil.setLOGIN_TYPES("1");
											if (PassengerApp.getInstance().getIsMobileVerified() == 1) {
												finish();
												startActivity(new Intent(LoginActivity.this, UserEnterPinActivity.class));
											} else {
												finish();
												startActivity(new Intent(LoginActivity.this, ConfirmRegistrationActivity.class));
											}
											
										} else if("3001".equals(result)) {
											
										} else if("4001".equals(result)) {

										} else {
											
										}
								} catch (Exception e) {
										Toast.makeText(LoginActivity.this, "Response Error: (" + e.getMessage() + ").  Please try again", Toast.LENGTH_SHORT).show();
									}
								}
								
							}).execute(ConstantValues.FUNC_ID_PROFILE_DETAILS, PassengerApp.getInstance().getPassengerId());
							
//								if ("2001".equals(result)) {
//									startActivity(new Intent(LoginActivity.this, RegistationActivity.class).putExtra("loggedin_type", 1));
//								} 
						} else if("3001".equals(result)) {
							finish();
							startActivity(new Intent(LoginActivity.this, RegistationActivity.class).putExtra("loggedin_type", 1));
//								new ProfileDetailsAsyncTask(LoginActivity.this, new OnRequestComplete() {
//									
//									@Override
//									public void onRequestComplete(String result) {
//										if("2001".equals(result)) {
//											preferenceUtil.setUserID(PassengerApp.getInstance().getPassengerId());
//											if (PassengerApp.getInstance().getIsMobileVerified() == 1) {
//												startActivity(new Intent(LoginActivity.this, UserEnterPinActivity.class));
//											} else {
//												startActivity(new Intent(LoginActivity.this, ConfirmRegistrationActivity.class));
//											}
//											
//										} else if("3001".equals(result)) {
//											
//										} else if("4001".equals(result)) {
//
//										} else {
//											
//										}
//									}
//								}).execute(ConstantValues.FUNC_ID_PROFILE_DETAILS, PassengerApp.getInstance().getPassengerId());
						} else if("4001".equals(result)) {
							Toast.makeText(getApplicationContext(), "This email is already registered", Toast.LENGTH_LONG).show();
						} else {
								Toast.makeText(getApplicationContext(), "Entry is not correct", Toast.LENGTH_LONG).show();
						}
					} catch (Exception e) {
						Toast.makeText(LoginActivity.this, "Response Error: (" + e.getMessage() + ").  Please try again", Toast.LENGTH_SHORT).show();
					}
				}
			}).execute(ConstantValues.FUNC_ID_LOGIN, "1", PassengerApp.getInstance().getPassengerId(), PassengerApp.getInstance().getFbGplusLoginInfo().getFbGplusEmail(), "", "2", getDeviceToken(), preferenceUtil.getRegistrationKey(), "");
		} else {
			Toast.makeText(LoginActivity.this, ConstantValues.INTERNET_CONNECTION_FAILURE_MSG, Toast.LENGTH_SHORT).show();
		}
}

	private void processLostPassword() {
		dialogLostPassword = new DialogController(LoginActivity.this).LostPasswordDialog();
		
		Button btnCancel = (Button) dialogLostPassword.findViewById(R.id.btn_cancel);
		btnCancel.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				dialogLostPassword.dismiss();
			}
		});
		final EditText edTxtEmailAddress = (EditText) dialogLostPassword.findViewById(R.id.ed_txt_email_address);
		RelativeLayout relativeLayoutPopUp = (RelativeLayout) dialogLostPassword.findViewById(R.id.relative_popup);
		relativeLayoutPopUp.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				PassengerApp.getInstance().hideKeyboard(LoginActivity.this, v);
			}
		});
		
		Button btnSubmit = (Button) dialogLostPassword.findViewById(R.id.btn_submit);
		btnSubmit.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if(PassengerApp.getInstance().isEmailValid(edTxtEmailAddress.getText().toString().trim())) {
					new LostPasswordAsyncTask(LoginActivity.this, new OnRequestComplete() {
						
						@Override
						public void onRequestComplete(String result) {
							try {
								if("2001".equals(result)) {
									Toast.makeText(LoginActivity.this, "Please check your mail inbox !", Toast.LENGTH_SHORT).show();
									dialogLostPassword.dismiss();
									processResetPassword();
								} else {
									Toast.makeText(LoginActivity.this, "Please try again !", Toast.LENGTH_SHORT).show();
								}
							} catch (Exception e) {
								Toast.makeText(LoginActivity.this, "Response error: (" + e.getMessage() + "). Please try again" , Toast.LENGTH_SHORT).show();
							}
							
							
						}
					}).execute(ConstantValues.FUNC_ID_LOST_PASSWORD, edTxtEmailAddress.getText().toString().trim());
				} else {
					edTxtEmailAddress.setError("Required");
				}
			}
		});
		dialogLostPassword.show();
	}

	private void processLogin() {
		if(InternetConnectivity.isConnectedToInternet(LoginActivity.this)) {
			new LoginAsyncTask(LoginActivity.this, new OnRequestComplete() {
				
				@Override
				public void onRequestComplete(String result) {
					edTxtPassword.setText("");
					try {
						if ("2001".equals(result)) {
							new ProfileDetailsAsyncTask(LoginActivity.this, new OnRequestComplete() {
								
								@Override
								public void onRequestComplete(String result) {
									edTxtPassword.setText("");
									
									try {
										if("2001".equals(result)) {
											preferenceUtil.setUserID(PassengerApp.getInstance().getPassengerId());
											preferenceUtil.setLOGIN_TYPES("3");
											if (PassengerApp.getInstance().getIsMobileVerified() == 1) {
												finish();
												startActivity(new Intent(LoginActivity.this, UserEnterPinActivity.class));
											} else {
												finish();
												startActivity(new Intent(LoginActivity.this, ConfirmRegistrationActivity.class));
											}
											
										} else if("3001".equals(result)) {
											
										} else if("4001".equals(result)) {
															
										} else {
											
										}
									} catch (Exception e) {
										Toast.makeText(LoginActivity.this, "Response error: (" + e.getMessage() + "). Please try again" , Toast.LENGTH_SHORT).show();
									}
									
								}
							}).execute(ConstantValues.FUNC_ID_PROFILE_DETAILS,PassengerApp.getInstance().getPassengerId());
							
						} else {
							Toast.makeText(LoginActivity.this, "User name or password is incorrect !", Toast.LENGTH_SHORT).show();
						}
					} catch (Exception e) {
							Toast.makeText(LoginActivity.this, "Response Error: (" + e.getMessage() + ").  Please try again", Toast.LENGTH_SHORT).show();
					}
				}
			}).execute(ConstantValues.FUNC_ID_LOGIN, "3", "", edTxtUserName.getText().toString().trim(), edTxtPassword.getText().toString().trim(), "2", getDeviceToken(), preferenceUtil.getRegistrationKey(), "");
		} else {
			Toast.makeText(LoginActivity.this, ConstantValues.INTERNET_CONNECTION_FAILURE_MSG, Toast.LENGTH_SHORT).show();
		}
	}
	
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		Session.getActiveSession().onActivityResult(this, requestCode, resultCode, data);
		
		if (requestCode == RC_SIGN_IN) {
			if (resultCode != RESULT_OK) {
				//mSignInClicked = false;
			}

			mIntentInProgress = false;

			if (!mGoogleApiClient.isConnecting()) {
				mGoogleApiClient.connect();
			}
		}
	}
	
	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		//Session session = Session.getActiveSession();
		//Session.saveSession(session, outState);
	}

	@Override
	public void onConnected(Bundle arg0) {
		//mSignInClicked = false;
		// Get user's information
		getProfileInformation();
		
	}

	@Override
	public void onConnectionSuspended(int arg0) {
		mGoogleApiClient.connect();
	}

	@Override
	public void onConnectionFailed(ConnectionResult result) {
		if (!result.hasResolution()) {
			GooglePlayServicesUtil.getErrorDialog(result.getErrorCode(), this, 0).show();
			return;
		}

		if (!mIntentInProgress) {
			// Store the ConnectionResult for later usage
			mConnectionResult = result;

			if (mSignInClicked) {
				// The user has already clicked 'sign-in' so we attempt to
				// resolve all
				// errors until the user is signed in, or they cancel.
				resolveSignInError();
			}
		}
	}
	
	private void resolveSignInError() {
		if(mConnectionResult != null) {
			if (mConnectionResult.hasResolution()) {
				try {
					mIntentInProgress = true;
					mConnectionResult.startResolutionForResult(this, RC_SIGN_IN);
				} catch (SendIntentException e) {
					mIntentInProgress = false;
					mGoogleApiClient.connect();
				}
			}
			//getProfileInformation();
		} else {
			//getProfileInformation();
		}
	}
	
	@Override
	protected void onStart() {
		super.onStart();
		mGoogleApiClient.connect();
	}
	
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
	}

	@Override
	protected void onStop() {
		super.onStop();
		if (mGoogleApiClient.isConnected()) {
			mGoogleApiClient.disconnect();
		}
	}
	
	private void getProfileInformation() {
		try {
			if (Plus.PeopleApi.getCurrentPerson(mGoogleApiClient) != null) {
				Person currentPerson = Plus.PeopleApi.getCurrentPerson(mGoogleApiClient);
				String personName = currentPerson.getDisplayName();
				String personPhotoUrl = currentPerson.getImage().getUrl();
				String personGooglePlusProfile = currentPerson.getUrl();
				String email = Plus.AccountApi.getAccountName(mGoogleApiClient);
				String profileId = currentPerson.getId();

				Log.e("Profile id is", "----"+profileId);
				Log.e("Google Account info is ", "Name: " + personName + ", plusProfile: "
						+ personGooglePlusProfile + ", email: " + email
						+ ", Image: " + personPhotoUrl);
	
					
					FaceBookGplusLoginInfo fbGplusInfo = new FaceBookGplusLoginInfo();
					fbGplusInfo.setFbGplusProfileId(profileId);
					
					if (personName.contains(" ")) {
						String [] name = personName.split(" ");
						fbGplusInfo.setFbGplusFirstName(name[0]);
						fbGplusInfo.setFbGplusLastName(name[name.length-1]);
						
					} else {
						fbGplusInfo.setFbGplusFirstName(personName);
						fbGplusInfo.setFbGplusLastName("");
						
					}
					
					fbGplusInfo.setFbGplusProfilePic(personPhotoUrl);
					fbGplusInfo.setFbGplusEmail(email);
					
					PassengerApp.getInstance().setFbGplusLoginInfo(fbGplusInfo);
					Log.e("is clicked signin", ""+mSignInClicked);
					if(mSignInClicked) {
						loginWithGPlus();
					}
					
			} else {
				Toast.makeText(getApplicationContext(), "Person information is null", Toast.LENGTH_LONG).show();
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
/*	@Override
	protected void onActivityResult(int requestCode, int responseCode, Intent intent) {
		if (requestCode == RC_SIGN_IN) {
			if (responseCode != RESULT_OK) {
				mSignInClicked = false;
			}

			mIntentInProgress = false;

			if (!mGoogleApiClient.isConnecting()) {
				mGoogleApiClient.connect();
			}
		}
	}*/
	
	private String getDeviceToken() {
		return Secure.getString(LoginActivity.this.getContentResolver(), Secure.ANDROID_ID);
	}
	
	private void processResetPassword() {
		BaseActivity.isLock = true;
		dialogResetPassword = new DialogController(LoginActivity.this).ResetPasswordDialog();
		Button btnCancel = (Button) dialogLostPassword.findViewById(R.id.btn_cancel);
		btnCancel.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				dialogResetPassword.dismiss();
			}
		});
		
		final EditText edTxtEmailAddress = (EditText) dialogResetPassword.findViewById(R.id.ed_txt_email_address);
		final EditText edTxtActivationCode = (EditText) dialogResetPassword.findViewById(R.id.ed_txt_activation_code);
		final EditText edTxtPassword = (EditText) dialogResetPassword.findViewById(R.id.ed_txt_password);
		final EditText edTxtConfrimPassword = (EditText) dialogResetPassword.findViewById(R.id.ed_txt_confirm_password);
		
		Button btnSubmit = (Button) dialogResetPassword.findViewById(R.id.btn_submit);
		btnSubmit.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if(PassengerApp.getInstance().isEmailValid(edTxtEmailAddress.getText().toString().trim()) && !edTxtActivationCode.getText().toString().trim().equalsIgnoreCase("") && !edTxtPassword.getText().toString().trim().equalsIgnoreCase("") && !edTxtConfrimPassword.getText().toString().trim().equalsIgnoreCase("") && edTxtPassword.getText().toString().trim().equals(edTxtConfrimPassword.getText().toString().trim())) {
					new ResetPasswordAsyncTask(LoginActivity.this, new OnRequestComplete() {
						
						@Override
						public void onRequestComplete(String result) {
							if("2001".equals(result)) {
								Toast.makeText(LoginActivity.this, "Your password successfully reset !", Toast.LENGTH_SHORT).show();
								dialogResetPassword.dismiss();
							} else {
								Toast.makeText(LoginActivity.this, "Please try again !", Toast.LENGTH_SHORT).show();
							}
						}
					}).execute(ConstantValues.FUNC_ID_RESET_PASSWORD, edTxtEmailAddress.getText().toString().trim(), edTxtActivationCode.getText().toString().trim(), edTxtPassword.getText().toString().trim());
				} else {
					Toast.makeText(LoginActivity.this, "Please ....fill up all fields....", Toast.LENGTH_SHORT).show();
				}
			}
		});
		dialogResetPassword.show();
	}
	
	@Override
	protected void onPause() {
		BaseActivity.isLock = true;
		super.onPause();
	}
	
	
	

}
