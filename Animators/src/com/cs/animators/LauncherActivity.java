package com.cs.animators;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

public class LauncherActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_launcher);
		welcome();
	}

	private void welcome() {
		new Handler().postDelayed(new Runnable() {
			
			@Override
			public void run() {
				Intent intent = new Intent(LauncherActivity.this, MainActivity.class);
				startActivity(intent);
				finish();
			}
		}, 1000);
	}

}
