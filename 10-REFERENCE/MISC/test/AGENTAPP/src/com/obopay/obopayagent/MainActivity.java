package com.obopay.obopayagent;

import java.util.Hashtable;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.obopay.obopayagent.restclient.OboServices;
import com.obopay.obopayagent.restclient.OboServicesConstants;
import com.obopay.obopayagent.restclient.OboServicesListener;
import com.obopay.obopayagent.utils.PreferenceManager;
import com.obopay.obopayagent.utils.Utils;

public class MainActivity extends Activity implements OboServicesListener {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// Restore preferences
		SharedPreferences preferences = getSharedPreferences(Utils.PREFS_NAME,
				MODE_PRIVATE);
		boolean acceptedEULA = preferences.getBoolean(
				Utils.ACCEPTED_EULA_PREF_NAME, false);
		boolean deviceSMSActivated = !PreferenceManager.getSecurityToken(this)
				.equals("");
		boolean deviceActivated = PreferenceManager.getPhoneNumber(this) != null
				&& !PreferenceManager.getPhoneNumber(this).equals("");
		Intent intent = null;
		if (!acceptedEULA) {
			intent = new Intent(this, AcceptEULAActivity.class);
		} else if (!deviceSMSActivated || !deviceActivated) {
			intent = new Intent(this, SMSActivationActivity.class);
		} 
		intent = new Intent(this, SelectOptionActivity.class);
		if (intent != null) {
			startActivity(intent);
			finish();
			return;
		}
		super.onCreate(savedInstanceState);
		setContentView(R.layout.splash_screen);
		checkAccountStatus();
	}

	private void checkAccountStatus() {
		Hashtable params = new Hashtable();
		OboServices services = new OboServices(
				MainActivity.this, MainActivity.this);
		services.checkAccountStatus(params);
	}
	
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		switch (requestCode) {
		case (Utils.RESET_PIN): {
			if (resultCode == Activity.RESULT_OK) {
				Intent intent = new Intent(this, SelectOptionActivity.class);
				startActivity(intent);
				finish();
			} else {
				finish();
			}
		}
		}
	}

	@Override
	public void onOboServicesResult(int serviceType, Object result,
			OboServices svcObj) {
		final String downloadType = (String)OboServices.getParam((Hashtable)result, OboServicesConstants.DATA_FIELD_DOWNLOAD_TYPE);
		if(downloadType!=null && !downloadType.equals("NA")) {
			final String downloadUrl = (String)OboServices.getParam((Hashtable)result, OboServicesConstants.DATA_FIELD_DOWNLOAD_URL);
			Utils.showNewVersionDownloadDialog(this, downloadType.equals("O"), new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					dialog.dismiss();
					Utils.updateApplication(MainActivity.this, downloadUrl);
				}
			}, new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					if(downloadType.equals("F")) {
						finish();
					} else {
						Intent intent = new Intent(MainActivity.this, SelectOptionActivity.class);
						startActivity(intent);
						finish();
					}
				}
			});
		} else {
			String isFirstTime = (String)OboServices.getParam((Hashtable)result, OboServicesConstants.DATA_FIELD_IS_FIRST_TIME);
			String isPinReset = (String)OboServices.getParam((Hashtable)result, OboServicesConstants.DATA_FIELD_IS_PIN_RESET);
			String programTypes=(String)OboServices.getParam((Hashtable)result, OboServicesConstants.DATA_FIELD_PROGRAM_TYPE);
			String featureList=(String)OboServices.getParam((Hashtable)result, OboServicesConstants.DATA_FIELD_FEATURE_NOT_SUPPORTED);
			String genderTypes = (String) OboServices.getParam((Hashtable)result, OboServicesConstants.DATA_FIELD_GENDER_TYPE);
			String registrationFees = (String) OboServices.getParam((Hashtable)result, OboServicesConstants.DATA_FIELD_REGISTRATION_FEE);
			String minCashInAmount = (String) OboServices.getParam((Hashtable)result, OboServicesConstants.DATA_FIELD_MIN_CASH_IN_AMOUNT);
			String maxCashInAmount = (String) OboServices.getParam((Hashtable)result, OboServicesConstants.DATA_FIELD_MAX_CASH_IN_AMOUNT);
			String idTypes = (String) OboServices.getParam((Hashtable)result, OboServicesConstants.DATA_FIELD_ID_TYPES);
			String idTypeMinLength = (String) OboServices.getParam((Hashtable)result, OboServicesConstants.DATA_FIELD_ID_TYPE_MIN_LENGTH);
			String idTypeMaxLength = (String) OboServices.getParam((Hashtable)result, OboServicesConstants.DATA_FIELD_ID_TYPE_MAX_LENGTH);
			String registrationType = (String) OboServices.getParam((Hashtable)result, OboServicesConstants.DATA_FIELD_REGISTRATION_TYPE);
			
			PreferenceManager.setProgramTypes(this, programTypes);
			PreferenceManager.setGenderTypes(this,genderTypes);
			PreferenceManager.setRegistrationFee(this,registrationFees);
			PreferenceManager.setMinCashInAmount(this,minCashInAmount);
			PreferenceManager.setMaxCashInAmount(this,maxCashInAmount);
			PreferenceManager.setIdTypes(this,idTypes);
			PreferenceManager.setIdTypeMinLength(this,idTypeMinLength);
			PreferenceManager.setIdTypeMaxLength(this,idTypeMaxLength);
			PreferenceManager.setRegistrationType(this,registrationType);
			
			if(isPinReset.equals("Y")) {
				Intent intent = new Intent(this, ChangePinActivity.class);
				intent.putExtra("requestCode", Utils.RESET_PIN);  
				startActivityForResult(intent, Utils.RESET_PIN);
			} else {
				Intent intent = new Intent(this, SelectOptionActivity.class);
				startActivity(intent);
				finish();
			}
		}
	}

	@Override
	public void onOboServicesError(int serviceType, Exception e,
			OboServices svcObj) {
		if(serviceType == OboServicesConstants.METHOD_CHECK_ACCOUNT_STATUS) {
			final AlertDialog alertDialog = new AlertDialog.Builder(this).create();
			alertDialog.setCancelable(true);
			alertDialog.setButton(DialogInterface.BUTTON_POSITIVE, getString(android.R.string.ok), new OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					checkAccountStatus();
				}
			});
			alertDialog.setButton(DialogInterface.BUTTON_NEGATIVE, getString(android.R.string.cancel), new OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					finish();
				}
			});
			alertDialog.setOnCancelListener(new OnCancelListener() {
				
				@Override
				public void onCancel(DialogInterface dialog) {
					finish();
				}
			});
			alertDialog.setTitle(getString(R.string.error));
			alertDialog.setMessage(getString(R.string.failed_to_check_account_status_try_again));
			alertDialog.show();
		}
	}

}
