package com.example.triage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;

public class MainActivity extends Activity {
	
	private Map<String, ArrayList<String>> logindatabase;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	public void login(View view) throws IOException {
		Launch launch = new Launch();
		this.logindatabase = launch.getUsers();
		
		EditText usernameText = (EditText) findViewById(R.id.editUsername);
		String username = usernameText.getText().toString();
		
		EditText passwordText = (EditText) findViewById(R.id.editPassword);
		String password = passwordText.getText().toString();
		
		User user = new User(username, password);
		if (user.login()) {
			if (user.getJob().equals("nurse")) {
				Intent intent = new Intent(this, NurseActivity.class);
				startActivity(intent);
			} else {
				Intent intent = new Intent(this, PhysicianActivity.class);
				startActivity(intent);
			}
		}
	}
}
