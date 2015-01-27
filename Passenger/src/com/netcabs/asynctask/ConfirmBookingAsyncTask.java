package com.netcabs.asynctask;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;

import com.netcabs.interfacecallback.OnRequestComplete;
import com.netcabs.json.CommunicationLayer;

public class ConfirmBookingAsyncTask extends AsyncTask<String, String, Void> {

	private Activity activity;
	private ProgressDialog progressDialog;
	private OnRequestComplete callback;
	private String responseStatus;

	public ConfirmBookingAsyncTask(Activity activity, OnRequestComplete callback2) {
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
		String dest_long = params[2];
		String dest_lat = params[3];
		String destination_address = params[4];
		String src_long = params[5];
		String src_lat = params[6];
		String pickup_address = params[7];
		String booking_via = params[8];
		String payment_method = params[9];
		String booking_date = params[10];
		String booking_time = params[11];
		String passenger_num = params[12];
		String is_parcel = params[13];
		String taxi_id = params[14];
		
		try {
			responseStatus = CommunicationLayer.getBooking(func_id, id, dest_long, dest_lat, destination_address, src_long, src_lat, pickup_address, booking_via, payment_method, booking_date, booking_time, passenger_num, is_parcel, taxi_id);
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
