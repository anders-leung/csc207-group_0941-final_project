package com.example.triage;

import java.io.File;
import java.io.IOException;
import java.util.TreeMap;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class PhysicianMainActivity extends Activity {

	public final static String PATIENT = "PatientInfo";
	private ListView listView;
	private Physician physician;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_physician_main);

		File dir = new File(this.getApplicationContext().getFilesDir()
				.getPath());
		try {
			physician = new Physician(dir);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		TreeMap<String, Patient> patientlist = (TreeMap<String, Patient>) physician
				.getPatientlist();

		// Get ListView object from xml
		listView = (ListView) findViewById(R.id.list);

		// Defined Array values to show in ListView
		String hcns = "";
		for (String hcn : patientlist.keySet()) {
			hcns += hcn + ",";
		}

		hcns = hcns.substring(0, hcns.length() - 1);
		String[] values = hcns.split(",");

		// Define a new Adapter
		// First parameter - Context
		// Second parameter - Layout for the row
		// Third parameter - ID of the TextView to which the data is written
		// Fourth - the Array of data

		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1, android.R.id.text1, values);

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

				Intent intent = new Intent(PhysicianMainActivity.this,
						PatientActivity.class);
				File dir = new File(PhysicianMainActivity.this
						.getApplicationContext().getFilesDir().getPath());
				try {
					physician = new Physician(dir);
					intent.putExtra(PATIENT, physician.lookupPatient(itemValue));
					intent.putExtra("caller", "PhysicianActivity");
					startActivity(intent);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}

		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.physician, menu);
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
	    switch (item.getItemId()) {
	    case R.id.action_logout:
	        logout();
	        return true;
	    default:
	        return super.onOptionsItemSelected(item);
	    }
	}

	public void goPrescription(View view) {
		Intent intent = new Intent(this, NewPrescriptionActivity.class);
		startActivity(intent);
	}
	
	public void logout() {
		Intent intent = new Intent(this, UserActivity.class);
		startActivity(intent);
	}
}
