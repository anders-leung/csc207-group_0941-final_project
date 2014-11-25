package com.example.triage;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.TreeMap;
import java.util.Scanner;

/**
 * An Organizer reads all files to create a patient list and allows for
 * modifying files.
 * 
 * @author c3wangyu
 * 
 */
public class Organizer {

	private static TreeMap<String, Patient> hcnToPatient = new TreeMap<String, Patient>();
	private File records;
	private File vitals;
	private File symptoms;
	private File prescription;
	private Organizer instance = null;

	private Organizer(File dir) throws IOException {
		records = new File(dir, "patient_records.txt");
		vitals = new File(dir, "patient_vitals.txt");
		symptoms = new File(dir, "patient_symptoms.txt");
		prescription = new File(dir, "patient_prescription.txt");
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
		if (symptoms.exists()) {
			this.readSymptoms(symptoms.getPath());
		} else {
			vitals.createNewFile();
		}
		if (prescription.exists()) {
			this.readPrescription(prescription.getPath());
		} else {
			vitals.createNewFile();
		}
	}

	/**
	 * Reads a given file to generate a list of patients.
	 * 
	 * @param path
	 *            - The path of the file to read from.
	 * @throws FileNotFoundException
	 */
	private void populatePatients(String path) throws FileNotFoundException {

		Scanner scanner = new Scanner(new FileInputStream(path));
		ArrayList<Patient> patients = new ArrayList<Patient>();

		String[] patientData;
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

		for (Patient p : patients) {
			hcnToPatient.put(p.hcn, p);
		}

	}

	/**
	 * Reads a file to add generate a list of patients' data.
	 * 
	 * @param path
	 *            - The path of the file to read from.
	 * @throws FileNotFoundException
	 */
	private void addPatientData(String path) throws FileNotFoundException {

		Scanner scanner = new Scanner(new FileInputStream(path));

		Patient patient;
		String[] patientData;
		String[] data;
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
				patient.setVitalsigns(toa, vitaltime, temp, bloodpressure,
						heartrate);
			}
		}

		scanner.close();

	}

	/**
	 * Reads a description of patients' symptoms from a file and saves them.
	 * 
	 * @param path
	 *            - The path of the file to read from.
	 * @throws FileNotFoundException
	 */
	private void readSymptoms(String path) throws FileNotFoundException {

		Scanner scanner = new Scanner(new FileInputStream(path));
		Patient patient;
		String[] symptomData;
		String[] info;
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
		scanner.close();

	}

	/**
	 * Reads patients' prescriptions from a file and saves them.
	 * 
	 * @param path
	 *            - The path of the file to read from.
	 * @throws FileNotFoundException
	 */
	private void readPrescription(String path) throws FileNotFoundException {

		Scanner scanner = new Scanner(new FileInputStream(path));
		Patient patient;
		String[] prescriptionInfo;
		String hcn;
		String prescription;

		while (scanner.hasNextLine()) {
			prescriptionInfo = scanner.nextLine().split(" ");
			hcn = prescriptionInfo[0];
			prescription = prescriptionInfo[1];
			patient = hcnToPatient.get(hcn);
			patient.setPrescription(prescription);
		}
		scanner.close();

	}

	/**
	 * Writes medical data for all patients to a file.
	 * 
	 * @param outputStream
	 * 			- Output file to be written to.
	 * @throws FileNotFoundException
	 */
	public void saveData(FileOutputStream outputStream)
			throws FileNotFoundException {

		String output;
		TreeMap<String, TreeMap<String, ArrayList<Object>>> vitals;

		try {
			for (String hcn : hcnToPatient.keySet()) {
				vitals = hcnToPatient.get(hcn).getVitalsigns();
				output = hcn + " ";
				for (String toa : vitals.keySet()) {
					for (String vitaltime : vitals.get(toa).keySet()) {
						output = output + toa + "," + vitaltime + ",";
						for (Object data : vitals.get(toa).get(vitaltime)) {
							output = output + data + ",";
						}
						output = output + " ";
					}
				}
				output = output.trim();
				outputStream.write((output + "\n").getBytes());
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	/**
	 * Writes all patient prescriptions to a file.
	 * 
	 * @param outputStream
	 * 			- Output file to be written to.
	 * @throws FileNotFoundException
	 */
	public void savePrescriptions(FileOutputStream outputStream)
			throws FileNotFoundException {

		String output;
		Patient patient;

		try {
			for (String hcn : hcnToPatient.keySet()) {
				patient = hcnToPatient.get(hcn);
				output = hcn + "," + patient.getPrescription();
				outputStream.write((output + "\n").getBytes());
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	/**
	 * Writes all patient symptom descriptions to a file.
	 * 
	 * @param outputStream
	 * 			- Output file to be written to.-
	 * @throws FileNotFoundException
	 */
	public void saveSymptoms(FileOutputStream outputStream)
			throws FileNotFoundException {

		String output;
		Patient patient;
		TreeMap<String, String> symptoms;

		try {
			for (String hcn : hcnToPatient.keySet()) {
				patient = hcnToPatient.get(hcn);
				symptoms = patient.getSymptoms();
				output = hcn + " ";
				for (String time : symptoms.keySet()) {
					output = output + time + "," + symptoms.get(time) + " ";
				}
				output = output.trim();
				outputStream.write((output + "\n").getBytes());
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	/**
	 * Writes all patients with their personal information to a file.
	 * 
	 * @param outputStream
	 * 			- Output file to be written to.
	 * @throws FileNotFoundException
	 */
	public void recordPatients(FileOutputStream outputStream)
			throws FileNotFoundException {

		String output;
		Patient patient;

		try {
			for (String hcn : hcnToPatient.keySet()) {
				patient = hcnToPatient.get(hcn);
				output = hcn + "," + patient.name + "," + patient.dob;
				outputStream.write((output + "\n").getBytes());
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	/**
	 * Adds new patient to the map of patients.
	 * 
	 * @param p
	 *            - The new patient to be a added.
	 */
	public void addPatient(Patient p) {
		hcnToPatient.put(p.hcn, p);
	}

	/**
	 * Returns the Map of patients.
	 * 
	 * @return A Map of healthcard numbers to their respective patients.
	 * @throws FileNotFoundException
	 */
	public static TreeMap<String, Patient> getHcnToPatient()
			throws FileNotFoundException {
		return hcnToPatient;
	}

	/**
	 * Returns the instance of Organizer. Creates new one if none exist.
	 * 
	 * @param dir
	 *            - The directory where the required files are located.
	 * @return The single instance of the class Organizer.
	 * @throws IOException
	 */
	public Organizer getInstance(File dir) throws IOException {
		if (instance == null) {
			instance = new Organizer(dir);
		}
		return instance;
	}

}