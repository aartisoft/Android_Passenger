package com.netcabs.passengerinfo;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.provider.Settings;
import android.text.Html;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.fortysevendeg.swipelistview.SwipeListView;
import com.google.android.gms.maps.GoogleMap;
import com.netcabs.datamodel.Country;
import com.netcabs.datamodel.CreditCardInfo;
import com.netcabs.datamodel.DestinationInfo;
import com.netcabs.datamodel.FaceBookGplusLoginInfo;
import com.netcabs.datamodel.FavoriteInfo;
import com.netcabs.datamodel.FriendsInfo;
import com.netcabs.datamodel.LocationSearchInfo;
import com.netcabs.datamodel.MyBookingInfo;
import com.netcabs.datamodel.PastBookingDetailsInfo;
import com.netcabs.datamodel.PaymentsInfo;
import com.netcabs.datamodel.PickUpInfo;
import com.netcabs.datamodel.ProfileDetailsInfo;
import com.netcabs.datamodel.SeenUnSeenInfo;
import com.netcabs.datamodel.TaxiInfo;

public class PassengerApp {
	private String passengerId;
	private int isMobileVerified;
	private String baseUrl = "http://114.134.91.91:8000/action";
	public static PassengerApp instance;
	private ArrayList<CreditCardInfo> creditCardInfoList;
	private ArrayList<FavoriteInfo> favoriteInfoList;
	private ArrayList<MyBookingInfo> myBookingInfoList;
	private PastBookingDetailsInfo pastBookingDetailsInfo;
	private ArrayList<TaxiInfo> taxiInfoList;
	private ArrayList<FriendsInfo> friendsInfoList;
	private LocationSearchInfo searchInfo;
	private PickUpInfo pickUpLocationInfo;
	private DestinationInfo destinationInfo;
	private ArrayList<Country>countryArray;
	private ArrayList<PaymentsInfo> paymentsInfoList;
	private ProfileDetailsInfo profileDetailsInfo;
	private SeenUnSeenInfo seenUnSeenInfo;
	private String bookingId;
	private MyBookingInfo myBookingInfo;
	private SwipeListView lstViewCard;
	private FaceBookGplusLoginInfo fbGplusLoginInfo;
	private String paymentId;
	private String shareTripLink = "";
	private int logInType;  //1=google, 2=facebook, 3=email  
	
	private GoogleMap gmap;
	
	public ArrayList<CreditCardInfo> getCreditCardInfoList() {
		return creditCardInfoList;
	}

	public void setCreditCardInfoList(ArrayList<CreditCardInfo> creditCardInfoList) {
		this.creditCardInfoList = creditCardInfoList;
	}

	public ArrayList<FavoriteInfo> getFavoriteInfoList() {
		return favoriteInfoList;
	}

	public void setFavoriteInfoList(ArrayList<FavoriteInfo> favoriteInfoList) {
		this.favoriteInfoList = favoriteInfoList;
	}

	public ArrayList<MyBookingInfo> getMyBookingInfoList() {
		return myBookingInfoList;
	}

	public void setMyBookingInfoList(ArrayList<MyBookingInfo> myBookingInfoList) {
		this.myBookingInfoList = myBookingInfoList;
	}

	public ArrayList<TaxiInfo> getTaxiInfoList() {
		return taxiInfoList;
	}

	public void setTaxiInfoList(ArrayList<TaxiInfo> taxiInfoList) {
		this.taxiInfoList = taxiInfoList;
	}

	public void setBaseUrl(String baseUrl) {
		this.baseUrl = baseUrl;
	}

	public static void setInstance(PassengerApp instance) {
		PassengerApp.instance = instance;
	}

	public static PassengerApp getInstance() {
		if (instance == null) {
			instance = new PassengerApp();
		}
		return instance;
	}

	public void destroyInstance() {
		instance = null;
	}

	public void hideKeyboard(Context context, View v) {
		InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
		imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
	}

	public void openInternetSettingsActivity(final Context context) {
		final AlertDialog.Builder alert = new AlertDialog.Builder(context);
		alert.setTitle("Internet Problem");
		alert.setMessage("No internet connection. Please connect to a network first.");
		alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int whichButton) {
				context.startActivity(new Intent(Settings.ACTION_WIFI_SETTINGS));
			}
		});

		alert.show();
	}

	public void openErrorDialog(String err_msg, Context context) {
		err_msg = Html.fromHtml(err_msg).toString();
		final AlertDialog.Builder alert = new AlertDialog.Builder(context);
		alert.setMessage(err_msg);
		alert.setCancelable(true);

		alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int whichButton) {

			}
		});
		alert.show();
	}

	public String getBaseUrl() {
		return baseUrl;
	}

	public boolean isEmailValid(String email) {
		boolean isValid = false;

		String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
		CharSequence inputStr = email;

		Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
		Matcher matcher = pattern.matcher(inputStr);
		if (matcher.matches()) {
			isValid = true;
		}
		return isValid;
	}

	public LocationSearchInfo getSearchInfo() {
		return searchInfo;
	}

	public void setSearchInfo(LocationSearchInfo searchInfo) {
		this.searchInfo = searchInfo;
	}

	public PickUpInfo getPickUpLocationInfo() {
		return pickUpLocationInfo;
	}

	public void setPickUpLocationInfo(PickUpInfo pickUpLocationInfo) {
		this.pickUpLocationInfo = pickUpLocationInfo;
	}

	public DestinationInfo getDestinationInfo() {
		return destinationInfo;
	}

	public void setDestinationInfo(DestinationInfo destinationInfo) {
		this.destinationInfo = destinationInfo;
	}

	public ArrayList<FriendsInfo> getFriendsInfoList() {
		return friendsInfoList;
	}

	public void setFriendsInfoList(ArrayList<FriendsInfo> friendsInfoList) {
		this.friendsInfoList = friendsInfoList;
	}

	public ArrayList<Country> getCountryArray() {
		return countryArray;
	}

	public void setCountryArray(ArrayList<Country> countryArray) {
		this.countryArray = countryArray;
	}

	public GoogleMap getGmap() {
		return gmap;
	}

	public void setGmap(GoogleMap gmap) {
		this.gmap = gmap;
	}

	public ProfileDetailsInfo getProfileDetailsInfo() {
		return profileDetailsInfo;
	}

	public void setProfileDetailsInfo(ProfileDetailsInfo profileDetailsInfo) {
		this.profileDetailsInfo = profileDetailsInfo;
	}

	public SeenUnSeenInfo getSeenUnSeenInfo() {
		return seenUnSeenInfo;
	}

	public void setSeenUnSeenInfo(SeenUnSeenInfo seenUnSeenInfo) {
		this.seenUnSeenInfo = seenUnSeenInfo;
	}

	public String getBookingId() {
		return bookingId;
	}

	public void setBookingId(String bookingId) {
		this.bookingId = bookingId;
	}

	public ArrayList<PaymentsInfo> getPaymentsInfoList() {
		return paymentsInfoList;
	}

	public void setPaymentsInfoList(ArrayList<PaymentsInfo> paymentsInfoList) {
		this.paymentsInfoList = paymentsInfoList;
	}

	public PastBookingDetailsInfo getPastBookingDetailsInfo() {
		return pastBookingDetailsInfo;
	}

	public void setPastBookingDetailsInfo(PastBookingDetailsInfo pastBookingDetailsInfo) {
		this.pastBookingDetailsInfo = pastBookingDetailsInfo;
	}

	public MyBookingInfo getMyBookingInfo() {
		return myBookingInfo;
	}

	public void setMyBookingInfo(MyBookingInfo myBookingInfo) {
		this.myBookingInfo = myBookingInfo;
	}

	public SwipeListView getLstViewCard() {
		return lstViewCard;
	}

	public void setLstViewCard(SwipeListView lstViewCard) {
		this.lstViewCard = lstViewCard;
	}

	public FaceBookGplusLoginInfo getFbGplusLoginInfo() {
		return fbGplusLoginInfo;
	}

	public void setFbGplusLoginInfo(FaceBookGplusLoginInfo fbGplusLoginInfo) {
		this.fbGplusLoginInfo = fbGplusLoginInfo;
	}



	public String getPaymentId() {
		return paymentId;
	}

	public void setPaymentId(String paymentId) {
		this.paymentId = paymentId;
	}

	public int getIsMobileVerified() {
		return isMobileVerified;
	}

	public void setIsMobileVerified(int isMobileVerified) {
		this.isMobileVerified = isMobileVerified;
	}

	public String getPassengerId() {
		return passengerId;
	}

	public void setPassengerId(String passengerId) {
		this.passengerId = passengerId;
	}

	public String getShareTripLink() {
		return shareTripLink;
	}

	public void setShareTripLink(String shareTripLink) {
		this.shareTripLink = shareTripLink;
	}

	public int getLogInType() {
		return logInType;
	}

	public void setLogInType(int logInType) {
		this.logInType = logInType;
	}

}
