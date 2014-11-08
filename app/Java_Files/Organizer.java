package com.example.triage;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

public class Organizer {

	private static HashMap<String, Patient> hcnToPatient = new HashMap<String, Patient>();
	
	/**
	 * @param args
	 * @throws FileNotFoundException 
	 */
	public static void main(String[] args) throws FileNotFoundException {
		// TODO Auto-generated method stub
		
		populatePatients();
		addPatientData();
		
		Nurse nurse = new Nurse();
	}
	
	/**
	 * Reads a given file to generate a list of patients.
	 * @throws FileNotFoundException
	 */
	private static void populatePatients() throws FileNotFoundException {
		
		Scanner scanner = 
				new Scanner(new FileInputStream("patient_records.txt"));
		ArrayList<Patient> patients = new ArrayList<Patient>();
		
		String [] patientData;
		String hcn;
		String dob;
		String name;
		
		while (scanner.hasNextLine()) {
			patientData = scanner.nextLine().split(",");
			hcn = patientData[0];
			name = patientData[1];
			dob = patientData[2];
			patients.add(new Patient(name, dob, hcn));
		}
		scanner.close();
		
		for (Patient p: patients){
			hcnToPatient.put(p.hcn, p);
		}
		
	}
	
	/**
	 * Reads a file to add generate a list of patients' data.
	 * @throws FileNotFoundException
	 */
	private static void addPatientData() throws FileNotFoundException {
		
		Scanner scanner =
				new Scanner(new FileInputStream("patient_vitals.txt"));
		
		Patient patient;
		String [] patientData;
		String [] data;
		String hcn;
		String toa;
		String vitaltime;
		double temp;
		int bloodpressure;
		int heartrate;
		
		while (scanner.hasNextLine()) {
			patientData = scanner.nextLine().split(" ");
			hcn = patientData[0];
			patient = hcnToPatient.get(hcn);
			for (int i = 1; i < patientData.length; i++) {
				data = patientData[i].split(",");
				toa = data[0];
				vitaltime = data[1];
				temp = Double.parseDouble(data[2]);
				bloodpressure = Integer.parseInt(data[3]);
				heartrate = Integer.parseInt(data[4]);
				patient.setVitalsigns(
						toa, vitaltime, temp, bloodpressure, heartrate);
			}
		}
		
		scanner.close();
		
	}
	
	/**
	 * Saves data for all patients to a file.
	 * @throws FileNotFoundException
	 */
	public static void saveData() throws FileNotFoundException {
		
		FileOutputStream outputStream = 
				openFileOutput("patient_vitals.txt", MODE_PRIVATE);
		String output;
		Map<String, Map<String, ArrayList<Number>>> vitals;
		
		try {
			for (String hcn: hcnToPatient.keySet()) {
				vitals = hcnToPatient.get(hcn).getVitalsigns();
				output = hcn + " ";
				for (String toa: vitals.keySet()) {
					output = output + toa + ",";
					for (String vitaltime: vitals.get(toa).keySet()) {
						output = output + vitaltime + ",";
						for (Number data: vitals.get(toa).get(vitaltime)) {
							output = output + data + ",";
						}
						output = output + " ";
					}
				}
				outputStream.write((output + "\n").getBytes());
			}
		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}

}
