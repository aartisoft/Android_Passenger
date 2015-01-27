package com.netcabs.passenger;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.ActivityManager.RunningServiceInfo;
import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.netcabs.asynctask.CancelBookingAsyncTask;
import com.netcabs.customview.DialogController;
import com.netcabs.interfacecallback.OnRequestComplete;
import com.netcabs.internetconnection.InternetConnectivity;
import com.netcabs.passengerinfo.PassengerApp;
import com.netcabs.utils.ConstantValues;

public class ConfirmBookingSearchActivity extends Activity implements OnClickListener {

	private TextView txtViewPickupAddress;
	private TextView txtViewDestinationAddress;
	private TextView txtViewTimer;
	private TextView txtViewDriversSentTo;
	private TextView txtViewDriversSeenTo;
	private TextView txtViewDriversRejectedTo;

	private Button btnCancel;
	private Button btnBack;

	private String minute;
	private String second;

	private CountDownTimer countDownTimer;

	private LinearLayout linearLayout;
	private ImageView imageView;

	private Dialog dialogConfirmCancel;
	private Dialog dialogBookingReset;
	private Intent serviceIntent;
	private boolean receiverStop = false;
	public boolean isLock = false;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_confirm_booking_search);
		
		initViews();
		setListener();
		loadData();
	}

	private void initViews() {
		dialogBookingReset = new DialogController(ConfirmBookingSearchActivity.this).ResetBookingSearchDialog();
		
		txtViewPickupAddress = (TextView) findViewById(R.id.txt_view_pickup_address);
		txtViewDestinationAddress = (TextView) findViewById(R.id.txt_view_destination_address);
		txtViewTimer = (TextView) findViewById(R.id.txt_view_timer);
		txtViewDriversSentTo = (TextView) findViewById(R.id.txt_view_drivers_sent);
		txtViewDriversSeenTo = (TextView) findViewById(R.id.txt_view_drivers_seen);
		txtViewDriversRejectedTo = (TextView) findViewById(R.id.txt_view_drivers_rejected);
		
		btnCancel = (Button) findViewById(R.id.btn_cancel);
		btnBack = (Button) findViewById(R.id.btn_back);
		
		linearLayout = (LinearLayout) findViewById(R.id.linear_timer);
	}

	private void setListener() {
		btnCancel.setOnClickListener(this);
		btnBack.setOnClickListener(this);
	}

	private void loadData() {
		txtViewPickupAddress.setText(PassengerApp.getInstance().getPickUpLocationInfo().getLocationName());
		txtViewDestinationAddress.setText(PassengerApp.getInstance().getDestinationInfo().getLocationName());
		
		for(int i = 0; i <ConstantValues.COUNTDOWN_TIMER_BAR; i++) {
			imageView = new ImageView(ConfirmBookingSearchActivity.this);
			imageView.setId(i);
			imageView.setBackgroundResource(R.drawable.time_loding_g);
			LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
			lp.setMargins(3, 0, 0, 0);
			imageView.setLayoutParams(lp);
			linearLayout.addView(imageView);
		}
		
		serviceIntent = new Intent(ConfirmBookingSearchActivity.this, com.netcabs.servicehttppost.BroadcastServiceSeenUnseen.class);
		showTimer(ConstantValues.TAXI_BOOKING_SEARCH_TIME, 0);
	}
	
	private void updateUI(Intent intent) {
    	String status = intent.getStringExtra("status"); 
    	Log.i("Staus code ", "___________" + status);
    	
    	try {
    		if ("2001".equals(status)) {
        		txtViewDriversSentTo.setText(""+PassengerApp.getInstance().getSeenUnSeenInfo().getSentCount());
        		txtViewDriversRejectedTo.setText("" + PassengerApp.getInstance().getSeenUnSeenInfo().getRejectedCount());
        		txtViewDriversSeenTo.setText(""+PassengerApp.getInstance().getSeenUnSeenInfo().getSeenCount());
        		
        		if (PassengerApp.getInstance().getSeenUnSeenInfo().getSentCount() == PassengerApp.getInstance().getSeenUnSeenInfo().getRejectedCount()) {
        			
        			stopService(serviceIntent);
        			unregisterReceiver(broadcastReceiver);
        			Toast.makeText(ConfirmBookingSearchActivity.this, "Booking has been rejected from all driver" , Toast.LENGTH_SHORT).show();
        			
        			if(countDownTimer != null) {
		    			countDownTimer.cancel();
		    			
		    		}
		        	finish();
        		}
        		
        		
        		if (PassengerApp.getInstance().getSeenUnSeenInfo().getIsAccepted() == 1) {
        			
        			if(dialogBookingReset.isShowing()) {
        				dialogBookingReset.dismiss();
        			}
        			stopService(serviceIntent);
        			unregisterReceiver(broadcastReceiver);
        			
        			receiverStop = true;
        			showAcceptConfirmPopup();
        			final Handler handler = new Handler();
        		    handler.postDelayed(new Runnable() {
        		        @Override
        		        public void run() {
        		        	isLock = true;
        		        	if(countDownTimer != null) {
        		    			countDownTimer.cancel();
        		    		}
        		        	dialogConfirmCancel.dismiss();
        		        	startActivity(new Intent(ConfirmBookingSearchActivity.this, MyBookingDetailsActivity.class).putExtra("VIEW_TYPE", ConstantValues.ComingFromBOOKING));
        		        	finish();
        		        }
        		    }, 2000);
        		}
        		
        	} else {
        		
        	}
    		
    	} catch (Exception e) {
    		Toast.makeText(ConfirmBookingSearchActivity.this, "Response error: (" + e.getMessage() + "). Please try again" , Toast.LENGTH_SHORT).show();
    	}
    	
    }
	
	private BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
		@Override
		public void onReceive(Context context, Intent intent) {
			updateUI(intent);
		}
	};

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.relative_main:
			PassengerApp.getInstance().hideKeyboard(ConfirmBookingSearchActivity.this, v);
			break;
			
		case R.id.btn_cancel:
			showConfirmPopup();
			break;
			
		case R.id.btn_back:
			showConfirmPopup();
			break;

		default:
			break;
		}
	}
	
	private void showTimer(final int min, final int sec) {
		final long totalTime = min * 60000 + sec * 1000;
		
		countDownTimer = new CountDownTimer(totalTime, 1000) {
			int barChangeInterval = (int) ((totalTime / 1000) / linearLayout.getChildCount());
			int imgViewIndex = 0;
			int timeSecond = 1;
			
			@Override
			public void onTick(long millisUntilFinished) {
				int s = (int) (millisUntilFinished / 1000) % 60;
				int m = (int) (millisUntilFinished / 60000) % 60;
				
				if(imgViewIndex < linearLayout.getChildCount()) {
					if(timeSecond % barChangeInterval == 0) {
						linearLayout.getChildAt(imgViewIndex).setBackgroundResource(R.drawable.time_loding_y);
						imgViewIndex++;
					}
				}
				timeSecond++;
				
				minute = (m < 10) ? "0"+m : ""+m;
				second = (s < 10) ? "0"+s : ""+s;
				
				txtViewTimer.setText(minute +":"+ second);
				
			}

			@Override
			public void onFinish() {
				countDownTimer.cancel();
				txtViewTimer.setText("00:00");
				linearLayout.getChildAt(linearLayout.getChildCount()-1).setBackgroundResource(R.drawable.time_loding_y);
				linearLayout.removeAllViews();
				
				TextView txtViewMsg = (TextView) dialogBookingReset.findViewById(R.id.txt_view_booking_message);
				txtViewMsg.setText("Do you want to restart booking search?");
				
				Button btnYes = (Button) dialogBookingReset.findViewById(R.id.btn_yes);
				Button btnCancel = (Button) dialogBookingReset.findViewById(R.id.btn_no);
				
				btnYes.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						/*try {
								stopService(serviceIntent);
								unregisterReceiver(broadcastReceiver);
						} catch (Exception e){
							
						}*/
						dialogBookingReset.dismiss();
						countDownTimer.cancel();
						for(int i = 0; i <ConstantValues.COUNTDOWN_TIMER_BAR; i++) {
							imageView = new ImageView(ConfirmBookingSearchActivity.this);
							imageView.setId(i);
							imageView.setBackgroundResource(R.drawable.time_loding_g);
							LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
							lp.setMargins(3, 0, 0, 0);
							imageView.setLayoutParams(lp);
							linearLayout.addView(imageView);
						}
						showTimer(ConstantValues.TAXI_BOOKING_SEARCH_TIME, 0);
						//startActivity(getIntent());
						
					}
				});
				
				btnCancel.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						dialogBookingReset.dismiss();
						
						if(InternetConnectivity.isConnectedToInternet(ConfirmBookingSearchActivity.this)) {
							new CancelBookingAsyncTask(ConfirmBookingSearchActivity.this, new OnRequestComplete() {
								
								@Override
								public void onRequestComplete(String result) {
									try{
										if("2001".equals(result)) {
											//onBackPressed();
											finish();
										} else {
											
										}
									} catch (Exception e) {
										Toast.makeText(ConfirmBookingSearchActivity.this, "Response Error: (" + e.getMessage() + ").  Please try again", Toast.LENGTH_SHORT).show();
									}
								}
							}).execute(ConstantValues.FUNC_ID_CANCEL_BOOKING, PassengerApp.getInstance().getPassengerId(), PassengerApp.getInstance().getBookingId());
							
						} else {
							Toast.makeText(ConfirmBookingSearchActivity.this, ConstantValues.INTERNET_CONNECTION_FAILURE_MSG, Toast.LENGTH_SHORT).show();
						}
					}
				});
				
				dialogBookingReset.show();
				
				/**/
				
			}
		};
		countDownTimer.start();
	}
	
	@Override
	public void onBackPressed() {
		//super.onBackPressed();
//		if(countDownTimer != null) {
//			countDownTimer.cancel();
//		}
		Toast.makeText(ConfirmBookingSearchActivity.this, "To go back you need to cancel the booking first!", Toast.LENGTH_SHORT).show();
		
	}
	
	private void showConfirmPopup() {
		try {
			dialogConfirmCancel = new DialogController(ConfirmBookingSearchActivity.this).ConfirmBookingDialog();
			TextView txtViewMsg = (TextView) dialogConfirmCancel.findViewById(R.id.txt_view_booking_message);
			txtViewMsg.setText("Are you Sure ?");
			
			Button btnYes = (Button) dialogConfirmCancel.findViewById(R.id.btn_yes);
			
			btnYes.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					dialogConfirmCancel.dismiss();
					if(InternetConnectivity.isConnectedToInternet(ConfirmBookingSearchActivity.this)) {
						if(countDownTimer != null) {
							countDownTimer.cancel();
						}
						new CancelBookingAsyncTask(ConfirmBookingSearchActivity.this, new OnRequestComplete() {
							
							@Override
							public void onRequestComplete(String result) {
								if("2001".equals(result)) {
									//onBackPressed();
									finish();
								} else {
									Toast.makeText(ConfirmBookingSearchActivity.this, "Request is unsuccessful at this moment, please try again", Toast.LENGTH_SHORT).show();
								}
							}
						}).execute(ConstantValues.FUNC_ID_CANCEL_BOOKING, PassengerApp.getInstance().getPassengerId(), PassengerApp.getInstance().getBookingId());
						
					} else {
						Toast.makeText(ConfirmBookingSearchActivity.this, ConstantValues.INTERNET_CONNECTION_FAILURE_MSG, Toast.LENGTH_SHORT).show();
					}
				}
			});
			Button btnNo = (Button) dialogConfirmCancel.findViewById(R.id.btn_no);
			btnNo.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					dialogConfirmCancel.dismiss();
				}
			});
			
			Button btnCancel = (Button) dialogConfirmCancel.findViewById(R.id.btn_cancel);
			btnCancel.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					dialogConfirmCancel.dismiss();
				}
			});
			
			dialogConfirmCancel.show();
			
		} catch (Exception e) {
			Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
		}
		
	}
	
	private void showAcceptConfirmPopup() {
		try {
			dialogConfirmCancel = new DialogController(ConfirmBookingSearchActivity.this).ConfirmAcceptDialog();
			dialogConfirmCancel.setCancelable(false);
			dialogConfirmCancel.setCanceledOnTouchOutside(false);
			
			TextView txtViewDriverName = (TextView) dialogConfirmCancel.findViewById(R.id.txt_view_driver_name);
			txtViewDriverName.setText(PassengerApp.getInstance().getSeenUnSeenInfo().getDriverFirstName() + " " + PassengerApp.getInstance().getSeenUnSeenInfo().getDriverLastName() + " has accepted.");
			dialogConfirmCancel.show();
			
		} catch (Exception e) {
			Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
		}
		
	}
	
//	@Override
//	protected void onPause() {
//		stopService(intent);
//		unregisterReceiver(broadcastReceiver);
//		super.onPause();
//	}
	
	@Override
	protected void onResume() {
		if(InternetConnectivity.isConnectedToInternet(ConfirmBookingSearchActivity.this)) {
			startService(serviceIntent);
			registerReceiver(broadcastReceiver, new IntentFilter(com.netcabs.servicehttppost.BroadcastServiceSeenUnseen.BROADCAST_ACTION));
		} else {
			Toast.makeText(ConfirmBookingSearchActivity.this, ConstantValues.INTERNET_CONNECTION_FAILURE_MSG, Toast.LENGTH_SHORT).show();
		}
		
		if (isLock) {
			Intent intent = new Intent(ConfirmBookingSearchActivity.this, LoginWithPinAuthActivity.class);
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
		Log.i("BroadCast status", "***" + isMyServiceRunning(com.netcabs.servicehttppost.BroadcastServiceSeenUnseen.class));
		try {
			//if(!receiverStop) {
				stopService(serviceIntent);
				unregisterReceiver(broadcastReceiver);
			//}
			
		} catch (Exception e){
			
		}
		isLock = false;
		super.onDestroy();
	}
	
	private boolean isMyServiceRunning(Class<?> serviceClass) {
	  ActivityManager manager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
	  for (RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
	    if (serviceClass.getName().equals(service.service.getClassName())) {
	        return true;
	    }
	  }
	  return false;
	}

}
