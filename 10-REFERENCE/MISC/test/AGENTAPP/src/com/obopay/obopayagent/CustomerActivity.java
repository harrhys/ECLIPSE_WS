package com.obopay.obopayagent;

import java.util.ArrayList;
import java.util.Map;
import java.util.Map.Entry;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.text.InputFilter;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.obopay.obopayagent.domain.Customer;
import com.obopay.obopayagent.utils.DateFormat;
import com.obopay.obopayagent.utils.PopUpUtil;
import com.obopay.obopayagent.utils.PreferenceManager;
import com.obopay.obopayagent.utils.Utils;

public class CustomerActivity extends FragmentActivity {
	

	private EditText dob = null;
	private EditText mobileNumber = null;
	private EditText firstName = null;
	private EditText lastName = null;
	private Spinner gender = null;
	private Spinner idType = null;
	private EditText idNumber = null;
	private EditText promoterCode = null;
	private Customer customer;
	private Button submit = null;
	
	private OnClickListener listener = new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			if(v.getId() == R.id.submit_button_new_customer){
				String errorMsg = validateSubmit();
				if(errorMsg != null){
					PopUpUtil.error(CustomerActivity.this, errorMsg);
					return;
				}
				customer.setDateOfBirth(dob.getText().toString().trim());
				customer.setLastName(lastName.getText().toString().trim());
				customer.setFirstName(firstName.getText().toString().trim());
				customer.setMobileNumber(mobileNumber.getText().toString());
				customer.setGender(gender.getSelectedItem().toString());
				customer.setIdNo(idNumber.getText().toString().trim());
				customer.setIdType(idType.getSelectedItem().toString());
				customer.setPromoterCode(promoterCode.getText().toString().trim());
				
				Map<String, String> genderTypes = PreferenceManager.getGenderTypes(CustomerActivity.this);
				for (Entry<String, String> genderType : genderTypes.entrySet()) {
					if(genderType.getValue().equalsIgnoreCase(customer.getGender())){
						customer.setGenderType(genderType.getKey());
						break;
					}
				}
				
				if(idType.isShown()){
					Map<Integer, String> idTypesMap = PreferenceManager.getIdTypes(CustomerActivity.this);
					for (Entry<Integer, String> id : idTypesMap.entrySet()) {
						if(id.getValue().equalsIgnoreCase(customer.getIdType())){
							customer.setIdTypeValue(id.getKey());
							break;
						}
					}
				}else{
					customer.setIdTypeValue(0);
				}
				
				Intent intent = new Intent(CustomerActivity.this,CustomerLanguageSelectionActivity.class);
				intent.putExtra(Utils.CUSTOMER, customer);
				startActivityForResult(intent, Utils.CREATE_CUSTOMER);
			}else if(v.getId() == R.id.date_of_birth_date_picker_new_customer){
				DialogFragment newFragment = new DatePickerFragment(dob);
			    newFragment.show(getSupportFragmentManager(), "datePicker");
			}
			
		}	
	};
	
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		switch (requestCode) {
		case (Utils.CREATE_CUSTOMER): {
			if (resultCode == Activity.RESULT_OK) {
				setResult(Activity.RESULT_OK, data);
				finish();
			}
			break;
		}
		}
	}
	
	private String validateSubmit() {
		String mobileNumberString = mobileNumber.getText().toString().trim();
		String firstNameString = firstName.getText().toString().trim();
		String lastNameString = lastName.getText().toString().trim();
		String dobString = dob.getText().toString().trim();
		String idNumberString = idNumber.getText().toString().trim();
		Integer idNoMinLength = PreferenceManager.getIdTypeMinLength(CustomerActivity.this);
		Integer idNoMaxLength = PreferenceManager.getIdTypeMaxLength(CustomerActivity.this);
		String errors = "";
		
		if(mobileNumberString.length() == 0)
			errors += CustomerActivity.this.getString(R.string.mobile_number_missing)+"\n";
		else if(mobileNumber.length() !=10)
			errors += CustomerActivity.this.getString(R.string.invalid_mobile_number)+"\n";
		
		if(firstNameString.length() == 0)
			errors += CustomerActivity.this.getString(R.string.first_name_missing)+"\n";
		if(lastNameString.length() == 0)
			errors += CustomerActivity.this.getString(R.string.last_name_missing)+"\n";
		if(dobString.length() == 0)
			errors += CustomerActivity.this.getString(R.string.dob_missing)+"\n";
		else if(!DateFormat.isValidDate(dobString, "dd/MM/yyyy"))
			errors+=CustomerActivity.this.getString(R.string.dob_invalid)+"\n";
		if(idNumber.isShown()){
			if(idNumberString.length() == 0)
				errors += CustomerActivity.this.getString(R.string.id_number_missing)+"\n";
			else if(idNumberString.length() < idNoMinLength || idNumberString.length() > idNoMaxLength){
				errors += CustomerActivity.this.getString(R.string.invalid_id_number,idNoMinLength,idNoMaxLength)+"\n";
			}
		}
		if(errors.length()>0)
			return errors;
		
		else
			return null;
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_customer);
		customer = getIntent().getParcelableExtra(Utils.CUSTOMER);
		this.mobileNumber = (EditText) findViewById(R.id.mobile_number_edit_text_new_customer);
		this.firstName = (EditText) findViewById(R.id.first_name_edit_text_new_customer);
		this.lastName = (EditText) findViewById(R.id.last_name_edit_text_new_customer);
		this.dob = (EditText) findViewById(R.id.date_of_birth_edit_text_new_customer);
		this.submit = (Button) findViewById(R.id.submit_button_new_customer);
		Button datePicker = (Button) findViewById(R.id.date_of_birth_date_picker_new_customer);
		this.gender = (Spinner) findViewById(R.id.gender_spinner_new_customer);
		this.idType = (Spinner) findViewById(R.id.id_type_spinner_new_customer);
		this.idNumber = (EditText) findViewById(R.id.id_number_edit_text_new_customer);
		this.promoterCode = (EditText) findViewById(R.id.promoter_code_edit_text_new_customer);

		Integer idNoMaxLength = PreferenceManager.getIdTypeMaxLength(CustomerActivity.this);

		Map<String, String> genderTypes = PreferenceManager.getGenderTypes(this);
		ArrayList<String>genders = new ArrayList<String>();
		for (Entry<String, String> genderType : genderTypes.entrySet()) {
			genders.add(genderType.getValue());
		}
		
		Map<Integer, String> idTypesMap = PreferenceManager.getIdTypes(this);
		ArrayList<String>ids = new ArrayList<String>();
		for (Entry<Integer, String> id : idTypesMap.entrySet()) {
			ids.add(id.getValue());
		}
		
		ArrayAdapter<String> genderAdapter = new ArrayAdapter<String>(CustomerActivity.this, android.R.layout.simple_list_item_1, genders);
		ArrayAdapter<String> idTypeAdapter = new ArrayAdapter<String>(CustomerActivity.this, android.R.layout.simple_list_item_1, ids);
		
		gender.setAdapter(genderAdapter);
		idType.setAdapter(idTypeAdapter);
		
		InputFilter[] filters = new InputFilter[1];
		filters[0] = new InputFilter.LengthFilter(idNoMaxLength); //Filter to idNoMaxLength characters
		idNumber.setFilters(filters);
		
		idNumber.setImeOptions(EditorInfo.IME_ACTION_DONE);
		if(customer.getProduct().equalsIgnoreCase(getString(R.string.smart_product_name))) {
			TextView idNumberTextView = (TextView) findViewById(R.id.id_number_text_view_new_customer);
			TextView idTypeTextView = (TextView) findViewById(R.id.id_type_text_view_new_customer);
			idNumberTextView.setVisibility(View.GONE);
			idTypeTextView.setVisibility(View.GONE);
			idType.setVisibility(View.GONE);
			idNumber.setVisibility(View.GONE);
			dob.setImeOptions(EditorInfo.IME_ACTION_DONE);
		}else if(customer.getProduct().equalsIgnoreCase(getString(R.string.merchant_product_name))) {
			TextView idNumberTextView = (TextView) findViewById(R.id.id_number_text_view_new_customer);
			TextView idTypeTextView = (TextView) findViewById(R.id.id_type_text_view_new_customer);
			
			idNumberTextView.setVisibility(View.GONE);
			idTypeTextView.setVisibility(View.GONE);
			idType.setVisibility(View.GONE);
			idNumber.setVisibility(View.GONE);
			dob.setImeOptions(EditorInfo.IME_ACTION_DONE);
		}
		
		datePicker.setOnClickListener(listener);		
		submit.setOnClickListener(listener);
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
			intent.putExtra("page", "help_new_customer.html");
			startActivity(intent);
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

}