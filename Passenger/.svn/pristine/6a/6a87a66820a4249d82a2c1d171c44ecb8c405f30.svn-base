package com.netcabs.passenger;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.netcabs.asynctask.LocationByName;
import com.netcabs.customview.CustomAutoCompleteView;
import com.netcabs.datamodel.DestinationInfo;
import com.netcabs.datamodel.LocationSearchInfo;
import com.netcabs.datamodel.PickUpInfo;
import com.netcabs.interfacecallback.OnComplete;
import com.netcabs.internetconnection.InternetConnectivity;
import com.netcabs.latlon.GPSTracker;
import com.netcabs.passengerinfo.PassengerApp;
import com.netcabs.utils.ConstantValues;

import customlistener.CustomAutoCompleteTextChangedListener;


public class PickUpLocationSearchActivity extends Activity implements OnClickListener {

	public static CustomAutoCompleteView myAutoComplete;
	private static final String TAG_RESULT = "predictions";
	public TextView txtView; 
	
	private RelativeLayout relativeLayout;
	private Button btnBack;
	private int searchType;
	private LocationSearchInfo info;
	public boolean isLock = false;
	public GoogleMap googleMap;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_pick_up_location_search);
		
		initViews();
		initMap();
		setListener();
	}

	private void initMap() {
		 if (googleMap == null) {
			 googleMap = ((MapFragment) getFragmentManager().findFragmentById(R.id.map)).getMap();
		        
			 if (googleMap != null) {
		        	googleMap.setMyLocationEnabled(true);
		        	//googleMap.moveCamera(CameraUpdateFactory.newLatLng(new cur_Latlng));
		    		googleMap.animateCamera(CameraUpdateFactory.zoomTo(1));
		    		
		    		GPSTracker gps = new GPSTracker(PickUpLocationSearchActivity.this);
		    		double lat = gps.getLatitude();
		    		double lon = gps.getLongitude();

		    		if(lat!= 0.0 && lon!=0.0){
			    		CameraPosition cameraPosition = new CameraPosition.Builder()
			    				.target(new LatLng(lat, lon)) // Sets the
			    																// center of the
			    																// map to
			    																// Mountain View
			    				.zoom(14) // Sets the zoom
			    				.tilt(0) // Sets the tilt of the camera to 30 degrees
			    				.build(); // Creates a CameraPosition from the builder
			    		googleMap.animateCamera(CameraUpdateFactory
			    				.newCameraPosition(cameraPosition));
		    		}
		        }
		    }
	}

	private void initViews() {
		relativeLayout = (RelativeLayout) findViewById(R.id.relative_main);
		info = new LocationSearchInfo();
		
		if(getIntent().getExtras()!= null){
			searchType = getIntent().getExtras().getInt("SEARCH_TYPE",0);
			
		}else{
			
		}
		
		myAutoComplete = (CustomAutoCompleteView) findViewById(R.id.ed_txt_pickup_location);
		btnBack = (Button) findViewById(R.id.btn_back);
		
	}

	private void setListener() {
		relativeLayout.setOnClickListener(this);
	    myAutoComplete.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View arg1, int pos, long id) {
                 
                RelativeLayout rl = (RelativeLayout) arg1;
                final TextView tv = (TextView) rl.getChildAt(1);
                myAutoComplete.setText(tv.getText().toString().trim());
               // ConstantValues.locationNamel = tv.getText().toString().trim();
                Log.e("ref id is ", "----"+ConstantValues.refId.get(pos));
                
                PassengerApp.getInstance().hideKeyboard(PickUpLocationSearchActivity.this, arg1);
                Log.e("ref id is ", "----"+ConstantValues.refId.get(pos));
                if(InternetConnectivity.isConnectedToInternet(PickUpLocationSearchActivity.this)) {
                new LocationByName(PickUpLocationSearchActivity.this, new OnComplete() {
					@Override
					public void onLocationComplete(int result, double lat, double lon) {
						if(searchType == 0){
							LocationSearchInfo info = new LocationSearchInfo();
							info.setLocationName(tv.getText().toString().trim());
							info.setLocationLatitude(lat);
							info.setLocationLongitude(lon);
							Log.i("Value is ", "_____"+info.getLocationName() + info.getLocationLongitude() + info.getLocationLongitude());
							PassengerApp.getInstance().setSearchInfo(info);
						}
						else if(searchType == 1){
							PickUpInfo info = new PickUpInfo();
							info.setLocationName(tv.getText().toString().trim());
							info.setLocationLatitude(lat);
							info.setLocationLongitude(lon);
							Log.i("Value is ", "_____"+info.getLocationName() +info.getLocationLongitude() + info.getLocationLongitude());
							PassengerApp.getInstance().setPickUpLocationInfo(info);
						}
						else if(searchType == 2){
							DestinationInfo info = new DestinationInfo();
							info.setLocationName(tv.getText().toString().trim());
							info.setLocationLatitude(lat);
							info.setLocationLongitude(lon);
							Log.i("Value is ", "_____"+info.getLocationName() +info.getLocationLongitude() + info.getLocationLongitude());
							PassengerApp.getInstance().setDestinationInfo(info);
						}else{
							
						}
						
						Log.e("_ place lat & lon", info.getLocationName()+info.getLocationLatitude()+info.getLocationLongitude()+"");
						finish();
					}

					@Override
					public void onComplete(int result, ArrayList<String> data, ArrayList<String> refId) {
						
					}

					@Override
					public void onAddressComplete(int result, String address) {
						// TODO Auto-generated method stub
						
					}
					
				}, ConstantValues.refId.get(pos)).execute();
                }else{
                	Toast.makeText(PickUpLocationSearchActivity.this, ConstantValues.INTERNET_CONNECTION_FAILURE_MSG, Toast.LENGTH_SHORT).show();
                }
			
              
            }

        });
         
        // add the listener so it will tries to suggest while the user types
        myAutoComplete.addTextChangedListener(new CustomAutoCompleteTextChangedListener(this));
		btnBack.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.relative_main:
			PassengerApp.getInstance().hideKeyboard(PickUpLocationSearchActivity.this, v);
			break;
			
		case R.id.btn_back:
			finish();
			onBackPressed();
			break;

		default:
			break;
		}
	}
	
	
	@Override
	protected void onResume() {
		if (isLock) {
			Intent intent = new Intent(PickUpLocationSearchActivity.this, LoginWithPinAuthActivity.class);
			intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivity(intent);
		}
		super.onResume();
	}
	
	@Override
	public void onPause(){
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
