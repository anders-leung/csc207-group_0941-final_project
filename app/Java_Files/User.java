package com.example.triage;

import java.io.IOException;

public class User {
	
	private String username;
	private String password;
	private String job;
	
	public User(String un, String pw) {
		this.username = un;
		this.password = pw;
	}
	
	public boolean login() throws IOException {
		Launch launch = new Launch();
		String[] logininfo = {this.username,this.password};
		if (launch.getUsers().containsValue(logininfo)) {
			if (launch.getUsers().get("nurse").contains(logininfo)) {
				this.setJob("nurse");
				return true;
			} else {
				this.setJob("physician");
				return true;
			}
		} else {
			return false;
		}
	}

	public String getJob() {
		return job;
	}

	public void setJob(String job) {
		this.job = job;
	}
}
