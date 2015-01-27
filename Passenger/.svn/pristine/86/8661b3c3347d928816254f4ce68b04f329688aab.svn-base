package com.netcabs.fragments;

import com.netcabs.passenger.MainMenuActivity;
import com.netcabs.passenger.R;
import com.netcabs.passenger.SupportDetailsActivity;
import com.netcabs.utils.BaseActivity;
import com.netcabs.utils.ConstantValues;

import android.app.Fragment;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.SyncStateContract.Constants;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class SupportFragment extends Fragment implements OnClickListener {
	
	private View view;
	private Button btnContact;
	private Button btnLetsGo;
	MainMenuActivity mainMenuActivity;
	private LinearLayout linearLayoutTitle;
	private LinearLayout linearLayoutAddress;
	private TextView txtViewTitle;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.fragment_support, null);
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
		txtViewTitle.setText("Support");
		btnLetsGo = (Button) view.findViewById(R.id.btn_lets_go);
		btnContact = (Button) view.findViewById(R.id.btn_contact);
	}

	private void setListener() {
		btnLetsGo.setOnClickListener(this);
		btnContact.setOnClickListener(this);
	}

	private void loadData() {
		((TextView) getActivity().findViewById(R.id.txt_view_title)).setText("Support");
		((Button) getActivity().findViewById(R.id.btn_search_location_name)).setVisibility(View.INVISIBLE);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_lets_go:
			mainMenuActivity.isLock = true;
			startActivity(new Intent(getActivity(), SupportDetailsActivity.class));
			break;
			
		case R.id.btn_contact:
			BaseActivity.isLock = true;
			mainMenuActivity.isLock = true;
			Intent intent_phone = new Intent(Intent.ACTION_DIAL);
			intent_phone.setData(Uri.parse("tel:"+ ConstantValues.LetsGo_SUPPORT_CONTACT));
			startActivity(intent_phone); 
			break;
		
		default:
			break;
		}
	}
}
