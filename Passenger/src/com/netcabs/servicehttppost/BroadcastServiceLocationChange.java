package com.netcabs.servicehttppost;

import android.app.ProgressDialog;
import android.app.Service;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import com.netcabs.internetconnection.InternetConnectivity;
import com.netcabs.json.CommunicationLayer;
import com.netcabs.passengerinfo.PassengerApp;
import com.netcabs.utils.ConstantValues;

public class BroadcastServiceLocationChange  extends Service {
	private static final String TAG = "BroadcastService";
	public static final String BROADCAST_ACTION = "com.netcabs.MyBookingDetailsActivity";
	private final Handler handler = new Handler();
	Intent intent;
	int counter = 0;
	int count = 0;
	@Override
	public void onCreate() {
		super.onCreate();
    	intent = new Intent(BROADCAST_ACTION);	
	}
	
    @Override
    public void onStart(Intent intent, int startId) {
        handler.removeCallbacks(sendUpdatesToUI);
        handler.postDelayed(sendUpdatesToUI, 1000); // 1 second
   
    }

    private Runnable sendUpdatesToUI = new Runnable() {
    	public void run() {

    		if(InternetConnectivity.isConnectedToInternet(getApplicationContext())) {
    			new GetPostResult().execute();
    			
    		} else {
    			Toast.makeText(getApplicationContext(), ConstantValues.INTERNET_CONNECTION_FAILURE_MSG, Toast.LENGTH_SHORT).show();
    		}
    	    handler.postDelayed(this, 5000); // 5 seconds
    	}
    };    

	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}

	@Override
	public void onDestroy() {		
        handler.removeCallbacks(sendUpdatesToUI);		
		super.onDestroy();
	}
	
	public class GetPostResult extends AsyncTask<Void, Void, Void> {
		ProgressDialog dlog;
		String getResponse= null;
		
		@Override
		protected void onPreExecute() {
			
		}
		
		@Override
		protected Void doInBackground(Void... arg0) {
			try {
				getResponse = CommunicationLayer.getBookingDetails(ConstantValues.FUNC_ID_BOOKING_DETAILS, PassengerApp.getInstance().getPassengerId(), PassengerApp.getInstance().getBookingId());
			} catch (Exception e) {
				e.printStackTrace();
				getResponse = "Exception Location Service";
			}
			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			super.onPostExecute(result);
			
			try {
				if (getResponse.equals("2001")) {
					intent.putExtra("status", getResponse);
			    	sendBroadcast(intent);
			    	
				} else {
					
				}
			} catch (Exception e) {
				Log.i("Print","Response Error" + e.getMessage());
			}
			
		}
	}
	
}
