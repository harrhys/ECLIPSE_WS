package com.obopay.obopayagent;

import java.util.Hashtable;
import java.util.Vector;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;

import com.obopay.obopayagent.restclient.Metadata;
import com.obopay.obopayagent.restclient.MetadataException;
import com.obopay.obopayagent.restclient.OboServices;
import com.obopay.obopayagent.restclient.OboServicesConstants;
import com.obopay.obopayagent.restclient.OboServicesListener;
import com.obopay.obopayagent.utils.PopUpUtil;
import com.obopay.obopayagent.utils.Utils;

public class ChangePinActivity extends Activity implements OboServicesListener, OnEditorActionListener {
	private EditText oldPin;
	private EditText newPin;
	private EditText reEnterNewPin;
	private int requestCode;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Intent intent = getIntent();
		requestCode = intent.getIntExtra("requestCode", 0);
		setContentView(R.layout.activity_change_pin);
		if(Utils.RESET_PIN == requestCode) {
			((TextView)findViewById(R.id.title)).setText(getString(R.string.reset_pin));
			((TextView)findViewById(R.id.current_pin)).setText(getString(R.string.enter_temp_pin));
			((TextView)findViewById(R.id.current_pin)).setHint(getString(R.string.enter_temp_pin));
		}
		oldPin = (EditText) findViewById(R.id.current_pin_edit_text_change_pin);
		newPin = (EditText) findViewById(R.id.new_pin_edit_text_change_pin);
		reEnterNewPin = (EditText) findViewById(R.id.re_enter_new_pin_edit_text_change_pin);
		reEnterNewPin.setOnEditorActionListener(this);
		Button submit = (Button) findViewById(R.id.submit_button_change_pin);
		submit.setOnClickListener( new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				handleSubmitClick();
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
			intent.putExtra("page", "help_change_reset_pin.html");
			startActivity(intent);
			return true;
		}
		return super.onOptionsItemSelected(item);
	}


	
	private void handleSubmitClick() {
		String error = validateSubmit();
		if(error != null){
			PopUpUtil.error(ChangePinActivity.this, error);
			return;
		}
		InputMethodManager inputManager = (InputMethodManager)
				getSystemService(Context.INPUT_METHOD_SERVICE);
		inputManager.toggleSoftInput(0, 0);
		Hashtable params = new Hashtable();
		params.put(OboServicesConstants.DATA_FIELD_PIN, oldPin.getText().toString());
		params.put(OboServicesConstants.DATA_FIELD_NEW_PIN, newPin.getText().toString());
		params.put(OboServicesConstants.DATA_FIELD_REENTER_PIN, reEnterNewPin.getText().toString());
		OboServices services = new OboServices(ChangePinActivity.this, ChangePinActivity.this);
		if(Utils.RESET_PIN == requestCode)
			services.resetPin(params);
		else
			services.changePIN(params);
	}

	private String validateSubmit() {
		String newPinString = newPin.getText().toString();
		String reEnterNewPinString = reEnterNewPin.getText().toString();
		String oldPinString = oldPin.getText().toString();
		String errors = "";
		
		if(oldPinString.length() != 4) {
			if(Utils.RESET_PIN == requestCode) {
				errors+=ChangePinActivity.this.getString(R.string.temp_pin_invalid)+"\n";
			} else {
				errors+=ChangePinActivity.this.getString(R.string.current_pin_invalid)+"\n";
			}
			oldPin.setText("");
		} if(newPinString.length() != 4 || reEnterNewPinString.length() != 4){
			reEnterNewPin.setText("");
			newPin.setText("");
			newPin.requestFocus();
			errors+=ChangePinActivity.this.getString(R.string.new_pin_value_invalid)+"\n";
		} else if(!(newPinString.equals(reEnterNewPinString))){
			reEnterNewPin.setText("");
			newPin.setText("");
			newPin.requestFocus();
			errors+=ChangePinActivity.this.getString(R.string.pin_values_do_not_match)+"\n";
		} else if(newPinString.equals(oldPinString)) {
			errors+=ChangePinActivity.this.getString(R.string.new_pin_same_as_old_pin)+"\n";
			reEnterNewPin.setText("");
			newPin.setText("");
			newPin.requestFocus();
		}
		
		if(errors.length()>0)
			return errors;
		else
			return null;
	}
	
    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        if (EditorInfo.IME_ACTION_DONE == actionId) {
        	handleSubmitClick();
            return true;
        }
        return false;
    }
    
	public void onOboServicesResult(int serviceType, Object result,
			OboServices svcObj) {
		if(serviceType == OboServicesConstants.METHOD_CHANGE_PIN ||
				serviceType == OboServicesConstants.METHOD_RESET ) {
			Log.d(Utils.LOG_TAG, result.toString());
			Vector vecMet = (Vector)OboServices.getParam((Hashtable) result, OboServicesConstants.DATA_FIELD_METADATA);
			String title = null;
			String message = null;
			if(Utils.RESET_PIN == requestCode) {
				title = getString(R.string.reset_pin);
				message = getString(R.string.reset_pin_successfully);
			} else {
				title = getString(R.string.change_pin);
				if(!vecMet.isEmpty()) {
					try {
						Metadata metadata = (Metadata)vecMet.elementAt(0);
						if(metadata.name.equals("MESSAGE")) {
							message = metadata.getValueAsString();
						}
					} catch (MetadataException e) {
						Log.e(Utils.LOG_TAG, "Failed to read response data:", e);
					}
				}
			}
			PopUpUtil.info(this, title, message, getString(R.string.done), new DialogInterface.OnClickListener() {

				@Override
				public void onClick(DialogInterface dialog,
						int which) {
					if(Utils.RESET_PIN == requestCode) {
						Intent resultIntent = new Intent();
						resultIntent.putExtra(Utils.RESET_PIN_RESPONSE, true);
						setResult(Activity.RESULT_OK, resultIntent);
					}
					finish();
				}
			});
		}
	}

	@Override
	public void onOboServicesError(int serviceType, Exception e,
			OboServices svcObj) {
		if(serviceType == OboServicesConstants.METHOD_CHANGE_PIN) {
			PopUpUtil.error(this, getString(R.string.failed_to_change_pin, e.getMessage()));
		} else {
			PopUpUtil.error(this, getString(R.string.failed_to_reset_pin, e.getMessage()));
		}
		oldPin.setText("");
		newPin.setText("");
		reEnterNewPin.setText("");
	}	

}
