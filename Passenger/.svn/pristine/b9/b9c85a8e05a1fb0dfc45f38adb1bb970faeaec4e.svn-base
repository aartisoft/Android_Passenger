package com.netcabs.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.netcabs.passenger.R;

public class SliderMenuAdapter extends BaseAdapter {

	private LayoutInflater mInflater;
	private Context context;
	private String[] menuList;
	private int[] menuIcon;

	public SliderMenuAdapter(Context context, String[] menuList, int[] menuIcon) {
		this.context = context;
		this.menuList = menuList;
		this.menuIcon = menuIcon;
	}

	@Override
	public int getCount() {
		if(menuList != null) {
			return menuList.length;
		} else {
			return 0;
		}
	}

	@Override
	public Object getItem(int position) {
		return menuList[position];
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
			convertView = mInflater.inflate(R.layout.slider_menu_row, null);
			holder = new ViewHolder();
			holder.txtViewName = (TextView) convertView.findViewById(R.id.txt_view_name);
			holder.imgViewLogo = (ImageView) convertView.findViewById(R.id.img_view_logo);
			convertView.setTag(holder);

		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		
		holder.txtViewName.setText(menuList[position].toString());
		if(position == 0) {
			holder.imgViewLogo.setBackgroundResource(R.drawable.home);
		} else {
			holder.imgViewLogo.setBackgroundResource(menuIcon[position]);
		}
		

		return convertView;
	}

	static class ViewHolder {
		TextView txtViewName;
		ImageView imgViewLogo;
	}

}
