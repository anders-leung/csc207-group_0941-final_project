package com.example.triageii;

import java.io.File;
import java.io.IOException;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class ArrivaltimesActivity extends Activity {
	
	public final static String ARRIVALTIME = "ARRIVALTIME";
	public final static String PATIENT = "PatientInfo";
	private Patient patientinfo;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_arrivaltimes);
		
		Intent intent = getIntent();
		String caller = getIntent().getStringExtra("caller");
		Log.v("ArrivaltimesActivity", caller);
		if (caller.equals("PatientActivity")) {
			this.patientinfo = (Patient) intent.getExtras().get(PatientActivity.PATIENT);
		} else {
			this.patientinfo = (Patient) intent.getExtras().get(VitaltimesActivity.PATIENT);
		}
		TextView textView = (TextView) findViewById(R.id.textViewArrivaltimes);
		String arrivalmap = this.patientinfo.getVitalsigns().keySet().toString();
		textView.setText(arrivalmap);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.arrivaltimes, menu);
		return true;
	}

	public void goBack(View view) {
		Intent intentback = new Intent(this, PatientActivity.class);
		intentback.putExtra(PATIENT, patientinfo);
		intentback.putExtra("caller", "ArrivalimesActivity");
		startActivity(intentback);
	}	
	
	public void viewVitaltimes(View view) throws IOException {
		Intent intentnext = new Intent(this, VitaltimesActivity.class);
		//gets desired arrival time
		EditText editText = (EditText) findViewById(R.id.editTextArrivalinfo);
		intentnext.putExtra(ARRIVALTIME, editText.getText().toString());
		intentnext.putExtra(PATIENT, patientinfo);
		intentnext.putExtra("caller", "ArrivaltimesActivity");
		startActivity(intentnext);
	}
}