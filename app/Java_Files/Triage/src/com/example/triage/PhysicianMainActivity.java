package com.example.triage;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;

public class PhysicianMainActivity extends Activity {
	
	public final static String HEALTHCARDNUM = "HealthCardNum";
	public final static String PATIENT = "PatientInfo";	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_physician_main);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.physician_main, menu);
		return true;
	}
	
	public void lookupPatient(View view) {
		
		SharedPreferences patient = this.getSharedPreferences("com.example.triage", 0);
		SharedPreferences.Editor prefEditor = patient.edit();
		
		EditText editText = (EditText) findViewById(R.id.editHealthcardnum);
		String healthcardnum = editText.getText().toString();
		
		prefEditor.putString("healthcardnumber", healthcardnum);
		prefEditor.commit();
		
		//Nurse nurse = new Nurse();
		//nurse.lookupPatient(patientdocs, healthcardnum);
		
		//sends patient information here
		//intent.putExtra("Patient_info", nurse.lookupPatient(patientdocs, healthcardnum));		
		
		Intent intent = new Intent(this, PhysicianActivity.class);
		startActivity(intent);
	}
	

}
