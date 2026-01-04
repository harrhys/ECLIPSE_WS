package com.obopay.obopayagent;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class ManageCustomerActivity extends Activity {
	
	private OnClickListener buttonClick = new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			switch(v.getId()){
				case R.id.new_customer_button:
					Intent intent = new Intent(ManageCustomerActivity.this,CustomerProgramSelectionActivity.class);
					startActivity(intent);
					break;
					
				case R.id.verify_customer_button:
					Intent intent1 = new Intent(ManageCustomerActivity.this,VerifyCustomerActivity.class);
					startActivity(intent1);
					break;
					
				case R.id.issue_card_button:
					Intent intent2 = new Intent(ManageCustomerActivity.this,IssueCardActivity.class);
					startActivity(intent2);
					break;
				default:
			}
		}
	};	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_manage_customer);
		Button newCustomerButton = (Button) findViewById(R.id.new_customer_button);
		Button verifyCustomerButton = (Button) findViewById(R.id.verify_customer_button);
		Button issueCardButton = (Button) findViewById(R.id.issue_card_button);
		
		newCustomerButton.setOnClickListener(buttonClick);
		verifyCustomerButton.setOnClickListener(buttonClick);
		issueCardButton.setOnClickListener(buttonClick);
		
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
			intent.putExtra("page", "help_manage_customer.html");
			startActivity(intent);
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

}
