package com.netcabs.adapter;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.netcabs.datamodel.Country;
import com.netcabs.passenger.R;

@SuppressWarnings("unused")
public class CountrySpinnerAdapter extends BaseAdapter {
	private LayoutInflater mInflater;
	private Context context;
	private ArrayList<Country> country;

	public CountrySpinnerAdapter(Context context, ArrayList<Country> country) {
		this.context = context;
		this.country = country;
	}

	@Override
	public int getCount() {
		if(country != null) {
			return country.size();
		} else {
			return 0;
		}		
	}

	@Override
	public Object getItem(int position) {
		return country.get(position);
	}

	
	public long getItemId(int position) {
		return position;
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		ViewHolder holder;
		mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

		if (convertView == null) {
			convertView = mInflater.inflate(R.layout.country_spinner_row, null);
			holder = new ViewHolder();
			holder.txtViewCountry = (TextView) convertView.findViewById(R.id.txt_view_country_name);
			holder.imgViewCountry = (ImageView) convertView.findViewById(R.id.img_view_country);
			convertView.setTag(holder);

		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		
		holder.txtViewCountry.setText(country.get(position).getName());
		holder.imgViewCountry.setImageBitmap(country.get(position).getImgBitmap());
		

		return convertView;
	}

	static class ViewHolder {
		TextView txtViewCountry;
		ImageView imgViewCountry;
	}
	
}
