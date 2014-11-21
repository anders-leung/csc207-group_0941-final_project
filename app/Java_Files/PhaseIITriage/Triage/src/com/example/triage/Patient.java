package com.example.triage;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * A patient that has permanent values that are name, date of birth,
 * health card number, and time of arrival. A patient also has a list of
 * vital signs which can be added to.
 * @author c3leungb
 *	
 */

public class Patient implements Serializable {	
	
	/**
	 * The identifier of the Patient.
	 */
	protected String hcn;
	protected String name;
	protected String dob;
	
	/**
	 * A list that takes a list of numbers that represent the temperature,
	 * blood pressure and heart rate of the patient.
	 */
	private Map<String, Map<String, ArrayList<Number>>> vitalsigns;
	
	/**
	 * Constructs Patient with identifier "hcn" final attributes like
	 * name, date of birth, and time of arrival and mutable attributes
	 * under vital signs.
	 */
	public Patient(String Name, String DoB, String HCN) {
		this.name = Name;
		this.dob = DoB;
		this.hcn = HCN;
		this.vitalsigns = 
				new HashMap<String, Map<String, ArrayList<Number>>>();
	}
	
	/**
	 * Returns a list containing all recorded data for a
	 * patient's vital signs.
	 * 
	 * 
	 * @return - a list of all the collected data.
	 */
	public Map<String, Map<String, ArrayList<Number>>> getVitalsigns() {
		return vitalsigns;
	}
	
	/**
	 * Add a list of doubles that represent the patient's vital
	 * signs to vitalsigns.
	 * 
	 * @param temp
	 * 			- the temperature of the patient
	 * @param bloodpressure
	 * 			- the blood pressure of the patient
	 * @param heartrate
	 * 			- the heart rate of the patient
	 */
	public void setVitalsigns(String ToA, String vitaltime, double temp, 
			int bloodpressure, int heartrate) {
		if (this.vitalsigns.containsKey(ToA)) {
			ArrayList<Number> vitals = new ArrayList<Number>();
			vitals.add(temp);
			vitals.add(bloodpressure);
			vitals.add(heartrate);
			this.vitalsigns.get(ToA).put(vitaltime, vitals);
		} else {
		Map<String, ArrayList<Number>> v = 
				new HashMap<String, ArrayList<Number>>();
		ArrayList<Number> vitals = new ArrayList<Number>();
		vitals.add(temp);
		vitals.add(bloodpressure);
		vitals.add(heartrate);
		v.put(vitaltime, vitals);
		this.vitalsigns.put(ToA, v);
		}
	}
}
