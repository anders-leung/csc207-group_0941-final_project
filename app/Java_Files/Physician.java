package com.example.triage;

import java.util.Map;

public class Physician extends User {
	
	private Patient patient;
	private Map<String, Patient> patientlist;
	
	public Physician() {
	}
	
	public Patient lookupPatient(String hcn) {
		if (patientlist.get(hcn) == null) {
			return null;
		} else {
			this.patient = patientlist.get(hcn);
			return this.patient;
		}
	}
	
	public void prescribe(String prescription) {
		this.patient.setPrescription(prescription);
	}
}
