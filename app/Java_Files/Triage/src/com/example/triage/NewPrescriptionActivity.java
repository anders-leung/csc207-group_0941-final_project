package com.example.triage;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;

public class NewPrescriptionActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_new_prescription);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.new_prescription, menu);
		return true;
	}
	
	public void savePrescription(View view) {
		EditText prescriptiontext = (EditText) findViewById(R.id.editText1);
		String prescription = prescriptiontext.getText().toString();
		
		//save prescription
		Intent intent = new Intent(this, PhysicianActivity.class);
		startActivity(intent);
	}
	public void goBack(View view) {
		Intent intent = new Intent(this, PhysicianActivity.class);
		startActivity(intent);		
	}

}
