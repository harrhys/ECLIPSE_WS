package com.obopay.obopayagent;

import java.util.Hashtable;
import java.util.Vector;

import android.content.DialogInterface;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.util.TypedValue;
import android.view.ViewGroup.LayoutParams;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.obopay.obopayagent.EnterPinDialog.EnterPinDialogListener;
import com.obopay.obopayagent.restclient.Metadata;
import com.obopay.obopayagent.restclient.OboServices;
import com.obopay.obopayagent.restclient.OboServicesConstants;
import com.obopay.obopayagent.restclient.OboServicesListener;
import com.obopay.obopayagent.utils.PopUpUtil;
import com.obopay.obopayagent.utils.Utils;

public class ShowBalanceActivity extends FragmentActivity implements 
EnterPinDialogListener, OboServicesListener {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_show_balance);
		FragmentManager fm = getSupportFragmentManager();
		EnterPinDialog editNameDialog = new EnterPinDialog() {

			@Override
			public void onCancel(DialogInterface dialog) {
				finish();
			}
			
		};
        editNameDialog.show(fm, "fragment_enter_pin");
	}
	
	@Override
	public void onFinishPinDialog(String pin) {
		Hashtable params = new Hashtable();
		params.put(OboServicesConstants.DATA_FIELD_PIN, pin);
		OboServices services = new OboServices(
				ShowBalanceActivity.this, ShowBalanceActivity.this);
		services.getBalance(params);
		
	}
	
	
	public void onOboServicesResult(int serviceType, Object result,
			OboServices svcObj) {
		if(serviceType == OboServicesConstants.METHOD_GET_BALANCE) {
			Log.d(Utils.LOG_TAG, result.toString());
			TableLayout tableLayout = (TableLayout) findViewById(R.id.show_balance_table);
			Vector vec = (Vector)OboServices.getParam((Hashtable) result, OboServicesConstants.DATA_FIELD_METADATA);
			for (int i = 0; i < vec.size(); i++) {
				Metadata metadata = (Metadata)vec.elementAt(i);
				TableRow tr = new TableRow(this);
				TextView labelView = new TextView(this);
				labelView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15 );
				labelView.setTypeface(Typeface.DEFAULT, Typeface.BOLD);
				labelView.setPadding(10, 10, 10, 10);
				labelView.setText(metadata.label);
				tr.addView(labelView);
				TextView valueView = new TextView(this);
				valueView.setText((String)metadata.value);
				valueView.setPadding(10, 10, 10, 10);
				tr.addView(valueView);
				tableLayout.addView(tr, new TableLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
			}
		}
	}

	@Override
	public void onOboServicesError(int serviceType, Exception e,
			OboServices svcObj) {
		if(serviceType == OboServicesConstants.METHOD_GET_BALANCE) {
			PopUpUtil.error(this, getString(R.string.failed_to_get_balance, e.getMessage()), new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int which) {
					finish();
				}
			});
		}
	}
	

}
