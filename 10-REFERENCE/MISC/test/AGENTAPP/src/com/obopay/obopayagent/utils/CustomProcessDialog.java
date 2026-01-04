package com.obopay.obopayagent.utils;

import com.obopay.obopayagent.R;

import android.app.ProgressDialog;
import android.content.Context;

public class CustomProcessDialog {

	private static ProgressDialog dialog;
	
	public static void show(Context context){
		show(context, context.getString(R.string.please_wait));
	}
	public static void show(Context context, String message){
		if(message == null || message.equals(""))
			message = context.getString(R.string.please_wait);
		hide();
		dialog = new ProgressDialog(context);
		dialog.setMessage(message);
		dialog.show();
	}
	
	public static void hide(){
		if(dialog != null && dialog.isShowing()){
			dialog.dismiss();
			dialog = null;
		}
	}
}
