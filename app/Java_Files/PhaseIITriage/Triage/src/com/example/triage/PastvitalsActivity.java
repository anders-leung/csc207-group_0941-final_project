package com.example.triageii;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;

public class PastvitalsActivity extends Activity {
	
	public static final String PATIENT = "PatientInfo";
	public final static String ARRIVALTIME = "ARRIVALTIME";
	private Patient patientinfo;
	private Intent intent;
	private String vitals;	
	private String arrivaltime;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_pastvitals);
		
		intent = getIntent();
		patientinfo = (Patient) intent.getExtras().get(VitaltimesActivity.PATIENT);
		vitals = intent.getStringExtra(VitaltimesActivity.VITALS);
		arrivaltime = intent.getStringExtra(ArrivaltimesActivity.ARRIVALTIME);
		
		// method for vital information here
		
		TextView textView = (TextView) findViewById(R.id.textView);
		textView.setText(vitals);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.pastvitals, menu);
		return true;
	}
	
	public void goBack(View view) {
		Intent intentback = new Intent(this, VitaltimesActivity.class);
		intentback.putExtra(PATIENT, patientinfo);
		intentback.putExtra(ARRIVALTIME, arrivaltime);
		intentback.putExtra("caller", "PastvitalsActivity");
		startActivity(intentback);
	}
}