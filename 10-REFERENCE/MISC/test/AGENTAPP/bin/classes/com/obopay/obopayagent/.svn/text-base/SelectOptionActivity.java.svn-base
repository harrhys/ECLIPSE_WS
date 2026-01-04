package com.obopay.obopayagent;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class SelectOptionActivity extends Activity {

	private OnClickListener listener = new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			switch(v.getId()){
			case R.id.button_manage_customer_main: 
				Intent intent = new Intent(SelectOptionActivity.this,ManageCustomerActivity.class);
				startActivity(intent);
				break;
			case R.id.button_deposit_money_main:
				Intent intent1 = new Intent(SelectOptionActivity.this,DepositMoneyActivity.class);
				startActivity(intent1);
				break;
			case R.id.button_my_account_main:
				Intent intent2 = new Intent(SelectOptionActivity.this,MyAccountSelectOptionActivity.class);
				startActivity(intent2);
				break;
			case R.id.button_remit_money_main:
				Intent intent3 = new Intent(SelectOptionActivity.this,RemitMoneyActivity.class);
				startActivity(intent3);
				break;
			}
			
		}
	};
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_select_option);
		Button manageCustomer = (Button) findViewById(R.id.button_manage_customer_main);
		Button depositMoney = (Button) findViewById(R.id.button_deposit_money_main);
		Button myAccount = (Button) findViewById(R.id.button_my_account_main);
		Button remitMoney = (Button) findViewById(R.id.button_remit_money_main);
		
		manageCustomer.setOnClickListener(listener);
		depositMoney.setOnClickListener(listener);
		myAccount.setOnClickListener(listener);
		remitMoney.setOnClickListener(listener);
	}
}
