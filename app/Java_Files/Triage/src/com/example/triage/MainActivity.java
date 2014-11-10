package com.example.triage;

import java.util.HashMap;

import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;

public class MainActivity extends Activity {
	
	// public final static String HEALTHCARDNUM = "com.example.triage.HEALTHCARDNUMBER";
	public final static String HEALTHCARDNUM = "HealthCardNum";
	public final static String PATIENT = "PatientInfo";

	// loads files
	HashMap<String, Patient> patientdocs = Organizer.getHcnToPatient();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_main);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	public void lookupPatient(View view) {
		Intent intent = new Intent(this, PatientActivity.class);
		
		SharedPreferences patient = this.getSharedPreferences("com.example.triage", 0);
		SharedPreferences.Editor prefEditor = patient.edit();
		
		EditText editText = (EditText) findViewById(R.id.editHealthcardnum);
		String healthcardnum = editText.getText().toString();
		
		prefEditor.putString("healthcardnumber", healthcardnum);
		prefEditor.commit();
		
		
		Nurse nurse = new Nurse();
		nurse.lookupPatient(patientdocs, healthcardnum);
		
		intent.putExtra("Patient_info", nurse.lookupPatient(patientdocs, healthcardnum));
		startActivity(intent);
	}
}
