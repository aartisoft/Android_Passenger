 package com.netcabs.passenger;

import java.io.File;
import java.io.FileNotFoundException;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.Settings.Secure;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.androidquery.AQuery;
import com.androidquery.callback.BitmapAjaxCallback;
import com.netcabs.adapter.CountrySpinnerAdapter;
import com.netcabs.asynctask.EmailCheckAsyncTask;
import com.netcabs.asynctask.RegistrationAsyncTask;
import com.netcabs.database.PreferenceUtil;
import com.netcabs.interfacecallback.OnRequestComplete;
import com.netcabs.internetconnection.InternetConnectivity;
import com.netcabs.passengerinfo.PassengerApp;
import com.netcabs.utils.BaseActivity;
import com.netcabs.utils.ConstantValues;
import com.netcabs.utils.Utils;

public class RegistationActivity extends Activity implements OnClickListener, OnFocusChangeListener, OnItemSelectedListener {

	private Button btnSave;
	private Button btnBack;
	private EditText edTxtFirstName;
	private EditText edTxtLastName;
	private EditText edTxtEmailAddress;
	private EditText edTxtContactNumber;
	private TextView txtViewCountry;
	private EditText edTxtZipCode;
	private EditText edTxtPassword;
	private EditText edTxtConfirmPassword;
	private ImageView imgViewProfilePic;
	private Spinner spinnerCountry;
	private Uri mImageCaptureUri;
	private LinearLayout linearCountry;
	private static final int PICK_FROM_CAMERA = 1;
	private static final int PICK_FROM_FILE = 2;
	private String selectedImagePath;
	private String reSizedBitmapPath= "";
	private int loggedinType = 0; 
	private String callingCode = "";
	private ImageView imgViewFlag;
	private AQuery lazyImageLoader;
	private PreferenceUtil preferenceUtil;
	private int callingCodeIndex = 0;
	private boolean isEmailId = false;
	private RelativeLayout relativeLayout;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_registation);
		BaseActivity.authLock = true;
		lazyImageLoader= new AQuery(this);
		initViews();
		setListener();
		loadData();
	}

	private void initViews() {
		btnSave = (Button) findViewById(R.id.btn_save);
		btnBack = (Button) findViewById(R.id.btn_back);
		relativeLayout = (RelativeLayout) findViewById(R.id.relative_main);
		edTxtFirstName = (EditText) findViewById(R.id.ed_txt_first_name);
		edTxtLastName = (EditText) findViewById(R.id.ed_txt_last_name);
		edTxtEmailAddress = (EditText) findViewById(R.id.ed_txt_email_address);
		edTxtPassword = (EditText) findViewById(R.id.ed_txt_password);
		edTxtConfirmPassword = (EditText) findViewById(R.id.ed_txt_confirm_password);
		txtViewCountry = (TextView) findViewById(R.id.txt_view_country);
		edTxtZipCode = (EditText) findViewById(R.id.ed_txt_zip_code);
		edTxtContactNumber = (EditText) findViewById(R.id.ed_txt_contact_number);
		spinnerCountry = (Spinner) findViewById(R.id.spinner_country);
		imgViewProfilePic = (ImageView) findViewById(R.id.img_view_user_pic);
		linearCountry = (LinearLayout)findViewById(R.id.linear_layout_country);	
		preferenceUtil = new PreferenceUtil(RegistationActivity.this);
		imgViewFlag = (ImageView)findViewById(R.id.img_view_flag);
		
		int defualtPosition = 0;
		if(PassengerApp.getInstance().getCountryArray()!= null) {
			for (int i = 0; i < PassengerApp.getInstance().getCountryArray().size(); i++) {
				if (PassengerApp.getInstance().getCountryArray().get(i).getName().equalsIgnoreCase(ConstantValues.COUNTRY_NAME)) {
					defualtPosition = i;
				}
				
			}
			CountrySpinnerAdapter countryAdapter = new CountrySpinnerAdapter(RegistationActivity.this, PassengerApp.getInstance().getCountryArray());
			spinnerCountry.setAdapter(countryAdapter);
			spinnerCountry.setSelection(defualtPosition);
		}
		
		
	}
	
	private void setInput() {
		edTxtFirstName.setText(PassengerApp.getInstance().getFbGplusLoginInfo().getFbGplusFirstName());
		edTxtFirstName.setSelection(edTxtFirstName.getText().toString().trim().length());
		edTxtLastName.setText(PassengerApp.getInstance().getFbGplusLoginInfo().getFbGplusdLastName());
		edTxtEmailAddress.setText(PassengerApp.getInstance().getFbGplusLoginInfo().getFbGplusEmail());
		edTxtContactNumber.setText("");
		edTxtZipCode.setText("");
		lazyImageLoader.id(imgViewProfilePic).image(PassengerApp.getInstance().getFbGplusLoginInfo().getFbGplusProfilePic(), true, true,0, R.drawable.default_icon);
	}

	private void setListener() {
		relativeLayout.setOnClickListener(this);
		btnSave.setOnClickListener(this);
		btnBack.setOnClickListener(this);
		imgViewProfilePic.setOnClickListener(this);
		edTxtEmailAddress.setOnFocusChangeListener(this);
		linearCountry.setOnClickListener(this);
		spinnerCountry.setOnItemSelectedListener(this);
		
		edTxtContactNumber.addTextChangedListener(new TextWatcher() {
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				
			}
			
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {
			}
			
			@Override
			public void afterTextChanged(Editable s) {
				if(edTxtContactNumber.getText().length() == callingCode.length() + 1) {
					edTxtContactNumber.setText(edTxtContactNumber.getText() + " ");
					edTxtContactNumber.setSelection(edTxtContactNumber.getText().toString().length());
				}
			}
		});
	}

	private void loadData() {
		if(getIntent().getExtras() != null) {
			loggedinType = getIntent().getExtras().getInt("loggedin_type");
		}
		setLoggedinImage(loggedinType);
	}

	private void setLoggedinImage(int loggedinType2) {
		switch (loggedinType2) {
		case 1:
			((Button) findViewById(R.id.btn_login_logo)).setBackgroundResource(R.drawable.logged_google);
			edTxtPassword.setVisibility(View.GONE);
			edTxtEmailAddress.setFocusable(false);
			edTxtEmailAddress.setClickable(false);
			edTxtConfirmPassword.setVisibility(View.GONE);
			setInput();
			break;
			
		case 2:
			((Button) findViewById(R.id.btn_login_logo)).setBackgroundResource(R.drawable.logged_fb);
			edTxtEmailAddress.setFocusable(false);
			edTxtEmailAddress.setClickable(false);
			edTxtPassword.setVisibility(View.GONE);
			edTxtConfirmPassword.setVisibility(View.GONE);
			setInput();
			break;
			
		case 3:
			edTxtEmailAddress.setFocusable(true);
			edTxtEmailAddress.setClickable(true);
			((Button) findViewById(R.id.btn_login_logo)).setVisibility(View.GONE);
			break;

		default:
			break;
		}
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.relative_main:
			PassengerApp.getInstance().hideKeyboard(RegistationActivity.this, v);
			break;
			
		case R.id.btn_back:
			onBackPressed();
			break;
			
		case R.id.btn_save:
			if(InternetConnectivity.isConnectedToInternet(RegistationActivity.this)) {
				processRegistration(loggedinType);
			} else {
				Toast.makeText(RegistationActivity.this, ConstantValues.INTERNET_CONNECTION_FAILURE_MSG, Toast.LENGTH_SHORT).show();
			}
			break;
			
		case R.id.img_view_user_pic:
			startDialog() ;
			break;
			
		case R.id.linear_layout_country:
			PassengerApp.getInstance().hideKeyboard(RegistationActivity.this, v);
			spinnerCountry.setSelection(callingCodeIndex);
			spinnerCountry.performClick();
			break;
			
		default:
			break;
		}
	}

	private void processRegistration(int loggedinType) {
		switch (loggedinType) {
		case 1:
			registrationByFacebookOrGooglePlus();
			break;
			
		case 2:
			registrationByFacebookOrGooglePlus();
			break;
			
		case 3:
			if(isEmailId && !edTxtFirstName.getText().toString().trim().equalsIgnoreCase("") && !edTxtLastName.getText().toString().trim().equalsIgnoreCase("") && PassengerApp.getInstance().isEmailValid(edTxtEmailAddress.getText().toString().trim()) && !edTxtPassword.getText().toString().trim().equalsIgnoreCase("") && !edTxtConfirmPassword.getText().toString().trim().equalsIgnoreCase("") && edTxtPassword.getText().toString().trim().equals(edTxtConfirmPassword.getText().toString().trim()) && edTxtPassword.getText().length() >= ConstantValues.PASSWORD_LENGTH && !edTxtZipCode.getText().toString().trim().equalsIgnoreCase("") && edTxtContactNumber.getText().toString().trim().length() > (PassengerApp.getInstance().getCountryArray().get(callingCodeIndex).getCallingId().length() + 2) && edTxtContactNumber.getText().toString().trim().length() < 16) {
				new RegistrationAsyncTask(RegistationActivity.this, new OnRequestComplete() {
					
					@Override
					public void onRequestComplete(String result) {
						try {
							if("2001".equals(result)) {
								preferenceUtil.setUserID(PassengerApp.getInstance().getPassengerId());
								preferenceUtil.setLOGIN_TYPES("3");
								finish();
								startActivity(new Intent(RegistationActivity.this, CardRegistrationActivity.class));
							} else if ("4001".equals(result)) {
								Toast.makeText(RegistationActivity.this, "Duplicate Email ID !", Toast.LENGTH_SHORT).show();
								
							}else if("5001".equals(result)) { 
								Log.e("Response is:","------"+5001);
								Toast.makeText(RegistationActivity.this, "Mobile Number is Invalid!", Toast.LENGTH_SHORT).show();
							}else{
								Toast.makeText(RegistationActivity.this, "Entry is not correct, please try again!", Toast.LENGTH_SHORT).show();
							}
						} catch (Exception e) {
							Toast.makeText(RegistationActivity.this, "Response error: (" + e.getMessage() + "). Please try again" , Toast.LENGTH_SHORT).show();
						}
						
					}
				}).execute(ConstantValues.FUNC_ID_REGISTRATION, "2", getDeviceToken(), "", "", edTxtFirstName.getText().toString().trim(), edTxtLastName.getText().toString().trim(), "3", "", edTxtPassword.getText().toString().trim(), PassengerApp.getInstance().getCountryArray().get(callingCodeIndex).getId(), edTxtEmailAddress.getText().toString().trim(), edTxtZipCode.getText().toString().trim(), edTxtContactNumber.getText().toString().trim().replace(" ", ""), reSizedBitmapPath,"", "1");
			
			} else {
				if(edTxtFirstName.getText().toString().trim().equalsIgnoreCase("")) {
					edTxtFirstName.setError("Required");
				} 
				if(edTxtLastName.getText().toString().trim().equalsIgnoreCase("")) {
					edTxtLastName.setError("Required");
				}
				
				if(!PassengerApp.getInstance().isEmailValid(edTxtEmailAddress.getText().toString().trim())) {
					edTxtEmailAddress.setError("Required");
				}
				
				if(edTxtPassword.getText().toString().trim().equalsIgnoreCase("")) {
					edTxtPassword.setError("Required");
				} else {
					if(edTxtPassword.getText().toString().trim().length() < ConstantValues.PASSWORD_LENGTH) {
						edTxtPassword.setError("Length must be "+ConstantValues.PASSWORD_LENGTH);
					}
				}
				
				if(!edTxtPassword.getText().toString().trim().equals(edTxtConfirmPassword.getText().toString().trim())) {
						edTxtPassword.setError("Password not match");
						edTxtConfirmPassword.setError("Password not match");
				}
				
				if(edTxtConfirmPassword.getText().toString().trim().equalsIgnoreCase("")) {
					edTxtConfirmPassword.setError("Required");
				}
				
				if(edTxtZipCode.getText().toString().trim().equalsIgnoreCase("")) {
					edTxtZipCode.setError("Required");
				}
				
				if(edTxtContactNumber.getText().toString().trim().length() <= (PassengerApp.getInstance().getCountryArray().get(callingCodeIndex).getCallingId().length() + 2)) {
					edTxtContactNumber.setError("Required");
				} else if (edTxtContactNumber.getText().toString().trim().length() > 15) {
					edTxtContactNumber.setError("Exceed maximum length ");
				}
			}
			break;
			

		default:
			break;
		}
		
	}
	
	public void registrationByFacebookOrGooglePlus() {
		if(!edTxtFirstName.getText().toString().trim().equalsIgnoreCase("") && !edTxtLastName.getText().toString().trim().equalsIgnoreCase("") && PassengerApp.getInstance().isEmailValid(edTxtEmailAddress.getText().toString().trim()) && !edTxtZipCode.getText().toString().trim().equalsIgnoreCase("") && edTxtContactNumber.getText().toString().trim().length() > (PassengerApp.getInstance().getCountryArray().get(callingCodeIndex).getCallingId().length() + 2) && edTxtContactNumber.getText().toString().trim().length() < 16) {
			new RegistrationAsyncTask(RegistationActivity.this, new OnRequestComplete() {
				
				@Override
				public void onRequestComplete(String result) {
					if("2001".equals(result)) {
						//ConstantValues.IS_FIRST_REGISTRATION = true;
						finish();
						preferenceUtil.setUserID(PassengerApp.getInstance().getPassengerId());
						preferenceUtil.setLOGIN_TYPES(""+loggedinType);
						startActivity(new Intent(RegistationActivity.this, CardRegistrationActivity.class));
					} else if ("4001".equals(result)){
						Toast.makeText(RegistationActivity.this, "Duplicate Email ID !", Toast.LENGTH_SHORT).show();
						
					}else{
						Toast.makeText(RegistationActivity.this, "Entry is not correct, please try again!", Toast.LENGTH_SHORT).show();
					}
				}
			}).execute(ConstantValues.FUNC_ID_REGISTRATION, "2", getDeviceToken(), preferenceUtil.getRegistrationKey(), "", edTxtFirstName.getText().toString().trim(), edTxtLastName.getText().toString().trim(), ""+loggedinType, PassengerApp.getInstance().getFbGplusLoginInfo().getFbGplusProfileId(), edTxtPassword.getText().toString().trim(), PassengerApp.getInstance().getCountryArray().get(callingCodeIndex).getId(), edTxtEmailAddress.getText().toString().trim(), edTxtZipCode.getText().toString().trim(), edTxtContactNumber.getText().toString().trim().replace(" ", ""), reSizedBitmapPath, PassengerApp.getInstance().getFbGplusLoginInfo().getFbGplusProfilePic(), "1");
		
		} else {
			if(edTxtFirstName.getText().toString().trim().equalsIgnoreCase("")) {
				edTxtFirstName.setError("Required");
			} 
			
			if(edTxtLastName.getText().toString().trim().equalsIgnoreCase("")) {
				edTxtLastName.setError("Required");
			}
			
			if(!PassengerApp.getInstance().isEmailValid(edTxtEmailAddress.getText().toString().trim())) {
				edTxtEmailAddress.setError("Required");
			}
			
			if(edTxtZipCode.getText().toString().trim().equalsIgnoreCase("")) {
				edTxtZipCode.setError("Required");
			}
			
			if(edTxtContactNumber.getText().toString().trim().length() <= (PassengerApp.getInstance().getCountryArray().get(callingCodeIndex).getCallingId().length() + 2)) {
				edTxtContactNumber.setError("Required");
			} else if (edTxtContactNumber.getText().toString().trim().length() > 15) {
				edTxtContactNumber.setError("Exceed maximum length ");
			}
		}
	}
	
	private String getDeviceToken() {
		return Secure.getString(RegistationActivity.this.getContentResolver(), Secure.ANDROID_ID);
	}
	
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		Log.e("RequesT Code is", "----"+resultCode);
		if (resultCode != RESULT_OK)
			return;
		if(resultCode == RESULT_CANCELED)
			return;
		if(resultCode == RESULT_FIRST_USER)
			return;
		
		switch (requestCode) {
		case PICK_FROM_CAMERA:
			Log.i("In case 1", "--------------------PICK_FROM_CAMERA");
			selectedImagePath = mImageCaptureUri.getPath().toString();
			
			try {
				BitmapFactory.Options btmapOptions = new BitmapFactory.Options();
				btmapOptions.inSampleSize = 6;  
				Bitmap btmapImg = BitmapFactory.decodeFile(selectedImagePath, btmapOptions);
				Bitmap resizedBitmap = Utils.getResizedBitmap(btmapImg, 102, 104);
				//imgViewProfilePic.setImageBitmap(resizedBitmap);
				reSizedBitmapPath = Utils.getBitmapPath(RegistationActivity.this,resizedBitmap);
				Log.e("Bitmap Path is:", "---"+reSizedBitmapPath);
				setBitMapImage(); 
				
			} catch (Exception e) {
				e.printStackTrace();
			}
			break;

		case PICK_FROM_FILE:
			mImageCaptureUri = data.getData();
			if(mImageCaptureUri != null){
				Log.i("In case 2", "--------------------PICK_FROM_FILE" + data.getData());
				
				Log.e("Bitmap Path is:", "---"+mImageCaptureUri);
				String url = data.getData().toString();
				Bitmap bitmap = null;
				java.io.InputStream is = null;
				if (url.startsWith("content://com.google.android.apps.photos.content") || url.startsWith("content://com.google.android.gallery3d.provider")){
				       try {
						is = getContentResolver().openInputStream(Uri.parse(url));
					} catch (FileNotFoundException e) {
						e.printStackTrace();
					}
				       bitmap =BitmapFactory.decodeStream(is);
				       Bitmap resizedBitmap = Utils.getResizedBitmap(bitmap, 102, 104);
				       //imgViewProfilePic.setImageBitmap(resizedBitmap);
				       reSizedBitmapPath = Utils.getBitmapPath(RegistationActivity.this,resizedBitmap);
				       Log.e("Bitmap Path is:", "---"+reSizedBitmapPath);
				       setBitMapImage(); 
				}else{
					String[] filePathColumn = { MediaStore.Images.Media.DATA };
					Cursor cursor = getContentResolver().query(mImageCaptureUri,
							filePathColumn, null, null, null);
					if(cursor == null){
						selectedImagePath = mImageCaptureUri.getPath().toString();
					}else{
						cursor.moveToFirst();
						int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
			
						selectedImagePath = cursor.getString(columnIndex);
						cursor.close();
					}
				
				try {
					BitmapFactory.Options btmapOptions = new BitmapFactory.Options();
					btmapOptions.inSampleSize = 6;  
					Log.e("selected Path is:", "---"+selectedImagePath);
					Bitmap btmapImg = BitmapFactory.decodeFile(selectedImagePath, btmapOptions);
					Bitmap resizedBitmap = Utils.getResizedBitmap(btmapImg, 102, 104);
					//imgViewProfilePic.setImageBitmap(resizedBitmap);
					reSizedBitmapPath = Utils.getBitmapPath(RegistationActivity.this,resizedBitmap);
					Log.e("Bitmap Path is:", "---"+reSizedBitmapPath);
					setBitMapImage(); 
					
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			
		}else{
		}
			break;
			
		default:
			break;
		}
	}
	
	 private void setBitMapImage() {
		 AQuery aq = new AQuery(RegistationActivity.this);
		    BitmapAjaxCallback bmCallBack = new BitmapAjaxCallback();
			bmCallBack.url(reSizedBitmapPath).targetWidth(300).rotate(true);
			bmCallBack.memCache(true);
			bmCallBack.fileCache(true);
			aq.id(imgViewProfilePic).image(bmCallBack);
	}
		

	private void startDialog() {
		    AlertDialog.Builder myAlertDialog = new AlertDialog.Builder(RegistationActivity.this);
		    myAlertDialog.setTitle("Upload Pictures Option");
		    myAlertDialog.setMessage("How do you want to set your picture?");

		    myAlertDialog.setNegativeButton("Gallery",
		            new DialogInterface.OnClickListener() {
		                public void onClick(DialogInterface arg0, int arg1) {
		                	BaseActivity.isLock = true;
//		                	Intent intent = new Intent();
//		    				intent.setType("image/*");
//		    				intent.setAction(Intent.ACTION_GET_CONTENT);
//		    				startActivityForResult(Intent.createChooser(intent, "Complete action using"),PICK_FROM_FILE);
		    				
		    				Intent i = new Intent(Intent.ACTION_PICK,android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
		    				i.setType("image/*");
		    				i.setAction(Intent.ACTION_GET_CONTENT);
		    				startActivityForResult(i, PICK_FROM_FILE);
		                }
		            });

		    myAlertDialog.setPositiveButton("Camera",
		            new DialogInterface.OnClickListener() {
		                public void onClick(DialogInterface arg0, int arg1) {
		                	BaseActivity.isLock = true;
		                	Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
		                	mImageCaptureUri = Uri.fromFile(new File(Environment.getExternalStorageDirectory(), "/netcabs_"+ String.valueOf(System.currentTimeMillis()) + ".jpg"));
		                	intent.putExtra(android.provider.MediaStore.EXTRA_OUTPUT,mImageCaptureUri);

		                	intent.putExtra("return-data", true);
		                	startActivityForResult(intent, PICK_FROM_CAMERA);
		                }
		            });
		    myAlertDialog.show();
		}
	 

	@Override
	public void onFocusChange(View v, boolean hasFocus) {
		switch (v.getId()) {
		case R.id.ed_txt_email_address:
			if(PassengerApp.getInstance().isEmailValid(edTxtEmailAddress.getText().toString().trim()) && !hasFocus) {
				if(InternetConnectivity.isConnectedToInternet(RegistationActivity.this)) {
					new EmailCheckAsyncTask(RegistationActivity.this, new OnRequestComplete() {
						
						@Override
						public void onRequestComplete(String result) {
							try {
								if("2001".equals(result)) {
									isEmailId = true;
								} else if("3001".equals(result)) {
									isEmailId = false;
								} else if("4001".equals(result)) {
									isEmailId = false;
									edTxtEmailAddress.setError("Someone already has that email id");
									Toast.makeText(RegistationActivity.this, "This email id is already registered, Please enter a new one", Toast.LENGTH_SHORT).show();
								} else if("1001".equals(result)) {
									edTxtEmailAddress.setError("Someone already has that email id");
									isEmailId = false;
								} else {
									isEmailId = false;
								}
							} catch (Exception e) {
								Toast.makeText(RegistationActivity.this, "Response Error: (" + e.getMessage() + ").  Please try again", Toast.LENGTH_SHORT).show();
							}
						}
					}).execute(ConstantValues.FUNC_ID_EMAIL_CHECK, edTxtEmailAddress.getText().toString());
				} else {
					Toast.makeText(RegistationActivity.this, ConstantValues.INTERNET_CONNECTION_FAILURE_MSG, Toast.LENGTH_SHORT).show();
				}
			} else {
				if(!hasFocus) {
					edTxtEmailAddress.setError("Please enter valid email");
				}
			}
			break;

		default:
			break;
		}
		
	}

	@Override
	public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
		imgViewFlag.setImageBitmap(PassengerApp.getInstance().getCountryArray().get(position).getImgBitmap());
		callingCodeIndex = position;
		callingCode = PassengerApp.getInstance().getCountryArray().get(position).getCallingId();
		txtViewCountry.setText(PassengerApp.getInstance().getCountryArray().get(position).getName());
		edTxtContactNumber.setText("+"+PassengerApp.getInstance().getCountryArray().get(position).getCallingId());
	}

	@Override
	public void onNothingSelected(AdapterView<?> parent) {
		
	}

}
