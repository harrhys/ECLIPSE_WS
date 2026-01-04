package com.obopay.obopayagent;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

import com.obopay.obopayagent.domain.CustomerVerification;
import com.obopay.obopayagent.utils.Utils;

public class VerifyCustomerResult extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_verify_customer_result);
		CustomerVerification customerVerification = getIntent().getParcelableExtra(Utils.CUSTOMER_VERIFICATION);
		
		TextView programCodeTextView = (TextView) findViewById(R.id.program_code_text_view);
		TextView mobileNumber = (TextView) findViewById(R.id.mobile_number_text_view);
		TextView firstName = (TextView) findViewById(R.id.first_name_text_view);
		TextView lastName = (TextView) findViewById(R.id.last_name_text_view);
		TextView registrationDateTextView = (TextView) findViewById(R.id.registration_date_text_view);
		TextView statusTextView = (TextView) findViewById(R.id.status_text_view);
		TextView idLabel1 = (TextView) findViewById(R.id.id_label_1_text_view);
		TextView idValue11 = (TextView) findViewById(R.id.id_value_1_text_view);
		TextView idLabel2 = (TextView) findViewById(R.id.id_label_2_text_view);
		TextView idValue12 = (TextView) findViewById(R.id.id_value_2_text_view);
		
		programCodeTextView.setText(customerVerification.getProgramCode());
		mobileNumber.setText(customerVerification.getMobileNumber());
		firstName.setText(customerVerification.getFirstName());
		lastName.setText(customerVerification.getLastName());
		registrationDateTextView.setText(customerVerification.getRegistrationDate());
		statusTextView.setText(customerVerification.getStatus());
		if(customerVerification.getIdType1() != null && 
				customerVerification.getIdType1().equals("")) {
			idLabel1.setText(customerVerification.getIdType1());
			idValue11.setText(customerVerification.getIdValue1());
		}
		if(customerVerification.getIdType2() != null && 
				customerVerification.getIdType2().equals("")) {
			idLabel2.setText(customerVerification.getIdType2());
			idValue12.setText(customerVerification.getIdValue2());
		}
	}
}
