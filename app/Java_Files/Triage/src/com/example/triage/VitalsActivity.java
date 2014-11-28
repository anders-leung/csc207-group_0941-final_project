package com.example.triage;

import java.io.File;
import java.io.IOException;
import java.util.HashSet;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class VitalsActivity extends Activity {

	public final static String PATIENT = "PatientInfo";
	private Patient patientinfo;
	private Nurse nurse;
	private HashSet<String> info = new HashSet<String>();
	private String timeSeenByDoctor;
	private String timeOfArrival;
	private String timeVitalsTaken;
	private String stringTemperature;
	private String stringBloodPressure;
	private String measurement;
	private String stringHeartRate;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_vitals);
		Intent intent = getIntent();
		patientinfo = (Patient) intent.getExtras().get(PatientActivity.PATIENT);

		File dir = new File(this.getApplicationContext().getFilesDir()
				.getPath());
		try {
			nurse = new Nurse(dir);
			nurse.lookupPatient(patientinfo.hcn);
		} catch (IOException e) {
			e.printStackTrace();
		}

		TextView textViewName = (TextView) findViewById(R.id.textName);
		textViewName.setText(patientinfo.name);
		TextView textViewDob = (TextView) findViewById(R.id.textDob);
		textViewDob.setText(patientinfo.dob);
		TextView textViewHcn = (TextView) findViewById(R.id.textHealthcardnum);
		textViewHcn.setText(patientinfo.hcn);
		TextView timeseenbydoctor = (TextView) findViewById(R.id.textPatientdoctime);
		if (patientinfo.getSeenbydoctor().isEmpty()) {
			timeseenbydoctor.setText("Not yet seen by a doctor.");
		} else {
			String[] times = patientinfo.getSeenbydoctor().split(",");
			String doctimes = "";
			for (String time : times) {
				doctimes += time.substring(0, 10) + " "
						+ time.substring(11, time.length()) + ", ";
			}
			
			timeseenbydoctor.setText(doctimes.substring(0, doctimes.length() - 2));
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.vitals, menu);
		return true;
	}

	public void compileEditTexts() {
		this.timeSeenByDoctor = ((EditText) findViewById(R.id.editDoctime))
				.getText().toString();
		this.timeOfArrival = ((EditText) findViewById(R.id.editArrivaltime))
				.getText().toString();
		this.timeVitalsTaken = ((EditText) findViewById(R.id.editVitaltime))
				.getText().toString();
		this.stringTemperature = ((EditText) findViewById(R.id.editTemperature))
				.getText().toString();
		this.stringBloodPressure = ((EditText) findViewById(R.id.editBloodpressure))
				.getText().toString();
		this.stringHeartRate = ((EditText) findViewById(R.id.editHeartrate))
				.getText().toString();
		info.add(timeOfArrival);
		info.add(timeVitalsTaken);
		info.add(stringTemperature);
		info.add(stringBloodPressure);
		info.add(stringHeartRate);
		for (String i : info) {
			System.out.println(i);
		}
	}

	public void Save(View v) throws IOException {
		Intent intent = new Intent(this, PatientActivity.class);
		compileEditTexts();
		Double temperature = Double.parseDouble(stringTemperature);
		Integer bloodPressure = Integer
				.parseInt(stringBloodPressure.split(" ")[0]);
		measurement = stringBloodPressure.split(" ")[1];
		Integer heartRate = Integer.parseInt(stringHeartRate);
		if (patientinfo.getVitalsigns().isEmpty()) {
			if (info.contains("")) {
				Toast.makeText(getApplicationContext(), "Missing information!",
						Toast.LENGTH_SHORT).show();
				return;
			} else {
				nurse.newVisitRecord(timeOfArrival, timeVitalsTaken,
						temperature, bloodPressure, measurement, heartRate);
			}
		} else {
			if (timeOfArrival.matches("")) {
				Log.v("VitalsActivity", "time of arrival is empty");
				timeOfArrival = patientinfo.getVitalsigns().descendingKeySet()
						.first();
				String timeVitalsTaken = patientinfo.getVitalsigns()
						.get(timeOfArrival).descendingKeySet().first();
				if (timeVitalsTaken.matches("")) {
					Toast.makeText(getApplicationContext(),
							"Time Vitals Taken field required",
							Toast.LENGTH_SHORT).show();
					return;
				}
				if (stringTemperature.matches("")) {
					temperature = (Double) patientinfo.getLastVitalValue(0);
				}
				if (stringBloodPressure.matches("")) {
					bloodPressure = (Integer) patientinfo.getLastVitalValue(1);
					measurement = (String) patientinfo.getLastVitalValue(2);
				}
				if (stringHeartRate.matches("")) {
					heartRate = (Integer) patientinfo.getLastVitalValue(3);
				}
				nurse.updatePatient(timeVitalsTaken, temperature,
						bloodPressure, measurement, heartRate);
			} else {
				nurse.newVisitRecord(timeOfArrival, timeVitalsTaken,
						temperature, bloodPressure, measurement, heartRate);
			}
		}
		nurse.setTimeSeenByDoctor(timeSeenByDoctor);
		File dir = new File(this.getApplicationContext().getFilesDir()
				.getPath());
		nurse = new Nurse(dir);
		patientinfo = nurse.lookupPatient(patientinfo.hcn);
		intent.putExtra(PATIENT, patientinfo);
		intent.putExtra("caller", "VitalsActivity");
		Toast.makeText(getApplicationContext(), "Saving information...",
				Toast.LENGTH_SHORT).show();
		startActivity(intent);
	}

	public void goBack(View v) {
		Intent intent = new Intent(this, PatientActivity.class);
		intent.putExtra(PATIENT, patientinfo);
		intent.putExtra("caller", "VitalsActivity");
		startActivity(intent);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if (item.getItemId() == R.id.action_logout) {
			logout();
			return true;
		} else {
			return super.onOptionsItemSelected(item);
		}
	}

	public void logout() {
		Intent intent = new Intent(this, UserActivity.class);
		Toast.makeText(getApplicationContext(), "Logging out...",
				Toast.LENGTH_LONG).show();
		startActivity(intent);
	}
}
