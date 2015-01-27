package com.netcabs.fragments;

import java.io.File;
import java.io.FileNotFoundException;

import android.app.AlertDialog;
import android.app.Fragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.webkit.WebView.FindListener;
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
import com.netcabs.asynctask.ProfileUpdateAsyncTask;
import com.netcabs.interfacecallback.OnRequestComplete;
import com.netcabs.internetconnection.InternetConnectivity;
import com.netcabs.passenger.MainMenuActivity;
import com.netcabs.passenger.R;
import com.netcabs.passenger.UserEnterPinConfirmActivity;
import com.netcabs.passengerinfo.PassengerApp;
import com.netcabs.utils.BaseActivity;
import com.netcabs.utils.ConstantValues;
import com.netcabs.utils.Utils;

public class MyDetailsFragment extends Fragment implements OnClickListener, OnItemSelectedListener {

	private EditText edTxtFirstName;
	private EditText edTxtLastName;
	private EditText edTxtEmailAddress;
	private EditText edTxtCurrentPassword;
	private EditText edTxtPassword;
	private EditText edTxtConfirmPassword;
	private EditText edTxtContactNumber;
	//private EditText edTxtCountry;
	private EditText edTxtZipCode;
	private ImageView imgViewProfilePic;
	
	private Spinner spinnerCountry;
	private TextView txtViewCountry;
	private LinearLayout linearCountry;
	private ImageView imgViewFlag;
	private Button btnLogInLogo;
	
	private Button btnUpdatePin;
	private Button btnSave;
	
	private Uri mImageCaptureUri;
	private static final int PICK_FROM_CAMERA = 1;
	private static final int PICK_FROM_FILE = 2;
	private String selectedImagePath = "";
	private String reSizedBitmapPath = "";
	private String callingCode = "";
	private View view;
	private int countryIndex = 0;
	private boolean isSelectedFromSpinner = false;
	private String mobileNumber = "";
	MainMenuActivity mainMenuActivity;
	private RelativeLayout relativeLayout;
	private LinearLayout linearLayoutTitle;
	private LinearLayout linearLayoutAddress;
	private TextView txtViewTitle;
	
	
	private static int rotate = 0;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.fragment_my_details, null);
		mainMenuActivity = (MainMenuActivity) getActivity();
		
		initViews();
		setListener();
		loadData();
		
		return view;
	}

	private void initViews() {
		linearLayoutTitle =(LinearLayout)getActivity().findViewById(R.id.liner_layout_title);
		linearLayoutAddress =(LinearLayout)getActivity().findViewById(R.id.liner_layout_address);
		linearLayoutTitle.setVisibility(View.VISIBLE);
		linearLayoutAddress.setVisibility(View.INVISIBLE);
		txtViewTitle = (TextView) getActivity().findViewById(R.id.txt_view_title);
		txtViewTitle.setText("My Details");
		
		relativeLayout = (RelativeLayout) view.findViewById(R.id.relative_main);
		edTxtFirstName = (EditText) view.findViewById(R.id.ed_txt_first_name);
		edTxtLastName = (EditText) view.findViewById(R.id.ed_txt_last_name);
		edTxtEmailAddress = (EditText) view.findViewById(R.id.ed_txt_email_address);
		edTxtEmailAddress.setFocusable(false);
		edTxtEmailAddress.setClickable(false);
		edTxtCurrentPassword = (EditText) view.findViewById(R.id.ed_txt_current_password);
		edTxtPassword = (EditText) view.findViewById(R.id.ed_txt_password);
		edTxtConfirmPassword = (EditText) view.findViewById(R.id.ed_txt_confirm_password);
		edTxtZipCode = (EditText) view.findViewById(R.id.ed_txt_zip_code);
		edTxtContactNumber = (EditText) view.findViewById(R.id.ed_txt_contact_number);
		btnLogInLogo = (Button) view.findViewById(R.id.btn_login_logo);
		imgViewProfilePic = (ImageView) view.findViewById(R.id.img_view_user_pic);
		
		btnUpdatePin = (Button) view.findViewById(R.id.btn_update_pin);
		btnSave = (Button) getActivity().findViewById(R.id.btn_search_location_name);
		
		linearCountry = (LinearLayout)view.findViewById(R.id.linear_layout_country);
		txtViewCountry = (TextView)view.findViewById(R.id.txt_view_country);
		spinnerCountry = (Spinner)view.findViewById(R.id.spinner_country);
		imgViewFlag = (ImageView) view.findViewById(R.id.img_view_flag);
		
		if(PassengerApp.getInstance().getCountryArray() != null) {
			CountrySpinnerAdapter countryAdapter = new CountrySpinnerAdapter(getActivity(), PassengerApp.getInstance().getCountryArray());
			spinnerCountry.setAdapter(countryAdapter);
		}
		
		if(PassengerApp.getInstance().getLogInType() > 0 && PassengerApp.getInstance().getLogInType()!= 3){
//			edTxtCurrentPassword.setAlpha(0.5f);
//			edTxtPassword.setAlpha(0.5f);
//			edTxtConfirmPassword.setAlpha(0.5f);
//			edTxtCurrentPassword.setEnabled(false);
//			edTxtPassword.setEnabled(false);
//			edTxtConfirmPassword.setEnabled(false);
			btnLogInLogo.setVisibility(View.VISIBLE);
			edTxtCurrentPassword.setVisibility(View.GONE);
			edTxtConfirmPassword.setVisibility(View.GONE);
			edTxtPassword.setVisibility(View.GONE);
			if(PassengerApp.getInstance().getLogInType() == 1){
				btnLogInLogo.setBackgroundResource(R.drawable.logged_google);
			} else {
				btnLogInLogo.setBackgroundResource(R.drawable.logged_fb);
			}
			
		} else{
			btnLogInLogo.setVisibility(View.GONE);
			edTxtCurrentPassword.setVisibility(View.VISIBLE);
			edTxtConfirmPassword.setVisibility(View.VISIBLE);
			edTxtPassword.setVisibility(View.VISIBLE);
		}
	}

	private void setListener() {
		relativeLayout.setOnClickListener(this);
		btnUpdatePin.setOnClickListener(this);
		btnSave.setOnClickListener(this);
		imgViewProfilePic.setOnClickListener(this);
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
				if(edTxtContactNumber.getText().length() == (callingCode.length())) {
					Log.i("If", "___________");
					edTxtContactNumber.setText(edTxtContactNumber.getText() + " ");
					edTxtContactNumber.setSelection(edTxtContactNumber.getText().toString().length());
				} else {
					Log.i("If", "______else______");
				}
				
				if(edTxtContactNumber.getText().toString().contains(" ")) {
					if(edTxtContactNumber.getText().toString().split(" ").length > 1) {
						mobileNumber = edTxtContactNumber.getText().toString().split(" ")[1];
					} else {
						mobileNumber = "";
					}
					
				} else {
					
				}
			}
		});
	}

	private void loadData() {
		((TextView) getActivity().findViewById(R.id.txt_view_title)).setText("My Details");
		btnSave.setBackgroundResource(R.drawable.save_btn);
		btnSave.setVisibility(View.VISIBLE);
		
		edTxtFirstName.setText(PassengerApp.getInstance().getProfileDetailsInfo().getFirstName());
		edTxtFirstName.setSelection(PassengerApp.getInstance().getProfileDetailsInfo().getFirstName().length());
		edTxtLastName.setText(PassengerApp.getInstance().getProfileDetailsInfo().getLastName());
		edTxtEmailAddress.setText(PassengerApp.getInstance().getProfileDetailsInfo().getEmail());
		txtViewCountry.setText(PassengerApp.getInstance().getProfileDetailsInfo().getCountry());
		edTxtZipCode.setText(PassengerApp.getInstance().getProfileDetailsInfo().getZip());
		txtViewCountry.setText(PassengerApp.getInstance().getProfileDetailsInfo().getCountry());
		
		if (PassengerApp.getInstance().getCountryArray() != null) {
			for(int i = 0; i < PassengerApp.getInstance().getCountryArray().size(); i++) {
				if(PassengerApp.getInstance().getProfileDetailsInfo().getCountryId().equals(PassengerApp.getInstance().getCountryArray().get(i).getId())) {
					countryIndex = i;
				} 
			}
		}
		
		callingCode = "+"+PassengerApp.getInstance().getCountryArray().get(countryIndex).getCallingId().trim() +" ";
		mobileNumber = PassengerApp.getInstance().getProfileDetailsInfo().getMobileNo().toString().substring(callingCode.length() - 1, PassengerApp.getInstance().getProfileDetailsInfo().getMobileNo().toString().length());
		edTxtContactNumber.setText(callingCode + mobileNumber.trim());
		
		imgViewFlag.setImageBitmap(PassengerApp.getInstance().getCountryArray().get(countryIndex).getImgBitmap());
		//reSizedBitmapPath  = PassengerApp.getInstance().getProfileDetailsInfo().getProfilePic();
		new AQuery(getActivity()).id(imgViewProfilePic).image(PassengerApp.getInstance().getProfileDetailsInfo().getProfilePic(), true, true, 0, R.drawable.image_holdar);
	
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.relative_main:
			PassengerApp.getInstance().hideKeyboard(getActivity(), v);
			break;
		
		case R.id.btn_update_pin:
			mainMenuActivity.isLock = true;
			startActivity(new Intent(getActivity(), UserEnterPinConfirmActivity.class).putExtra("is_confirm_pin", false));
			break;
			
		case R.id.img_view_user_pic:
			startDialog();
			break;
			
		case R.id.linear_layout_country:
			PassengerApp.getInstance().hideKeyboard(getActivity(), v);
			isSelectedFromSpinner = true;
			spinnerCountry.setVisibility(View.VISIBLE);
			spinnerCountry.setSelection(countryIndex);
			spinnerCountry.performClick();
			break;
			
		case R.id.btn_search_location_name:
			String countryId = "";
			if(txtViewCountry.getText()!=null){
				if(isSelectedFromSpinner){
					countryId = PassengerApp.getInstance().getCountryArray().get(countryIndex).getId();
				}else{
					countryId = PassengerApp.getInstance().getProfileDetailsInfo().getCountryId();
				}
			}
			
			if(edTxtCurrentPassword.getText().toString().trim().length() > 0) {
				if(!edTxtCurrentPassword.getText().toString().trim().equals(edTxtPassword.getText().toString().trim())) {
					if(edTxtPassword.getText().toString().trim().equals(edTxtConfirmPassword.getText().toString().trim()) && edTxtPassword.getText().length() >= ConstantValues.PASSWORD_LENGTH) {
						if(!edTxtFirstName.getText().toString().trim().equalsIgnoreCase("") && !edTxtLastName.getText().toString().trim().equalsIgnoreCase("") && !txtViewCountry.getText().toString().trim().equalsIgnoreCase("") && !edTxtZipCode.getText().toString().trim().equalsIgnoreCase("") && edTxtContactNumber.getText().toString().trim().length() > (PassengerApp.getInstance().getCountryArray().get(countryIndex).getCallingId().length() + 2)) {
							if(InternetConnectivity.isConnectedToInternet(getActivity())) {
								new ProfileUpdateAsyncTask(getActivity(), new OnRequestComplete() {
									
									@Override
									public void onRequestComplete(String result) {
										try {
											if("2001".equals(result)) {
												PassengerApp.getInstance().getProfileDetailsInfo().setFirstName(edTxtFirstName.getText().toString().trim());
												PassengerApp.getInstance().getProfileDetailsInfo().setLastName(edTxtLastName.getText().toString().trim());
												PassengerApp.getInstance().getProfileDetailsInfo().setCountry(txtViewCountry.getText().toString().trim());
												PassengerApp.getInstance().getProfileDetailsInfo().setProfilePic("");
												
												if(isSelectedFromSpinner){
													PassengerApp.getInstance().getProfileDetailsInfo().setCountryId(PassengerApp.getInstance().getCountryArray().get(countryIndex).getId());
												}
												PassengerApp.getInstance().getProfileDetailsInfo().setZip(edTxtZipCode.getText().toString().trim());
												PassengerApp.getInstance().getProfileDetailsInfo().setMobileNo(edTxtContactNumber.getText().toString().trim().replace(" ", ""));
												edTxtCurrentPassword.setText("");
												edTxtPassword.setText("");
												edTxtConfirmPassword.setText("");
												Toast.makeText(getActivity(), "Profile Update is Successful!", Toast.LENGTH_SHORT).show();
											} else if("3001".equals(result)) {
												
											} else if("4001".equals(result)) {
																
											} else if("5001".equals(result)) {
												Toast.makeText(getActivity(), "Current password doesn't match", Toast.LENGTH_SHORT).show();
											}
										} catch (Exception e) {
												Toast.makeText(getActivity(), "Response Error: (" + e.getMessage() + ").  Please try again", Toast.LENGTH_SHORT).show();
										}
									}
								}).execute(ConstantValues.FUNC_ID_UPDATE_PROFILE, PassengerApp.getInstance().getPassengerId(), edTxtFirstName.getText().toString().trim(), edTxtLastName.getText().toString().trim(), edTxtCurrentPassword.getText().toString().trim(), edTxtPassword.getText().toString().trim(), countryId, edTxtEmailAddress.getText().toString().trim(), edTxtZipCode.getText().toString().trim(), edTxtContactNumber.getText().toString().trim().replace(" ", ""), reSizedBitmapPath, "");
							} else {
								Toast.makeText(getActivity(), ConstantValues.INTERNET_CONNECTION_FAILURE_MSG, Toast.LENGTH_SHORT).show();
							}
						} else {
							
							if(edTxtFirstName.getText().toString().trim().equalsIgnoreCase("")) {
								edTxtFirstName.setError("Required");
							} 
							
							if(edTxtLastName.getText().toString().trim().equalsIgnoreCase("")) {
								edTxtLastName.setError("Required");
							}
							
							if(edTxtPassword.getText().toString().trim().equalsIgnoreCase("")) {
								edTxtPassword.setError("Required");
							} 
							
							if(edTxtConfirmPassword.getText().toString().trim().equalsIgnoreCase("")) {
								edTxtConfirmPassword.setError("Required");
							}
							
							if(edTxtZipCode.getText().toString().trim().equalsIgnoreCase("")) {
								edTxtZipCode.setError("Required");
							}
							
							if(edTxtContactNumber.getText().toString().trim().length() <= (PassengerApp.getInstance().getCountryArray().get(countryIndex).getCallingId().length() + 1)) {
								edTxtContactNumber.setError("Required");
							}
							
							//Toast.makeText(getActivity(), "Please... !", Toast.LENGTH_SHORT).show();
						}
						
					} else {
						if(edTxtPassword.getText().toString().trim().length() < ConstantValues.PASSWORD_LENGTH) {
							edTxtPassword.setError("Length must be "+ConstantValues.PASSWORD_LENGTH);
						}
						if(edTxtConfirmPassword.getText().toString().trim().length() < ConstantValues.PASSWORD_LENGTH) {
							edTxtConfirmPassword.setError("Length must be "+ConstantValues.PASSWORD_LENGTH);
						}
						Toast.makeText(getActivity(), "New and Confirm Password not mathced !", Toast.LENGTH_SHORT).show();
					}
				} else {
					Toast.makeText(getActivity(), "There are no difference between Current and New Password !", Toast.LENGTH_SHORT).show();
				}
				
			} else {
				if(!edTxtFirstName.getText().toString().trim().equalsIgnoreCase("") && !edTxtLastName.getText().toString().trim().equalsIgnoreCase("") && !txtViewCountry.getText().toString().trim().equalsIgnoreCase("") && !edTxtZipCode.getText().toString().trim().equalsIgnoreCase("") && edTxtContactNumber.getText().toString().trim().length() > (PassengerApp.getInstance().getCountryArray().get(countryIndex).getCallingId().length() + 2) && edTxtContactNumber.getText().toString().trim().length() < 16) {
					if(!PassengerApp.getInstance().getProfileDetailsInfo().getFirstName().equals(edTxtFirstName.getText().toString().trim()) 
							|| !PassengerApp.getInstance().getProfileDetailsInfo().getLastName().equals(edTxtLastName.getText().toString().trim()) 
							|| !PassengerApp.getInstance().getProfileDetailsInfo().getEmail().equals(edTxtEmailAddress.getText().toString().trim()) 
							|| !PassengerApp.getInstance().getProfileDetailsInfo().getCountry().equals(txtViewCountry.getText().toString().trim()) 
							|| !PassengerApp.getInstance().getProfileDetailsInfo().getZip().equals(edTxtZipCode.getText().toString().trim()) 
							|| !PassengerApp.getInstance().getProfileDetailsInfo().getMobileNo().equals(edTxtContactNumber.getText().toString().trim().replace(" ", ""))
							|| !reSizedBitmapPath.equalsIgnoreCase("")) {
						if(InternetConnectivity.isConnectedToInternet(getActivity())) {
							new ProfileUpdateAsyncTask(getActivity(), new OnRequestComplete() {
								
								@Override
								public void onRequestComplete(String result) {
									try {
										if("2001".equals(result)) {
											reSizedBitmapPath = "";
											PassengerApp.getInstance().getProfileDetailsInfo().setFirstName(edTxtFirstName.getText().toString().trim());
											PassengerApp.getInstance().getProfileDetailsInfo().setLastName(edTxtLastName.getText().toString().trim());
											PassengerApp.getInstance().getProfileDetailsInfo().setCountry(txtViewCountry.getText().toString().trim());
											PassengerApp.getInstance().getProfileDetailsInfo().setZip(edTxtZipCode.getText().toString().trim());
											if(isSelectedFromSpinner) {
												PassengerApp.getInstance().getProfileDetailsInfo().setCountryId(PassengerApp.getInstance().getCountryArray().get(countryIndex).getId());
											}
											PassengerApp.getInstance().getProfileDetailsInfo().setMobileNo(edTxtContactNumber.getText().toString().trim().replace(" ", ""));
											Toast.makeText(getActivity(), "Profile Update is Successful!", Toast.LENGTH_SHORT).show();
										} else if("3001".equals(result)) {
											
										} else if("4001".equals(result)) {
															
										} else {
											Toast.makeText(getActivity(), "Please fill the fields with correct Information!", Toast.LENGTH_SHORT).show();
										}
									} catch (Exception e) {
										Toast.makeText(getActivity(), "Response Error: (" + e.getMessage() + ").  Please try again", Toast.LENGTH_SHORT).show();
									}
								}
							}).execute(ConstantValues.FUNC_ID_UPDATE_PROFILE, PassengerApp.getInstance().getPassengerId(), edTxtFirstName.getText().toString().trim(), edTxtLastName.getText().toString().trim(), "", "", countryId, edTxtEmailAddress.getText().toString().trim(), edTxtZipCode.getText().toString().trim(), edTxtContactNumber.getText().toString().trim().replace(" ", ""), reSizedBitmapPath, "");
						} else{
							Toast.makeText(getActivity(), ConstantValues.INTERNET_CONNECTION_FAILURE_MSG, Toast.LENGTH_SHORT).show();
						}
					} else {
						Toast.makeText(getActivity(), "There are no update... !", Toast.LENGTH_SHORT).show();
					}
					
				} else {
					if(edTxtFirstName.getText().toString().trim().equalsIgnoreCase("")) {
						edTxtFirstName.setError("Required");
					} 
					
					if(edTxtLastName.getText().toString().trim().equalsIgnoreCase("")) {
						edTxtLastName.setError("Required");
					}
					
					if(edTxtZipCode.getText().toString().trim().equalsIgnoreCase("")) {
						edTxtZipCode.setError("Required");
					}
					
					if(edTxtContactNumber.getText().toString().trim().length() <= (PassengerApp.getInstance().getCountryArray().get(countryIndex).getCallingId().length() + 1)) {
						edTxtContactNumber.setError("Required");
					} else if(edTxtContactNumber.getText().toString().trim().length() > 15) {
						edTxtContactNumber.setError("Exceed maximum length ");
					}
					//Toast.makeText(getActivity(), "Please... !", Toast.LENGTH_SHORT).show();
				}
			}
			break;

		default:
			break;
		
		}
	}
	
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		Log.e("RequesT Code is", "----"+resultCode);
		if (resultCode != -1)
			return;
		if(resultCode == 0)
			return;
		if(resultCode == 1)
			return;

		switch (requestCode) {
		case PICK_FROM_CAMERA:
			Log.i("In case 1", "--------------------PICK_FROM_CAMERA");
			selectedImagePath = mImageCaptureUri.getPath().toString();
			getCameraPhotoOrientation(getActivity(), mImageCaptureUri, selectedImagePath);
			
			try {
				BitmapFactory.Options btmapOptions = new BitmapFactory.Options();
				btmapOptions.inSampleSize = 6;  
				Bitmap btmapImg = BitmapFactory.decodeFile(selectedImagePath, btmapOptions);
				Bitmap resizedBitmap = Utils.getResizedBitmap(btmapImg, 102, 104);
				//imgViewProfilePic.setImageBitmap(resizedBitmap);
				reSizedBitmapPath = Utils.getBitmapPath(getActivity(),resizedBitmap);
				Log.e("Bitmap Path is:", "---"+reSizedBitmapPath);
				setBitMapImage();
			} catch (Exception e) {
				e.printStackTrace();
			}
			break;

		case PICK_FROM_FILE:
			mImageCaptureUri = data.getData();
			if(mImageCaptureUri != null){
				Log.i("In case 2", "--------------------PICK_FROM_FILE");
				Log.e("Bitmap Path is:", "---"+mImageCaptureUri);
				String url = data.getData().toString();
				Bitmap bitmap = null;
				java.io.InputStream is = null;
				if (url.startsWith("content://com.google.android.apps.photos.content") || url.startsWith("content://com.google.android.gallery3d.provider")){
				       try {
						is = getActivity().getContentResolver().openInputStream(Uri.parse(url));
					} catch (FileNotFoundException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				       bitmap =BitmapFactory.decodeStream(is);
				       Bitmap resizedBitmap = Utils.getResizedBitmap(bitmap, 102, 104);
				       //imgViewProfilePic.setImageBitmap(resizedBitmap);
				       reSizedBitmapPath = Utils.getBitmapPath(getActivity(),resizedBitmap);
				       Log.e("Bitmap Path is:", "---"+reSizedBitmapPath);
				       setBitMapImage();
				}else{
					String[] filePathColumn = { MediaStore.Images.Media.DATA };
					Cursor cursor = getActivity().getContentResolver().query(mImageCaptureUri,
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
					reSizedBitmapPath = Utils.getBitmapPath(getActivity(),resizedBitmap);
					Log.e("Bitmap Path is:", "---"+reSizedBitmapPath);
					
					setBitMapImage();
					
					
					
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
			break;
			
		default:
			break;
		}
	}
	
	 private void setBitMapImage() {
		 AQuery aq = new AQuery(getActivity());
		    BitmapAjaxCallback bmCallBack = new BitmapAjaxCallback();
			bmCallBack.url(reSizedBitmapPath).targetWidth(300).rotate(true);
			bmCallBack.memCache(true);
			bmCallBack.fileCache(true);
			imgViewProfilePic.setRotation(rotate);
			aq.id(imgViewProfilePic).image(bmCallBack);
			rotate = 0;
	}

	private void startDialog() {
		    AlertDialog.Builder myAlertDialog = new AlertDialog.Builder(getActivity());
		    myAlertDialog.setTitle("Upload Pictures Option");
		    myAlertDialog.setMessage("How do you want to set your picture?");

		    myAlertDialog.setNegativeButton("Gallery",
		            new DialogInterface.OnClickListener() {
		                public void onClick(DialogInterface arg0, int arg1) {
		                	BaseActivity.isLock = true;
//		                	Intent intent = new Intent();
//		    				intent.setType("image/*");
//		    				intent.setAction(Intent.ACTION_GET_CONTENT);
//		    				startActivityForResult(Intent.createChooser(intent, "Complete action using"), PICK_FROM_FILE);
		                	mainMenuActivity.isLock = true;
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
		                	mainMenuActivity.isLock = true;
		                	Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
		                	intent.putExtra("crop", "true");
		                	mImageCaptureUri = Uri.fromFile(new File(Environment.getExternalStorageDirectory(), "/netcabs_" + String.valueOf(System.currentTimeMillis()) + ".jpg"));
		                	intent.putExtra(android.provider.MediaStore.EXTRA_OUTPUT, mImageCaptureUri);

		                	intent.putExtra("return-data", true);
		                	startActivityForResult(intent, PICK_FROM_CAMERA);
		                }
		            });
		    myAlertDialog.show();
		}

	@Override
	public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
		imgViewFlag.setImageBitmap(PassengerApp.getInstance().getCountryArray().get(position).getImgBitmap());
		callingCode = "+"+PassengerApp.getInstance().getCountryArray().get(position).getCallingId() + " ";
		txtViewCountry.setText(PassengerApp.getInstance().getCountryArray().get(position).getName());
		countryIndex = position;
		edTxtContactNumber.setText(callingCode + mobileNumber);
		edTxtContactNumber.setSelection(edTxtContactNumber.getText().toString().length());
	}

	@Override
	public void onNothingSelected(AdapterView<?> parent) {
		
	}
	
	public static int getCameraPhotoOrientation(Context context, Uri imageUri, String imagePath){
	    //int rotate = 0;
	    try {
	        context.getContentResolver().notifyChange(imageUri, null);
	        File imageFile = new File(imagePath);
	        ExifInterface exif = new ExifInterface(
	                imageFile.getAbsolutePath());
	        int orientation = exif.getAttributeInt(
	                ExifInterface.TAG_ORIENTATION,
	                ExifInterface.ORIENTATION_NORMAL);

	        switch (orientation) {
	        case ExifInterface.ORIENTATION_ROTATE_270:
	            rotate = 270;
	            break;
	            
	        case ExifInterface.ORIENTATION_ROTATE_180:
	            rotate = 180;
	            break;
	        case ExifInterface.ORIENTATION_ROTATE_90:
	            rotate = 90;
	            break;
	            
	        default:
	        	rotate = 0;
				break;
	        }

	        Log.e("Orientation is", "Exif orientation: " + orientation+"::::"+rotate);
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	   return rotate;
	}
}
