package com.netcabs.customview;

import com.netcabs.passenger.R;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.util.Log;
import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputConnection;
import android.view.inputmethod.InputConnectionWrapper;
import android.widget.EditText;

public class CustomEditText extends EditText {
    /* Must use this constructor in order for the layout files to instantiate the class properly */
    public CustomEditText(Context context, AttributeSet attrs) 
    {
        super(context, attrs);
        // TODO Auto-generated constructor stub
    }

    @Override
    public boolean onKeyPreIme (int keyCode, KeyEvent event)
    {
        // Return true if I handle the event:
            // In my case i want the keyboard to not be dismissible so i simply return true
            // Other people might want to handle the event differently
        Log.e("onKeyPreIme from custom edittext ", ""+event);
        return true;
    }
    
	public void init(AttributeSet attrs) {
		if (attrs != null) {
			TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.NetCabsTextView);
			String fontName = a.getString(R.styleable.NetCabsTextView_fontName);
			Typeface myTypeface = Typeface.createFromAsset(getContext() .getAssets(), "fonts/" + fontName);

			if (fontName != null) {
				setTypeface(myTypeface);
			}

			a.recycle();
		}
	}
	
	   @Override
	    public InputConnection onCreateInputConnection(EditorInfo outAttrs) {
	        return new InputConnection(super.onCreateInputConnection(outAttrs), true);
	    }

	    private class InputConnection extends InputConnectionWrapper {

	        public InputConnection(android.view.inputmethod.InputConnection inputConnection, boolean mutable) {
	            super(inputConnection, mutable);
	        }

	        @Override
	        public boolean sendKeyEvent(KeyEvent event) {
	            if (event.getAction() == KeyEvent.ACTION_DOWN  && event.getKeyCode() == KeyEvent.KEYCODE_DEL) {
	             //  CustomEditText.this.setRandomBackgroundColor();
	                // Un-comment if you wish to cancel the backspace:
	                // return false;
	            	 Log.e("sendKeyEvent from custom edittext ", ""+event);
	            }
	            return super.sendKeyEvent(event);
	        }
	        
	        @Override
	        public boolean deleteSurroundingText(int beforeLength, int afterLength) {       
	            // magic: in latest Android, deleteSurroundingText(1, 0) will be called for backspace
	            if (beforeLength == 1 && afterLength == 0) {
	                // backspace
	            	 Log.e("deleteSurroundingText from custom edittext ", "-----");
	                return sendKeyEvent(new KeyEvent(KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_DEL))
	                    && sendKeyEvent(new KeyEvent(KeyEvent.ACTION_UP, KeyEvent.KEYCODE_DEL));
	            }

	            return super.deleteSurroundingText(beforeLength, afterLength);
	        }

	    }
	    
	    
	

}