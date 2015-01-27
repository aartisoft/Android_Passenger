package com.netcabs.fragments;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.webkit.WebView.FindListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.TextView.OnEditorActionListener;

import com.netcabs.asynctask.HailedRegoCheckAsyncTask;
import com.netcabs.interfacecallback.OnRequestComplete;
import com.netcabs.internetconnection.InternetConnectivity;
import com.netcabs.passenger.HailedCabDetailsActivity;
import com.netcabs.passenger.MainMenuActivity;
import com.netcabs.passenger.R;
import com.netcabs.passengerinfo.PassengerApp;
import com.netcabs.utils.ConstantValues;

public class HailedCabFragment extends Fragment implements OnClickListener {
	
	private View view;
	private EditText edTxtTaxiRegNo;
	private Button btnSubmit;
	MainMenuActivity mainMenuActivity;
	private RelativeLayout relativeLayout;
	private LinearLayout linearLayoutTitle;
	private LinearLayout linearLayoutAddress;
	private TextView txtViewTitle;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.fragment_hailed_cab, null);
		mainMenuActivity = (MainMenuActivity) getActivity();
		
		initViews();
		setListener();
		loadData();
		
		return view;
	}
	
	private void initViews() {
		linearLayoutTitle =(LinearLayout)getActivity().findViewById(R.id.liner_layout_title);
		linearLayoutAddress =(LinearLayout)getActivity().findViewById(R.id.liner_layout_address);
		linearLayoutTitle.setVisibility(View.VISIBLE);
		linearLayoutAddress.setVisibility(View.INVISIBLE);
		txtViewTitle = (TextView) getActivity().findViewById(R.id.txt_view_title);
		txtViewTitle.setText("Hailed Cab");
		edTxtTaxiRegNo = (EditText) view.findViewById(R.id.ed_txt_taxi_reg_no);
		btnSubmit = (Button) view.findViewById(R.id.btn_submit);
		relativeLayout = (RelativeLayout) view.findViewById(R.id.relative_main);
	}

	private void setListener() {
		btnSubmit.setOnClickListener(this);
		relativeLayout.setOnClickListener(this);
		
		
		edTxtTaxiRegNo.setOnEditorActionListener(new OnEditorActionListener() {
			@Override
			public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
				if(!edTxtTaxiRegNo.getText().toString().trim().equalsIgnoreCase("")) {
					if(InternetConnectivity.isConnectedToInternet(getActivity())) {
						new HailedRegoCheckAsyncTask(getActivity(), new OnRequestComplete() {
							@Override
							public void onRequestComplete(String result) {
								try {
										if("2001".equals(result)) {
											mainMenuActivity.isLock = true;
											startActivity(new Intent(getActivity(), HailedCabDetailsActivity.class).putExtra("reg_no", edTxtTaxiRegNo.getText().toString().trim()));
										} else {
											edTxtTaxiRegNo.setError("Invalid");
										}
								} catch (Exception e) {
									Toast.makeText(getActivity(), "Response Error: (" + e.getMessage() + ").  Please try again", Toast.LENGTH_SHORT).show();
								}
							}
						}).execute(ConstantValues.FUNC_ID_HAILED_REGO_CHECK, PassengerApp.getInstance().getPassengerId(), edTxtTaxiRegNo.getText().toString().trim());
					}else {
						Toast.makeText(getActivity(), ConstantValues.INTERNET_CONNECTION_FAILURE_MSG, Toast.LENGTH_SHORT).show();
					}
				
			} else {
				edTxtTaxiRegNo.setError("Required");
			}
				return false;
			}
	    });
	}

	private void loadData() {
		((TextView) getActivity().findViewById(R.id.txt_view_title)).setText("Hailed Cab");
		((Button) getActivity().findViewById(R.id.btn_search_location_name)).setVisibility(View.INVISIBLE);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.relative_main:
			PassengerApp.getInstance().hideKeyboard(getActivity(), v);
			break;
			
		case R.id.btn_submit:
			if(!edTxtTaxiRegNo.getText().toString().trim().equalsIgnoreCase("")) {
					if(InternetConnectivity.isConnectedToInternet(getActivity())) {
						new HailedRegoCheckAsyncTask(getActivity(), new OnRequestComplete() {
							@Override
							public void onRequestComplete(String result) {
								if("2001".equals(result)) {
									mainMenuActivity.isLock = true;
									startActivity(new Intent(getActivity(), HailedCabDetailsActivity.class).putExtra("reg_no", edTxtTaxiRegNo.getText().toString().trim()));
								} else {
									edTxtTaxiRegNo.setError("Invalid");
								}
							}
						}).execute(ConstantValues.FUNC_ID_HAILED_REGO_CHECK, PassengerApp.getInstance().getPassengerId(), edTxtTaxiRegNo.getText().toString().trim());
					}else {
						Toast.makeText(getActivity(), ConstantValues.INTERNET_CONNECTION_FAILURE_MSG, Toast.LENGTH_SHORT).show();
					}
				
			} else {
				edTxtTaxiRegNo.setError("Required");
			}
			break;

		default:
			break;
		}
	}
	
}
