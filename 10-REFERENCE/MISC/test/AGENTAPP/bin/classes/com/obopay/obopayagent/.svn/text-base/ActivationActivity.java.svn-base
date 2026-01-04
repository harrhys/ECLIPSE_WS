package com.obopay.obopayagent;

import java.util.Hashtable;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
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

import com.obopay.obopayagent.restclient.OboServices;
import com.obopay.obopayagent.restclient.OboServicesConstants;
import com.obopay.obopayagent.restclient.OboServicesListener;
import com.obopay.obopayagent.utils.DateFormat;
import com.obopay.obopayagent.utils.PopUpUtil;
import com.obopay.obopayagent.utils.Utils;

public class ActivationActivity extends FragmentActivity implements OboServicesListener, OnEditorActionListener {
	private EditText dob;
	private EditText mothersDob;
	private EditText reEnterPassword;
	private EditText newPassword;
	private OnClickListener listener = new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			if(v.getId() == R.id.submit_button_activation){
				handleSubmitClick();

			}else if(v.getId() == R.id.date_of_birth_date_picker_activation){
				DialogFragment newFragment = new DatePickerFragment(dob);
			    newFragment.show(getSupportFragmentManager(), "datePicker");
			}else if(v.getId() == R.id.mother_date_of_birth_date_picker_activation){
				DialogFragment newFragment = new DatePickerFragment(mothersDob);
			    newFragment.show(getSupportFragmentManager(), "datePicker");
			}
		}		
	};
	
	private String validateSubmit() {
		String newPin = newPassword.getText().toString().trim();
		String reEnterNewPin = reEnterPassword.getText().toString().trim();
		String mothersDobString = mothersDob.getText().toString().trim();
		String dobString = dob.getText().toString().trim();
		String errors = "";
		if(dobString.length() == 0)
			errors+=ActivationActivity.this.getString(R.string.dob_missing)+"\n";
		else if(!DateFormat.isValidDate(dobString, "dd/MM/yyyy"))
			errors+=ActivationActivity.this.getString(R.string.dob_invalid)+"\n";
		if(mothersDobString.length() == 0)
			errors+= ActivationActivity.this.getString(R.string.mother_dob_missing)+"\n";
		else if(!DateFormat.isValidDate(mothersDobString, "dd/MM/yyyy"))
			errors+=ActivationActivity.this.getString(R.string.mother_dob_invalid)+"\n";
		if(newPin.length() != 4 || reEnterNewPin.length() != 4){
			reEnterPassword.setText("");
			newPassword.setText("");
			errors+= ActivationActivity.this.getString(R.string.pin_value_invalid)+"\n";
		}else if(!(newPin.equals(reEnterNewPin))){
			reEnterPassword.setText("");
			newPassword.setText("");
			errors+= ActivationActivity.this.getString(R.string.pin_values_do_not_match)+"\n";
		}
		
		if(errors.length()>0)
			return errors;
		else
			return null;
		
	}
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_activation);
		dob = (EditText) findViewById(R.id.date_of_birth_edit_text_activation);
		mothersDob = (EditText) findViewById(R.id.mother_date_of_birth_edit_text_activation);
		reEnterPassword = (EditText) findViewById(R.id.re_enter_new_pin_edit_text_activation);
		newPassword = (EditText) findViewById(R.id.new_pin_edit_text_activation);
		Button datePicker = (Button) findViewById(R.id.date_of_birth_date_picker_activation);
		Button mothersDatePicker = (Button) findViewById(R.id.mother_date_of_birth_date_picker_activation);
		Button submit = (Button) findViewById(R.id.submit_button_activation);
		reEnterPassword.setOnEditorActionListener(this);
		submit.setOnClickListener(listener);
		datePicker.setOnClickListener(listener);
		mothersDatePicker.setOnClickListener(listener);
		
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
			intent.putExtra("page", "help_activation.html");
			startActivity(intent);
			return true;
		}
		return super.onOptionsItemSelected(item);
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
		if(serviceType == OboServicesConstants.METHOD_ACTIVATE) {
			Log.d(Utils.LOG_TAG, result.toString());
	    	Intent selectOptionIntent = new Intent(this, SelectOptionActivity.class);
	    	startActivity(selectOptionIntent);
		}
	}

	@Override
	public void onOboServicesError(int serviceType, Exception e,
			OboServices svcObj) {
		if(serviceType == OboServicesConstants.METHOD_ACTIVATE) {
			PopUpUtil.error(this, getString(R.string.failed_to_activate_agent, e.getMessage()));
		}
	}
	private void handleSubmitClick() {
		String error = validateSubmit();
		if(error != null){
			PopUpUtil.error(ActivationActivity.this, error);
			return;
		}
        InputMethodManager inputManager = (InputMethodManager)
        getSystemService(Context.INPUT_METHOD_SERVICE);
        inputManager.toggleSoftInput(0, 0);

		String dobStr = dob.getText().toString();
		String mothersDobStr = mothersDob.getText().toString();
		String passwordStr = newPassword.getText().toString();
		String reEnterPasswordStr = reEnterPassword.getText().toString();

		Hashtable params = new Hashtable();
		OboServices.setParam(params,
				OboServicesConstants.DATA_FIELD_DATE_CUSTOMER_BACK,
				dobStr);
		OboServices.setParam(params,
				OboServicesConstants.DATA_FIELD_BIRTH_DATE,
				dobStr);
		OboServices.setParam(params,
				OboServicesConstants.DATA_FIELD_DATE_MOTHERS_BACK,
				mothersDobStr);
		OboServices.setParam(params,
				OboServicesConstants.DATA_FIELD_MOTHER_BIRTH_DATE,
				mothersDobStr);
		OboServices.setParam(params,
				OboServicesConstants.DATA_FIELD_NEW_PIN,
				passwordStr);
		OboServices.setParam(params,
				OboServicesConstants.DATA_FIELD_REENTER_PIN,
				reEnterPasswordStr);
		OboServices services = new OboServices(
				ActivationActivity.this, ActivationActivity.this);
		services.activate(params);
	}
}
