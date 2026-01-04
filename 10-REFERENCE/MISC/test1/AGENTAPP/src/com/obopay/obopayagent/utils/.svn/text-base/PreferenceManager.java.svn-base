package com.obopay.obopayagent.utils;

import java.util.LinkedHashMap;
import java.util.Map;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class PreferenceManager {

	public static String getSecurityToken (Context context) {
	    SharedPreferences preferences = context.getSharedPreferences(Utils.PREFS_NAME, Context.MODE_PRIVATE);
	    return preferences.getString(Utils.SEC_TOKEN, "");
	}
	
	public static void setSecurityToken (Context context, String token) {
	    SharedPreferences preferences = context.getSharedPreferences(Utils.PREFS_NAME, Context.MODE_PRIVATE);
	    Editor editor = preferences.edit();
		if (token != null)
		    editor.putString(Utils.SEC_TOKEN, token);
		else
			editor.remove(Utils.SEC_TOKEN);
	    editor.commit();
	}
	
	public static Map<Integer, String> getProgramTypes(Context context) {
	    SharedPreferences preferences = context.getSharedPreferences(Utils.PREFS_NAME, Context.MODE_PRIVATE);
	    String programTypes = preferences.getString(Utils.PROGRAM_TYPES, "");
		String[] programTypesArray=programTypes.split(","); 
		Map<Integer, String> programTypeMap = new LinkedHashMap<Integer, String>();
		for (String programType : programTypesArray) {
			String[] programCodeAndType = programType.split(":");
			programTypeMap.put(Integer.parseInt(programCodeAndType[0]), programCodeAndType[1]);
		}
		return programTypeMap;
	}
	
	public static void setProgramTypes(Context context, String programTypes) {
	    SharedPreferences preferences = context.getSharedPreferences(Utils.PREFS_NAME, Context.MODE_PRIVATE);
	    Editor editor = preferences.edit();
		if (programTypes != null)
		    editor.putString(Utils.PROGRAM_TYPES, programTypes);
		else
			editor.remove(Utils.PROGRAM_TYPES);
	    editor.commit();
	}
	
	public static String getMFAToken (Context context) {
	    SharedPreferences preferences = context.getSharedPreferences(Utils.PREFS_NAME, Context.MODE_PRIVATE);
	    return preferences.getString(Utils.MFA_TOKEN, "");
	}
	
	public static void setMFAToken (Context context, String token) {
	    SharedPreferences preferences = context.getSharedPreferences(Utils.PREFS_NAME, Context.MODE_PRIVATE);
	    Editor editor = preferences.edit();
		if (token != null)
		    editor.putString(Utils.MFA_TOKEN, token);
		else
			editor.remove(Utils.MFA_TOKEN);
	    editor.commit();
	}
	
	public static final String getAggregatorNumber (Context context) {
		SharedPreferences preferences = context.getSharedPreferences(Utils.PREFS_NAME, Context.MODE_PRIVATE);
		return preferences.getString(Utils.AGGREGATOR_NUM, "");
	}
	
	public static void setAggregatorNumber (Context context, String aggregatorNumber) {
		SharedPreferences preferences = context.getSharedPreferences(Utils.PREFS_NAME, Context.MODE_PRIVATE);
	    Editor editor = preferences.edit();
		if (aggregatorNumber != null)
		    editor.putString(Utils.AGGREGATOR_NUM, aggregatorNumber);
		else
			editor.remove(Utils.AGGREGATOR_NUM);
	    editor.commit();
	}
	
	public static String getPhoneNumber(Context context) {
	    SharedPreferences preferences = context.getSharedPreferences(Utils.PREFS_NAME, Context.MODE_PRIVATE);
	    return preferences.getString(Utils.MEMBER_ID_PROPERTY_NAME, null);
	}
	
	public static void setPhoneNumber (Context context, String phoneNumber) {
		SharedPreferences preferences = context.getSharedPreferences(Utils.PREFS_NAME, Context.MODE_PRIVATE);
	    Editor editor = preferences.edit();
		if (phoneNumber != null)
		    editor.putString(Utils.MEMBER_ID_PROPERTY_NAME, phoneNumber);
		else
			editor.remove(Utils.MEMBER_ID_PROPERTY_NAME);
	    editor.commit();
	}
	
	public static String getCustomerCareNumber(Context context) {
	    SharedPreferences preferences = context.getSharedPreferences(Utils.PREFS_NAME, Context.MODE_PRIVATE);
	    return preferences.getString(Utils.CUSTOMER_CARE_NUMBER, null);
	}
	
	public static void setCustomerCareNumber (Context context, String customerCareNumber) {
		SharedPreferences preferences = context.getSharedPreferences(Utils.PREFS_NAME, Context.MODE_PRIVATE);
	    Editor editor = preferences.edit();
		if (customerCareNumber != null)
		    editor.putString(Utils.CUSTOMER_CARE_NUMBER, customerCareNumber);
		else
			editor.remove(Utils.CUSTOMER_CARE_NUMBER);
	    editor.commit();
	}
	
	public static void setGenderTypes(Context context,String genderTypes) {
		SharedPreferences preferences = context.getSharedPreferences(Utils.PREFS_NAME, Context.MODE_PRIVATE);
	    Editor editor = preferences.edit();
		if (genderTypes != null)
		    editor.putString(Utils.GENDER_TYPES, genderTypes);
		else
			editor.remove(Utils.GENDER_TYPES);
	    editor.commit();
	}

	public static void setRegistrationFee(Context context,String registrationFees) {
		SharedPreferences preferences = context.getSharedPreferences(Utils.PREFS_NAME, Context.MODE_PRIVATE);
	    Editor editor = preferences.edit();
		if (registrationFees != null)
		    editor.putString(Utils.REGISTRATION_FEE, registrationFees);
		else
			editor.remove(Utils.REGISTRATION_FEE);
	    editor.commit();
	}

	public static void setMinCashInAmount(Context context,String minCashInAmount) {
		SharedPreferences preferences = context.getSharedPreferences(Utils.PREFS_NAME, Context.MODE_PRIVATE);
	    Editor editor = preferences.edit();
		if (minCashInAmount != null)
		    editor.putString(Utils.MIN_CASH_IN_AMOUNT, minCashInAmount);
		else
			editor.remove(Utils.MIN_CASH_IN_AMOUNT);
	    editor.commit();
	}

	public static void setMaxCashInAmount(Context context,String maxCashInAmount) {
		SharedPreferences preferences = context.getSharedPreferences(Utils.PREFS_NAME, Context.MODE_PRIVATE);
	    Editor editor = preferences.edit();
		if (maxCashInAmount != null)
		    editor.putString(Utils.MAX_CASH_IN_AMOUNT, maxCashInAmount);
		else
			editor.remove(Utils.MAX_CASH_IN_AMOUNT);
	    editor.commit();
	}

	public static void setIdTypes(Context context, String idTypes) {
		SharedPreferences preferences = context.getSharedPreferences(Utils.PREFS_NAME, Context.MODE_PRIVATE);
	    Editor editor = preferences.edit();
		if (idTypes != null)
		    editor.putString(Utils.ID_TYPES, idTypes);
		else
			editor.remove(Utils.ID_TYPES);
	    editor.commit();
	}

	public static void setIdTypeMinLength(Context context,String idTypeMinLength) {
		SharedPreferences preferences = context.getSharedPreferences(Utils.PREFS_NAME, Context.MODE_PRIVATE);
	    Editor editor = preferences.edit();
		if (idTypeMinLength != null)
		    editor.putString(Utils.ID_TYPE_MIN_LENGTH, idTypeMinLength);
		else
			editor.remove(Utils.ID_TYPE_MIN_LENGTH);
	    editor.commit();
	}

	public static void setIdTypeMaxLength(Context context,String idTypeMaxLength) {
		SharedPreferences preferences = context.getSharedPreferences(Utils.PREFS_NAME, Context.MODE_PRIVATE);
	    Editor editor = preferences.edit();
		if (idTypeMaxLength != null)
		    editor.putString(Utils.ID_TYPE_MAX_LENGTH, idTypeMaxLength);
		else
			editor.remove(Utils.ID_TYPE_MAX_LENGTH);
	    editor.commit();
	}

	public static Map<String, String> getGenderTypes(Context context) {
		SharedPreferences preferences = context.getSharedPreferences(Utils.PREFS_NAME, Context.MODE_PRIVATE);
	    String genderTypes = preferences.getString(Utils.GENDER_TYPES, "");
	    Map<String, String> genderTypeMap = new LinkedHashMap<String, String>();
	    if(null != genderTypes){
			String[] genderTypesArray=genderTypes.split(","); 
			for (String genderType : genderTypesArray) {
				String[] genderCodeAndType = genderType.split(":");
				genderTypeMap.put(genderCodeAndType[0], genderCodeAndType[1]);
			}
	    }
		return genderTypeMap;
	}

	public static Map<Integer, String> getIdTypes(Context context) {
		SharedPreferences preferences = context.getSharedPreferences(Utils.PREFS_NAME, Context.MODE_PRIVATE);
	    String idTypes = preferences.getString(Utils.ID_TYPES, "");
	    Map<Integer, String> idTypeMap = new LinkedHashMap<Integer, String>();
	    if(null != idTypes){
			String[] idTypesArray=idTypes.split(","); 
			for (String id : idTypesArray) {
				String[] idCodeAndType = id.split(":");
				idTypeMap.put(Integer.parseInt(idCodeAndType[0]), idCodeAndType[1]);
			}
	    }
		return idTypeMap;
	}

	public static Integer getIdTypeMinLength(Context context) {
		SharedPreferences preferences = context.getSharedPreferences(Utils.PREFS_NAME, Context.MODE_PRIVATE);
	    String minLength = preferences.getString(Utils.ID_TYPE_MIN_LENGTH, "");
	    if(null != minLength){
			return Integer.parseInt(minLength);
	    }
		return null;
	}

	public static Integer getIdTypeMaxLength(Context context) {
		SharedPreferences preferences = context.getSharedPreferences(Utils.PREFS_NAME, Context.MODE_PRIVATE);
	    String maxLength = preferences.getString(Utils.ID_TYPE_MAX_LENGTH, "");
	    if(null != maxLength){
			return Integer.parseInt(maxLength);
	    }
		return null;
	}

	public static Map<String, Integer> getRegistrationFee(Context context){
	    SharedPreferences preferences = context.getSharedPreferences(Utils.PREFS_NAME, Context.MODE_PRIVATE);
	    String registrationFees = preferences.getString(Utils.REGISTRATION_FEE, "");
	    Map<String, Integer> registrationFeeMap = new LinkedHashMap<String, Integer>();
	    if(null != registrationFees){
			String[] registrationFeeArray=registrationFees.split(","); 
			for (String registrationFee : registrationFeeArray) {
				String[] registrationFeeCodeAndType = registrationFee.split(":");
				registrationFeeMap.put(registrationFeeCodeAndType[0], Integer.parseInt(registrationFeeCodeAndType[1]));
			}
	    }
		return registrationFeeMap;
	}

	public static  Map<Integer, Integer> getMinCashInAmount(Context context) {
		SharedPreferences preferences = context.getSharedPreferences(Utils.PREFS_NAME, Context.MODE_PRIVATE);
	    String minCashInAmount = preferences.getString(Utils.MIN_CASH_IN_AMOUNT, "");
	    Map<Integer, Integer> minCashInAmountMap = new LinkedHashMap<Integer, Integer>();
	    if(null != minCashInAmount){
			String[] minCashInAmountArray=minCashInAmount.split(","); 
			for (String minCashInAmountString : minCashInAmountArray) {
				String[] minAmountCodeAndType = minCashInAmountString.split(":");
				minCashInAmountMap.put(Integer.parseInt(minAmountCodeAndType[0]), Integer.parseInt(minAmountCodeAndType[1]));
			}
	    }
		return minCashInAmountMap;
	}

	public static Map<Integer, Integer> getMaxCashInAmount(Context context) {
		SharedPreferences preferences = context.getSharedPreferences(Utils.PREFS_NAME, Context.MODE_PRIVATE);
	    String maxCashInAmount = preferences.getString(Utils.MAX_CASH_IN_AMOUNT, "");
	    Map<Integer, Integer> maxCashInAmountMap = new LinkedHashMap<Integer, Integer>();
	    if(null != maxCashInAmount){
			String[] maxAmountArray=maxCashInAmount.split(","); 
			for (String maxAmount : maxAmountArray) {
				String[] maxAmountCodeAndType = maxAmount.split(":");
				maxCashInAmountMap.put(Integer.parseInt(maxAmountCodeAndType[0]), Integer.parseInt(maxAmountCodeAndType[1]));
			}
	    }
		return maxCashInAmountMap;
	}

	public static void setRegistrationType(Context context,String registrationType) {
		SharedPreferences preferences = context.getSharedPreferences(Utils.PREFS_NAME, Context.MODE_PRIVATE);
	    Editor editor = preferences.edit();
		if (registrationType != null)
		    editor.putString(Utils.REGISTRATION_TYPE, registrationType);
		else
			editor.remove(Utils.REGISTRATION_TYPE);
	    editor.commit();
	}
	
	public static Integer getRegistrationType(Context context) {
		SharedPreferences preferences = context.getSharedPreferences(Utils.PREFS_NAME, Context.MODE_PRIVATE);
	    String registrationType = preferences.getString(Utils.REGISTRATION_TYPE, "");
	    if(null != registrationType){
			return Integer.parseInt(registrationType);
	    }
		return null;
	}
}
