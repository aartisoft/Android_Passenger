package com.netcabs.adapter;

import java.util.ArrayList;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.netcabs.asynctask.DeleteCardNoAsyncTask;
import com.netcabs.asynctask.MakeDefultAsyncTask;
import com.netcabs.customview.DialogController;
import com.netcabs.datamodel.CreditCardInfo;
import com.netcabs.fragments.PaymentFragment;
import com.netcabs.interfacecallback.OnRequestComplete;
import com.netcabs.internetconnection.InternetConnectivity;
import com.netcabs.passenger.R;
import com.netcabs.passengerinfo.PassengerApp;
import com.netcabs.utils.ConstantValues;

public class PaymentCardAdapter extends BaseAdapter {

	private LayoutInflater mInflater;
	private Activity context;
	private Dialog dialogConfirmCancel;
	private ArrayList<CreditCardInfo> creditCardList;

	public PaymentCardAdapter(Activity context, ArrayList<CreditCardInfo> creditCardList) {
		this.context = context;
		this.creditCardList = creditCardList;
	}

	@Override
	public int getCount() {
		if(creditCardList != null) {
			return creditCardList.size();
		} else {
			return 0;
		}
	}

	@Override
	public Object getItem(int position) {
		return creditCardList.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		final ViewHolder holder;
		
		mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

		if (convertView == null) {
			convertView = mInflater.inflate(R.layout.payment_card_row, null);
			holder = new ViewHolder();
			holder.txtViewCardNumber = (TextView) convertView.findViewById(R.id.txt_view_card_number);
			holder.imgViewCardLogo = (ImageView) convertView.findViewById(R.id.img_view_card_logo);
			holder.imgViewCardSelect = (ImageView) convertView.findViewById(R.id.img_view_select);
			holder.btnDelete = (Button) convertView.findViewById(R.id.btn_delete);
			holder.imgViewCardSelect.setTag(position);
			holder.btnDelete.setTag(position);
			convertView.setTag(holder);

		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		
		holder.txtViewCardNumber.setText("**** **** **** " + creditCardList.get(position).getCardNumber().toString().subSequence(creditCardList.get(position).getCardNumber().toString().trim().length() - 4, creditCardList.get(position).getCardNumber().toString().trim().length()));
		
		
		if (creditCardList.get(position).getCareType().equalsIgnoreCase("visa")) {
			holder.imgViewCardLogo.setBackgroundResource(R.drawable.visa);

		} else if (creditCardList.get(position).getCareType().equalsIgnoreCase("master")) {
			holder.imgViewCardLogo.setBackgroundResource(R.drawable.master);

		} else if (creditCardList.get(position).getCareType().equalsIgnoreCase("americanexpress")) {
			holder.imgViewCardLogo.setBackgroundResource(R.drawable.american);

		} else if (creditCardList.get(position).getCareType().equalsIgnoreCase("discover")) {
			holder.imgViewCardLogo.setBackgroundResource(R.drawable.discover);
		}
		
		if (creditCardList.get(position).getIsDefault() == 1) {
			holder.imgViewCardSelect.setBackgroundResource(R.drawable.default_icon);
			
		} else {
			holder.imgViewCardSelect.setBackgroundResource(R.drawable.selete_icon);
			
		}
		
		holder.imgViewCardSelect.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				final int index = ((Integer) v.getTag());
				Log.i("Default Position","" + position);
				if(InternetConnectivity.isConnectedToInternet(context)) {
					if (creditCardList.get(position).getIsDefault() == 1) {
						
					} else {
						new MakeDefultAsyncTask(context, new OnRequestComplete() {
							
							@Override
							public void onRequestComplete(String result) {
								if("2001".equals(result)) {
									PassengerApp.getInstance().getCreditCardInfoList().get(position).setIsDefault(1);
									for(int i = 0; i < PassengerApp.getInstance().getCreditCardInfoList().size(); i++) {
										if(i == position) {
											
										} else {
											PassengerApp.getInstance().getCreditCardInfoList().get(i).setIsDefault(0);
										}
									}
									holder.imgViewCardSelect.setBackgroundResource(R.drawable.default_icon);
									Toast.makeText(context, "Card is set to default!", Toast.LENGTH_SHORT).show();
									PaymentCardAdapter.this.notifyDataSetChanged();
									
								} else if("3001".equals(result)) {
									
								} else if("4001".equals(result)) {
													
								} else {
									
								}
							}
						}).execute(ConstantValues.FUNC_ID_MAKE_DEFAULT, PassengerApp.getInstance().getPassengerId(), creditCardList.get(position).getPaymentId(), "1");
					}
				}
				 else {
					 Toast.makeText(context, ConstantValues.INTERNET_CONNECTION_FAILURE_MSG, Toast.LENGTH_SHORT).show();
				}
			}
		});
		
		if (creditCardList.get(position).getIsDefault() == 1) {
			holder.btnDelete.setVisibility(View.INVISIBLE);
			
		} else {
			holder.btnDelete.setVisibility(View.VISIBLE);
			
		}
		
		//holder.btnDelete.setVisibility(View.VISIBLE);
		
		holder.btnDelete.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				final int index = ((Integer) v.getTag());
				Log.i("Delete Position","" + position);
				showConfirmPopup(creditCardList.get(position).getCardNumber().toString(), position);
			}
		});

		return convertView;
	}

	static class ViewHolder {
		TextView txtViewCardNumber;
		ImageView imgViewCardLogo;
		ImageView imgViewCardSelect;
		Button btnDelete;
	}
	
	private void showConfirmPopup(final String cardNumber, final int deletePosition) {
		dialogConfirmCancel = new DialogController(context).ConfirmBookingDialog();
		
		TextView txtViewMsg = (TextView) dialogConfirmCancel.findViewById(R.id.txt_view_booking_message);
		txtViewMsg.setText("Are you Sure ?");
		
		Button btnYes = (Button) dialogConfirmCancel.findViewById(R.id.btn_yes);
		
		btnYes.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if(InternetConnectivity.isConnectedToInternet(context)) {	
					new DeleteCardNoAsyncTask(context, new OnRequestComplete() {
						
						@Override
						public void onRequestComplete(String result) {
							if ("2001".equals(result)) {
								//creditCardList.remove(deletePosition);
								PassengerApp.getInstance().getCreditCardInfoList().remove(deletePosition);
								PassengerApp.getInstance().getLstViewCard().closeOpenedItems();
								PaymentCardAdapter.this.notifyDataSetChanged();
								dialogConfirmCancel.dismiss();
								Toast.makeText(context, "Delete successfully completed ", Toast.LENGTH_SHORT).show();
							} else {
								Toast.makeText(context, "Delete not successfully completed", Toast.LENGTH_SHORT).show();
							}
						}
					}).execute(ConstantValues.FUNC_ID_CARD_NUMBER_DELETE, PassengerApp.getInstance().getPassengerId(), cardNumber);
					
				} else {
					Toast.makeText(context, ConstantValues.INTERNET_CONNECTION_FAILURE_MSG, Toast.LENGTH_SHORT).show();
				}
			}
		});
		
		Button btnNo = (Button) dialogConfirmCancel.findViewById(R.id.btn_no);
		btnNo.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				dialogConfirmCancel.dismiss();
			}
		});
		
		Button btnCancel = (Button) dialogConfirmCancel.findViewById(R.id.btn_cancel);
		btnCancel.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				dialogConfirmCancel.dismiss();
			}
		});
		
		dialogConfirmCancel.show();
	}

}
