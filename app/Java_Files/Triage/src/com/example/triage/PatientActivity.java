package com.example.triage;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class PatientActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		SharedPreferences patient = getSharedPreferences("com.example.triage", 0);
		String healthcardnum = patient.getString("healhcardnumber", "N/A");
		
		// String healthcardnum = intent.getStringExtra(MainActivity.HEALTHCARDNUM);
		
		// method for patient info here
		
		// String name =
		// String dob =
		// String arrivaltime =
		// String temperature =
		// String bloodpressure =
		// String heartrate =
		// String vitaltime =
		
		TextView textViewName = (TextView) findViewById(R.id.textViewName);
		// textViewName.setText(name);
		
		TextView textViewDob = (TextView) findViewById(R.id.textViewDob);
		// textViewDob.setText(dob);
		
		TextView textViewHealthcardnum = (TextView) findViewById(R.id.textViewHealthcardnum);
		textViewHealthcardnum.setText(healthcardnum);
		
		TextView textViewArrivaltime = (TextView) findViewById(R.id.textViewArrivaltime);
		// textViewArrivaltime.setText(arrivaltime);
		
		TextView textViewTemperature = (TextView) findViewById(R.id.textViewTemperature);
		// textViewTemperature.setText(temperature);
		
		TextView textViewBloodpressure = (TextView) findViewById(R.id.textViewBloodpressure);
		// textViewBloodpressure.setText(bloodpressure);
		
		TextView textViewHeartrate = (TextView) findViewById(R.id.textViewHeartrate);
		// textViewHeartrate.setText(heartrate);
		
		TextView textViewVitaltime = (TextView) findViewById(R.id.textViewVitaltime);
		// textViewVitaltime.setText(vitaltime);
		
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
	
	public void Save() {
		
		// gets all the new imputs
		EditText editTextArrivaltime = (EditText) findViewById(R.id.editTextArrivaltime);
		EditText editTextTemperature = (EditText) findViewById(R.id.editTextTemperature);
		EditText editTextBloodpressure = (EditText) findViewById(R.id.editTextBloodpressure);
		EditText editTextHeartrate = (EditText) findViewById(R.id.editTextHeartrate);
		EditText editTextVitaltime = (EditText) findViewById(R.id.editTextVitaltime);
		// method for saving here
		
		// After save, goes back to the main menu
		Intent intent = new Intent(this, MainActivity.class);
		startActivity(intent);
	}
	
	public void viewArrivaltimes(View view) {
		Intent intent = new Intent(this, ArrivaltimesActivity.class);
		startActivity(intent);
	}
}
