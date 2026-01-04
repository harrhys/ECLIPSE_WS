package com.obopay.obopayagent.utils;

import com.obopay.obopayagent.R;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

public class PopUpUtil {
	public static void error(Context context,String msg, DialogInterface.OnClickListener listener) {
		final AlertDialog alertDialog = new AlertDialog.Builder(context).create();
		alertDialog.setButton(DialogInterface.BUTTON_NEGATIVE, context.getString(android.R.string.ok), listener);
		alertDialog.setCancelable(false);
		alertDialog.setTitle(context.getString(R.string.error));
		alertDialog.setMessage(msg);
		alertDialog.show();
	}
	
	public static void error(Context context,String msg){
		error(context, msg, new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				
			}
		});
	}
	
	public static void info(Context context, String title, String msg, String buttonText){
		info(context, title, msg, buttonText, null);
	}
	
	public static void info(Context context, String title, String msg, String buttonText, DialogInterface.OnClickListener clickListener){
		final AlertDialog alertDialog = new AlertDialog.Builder(context).create();
		if(clickListener == null) {
			clickListener = new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int which) {
					
				}
			};
		}
		if(buttonText == null)
			buttonText = context.getString(android.R.string.ok);
		alertDialog.setButton(DialogInterface.BUTTON_POSITIVE, buttonText, clickListener);
		if(title == null)
			title="Information";
		alertDialog.setTitle(title);
		alertDialog.setMessage(msg);
		alertDialog.show();
	}
	
	public static void jsonParseError(Context context){
		error(context, context.getString(R.string.failed_to_read_response_please_try_later));
	}
	
}
