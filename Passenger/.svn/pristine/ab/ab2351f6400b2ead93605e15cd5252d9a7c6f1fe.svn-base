package com.netcabs.adapter;
import java.util.ArrayList;

import com.netcabs.interfacecallback.OnComplete;
import com.netcabs.passenger.R;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
 
public class AutoCompleteArrayCustomAdapter extends ArrayAdapter<String> {
 
    final String TAG = "AutocompleteCustomArrayAdapter.java";
         
    Context mContext;
    int layoutResourceId;
    ArrayList<String> data = null;
    private OnComplete onComplete;
 
    public AutoCompleteArrayCustomAdapter(Context mContext, OnComplete onComplete, int layoutResourceId, ArrayList<String> data) {
 
        super(mContext, layoutResourceId, data);
         
        this.layoutResourceId = layoutResourceId;
        this.mContext = mContext;
        this.data = data;
        this.onComplete = onComplete;
    }
 
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
         
        try{
            
            if(convertView==null){
                // inflate the layout
                LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = inflater.inflate(layoutResourceId, parent, false);
            }
             
            TextView textViewItem = (TextView) convertView.findViewById(R.id.txt_view_suggestion);
            textViewItem.setText(data.get(position));
            // in case you want to add some style, you can do something like:
            textViewItem.setBackgroundColor(Color.WHITE);
             
        } catch (NullPointerException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
         
        return convertView;
         
    }
}