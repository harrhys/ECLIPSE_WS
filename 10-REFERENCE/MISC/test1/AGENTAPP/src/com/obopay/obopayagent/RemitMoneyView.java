package com.obopay.obopayagent;

import java.util.Hashtable;
import java.util.Vector;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.obopay.obopayagent.domain.CustomerRemittance;
import com.obopay.obopayagent.restclient.Metadata;
import com.obopay.obopayagent.restclient.MetadataException;
import com.obopay.obopayagent.restclient.OboServices;
import com.obopay.obopayagent.restclient.OboServicesConstants;
import com.obopay.obopayagent.restclient.OboServicesListener;
import com.obopay.obopayagent.utils.PopUpUtil;
import com.obopay.obopayagent.utils.Utils;

public class RemitMoneyView extends Activity implements OboServicesListener {

	private static final String VSEND = "VSEND";
	private CustomerRemittance remittance;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_remit_money_view);
		TableLayout tableLayout = (TableLayout) findViewById(R.id.remit_details_table);
		remittance = getIntent().getParcelableExtra(Utils.CUSTOMER_REMITTANCE);
		((TextView)tableLayout.findViewById(R.id.mobile_number)).setText(remittance.getMobileNumber());
		((TextView)tableLayout.findViewById(R.id.first_name)).setText(remittance.getFirstName());
		((TextView)tableLayout.findViewById(R.id.last_name)).setText(remittance.getLastName());
		((TextView)tableLayout.findViewById(R.id.transaction_id)).setText(remittance.getTransactionId());
		((TextView)tableLayout.findViewById(R.id.amount)).setText(remittance.getAmount());
		if(remittance.getTransactionType().equals(VSEND)) {
			TableRow tr = new TableRow(this);
			TextView labelView = new TextView(this);
			labelView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15 );
			labelView.setTypeface(Typeface.DEFAULT, Typeface.BOLD);
			labelView.setPadding(10, 5, 10, 5);
			labelView.setText(remittance.getIdType1());
			tr.addView(labelView);
			TextView valueView = new TextView(this);
			valueView.setText(remittance.getIdValue1());
			valueView.setPadding(10, 3, 10, 3);
			tr.addView(valueView);
			tableLayout.addView(tr, new TableLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
			tr = new TableRow(this);
			labelView = new TextView(this);
			labelView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15 );
			labelView.setTypeface(Typeface.DEFAULT, Typeface.BOLD);
			labelView.setPadding(10, 3, 10, 3);
			labelView.setText(remittance.getIdType2());
			tr.addView(labelView);
			valueView = new TextView(this);
			valueView.setText(remittance.getIdValue2());
			valueView.setPadding(10, 5, 10, 5);
			tr.addView(valueView);
			tableLayout.addView(tr, new TableLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
		}
		if(remittance.getIsOTPEnabled()) {
			findViewById(R.id.otp_label).setVisibility(View.VISIBLE);
			findViewById(R.id.otp_edit_text).setVisibility(View.VISIBLE);
		}
		final Button confirm = (Button) findViewById(R.id.confirm);
		final Button reject = (Button) findViewById(R.id.reject);
		if(remittance.getTransactionType().equals(VSEND)) {
			reject.setVisibility(View.GONE);
		}
		OnClickListener listener = new OnClickListener() {
			@Override
			public void onClick(View v) {
				String otp = null;
				if(remittance.getIsOTPEnabled()) {
					otp = ((TextView)findViewById(R.id.otp_edit_text)).getText().toString();
					if(otp.equals("")) {
						PopUpUtil.error(RemitMoneyView.this, getString(R.string.please_enter_otp));
						return;
					}
				}
				if(v == confirm)
					confirmRemitMoney(otp);
				else if(v == reject)
					rejectRemitMoney(otp);
			}
		};
		confirm.setOnClickListener(listener);
		reject.setOnClickListener(listener);
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
			intent.putExtra("page", "help_remit_money_confirmation.html");
			startActivity(intent);
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	protected void rejectRemitMoney(String otp) {
		Hashtable params = new Hashtable();
		params.put(OboServicesConstants.DATA_FIELD_PIN, remittance.getPin());
		params.put(OboServicesConstants.DATA_FIELD_PHONE_NUMBER, Utils.COUNTRY_CODE + remittance.getMobileNumber());
		params.put(OboServicesConstants.DATA_FIELD_TXN_ID, remittance.getTransactionId());
		if(remittance.getIsOTPEnabled()) {
			params.put(OboServicesConstants.DATA_FIELD_OTP, otp);
		}
		OboServices services = new OboServices(this, this);
		services.remitMoneyReject(params);
	}

	protected void confirmRemitMoney(String otp) {
		Hashtable params = new Hashtable();
		params.put(OboServicesConstants.DATA_FIELD_PIN, remittance.getPin());
		params.put(OboServicesConstants.DATA_FIELD_PHONE_NUMBER, Utils.COUNTRY_CODE + remittance.getMobileNumber());
		params.put(OboServicesConstants.DATA_FIELD_TXN_ID, remittance.getTransactionId());
		if(remittance.getIsOTPEnabled()) {
			params.put(OboServicesConstants.DATA_FIELD_OTP, otp);
		}
		OboServices services = new OboServices(this, this);
		if(remittance.getTransactionType().equals(VSEND)) {
			params.put(OboServicesConstants.DATA_FIELD_FIRST_NAME, remittance.getFirstName());
			params.put(OboServicesConstants.DATA_FIELD_LAST_NAME, remittance.getLastName());
			params.put(OboServicesConstants.DATA_FIELD_BENID1_LABEL, remittance.getIdType1());
			params.put(OboServicesConstants.DATA_FIELD_BENID1, remittance.getIdValue1());
			params.put(OboServicesConstants.DATA_FIELD_BENID2_LABEL, remittance.getIdType2());
			params.put(OboServicesConstants.DATA_FIELD_BENID2, remittance.getIdValue2());
			services.remitMoneyViral(params);
		} else {
			services.remitMoney(params);
		}
	}

	public void onOboServicesResult(int serviceType, Object result,
			OboServices svcObj) {
		Log.d(Utils.LOG_TAG, result.toString());
		if(serviceType == OboServicesConstants.METHOD_REMIT_MONEY ||
				serviceType == OboServicesConstants.METHOD_VIRAL_SEND ||
				serviceType == OboServicesConstants.METHOD_REMIT_MONEY_REJECT) {
			Vector vecMet = (Vector)OboServices.getParam((Hashtable) result, OboServicesConstants.DATA_FIELD_METADATA);
			if(!vecMet.isEmpty()) {
				Metadata metadata = (Metadata)vecMet.elementAt(0);
				if(metadata.name.equals("MESSAGE")) {
					try {
						PopUpUtil.info(this, getString(R.string.cash_out), metadata.getValueAsString(), getString(R.string.done), new DialogInterface.OnClickListener() {
	
							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								Intent resultIntent = new Intent();
								resultIntent.putExtra(Utils.REMIT_RESPONSE, true);
								setResult(Activity.RESULT_OK, resultIntent);
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
		if(serviceType == OboServicesConstants.METHOD_REMIT_MONEY) 
			PopUpUtil.error(this, getString(R.string.failed_to_remit, e.getMessage()));
		else if(serviceType == OboServicesConstants.METHOD_REMIT_MONEY_REJECT)
			PopUpUtil.error(this, getString(R.string.failed_to_reject_remit, e.getMessage()));
	}
}
