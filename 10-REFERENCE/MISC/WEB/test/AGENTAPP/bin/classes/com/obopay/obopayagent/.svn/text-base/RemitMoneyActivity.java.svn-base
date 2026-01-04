package com.obopay.obopayagent;

import com.obopay.obopayagent.utils.Utils;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class RemitMoneyActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_remit_money);
		
		Button verifyTransaction = (Button) findViewById(R.id.button_verify_transaction_remit_money);
		verifyTransaction.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(RemitMoneyActivity.this, VerifyTransaction.class);
				startActivityForResult(intent, Utils.REMIT);
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
			intent.putExtra("page", "help_remit_money.html");
			startActivity(intent);
			return true;
		}
		return super.onOptionsItemSelected(item);
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
}
