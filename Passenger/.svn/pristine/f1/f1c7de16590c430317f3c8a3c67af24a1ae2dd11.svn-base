package com.netcabs.servicehttppost;

import java.util.ArrayList;

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
import com.netcabs.latlon.GPSTracker;
import com.netcabs.passenger.HailedCabDetailsActivity;
import com.netcabs.passengerinfo.PassengerApp;
import com.netcabs.utils.ConstantValues;

public class BroadcastService  extends Service {
	private static final String TAG = "BroadcastService";
	public static final String BROADCAST_ACTION = "com.netcabs.mapfragment";
	private final Handler handler = new Handler();
	Intent intent;
	int counter = 0;
	ArrayList<String> arrrayList = new ArrayList<String>();
	int count = 0;
	@Override
	public void onCreate() {
		super.onCreate();
		arrrayList.add("23.816278921411115,90.42631149291992");
		arrrayList.add("23.81384472359206,90.42274951934814");
		arrrayList.add("23.812372404371864,90.4259467124939");
		arrrayList.add("23.81201904527452,90.43161153793335");
		arrrayList.add("23.809859607672323,90.43116092681885");
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
    		
    	    handler.postDelayed(this, 3000); // 3 seconds
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
				if(PassengerApp.getInstance().getSearchInfo() != null) {
					getResponse = CommunicationLayer.getTaxiInfoData(ConstantValues.FUNC_ID_TAXI_INFO, PassengerApp.getInstance().getPassengerId(), Double.toString(PassengerApp.getInstance().getSearchInfo().getLocationLatitude()), Double.toString(PassengerApp.getInstance().getSearchInfo().getLocationLongitude()));
				} else {
					getResponse = CommunicationLayer.getTaxiInfoData(ConstantValues.FUNC_ID_TAXI_INFO, PassengerApp.getInstance().getPassengerId(), Double.toString(new GPSTracker(getApplicationContext()).getLatitude()), Double.toString(new GPSTracker(getApplicationContext()).getLatitude()));
				}
				//getResponse = CommunicationLayer.getTaxiInfoData(ConstantValues.FUNC_ID_TAXI_INFO, "54046239481a594401344c9e", "23.812950997796495", "90.4286290705204");
			} catch (Exception e) {
				getResponse = "Exception";
				e.printStackTrace();
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
