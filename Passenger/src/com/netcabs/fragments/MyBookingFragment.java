package com.netcabs.fragments;

import android.app.Fragment;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils.TruncateAt;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.netcabs.adapter.MyBookingAdapter;
import com.netcabs.asynctask.MyBookingAsyncTask;
import com.netcabs.interfacecallback.OnRequestComplete;
import com.netcabs.internetconnection.InternetConnectivity;
import com.netcabs.passenger.MainMenuActivity;
import com.netcabs.passenger.MyBookingDetailsActivity;
import com.netcabs.passenger.PastBookingDetailsActivity;
import com.netcabs.passenger.R;
import com.netcabs.passengerinfo.PassengerApp;
import com.netcabs.utils.BaseActivity;
import com.netcabs.utils.ConstantValues;

public class MyBookingFragment extends Fragment implements OnCheckedChangeListener, OnItemClickListener, OnClickListener {
	
	private View view;
	private RadioButton radioBtnCurrentBooking;
	private RadioButton radioBtnCurrentTrip;
	private RadioButton radioBtnFutureBooking;
	private RadioButton radioBtnPastBooking;
	private ListView lstViewMyBooking;
	private LinearLayout linearLayoutTitle;
	private LinearLayout linearLayoutAddress;
	
	private TextView txtViewTitle;
	private TextView txtViewFurureBooking;
	private int tabType = 0;
	MainMenuActivity mainMenuActivity;
	private RelativeLayout relativeLayout;
	private MyBookingAsyncTask asyncTask;
	private boolean doNormalLoad = false;
	
//	private boolean isBackground = false;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.fragment_my_booking, null);
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
		relativeLayout = (RelativeLayout) view.findViewById(R.id.relative_main);
		radioBtnCurrentBooking = (RadioButton) view.findViewById(R.id.radio_btn_current_booking);
		radioBtnCurrentTrip = (RadioButton) view.findViewById(R.id.radio_btn_current_trip);
		radioBtnFutureBooking = (RadioButton) view.findViewById(R.id.radio_btn_future_booking);
		radioBtnPastBooking = (RadioButton) view.findViewById(R.id.radio_btn_past_booking);
		
		lstViewMyBooking = (ListView) view.findViewById(R.id.lst_view_my_booking);
		
		txtViewTitle = (TextView) getActivity().findViewById(R.id.txt_view_title);
		txtViewTitle.setText("Current Booking");
		txtViewFurureBooking = (TextView) view.findViewById(R.id.txt_view_future_booking);
	}

	private void setListener() {
		((RadioGroup) view.findViewById(R.id.radio_group)).setOnCheckedChangeListener(this);
		relativeLayout.setOnClickListener(this);
		lstViewMyBooking.setOnItemClickListener(this);
	}

	private void loadData() {
		//txtViewTitle.setText("Current Booking");
		((Button) getActivity().findViewById(R.id.btn_search_location_name)).setVisibility(View.INVISIBLE);
		Log.e("value of doNormal LOAD", ""+doNormalLoad);
		//loadBookingData(tabType + 1);
		if(doNormalLoad) {
			doNormalLoad = false;
			loadBookingData(tabType + 1);
		}
	}

	@Override
	public void onCheckedChanged(RadioGroup group, int checkedId) {
		switchTab();
	}
	
	private void switchTab() {
		int selectedTab = ((RadioGroup) view.findViewById(R.id.radio_group)).getCheckedRadioButtonId();
		
		switch (selectedTab) {
			case R.id.radio_btn_current_booking:
				lstViewMyBooking.setVisibility(View.VISIBLE);
				txtViewFurureBooking.setVisibility(View.INVISIBLE);
				tabType = 0;
				txtViewTitle.setText("Current Booking");
				radioBtnCurrentBooking.setButtonDrawable(R.drawable.current_booking_tab_act);
				radioBtnCurrentTrip.setButtonDrawable(R.drawable.current_trip_tab);
				radioBtnFutureBooking.setButtonDrawable(R.drawable.future_booking_tab);
				radioBtnPastBooking.setButtonDrawable(R.drawable.past_booking_tab);
				loadBookingData(tabType + 1);
				break;
				
			case R.id.radio_btn_current_trip:
				lstViewMyBooking.setVisibility(View.VISIBLE);
				txtViewFurureBooking.setVisibility(View.INVISIBLE);
				tabType = 1;
				txtViewTitle.setText("Current Trip");
				radioBtnCurrentBooking.setButtonDrawable(R.drawable.current_booking_tab);
				radioBtnCurrentTrip.setButtonDrawable(R.drawable.current_trip_tab_act);
				radioBtnFutureBooking.setButtonDrawable(R.drawable.future_booking_tab);
				radioBtnPastBooking.setButtonDrawable(R.drawable.past_booking_tab);
				loadBookingData(tabType + 1);
				break;
				
			case R.id.radio_btn_future_booking:
				lstViewMyBooking.setVisibility(View.INVISIBLE);
				txtViewFurureBooking.setVisibility(View.VISIBLE);
				tabType = 2;
				txtViewTitle.setText("Future Booking");
				radioBtnCurrentBooking.setButtonDrawable(R.drawable.current_booking_tab);
				radioBtnCurrentTrip.setButtonDrawable(R.drawable.current_trip_tab);
				radioBtnFutureBooking.setButtonDrawable(R.drawable.future_booking_tab_act);
				radioBtnPastBooking.setButtonDrawable(R.drawable.past_booking_tab);
				break;
				
			case R.id.radio_btn_past_booking:
				lstViewMyBooking.setVisibility(View.VISIBLE);
				txtViewFurureBooking.setVisibility(View.INVISIBLE);
				tabType = 3;
				txtViewTitle.setText("Past Booking");
				radioBtnCurrentBooking.setButtonDrawable(R.drawable.current_booking_tab);
				radioBtnCurrentTrip.setButtonDrawable(R.drawable.current_trip_tab);
				radioBtnFutureBooking.setButtonDrawable(R.drawable.future_booking_tab);
				radioBtnPastBooking.setButtonDrawable(R.drawable.past_booking_tab_act);
				loadBookingData(tabType + 1);
				break;
	
			default:
				break;
		}
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		if(tabType == 3) {
			BaseActivity.isLock = true;
			mainMenuActivity.isLock = true;
			ConstantValues.normalBack = true;
			startActivity(new Intent(getActivity(), PastBookingDetailsActivity.class).putExtra("position", arg2));
		} else {
			BaseActivity.isLock = true;
			mainMenuActivity.isLock = true;
			ConstantValues.normalBack = true;
			startActivity(new Intent(getActivity(), MyBookingDetailsActivity.class).putExtra("position", arg2).putExtra("tab_type", tabType).putExtra("VIEW_TYPE", 0));
		}
	}
	
	private void loadBookingData(int flag) {
		if(InternetConnectivity.isConnectedToInternet(getActivity())) {
			
			if(asyncTask != null) {
				asyncTask.cancel(true);
			}
			
			if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
				asyncTask = (MyBookingAsyncTask) new MyBookingAsyncTask(getActivity(), new OnRequestComplete() {
					
					@Override
					public void onRequestComplete(String result) {
						try {
							if("2001".equals(result)) {
								if(PassengerApp.getInstance().getMyBookingInfoList() != null) {
									lstViewMyBooking.setAdapter(new MyBookingAdapter(getActivity(), PassengerApp.getInstance().getMyBookingInfoList()));
								} else {
									lstViewMyBooking.setAdapter(null);
								}
							} else {
								
							}
						} catch (Exception e) {
							Toast.makeText(getActivity(), "Response Error: (" + e.getMessage() + ").  Please try again", Toast.LENGTH_SHORT).show();
						}
					}
				}).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, ConstantValues.FUNC_ID_BOOKING_LIST, PassengerApp.getInstance().getPassengerId(), Integer.toString(tabType + 1));
			} else {
				asyncTask = (MyBookingAsyncTask) new MyBookingAsyncTask(getActivity(), new OnRequestComplete() {
					
					@Override
					public void onRequestComplete(String result) {
						try {
							if("2001".equals(result)) {
								if(PassengerApp.getInstance().getMyBookingInfoList() != null) {
									lstViewMyBooking.setAdapter(new MyBookingAdapter(getActivity(), PassengerApp.getInstance().getMyBookingInfoList()));
								} else {
									lstViewMyBooking.setAdapter(null);
								}
							} else {
								
							}
						} catch (Exception e) {
							Toast.makeText(getActivity(), "Response Error: (" + e.getMessage() + ").  Please try again", Toast.LENGTH_SHORT).show();
						}
					}
				}).execute(ConstantValues.FUNC_ID_BOOKING_LIST, PassengerApp.getInstance().getPassengerId(), Integer.toString(tabType + 1));
			}
			
		} else {
			Toast.makeText(getActivity(), ConstantValues.INTERNET_CONNECTION_FAILURE_MSG, Toast.LENGTH_SHORT).show();
		}
	}
	
	@Override
	public void onResume() {
		if(!ConstantValues.isBackground) {
			ConstantValues.isBackground = false;
			initViews();
			if(ConstantValues.isComingFromHailedCabDetails) {
				Log.e("in case 1 if","----------------------------- 1");
				ConstantValues.isComingFromHailedCabDetails = false;
	//			tabType = 1;
	//			onCheckedChanged(((RadioGroup) view.findViewById(R.id.radio_group)), 1);
				lstViewMyBooking.setVisibility(View.VISIBLE);
				txtViewFurureBooking.setVisibility(View.INVISIBLE);
				tabType = 1;
				txtViewTitle.setText("Current Trip");
				radioBtnCurrentBooking.setButtonDrawable(R.drawable.current_booking_tab);
				radioBtnCurrentTrip.setButtonDrawable(R.drawable.current_trip_tab_act);
				radioBtnFutureBooking.setButtonDrawable(R.drawable.future_booking_tab);
				//radioBtnPastBooking.setButtonDrawable(R.drawable.past_booking_tab);
				radioBtnCurrentTrip.isChecked();
				radioBtnCurrentTrip.setChecked(true);
				radioBtnCurrentTrip.isSelected();
				//loadBookingData(tabType + 1);
			} else if (!ConstantValues.isCommingFromConfirmBooking && !ConstantValues.isComingFromHailedCabDetails && ConstantValues.isCommingFromPast) {
				Log.e("in case 2 else if","----------------------------- else if 2");
				ConstantValues.isCommingFromPast = false;
				lstViewMyBooking.setVisibility(View.VISIBLE);
				txtViewFurureBooking.setVisibility(View.INVISIBLE);
				tabType = 3;
				txtViewTitle.setText("Past Trip");
				txtViewTitle.setEllipsize(TruncateAt.END);
				radioBtnCurrentBooking.setButtonDrawable(R.drawable.current_booking_tab);
				radioBtnCurrentTrip.setButtonDrawable(R.drawable.current_trip_tab);
				radioBtnFutureBooking.setButtonDrawable(R.drawable.future_booking_tab);
				radioBtnPastBooking.setButtonDrawable(R.drawable.past_booking_tab_act);
				radioBtnPastBooking.setChecked(true);
				radioBtnPastBooking.isSelected();
				//loadBookingData(tabType + 1);
			} else if(ConstantValues.isCommingFromConfirmBooking){
				Log.e("in case 3 else if","----------------------------- else if 3");
				lstViewMyBooking.setVisibility(View.VISIBLE);
				txtViewFurureBooking.setVisibility(View.INVISIBLE);
				tabType = 0;
				ConstantValues.isCommingFromConfirmBooking = false;
				txtViewTitle.setText("Current Booking");
				txtViewTitle.setEllipsize(TruncateAt.END);
				radioBtnCurrentBooking.setButtonDrawable(R.drawable.current_booking_tab_act);
				radioBtnCurrentTrip.setButtonDrawable(R.drawable.current_trip_tab);
				radioBtnFutureBooking.setButtonDrawable(R.drawable.future_booking_tab);
				radioBtnPastBooking.setButtonDrawable(R.drawable.past_booking_tab);
				radioBtnCurrentBooking.setChecked(true);
				radioBtnCurrentBooking.isSelected();
			//	txtViewTitle.setSelected(false);
			} else {
				Log.e("in case 4 else","----------------------------- else 4");
				lstViewMyBooking.setVisibility(View.VISIBLE);
				txtViewFurureBooking.setVisibility(View.INVISIBLE);
				tabType = 0;
				txtViewTitle.setText("Current Booking");
				txtViewTitle.setEllipsize(TruncateAt.END);
				radioBtnCurrentBooking.setButtonDrawable(R.drawable.current_booking_tab_act);
				radioBtnCurrentTrip.setButtonDrawable(R.drawable.current_trip_tab);
				radioBtnFutureBooking.setButtonDrawable(R.drawable.future_booking_tab);
				radioBtnPastBooking.setButtonDrawable(R.drawable.past_booking_tab);
				radioBtnCurrentBooking.setChecked(true);
				radioBtnCurrentBooking.isSelected();
				doNormalLoad = true;
				//txtViewTitle.setSelected(false);
			} 
		} else {
			if (ConstantValues.normalBack) {
				Log.e("in case 5 else else",
						"----------------------------- else 5");
				doNormalLoad = true;
				ConstantValues.normalBack = false;
				// radioBtnCurrentBooking.setChecked(true);
				// radioBtnCurrentBooking.isSelected();
			} else {
				doNormalLoad = true;
				radioBtnCurrentBooking.setChecked(true);
				radioBtnCurrentBooking.isSelected();
			}
		}
		super.onResume();
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
	public void onPause() {
		ConstantValues.isBackground = true;		
		super.onPause();
	}
	
}
