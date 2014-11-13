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
	
	private Map<String, ArrayList<String>> users;
	
	public Launch(File dir, String fileName) throws IOException {
		this.users = new HashMap<String, ArrayList<String>>();
		File file = new File(dir, fileName);
		if (file.exists()) {
			this.populate(file.getPath());
		} else {
			file.createNewFile();
		}
	}
	public void populate(String filePath) throws FileNotFoundException {
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
		}
	}
	
	/**
	public static void main(String[] args) {
		File directory = new File("/h/u8/c3/00/c3leungb/");
		Launch launch = null;
		try {
			launch = new Launch(directory, "passwords.txt");
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try {
			launch.populate("/h/u8/c3/00/c3leungb/passwords.txt");
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(launch.users);
	}*/
}
