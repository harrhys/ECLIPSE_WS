package com.obopay.obopayagent;

import java.util.Hashtable;
import java.util.Vector;

import android.app.Activity;
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
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;

import com.obopay.obopayagent.EnterPinDialog.EnterPinDialogListener;
import com.obopay.obopayagent.domain.CustomerRemittance;
import com.obopay.obopayagent.restclient.Metadata;
import com.obopay.obopayagent.restclient.OboServices;
import com.obopay.obopayagent.restclient.OboServicesConstants;
import com.obopay.obopayagent.restclient.OboServicesListener;
import com.obopay.obopayagent.utils.PopUpUtil;
import com.obopay.obopayagent.utils.Utils;

public class VerifyTransaction extends FragmentActivity implements 
EnterPinDialogListener, OboServicesListener, OnEditorActionListener {

	private EditText mobileNumber;
	private EditText transactionId;
	private String pin;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_verify_transaction);
		Button submit = (Button) findViewById(R.id.submit_button_verify_transaction);
		mobileNumber = (EditText) findViewById(R.id.mobile_number_edit_text_verify_transaction);
		transactionId = (EditText) findViewById(R.id.transaction_id_edit_text_verify_transaction);
		transactionId.setOnEditorActionListener(this);
		submit.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				handleSubmit();
			}
		});
	}
	
	@Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        if (EditorInfo.IME_ACTION_DONE == actionId) {
        	handleSubmit();
        	return true;
        }
        return false;
    }
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		switch (requestCode) {
		case (Utils.REMIT): {
			if (resultCode == Activity.RESULT_OK) {
				setResult(Activity.RESULT_OK, data);
				finish();
			}
			break;
		}
		}
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
			intent.putExtra("page", "help_remit_money_verify_transaction.html");
			startActivity(intent);
			return true;
		}
		return super.onOptionsItemSelected(item);
	}


	private String validateSubmit(){
		String mobileNumberString = mobileNumber.getText().toString().trim();
		String transactionIdString = transactionId.getText().toString().trim();
		String errors = "";
		if(mobileNumberString.length() == 0)
			errors += VerifyTransaction.this.getString(R.string.mobile_number_missing)+"\n";
		else if(mobileNumber.length() !=10)
			errors += VerifyTransaction.this.getString(R.string.invalid_mobile_number)+"\n";
		
		if(transactionIdString.length() == 0)
			errors += VerifyTransaction.this.getString(R.string.transaction_id_missing)+"\n";
		
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
		params.put(OboServicesConstants.DATA_FIELD_TXN_ID, transactionId.getText().toString());
		OboServices services = new OboServices(this, this);
		services.validatingTxnID(params);
	}

	
	public void onOboServicesResult(int serviceType, Object result,
			OboServices svcObj) {
		if(serviceType == OboServicesConstants.METHOD_VALIDATING_TXN_ID) {
			Log.d(Utils.LOG_TAG, result.toString());
			Vector vec = (Vector)OboServices.getParam((Hashtable) result, OboServicesConstants.DATA_FIELD_METADATA);
			CustomerRemittance remittance = new CustomerRemittance();
			for (int i = 0; i < vec.size(); i++) {
				Metadata metadata = (Metadata)vec.elementAt(i);
				Log.d(Utils.LOG_TAG, metadata.label + ":" + (String)metadata.value);
				if(metadata.label.equals("Mobile Number"))
					remittance.setMobileNumber((String) metadata.value);
				if(metadata.label.equals("First Name"))
					remittance.setFirstName((String) metadata.value);
				if(metadata.label.equals("Last Name"))
					remittance.setLastName((String) metadata.value);
				if(metadata.label.equals("Amount"))
					remittance.setAmount((String) metadata.value);
				if(metadata.label.equals("Transaction Id"))
					remittance.setTransactionId((String) metadata.value);
				if(metadata.label.equals("TransactionType"))
					remittance.setTransactionType((String) metadata.value);
				if(metadata.label.equals("IsOTPEnabled")) {
					remittance.setIsOTPEnabled(metadata.value.equals("true"));
					continue;
				}
			}
			remittance.setPin(pin);
			if(remittance.getTransactionType().equals("VSEND")) {
				Intent intent = new Intent(VerifyTransaction.this, ViralRemitMoneyBeneficiaryActivity.class);
				intent.putExtra(Utils.CUSTOMER_REMITTANCE, remittance);
				startActivityForResult(intent, Utils.REMIT);
			} else {
				Intent intent = new Intent(VerifyTransaction.this, RemitMoneyView.class);
				intent.putExtra(Utils.CUSTOMER_REMITTANCE, remittance);
				startActivityForResult(intent, Utils.REMIT);
			}
		}
	}

	@Override
	public void onOboServicesError(int serviceType, Exception e,
			OboServices svcObj) {
		if(serviceType == OboServicesConstants.METHOD_VALIDATING_TXN_ID) {
			PopUpUtil.error(this, getString(R.string.failed_to_verify_transaction, e.getMessage()));
		}
	}

	private void handleSubmit() {
		String error = validateSubmit();
		if(error != null){
			PopUpUtil.error(VerifyTransaction.this, error);
			return;
		}
		FragmentManager fm = getSupportFragmentManager();
		EnterPinDialog enterPinDialog = new EnterPinDialog();
		enterPinDialog.show(fm, "fragment_enter_pin");
	}
}
