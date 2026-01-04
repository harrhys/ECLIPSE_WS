package com.obopay.obopayagent;

import com.obopay.obopayagent.utils.Utils;

import android.os.Bundle;
import android.os.Handler;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;

public class WelcomeSplashScreen extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_welcome_splash_screen);
		new Handler().postDelayed(new Runnable() {
			
			@Override
			public void run() {
				Intent intent = new Intent(WelcomeSplashScreen.this, MainActivity.class);
				startActivity(intent);
				finish();
			}
		}, Utils.WELCOME_SCREEN_TIME_OUT);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.help_menu, menu);
		return true;
	}

}
