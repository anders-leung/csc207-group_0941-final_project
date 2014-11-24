package com.example.triageii;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Set;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class PatientActivity extends Activity {
	
	public final static String PATIENT = "PatientInfo";
	private Intent intent;
	private Patient patientinfo;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);	
		
		setContentView(R.layout.activity_patient);
		
		intent = getIntent();
		
		String caller = intent.getStringExtra("caller");
		Log.v("PatientActivity", caller);
		if (caller.equals("MainActivity")) {
			patientinfo = (Patient) intent.getExtras().get(MainActivity.PATIENT);
			Log.v("PatientActivity", patientinfo.name);
		} else {
			patientinfo = (Patient) intent.getExtras().get(ArrivaltimesActivity.PATIENT);
		}
		
		TextView textViewName = (TextView) findViewById(R.id.textViewName);
		textViewName.setText(patientinfo.name);
		
		TextView textViewDob = (TextView) findViewById(R.id.textViewDob);
		textViewDob.setText(patientinfo.dob);
		
		TextView textViewHealthcardnum = (TextView) findViewById(R.id.textViewHealthcardnum);
		textViewHealthcardnum.setText(patientinfo.hcn);
		
		Set<String> arrivalkeys = patientinfo.getVitalsigns().keySet();
		if (!(arrivalkeys.isEmpty())) {
			Object[] arrivalmap = arrivalkeys.toArray();
			String arrivaltime = arrivalmap[arrivalmap.length-1].toString();
			Object[] vitalmap = patientinfo.getVitalsigns().get(arrivaltime).keySet().toArray();
			String vitaltime = vitalmap[vitalmap.length-1].toString();
			Number temperature = patientinfo.getVitalsigns().get(arrivaltime).get(vitaltime).get(0);
			Number bloodpressure = patientinfo.getVitalsigns().get(arrivaltime).get(vitaltime).get(1);
			Number heartrate = patientinfo.getVitalsigns().get(arrivaltime).get(vitaltime).get(2);
			
			// setting the text views
			TextView textViewArrivaltime = (TextView) findViewById(R.id.textViewArrivaltime);
			textViewArrivaltime.setText(arrivaltime);
			
			TextView textViewVitaltime = (TextView) findViewById(R.id.textViewVitaltime);
			textViewVitaltime.setText(vitaltime);
			
			TextView textViewTemperature = (TextView) findViewById(R.id.textViewTemperature);
			textViewTemperature.setText(temperature.toString());
			
			TextView textViewBloodpressure = (TextView) findViewById(R.id.textViewBloodpressure);
			textViewBloodpressure.setText(bloodpressure.toString());
			
			TextView textViewHeartrate = (TextView) findViewById(R.id.textViewHeartrate);
			textViewHeartrate.setText(heartrate.toString());
		}	
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.patient, menu);
		return true;
	}
	
	public void goBack(View view) {
		Intent intent = new Intent(this, MainActivity.class);
		startActivity(intent);
	}
	
	public void Save(View view) throws IOException {
		
		// gets all the new inputs
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
		
		File dir = new File(this.getApplicationContext().getFilesDir().getPath());
		File file = new File(dir, "patient_vitals.txt");
		FileOutputStream outputStream = new FileOutputStream(file);
		Nurse nurse = new Nurse(dir);
		nurse.lookupPatient(patientinfo.hcn);
		nurse.updatepatient(outputStream,arrivaltime,vitaltime,temp,bp,hr);
		
		// method for saving here
		
		// After save, goes back to the main menu
		Intent intentback = new Intent(this, MainActivity.class);
		startActivity(intentback);
	}
	
	public void viewArrivaltimes(View view) throws IOException {
		
		Intent intentnext = new Intent(this, ArrivaltimesActivity.class);
		intentnext.putExtra(PATIENT, patientinfo);
		intentnext.putExtra("caller", "PatientActivity");
		startActivity(intentnext);
	}
}