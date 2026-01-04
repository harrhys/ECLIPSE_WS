package com.obopay.obopayagent;

import java.util.Hashtable;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;

import com.obopay.obopayagent.EnterPinDialog.EnterPinDialogListener;
import com.obopay.obopayagent.domain.CustomerDeposit;
import com.obopay.obopayagent.restclient.OboServices;
import com.obopay.obopayagent.restclient.OboServicesConstants;
import com.obopay.obopayagent.restclient.OboServicesListener;
import com.obopay.obopayagent.utils.PopUpUtil;
import com.obopay.obopayagent.utils.Utils;
public class DepositMoneyActivity extends FragmentActivity implements 
EnterPinDialogListener, OboServicesListener, OnEditorActionListener {
	private EditText mobileNumber;
	private EditText amountDeposit;
	private String pin;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_deposit_money);
		Button submit = (Button) findViewById(R.id.submit_button_deposit_money);
		mobileNumber = (EditText) findViewById(R.id.mobile_number_edit_text_deposit_money);
		amountDeposit = (EditText) findViewById(R.id.amount_to_deposit_edit_text);
		amountDeposit.setOnEditorActionListener(this);
		submit.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				handleSubmit();
			}
		});
	}
	
	@Override
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
			intent.putExtra("page", "help_deposit_money.html");
			startActivity(intent);
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		switch (requestCode) {
		case (Utils.DEPOSIT): {
			if (resultCode == Activity.RESULT_OK) {
				setResult(Activity.RESULT_OK, data);
				finish();
			}
			break;
		}
		}
	}

    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        if (EditorInfo.IME_ACTION_DONE == actionId) {
        	handleSubmit();
        	return true;
        }
        return false;
    }
	private String validateSubmit(){
			String mobileNumberString = mobileNumber.getText().toString().trim();
			String amountDepositString = amountDeposit.getText().toString().trim();
			String errors = "";
			if(mobileNumberString.length() == 0)
				errors += DepositMoneyActivity.this.getString(R.string.mobile_number_missing)+"\n";
			else if(mobileNumber.length() !=10)
				errors += DepositMoneyActivity.this.getString(R.string.invalid_mobile_number)+"\n";
			if(amountDepositString.length() == 0)
				errors += DepositMoneyActivity.this.getString(R.string.amount_to_deposit_missing)+"\n";
			else{
				try {
					int amount = Integer.parseInt(amountDepositString);
					if(amount <= 0) {
						errors += DepositMoneyActivity.this.getString(R.string.amount_to_deposit_should_be_greater_than_zero)+"\n";
					}
				} catch(NumberFormatException e) {
					errors += DepositMoneyActivity.this.getString(R.string.amount_to_deposit_should_be_number)+"\n";
				}
			}
			
			if(errors.length()>0)
				return errors;
			else
				return null;
		}
	
	@Override
	public void onFinishPinDialog(String pin) {
		this.pin = pin;
		Hashtable params = new Hashtable();
		params.put(OboServicesConstants.DATA_FIELD_PIN, pin);
		params.put(OboServicesConstants.DATA_FIELD_PHONE_NUMBER, Utils.COUNTRY_CODE + mobileNumber.getText().toString());
		params.put(OboServicesConstants.DATA_FIELD_AMT, amountDeposit.getText().toString());
		OboServices services = new OboServices(this, this);
		services.getDepositUserDetails(params);
	}

	
	public void onOboServicesResult(int serviceType, Object result,
			OboServices svcObj) {
		if(serviceType == OboServicesConstants.METHOD_DEPOSIT_USER_DETAILS) {
			Log.d(Utils.LOG_TAG, result.toString());
			String firstName = (String) OboServices.getParam((Hashtable) result, OboServicesConstants.DATA_FIELD_FIRST_NAME);
			String lastName = (String) OboServices.getParam((Hashtable) result, OboServicesConstants.DATA_FIELD_LAST_NAME);
			String status = (String) OboServices.getParam((Hashtable) result, OboServicesConstants.DATA_FIELD_STATUS);
			String mobileNumber = (String) OboServices.getParam((Hashtable) result, OboServicesConstants.DATA_FIELD_PHONE_NUMBER);
			String productName = (String) OboServices.getParam((Hashtable) result, OboServicesConstants.DATA_FIELD_PROGRAM_CODE);
			
			CustomerDeposit deposit = new CustomerDeposit();
			deposit.setFirstName(firstName);
			deposit.setLastName(lastName);
			deposit.setMobileNumber(mobileNumber);
			deposit.setAmount(amountDeposit.getText().toString());
			deposit.setPin(pin);
			deposit.setProgramCode(productName);
			Intent intent = new Intent(DepositMoneyActivity.this, DepositMoneyViewActivity.class);
			intent.putExtra(Utils.CUSTOMER_DEPOSIT, deposit);
			startActivityForResult(intent, Utils.DEPOSIT);
		}
	}

	@Override
	public void onOboServicesError(int serviceType, Exception e,
			OboServices svcObj) {
		if(serviceType == OboServicesConstants.METHOD_DEPOSIT_USER_DETAILS) {
			PopUpUtil.error(this, getString(R.string.failed_to_deposit_money, e.getMessage()));
		}
	}

	private void handleSubmit() {
		String error = validateSubmit();
		if(error != null){
			PopUpUtil.error(DepositMoneyActivity.this, error);
			return;
		}
		InputMethodManager inputManager = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
		inputManager.toggleSoftInput(0, 0);
		FragmentManager fm = getSupportFragmentManager();
		EnterPinDialog enterPinDialog = new EnterPinDialog();
		enterPinDialog.show(fm, "fragment_enter_pin");
	}
}
