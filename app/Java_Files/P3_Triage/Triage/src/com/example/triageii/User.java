package com.example.triageii;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

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
	
	public boolean login(File dir) throws IOException {
		Launch launch = new Launch(dir);
		String logininfo = this.username + "," + this.password;
		ArrayList<String> nurses;
		ArrayList<String> physicians;
		nurses = launch.getUsers().get("nurse");
		physicians = launch.getUsers().get("physician");
		if (launch.getUsers().isEmpty()) {
			return false;
		}
		if (nurses.contains(logininfo)) {
			this.setJob("nurse");
			return true;
		} else if (physicians.contains(logininfo)) {
			this.setJob("physician");
			return true;
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
		User user = new User("anders", "leung");
		File dir = new File("C:\\Users\\Anders\\Desktop");
		System.out.println(user.login(dir));
		System.out.println(user.job);
	}
}
