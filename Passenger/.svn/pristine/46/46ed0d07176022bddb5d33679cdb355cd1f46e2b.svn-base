package com.netcabs.adapter;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.netcabs.datamodel.FriendsInfo;
import com.netcabs.passenger.R;

public class FriendsListAdapter extends BaseAdapter {

	private LayoutInflater mInflater;
	private Context context;
	private ArrayList<FriendsInfo> friendsList;

	public FriendsListAdapter(Context context, ArrayList<FriendsInfo> friendsList) {
		this.context = context;
		this.friendsList = friendsList;
	}

	@Override
	public int getCount() {
		if(friendsList != null) {
			return friendsList.size();
		} else {
			return 0;
		}
	}

	@Override
	public Object getItem(int position) {
		return friendsList.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		ViewHolder holder;
		mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

		if (convertView == null) {
			convertView = mInflater.inflate(R.layout.friend_list_row, null);
			holder = new ViewHolder();
			holder.txtViewFriendName = (TextView) convertView.findViewById(R.id.txt_view_friend_name);
			holder.txtViewFriendEmail = (TextView) convertView.findViewById(R.id.txt_view_email);
			holder.imgViewPic = (ImageView) convertView.findViewById(R.id.img_view_user_pic);
			convertView.setTag(holder);

		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		
		holder.txtViewFriendName.setText(friendsList.get(position).getName().toString());
		holder.txtViewFriendEmail.setText(friendsList.get(position).getEmail().toString());
		

		return convertView;
	}

	static class ViewHolder {
		TextView txtViewFriendName;
		TextView txtViewFriendEmail;
		ImageView imgViewPic;
	}

}
