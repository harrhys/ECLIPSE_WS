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
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;

import com.obopay.obopayagent.domain.CustomerRemittance;
import com.obopay.obopayagent.utils.PopUpUtil;
import com.obopay.obopayagent.utils.Utils;

public class ViralRemitMoneyBeneficiaryIdsActivity extends Activity implements OnEditorActionListener {
	
	private Spinner idType1;
	private Spinner idType2;
	private EditText idValue1;
	private EditText idValue2;
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
			intent.putExtra("page", "help_remit_money_beneficiary_ids.html");
			startActivity(intent);
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	private String validateSubmit() {
		String idType1String = idType1.getSelectedItem().toString().trim();
		String idValue1String = idValue1.getText().toString().trim();
		String idType2String = idType2.getSelectedItem().toString().trim();
		String idValue2String = idValue2.getText().toString().trim();
		String errors = "";
		if(idType1String.length() == 0)
			errors += getString(R.string.id_type_1_missing)+"\n";
		if(idValue1String.length() == 0)
			errors +=getString(R.string.id_value_1_missing)+"\n";
		if(idType2String.length() == 0)
			errors += getString(R.string.id_type_2_missing)+"\n";
		if(idValue2String.length() == 0)
			errors +=getString(R.string.id_value_2_missing)+"\n";
		if(errors.length()>0)
			return errors;
		else
			return null;
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_viral_remit_money_beneficiary_ids);
		remittance = getIntent().getParcelableExtra(Utils.CUSTOMER_REMITTANCE);
		idType1 = (Spinner) findViewById(R.id.beneficiary_id_type_1);
		idValue1 = (EditText) findViewById(R.id.beneficiary_id_value_1);
		idType2 = (Spinner) findViewById(R.id.beneficiary_id_type_2);
		idValue2 = (EditText) findViewById(R.id.beneficiary_id_value_2);
		idValue2.setOnEditorActionListener(this);
		this.submit = (Button) findViewById(R.id.submit_button);
		submit.setOnClickListener(listener);
	}

	private void handleSubmit() {
		String errorMsg = validateSubmit();
		if(errorMsg != null){
			PopUpUtil.error(ViralRemitMoneyBeneficiaryIdsActivity.this, errorMsg);
			return;
		}
		remittance.setIdType1(idType1.getSelectedItem().toString().trim());
		remittance.setIdValue1(idValue1.getText().toString().trim());
		remittance.setIdType2(idType2.getSelectedItem().toString().trim());
		remittance.setIdValue2(idValue2.getText().toString().trim());
		Intent intent = new Intent(ViralRemitMoneyBeneficiaryIdsActivity.this,RemitMoneyView.class);
		intent.putExtra(Utils.CUSTOMER_REMITTANCE, remittance);
		startActivityForResult(intent, Utils.REMIT);
	}
}