package com.obopay.obopayagent;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

import com.obopay.obopayagent.domain.Customer;
import com.obopay.obopayagent.utils.PopUpUtil;
import com.obopay.obopayagent.utils.Utils;

public class AtmCardIssuanceActivity extends Activity {

	Button button;
	EditText atmRefNo;
	Customer customer;
	
	private OnClickListener listener = new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			if(v.getId() == R.id.submit_button_atm_card_issuance){
				String errorMsg = validateSubmit();
				if(errorMsg != null){
					PopUpUtil.error(AtmCardIssuanceActivity.this, errorMsg);
					return;
				}
				
				customer.setAtmCardRefNo(atmRefNo.getText().toString().trim());
				Intent intent = new Intent(AtmCardIssuanceActivity.this,CustomerViewActivity.class);
				intent.putExtra(Utils.CUSTOMER, customer);
				startActivityForResult(intent, Utils.CREATE_CUSTOMER);
			}
		}

		private String validateSubmit() {
			String refNo = atmRefNo.getText().toString().trim();
			String errors="";
			String cardRefNumberLength = AtmCardIssuanceActivity.this.getString(R.string.atm_card_ref_number_length);
			if(refNo.length() == 0)
				errors += AtmCardIssuanceActivity.this.getString(R.string.atm_card_ref_number_missing)+"\n";
			if(refNo.length() != Integer.parseInt(cardRefNumberLength))
				errors += AtmCardIssuanceActivity.this.getString(R.string.atm_card_ref_number_is_invalid)+"\n";
			
			if(errors.length()>0)
				return errors;
			
			else
				return null;
		}
	};
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_atm_card_issuance);
		customer = getIntent().getParcelableExtra(Utils.CUSTOMER);
		atmRefNo = (EditText) findViewById(R.id.atm_card_ref_no_edit_text_atm_card_issuance);
		button = (Button) findViewById(R.id.submit_button_atm_card_issuance);
		button.setOnClickListener(listener);
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
