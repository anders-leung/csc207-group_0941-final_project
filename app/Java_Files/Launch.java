package com.example.triage;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;


public class Launch {
	
	private Map<String, ArrayList<String>> users = 
			new TreeMap<String, ArrayList<String>>();
	
	
	public void populate() throws FileNotFoundException {
		Scanner scanner = new Scanner(
				new FileInputStream("/h/u8/c3/00/c3leungb/passwords.txt"));
		String [] record;
		while (scanner.hasNextLine()) {
			record = scanner.nextLine().split(",");
			String username = record[0];
			String password = record[1];
			String user = record[2];
			if (users.containsKey(user)) {
				users.get(user).add(username + "," + password);
			} else {
				ArrayList<String> login = new ArrayList<String>();
				login.add(username + "," + password);
				users.put(user, login);
			}
		}
	}
	
	/*public void main(String[] args) throws FileNotFoundException {
		populate();
	}*/
}
