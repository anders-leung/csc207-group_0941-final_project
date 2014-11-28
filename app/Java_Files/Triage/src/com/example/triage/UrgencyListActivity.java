package com.example.triage;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.TreeMap;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class UrgencyListActivity extends Activity {

	public final static String PATIENT = "PatientInfo";
	private ListView listView;
	private Nurse nurse;
	private TreeMap<Integer, ArrayList<Patient>> patientlist;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_urgency_list);
		File dir = new File(this.getApplicationContext().getFilesDir()
				.getPath());
		try {
			nurse = new Nurse(dir);
			patientlist = nurse.notseenbydoctor();
		} catch (IOException e) {
			e.printStackTrace();
		}

		// Get ListView object from xml
		listView = (ListView) findViewById(R.id.list);

		// Defined Array values to show in ListView
		String patients = "";
		for (ArrayList<Patient> a : patientlist.values()) {
			for (Patient patient : a) {
				patients += "								  				" + "									Urgency Points: "
						+ patient.getUrgency()
						+ System.getProperty("line.separator")
						+ patient.toString() + ",";
			}
		}

		String[] values = patients.split(",");
		for (String records : values) {
			Log.v("UrgencyListActivity", records);
		}
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

				// ListView Clicked item value
				String itemValue = (String) listView
						.getItemAtPosition(position);

				// Show Alert
				Toast.makeText(getApplicationContext(),
						"Retrieving patient...", Toast.LENGTH_LONG).show();

				Intent intent = new Intent(UrgencyListActivity.this,
						PatientActivity.class);
				String hcnline = itemValue.split(System
						.getProperty("line.separator"))[3];
				String hcn = hcnline.split(" ")[3];
				intent.putExtra(PATIENT, nurse.lookupPatient(hcn));
				intent.putExtra("caller", "UrgencyListActivity");
				startActivity(intent);
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.urgency_list, menu);
		return true;
	}

	public void goBack(View view) {
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

	public void logout() {
		Intent intent = new Intent(this, UserActivity.class);
		Toast.makeText(getApplicationContext(), "Logging out...",
				Toast.LENGTH_LONG).show();
		startActivity(intent);
	}

}
