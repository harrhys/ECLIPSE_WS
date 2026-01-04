package com.obopay.obopayagent.utils;

 import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.http.HttpResponse;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Environment;
import android.telephony.TelephonyManager;
import android.util.DisplayMetrics;
import android.util.Log;

import com.loopj.android.http.BinaryHttpResponseHandler;
import com.obopay.obopayagent.R;
import com.obopay.obopayagent.restclient.OboServices;


public class Utils {

	public static final String LOG_TAG= "com.obopay.obopayagent";  
	public static final String CUSTOMER = "Customer";
	public static final String CUSTOMER_DEPOSIT = "CustomerDeposit";
	public static final String CUSTOMER_REMITTANCE = "CustomerRemittance";
	public static final String CUSTOMER_VERIFICATION = "CustomerVerification";
	public static final String TRANSACTION = "Transaction";

	public static final int CREATE_CUSTOMER = 1;
	public static final String CREATE_CUSTOMER_RESPONSE = "CreateCustomerResponse";

	public static final int DEPOSIT = 2;
	public static final String DEPOSIT_RESPONSE = "DepositResponse";

	public static final int REMIT = 3;
	public static final String REMIT_RESPONSE = "RemitResponse";

	public static final int RESET_PIN = 4;
	public static final String RESET_PIN_RESPONSE = "ChangePinResponse";

	public static final int ACCEPT_EULA = 5;
	public static final String ACCEPT_EULA_RESPONSE = "AcceptEulaResponse";

	public static final String VERSION_CODE = "versionCode";

	public static final String APK_URL = "apkUrl";
	
	public static final String PREFS_NAME = "com.obopay.obopayagent";
	
	public static final String ACCEPTED_EULA_PREF_NAME = "acceptedEULAPrefName";
	public static final String DEVICE_ACTIVATED_PREF_NAME = "deviceActivatedPrefName";
	public static final String PROGRAM_TYPES = "programTypes";

	public static final String SEC_TOKEN = "securityToken";
	public static final String MFA_TOKEN = "token";
	public static final String AGGREGATOR_NUM = "Aggregator";
	public static final String MEMBER_ID_PROPERTY_NAME = "memberId";
	public static final String CUSTOMER_CARE_NUMBER = "helpLineNumber";

	
	public static final String TOTAL = "total";
	public static final String SUCCESS = "success";
	public static final String FAILURE = "failure";
	
	public static final String USER = "user";
	public static final String TABLET_ASSIGNMENT = "tabletAssignment";
	public static final String DAILY_LINE_PLAN = "dailyLinePlan";
	public static final String HOUR_NUMBER = "hourNumber";
	public static final String HOURLY_PRODUCTION = "hourlyProduction";

	public static final String CLOSE_ACTIVITY = "closeActivity";

	public static final String DATE = "date";
	public static final String COUNTRY_CODE = "91";

	public static final int WELCOME_SCREEN_TIME_OUT = 4000;
	public static final String GENDER_TYPES = "genderTypes";
	public static final String REGISTRATION_FEE = "registrationFee";
	public static final String MIN_CASH_IN_AMOUNT = "minCashInAmount";
	public static final String MAX_CASH_IN_AMOUNT = "maxCashInAmount";
	public static final String ID_TYPES = "idTypeList";
	public static final String ID_TYPE_MIN_LENGTH = "idTypeMinLength";
	public static final String ID_TYPE_MAX_LENGTH = "idTypeMaxLength";
	public static final String REGISTRATION_TYPE = "registrationType";
	
	public static Integer parseInt(String data, int defaultValue) {
		try {
			return Integer.parseInt(data);
		} catch (NumberFormatException nfe) {
			return defaultValue;
		}
	}
	
	public static String getDeviceId(Context context) {
		final TelephonyManager tm = (TelephonyManager) context
				.getSystemService(Context.TELEPHONY_SERVICE);

		final String tmDevice, tmSerial, androidId;
//		tmDevice = "" + tm.getDeviceId();
//		tmSerial = "" + tm.getSimSerialNumber();
		androidId = ""
				+ android.provider.Settings.Secure.getString(
						context.getContentResolver(),
						android.provider.Settings.Secure.ANDROID_ID);

//		UUID deviceUuid = new UUID(androidId.hashCode(),
//				((long) tmDevice.hashCode() << 32) | tmSerial.hashCode());
//		UUID deviceUuid = new UUID(0, androidId.hashCode());
//		String deviceId = deviceUuid.toString();
		Log.d(LOG_TAG, "Device Id is:" + androidId);
		return androidId;
	}
	
	public static int getVersionCode(Context context) {
		PackageManager manager = context.getPackageManager();
		PackageInfo info = null;
		try {
			info = manager.getPackageInfo(context.getPackageName(), 0);
		} catch (NameNotFoundException e1) {
			PopUpUtil.error(context, "Failed to load package information");
			e1.printStackTrace();
		}
		final int versionCode = info.versionCode;
		return versionCode;
	}
	
	public static void showNewVersionDownloadDialog(final Activity activity, boolean optional, DialogInterface.OnClickListener okClickListener, 
			DialogInterface.OnClickListener cancelClickListener) {
		final AlertDialog alertDialog = new AlertDialog.Builder(activity).create();
		alertDialog.setButton(DialogInterface.BUTTON_POSITIVE, activity.getString(R.string.install), okClickListener);
		alertDialog.setButton(DialogInterface.BUTTON_NEGATIVE, activity.getString(android.R.string.cancel), cancelClickListener);

		alertDialog.setTitle(R.string.new_version_title);
		if(optional)
			alertDialog.setMessage(activity.getString(R.string.optional_new_version_description));
		else
			alertDialog.setMessage(activity.getString(R.string.new_version_description));
		alertDialog.show();

	}
	
	public static void updateApplication(final Activity activity, String apkurl) {
		final String contentType = "application/vnd.android.package-archive";
		final ProgressDialog dialog = new ProgressDialog(activity);
		dialog.setCancelable(false);
		dialog.setMessage(activity.getString(R.string.please_wait));
		dialog.show();
		OboServices.downloadUpgrade(activity, apkurl, new BinaryHttpResponseHandler(new String[] { contentType }) {
			public void onSuccess(byte[] response) {
				dialog.dismiss();
				String PATH = Environment.getExternalStorageDirectory() + "/download/";
				File file = new File(PATH);
				file.mkdirs();
				File outputFile = new File(file, "app.apk");
				FileOutputStream fos = null;
				try {
					fos = new FileOutputStream(outputFile);
					fos.write(response);
				} catch (FileNotFoundException e) {
					PopUpUtil.error(activity, e.toString());
					e.printStackTrace();
				} catch (IOException e) {
					PopUpUtil.error(activity, e.toString());
					e.printStackTrace();
				} finally {
					if (fos != null)
						try {
							fos.close();
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
				}
				Intent intent = new Intent(Intent.ACTION_VIEW);
				intent.setDataAndType(Uri.fromFile(new File(Environment
						.getExternalStorageDirectory()
						+ "/download/"
						+ "app.apk")), contentType);
				activity.startActivity(intent);
				activity.finish();
			}

			@Override
			public void onFailure(Throwable e, byte[] data) {
				dialog.dismiss();
				PopUpUtil.error(activity, e.toString(), new OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog2, int which) {
						dialog2.dismiss();
						activity.finish();
					}
				});
			}
			
//			public void sendResponseMessage(HttpResponse response) {
//			    System.out.println(response.getHeaders("Content-Type")[0].getValue());
//			}
		});
	}


	
	/**
	 * This method convets dp unit to equivalent device specific value in pixels. 
	 * 
	 * @param dp A value in dp(Device independent pixels) unit. Which we need to convert into pixels
	 * @param context Context to get resources and device specific display metrics
	 * @return A float value to represent Pixels equivalent to dp according to device
	 */
	public static float convertDpToPixel(float dp,Context context){
	    Resources resources = context.getResources();
	    DisplayMetrics metrics = resources.getDisplayMetrics();
	    float px = dp * (metrics.densityDpi/160f);
	    return px;
	}
	/**
	 * This method converts device specific pixels to device independent pixels.
	 * 
	 * @param px A value in px (pixels) unit. Which we need to convert into db
	 * @param context Context to get resources and device specific display metrics
	 * @return A float value to represent db equivalent to px value
	 */
	public static float convertPixelsToDp(float px,Context context){
	    Resources resources = context.getResources();
	    DisplayMetrics metrics = resources.getDisplayMetrics();
	    float dp = px / (metrics.densityDpi / 160f);
	    return dp;

	}

}
