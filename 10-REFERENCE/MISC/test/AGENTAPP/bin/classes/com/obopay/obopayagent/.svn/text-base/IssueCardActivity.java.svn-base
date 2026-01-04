package com.obopay.obopayagent;

import java.util.Hashtable;
import java.util.Vector;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

import com.obopay.obopayagent.EnterPinDialog.EnterPinDialogListener;
import com.obopay.obopayagent.restclient.Metadata;
import com.obopay.obopayagent.restclient.MetadataException;
import com.obopay.obopayagent.restclient.OboServices;
import com.obopay.obopayagent.restclient.OboServicesConstants;
import com.obopay.obopayagent.restclient.OboServicesListener;
import com.obopay.obopayagent.utils.PopUpUtil;
import com.obopay.obopayagent.utils.Utils;

public class IssueCardActivity extends FragmentActivity implements 
EnterPinDialogListener, OboServicesListener {
	private EditText mobileNumber;
	private EditText cardReferenceNumber;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_issue_card);
		Button submit = (Button) findViewById(R.id.submit_button_issue_card);
		mobileNumber = (EditText) findViewById(R.id.mobile_number_edit_text_issue_card);
		cardReferenceNumber = (EditText) findViewById(R.id.card_reference_number_issue_card);
		
		submit.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
					
				String error = validateSumbmit();
				if(error != null){
					PopUpUtil.error(IssueCardActivity.this, error);
					return;
				}
				FragmentManager fm = getSupportFragmentManager();
				EnterPinDialog enterPinDialog = new EnterPinDialog();
		        enterPinDialog.show(fm, "fragment_enter_pin");
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
			intent.putExtra("page", "help_reissue_card.html");
			startActivity(intent);
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	private String validateSumbmit() {
		String mobileNumberString = mobileNumber.getText().toString().trim();
		String cardReferenceString = cardReferenceNumber.getText().toString().trim();
		String errors = "";
		
		if(mobileNumberString.length() == 0)
			errors += IssueCardActivity.this.getString(R.string.mobile_number_missing)+"\n";
		else if(mobileNumber.length() !=10)
			errors += IssueCardActivity.this.getString(R.string.invalid_mobile_number)+"\n";
		if(cardReferenceString.length() == 0)
			errors += IssueCardActivity.this.getString(R.string.card_reference_number_missing)+"\n";
		else if(cardReferenceString.length() != 14)
			errors += IssueCardActivity.this.getString(R.string.invalid_card_reference_number)+"\n";
		
		if(errors.length()>0)
			return errors;
		else
			return null;
	}
	
	@Override
	public void onFinishPinDialog(String pin) {
		Hashtable params = new Hashtable();
		params.put(OboServicesConstants.DATA_FIELD_PIN, pin);
		params.put(OboServicesConstants.DATA_FIELD_PHONE_NUMBER, Utils.COUNTRY_CODE + mobileNumber.getText().toString());
		params.put(OboServicesConstants.DATA_FIELD_CARD_NUM, cardReferenceNumber.getText().toString());
		OboServices services = new OboServices(this, this);
		services.cardReissue(params);
	}

	
	public void onOboServicesResult(int serviceType, Object result,
			OboServices svcObj) {
		if(serviceType == OboServicesConstants.METHOD_CARD_REISSUE) {
			Log.d(Utils.LOG_TAG, result.toString());
			Vector vecMet = (Vector)OboServices.getParam((Hashtable) result, OboServicesConstants.DATA_FIELD_METADATA);
			if(!vecMet.isEmpty()) {
				Metadata metadata = (Metadata)vecMet.elementAt(0);
				if(metadata.name.equals("MESSAGE")) {
					try {
						PopUpUtil.info(this, getString(R.string.issue_card), metadata.getValueAsString(), getString(R.string.done), new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								finish();
							}
						}) ;
					} catch (MetadataException e) {
						Log.e(Utils.LOG_TAG, "Failed to read response data:", e);
					}
				}
			}
		}
	}

	@Override
	public void onOboServicesError(int serviceType, Exception e,
			OboServices svcObj) {
		if(serviceType == OboServicesConstants.METHOD_CARD_REISSUE) {
			PopUpUtil.error(this, getString(R.string.failed_to_issue_card, e.getMessage()));
		}
	}
}
