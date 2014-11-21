package com.example.triage;

import java.io.IOException;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;

public class UserActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_user);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.user, menu);
		return true;
	}
	
	public void login(View view) throws IOException {
		
		EditText usernameText = (EditText) findViewById(R.id.editTextUsername);
		String username = usernameText.getText().toString();
		
		EditText passwordText = (EditText) findViewById(R.id.editTextPassword);
		String password = passwordText.getText().toString();
		
		User user = new User(username, password);
		if (user.login()) {
			if (user.getJob().equals("nurse")) {
				Intent intent = new Intent(this, MainActivity.class);
				startActivity(intent);
			} else {
				Intent intent = new Intent(this, PhysicianMainActivity.class);
				startActivity(intent);
			}
		}
	}
}
