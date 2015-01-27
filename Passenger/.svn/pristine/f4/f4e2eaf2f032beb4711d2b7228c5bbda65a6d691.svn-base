package com.netcabs.asynctask;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;

import com.netcabs.interfacecallback.OnRequestComplete;
import com.netcabs.json.CommunicationLayer;

public class ContactEmailAsyncTask extends AsyncTask<String, Void, Void> {

	private Activity activity;
	private ProgressDialog progressDialog;
	private OnRequestComplete callback;
	private String responseStatus;

	public ContactEmailAsyncTask(Activity activity, OnRequestComplete callback2) {
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
		String subject = params[2];
		String body = params[3];
		try {
			responseStatus = CommunicationLayer.getContactEmail(func_id, id, subject, body);
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

