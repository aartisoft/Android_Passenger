package com.netcabs.passenger;

import android.app.Activity;
import android.app.Dialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.netcabs.adapter.SliderMenuAdapter;
import com.netcabs.customview.DialogController;
import com.netcabs.database.PreferenceUtil;
import com.netcabs.facebook.FacebookModule;
import com.netcabs.fragments.HailedCabFragment;
import com.netcabs.fragments.MapFragment;
import com.netcabs.fragments.MyBookingFragment;
import com.netcabs.fragments.MyDetailsFragment;
import com.netcabs.fragments.PaymentFragment;
import com.netcabs.fragments.SupportFragment;
import com.netcabs.passengerinfo.PassengerApp;
import com.netcabs.utils.BaseActivity;
import com.netcabs.utils.ConstantValues;

public class MainMenuActivity extends Activity implements OnClickListener, OnItemClickListener {

	private Button btnMenu;
	private TextView txtViewTitle;
	//private DrawerLayout drawerLayout; ****raman
	private ListView lstViewSliderMenu;
	private SlidingMenu slidingMenu;
	private FragmentManager fragmentManager;
	private FragmentTransaction fragmentTransaction;
	private FacebookModule fbModule;

	private Fragment fragment = null;
	private int selectedIndex = 0;
	public boolean isLock = false;

	//private boolean isDrawerOpen = false; ****raman

	private String[] menuItem = { "Home", "My Details", "My Booking", "Hailed Cab", "Support", "Payment", "Logout" };
	private int[] menuIconY = { R.drawable.home, R.drawable.my_details, R.drawable.my_bookings, R.drawable.hailed_cab, R.drawable.support, R.drawable.payment, R.drawable.logout };
	private int[] menuIcon = { R.drawable.home_y, R.drawable.my_details_y, R.drawable.my_bookings_y, R.drawable.hailed_cab_y, R.drawable.support_y, R.drawable.payment_y, R.drawable.logout_y };

	private boolean isDrawerOpen = false;
	private PreferenceUtil preferenceUtil;
	private Dialog dialogConfirmCancel;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main_menu);
		fbModule = new FacebookModule(MainMenuActivity.this, savedInstanceState);
		initViews();
		setListener();
		loadData();
		
	}

	private void initViews() {
		btnMenu = (Button) findViewById(R.id.btn_menu);
		txtViewTitle = (TextView) findViewById(R.id.txt_view_title);
		slidingMenu = new SlidingMenu(this);

	}

	private void setListener() {
		btnMenu.setOnClickListener(this);
	}

	private void loadData() {
		preferenceUtil = new PreferenceUtil(MainMenuActivity.this);

		fragmentManager = getFragmentManager();
		fragmentTransaction = fragmentManager.beginTransaction();
		fragmentTransaction.add(R.id.activity_main_content_fragment, new MapFragment());
		fragmentTransaction.commit();

		slidingMenu.setShadowWidthRes(R.dimen.shadow_width);
		slidingMenu.setShadowDrawable(R.drawable.shadow);
		slidingMenu.setBehindOffsetRes(R.dimen.slidingmenu_offset);
		slidingMenu.setFadeDegree(0.35f);
		slidingMenu.attachToActivity(this, SlidingMenu.SLIDING_CONTENT);
		slidingMenu.setMenu(R.layout.menu_listview);
		lstViewSliderMenu = (ListView) findViewById(R.id.lvSlideMenu);

		lstViewSliderMenu.setOnItemClickListener(this);
		slidingMenu.setSlidingEnabled(false);

		lstViewSliderMenu.setAdapter(new SliderMenuAdapter(MainMenuActivity.this, menuItem, menuIcon));
		//lstViewSliderMenu.setItemChecked(0, true);
		//lstViewSliderMenu.performItemClick(lstViewSliderMenu.getAdapter().getView(2, null, null), 2, lstViewSliderMenu.getAdapter().getItemId(2));
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_menu:
			slidingMenu.toggle();
			PassengerApp.getInstance().hideKeyboard(MainMenuActivity.this, v);
		default:
			break;
		}
	}

	@Override
	public void onBackPressed() {
		if (getFragmentManager().getBackStackEntryCount() > 0) {
			getFragmentManager().popBackStackImmediate();
		} else {
			showConfirmPopup();
		}

	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View view, int position, long arg3) {
		PassengerApp.getInstance().hideKeyboard(MainMenuActivity.this, view);
		
		switch (position) {
		case 0:
			if (selectedIndex != 0) {
				selectedIndex = 0;
				fragment = new MapFragment();
				logoChange(position);
			}
			break;

		case 1:
			if (selectedIndex != 1) {
				selectedIndex = 1;
				fragment = new MyDetailsFragment();
				logoChange(position);
			}
			break;

		case 2:
			if (selectedIndex != 2) {
				selectedIndex = 2;
				fragment = new MyBookingFragment();
				logoChange(position);
			}
			break;

		case 3:
			if (selectedIndex != 3) {
				selectedIndex = 3;
				fragment = new HailedCabFragment();
				logoChange(position);
			}
			break;

		case 4:
			if (selectedIndex != 4) {
				selectedIndex = 4;
				fragment = new SupportFragment();
				logoChange(position);
			}
			break;

		case 5:
			if (selectedIndex != 5) {
				selectedIndex = 5;
				fragment = new PaymentFragment();
				logoChange(position);
			}
			break;

		case 6:
			finish();
			BaseActivity.isLock = true;
			fbModule.userLogout();
			startActivity(new Intent(MainMenuActivity.this, LoginWithPinActivity.class));
			break;

		default:
			PassengerApp.getInstance().hideKeyboard(MainMenuActivity.this, view);
			break;
		}

		if (fragment != null) {
			fragmentManager = getFragmentManager();
			fragmentTransaction = fragmentManager.beginTransaction();
			fragmentTransaction.replace(R.id.activity_main_content_fragment, fragment);
			fragmentTransaction.commit();

			lstViewSliderMenu.setItemChecked(position, true);
			// drawerLayout.closeDrawer(lstViewSliderMenu); ***raman

		} else {
			
		}
		
		if (ConstantValues.isComingFromHailedCabDetails || ConstantValues.isCommingFromConfirmBooking ||ConstantValues.isCommingFromPast) {
			ConstantValues.isCommingFromConfirmBooking = false;
		} else {
			slidingMenu.toggle();
		}

	}
	
	@Override
	protected void onDestroy() {
		//preferenceUtil.setAPP_NEW("0");
		super.onDestroy();
	}

	private void logoChange(int position) { // Set the listrows' image while selected or not
		for (int i = 0; i < 6; i++) {
			if (i == position) {
				lstViewSliderMenu.getChildAt(i).findViewById(R.id.img_view_logo).setBackgroundResource(menuIconY[i]);
			} else {
				lstViewSliderMenu.getChildAt(i).findViewById(R.id.img_view_logo).setBackgroundResource(menuIcon[i]);
			}
		}
	}
	
	private void showConfirmPopup() {
		dialogConfirmCancel = new DialogController(MainMenuActivity.this).ConfirmBookingDialog();
		
		TextView txtViewMsg = (TextView) dialogConfirmCancel.findViewById(R.id.txt_view_booking_message);
		txtViewMsg.setText("Are you Sure ?");
		
		Button btnYes = (Button) dialogConfirmCancel.findViewById(R.id.btn_yes);
		
		btnYes.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				PassengerApp.getInstance().destroyInstance();
				dialogConfirmCancel.dismiss();
				finish();
			}
		});
		Button btnNo = (Button) dialogConfirmCancel.findViewById(R.id.btn_no);
		btnNo.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				dialogConfirmCancel.dismiss();
			}
		});
		
		Button btnCancel = (Button) dialogConfirmCancel.findViewById(R.id.btn_cancel);
		btnCancel.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				dialogConfirmCancel.dismiss();
			}
		});
		
		dialogConfirmCancel.show();
		
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		
		if (isLock) {
			Intent intent = new Intent(MainMenuActivity.this, LoginWithPinAuthActivity.class);
			intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivity(intent);
		}
		
		if(ConstantValues.isComingFromHailedCabDetails) {
			lstViewSliderMenu.performItemClick(lstViewSliderMenu.getAdapter().getView(2, null, null), 2, lstViewSliderMenu.getAdapter().getItemId(2));
			
		} else if (ConstantValues.isCommingFromConfirmBooking) {
			lstViewSliderMenu.performItemClick(lstViewSliderMenu.getAdapter().getView(2, null, null), 2, lstViewSliderMenu.getAdapter().getItemId(2));
			
		} else if (ConstantValues.isCommingFromPast && !ConstantValues.isComingFromHailedCabDetails && !ConstantValues.isCommingFromConfirmBooking){
			lstViewSliderMenu.performItemClick(lstViewSliderMenu.getAdapter().getView(2, null, null), 2, lstViewSliderMenu.getAdapter().getItemId(2));
			return;
			
		}
		
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
