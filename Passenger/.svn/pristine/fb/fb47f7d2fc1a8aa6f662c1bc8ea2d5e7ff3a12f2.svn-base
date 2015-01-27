package com.netcabs.asynctask;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;

import com.netcabs.interfacecallback.OnRequestComplete;
import com.netcabs.json.CommunicationLayer;

public class CardRegistrationAsyncTask extends AsyncTask<String, Void, Void> {

	private Activity activity;
	private ProgressDialog progressDialog;
	private OnRequestComplete callback;
	private String responseStatus;

	public CardRegistrationAsyncTask(Activity activity, OnRequestComplete callback2) {
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
		String card_type = params[2];
		String card_num = params[3];
		String card_holder_name = params[4];
		String card_exp_date = params[5];
		String cvv = params[6];
		String country_id = params[7];
		String zip_code = params[8];
		String is_default = params[9];
		try {
			responseStatus = CommunicationLayer.getCardRegistrationData(func_id, id, card_type, card_num, card_holder_name, card_exp_date, cvv, country_id, zip_code, is_default);
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
