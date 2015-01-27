package com.netcabs.interfacecallback;

import java.util.ArrayList;

public interface OnComplete {
	public void onComplete(int result, ArrayList<String> data, ArrayList<String>refId);
	public void onAddressComplete(int result, String address);
	public void onLocationComplete(int result, double lat, double lon);
}
