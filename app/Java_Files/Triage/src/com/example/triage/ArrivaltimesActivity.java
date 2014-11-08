package com.example.triage;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class ArrivaltimesActivity extends Activity {
	
	public final static String ARRIVALTIME = "ARRIVALTIME";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		SharedPreferences patient = getSharedPreferences("com.example.triage", 0);
		String healthcardnum = patient.getString("healhcardnumber", "N/A");
		
		// run method to get arrival times
		
		TextView textView = (TextView) findViewById(R.id.textViewArrivaltimes);
		// textView.setText(list of arrival times);
		
		setContentView(R.layout.activity_arrivaltimes);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.arrivaltimes, menu);
		return true;
	}

	public void goBack(View view) {
		Intent intent = new Intent(this, PatientActivity.class);
		startActivity(intent);
	}	
	
	public void viewVitaltimes(View view) {
		Intent intent = new Intent(this, VitaltimesActivity.class);
		//gets desired arrival time
		EditText editText = (EditText) findViewById(R.id.editTextArrivalinfo);
		intent.putExtra(ARRIVALTIME, editText.getText());
		startActivity(intent);
	}
}
