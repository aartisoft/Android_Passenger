                                                                                                                                                                                package com.netcabs.passenger;

import io.card.payment.CardIOActivity;
import io.card.payment.CreditCard;

import java.util.ArrayList;
import java.util.Calendar;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.picker.DatePicker;
import android.picker.DatePicker.DateWatcher;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
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
import com.netcabs.asynctask.CardRegistrationAsyncTask;
import com.netcabs.asynctask.ProfileDetailsAsyncTask;
import com.netcabs.customview.DialogController;
import com.netcabs.datamodel.CreditCardInfo;
import com.netcabs.interfacecallback.OnRequestComplete;
import com.netcabs.internetconnection.InternetConnectivity;
import com.netcabs.passengerinfo.PassengerApp;
import com.netcabs.utils.BaseActivity;
import com.netcabs.utils.CardValidationChecking;
import com.netcabs.utils.ConstantValues;
import com.paypal.android.sdk.payments.PayPalConfiguration;
import com.paypal.android.sdk.payments.PayPalService;

public class CardRegistrationActivity extends Activity implements OnClickListener, OnItemSelectedListener, OnFocusChangeListener, DateWatcher {

	private EditText edTxtCardNumber;
	private EditText edTxtCardName;
	private TextView edTxtExp;
	private EditText edTxtCvv;
	private EditText edTxtZipCode;
	private Button btnScan;
	private Button btnSave;
	private Button btnSkip;
	private Button btnBack;
	
	private ImageView imgViewCard;
	
	private TextView txtViewCountryName;
	private ImageView imgViewCountryFlag;
	private LinearLayout linearCountry;
	private Spinner spinnerCountry;
	private int flagIndex = 0;
	
	private String cardType;
	private String str;
    private int keyDel;
    private boolean isForwardActivity = false;
    private boolean isDeleting = false;
    private int sizeIs = 0;
    private boolean isLock = false;
    private RelativeLayout relativeLayout;
    private Dialog dialogDatePicker;
    private String pickerDate = null;
	private int selectedYear = 0;
	
	//credit card scan
	private static final String CONFIG_ENVIRONMENT = PayPalConfiguration.ENVIRONMENT_NO_NETWORK;
	private static final String CONFIG_CLIENT_ID = "credential from developer.paypal.com";
	private static final int MY_SCAN_REQUEST_CODE = 1;
	
	private static PayPalConfiguration config = new PayPalConfiguration()
	.environment(CONFIG_ENVIRONMENT)
	.clientId(CONFIG_CLIENT_ID)
	// The following are only used in PayPalFuturePaymentActivity.
	.merchantName("Hipster Store")
	.merchantPrivacyPolicyUri(Uri.parse("https://www.example.com/privacy"))
	.merchantUserAgreementUri(Uri.parse("https://www.example.com/legal"));
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_card_registration);
		
		Intent intent = new Intent(this, PayPalService.class);
		intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION, config);
		startService(intent);
		
		initViews();
		setListener();
		loadData();
	}

	private void initViews() {
		relativeLayout = (RelativeLayout) findViewById(R.id.relative_main);
		edTxtCardNumber = (EditText) findViewById(R.id.ed_txt_card_number);
		edTxtCardName = (EditText) findViewById(R.id.ed_txt_card_name);
		edTxtExp = (TextView) findViewById(R.id.ed_txt_exp);
		edTxtCvv = (EditText) findViewById(R.id.ed_txt_cvv);
		edTxtZipCode = (EditText) findViewById(R.id.ed_txt_zip_code);
		
		txtViewCountryName = (TextView) findViewById(R.id.txt_view_country);
		imgViewCountryFlag = (ImageView) findViewById(R.id.img_view_flag);
		linearCountry = (LinearLayout)findViewById(R.id.linear_layout_country);
		spinnerCountry = (Spinner) findViewById(R.id.spinner_country);
		imgViewCard = (ImageView) findViewById(R.id.img_view_card);
		
		btnSave = (Button) findViewById(R.id.btn_save);
		btnScan = (Button) findViewById(R.id.btn_scan);
		btnSkip = (Button) findViewById(R.id.btn_skip);
		btnBack = (Button) findViewById(R.id.btn_back);
		
		int defualtPosition = 0;
		if(PassengerApp.getInstance().getCountryArray() != null) {
			for (int i = 0; i < PassengerApp.getInstance().getCountryArray().size(); i++) {
				if (PassengerApp.getInstance().getCountryArray().get(i).getName().equalsIgnoreCase(ConstantValues.COUNTRY_NAME)) {
					defualtPosition = i;
				}
				
			}
			CountrySpinnerAdapter countryAdapter = new CountrySpinnerAdapter(CardRegistrationActivity.this, PassengerApp.getInstance().getCountryArray());
			spinnerCountry.setAdapter(countryAdapter);
			spinnerCountry.setSelection(defualtPosition);
		} 
		
		
	}

	private void setListener() {
		relativeLayout.setOnClickListener(this);
		btnSave.setOnClickListener(this);
		btnScan.setOnClickListener(this);
		btnSkip.setOnClickListener(this);
		btnBack.setOnClickListener(this);
		edTxtExp.setOnClickListener(this);
		
		linearCountry.setOnClickListener(this);
		spinnerCountry.setOnItemSelectedListener(this);
		edTxtCardNumber.setOnFocusChangeListener(this);
		
		edTxtCardNumber.addTextChangedListener(new TextWatcher() {
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				
			}
			
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {
				if(edTxtCardNumber.getText().length() == 5) {
					isDeleting = true;
				} else if(edTxtCardNumber.getText().length() == 10) {
					isDeleting = true;
				} else if(edTxtCardNumber.getText().length() == 15) {
					isDeleting = true;
				} else {
					isDeleting = false;
				}
				
			}
			
			@Override
			public void afterTextChanged(Editable s) {
				if(isDeleting) {
					
				} else {
					switch (edTxtCardNumber.getText().length()) {
					case 4:
						edTxtCardNumber.setText(edTxtCardNumber.getText() + " ");
						edTxtCardNumber.setSelection(edTxtCardNumber.getText().length());
						break;
					case 9:
						edTxtCardNumber.setText(edTxtCardNumber.getText() + " ");
						edTxtCardNumber.setSelection(edTxtCardNumber.getText().length());
						break;
						
					case 14:
						edTxtCardNumber.setText(edTxtCardNumber.getText() + " ");
						edTxtCardNumber.setSelection(edTxtCardNumber.getText().length());
						break;

					default:
						break;
					}
				}
			}
		});
		
		
		
		/*edTxtExp.addTextChangedListener(new TextWatcher() {
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				
			}
			
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {
				if(edTxtExp.getText().length() == 3) {
					isDeleting = true;
				} else {
					isDeleting = false;
				}
			}
			
			@Override
			public void afterTextChanged(Editable s) {
				if(isDeleting) {
				
				} else {
					if(edTxtExp.getText().length() == 2) {
						edTxtExp.setText(edTxtExp.getText() + "/");
						edTxtExp.setSelection(edTxtExp.getText().length());
					} else {
						
					}
				}
			}
		});*/
	}

	private void loadData() {
		if(getIntent().getExtras() != null) {
			if(getIntent().getExtras().getBoolean("is_skip")) {
				btnSkip.setVisibility(View.INVISIBLE);
				isForwardActivity = true;
			} else {
				btnSkip.setVisibility(View.VISIBLE);
				isForwardActivity = false;
			}
		}else{
			if(InternetConnectivity.isConnectedToInternet(CardRegistrationActivity.this)) {
				new ProfileDetailsAsyncTask(CardRegistrationActivity.this, new OnRequestComplete() {
					
					@Override
					public void onRequestComplete(String result) {
						try {
							if("2001".equals(result)) {
								Log.e("I am in else 2001", "");
		//						btnSkip.setVisibility(View.VISIBLE);
		//						isForwardActivity = false;
							} else if("3001".equals(result)) {
								
							} else if("4001".equals(result)) {
												
							} else {
								
							}
						} catch (Exception e) {
							Toast.makeText(CardRegistrationActivity.this, "Response Error: (" + e.getMessage() + ").  Please try again", Toast.LENGTH_SHORT).show();
						}
					}
				}).execute(ConstantValues.FUNC_ID_PROFILE_DETAILS, PassengerApp.getInstance().getPassengerId());
			} else{
				Toast.makeText(CardRegistrationActivity.this, ConstantValues.INTERNET_CONNECTION_FAILURE_MSG, Toast.LENGTH_SHORT).show();
			}
		}
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.relative_main:
			PassengerApp.getInstance().hideKeyboard(CardRegistrationActivity.this, v);
			break;
			
		case R.id.btn_scan:
			processCardScan();
			break;
			
		case R.id.btn_save:
			processCardInfoSave();
			break;
			
		case R.id.btn_skip:
			finish();
			startActivity(new Intent(CardRegistrationActivity.this, ConfirmRegistrationActivity.class));
			break;
			
		case R.id.linear_layout_country:
			PassengerApp.getInstance().hideKeyboard(CardRegistrationActivity.this, v);
			spinnerCountry.setSelection(flagIndex);
			spinnerCountry.performClick();
			break;
			
		case R.id.btn_back:
			onBackPressed();
			break;
		
		case R.id.ed_txt_exp:
			datePicker();
			break;
		

		default:
			break;
		}
	}
	
	private void datePicker() {
		dialogDatePicker = new DialogController(CardRegistrationActivity.this).DialogDatePicker();
		DatePicker d = (DatePicker) dialogDatePicker.findViewById(R.id.datePicker1);
		d.setDateChangedListener(CardRegistrationActivity.this);

		try {
			Calendar c = Calendar.getInstance();
			//int year = c.get(Calendar.YEAR);
			Log.e("CURRENT_YEAR","****" + c.get(Calendar.YEAR));
			//d.setStartYear(c.get(Calendar.YEAR));
			//d.setEndYear(2090);
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
					Toast.makeText(CardRegistrationActivity.this, "Expired year can not be less than current year" + year, Toast.LENGTH_SHORT).show();
				} else {
					dialogDatePicker.dismiss();
					edTxtExp.setText(pickerDate);
				}
			}
		});
		
		dialogDatePicker.show();
	}

	private void processCardInfoSave() {
		if(!edTxtCardNumber.getText().toString().trim().equalsIgnoreCase("") && !edTxtCardName.getText().toString().trim().equalsIgnoreCase("") && !edTxtExp.getText().toString().trim().equalsIgnoreCase("") && !edTxtCvv.getText().toString().trim().equalsIgnoreCase("") && !edTxtZipCode.getText().toString().trim().equalsIgnoreCase("")) {
			String cardChecking = new CardValidationChecking().getdigit(edTxtCardNumber.getText().toString().trim().replace(" ", ""));
			Log.i("value is ", "_____"+cardChecking);
			String checkingArray[] = cardChecking.split(" ");
			if(!checkingArray[0].equals("invalid")) {
				if(!checkingArray[1].equals("invalid")) {
					cardType = checkingArray[0].toString();
					if(InternetConnectivity.isConnectedToInternet(CardRegistrationActivity.this)) {
						new CardRegistrationAsyncTask(CardRegistrationActivity.this, new OnRequestComplete() {
							
							@Override
							public void onRequestComplete(String result) {
								try {
									if("2001".equals(result)) {
										if(isForwardActivity) {
											CreditCardInfo newCard = new CreditCardInfo();
											newCard.setCardHolderName(edTxtCardName.getText().toString().trim());
											newCard.setCardNumber(edTxtCardNumber.getText().toString().trim());
											newCard.setCareType(cardType);
											newCard.setCountryId(PassengerApp.getInstance().getCountryArray().get(flagIndex).getId());
											newCard.setCountryName(txtViewCountryName.getText().toString().trim());
											newCard.setCvv(Integer.parseInt((edTxtCvv.getText().toString().trim())));
											newCard.setZip(edTxtCvv.getText().toString().trim());
											newCard.setExpireDate(edTxtExp.getText().toString().trim());
											newCard.setPaymentId(PassengerApp.getInstance().getPaymentId());
											newCard.setIsDefault(PassengerApp.getInstance().getCreditCardInfoList() != null ? 0 : 1);
											
											
											if (PassengerApp.getInstance().getCreditCardInfoList() == null) {
												
												ArrayList<CreditCardInfo> creditCardInfoList = new ArrayList<CreditCardInfo>();
												creditCardInfoList.add(newCard);
												PassengerApp.getInstance().setCreditCardInfoList(creditCardInfoList);
												
												//PassengerApp.getInstance().getCreditCardInfoList().add(newCard);
												
											} else {
												PassengerApp.getInstance().getCreditCardInfoList().add(newCard);
											}
											
											//PassengerApp.getInstance().getCreditCardInfoList().
											
											Toast.makeText(CardRegistrationActivity.this, "Successfully card registered !", Toast.LENGTH_SHORT).show();
											
										} else {
											finish();
											startActivity(new Intent(CardRegistrationActivity.this, ConfirmRegistrationActivity.class));
										}
									
									} else if ("1001".equals(result)) {
										Toast.makeText(CardRegistrationActivity.this, "Card number already exist", Toast.LENGTH_SHORT).show();
									} else {
										Toast.makeText(CardRegistrationActivity.this, "Entry is not correct. Please try again", Toast.LENGTH_SHORT).show();
									}
								} catch (Exception e) {
									Toast.makeText(CardRegistrationActivity.this, "Response error: (" + e.getMessage() + "). Please try again" , Toast.LENGTH_SHORT).show();
								}
							}
							
						}).execute(ConstantValues.FUNC_ID_CARD_REGISTRATION, PassengerApp.getInstance().getPassengerId(), cardType.toLowerCase(), edTxtCardNumber.getText().toString().trim(), edTxtCardName.getText().toString().trim(), edTxtExp.getText().toString().trim(), edTxtCvv.getText().toString().trim(), PassengerApp.getInstance().getCountryArray().get(flagIndex).getId(), edTxtZipCode.getText().toString().trim(), (PassengerApp.getInstance().getCreditCardInfoList() != null && PassengerApp.getInstance().getCreditCardInfoList().size() != 0 ? "0" : "1"));
					} else {
						Toast.makeText(CardRegistrationActivity.this, ConstantValues.INTERNET_CONNECTION_FAILURE_MSG, Toast.LENGTH_SHORT).show();
					}
					
				} else {
					cardType = "";
					Toast.makeText(CardRegistrationActivity.this, "Card is invalid 1", Toast.LENGTH_SHORT).show();
				}
			} else {
				Toast.makeText(CardRegistrationActivity.this, "Card is invalid 2", Toast.LENGTH_SHORT).show();
			}
		} else {
			if(edTxtCardNumber.getText().toString().trim().equals("")) {
				edTxtCardNumber.setError("Required");
			}
			
			if(edTxtCardName.getText().toString().trim().equals("")) {
				edTxtCardName.setError("Required");
			}
			
			if(edTxtZipCode.getText().toString().trim().equals("")) {
				edTxtZipCode.setError("Required");
			}
			
			if(edTxtExp.getText().toString().trim().equals("")) {
				edTxtExp.setError("Required");
			}
			
			if(edTxtCvv.getText().toString().trim().equals("")) {
				edTxtCvv.setError("Required");
			}
			
		}
	}

	private void processCardScan() {
		// This method is set up as an onClick handler in the layout xml
		// e.g. android:onClick="onScanPress"
		isLock = true;
		BaseActivity.isLock = true;
		Intent scanIntent = new Intent(CardRegistrationActivity.this, CardIOActivity.class);
		// required for authentication with card.io
		scanIntent.putExtra(CardIOActivity.EXTRA_APP_TOKEN, "a43d19b207454c4b80da3cbce5a89001");
	
		// customize these values to suit your needs.
		scanIntent.putExtra(CardIOActivity.EXTRA_REQUIRE_EXPIRY, true); // default: true
		scanIntent.putExtra(CardIOActivity.EXTRA_REQUIRE_CVV, false); // default: false
		scanIntent.putExtra(CardIOActivity.EXTRA_REQUIRE_POSTAL_CODE, false); // default: false
	
		// hides the manual entry button
		// if set, developers should provide their own manual entry mechanism in the app
		scanIntent.putExtra(CardIOActivity.EXTRA_SUPPRESS_MANUAL_ENTRY, false); // default: false
	
		// MY_SCAN_REQUEST_CODE is arbitrary and is only used within this activity.
		startActivityForResult(scanIntent, MY_SCAN_REQUEST_CODE);
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == MY_SCAN_REQUEST_CODE) {
			Log.e("I am here", "----");
	        String resultDisplayStr;
	        if (data != null && data.hasExtra(CardIOActivity.EXTRA_SCAN_RESULT)) {
	            CreditCard scanResult = data.getParcelableExtra(CardIOActivity.EXTRA_SCAN_RESULT);
	            edTxtCardNumber.setText(scanResult.getFormattedCardNumber());
	           
	            cardType = "" + scanResult.getCardType();
	            setCardImage();
	            
	            Log.i("Card type", "___________"+cardType);
	            if(scanResult.cvv != null) {
	            	edTxtCvv.setText(""+scanResult.cvv);
	            }
	            
	            edTxtExp.setText(""+scanResult.expiryMonth+"/"+scanResult.expiryYear);
	            // Never log a raw card number. Avoid displaying it, but if necessary use getFormattedCardNumber()
	            resultDisplayStr = "Card Number: " + scanResult.getRedactedCardNumber() + "\n";
	            
	            Log.i("Card Details","-----" + scanResult.getFormattedCardNumber());
	            // Do something with the raw number, e.g.:
	            // myService.setCardNumber( scanResult.cardNumber );

	            if (scanResult.isExpiryValid()) {
	                resultDisplayStr += "Expiration Date: " + scanResult.expiryMonth + "/" + scanResult.expiryYear + "\n";
	                Log.i("Card Exp Valid","-----"+resultDisplayStr);
	            }

	            if (scanResult.cvv != null) {
	                // Never log or display a CVV
	                resultDisplayStr += "CVV has " + scanResult.cvv.length() + " digits.\n";
	                Log.i("CVV","-----"+resultDisplayStr);
	            }

	            if (scanResult.postalCode != null) {
	                resultDisplayStr += "Postal Code: " + scanResult.postalCode + "\n";
	                Log.i("Postal Code","-----"+resultDisplayStr);
	            }
	            
	            Log.i("CARD_INFO", scanResult.cvv + "-----" + resultDisplayStr);
	           
	        } else {
	            resultDisplayStr = "Scan was canceled.";
	            Log.e("Failed Msg","-----"+resultDisplayStr);
	        }
	        // do something with resultDisplayStr, maybe display it in a textView
	        // resultTextView.setText(resultStr);
	    } else {
	    	
	    }
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
	public void onFocusChange(View v, boolean hasFocus) {
		switch (v.getId()) {
		case R.id.ed_txt_card_number:
			if(!edTxtCardNumber.getText().toString().trim().equalsIgnoreCase("")) {
				setCardImage();
			} else {
				if(!hasFocus) {
					edTxtCardNumber.setError("required");
				}
			}
			break;

		default:
			break;
		}
	}
	
	public void setCardImage(){
		String cardChecking = new CardValidationChecking().getdigit(edTxtCardNumber.getText().toString().trim().replace(" ", ""));
		String checkingArray[] = cardChecking.split(" ");
		if(!checkingArray[0].equals("invalid")) {
			if(!checkingArray[1].equals("invalid")) {
				cardType = checkingArray[0].toString();
				
				if (cardType.equalsIgnoreCase("visa")) {
					imgViewCard.setBackgroundResource(R.drawable.visa);

				} else if (cardType.equalsIgnoreCase("master")) {
					imgViewCard.setBackgroundResource(R.drawable.master);

				} else if (cardType.equalsIgnoreCase("americanexpress")) {
					imgViewCard.setBackgroundResource(R.drawable.american);

				} else if (cardType.equalsIgnoreCase("discover")) {
					imgViewCard.setBackgroundResource(R.drawable.discover);
				} else {
					imgViewCard.setBackgroundResource(R.drawable.card_image);
				}
			} else {
				imgViewCard.setBackgroundResource(R.drawable.card_image);
			}
		} else {
			imgViewCard.setBackgroundResource(R.drawable.card_image);
		}
	}
	
	@Override
	protected void onResume() {
		if (getIntent().getExtras() != null) {
			if (getIntent().getExtras().getBoolean("is_skip")) {
				if (isLock) {
					Log.e("i am here", "---");
					Intent intent = new Intent(CardRegistrationActivity.this, LoginWithPinAuthActivity.class);
					intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
					startActivity(intent);
				}
			}
			
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
		Log.e("onChangeDate", "" + c.get(Calendar.MONTH) + " " + c.get(Calendar.DAY_OF_MONTH) + " " + c.get(Calendar.YEAR));
		selectedYear = c.get(Calendar.YEAR);
		if ((c.get(Calendar.MONTH) + 1) < 10) {
			pickerDate = "0" + (c.get(Calendar.MONTH) + 1) + "/" + c.get(Calendar.YEAR);
		} else {
			pickerDate = "" + (c.get(Calendar.MONTH) + 1) + "/" + c.get(Calendar.YEAR);
		}
		
		
	}

}
