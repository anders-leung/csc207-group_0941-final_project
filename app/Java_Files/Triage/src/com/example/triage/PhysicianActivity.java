package com.example.triage;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;

public class PhysicianActivity extends Activity {
	
	SharedPreferences patient = getSharedPreferences("com.example.triage", 0);
	String healthcardnum = patient.getString("healhcardnumber", "N/A");
	//Patient patientinfo = (Patient) intent.getExtras().getSerializable("Patient_info");
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_physician);
		
//		String name = patientinfo.name;
//		String dob = patientinfo.dob;
//		
//		// with health card number, get patient info
//		TextView textViewName = (TextView) findViewById(R.id.textViewName);
//		textViewName.setText(name);
//		
//		TextView textViewDob = (TextView) findViewById(R.id.textViewDob);
//		textViewDob.setText(dob);
//		
//		TextView textViewHealthcardnum = (TextView) findViewById(R.id.textViewHealthcardnum);
//		textViewHealthcardnum.setText(healthcardnum);
//		
//		TextView textViewArrivaltime = (TextView) findViewById(R.id.textViewArrivaltime);
//		//textViewArrivaltime.setText(arrivaltime);
//		
//		TextView textViewTemperature = (TextView) findViewById(R.id.textViewTemperature);
//		//textViewTemperature.setText(temperature.toString());
//		
//		TextView textViewBloodpressure = (TextView) findViewById(R.id.textViewBloodpressure);
//		//textViewBloodpressure.setText(bloodpressure.toString());
//		
//		TextView textViewHeartrate = (TextView) findViewById(R.id.textViewHeartrate);
//		//textViewHeartrate.setText(heartrate.toString());
//		
//		TextView textViewVitaltime = (TextView) findViewById(R.id.textViewVitaltime);
//		//textViewVitaltime.setText(vitaltime);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.physician, menu);
		return true;
	}
	
	public void goPrescription(View view) {
		Intent intent = new Intent(this, NewPrescriptionActivity.class);
		startActivity(intent);
	}
}
