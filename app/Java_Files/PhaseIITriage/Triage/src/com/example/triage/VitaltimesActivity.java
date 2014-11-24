package com.example.triageii;

import java.util.ArrayList;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class VitaltimesActivity extends Activity {
	
	public final static String ARRIVALTIME = "ARRIVALTIME";
	public static final String VITALS = "VITALS";
	public static final String PATIENT = "PatientInfo";
	private Patient patientinfo;
	private SharedPreferences patient;
	private String healthcardnum;
	private String arrivaltime;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_vitaltimes);
		
		
		Intent intent = getIntent();
		String caller = getIntent().getStringExtra("caller");
		Log.v("VitalimesActivity", caller);
		if (caller.equals("ArrivaltimesActivity")) {
			patientinfo = (Patient) intent.getExtras().get(ArrivaltimesActivity.PATIENT);
		} else {
			patientinfo = (Patient) intent.getExtras().get(PastvitalsActivity.PATIENT);
		}
		patient = getSharedPreferences("com.example.triage", 0);
		healthcardnum = patient.getString("healhcardnumber", "N/A");
		arrivaltime = intent.getStringExtra(ArrivaltimesActivity.ARRIVALTIME);
		
		TextView textView = (TextView) findViewById(R.id.textViewVitaltimes);
		String vitalmap = patientinfo.getVitalsigns().get(arrivaltime).keySet().toString();
		textView.setText(vitalmap);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.vitaltimes, menu);
		return true;
	}
	
	public void goBack(View v) {
		Intent intentback = new Intent(this, ArrivaltimesActivity.class);
		intentback.putExtra(PATIENT, patientinfo);
		intentback.putExtra("caller", "VitaltimesActivity");
		startActivity(intentback);
	}
	
	public void viewPastVitals(View v) {
		Intent intentnext = new Intent(this, PastvitalsActivity.class);
		EditText editText = (EditText) findViewById(R.id.editTextVitaltimeinfo);
		ArrayList<Number> vitals = 
				patientinfo.getVitalsigns().get(arrivaltime).get(
						editText.getText().toString());
		intentnext.putExtra(VITALS, vitals.toString());
		intentnext.putExtra(PATIENT, patientinfo);
		intentnext.putExtra(ARRIVALTIME, arrivaltime);
		intentnext.putExtra("caller", "VitaltimesActivity");
		startActivity(intentnext);
	}

}