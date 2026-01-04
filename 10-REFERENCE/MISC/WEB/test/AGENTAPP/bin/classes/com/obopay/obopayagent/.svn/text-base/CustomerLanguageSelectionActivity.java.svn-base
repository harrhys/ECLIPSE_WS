package com.obopay.obopayagent;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.obopay.obopayagent.domain.Customer;
import com.obopay.obopayagent.utils.PreferenceManager;
import com.obopay.obopayagent.utils.Utils;

public class CustomerLanguageSelectionActivity extends Activity {

	private Customer customer;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_customer_language_selection);
		customer = getIntent().getParcelableExtra(Utils.CUSTOMER);
		Button englishLanguage = (Button) findViewById(R.id.english_language_button);
		Button hindiLanguage = (Button) findViewById(R.id.hindi_language_button);

		englishLanguage.setOnClickListener(listener);
		hindiLanguage.setOnClickListener(listener);
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
			intent.putExtra("page", "help_select_language.html");
			startActivity(intent);
			return true;
		}
		return super.onOptionsItemSelected(item);
	}


	private OnClickListener listener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.english_language_button:
				customer.setLanguage(CustomerLanguageSelectionActivity.this
						.getString(R.string.english_language_selection));
				break;
			case R.id.hindi_language_button:
				customer.setLanguage(CustomerLanguageSelectionActivity.this
						.getString(R.string.hindi_language_selection));
				break;
			}
			Integer registrationType = PreferenceManager.getRegistrationType(CustomerLanguageSelectionActivity.this);
			Intent intent  = null;
			if(customer.getProduct().equalsIgnoreCase(getString(R.string.merchant_product_name)) ||
					registrationType.equals(1))
				intent = new Intent(CustomerLanguageSelectionActivity.this,CustomerViewActivity.class);
			else
				intent = new Intent(CustomerLanguageSelectionActivity.this,CashInActivity.class);
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

}
