package com.netcabs.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import android.app.Activity;
import android.content.Context;
import android.content.CursorLoader;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.location.Address;
import android.location.Geocoder;
import android.location.LocationManager;
import android.net.Uri;
import android.provider.MediaStore;
import android.provider.MediaStore.Images;
import android.util.DisplayMetrics;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;

public class Utils {

	public static Bitmap getResizedBitmap(Bitmap bm, int newHeight, int newWidth) {
		int width = bm.getWidth();
		int height = bm.getHeight();
		float scaleWidth = ((float) newWidth) / width;
		float scaleHeight = ((float) newHeight) / height;
		// CREATE A MATRIX FOR THE MANIPULATION
		Matrix matrix = new Matrix();
		// RESIZE THE BIT MAP
		matrix.postScale(scaleWidth, scaleHeight);
		// RECREATE THE NEW BITMAP
		Bitmap resizedBitmap = Bitmap.createBitmap(bm, 0, 0, width, height,matrix, false);
		return resizedBitmap;

	}
	
	 public static int convertDpToPixel(float dp, Activity activity) {
	        DisplayMetrics metrics = activity.getResources().getDisplayMetrics();
	        float px = dp * (metrics.densityDpi / 160f);
	        return (int) px;
	    }
	 
	 public static String getBitmapPath(Context inContext, Bitmap inImage) {
		  String path = Images.Media.insertImage(inContext.getContentResolver(), inImage, "LetzGO_UpP", null);
		  return getRealPathFromURI(inContext, Uri.parse(path));
		 // return Uri.parse(path).getPath().toString();
		}
	 
	 public static String getRealPathFromURI(Context context, Uri contentUri) {
		  String[] proj = { MediaStore.Images.Media.DATA };
		  
		  CursorLoader cursorLoader = new CursorLoader(context, 
		            contentUri, proj, null, null, null);        
		  Cursor cursor = cursorLoader.loadInBackground();
		  
		  int column_index = 
		    cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
		  cursor.moveToFirst();
		  return cursor.getString(column_index); 
		 }
	 
	 public static int calculateInSampleSize(
	            BitmapFactory.Options options, int reqWidth, int reqHeight) {
	    // Raw height and width of image
	    final int height = options.outHeight;
	    final int width = options.outWidth;
	    int inSampleSize = 1;

	    if (height > reqHeight || width > reqWidth) {

	        final int halfHeight = height / 2;
	        final int halfWidth = width / 2;

	        // Calculate the largest inSampleSize value that is a power of 2 and keeps both
	        // height and width larger than the requested height and width.
	        while ((halfHeight / inSampleSize) > reqHeight
	                && (halfWidth / inSampleSize) > reqWidth) {
	            inSampleSize *= 2;
	        }
	    }

	    return inSampleSize;
	}
	 
	 
	public static LatLngBounds centerIncidentRouteOnMap(ArrayList<LatLng> copiedPoints) {
		double minLat = Integer.MAX_VALUE;
		double maxLat = Integer.MIN_VALUE;
		double minLon = Integer.MAX_VALUE;
		double maxLon = Integer.MIN_VALUE;
		for (LatLng point : copiedPoints) {
			maxLat = Math.max(point.latitude, maxLat);
			minLat = Math.min(point.latitude, minLat);
			maxLon = Math.max(point.longitude, maxLon);
			minLon = Math.min(point.longitude, minLon);
		}
		final LatLngBounds bounds = new LatLngBounds.Builder()
				.include(new LatLng(maxLat, maxLon))
				.include(new LatLng(minLat, minLon)).build();
		
		return bounds;
		
	}
	
	public static String getCountryName(Activity activity, double lat, double lon) {
		 Geocoder myLocation = new Geocoder(activity, Locale.getDefault());
		 String country = null;
		 List<Address> myList = null;
		 
         try {
             myList = myLocation.getFromLocation(lat, lon, 1);
             
         } catch (Exception e) {
             e.printStackTrace();
         }
         
         if(myList != null) {
             try{
                 country = myList.get(0).getCountryName();
              } catch (Exception e) {
           	   
              }
         }
		return country;
	
	}
	
	public static String getTimeDateFormat(String timeDate) {
		 String formatedTimeDate = "";
		 try {
			 if(timeDate.contains("/")){
				 SimpleDateFormat sdf = new SimpleDateFormat("d/M/yyyy");
				  Date dateObj = sdf.parse(timeDate);
				  formatedTimeDate = new SimpleDateFormat("dd/MM/yy").format(dateObj);
			 }else if(timeDate.contains(":")){
				  SimpleDateFormat sdf = new SimpleDateFormat("H:m:s");
				  Date dateObj = sdf.parse(timeDate);
				  formatedTimeDate = new SimpleDateFormat("HH:mm:ss").format(dateObj);
				 }
		 } catch (final ParseException e) {
		     e.printStackTrace();
		 }
		 return  formatedTimeDate;
	}
	
	public static String getBookingTimeDateFormat(String timeDate) {
		 String formatedTimeDate = "";
		 try {
			 if(timeDate.contains("/")){
				 SimpleDateFormat sdf = new SimpleDateFormat("d/M/yyyy");
				  Date dateObj = sdf.parse(timeDate);
				  formatedTimeDate = new SimpleDateFormat("dd / MM / yy").format(dateObj);
			 }else if(timeDate.contains(":")){
				  SimpleDateFormat sdf = new SimpleDateFormat("H:m:s");
				  Date dateObj = sdf.parse(timeDate);
				  formatedTimeDate = new SimpleDateFormat("HH : mm : ss").format(dateObj);
				 }
		 } catch (final ParseException e) {
		     e.printStackTrace();
		 }
		 return  formatedTimeDate;
	}
	
	public static String getFormatedDuration(String duration){
		String formatedduration = "";
		String[] splitDuration = null;

		if (duration.contains("hours") || duration.contains("hour")) {
			splitDuration = duration.split(" ");
			String hour = "";
			String min = "";
			if (splitDuration[0].length() == 1) {
				hour = "0" + splitDuration[0];
			} else {
				hour = splitDuration[0];
			}
			if (splitDuration[2].length() == 1) {
				min = "0" + splitDuration[2];
			} else {
				min = splitDuration[2];
			}
			formatedduration = hour + ":" + min + " h";

		} else {
			splitDuration = duration.split(" ");
			String hour = "00";
			String min = "";
			if (splitDuration[0].length() == 1) {
				min = "0" + splitDuration[0];
			} else {
				min = splitDuration[0];
			}
			formatedduration = hour + ":" + min + " h";
		}
		return formatedduration;
	}
	
	
	public static String getFormatedDistance(String distance){
		String formateddistance = "";
		String[] splitDistance = null;

		if (distance.contains(".")) {
			splitDistance = distance.split(" |\\.");
			String d1 = "";
			String d2 = "";
			if (splitDistance[0].length() == 1) {
				d1 = "0" + splitDistance[0];
			} else {
				d1 = splitDistance[0];
			}
			if (splitDistance[1].length() == 1) {
				d2 = "0" + splitDistance[1];
			} else {
				d2 = splitDistance[1];
			}
			formateddistance = d1 + "." + d2 + " km";

		} else {
			splitDistance = distance.split(" ");
			String d = "";
			if (splitDistance[0].length() == 1) {
				d = "0" + splitDistance[0];
			} else {
				d = splitDistance[0];
			}
			formateddistance = d+" km";
		}
		return formateddistance;
	}
	
	
	public static boolean  isGpsEnable(Context mContext) {
		 // flag for network status
	    boolean isNetworkEnabled = false;
	    boolean isGPSEnabled = false;
	 
	    // flag for GPS status
	    boolean canGetLocation = false;
		LocationManager locationManager  = (LocationManager) mContext.getSystemService(Context.LOCATION_SERVICE);
        // getting GPS status
        isGPSEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);

        // getting network status
        isNetworkEnabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
        if (!isGPSEnabled && !isNetworkEnabled) {
        	Toast.makeText(mContext,"GPS and Network both are disable, Please turn on", Toast.LENGTH_SHORT).show();
        	return false;
        	//showSettingsAlert();
        } 
        
		return true;
	}
	



}
