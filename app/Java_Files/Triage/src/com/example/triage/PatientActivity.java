package com.example.triage;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;

public class PatientActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Intent intent = getIntent();
		String healthcardnum = intent.getStringExtra(MainActivity.HEALTHCARDNUM);
		// method for patient info here
		// String name =
		// String dob =
		
		TextView textViewName = (TextView) findViewById(R.id.textViewName);
		textViewName.setText(name);
		
		TextView textViewDob = (TextView) findViewById(R.id.textViewDob);
		textViewName.setText(dob);
		
		TextView textViewHealthcardnum = (TextView) findViewById(R.id.textViewHealthcardnum);
		textViewName.setText(healthcardnum);
		
		
		
		
		
		
		
		
		
		setContentView(R.layout.activity_patient);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.patient, menu);
		return true;
	}
	
	public void goBack() {
		Intent intent = new Intent(this, MainActivity.class);
		startActivity(intent);
	}
}
