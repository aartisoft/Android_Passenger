package com.netcabs.passenger;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings.Secure;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnKeyListener;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.TextView.OnEditorActionListener;

import com.netcabs.asynctask.LoginWithPinAsyncTask;
import com.netcabs.asynctask.SetPinAsyncTask;
import com.netcabs.customview.CustomEditText;
import com.netcabs.interfacecallback.OnRequestComplete;
import com.netcabs.internetconnection.InternetConnectivity;
import com.netcabs.passengerinfo.PassengerApp;
import com.netcabs.utils.ConstantValues;

public class UserEnterPinConfirmActivity extends Activity implements OnClickListener {

	private TextView txtViewPinTitle;
	private CustomEditText edTxtPinOne;
	private CustomEditText edTxtPinTwo;
	private CustomEditText edTxtPinThree;
	private CustomEditText edTxtPinFour;
	private String choosePin;
	private int changeState = 1; //1 for checking state 2 for new entry state 3 for confirm
	private String newPinCode;
	private boolean isConfirmPin = false;
	private RelativeLayout relativeLayout;
	private int ediTxtFocusedPosition = 0;
	
	private boolean hasValue = true;
	
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_user_enter_pin);
		
		initViews();
		setListener();
		loadData();
	}

	private void initViews() {
		txtViewPinTitle = (TextView) findViewById(R.id.txt_view_pin_title);
		relativeLayout = (RelativeLayout) findViewById(R.id.relative_main);
		edTxtPinOne = (CustomEditText) findViewById(R.id.ed_txt_pin_one);
		edTxtPinTwo = (CustomEditText) findViewById(R.id.ed_txt_pin_two);
		edTxtPinThree = (CustomEditText) findViewById(R.id.ed_txt_pin_three);
		edTxtPinFour = (CustomEditText) findViewById(R.id.ed_txt_pin_four);
	}

	private void setListener() {
		relativeLayout.setOnClickListener(this);
		
		
		edTxtPinOne.setOnKeyListener(new OnKeyListener() {                 
		        @Override
		        public boolean onKey(View v, int keyCode, KeyEvent event) {
		            //You can identify which key pressed buy checking keyCode value with KeyEvent.KEYCODE_
		             if(keyCode == KeyEvent.KEYCODE_DEL){  
		            		edTxtPinOne.clearFocus();
							edTxtPinFour.setText("");
							edTxtPinFour.requestFocus();
							ediTxtFocusedPosition = 3;
							edTxtPinFour.setCursorVisible(true);
		                 }
		        return false;       
		            }
		    });
		
		edTxtPinTwo.setOnKeyListener(new OnKeyListener() {                 
	        @Override
	        public boolean onKey(View v, int keyCode, KeyEvent event) {
	            //You can identify which key pressed buy checking keyCode value with KeyEvent.KEYCODE_
	             if(keyCode == KeyEvent.KEYCODE_DEL){  
	            		edTxtPinTwo.clearFocus();
						edTxtPinOne.setText("");
						edTxtPinOne.requestFocus();
						ediTxtFocusedPosition = 0;
						edTxtPinOne.setCursorVisible(true);
	                 }
	        return false;       
	            }
	    });
		edTxtPinThree.setOnKeyListener(new OnKeyListener() {                 
	        @Override
	        public boolean onKey(View v, int keyCode, KeyEvent event) {
	            //You can identify which key pressed buy checking keyCode value with KeyEvent.KEYCODE_
	             if(keyCode == KeyEvent.KEYCODE_DEL){  
	            	 	edTxtPinThree.clearFocus();
						edTxtPinTwo.setText("");
						edTxtPinTwo.requestFocus();
						ediTxtFocusedPosition = 1;
						edTxtPinTwo.setCursorVisible(true);
	                 }
	        return false;       
	            }
	    });
		edTxtPinFour.setOnKeyListener(new OnKeyListener() {                 
	        @Override
	        public boolean onKey(View v, int keyCode, KeyEvent event) {
	            //You can identify which key pressed buy checking keyCode value with KeyEvent.KEYCODE_
	             if(keyCode == KeyEvent.KEYCODE_DEL){  
	            		edTxtPinFour.clearFocus();
						edTxtPinThree.setText("");
						edTxtPinThree.requestFocus();
						ediTxtFocusedPosition = 2;
						edTxtPinThree.setCursorVisible(true);
	                 }
	        return false;       
	            }
	    });
		
		edTxtPinOne.setOnEditorActionListener(new OnEditorActionListener() {
			@Override
			public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
				edTxtPinOne.clearFocus();
				edTxtPinFour.setText("");
				edTxtPinFour.requestFocus();
				ediTxtFocusedPosition = 3;
				edTxtPinFour.setCursorVisible(true);
				return false;
			}
	    });
		
		edTxtPinTwo.setOnEditorActionListener(new OnEditorActionListener() {
			@Override
			public boolean onEditorAction(TextView v, int actionId,
					KeyEvent event) {
				edTxtPinTwo.clearFocus();
				edTxtPinOne.setText("");
				edTxtPinOne.requestFocus();
				ediTxtFocusedPosition = 0;
				edTxtPinOne.setCursorVisible(true);
				return false;
			}
	    });
		
		edTxtPinThree.setOnEditorActionListener(new OnEditorActionListener() {
			@Override
			public boolean onEditorAction(TextView v, int actionId,
					KeyEvent event) {
				edTxtPinThree.clearFocus();
				edTxtPinTwo.setText("");
				edTxtPinTwo.requestFocus();
				ediTxtFocusedPosition = 1;
				edTxtPinTwo.setCursorVisible(true);
				return false;
			}
	    });
		
		edTxtPinFour.setOnEditorActionListener(new OnEditorActionListener() {
			@Override
			public boolean onEditorAction(TextView v, int actionId,
					KeyEvent event) {
				edTxtPinFour.clearFocus();
				edTxtPinThree.setText("");
				edTxtPinThree.requestFocus();
				ediTxtFocusedPosition = 2;
				edTxtPinThree.setCursorVisible(true);
				return false;
			}
	    });
		
		
		edTxtPinOne.addTextChangedListener(new TextWatcher() {
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				if( edTxtPinOne.getText().toString().length() == 0){
					hasValue = false;
				} else{
					hasValue = true;
				}
			}
			
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {

			}
			
			@Override
			public void afterTextChanged(Editable s) {
				if(hasValue){
					if (!edTxtPinOne.equals("")) {
						edTxtPinOne.clearFocus();
						edTxtPinTwo.requestFocus();
						ediTxtFocusedPosition = 1;
						edTxtPinTwo.setCursorVisible(true);
						if(isConfirmPin) {
							sendToMainMenuCodeActivity();
						} else {
							checkPinCode();
						}
					}
				} else {
					edTxtPinOne.requestFocus();
					edTxtPinOne.setCursorVisible(true);
				}
			}
		});
		
		edTxtPinTwo.addTextChangedListener(new TextWatcher() {
			
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				if(edTxtPinTwo.getText().toString().length() == 0){
					hasValue = false;
				} else{
					hasValue = true;
				}
			}
			
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {
				
			}
			
			@Override
			public void afterTextChanged(Editable s) {
				if(hasValue){
					if (!edTxtPinTwo.equals("")) {
						edTxtPinTwo.clearFocus();
						edTxtPinThree.requestFocus();
						ediTxtFocusedPosition = 2;
						edTxtPinThree.setCursorVisible(true);
						if(isConfirmPin) {
							sendToMainMenuCodeActivity();
						} else {
							checkPinCode();
						}
					}
				} else {
					edTxtPinTwo.requestFocus();
					edTxtPinTwo.setCursorVisible(true);
				}
			}
		});
		edTxtPinThree.addTextChangedListener(new TextWatcher() {
			
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				if(edTxtPinThree.getText().toString().length() == 0){
					hasValue = false;
				} else{
					hasValue = true;
				}
			}
			
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {
				
			}
			
			@Override
			public void afterTextChanged(Editable s) {
				if(hasValue){
					if (!edTxtPinThree.equals("")) {
						edTxtPinThree.clearFocus();
						edTxtPinFour.requestFocus();
						ediTxtFocusedPosition = 3;
						edTxtPinFour.setCursorVisible(true);
						if(isConfirmPin) {
							sendToMainMenuCodeActivity();
						} else {
							checkPinCode();
						}
					}
				} else {
					edTxtPinThree.requestFocus();
					edTxtPinThree.setCursorVisible(true);
				}
			}
		});
		edTxtPinFour.addTextChangedListener(new TextWatcher() {
			
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				if(edTxtPinFour.getText().toString().length() == 0){
					hasValue = false;
				} else{
					hasValue = true;
				}
			}
			
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {
				
			}
			
			@Override
			public void afterTextChanged(Editable s) {
				if(hasValue){
					edTxtPinFour.requestFocus();
					edTxtPinFour.setCursorVisible(true);
					if(isConfirmPin) {
						sendToMainMenuCodeActivity();
					} else {
						checkPinCode();
					}
				} else {
//					edTxtPinFour.requestFocus();
//					edTxtPinFour.setCursorVisible(true);
				}
				
			}
		});
	}

	private void loadData() {
		txtViewPinTitle.setText("Please confirm your PIN");
		
		if(getIntent().getExtras() != null) {
			choosePin = getIntent().getExtras().getString("pin_code");
			isConfirmPin = getIntent().getExtras().getBoolean("is_confirm_pin");
		}
	}
	
	public void sendToMainMenuCodeActivity() {
		if(edTxtPinOne.getText().toString().length() > 0 && edTxtPinTwo.getText().toString().length() > 0 && edTxtPinThree.getText().toString().length() > 0 && edTxtPinFour.getText().toString().length() > 0) {
			final String strPin = edTxtPinOne.getText().toString().trim() + edTxtPinTwo.getText().toString().trim() + edTxtPinThree.getText().toString().trim() + edTxtPinFour.getText().toString().trim();
			if(strPin.equals(choosePin)) {
				if(InternetConnectivity.isConnectedToInternet(UserEnterPinConfirmActivity.this)) {
					new SetPinAsyncTask(UserEnterPinConfirmActivity.this, new OnRequestComplete() {
						
						@Override
						public void onRequestComplete(String result) {
							try {
								if ("2001".equals(result)) {	
									finish();
									startActivity(new Intent(UserEnterPinConfirmActivity.this, MainMenuActivity.class));
									
	//							} else if ("2001".equals(result)) {
	//								Toast.makeText(UserEnterPinConfirmActivity.this, "Verification error . Please try again !", Toast.LENGTH_SHORT).show();
	//								
								} else {
									Toast.makeText(UserEnterPinConfirmActivity.this, "Invalid Entry.. please re-enter your PIN", Toast.LENGTH_SHORT).show();
								}
							} catch (Exception e) {
								Toast.makeText(UserEnterPinConfirmActivity.this, "Response Error: (" + e.getMessage() + ").  Please try again", Toast.LENGTH_SHORT).show();
							}
						}
					}).execute(ConstantValues.FUNC_ID_SET_PIN, PassengerApp.getInstance().getPassengerId(), strPin);
				} else {
					Toast.makeText(UserEnterPinConfirmActivity.this, ConstantValues.INTERNET_CONNECTION_FAILURE_MSG, Toast.LENGTH_SHORT).show();
				}
			} else {
				edTxtPinOne.setText("");
				edTxtPinTwo.setText("");
				edTxtPinThree.setText("");
				edTxtPinFour.setText("");
				edTxtPinOne.requestFocus();
				edTxtPinOne.setCursorVisible(true);
				
				Toast.makeText(UserEnterPinConfirmActivity.this, "Entry is invalid . Please try again !", Toast.LENGTH_SHORT).show();
				finish();
				startActivity(new Intent(UserEnterPinConfirmActivity.this, UserEnterPinActivity.class));
			}
			
		}
	}
	
	public void checkPinCode() {
		
		if(edTxtPinOne.getText().toString().length() > 0 && edTxtPinTwo.getText().toString().length() > 0 && edTxtPinThree.getText().toString().length() > 0 && edTxtPinFour.getText().toString().length() > 0) {
			final String pinString = edTxtPinOne.getText().toString().trim() + edTxtPinTwo.getText().toString().trim() + edTxtPinThree.getText().toString().trim() + edTxtPinFour.getText().toString().trim();
			if (changeState == 1) {
				new LoginWithPinAsyncTask(UserEnterPinConfirmActivity.this, new OnRequestComplete() {
					
					@Override
					public void onRequestComplete(String result) {
						if("2001".equals(result)) {
							changeState = 2;
							txtViewPinTitle.setText("Enter new PIN code");
							edTxtPinOne.setText("");
							edTxtPinTwo.setText("");
							edTxtPinThree.setText("");
							edTxtPinFour.setText("");
							edTxtPinOne.requestFocus();
							edTxtPinOne.setCursorVisible(true);
							
						} else {
							edTxtPinOne.setText("");
							edTxtPinTwo.setText("");
							edTxtPinThree.setText("");
							edTxtPinFour.setText("");
							Toast.makeText(UserEnterPinConfirmActivity.this, "Entry is invalid . Please try again !", Toast.LENGTH_SHORT).show();
						}
					}
				}).execute(ConstantValues.FUNC_ID_LOGIN_WITH_PIN, PassengerApp.getInstance().getPassengerId(), pinString, "2", getDeviceToken(), "", "");
				
			} else if (changeState == 2) {
				changeState = 3;
				newPinCode = pinString;
				txtViewPinTitle.setText("Confirm your PIN code");
				edTxtPinOne.setText("");
				edTxtPinTwo.setText("");
				edTxtPinThree.setText("");
				edTxtPinFour.setText("");
				edTxtPinOne.requestFocus();
				edTxtPinOne.setCursorVisible(true);
				Toast.makeText(UserEnterPinConfirmActivity.this, "Confirm", Toast.LENGTH_SHORT).show();
				 
			} else if (changeState == 3) {
				 if (newPinCode.equals(pinString)) {
					 new SetPinAsyncTask(UserEnterPinConfirmActivity.this, new OnRequestComplete() {
						
						@Override
						public void onRequestComplete(String result) {
							if("2001".equals(result)) {
								newPinCode = "";
								changeState = 1;
								Toast.makeText(UserEnterPinConfirmActivity.this, "Success!", Toast.LENGTH_SHORT).show();
								finish();
							} else {
								Toast.makeText(UserEnterPinConfirmActivity.this, "Entry is invalid . Please try again !", Toast.LENGTH_SHORT).show();
							}
						}
					}).execute(ConstantValues.FUNC_ID_SET_PIN, PassengerApp.getInstance().getPassengerId(), newPinCode);
				 } else {
					changeState = 2;
					txtViewPinTitle.setText("Enter new PIN code");
					edTxtPinOne.setText("");
					edTxtPinTwo.setText("");
					edTxtPinThree.setText("");
					edTxtPinFour.setText("");
					edTxtPinOne.requestFocus();
					edTxtPinOne.setCursorVisible(true);
					Toast.makeText(UserEnterPinConfirmActivity.this, "Wrong!", Toast.LENGTH_SHORT).show();
				 }
				
			}
			
		}
		
	}
	
	private String getDeviceToken() {
		return Secure.getString(UserEnterPinConfirmActivity.this.getContentResolver(), Secure.ANDROID_ID);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.relative_main:
		//	PassengerApp.getInstance().hideKeyboard(UserEnterPinConfirmActivity.this, v);
			break;
			
		default:
			break;
		}
		
	}
	
	@Override
	public void onBackPressed() {
		if(ediTxtFocusedPosition == 0){
			edTxtPinOne.clearFocus();
			finish();
			//super.onBackPressed();
		} else if(ediTxtFocusedPosition == 1){
			ediTxtFocusedPosition = 0;
			edTxtPinTwo.clearFocus();
			edTxtPinOne.requestFocus();
			edTxtPinOne.setCursorVisible(true);
		}else if(ediTxtFocusedPosition == 2){
			ediTxtFocusedPosition = 1;
			edTxtPinThree.clearFocus();
			edTxtPinTwo.requestFocus();
			edTxtPinTwo.setCursorVisible(true);
		}else if(ediTxtFocusedPosition == 3){
			ediTxtFocusedPosition = 2;
			edTxtPinFour.clearFocus();
			edTxtPinThree.requestFocus();
			edTxtPinThree.setCursorVisible(true);
		}
		//super.onBackPressed();
	}

}
