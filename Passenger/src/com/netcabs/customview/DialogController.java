package com.netcabs.customview;

import android.app.ActionBar.LayoutParams;
import android.app.Activity;
import android.app.Dialog;
import android.graphics.drawable.ColorDrawable;
import android.view.Window;

import com.netcabs.passenger.R;

public class DialogController {
	private Dialog dialogLostPassword;
	private Dialog dialogLostPin;
	private Dialog dialogConfirmBooking;
	private Dialog dialogShareWithFriend;
	private Dialog dialogResetPassword;
	private Dialog resetBookingSearch;
	private Activity activity;
	
	public DialogController(Activity activity) {
		this.activity = activity;
	}
	
	public Dialog LostPasswordDialog(){
		dialogLostPassword = new Dialog(this.activity);
		dialogLostPassword.requestWindowFeature(Window.FEATURE_NO_TITLE);
		dialogLostPassword.setContentView(R.layout.dialog_lost_password);
		dialogLostPassword.setCanceledOnTouchOutside(true);
		dialogLostPassword.setCancelable(true);
		dialogLostPassword.getWindow().setLayout(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
		dialogLostPassword.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
		return dialogLostPassword;
	}
	
	public Dialog LostPinDialog(){
		dialogLostPin = new Dialog(this.activity);
		dialogLostPin.requestWindowFeature(Window.FEATURE_NO_TITLE);
		dialogLostPin.setContentView(R.layout.dialog_lost_pin);
		dialogLostPin.setCanceledOnTouchOutside(true);
		dialogLostPin.setCancelable(true);
		dialogLostPin.getWindow().setLayout(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
		dialogLostPin.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
		return dialogLostPin;
	}
	
	public Dialog ConfirmBookingDialog(){
		dialogConfirmBooking = new Dialog(this.activity);
		dialogConfirmBooking.requestWindowFeature(Window.FEATURE_NO_TITLE);
		dialogConfirmBooking.setContentView(R.layout.dialog_confirm_booking);
		dialogConfirmBooking.setCanceledOnTouchOutside(true);
		dialogConfirmBooking.setCancelable(true);
		dialogConfirmBooking.getWindow().setLayout(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
		dialogConfirmBooking.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
		return dialogConfirmBooking;
	}
	
	public Dialog ConfirmAcceptDialog(){
		dialogConfirmBooking = new Dialog(this.activity);
		dialogConfirmBooking.requestWindowFeature(Window.FEATURE_NO_TITLE);
		dialogConfirmBooking.setContentView(R.layout.dialog_driver_accepted);
		dialogConfirmBooking.setCanceledOnTouchOutside(true);
		dialogConfirmBooking.setCancelable(true);
		dialogConfirmBooking.getWindow().setLayout(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
		dialogConfirmBooking.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
		return dialogConfirmBooking;
	}
	
	public Dialog ShareWithFriendDialog(){
		dialogShareWithFriend = new Dialog(this.activity);
		dialogShareWithFriend.requestWindowFeature(Window.FEATURE_NO_TITLE);
		dialogShareWithFriend.setContentView(R.layout.dialog_share_with_friend);
		dialogShareWithFriend.setCanceledOnTouchOutside(true);
		dialogShareWithFriend.setCancelable(true);
		dialogShareWithFriend.getWindow().setLayout(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
		dialogShareWithFriend.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
		return dialogShareWithFriend;
	}
	
	public Dialog ResetPasswordDialog(){
		dialogResetPassword = new Dialog(this.activity);
		dialogResetPassword.requestWindowFeature(Window.FEATURE_NO_TITLE);
		dialogResetPassword.setContentView(R.layout.dialog_reset_password);
		dialogResetPassword.setCanceledOnTouchOutside(true);
		dialogResetPassword.setCancelable(true);
		dialogResetPassword.getWindow().setLayout(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
		dialogResetPassword.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
		return dialogResetPassword;
	}
	
	public Dialog ResetBookingSearchDialog(){
		resetBookingSearch = new Dialog(this.activity);
		resetBookingSearch.requestWindowFeature(Window.FEATURE_NO_TITLE);
		resetBookingSearch.setContentView(R.layout.dialog_reset_booking_search);
		resetBookingSearch.setCanceledOnTouchOutside(false);
		resetBookingSearch.setCancelable(false);
		resetBookingSearch.getWindow().setLayout(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
		resetBookingSearch.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
		return resetBookingSearch;
	}
	
	public Dialog DialogDatePicker(){
		resetBookingSearch = new Dialog(this.activity);
		resetBookingSearch.requestWindowFeature(Window.FEATURE_NO_TITLE);
		resetBookingSearch.setContentView(R.layout.dialog_date_picker);
		resetBookingSearch.setCanceledOnTouchOutside(true);
		resetBookingSearch.setCancelable(true);
		resetBookingSearch.getWindow().setLayout(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
		resetBookingSearch.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
		return resetBookingSearch;
	}
	
}
