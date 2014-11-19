package com.example.triage;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;



public class Launch {
	
	private static Map<String, ArrayList<String>> users = 
			new HashMap<String, ArrayList<String>>();
	
	private static void populate() throws FileNotFoundException {
		Scanner scanner = new Scanner(new FileInputStream("passwords.txt"));
		String [] record;
		while (scanner.hasNextLine()) {
			record = scanner.nextLine().split(",");
			String username = record[0];
			String password = record[1];
			String user = record[2];
			if (Launch.users.containsKey(user)) {
				users.get(user).add(username + "," + password);
			} else {
				ArrayList<String> login = new ArrayList<String>();
				login.add(username + "," + password);
				users.put(user, login);
			}
		}
	}
	
	public static Map<String, ArrayList<String>> getUsers() throws FileNotFoundException {
		populate();
		return Launch.users;
	}
	
	public static void main(String[] args) throws IOException {
		System.out.println(Launch.getUsers());
	}
}
