package com.example.triage;

import java.util.HashMap;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class VitaltimesActivity extends Activity {
	
	public static final String VITALS = "VITALS";
	Intent intent = getIntent();
	Patient patientinfo = (Patient) intent.getExtras().getSerializable("Patient_info");
	SharedPreferences patient = getSharedPreferences("com.example.triage", 0);
	String healthcardnum = patient.getString("healhcardnumber", "N/A");
	//HashMap<String, Patient> patientdocs = Organizer.getHcnToPatient();
	//String arrivaltime = intent.getStringExtra(ArrivaltimesActivity.ARRIVALTIME);

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		TextView textView = (TextView) findViewById(R.id.textViewVitaltimes);
		//String vitalmap = patientinfo.getVitalsigns().get(arrivaltime).keySet().toString();
		//textView.setText(vitalmap);
		
		setContentView(R.layout.activity_vitaltimes);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.vitaltimes, menu);
		return true;
	}
	
	public void goBack(View v) {
		Intent intentback = new Intent(this, ArrivaltimesActivity.class);
		startActivity(intentback);
	}
	
	public void viewPastVitals(View v) {
		Intent intentnext = new Intent(this, PastvitalsActivity.class);
		EditText editText = (EditText) findViewById(R.id.editTextVitaltimeinfo);
		//String vitals = patientinfo.getVitalsigns().get(arrivaltime).get(editText).toString();
	
		//intentnext.putExtra(vitals, VITALS);
		startActivity(intentnext);
	}

}
