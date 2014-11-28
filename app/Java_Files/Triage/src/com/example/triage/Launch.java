package com.example.triage;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;



public class Launch {
	
	private Map<String, ArrayList<String>> users = 
			new HashMap<String, ArrayList<String>>();
	
	public Launch(File dir) throws IOException {
		File file = new File(dir, "passwords.txt");
		if (file.exists()) {
			this.populate(file.getPath());
		} else {
			file.createNewFile();
		}
	}
	
	private void populate(String filePath) throws FileNotFoundException {
		Scanner scanner = new Scanner(new FileInputStream(filePath));
		String [] record;
		while (scanner.hasNextLine()) {
			record = scanner.nextLine().split(",");
			String username = record[0];
			String password = record[1];
			String user = record[2];
			if (this.users.containsKey(user)) {
				users.get(user).add(username + "," + password);
			} else {
				ArrayList<String> login = new ArrayList<String>();
				login.add(username + "," + password);
				users.put(user, login);
			}
		} scanner.close();
	}
	
	public Map<String, ArrayList<String>> getUsers() throws FileNotFoundException {
		return this.users;
	}
	
	public static void main(String[] args) throws IOException {
		File dir = new File("C:\\Users\\Anders\\Desktop");
		Launch launch = new Launch(dir);
		System.out.println(launch.getUsers());
	}
}
