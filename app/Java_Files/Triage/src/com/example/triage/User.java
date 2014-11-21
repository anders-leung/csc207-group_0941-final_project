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
	
	public User() {
		
	}
	
	public boolean login() throws IOException {
		String[] logininfo = {this.username,this.password};
		if (Launch.getUsers().containsValue(logininfo)) {
			if (Launch.getUsers().get("nurse").contains(logininfo)) {
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
	
	public static void main(String[] args) throws IOException {
		User user = new User("yorgos", "34234dd");
		System.out.println(user.login());
	}
}
