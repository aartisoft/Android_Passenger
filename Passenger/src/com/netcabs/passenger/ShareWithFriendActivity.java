package com.netcabs.passenger;

import java.util.ArrayList;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.fortysevendeg.swipelistview.BaseSwipeListViewListener;
import com.fortysevendeg.swipelistview.SwipeListView;
import com.netcabs.adapter.FriendsListAdapter;
import com.netcabs.customview.DialogController;
import com.netcabs.datamodel.FriendsInfo;
import com.netcabs.passengerinfo.PassengerApp;
import com.netcabs.utils.Utils;

public class ShareWithFriendActivity extends Activity implements OnItemClickListener, OnClickListener {

	private Button btnBack;
	private Button btnSearch;
	private EditText edTxtSearchFriend;
	private SwipeListView lstViewFriendList;
	private Dialog dialogSharing;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_share_with_friend);
		
		initViews();
		setListener();
		loadDemoData();
		loadData();
	}

	private void initViews() {
		btnBack = (Button) findViewById(R.id.btn_back);
		btnSearch = (Button) findViewById(R.id.btn_search);
		edTxtSearchFriend = (EditText) findViewById(R.id.ed_txt_search_friend);
		lstViewFriendList = (SwipeListView) findViewById(R.id.lst_view_friend_list);
	}

	private void setListener() {
		btnBack.setOnClickListener(this);
		btnSearch.setOnClickListener(this);
		lstViewFriendList.setOnItemClickListener(this);
		lstViewFriendList.setSwipeListViewListener(new BaseSwipeListViewListener() {
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
                lstViewFriendList.openAnimate(position); //when you touch front view it will open
            }

            @Override
            public void onClickBackView(int position) {
                Log.d("swipe", String.format("onClickBackView %d", position));
                
                lstViewFriendList.closeAnimate(position);//when you touch back view it will close
            }

            @Override
            public void onDismiss(int[] reverseSortedPositions) {
            	
            }

        });
        
        //These are the swipe listview settings. you can change these
        //setting as your requirement 
        //swipelistview.setSwipeMode(SwipeListView.SWIPE_MODE_BOTH); // there are five swiping modes
		lstViewFriendList.setSwipeMode(SwipeListView.SWIPE_MODE_LEFT); // there are five swiping modes
		lstViewFriendList.setSwipeActionLeft(SwipeListView.SWIPE_ACTION_NONE); //there are four swipe actions 
		lstViewFriendList.setSwipeActionRight(SwipeListView.SWIPE_ACTION_NONE);
		lstViewFriendList.setOffsetLeft(Utils.convertDpToPixel(220f, ShareWithFriendActivity.this)); // left side offset
		lstViewFriendList.setOffsetRight(Utils.convertDpToPixel(0f, ShareWithFriendActivity.this)); // right side offset
		lstViewFriendList.setAnimationTime(300); // Animation time
		lstViewFriendList.setSwipeOpenOnLongPress(true); // enable or disable SwipeOpenOnLongPress
	
	}

	private void loadData() {
		lstViewFriendList.setAdapter(new FriendsListAdapter(ShareWithFriendActivity.this, PassengerApp.getInstance().getFriendsInfoList()));
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_back:
			onBackPressed();
			break;
			
		case R.id.btn_search:
			startActivity(new Intent(ShareWithFriendActivity.this, FriendSearchActivity.class));
			break;

		default:
			break;
		}
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		showPopupForSharing(arg2);
	}
	
	private void loadDemoData() {
		ArrayList<FriendsInfo> list = new ArrayList<FriendsInfo>();
		 for(int i = 0; i < 20; i++) {
			 FriendsInfo info = new FriendsInfo();
			 info.setName("Testing "+ i);
			 info.setEmail("atomixdroid@gmail.com");
			 list.add(info);
		 }
		 
		 PassengerApp.getInstance().setFriendsInfoList(list);
	}
	
	private void showPopupForSharing(int position) {
		dialogSharing = new DialogController(ShareWithFriendActivity.this).ShareWithFriendDialog();
		
		Button btnCancel = (Button) dialogSharing.findViewById(R.id.btn_cancel);
		btnCancel.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				dialogSharing.dismiss();
			}
		});
		
		TextView txtViewFriendName = (TextView) dialogSharing.findViewById(R.id.txt_view_share_name);
		txtViewFriendName.setText("You are going to\nshare this trip with "+PassengerApp.getInstance().getFriendsInfoList().get(position).getName());
		
		ImageView imageView = (ImageView) dialogSharing.findViewById(R.id.img_view_add_to_friend_list);
		imageView.setVisibility(View.INVISIBLE);
		
		TextView textView = (TextView) dialogSharing.findViewById(R.id.textView1);
		textView.setVisibility(View.INVISIBLE);
		
		Button btnShare = (Button) dialogSharing.findViewById(R.id.btn_share);
		btnShare.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
			}
		});
		dialogSharing.show();
	}
	

}
