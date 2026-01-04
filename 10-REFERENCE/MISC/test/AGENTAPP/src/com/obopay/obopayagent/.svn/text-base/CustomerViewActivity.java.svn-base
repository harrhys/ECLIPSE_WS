package com.obopay.obopayagent;

import java.util.Hashtable;
import java.util.Vector;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.obopay.obopayagent.EnterPinDialog.EnterPinDialogListener;
import com.obopay.obopayagent.domain.Customer;
import com.obopay.obopayagent.restclient.Metadata;
import com.obopay.obopayagent.restclient.MetadataException;
import com.obopay.obopayagent.restclient.OboServices;
import com.obopay.obopayagent.restclient.OboServicesConstants;
import com.obopay.obopayagent.restclient.OboServicesListener;
import com.obopay.obopayagent.utils.PopUpUtil;
import com.obopay.obopayagent.utils.PreferenceManager;
import com.obopay.obopayagent.utils.Utils;

public class CustomerViewActivity extends FragmentActivity implements 
EnterPinDialogListener, OboServicesListener {
	private Customer customer;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_customer_view);
		customer = getIntent().getParcelableExtra(Utils.CUSTOMER);
		
		TextView productTextView = (TextView) findViewById(R.id.product_customer_view_text_view);
		TextView mobileNumber = (TextView) findViewById(R.id.mobile_number_customer_view_text_view);
		TextView firstName = (TextView) findViewById(R.id.first_name_text_view);
		TextView lastName = (TextView) findViewById(R.id.last_name_text_view);
		TextView dob = (TextView) findViewById(R.id.date_of_birth_text_view);
		TextView languageTextView = (TextView) findViewById(R.id.language_customer_view_text_view);
		TextView genderTextView = (TextView) findViewById(R.id.gender_text_view_customer_view);
		TextView idTypeTextView = (TextView) findViewById(R.id.id_type_customer_view_text_view);
		TextView idNumberTextView = (TextView) findViewById(R.id.id_number_customer_view_text_view);
		TextView registrationFeesTextView = (TextView) findViewById(R.id.registration_fees_customer_view_text_view);
		TextView amountTextView = (TextView) findViewById(R.id.amount_customer_view_text_view);
		TextView atmCardRefNumberTextView = (TextView) findViewById(R.id.atm_card_ref_number_customer_view_text_view);
		TextView promoterCode = (TextView) findViewById(R.id.promoter_code_text_view);
		
		productTextView.setText(customer.getProduct());
		mobileNumber.setText(customer.getMobileNumber());
		firstName.setText(customer.getFirstName());
		lastName.setText(customer.getLastName());
		dob.setText(customer.getDateOfBirth());
		languageTextView.setText(customer.getLanguage());
		genderTextView.setText(customer.getGender());
		idTypeTextView.setText(customer.getIdType());
		idNumberTextView.setText(customer.getIdNo());
		registrationFeesTextView.setText(customer.getRegistrationFees()+"");
		amountTextView.setText(customer.getAmount()+"");
		atmCardRefNumberTextView.setText(customer.getAtmCardRefNo());
		promoterCode.setText(customer.getPromoterCode());
		
		Integer registrationType = PreferenceManager.getRegistrationType(CustomerViewActivity.this);
		if(customer.getProduct().equalsIgnoreCase(getString(R.string.lite_product_name))){
			TextView amountText = (TextView) findViewById(R.id.amount_text_customer_view);
			TextView atmCard = (TextView) findViewById(R.id.atm_card_text_customer_view_text_view);
			atmCardRefNumberTextView.setVisibility(View.GONE);
			atmCard.setVisibility(View.GONE);
			if(registrationType.equals(1)) {
				amountTextView.setVisibility(View.GONE);
				amountText.setVisibility(View.GONE);
			}
		}else if (customer.getProduct().equalsIgnoreCase(getString(R.string.smart_product_name))){
			TextView atmCard = (TextView) findViewById(R.id.atm_card_text_customer_view_text_view);
			TextView idNumber = (TextView) findViewById(R.id.id_number_text_customer_view_text_view);
			TextView idType = (TextView) findViewById(R.id.id_type_text_customer_view_text_view);
			idTypeTextView.setVisibility(View.GONE);
			idNumberTextView.setVisibility(View.GONE);
			idNumber.setVisibility(View.GONE);
			idType.setVisibility(View.GONE);
			if(!registrationType.equals(3)) {
				atmCardRefNumberTextView.setVisibility(View.GONE);
				atmCard.setVisibility(View.GONE);
			}
			if(registrationType.equals(1)) {
				TextView amountText = (TextView) findViewById(R.id.amount_text_customer_view);
				amountTextView.setVisibility(View.GONE);
				amountText.setVisibility(View.GONE);
			}
		}else if(customer.getProduct().equalsIgnoreCase(getString(R.string.merchant_product_name))){
			TextView idNumber = (TextView) findViewById(R.id.id_number_text_customer_view_text_view);
			TextView idType = (TextView) findViewById(R.id.id_type_text_customer_view_text_view);
			TextView amountText = (TextView) findViewById(R.id.amount_text_customer_view);
			TextView registrationFeesText = (TextView) findViewById(R.id.registration_fees_text_customer_view);
			TextView atmCardRefNoText = (TextView) findViewById(R.id.atm_card_text_customer_view_text_view);
			
			amountText.setVisibility(View.GONE);
			registrationFeesText.setVisibility(View.GONE);
			atmCardRefNoText.setVisibility(View.GONE);
			amountTextView.setVisibility(View.GONE);
			registrationFeesTextView.setVisibility(View.GONE);
			atmCardRefNumberTextView.setVisibility(View.GONE);
			idTypeTextView.setVisibility(View.GONE);
			idNumberTextView.setVisibility(View.GONE);
			idNumber.setVisibility(View.GONE);
			idType.setVisibility(View.GONE);
		}
		
		Button submit = (Button) findViewById(R.id.submit_button_customer_view);
		submit.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				FragmentManager fm = getSupportFragmentManager();
				EnterPinDialog enterPinDialog = new EnterPinDialog();
		        enterPinDialog.show(fm, "fragment_enter_pin");
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
			intent.putExtra("page", "help_customer_confirmation.html");
			startActivity(intent);
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	@Override
	public void onFinishPinDialog(String pin) {
		Hashtable params = new Hashtable();
		params.put(OboServicesConstants.DATA_FIELD_PIN, pin);
		params.put(OboServicesConstants.DATA_FIELD_PROG_TYPE, customer.getProgramType());
		params.put(OboServicesConstants.DATA_FIELD_PHONE_NUMBER, Utils.COUNTRY_CODE + customer.getMobileNumber());
		params.put(OboServicesConstants.DATA_FIELD_FORM_NUMBER, customer.getPromoterCode());
		params.put(OboServicesConstants.DATA_FIELD_FIRST_NAME, customer.getFirstName());
		params.put(OboServicesConstants.DATA_FIELD_LAST_NAME, customer.getLastName());
		params.put(OboServicesConstants.DATA_FIELD_DATE_BACK, customer.getDateOfBirth());
		params.put(OboServicesConstants.DATA_FIELD_LANG, customer.getLanguage());
		params.put(OboServicesConstants.DATA_FIELD_GENDER_TYPE, customer.getGenderType());
		params.put(OboServicesConstants.DATA_FIELD_ID_TYPES, customer.getIdTypeValue());
		params.put(OboServicesConstants.DATA_FIELD_ID_NUMBER, customer.getIdNo());
		params.put(OboServicesConstants.DATA_FIELD_REGISTRATION_FEE, customer.getRegistrationFees());
		params.put(OboServicesConstants.DATA_FIELD_CASH_IN_AMOUNT, customer.getAmount());
		params.put(OboServicesConstants.DATA_FIELD_ATM_CARD_REF_NUMBER, customer.getAtmCardRefNo());
		params.put(OboServicesConstants.DATA_FIELD_REGISTRATION_TYPE, customer.getRegistrationType());
		OboServices services = new OboServices(this, this);
		services.newCustomer(params);
		
	}
	
	
	public void onOboServicesResult(int serviceType, Object result,
			OboServices svcObj) {
		if(serviceType == OboServicesConstants.METHOD_NEW_CUSTOMER) {
			Log.d(Utils.LOG_TAG, result.toString());
			Vector vecMet = (Vector)OboServices.getParam((Hashtable) result, OboServicesConstants.DATA_FIELD_METADATA);
			if(!vecMet.isEmpty()) {
				Metadata metadata = (Metadata)vecMet.elementAt(0);
				if(metadata.name.equals("MESSAGE")) {
					try {
						PopUpUtil.info(this, getString(R.string.new_customer), metadata.getValueAsString(), getString(R.string.done), new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								Intent resultIntent = new Intent();
								resultIntent.putExtra(Utils.CREATE_CUSTOMER_RESPONSE, true);
								setResult(Activity.RESULT_OK, resultIntent);
								finish();
							}
						}) ;
					} catch (MetadataException e) {
						Log.e(Utils.LOG_TAG, "Failed to read response data:", e);
					}
				}
			}
		}
	}

	@Override
	public void onOboServicesError(int serviceType, Exception e,
			OboServices svcObj) {
		if(serviceType == OboServicesConstants.METHOD_NEW_CUSTOMER) {
			PopUpUtil.error(this, getString(R.string.failed_to_create_customer, e.getMessage()));
		}
	}

}
