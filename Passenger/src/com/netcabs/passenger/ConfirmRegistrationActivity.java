package com.netcabs.passenger;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnKeyListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.TextView.OnEditorActionListener;

import com.netcabs.asynctask.PhoneVerifyAsyncTask;
import com.netcabs.asynctask.ResendActivationCodeAsyncTask;
import com.netcabs.customview.CustomEditText;
import com.netcabs.interfacecallback.OnRequestComplete;
import com.netcabs.internetconnection.InternetConnectivity;
import com.netcabs.passengerinfo.PassengerApp;
import com.netcabs.utils.BaseActivity;
import com.netcabs.utils.ConstantValues;

public class ConfirmRegistrationActivity extends Activity implements OnClickListener {

	private TextView txtViewMobileNo;
	private TextView txtViewLostPin;
	private CustomEditText edTxtPinOne;
	private CustomEditText edTxtPinTwo;
	private CustomEditText edTxtPinThree;
	private CustomEditText edTxtPinFour;
	private Dialog dialogLostPin;
	private int ediTxtFocusedPosition = 0;
	private RelativeLayout relativeLayout;
	private boolean hasValue = false;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
		imm.toggleSoftInput(InputMethodManager.SHOW_FORCED,
		InputMethodManager.HIDE_IMPLICIT_ONLY);
		
		setContentView(R.layout.activity_confirm_registration);
		BaseActivity.authLock = true;
		
		initViews();
		setListener();
		loadData();
	}

	private void initViews() {
		relativeLayout = (RelativeLayout) findViewById(R.id.relative_main);
		txtViewMobileNo = (TextView) findViewById(R.id.txt_view_mobile_no);
		txtViewLostPin = (TextView) findViewById(R.id.txt_view_lost_pin);
		edTxtPinOne = (CustomEditText) findViewById(R.id.ed_txt_pin_one);
		edTxtPinTwo = (CustomEditText) findViewById(R.id.ed_txt_pin_two);
		edTxtPinThree = (CustomEditText) findViewById(R.id.ed_txt_pin_three);
		edTxtPinFour = (CustomEditText) findViewById(R.id.ed_txt_pin_four);
	}

	private void setListener() {
		txtViewLostPin.setOnClickListener(this);
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
			public boolean onEditorAction(TextView v, int actionId,
					KeyEvent event) {
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
					edTxtPinOne.clearFocus();
					edTxtPinTwo.requestFocus();
					ediTxtFocusedPosition = 1;
					edTxtPinTwo.setCursorVisible(true);
					sendToChoosePinCodeActivity();
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
					edTxtPinTwo.clearFocus();
					edTxtPinThree.requestFocus();
					edTxtPinThree.setCursorVisible(true);
					ediTxtFocusedPosition = 2;
					sendToChoosePinCodeActivity();
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
					edTxtPinThree.clearFocus();
					edTxtPinFour.requestFocus();
					edTxtPinFour.setCursorVisible(true);
					ediTxtFocusedPosition = 3;
					sendToChoosePinCodeActivity();
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
					sendToChoosePinCodeActivity();
				} else {
//					edTxtPinFour.requestFocus();
//					edTxtPinFour.setCursorVisible(true);
				}
				
			}
		});
		
	}

	private void loadData() {
		if (PassengerApp.getInstance().getProfileDetailsInfo().getMobileNo() != null) {
			txtViewMobileNo.setText("sent to "+PassengerApp.getInstance().getProfileDetailsInfo().getMobileNo().toString());
		} else {
			txtViewMobileNo.setText("Mobile no not found");
		}
		
	}
	
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.txt_view_lost_pin:
			if(InternetConnectivity.isConnectedToInternet(ConfirmRegistrationActivity.this)) {
				new ResendActivationCodeAsyncTask(ConfirmRegistrationActivity.this, new OnRequestComplete() {
					
					@Override
					public void onRequestComplete(String result) {
						try {
							if("2001".equals(result)) {
								Toast.makeText(ConfirmRegistrationActivity.this, "Please check your mobile inbox !", Toast.LENGTH_SHORT).show();
							}else if ("5001".equals(result)){
								Toast.makeText(ConfirmRegistrationActivity.this, "Invalid Phone Number !", Toast.LENGTH_SHORT).show();
							}else {
								Toast.makeText(ConfirmRegistrationActivity.this, "Please try again !", Toast.LENGTH_SHORT).show();
							}
						} catch (Exception e) {
							Toast.makeText(ConfirmRegistrationActivity.this, "Response Error: (" + e.getMessage() + ").  Please try again", Toast.LENGTH_SHORT).show();
						}
					}
				}).execute(ConstantValues.FUNC_ID_RESEND_CODE, PassengerApp.getInstance().getPassengerId());
			}else{
				Toast.makeText(ConfirmRegistrationActivity.this, ConstantValues.INTERNET_CONNECTION_FAILURE_MSG, Toast.LENGTH_SHORT).show();
			}
			break;
		case R.id.relative_main:
			PassengerApp.getInstance().hideKeyboard(ConfirmRegistrationActivity.this, v);
			break;

		default:
			break;
		}
	}

//	private void processLostPIN() {
//		dialogLostPin = new DialogController(ConfirmRegistrationActivity.this).LostPinDialog();
//		
//		Button btnCancel = (Button) dialogLostPin.findViewById(R.id.btn_cancel);
//		btnCancel.setOnClickListener(new OnClickListener() {
//			
//			@Override
//			public void onClick(View v) {
//				dialogLostPin.dismiss();
//			}
//		});
//		
//		final EditText edTxtMobileNumber = (EditText) dialogLostPin.findViewById(R.id.ed_txt_phone_number);
//		
//		Button btnSubmit = (Button) dialogLostPin.findViewById(R.id.btn_submit);
//		btnSubmit.setOnClickListener(new OnClickListener() {
//			
//			@Override
//			public void onClick(View v) {
//				if(!edTxtMobileNumber.getText().toString().trim().equalsIgnoreCase("")) {
//					if(InternetConnectivity.isConnectedToInternet(ConfirmRegistrationActivity.this)) {
//						new ResendCodeAsyncTask(ConfirmRegistrationActivity.this, new OnRequestComplete() {
//							
//							@Override
//							public void onRequestComplete(String result) {
//								if("2001".equals(result)) {
//									Toast.makeText(ConfirmRegistrationActivity.this, "Please check your mobile inbox !", Toast.LENGTH_SHORT).show();
//									dialogLostPin.dismiss();
//								} else {
//									Toast.makeText(ConfirmRegistrationActivity.this, "Please try again !", Toast.LENGTH_SHORT).show();
//								}
//							}
//						}).execute(ConstantValues.FUNC_ID_RESEND_CODE, PassengerApp.getInstance().getPassengerId());
//					}else{
//						
//					}
//				} else {
//					edTxtMobileNumber.setError("Required");
//				}
//			}
//		});
//		dialogLostPin.show();
//	}
	
	public void sendToChoosePinCodeActivity() {
		if(edTxtPinOne.getText().toString().length() > 0 && edTxtPinTwo.getText().toString().length() > 0 && edTxtPinThree.getText().toString().length() > 0 && edTxtPinFour.getText().toString().length() > 0) {
			String strPin = edTxtPinOne.getText().toString().trim() + edTxtPinTwo.getText().toString().trim() + edTxtPinThree.getText().toString().trim() + edTxtPinFour.getText().toString().trim();
			if(InternetConnectivity.isConnectedToInternet(ConfirmRegistrationActivity.this)) {
			new PhoneVerifyAsyncTask(ConfirmRegistrationActivity.this, new OnRequestComplete() {
				
				@Override
				public void onRequestComplete(String result) {
					try {
						if("2001".equals(result)) {
							finish();
							BaseActivity.authLock = false;
							startActivity(new Intent(ConfirmRegistrationActivity.this, UserEnterPinActivity.class));
						} else {
							edTxtPinOne.setText("");
							edTxtPinTwo.setText("");
							edTxtPinThree.setText("");
							edTxtPinFour.setText("");
							Toast.makeText(ConfirmRegistrationActivity.this, "Verify code not matched ! Please try again or send again !", Toast.LENGTH_SHORT).show();
						}
					} catch (Exception e) {
							Toast.makeText(ConfirmRegistrationActivity.this, "Response Error: (" + e.getMessage() + ").  Please try again", Toast.LENGTH_SHORT).show();
						}
				}
				
			}).execute(ConstantValues.FUNC_ID_PHONE_VERIFY, PassengerApp.getInstance().getPassengerId(), strPin);
		} else {
			Toast.makeText(ConfirmRegistrationActivity.this, ConstantValues.INTERNET_CONNECTION_FAILURE_MSG, Toast.LENGTH_SHORT).show();
		}
			
		}
	}
	
	@Override
	public void onBackPressed() {
		if(ediTxtFocusedPosition == 0){
			edTxtPinOne.clearFocus();
			finish();
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
