package com.example.triageii;

import java.io.File;
import java.io.IOException;
import java.util.HashSet;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
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
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.vitals, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	public void Save(View v) throws IOException {
		Intent intent = new Intent(this, PatientActivity.class);
		String doctime = ((EditText) findViewById(R.id.editDoctime)).getText()
				.toString();
		String arrivaltime = ((EditText) findViewById(R.id.editArrivaltime))
				.getText().toString();
		String vitaltime = ((EditText) findViewById(R.id.editVitaltime))
				.getText().toString();
		String stringTemp = ((EditText) findViewById(R.id.editTemperature))
				.getText().toString();
		String stringBp = ((EditText) findViewById(R.id.editBloodpressure))
				.getText().toString();
		String stringHr = ((EditText) findViewById(R.id.editHeartrate))
				.getText().toString();
		HashSet<String> info = new HashSet<String>();
		info.add(arrivaltime);
		info.add(vitaltime);
		info.add(stringTemp);
		info.add(stringBp);
		info.add(stringHr);
		Double temp;
		Integer bp;
		String meas;
		Integer hr;
		if (patientinfo.getVitalsigns().isEmpty()) {
			if (info.contains("")) {
				Toast.makeText(getApplicationContext(), "Missing information!",
						Toast.LENGTH_SHORT).show();
			} else {
				temp = Double.parseDouble(stringTemp);
				bp = Integer.parseInt(stringBp.split(" ")[0]);
				meas = stringBp.split(" ")[1];
				hr = Integer.parseInt(stringHr);
				nurse.newVisitRecord(arrivaltime, vitaltime, temp, bp,
						meas, hr);
			}
		} else {
			if (arrivaltime.matches("")) {
				arrivaltime = patientinfo.getVitalsigns().descendingKeySet()
						.first();
				String lastvital = patientinfo.getVitalsigns().get(arrivaltime)
						.descendingKeySet().first();
				if (stringTemp.matches("")) {
					temp = (Double) patientinfo.getVitalsigns()
							.get(arrivaltime).get(lastvital).get(0);
				} else {
					temp = Double.parseDouble(stringTemp);
				}
				if (stringBp.matches("")) {
					bp = (Integer) patientinfo.getVitalsigns().get(arrivaltime)
							.get(lastvital).get(1);
					meas = (String) patientinfo.getVitalsigns()
							.get(arrivaltime).get(lastvital).get(2);
				} else {
					bp = Integer.parseInt(stringBp.split(" ")[0]);
					meas = stringBp.split(" ")[1];
				}
				if (stringHr.matches("")) {
					hr = (Integer) patientinfo.getVitalsigns().get(arrivaltime)
							.get(lastvital).get(3);
				} else {
					hr = Integer.parseInt(stringHr);
				}
				nurse.updatePatient(vitaltime, temp, bp, meas, hr);
			} else {
				temp = Double.parseDouble(stringTemp);
				bp = Integer.parseInt(stringBp.split(" ")[0]);
				meas = stringBp.split(" ")[1];
				hr = Integer.parseInt(stringHr);
				nurse.newVisitRecord(arrivaltime, vitaltime, temp, bp,
						meas, hr);
				nurse.lookupPatient(patientinfo.hcn);
			}
		}
		if (doctime.matches("")) {

		} else {
			nurse.seenByDoctor(doctime);
		}
		File dir = new File(this.getApplicationContext().getFilesDir().getPath());
		nurse = new Nurse(dir);
		patientinfo = nurse.lookupPatient(patientinfo.hcn);
		intent.putExtra(PATIENT, patientinfo);
		intent.putExtra("caller", "VitalsActivity");
		startActivity(intent);
	}
	
	public void goBack(View v) {
		Intent intent = new Intent(this, PatientActivity.class);
		intent.putExtra(PATIENT, patientinfo);
		intent.putExtra("caller", "VitalsActivity");
		startActivity(intent);
	}
}
