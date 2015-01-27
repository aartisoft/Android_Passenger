package com.netcabs.asynctask;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.util.Log;

import com.netcabs.interfacecallback.OnRequestComplete;
import com.netcabs.passengerinfo.PassengerApp;

public class ProfileUpdateAsyncTask extends AsyncTask<String, Void, Void> {

	private Activity activity;
	private ProgressDialog progressDialog;
	private OnRequestComplete callback;
	private String responseStatus;

	public ProfileUpdateAsyncTask(Activity activity, OnRequestComplete callback2) {
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
		String first_name = params[2];
		String last_name = params[3];
		String current_password = params[4];
		String password = params[5];
		String country = params[6];
		String email = params[7];
		String zip = params[8];
		String mobile_no = params[9];
		String profile_pic = params[10];
		String pic_path = params[11];
		
		try {
			HttpClient httpclient = new DefaultHttpClient();
			HttpPost httppost = new HttpPost(PassengerApp.getInstance().getBaseUrl());

			MultipartEntity reqEntry = new MultipartEntity(HttpMultipartMode.BROWSER_COMPATIBLE);
			reqEntry.addPart("func_id", new StringBody(func_id));
			reqEntry.addPart("id", new StringBody(id));
			reqEntry.addPart("first_name", new StringBody(first_name));
			reqEntry.addPart("last_name", new StringBody(last_name));
			reqEntry.addPart("current_password", new StringBody(current_password));
			reqEntry.addPart("password", new StringBody(password));
			reqEntry.addPart("country", new StringBody(country));
			reqEntry.addPart("email", new StringBody(email));
			reqEntry.addPart("zip", new StringBody(zip));
			reqEntry.addPart("mobile_no", new StringBody(mobile_no));
			
			if(!profile_pic.equalsIgnoreCase("")) {
				FileBody bin = new FileBody(new File(profile_pic), "image/jpeg");
				reqEntry.addPart("profile_pic", bin);
			}
			
			if(!pic_path.equalsIgnoreCase("")) {
				FileBody bin2 = new FileBody(new File(pic_path), "image/jpeg");
				reqEntry.addPart("pic_path", bin2);
			}
			
			httppost.setEntity(reqEntry);

			HttpResponse response = httpclient.execute(httppost);
			HttpEntity resEntity = response.getEntity();

			InputStream is = resEntity.getContent();
			BufferedReader reader = new BufferedReader(new InputStreamReader(is, "iso-8859-1"), 8);

			StringBuilder sb = new StringBuilder();
			String line = null;

			while ((line = reader.readLine()) != null) {
				sb.append(line);
			}

			is.close();
			String thisdata = "";
			thisdata = sb.toString().trim();
			Log.i("update ", "__________"+thisdata);
			JSONObject jDataObj = new JSONObject(thisdata);
			
			if (jDataObj.getString("pic_path") !=null) {
				PassengerApp.getInstance().getProfileDetailsInfo().setProfilePic(jDataObj.getString("pic_path"));
			}
			
			responseStatus = jDataObj.getString("status_code");

		} catch (Exception ex) {
			ex.printStackTrace();
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
