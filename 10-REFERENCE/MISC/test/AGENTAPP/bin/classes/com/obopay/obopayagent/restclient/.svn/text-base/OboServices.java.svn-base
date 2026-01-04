package com.obopay.obopayagent.restclient;

import java.io.IOException;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.util.Calendar;
import java.util.Date;
import java.util.Hashtable;
import java.util.Vector;

import org.apache.http.Header;
import org.apache.http.HttpException;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.message.BasicHeader;

import android.app.ProgressDialog;
import android.content.Context;
import android.util.Log;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.BinaryHttpResponseHandler;
import com.obopay.obopayagent.Config;
import com.obopay.obopayagent.R;
import com.obopay.obopayagent.utils.PreferenceManager;
import com.obopay.obopayagent.utils.StringFormat;
import com.obopay.obopayagent.utils.Utils;

/**
 * Encapsulates requests to Obopay web service server.
 * Asynchronous notification (if a listener is specified) is made via {@link OboServicesListener} methods.<p>
 * <b>Note:</b> Currently, the class supports only one web call at a time, per <code>OboServices</code> object.
 * When you call a public method in an <code>OboServices</code> object, 
 * you must wait until its {@link OboServicesListener} is notified, or call {@link cancel()} before calling another public method.<p>
 * The output objects are <code>Hashtables</code>, which are used as lookup tables for individual variables.
 * This is used instead of custom output classes to reduce the JAR size.
 * (Custom output classes are better design-wise, but would add 1-2K of byte code per class).
 * @author		<a href=mailto:larry@obopay.com>Larry Wang</a>
 */
public class OboServices
{
	
	private static final int TIMEOUT_MILLISECONDS = 60000;

	
	private static final String WEB_SERVICE_PARAM_PHONE_NUMBER				= "phoneNumber";
	private static final String WEB_SERVICE_PARAM_TOKEN						= "token";
	private static final String WEB_SERVICE_PARAM_START						= "start";
	private static final String WEB_SERVICE_PARAM_END						= "end";
	private static final String WEB_SERVICE_PARAM_REFRESH_CACHE				= "refreshCache";

	private static final String WEB_SERVICE_PARAM_PI_CODE					= "piCode";
	private static final String WEB_SERVICE_PARAM_PROGRAM_CODE				= "programCode";
	private static final String WEB_SERVICE_PARAM_THEME_VERSION				= "themeVersion";
	private static final String WEB_SERVICE_PARAM_LOCALE					= "locale";
	private static final String WEB_SERVICE_PARAM_DISPLAY_SIZE				= "displaySize";
	
	private static final String WEB_SERVICE_PARAM_STATUS					= "status";
	private static final String WEB_SERVICE_PARAM_VERSION					= "version";
	private static final String WEB_SERVICE_PARAM_URL						= "url";
	private static final String WEB_SERVICE_PARAM_DETAILS					= "details";

	private static final String WEB_SERVICE_PARAM_VALUE						= "value";

	private static final String WEB_SERVICE_PARAM_COUNT						= "count";
	private static final String WEB_SERVICE_PARAM_SERIES_NUMBER				= "seriesNumber"; 
	private static final String WEB_SERVICE_PARAM_SHORT_DESC				= "shortDesc";
	private static final String WEB_SERVICE_PARAM_DESC						= "desc";
	
	private static final String WEB_SERVICE_PARAM_TEXT						= "text";
	private static final String WEB_SERVICE_PARAM_NAME						= "name";
	private static final String WEB_SERVICE_PARAM_LABEL						= "label";
	private static final String WEB_SERVICE_PARAM_TYPE						= "type";
	private static final String WEB_SERVICE_PARAM_ID						= "id";
		
	private static final String WEB_SERVICE_PARAM_FORMAT_RESP				= "formatResp";
	private static final String WEB_SERVICE_PARAM_FALSE						= "false";
	private static final String WEB_SERVICE_PARAM_TRUE					    = "true";

	/**
	 * Value pass to {@link #getText(String)} to get the Terms & Conditions text.
	 */
	public static final String TEXT_ID_TERMS_AND_CONDITIONS					= "INTWEBSVC";

	/**
	 * Value pass to {@link #getText(String)} to get the privacy policy text.
	 */
	public static final String TEXT_ID_PRIVACY_POLICY						= "PRIVPOLICY";
	
	
	/**
	 * Value pass to {@link #getText(String)} to get the Citi privacy policy text.
	 */
	public static final String TEXT_ID_CITI_PRIVACY_POLICY					= "CITIPRVPOL";
	
	/**
	 * Value pass to {@link #getText(String)} to get the MCMS privacy policy text.
	 */
	public static final String TEXT_ID_MCMS_PRIVACY_POLICY					= "MCMSPRVPOL";
	public static final String TEXT_ID_EULA						= "EULA";

	/**
	 * Value pass to {@link #getText(String)} to get the software license text.
	 */
	public static final String TEXT_ID_SOFTWARE_LICENSE						= "MOBAPPEULA";
	
//#ifdef app.sms
	/**
	 * Indicates J2ME application SMS notification (push registry).
	 */
	public static final String SMS_TYPE_APP									= "App";
//#endif

	/**
	 * Indicates manual SMS notification.
	 */
	public static final String SMS_TYPE_MANUAL								= "Manual";

	/**
	 * The web service URL.
	 */
	private final String mWebSvcUrl;
	
	private AsyncHttpClient mHttp;
	
	private final Context mContext;

	/**
	 * Phone number in the client context/gateway metrics info that's passed into all web service APIs.
	 * cf. package <code>com.ewp.internal.mobile.web.services.data</code> 
	 * cf. <code>com.ewp.internal.mobile.web.services.data.WebSvcClientContext</code> 
	 */
	private final String mPhoneNumber;

	/**
	 * App version in the client context/gateway metrics info that's passed into all web service APIs.
	 * cf. package <code>com.ewp.internal.mobile.web.services.data</code> 
	 * cf. <code>com.ewp.internal.mobile.web.services.data.WebSvcClientContext</code> 
	 */
	private final String mAppVersion;

	/**
	 * App ID in the client context/gateway metrics info that's passed into all web service APIs.
	 * cf. package <code>com.ewp.internal.mobile.web.services.data</code> 
	 * cf. <code>com.ewp.internal.mobile.web.services.data.WebSvcClientContext</code> 
	 */
	private final String mAppID;

	/**
	 * Make-model in the client context/gateway metrics info that's passed into all web service APIs.
	 * cf. package <code>com.ewp.internal.mobile.web.services.data</code> 
	 * cf. <code>com.ewp.internal.mobile.web.services.data.WebSvcClientContext</code> 
	 */
	private final String mMakeModel;

	/**
	 * Carrier name in the client context/gateway metrics info that's passed into all web service APIs.
	 * cf. package <code>com.ewp.internal.mobile.web.services.data</code> 
	 * cf. <code>com.ewp.internal.mobile.web.services.data.WebSvcClientContext</code> 
	 */
	private final String mCarrier;
	
	/**
	 * Listener for success or failure events.
	 */
	private final OboServicesListener mListener;

	/**
	 * The PIN that was passed to a successful call to <code>establishService</code>.
	 */
	private static String mCachedPIN;
	private ProgressDialog dialog;
	
	/**
	 * @param phoneNumber					The mobile phone number.
	 * @param appVersion					App version in the client context/gateway metrics info that's passed into all web service APIs.
	 * @param appID							App ID in the client context/gateway metrics info that's passed into all web service APIs.
	 * @param makeModel						Make-model in the client context/gateway metrics info that's passed into all web service APIs.
	 * @param carrier						Carrier name in the client context/gateway metrics info that's passed into all web service APIs.
	 * @param protocol						The protocol to use; may be "http" or "https".
	 * @param server						The web service server name.
	 * @param listener						the listener for events from <code>OboServices</code>
	 * @throws IllegalArgmentException		If <code>protocol</code> is neither "http" nor "https".
	 */
	public OboServices (
		Context context,	
		OboServicesListener listener)
	{
		mContext = context;
		mPhoneNumber = PreferenceManager.getPhoneNumber(context);
		mAppVersion = "" + Utils.getVersionCode(context);
		mAppID = context.getString(R.string.appId);
//		mMakeModel = Build.MODEL;
		mMakeModel = "";
//		mCarrier = telephonyManager.getNetworkOperatorName();
		mCarrier = "";
		mListener = listener;
		mWebSvcUrl = Config.URL;
	}

	/**
	 * Convenience method to set a param value to or from <code>OboServices</code>.
	 * @param params					the params.
	 * @param							the ID of the data field.
	 * @param data						the param value to set. If <code>data</code> is <code>null</code>,
	 * 									the value is removed from <code>params</code>.
	 */
	public static final void setParam (Hashtable params, int field, Object data)
	{
		if (params != null)
		{
			if (data != null)
			{
				params.put(new Integer(field), data);
			}
			else
			{
				params.remove(new Integer(field));
			}
		}
	}

	/**
	 * Convenience method to remove a param value to or from <code>OboServices</code>.
	 * @param params					the params.
	 * @param							the ID of the data field.
	 */
	public static final void removeParam (Hashtable params, int field)
	{
		if (params != null)
		{
			params.remove(new Integer(field));
		}
	}
	
	/**
	 * Convenience method to get a param value to or from <code>OboServices</code>.
	 * @param params					the params.
	 * @param							the ID of the data field.
	 * @return							the value; <code>null</code> if none is stored in <code>null</code>.
	 */
	public static final Object getParam (Hashtable params, int field)
	{
//#ifdef build.debug
		if (null == params)
		{

		}
//#endif
		return (params != null) ? params.get(new Integer(field)) : null;
	}
	
	/**
	 * Cancels an existing web service call.
	 * If there is no existing web service call, this method does nothing.
	 * @param					<code>true</code> if an HTTP connection was terminated.
	 */
	public boolean cancel ()
	{
		
		if (mHttp != null)
		{
			mHttp.cancelRequests(mContext, true);
			return true;
		}
		
		return false;
	}

	/**
	 * Gets the PIN that was passed to <code>establishService</code>.
	 * @return					The last PIN that was passed to a successful call to 
	 * 							<code>establishService</code>, <code>null</code> if none.
	 */
	public static final String getCachedPIN ()
	{
		return mCachedPIN;
	}

	/**
	 * Clears the cached PIN. If there is no cached PIN, this method does nothing.
	 */
	public static final void clearCachedPIN ()
	{
		mCachedPIN = null;
	}
	
	/**
	 * Formats the POST payload for an Obopay web service request. 
	 * @param methodName				the web service API name.
	 * @param params					the parameters for the web service API; an array of an array of key-value pairs.
	 * @param pin						the PIN. May be <code>null</code> for some APIs.
	 * @param requestID					the idempotence ID; may be <code>null</code>.
	 * @return							a string to be used as the POST payload.
	 */
	private final String formatPayload (
		String methodName,
		Object[][] params,
		String pin,
		String requestID)
	{
		final String PARAM_FORMAT = "<%0>%1</%0>";
		final String PARAM_NIL_FORMAT = "<%0 xsi:nil=\"true\"/>";
		StringBuffer bufParams = new StringBuffer(640);
		// Fill in the parameter values
		if (params != null)
		{
			for (int i = 0; i < params.length; i++)
			{
				Object[] pair = (Object[])params[i];
				String key = (String)pair[0];
				Object obj = pair[1];
				if (obj instanceof String[])
				{
					StringBuffer bufStr = new StringBuffer(128);
					String[] arr = (String[])obj;
					for (int j = 0; j < arr.length; j++)
					{
						final String[] fmtParams = 
						{
							"JavaLangstring",
							xmlEncode(arr[j])
						};

						// Format of an element in an string array in the web service payload.
						bufStr.append(StringFormat.formatString(PARAM_FORMAT, fmtParams));
					}
					
					final String[] fmtParams =
					{
						key,
						bufStr.toString()
					};

					bufParams.append(StringFormat.formatString(PARAM_FORMAT, fmtParams));
				}
				/*
				else if (obj instanceof int[])
				{
					StringBuffer bufStr = new StringBuffer(128);
					int[] arr = (int[])obj;
					for (int j = 0; j < arr.length; j++)
					{
						final String[] fmtParams = 
						{
							"int",
							String.valueOf(arr[j])
						};

						// Format of an element in an string array in the web service payload.
						bufStr.append(StringFormat.formatString(PARAM_FORMAT, fmtParams));
					}
					
					final String[] fmtParams =
					{
						key,
						bufStr.toString()
					};

					bufParams.append(StringFormat.formatString(PARAM_FORMAT, fmtParams));
				}
				*/
				else if (obj instanceof Date)
				{
					final String[] fmtParams =
					{
						key,
						formatISO8601Date((Date)obj)
					};
					
					bufParams.append(StringFormat.formatString(PARAM_FORMAT, fmtParams));
				}
				else if (obj != null)
				{
					final String[] fmtParams =
					{
						key,
						xmlEncode(obj.toString())
					};
					
					bufParams.append(StringFormat.formatString(PARAM_FORMAT, fmtParams));
				}
				else
				{
					// null value
					bufParams.append(StringFormat.formatString(PARAM_NIL_FORMAT, key));
				}
			}
		}
		
		// add the client context/gateway metrics values
		final String[] fmtContextFieldNames =
		{
			"java:idempotenceID",
			"java:phoneNumber",
			"java:pin",
			"java:appVersion",
			"java:appID",
			"java:makeModel",
			"java:carrier"
		};
		final String[] fmtClientFieldValues =
		{
			(requestID != null) ? xmlEncode(requestID) : null,
			mPhoneNumber,
			xmlEncode(pin),
			mAppVersion,
			mAppID,
			mMakeModel,
			mCarrier
		};

		StringBuffer bufClientField = new StringBuffer(128);
		for (int i = 0; i < fmtContextFieldNames.length; i++)
		{
			final String[] clientParams =
			{
				fmtContextFieldNames[i],
				fmtClientFieldValues[i]
			};
			
			bufClientField.append(StringFormat.formatString(
				(fmtClientFieldValues[i] != null) ? PARAM_FORMAT : PARAM_NIL_FORMAT, clientParams));
		};
		
		final String[] fmtClientContext =
		{
			"context",
			bufClientField.toString()
		};
		bufParams.append(StringFormat.formatString(PARAM_FORMAT, fmtClientContext));

		final String[] fmtParams =
		{
			methodName,
			bufParams.toString()
		};

		// Format of a web service payload.
		final String WEB_SERVICE_PAYLOAD_FORMAT =
			"<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\">" +
			"<soapenv:Body>" + 
			"<%0 xmlns=\"http://www.obopay.com\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:java=\"java:com.ewp.webservices.mobile.data\">%1</%0>" +
			"</soapenv:Body>" + 
			"</soapenv:Envelope>";
		return StringFormat.formatString(WEB_SERVICE_PAYLOAD_FORMAT, fmtParams);
	}
	
	public static void downloadUpgrade(Context context, String apkUrl, BinaryHttpResponseHandler responseHandler) {
		AsyncHttpClient asyncHttpClient = new AsyncHttpClient();
		if(!Config.LIVE)
			asyncHttpClient.setSSLSocketFactory(createAllAcceptingSocketFactory());
		asyncHttpClient.setTimeout(TIMEOUT_MILLISECONDS);

		asyncHttpClient.get(apkUrl, responseHandler);
	}


	/**
	 * Calls {@link #checkAppUpdate(String)} asynchronously.
	 * @param params					The input parameters.
	 */
	public final void checkAppUpdate (Hashtable params)
	{
		invokeSoapService(OboServicesConstants.METHOD_CHECK_APP_UPDATE, params);
	}

	/**
	 * Checks whether or not there's a mandatory or recommended update to a client app.
	 * This method calls the methods in {@link OboServicesListener}to notify the status.
	 * If successful, it calls {@link OboServicesListener#onOboServicesResult(int, Object, OboServices)} with 
	 * <code>OboServicesConstants.METHOD_CHECK_APP_UPDATE</code> and a <code>Hashtable</code> that 
	 * contains the app version data.
	 * @param requestID				the idempotence ID.
	 */
	public final void checkAppUpdate (String requestID)
	{
		try
		{
			// Name of web service API to check whether or nor there's is an application update.
			final String SOAP_METHOD_NAME						= "checkAppUpdate";
			
			String strReq = formatPayload(SOAP_METHOD_NAME, null, null, requestID);
			doSoapRequest(SOAP_METHOD_NAME, strReq, new AsyncHttpResponseHandler() {

				@Override
				public void onFailure(Throwable exception, String strResp) {
					Exception e = parseError(exception, strResp);
					if (e != null)
					{
						strResp = null;
						mListener.onOboServicesError(OboServicesConstants.METHOD_CHECK_APP_UPDATE, e, OboServices.this);
					}
				}

				@Override
				public void onSuccess(String strResp) {
					Exception e = parseError(null, strResp);
					if (e != null)
					{
						strResp = null;
						mListener.onOboServicesError(OboServicesConstants.METHOD_CHECK_APP_UPDATE, e, OboServices.this);
					}
					else
					{
						Hashtable result = parseCheckAppUpdateResult(strResp);
						strResp = null;
						mListener.onOboServicesResult(
							OboServicesConstants.METHOD_CHECK_APP_UPDATE, 
							result, 
							OboServices.this);
					}
				}
				
			});
		}
		catch (Exception e)
		{
			handleException(OboServicesConstants.METHOD_CHECK_APP_UPDATE, e);
		}
	}

	/**
	 * Calls {@link #getClientUpdates()} asynchronously.
	 * @param params					The input parameters.
	 */
	public final void getClientUpdates (Hashtable params)
	{
		invokeSoapService(OboServicesConstants.METHOD_GET_CLIENT_UPDATES, params);
	}

	/**
	 * Checks for client update data. This includes:
	 * <ul>
	 * <li>New version of the client app - the method calls {@link OboServicesListener#onOboServicesResult(int, Object, OboServices)} with 
	 * {@link OboServicesConstants#DATA_APP_UPDATE} and a <code>Hashtable</code> that contains the client app update info</li>
	 * <li>New version of theme data - the method calls {@link OboServicesListener#onOboServicesResult(int, Object, OboServices)} with 
	 * {@link OboServicesConstants#DATA_THEME_UPDATE} and a <code>Hashtable</code> that contains the theme update info</li>
	 * </ul> 
	 * @param piCode						The code for the PI used for updating themes
	 * @param programCode					The program associated with the current theme
	 * @param themeVersion					The version of the theme currently used by the client application. 
	 * @param locale						The locale of the theme currently used by the client application.
	 * @param displaySize					The screen size for the current device
	 * @param requestID						the idempotence ID.
	 * @see OboServicesConstants
	 */
	public void getClientUpdates (String piCode, String programCode, String themeVersion, String locale, String displaySize, String requestID)
	{
		try
		{
			Object[][] params =
			{
				{ WEB_SERVICE_PARAM_PI_CODE, piCode },
				{ WEB_SERVICE_PARAM_PROGRAM_CODE, programCode },
				{ WEB_SERVICE_PARAM_THEME_VERSION, themeVersion },
				{ WEB_SERVICE_PARAM_LOCALE, locale },
				{ WEB_SERVICE_PARAM_DISPLAY_SIZE, displaySize }
			};

			// Name of web service API to get user info.
			final String SOAP_METHOD_NAME = "getClientUpdates";
			String strReq = formatPayload(SOAP_METHOD_NAME, params, null, requestID);
			doSoapRequest(SOAP_METHOD_NAME, strReq, new AsyncHttpResponseHandler() {

				@Override
				public void onFailure(Throwable arg0, String strResp) {
					Exception e = parseError(arg0, strResp);
					if (e != null)
					{
						strResp = null;
						mListener.onOboServicesError(OboServicesConstants.METHOD_GET_CLIENT_UPDATES, e, OboServices.this);
					}
				}

				@Override
				public void onSuccess(String strResp) {
					Exception e = parseError(null, strResp);
					if (e != null)
					{
						strResp = null;
						mListener.onOboServicesError(OboServicesConstants.METHOD_GET_CLIENT_UPDATES, e, OboServices.this);
					}
					else
					{
						// Parse the app update result
						String xml = getCData(strResp, "appUpdateResponseData");
						Hashtable result = parseCheckAppUpdateResult(xml);
						// Only call handler if results were present
						if (result.size() >0)
						{
							mListener.onOboServicesResult(
								OboServicesConstants.METHOD_CHECK_APP_UPDATE, 
								result, 
								OboServices.this);
						}

						// Parse the theme update result.
						xml = getCData(strResp, "themeResponseData");
						strResp = null;		// Clear the reference from the scope for GC.
						result = parseCheckThemeUpdateResult(xml);
						mListener.onOboServicesResult(
							OboServicesConstants.DATA_THEME_METADATA, 
							result, 
							OboServices.this);
					}
				}
				
			});
		}
		catch (Exception e)
		{
			handleException(OboServicesConstants.METHOD_GET_CLIENT_UPDATES, e);
		}
	}

	//----------------------------------------------------------------------------------------------------------
	
	public final void resetPin (Hashtable params)
	{
		invokeSoapService(OboServicesConstants.METHOD_RESET, params);
	}
		
	public final void checkAccountStatus (Hashtable params)
	{
		invokeSoapService(OboServicesConstants.METHOD_CHECK_ACCOUNT_STATUS, params);
	}	
	
	public final void activate (Hashtable params)
	{
		invokeSoapService(OboServicesConstants.METHOD_ACTIVATE, params);
	}
	
//	public final void verifyTempPIN (Hashtable params)
//	{
//		startThread(OboServicesConstants.METHOD_VERIFY_TEMP_PIN, params);
//	}

	public final void verifyPIN (Hashtable params)
	{
		invokeSoapService(OboServicesConstants.METHOD_VERIFY_PIN, params);
	}
	
	public final void getBalance (Hashtable params)
	{
		invokeSoapService(OboServicesConstants.METHOD_GET_BALANCE, params);
	}

	public final void getHistory (Hashtable params)
	{
		invokeSoapService(OboServicesConstants.METHOD_GET_HISTORY, params);
	}
	
	public final void newCustomer (Hashtable params)
	{
		invokeSoapService(OboServicesConstants.METHOD_NEW_CUSTOMER, params);
	}
	
	public final void verifyCustomer (Hashtable params)
	{
		invokeSoapService(OboServicesConstants.METHOD_VERIFY_CUSTOMER, params);
	}
	
	public final void validatingTxnID (Hashtable params)
	{
		invokeSoapService(OboServicesConstants.METHOD_VALIDATING_TXN_ID, params);
	}	
	
	public final void remitMoney (Hashtable params)
	{
		invokeSoapService(OboServicesConstants.METHOD_REMIT_MONEY, params);
	}
	//Viral Send method 
	public final void remitMoneyViral (Hashtable params)
	{
		invokeSoapService(OboServicesConstants.METHOD_VIRAL_SEND, params);
	}
	public final void remitMoneyReject (Hashtable params)
	{
		invokeSoapService(OboServicesConstants.METHOD_REMIT_MONEY_REJECT, params);
	}
	
	public final void getUserDetails (Hashtable params)
	{
		invokeSoapService(OboServicesConstants.METHOD_USER_DETAILS, params);
	}
	
	public final void getDepositUserDetails (Hashtable params)
	{
		invokeSoapService(OboServicesConstants.METHOD_DEPOSIT_USER_DETAILS, params);
	}
	
	public final void upgradeCustomer (Hashtable params)
	{
		invokeSoapService(OboServicesConstants.METHOD_UPGRADE_CUSTOMER, params);
	}
	
	public final void depositMoney (Hashtable params)
	{
		invokeSoapService(OboServicesConstants.METHOD_DEPOSIT_MONEY, params);
	}
		
	public final void changePIN (Hashtable params)
	{
		invokeSoapService(OboServicesConstants.METHOD_CHANGE_PIN, params);
	}
	
	public final void changeLanguage (Hashtable params)
	{
		invokeSoapService(OboServicesConstants.METHOD_CHANGE_LANGUAGE, params);
	}
	public final void cardReissue (Hashtable params)
	{
		invokeSoapService(OboServicesConstants.METHOD_CARD_REISSUE, params);
	}
	//----------------------------------------------------------------------------------------------------------------

	public final void doResetPin (
			String requestID,
			String tempPin,
			String newPin,
			final int mServiceType)
		{
			try
			{			
				Object[][] params =
				{	
					{"mobileSecToken", PreferenceManager.getSecurityToken(mContext)},
					{ "tempPin", tempPin },
					{ "newPin", newPin },
					{ WEB_SERVICE_PARAM_FORMAT_RESP, WEB_SERVICE_PARAM_TRUE }					
				};
				
				// Name of web service API to send money.
				final String SOAP_METHOD_NAME				= "resetPinV2";
				String strReq = formatPayload(SOAP_METHOD_NAME, params, tempPin, requestID);
			
				doSoapRequest(SOAP_METHOD_NAME, strReq, new AsyncHttpResponseHandler() {

					@Override
					public void onFailure(Throwable arg0, String strResp) {
						Exception e = parseError(arg0, strResp);
						if (e != null)
						{
							strResp = null;
							mListener.onOboServicesError(mServiceType, e, OboServices.this);
						}
					}

					@Override
					public void onSuccess(String strResp) {
						Exception e = parseError(null, strResp);
						if (e != null)
						{
							strResp = null;
							mListener.onOboServicesError(mServiceType, e, OboServices.this);
						}
						else
						{	
							Hashtable result = parseMetadataResult(strResp);
							PreferenceManager.setPhoneNumber(mContext, getCData(strResp, "encryptedPhoneNumber"));	
							strResp = null;
							mListener.onOboServicesResult(
									mServiceType, 
								result, 
								OboServices.this);
						}
					}
				});
			}
			catch (Exception e)
			{				
				handleException(mServiceType, e);
			}
		}
	

	public final void doActivate (
			String requestID,
			String newpin,
			String dob,
			String motherdob,
			final int mServiceType)
		{
			try
			{	

				Object[][] params =
				{	
					{"mobileSecToken", PreferenceManager.getSecurityToken(mContext)},
					{"newPin",newpin},
					{ "mothersDob", motherdob },
					{ "dob", dob },
					{ WEB_SERVICE_PARAM_FORMAT_RESP, WEB_SERVICE_PARAM_TRUE }	
					
								};
				
				// Name of web service API to send money.
				final String SOAP_METHOD_NAME				= "activateUserV2";
				String strReq = formatPayload(SOAP_METHOD_NAME, params, null, requestID);
			
				doSoapRequest(SOAP_METHOD_NAME, strReq, new AsyncHttpResponseHandler() {

					@Override
					public void onFailure(Throwable arg0, String strResp) {
						Exception e = parseError(arg0, strResp);
						if (e != null)
						{
							
							strResp = null;
							mListener.onOboServicesError(mServiceType, e, OboServices.this);
						}
					}

					@Override
					public void onSuccess(String strResp) {
						Exception e = parseError(null, strResp);
						if (e != null)
						{
							
							strResp = null;
							mListener.onOboServicesError(mServiceType, e, OboServices.this);
						}
						else
						{	
							Hashtable result = parseMetadataResult(strResp);
							PreferenceManager.setPhoneNumber(mContext, getCData(strResp, "encryptedPhoneNumber"));	

							strResp = null;
							mListener.onOboServicesResult(
									mServiceType, 
								result, 
								OboServices.this);
						}
					}
					
				});
				
			}
			catch (Exception e)
			{
				handleException(mServiceType, e);
			}
		}

	public final void checkAccountStatus (
			String requestID,
			final int mServiceType
			)
		{
			try
			{				
				Object[][] params =
				{								
					
					{"mobileSecToken", PreferenceManager.getSecurityToken(mContext)},
					{ WEB_SERVICE_PARAM_FORMAT_RESP, WEB_SERVICE_PARAM_TRUE }	
				};

				// Name of web service API to send money.
				final String SOAP_METHOD_NAME				= "checkAccountStatusV3";
				String strReq = formatPayload(SOAP_METHOD_NAME, params, null, requestID);
				doSoapRequest(SOAP_METHOD_NAME, strReq, new AsyncHttpResponseHandler() {

					@Override
					public void onFailure(Throwable arg0, String strResp) {
						Exception e = parseError(arg0, strResp);
						if (e != null)
						{
							strResp = null;
							mListener.onOboServicesError(mServiceType, e, OboServices.this);
						}
					}

					@Override
					public void onSuccess(String strResp) {
						Exception e = parseError(null, strResp);
						if (e != null)
						{
							strResp = null;
							mListener.onOboServicesError(mServiceType, e, OboServices.this);
						}
						else
						{	
							Hashtable result = parseAccounStatusResult(strResp);
							strResp = null;
							mListener.onOboServicesResult(
									mServiceType, 
								result, 
								OboServices.this);
						}
					}
					
				});
			}
			catch (Exception e)
			{
				handleException(mServiceType, e);
			}
		}
	
	
	public final void doVerifyTempPIN (
			String requestID,
			String pin,
			final int mServiceType)
		{
			try
			{
				Object[][] params =
				{								
					{ WEB_SERVICE_PARAM_FORMAT_RESP, WEB_SERVICE_PARAM_TRUE }					
				};
				
				String SOAP_METHOD_NAME	=	 "validateTempPin";
								
				String strReq = formatPayload(SOAP_METHOD_NAME, params, pin, requestID);
			
				doSoapRequest(SOAP_METHOD_NAME, strReq, new AsyncHttpResponseHandler() {

					@Override
					public void onFailure(Throwable arg0, String strResp) {
						Exception e = parseError(arg0, strResp);
						if (e != null)
						{
							
							strResp = null;
							mListener.onOboServicesError(mServiceType, e, OboServices.this);
						}
					}

					@Override
					public void onSuccess(String strResp) {
						Exception e = parseError(null, strResp);
						if (e != null)
						{
							
							strResp = null;
							mListener.onOboServicesError(mServiceType, e, OboServices.this);
						}
						else
						{	
							Hashtable result = parseMetadataResult(strResp);
							strResp = null;
							mListener.onOboServicesResult(
									mServiceType, 
								result, 
								OboServices.this);
						}
					}
					
				});
			}
			catch (Exception e)
			{
				handleException(mServiceType, e);
			}
		}

	public final void doVerifyPIN (
			String requestID,
			String pin,
			final int mServiceType)
		{
			try
			{
				Object[][] params =
				{		
						{"mobileSecToken",PreferenceManager.getSecurityToken(mContext)},
					{ WEB_SERVICE_PARAM_FORMAT_RESP, WEB_SERVICE_PARAM_TRUE }
				
					
				};
				
				String SOAP_METHOD_NAME	= "agentLoginV2";				
				String strReq = formatPayload(SOAP_METHOD_NAME, params, pin, requestID);
			
				doSoapRequest(SOAP_METHOD_NAME, strReq, new AsyncHttpResponseHandler() {

					@Override
					public void onFailure(Throwable arg0, String strResp) {
						Exception e = parseError(arg0, strResp);
						if (e != null)
						{
							
							strResp = null;
							mListener.onOboServicesError(mServiceType, e, OboServices.this);
						}
					}

					@Override
					public void onSuccess(String strResp) {
						Exception e = parseError(null, strResp);
						if (e != null)
						{
							
							strResp = null;
							mListener.onOboServicesError(mServiceType, e, OboServices.this);
						}
						else
						{	
							Hashtable result = parseMetadataResult(strResp);
							PreferenceManager.setPhoneNumber(mContext, getCData(strResp, "encryptedPhoneNumber"));	
							strResp = null;
							mListener.onOboServicesResult(
									mServiceType, 
								result, 
								OboServices.this);
						}
					}
					
				});
			}
			catch (Exception e)
			{
				handleException(mServiceType, e);
			}
		}
	
	public final void doChangeLanguage (String language,String requestID,final int mServiceType)
	{
		try
		{									
				Object[][] params =
				{	
					{"language",language},
					{ WEB_SERVICE_PARAM_FORMAT_RESP, WEB_SERVICE_PARAM_TRUE }
				};				
				
				final String SOAP_METHOD_NAME					= "setLanguageForAgent";
				String strReq = formatPayload(SOAP_METHOD_NAME, params, null, requestID);
			
				doSoapRequest(SOAP_METHOD_NAME, strReq, new AsyncHttpResponseHandler() {

					@Override
					public void onFailure(Throwable arg0, String strResp) {
						Exception e = parseError(arg0, strResp);
						if (e != null)
						{
							strResp = null;
							mListener.onOboServicesError(mServiceType, e, OboServices.this);
						}
					}

					@Override
					public void onSuccess(String strResp) {
						Exception e = parseError(null, strResp);
						if (e != null)
						{
							strResp = null;
							mListener.onOboServicesError(mServiceType, e, OboServices.this);
						}
						else
						{
							Hashtable result = parseMetadataResult(strResp);
							strResp = null;
							mListener.onOboServicesResult(
									mServiceType, 
								result, 
								OboServices.this);
						}
					}
					
				});
			}
			catch (Exception e)
			{
				handleException(mServiceType, e);
			}
	}	

		
	public final void doChangePIN (String pin, String newPin,String requestID,final int mServiceType)
	{
		try
		{
									
			Object[][] params =
			{	
				{"oldPin",pin},
				{ "newPin", newPin },
				{ WEB_SERVICE_PARAM_FORMAT_RESP, WEB_SERVICE_PARAM_TRUE }
			};
			
			
			final String SOAP_METHOD_NAME					= "changePinForAgent";
			String strReq = formatPayload(SOAP_METHOD_NAME, params, pin, requestID);
		
			doSoapRequest(SOAP_METHOD_NAME, strReq, new AsyncHttpResponseHandler() {

				@Override
				public void onFailure(Throwable arg0, String strResp) {
					Exception e = parseError(arg0, strResp);
					if (e != null)
					{
						strResp = null;
						mListener.onOboServicesError(mServiceType, e, OboServices.this);
					}
				}

				@Override
				public void onSuccess(String strResp) {
					Exception e = parseError(null, strResp);
					if (e != null)
					{
						strResp = null;
						mListener.onOboServicesError(mServiceType, e, OboServices.this);
					}
					else
					{
						Hashtable result = parseMetadataResult(strResp);
						strResp = null;
						mListener.onOboServicesResult(
								mServiceType, 
							result, 
							OboServices.this);
					}
				}
			});
		}
		catch (Exception e)
		{
			handleException(mServiceType, e);
		}
	}	

	
	private void createNewCustomer(
			String requestID,
			String custMobile,
			String cardNum,
			Integer progName,
			String pin,
			String formNum,
			String firstName,
			String lastName,
			String dob,
			String lang,
			String gender,
			Integer idType,
			String idNumber,
			Integer registrationType,
			Integer cashInAmount,
			String atmCardRefNo,
			final int mServiceType
			)
	{
		try
		{		
			
			Object[][] params =
			{	
				{"customerMobileNumber",custMobile},
				{"programName",progName},
				{"formNo",formNum},
				{"firstName",firstName},
				{"lastName",lastName},
				{"birthdate",dob},
				{"gender",gender},
				{"idType",idType},
				{"idValue",idNumber},
				{"customerLangPref",lang},
				{"cardNumber",atmCardRefNo},
				{"registrationType",registrationType},
				{"cashInAmt",cashInAmount},
				{ WEB_SERVICE_PARAM_FORMAT_RESP, WEB_SERVICE_PARAM_TRUE }
			};

			final String SOAP_METHOD_NAME					= "enrollUserByProgramWithCashCard";
			String strReq = formatPayload(SOAP_METHOD_NAME, params, pin, requestID);
		
			doSoapRequest(SOAP_METHOD_NAME, strReq, new AsyncHttpResponseHandler() {

				@Override
				public void onFailure(Throwable arg0, String strResp) {
					Exception e = parseError(arg0, strResp);
					if (e != null)
					{
						strResp = null;
						mListener.onOboServicesError(mServiceType, e, OboServices.this);
					}
				}

				@Override
				public void onSuccess(String strResp) {
					Exception e = parseError(null, strResp);
					if (e != null)
					{
						strResp = null;
						mListener.onOboServicesError(mServiceType, e, OboServices.this);
					}
					else
					{
						Hashtable result = parseMetadataResult(strResp);
						strResp = null;
						mListener.onOboServicesResult(
								mServiceType, 
							result, 
							OboServices.this);
					}
				}
				
			});
		}
		catch (Exception e)
		{
			handleException(mServiceType, e);
		}
	}
	
	
	private void getUserDetails(
			String requestID,
			String custMobile,
			String pin,
			final int mServiceType
			)
	{
		try
		{	
			Object[][] params =
			{	
				{"customerMobileNumber",custMobile},								
				{ WEB_SERVICE_PARAM_FORMAT_RESP, WEB_SERVICE_PARAM_TRUE }
			};

			final String SOAP_METHOD_NAME					= "getUserDetails";
			String strReq = formatPayload(SOAP_METHOD_NAME, params,pin, requestID);
	
			doSoapRequest(SOAP_METHOD_NAME, strReq, new AsyncHttpResponseHandler() {

				@Override
				public void onFailure(Throwable arg0, String strResp) {
					Exception e = parseError(arg0, strResp);
					if (e != null)
					{
						strResp = null;
						mListener.onOboServicesError(mServiceType, e, OboServices.this);
					}
				}

				@Override
				public void onSuccess(String strResp) {
					Exception e = parseError(null, strResp);
					if (e != null)
					{
						strResp = null;
						mListener.onOboServicesError(mServiceType, e, OboServices.this);
					}
					else
					{
						Hashtable result = parseResult(strResp);
					
						strResp = null;
						mListener.onOboServicesResult(
								mServiceType, 
							result, 
							OboServices.this);
					}
				}
				
			});
		}
		catch (Exception e)
		{
			handleException(mServiceType, e);
		}
	}
	
	 private void depositMoney(
				String requestID,				
				String custMobile,				
				String pin,
				String amt,
				final int mServiceType
				)
		{
			try
			{	
			
				
				Object[][] params =
				{	
					{"targetDeviceNumber",custMobile},
					{"amount",amt},			
					{ WEB_SERVICE_PARAM_FORMAT_RESP, WEB_SERVICE_PARAM_TRUE }
				};

				final String SOAP_METHOD_NAME					= "cashIn";
				String strReq = formatPayload(SOAP_METHOD_NAME, params, pin, requestID);
		
				doSoapRequest(SOAP_METHOD_NAME, strReq, new AsyncHttpResponseHandler() {

					@Override
					public void onFailure(Throwable arg0, String strResp) {
						Exception e = parseError(arg0, strResp);
						if (e != null)
						{
							strResp = null;
							mListener.onOboServicesError(mServiceType, e, OboServices.this);
						}
					}

					@Override
					public void onSuccess(String strResp) {
						Exception e = parseError(null, strResp);
						if (e != null)
						{
							strResp = null;
							mListener.onOboServicesError(mServiceType, e, OboServices.this);
						}
						else
						{
							Hashtable result = parseMetadataResult(strResp);
							strResp = null;
							mListener.onOboServicesResult(
									mServiceType, 
								result, 
								OboServices.this);
						}
					}
					
				});
			}
			catch (Exception e)
			{
				handleException(mServiceType, e);
			}
		}
	 	 
	 private void verifyCustomer(
				String requestID,				
				String custMobile,
				String dob,
				String pin,
				final int mServiceType
				)
		{
			try
			{	
			
				
				Object[][] params =
				{	
					{"customerMobileNumber",custMobile},
					{"customerDOB",dob},			
					{ WEB_SERVICE_PARAM_FORMAT_RESP, WEB_SERVICE_PARAM_TRUE }
				};

				final String SOAP_METHOD_NAME					= "verifyCustomer";
				String strReq = formatPayload(SOAP_METHOD_NAME, params, pin, requestID);
				
				doSoapRequest(SOAP_METHOD_NAME, strReq, new AsyncHttpResponseHandler() {

					@Override
					public void onFailure(Throwable arg0, String strResp) {
						Exception e = parseError(arg0, strResp);
						if (e != null)
						{
							strResp = null;
							mListener.onOboServicesError(mServiceType, e, OboServices.this);
						}
					}

					@Override
					public void onSuccess(String strResp) {
						Exception e = parseError(null, strResp);
						if (e != null)
						{
							strResp = null;
							mListener.onOboServicesError(mServiceType, e, OboServices.this);
						}
						else
						{
							Hashtable result = parseResult(strResp);
							strResp = null;
							mListener.onOboServicesResult(
									mServiceType, 
								result, 
								OboServices.this);
						}
					}
					
				});
			}
			catch (Exception e)
			{
				handleException(mServiceType, e);
			}
		}

	 private void validateTxnID(
				String requestID,				
				String custMobile,
				String transNumber,
				String pin,
				final int mServiceType
				)
		{
			try
			{	
			
				
				Object[][] params =
				{	
					{"customerMobileNumber",custMobile},
					{"transNumber",transNumber},			
					{ WEB_SERVICE_PARAM_FORMAT_RESP, WEB_SERVICE_PARAM_TRUE }
				};

				final String SOAP_METHOD_NAME					= "validateTrasnactionNumber";
				String strReq = formatPayload(SOAP_METHOD_NAME, params, pin, requestID);
				
				doSoapRequest(SOAP_METHOD_NAME, strReq, new AsyncHttpResponseHandler() {

					@Override
					public void onFailure(Throwable arg0, String strResp) {
						Exception e = parseError(arg0, strResp);
						if (e != null)
						{
							strResp = null;
							mListener.onOboServicesError(mServiceType, e, OboServices.this);
						}
					}

					@Override
					public void onSuccess(String strResp) {
						Exception e = parseError(null, strResp);
						if (e != null)
						{
							strResp = null;
							mListener.onOboServicesError(mServiceType, e, OboServices.this);
						}
						else
						{
							Hashtable result = parseMetadataResult(strResp);
							
							strResp = null;
							mListener.onOboServicesResult(
									mServiceType, 
								result, 
								OboServices.this);
						}
					}
					
				});
			}
			catch (Exception e)
			{
				handleException(mServiceType, e);
			}
		}	

	 private void remitMoney(
				String requestID,	
				String custMobile,
				String transNumber,
				String otp,
				String pin,
				final int mServiceType
				)
		{
			try
			{	
			
				
				Object[][] params =
				{	
					{"customerMobileNumber",custMobile},
					{"transNumber",transNumber},
					{"otp",otp},			
					{ WEB_SERVICE_PARAM_FORMAT_RESP, WEB_SERVICE_PARAM_TRUE }
				};

				final String SOAP_METHOD_NAME					= "processOTPTrasnaction";
				String strReq = formatPayload(SOAP_METHOD_NAME, params, pin, requestID);
				
				doSoapRequest(SOAP_METHOD_NAME, strReq, new AsyncHttpResponseHandler() {

					@Override
					public void onFailure(Throwable arg0, String strResp) {
						Exception e = parseError(arg0, strResp);
						if (e != null)
						{
							strResp = null;
							mListener.onOboServicesError(mServiceType, e, OboServices.this);
						}
					}

					@Override
					public void onSuccess(String strResp) {
						Exception e = parseError(null, strResp);
						if (e != null)
						{
							strResp = null;
							mListener.onOboServicesError(mServiceType, e, OboServices.this);
						}
						else
						{
							Hashtable result = parseMetadataResult(strResp);
							strResp = null;
							mListener.onOboServicesResult(
									mServiceType, 
								result, 
								OboServices.this);
						}
					}
					
				});
			}
			catch (Exception e)
			{
				handleException(mServiceType, e);
			}
		}
	 
	// remitMoneyViral(requestID,firstName,lastName,idType1,idValue1,idType2,idValue2,custMobile,txnID,otp,pin,mServiceType);
		//remitMoney(requestID,custMobile,txnID,otp,pin,mServiceType);
	 
	 private void remitMoneyViral(
				String requestID,
				String firstName,
				String lastName,
				String custMobile,
				String idType1,
				String idValue1,
				String idType2,
				String idValue2,
				String transNumber,
				String otp,
				String pin,
				final int mServiceType
				)
		{
			try
		{	
			
//	        <java:firstName>fsdfdsf</java:firstName>
//	        <java:lastName>sdfdsfdsf</java:lastName>
//	        <java:customerMobileNumber>9535246933</java:customerMobileNumber>
//	        <java:idType1>dfgfdg</java:idType1>
//	        <java:idValue1>dfgfdgfdg</java:idValue1>
//	        <java:idType2>dfgfdg</java:idType2>
//	        <java:idValue2>dfgfdgfdg</java:idValue2>
//	        <java:txnNumber>1216</java:txnNumber>
//	        <java:OTP>885974</java:OTP>


			
				
				Object[][] params =
				{	
//						{"firstName","fsdfdsf"},
//						{"lastName","sdfdsfdsf"},
//						{"customerMobileNumber",custMobile},
//						{"idType1","idType1"},
//						{"idValue1","idValue1"},
//						{"idType2","dfgfdg"},
//						{"idValue2","idValue2"},
//						{"txnNumber",transNumber},
//						{"OTP",otp},
						
						{"firstName",firstName},
						{"lastName",lastName},
						{"customerMobileNumber",custMobile},
						{"idType1",idType1},
						{"idValue1",idValue1},
						{"idType2",idType2},
						{"idValue2",idValue2},
						{"transNumber",transNumber},
						{"OTP",otp},			
					{ WEB_SERVICE_PARAM_FORMAT_RESP, WEB_SERVICE_PARAM_TRUE }
				};

				final String SOAP_METHOD_NAME					= "processVPICKTrasnaction";
				String strReq = formatPayload(SOAP_METHOD_NAME, params, pin, requestID);
				
				doSoapRequest(SOAP_METHOD_NAME, strReq, new AsyncHttpResponseHandler() {

					@Override
					public void onFailure(Throwable arg0, String strResp) {
						Exception e = parseError(arg0, strResp);
						if (e != null)
						{
							strResp = null;
							mListener.onOboServicesError(mServiceType, e, OboServices.this);
						}
					}

					@Override
					public void onSuccess(String strResp) {
						Exception e = parseError(null, strResp);
						if (e != null)
						{
							strResp = null;
							mListener.onOboServicesError(mServiceType, e, OboServices.this);
						}
						else
						{
							Hashtable result = parseMetadataResult(strResp);
							strResp = null;
							mListener.onOboServicesResult(
									mServiceType, 
								result, 
								OboServices.this);
						}
					}
					
				});
			}
			catch (Exception e)
			{
				handleException(mServiceType, e);
			}
		}
	 
	 private void rejectRemitMoney(
				String requestID,	
				String custMobile,
				String transNumber,
				String otp,
				String pin,
				final int mServiceType
				)
		{
			try
			{	
			
				
				Object[][] params =
				{	
					{"customerMobileNumber",custMobile},
					{"transNumber",transNumber},
					{"otp",otp},			
					{ WEB_SERVICE_PARAM_FORMAT_RESP, WEB_SERVICE_PARAM_TRUE }
				};

				final String SOAP_METHOD_NAME					= "rejectOTPTxn";
				String strReq = formatPayload(SOAP_METHOD_NAME, params, pin, requestID);
				
				doSoapRequest(SOAP_METHOD_NAME, strReq, new AsyncHttpResponseHandler() {

					@Override
					public void onFailure(Throwable arg0, String strResp) {
						Exception e = parseError(arg0, strResp);
						if (e != null)
						{
							strResp = null;
							mListener.onOboServicesError(mServiceType, e, OboServices.this);
						}
					}

					@Override
					public void onSuccess(String strResp) {
						Exception e = parseError(null, strResp);
						if (e != null)
						{
							strResp = null;
							mListener.onOboServicesError(mServiceType, e, OboServices.this);
						}
						else
						{
							Hashtable result = parseMetadataResult(strResp);
							strResp = null;
							mListener.onOboServicesResult(
									mServiceType, 
								result, 
								OboServices.this);
						}
					}
					
				});
			}
			catch (Exception e)
			{
				handleException(mServiceType, e);
			}
		}
	 
	 
	 private void cardReissue(
				String requestID,				
				String custMobile,
				String cardNumber,
				String pin,
				final int mServiceType
				)
		{
			try
			{	
			
				
				Object[][] params =
				{	
					{"customerMobileNumber",custMobile},
					{"newCardNumber",cardNumber},			
					{ WEB_SERVICE_PARAM_FORMAT_RESP, WEB_SERVICE_PARAM_TRUE }
				};

				final String SOAP_METHOD_NAME					= "issueCard";
				String strReq = formatPayload(SOAP_METHOD_NAME, params, pin, requestID);
				
				doSoapRequest(SOAP_METHOD_NAME, strReq, new AsyncHttpResponseHandler() {

					@Override
					public void onFailure(Throwable arg0, String strResp) {
						Exception e = parseError(arg0, strResp);
						if (e != null)
						{
							strResp = null;
							mListener.onOboServicesError(mServiceType, e, OboServices.this);
						}
					}

					@Override
					public void onSuccess(String strResp) {
						Exception e = parseError(null, strResp);
						if (e != null)
						{
							strResp = null;
							mListener.onOboServicesError(mServiceType, e, OboServices.this);
						}
						else
						{
							Hashtable result = parseMetadataResult(strResp);
							
							strResp = null;
							mListener.onOboServicesResult(
									mServiceType, 
								result, 
								OboServices.this);
						}
					}
					
				});
			}
			catch (Exception e)
			{
				handleException(mServiceType, e);
			}
		}	

	 
	
	private void upgradeCustomer(
			String requestID,
			String custMobile,
			String cardNum,
			String progName,
			String pin,
			String formNum,
			final int mServiceType
			)
	{
		try
		{	
		
			
			Object[][] params =
			{	
				{"customerMobileNumber",custMobile},
				{"programName",progName},
				{"formNumber",formNum},
				{"cardNumber",cardNum},
				{ WEB_SERVICE_PARAM_FORMAT_RESP, WEB_SERVICE_PARAM_TRUE }
			};

			final String SOAP_METHOD_NAME					= "upgradeCustomerWithCard";
			String strReq = formatPayload(SOAP_METHOD_NAME, params, pin, requestID);
		
			doSoapRequest(SOAP_METHOD_NAME, strReq, new AsyncHttpResponseHandler() {

				@Override
				public void onFailure(Throwable arg0, String strResp) {
					Exception e = parseError(arg0, strResp);
					if (e != null)
					{
						strResp = null;
						mListener.onOboServicesError(mServiceType, e, OboServices.this);
					}
				}

				@Override
				public void onSuccess(String strResp) {
					Exception e = parseError(null, strResp);
					if (e != null)
					{
						strResp = null;
						mListener.onOboServicesError(mServiceType, e, OboServices.this);
					}
					else
					{
						Hashtable result = parseMetadataResult(strResp);
						strResp = null;
						mListener.onOboServicesResult(
								mServiceType, 
							result, 
							OboServices.this);
					}
				}
				
			});
		}
		catch (Exception e)
		{
			handleException(mServiceType, e);
		}
	}
	
	
	/**
	 * Gets the account balance.
	 * This method calls the methods in {@link OboServicesListener}to notify the status.
	 * If successful, it calls {@link OboServicesListener#onOboServicesResult(int, Object, OboServices)} with 
	 * <code>OboServicesConstants.METHOD_GET_BALANCE</code> and a <code>Hashtable</code> that 
	 * contains the balance information.
	 * @param pin						The user's PIN.
	 * @param requestID					the idempotence ID.
	 * @see OboServicesConstants
	 */
	public final void getBalance (String pin, String requestID)
	{
		try
		{
			Object[][] params =
			{
				{ WEB_SERVICE_PARAM_FORMAT_RESP, "true" }
			};

			// Name of web service API to get the balance.
			// The new getBalanceSponsored API returns the account's balance,
			// plus the balance of the sponsored accounts, if any.
			final String SOAP_METHOD_NAME				= "getAgentBalance";
			String strReq = formatPayload(SOAP_METHOD_NAME, params, pin, requestID);
			doSoapRequest(SOAP_METHOD_NAME, strReq, new AsyncHttpResponseHandler() {

				@Override
				public void onFailure(Throwable arg0, String strResp) {
					Exception e = parseError(arg0, strResp);
					if (e != null)
					{
						strResp = null;
						mListener.onOboServicesError(OboServicesConstants.METHOD_GET_BALANCE, e, OboServices.this);
					}
				}

				@Override
				public void onSuccess(String strResp) {
					Exception e = parseError(null, strResp);
					if (e != null)
					{
						strResp = null;
						mListener.onOboServicesError(OboServicesConstants.METHOD_GET_BALANCE, e, OboServices.this);
					}
					else
					{
						Hashtable result = parseMetadataResult(strResp);
						strResp = null;
						mListener.onOboServicesResult(
							OboServicesConstants.METHOD_GET_BALANCE, 
							result, 
							OboServices.this);
					}
				}
				
			});
		}
		catch (Exception e)
		{
			handleException(OboServicesConstants.METHOD_GET_BALANCE, e);
		}
	}
	
		
	/**
	 * Gets the account history.
	 * This method calls the methods in {@link OboServicesListener}to notify the status.
	 * If successful, it calls {@link OboServicesListener#onOboServicesResult(int, Object, OboServices)} with 
	 * <code>OboServicesConstants.METHOD_GET_HISTORY</code> and a <code>Vector</code> of <code>Hashtables</code> that 
	 * contains the history information.
	 * @param pin					The user's PIN.
	 * @param start					Index of the first history item to get. Must be greater than or equal to <code>1</code>.
	 * @param end					Index of the last history item to get. Must be greater than or equal to <code>start</code>.
	 * 								The total number of history items may be less than <code>end - start</code>.
	 * @param refreshCache			If <code>true</code>, requests the backend to get a fresh copy of the history items
	 * 								(instead of from its cache).  
	 * @param requestID				the idempotence ID.
	 * @see OboServicesConstants
	 */
	public final void getHistory (
		String pin,
		int start,
		int end,
		boolean refreshCache,
		String requestID)
	{
		try
		{
			Object[][] params = 
			{
				{ WEB_SERVICE_PARAM_START, String.valueOf(start) },
				{ WEB_SERVICE_PARAM_END, String.valueOf(end) },
				{ WEB_SERVICE_PARAM_REFRESH_CACHE, String.valueOf(refreshCache) },
				{ WEB_SERVICE_PARAM_FORMAT_RESP, WEB_SERVICE_PARAM_FALSE }
			};

			// Name of web service API to get the history.
			final String SOAP_METHOD_NAME								= "getHistoryForAgent";
			String strReq = formatPayload(SOAP_METHOD_NAME, params, pin, requestID);
			
			doSoapRequest(SOAP_METHOD_NAME, strReq, new AsyncHttpResponseHandler() {

				@Override
				public void onFailure(Throwable arg0, String strResp) {
					Exception e = parseError(arg0, strResp);
					if (e != null)
					{
						strResp = null;
						mListener.onOboServicesError(OboServicesConstants.METHOD_GET_HISTORY, e, OboServices.this);
					}
				}

				@Override
				public void onSuccess(String strResp) {
					Exception e = parseError(null, strResp);
					if (e != null)
					{
						strResp = null;
						mListener.onOboServicesError(OboServicesConstants.METHOD_GET_HISTORY, e, OboServices.this);
					}
					else
					{
						Vector result = parseHistoryResult(strResp);
						strResp = null;
						mListener.onOboServicesResult(
							OboServicesConstants.METHOD_GET_HISTORY, 
							result, 
							OboServices.this);
					}
				}
			});
		}
		catch (Exception e)
		{
			handleException(OboServicesConstants.METHOD_GET_HISTORY, e);
		}
	}
	//----------------------------------------------MDN----------------------------------------------
	

	/**
	 * Calls {@link #requestClientToken(int, String, int, String)} asynchronously.
	 * @param params					The input parameters.
	 */
	public final void requestClientToken (Hashtable params)
	{
		invokeSoapService(OboServicesConstants.METHOD_REQUEST_TOKEN, params);
	}
	

	public final void requestClientToken (
		String requestID)
	{
		try
		{
			// Name of web service API to establish service.
			final String SOAP_METHOD_NAME					= "getMobileToken";
			String strReq = formatPayload(SOAP_METHOD_NAME, null, null, requestID);
			doSoapRequest(SOAP_METHOD_NAME, strReq, new AsyncHttpResponseHandler() {

				@Override
				public void onFailure(Throwable arg0, String strResp) {
					Exception e = parseError(arg0, strResp);
					if (e != null)
					{
						strResp = null;
						mListener.onOboServicesError(OboServicesConstants.METHOD_REQUEST_TOKEN, e, OboServices.this);
					}
				}

				@Override
				public void onSuccess(String strResp) {
					Exception e = parseError(null, strResp);
					if (e != null)
					{
						strResp = null;
						mListener.onOboServicesError(OboServicesConstants.METHOD_REQUEST_TOKEN, e, OboServices.this);
					}
					else
					{
						String token = getCData(strResp, "mobileToken");
						String shortCode = getCData(strResp, "smsShortCode");
						if(shortCode != null && shortCode.length() > 0)
						{						
							PreferenceManager.setAggregatorNumber(mContext, shortCode);
						}
						
						if(token == null || token.length() == 0) {
							Exception e1 = new Exception("Unable to verify mobile number. Please try again after some time.");
							mListener.onOboServicesError(OboServicesConstants.METHOD_REQUEST_TOKEN, e1, OboServices.this);
						}
						strResp = null;
						mListener.onOboServicesResult(
							OboServicesConstants.METHOD_REQUEST_TOKEN, 
							token,
							OboServices.this);
					}
				}
				
			});
		}
		catch (Exception e)
		{
			handleException(OboServicesConstants.METHOD_REQUEST_TOKEN, e);
		}
	}

	/**
	 * Calls {@link #pollForTokenValidation(String, int, String, int)} asynchronously.
	 * @param params					The input parameters.
	 */
	public final void pollForTokenValidation (Hashtable params)
	{
		invokeSoapService(OboServicesConstants.METHOD_POLL_TOKEN_VALIDATION, params);
	}
	
	/**
	 * Polls for validating the token received through requestClientToken
	 * This method calls the methods in {@link OboServicesListener}to notify the status.
	 * If successful, it calls {@link OboServicesListener#onOboServicesResult(int, Object, OboServices)} with 
	 * <code>OboServicesConstants.METHOD_POLL_TOKEN_VALIDATION</code> and <code>null</code> data.
	 * @param requestID				the idempotence ID
	 * @see OboServicesConstants
	 */
	public final void pollForTokenValidation (
			String requestID
			
			)
	{
		try
		{
			
			Object[][] params = 
			{
				{ WEB_SERVICE_PARAM_TOKEN, PreferenceManager.getMFAToken(mContext) }
			};
			
			// Name of web service API to establish service.
			final String SOAP_METHOD_NAME					= "validateMobileTokenV2";
			String strReq = formatPayload(SOAP_METHOD_NAME, params, null, requestID);
			doSoapRequest(SOAP_METHOD_NAME, strReq, new AsyncHttpResponseHandler() {

				@Override
				public void onFailure(Throwable arg0, String strResp) {
					Exception e = parseError(arg0, strResp);
					if (e != null)
					{
						strResp = null;
						mListener.onOboServicesError(OboServicesConstants.METHOD_POLL_TOKEN_VALIDATION, e, OboServices.this);
					}
				}

				@Override
				public void onSuccess(String strResp) {
					Exception e = parseError(null, strResp);
					if (e != null)
					{
						strResp = null;
						mListener.onOboServicesError(OboServicesConstants.METHOD_POLL_TOKEN_VALIDATION, e, OboServices.this);
					}
					else
					{
						String secToken = getCData(strResp, "securityToken");
						strResp = null;
						mListener.onOboServicesResult(
							OboServicesConstants.METHOD_POLL_TOKEN_VALIDATION, 
							secToken, 
							OboServices.this);
					}
				}
				
			});
		}
		catch (Exception e)
		{
			handleException(OboServicesConstants.METHOD_POLL_TOKEN_VALIDATION, e);
		}
	}
	

//	----------------------------------------------------------------------------------------------------------------
		/**
	 * Calls {@link #getText(String)} asynchronously.
	 * @param params					The input parameters.
	 */
	public final void getText (Hashtable params)
	{
		invokeSoapService(OboServicesConstants.METHOD_GET_TEXT, params);
	}

	/**
	 * Gets the text of the Terms & Conditions.
	 * This method calls the methods in {@link OboServicesListener}to notify the status.
	 * If successful, it calls {@link OboServicesListener#onOboServicesResult(int, Object, OboServices)} with 
	 * <code>OboServicesConstants.METHOD_GET_TEXT</code> and a <code>String</code> that 
	 * contains the text.
	 * @param id					ID of the text to get. Currently supported:
	 * 								<ul>
	 * 								<li>{@link #TEXT_ID_TERMS_AND_CONDITIONS} - the terms and conditions text. The text should be displayed and accepted by the user
	 * 									before calling {@link #enroll(String, String, Date, String, String, String, boolean, MobileClientContext)}.</li>
	 * 								<li>{@link #TEXT_ID_PRIVACY_POLICY} - privacy policy.</li>
	 * 								<li>{@link #TEXT_ID_SOFTWARE_LICENSE} - client app software license.</li>
	 * 								</ul>
	 * @param requestID				the idempotence ID.
	 * @see OboServicesConstants
	 */
	public final void getText (String id, String code, String requestID)
	{
		try
		{
			// See if we have text stored locally. If so, use it instead of
			// doing service call
			if(id != null) {
				String resourceFile = "/" + id + ".txt";
// TODO Fix this 				
//				String text = MoneyAgent.mStringMgr.getStringFromResourceFile(resourceFile, true);
//				if (text != null)
//				{
//					mListener.onOboServicesResult(
//							OboServicesConstants.METHOD_GET_TEXT, 
//							resourceFile,
//							this);
//					return;
//				}
			}
			
			Object[][] params = 
			{
				{ WEB_SERVICE_PARAM_ID, id }
			};
			
			// Name of web service API to create a new user account.
			final String SOAP_METHOD_NAME					= "getTCText";
			String strReq = formatPayload(SOAP_METHOD_NAME, params, null, requestID);
			doSoapRequest(SOAP_METHOD_NAME, strReq, new AsyncHttpResponseHandler() {

				@Override
				public void onFailure(Throwable arg0, String arg1) {
					// TODO Auto-generated method stub
					super.onFailure(arg0, arg1);
				}

				@Override
				public void onSuccess(String strResp) {
					Exception e = parseError(null, strResp);
					if (e != null)
					{
						strResp = null;
						mListener.onOboServicesError(OboServicesConstants.METHOD_GET_TEXT, e, OboServices.this);
					}
					else
						
					{
						String text = getCData(strResp, WEB_SERVICE_PARAM_TEXT);
						strResp = null;
						if (text != null)
						{
							mListener.onOboServicesResult(
								OboServicesConstants.METHOD_GET_TEXT, 
								text, 
								OboServices.this);
						}
						else
						{
							mListener.onOboServicesError(OboServicesConstants.METHOD_GET_TEXT, new Exception(strResp), OboServices.this);
						}
					}
				}
				
			});
		}
		catch (Exception e)
		{
//			if(e instanceof NullPointerException)
//			{
//				System.gc();
//			}
//			else
				handleException(OboServicesConstants.METHOD_GET_TEXT, e);
		}
	}

	
	/**
	 * Parses a SOAP XML for application errorCode and errorString.
	 * @param e 
	 * @param xml					the XML data to parse.
	 * @return						An error in the form of an <code>Exception</code>(
	 * 								to be passed in {@link OboServicesListener#onOboServicesError(int, Exception, OboServices));
	 * 								<code>null</code> if there were no error.
	 */
	private Exception parseError (Throwable e, String xml)
	{
		if(dialog != null) dialog.dismiss();
		String errorCode = getCData(xml, "errorCode");
		String errorString = getCData(xml, "errorString");
		if (errorCode != null || errorString != null)
		{
			StringBuffer buf = new StringBuffer(64);
			if (errorString != null)
			{
				buf.append(errorString);
			}
			if (errorCode != null)
			{
				if (errorString != null)
				{
					buf.append("\n\n");
				}
				buf.append(errorCode);
			}
			return new OboServicesException(errorCode, buf.toString()); 
		}
		// If ConnectException is thrown, the message is null. So rethrowing the causal SocketException which has the 
		// exception message.
		if(e instanceof SocketException)
			return (Exception) e.getCause();
		else if(e instanceof UnknownHostException) {
			return new Exception("Unknown host:" + e.getMessage());
		}
		return (Exception) e;
	}

	/**
	 * Handles a thrown <code>Exception</code> while making a web service call.
	 * Calls an <code>OboServicesListener</code>'s <code>onError</code> method.
	 * @param serviceType			identifies the web service call. May be one of the <code>OboServicesConstants.METHOD_*</code> values.
	 * @param e						the thrown exception.
	 */
	private void handleException (int serviceType, Exception e)
	{
		if (null == mListener)
		{
			return;
		}
		e.printStackTrace();
		mListener.onOboServicesError(serviceType, e, this);
	}
	
	/**
	 * Convenience method to extract an XML value from an error response body from web service. 
	 * @param xml					the XML data
	 * @param name					the name of the field to extract.
	 * @return						the value of the field in the error response body.
	 */
	private static String getErrorField (String xml, String name)
	{
		String tag = '<' + name + '>';
		int i = xml.indexOf(tag);
		if (i > 0)
		{
			i += tag.length();
			int j = xml.indexOf("</", i);
			if (j > 0)
			{
				return xml.substring(i, j);
			}
		}
		
		return null;
	}

	/**
	 * Convenience method to create an HTTP header to pass to <code>post</code>
	 * @param soapMethod				the name of the web service method.
	 * @return							a <code>Hashtable</code> that can be passed to <code>post</code>.
	 */
	private static Header []createHttpHeader (String soapMethod)
	{
		Header []retVal = new Header[1];
		//retVal[0] = new BasicHeader("Content-Type", "text/xml; charset=\"utf-8\"");
		retVal[0] = new BasicHeader("SOAPAction", soapMethod);
		return retVal;
	}

	/**
	 * Convenience method to extract an CDATA value from a response body from web service. 
	 * @param xml					the XML data
	 * @param name					the name of the CDATA to extract.
	 * @return						the value of the CDATA in the XML data,
	 *								<code>null</code> if <code>xml</code> is <code>null</code>, <code>name</code> is <code>null</code>, or no value was found for <code>name</code>.
	 */
	private static String getCData (String xml, String name)
	{
		if (null == xml || null == name)
		{
			return null;
		}

		// parse the xml fields we need
		// TODO: generic xml parsing?
		int i = xml.indexOf(":" + name + "/>");
		if (i > 0)
		{
			return "";
		}

		String ret = null;
		// TODO: Fix bug for false positive for substring.
		// For exmaple, if there's <feeType ...>, you get fee

		// Parse for a start tag with namespace.
		i = xml.indexOf(":" + name);
		if (i < 0)
		{
			// No namespace in the start tag.
			// Parse for a start tag without namespace.
			i = xml.indexOf("<" + name + ">") + 1;
		}

		if (i > 0)
		{
			int j = xml.indexOf(">", i);
			if (j > 0)
			{
				// Parse for an end tag with namespace.
				int k = xml.indexOf(":" + name, j + 1);
				if (k < 0)
				{
					// Parse for an end tag without namespace.
					k = xml.indexOf("</" + name + ">", j + 1);
				}
				else
				{
					// Move the index back to the start of the end tag (past the namespace)
					k = xml.lastIndexOf('<', k);
				}

				if (k > 0)
				{
					int n = xml.substring(i, j).indexOf("xsi:nil=\"true\"");
					if (n > 0)
					{
						// null data
						// ret is null
					}
					else if (j + 1 == k)
					{
						// empty cdata
						ret = "";
					}
					else if (j + 1 < k)
					{
						String str = xml.substring(j + 1, k);

						final String CDATA_START			= "<![CDATA[";
						final String CDATA_END				= "]]>";
						if (str.startsWith(CDATA_START))
						{
							// not URL-encoded data
							ret = str.substring(CDATA_START.length(), str.length() - CDATA_END.length());
						}
						else
						{
							// URL-encoded data
							ret = xmlDecode(str);
						}
					}
				}
			}
		}

		return ret;
	}
	
//	----------------------------------------------------------------------------------------------------------------
	

	
	private Hashtable parseAccounStatusResult (String xml)
	{
		Hashtable result = new Hashtable();
			
		setParam(result, OboServicesConstants.DATA_FIELD_IS_PIN_RESET, getCData(xml, "isPinReset"));	
		setParam(result, OboServicesConstants.DATA_FIELD_IS_FIRST_TIME, getCData(xml, "isFirstTimeLogin"));	
		//Version Upgrade--Pooja
		setParam(result, OboServicesConstants.DATA_FIELD_DOWNLOAD_TYPE, getCData(xml, "upgradeFlag"));	//F, O, NA
		setParam(result, OboServicesConstants.DATA_FIELD_DOWNLOAD_URL, getCData(xml, "url"));
		setParam(result, OboServicesConstants.DATA_FIELD_PROGRAM_TYPE, getCData(xml, "partnerProgramInfo"));
		setParam(result, OboServicesConstants.DATA_FIELD_FEATURE_NOT_SUPPORTED, getCData(xml, "blockedList"));
		setParam(result, OboServicesConstants.DATA_FIELD_GENDER_TYPE, getCData(xml, "genderType"));
		setParam(result, OboServicesConstants.DATA_FIELD_REGISTRATION_FEE, getCData(xml, "registrationFee"));
		setParam(result, OboServicesConstants.DATA_FIELD_MIN_CASH_IN_AMOUNT, getCData(xml, "minCashInAmount"));
		setParam(result, OboServicesConstants.DATA_FIELD_MAX_CASH_IN_AMOUNT, getCData(xml, "maxCashInAmount"));
		setParam(result, OboServicesConstants.DATA_FIELD_ID_TYPES, getCData(xml, "idTypeList"));
		setParam(result, OboServicesConstants.DATA_FIELD_ID_TYPE_MIN_LENGTH, getCData(xml, "idTypeMinLength"));
		setParam(result, OboServicesConstants.DATA_FIELD_ID_TYPE_MAX_LENGTH, getCData(xml, "idTypeMaxLength"));
		setParam(result, OboServicesConstants.DATA_FIELD_REGISTRATION_TYPE, getCData(xml, "registrationType"));

		PreferenceManager.setCustomerCareNumber(mContext, (String) getCData(xml, "helpLineNumber"));
		return result;
	}
	
	private static Hashtable parseResult (String xml)
	{
		Hashtable result = new Hashtable();

		setParam(result, OboServicesConstants.DATA_FIELD_FIRST_NAME, getCData(xml, "firstName"));		
		setParam(result, OboServicesConstants.DATA_FIELD_LAST_NAME, getCData(xml, "lastName"));		
		setParam(result, OboServicesConstants.DATA_FIELD_PHONE_NUMBER, getCData(xml, "mobileNumber"));		
		setParam(result, OboServicesConstants.DATA_FIELD_DATE, getCData(xml, "registrationDate"));	
		setParam(result, OboServicesConstants.DATA_FIELD_PROGRAM_CODE, getCData(xml, "programName"));	
		setParam(result, OboServicesConstants.DATA_FIELD_STATUS, getCData(xml, "accountStatus"));	
	
		setParam(result, OboServicesConstants.DATA_FIELD_ID_VALUE1, getCData(xml, "idValue1"));	
		setParam(result, OboServicesConstants.DATA_FIELD_ID_TYPE1, getCData(xml, "idType1"));	
		
		
		setParam(result, OboServicesConstants.DATA_FIELD_ID_TYPE2, getCData(xml, "idType2"));	
		setParam(result, OboServicesConstants.DATA_FIELD_ID_VALUE2, getCData(xml, "idValue2"));	
		
		
		return result;
	}

	private static Hashtable parseCheckAppUpdateResult (String xml)
	{
		Hashtable result = new Hashtable();
		setParam(result, OboServicesConstants.DATA_FIELD_STATUS, getCData(xml, WEB_SERVICE_PARAM_STATUS));
		setParam(result, OboServicesConstants.DATA_FIELD_VERSION, getCData(xml, WEB_SERVICE_PARAM_VERSION));
		setParam(result, OboServicesConstants.DATA_FIELD_URL, getCData(xml, WEB_SERVICE_PARAM_URL));
		setParam(result, OboServicesConstants.DATA_FIELD_DETAILS, getCData(xml, WEB_SERVICE_PARAM_DETAILS));
		return result;
	}
	

	private Hashtable parseCheckThemeUpdateResult (String xml)
	{
		Hashtable result = new Hashtable();
		
		setParam(result, OboServicesConstants.DATA_FIELD_PI_CODE, getCData(xml, WEB_SERVICE_PARAM_PI_CODE));
		setParam(result, OboServicesConstants.DATA_FIELD_PROGRAM_CODE, getCData(xml, WEB_SERVICE_PARAM_PROGRAM_CODE));
		setParam(result, OboServicesConstants.DATA_FIELD_VERSION, getCData(xml, WEB_SERVICE_PARAM_VERSION));
		setParam(result, OboServicesConstants.DATA_FIELD_LOCALE, getCData(xml, WEB_SERVICE_PARAM_LOCALE));
		
		//Theme elements are now delivered as metadata elements in-line
		//setParam(result, OboServicesConstants.DATA_FIELD_URL, getCData(xml, WEB_SERVICE_PARAM_URL));
		Vector metadata = new Vector();
		setParam(result, OboServicesConstants.DATA_FIELD_METADATA, metadata);
		parseMetadata(xml, metadata); 

		return result;
	}

	/*******************************************************************************************************
	private Hashtable parseGetThemeResult (String xml, OboServicesListener listener)
	{
		Hashtable result = new Hashtable();

		setParam(result, OboServicesConstants.DATA_FIELD_ID, getCData(xml, WEB_SERVICE_PARAM_NAME));
		setParam(result, OboServicesConstants.DATA_FIELD_VERSION, getCData(xml, WEB_SERVICE_PARAM_VERSION));
		setParam(result, OboServicesConstants.DATA_FIELD_LOCALE, getCData(xml, WEB_SERVICE_PARAM_LOCALE));

		listener.onOboServicesResult(OboServicesConstants.DATA_THEME_START, result, this);

		parseThemeImages(xml, listener);
		parseThemeText(xml, listener);
		parseThemeColor(xml, listener);
		parseThemeScreen(xml, listener);
		
		return result;
	}

	private void parseThemeImages (String xml, OboServicesListener listener)
	{
		// Parse the images
		final String START_TAG			= "<image>";	// ignore the index in the namespace
		final String END_TAG			= "</image>";	// ignore the index in the namespace
		for (int i = xml.indexOf(START_TAG), j;
		 	 i > 0;
		 	 i = xml.indexOf(START_TAG, j + END_TAG.length()))
		{
			j = xml.indexOf(END_TAG, i + START_TAG.length());
		
			// extract the cdata between the start and end tags.
			String str = xml.substring(xml.indexOf('>', i) + 1, xml.lastIndexOf('<', j)).trim();

			try
			{
				Hashtable result = new Hashtable(2);
				setParam(result, OboServicesConstants.DATA_FIELD_ID, getCData(str, WEB_SERVICE_PARAM_ID));
				setParam(result, OboServicesConstants.DATA_FIELD_FILE, getCData(str, WEB_SERVICE_PARAM_FILE));
				
				// decode and store the image data
				byte[] buf = Base64decoder.decode(getCData(str, WEB_SERVICE_PARAM_VALUE));
				setParam(result, OboServicesConstants.DATA_FIELD_VALUE, buf);

				listener.onOboServicesResult(OboServicesConstants.DATA_THEME_IMAGE, result, this);
			}
			catch (Exception e)
			{
				//#ifdef build.debug
				e.printStackTrace();
				//#endif
			}
		}
	}

	private void parseThemeText (String xml, OboServicesListener listener)
	{
		// Parse the images
		final String START_TAG		= "<text>";		// ignore the index in the namespace
		final String END_TAG		= "</text>";	// ignore the index in the namespace
		for (int i = xml.indexOf(START_TAG), j;
		 	 i > 0;
		 	 i = xml.indexOf(START_TAG, j + END_TAG.length()))
		{
			j = xml.indexOf(END_TAG, i + START_TAG.length());
		
			// extract the cdata between the start and end tags.
			String str = xml.substring(xml.indexOf('>', i) + 1, xml.lastIndexOf('<', j)).trim();

			try
			{
				Hashtable result = new Hashtable(3);

				setParam(result, OboServicesConstants.DATA_FIELD_FILE, getCData(str, WEB_SERVICE_PARAM_FILE));

				setParam(result, OboServicesConstants.DATA_FIELD_ID, getCData(str, WEB_SERVICE_PARAM_ID));
				
				// decode and store the image data
				String text = getCData(str, WEB_SERVICE_PARAM_VALUE);
				setParam(result, OboServicesConstants.DATA_FIELD_VALUE, text);

				listener.onOboServicesResult(OboServicesConstants.DATA_THEME_TEXT, result, this);
			}
			catch (Exception e)
			{
				//#ifdef build.debug
				e.printStackTrace();
				//#endif
			}
		}
	}

	private void parseThemeColor (String xml, OboServicesListener listener)
	{
		// Parse the images
		final String START_TAG		= "<color>";	// ignore the index in the namespace
		final String END_TAG		= "</color>";	// ignore the index in the namespace
		for (int i = xml.indexOf(START_TAG), j;
		 	 i > 0;
		 	 i = xml.indexOf(START_TAG, j + END_TAG.length()))
		{
			j = xml.indexOf(END_TAG, i + START_TAG.length());
		
			// extract the cdata between the start and end tags.
			String str = xml.substring(xml.indexOf('>', i) + 1, xml.lastIndexOf('<', j)).trim();

			try
			{
				Hashtable result = new Hashtable();
				setParam(result, OboServicesConstants.DATA_FIELD_ID, getCData(str, WEB_SERVICE_PARAM_ID));
				
				// decode and store the image data
				String text = getCData(str, WEB_SERVICE_PARAM_VALUE);
				setParam(result, OboServicesConstants.DATA_FIELD_VALUE, text);

				listener.onOboServicesResult(OboServicesConstants.DATA_THEME_COLOR, result, this);
			}
			catch (Exception e)
			{
				//#ifdef build.debug
				e.printStackTrace();
				//#endif
			}
		}
	}

	private void parseThemeScreen(String xml, OboServicesListener listener)
	{
		// Parse the images
		final String START_TAG		= "<screen>";	// ignore the index in the namespace
		final String END_TAG		= "</screen>";	// ignore the index in the namespace
		for (int i = xml.indexOf(START_TAG), j;
		 	 i > 0;
		 	 i = xml.indexOf(START_TAG, j + END_TAG.length()))
		{
			j = xml.indexOf(END_TAG, i + START_TAG.length());
		
			// extract the cdata between the start and end tags.
			String str = xml.substring(xml.indexOf('>', i) + 1, xml.lastIndexOf('<', j)).trim();

			try
			{
				Hashtable result = new Hashtable(2);
				setParam(result, OboServicesConstants.DATA_FIELD_ID, getCData(str, WEB_SERVICE_PARAM_ID));
				
				// decode and store the image data
				String text = getCData(str, WEB_SERVICE_PARAM_VALUE);
				setParam(result, OboServicesConstants.DATA_FIELD_VALUE, text);

				listener.onOboServicesResult(OboServicesConstants.DATA_THEME_SCREEN, result, this);
			}
			catch (Exception e)
			{
				//#ifdef build.debug
				e.printStackTrace();
				//#endif
			}
		}
	}
	*********************************************************************************************************/

	private Vector parseHistoryResult (String xml)
	{
		String val = getCData(xml, WEB_SERVICE_PARAM_COUNT);
		Integer count = null;
		try
		{
			count = new Integer(Integer.parseInt(val));
		}
		catch (Exception e)
		{
//#ifdef build.debug
			e.printStackTrace();
//#endif
		}

		Vector result = new Vector();
		final String START_TAG			= "<java:history";
		final String END_TAG			= "</java:history>";
		for (int i = xml.indexOf(START_TAG), j;
			 i > 0; 
			 i = xml.indexOf(START_TAG, j + END_TAG.length()))
		{
			j = xml.indexOf(END_TAG, i + START_TAG.length());
			
			Hashtable record = new Hashtable();

			// extract the cdata between the start and end tags.
			String str = xml.substring(xml.indexOf('>', i) + 1, xml.lastIndexOf('<', j));

			setParam(record, OboServicesConstants.DATA_FIELD_COUNT, count);
			val = getCData(str, WEB_SERVICE_PARAM_SERIES_NUMBER);
			try
			{
				setParam(record, OboServicesConstants.DATA_FIELD_SERIES_NUMBER, new Integer(Integer.parseInt(val)));
			}
			catch (Exception e)
			{
			}

			setParam(
				record, 
				OboServicesConstants.DATA_FIELD_SHORT_DESC, 
				getCData(str, WEB_SERVICE_PARAM_SHORT_DESC));
			setParam(
				record, 
				OboServicesConstants.DATA_FIELD_DESC, 
				getCData(str, WEB_SERVICE_PARAM_DESC));
			
			Vector metadata = new Vector();
			setParam(record, OboServicesConstants.DATA_FIELD_METADATA, metadata);
			parseMetadata(str, metadata);

			result.addElement(record);
		}
		
		return result;
	}
	
	
	

	
	/**
	 * Convenience method to parse metadata into a <code>Hashtable</code>.
	 * @param xml			the xml string to parse.
	 * @return				the <code>Hashtable</code> that contains the parsed metadata result.
	 */
	private Hashtable parseMetadataResult (String xml)
	{
		Hashtable result = new Hashtable();

		setParam(result, OboServicesConstants.DATA_FIELD_STATUS, getCData(xml, WEB_SERVICE_PARAM_STATUS));	
		Vector metadata = new Vector();
		setParam(result, OboServicesConstants.DATA_FIELD_METADATA, metadata);
		parseMetadata(xml, metadata); 
		System.out.println("result  "+result);
		return result;
	}
	
	/**
	 * Parse a string for <code>Metadata</code> name-value pairs.
	 * @param xml				the xml string to parse.
	 * @param metadata			the <code>Vector</code> to populate the parsed metadata
	 */
	private void parseMetadata (String xml, Vector metadata)
	{
		if (xml == null) return;
		
		final String START_TAG		= ":metadata";	// ignore the index in the namespace
		final String END_TAG		= ":metadata>";	// ignore the index in the namespace
		
		int nexti = 0;
		
		for (int i = xml.indexOf(START_TAG), j;
		 	 i > 0;
		 	 i = xml.indexOf(START_TAG, nexti))
		{
			// See if the start tag has a "/>" terminator, meaning there
			// won't be an end tag for it
			int k = xml.indexOf(">", i);
			if (k > -1 && "/".equals(xml.substring(k-1, k)))
			{
				nexti = k+1;
				continue;
			}
			j = xml.indexOf(END_TAG, i + START_TAG.length());
			if (j < 0) break;
			nexti = j + END_TAG.length();
			
			// extract the cdata between the start and end tags.
			String str = xml.substring(xml.indexOf('>', i) + 1, xml.lastIndexOf('<', j));
			
			try
			{
				Metadata m = new Metadata(mContext,
					getCData(str, WEB_SERVICE_PARAM_NAME),
					getCData(str, WEB_SERVICE_PARAM_TYPE),
					getCData(str, WEB_SERVICE_PARAM_LABEL));
				m.setValueFromString(getCData(str, WEB_SERVICE_PARAM_VALUE));
				metadata.addElement(m);
			}
			catch(MetadataException e)
			{
				//#ifdef build.debug
				//#endif
			}
		}
	}

	
	/**
	 * Convenience method to get the next idempotent request ID in the params for a web service request.
	 * If one doesn't exist in the given <code>Hashtable</code> <code>param</code>, an idempotenct ID is created. 
	 * The request ID is cached in the given <code>Hashtable</code> <code>param</code>.
	 * @param params					The web service params.
	 * @return							The next itempotent request ID. 
	 * 									If the request ID already exists in <code>params</code>, then the same ID is returned.
	 */
	private static String getRequestID (Hashtable params)
	{
		//String requestID = (String)getParam(params, OboServicesConstants.DATA_FIELD_REQUEST_ID); 
		//if (null == requestID)
		//{
			String requestID = getNextIKeySeqNum();
			//setParam(params, OboServicesConstants.DATA_FIELD_REQUEST_ID, requestID);
		//}
		
		return requestID;
	}
	
	/**
	 * This is a convenience method for getting the next idempotence value for a web service request.
	 * @return							The next idempotence value.
	 */
	private static String getNextIKeySeqNum ()
	{
		// The previous implementation (2.5 and older)
		// used the RMS to store the last value used, and incremented the value.
		// We can just used the current timestamp.
		return String.valueOf(System.currentTimeMillis());
	}

	/**
	 * Formats a <code>Date</code> into an ISO 8601 date string.
	 * @param date						the <code>Date</code> to format.
	 * @return							the ISO 8601 string.
	 */
	public static final String formatISO8601Date (Date date)
	{
		/*
		 * cf. http://www.w3.org/TR/NOTE-datetime
		 * We use the format YYYY-MM-DD (eg 1997-07-16) and ignore the time part.
		 */
		StringBuffer buf = new StringBuffer(10);
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		buf.append(cal.get(Calendar.YEAR));
		buf.append('-');
		int n = cal.get(Calendar.MONTH) + 1;
		if (n < 10)
		{
			buf.append('0');
		}
		buf.append(n);
		buf.append('-');
		n = cal.get(Calendar.DATE);
		if (n < 10)
		{
			buf.append('0');
		}
		buf.append(n);
		return buf.toString();
	}
	/**
	 * Convenience method to send a SOAP request.
	 * request at a time.
	 * @param methodName			the SOAP method name.
	 * @param payload				the SOAP payload.
	 * @return						the SOAP response; <code>null</code> if the SOAP connection was cancelled.
	 * @throws IOException			if there's a network connection error.
	 * @throws HttpException		if there's a server error.
	 */
	private void doSoapRequest (String methodName, String payload, 
			final AsyncHttpResponseHandler responseHandler) throws HttpException, IOException
	{
		String strResp = null;

		if (mHttp != null)
		{
			mHttp.cancelRequests(mContext, true);
		}

		mHttp = new AsyncHttpClient();
		if(!Config.LIVE)
			mHttp.setSSLSocketFactory(createAllAcceptingSocketFactory());
		mHttp.setTimeout(TIMEOUT_MILLISECONDS);

		Log.d(Utils.LOG_TAG, "Invoking SOAP Service with Url:" + mWebSvcUrl + " and payload:" + payload);
		dialog = new ProgressDialog(mContext);
		dialog.setCancelable(false);
		dialog.setMessage("Please Wait..");
		dialog.show();

		mHttp.post(mContext,
			mWebSvcUrl,
			createHttpHeader(methodName),
			new ByteArrayEntity(payload.getBytes()),
			"text/xml; charset=\"utf-8\"",
			responseHandler
			);
	}

	/**
	 * Convenience method to start a thread to make a web service call.
	 * @param serviceType			identifies the web service method
	 * @param params				the input parameters to the web service method
	 */
	private void invokeSoapService (int serviceType, Hashtable params)
	{
		SoapClient soapClient = new SoapClient(serviceType, params, this);
		soapClient.invokeService();
	}

	private class SoapClient
	{
		/**
		 * The ID of web service method to call in a thread. 
		 */
		private int mServiceType;
		
		/**
		 * The input parameters to a web service call.
		 */
		private Hashtable mParams;

		/**
		 * Reference to the parent {@link OboServices} object.
		 */
		private OboServices mSvcObj;

		SoapClient (int serviceType, Hashtable params, OboServices svcObj)
		{
			mServiceType = serviceType;
			mParams = params;
			mSvcObj = svcObj;
		}

		public void invokeService ()
		{
			// Use the request ID from the params
			String requestID = getRequestID(mParams);
			// Use try-catch block to catch and display unexpected errors.
			try
			{
				// Synchronized so that multiple asynchronous calls are thread-safe.
				// Only one HTTP connection call at a time.
				synchronized (mSvcObj)
				{

				switch (mServiceType)
				{
		
				case OboServicesConstants.METHOD_REQUEST_TOKEN:
				{
					requestClientToken(requestID);
					break;
				}
				
				case OboServicesConstants.METHOD_POLL_TOKEN_VALIDATION:
				{
					String token = (String)getParam(mParams, OboServicesConstants.DATA_FIELD_TOKEN);
					pollForTokenValidation(requestID);
					break;
				}
				case OboServicesConstants.METHOD_GET_CLIENT_UPDATES:
				{
					String piCode = (String)getParam(mParams, OboServicesConstants.DATA_FIELD_PI_CODE);
					String programCode = (String)getParam(mParams, OboServicesConstants.DATA_FIELD_PROGRAM_CODE);
					String version = (String)getParam(mParams, OboServicesConstants.DATA_FIELD_VERSION);
					String locale = (String)getParam(mParams, OboServicesConstants.DATA_FIELD_LOCALE);
					String displaySize = (String)getParam(mParams, OboServicesConstants.DATA_FIELD_DISPLAY_SIZE);
					getClientUpdates(piCode, programCode, version, locale, displaySize, requestID);
					break;
				}
				case OboServicesConstants.METHOD_CHECK_APP_UPDATE:
					checkAppUpdate(requestID);
					break;
			
				case OboServicesConstants.METHOD_GET_BALANCE:
				{
					String pin = (String)getParam(mParams, OboServicesConstants.DATA_FIELD_PIN); 
					getBalance(pin, requestID);
					break;
				}
			
				
				case OboServicesConstants.METHOD_GET_TEXT:
				{
					String id = (String)getParam(mParams, OboServicesConstants.DATA_FIELD_ID);
					String code = (String)getParam(mParams, OboServicesConstants.DATA_FIELD_TOKEN);
					
					getText(id, code, requestID);
					break;
				}				
						
				case OboServicesConstants.METHOD_CHANGE_PIN:
				{
					String pin = (String)getParam(mParams, OboServicesConstants.DATA_FIELD_PIN); 		
					String newPin = (String)getParam(mParams, OboServicesConstants.DATA_FIELD_NEW_PIN); 	
					
					doChangePIN(pin,newPin,requestID,mServiceType);
					break;
				}

				case OboServicesConstants.METHOD_CHANGE_LANGUAGE:
				{

					String language = (String)getParam(mParams, OboServicesConstants.DATA_FIELD_LANG); 	
					
					doChangeLanguage(language,requestID,mServiceType);
					break;
				}	
				
				
				case OboServicesConstants.METHOD_NEW_CUSTOMER:
				{
					String pin = (String)getParam(mParams, OboServicesConstants.DATA_FIELD_PIN); 
					Integer progName= (Integer)getParam(mParams, OboServicesConstants.DATA_FIELD_PROG_TYPE); 
					String custMobile = (String)getParam(mParams, OboServicesConstants.DATA_FIELD_PHONE_NUMBER);
					String cardNum = null;
					String formNum = (String)getParam(mParams, OboServicesConstants.DATA_FIELD_FORM_NUMBER); 
					String firstName = (String)getParam(mParams, OboServicesConstants.DATA_FIELD_FIRST_NAME); 
					String lastName = (String)getParam(mParams, OboServicesConstants.DATA_FIELD_LAST_NAME); 
					String dob = (String)getParam(mParams, OboServicesConstants.DATA_FIELD_DATE_BACK);
					String lang = (String)getParam(mParams, OboServicesConstants.DATA_FIELD_LANG); 
					String gender = (String) getParam(mParams, OboServicesConstants.DATA_FIELD_GENDER_TYPE);
					Integer idType = (Integer) getParam(mParams, OboServicesConstants.DATA_FIELD_ID_TYPES);
					String idNumber = (String) getParam(mParams, OboServicesConstants.DATA_FIELD_ID_NUMBER);
					Integer registrationFee =  (Integer) getParam(mParams, OboServicesConstants.DATA_FIELD_REGISTRATION_FEE);
					Integer cashInAmount = (Integer) getParam(mParams, OboServicesConstants.DATA_FIELD_CASH_IN_AMOUNT);
					String atmCardRefNo = (String) getParam(mParams, OboServicesConstants.DATA_FIELD_ATM_CARD_REF_NUMBER);
					Integer registrationType = (Integer) getParam(mParams, OboServicesConstants.DATA_FIELD_REGISTRATION_TYPE);
					createNewCustomer(requestID,custMobile,cardNum,progName,pin,formNum,firstName,lastName,dob,lang,
							gender,idType,idNumber,registrationType,cashInAmount,atmCardRefNo,mServiceType);
					break;
				}
				
				case OboServicesConstants.METHOD_UPGRADE_CUSTOMER:
				{
					String pin = (String)getParam(mParams, OboServicesConstants.DATA_FIELD_PIN); 
					String progName= "SILVER";
					String custMobile = (String)getParam(mParams, OboServicesConstants.DATA_FIELD_PHONE_NUMBER);
					String cardNum = null;
					String formNum = (String)getParam(mParams, OboServicesConstants.DATA_FIELD_FORM_NUMBER); 
					
					upgradeCustomer(requestID,custMobile,cardNum,progName,pin,formNum,mServiceType);
					break;
				}
				
				case OboServicesConstants.METHOD_DEPOSIT_MONEY:
				{
					String pin = (String)getParam(mParams, OboServicesConstants.DATA_FIELD_PIN); 
					String custMobile = (String)getParam(mParams, OboServicesConstants.DATA_FIELD_PHONE_NUMBER); 
					String amt = (String)getParam(mParams, OboServicesConstants.DATA_FIELD_AMT); 
					
					depositMoney(requestID,custMobile,pin,amt,mServiceType);
					break;
				}
				
				
				case OboServicesConstants.METHOD_VERIFY_CUSTOMER:
				{
					
					String pin = (String)getParam(mParams, OboServicesConstants.DATA_FIELD_PIN); 
					String custMobile = (String)getParam(mParams, OboServicesConstants.DATA_FIELD_PHONE_NUMBER); 
					String dob =(String)getParam(mParams, OboServicesConstants.DATA_FIELD_DATE_BACK); 	
					verifyCustomer(requestID,custMobile,dob,pin,mServiceType);
					break;
				}
				case OboServicesConstants.METHOD_CARD_REISSUE:
				{
					
					String pin = (String)getParam(mParams, OboServicesConstants.DATA_FIELD_PIN); 
					String custMobile = (String)getParam(mParams, OboServicesConstants.DATA_FIELD_PHONE_NUMBER); 
					String cardNum = (String)getParam(mParams, OboServicesConstants.DATA_FIELD_CARD_NUM); 							
					
					cardReissue(requestID,custMobile,cardNum,pin,mServiceType);
					break;
				}				
				case OboServicesConstants.METHOD_USER_DETAILS:
				case OboServicesConstants.METHOD_DEPOSIT_USER_DETAILS:
				{							
					String custMobile = (String)getParam(mParams, OboServicesConstants.DATA_FIELD_PHONE_NUMBER); 
					String pin = (String)getParam(mParams, OboServicesConstants.DATA_FIELD_PIN); 							
					getUserDetails(requestID,custMobile,pin,mServiceType);
					break;
				}
				case OboServicesConstants.METHOD_VALIDATING_TXN_ID:
				{
					String custMobile = (String)getParam(mParams, OboServicesConstants.DATA_FIELD_PHONE_NUMBER); 
					String txnID=(String)getParam(mParams, OboServicesConstants.DATA_FIELD_TXN_ID); 
					String pin = (String)getParam(mParams, OboServicesConstants.DATA_FIELD_PIN); 
					validateTxnID(requestID,custMobile,txnID,pin,mServiceType);
					break;
				}
				case OboServicesConstants.METHOD_REMIT_MONEY:
				{
					String custMobile = (String)getParam(mParams, OboServicesConstants.DATA_FIELD_PHONE_NUMBER); 
					System.out.println("custMobile----------------"+custMobile);
					String otp=(String)getParam(mParams, OboServicesConstants.DATA_FIELD_OTP); 
					String txnID=(String)getParam(mParams, OboServicesConstants.DATA_FIELD_TXN_ID); 
					String pin = (String)getParam(mParams, OboServicesConstants.DATA_FIELD_PIN); 
					remitMoney(requestID,custMobile,txnID,otp,pin,mServiceType);
					break;
				}
//				VIRAL SEND States added- Start
				case OboServicesConstants.METHOD_VIRAL_SEND:
				{
					String custMobile = (String)getParam(mParams, OboServicesConstants.DATA_FIELD_PHONE_NUMBER); 
					//System.out.println("custMobile----METHOD_VIRAL_SEND------------"+custMobile);
					String otp=(String)getParam(mParams, OboServicesConstants.DATA_FIELD_OTP); 
					String txnID=(String)getParam(mParams, OboServicesConstants.DATA_FIELD_TXN_ID); 
					String pin = (String)getParam(mParams, OboServicesConstants.DATA_FIELD_PIN); 
					//Added for Viral Send
					String firstName = (String)getParam(mParams, OboServicesConstants.DATA_FIELD_FIRST_NAME); 
					String lastName = (String)getParam(mParams, OboServicesConstants.DATA_FIELD_LAST_NAME); 
					String idType1=(String)getParam(mParams, OboServicesConstants.DATA_FIELD_BENID1_LABEL); 
					String idValue1 = (String)getParam(mParams, OboServicesConstants.DATA_FIELD_BENID1);
					String idType2=(String)getParam(mParams, OboServicesConstants.DATA_FIELD_BENID2_LABEL); 
					String idValue2 = (String)getParam(mParams, OboServicesConstants.DATA_FIELD_BENID2);
				

					remitMoneyViral(requestID,firstName,lastName,custMobile,idType1,idValue1,idType2,idValue2,txnID,otp,pin,mServiceType);
					break;
				}
//				VIRAL SEND States added- End
				
				case OboServicesConstants.METHOD_REMIT_MONEY_REJECT:
				{
					String custMobile = (String)getParam(mParams, OboServicesConstants.DATA_FIELD_PHONE_NUMBER); 
					String otp=(String)getParam(mParams, OboServicesConstants.DATA_FIELD_OTP); 
					String txnID=(String)getParam(mParams, OboServicesConstants.DATA_FIELD_TXN_ID); 
					String pin = (String)getParam(mParams, OboServicesConstants.DATA_FIELD_PIN); 
					rejectRemitMoney(requestID,custMobile,txnID,otp,pin,mServiceType);
					break;
				}
				
				case OboServicesConstants.METHOD_VERIFY_PIN:
				{
					String pin = (String)getParam(mParams, OboServicesConstants.DATA_FIELD_PIN); 
					doVerifyPIN(requestID,pin,mServiceType);
					break;
				}
				
				
//				case OboServicesConstants.METHOD_VERIFY_TEMP_PIN:
//				{
//					String pin = (String)getParam(mParams, OboServicesConstants.DATA_FIELD_PIN); 
//					doVerifyTempPIN(requestID,pin,mServiceType);
//					break;
//				}
				
				case OboServicesConstants.METHOD_ACTIVATE:
				{
					
					String newpin = (String)getParam(mParams, OboServicesConstants.DATA_FIELD_NEW_PIN); 
					String dob = ((String)getParam(mParams, OboServicesConstants.DATA_FIELD_DATE_CUSTOMER_BACK));
					
					String motherdob =( (String)getParam(mParams, OboServicesConstants.DATA_FIELD_DATE_MOTHERS_BACK)) ;
					doActivate(requestID,newpin,dob,motherdob,mServiceType);
												
					break;
				}
				
				case OboServicesConstants.METHOD_RESET:
				{
					String newpin = (String)getParam(mParams, OboServicesConstants.DATA_FIELD_NEW_PIN); 
					String tempPin = (String)getParam(mParams, OboServicesConstants.DATA_FIELD_PIN); 	
					doResetPin(requestID,tempPin,newpin,mServiceType);
					break;
				}
				case OboServicesConstants.METHOD_GET_HISTORY:
				{
					String pin = (String)getParam(mParams, OboServicesConstants.DATA_FIELD_PIN);
					int seriesStart = ((Integer)getParam(mParams, OboServicesConstants.DATA_FIELD_SERIES_START)).intValue();
					int seriesEnd = ((Integer)getParam(mParams, OboServicesConstants.DATA_FIELD_SERIES_END)).intValue();
					boolean clearCache = ((Boolean)getParam(mParams, OboServicesConstants.DATA_FIELD_CLEAR_CACHE)).booleanValue();
					getHistory(pin, seriesStart, seriesEnd, clearCache, requestID);
					break;
				}	
				case OboServicesConstants.METHOD_CHECK_ACCOUNT_STATUS:
				{		
					checkAccountStatus(requestID,mServiceType);					
					break;
				}
				
			
											
				default:
				{
				//#ifdef build.debug
					throw new IllegalArgumentException("Invalid mServiceType: " + mServiceType);
				//#else
				//#	throw new IllegalArgumentException();
				//#endif

				} // end default
				
				} // end switch (mServiceType)
				
				} // end synchronized (this)
			}
			catch (Throwable t)
			{
				String msg = t.getMessage(); 
				handleException(
					mServiceType, 
					new Exception((msg != null) ? msg : t.toString()));
			}
		}
	} // end SoapThread
	
	
	/**
	 * XML-encodes a string.<p>
	 * cf. http://www.w3.org/TR/2000/REC-xml-20001006#dt-escape
	 * @param str				the string to encode
	 * @return					the xml-encoded string; 
	 * 							<code>null</code> if <code>str</code> is <code>null</code>.
	 */
	public static final String xmlEncode (String str)
	{
		if (null == str)
		{
			return null;
		}

		StringBuffer buf = new StringBuffer(str.length() + 32);

		// for now, just replace '&' and '<'
		for (int i = 0; i < str.length(); i++)
		{
			char c = str.charAt(i);
			switch (c)
			{
			case '&':
				buf.append("&amp;");
				break;
			case '<':
				buf.append("&lt;");
				break;
			default:
				buf.append(c);
				break;
			}
		}
		return buf.toString();
	}

	/**
	 * XML-decodes a string.<p>
	 * cf. http://www.w3.org/TR/2000/REC-xml-20001006#dt-escape
	 * @param str				the string to decode
	 * @return					the xml-decoded string
	 */
	public static final String xmlDecode (String str)
	{
		if (null == str)
		{
			return null;
		}

		String[] TOKENS = 
		{
			"&lt;",
			"&gt;",
			"&apos;",
			"&quot;",
			"&amp;"
		};

		char[] CHARS = 
		{
			'<',
			'>',
			'\'',
			'\"',
			'&'
		};

		for (int i = 0; i < TOKENS.length; i++)
		{
			StringBuffer buf = new StringBuffer(str.length());

			int j = 0;
			for (int k = str.indexOf(TOKENS[i]);
				 k >= 0; 
				 k = str.indexOf(TOKENS[i], j))
			{
				buf.append(str.substring(j, k));
				buf.append(CHARS[i]);
				j = k + TOKENS[i].length();
			}
			
			if (j > 0)
			{
				buf.append(str.substring(j, str.length()));
				str = buf.toString();
			}
		}
		
		return str;
	}
	
	
	private static SSLSocketFactory createAllAcceptingSocketFactory() {
		try {
			KeyStore trustStore = KeyStore.getInstance(KeyStore.getDefaultType());
			trustStore.load(null, null);

			SSLSocketFactory sf = new AllAcceptingSSLSocketFactory(trustStore);
			sf.setHostnameVerifier(SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
			return sf;
		} catch (KeyManagementException e) {
			Log.e(Utils.LOG_TAG, e.getMessage(), e);
		} catch (KeyStoreException e) {
			Log.e(Utils.LOG_TAG, e.getMessage(), e);
		} catch (NoSuchAlgorithmException e) {
			Log.e(Utils.LOG_TAG, e.getMessage(), e);
		} catch (CertificateException e) {
			Log.e(Utils.LOG_TAG, e.getMessage(), e);
		} catch (UnrecoverableKeyException e) {
			Log.e(Utils.LOG_TAG, e.getMessage(), e);
		} catch (IOException e) {
			Log.e(Utils.LOG_TAG, e.getMessage(), e);
		}
		return null;
	}

	
}
