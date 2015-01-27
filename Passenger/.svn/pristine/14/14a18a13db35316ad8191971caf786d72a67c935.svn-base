package com.netcabs.passenger;

import java.io.IOException;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.netcabs.adapter.PaymentsMethodAdapter;
import com.netcabs.asynctask.CardListAsyncTask;
import com.netcabs.asynctask.HailedBookingAsyncTask;
import com.netcabs.customview.DialogController;
import com.netcabs.datamodel.LocationSearchInfo;
import com.netcabs.datamodel.PickUpInfo;
import com.netcabs.interfacecallback.OnRequestComplete;
import com.netcabs.internetconnection.InternetConnectivity;
import com.netcabs.latlon.GPSTracker;
import com.netcabs.passengerinfo.PassengerApp;
import com.netcabs.utils.BaseActivity;
import com.netcabs.utils.ConstantValues;
import com.netcabs.utils.Utils;

public class HailedCabDetailsActivity extends Activity implements OnClickListener, OnItemSelectedListener{

	private Button btnBack;
	private Button btnConfirm;
	private Button btnParcel;
	private Button btnPassengerOne;
	private Button btnPassengerTwo;
	private Button btnPassengerThree;
	private Button btnPassengerFour;
	
	private TextView txtViewLocationName;
	private TextView txtViewRegNo;
	private TextView txtViewDestination;
	
	private EditText edTxtPassengerName;
	
	private Spinner spinnerPayment;
	
	private boolean isParcel;
	private String paymentMethodName;
	private int passengerCounts;
	
	private Dialog dialogConfirmBooking;
	private String paymentMethod = "";
	private boolean isLock = false;
	private RelativeLayout relativeLayout;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_hailed_cab_details);
		
		initViews();
		setListener();
		loadData();
		loadPaymentInfo();
	}

	private void initViews() {
		relativeLayout = (RelativeLayout) findViewById(R.id.relative_main);
		btnBack = (Button) findViewById(R.id.btn_back);
		btnConfirm = (Button) findViewById(R.id.btn_confirm);
		btnParcel = (Button) findViewById(R.id.btn_parcel);
		btnPassengerOne = (Button) findViewById(R.id.btn_passenger_one);
		btnPassengerTwo = (Button) findViewById(R.id.btn_passenger_two);
		btnPassengerThree = (Button) findViewById(R.id.btn_passenger_three);
		btnPassengerFour = (Button) findViewById(R.id.btn_passenger_four);
		
		edTxtPassengerName = (EditText) findViewById(R.id.ed_txt_passenger_name);
		
		txtViewDestination = (TextView) findViewById(R.id.txt_view_destination);
		txtViewLocationName = (TextView) findViewById(R.id.txt_view_location_name);
		txtViewRegNo = (TextView) findViewById(R.id.txt_view_reg_no);
		
		spinnerPayment = (Spinner) findViewById(R.id.spinner_payment);
	}

	private void setListener() {
		relativeLayout.setOnClickListener(this);
		btnBack.setOnClickListener(this);
		btnConfirm.setOnClickListener(this);
		btnParcel.setOnClickListener(this);
		btnPassengerOne.setOnClickListener(this);
		btnPassengerTwo.setOnClickListener(this);
		btnPassengerThree.setOnClickListener(this);
		btnPassengerFour.setOnClickListener(this);
		
		txtViewDestination.setOnClickListener(this);
		
		spinnerPayment.setOnItemSelectedListener(this);
	}
	
	private void loadPaymentInfo() {
		
		if(InternetConnectivity.isConnectedToInternet(HailedCabDetailsActivity.this)) {
			new CardListAsyncTask(HailedCabDetailsActivity.this, new OnRequestComplete() {
				
				@Override
				public void onRequestComplete(String result) {
					try {
						if("2001".equals(result)) {
							if(PassengerApp.getInstance().getCreditCardInfoList() != null) {
							} else {
								//lstViewCard.setAdapter(null);
							}
						} else if("3001".equals(result)) {
							
						} else if("4001".equals(result)) {
											
						} else {
							
						}
					} catch (Exception e) {
						Toast.makeText(HailedCabDetailsActivity.this, "Response Error: (" + e.getMessage() + ").  Please try again", Toast.LENGTH_SHORT).show();
					}
				}
			}).execute(ConstantValues.FUNC_ID_CARD_LIST, PassengerApp.getInstance().getPassengerId());
			
		} else {
			Toast.makeText(HailedCabDetailsActivity.this, ConstantValues.INTERNET_CONNECTION_FAILURE_MSG, Toast.LENGTH_SHORT).show();
		}
		
	}

	private void loadData() {
		edTxtPassengerName.setText(PassengerApp.getInstance().getProfileDetailsInfo().getFirstName() +" "+PassengerApp.getInstance().getProfileDetailsInfo().getLastName());
		edTxtPassengerName.setSelection(edTxtPassengerName.getText().length());
		
		if(InternetConnectivity.isConnectedToInternet(HailedCabDetailsActivity.this)) {
			final Geocoder gcd = new Geocoder(HailedCabDetailsActivity.this, Locale.getDefault());
			List<Address> addresses = null;
			try {
				GPSTracker gps = new GPSTracker(HailedCabDetailsActivity.this);
				addresses = gcd.getFromLocation(gps.getLatitude(), gps.getLongitude(), 1);
				if (!addresses.equals(null)) {
					if (addresses.size() > 0) {
						Log.i("Value found", "________________");
						String address = addresses.get(0).getAddressLine(0) + ", " + addresses.get(0).getSubLocality() + ", " + addresses.get(0).getAdminArea();
						String addressWithoutNullValue = address.replace("null", "");
						Log.e("Address is 1", "" + addressWithoutNullValue);
						String formatedAddress = addressWithoutNullValue.replace(", ,", ",");
						Log.e("Address is 2", "" + formatedAddress);
						PickUpInfo info = new  PickUpInfo();
						info.setLocationName(formatedAddress);
						info.setLocationLatitude(gps.getLatitude());
						info.setLocationLongitude(gps.getLongitude());
						Log.i("Value is LATLONG", "_____"+info.getLocationName() +info.getLocationLatitude() + info.getLocationLongitude());
						PassengerApp.getInstance().setPickUpLocationInfo(info);
					} else {
						Log.i("Value is null", "______empty__________");
					}
					   
				} else {
					Log.i("Value is null", "________________");
				}
				
					
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			
		} else {
			Toast.makeText(HailedCabDetailsActivity.this, ConstantValues.INTERNET_CONNECTION_FAILURE_MSG, Toast.LENGTH_SHORT).show();
		}
		
		if (PassengerApp.getInstance().getPickUpLocationInfo() != null) {
			txtViewLocationName.setText(PassengerApp.getInstance().getPickUpLocationInfo().getLocationName());
		}
		
		if(getIntent().getExtras() != null) {
			txtViewRegNo.setText(getIntent().getExtras().getString("reg_no"));
		}
		
		if(PassengerApp.getInstance().getPaymentsInfoList() != null) {
			spinnerPayment.setAdapter(new PaymentsMethodAdapter(HailedCabDetailsActivity.this, R.layout.spinner_simple_row, PassengerApp.getInstance().getPaymentsInfoList()));
		} else {
			spinnerPayment.setAdapter(null);
		}
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.relative_main:
			PassengerApp.getInstance().hideKeyboard(HailedCabDetailsActivity.this, v);
			break;
			
		case R.id.btn_back:
			onBackPressed();
			break;
			
		case R.id.btn_parcel:
			if(isParcel) {
				isParcel = false;
				btnParcel.setBackgroundResource(R.drawable.no_btn);
				
				btnPassengerOne.setBackgroundResource(R.drawable.number);
				btnPassengerOne.setClickable(true);
				btnPassengerOne.setText("1");
				btnPassengerTwo.setBackgroundResource(R.drawable.number);
				btnPassengerTwo.setClickable(true);
				btnPassengerTwo.setText("2");
				btnPassengerThree.setBackgroundResource(R.drawable.number);
				btnPassengerThree.setClickable(true);
				btnPassengerThree.setText("3");
				btnPassengerFour.setBackgroundResource(R.drawable.number);
				btnPassengerFour.setClickable(true);
				btnPassengerFour.setText("4");
				
				btnPassengerOne.setTextColor(getResources().getColor(R.color.warm_gray4));
				btnPassengerTwo.setTextColor(getResources().getColor(R.color.warm_gray4));
				btnPassengerThree.setTextColor(getResources().getColor(R.color.warm_gray4));
				btnPassengerFour.setTextColor(getResources().getColor(R.color.warm_gray4));
				
			} else {
				passengerCounts = 0;
				isParcel = true;
				btnParcel.setBackgroundResource(R.drawable.yes_btn);
				btnPassengerOne.setBackgroundResource(R.drawable.number_off);
				btnPassengerOne.setClickable(false);
				btnPassengerOne.setText("1");
				btnPassengerTwo.setBackgroundResource(R.drawable.number_off);
				btnPassengerTwo.setClickable(false);
				btnPassengerTwo.setText("2");
				btnPassengerThree.setBackgroundResource(R.drawable.number_off);
				btnPassengerThree.setClickable(false);
				btnPassengerThree.setText("3");
				btnPassengerFour.setBackgroundResource(R.drawable.number_off);
				btnPassengerFour.setClickable(false);
				btnPassengerFour.setText("4");
				
				btnPassengerOne.setTextColor(getResources().getColor(R.color.warm_gray4));
				btnPassengerTwo.setTextColor(getResources().getColor(R.color.warm_gray4));
				btnPassengerThree.setTextColor(getResources().getColor(R.color.warm_gray4));
				btnPassengerFour.setTextColor(getResources().getColor(R.color.warm_gray4));
			}
			break;
			
		case R.id.btn_passenger_one:
			passengerCounts = 1;
			btnPassengerOne.setBackgroundResource(R.drawable.number_click);
			btnPassengerOne.setClickable(true);
			btnPassengerOne.setText("1");
			btnPassengerOne.setTextColor(getResources().getColor(R.color.white));
			btnPassengerTwo.setBackgroundResource(R.drawable.number);
			btnPassengerTwo.setClickable(true);
			btnPassengerTwo.setText("2");
			btnPassengerTwo.setTextColor(getResources().getColor(R.color.warm_gray4));
			btnPassengerThree.setBackgroundResource(R.drawable.number);
			btnPassengerThree.setClickable(true);
			btnPassengerThree.setText("3");
			btnPassengerThree.setTextColor(getResources().getColor(R.color.warm_gray4));
			btnPassengerFour.setBackgroundResource(R.drawable.number);
			btnPassengerFour.setClickable(true);
			btnPassengerFour.setText("4");
			btnPassengerFour.setTextColor(getResources().getColor(R.color.warm_gray4));
			break;
			
		case R.id.btn_passenger_two:
			passengerCounts = 2;
			btnPassengerOne.setBackgroundResource(R.drawable.number);
			btnPassengerOne.setClickable(true);
			btnPassengerOne.setText("1");
			btnPassengerOne.setTextColor(getResources().getColor(R.color.warm_gray4));
			
			btnPassengerTwo.setBackgroundResource(R.drawable.number_click);
			btnPassengerTwo.setClickable(true);
			btnPassengerTwo.setText("2");
			btnPassengerTwo.setTextColor(getResources().getColor(R.color.white));
			
			btnPassengerThree.setBackgroundResource(R.drawable.number);
			btnPassengerThree.setClickable(true);
			btnPassengerThree.setText("3");
			btnPassengerThree.setTextColor(getResources().getColor(R.color.warm_gray4));
			
			btnPassengerFour.setBackgroundResource(R.drawable.number);
			btnPassengerFour.setClickable(true);
			btnPassengerFour.setText("4");
			btnPassengerFour.setTextColor(getResources().getColor(R.color.warm_gray4));
			break;
			
		case R.id.btn_passenger_three:
			passengerCounts = 3;
			btnPassengerOne.setBackgroundResource(R.drawable.number);
			btnPassengerOne.setClickable(true);
			btnPassengerOne.setText("1");
			btnPassengerOne.setTextColor(getResources().getColor(R.color.warm_gray4));
			
			btnPassengerTwo.setBackgroundResource(R.drawable.number);
			btnPassengerTwo.setClickable(true);
			btnPassengerTwo.setText("2");
			btnPassengerTwo.setTextColor(getResources().getColor(R.color.warm_gray4));
			
			btnPassengerThree.setBackgroundResource(R.drawable.number_click);
			btnPassengerThree.setClickable(true);
			btnPassengerThree.setText("3");
			btnPassengerThree.setTextColor(getResources().getColor(R.color.white));
			
			btnPassengerFour.setBackgroundResource(R.drawable.number);
			btnPassengerFour.setClickable(true);
			btnPassengerFour.setText("4");
			btnPassengerFour.setTextColor(getResources().getColor(R.color.warm_gray4));
			break;
			
		case R.id.btn_passenger_four:
			passengerCounts = 4;
			btnPassengerOne.setBackgroundResource(R.drawable.number);
			btnPassengerOne.setClickable(true);
			btnPassengerOne.setText("1");
			btnPassengerOne.setTextColor(getResources().getColor(R.color.warm_gray4));
			
			btnPassengerTwo.setBackgroundResource(R.drawable.number);
			btnPassengerTwo.setClickable(true);
			btnPassengerTwo.setText("2");
			btnPassengerTwo.setTextColor(getResources().getColor(R.color.warm_gray4));
			
			btnPassengerThree.setBackgroundResource(R.drawable.number);
			btnPassengerThree.setClickable(true);
			btnPassengerThree.setText("3");
			btnPassengerThree.setTextColor(getResources().getColor(R.color.warm_gray4));
			
			btnPassengerFour.setBackgroundResource(R.drawable.number_click);
			btnPassengerFour.setClickable(true);
			btnPassengerFour.setText("4");
			btnPassengerFour.setTextColor(getResources().getColor(R.color.white));
			break;
		
		case R.id.txt_view_destination:
			isLock = true;
			startActivity(new Intent(HailedCabDetailsActivity.this, DestinationAddressActivity.class));
			break;
			
		case R.id.btn_confirm:
			if (!paymentMethodName.toString().trim().equalsIgnoreCase("Cash Payment")) {
				if(PassengerApp.getInstance().getCreditCardInfoList() != null) {
					
					if (PassengerApp.getInstance().getCreditCardInfoList().size() < 1) {
						Toast.makeText(HailedCabDetailsActivity.this,"You did not add any card in your profile", Toast.LENGTH_SHORT).show();
						return;
					}
				} else {
					Toast.makeText(HailedCabDetailsActivity.this,"You did not add any card in your profile", Toast.LENGTH_SHORT).show();
					return;
				}
			}
			
			if(!edTxtPassengerName.getText().toString().trim().equalsIgnoreCase("") && !txtViewDestination.getText().toString().trim().equalsIgnoreCase("")) {
				if(isParcel) {
					showConfirmPopup();
				} else if(!isParcel && passengerCounts != 0) {
					showConfirmPopup();
				} else {
					Toast.makeText(HailedCabDetailsActivity.this,"Please select number of passenger", Toast.LENGTH_SHORT).show();
				}
			} else {
				if(edTxtPassengerName.getText().toString().trim().equalsIgnoreCase("")) {
					edTxtPassengerName.setError("Required");
				}
				
				if(txtViewDestination.getText().toString().trim().equalsIgnoreCase("")) {
					Toast.makeText(HailedCabDetailsActivity.this, "Please select destination address", Toast.LENGTH_SHORT).show();
				}
			}
			
			break;

		default:
			break;
		}
	}
	
	private void showConfirmPopup() {
		dialogConfirmBooking = new DialogController(HailedCabDetailsActivity.this).ConfirmBookingDialog();
		
		TextView txtViewMsg = (TextView) dialogConfirmBooking.findViewById(R.id.txt_view_booking_message);
		txtViewMsg.setText("Are you sure to confirm this booking?");
		
		Button btnYes = (Button) dialogConfirmBooking.findViewById(R.id.btn_yes);
		btnYes.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				String isParseltoString;

				if (isParcel) {
					isParseltoString = "1";
				} else {
					isParseltoString = "0";
				}
				
				Calendar calendar = Calendar.getInstance();
				String bookingDate = Utils.getTimeDateFormat(calendar.get(Calendar.DAY_OF_MONTH) +"/"+ (calendar.get(Calendar.MONTH)+1) +"/"+ calendar.get(Calendar.YEAR));
				String bookingTime = Utils.getTimeDateFormat(calendar.get(Calendar.HOUR_OF_DAY) +":"+ calendar.get(Calendar.MINUTE) +":"+ calendar.get(Calendar.SECOND));
				
				if(InternetConnectivity.isConnectedToInternet(HailedCabDetailsActivity.this)) {
					new HailedBookingAsyncTask(HailedCabDetailsActivity.this, new OnRequestComplete() {
						
						@Override
						public void onRequestComplete(String result) {
							Log.e("the result is  : ", "---"+result);
								try {
									if ("2001".equals(result)) {
										dialogConfirmBooking.dismiss();
										isLock = true;
										BaseActivity.isLock = true;
										Toast.makeText(HailedCabDetailsActivity.this, "Successful!", Toast.LENGTH_SHORT).show();
										//startActivity(new Intent(HailedCabDetailsActivity.this, MyBookingDetailsActivity.class).putExtra("VIEW_TYPE", ConstantValues.ComingFromHailedCab).putExtra("tab_type", 1));
										startActivity(new Intent(HailedCabDetailsActivity.this, MyBookingDetailsActivity.class).putExtra("VIEW_TYPE", ConstantValues.ComingFromBOOKING));
										finish();
									} else {
										Toast.makeText(HailedCabDetailsActivity.this, "Entry is not correct. Please try again !", Toast.LENGTH_SHORT).show();
									}
								} catch (Exception e) {
								Toast.makeText(HailedCabDetailsActivity.this, "Response Error: (" + e.getMessage() + ").  Please try again", Toast.LENGTH_SHORT).show();
							}
						}
					}).execute(ConstantValues.FUNC_ID_HAILED_BOOKING, 
								PassengerApp.getInstance().getPassengerId(), 
								txtViewRegNo.getText().toString(),
								Double.toString(PassengerApp.getInstance().getDestinationInfo().getLocationLongitude()), 
								Double.toString(PassengerApp.getInstance().getDestinationInfo().getLocationLatitude()),
								PassengerApp.getInstance().getDestinationInfo().getLocationName(),
								Double.toString(PassengerApp.getInstance().getPickUpLocationInfo().getLocationLongitude()),
								Double.toString(PassengerApp.getInstance().getPickUpLocationInfo().getLocationLatitude()), 
								PassengerApp.getInstance().getPickUpLocationInfo().getLocationName(),
								ConstantValues.BOOKING_VIA_HAILEDCAB,
								paymentMethod,
								bookingDate,
								bookingTime,
								Integer.toString(passengerCounts),
								isParseltoString);
				} else {
					Toast.makeText(HailedCabDetailsActivity.this, ConstantValues.INTERNET_CONNECTION_FAILURE_MSG, Toast.LENGTH_SHORT).show();
				}
				
			}
			
		});
		
		Button btnNo = (Button) dialogConfirmBooking.findViewById(R.id.btn_no);
		btnNo.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				dialogConfirmBooking.dismiss();
			}
		});
		
		Button btnCancel = (Button) dialogConfirmBooking.findViewById(R.id.btn_cancel);
		btnCancel.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				dialogConfirmBooking.dismiss();
			}
		});
		
		dialogConfirmBooking.show();
	}
	
	@Override
	public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
		paymentMethod = PassengerApp.getInstance().getPaymentsInfoList().get(position).getId();
		paymentMethodName = PassengerApp.getInstance().getPaymentsInfoList().get(position).getMethodName();
		
	}

	@Override
	public void onNothingSelected(AdapterView<?> parent) {
		
	}
	
	@Override
    public void onResume() {
        super.onResume();
        loadData();
        if(PassengerApp.getInstance().getDestinationInfo() != null) {
        	txtViewDestination.setText(PassengerApp.getInstance().getDestinationInfo().getLocationName());
        }
        
        if (isLock) {
			Intent intent = new Intent(HailedCabDetailsActivity.this, LoginWithPinAuthActivity.class);
			intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivity(intent);
		}
      
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
	
}
