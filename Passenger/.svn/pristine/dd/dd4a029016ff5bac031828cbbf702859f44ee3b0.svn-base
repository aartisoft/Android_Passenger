//package com.netcabs.asynctask;
//
//import android.app.Activity;
//import android.app.ProgressDialog;
//import android.os.AsyncTask;
//
//import com.netcabs.interfacecallback.OnRequestComplete;
//import com.netcabs.json.CommunicationLayer;
//
//public class ResendCodeAsyncTask extends AsyncTask<String, Void, Void> {
//
//	private Activity activity;
//	private ProgressDialog progressDialog;
//	private OnRequestComplete callback;
//	private String responseStatus;
//
//	public ResendCodeAsyncTask(Activity activity, OnRequestComplete callback2) {
//		this.activity = activity;
//		this.callback = (OnRequestComplete) callback2;
//	}
//
//	@Override
//	protected void onPreExecute() {
//		super.onPreExecute();
//		progressDialog = ProgressDialog.show(activity, "", "Sending...", true, false);
//	}
//
//	@Override
//	protected Void doInBackground(String... params) {
//		String func_id = params[0];
//		String id = params[1];
//		try {
//			responseStatus = CommunicationLayer.getResendCode(func_id, id);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		
//		return null;
//	}
//
//	@Override
//	protected void onPostExecute(Void result) {
//		super.onPostExecute(result);
//		if(progressDialog.isShowing()) {
//			progressDialog.dismiss();
//		}
//		
//		callback.onRequestComplete(responseStatus);
//	}
//
//}
