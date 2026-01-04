package com.obopay.obopayagent;

import java.util.Map.Entry;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup.LayoutParams;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.obopay.obopayagent.domain.Transaction;
import com.obopay.obopayagent.utils.Utils;

public class ShowTransactionActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_show_transaction);
		Transaction transaction = getIntent().getParcelableExtra(Utils.TRANSACTION);
		TableLayout tableLayout = (TableLayout) findViewById(R.id.show_transaction_table);
		transaction.getResponseMetadata().entrySet();
		for (Entry<String, String> metadata : transaction.getResponseMetadata().entrySet()) {
			TableRow tr = new TableRow(this);
			TextView labelView = new TextView(this);
			labelView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15 );
			labelView.setTypeface(Typeface.DEFAULT, Typeface.BOLD);
			labelView.setPadding(10, 10, 10, 10);
			labelView.setText(metadata.getKey());
			tr.addView(labelView);
			TextView valueView = new TextView(this);
			if(metadata.getKey().equalsIgnoreCase("Date/Time")) {
				valueView.setText(transaction.getDateTime());
			} else {
				valueView.setText(metadata.getValue());
			}
			valueView.setPadding(10, 10, 10, 10);
			tr.addView(valueView);
			tableLayout.addView(tr, new TableLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
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
			intent.putExtra("page", "help_transaction_detail.html");
			startActivity(intent);
			return true;
		}
		return super.onOptionsItemSelected(item);
	}


}
