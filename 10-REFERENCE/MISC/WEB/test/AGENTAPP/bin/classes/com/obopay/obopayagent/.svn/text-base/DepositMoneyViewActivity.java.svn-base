package com.obopay.obopayagent;

import java.util.Hashtable;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Vector;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.obopay.obopayagent.domain.CustomerDeposit;
import com.obopay.obopayagent.restclient.Metadata;
import com.obopay.obopayagent.restclient.OboServices;
import com.obopay.obopayagent.restclient.OboServicesConstants;
import com.obopay.obopayagent.restclient.OboServicesListener;
import com.obopay.obopayagent.utils.PopUpUtil;
import com.obopay.obopayagent.utils.Utils;

public class DepositMoneyViewActivity extends Activity implements OboServicesListener{

	private CustomerDeposit customerDeposit;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_deposit_money_view);
		customerDeposit = getIntent().getParcelableExtra(Utils.CUSTOMER_DEPOSIT);
		TextView mobileNumberTextView = (TextView) findViewById(R.id.mobile_number_text_view);
		TextView firstNameTextView = (TextView) findViewById(R.id.first_name_text_view);
		TextView lastNameTextView = (TextView) findViewById(R.id.last_name_text_view);
		TextView amountTextView = (TextView) findViewById(R.id.amount_deposited_text_view);
		TextView productTextView = (TextView) findViewById(R.id.product_deposit_money_view_text_view);
		
		Button submitButton = (Button)findViewById(R.id.submit_button);
		mobileNumberTextView.setText(customerDeposit.getMobileNumber());
		firstNameTextView.setText(customerDeposit.getFirstName());
		lastNameTextView.setText(customerDeposit.getLastName());
		amountTextView.setText(customerDeposit.getAmount());
		productTextView.setText(customerDeposit.getProgramCode());
		submitButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Hashtable params = new Hashtable();
				params.put(OboServicesConstants.DATA_FIELD_PIN, customerDeposit.getPin());
				params.put(OboServicesConstants.DATA_FIELD_PHONE_NUMBER, customerDeposit.getMobileNumber());
				params.put(OboServicesConstants.DATA_FIELD_AMT, customerDeposit.getAmount());
				OboServices services = new OboServices(DepositMoneyViewActivity.this, DepositMoneyViewActivity.this);
				services.depositMoney(params);

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
			intent.putExtra("page", "help_deposit_confirmation.html");
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
	public void onOboServicesResult(int serviceType, Object result,
			OboServices svcObj) {
		if(serviceType == OboServicesConstants.METHOD_DEPOSIT_MONEY) {
			Log.d(Utils.LOG_TAG, result.toString());
			Map<String, String> responseMetaData = new LinkedHashMap<String, String>();
			Vector vec = (Vector)OboServices.getParam((Hashtable) result, OboServicesConstants.DATA_FIELD_METADATA);
			for (int i = 0; i < vec.size(); i++) {
				Metadata metadata = (Metadata)vec.elementAt(i);
				responseMetaData.put(metadata.label, metadata.value.toString());
			}
			CustomerDeposit customerDeposit = new CustomerDeposit();
			customerDeposit.setResponseMetaData(responseMetaData);
			Intent intent = new Intent(DepositMoneyViewActivity.this, DepositMoneyResult.class);
			intent.putExtra(Utils.CUSTOMER_DEPOSIT, customerDeposit);
			startActivityForResult(intent, Utils.DEPOSIT);
		}
	}

	@Override
	public void onOboServicesError(int serviceType, Exception e,
			OboServices svcObj) {
		if(serviceType == OboServicesConstants.METHOD_DEPOSIT_MONEY) {
			PopUpUtil.error(this, getString(R.string.failed_to_deposit_money, e.getMessage()));
		}
	}
}
