package com.netcabs.passenger;

import java.util.Calendar;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
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

import com.netcabs.adapter.PaymentCardAdapter;
import com.netcabs.adapter.PaymentsMethodAdapter;
import com.netcabs.asynctask.CardListAsyncTask;
import com.netcabs.asynctask.ConfirmBookingAsyncTask;
import com.netcabs.customview.DialogController;
import com.netcabs.interfacecallback.OnRequestComplete;
import com.netcabs.internetconnection.InternetConnectivity;
import com.netcabs.passengerinfo.PassengerApp;
import com.netcabs.utils.ConstantValues;
import com.netcabs.utils.Utils;

public class ConfirmBookingActivity extends Activity implements OnClickListener, OnItemSelectedListener {

	private TextView txtViewDate;
	private TextView txtViewTime;
	private TextView txtViewPickupAddress;
	private TextView txtViewDestination;
	private EditText edTxtPassengerName;
	
	private Button btnConfrim;
	private Button btnBack;
	private Button btnNow;
	private Button btnParcel;
	private Button btnPassengerOne;
	private Button btnPassengerTwo;
	private Button btnPassengerThree;
	private Button btnPassengerFour;
	private RelativeLayout relativeLayout;
	private Dialog dialogConfirmBooking;
	
	private boolean isParcel;
	private int passengerCounts;
	private String taxiIdList;
	
	private Spinner spinnerPayment;
	private String paymentMethodId = "";
	private String paymentMethodName = "";
	public boolean isLock = false;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_confirm_booking);
		
		initViews();
		setListener();
		loadData();
		loadPaymentInfo();
	}
	
	private void loadPaymentInfo() {
		
		if(InternetConnectivity.isConnectedToInternet(ConfirmBookingActivity.this)) {
			new CardListAsyncTask(ConfirmBookingActivity.this, new OnRequestComplete() {
				
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
						Toast.makeText(ConfirmBookingActivity.this, "Response Error: (" + e.getMessage() + ").  Please try again", Toast.LENGTH_SHORT).show();
					}
				}
			}).execute(ConstantValues.FUNC_ID_CARD_LIST, PassengerApp.getInstance().getPassengerId());
			
		} else {
			Toast.makeText(ConfirmBookingActivity.this, ConstantValues.INTERNET_CONNECTION_FAILURE_MSG, Toast.LENGTH_SHORT).show();
		}
		
	}

	private void initViews() {
		relativeLayout = (RelativeLayout)findViewById(R.id.relative_main);
		txtViewDate = (TextView) findViewById(R.id.txt_view_date_pick);
		txtViewTime = (TextView) findViewById(R.id.txt_view_time_pick);
		txtViewPickupAddress = (TextView) findViewById(R.id.txt_view_pickup_address);
		txtViewDestination = (TextView) findViewById(R.id.txt_view_destination);
		edTxtPassengerName = (EditText) findViewById(R.id.ed_txt_passenger_name);
		
		btnBack = (Button) findViewById(R.id.btn_back);
		btnNow = (Button) findViewById(R.id.btn_now);
		btnConfrim = (Button) findViewById(R.id.btn_confirm);
		btnParcel = (Button) findViewById(R.id.btn_parcel);
		btnPassengerOne = (Button) findViewById(R.id.btn_passenger_one);
		btnPassengerTwo = (Button) findViewById(R.id.btn_passenger_two);
		btnPassengerThree = (Button) findViewById(R.id.btn_passenger_three);
		btnPassengerFour = (Button) findViewById(R.id.btn_passenger_four);
		
		spinnerPayment = (Spinner) findViewById(R.id.spinner_payment);
	}

	private void setListener() {
		relativeLayout.setOnClickListener(this);
		btnBack.setOnClickListener(this);
		btnNow.setOnClickListener(this);
		btnConfrim.setOnClickListener(this);
		btnParcel.setOnClickListener(this);
		btnPassengerOne.setOnClickListener(this);
		btnPassengerTwo.setOnClickListener(this);
		btnPassengerThree.setOnClickListener(this);
		btnPassengerFour.setOnClickListener(this);
		
		txtViewPickupAddress.setOnClickListener(this);
		txtViewDestination.setOnClickListener(this);
		
		spinnerPayment.setOnItemSelectedListener(this);
	}

	private void loadData() {
		
		//Log.i("else", "______if______"+PassengerApp.getInstance().getPaymentsInfoList().size());
		if(PassengerApp.getInstance().getProfileDetailsInfo() != null) {
			edTxtPassengerName.setText(PassengerApp.getInstance().getProfileDetailsInfo().getFirstName() +" "+PassengerApp.getInstance().getProfileDetailsInfo().getLastName());
			edTxtPassengerName.setSelection(edTxtPassengerName.getText().length());
		}
		if(PassengerApp.getInstance().getPaymentsInfoList() != null) {
			Log.i("else", "______if______");
			//spinnerPayment.setAdapter(new PaymentsMethodAdapter(ConfirmBookingActivity.this, PassengerApp.getInstance().getPaymentsInfoList()));
			spinnerPayment.setAdapter(new PaymentsMethodAdapter(ConfirmBookingActivity.this, R.layout.spinner_simple_row, PassengerApp.getInstance().getPaymentsInfoList()));
		
		} else {
			spinnerPayment.setAdapter(null);
			Log.i("else", "____________");
		}
		
		Calendar calendar = Calendar.getInstance();
		txtViewDate.setText(Utils.getBookingTimeDateFormat(calendar.get(Calendar.DAY_OF_MONTH) +"/"+ (calendar.get((Calendar.MONTH))+1) +"/"+ calendar.get(Calendar.YEAR)));
		txtViewTime.setText(Utils.getBookingTimeDateFormat(calendar.get(Calendar.HOUR_OF_DAY) +":"+ calendar.get(Calendar.MINUTE) +":"+ calendar.get(Calendar.SECOND)));
	   
		if(PassengerApp.getInstance().getPickUpLocationInfo()!= null){
			txtViewPickupAddress.setText(PassengerApp.getInstance().getPickUpLocationInfo().getLocationName());
		}
		
		if(PassengerApp.getInstance().getDestinationInfo() != null){
			txtViewDestination.setText(PassengerApp.getInstance().getDestinationInfo().getLocationName());
		}
		
		
		 StringBuilder result = new StringBuilder();
		 if(PassengerApp.getInstance().getTaxiInfoList() != null) {
			 for (int i = 0; i < PassengerApp.getInstance().getTaxiInfoList().size(); i++) {
				 result.append(PassengerApp.getInstance().getTaxiInfoList().get(i).getTaxiId());
			     result.append(",");
			 } 
		 }
		 
		taxiIdList =  result.length() > 0 ? result.substring(0, result.length() - 1) : "";
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.relative_main:
			PassengerApp.getInstance().hideKeyboard(ConfirmBookingActivity.this, v);
			break;
			
		case R.id.btn_back:
			onBackPressed();
			break;
			
		case R.id.btn_confirm:
			if (!paymentMethodName.toString().trim().equalsIgnoreCase("Cash Payment")) {
				if(PassengerApp.getInstance().getCreditCardInfoList() != null) {
					if (PassengerApp.getInstance().getCreditCardInfoList().size() < 1) {
						Toast.makeText(ConfirmBookingActivity.this,"You did not add any card in your profile", Toast.LENGTH_SHORT).show();
						return;
					}
				} else {
					Toast.makeText(ConfirmBookingActivity.this,"You did not add any card in your profile", Toast.LENGTH_SHORT).show();
					return;
				}
			}
			
			if(!edTxtPassengerName.getText().toString().trim().equalsIgnoreCase("") && !txtViewPickupAddress.getText().toString().trim().equalsIgnoreCase("") && !txtViewDestination.getText().toString().trim().equalsIgnoreCase("")) {
				if(isParcel) {
					showConfirmPopup();
				} else if(!isParcel && passengerCounts != 0) {
					showConfirmPopup();
				} else {
					Toast.makeText(ConfirmBookingActivity.this,"Please select number of passenger", Toast.LENGTH_SHORT).show();
				}
			} else {
				if(edTxtPassengerName.getText().toString().trim().equalsIgnoreCase("")) {
					edTxtPassengerName.setError("Required");
				}
				
				if(txtViewDestination.getText().toString().trim().equalsIgnoreCase("")) {
					Toast.makeText(ConfirmBookingActivity.this, "Please select destination address", Toast.LENGTH_SHORT).show();
				}
			}
			
			break;
			
		case R.id.btn_now:
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
			
		case R.id.txt_view_pickup_address:
			isLock = true;
			startActivity(new Intent(ConfirmBookingActivity.this, PickupAddressActivity.class));
			break;
			
		case R.id.txt_view_destination:
			isLock = true;
			startActivity(new Intent(ConfirmBookingActivity.this, DestinationAddressActivity.class));
			break;

		default:
			break;
		}
	}

	private void showConfirmPopup() {
		dialogConfirmBooking = new DialogController(ConfirmBookingActivity.this).ConfirmBookingDialog();
		
		TextView txtViewMsg = (TextView) dialogConfirmBooking.findViewById(R.id.txt_view_booking_message);
		txtViewMsg.setText("Your booking will be broadcast to\nnearby drivers. You will receive\n a booking confirmation \nwithin 3 minutes.\nProcced?");
		
		Button btnYes = (Button) dialogConfirmBooking.findViewById(R.id.btn_yes);
		btnYes.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				dialogConfirmBooking.dismiss();
				String isParseltoString;

				if (isParcel) {
					isParseltoString = "1";
				} else {
					isParseltoString = "0";
				}
				
				if(InternetConnectivity.isConnectedToInternet(ConfirmBookingActivity.this)) {
					new ConfirmBookingAsyncTask(ConfirmBookingActivity.this, new OnRequestComplete() {
						
						@Override
						public void onRequestComplete(String result) {
							try {
								if ("2001".equals(result)) {
									isLock = true;
									Intent intent = new Intent(ConfirmBookingActivity.this, ConfirmBookingSearchActivity.class);
									intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_SINGLE_TOP);
									startActivity(intent);
									//dialogConfirmBooking.dismiss();
									finish();
								} else {
									Toast.makeText(ConfirmBookingActivity.this, "Entry is not correct. Please try again !", Toast.LENGTH_SHORT).show();
								}
							} catch (Exception e) {
								Toast.makeText(ConfirmBookingActivity.this, "Response error: (" + e.getMessage() + "). Please try again" , Toast.LENGTH_SHORT).show();
							}
							
						}
						
					}).execute(ConstantValues.FUNC_ID_BOOKING_CONFRIMATION, 
								PassengerApp.getInstance().getPassengerId(), 
								Double.toString(PassengerApp.getInstance().getDestinationInfo().getLocationLongitude()), 
								Double.toString(PassengerApp.getInstance().getDestinationInfo().getLocationLatitude()),
								PassengerApp.getInstance().getDestinationInfo().getLocationName(),
								Double.toString(PassengerApp.getInstance().getPickUpLocationInfo().getLocationLongitude()),
								Double.toString(PassengerApp.getInstance().getPickUpLocationInfo().getLocationLatitude()), 
								PassengerApp.getInstance().getPickUpLocationInfo().getLocationName(),
								ConstantValues.BOOKING_VIA_APP,
								paymentMethodId,
								txtViewDate.getText().toString().trim().replace(" ", ""),
								txtViewTime.getText().toString().trim().replace(" ", ""),
								Integer.toString(passengerCounts),
								isParseltoString, taxiIdList);
				} else{
					Toast.makeText(ConfirmBookingActivity.this, ConstantValues.INTERNET_CONNECTION_FAILURE_MSG, Toast.LENGTH_SHORT).show();
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
    public void onResume() {
        super.onResume();
        if(PassengerApp.getInstance().getPickUpLocationInfo() != null) {
        	txtViewPickupAddress.setText(PassengerApp.getInstance().getPickUpLocationInfo().getLocationName());
        } 
        if(PassengerApp.getInstance().getDestinationInfo() != null) {
        	txtViewDestination.setText(PassengerApp.getInstance().getDestinationInfo().getLocationName());
        }
        
        if (isLock) {
			Intent intent = new Intent(ConfirmBookingActivity.this, LoginWithPinAuthActivity.class);
			intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivity(intent);
		}
        
    }

    @Override
    public void onPause() {
    	if (isLock) {
			isLock = false;
		} else {
			isLock = true;
		}
        super.onPause();
       
    }

    @Override
    public void onDestroy() {
    	isLock = false;
        super.onDestroy();
    }

	@Override
	public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		paymentMethodId = PassengerApp.getInstance().getPaymentsInfoList().get(arg2).getId();
		paymentMethodName = PassengerApp.getInstance().getPaymentsInfoList().get(arg2).getMethodName();
		//Toast.makeText(getApplicationContext(), "The planet is " + PassengerApp.getInstance().getPaymentsInfoList().get(arg2).getMethodName(), Toast.LENGTH_LONG).show();
		
	}

	@Override
	public void onNothingSelected(AdapterView<?> arg0) {
		
	}


}
