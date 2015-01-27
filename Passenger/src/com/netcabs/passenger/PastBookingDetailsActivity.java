package com.netcabs.passenger;


import java.util.ArrayList;

import org.w3c.dom.Document;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.netcabs.asynctask.PastBookingDetailsAsyncTask;
import com.netcabs.interfacecallback.OnRequestComplete;
import com.netcabs.internetconnection.InternetConnectivity;
import com.netcabs.passengerinfo.PassengerApp;
import com.netcabs.utils.ConstantValues;
import com.netcabs.utils.GMapV2GetRouteDirection;
import com.netcabs.utils.Utils;

public class PastBookingDetailsActivity extends Activity implements OnClickListener {

	private Button btnBack;
	
	private TextView txtViewPassengerName;
	private TextView txtViewPassengerEmail;
	private TextView txtViewDriverName;
	private TextView txtViewRegNo;
	private TextView txtViewPickupTime;
	private TextView txtViewDropOfTime;
	private TextView txtViewTotalTripTime;
	private TextView txtViewDistance;
	private TextView txtViewPickupLocation;
	private TextView txtViewDropofLocation;
	private TextView txtViewFarePrice;
	private TextView txtViewExtrasValue;
	private TextView txtViewGstValue;
	private TextView txtViewTotalAmount;
	private TextView txtViewType;
	private TextView txtViewDate;
	private TextView txtViewTotal;
	
	private RelativeLayout relative_top;
	private RelativeLayout relative_main;
	private GoogleMap map;
	
	private ArrayList<LatLng> journeyDirectionList;
	private int position;
	private String wayPoints = "";
	private PolylineOptions rectLine;
	private boolean isLock = false;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_past_booking_details);
		initViews();
		setListener();
		loadData();
	}

	private void initViews() {
		btnBack = (Button) findViewById(R.id.btn_back);
		relative_top = (RelativeLayout) findViewById(R.id.relative_top);
		relative_main = (RelativeLayout) findViewById(R.id.relative_main);
		
		txtViewPassengerName = (TextView) findViewById(R.id.txt_view_passenger_name);
		txtViewPassengerEmail = (TextView) findViewById(R.id.txt_view_passenger_email);
		txtViewDriverName = (TextView) findViewById(R.id.txt_view_driver_name);
		txtViewRegNo = (TextView) findViewById(R.id.txt_view_reg_no);
		txtViewPickupTime = (TextView) findViewById(R.id.txt_view_pickup_time);
		txtViewDropOfTime = (TextView) findViewById(R.id.txt_view_drop_of_time);
		txtViewDistance = (TextView) findViewById(R.id.txt_view_distance_time);
		txtViewTotalTripTime = (TextView) findViewById(R.id.txt_view_total_trip_time);
		txtViewPickupLocation = (TextView) findViewById(R.id.txt_view_pickup_location_name);
		txtViewDropofLocation = (TextView) findViewById(R.id.txt_view_drop_of_location_name);
		txtViewFarePrice = (TextView) findViewById(R.id.txt_view_fare_price);
		txtViewExtrasValue = (TextView) findViewById(R.id.txt_view_extras_value);
		txtViewGstValue = (TextView) findViewById(R.id.txt_view_gst_value);
		txtViewTotalAmount = (TextView) findViewById(R.id.txt_view_total_amount_value);
		txtViewType = (TextView) findViewById(R.id.txt_view_type);
		txtViewDate = (TextView) findViewById(R.id.txt_view_date);
		txtViewTotal = (TextView) findViewById(R.id.txt_view_total);
		
	}

	private void setListener() {
		btnBack.setOnClickListener(this);
	}

	private void loadData() {
		
		if (getIntent().getExtras() != null) {
			position = getIntent().getExtras().getInt("position", 0);
		}
		
		//Log.i("PAST DETAILS ID"," user:" + PassengerApp.getInstance().getPassengerId() + " booking id: " + PassengerApp.getInstance().getMyBookingInfoList().get(position).getBookingId());
		
		map = ((MapFragment) getFragmentManager().findFragmentById(R.id.map)).getMap();
		
		if(InternetConnectivity.isConnectedToInternet(PastBookingDetailsActivity.this)) {
			new PastBookingDetailsAsyncTask(PastBookingDetailsActivity.this, new OnRequestComplete() {
				@Override
				public void onRequestComplete(String result) {
					try {
						if ("2001".equals(result)) {
							txtViewPassengerName.setText(PassengerApp.getInstance().getPastBookingDetailsInfo().getPassengerName());
							txtViewPassengerEmail.setText(PassengerApp.getInstance().getPastBookingDetailsInfo().getPassengerEmail());
							txtViewDriverName.setText(PassengerApp.getInstance().getPastBookingDetailsInfo().getDriverName());
							txtViewRegNo.setText(PassengerApp.getInstance().getPastBookingDetailsInfo().getRegNo());
							
							if (PassengerApp.getInstance().getPastBookingDetailsInfo().getPickUpTime().contains("/")) {
								txtViewPickupTime.setText(PassengerApp.getInstance().getPastBookingDetailsInfo().getPickUpTime().split(" ")[1]);
							} else {
								txtViewPickupTime.setText(PassengerApp.getInstance().getPastBookingDetailsInfo().getPickUpTime());
							}
							
							if (PassengerApp.getInstance().getPastBookingDetailsInfo().getDropOfTime().contains("/")) {
								txtViewDropOfTime.setText(PassengerApp.getInstance().getPastBookingDetailsInfo().getDropOfTime().split(" ")[1]);
							} else {
								txtViewDropOfTime.setText(PassengerApp.getInstance().getPastBookingDetailsInfo().getDropOfTime());
							}
							
							txtViewTotalTripTime.setText(PassengerApp.getInstance().getPastBookingDetailsInfo().getTotalTripTime());
							txtViewDistance.setText(PassengerApp.getInstance().getPastBookingDetailsInfo().getDistance());
							txtViewPickupLocation.setText(PassengerApp.getInstance().getPastBookingDetailsInfo().getPickupAddress());
							txtViewDropofLocation.setText(PassengerApp.getInstance().getPastBookingDetailsInfo().getDropOfAddress());
			
							txtViewType.setText(PassengerApp.getInstance().getPastBookingDetailsInfo().getPaymentType());
							txtViewDate.setText(PassengerApp.getInstance().getPastBookingDetailsInfo().getPaymentDate().split(" ")[0]);
							
							/*Double farePrice = 0.00;
							
							if(PassengerApp.getInstance().getPastBookingDetailsInfo().getDistance().contains(",")){
								PassengerApp.getInstance().getPastBookingDetailsInfo().setDistance(PassengerApp.getInstance().getPastBookingDetailsInfo().getDistance().replace(",", ""));
							}
							
							Log.e("distance", PassengerApp.getInstance().getPastBookingDetailsInfo().getDistance());
							if (PassengerApp.getInstance().getPastBookingDetailsInfo().getDistance().split(" ")[0] != null || !PassengerApp.getInstance().getPastBookingDetailsInfo().getDistance().split(" ")[0].equalsIgnoreCase("null") || !"".equalsIgnoreCase(PassengerApp.getInstance().getPastBookingDetailsInfo().getDistance().split(" ")[0])) {
								farePrice = Double.parseDouble(PassengerApp.getInstance().getPastBookingDetailsInfo().getDistance().split(" ")[0]) * 10;
							}
							
							Double GST = farePrice * 10 / 100;
							Double ExtrasValue  =((farePrice * 5)/100);
							
							txtViewFarePrice.setText("" + String.format("%.2f", farePrice));
							txtViewGstValue.setText("" + String.format("%.2f", GST));
							txtViewExtrasValue.setText("" + String.format("%.2f", ExtrasValue));
							txtViewTotalAmount.setText("" + String.format("%.2f", (farePrice + GST + ExtrasValue)));
							txtViewTotal.setText("" + String.format("%.2f", (farePrice + GST + ExtrasValue)));*/
							
							txtViewFarePrice.setText("$" + PassengerApp.getInstance().getPastBookingDetailsInfo().getFarePrice());
							txtViewGstValue.setText("$" + PassengerApp.getInstance().getPastBookingDetailsInfo().getGST());
							txtViewExtrasValue.setText("$" + PassengerApp.getInstance().getPastBookingDetailsInfo().getExtras());
							txtViewTotalAmount.setText("$" + PassengerApp.getInstance().getPastBookingDetailsInfo().getTotalAmount());
							txtViewTotal.setText("$" + PassengerApp.getInstance().getPastBookingDetailsInfo().getTotalAmount());
							
							if(PassengerApp.getInstance().getPastBookingDetailsInfo().getJourneyCoordinateList() != null) {
								journeyDirectionList = new ArrayList<LatLng>();
								for (int i = 0; i < PassengerApp.getInstance().getPastBookingDetailsInfo().getJourneyCoordinateList().size(); i++) {
									journeyDirectionList.add(new LatLng(PassengerApp.getInstance().getPastBookingDetailsInfo().getJourneyCoordinateList().get(i).getJourneyLatitude(), PassengerApp.getInstance().getPastBookingDetailsInfo().getJourneyCoordinateList().get(i).getJourneyLongitude()));
									wayPoints = wayPoints+"|"+PassengerApp.getInstance().getPastBookingDetailsInfo().getJourneyCoordinateList().get(i).getJourneyLatitude()+","+PassengerApp.getInstance().getPastBookingDetailsInfo().getJourneyCoordinateList().get(i).getJourneyLongitude()+"|";
									Circle circle = map.addCircle(new CircleOptions()
									.center(new LatLng(PassengerApp.getInstance().getPastBookingDetailsInfo().getJourneyCoordinateList().get(i).getJourneyLatitude(),
											PassengerApp.getInstance().getPastBookingDetailsInfo().getJourneyCoordinateList().get(i).getJourneyLongitude())).radius(2.5)
									.strokeColor(Color.RED).fillColor(Color.GREEN));
								}
							}
							
							Marker pickUpMarker = map.addMarker(new MarkerOptions()
							.title("PickUp Point")
							.position(new LatLng(PassengerApp.getInstance().getPastBookingDetailsInfo().getPickUpLocationLatitude(), PassengerApp.getInstance().getPastBookingDetailsInfo().getPickUpLocationLongitude()))
							.icon(BitmapDescriptorFactory.fromResource(R.drawable.pickup_icon))
							.snippet(PassengerApp.getInstance().getPastBookingDetailsInfo().getPickupAddress()));
		
							Marker destinationMarker = map.addMarker(new MarkerOptions()
							.title("Destination Point")
							.position(new LatLng(PassengerApp.getInstance().getPastBookingDetailsInfo().getDestinationLocationLatitude(), PassengerApp.getInstance().getPastBookingDetailsInfo().getDestinationLocationLongitude()))
							.icon(BitmapDescriptorFactory.fromResource(R.drawable.destination_icon))
							.snippet(PassengerApp.getInstance().getPastBookingDetailsInfo().getDropOfAddress()));
							
							
							
							if (map != null) {
								CameraPosition cameraPosition = new CameraPosition.Builder()
								.target(new LatLng(PassengerApp.getInstance().getPastBookingDetailsInfo().getPickUpLocationLatitude(), PassengerApp.getInstance().getPastBookingDetailsInfo().getPickUpLocationLongitude())).zoom(14) // Sets the zoom
								.tilt(0) // Sets the tilt of the camera to 30 degrees
								.build(); // Creates a CameraPosition from the builder
								map.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
								
							} else {
								Log.i("Map is null", "_________________");
							}
							
							if(journeyDirectionList != null) {
							/*	drawTaxiPath(journeyDirectionList);
								Toast.makeText(PastBookingDetailsActivity.this, "Actual Path is drawn", Toast.LENGTH_LONG).show();*/
							} else {
							//	Toast.makeText(PastBookingDetailsActivity.this, "Shortest path is drawn", Toast.LENGTH_LONG).show();
								if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
									new GetRouteTask(new LatLng(PassengerApp.getInstance().getPastBookingDetailsInfo().getPickUpLocationLatitude(), PassengerApp.getInstance().getPastBookingDetailsInfo().getPickUpLocationLongitude()), new LatLng(PassengerApp.getInstance().getPastBookingDetailsInfo().getDestinationLocationLatitude(), PassengerApp.getInstance().getPastBookingDetailsInfo().getDestinationLocationLongitude()), true).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
								 } else {
								   new GetRouteTask(new LatLng(PassengerApp.getInstance().getPastBookingDetailsInfo().getPickUpLocationLatitude(), PassengerApp.getInstance().getPastBookingDetailsInfo().getPickUpLocationLongitude()), new LatLng(PassengerApp.getInstance().getPastBookingDetailsInfo().getDestinationLocationLatitude(), PassengerApp.getInstance().getPastBookingDetailsInfo().getDestinationLocationLongitude()), true).execute();
								 }
							}
				
						} else {
							Toast.makeText(PastBookingDetailsActivity.this, "Data not found", Toast.LENGTH_LONG).show();
						}
						
					} catch (Exception e) {
						Toast.makeText(PastBookingDetailsActivity.this, "Response Error: (" + e.getMessage() + ").  Please try again", Toast.LENGTH_SHORT).show();
					}
				}
				
			}).execute(ConstantValues.FUNC_ID_PAST_BOOKING_DETAILS, PassengerApp.getInstance().getPassengerId(), PassengerApp.getInstance().getMyBookingInfoList().get(position).getBookingId());
		} else {
			Toast.makeText(PastBookingDetailsActivity.this, ConstantValues.INTERNET_CONNECTION_FAILURE_MSG, Toast.LENGTH_SHORT).show();
		}
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_back:
			onBackPressed();
			break;

		default:
			break;
		}
	}
	
	private void drawTaxiPath(ArrayList<LatLng> taxiPathArray) {
		LatLngBounds bounds = Utils.centerIncidentRouteOnMap(taxiPathArray);
		rectLine = new PolylineOptions().width(5).color(Color.RED);
		for (int i = 0; i < taxiPathArray.size(); i++) {
			rectLine.add(taxiPathArray.get(i));
		}
		map.addPolyline(rectLine);
		
	}
	
	private class GetRouteTask extends AsyncTask<String, Void, String> {

		private ProgressDialog pathLoadingProgress;
		String response = "";
		boolean isProgress = false;
		private GMapV2GetRouteDirection routeDirection;
		private Document doc;
		private PolylineOptions rectLine;
		private Polyline line;

		LatLng pickUpPosition, DestinationPostion;

		public GetRouteTask(LatLng pickUpPosition, LatLng DestinationPostion, boolean isProgressOn) {
			this.pickUpPosition = pickUpPosition;
			this.isProgress = isProgressOn;
			this.DestinationPostion = DestinationPostion;
			this.routeDirection = new GMapV2GetRouteDirection();
		}

		@Override
		protected void onPreExecute() {
			pathLoadingProgress = new ProgressDialog(PastBookingDetailsActivity.this);
			pathLoadingProgress.setMessage("Loading...");
			pathLoadingProgress.setCancelable(false);
			if(isProgress){
				pathLoadingProgress.show();
			}
			
		}

		@Override
		protected String doInBackground(String... urls) {
			// Get All Route values
			try {
				doc = routeDirection.getDocument(pickUpPosition, DestinationPostion, GMapV2GetRouteDirection.MODE_DRIVING, wayPoints.substring(1, wayPoints.length() - 1).replace("||", "|"));
				response = "Success";
			} catch (Exception e) {
				response = "Error";
			}
			
			return response;

		}

		@Override
		protected void onPostExecute(String result) {
			
			try {
				if (pathLoadingProgress.isShowing()) {
					pathLoadingProgress.dismiss();

				}
				
				if (response.equalsIgnoreCase("Success")) {
		
					ArrayList<LatLng> directionPoint = routeDirection.getDirection(doc);
					LatLngBounds bounds = Utils.centerIncidentRouteOnMap(directionPoint);

					rectLine = new PolylineOptions().width(5).color(Color.RED);
					
					for (int i = 0; i < directionPoint.size(); i++) {
						rectLine.add(directionPoint.get(i));
					}
					// Adding route on the map
					line = map.addPolyline(rectLine);
					
				}
			} catch (Exception e ) {
				Toast.makeText(PastBookingDetailsActivity.this, "Response error: (" + e.getMessage() + "). Please try again" , Toast.LENGTH_SHORT).show();
			}
			
		}
	}
	 
	@Override
	protected void onResume() {
		if (isLock) {
			Intent intent = new Intent(PastBookingDetailsActivity.this, LoginWithPinAuthActivity.class);
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
