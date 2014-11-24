package com.example.triageii;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Map;


public class Nurse implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 3609426486460356270L;
	private Patient patient;
	private Map<String, Patient> patientlist;
	private Organizer organizer;
	private File dir;

	public Nurse(File dir) throws IOException {
		organizer = new Organizer(dir);
		this.patientlist = organizer.getHcnToPatient();
	}
	
	/**
	 * Return a certain patient given by the health card number from the patient list
	 * else return null
	 * @param patientlist
	 * @param hcn
	 * @return
	 */
	public boolean lookupPatient(String hcn){
		
		if (this.patientlist.get(hcn) != null) {
			this.patient = this.patientlist.get(hcn);
			return true;
		}
		else {
			return false;
		}
	}
	
	/**
	 * Nurse can update a patient's visit record with vital signs at a particular
	 * time, retaining older values
	 * @throws FileNotFoundException 
	 */
	public void updatepatient(FileOutputStream outputStream, String toa, String vitaltime, double temp, 
			int bloodpressure, int heartrate) throws FileNotFoundException {
		this.patient.setVitalsigns(toa, vitaltime, temp, bloodpressure, heartrate);
		organizer.saveData(outputStream);
	}
	
	/**
	 * Nurse can create a new visit record based on the patient's arrival time
	 */
	public void newVisitRecord(String toa, String vitaltime, double temp, 
			int bloodpressure, int heartrate) {
		this.patient.setVitalsigns(toa, vitaltime, temp, bloodpressure, heartrate);
	}
	
	/**
	 * Nurse can view previous record for a given patient
	 * @param patientlist
	 * @param toa
	 * @return
	 */
	public Map<String, ArrayList<Number>> lookupPatientRecord(String toa) {
		return this.patient.getVitalsigns().get(toa);
	}
	
	public Patient getPatient() {
		return this.patient;
	}

	public Map<String, Patient> getPatientlist() {
		return patientlist;
	}

	public void setPatientlist(File dir, String records, String vitals) throws IOException {
		
	}
	
	public static void main(String args[]) throws IOException {
		File dir = new File("C:\\Users\\Anders\\Desktop");
		Nurse nurse = new Nurse(dir);
		System.out.println(nurse.lookupPatient("111111"));
		System.out.println(nurse.getPatient());
	}
}