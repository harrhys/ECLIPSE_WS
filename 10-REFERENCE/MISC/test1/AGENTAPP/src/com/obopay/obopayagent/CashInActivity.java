package com.obopay.obopayagent;

import java.util.Map;
import java.util.Map.Entry;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.obopay.obopayagent.domain.Customer;
import com.obopay.obopayagent.utils.PopUpUtil;
import com.obopay.obopayagent.utils.PreferenceManager;
import com.obopay.obopayagent.utils.Utils;

public class CashInActivity extends Activity {


	private Customer customer;
	private TextView registrationFees;
	private TextView product;
	private EditText amount;
	private int registrationFeeAmount;
	private OnClickListener listener = new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			if(v.getId() == R.id.submit_button_cash_in){
				String errorMsg = validateSubmit();
				if(errorMsg != null){
					PopUpUtil.error(CashInActivity.this, errorMsg);
					return;
				}
				
				String registrationFeesString = registrationFees.getText().toString().trim();
				int registrationFees =0;
				if(null != registrationFeesString && !"".equals(registrationFeesString)){
					registrationFees = Integer.parseInt(registrationFeesString);
				}
				String amountString = amount.getText().toString().trim();
				int amount = Integer.parseInt(amountString);
				customer.setRegistrationFees(registrationFees);
				customer.setAmount(amount);
				Integer registrationType = PreferenceManager.getRegistrationType(CashInActivity.this);
				Intent intent = null;
				if(customer.getProduct().equalsIgnoreCase(getString(R.string.smart_product_name)) &&
						registrationType.equals(3))
					intent = new Intent(CashInActivity.this, AtmCardIssuanceActivity.class);
				else
					intent = new Intent(CashInActivity.this,CustomerViewActivity.class);
				intent.putExtra(Utils.CUSTOMER, customer);
				startActivityForResult(intent, Utils.CREATE_CUSTOMER);
			}
		}

		private String validateSubmit() {
			Integer minCashInAmountIncludingRegistration = 0, minCashInAmount=0;
			Integer maxCashInAmount = 0;
			
			Map<Integer, Integer> minCashInAmountMap = PreferenceManager.getMinCashInAmount(CashInActivity.this);
			for (Entry<Integer, Integer> minCashInEntry : minCashInAmountMap.entrySet()) {
				if(minCashInEntry.getKey() == customer.getProgramType()){
					minCashInAmount = minCashInEntry.getValue();
					break;
				}
			}
			
			Map<Integer, Integer> maxCashInAmountMap = PreferenceManager.getMaxCashInAmount(CashInActivity.this);
			for (Entry<Integer, Integer> maxCashInEntry : maxCashInAmountMap.entrySet()) {
				if(maxCashInEntry.getKey() == customer.getProgramType()){
					maxCashInAmount = maxCashInEntry.getValue();
					break;
				}
			}
			
			minCashInAmountIncludingRegistration = minCashInAmount + registrationFeeAmount;
			
			String amountString = amount.getText().toString().trim();
			String errors = "";
			
			if(amountString.length() == 0)
				errors += CashInActivity.this.getString(R.string.amount_missing)+"\n";
			else if(Integer.parseInt(amountString) < minCashInAmountIncludingRegistration || Integer.parseInt(amountString) > maxCashInAmount){
				errors += CashInActivity.this.getString(R.string.invalid_amount,minCashInAmountIncludingRegistration,minCashInAmount, registrationFeeAmount, maxCashInAmount)+"\n";
			}
			
			if(errors.length()>0)
				return errors;
			
			else
				return null;
		}
	};
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_cash_in);
		customer = getIntent().getParcelableExtra(Utils.CUSTOMER);
		registrationFees = (TextView) findViewById(R.id.registration_fees_text_view_cash_in);
		amount = (EditText) findViewById(R.id.amount_edit_text_cash_in);
		product = (TextView) findViewById(R.id.product_text_view_cash_in);
		Button submit = (Button) findViewById(R.id.submit_button_cash_in);
		submit.setOnClickListener(listener);
		
		product.setText(customer.getProduct());
		registrationFeeAmount = customer.getRegistrationFees();
		registrationFees.setText(registrationFeeAmount+"");
		Integer registrationType = PreferenceManager.getRegistrationType(CashInActivity.this);
		customer.setRegistrationType(registrationType);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
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
}
