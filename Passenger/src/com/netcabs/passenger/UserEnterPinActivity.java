package com.netcabs.passenger;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnKeyListener;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;

import com.netcabs.customview.CustomEditText;
import com.netcabs.utils.BaseActivity;

public class UserEnterPinActivity extends Activity implements OnClickListener {

	private TextView txtViewPinTitle;
	private CustomEditText edTxtPinOne;
	private CustomEditText edTxtPinTwo;
	private CustomEditText edTxtPinThree;
	private CustomEditText edTxtPinFour;
	private RelativeLayout relativeLayout;
	private int ediTxtFocusedPosition = 0;
	private boolean hasValue = false;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_user_enter_pin);
		BaseActivity.authLock = false;
		
		initViews();
		setListener();
		loadData();
	}

	private void loadData() {
		txtViewPinTitle.setText("For faster access to the app, please choose a 4-digit PIN. You will use this PIN whenever you wish to access the app");
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
					edTxtPinOne.clearFocus();
					edTxtPinTwo.requestFocus();
					ediTxtFocusedPosition = 1;
					edTxtPinTwo.setCursorVisible(true);
					sendToConfirmPinCodeActivity();;
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
					sendToConfirmPinCodeActivity();
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
					sendToConfirmPinCodeActivity();
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
					sendToConfirmPinCodeActivity();
				} else {
//					edTxtPinFour.requestFocus();
//					edTxtPinFour.setCursorVisible(true);
				}
				
			}
		});
		
//		edTxtPinOne.addTextChangedListener(new TextWatcher() {
//			
//			@Override
//			public void onTextChanged(CharSequence s, int start, int before, int count) {
//				edTxtPinOne.clearFocus();
//				edTxtPinTwo.requestFocus();
//				edTxtPinTwo.setCursorVisible(true);
//				ediTxtFocusedPosition = 1;
//				sendToConfirmPinCodeActivity();
//			}
//			
//			@Override
//			public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//				
//			}
//			
//			@Override
//			public void afterTextChanged(Editable s) {
//				
//			}
//		});
//		edTxtPinTwo.addTextChangedListener(new TextWatcher() {
//			
//			@Override
//			public void onTextChanged(CharSequence s, int start, int before, int count) {
//				edTxtPinTwo.clearFocus();
//				edTxtPinThree.requestFocus();
//				edTxtPinThree.setCursorVisible(true);
//				ediTxtFocusedPosition = 2;
//				sendToConfirmPinCodeActivity();
//			}
//			
//			@Override
//			public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//				
//			}
//			
//			@Override
//			public void afterTextChanged(Editable s) {
//				
//			}
//		});
//		edTxtPinThree.addTextChangedListener(new TextWatcher() {
//			
//			@Override
//			public void onTextChanged(CharSequence s, int start, int before, int count) {
//				edTxtPinThree.clearFocus();
//				edTxtPinFour.requestFocus();
//				edTxtPinFour.setCursorVisible(true);
//				ediTxtFocusedPosition = 3;
//				sendToConfirmPinCodeActivity();
//			}
//			
//			@Override
//			public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//				
//			}
//			
//			@Override
//			public void afterTextChanged(Editable s) {
//				
//			}
//		});
//		edTxtPinFour.addTextChangedListener(new TextWatcher() {
//			
//			@Override
//			public void onTextChanged(CharSequence s, int start, int before, int count) {
//				edTxtPinFour.clearFocus();
//				sendToConfirmPinCodeActivity();
//			}
//			
//			@Override
//			public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//				
//			}
//			
//			@Override
//			public void afterTextChanged(Editable s) {
//				
//			}
//		});
	}
	
	public void sendToConfirmPinCodeActivity() {
		if(edTxtPinOne.getText().toString().length() > 0 && edTxtPinTwo.getText().toString().length() > 0 && edTxtPinThree.getText().toString().length() > 0 && edTxtPinFour.getText().toString().length() > 0) {
			String strPin = edTxtPinOne.getText().toString().trim() + edTxtPinTwo.getText().toString().trim() + edTxtPinThree.getText().toString().trim() + edTxtPinFour.getText().toString().trim();
			finish();
			startActivity(new Intent(UserEnterPinActivity.this, UserEnterPinConfirmActivity.class).putExtra("pin_code", strPin).putExtra("is_confirm_pin", true));
		}
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.relative_main:
			//PassengerApp.getInstance().hideKeyboard(UserEnterPinActivity.this, v);
			break;
			
		default:
			break;
		}
		
	}
	@Override
	public void onBackPressed() {
		if(ediTxtFocusedPosition == 0){
			edTxtPinOne.clearFocus();
			//finish();
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
