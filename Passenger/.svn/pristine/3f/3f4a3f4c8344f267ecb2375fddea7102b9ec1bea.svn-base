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
import com.netcabs.servicehttppost.BroadcastService.GetPostResult;
import com.netcabs.utils.ConstantValues;

public class BroadcastServiceSeenUnseen  extends Service {
	private static final String TAG = "BroadcastServiceSeenUnseen";
	public static final String BROADCAST_ACTION = "com.netcabs.ConfirmBookingSearchActivity";
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
    	    handler.postDelayed(this, 3000); // 10 seconds
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
		String getResponse = null;
		
		@Override
		protected void onPreExecute() {
		}
		@Override
		protected Void doInBackground(Void... arg0) {
			try {
				getResponse = CommunicationLayer.getSendAndSeen(ConstantValues.FUNC_ID_SENT_SEEN, PassengerApp.getInstance().getPassengerId(), PassengerApp.getInstance().getBookingId());
				//getResponse = CommunicationLayer.getSendAndSeen(ConstantValues.FUNC_ID_SENT_SEEN, "54046239481a594401344c9e","");
				
			} catch (Exception e) {
				getResponse = "Exception";
				e.printStackTrace();
			}
			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			super.onPostExecute(result);
			Log.i("Response is ", "____________" + getResponse);
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
