package com.example.triageii;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;

public class NewPatientActivity extends Activity {

	private Nurse nurse;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_new_patient);
		File dir = new File(this.getApplicationContext().getFilesDir()
				.getPath());
		try {
			nurse = new Nurse(dir);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.new_patient, menu);
		return true;
	}

	public void createPatient(View view) throws FileNotFoundException {
		EditText nameText = (EditText) findViewById(R.id.editTextNewname);
		String name = nameText.getText().toString();

		EditText dobText = (EditText) findViewById(R.id.editTextNewDob);
		String dob = dobText.getText().toString();

		EditText hcnText = (EditText) findViewById(R.id.editTextNewHcn);
		String hcn = hcnText.getText().toString();

		// saves info to create patient
		nurse.addPatient(name, dob, hcn);

		Intent intent = new Intent(this, MainActivity.class);
		startActivity(intent);

	}

	public void goBack(View v) {
		Intent intent = new Intent(this, MainActivity.class);
		startActivity(intent);
	}

}
