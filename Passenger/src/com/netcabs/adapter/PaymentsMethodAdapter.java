package com.netcabs.adapter;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.netcabs.datamodel.PaymentsInfo;
import com.netcabs.passenger.R;

public class PaymentsMethodAdapter  extends ArrayAdapter<PaymentsInfo>
{
    private Activity context;
    ArrayList<PaymentsInfo> paymentList = null;
    LayoutInflater 	mInflater;

    public PaymentsMethodAdapter (Activity context, int resource, ArrayList<PaymentsInfo> data)
    {
        super(context, resource, data);
        this.context = context;
        this.paymentList = data;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) 
    {   // Ordinary view in Spinner, we use android.R.layout.simple_spinner_item
    	ViewHolder holder;
		mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

		if (convertView == null) {
			convertView = mInflater.inflate(R.layout.spinner_simple_row, null);
			holder = new ViewHolder();
			holder.txtViewParentName = (TextView) convertView.findViewById(R.id.txt_view_payment_method_name);
			convertView.setTag(holder);

		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		
		holder.txtViewParentName.setText(paymentList.get(position).getMethodName().toString());

		return convertView;
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent)
    {   // This view starts when we click the spinner.

		ViewHolder holder;
		mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

		if (convertView == null) {
			convertView = mInflater.inflate(R.layout.payment_method_row, null);
			holder = new ViewHolder();
			holder.txtViewPaymentsName = (TextView) convertView.findViewById(R.id.txt_view_payment_name);
			holder.imgViewLogo = (ImageView) convertView.findViewById(R.id.img_view_logo);
			convertView.setTag(holder);

		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		
		if(paymentList.get(position).getMethodName().toLowerCase().contains("auto")){
			holder.imgViewLogo.setBackgroundResource(R.drawable.auto_pay_icon);
		} else if(paymentList.get(position).getMethodName().toLowerCase().contains("card")) {
			holder.imgViewLogo.setBackgroundResource(R.drawable.card_pay_icon);
		} else if(paymentList.get(position).getMethodName().toLowerCase().contains("cash")) {
			holder.imgViewLogo.setBackgroundResource(R.drawable.cash_pay_icon);
		}
		
		holder.txtViewPaymentsName.setText("  "+paymentList.get(position).getMethodName().toString());

		return convertView;
	}

	static class ViewHolder {
		TextView txtViewPaymentsName;
		ImageView imgViewLogo;
		TextView txtViewParentName;
	}
	
}

