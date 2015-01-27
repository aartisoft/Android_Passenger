package com.netcabs.passenger;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.netcabs.customview.DialogController;
import com.netcabs.passengerinfo.PassengerApp;

public class FriendSearchActivity extends Activity implements OnClickListener {

	private Button btnBack;
	private EditText edTxtSearchFriend;
	private Dialog dialogSharing;
	private boolean isFriendShare = false;
	private boolean isLock = false;
	private RelativeLayout relativeLayoutMain;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_friend_search);
		
		initViews();
		setListener();
		loadData();
	}

	private void initViews() {
		btnBack = (Button) findViewById(R.id.btn_back);
		edTxtSearchFriend = (EditText) findViewById(R.id.ed_txt_search_friend);
		relativeLayoutMain = (RelativeLayout) findViewById(R.id.relative_main);
	}

	private void setListener() {
		btnBack.setOnClickListener(this);
		relativeLayoutMain.setOnClickListener(this);
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
			PassengerApp.getInstance().hideKeyboard(FriendSearchActivity.this, v);
			break;
		default:
			break;
		}
	}
	
	private void showPopupForSharing(int position) {
		dialogSharing = new DialogController(FriendSearchActivity.this).ShareWithFriendDialog();
		
		Button btnCancel = (Button) dialogSharing.findViewById(R.id.btn_cancel);
		btnCancel.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				dialogSharing.dismiss();
			}
		});
		
		TextView txtViewFriendName = (EditText) dialogSharing.findViewById(R.id.txt_view_share_name);
		txtViewFriendName.setText("You are going to\nshare this trip with "+PassengerApp.getInstance().getFriendsInfoList().get(position).getName());
		
		final ImageView imageView = (ImageView) dialogSharing.findViewById(R.id.img_view_add_to_friend_list);
		imageView.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if(isFriendShare) {
					isFriendShare = false;
					imageView.setBackgroundResource(R.drawable.tick_holdar);
				} else {
					isFriendShare = true;
					imageView.setBackgroundResource(R.drawable.tick_icon);
				}
			}
		});
		
		
		Button btnShare = (Button) dialogSharing.findViewById(R.id.btn_share);
		btnShare.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
			}
		});
		dialogSharing.show();
	}
	
	@Override
	protected void onResume() {
		if (isLock) {
			Intent intent = new Intent(FriendSearchActivity.this, LoginWithPinAuthActivity.class);
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
	
	@Override
	protected void onDestroy() {
		isLock = false;
		super.onDestroy();
	}

}
