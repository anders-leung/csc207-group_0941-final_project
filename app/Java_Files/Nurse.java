package com.example.triage;


import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;


public class Nurse extends User{
	
	private Patient patient;
	private Map<String, Patient> patientlist;
	

	public Nurse() {
		try {
			Organizer organizer = new Organizer();
		}
		catch (FileNotFoundException e) {
			
		}
		this.patientlist = Organizer.getHcnToPatient();
	}
	
	/**
	 * Return a certain patient given by the health card number from the patient list
	 * else return null
	 * @param patientlist
	 * @param hcn
	 * @return
	 */
	public Patient lookupPatient(String hcn){
		
		if (patientlist.get(hcn) != null) {
			this.patient = this.patientlist.get(hcn);
			return this.patient;
		}
		else {
			return null;
		}
	}
	
	/**
	 * Nurse can update a patient's visit record with vital signs at a particular
	 * time, retaining older values
	 */
	public void updatepatient(String toa, String vitaltime, double temp, 
			String bloodpressure, int heartrate) {
		this.patient.setVitalsigns(toa, vitaltime, temp, bloodpressure, heartrate);
	}
	
	/**
	 * Nurse can create a new visit record based on the patient's arrival time
	 */
	public void newVisitRecord(String toa, String vitaltime, double temp, 
			String bloodpressure, int heartrate) {
		this.patient.setVitalsigns(toa, vitaltime, temp, bloodpressure, heartrate);
	}
	
	/**
	 * Nurse can view previous record for a given patient
	 * @param patientlist
	 * @param toa
	 * @return
	 */
	public Map<String, ArrayList<Object>> lookupPatientRecord(String toa) {
		return this.patient.getVitalsigns().get(toa);
	}
	
	public Map<Integer, ArrayList<Patient>> notseenbydoctor() {
		TreeMap<Integer, ArrayList<Patient>> map = 
				new TreeMap<Integer, ArrayList<Patient>>();
		for (String hcn: this.patientlist.keySet()) {
			Patient p = this.patientlist.get(hcn);
			if (p.getSeenbydoctor().equals("[]")) {
				if (map.keySet().contains(p.getUrgency())) {
					map.get(p.getUrgency()).add(p);
				} else {
					ArrayList<Patient> patients = new ArrayList<Patient>();
					patients.add(p);
					map.put(p.getUrgency(), patients);
				}
			}
		} return map.descendingMap();
	}
}