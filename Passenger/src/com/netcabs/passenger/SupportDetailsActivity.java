package com.netcabs.passenger;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.netcabs.asynctask.ContactEmailAsyncTask;
import com.netcabs.interfacecallback.OnRequestComplete;
import com.netcabs.internetconnection.InternetConnectivity;
import com.netcabs.passengerinfo.PassengerApp;
import com.netcabs.utils.ConstantValues;

public class SupportDetailsActivity extends Activity implements OnClickListener {

	private Button btnBack;
	private Button btnSend;
	private EditText edTxtSubject;
	private EditText edTxtMessage;
	private RelativeLayout relativeLayout;
	public boolean isLock = false;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_support_details);
		
		initViews();
		setListerner();
		loadData();
	}

	private void initViews() {
		btnBack = (Button) findViewById(R.id.btn_back);
		btnSend = (Button) findViewById(R.id.btn_send);
		relativeLayout = (RelativeLayout) findViewById(R.id.relative_main);
		edTxtSubject = (EditText) findViewById(R.id.ed_txt_subject);
		edTxtMessage = (EditText) findViewById(R.id.ed_txt_msg);
	}

	private void setListerner() {
		btnBack.setOnClickListener(this);
		btnSend.setOnClickListener(this);
		relativeLayout.setOnClickListener(this);
	}

	private void loadData() {
		
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.relative_main:
			PassengerApp.getInstance().hideKeyboard(SupportDetailsActivity.this, v);
			break;
			
		case R.id.btn_back:
			onBackPressed();
			break;
			
		case R.id.btn_send:
			if(!edTxtSubject.getText().toString().trim().equalsIgnoreCase("") && !edTxtMessage.getText().toString().trim().equalsIgnoreCase("")) {
				if(InternetConnectivity.isConnectedToInternet(SupportDetailsActivity.this)) {
					new ContactEmailAsyncTask(SupportDetailsActivity.this, new OnRequestComplete() {
						
						@Override
						public void onRequestComplete(String result) {
							try {  
								if("2001".equals(result)) {
									//startActivity(new Intent(getActivity(), HailedCabDetailsActivity.class).putExtra("reg_no", edTxtTaxiRegNo.getText().toString().trim()));
									Toast.makeText(SupportDetailsActivity.this,"Email is sent to 13LetzGo", Toast.LENGTH_SHORT).show();
									
								} else {
									
								}
							} catch (Exception e) {
								Toast.makeText(SupportDetailsActivity.this, "Response Error: (" + e.getMessage() + ").  Please try again", Toast.LENGTH_SHORT).show();
							}
						}
					}).execute(ConstantValues.FUNC_ID_CONTACT_EMAIL, PassengerApp.getInstance().getPassengerId(), edTxtSubject.getText().toString().trim(), edTxtMessage.getText().toString().trim());
				}else {
					Toast.makeText(SupportDetailsActivity.this, ConstantValues.INTERNET_CONNECTION_FAILURE_MSG, Toast.LENGTH_SHORT).show();
				}
			} else {
				if(edTxtSubject.getText().toString().trim().equalsIgnoreCase("")) {
					edTxtSubject.setError("Required");
				}
				if(edTxtMessage.getText().toString().trim().equalsIgnoreCase("")) {
					edTxtMessage.setError("Required");
				}
			}
			break;

		default:
			break;
		}
	}
	
	@Override
	protected void onResume() {
		if (isLock) {
			Intent intent = new Intent(SupportDetailsActivity.this, LoginWithPinAuthActivity.class);
			intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivity(intent);
		}
		
		super.onResume();
	}
	
	@Override
	protected void onPause() {
		if (isLock) {
			isLock = false;
		} else {
			isLock = true;
		}
		
		super.onPause();
	}

}
