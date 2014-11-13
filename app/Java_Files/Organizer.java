package com.example.triage;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.TreeMap;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

public class Organizer {

	private static TreeMap<String, Patient> hcnToPatient = new TreeMap<String, Patient>();
	
	/**
	 * @param args
	 * @throws FileNotFoundException 
	 */
	public static void main(String[] args) throws FileNotFoundException {
		// TODO Auto-generated method stub
		
		populatePatients();
		addPatientData();
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
		String bloodpressure;
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
				bloodpressure = data[3];
				heartrate = Integer.parseInt(data[4]);
				patient.setVitalsigns(
						toa, vitaltime, temp, bloodpressure, heartrate);
			}
		}
		
		scanner.close();
		
	}
	
	private static void readSymptoms() throws FileNotFoundException {

		Scanner scanner =
				new Scanner(new FileInputStream("patient_symptoms.txt"));
		Patient patient;
		String [] symptomData;
		String [] info;
		String hcn;
		String time;
		String description;
		
		while (scanner.hasNextLine()) {
			symptomData = scanner.nextLine().split(" ");
			hcn = symptomData[0];
			patient = hcnToPatient.get(hcn);
			
			for (int i = 1; i < symptomData.length; i++) {
				info = symptomData[i].split(",");
				time = info[0];
				description = info[1];
				patient.setSymptoms(time, description);
				
			}
		}
		
		
	}
	
	private static void readPrescription() throws FileNotFoundException {
		
		Scanner scanner =
				new Scanner(new FileInputStream("patient_prescription.txt"));
		Patient patient;
		String [] prescriptionInfo;
		String hcn;
		String prescription;
		
		while (scanner.hasNextLine()) {
			prescriptionInfo = scanner.nextLine().split(" ");
			hcn = prescriptionInfo[0];
			prescription = prescriptionInfo[1];
			patient = hcnToPatient.get(hcn);
			patient.setPrescription(prescription);
		}
		
	}
	
	/**
	 * Saves data for all patients to a file.
	 * @throws FileNotFoundException
	 */
	public static void saveData() throws FileNotFoundException {
		
		FileOutputStream outputStream = 
				openFileOutput("patient_vitals.txt", MODE_PRIVATE);
		String output;
		TreeMap<String, TreeMap<String, ArrayList<Object>>> vitals;
		
		try {
			for (String hcn: hcnToPatient.keySet()) {
				vitals = hcnToPatient.get(hcn).getVitalsigns();
				output = hcn + " ";
				for (String toa: vitals.keySet()) {
					output = output + toa + ",";
					for (String vitaltime: vitals.get(toa).keySet()) {
						output = output + vitaltime + ",";
						for (Object data: vitals.get(toa).get(vitaltime)) {
							output = output + data + ",";
						}
						output = output + " ";
					}
				}
				output = output.trim();
				outputStream.write((output + "\n").getBytes());
			}
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	public static void savePrescriptions() {
		
		FileOutputStream outputStream = 
				openFileOutput("patient_prescriptions.txt", MODE_PRIVATE);
		String output;
		Patient patient;
		
		try {
			for (String hcn: hcnToPatient.keySet()) {
				patient = hcnToPatient.get(hcn);
				output = hcn + "," + patient.getPrescription();
				outputStream.write((output + "\n").getBytes());
			}
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	public static void saveSymptoms() {
		
		FileOutputStream outputStream = 
				openFileOutput("patient_prescriptions.txt", MODE_PRIVATE);
		String output;
		Patient patient;
		TreeMap<String, String> symptoms;
		
		try {
			for (String hcn: hcnToPatient.keySet()) {
				patient = hcnToPatient.get(hcn);
				symptoms = patient.getSymptoms();
				output = hcn + " ";
				for (String time: symptoms.keySet()) {
					output = output + time + "," +symptoms.get(time) + " ";
				}
				output = output.trim();
				outputStream.write((output + "\n").getBytes());
			}
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	public static void addPatient(Patient p) {
		
		FileOutputStream outputStream = 
				openFileOutput("patient_records.txt", MODE_PRIVATE);
		String output;
		Patient patient;
		
		try {
			for (String hcn: hcnToPatient.keySet()) {
				patient = hcnToPatient.get(hcn);
				output = hcn + "," + patient.name + "," + patient.dob;
				outputStream.write((output + "\n").getBytes());
			}
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	public static TreeMap<String, Patient> getHcnToPatient() {
		return hcnToPatient;
	}

}