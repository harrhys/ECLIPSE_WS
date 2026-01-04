package com.obopay.obopayagent;

import java.util.Map;
import java.util.Map.Entry;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;

import com.obopay.obopayagent.domain.Customer;
import com.obopay.obopayagent.utils.PreferenceManager;
import com.obopay.obopayagent.utils.Utils;

public class CustomerProgramSelectionActivity extends Activity {
	private Customer customer;
	private OnClickListener listener = new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			Intent intent = new Intent(CustomerProgramSelectionActivity.this, CustomerActivity.class);
			customer.setProduct(((Button)v).getText().toString());
			customer.setProgramType((Integer) v.getTag());
			customer.setPromoterCode("" + customer.getProgramType());
			Map<String, Integer> registrationFeeMap = PreferenceManager.getRegistrationFee(CustomerProgramSelectionActivity.this);
			customer.setRegistrationFees(registrationFeeMap.get(customer.getProgramType()+""));

			intent.putExtra(Utils.CUSTOMER, customer);
			startActivityForResult(intent, Utils.CREATE_CUSTOMER);
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
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		if(customer == null){
			customer = new Customer();
			customer.setMobileNumber("");
			customer.setDateOfBirth("");
			customer.setFirstName("");
			customer.setLastName("");
			customer.setLanguage("");
			customer.setProduct("");
			customer.setProgramType(-1);
			customer.setPromoterCode("");
			customer.setGender("");
			customer.setIdType("");
			customer.setIdNo("");
			customer.setRegistrationFees(0);
			customer.setAmount(0);
			customer.setAtmCardRefNo("");
			customer.setGenderType("");
			customer.setIdTypeValue(0);
			customer.setRegistrationType(0);
		}		
		setContentView(R.layout.activity_customer_program_selection);
		
		Map<Integer, String> programTypes = PreferenceManager.getProgramTypes(this);
		
		for (Entry<Integer, String> programTypeEntry : programTypes.entrySet()) {
			Button programButton = (Button) getLayoutInflater().inflate(R.layout.template_program_button, null);
			programButton.setText(programTypeEntry.getValue());
			programButton.setTag(programTypeEntry.getKey());
			programButton.setOnClickListener(listener);
			int height = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 50, getResources().getDisplayMetrics());
			int leftAndRight = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 30, getResources().getDisplayMetrics());
			int topAndBottom = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 10, getResources().getDisplayMetrics());
			LayoutParams layoutParams = new LayoutParams(LayoutParams.FILL_PARENT, height);
			layoutParams.setMargins(leftAndRight, topAndBottom, leftAndRight, topAndBottom);
			programButton.setLayoutParams(layoutParams);
			((LinearLayout)findViewById(R.id.programs_container)).addView(programButton);
		}
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
			intent.putExtra("page", "help_select_program_type.html");
			startActivity(intent);
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

}
