package com.obopay.obopayagent;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
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

import com.obopay.obopayagent.domain.CustomerRemittance;
import com.obopay.obopayagent.utils.PopUpUtil;
import com.obopay.obopayagent.utils.Utils;

public class ViralRemitMoneyBeneficiaryActivity extends Activity implements OnEditorActionListener {
	
	private EditText firstName = null;
	private EditText lastName = null;
	private CustomerRemittance remittance;
	private Button submit = null;
	private OnClickListener listener = new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			handleSubmit();
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
			intent.putExtra("page", "help_remit_money_beneficiary_details.html");
			startActivity(intent);
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	private String validateSubmit() {
		String firstNameString = firstName.getText().toString().trim();
		String lastNameString = lastName.getText().toString().trim();
		String errors = "";
		if(firstNameString.length() == 0)
			errors += ViralRemitMoneyBeneficiaryActivity.this.getString(R.string.first_name_missing)+"\n";
		if(lastNameString.length() == 0)
			errors += ViralRemitMoneyBeneficiaryActivity.this.getString(R.string.last_name_missing)+"\n";
		if(errors.length()>0)
			return errors;
		else
			return null;
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_viral_remit_money_beneficiary);
		remittance = getIntent().getParcelableExtra(Utils.CUSTOMER_REMITTANCE);
		firstName = (EditText) findViewById(R.id.beneficiary_first_name);
		lastName = (EditText) findViewById(R.id.beneficiary_last_name);
		submit = (Button) findViewById(R.id.submit_button);
		lastName.setOnEditorActionListener(this);
		submit.setOnClickListener(listener);
		
	}

	private void handleSubmit() {
		String errorMsg = validateSubmit();
		if(errorMsg != null){
			PopUpUtil.error(ViralRemitMoneyBeneficiaryActivity.this, errorMsg);
			return;
		}
		remittance.setFirstName(firstName.getText().toString().trim());
		remittance.setLastName(lastName.getText().toString().trim());
		Intent intent = new Intent(ViralRemitMoneyBeneficiaryActivity.this,ViralRemitMoneyBeneficiaryIdsActivity.class);
		intent.putExtra(Utils.CUSTOMER_REMITTANCE, remittance);
		startActivityForResult(intent, Utils.REMIT);
	}
}