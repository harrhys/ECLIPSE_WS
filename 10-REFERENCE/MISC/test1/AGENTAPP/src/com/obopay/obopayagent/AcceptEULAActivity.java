package com.obopay.obopayagent;

import com.obopay.obopayagent.R;
import com.obopay.obopayagent.utils.Utils;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.view.Menu;
import android.view.MenuItem;

public class AcceptEULAActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_accept_eula);
	}
	
	@Override
	public void onBackPressed() {
		Intent failedIntent = new Intent();
		failedIntent.putExtra(Utils.ACCEPT_EULA_RESPONSE, false);
		setResult(Activity.RESULT_CANCELED, failedIntent);
		finish();
	}



	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_accept_eula, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.menu_accept:
		    SharedPreferences preferences = getSharedPreferences(Utils.PREFS_NAME, MODE_PRIVATE);
		    Editor editor = preferences.edit();
		    editor.putBoolean(Utils.ACCEPTED_EULA_PREF_NAME, true);
		    editor.commit();
	    	Intent smsActivationIntent = new Intent(this, SMSActivationActivity.class);
		    startActivity(smsActivationIntent);
		    finish();
			return true;
		case R.id.menu_help:
	    	Intent helpIntent = new Intent(this, HelpActivity.class);
		    startActivity(helpIntent);
			return true;
		case R.id.menu_exit:
			Intent failedIntent = new Intent();
			failedIntent.putExtra(Utils.ACCEPT_EULA_RESPONSE, false);
			setResult(Activity.RESULT_CANCELED, failedIntent);
			finish();
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
