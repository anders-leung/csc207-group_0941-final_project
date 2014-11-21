package com.example.triage;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;

public class NewPatientActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_new_patient);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.new_patient, menu);
		return true;
	}
	
	public void createPatient(View view) {
		EditText nameText = (EditText) findViewById(R.id.editTextNewname);
		String name = nameText.getText().toString();
		
		EditText dobText = (EditText) findViewById(R.id.editTextNewDob);
		String dob = dobText.getText().toString();
		
		EditText hcnText = (EditText) findViewById(R.id.editTextNewHcn);
		String hcn = hcnText.getText().toString();
		
		// saves info to create patient
		
		Intent intent = new Intent(this, MainActivity.class);
		startActivity(intent);
		
	}

	public void goBack() {
		Intent intent = new Intent(this, MainActivity.class);
		startActivity(intent);
	}
	
}
