package com.netcabs.asynctask;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;

import com.netcabs.interfacecallback.OnRequestComplete;
import com.netcabs.json.CommunicationLayer;

public class PhoneVerifyAsyncTask extends AsyncTask<String, Void, Void> {

	private Activity activity;
	private ProgressDialog progressDialog;
	private OnRequestComplete callback;
	private String responseStatus;

	public PhoneVerifyAsyncTask(Activity activity, OnRequestComplete callback2) {
		this.activity = activity;
		this.callback = (OnRequestComplete) callback2;
	}

	@Override
	protected void onPreExecute() {
		super.onPreExecute();
		progressDialog = ProgressDialog.show(activity, "", "Checking...", true, false);
	}

	@Override
	protected Void doInBackground(String... params) {
		String func_id = params[0];
		String user_id = params[1];
		String activation_code = params[2];
		try {
			 responseStatus = CommunicationLayer.getMobileSetPin(func_id, user_id, activation_code);
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
