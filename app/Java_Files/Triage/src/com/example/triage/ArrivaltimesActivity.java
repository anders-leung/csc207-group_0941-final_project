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

public class ArrivaltimesActivity extends Activity {
	
	public final static String ARRIVALTIME = "ARRIVALTIME";
	
	Intent intent = getIntent();
	Patient patientinfo = (Patient) intent.getExtras().getSerializable("Patient_info");
	SharedPreferences patient = getSharedPreferences("com.example.triage", 0);
	String healthcardnum = patient.getString("healhcardnumber", "N/A");
	HashMap<String, Patient> patientdocs = Organizer.getHcnToPatient();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		TextView textView = (TextView) findViewById(R.id.textViewArrivaltimes);
		String arrivalmap = patientinfo.getVitalsigns().keySet().toString();
		textView.setText(arrivalmap);
		setContentView(R.layout.activity_arrivaltimes);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.arrivaltimes, menu);
		return true;
	}

	public void goBack(View view) {
		Intent intentback = new Intent(this, PatientActivity.class);
		startActivity(intentback);
	}	
	
	public void viewVitaltimes(View view) {
		Intent intentnext = new Intent(this, VitaltimesActivity.class);
		//gets desired arrival time
		EditText editText = (EditText) findViewById(R.id.editTextArrivalinfo);
		intentnext.putExtra(ARRIVALTIME, editText.getText());
		
		Nurse nurse = new Nurse();
		nurse.lookupPatient(patientdocs, healthcardnum);
		intentnext.putExtra("Patient_info", nurse.lookupPatient(patientdocs, healthcardnum));
		
		startActivity(intentnext);
	}
}
