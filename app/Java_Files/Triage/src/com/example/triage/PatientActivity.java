package com.example.triage;

import java.io.File;
import java.io.IOException;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class PatientActivity extends Activity {

	public final static String PATIENT = "PatientInfo";
	private Intent intent;
	private Patient patientinfo;
	private ListView listView;
	private Nurse nurse;
	private String timeseenbydoctor;
	private File dir;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_patient);

		intent = getIntent();
		String caller = intent.getStringExtra("caller");

		if (caller.equals("MainActivity")) {
			patientinfo = (Patient) intent.getExtras()
					.get(MainActivity.PATIENT);
		} else if (caller.equals("UrgencyListActivity")) {
			patientinfo = (Patient) intent.getExtras().get(
					UrgencyListActivity.PATIENT);
		} else {
			patientinfo = (Patient) intent.getExtras().get(
					PatientActivity.PATIENT);
		}
		dir = new File(this.getApplicationContext().getFilesDir().getPath());
		try {
			nurse = new Nurse(dir);
			nurse.lookupPatient(patientinfo.hcn);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		TextView name = (TextView) findViewById(R.id.patientname);
		name.setText("Name: " + patientinfo.name);

		TextView dob = (TextView) findViewById(R.id.patientdob);
		dob.setText("Date of Birth: " + patientinfo.dob);

		TextView hcn = (TextView) findViewById(R.id.patienthcn);
		hcn.setText("Health Card Number: " + patientinfo.hcn);

		TextView timeseenbydoctor = (TextView) findViewById(R.id.textpatientdoctime);
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

		TextView prescription = (TextView) findViewById(R.id.textPrescription);
		String prescriptions = patientinfo.getPrescription();
		if (prescriptions.isEmpty()) {
			prescription.setText("Patient has no prescription.");
		} else {
			prescription.setText(prescriptions);
		}

		// Get ListView object from xml
		listView = (ListView) findViewById(R.id.list);

		// Defined Array values to show in ListView
		String[] values = patientinfo.getVitalSignsString().split(",");

		// Define a new Adapter
		// First parameter - Context
		// Second parameter - Layout for the row
		// Third parameter - ID of the TextView to which the data is written
		// Fourth - the Array of data

		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_2, android.R.id.text2, values);

		// Assign adapter to ListView
		listView.setAdapter(adapter);

		// ListView Item Click Listener
		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {

			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.patient, menu);
		return true;
	}

	public void updateVitals(View v) {
		Intent intent = new Intent(this, VitalsActivity.class);
		intent.putExtra(PATIENT, patientinfo);
		Toast.makeText(getApplicationContext(), "Loading page...",
				Toast.LENGTH_SHORT).show();
		startActivity(intent);
	}

	public void goBack(View v) {
		Intent intent = new Intent(this, MainActivity.class);
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

	public void seenByDoctor(View v) throws IOException {
		EditText editseenbydoctor = (EditText) findViewById(R.id.editDoctime);
		timeseenbydoctor = editseenbydoctor.getText().toString();
		nurse.setTimeSeenByDoctor(timeseenbydoctor);
		Intent intent = new Intent(this, PatientActivity.class);
		intent.putExtra(PATIENT, patientinfo);
		intent.putExtra("caller", "PatientActivity");
		Toast.makeText(getApplicationContext(), "Saving...",
				Toast.LENGTH_LONG).show();
		startActivity(intent);
	}

	public void logout() {
		Intent intent = new Intent(this, UserActivity.class);
		Toast.makeText(getApplicationContext(), "Logging out...",
				Toast.LENGTH_LONG).show();
		startActivity(intent);
	}
}