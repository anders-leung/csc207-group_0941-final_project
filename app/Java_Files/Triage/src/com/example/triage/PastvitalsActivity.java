package com.example.triage;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;

public class PastvitalsActivity extends Activity {

	Intent intent = getIntent();
	//String vitals = intent.getStringExtra(VitaltimesActivity.VITALS);
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		// method for vital information here
		
		TextView textView = (TextView) findViewById(R.id.textView);
		//textView.setText(vitals);
		
		setContentView(R.layout.activity_pastvitals);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.pastvitals, menu);
		return true;
	}
	
	public void goBack(View view) {
		Intent intentback = new Intent(this, VitaltimesActivity.class);
		startActivity(intentback);
	}
}
