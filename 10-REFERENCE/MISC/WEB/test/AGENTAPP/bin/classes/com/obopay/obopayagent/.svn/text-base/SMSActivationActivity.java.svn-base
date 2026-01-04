package com.obopay.obopayagent;

import java.util.Hashtable;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.DialogInterface.OnCancelListener;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.telephony.SmsManager;

import com.obopay.obopayagent.EnterPinDialog.EnterPinDialogListener;
import com.obopay.obopayagent.restclient.OboServices;
import com.obopay.obopayagent.restclient.OboServicesConstants;
import com.obopay.obopayagent.restclient.OboServicesListener;
import com.obopay.obopayagent.utils.CustomProcessDialog;
import com.obopay.obopayagent.utils.PopUpUtil;
import com.obopay.obopayagent.utils.PreferenceManager;
import com.obopay.obopayagent.utils.Utils;

public class SMSActivationActivity extends FragmentActivity implements EnterPinDialogListener, OboServicesListener {
	private static final String SENT = "OBOPAY_SMS_SENT";
	private BroadcastReceiver mBroadcastReceiver = new BroadcastReceiver() {
		@Override
        public void onReceive(Context arg0, Intent arg1) 
        {
			CustomProcessDialog.hide();
            int resultCode = getResultCode();
            switch (resultCode) 
            {
             case Activity.RESULT_OK: 
            	 makeTokenValidationService();
            	 break;
            case SmsManager.RESULT_ERROR_GENERIC_FAILURE:  
            case SmsManager.RESULT_ERROR_NO_SERVICE:       
            case SmsManager.RESULT_ERROR_NULL_PDU:         
            case SmsManager.RESULT_ERROR_RADIO_OFF:        
            	handleSMSFailure(resultCode);
                break;
            }
        }
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_sms_activation);
		SharedPreferences preferences = getSharedPreferences(Utils.PREFS_NAME,
				MODE_PRIVATE);
		boolean deviceSMSActivated = !PreferenceManager.getSecurityToken(this)
				.equals("");
		boolean deviceActivated = PreferenceManager.getPhoneNumber(this) != null
				&& !PreferenceManager.getPhoneNumber(this).equals("");
		if(!deviceSMSActivated)
			getMobileToken();
		else if(!deviceActivated)
			checkAccountStatus();
	}
	
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		switch (requestCode) {
		case (Utils.RESET_PIN): {
			if (resultCode == Activity.RESULT_OK) {
				Intent intent = new Intent(this, SelectOptionActivity.class);
				startActivity(intent);
			}
			finish();
		}
		}
	}

	@Override
	protected void onPause() {
		super.onPause();
		unregisterReceiver(mBroadcastReceiver);
	}

	@Override
	protected void onResume() {
		super.onResume();
		registerReceiver(mBroadcastReceiver, new IntentFilter(SENT));
	}
	
	@Override
	public void onFinishPinDialog(String pin) {
		Hashtable params = new Hashtable();
		params.put(OboServicesConstants.DATA_FIELD_PIN, pin);
		OboServices services = new OboServices(this, this);
		services.verifyPIN(params);
	}


	protected void handleSMSFailure(int resultCode) {
		PopUpUtil.error(this, getString(R.string.failed_to_send_token_sms, resultCode));
	}

	protected void makeTokenValidationService() {
		CustomProcessDialog.show(this, getString(R.string.wait_few_seconds_before_polling_for_token_validation));
		Handler handler = new Handler();
		Runnable myrunnable = new Runnable() {
			public void run() {
				CustomProcessDialog.hide();
				OboServices services = new OboServices(
						SMSActivationActivity.this, SMSActivationActivity.this);
				services.pollForTokenValidation(new Hashtable());
			}
		};
		handler.postDelayed(myrunnable, 6000);
	}

	private void getMobileToken() {
		OboServices services = new OboServices(
				this, this);
		services.requestClientToken(new Hashtable());
	}

	
	private void checkAccountStatus() {
		Hashtable params = new Hashtable();
		OboServices services = new OboServices(
				SMSActivationActivity.this, SMSActivationActivity.this);
		services.checkAccountStatus(params);
	}
	

	/**
	 * Sets the token for MFA/MDN verification.
	 * Packages access for the app state manager classes.
	 * @param token			    The token, or <code>null</code> to delete the token 
	 * 							and revert to the default value.
	 */
	@Override
	public void onOboServicesResult(int serviceType, Object result,
			OboServices svcObj) {
		if(serviceType == OboServicesConstants.METHOD_CHECK_ACCOUNT_STATUS) {
			final String downloadType = (String)OboServices.getParam((Hashtable)result, OboServicesConstants.DATA_FIELD_DOWNLOAD_TYPE);
			final String isFirstTime = (String)OboServices.getParam((Hashtable)result, OboServicesConstants.DATA_FIELD_IS_FIRST_TIME);
			final String isPinReset = (String)OboServices.getParam((Hashtable)result, OboServicesConstants.DATA_FIELD_IS_PIN_RESET);

			if(downloadType!=null && !downloadType.equals("NA")) {
				final String downloadUrl = (String)OboServices.getParam((Hashtable)result, OboServicesConstants.DATA_FIELD_DOWNLOAD_URL);
				Utils.showNewVersionDownloadDialog(this, downloadType.equals("O"), new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();
						Utils.updateApplication(SMSActivationActivity.this, downloadUrl);
					}
				}, new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						if(downloadType.equals("F")) {
							finish();
						} else if(isPinReset.equals("Y")) {
							Intent intent = new Intent(SMSActivationActivity.this, ChangePinActivity.class);
							intent.putExtra("requestCode", Utils.RESET_PIN);  
							startActivityForResult(intent, Utils.RESET_PIN);
						} else if(isFirstTime.equals("Y")) {
							Intent activationIntent = new Intent(SMSActivationActivity.this, ActivationActivity.class);
							startActivity(activationIntent);
							finish();
						} else {
							openPinDialog();
						}
					}
				});
			} else {
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
				} else if(isFirstTime.equals("Y")) {
					Intent activationIntent = new Intent(this, ActivationActivity.class);
					startActivity(activationIntent);
					finish();
				} else {
					openPinDialog();
				}
			}
		} else if(serviceType == OboServicesConstants.METHOD_REQUEST_TOKEN) {
			PreferenceManager.setMFAToken(this, (String) result);
			CustomProcessDialog.show(this, getString(R.string.send_token_sms));
			SmsManager smsManager =     SmsManager.getDefault();
			String mobileNumberForSMS = PreferenceManager.getAggregatorNumber(this);
			String SMS_FORMAT = "VERTOK";
			String smsMessage = SMS_FORMAT + " " + result;
			PendingIntent deliveryIntent = PendingIntent.getBroadcast(this, 0, new Intent(SENT), 0);
			smsManager.sendTextMessage(mobileNumberForSMS, null, smsMessage, null, deliveryIntent);
		} else if(serviceType == OboServicesConstants.METHOD_POLL_TOKEN_VALIDATION) {
			if(null == result || ((String)result).length() == 0) {
				PopUpUtil.error(this, getString(R.string.failed_to_validate_mobile_token, getString(R.string.response_empty)), new OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						finish();
					}
				});
				return;
			}
			PreferenceManager.setSecurityToken(this, (String) result);
			checkAccountStatus();
		} else if(serviceType == OboServicesConstants.METHOD_VERIFY_PIN) {
			String status = (String)OboServices.getParam((Hashtable)result, OboServicesConstants.DATA_FIELD_STATUS);
			if(status.equals("SUCCESS")) {
		    	Intent selectOptionIntent = new Intent(this, SelectOptionActivity.class);
		    	startActivity(selectOptionIntent);
		    	finish();
			} else {
				PopUpUtil.error(this, getString(R.string.pin_value_wrong), new OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						openPinDialog();
					}
				});
			}
		}
	}

	private void openPinDialog() {
		FragmentManager fm = getSupportFragmentManager();
		EnterPinDialog enterPinDialog = new EnterPinDialog() {
			@Override
			public void onCancel(DialogInterface dialog) {
				finish();
			}
		};
		enterPinDialog.show(fm, "fragment_enter_pin");
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
		} else if(serviceType == OboServicesConstants.METHOD_REQUEST_TOKEN) {
			PopUpUtil.error(this, getString(R.string.failed_to_retrieve_mobile_token, e.getMessage()), new OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					finish();
				}
			});
		} else if(serviceType == OboServicesConstants.METHOD_VERIFY_PIN) {
			PopUpUtil.error(this, getString(R.string.failed_to_verify_pin, e.getMessage()), new OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					openPinDialog();
				}
			});
		} else if(serviceType == OboServicesConstants.METHOD_VALIDATING_TXN_ID) {
			PopUpUtil.error(this, getString(R.string.failed_to_validate_mobile_token, e.getMessage()), new OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					finish();
				}
			});
		} else if(serviceType == OboServicesConstants.METHOD_POLL_TOKEN_VALIDATION) {
			PopUpUtil.error(this, getString(R.string.failed_to_validate_mobile_token, e.getMessage()), new OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					finish();
				}
			});
		}
	}
}
