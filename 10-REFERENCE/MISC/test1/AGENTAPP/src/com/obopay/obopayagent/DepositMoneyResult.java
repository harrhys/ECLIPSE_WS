package com.obopay.obopayagent;

import java.util.Map;
import java.util.Map.Entry;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.obopay.obopayagent.domain.CustomerDeposit;
import com.obopay.obopayagent.utils.Utils;

public class DepositMoneyResult extends Activity {

	
	@Override
	public void onBackPressed() {
		Intent resultIntent = new Intent();
		resultIntent.putExtra(Utils.DEPOSIT_RESPONSE, true);
		setResult(Activity.RESULT_OK, resultIntent);
		finish();
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_deposit_money_result);
		TableLayout tableLayout = (TableLayout) findViewById(R.id.deposit_result_table);
		CustomerDeposit customerDeposit = getIntent().getParcelableExtra(Utils.CUSTOMER_DEPOSIT);
		Map<String, String> responseMetaData = customerDeposit.getResponseMetaData();
		for (final Entry<String, String> entry : responseMetaData.entrySet()) {
			TableRow tr = new TableRow(this);
			TextView labelView = new TextView(this);
			labelView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15 );
			labelView.setTypeface(Typeface.DEFAULT, Typeface.BOLD);
			labelView.setPadding(10, 10, 10, 10);
			labelView.setText(entry.getKey());
			tr.addView(labelView);
			TextView valueView = new TextView(this);
			valueView.setText(entry.getValue());
			valueView.setPadding(10, 10, 10, 10);
			tr.addView(valueView);
			tableLayout.addView(tr, new TableLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
		}
		TableRow tr = new TableRow(this);
		Button doneButton = new Button(this);
		doneButton.setPadding(10, 10, 10, 10);
		doneButton.setBackgroundResource(R.drawable.default_buttons);
		doneButton.setText(getString(R.string.done));
		doneButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent resultIntent = new Intent();
				resultIntent.putExtra(Utils.DEPOSIT_RESPONSE, true);
				setResult(Activity.RESULT_OK, resultIntent);
				finish();
			}
		});
		tr.addView(doneButton);
		tableLayout.addView(tr, new TableLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
	}
}
