package com.netcabs.utils;

import java.util.ArrayList;

import com.netcabs.datamodel.LocationSearchInfo;

public class ConstantValues {
	public static String LetsGo_SUPPORT_CONTACT = "123456789";
	public static int PASSWORD_LENGTH = 6;
	public static int TAXI_BOOKING_SEARCH_TIME = 3; // in MINUTE
	public static int COUNTDOWN_TIMER_BAR = 60; 
	public static String FUNC_ID_REGISTRATION = "1";
	public static String FUNC_ID_CARD_REGISTRATION = "2";
	public static String FUNC_ID_PHONE_VERIFY = "3";
	public static String FUNC_ID_SET_PIN = "4";
	public static String FUNC_ID_LOGIN = "5";
	public static String FUNC_ID_LOGIN_WITH_PIN = "6";
	public static String FUNC_ID_TAXI_INFO = "7";
	public static String FUNC_ID_BOOKING_CONFRIMATION = "8";
	public static String FUNC_ID_SENT_SEEN = "9";
	public static String FUNC_ID_FAVOURITE_DESTINATION = "10";
	public static String FUNC_ID_FAVOURITE_DESTINATION_LIST = "11";
	public static String FUNC_ID_BOOKING_LIST = "12";
	public static String FUNC_ID_CANCEL_BOOKING = "13";
	public static String FUNC_ID_SHARE_TRIP = "16";
	public static String FUNC_ID_CARD_LIST = "17";
	public static String FUNC_ID_MAKE_DEFAULT = "18";
	public static String FUNC_ID_UPDATE_PROFILE = "19";
	public static String FUNC_ID_LOST_PASSWORD = "20";
	public static String FUNC_ID_RESET_PASSWORD = "21";
	public static String FUNC_ID_RESEND_CODE = "22";
	public static String FUNC_ID_EMAIL_CHECK = "23";
	public static String FUNC_ID_COUNTRY_LIST = "24";
	public static String FUNC_ID_PAYMENT_METHOD_LIST = "25";
	public static String FUNC_ID_BOOKING_DETAILS = "26";
	public static String FUNC_ID_PROFILE_DETAILS = "27";
	public static String FUNC_ID_CARD_NUMBER_DELETE = "29";
	
	public static String FUNC_ID_PAST_BOOKING_DETAILS = "30";
	public static String FUNC_ID_CONTACT_EMAIL = "31";
	public static String FUNC_ID_HAILED_REGO_CHECK = "32";
	public static String FUNC_ID_HAILED_BOOKING = "33";
	public static String FUNC_ID_CARD_EDIT = "34";
	
	public static String BOOKING_VIA_APP = "1";
	public static String BOOKING_VIA_HAILEDCAB = "4";
	public static ArrayList<String> refId;
	public static LocationSearchInfo locationInfo = null;
	//public static boolean IS_FIRST_REGISTRATION = false; 
	
	public static String browserKey = "AIzaSyClsbD9wwIuk_6MtTHxlVP_Ym7GwO3JpJY";

	//MyBookingDetails VIEW_TYPE
	public static int ComingFromLIST = 0;
	public static int ComingFromBOOKING = 1;
	public static int ComingFromHailedCab = 2;
	
	public static boolean isComingFromHailedCabDetails = false;
	public static boolean isCommingFromConfirmBooking = false;
	public static boolean isCommingFromPast = false;
	public static boolean isBackground = false;
	public static boolean normalBack = false; 
	public static String COUNTRY_NAME = ""; 
	
	public static String INTERNET_CONNECTION_FAILURE_MSG = "Please turn on your internet";
	
		
}
