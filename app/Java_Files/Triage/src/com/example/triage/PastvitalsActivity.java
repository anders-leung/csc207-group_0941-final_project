package com.example.triage;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;

public class PastvitalsActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// get health card number
		SharedPreferences patient = getSharedPreferences("com.example.triage", 0);
		String healthcardnum = patient.getString("healhcardnumber", "N/A");
		
		Intent intent = getIntent();
		String vitaltime = intent.getStringExtra(VitaltimesActivity.VITALTIME);
		
		// method for vital information here
		
		TextView textView = (TextView) findViewById(R.id.textView);
		// textView.setText(list of vital info);
		
		setContentView(R.layout.activity_pastvitals);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.pastvitals, menu);
		return true;
	}
	
	public void goBack(View view) {
		Intent intent = new Intent(this, VitaltimesActivity.class);
		startActivity(intent);
	}
}
