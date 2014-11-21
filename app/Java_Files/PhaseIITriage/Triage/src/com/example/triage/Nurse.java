package com.example.triage;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.Map;


public class Nurse implements Serializable {
	
	protected Patient patient;
	protected Map<String, Patient> patientlist;
	

	public Nurse() {
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
	 * Nurse can update a patient's visit record with vital signs at a particular
	 * time, retaining older values
	 */
	public void updatepatient(String toa, String vitaltime, double temp, 
			int bloodpressure, int heartrate) {
		this.patient.setVitalsigns(toa, vitaltime, temp, bloodpressure, heartrate);
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
	public Map<String, ArrayList<Number>> lookupPatientRecord(Map<String, Patient> patientlist, 
			String toa) {
		return this.patient.getVitalsigns().get(toa);
	}
}