package com.netcabs.fragments;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

import android.app.Fragment;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.netcabs.asynctask.HailedRegoCheckAsyncTask;
import com.netcabs.asynctask.LocationAsyncTask;
import com.netcabs.interfacecallback.OnComplete;
import com.netcabs.interfacecallback.OnRequestComplete;
import com.netcabs.internetconnection.InternetConnectivity;
import com.netcabs.latlon.GPSTracker;
import com.netcabs.passenger.HailedCabDetailsActivity;
import com.netcabs.passenger.R;
import com.netcabs.passengerinfo.PassengerApp;
import com.netcabs.utils.ConstantValues;

public class LocationSerachFragment extends Fragment implements android.view.View.OnClickListener {
	String url;
	private AutoCompleteTextView edTxtPickupLocation;
	private View view;
	GPSTracker gps;
	JSONObject json;
	String[] search_text;
	JSONArray contacts = null;
	ArrayList<String> names;
	ArrayAdapter<String> adp;
	public TextView txtView; 
	private static final String TAG_RESULT = "predictions";
	private RelativeLayout relativeLayout;
	private LinearLayout linearLayoutTitle;
	private LinearLayout linearLayoutAddress;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.fragment_location_search, container,false);
		gps = new GPSTracker(getActivity());
		linearLayoutTitle =(LinearLayout)view.findViewById(R.id.liner_layout_title);
		linearLayoutAddress =(LinearLayout)view.findViewById(R.id.liner_layout_address);
		linearLayoutTitle.setVisibility(View.INVISIBLE);
		linearLayoutAddress.setVisibility(View.VISIBLE);
		txtView = (TextView) getActivity().findViewById(R.id.txt_view_address);
		txtView.setText("Location Search");
		//getActivity().t
		initViews();
		setListener();
		return view;
	}

	private void setListener() {
		relativeLayout.setOnClickListener(this);
		edTxtPickupLocation.addTextChangedListener(new TextWatcher() {
			
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {
				
			}
			
			@Override
			public void afterTextChanged(Editable s) {
				txtView.setText("" + edTxtPickupLocation.getText());
			}
			
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				
				search_text = edTxtPickupLocation.getText().toString().trim().split(",");
				url = "https://maps.googleapis.com/maps/api/place/autocomplete/json?input="+ search_text[0] + "&location=" + gps.getLatitude() + "," + gps.getLongitude() + "&radius=500&types=establishment&sensor=true&key=" + ConstantValues.browserKey;
				url = url.replace(" ", "%20");
				if (search_text.length <= 1) {
					names = new ArrayList<String>();
					if(InternetConnectivity.isConnectedToInternet(getActivity())) {
					new LocationAsyncTask(getActivity(),url, new OnComplete() {
						
						@Override
						public void onComplete(int result, ArrayList<String> data, ArrayList<String> refId) {
							adp = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, data) {
								@Override
								public View getView(int position, View convertView, ViewGroup parent) {
									View view = super.getView(position, convertView, parent);
									TextView text = (TextView) view.findViewById(android.R.id.text1);
									text.setTextColor(Color.BLACK);
									return view;
								}
							};
							edTxtPickupLocation.setAdapter(adp);
						}

						@Override
						public void onLocationComplete(int result, double lat,
								double lon) {
							
						}

						@Override
						public void onAddressComplete(int result, String address) {
							
						}
						
					}, search_text, gps.getLatitude(), gps.getLongitude()).execute();
					} else {
						Toast.makeText(getActivity(), ConstantValues.INTERNET_CONNECTION_FAILURE_MSG, Toast.LENGTH_SHORT).show();
					}
				}
			}
			
		});
	}

	private void initViews() {
		relativeLayout = (RelativeLayout) view.findViewById(R.id.relative_main);
		edTxtPickupLocation = (AutoCompleteTextView) view.findViewById(R.id.ed_txt_pickup_location);
		edTxtPickupLocation.setThreshold(0);
	}
	

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.relative_main:
			PassengerApp.getInstance().hideKeyboard(getActivity(), v);
			break;

		default:
			break;
		}
	}
	@Override
	public void onResume() {
		super.onResume();
		initViews();
		setListener();
	}

}
