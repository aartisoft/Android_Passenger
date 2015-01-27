package com.netcabs.adapter;

import java.util.ArrayList;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.androidquery.AQuery;
import com.google.android.gms.maps.model.LatLng;
import com.netcabs.asynctask.GetDurationAsyncTask;
import com.netcabs.datamodel.MyBookingInfo;
import com.netcabs.interfacecallback.OnRequestComplete;
import com.netcabs.internetconnection.InternetConnectivity;
import com.netcabs.passenger.R;
import com.netcabs.utils.ConstantValues;
import com.netcabs.utils.Utils;

public class MyBookingAdapter extends BaseAdapter {

	private LayoutInflater mInflater;
	private Context context;
	private ArrayList<MyBookingInfo> myBookingList;

	public MyBookingAdapter(Context context, ArrayList<MyBookingInfo> myBookingList) {
		this.context = context;
		this.myBookingList = myBookingList;
	}

	@Override
	public int getCount() {
		if(myBookingList != null) {
			return myBookingList.size();
		} else {
			return 0;
		}
	}

	@Override
	public Object getItem(int position) {
		return myBookingList.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {

		final ViewHolder holder;
		mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

		if (convertView == null) {
			convertView = mInflater.inflate(R.layout.my_booking_row, null);
			holder = new ViewHolder();
			holder.txtViewTime = (TextView) convertView.findViewById(R.id.txt_view_time);
			holder.txtViewDate = (TextView) convertView.findViewById(R.id.txt_view_date);
			holder.txtViewRegNo = (TextView) convertView.findViewById(R.id.txt_view_reg_no);
			holder.txtViewDistance = (TextView) convertView.findViewById(R.id.txt_view_distance);
			holder.txtViewPrice = (TextView) convertView.findViewById(R.id.txt_view_price);
			holder.imgViewTaxiLogo = (ImageView) convertView.findViewById(R.id.img_view_taxi_logo);
			convertView.setTag(holder);

		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		Log.e("time is", "----"+myBookingList.get(position).getTime().toString());
		Log.e("date is", "----"+myBookingList.get(position).getDate().toString());
		if(myBookingList.get(position).getTime().toString()!= null)
		{
			holder.txtViewTime.setText("Time: " +Utils.getTimeDateFormat(myBookingList.get(position).getTime().toString()));
		}
		if(myBookingList.get(position).getDate().toString()!= null) {
			holder.txtViewDate.setText("Date: " +Utils.getTimeDateFormat(myBookingList.get(position).getDate().toString()));
		}
		
		if(myBookingList.get(position).getDistance().toString()!= null) {
			if(myBookingList.get(position).getDistance().toString().trim().equalsIgnoreCase("null"))  {
				holder.txtViewDistance.setText("Distance: 00 km");
			} else {
				holder.txtViewDistance.setText("Distance: "+myBookingList.get(position).getDistance());
			}
		} else {
			holder.txtViewDistance.setText("Distance: 00 km");
		}
		
		holder.txtViewPrice.setText("$" + myBookingList.get(position).getPrice());
		
		holder.txtViewRegNo.setText("Reg no: " +myBookingList.get(position).getTaxiRegNo().toString());
		//holder.txtViewPrice.setText("$ " +myBookingList.get(position).getPrice());
		LatLng pickUpLatLon = new LatLng(myBookingList.get(position).getPickupLat(), myBookingList.get(position).getPickupLon());
		LatLng destinationLatLon = new LatLng(myBookingList.get(position).getDestinationLat(), myBookingList.get(position).getDestinationLon());
		
		Log.i("Value is "+pickUpLatLon, "_________"+destinationLatLon);
		
		new AQuery(context).id(holder.imgViewTaxiLogo).image(myBookingList.get(position).getTaxiLogoUrl(), true, true, 50, R.drawable.texi_logo);
		
		
		
//		if(InternetConnectivity.isConnectedToInternet(context)) {
//			
//			if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
//				new GetDurationAsyncTask(context, pickUpLatLon, destinationLatLon,  new OnRequestComplete() {
//					@Override
//					public void onRequestComplete(String result) {
//						Log.e("Duration is:", "::::"+result);
//						String [] distanceDuration = result.split("//");
//						myBookingList.get(position).setDistance(distanceDuration[0]);
//						myBookingList.get(position).setDurationTime(distanceDuration[1]);
//						holder.txtViewDistance.setText("Distance: " +myBookingList.get(position).getDistance());
//						Log.e("I am here", "---------");
//						
//						Double farePrice = 0.00;
//						if(myBookingList.get(position).getDistance().contains(",")){
//							myBookingList.get(position).setDistance(myBookingList.get(position).getDistance().replace(",", ""));
//						}
//						if (myBookingList.get(position).getDistance().split(" ")[0] != null || !myBookingList.get(position).getDistance().split(" ")[0].equalsIgnoreCase("null") || !"".equalsIgnoreCase(myBookingList.get(position).getDistance().split(" ")[0])) {
//							farePrice = Double.parseDouble(myBookingList.get(position).getDistance().split(" ")[0]) * 10;
//						}
//						
//						holder.txtViewPrice.setText("$" + String.format("%.2f", farePrice));
//					}
//				}).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
//			} else {
//				new GetDurationAsyncTask(context, pickUpLatLon, destinationLatLon,  new OnRequestComplete() {
//					@Override
//					public void onRequestComplete(String result) {
//						Log.e("Duration is:", "::::"+result);
//						String [] distanceDuration = result.split("//");
//						myBookingList.get(position).setDistance(distanceDuration[0]);
//						myBookingList.get(position).setDurationTime(distanceDuration[1]);
//						holder.txtViewDistance.setText("Distance: " +myBookingList.get(position).getDistance());
//						Log.e("I am here", "---------");
//						
//						Double farePrice = 0.00;
//						if(myBookingList.get(position).getDistance().contains(",")){
//							myBookingList.get(position).setDistance(myBookingList.get(position).getDistance().replace(",", ""));
//						}
//						if (myBookingList.get(position).getDistance().split(" ")[0] != null || !myBookingList.get(position).getDistance().split(" ")[0].equalsIgnoreCase("null") || !"".equalsIgnoreCase(myBookingList.get(position).getDistance().split(" ")[0])) {
//							farePrice = Double.parseDouble(myBookingList.get(position).getDistance().split(" ")[0]) * 10;
//						}
//						
//						holder.txtViewPrice.setText("$" + String.format("%.2f", farePrice));
//					}
//				}).execute();
//			}
//			
//		} else {
//			Toast.makeText(context, ConstantValues.INTERNET_CONNECTION_FAILURE_MSG, Toast.LENGTH_SHORT).show();
//		}

		return convertView;
	}

	static class ViewHolder {
		TextView txtViewTime;
		TextView txtViewDate;
		TextView txtViewRegNo;
		TextView txtViewDistance;
		TextView txtViewPrice;
		ImageView imgViewTaxiLogo;
	}

}
