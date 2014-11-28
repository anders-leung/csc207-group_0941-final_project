package com.example.triage;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Map;
import java.util.TreeMap;

public class Nurse extends User {

	private Patient patient;
	private TreeMap<String, Patient> patientlist;
	private Organizer organizer;
	private File records;
	private File vitals;
	private File doctimes;

	public Nurse(File dir) throws IOException {
		organizer = Organizer.getInstance(dir);
		records = new File(dir, "patient_records.txt");
		vitals = new File(dir, "patient_vitals.txt");
		doctimes = new File(dir, "patient_doctimes.txt");
		this.patientlist = organizer.getHcnToPatient();
	}

	/**
	 * Return a certain patient given by the health card number from the patient
	 * list else return null
	 * 
	 * @param patientlist
	 * @param hcn
	 * @return
	 */
	public Patient lookupPatient(String hcn) {

		if (this.patientlist.get(hcn) != null) {
			this.patient = this.patientlist.get(hcn);
			return this.patient;
		} else {
			return null;
		}
	}

	/**
	 * Nurse can create new patient records and record patient data
	 * 
	 * @param patientlist
	 * @param name
	 * @param dob
	 * @param hcn
	 * @throws FileNotFoundException
	 */
	public void addPatient(String name, String dob, String hcn)
			throws FileNotFoundException {
		Patient patient = new Patient(name, dob, hcn);
		if (patientlist.containsValue(patient)) {

		} else {
			organizer.addPatient(patient);
			FileOutputStream os = new FileOutputStream(records);
			organizer.recordPatients(os);
		}
	}

	/**
	 * Nurse can record date and time when the patient is seen by a doctor
	 * 
	 * @param dateandtime
	 * @throws FileNotFoundException
	 */
	public void setTimeSeenByDoctor(String dateandtime) throws FileNotFoundException {
		this.patient.setSeenbydoctor(dateandtime);
		FileOutputStream os = new FileOutputStream(doctimes);
		organizer.saveTimesSeenByDoctor(os);
	}
	
	public String getTimeSeenByDoctor() {
		return this.patient.getSeenbydoctor();
	}

	/**
	 * Nurse can update a patient's visit record with vital signs at a
	 * particular time, retaining older values
	 * 
	 * @throws FileNotFoundException
	 */
	public void updatePatient(String vitaltime, double temp, int bloodpressure,
			String measurement, int heartrate) throws FileNotFoundException {
		String toa = this.patient.getVitalsigns().descendingKeySet().first();
		this.patient.setVitalsigns(toa, vitaltime, temp, bloodpressure,
				measurement, heartrate);
		FileOutputStream os = new FileOutputStream(vitals);
		organizer.saveData(os);
	}

	/**
	 * Nurse can create a new visit record based on the patient's arrival time
	 * 
	 * @throws FileNotFoundException
	 */
	public void newVisitRecord(String toa, String vitaltime, double temp,
			int bloodpressure, String measurement, int heartrate)
			throws FileNotFoundException {
		this.patient.setVitalsigns(toa, vitaltime, temp, bloodpressure,
				measurement, heartrate);
		FileOutputStream os = new FileOutputStream(vitals);
		organizer.saveData(os);
	}

	/**
	 * Nurse can view previous record for a given patient
	 * 
	 * @param patientlist
	 * @param toa
	 * @return
	 */
	public TreeMap<String, TreeMap<String, ArrayList<Object>>> lookupPatientRecord() {
		return this.patient.getVitalsigns();
	}

	public TreeMap<Integer, ArrayList<Patient>> notseenbydoctor() {
		TreeMap<Integer, ArrayList<Patient>> map = new TreeMap<Integer, ArrayList<Patient>>(
				Collections.reverseOrder());
		for (String hcn : this.patientlist.keySet()) {
			Patient p = this.patientlist.get(hcn);
			if (p.getSeenbydoctor().equals("")) {
				if (map.keySet().contains(p.getUrgency())) {
					map.get(p.getUrgency()).add(p);
				} else {
					ArrayList<Patient> patients = new ArrayList<Patient>();
					patients.add(p);
					map.put(p.getUrgency(), patients);
				}
			}
		}
		return map;
	}

	public TreeMap<String, Patient> getPatientlist() {
		return this.patientlist;
	}

	public static void main(String[] args) throws IOException {
		File dir = new File("C:\\Users\\Anders\\Desktop");
		Nurse nurse = new Nurse(dir);
		nurse.addPatient("Anders Leung", "1995-06-16", "123123");
		nurse.lookupPatient("123123");
		// nurse.newVisitRecord("2014-11-25 11:30", "11:40", 23.0, 120,
		// "diastolic", 10);
		Nurse nurse1 = new Nurse(dir);
		System.out.println(nurse1.getPatientlist().get("123123").age);
		Map<Integer, ArrayList<Patient>> urgency = nurse.notseenbydoctor();
		System.out.println(urgency);
		System.out.println(urgency.values());
		System.out.println("-----------------------------");
		String patients = "";
		for (ArrayList<Patient> a : urgency.values()) {
			for (Patient patient : a) {
				patients += patient.toString();
			}
		}
		System.out.println(patients);
		String arrivaltime = "2014-11-25 11:30";
		arrivaltime = arrivaltime.substring(0, 10) + "-"
				+ arrivaltime.substring(11, arrivaltime.length() - 1);
		System.out.println(arrivaltime);
	}
}