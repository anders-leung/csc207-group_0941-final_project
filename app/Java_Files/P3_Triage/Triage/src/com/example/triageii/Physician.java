package com.example.triageii;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.TreeMap;

public class Physician extends User {
	
	private Patient patient;
	private TreeMap<String, Patient> patientlist;
	private Organizer organizer;
	private File prescriptions;
	
	public Physician(File dir) throws IOException {
		organizer = Organizer.getInstance(dir);
		prescriptions = new File(dir, "patient_prescriptions.txt");
		this.patientlist = organizer.getHcnToPatient();
	}
	
	public Patient lookupPatient(String hcn) {
		if (patientlist.get(hcn) == null) {
			return null;
		} else {
			this.patient = patientlist.get(hcn);
			return this.patient;
		}
	}
	
	public void prescribe(String prescription) throws FileNotFoundException {
		this.patient.setPrescription(prescription);
		FileOutputStream os = new FileOutputStream(prescriptions);
		organizer.savePrescriptions(os);
	}
	
	public TreeMap<String, Patient> getPatientlist() {
		return this.patientlist;
	}
}
