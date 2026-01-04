package com.obopay.obopayagent;

import java.util.Hashtable;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;

import com.obopay.obopayagent.EnterPinDialog.EnterPinDialogListener;
import com.obopay.obopayagent.domain.CustomerVerification;
import com.obopay.obopayagent.restclient.OboServices;
import com.obopay.obopayagent.restclient.OboServicesConstants;
import com.obopay.obopayagent.restclient.OboServicesListener;
import com.obopay.obopayagent.utils.DateFormat;
import com.obopay.obopayagent.utils.PopUpUtil;
import com.obopay.obopayagent.utils.Utils;

public class VerifyCustomerActivity extends FragmentActivity implements 
EnterPinDialogListener, OboServicesListener, OnEditorActionListener {
	private EditText mobileNumber;
	private EditText dob;
	private OnClickListener listener = new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			switch(v.getId()){
				case R.id.submit_button_verify_customer:
				handleSubmit();
					break;
				case R.id.date_of_birth_date_picker_verify_customer:
					DialogFragment newFragment = new DatePickerFragment(dob);
				    newFragment.show(getSupportFragmentManager(), "datePicker");
			}
		}		
	};
	
    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        if (EditorInfo.IME_ACTION_DONE == actionId) {
        	handleSubmit();
        	return true;
        }
        return false;
    }
	private String validateSubmit() {
		String mobileNumberString = mobileNumber.getText().toString().trim();
		String dobString = dob.getText().toString().trim();
		String errors = "";
		if(mobileNumberString.length() == 0)
			errors += VerifyCustomerActivity.this.getString(R.string.mobile_number_missing)+"\n";
		else if(mobileNumber.length() !=10)
			errors+= VerifyCustomerActivity.this.getString(R.string.invalid_mobile_number)+"\n";
		if(dobString.length() == 0)
			errors += VerifyCustomerActivity.this.getString(R.string.dob_missing)+"\n";
		else if(!DateFormat.isValidDate(dobString, "dd/MM/yyyy"))
			errors+=VerifyCustomerActivity.this.getString(R.string.dob_invalid)+"\n";
		if(errors.length()>0 )
			return errors;
		else
			return null;
		
	}
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_verify_customer);
		
		Button verifyCustomer = (Button) findViewById(R.id.submit_button_verify_customer);
		Button datePicker = (Button) findViewById(R.id.date_of_birth_date_picker_verify_customer);
		mobileNumber = (EditText) findViewById(R.id.mobile_number_edit_text_verify_customer);
		dob = (EditText) findViewById(R.id.date_of_birth_edit_text_verify_customer);
		dob.setOnEditorActionListener(this);
		verifyCustomer.setOnClickListener(listener);
		datePicker.setOnClickListener(listener);
	}
	
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.help_menu, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.menu_help:
			Intent intent = new Intent(this,ShowHelpActivity.class);
			intent.putExtra("page", "help_customer_verify.html");
			startActivity(intent);
			return true;
		}
		return super.onOptionsItemSelected(item);
	}



	@Override
	public void onFinishPinDialog(String pin) {
		Hashtable params = new Hashtable();
		params.put(OboServicesConstants.DATA_FIELD_PIN, pin);
		params.put(OboServicesConstants.DATA_FIELD_PHONE_NUMBER, Utils.COUNTRY_CODE + mobileNumber.getText().toString());
		params.put(OboServicesConstants.DATA_FIELD_DATE_BACK, dob.getText().toString());
		OboServices services = new OboServices(this, this);
		services.verifyCustomer(params);
		
	}
	
	
	public void onOboServicesResult(int serviceType, Object result,
			OboServices svcObj) {
		if(serviceType == OboServicesConstants.METHOD_VERIFY_CUSTOMER) {
			Log.d(Utils.LOG_TAG, result.toString());
			CustomerVerification customerVerification = new CustomerVerification();
			customerVerification.setFirstName((String) OboServices.getParam((Hashtable) result, OboServicesConstants.DATA_FIELD_FIRST_NAME));
			customerVerification.setLastName((String) OboServices.getParam((Hashtable) result, OboServicesConstants.DATA_FIELD_LAST_NAME));
			customerVerification.setMobileNumber((String) OboServices.getParam((Hashtable) result, OboServicesConstants.DATA_FIELD_PHONE_NUMBER));
			customerVerification.setRegistrationDate((String) OboServices.getParam((Hashtable) result, OboServicesConstants.DATA_FIELD_DATE));
			customerVerification.setProgramCode((String) OboServices.getParam((Hashtable) result, OboServicesConstants.DATA_FIELD_PROGRAM_CODE));
			customerVerification.setStatus((String) OboServices.getParam((Hashtable) result, OboServicesConstants.DATA_FIELD_STATUS));
			customerVerification.setIdType1((String) OboServices.getParam((Hashtable) result, OboServicesConstants.DATA_FIELD_ID_TYPE1));
			customerVerification.setIdValue1((String) OboServices.getParam((Hashtable) result, OboServicesConstants.DATA_FIELD_ID_VALUE1));
			customerVerification.setIdType2((String) OboServices.getParam((Hashtable) result, OboServicesConstants.DATA_FIELD_ID_TYPE2));
			customerVerification.setIdValue2((String) OboServices.getParam((Hashtable) result, OboServicesConstants.DATA_FIELD_ID_VALUE2));
			Intent intent = new Intent(VerifyCustomerActivity.this, VerifyCustomerResult.class);
			intent.putExtra(Utils.CUSTOMER_VERIFICATION, customerVerification);
			startActivity(intent);
		}
	}

	@Override
	public void onOboServicesError(int serviceType, Exception e,
			OboServices svcObj) {
		if(serviceType == OboServicesConstants.METHOD_VERIFY_CUSTOMER) {
			PopUpUtil.error(this, getString(R.string.failed_to_verify_customer, e.getMessage()));
		}
	}
	private void handleSubmit() {
		String error = validateSubmit();
		if(error != null){
			PopUpUtil.error(VerifyCustomerActivity.this, error);
			return;
		}
		FragmentManager fm = getSupportFragmentManager();
		EnterPinDialog enterPinDialog = new EnterPinDialog();
		enterPinDialog.show(fm, "fragment_enter_pin");
	}
	

}
