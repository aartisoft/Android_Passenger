package com.netcabs.asynctask;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;

import com.netcabs.interfacecallback.OnRequestComplete;
import com.netcabs.json.CommunicationLayer;

public class HailedBookingAsyncTask extends AsyncTask<String, String, Void> {

	private Activity activity;
	private ProgressDialog progressDialog;
	private OnRequestComplete callback;
	private String responseStatus;

	public HailedBookingAsyncTask(Activity activity, OnRequestComplete callback2) {
		this.activity = activity;
		this.callback = (OnRequestComplete) callback2;
	}

	@Override
	protected void onPreExecute() {
		super.onPreExecute();
		progressDialog = ProgressDialog.show(activity, "", "Loading...", true, false);
	}

	@Override
	protected Void doInBackground(String... params) {
		String func_id = params[0];
		String id = params[1];
		String rego_no = params[2];
		String dest_long = params[3];
		String dest_lat = params[4];
		String destination_address = params[5];
		String src_long = params[6];
		String src_lat = params[7];
		String pickup_address = params[8];
		String booking_via = params[9];
		String payment_method = params[10];
		String booking_date = params[11];
		String booking_time = params[12];
		String passenger_num = params[13];
		String is_parcel = params[14];
		
		try {
			responseStatus = CommunicationLayer.getHailedBooking(func_id, id, rego_no, dest_long, dest_lat, destination_address, src_long, src_lat, pickup_address, booking_via, payment_method, booking_date, booking_time, passenger_num, is_parcel);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}

	@Override
	protected void onPostExecute(Void result) {
		super.onPostExecute(result);
		try {
			if ((progressDialog != null) && progressDialog.isShowing()) {
				progressDialog.dismiss();
			}
		} catch (final IllegalArgumentException e) {
		} catch (final Exception e) {
		} finally {
			progressDialog = null;
		}
		
		
		callback.onRequestComplete(responseStatus);
	}

}
