package com.example.triage;

import java.util.ArrayList;
import java.util.List;

/**
 * A patient that has permanent values that are name, date of birth,
 * health card number, and time of arrival. A patient also has a list of
 * vital signs which can be added to.
 * @author c3leungb
 *
 */

public class Patient {
	protected String name;
	protected String dob;
	
	/**
	 * The identifier of the Patient.
	 */
	protected int hcn;
	protected String toa;
	
	/**
	 * A list that takes a list of numbers that represent the temperature,
	 * blood pressure and heart rate of the patient.
	 */
	private List<ArrayList<Double>> vitalsigns;
	
	/**
	 * Constructs Patient with identifier "hcn" final attributes like
	 * name, date of birth, and time of arrival and mutable attributes
	 * under vital signs.
	 */
	public Patient(String Name, String DoB, int HCN, String ToA, 
			double temp, double bloodpressure, double heartrate) {
		this.name = Name;
		this.dob = DoB;
		this.hcn = HCN;
		this.toa = ToA;
		this.setVitalsigns(temp, bloodpressure, heartrate);
	}
	
	/**
	 * 
	 * @param patient
	 * 			- another patient
	 * @return - whether patient has the same health card number.
	 */
	public boolean equals(Patient patient) {
		if (!(patient instanceof Patient))
			return false;
		return patient.hcn == this.hcn;
	}
	
	/**
	 * 
	 * @return - a list of all the collected data.
	 */
	public List<ArrayList<Double>> getVitalsigns() {
		return vitalsigns;
	}
	
	/**
	 * 
	 * @param temp
	 * 			- the temperature of the patient
	 * @param bloodpressure
	 * 			- the blood pressure of the patient
	 * @param heartrate
	 * 			- the heart rate of the patient
	 */
	public void setVitalsigns(double temp, double bloodpressure,
			double heartrate) {
		ArrayList<Double> vitals = new ArrayList<Double>();
		vitals.add(temp);
		vitals.add(bloodpressure);
		vitals.add(heartrate);
		this.vitalsigns.add(vitals);
	}
}
