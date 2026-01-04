package com.obopay.obopayagent;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MyAccountSelectOptionActivity extends Activity  {

	private OnClickListener listener = new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			switch(v.getId()){
				case R.id.button_balance_my_account : 
					Intent intent = new Intent(MyAccountSelectOptionActivity.this,ShowBalanceActivity.class);
					startActivity(intent);
					break;
				case R.id.button_history_my_account :
					Intent intent1 = new Intent(MyAccountSelectOptionActivity.this,ShowHistoryActivity.class);
					startActivity(intent1);
					break;
				case R.id.button_change_pin_my_account:
					Intent intent2 = new Intent(MyAccountSelectOptionActivity.this,ChangePinActivity.class);
					startActivity(intent2);
					break;
				default:break;
			}
		}
	};
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_my_account_select_option);
		Button balance = (Button) findViewById(R.id.button_balance_my_account);
		Button history = (Button) findViewById(R.id.button_history_my_account);
		Button changePin = (Button) findViewById(R.id.button_change_pin_my_account);
		
		balance.setOnClickListener(listener);
		history.setOnClickListener(listener);
		changePin.setOnClickListener(listener);
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
			intent.putExtra("page", "help_my_account.html");
			startActivity(intent);
			return true;
		}
		return super.onOptionsItemSelected(item);
	}


}
