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

public class RegistrationAsyncTask extends AsyncTask<String, Void, Void> {

	private Activity activity;
	private ProgressDialog progressDialog;
	private OnRequestComplete callback;
	private String responseStatus;

	public RegistrationAsyncTask(Activity activity, OnRequestComplete callback2) {
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
		Log.i("Device Token", "device token no " + params[2]);
		String func_id = params[0];
		String device_type = params[1];
		String device_token = params[2];
		String registration_key = params[3];
		String ip = params[4];
		String firstName = params[5];
		String lastName = params[6];
		String profile_type = params[7];
		String profile_id = params[8];
		String password = params[9];
		String country = params[10];
		String emailAddress = params[11];
		String zipCode = params[12];
		String contactNumber = params[13];
		String profile_pic = params[14];
		String pic_path = params[15];
		String user_type = params[16];
		try {
			HttpClient httpclient = new DefaultHttpClient();
			HttpPost httppost = new HttpPost(PassengerApp.getInstance().getBaseUrl());

			MultipartEntity reqEntry = new MultipartEntity(HttpMultipartMode.BROWSER_COMPATIBLE);
			reqEntry.addPart("func_id", new StringBody(func_id));
			reqEntry.addPart("device_type", new StringBody(device_type));
			reqEntry.addPart("device_token", new StringBody(device_token));
			reqEntry.addPart("registration_key", new StringBody(registration_key));
			reqEntry.addPart("ip", new StringBody(ip));
			reqEntry.addPart("first_name", new StringBody(firstName));
			reqEntry.addPart("last_name", new StringBody(lastName));
			reqEntry.addPart("profile_type", new StringBody(profile_type));
			reqEntry.addPart("profile_id", new StringBody(profile_id));
			reqEntry.addPart("password", new StringBody(password));
			reqEntry.addPart("country_id", new StringBody(country));
			reqEntry.addPart("email", new StringBody(emailAddress));
			reqEntry.addPart("zip", new StringBody(zipCode));
			reqEntry.addPart("mobile_no", new StringBody(contactNumber));
			
			if(!profile_pic.equalsIgnoreCase("")) {
				FileBody bin = new FileBody(new File(profile_pic), "image/jpeg");
				reqEntry.addPart("profile_pic", bin);
			}
			
			if(!pic_path.equalsIgnoreCase("")) {
				//FileBody bin2 = new FileBody(new File(pic_path), "image/jpeg");
				reqEntry.addPart("pic_path", new StringBody(pic_path));
			}

			
			reqEntry.addPart("user_type", new StringBody(user_type));
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
			if(jDataObj.getString("status").equals("1")) {
				if ("2001".equals(jDataObj.getString("status_code"))) {
					PassengerApp.getInstance().setPassengerId(jDataObj.getString("id").toString().trim());
					PassengerApp.getInstance().setLogInType(Integer.parseInt(profile_type));
				}
				
//				info.setFirstName(jDataObj.getString("first_name").toString().trim());
//				info.setLasName(jDataObj.getString("last_name").toString().trim());
//				info.setEmail(jDataObj.getString("email").toString().trim());
//				info.setMobileNo(jDataObj.getString("mobile_no").toString().trim());
//				PassengerApp.getInstance().setProfileInfo(info);
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
		
		
		Log.e("Response is.....", "---"+responseStatus);
		callback.onRequestComplete(responseStatus);
	}
	
}

