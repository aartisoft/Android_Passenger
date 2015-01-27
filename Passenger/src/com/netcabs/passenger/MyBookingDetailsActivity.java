package com.netcabs.passenger;

import java.util.ArrayList;

import org.w3c.dom.Document;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.graphics.Point;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLayoutChangeListener;
import android.view.WindowManager.LayoutParams;
import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.androidquery.AQuery;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnMyLocationChangeListener;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.netcabs.asynctask.BookingDetailsAsyncTask;
import com.netcabs.asynctask.GetDurationAsyncTask;
import com.netcabs.asynctask.ShareTripAsyncTask;
import com.netcabs.interfacecallback.OnRequestComplete;
import com.netcabs.internetconnection.InternetConnectivity;
import com.netcabs.passengerinfo.PassengerApp;
import com.netcabs.utils.BaseActivity;
import com.netcabs.utils.ConstantValues;
import com.netcabs.utils.GMapV2GetRouteDirection;
import com.netcabs.utils.Utils;

public class MyBookingDetailsActivity extends Activity implements OnClickListener, OnMyLocationChangeListener  {

	private ImageView imgViewTaxiLogo;
	private TextView txtViewTime;
	private TextView txtViewDate;
	private TextView txtViewRegNo;
	private TextView txtViewPickupToDestination;
	private TextView txtViewDistance;
	private TextView txtViewTitle;
	private TextView txtViewEstimatedTime;
	
	private Button btnCancelOrShare;
	private Button btnBack;
	private GoogleMap map;
	private int tabType = 0;
	private int viewType = 0;

	private GMapV2GetRouteDirection routeDirection;
	private Document doc;
	private PolylineOptions rectLine;
	private int position;
	private LatLng pickUpLatLon;
	private LatLng destinationLatLon;
	private LatLng taxiLatLon;
	private String taxiNo = "";
	private String taxiName = "";
	
	private String destinationName = "";
	private String pickUpName = "";
	private Intent serviceIntent;
	
	private MarkerOptions a;
	private Marker m;
	private boolean isServiceRunning  = false;
	private Polyline line;
	private double previousLat;
	private double previousLon;
	private boolean isLock = false;
	private String driverMobileNo;
	
	private LatLng src = null;
	private LatLng desti = null;
	
	private boolean isFirstTime = true;
	
	private Handler handler = new Handler();
	private Runnable runner;
	private int tabTypeInitially;
	private boolean isTaxiArraived = false;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_my_booking_details);
		initViews();
		setListener();
		serviceIntent = new Intent(MyBookingDetailsActivity.this, com.netcabs.servicehttppost.BroadcastServiceLocationChange.class);
		loadData();
		
	}
	
	private BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
		@Override
		public void onReceive(Context context, Intent intent) {
			updateUI(intent);
		}
	};
	
	private void updateUI(Intent intent) {
    	String status = intent.getStringExtra("status"); 
    	if (status.equals("2001")) {
    		if (PassengerApp.getInstance().getMyBookingInfo().getAcceptStatus() == 2) {
    			try {
    				isServiceRunning = false;
    				stopService(serviceIntent);
    				unregisterReceiver(broadcastReceiver);
    			} catch (Exception e) {
    				
    			}
    			
    			Toast.makeText(getApplicationContext(), "Booking cancelled from driver", Toast.LENGTH_SHORT).show();
    			finish();
    			return;
    			
    		} if (PassengerApp.getInstance().getMyBookingInfo().getBookingStatus() == 5) {
    			if(!isTaxiArraived) {
    				isTaxiArraived = true;
    				Toast.makeText(getApplicationContext(), "Taxi has arrived", Toast.LENGTH_SHORT).show();
    			}
    		}
    		
    		if (PassengerApp.getInstance().getMyBookingInfo().getBookingStatus() == 3) {
    			tabType = 3;
    			viewType = 4;
    			Log.e("Trip is ended ", PassengerApp.getInstance().getMyBookingInfo().getAcceptStatus()+"");
    			Toast.makeText(getApplicationContext(), "Your trip is succesfully ended", Toast.LENGTH_SHORT).show();
    			onBackPressed();
    			return;
    		}
    		
    		Log.i("Get TAXI CURRENT", "******" + PassengerApp.getInstance().getMyBookingInfo().getTaxiCurrentLat());
    		taxiLatLon = new LatLng(PassengerApp.getInstance().getMyBookingInfo().getTaxiCurrentLat(), PassengerApp.getInstance().getMyBookingInfo().getTaxiCurrentLon());
    		if(taxiLatLon != null) {
    			animateMarker(m, taxiLatLon, false);
    		}
    		tabType = PassengerApp.getInstance().getMyBookingInfo().getBookingStatus();
    		loadData();
    		
//    		if (previousLat != taxiLatLon.latitude || previousLon != taxiLatLon.longitude) {
//    			loadData();
//
//    		}

    	} else {
    		Toast.makeText(MyBookingDetailsActivity.this, "Network problem occured", Toast.LENGTH_SHORT).show();
    	}
    	
    }
	
	
	private void loadMap() {
		//map.clear();
		Marker pickUpMarker = map.addMarker(new MarkerOptions()
				.title("PickUp Point")
				.position(pickUpLatLon)
				.icon(BitmapDescriptorFactory.fromResource(R.drawable.pickup_icon))
				.snippet(pickUpName));
		//Set marker to current position
		map.setMyLocationEnabled(true);
		map.setOnMyLocationChangeListener(this);
		Marker destinationMarker = map.addMarker(new MarkerOptions()
				.title("Destination Point")
				.position(destinationLatLon)
				.icon(BitmapDescriptorFactory.fromResource(R.drawable.destination_icon))
				.snippet(destinationName));

		if (map != null) {
			CameraPosition cameraPosition = new CameraPosition.Builder()
					.target(pickUpLatLon).zoom(14) // Sets the zoom
					.tilt(0) // Sets the tilt of the camera to 30 degrees
					.build(); // Creates a CameraPosition from the builder

			map.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
			
			if(InternetConnectivity.isConnectedToInternet(MyBookingDetailsActivity.this)) {
				if (tabType == 0) {
					   if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
						   new GetRouteTask(taxiLatLon, pickUpLatLon, true).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
					   } else {
						   new GetRouteTask(taxiLatLon, pickUpLatLon, true).execute();
					   }
				} if (tabType == 1) {
					 if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
						 new GetRouteTask(taxiLatLon, destinationLatLon, true).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
					 } else {
						 new GetRouteTask(taxiLatLon, destinationLatLon, true).execute();
					 }
				} 
			} else {
				Toast.makeText(MyBookingDetailsActivity.this, ConstantValues.INTERNET_CONNECTION_FAILURE_MSG, Toast.LENGTH_SHORT).show();
			}
    		
			a = new MarkerOptions().position(taxiLatLon);
	    	a.icon(BitmapDescriptorFactory.fromResource(R.drawable.car_icon_g));
	    	a.title(taxiName);
	    	a.snippet(taxiNo);
    		m = map.addMarker(a);
    		
		} else {
			Log.i("Map is null", "_________________");
		}

	}

	private void initViews() {
		if (getIntent().getExtras() != null && getIntent().getExtras().getInt("VIEW_TYPE") == 0) {
			position = getIntent().getExtras().getInt("position");
		}else if(getIntent().getExtras() != null && getIntent().getExtras().getInt("VIEW_TYPE") != 0){	
			viewType = getIntent().getExtras().getInt("VIEW_TYPE");
		}
		if (getIntent().getExtras() != null) {
			tabTypeInitially = getIntent().getExtras().getInt("tab_type");
			tabType = getIntent().getExtras().getInt("tab_type");
		} else {
			
		}

		map = ((MapFragment) getFragmentManager().findFragmentById(R.id.map)).getMap();
		routeDirection = new GMapV2GetRouteDirection();
		imgViewTaxiLogo = (ImageView) findViewById(R.id.img_view_taxi_logo);
		txtViewTitle = (TextView) findViewById(R.id.txt_view_title);
		txtViewTime = (TextView) findViewById(R.id.txt_view_time);
		txtViewDate = (TextView) findViewById(R.id.txt_view_date);
		txtViewRegNo = (TextView) findViewById(R.id.txt_view_reg_no);
		txtViewPickupToDestination = (TextView) findViewById(R.id.txt_view_pickup_destination);
		txtViewDistance = (TextView) findViewById(R.id.txt_view_distance);
		txtViewEstimatedTime = (TextView) findViewById(R.id.txt_view_estimated_time);
		btnCancelOrShare = (Button) findViewById(R.id.btn_cancel_share);
		btnBack = (Button) findViewById(R.id.btn_back);
		
		txtViewPickupToDestination.setEnabled(true);
		//txtViewPickupToDestination.setSelected(true);
		
		
		txtViewPickupToDestination.addOnLayoutChangeListener(new OnLayoutChangeListener() {
		        @Override
		        public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {
		            android.view.ViewGroup.LayoutParams params = v.getLayoutParams();
		            params.width = right - left;
		            params.height = bottom - top;
		            //params.weight = 0;
		            v.removeOnLayoutChangeListener(this);
		            v.setLayoutParams(params);
		        }
		    });
		
		runner = new Runnable() {
		    public void run() {
		    	//txtViewPickupToDestination.requestFocus();
		       // handler.postDelayed(this, 100);
		        Log.i("FOUCS","OK");
		    }
		};
		handler.postDelayed(runner, 100);
		
	}

	private void setListener() {
		btnCancelOrShare.setOnClickListener(this);
		btnBack.setOnClickListener(this);
	}

	private void loadData() {
			switch (tabType) {
			case 0:
				txtViewTitle.setText("Current Booking");
				btnCancelOrShare.setBackgroundResource(R.drawable.contact_btn);
				loadInfoData(getIntent().getExtras().getInt("position"));
				break;

			case 1:
				txtViewTitle.setText("Current Trip");
				btnCancelOrShare.setBackgroundResource(R.drawable.share_btn);
				loadInfoData(getIntent().getExtras().getInt("position"));
				break;

			default:
				break;
			}
		
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_contact:
			break;

		case R.id.btn_cancel_share:
			if (tabType == 0) {
				if(driverMobileNo!=null){
					//Toast.makeText(MyBookingDetailsActivity.this, "Send message to the driver", Toast.LENGTH_SHORT).show();
					Intent smsIntent = new Intent(Intent.ACTION_VIEW);
					smsIntent.setType("vnd.android-dir/mms-sms");
					smsIntent.putExtra("address", driverMobileNo);
					//smsIntent.putExtra("sms_body","Body of Message");
					startActivity(smsIntent);
				}else{
					Toast.makeText(MyBookingDetailsActivity.this, "Driver mobile number is not available", Toast.LENGTH_SHORT).show();
				}
			} 
			isLock = true;
			if(tabType == 1){
					if(InternetConnectivity.isConnectedToInternet(MyBookingDetailsActivity.this)) {
						new ShareTripAsyncTask(MyBookingDetailsActivity.this, new OnRequestComplete() {
							
							@Override
							public void onRequestComplete(String result) {
								try {
									if("2001".equals(result)) {
										
										  Intent intent = new Intent(Intent.ACTION_SEND);
		
					                      intent.setType("text/plain");
					                      intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);
					                      intent.putExtra(Intent.EXTRA_SUBJECT, "My Current Trip");
					                      intent.putExtra(Intent.EXTRA_TEXT, PassengerApp.getInstance().getShareTripLink());
					                      startActivity(Intent.createChooser(intent, "Share link!"));
										
									} else {
										Toast.makeText(MyBookingDetailsActivity.this, "Request unable to perform", Toast.LENGTH_SHORT).show();
									}
								} catch (Exception e) {
									Toast.makeText(MyBookingDetailsActivity.this, "Response Error: (" + e.getMessage() + ").  Please try again", Toast.LENGTH_SHORT).show();
								}
							}
						}).execute(ConstantValues.FUNC_ID_SHARE_TRIP, PassengerApp.getInstance().getPassengerId(), PassengerApp.getInstance().getBookingId());
					}else {
						Toast.makeText(MyBookingDetailsActivity.this, ConstantValues.INTERNET_CONNECTION_FAILURE_MSG, Toast.LENGTH_SHORT).show();
					}
				}
			break;

		case R.id.btn_back:
			onBackPressed();
			break;

		default:
			break;
		}
	}

	private void loadInfoData(int position) {
		
		Log.i("VIEW_TYPE","VIEW_Type " + viewType);
		Log.i("IF FIRST TIME", "FIRST TIME " + isFirstTime);
		
		if(viewType == 1 || viewType == 2) {
			if(isFirstTime){
				callBookingDetailsAsynctask(PassengerApp.getInstance().getBookingId());
			} else {
				pickUpLatLon = new LatLng(PassengerApp.getInstance().getMyBookingInfo().getPickupLat(), PassengerApp.getInstance().getMyBookingInfo().getPickupLon());
				destinationLatLon = new LatLng(PassengerApp.getInstance().getMyBookingInfo().getDestinationLat(), PassengerApp.getInstance().getMyBookingInfo().getDestinationLon());
				destinationName = PassengerApp.getInstance().getMyBookingInfo().getDestinationName();
				pickUpName = PassengerApp.getInstance().getMyBookingInfo().getPickupName();
				taxiLatLon = new LatLng(PassengerApp.getInstance().getMyBookingInfo().getTaxiCurrentLat(), PassengerApp.getInstance().getMyBookingInfo().getTaxiCurrentLon());
				taxiName = PassengerApp.getInstance().getMyBookingInfo().getTaxi_name();
				taxiNo = PassengerApp.getInstance().getMyBookingInfo().getTaxi_number();
				setDistanceDuration();
			}
			
		} else if (viewType == 0) {
			//txtViewPickupToDestination.requestFocus();
			new AQuery(MyBookingDetailsActivity.this).id(imgViewTaxiLogo).image(PassengerApp.getInstance().getMyBookingInfoList().get(position).getTaxiLogoUrl(), true, true, 50, R.drawable.texi_logo);
			pickUpLatLon = new LatLng(PassengerApp.getInstance().getMyBookingInfoList().get(position).getPickupLat(), PassengerApp.getInstance().getMyBookingInfoList().get(position).getPickupLon());
			destinationLatLon = new LatLng(PassengerApp.getInstance().getMyBookingInfoList().get(position).getDestinationLat(), PassengerApp.getInstance().getMyBookingInfoList().get(position).getDestinationLon());
			taxiLatLon = new LatLng(PassengerApp.getInstance().getMyBookingInfoList().get(position).getTaxiCurrentLat(), PassengerApp.getInstance().getMyBookingInfoList().get(position).getTaxiCurrentLon());
			taxiName = PassengerApp.getInstance().getMyBookingInfoList().get(position).getTaxi_name();
			taxiNo = PassengerApp.getInstance().getMyBookingInfoList().get(position).getTaxi_number();
//			txtViewTime.setText(Utils.getTimeDateFormat(PassengerApp.getInstance().getMyBookingInfoList().get(position).getTime()));
//			txtViewDate.setText(Utils.getTimeDateFormat(PassengerApp.getInstance().getMyBookingInfoList().get(position).getDate()));
//			txtViewRegNo.setText(PassengerApp.getInstance().getMyBookingInfoList().get(position).getTaxiRegNo());
//			
//			if (isFirstTime) {
//				txtViewPickupToDestination.setText(PassengerApp.getInstance().getMyBookingInfoList().get(position).getPickupName() + " to " + PassengerApp.getInstance().getMyBookingInfoList().get(position).getDestinationName());
//			}
//			
//			txtViewDistance.setText("" + PassengerApp.getInstance().getMyBookingInfoList().get(position).getDistance());
//			txtViewEstimatedTime.setText(PassengerApp.getInstance().getMyBookingInfoList().get(position).getDurationTime());
//			destinationName = PassengerApp.getInstance().getMyBookingInfoList().get(position).getDestinationName();
//			pickUpName = PassengerApp.getInstance().getMyBookingInfoList().get(position).getPickupName();
			//loadMap();
			PassengerApp.getInstance().setBookingId(PassengerApp.getInstance().getMyBookingInfoList().get(position).getBookingId());
			//callBookingDetailsAsynctask(PassengerApp.getInstance().getBookingId());
			setDistanceDuration();

			
		} else {
			
		} 
	}

	private void setDistanceDuration() {
		if(tabType == 0){
			src = taxiLatLon ;
			desti = pickUpLatLon;
		} else if(tabType == 1){
			src = pickUpLatLon ;
			desti = destinationLatLon;
		}
		
		if(src !=null && desti !=null ) {
			if(InternetConnectivity.isConnectedToInternet(MyBookingDetailsActivity.this)) {
				if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
					new GetDurationAsyncTask(MyBookingDetailsActivity.this, src , desti,  new OnRequestComplete() {
						@Override
						public void onRequestComplete(String result) {
							try {
								Log.e("Duration is:", "::::"+result);
								String [] distanceDuration = result.split("//");
								PassengerApp.getInstance().getMyBookingInfo().setDistance(distanceDuration[0]);
								PassengerApp.getInstance().getMyBookingInfo().setDurationTime(distanceDuration[1]);
								Log.e("I am here", "---------");
							
								
								txtViewTime.setText(Utils.getTimeDateFormat(PassengerApp.getInstance().getMyBookingInfo().getTime()));
								txtViewDate.setText(Utils.getTimeDateFormat(PassengerApp.getInstance().getMyBookingInfo().getDate()));
								txtViewRegNo.setText(PassengerApp.getInstance().getMyBookingInfo().getTaxiRegNo());
								
								if (isFirstTime) {
									txtViewPickupToDestination.setText(PassengerApp.getInstance().getMyBookingInfo().getPickupName() + " to " + PassengerApp.getInstance().getMyBookingInfo().getDestinationName());
								}
								
								txtViewDistance.setText("" + PassengerApp.getInstance().getMyBookingInfo().getDistance());
								txtViewEstimatedTime.setText(PassengerApp.getInstance().getMyBookingInfo().getDurationTime());
								driverMobileNo = PassengerApp.getInstance().getMyBookingInfo().getDriverMobileNo();
							} catch (Exception e) {
								Toast.makeText(MyBookingDetailsActivity.this, "Response error: (" + e.getMessage() + "). Please try again" , Toast.LENGTH_SHORT).show();
							}
								
								try {
									if(isFirstTime) {
										loadMap();
									} else {
										if(InternetConnectivity.isConnectedToInternet(MyBookingDetailsActivity.this)) {
										  if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
											  new GetRouteTask(src, desti, true).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
										  } else {
											  new GetRouteTask(src, desti, true).execute();
										  }
										} else {
											Toast.makeText(MyBookingDetailsActivity.this, ConstantValues.INTERNET_CONNECTION_FAILURE_MSG, Toast.LENGTH_SHORT).show();
										}
									}
							} catch (Exception e) {
								Toast.makeText(MyBookingDetailsActivity.this, "Response error: (" + e.getMessage() + "). Please try again" , Toast.LENGTH_SHORT).show();
							}
							
						}
					}).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
					
				} else {
					new GetDurationAsyncTask(MyBookingDetailsActivity.this, src , desti,  new OnRequestComplete() {
						@Override
						public void onRequestComplete(String result) {
							try {
								Log.e("Duration is:", "::::"+result);
								String [] distanceDuration = result.split("//");
								PassengerApp.getInstance().getMyBookingInfo().setDistance(distanceDuration[0]);
								PassengerApp.getInstance().getMyBookingInfo().setDurationTime(distanceDuration[1]);
							
								new AQuery(MyBookingDetailsActivity.this).id(imgViewTaxiLogo).image(PassengerApp.getInstance().getMyBookingInfo().getTaxiLogoUrl(), true, true, 50, R.drawable.texi_logo);
								
								txtViewTime.setText(Utils.getTimeDateFormat(PassengerApp.getInstance().getMyBookingInfo().getTime()));
								txtViewDate.setText(Utils.getTimeDateFormat(PassengerApp.getInstance().getMyBookingInfo().getDate()));
								txtViewRegNo.setText(PassengerApp.getInstance().getMyBookingInfo().getTaxiRegNo());
								
								if (isFirstTime) {
									txtViewPickupToDestination.setText(PassengerApp.getInstance().getMyBookingInfo().getPickupName() + " to " + PassengerApp.getInstance().getMyBookingInfo().getDestinationName());
								}
								
								txtViewDistance.setText("" + PassengerApp.getInstance().getMyBookingInfo().getDistance());
								txtViewEstimatedTime.setText(PassengerApp.getInstance().getMyBookingInfo().getDurationTime());
								driverMobileNo = PassengerApp.getInstance().getMyBookingInfo().getDriverMobileNo();
								if(isFirstTime) {
									loadMap();
								} else {
									if(InternetConnectivity.isConnectedToInternet(MyBookingDetailsActivity.this)) {
									  if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
										  new GetRouteTask(src, desti, true).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
									  } else {
										  new GetRouteTask(src, desti, true).execute();
									  }
									} else {
										Toast.makeText(MyBookingDetailsActivity.this, ConstantValues.INTERNET_CONNECTION_FAILURE_MSG, Toast.LENGTH_SHORT).show();
									}
								}
							} catch (Exception e) {
								Toast.makeText(MyBookingDetailsActivity.this, "Response error: (" + e.getMessage() + "). Please try again" , Toast.LENGTH_SHORT).show();
							}
							
						}
					}).execute();
				}
			} else {
				Toast.makeText(MyBookingDetailsActivity.this, ConstantValues.INTERNET_CONNECTION_FAILURE_MSG, Toast.LENGTH_SHORT).show();
			}
		}
	}


	private void callBookingDetailsAsynctask(String bookingId) {
		if(InternetConnectivity.isConnectedToInternet(MyBookingDetailsActivity.this)) {
			new BookingDetailsAsyncTask(MyBookingDetailsActivity.this, new OnRequestComplete() {
				
				@Override
				public void onRequestComplete(String result) {
					
					try {
						if ("2001".equals(result)) {
							new AQuery(MyBookingDetailsActivity.this).id(imgViewTaxiLogo).image(PassengerApp.getInstance().getMyBookingInfo().getTaxiLogoUrl(), true, true, 50, R.drawable.texi_logo);
							pickUpLatLon = new LatLng(PassengerApp.getInstance().getMyBookingInfo().getPickupLat(), PassengerApp.getInstance().getMyBookingInfo().getPickupLon());
							destinationLatLon = new LatLng(PassengerApp.getInstance().getMyBookingInfo().getDestinationLat(), PassengerApp.getInstance().getMyBookingInfo().getDestinationLon());
							destinationName = PassengerApp.getInstance().getMyBookingInfo().getDestinationName();
							pickUpName = PassengerApp.getInstance().getMyBookingInfo().getPickupName();
							taxiLatLon = new LatLng(PassengerApp.getInstance().getMyBookingInfo().getTaxiCurrentLat(), PassengerApp.getInstance().getMyBookingInfo().getTaxiCurrentLon());
							taxiName = PassengerApp.getInstance().getMyBookingInfo().getTaxi_name();
							taxiNo = PassengerApp.getInstance().getMyBookingInfo().getTaxi_number();
							setDistanceDuration();
							
						} else {
							Log.e("Server response for booing details is", "::::::::::::::::" + result);
									
						}
					} catch (Exception e) {
						Toast.makeText(MyBookingDetailsActivity.this, "Response error: (" + e.getMessage() + "). Please try again" , Toast.LENGTH_SHORT).show();
					}
					
					
				}
				
			}).execute(ConstantValues.FUNC_ID_BOOKING_DETAILS, PassengerApp.getInstance().getPassengerId(), bookingId);
			
		}else {
			Toast.makeText(MyBookingDetailsActivity.this, ConstantValues.INTERNET_CONNECTION_FAILURE_MSG, Toast.LENGTH_SHORT).show();
		}
	}
	

	private class GetRouteTask extends AsyncTask<String, Void, String> {

		private ProgressDialog Dialog;
		String response = "";
		boolean isProgress = false;

		LatLng pickUpPosition, DestinationPostion;

		public GetRouteTask(LatLng pickUpPosition, LatLng DestinationPostion, boolean isProgressOn) {
			this.pickUpPosition = pickUpPosition;
			this.isProgress = isProgressOn;
			this.DestinationPostion = DestinationPostion;
		}

		@Override
		protected void onPreExecute() {
//			Dialog = new ProgressDialog(MyBookingDetailsActivity.this);
//			Dialog.setMessage("Loading...");
//			Dialog.setCancelable(false);
//			if(isProgress){
//				Dialog.show();
//			}
			
		}

		@Override
		protected String doInBackground(String... urls) {
			// Get All Route values
			try {
				doc = routeDirection.getDocument(pickUpPosition, DestinationPostion, GMapV2GetRouteDirection.MODE_DRIVING, "");
				response = "Success";
			} catch (Exception e) {
				
			}
			
			return response;

		}

		@Override
		protected void onPostExecute(String result) {
			if (response.equalsIgnoreCase("Success")) {
				
//				if (line!= null) {
//					line.remove();
//				}
//				if (Dialog.isShowing()) {
//					Dialog.dismiss();
//					
//				} 
	
				ArrayList<LatLng> directionPoint = routeDirection.getDirection(doc);
				LatLngBounds bounds = Utils.centerIncidentRouteOnMap(directionPoint);
//				if(previousLat == 0.0 && previousLon == 0.0){
//					map.animateCamera(CameraUpdateFactory.newLatLngBounds(bounds, 120));
//				} else {
//					
//				}
				
				rectLine = new PolylineOptions().width(10).color(Color.RED);
				
				for (int i = 0; i < directionPoint.size(); i++) {
					rectLine.add(directionPoint.get(i));
				}
				// Adding route on the map
				Polyline newline = map.addPolyline(rectLine);
				if (line!= null) {
					line.remove();
				}
				line = newline;
				
//				if (previousLat != 0.0 || previousLon != 0.0) {
//					animateMarker(m, taxiLatLon, false);
//				}
				
				previousLat = taxiLatLon.latitude;
				previousLon = taxiLatLon.longitude;
				
				if(isFirstTime) {
					map.animateCamera(CameraUpdateFactory.newLatLngBounds(bounds, 120));
					isFirstTime = false;
					isServiceRunning = true;
		    		startService(serviceIntent);
		    		registerReceiver(broadcastReceiver, new IntentFilter(com.netcabs.servicehttppost.BroadcastServiceLocationChange.BROADCAST_ACTION));
					
				} else{
					
				}
				
			}
			
		}
	}
	
//	
//	private class GetRouteTaskFromService extends AsyncTask<String, Void, String> {
//		String response = "";
//		LatLng pickUpPosition, DestinationPostion;
//
//		public GetRouteTaskFromService(LatLng pickUpPosition, LatLng DestinationPostion, boolean isProgressOn) {
//			this.pickUpPosition = pickUpPosition;
//			this.DestinationPostion = DestinationPostion;
//		}
//
//		@Override
//		protected String doInBackground(String... urls) {
//			doc = routeDirection.getDocument(pickUpPosition, DestinationPostion, GMapV2GetRouteDirection.MODE_DRIVING);
//			response = "Success";
//			return response;
//		}
//
//		@Override
//		protected void onPostExecute(String result) {
//			if (response.equalsIgnoreCase("Success")) {
//				
//				if (line!= null) {
//					line.remove();
//				}
//				
//				ArrayList<LatLng> directionPoint = routeDirection.getDirection(doc);
//				LatLngBounds bounds = Utils.centerIncidentRouteOnMap(directionPoint);
//				//map.animateCamera(CameraUpdateFactory.newLatLngBounds(bounds, 120));
//				
//				rectLine = new PolylineOptions().width(10).color(Color.RED);
//				
//				for (int i = 0; i < directionPoint.size(); i++) {
//					rectLine.add(directionPoint.get(i));
//				}
//				// Adding route on the map
//				line = map.addPolyline(rectLine);
//				
//				if (previousLat != taxiLatLon.latitude || previousLon != taxiLatLon.longitude) {
//					animateMarker(m, taxiLatLon, false);
//				}
//				
//				previousLat = taxiLatLon.latitude;
//				previousLon = taxiLatLon.longitude;
//				
//			}
//		
//		}
//	}
	
	@Override
	public void onBackPressed() {
		try {
			Log.e("I am in backpressed try","------");
			isServiceRunning = false;
			stopService(serviceIntent);
			unregisterReceiver(broadcastReceiver);
		
		
			if(tabTypeInitially!=tabType) {
				ConstantValues.isBackground = false;
			}
			
			if (viewType == 2) {
				ConstantValues.isComingFromHailedCabDetails = true;
				ConstantValues.isCommingFromConfirmBooking = false;
			} else if (viewType == 1 || viewType == 0) {
				if(tabType == 0 ) {
					ConstantValues.isComingFromHailedCabDetails = false;
					ConstantValues.isCommingFromConfirmBooking = true;
				} else if(tabType == 1){
					ConstantValues.isCommingFromConfirmBooking = false;
					ConstantValues.isComingFromHailedCabDetails = true;
				}
	//			if(PassengerApp.getInstance().getMyBookingInfo() != null) {
	//				if(PassengerApp.getInstance().getMyBookingInfo().getBookingStatus() == 0) {
	//					ConstantValues.isComingFromHailedCabDetails = false;
	//					ConstantValues.isCommingFromConfirmBooking = true;
	//				} else {
	//					ConstantValues.isCommingFromConfirmBooking = false;
	//					ConstantValues.isComingFromHailedCabDetails = true;
	//				}
	//			} else {
	//				ConstantValues.isComingFromHailedCabDetails = false;
	//				ConstantValues.isCommingFromConfirmBooking = true;
	//			}
			} else if (tabType==3 && viewType == 4) {
				ConstantValues.isCommingFromPast = true;
				ConstantValues.isCommingFromConfirmBooking = false;
				ConstantValues.isComingFromHailedCabDetails = false;
			}
			
			BaseActivity.isLock = true;
			super.onBackPressed();
		
		} catch (Exception e) {
			Log.e("I am in backpressed catch","------");
		}
		
	}
	
	@Override
	protected void onResume() {
		/*startService(serviceIntent);
		registerReceiver(broadcastReceiver, new IntentFilter(com.netcabs.servicehttppost.BroadcastServiceLocationChange.BROADCAST_ACTION));*/
		if (isLock) {
			Intent intent = new Intent(MyBookingDetailsActivity.this, LoginWithPinAuthActivity.class);
			intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivity(intent);
		}
		
		if(!isFirstTime){
			isServiceRunning = true;
			startService(serviceIntent);
			registerReceiver(broadcastReceiver, new IntentFilter(com.netcabs.servicehttppost.BroadcastServiceLocationChange.BROADCAST_ACTION));	
		}

		super.onResume();
	}
	
	@Override
	protected void onPause() {
		try{
			isServiceRunning = false;
			stopService(serviceIntent);
			unregisterReceiver(broadcastReceiver);
		} catch(Exception e) {
			
		}
		
		if (isLock) {
			isLock = false;
		} else {
			isLock = true;
		}
		super.onPause();
	}
	
	@Override
	protected void onDestroy() {
		try {
			isServiceRunning = false;
			stopService(serviceIntent);
			unregisterReceiver(broadcastReceiver);
		} catch (Exception e) {
			
		}
		
		isLock = false;
		super.onDestroy();
	}
	
	public void animateMarker(final Marker marker, final LatLng toPosition, final boolean hideMarker) {
	     final Handler handler = new Handler();
	     final long start = SystemClock.uptimeMillis();
	     com.google.android.gms.maps.Projection proj = map.getProjection();
	     Point startPoint = proj.toScreenLocation(marker.getPosition());
	     final LatLng startLatLng = proj.fromScreenLocation(startPoint);
	     final long duration = 500;
	     final Interpolator interpolator = new LinearInterpolator();
	
	     handler.post(new Runnable() {
	      long elapsed;
	      float t;
	      float v;
	         @Override
	         public void run() {
	             elapsed = SystemClock.uptimeMillis() - start;
	             t = interpolator.getInterpolation((float) elapsed/ duration);
	             v = interpolator.getInterpolation(t);
	             double lng = t * toPosition.longitude + (1 - t) * startLatLng.longitude;
	             double lat = t * toPosition.latitude + (1 - t) * startLatLng.latitude;
	             marker.setPosition(new LatLng(lat, lng));
	             //marker.setPosition();
	             if (t < 1.0) {
	         // Post again 16ms later.
	                 handler.postDelayed(this, 2);
	             } else {
	                 if (hideMarker) {
	                     marker.setVisible(false);
	                 } else {
	                     marker.setVisible(true);
	                 }
	             }
	         }
	     });
	     
	}


	@Override
	public void onMyLocationChange(Location location) {
		  double latitude = location.getLatitude();
	        // Getting longitude of the current location
	      double longitude = location.getLongitude();
	 
	      // Creating a LatLng object for the current location
	      LatLng curenLatLng = new LatLng(latitude, longitude);
	}
	  
}
