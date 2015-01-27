package com.netcabs.passenger;

import java.util.Calendar;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.picker.DatePicker;
import android.picker.DatePicker.DateWatcher;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.netcabs.adapter.CountrySpinnerAdapter;
import com.netcabs.asynctask.CardEditAsyncTask;
import com.netcabs.customview.DialogController;
import com.netcabs.interfacecallback.OnRequestComplete;
import com.netcabs.passengerinfo.PassengerApp;
import com.netcabs.utils.ConstantValues;

public class PaymentCardEditActivity extends Activity implements OnClickListener, OnItemSelectedListener, DateWatcher {

	private Button btnBack;
	private Button btnSave;
	
	private EditText edTxtCardNumber;
	private TextView edTxtExp;
	private EditText edTxtCvv;
	private EditText edTxtPersonal;
	private EditText edTxtZipCode;
	
	private TextView txtViewCountryName;
	private ImageView imgViewCountryFlag;
	private LinearLayout linearCountry;
	private Spinner spinnerCountry;
	
	private ImageView imgViewCard;
	private RelativeLayout relativeLayout;
	private int position;
	private int flagIndex = 0;
	private boolean isLock = false;
	private String pickerDate = null;
	private int selectedYear = 0;
	private boolean firstViewDatePicker = true;
	
	private Dialog dialogDatePicker;
	private DatePicker d;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_payment_card_edit);
		
		initViews();
		setListener();
		loadData();
	}

	private void setListener() {
		btnBack.setOnClickListener(this);
		btnSave.setOnClickListener(this);
		linearCountry.setOnClickListener(this);
		relativeLayout.setOnClickListener(this);
		spinnerCountry.setOnItemSelectedListener(this);
		edTxtExp.setOnClickListener(this);
	}

	private void initViews() {
		btnBack = (Button) findViewById(R.id.btn_back);
		btnSave = (Button) findViewById(R.id.btn_save);
		relativeLayout = (RelativeLayout) findViewById(R.id.relative_main);
		edTxtCardNumber = (EditText) findViewById(R.id.ed_txt_card_number);
		edTxtExp = (TextView) findViewById(R.id.ed_txt_exp);
		edTxtCvv = (EditText) findViewById(R.id.ed_txt_cvv);
		edTxtPersonal = (EditText) findViewById(R.id.ed_txt_card_name);
		edTxtZipCode = (EditText) findViewById(R.id.ed_txt_zip_code);
		imgViewCard = (ImageView) findViewById(R.id.img_view_card);
		
		txtViewCountryName = (TextView) findViewById(R.id.txt_view_country);
		imgViewCountryFlag = (ImageView) findViewById(R.id.img_view_flag);
		linearCountry = (LinearLayout)findViewById(R.id.linear_layout_country);
		spinnerCountry = (Spinner) findViewById(R.id.spinner_country);
	}

	private void loadData() {
		if(getIntent().getExtras() != null) {
			position = getIntent().getExtras().getInt("position");
		}
		
		if (PassengerApp.getInstance().getCreditCardInfoList() == null) {
			return;
		}
		
		edTxtCardNumber.setText("**** **** **** "+PassengerApp.getInstance().getCreditCardInfoList().get(position).getCardNumber().toString().subSequence(PassengerApp.getInstance().getCreditCardInfoList().get(position).getCardNumber().toString().length() - 4, PassengerApp.getInstance().getCreditCardInfoList().get(position).getCardNumber().toString().length()));
		edTxtExp.setText(PassengerApp.getInstance().getCreditCardInfoList().get(position).getExpireDate().toString());
		edTxtCvv.setText(Integer.toString(PassengerApp.getInstance().getCreditCardInfoList().get(position).getCvv()));
		edTxtPersonal.setText(PassengerApp.getInstance().getCreditCardInfoList().get(position).getCardHolderName());
		edTxtZipCode.setText(PassengerApp.getInstance().getCreditCardInfoList().get(position).getZip());
		
		Log.e("Card type is", "----"+PassengerApp.getInstance().getCreditCardInfoList().get(position).getCareType());
		if (PassengerApp.getInstance().getCreditCardInfoList().get(position).getCareType().equalsIgnoreCase("visa")) {
			imgViewCard.setBackgroundResource(R.drawable.visa);

		} else if (PassengerApp.getInstance().getCreditCardInfoList().get(position).getCareType().equalsIgnoreCase("master")) {
			imgViewCard.setBackgroundResource(R.drawable.master);

		} else if (PassengerApp.getInstance().getCreditCardInfoList().get(position).getCareType().equalsIgnoreCase("americanexpress")) {
			imgViewCard.setBackgroundResource(R.drawable.american);

		} else if (PassengerApp.getInstance().getCreditCardInfoList().get(position).getCareType().equalsIgnoreCase("discover")) {
			imgViewCard.setBackgroundResource(R.drawable.discover);
		}
		
		for(int i = 0; i < PassengerApp.getInstance().getCountryArray().size(); i++) {
			if(PassengerApp.getInstance().getCountryArray().get(i).getId().equals(PassengerApp.getInstance().getCreditCardInfoList().get(position).getCountryId())) {
				flagIndex = i;
			}
		}
		
		imgViewCountryFlag.setImageBitmap(PassengerApp.getInstance().getCountryArray().get(flagIndex).getImgBitmap());
		if(PassengerApp.getInstance().getCountryArray() != null) {
			CountrySpinnerAdapter countryAdapter = new CountrySpinnerAdapter(PaymentCardEditActivity.this, PassengerApp.getInstance().getCountryArray());
			spinnerCountry.setAdapter(countryAdapter);
		}
		
		spinnerCountry.setSelection(flagIndex);
		
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_back:
			onBackPressed();
			break;
			
		case R.id.btn_save:
			if(!edTxtExp.getText().toString().trim().equalsIgnoreCase("") && !edTxtCvv.getText().toString().trim().equalsIgnoreCase("") && !edTxtPersonal.getText().toString().trim().equalsIgnoreCase("") && !edTxtZipCode.getText().toString().trim().equalsIgnoreCase("")) {
				if(!edTxtExp.getText().toString().trim().equals(PassengerApp.getInstance().getCreditCardInfoList().get(position).getExpireDate().toString())
					|| !edTxtCvv.getText().toString().trim().equals(Integer.toString(PassengerApp.getInstance().getCreditCardInfoList().get(position).getCvv()))
					|| !edTxtPersonal.getText().toString().trim().equals(PassengerApp.getInstance().getCreditCardInfoList().get(position).getCardHolderName().toString()) || !txtViewCountryName.getText().toString().equalsIgnoreCase(PassengerApp.getInstance().getCreditCardInfoList().get(position).getCountryName()) || !edTxtZipCode.getText().toString().equalsIgnoreCase(PassengerApp.getInstance().getCreditCardInfoList().get(position).getZip())) {
					new CardEditAsyncTask(PaymentCardEditActivity.this, new OnRequestComplete() {
						
						@Override
						public void onRequestComplete(String result) {
							try {
								if("2001".equals(result)) {
									PassengerApp.getInstance().getCreditCardInfoList().get(position).setCardHolderName(edTxtPersonal.getText().toString().trim());
									PassengerApp.getInstance().getCreditCardInfoList().get(position).setCvv(Integer.parseInt(edTxtCvv.getText().toString().trim()));
									PassengerApp.getInstance().getCreditCardInfoList().get(position).setExpireDate(edTxtExp.getText().toString().trim());
									PassengerApp.getInstance().getCreditCardInfoList().get(position).setCountryName(txtViewCountryName.getText().toString().trim());
									PassengerApp.getInstance().getCreditCardInfoList().get(position).setCountryId(PassengerApp.getInstance().getCountryArray().get(flagIndex).getId());
									PassengerApp.getInstance().getCreditCardInfoList().get(position).setZip(edTxtZipCode.getText().toString().trim());
									PassengerApp.getInstance().getCreditCardInfoList().get(position).setCardHolderName(edTxtPersonal.getText().toString().trim());
									
									Toast.makeText(PaymentCardEditActivity.this, "Card Successfully updated", Toast.LENGTH_SHORT).show();
								} else {
									Toast.makeText(PaymentCardEditActivity.this, "Entry error ! Please try again", Toast.LENGTH_SHORT).show();
								}
							} catch (Exception e) {
								Toast.makeText(PaymentCardEditActivity.this, "Response error: (" + e.getMessage() + "). Please try again" , Toast.LENGTH_SHORT).show();
							}
							
						}
					}).execute(ConstantValues.FUNC_ID_CARD_EDIT, PassengerApp.getInstance().getPassengerId(), PassengerApp.getInstance().getCreditCardInfoList().get(position).getPaymentId(), edTxtCvv.getText().toString().trim(), PassengerApp.getInstance().getCountryArray().get(flagIndex).getId(), edTxtZipCode.getText().toString().trim(), edTxtExp.getText().toString().trim(), edTxtPersonal.getText().toString().trim());
				} else {
					Toast.makeText(PaymentCardEditActivity.this, "There are no update", Toast.LENGTH_SHORT).show();
				}
			} else {
				if(edTxtExp.getText().toString().trim().equalsIgnoreCase("")) {
					edTxtExp.setError("Required");
				} 
				
				if(edTxtCvv.getText().toString().trim().equalsIgnoreCase("")) {
					edTxtCvv.setError("Required");
				}
				
				if(edTxtPersonal.getText().toString().trim().equalsIgnoreCase("")) {
					edTxtPersonal.setError("Required");
				}
				
				if(edTxtZipCode.getText().toString().trim().equalsIgnoreCase("")) {
					edTxtZipCode.setError("Required");
				}
			}
			break;
			
		case R.id.linear_layout_country:
			PassengerApp.getInstance().hideKeyboard(PaymentCardEditActivity.this, v);
			spinnerCountry.setSelection(flagIndex);
			spinnerCountry.performClick();
			break;
			
		case R.id.relative_main:
			PassengerApp.getInstance().hideKeyboard(PaymentCardEditActivity.this, v);
			break;
			
		case R.id.ed_txt_exp:
			datePicker();
			break;

		default:
			break;
		}
	}
	
	private void datePicker() {
		// TODO Auto-generated method stub
		dialogDatePicker = new DialogController(PaymentCardEditActivity.this).DialogDatePicker();
		d = (DatePicker) dialogDatePicker.findViewById(R.id.datePicker1);
		d.setDateChangedListener(PaymentCardEditActivity.this);

		try {
			Calendar c = Calendar.getInstance(); 
			int year = c.get(Calendar.YEAR);
			Log.e("CURRENT YEAR EDIT", "====" +c.get(Calendar.YEAR));
			d.setStartYear(c.get(Calendar.YEAR));
			d.setEndYear(2090);
		} catch (Exception e) {
			Log.e("", e.toString());
		}
		
		Button btnYes = (Button) dialogDatePicker.findViewById(R.id.btn_yes);
		
		btnYes.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Calendar c = Calendar.getInstance(); 
				int year = c.get(Calendar.YEAR);
				if (selectedYear < year) {
					Toast.makeText(PaymentCardEditActivity.this, "Expired year can not be less than current year" + year, Toast.LENGTH_SHORT).show();
				} else {
					dialogDatePicker.dismiss();
					edTxtExp.setText(pickerDate);
				}
				
			}
		});
		
		dialogDatePicker.show();
	}

	@Override
	public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		imgViewCountryFlag.setImageBitmap(PassengerApp.getInstance().getCountryArray().get(arg2).getImgBitmap());
		flagIndex = arg2;
		txtViewCountryName.setText(PassengerApp.getInstance().getCountryArray().get(arg2).getName());
	}

	@Override
	public void onNothingSelected(AdapterView<?> arg0) {
		
	}
	
	@Override
	protected void onResume() {
		if (isLock) {
			Intent intent = new Intent(PaymentCardEditActivity.this, LoginWithPinAuthActivity.class);
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
	
	@Override
	protected void onDestroy() {
		isLock = false;
		super.onDestroy();
	}

	@Override
	public void onDateChanged(Calendar c) {
		// TODO Auto-generated method stub
		Log.e("PaymentEdit", "" + c.get(Calendar.MONTH) + " " + c.get(Calendar.DAY_OF_MONTH) + " " + c.get(Calendar.YEAR));
		//edTxtExp.setText("" = c.get(Calendar.MONTH) + "/" + c.get(Calendar.YEAR));
//		try {
//			d.setEndYear(2090);
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
	    selectedYear = c.get(Calendar.YEAR);
	    
		if ((c.get(Calendar.MONTH) + 1) < 10) {
			pickerDate = "0" + (c.get(Calendar.MONTH) + 1) + "/" + c.get(Calendar.YEAR);
		} else {
			pickerDate = "" + (c.get(Calendar.MONTH) + 1) + "/" + c.get(Calendar.YEAR);
		}
		
	}

}
