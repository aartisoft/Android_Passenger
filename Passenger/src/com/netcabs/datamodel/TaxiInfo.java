package com.netcabs.datamodel;

public class TaxiInfo {
	private String taxiId;
	private String taxiModel;
	private String status;
	private String taxiLogo;
	private String isAvailable;
	private String taxiNumber;
	private double taxiLat;
	private double taxiLon;
	private String taxiName;
	private String currentLocation;
	private String timeDurationToReach;

	public String getTaxiModel() {
		return taxiModel;
	}

	public void setTaxiModel(String taxiModel) {
		this.taxiModel = taxiModel;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getTaxiLogo() {
		return taxiLogo;
	}

	public void setTaxiLogo(String taxiLogo) {
		this.taxiLogo = taxiLogo;
	}

	public String getIsAvailable() {
		return isAvailable;
	}

	public void setIsAvailable(String isAvailable) {
		this.isAvailable = isAvailable;
	}

	public String getTaxiNumber() {
		return taxiNumber;
	}

	public void setTaxiNumber(String taxiNumber) {
		this.taxiNumber = taxiNumber;
	}

	public double getTaxiLat() {
		return taxiLat;
	}

	public void setTaxiLat(double taxiLat) {
		this.taxiLat = taxiLat;
	}

	public double getTaxiLon() {
		return taxiLon;
	}

	public void setTaxiLon(double taxiLon) {
		this.taxiLon = taxiLon;
	}

	public String getTaxiName() {
		return taxiName;
	}

	public void setTaxiName(String taxiName) {
		this.taxiName = taxiName;
	}

	public String getCurrentLocation() {
		return currentLocation;
	}

	public void setCurrentLocation(String currentLocation) {
		this.currentLocation = currentLocation;
	}

	public String getTaxiId() {
		return taxiId;
	}

	public void setTaxiId(String taxiId) {
		this.taxiId = taxiId;
	}

	public String getTimeDurationToReach() {
		return timeDurationToReach;
	}

	public void setTimeDurationToReach(String timeDurationToReach) {
		this.timeDurationToReach = timeDurationToReach;
	}

}
