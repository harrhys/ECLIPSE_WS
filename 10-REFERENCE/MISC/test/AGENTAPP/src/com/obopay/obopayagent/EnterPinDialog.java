package com.obopay.obopayagent;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.WindowManager.LayoutParams;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;

public class EnterPinDialog extends DialogFragment implements OnEditorActionListener {

	public interface EnterPinDialogListener {
        void onFinishPinDialog(String pin);
    }
	
    private EditText mEditText;
    private Button submitPin;
    private TextView statusText;
    public EnterPinDialog() {
        // Empty constructor required for DialogFragment
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
    	getDialog().setTitle(getString(R.string.enter_pin));
        View view = inflater.inflate(R.layout.fragment_enter_pin, container);
        mEditText = (EditText) view.findViewById(R.id.pin);
        submitPin = (Button) view.findViewById(R.id.submit_button_pin);
        statusText = (TextView) view.findViewById(R.id.status_label);
        getDialog().setTitle(getString(R.string.enter_pin));
        mEditText.requestFocus();
        getDialog().getWindow().setSoftInputMode(
                LayoutParams.SOFT_INPUT_STATE_VISIBLE);
        mEditText.setOnEditorActionListener(this);
        submitPin.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				statusText.setVisibility(View.GONE);
				String error = validateSubmit();
				if(error !=null){
					statusText.setVisibility(View.VISIBLE);
					statusText.setText(error);
					return;
				}
				handlePinVerification();
				dismiss();
			}
		});
        return view;
    }
    
    private void handlePinVerification() {
    	 EnterPinDialogListener activity = (EnterPinDialogListener) getActivity();
         activity.onFinishPinDialog(mEditText.getText().toString());
         InputMethodManager inputManager = (InputMethodManager)
         getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
         inputManager.toggleSoftInput(0, 0);
	}
   
    private String validateSubmit(){
    	String pinTextString = mEditText.getText().toString();
    	String errors = "";
    	if(pinTextString.length() == 0){
    		errors += EnterPinDialog.this.getString(R.string.pin_value_missing)+"\n";
    	} else if(pinTextString.length() != 4){
    		errors += EnterPinDialog.this.getString(R.string.pin_value_invalid)+"\n";
    	}

    	if(errors.length()>0)
			return errors;
		else
			return null;
    }
    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        if (EditorInfo.IME_ACTION_DONE == actionId) {
        	statusText.setVisibility(View.GONE);
        	String error = validateSubmit();
			if(error !=null){
				statusText.setVisibility(View.VISIBLE);
				statusText.setText(error);
				return false;
			}
        	handlePinVerification();
        	dismiss();
            return true;
        }
        return false;
    }
}