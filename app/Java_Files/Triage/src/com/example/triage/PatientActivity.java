package com.example.triage;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;
import java.util.TreeSet;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class PatientActivity extends Activity {
	
	Intent intent = getIntent();
	Patient patientinfo = (Patient) intent.getExtras().getSerializable("Patient_info");
	SharedPreferences patient = getSharedPreferences("com.example.triage", 0);
	String healthcardnum = patient.getString("healhcardnumber", "N/A");
	//HashMap<String, Patient> patientdocs = Organizer.getHcnToPatient();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);	
		
		String name = patientinfo.name;
		String dob = patientinfo.dob;
		
		//Object[] arrivalmap = patientinfo.getVitalsigns().keySet().toArray();
		//String arrivaltime = arrivalmap[arrivalmap.length-1].toString();
		
		//Object[] vitalmap = patientinfo.getVitalsigns().get(arrivalmap).keySet().toArray();
		//String vitaltime = vitalmap[vitalmap.length-1].toString();
		
		//Number temperature = patientinfo.getVitalsigns().get(arrivalmap).get(vitaltime).get(0);
		//Number bloodpressure = patientinfo.getVitalsigns().get(arrivalmap).get(vitaltime).get(1);
		//Number heartrate = patientinfo.getVitalsigns().get(arrivalmap).get(vitaltime).get(2);
		
		// setting the text views
		TextView textViewName = (TextView) findViewById(R.id.textViewName);
		textViewName.setText(name);
		
		TextView textViewDob = (TextView) findViewById(R.id.textViewDob);
		textViewDob.setText(dob);
		
		TextView textViewHealthcardnum = (TextView) findViewById(R.id.textViewHealthcardnum);
		textViewHealthcardnum.setText(healthcardnum);
		
		TextView textViewArrivaltime = (TextView) findViewById(R.id.textViewArrivaltime);
		//textViewArrivaltime.setText(arrivaltime);
		
		TextView textViewTemperature = (TextView) findViewById(R.id.textViewTemperature);
		//textViewTemperature.setText(temperature.toString());
		
		TextView textViewBloodpressure = (TextView) findViewById(R.id.textViewBloodpressure);
		//textViewBloodpressure.setText(bloodpressure.toString());
		
		TextView textViewHeartrate = (TextView) findViewById(R.id.textViewHeartrate);
		//textViewHeartrate.setText(heartrate.toString());
		
		TextView textViewVitaltime = (TextView) findViewById(R.id.textViewVitaltime);
		//textViewVitaltime.setText(vitaltime);
		
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
	
	public void Save() throws FileNotFoundException {
		
		// gets all the new imputs
		EditText editTextArrivaltime = (EditText) findViewById(R.id.editTextArrivaltime);
		String arrivaltime = editTextArrivaltime.getText().toString();
		
		EditText editTextTemperature = (EditText) findViewById(R.id.editTextTemperature);
		double temp = Double.parseDouble(editTextTemperature.getText().toString());
		
		EditText editTextBloodpressure = (EditText) findViewById(R.id.editTextBloodpressure);
		int bp = Integer.parseInt(editTextBloodpressure.getText().toString());
		
		EditText editTextHeartrate = (EditText) findViewById(R.id.editTextHeartrate);
		int hr = Integer.parseInt(editTextHeartrate.getText().toString());
		
		EditText editTextVitaltime = (EditText) findViewById(R.id.editTextVitaltime);
		String vitaltime = editTextVitaltime.getText().toString();
		
		//patientinfo.setVitalsigns(arrivaltime,vitaltime,temp,bp,hr);
		
		// method for saving here
		
		// After save, goes back to the main menu
		//Organizer.saveData();
		
		Intent intentback = new Intent(this, MainActivity.class);
		startActivity(intentback);
	}
	
	public void viewArrivaltimes(View view) {
		Intent intentnext = new Intent(this, ArrivaltimesActivity.class);
		//Nurse nurse = new Nurse();
		//nurse.lookupPatient(patientdocs, healthcardnum);
		//intentnext.putExtra("Patient_info", nurse.lookupPatient(patientdocs, healthcardnum));
		startActivity(intentnext);
	}
}
