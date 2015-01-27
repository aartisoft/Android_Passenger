package com.netcabs.passenger;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import android.app.Activity;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnCameraChangeListener;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;

import com.netcabs.adapter.FavoriteAdapter;
import com.netcabs.asynctask.FavoriateListAsyncTask;
import com.netcabs.asynctask.FavouriteDestinationAsyncTask;
import com.netcabs.datamodel.DestinationInfo;
import com.netcabs.interfacecallback.OnRequestComplete;
import com.netcabs.internetconnection.InternetConnectivity;
import com.netcabs.latlon.GPSTracker;
import com.netcabs.passengerinfo.PassengerApp;
import com.netcabs.utils.ConstantValues;

public class DestinationAddressActivity extends Activity implements OnItemClickListener, OnClickListener, OnCheckedChangeListener {

	private RadioButton radioBtnAddress;
	private RadioButton radioBtnFavorite;
	private Button btnBack;
	private Button btnConfirm;
	private EditText edTxtDestinationAddress;
	private Button btnFavorite;
	private RelativeLayout relativeLayout;
	private RelativeLayout relativeLayoutMain;
	private ListView lstViewAddress;
	private double mcurrent_lat;
	private double mcurrent_lon;
	private DestinationInfo destinationInfo;
	private FavoriteAdapter favAdapter;
	
	private GoogleMap map;
	private Geocoder gcd;
	
	private boolean isFavorite = false;
	private boolean isLock = false;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_destination_address);
		
		initViews();
		setListener();
		initMap();
		loadData();
		
	}

	private void initViews() {
		destinationInfo = new DestinationInfo();
		 
		radioBtnAddress = (RadioButton) findViewById(R.id.radio_btn_address);
		radioBtnFavorite = (RadioButton) findViewById(R.id.radio_btn_favorite);
		
		btnBack = (Button) findViewById(R.id.btn_back);
		btnFavorite = (Button) findViewById(R.id.btn_favorite);
		btnConfirm = (Button) findViewById(R.id.btn_confirm);
		
		edTxtDestinationAddress = (EditText) findViewById(R.id.ed_txt_destination);
		relativeLayoutMain = (RelativeLayout) findViewById(R.id.relative_main);
		relativeLayout = (RelativeLayout) findViewById(R.id.relative_center);
		
		lstViewAddress = (ListView) findViewById(R.id.lst_view_address);
		
		
	}

	private void setListener() {
		btnBack.setOnClickListener(this);
		btnFavorite.setOnClickListener(this);
		btnConfirm.setOnClickListener(this);
		edTxtDestinationAddress.setOnClickListener(this);
		lstViewAddress.setOnItemClickListener(this);
		relativeLayoutMain.setOnClickListener(this);
		((RadioGroup) findViewById(R.id.radio_group)).setOnCheckedChangeListener(this);
	}
	
	private void initMap() {
		gcd = new Geocoder(DestinationAddressActivity.this, Locale.getDefault());
		destinationInfo = new DestinationInfo();
		map = ((MapFragment) getFragmentManager().findFragmentById(R.id.map)).getMap();
	
		final Geocoder gcd = new Geocoder(DestinationAddressActivity.this, Locale.getDefault());
		final LatLng cur_Latlng;
		
		if(PassengerApp.getInstance().getDestinationInfo() == null ) {
			GPSTracker gps = new GPSTracker(DestinationAddressActivity.this);
			mcurrent_lat = gps.getLatitude();
			mcurrent_lon = gps.getLongitude();
			cur_Latlng = new LatLng(mcurrent_lat, mcurrent_lon);
		} else{
			cur_Latlng = new LatLng(PassengerApp.getInstance().getDestinationInfo().getLocationLatitude(), PassengerApp.getInstance().getDestinationInfo().getLocationLongitude());
		}
		
		map.setOnCameraChangeListener(new OnCameraChangeListener() {
			@Override
			public void onCameraChange(CameraPosition arg0) {
				new GeoAddressAsyncTask(arg0.target.latitude, arg0.target.longitude).execute();
				/*//googleMap.clear();*/
				
			}
			
		});
		
		if(map != null) {
			map.moveCamera(CameraUpdateFactory.newLatLng(cur_Latlng));
			map.animateCamera(CameraUpdateFactory.zoomTo(14.0f));
			
			//Set marker to current position
			/*final Marker myplace = googleMap.addMarker(new MarkerOptions()
					.title("My Place Test")
					.position(cur_Latlng)
					.flat(true)
					.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)));*/
			
		} else {
			Log.i("Map is null", "_________________");
		}
		
	}


	private void loadData() {
		
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_back:
			onBackPressed();
			break;
			
		case R.id.relative_main:
			PassengerApp.getInstance().hideKeyboard(DestinationAddressActivity.this, v);
			break;
			
		case R.id.btn_favorite:
			if(InternetConnectivity.isConnectedToInternet(DestinationAddressActivity.this)) {
				if(!isFavorite) {
					new FavouriteDestinationAsyncTask(DestinationAddressActivity.this, new OnRequestComplete() {
						
						@Override
						public void onRequestComplete(String result) {
							try {
								if("2001".equals(result)) {
									isFavorite = true;
									Toast.makeText(DestinationAddressActivity.this, "Successfully Updated !", Toast.LENGTH_SHORT).show();
								} else if("3001".equals(result)) {
									
								} else if("4001".equals(result)) {
													
								} else {
									
								} 
							} catch (Exception e) {
								Toast.makeText(DestinationAddressActivity.this, "Response Error: (" + e.getMessage() + ").  Please try again", Toast.LENGTH_SHORT).show();
							}
						}
					}).execute(ConstantValues.FUNC_ID_FAVOURITE_DESTINATION, PassengerApp.getInstance().getPassengerId(), Double.toString(destinationInfo.getLocationLongitude()),Double.toString(destinationInfo.getLocationLatitude()), edTxtDestinationAddress.getText().toString().trim(), "2", "1" );
				} else {
					Toast.makeText(DestinationAddressActivity.this, "No update", Toast.LENGTH_SHORT).show();
				}
				
			} else {
				Toast.makeText(DestinationAddressActivity.this, ConstantValues.INTERNET_CONNECTION_FAILURE_MSG, Toast.LENGTH_SHORT).show();
			}
			break;
			
		case R.id.btn_confirm:
			PassengerApp.getInstance().setDestinationInfo(destinationInfo);
			finish();
			break;
			
		case R.id.ed_txt_destination:
			isLock = true;
			PassengerApp.getInstance().hideKeyboard(DestinationAddressActivity.this, v);
			startActivity(new Intent(DestinationAddressActivity.this, PickUpLocationSearchActivity.class).putExtra("SEARCH_TYPE", 2));
			break;
			
			
		default:
			break;
		}
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		destinationInfo = new DestinationInfo();
		destinationInfo.setLocationName(PassengerApp.getInstance().getFavoriteInfoList().get(arg2).getLocationName());
		destinationInfo.setLocationLatitude(Double.parseDouble(PassengerApp.getInstance().getFavoriteInfoList().get(arg2).getLocationLatitude()));
		destinationInfo.setLocationLongitude(Double.parseDouble(PassengerApp.getInstance().getFavoriteInfoList().get(arg2).getLocationLongitude()));
		PassengerApp.getInstance().setDestinationInfo(destinationInfo);
		//onCheckedChanged(((RadioGroup) findViewById(R.id.radio_group)), 0);
		relativeLayout.setVisibility(View.VISIBLE);
		lstViewAddress.setVisibility(View.INVISIBLE);
		radioBtnAddress.setButtonDrawable(R.drawable.address_tab);
		radioBtnFavorite.setButtonDrawable(R.drawable.faviorite_tab_inactive);
		radioBtnAddress.setChecked(true);
		initMap();
	}

	@Override
	public void onCheckedChanged(RadioGroup group, int checkedId) {
		switchTab();
	}
	
	private void switchTab() {
		int selectedTab = ((RadioGroup) findViewById(R.id.radio_group)).getCheckedRadioButtonId();
		
		switch (selectedTab) {
			case R.id.radio_btn_address:
				relativeLayout.setVisibility(View.VISIBLE);
				lstViewAddress.setVisibility(View.INVISIBLE);
				radioBtnAddress.setButtonDrawable(R.drawable.address_tab);
				radioBtnFavorite.setButtonDrawable(R.drawable.faviorite_tab_inactive);
				break;
				
			case R.id.radio_btn_favorite:
				relativeLayout.setVisibility(View.INVISIBLE);
				lstViewAddress.setVisibility(View.VISIBLE);
				radioBtnAddress.setButtonDrawable(R.drawable.address_tab_inactive);
				radioBtnFavorite.setButtonDrawable(R.drawable.faviorite_tab);
				if(InternetConnectivity.isConnectedToInternet(DestinationAddressActivity.this)) {
					new FavoriateListAsyncTask(DestinationAddressActivity.this, new OnRequestComplete() {
						
						@Override
						public void onRequestComplete(String result) {
							
							try {
								if("2001".equals(result)) {
									if(PassengerApp.getInstance().getFavoriteInfoList() != null) {
										favAdapter = new FavoriteAdapter(DestinationAddressActivity.this, PassengerApp.getInstance().getFavoriteInfoList(), true);
										lstViewAddress.setAdapter(favAdapter);
									} else {
										lstViewAddress.setAdapter(null);
									}
									
								} else if("3001".equals(result)) {
									
								} else if("4001".equals(result)) {
													
								} else {
									
								}
							} catch (Exception e) {
								Toast.makeText(DestinationAddressActivity.this, "Response error: (" + e.getMessage() + "). Please try again" , Toast.LENGTH_SHORT).show();
							}
							
						}
					}).execute(ConstantValues.FUNC_ID_FAVOURITE_DESTINATION_LIST, PassengerApp.getInstance().getPassengerId(), "2");
				} else {
					Toast.makeText(DestinationAddressActivity.this, ConstantValues.INTERNET_CONNECTION_FAILURE_MSG, Toast.LENGTH_SHORT).show();
				}
				break;
				
			default:
				break;
		}
	}
	
	@Override
    public void onResume() {
        super.onResume();
        initMap();
        if(PassengerApp.getInstance().getDestinationInfo() != null) {
        	if(map != null) {
        		map.clear();
		    	LatLng latLng = new LatLng(PassengerApp.getInstance().getDestinationInfo().getLocationLatitude(), PassengerApp.getInstance().getDestinationInfo().getLocationLongitude());
		    	map.moveCamera(CameraUpdateFactory.newLatLng(latLng));
		    	map.animateCamera(CameraUpdateFactory.zoomTo(14.0f));
		    	edTxtDestinationAddress.setText(PassengerApp.getInstance().getDestinationInfo().getLocationName());
		    	Log.e("lat , long & place name", "--------"+ PassengerApp.getInstance().getDestinationInfo().getLocationLatitude()+":::"+ PassengerApp.getInstance().getDestinationInfo().getLocationLongitude()+"::::"+PassengerApp.getInstance().getDestinationInfo().getLocationName());
        	}
        } else {
        	Log.e("lat , long & place name", "--------i am in else");
        }
        
        if (isLock) {
			Intent intent = new Intent(DestinationAddressActivity.this, LoginWithPinAuthActivity.class);
			intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivity(intent);
		}
        
    }
	
	
	private class GeoAddressAsyncTask extends AsyncTask<Void, Void, Void> {
		double Lat, Lon;
		List<Address> addresses = null;
		
		public GeoAddressAsyncTask(double Lat, double Lon) {
			this.Lat = Lat;
			this.Lon = Lon;
		}
	
	
		@Override
		protected void onPreExecute() {
		}

		@Override
		protected Void doInBackground(Void... arg0) {

			try {
				addresses = gcd.getFromLocation(Lat, Lon, 1);
			
			} catch (IOException e) {
				e.printStackTrace();
			}
			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			
			if (addresses != null) {
				if (addresses.size() > 0) {
					isFavorite = false;
			//		Log.e("locality & country name", ""+addresses.get(0).getLocality()+"///"+addresses.get(0).getCountryName()+"///"+addresses.get(0).getFeatureName());
					
					String address = addresses.get(0).getAddressLine(0) + ", " + addresses.get(0).getLocality() + ", " + addresses.get(0).getAdminArea();
//					String address = "";
					/*for(int i = 0; i<addresses.get(0).getMaxAddressLineIndex(); i++){
						if(address.equals("")){
							address = addresses.get(0).getAddressLine(i);
						} else {
							address = address+", "+addresses.get(0).getAddressLine(i);
						}
						
					}*/
					
					String addressWithoutNullValue = address.replace("null", "");
					Log.e("Address is 1", ""+addressWithoutNullValue);
					String formatedAddress = "";
					formatedAddress = addressWithoutNullValue.replace(", ,", ",");
					Log.e("Address is 2", ""+formatedAddress);
					if (formatedAddress.length() > 0 && formatedAddress.charAt(formatedAddress.length()-1)==',') {
						formatedAddress = formatedAddress.substring(0, formatedAddress.length()-2);
					}
					edTxtDestinationAddress.setText(formatedAddress);
					destinationInfo.setLocationName(formatedAddress);
					destinationInfo.setLocationLatitude(Lat);
					destinationInfo.setLocationLongitude(Lon);
				} else {
					Log.i("Value is null", "______empty__________");
				}
				   
			} else {
				Log.i("Value is null", "________________");
			}
			
			
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
    
}
