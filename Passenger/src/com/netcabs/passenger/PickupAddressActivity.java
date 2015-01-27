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
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
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
import com.netcabs.datamodel.PickUpInfo;
import com.netcabs.interfacecallback.OnRequestComplete;
import com.netcabs.internetconnection.InternetConnectivity;
import com.netcabs.latlon.GPSTracker;
import com.netcabs.passengerinfo.PassengerApp;
import com.netcabs.utils.BaseActivity;
import com.netcabs.utils.ConstantValues;

public class PickupAddressActivity extends Activity implements OnClickListener, OnItemClickListener,  OnCheckedChangeListener  {

	private Button btnBack;
	private Button btnConfirm;
	private EditText edTxtPickupAddress;
	private GoogleMap map;
	private Geocoder gcd;
	
	private RadioButton radioBtnAddress;
	private RadioButton radioBtnFavorite;
	private ListView lstViewAddress;
	private Button btnFavorite;
	
	private double mcurrent_lat;
	private double mcurrent_lon;
	private PickUpInfo pickUpInfo;
	public boolean isLock = false;
	private RelativeLayout relativeLayoutMain;
	private RelativeLayout relativeLayout;
	private FavoriteAdapter favAdapter;
	
	private boolean isFavorite = false;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_pickup_address);
		
		initViews();
		setListener();
		initMap();
		loadData();
		//BaseActivity.isLock = true;
	}

	private void initViews() {
		relativeLayoutMain = (RelativeLayout) findViewById(R.id.relative_main);
		btnBack = (Button) findViewById(R.id.btn_back);
		btnConfirm = (Button) findViewById(R.id.btn_confirm);
		edTxtPickupAddress = (EditText) findViewById(R.id.ed_txt_pickup_address);
		
		radioBtnAddress = (RadioButton) findViewById(R.id.radio_btn_address);
		radioBtnFavorite = (RadioButton) findViewById(R.id.radio_btn_favorite);
		lstViewAddress = (ListView) findViewById(R.id.lst_view_address);
		btnFavorite = (Button) findViewById(R.id.btn_favorite);
		relativeLayout = (RelativeLayout) findViewById(R.id.relative_center);
	}
	
	private void initMap() {
		pickUpInfo = new PickUpInfo();
		gcd = new Geocoder(PickupAddressActivity.this, Locale.getDefault());
		map = ((MapFragment) getFragmentManager().findFragmentById(R.id.map)).getMap();
		
		if(PassengerApp.getInstance().getPickUpLocationInfo() != null ){
			mcurrent_lat = PassengerApp.getInstance().getPickUpLocationInfo().getLocationLatitude();
			mcurrent_lon = PassengerApp.getInstance().getPickUpLocationInfo().getLocationLongitude();
			
		}else{
			GPSTracker gps = new GPSTracker(PickupAddressActivity.this);
			mcurrent_lat = gps.getLatitude();
			mcurrent_lon = gps.getLongitude();
		}
		
		final LatLng cur_Latlng = new LatLng(mcurrent_lat, mcurrent_lon);
		
		map.setOnCameraChangeListener(new OnCameraChangeListener() {
			@Override
			public void onCameraChange(CameraPosition arg0) {
				new GeoAddressAsyncTask(arg0.target.latitude, arg0.target.longitude).execute();
				
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


	private void setListener() {
		relativeLayoutMain.setOnClickListener(this);
		btnBack.setOnClickListener(this);
		btnConfirm.setOnClickListener(this);
		edTxtPickupAddress.setOnClickListener(this);
		btnFavorite.setOnClickListener(this);
		//lstViewAddress.setOnItemClickListener(this);
		lstViewAddress.setOnItemClickListener(this);
		((RadioGroup) findViewById(R.id.radio_group)).setOnCheckedChangeListener(this);
	}

	private void loadData() {
		
	}
	
//	@Override
//	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
//		pickUpInfo = new PickUpInfo();
//		pickUpInfo.setLocationName(PassengerApp.getInstance().getFavoriteInfoList().get(arg2).getLocationName());
//		pickUpInfo.setLocationLatitude(Double.parseDouble(PassengerApp.getInstance().getFavoriteInfoList().get(arg2).getLocationLatitude()));
//		pickUpInfo.setLocationLongitude(Double.parseDouble(PassengerApp.getInstance().getFavoriteInfoList().get(arg2).getLocationLongitude()));
//		PassengerApp.getInstance().setPickUpLocationInfo(pickUpInfo);
//		relativeLayout.setVisibility(View.VISIBLE);
//		lstViewAddress.setVisibility(View.INVISIBLE);
//		radioBtnAddress.setButtonDrawable(R.drawable.address_tab);
//		radioBtnFavorite.setButtonDrawable(R.drawable.faviorite_tab_inactive);
//		initMap();
//	}

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
				if(InternetConnectivity.isConnectedToInternet(PickupAddressActivity.this)) {
					new FavoriateListAsyncTask(PickupAddressActivity.this, new OnRequestComplete() {
						
						@Override
						public void onRequestComplete(String result) {
							try {
								if("2001".equals(result)) {
									if(PassengerApp.getInstance().getFavoriteInfoList() != null) {
										favAdapter = new FavoriteAdapter(PickupAddressActivity.this, PassengerApp.getInstance().getFavoriteInfoList(), false);
										lstViewAddress.setAdapter(favAdapter);
										//lstViewAddress.setItemsCanFocus(false);
									} else {
										lstViewAddress.setAdapter(null);
									}
									
								} else if("3001".equals(result)) {
									
								} else if("4001".equals(result)) {
													
								} else {
									
								}
								
							} catch (Exception e) {
								Toast.makeText(PickupAddressActivity.this, "Response error: (" + e.getMessage() + "). Please try again" , Toast.LENGTH_SHORT).show();
							}
						}
					}).execute(ConstantValues.FUNC_ID_FAVOURITE_DESTINATION_LIST, PassengerApp.getInstance().getPassengerId(), "1");
				} else {
					Toast.makeText(PickupAddressActivity.this, ConstantValues.INTERNET_CONNECTION_FAILURE_MSG, Toast.LENGTH_SHORT).show();
				}
				break;
				
			default:
				break;
		}
	}
	

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.relative_main:
			PassengerApp.getInstance().hideKeyboard(PickupAddressActivity.this, v);
			break;
			
		case R.id.btn_back:
			onBackPressed();
			break;
			
		case R.id.btn_favorite:
			if(InternetConnectivity.isConnectedToInternet(PickupAddressActivity.this)) {
				if(!isFavorite) {
					new FavouriteDestinationAsyncTask(PickupAddressActivity.this, new OnRequestComplete() {
						
						@Override
						public void onRequestComplete(String result) {
							try{
								if("2001".equals(result)) {
									isFavorite = true;
									Toast.makeText(PickupAddressActivity.this, "Successfully Updated !", Toast.LENGTH_SHORT).show();
								} else if("3001".equals(result)) {
									
								} else if("4001".equals(result)) {
													
								} else {
									
								}
							} catch (Exception e) {
								Toast.makeText(PickupAddressActivity.this, "Response Error: (" + e.getMessage() + ").  Please try again", Toast.LENGTH_SHORT).show();
							}
						}
					}).execute(ConstantValues.FUNC_ID_FAVOURITE_DESTINATION, PassengerApp.getInstance().getPassengerId(), Double.toString(pickUpInfo.getLocationLongitude()),Double.toString(pickUpInfo.getLocationLatitude()), edTxtPickupAddress.getText().toString().trim(), "1", "1" );
				} else {
					Toast.makeText(PickupAddressActivity.this, "No update", Toast.LENGTH_SHORT).show();
				}
				
			} else {
				Toast.makeText(PickupAddressActivity.this, ConstantValues.INTERNET_CONNECTION_FAILURE_MSG, Toast.LENGTH_SHORT).show();
			}
			break;
			
		case R.id.btn_confirm:
			PassengerApp.getInstance().setPickUpLocationInfo(pickUpInfo);
			finish();
			break;
			
		case R.id.ed_txt_pickup_address:
			BaseActivity.isLock = true;
			isLock = true;
			PassengerApp.getInstance().hideKeyboard(PickupAddressActivity.this, v);
			startActivity(new Intent(PickupAddressActivity.this, PickUpLocationSearchActivity.class).putExtra("SEARCH_TYPE", 1));
			break;

		default:
			break;
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
					 edTxtPickupAddress.setText(formatedAddress);
					 pickUpInfo.setLocationName(formatedAddress);
					 pickUpInfo.setLocationLatitude(Lat);
					 pickUpInfo.setLocationLongitude(Lon);
					 Log.i("Value is ", "_____"+pickUpInfo.getLocationName() +pickUpInfo.getLocationLongitude() + pickUpInfo.getLocationLongitude());
				} else {
					Log.i("Value is null", "______empty__________");
				}
				   
			} else {
				Log.i("Value is null", "________________");
			}
			
			
		}
	}
	
	@Override
    public void onResume() {
        super.onResume();
        if(PassengerApp.getInstance().getPickUpLocationInfo() != null) {
        	if(map != null) {
        		map.clear();
		    	LatLng latLng = new LatLng(PassengerApp.getInstance().getPickUpLocationInfo().getLocationLatitude(), PassengerApp.getInstance().getPickUpLocationInfo().getLocationLongitude());
		    	 map.moveCamera(CameraUpdateFactory.newLatLng(latLng));
		    	 map.animateCamera(CameraUpdateFactory.zoomTo(14.0f));
		    	 edTxtPickupAddress.setText(PassengerApp.getInstance().getPickUpLocationInfo().getLocationName());
		    	 Log.e("lat , long & place name", "--------"+ PassengerApp.getInstance().getPickUpLocationInfo().getLocationLatitude()+":::"+ PassengerApp.getInstance().getPickUpLocationInfo().getLocationLongitude()+"::::"+PassengerApp.getInstance().getPickUpLocationInfo().getLocationName());
        	}
        } else {
        	Log.e("lat , long & place name", "--------i am in else");
        }
        
        if (isLock) {
			Intent intent = new Intent(PickupAddressActivity.this, LoginWithPinAuthActivity.class);
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
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		Log.e("touched the position of", "__________________________________");
		pickUpInfo = new PickUpInfo();
		pickUpInfo.setLocationName(PassengerApp.getInstance().getFavoriteInfoList().get(position).getLocationName());
		pickUpInfo.setLocationLatitude(Double.parseDouble(PassengerApp.getInstance().getFavoriteInfoList().get(position).getLocationLatitude()));
		pickUpInfo.setLocationLongitude(Double.parseDouble(PassengerApp.getInstance().getFavoriteInfoList().get(position).getLocationLongitude()));
		PassengerApp.getInstance().setPickUpLocationInfo(pickUpInfo);
		//onCheckedChanged(((RadioGroup) findViewById(R.id.radio_group)), 0);
		relativeLayout.setVisibility(View.VISIBLE);
		lstViewAddress.setVisibility(View.INVISIBLE);
		radioBtnAddress.setButtonDrawable(R.drawable.address_tab);
		radioBtnFavorite.setButtonDrawable(R.drawable.faviorite_tab_inactive);
		radioBtnAddress.setChecked(true);
		initMap();
		
	}
    

}
