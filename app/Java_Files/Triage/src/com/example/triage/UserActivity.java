package com.example.triage;

import java.io.File;
import java.io.IOException;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class UserActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Toast.makeText(getApplicationContext(), "Loading",
				Toast.LENGTH_SHORT).show();
		setContentView(R.layout.activity_user);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.user, menu);
		return true;
	}
	
	public void Login(View view) throws IOException {
		
		EditText usernameText = (EditText) findViewById(R.id.editTextUsername);
		String username = usernameText.getText().toString();
		
		EditText passwordText = (EditText) findViewById(R.id.editTextPassword);
		String password = passwordText.getText().toString();
		
		User user = new User(username, password);
		File dir = new File(this.getApplicationContext().getFilesDir().getPath());
		if (user.login(dir)) {
			if (user.getJob().equals("nurse")) {
				Toast.makeText(getApplicationContext(), "Logging on", 
					      Toast.LENGTH_SHORT).show();
				Intent intent = new Intent(this, MainActivity.class);
				startActivity(intent);
			} else {
				Toast.makeText(getApplicationContext(), "Logging on", 
					      Toast.LENGTH_SHORT).show();
				Intent intent = new Intent(this, PhysicianMainActivity.class);
				startActivity(intent);
			}
		} else {
			Toast.makeText(getApplicationContext(), 
					"Incorrect username or password", 
					Toast.LENGTH_SHORT).show();
		}
	}
}
