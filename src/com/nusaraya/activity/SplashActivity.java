package com.nusaraya.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.donbaka.reklameuser.R;

public class SplashActivity extends Activity{
	private final int SPLASH_DELAY_LENGTH = 1000;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_splash_new);
		
		new Handler().postDelayed(new Runnable() {
			
			@Override
			public void run() {
				Intent intent = new Intent(SplashActivity.this, LoginNewActivity.class); 
				startActivity(intent);
				finish();
			}
		}, SPLASH_DELAY_LENGTH);
	}
	
	
}
