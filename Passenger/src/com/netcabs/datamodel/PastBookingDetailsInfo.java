package com.netcabs.datamodel;

import java.util.ArrayList;

public class PastBookingDetailsInfo {
	private String taxiId;
	private String taxiNumber;
	private String taxiModel;
	private String passengerName;
	private String passengerEmail;
	private String driverName;
	private String regNo;
	private String pickUpTime;
	private String totalTripTime;
	private String pickupAddress;
	private double pickUpLocationLatitude;
	private double pickUpLocationLongitude;

	private double destinationLocationLatitude;
	private double destinationLocationLongitude;

	private ArrayList<JourneyReportInfo> journeyCoordinateList;

	private String dropOfAddress;
	private String dropOfTime;
	private String distance;
	private String farePrice;
	private String extras;
	private String GST;
	private String totalAmount;
	private String paymentType;
	private String paymentDate;
	private String paymentTotal;

	public String getTaxiId() {
		return taxiId;
	}

	public void setTaxiId(String taxiId) {
		this.taxiId = taxiId;
	}

	public String getTaxiNumber() {
		return taxiNumber;
	}

	public void setTaxiNumber(String taxiNumber) {
		this.taxiNumber = taxiNumber;
	}

	public String getTaxiModel() {
		return taxiModel;
	}

	public void setTaxiModel(String taxiModel) {
		this.taxiModel = taxiModel;
	}

	public String getPassengerName() {
		return passengerName;
	}

	public void setPassengerName(String passengerName) {
		this.passengerName = passengerName;
	}

	public String getPassengerEmail() {
		return passengerEmail;
	}

	public void setPassengerEmail(String passengerEmail) {
		this.passengerEmail = passengerEmail;
	}

	public String getDriverName() {
		return driverName;
	}

	public void setDriverName(String driverName) {
		this.driverName = driverName;
	}

	public String getRegNo() {
		return regNo;
	}

	public void setRegNo(String regNo) {
		this.regNo = regNo;
	}

	public String getPickUpTime() {
		return pickUpTime;
	}

	public void setPickUpTime(String pickUpTime) {
		this.pickUpTime = pickUpTime;
	}

	public String getTotalTripTime() {
		return totalTripTime;
	}

	public void setTotalTripTime(String totalTripTime) {
		this.totalTripTime = totalTripTime;
	}

	public double getPickUpLocationLatitude() {
		return pickUpLocationLatitude;
	}

	public void setPickUpLocationLatitude(double pickUpLocationLatitude) {
		this.pickUpLocationLatitude = pickUpLocationLatitude;
	}

	public double getPickUpLocationLongitude() {
		return pickUpLocationLongitude;
	}

	public void setPickUpLocationLongitude(double pickUpLocationLongitude) {
		this.pickUpLocationLongitude = pickUpLocationLongitude;
	}

	public String getDropOfAddress() {
		return dropOfAddress;
	}

	public void setDropOfAddress(String dropOfLocation) {
		this.dropOfAddress = dropOfLocation;
	}

	public String getDropOfTime() {
		return dropOfTime;
	}

	public void setDropOfTime(String dropOfTime) {
		this.dropOfTime = dropOfTime;
	}

	public String getDistance() {
		return distance;
	}

	public void setDistance(String distance) {
		this.distance = distance;
	}

	public String getFarePrice() {
		return farePrice;
	}

	public void setFarePrice(String farePrice) {
		this.farePrice = farePrice;
	}

	public String getExtras() {
		return extras;
	}

	public void setExtras(String extras) {
		this.extras = extras;
	}

	public String getGST() {
		return GST;
	}

	public void setGST(String gST) {
		GST = gST;
	}

	public String getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(String totalAmount) {
		this.totalAmount = totalAmount;
	}

	public String getPaymentType() {
		return paymentType;
	}

	public void setPaymentType(String paymentType) {
		this.paymentType = paymentType;
	}

	public String getPaymentDate() {
		return paymentDate;
	}

	public void setPaymentDate(String paymentDate) {
		this.paymentDate = paymentDate;
	}

	public String getPaymentTotal() {
		return paymentTotal;
	}

	public void setPaymentTotal(String paymentTotal) {
		this.paymentTotal = paymentTotal;
	}

	public String getPickupAddress() {
		return pickupAddress;
	}

	public void setPickupAddress(String pickupAddress) {
		this.pickupAddress = pickupAddress;
	}

	public double getDestinationLocationLatitude() {
		return destinationLocationLatitude;
	}

	public void setDestinationLocationLatitude(
			double destinationLocationLatitude) {
		this.destinationLocationLatitude = destinationLocationLatitude;
	}

	public double getDestinationLocationLongitude() {
		return destinationLocationLongitude;
	}

	public void setDestinationLocationLongitude(
			double destinationLocationLongitude) {
		this.destinationLocationLongitude = destinationLocationLongitude;
	}

	public ArrayList<JourneyReportInfo> getJourneyCoordinateList() {
		return journeyCoordinateList;
	}

	public void setJourneyCoordinateList(ArrayList<JourneyReportInfo> journeyCoordinateList) {
		this.journeyCoordinateList = journeyCoordinateList;
	}

}
