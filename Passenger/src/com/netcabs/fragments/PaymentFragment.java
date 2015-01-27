package com.netcabs.fragments;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.fortysevendeg.swipelistview.BaseSwipeListViewListener;
import com.fortysevendeg.swipelistview.SwipeListView;
import com.netcabs.adapter.PaymentCardAdapter;
import com.netcabs.asynctask.CardListAsyncTask;
import com.netcabs.interfacecallback.OnRequestComplete;
import com.netcabs.internetconnection.InternetConnectivity;
import com.netcabs.passenger.CardRegistrationActivity;
import com.netcabs.passenger.MainMenuActivity;
import com.netcabs.passenger.PastBookingDetailsActivity;
import com.netcabs.passenger.PaymentCardEditActivity;
import com.netcabs.passenger.R;
import com.netcabs.passengerinfo.PassengerApp;
import com.netcabs.utils.ConstantValues;
import com.netcabs.utils.Utils;

public class PaymentFragment extends Fragment implements OnClickListener {

	private Button btnAddCard;
	private SwipeListView lstViewCard;
	private View view;
	private PaymentCardAdapter paymentAdapter;
	private boolean isListItemPressed = false;
	MainMenuActivity mainMenuActivity;
	private RelativeLayout relativeLayout;
	private LinearLayout linearLayoutTitle;
	private LinearLayout linearLayoutAddress;
	private TextView txtViewTitle;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.fragment_payment, null);
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
		txtViewTitle.setText("Payment");
		relativeLayout = (RelativeLayout) view.findViewById(R.id.relative_main);
		btnAddCard = (Button) getActivity().findViewById(R.id.btn_search_location_name);
		lstViewCard = (SwipeListView) view.findViewById(R.id.lst_view_card_info);
		PassengerApp.getInstance().setLstViewCard(lstViewCard);
	}

	private void setListener() {
		relativeLayout.setOnClickListener(this);
		btnAddCard.setOnClickListener(this);
		
		lstViewCard.setSwipeListViewListener(new BaseSwipeListViewListener() {
            @Override
            public void onOpened(int position, boolean toRight) {
            }

            @Override
            public void onClosed(int position, boolean fromRight) {
            }

            @Override
            public void onListChanged() {
            }

            @Override
            public void onMove(int position, float x) {
            }

            @Override
            public void onStartOpen(int position, int action, boolean right) {
                Log.d("swipe", String.format("onStartOpen %d - action %d", position, action));
            }

            @Override
            public void onStartClose(int position, boolean right) {
                Log.d("swipe", String.format("onStartClose %d", position));
            }

            @Override
            public void onClickFrontView(int position) {
                Log.d("swipe", String.format("onClickFrontView %d", position));
                isListItemPressed  = true;
                mainMenuActivity.isLock = true;
                startActivity(new Intent(getActivity(), PaymentCardEditActivity.class).putExtra("position", position));
                //lstViewCard.openAnimate(position); //when you touch front view it will open
            }

            @Override
            public void onClickBackView(int position) {
                Log.d("swipe", String.format("onClickBackView %d", position));
                //PassengerApp.getInstance().getLstViewCard().closeOpenedItems();
                //lstViewCard.closeAnimate(position);//when you touch back view it will close
            }

            @Override
            public void onDismiss(int[] reverseSortedPositions) {
            	
            }
            
        });
        
        //These are the swipe listview settings. you can change these
        //setting as your requirement 
        //swipelistview.setSwipeMode(SwipeListView.SWIPE_MODE_BOTH); // there are five swiping modes
		lstViewCard.setSwipeMode(SwipeListView.SWIPE_MODE_LEFT); // there are five swiping modes
		lstViewCard.setSwipeActionLeft(SwipeListView.SWIPE_ACTION_NONE); //there are four swipe actions 
		lstViewCard.setSwipeActionRight(SwipeListView.SWIPE_ACTION_NONE);
		lstViewCard.setOffsetLeft(Utils.convertDpToPixel(220f, getActivity())); // left side offset
		lstViewCard.setOffsetRight(Utils.convertDpToPixel(0f, getActivity())); // right side offset
		lstViewCard.setAnimationTime(300); // Animation time
		lstViewCard.setSwipeOpenOnLongPress(true); // enable or disable SwipeOpenOnLongPress
		//lstViewCard.closeOpenedItems();
	}

	private void loadData() {
		((TextView) getActivity().findViewById(R.id.txt_view_title)).setText("Payment");
		btnAddCard.setBackgroundResource(R.drawable.add_card_btn);
		btnAddCard.setVisibility(View.VISIBLE);
		
		if(InternetConnectivity.isConnectedToInternet(getActivity())) {
			new CardListAsyncTask(getActivity(), new OnRequestComplete() {
				
				@Override
				public void onRequestComplete(String result) {
					try {
						if("2001".equals(result)) {
							if(PassengerApp.getInstance().getCreditCardInfoList() != null) {
								paymentAdapter = new PaymentCardAdapter(getActivity(), PassengerApp.getInstance().getCreditCardInfoList());
								lstViewCard.setAdapter(paymentAdapter);
							} else {
								//lstViewCard.setAdapter(null);
							}
						} else if("3001".equals(result)) {
							
						} else if("4001".equals(result)) {
											
						} else {
							
						}
					} catch (Exception e) {
						Toast.makeText(getActivity(), "Response Error: (" + e.getMessage() + ").  Please try again", Toast.LENGTH_SHORT).show();
					}
				}
			}).execute(ConstantValues.FUNC_ID_CARD_LIST, PassengerApp.getInstance().getPassengerId());
		} else {
			Toast.makeText(getActivity(), ConstantValues.INTERNET_CONNECTION_FAILURE_MSG, Toast.LENGTH_SHORT).show();
		}
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.relative_main:
			PassengerApp.getInstance().hideKeyboard(getActivity(), v);
			break;
			
		case R.id.btn_search_location_name:
			isListItemPressed = true;
			mainMenuActivity.isLock = true;
			startActivity(new Intent(getActivity(), CardRegistrationActivity.class).putExtra("is_skip", true));
			break;

		default:
			break;
		}
	}
	
	@Override
	public void onResume() {
		if (isListItemPressed) {
			isListItemPressed = false;
			
			if (PassengerApp.getInstance().getCreditCardInfoList() != null) {
				paymentAdapter = new PaymentCardAdapter(getActivity(), PassengerApp.getInstance().getCreditCardInfoList());
				lstViewCard.setAdapter(paymentAdapter);
				// paymentAdapter.notifyDataSetChanged();
			}
			
			/*if(paymentAdapter != null){
				paymentAdapter.notifyDataSetChanged();
			}*/
			
		} else {

		}
		
		super.onResume();
	}
	
}
