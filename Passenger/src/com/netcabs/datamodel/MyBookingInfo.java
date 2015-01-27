package com.netcabs.datamodel;

public class MyBookingInfo {
	private String bookingId;
	private String time;
	private String date;
	private String distance;
	private int isParcel;
	private double price;
	private String pickupName;
	private String destinationName;
	private double pickupLat;
	private double pickupLon;
	private double destinationLat;
	private double destinationLon;
	private double taxiCurrentLat;
	private double taxiCurrentLon;
	
	private int passengerNumber;
	private String durationTime;
	
	private String taxiRegNo;
	private String taxi_name;
	private String taxi_id;
	private String taxi_model;
	private String taxi_number;
	private String taxiLogoUrl;
	
	private String driverMobileNo;
	private int bookingStatus;
	private int acceptStatus;

	public String getPickupName() {
		return pickupName;
	}

	public void setPickupName(String pickupName) {
		this.pickupName = pickupName;
	}

	public String getDestinationName() {
		return destinationName;
	}

	public void setDestinationName(String destinationName) {
		this.destinationName = destinationName;
	}

	public double getPickupLat() {
		return pickupLat;
	}

	public void setPickupLat(double pickupLat) {
		this.pickupLat = pickupLat;
	}

	public double getPickupLon() {
		return pickupLon;
	}

	public void setPickupLon(double pickupLon) {
		this.pickupLon = pickupLon;
	}

	public double getDestinationLat() {
		return destinationLat;
	}

	public void setDestinationLat(double destinationLat) {
		this.destinationLat = destinationLat;
	}

	public double getDestinationLon() {
		return destinationLon;
	}

	public void setDestinationLon(double destinationLon) {
		this.destinationLon = destinationLon;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getDistance() {
		return distance;
	}

	public void setDistance(String distance) {
		this.distance = distance;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public int getIsParcel() {
		return isParcel;
	}

	public void setIsParcel(int isParcel) {
		this.isParcel = isParcel;
	}

	public int getPassengerNumber() {
		return passengerNumber;
	}

	public void setPassengerNumber(int passengerNumber) {
		this.passengerNumber = passengerNumber;
	}

	public String getBookingId() {
		return bookingId;
	}

	public void setBookingId(String bookingId) {
		this.bookingId = bookingId;
	}

	public String getDurationTime() {
		return durationTime;
	}

	public void setDurationTime(String durationTime) {
		this.durationTime = durationTime;
	}

	public String getTaxiRegNo() {
		return taxiRegNo;
	}

	public void setTaxiRegNo(String taxiRegNo) {
		this.taxiRegNo = taxiRegNo;
	}

	public String getTaxiLogoUrl() {
		return taxiLogoUrl;
	}

	public void setTaxiLogoUrl(String taxiLogoUrl) {
		this.taxiLogoUrl = taxiLogoUrl;
	}

	public String getTaxi_name() {
		return taxi_name;
	}

	public void setTaxi_name(String taxi_name) {
		this.taxi_name = taxi_name;
	}

	public String getTaxi_id() {
		return taxi_id;
	}

	public void setTaxi_id(String taxi_id) {
		this.taxi_id = taxi_id;
	}

	public String getTaxi_number() {
		return taxi_number;
	}

	public void setTaxi_number(String taxi_number) {
		this.taxi_number = taxi_number;
	}

	public String getTaxi_model() {
		return taxi_model;
	}

	public void setTaxi_model(String taxi_model) {
		this.taxi_model = taxi_model;
	}

	public double getTaxiCurrentLat() {
		return taxiCurrentLat;
	}

	public void setTaxiCurrentLat(double taxiCurrentLat) {
		this.taxiCurrentLat = taxiCurrentLat;
	}

	public double getTaxiCurrentLon() {
		return taxiCurrentLon;
	}

	public void setTaxiCurrentLon(double taxiCurrentLon) {
		this.taxiCurrentLon = taxiCurrentLon;
	}

	public String getDriverMobileNo() {
		return driverMobileNo;
	}

	public void setDriverMobileNo(String driverMobileNo) {
		this.driverMobileNo = driverMobileNo;
	}

	public int getBookingStatus() {
		return bookingStatus;
	}

	public void setBookingStatus(int bookingStatus) {
		this.bookingStatus = bookingStatus;
	}

	public int getAcceptStatus() {
		return acceptStatus;
	}

	public void setAcceptStatus(int acceptStatus) {
		this.acceptStatus = acceptStatus;
	}


}
