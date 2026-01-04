package com.obopay.obopayagent.utils;

import java.util.Calendar;
import java.util.GregorianCalendar;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.widget.DatePicker;
import android.widget.EditText;

public class DatePopUp extends Activity{

	public EditText date;
	
	@Override
	protected Dialog onCreateDialog(int id) {
		switch(id){
		case 1: Calendar gregorianCalendar = new GregorianCalendar();
				int month=gregorianCalendar.get(Calendar.MONTH);
				int day=gregorianCalendar.get(Calendar.DAY_OF_MONTH);
				int year=gregorianCalendar.get(Calendar.YEAR);
		      return new DatePickerDialog(this,android.R.style.Theme_Translucent,
		                dateListener,
		                year, month, day); 
		default: return null;
		}
		
	}
	
	private DatePickerDialog.OnDateSetListener dateListener =
		    new DatePickerDialog.OnDateSetListener() {
		        public void onDateSet(DatePicker view, int year, 
		                              int monthOfYear, int dayOfMonth) {
		        	
		        	String month = (monthOfYear+1)+"";
		        	String day = dayOfMonth+"";
		        	
		        	if((dayOfMonth)<10)
		        		day = 0+day;
		        	
		        	if(monthOfYear<10)
		        		month = 0+month;
		        	date.setText(day+"/"+month+"/"+year);   
		        	
		        }
		    };
		    
	private DatePickerDialog.OnCancelListener cancelListener = new OnCancelListener() {
		
		@Override
		public void onCancel(DialogInterface dialog) {
			// TODO Auto-generated method stub
			
		}
	};
		    
}
