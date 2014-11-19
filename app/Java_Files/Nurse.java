package com.example.triage;


import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Map;
import java.util.TreeMap;


public class Nurse {
	
	protected Patient patient;
	protected Map<String, Patient> patientlist;
	

	public Nurse(String un, String pw) throws FileNotFoundException {
		Organizer organizer = new Organizer();
		this.patientlist = organizer.getHcnToPatient();
	}
	
	/**
	 * Return a certain patient given by the health card number from the patient list
	 * else return null
	 * @param patientlist
	 * @param hcn
	 * @return
	 */
	public Patient lookupPatient(Map<String, Patient> patientlist, String hcn){
		
		if (patientlist.get(hcn) != null) {
			patient = patientlist.get(hcn);
			return patient;
		}
		else {
			return null;
		}
	}
	/**
	 * Nurse can create new patient records and record patient data
	 * @param patientlist
	 * @param name
	 * @param dob
	 * @param hcn
	 */
	public void addPatient(Map<String, Patient> patientlist, String name, String dob, String hcn){
		Patient patient = new Patient(name, dob, hcn);
		patientlist.put(hcn, patient);
		
	}
	
	/**
	 * Nurse can record date and time when the patient is seen by a doctor
	 * @param dateandtime
	 */
	public void recordDateandTime(String dateandtime){
		this.patient.setSeenbydoctor(dateandtime);
	}
	
	/**
	 * Nurse can access a list of patients who has not yet seen by a doctorand ordered by 
	 * decreasing urgency points
	 * @param patientlist
	 * @return
	 */
	public ArrayList<Patient> urgencylevel(){
		ArrayList<Patient> unseenpatients = new ArrayList<Patient>();
		for (Map.Entry<String, Patient> entry : this.patientlist.entrySet()){
			if (entry.getValue().getSeenbydoctor().toString().equals("[]")){
				unseenpatients.add(entry.getValue());
			}
		}
		ArrayList<Integer> sortedpoints = new ArrayList<Integer>();
		for (Patient patient: unseenpatients){
			sortedpoints.add(patient.getUrgency());
		}
		Collections.sort(sortedpoints);
		ArrayList<Patient> sortedlist = new ArrayList<Patient>();
		for (int points : sortedpoints){
			for (Patient p : unseenpatients){
				if (points == p.getUrgency()){
					sortedlist.add(p);
				}
			}
		}
		return sortedlist;
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
	public TreeMap<String, ArrayList<Object>> lookupPatientRecord(Map<String, Patient> patientlist, 
			String toa) {
		return this.patient.getVitalsigns().get(toa);
	}
	
	public Map<Integer, ArrayList<Patient>> notseenbydoctor() {
		TreeMap<Integer, ArrayList<Patient>> map = 
				new TreeMap<Integer, ArrayList<Patient>>();
		for (String hcn: this.patientlist.keySet()) {
			Patient p = this.patientlist.get(hcn);
			if (p.getSeenbydoctor().toString().equals("[]")) {
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
	
//	public static void main(String[] args) throws FileNotFoundException {
//		Nurse nurse = new Nurse('bob','booob');
//		System.out.println(nurse.urgencylevel().get(3).name);
//	}
}