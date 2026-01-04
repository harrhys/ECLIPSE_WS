package com.obopay.obopayagent.restclient;

/**
 * Constants used as lookup keys in the <code>Hashtables</code> that are used
 * as in/out parameters in web service APIs. Used by {@link OboServices} methods.
 * The comments below contain a brief description of each field.
 * It is assumed that the reader is familiar with Obopay's web service APIs, and the format of the parameter values.
 * Using these IDs instead of the web service parameters eliminates the tight coupling with server implementation, 
 * and confusion on the correct parameter names to use for a web service API.
 * @author <a href="larry@obopay.com">Larry Wang</a>
 * @see OboServices
 */
public abstract class OboServicesConstants
{
	/**
	 * ID used by {@link OboServices} to call the <code>getClientUpdates</code> web service API.
	 * @see OboServices#getClientUpdates(java.util.Hashtable)
	 */
	public static final int METHOD_GET_CLIENT_UPDATES							= -1;

	/**
	 * ID used by {@link OboServices} to call the <code>establishService</code> web service API.
	 * @see OboServices#establishService(java.util.Hashtable)
	 */
		/**
	 * ID used by {@link OboServices} to call the <code>retrieveBalance</code> web service API.
	 * @see OboServices#getBalance(java.util.Hashtable)
	 */
	public static final int METHOD_GET_BALANCE									= 5;

	/**
	 * ID used by {@link OboServices} to call the <code>getHistory</code> web service API.
	 * @see OboServices#getHistory(java.util.Hashtable)
	 */
	public static final int METHOD_GET_HISTORY									= 6;

		/**
	 * ID used by {@link OboServices} to call the <code>getTC</code> web service API.
	 * @see OboServices#getTermsAndConditions(java.util.Hashtable)
	 */
	public static final int METHOD_GET_TEXT										= 10;

	/**
	 * ID used by {@link OboServices} to call the <code>checkAppUpdate</code> web service API.
	 * @see OboServices#checkAppUpdate(java.util.Hashtable)
	 */
	public static final int METHOD_CHECK_APP_UPDATE								= 12;

	/**
	 * ID used by {@link OboServices} to notify that theme update data is available
	 * @see OboServices#getClientUpdates(java.util.Hashtable)
	 */
	public static final int DATA_THEME_UPDATE									= 102;

	/**
	 * ID used by {@link OboServices} to notify that theme update data is available in
	 * the form of a vector of metadata elements.
	 * @see OboServices#getClientUpdates(java.util.Hashtable)
	 */
	public static final int DATA_THEME_METADATA									= 103;

	/**
	 * ID used by {@link OboServices} to notify the start of parsing a theme.
	 * @see OboServices#getTheme(java.util.Hashtable)
	 */
	public static final int DATA_THEME_START									= 200;

	/**
	 * ID used by {@link OboServices} to notify parsing a theme image.
	 * @see OboServices#getTheme(java.util.Hashtable)
	 */
	public static final int DATA_THEME_IMAGE									= 201;

	/**
	 * ID used by {@link OboServices} to notify parsing a theme text.
	 * @see OboServices#getTheme(java.util.Hashtable)
	 */
	public static final int DATA_THEME_TEXT										= 202;

	/**
	 * ID used by {@link OboServices} to notify parsing a theme color.
	 * @see OboServices#getTheme(java.util.Hashtable)
	 */
	public static final int DATA_THEME_COLOR									= 203;

	/**
	 * ID used by {@link OboServices} to notify parsing a theme screen.
	 * @see OboServices#getTheme(java.util.Hashtable)
	 */
	public static final int DATA_THEME_SCREEN									= 204;

	/**
	 * ID used by {@link OboServices} to notify the end of parsing a theme.
	 * @see OboServices#getTheme(java.util.Hashtable)
	 */
	public static final int DATA_THEME_END										= 205;

	
	// enums used in a Hashtable for output from OboServices.
	// This is used to reduce the code size (by not using custom output classes).
	public static final int DATA_FIELD_UNDEFINED								= -1;
	public static final int DATA_FIELD_EMAIL									= 0;
	public static final int DATA_FIELD_MSG										= 1;
	public static final int DATA_FIELD_FIRST_NAME								= 2;
	public static final int DATA_FIELD_LAST_NAME								= 3;
	public static final int DATA_FIELD_BIRTH_DATE								= 4;
	public static final int DATA_FIELD_PIN										= 5;
	public static final int DATA_FIELD_ID										= 6;
	public static final int DATA_FIELD_PASSWORD									= 7;
	public static final int DATA_FIELD_SMS_PORT									= 8;
	public static final int DATA_FIELD_BUILD_NUMBER								= 9;
	public static final int DATA_FIELD_CONFIRM_PHONE_NUMBER_REQUIRED			= 10;
	public static final int DATA_FIELD_AMT										= 12;
	public static final int DATA_FIELD_COUNT									= 13;
	public static final int DATA_FIELD_SHORT_DESC								= 14;
	public static final int DATA_FIELD_DESC										= 15;
	public static final int DATA_FIELD_SERIES_NUMBER							= 16;
	public static final int DATA_FIELD_TX_ID									= 17;
	public static final int DATA_FIELD_IS_PAYMENT_REQUEST						= 18;
	public static final int DATA_FIELD_PHONE_NUMBER								= 19;
	public static final int DATA_FIELD_VERSION									= 21;
	public static final int DATA_FIELD_STATUS									= 22;
	public static final int DATA_FIELD_DETAILS									= 23;
	public static final int DATA_FIELD_URL										= 24;
	public static final int DATA_FIELD_ACCT_ID									= 25;
	public static final int DATA_FIELD_CVV										= 27;
	public static final int DATA_FIELD_ACCT_TYPE								= 28;
	public static final int DATA_FIELD_NICKNAME									= 29;
	public static final int DATA_FIELD_MASKED_ACCT_NUMBER						= 30;
	public static final int DATA_FIELD_NAME										= 31;
	public static final int DATA_FIELD_DATE										= 33;
	public static final int DATA_FIELD_SERIES_START								= 34;
	public static final int DATA_FIELD_SERIES_END								= 35;
	public static final int DATA_FIELD_CLEAR_CACHE								= 36;
	public static final int DATA_FIELD_ACCT_TYPES								= 37;
	public static final int DATA_FIELD_IS_PAID									= 38;
	public static final int DATA_FIELD_ANSWERS									= 39;
	public static final int DATA_FIELD_TEXT										= 40;
	public static final int DATA_FIELD_TOKEN										= 41;
	public static final int DATA_FIELD_QUESTIONS								= 43;
	public static final int DATA_FIELD_ZIP										= 45;
	public static final int DATA_FIELD_CREDIT_CARD_NUMBER						= 47;
	public static final int DATA_FIELD_LINKED_ACCTS								= 48;
	public static final int DATA_FIELD_LOAD_MONEY_TIME							= 49;
	public static final int DATA_FIELD_ADDR1									= 50;
	public static final int DATA_FIELD_CITY										= 51;
	public static final int DATA_FIELD_STATE									= 52;
	public static final int DATA_FIELD_REQUEST_ID								= 53;
	public static final int DATA_FIELD_SMS_TYPE									= 54;
	public static final int DATA_FIELD_PURPOSE									= 55;
	public static final int DATA_FIELD_VALUE									= 56;
	public static final int DATA_FIELD_FILE										= 57;
	public static final int DATA_FIELD_LOCALE									= 58;
	public static final int DATA_FIELD_SECONDARY_SOURCE							= 59;
	public static final int DATA_FIELD_BALANCE									= 60;
	public static final int DATA_FIELD_APP_TYPE									= 61;
	public static final int DATA_FIELD_CARD_NUM									= 62;
	public static final int DATA_FIELD_METADATA									= 100;
	public static final int DATA_FIELD_PI_CODE									= 101;
	public static final int DATA_FIELD_FROM_ACCT								= 102;
	public static final int DATA_FIELD_TO_ACCT									= 103;
	public static final int DATA_FIELD_FEATURE_NOT_SUPPORTED					= 104;
	public static final int DATA_FIELD_DISPLAY_SIZE								= 105;
	public static final int DATA_FIELD_PROGRAM_CODE								= 106;
	public static final int DATA_FIELD_NEW_PIN									= 107;
	public static final int DATA_FIELD_REENTER_PIN								= 108;
		public static final int DATA_FIELD_DEVICE_VERIFIED							= 108;
	public static final String ERROR_TYPE_INTERNAL								= "IE";
	

    public static final int DATA_FIELD_AGENT_ID					   				= 119;
    public static final int DATA_FIELD_TRANSACTION_FEES							= 150;
    public static final int DATA_FIELD_DEBIT_FEES								= 151; 
    public static final int DATA_FIELD_TRANSACTION_ID							= 152;

	public static final int METHOD_CHANGE_PIN			   					 		= 318;
	public static final int DATA_FIELD_SMS									 		= 319;
	public static final int DATA_FIELD_LANG											= 320;
	public static final int DATA_FIELD_FORM_NUMBER									= 321;
	public static final int DATA_FIELD_PROG_TYPE									= 322;
	
	public static final int METHOD_NEW_CUSTOMER										= 323;
	public static final int METHOD_UPGRADE_CUSTOMER									= 324;
	
	public static final int DATA_FIELD_MOTHER_BIRTH_DATE							= 325;
	
	
	public static final int METHOD_ACTIVATE											= 326;
	public static final int METHOD_USER_DETAILS										= 327;
	public static final int METHOD_VERIFY_CUSTOMER									= 328;
	public static final int METHOD_DEPOSIT_USER_DETAILS								= 329;
	public static final int METHOD_DEPOSIT_MONEY									= 330;
	public static final int DATA_FIELD_ID_TYPE1										= 331;
	public static final int DATA_FIELD_ID_TYPE2										= 332;
	public static final int DATA_FIELD_ID_VALUE1									= 333;
	public static final int DATA_FIELD_ID_VALUE2									= 334;
	public static final int METHOD_CHANGE_LANGUAGE			   					 	= 335;
//	public static final int METHOD_VERIFY_TEMP_PIN									= 336;
	public static final int METHOD_CHECK_ACCOUNT_STATUS								= 337;
	public static final int METHOD_VERIFY_PIN										= 338;
	public static final int METHOD_RESET											= 339;
	public static final int METHOD_CARD_REISSUE										= 340;
	
	public static final int DATA_FIELD_IS_FIRST_TIME								= 340;
	public static final int DATA_FIELD_IS_PIN_RESET									= 341;
	
	public static final int DATA_FIELD_TIME											= 342;
	public static final int DATA_FIELD_TIME_LABEL									= 343;
	public static final int DATA_FIELD_AMT_LABEL									= 344;
	public static final int DATA_FIELD_DATE_LABEL									= 345;
	
	public static final int DATA_FIELD_PHONE_NUMBER_LABEL							= 346;
	public static final int DATA_FIELD_AGENT_ID_LABEL								= 347;
	public static final int DATA_FIELD_TRANSACTION_FEES_LABEL						= 348;
	public static final int DATA_FIELD_DEBIT_FEES_LABEL								= 349;
	public static final int DATA_FIELD_TRANSACTION_ID_LABEL							= 350;
	public static final int METHOD_REQUEST_TOKEN									= 351;
	public static final int METHOD_POLL_TOKEN_VALIDATION							= 352;
	//Version Upgrade---Pooja
	public static final int DATA_FIELD_DOWNLOAD_TYPE								= 353;
	public static final int DATA_FIELD_DOWNLOAD_URL									= 354;
	
	public static final int DATA_FIELD_DATE_BACK									= 355;
	public static final int DATA_FIELD_DATE_MOTHERS_BACK							= 356;
	public static final int DATA_FIELD_DATE_CUSTOMER_BACK							= 357;
	public static final int DATA_FIELD_PROGRAM_TYPE									= 358;
	public static final int DATA_FIELD_HELP_LINE_NUMBER								= 359;
	public static final int DATA_FIELD_SEC_TOKEN									= 360;
	
	public static final int DATA_FIELD_OTP											= 361;
	public static final int METHOD_REMIT_MONEY										= 362;
	public static final int METHOD_VALIDATING_TXN_ID								= 363;
	public static final int DATA_FIELD_TXN_ID										= 364;
	public static final int DATA_FIELD_IS_OTP_ENABLED								= 365;
	public static final int METHOD_REMIT_MONEY_REJECT								= 366;
	
	public static final int DATA_FIELD_FIRST_NAME_LABEL								= 367;
	public static final int DATA_FIELD_LAST_NAME_LABEL								= 368;
	public static final int DATA_FIELD_IS_OTP_ENABLED_LABEL							= 369;

	//Added for viran send  ::
	public static final int DATA_FIELD_TRAN_TYPE									=370;
	public static final int DATA_FIELD_TRAN_TYPE_LABEL								=371;

	public static final int DATA_FIELD_BENID1_LABEL									=372;
	public static final int DATA_FIELD_BENID2_LABEL									=373;
	
	public static final int DATA_FIELD_BENID1										=374;
	public static final int DATA_FIELD_BENID2										=375;
	public static final int METHOD_VIRAL_SEND										= 376;

	public static final int DATA_FIELD_GENDER_TYPE									= 377;
	public static final int DATA_FIELD_REGISTRATION_FEE 							= 378;
	public static final int DATA_FIELD_MIN_CASH_IN_AMOUNT 							= 379;
	public static final int DATA_FIELD_MAX_CASH_IN_AMOUNT 							= 380;
	public static final int DATA_FIELD_ID_TYPES 									= 381;
	public static final int DATA_FIELD_ID_TYPE_MIN_LENGTH							= 382;
	public static final int DATA_FIELD_ID_TYPE_MAX_LENGTH 							= 383;
	public static final int DATA_FIELD_CASH_IN_AMOUNT								= 384;
	public static final int DATA_FIELD_ID_NUMBER 									= 385;
	public static final int DATA_FIELD_ATM_CARD_REF_NUMBER							= 386;
	public static final int DATA_FIELD_REGISTRATION_TYPE							= 387;
}
