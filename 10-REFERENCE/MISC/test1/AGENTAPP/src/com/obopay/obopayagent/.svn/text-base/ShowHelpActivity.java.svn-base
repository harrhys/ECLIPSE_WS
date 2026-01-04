package com.obopay.obopayagent;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import com.obopay.obopayagent.utils.PreferenceManager;
import com.obopay.obopayagent.utils.Utils;


import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebSettings;
import android.webkit.WebView;

public class ShowHelpActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_about_help);
		WebView browser = (WebView) findViewById(R.id.about_help_web_view);
		WebSettings settings = browser.getSettings();
	    settings.setBuiltInZoomControls(true);
	    StringBuilder total;
		try {
			InputStream inputStream = getAssets().open("help/" + getIntent().getStringExtra("page"));
			BufferedReader r = new BufferedReader(new InputStreamReader(inputStream));
			total = new StringBuilder();
			String line;
			while ((line = r.readLine()) != null) {
			    total.append(line);
			}
		} catch (IOException e) {
			Log.e(Utils.LOG_TAG, e.getMessage(), e);
			total = new StringBuilder("Failed to load:" + "help/" + getIntent().getStringExtra("page"));
		}
	    String template = total.toString();
	    String data = template.replaceAll("<%CUSTOMER_CARE%>", PreferenceManager.getCustomerCareNumber(this));
	    browser.loadDataWithBaseURL("file:///android_asset/help/", data, "text/html", "utf-8", null);

	}
}
