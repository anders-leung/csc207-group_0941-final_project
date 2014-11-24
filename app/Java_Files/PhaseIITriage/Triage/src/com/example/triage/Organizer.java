package com.example.triageii;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Organizer {

	private static HashMap<String, Patient> hcnToPatient = 
			new HashMap<String, Patient>();
	
	public Organizer(File dir) throws IOException {
		File records = new File(dir, "patient_records.txt");
		File vitals = new File(dir, "patient_vitals.txt");
		if (records.exists()) {
			this.populatePatients(records.getPath());
		} else {
			records.createNewFile();
		} 
		if (vitals.exists()) {
			this.addPatientData(vitals.getPath());
		} else {
			vitals.createNewFile();
		}
	}
	
	public HashMap<String, Patient> getHcnToPatient() {
		return hcnToPatient;
	}

	/**
	 * Reads a given file to generate a list of patients.
	 * @throws FileNotFoundException
	 */
	private void populatePatients(String filePath) throws FileNotFoundException {
		
		Scanner scanner = 
				new Scanner(new FileInputStream(filePath));
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
	public void addPatientData(String filePath) throws FileNotFoundException {
		
		Scanner scanner =
				new Scanner(new FileInputStream(filePath));
		
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
	
	public void addPatientVitals(Patient patient) {
		hcnToPatient.put(patient.hcn, patient);
	}
	/**
	 * Saves data for all patients to a file.
	 * @throws FileNotFoundException
	 */
	public void saveData(FileOutputStream outputStream) throws FileNotFoundException {
		
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

	private static FileOutputStream openFileOutput(String string, int i) {
		// TODO Auto-generated method stub
		return null;
	}
	
	public static void main(String[] args) throws IOException {
		File dir = new File("C:\\Users\\Anders\\Desktop");
		Organizer organizer = new Organizer(dir);
		Patient patient = new Patient("Serge Abiteboul", "1984-09-12", "111111");
		patient.setVitalsigns("12:45", "12:50", 30.0, 90, 120);
		organizer.hcnToPatient.put("111111", patient);
		File file = new File(dir, "patient_vitals.txt");
		FileOutputStream os = new FileOutputStream(file);
		organizer.saveData(os);
	}
}