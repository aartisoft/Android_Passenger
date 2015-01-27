package com.netcabs.adapter;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
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
import com.netcabs.asynctask.AddressByCoordinatesAsyncTask;
import com.netcabs.asynctask.GetDurationAsyncTask;
import com.netcabs.datamodel.TaxiInfo;
import com.netcabs.interfacecallback.OnComplete;
import com.netcabs.interfacecallback.OnRequestComplete;
import com.netcabs.internetconnection.InternetConnectivity;
import com.netcabs.passenger.R;
import com.netcabs.passengerinfo.PassengerApp;
import com.netcabs.utils.ConstantValues;

public class TaxiAdapter extends BaseAdapter {

	private LayoutInflater mInflater;
	private Activity context;
	private ArrayList<TaxiInfo> taxiList;
	private String urlTogetAddress;

	public TaxiAdapter(Activity context, ArrayList<TaxiInfo> taxiList) {
		this.context = context;
		this.taxiList = taxiList;
	}

	@Override
	public int getCount() {
		if(taxiList != null) {
			return taxiList.size();
		} else {
			return 0;
		}
	}

	@Override
	public Object getItem(int position) {
		return taxiList.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		
		try {
			final ViewHolder holder;
			mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

			if (convertView == null) {
				convertView = mInflater.inflate(R.layout.taxi_info_row, null);
				holder = new ViewHolder();
				holder.txtViewRegNo = (TextView) convertView.findViewById(R.id.txt_view_reg_no);
				holder.txtViewLocationName = (TextView) convertView.findViewById(R.id.txt_view_location);
				holder.txtViewTime = (TextView) convertView.findViewById(R.id.txt_view_time);
				holder.imgViewTaxiLogo = (ImageView) convertView.findViewById(R.id.img_view_taxi_logo);
				convertView.setTag(holder);

			} else {
				holder = (ViewHolder) convertView.getTag();
			}
			
			holder.txtViewRegNo.setText(taxiList.get(position).getTaxiNumber());
			
			//holder.txtViewTime.setText(taxiList.get(position).getTime().toString());
			holder.imgViewTaxiLogo.setImageResource(android.R.color.transparent); 
			holder.imgViewTaxiLogo.setImageBitmap(null);
			
			new AQuery(context).id(holder.imgViewTaxiLogo).image(taxiList.get(position).getTaxiLogo(), true, true, 50, R.drawable.texi_logo);
			Log.e("lat & lon", "----"+taxiList.get(position).getTaxiLat()+":::"+taxiList.get(position).getTaxiLon());
			urlTogetAddress = "http://maps.googleapis.com/maps/api/geocode/json?latlng="+taxiList.get(position).getTaxiLat()+","+taxiList.get(position).getTaxiLon()+"&sensor=true";
			
			if(InternetConnectivity.isConnectedToInternet(context)) {
				new AddressByCoordinatesAsyncTask(context, urlTogetAddress, new OnComplete() {
					
					@Override
					public void onComplete(int result,ArrayList<String> data, ArrayList<String> refId) {
					}

					@Override
					public void onLocationComplete(int result, double lat, double lon) {
					}

					@Override
					public void onAddressComplete(int result, String address) {
						Log.e("Address is", "----------"+address);
						holder.txtViewLocationName.setText(address);
					}
			
				}).execute();
			
			} else {
				Toast.makeText(context, ConstantValues.INTERNET_CONNECTION_FAILURE_MSG, Toast.LENGTH_SHORT).show();
			}
			if(PassengerApp.getInstance().getSearchInfo() != null) {
				LatLng taxiLatLng = new LatLng( taxiList.get(position).getTaxiLat(), taxiList.get(position).getTaxiLon());
				LatLng userLatLng = new LatLng( PassengerApp.getInstance().getSearchInfo().getLocationLatitude(), PassengerApp.getInstance().getSearchInfo().getLocationLongitude());
				Log.e("my lat & lon", userLatLng.latitude +"-------------"+userLatLng.longitude);
				if(InternetConnectivity.isConnectedToInternet(context)) {
					new GetDurationAsyncTask(context, userLatLng, taxiLatLng,  new OnRequestComplete() {
						@Override
						public void onRequestComplete(String result) {
							
							try {
								Log.e("Duration of item is:", "::::"+result);
								String [] distanceDuration = result.split("//");
								PassengerApp.getInstance().getTaxiInfoList().get(position).setTimeDurationToReach(distanceDuration[1]);
								holder.txtViewTime.setText(PassengerApp.getInstance().getTaxiInfoList().get(position).getTimeDurationToReach());
								Log.e("List distance", "---------" + distanceDuration[1]);
								
							} catch (Exception e) {
								Log.e("Taxi list exception", "---------" + e.getMessage());
							}
							
						}
					}).execute();
				} else {
					Toast.makeText(context, ConstantValues.INTERNET_CONNECTION_FAILURE_MSG, Toast.LENGTH_SHORT).show();
				}
			} else {
				
			}

			return convertView;
			
		} catch (Exception e) {
			Log.e("Exception Taxi adapter", "***" + e.getMessage());
		}
		
		return null;
	}

	static class ViewHolder {
		TextView txtViewRegNo;
		TextView txtViewLocationName;
		TextView txtViewTime;
		ImageView imgViewTaxiLogo;
	}

}
