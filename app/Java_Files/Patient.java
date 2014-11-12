package com.example.triage;

import java.util.ArrayList;
import java.util.TreeMap;

/**
 * A patient that has permanent values that are name, date of birth,
 * health card number, and time of arrival. A patient also has a list of
 * vital signs which can be added to.
 * @author c3leungb
 *
 */

public class Patient {	
	
	/**
	 * The identifier of the Patient.
	 */
	protected String hcn;
	protected String name;
	protected String dob;
	protected int age;
	private int urgency;
	private String prescription;
	
	/**
	 * An ArrayList that holds the times that Patient was seen by doctor.
	 */
	private ArrayList<String> seenbydoctor;
	
	/**
	 * A map has the descriptions of Patients symptoms as values and the time
	 * they were recorded as the keys.
	 */
	private TreeMap<String, String> symptoms;
	
	/**
	 * A list that takes a list of numbers that represent the temperature,
	 * blood pressure and heart rate of the patient.
	 */
	private TreeMap<String, TreeMap<String, ArrayList<Object>>> vitalsigns;
	
	/**
	 * Constructs Patient with identifier "hcn" final attributes like
	 * name, date of birth, and time of arrival and mutable attributes
	 * under vital signs. Also create the list for seenbydoctor and the map
	 * for symptoms and urgency with value 0.
	 */
	public Patient(String Name, String DoB, String HCN) {
		this.name = Name;
		this.dob = DoB;
		this.hcn = HCN;
		this.setAge();
		this.vitalsigns = 
				new TreeMap<String, TreeMap<String, ArrayList<Object>>>();
		this.seenbydoctor = new ArrayList<String>();
		this.symptoms = new TreeMap<String, String>();
		this.urgency = 0;
	}
	
	/**
	 * Returns a list containing all recorded data for a
	 * patient's vital signs.
	 * 
	 * @return - a list of all the collected data.
	 */
	public TreeMap<String, TreeMap<String, ArrayList<Object>>> getVitalsigns() {
		return vitalsigns;
	}
	
	/**
	 * Add a list of objects that represent the patient's vital
	 * signs to vitalsigns. After adding the vital signs, also determine
	 * Patient.urgency based on the latest information provided.
	 * 
	 * @param temp
	 * 			- the temperature of the patient
	 * Add a list of doubles that represent the patient's vital
	 * signs to vitalsigns.
	 * 
	 * @param bloodpressure
	 * 			- the blood pressure of the patient
	 * @param heartrate
	 * 			- the heart rate of the patient
	 */
	public void setVitalsigns(String ToA, String vitaltime, Double temp, 
			String bloodpressure, Integer heartrate) {
		if (this.vitalsigns.containsKey(ToA)) {
			ArrayList<Object> vitals = new ArrayList<Object>();
			vitals.add(temp);
			vitals.add(bloodpressure);
			vitals.add(heartrate);
			this.vitalsigns.get(ToA).put(vitaltime, vitals);
		} else {
		TreeMap<String, ArrayList<Object>> v = 
				new TreeMap<String, ArrayList<Object>>();
		ArrayList<Object> vitals = new ArrayList<Object>();
		vitals.add(temp);
		vitals.add(bloodpressure);
		vitals.add(heartrate);
		v.put(vitaltime, vitals);
		this.vitalsigns.put(ToA, v);
		}
		this.urgency = 0;
		this.setUrgency();
	}
	
	/**
	 * Returns the latest time at which Patient was seen by a doctor.
	 * 
	 * @return a String representation of the time Patient was seen by doctor.
	 */
	public String getSeenbydoctor() {
		int lastindex = this.seenbydoctor.size() - 1;
		return seenbydoctor.get(lastindex);
	}
	
	/**
	 * Record the last time that Patient was seen by a doctor.
	 * @param timeseenbydoctor
	 * 			- A string representation of the time Patient was seen by
	 * 			  doctor.
	 */
	public void setSeenbydoctor(String timeseenbydoctor) {
		this.seenbydoctor.add(timeseenbydoctor);
	}
	
	/**
	 * Return the recorded description of Patients symptoms at specific time.
	 * 
	 * @param time
	 * 			- String of the desired time that Patients symptoms were
	 * 			  recorded.
	 * 
	 * @return A String that describes Patients symptoms at given time.
	 */
	public String getSymptoms(String time) {
		return symptoms.get(time);
	}
	 
	/**
	 * Records symptoms of Patient for a given time.
	 * 
	 * @param time
	 * 			- A string representation of the time that Patients symptoms
	 * 			  are being updated.
	 * @param description
	 * 			- The description of Patients symptoms.
	 */
	public void setSymptoms(String time, String description) {
		this.symptoms.put(time, description);
	}
	
	/**
	 * Determine Patients age by comparing Patient.dob to the current date.
	 */
	public void setAge() {
		int birthmonth = Integer.parseInt(dob.substring(5, 7));
		int birthdate = Integer.parseInt(dob.substring(8, 10));
		int birthyear = Integer.parseInt(dob.substring(0, 4));
		if (birthmonth == 11) {
			if (birthdate <= 28) {
				this.age = 2014 - birthyear;
			} else {
				this.age = 2014 - birthyear - 1;
			}
		} else if (birthmonth <= 11) {
			this.age = 2014 - birthyear;
		} else {
			this.age = 2014 - birthyear - 1;
		}
	}
	
	/**
	 * Return the number of points of urgency Patient has.
	 * @return an integer referring to the number of points of urgency Patient
	 * 			has.
	 */
	public int getUrgency() {
		return urgency;
	}
	
	/**
	 * Set Patient.urgency based on the hospital policies: 1 point for:
	 * 		- age < 2 years
	 * 		- temperature >= 39.0 degrees Celsius
	 * 		- blood pressure: systolic >= 140, diastolic >= 90
	 * 		- heart rate >= 100 or heart rate <= 50
	 */
	public void setUrgency() {
		String latestvisit = this.getVitalsigns().lastKey();
		String latestvitaltime = this.getVitalsigns().get(latestvisit).lastKey();
		ArrayList<Object> latestrecord = this.getVitalsigns().get(latestvisit).get(latestvitaltime);
		String measurement = ((String) latestrecord.get(1)).split(" ")[1];
		int bloodpressure = 
				Integer.parseInt(((String) latestrecord.get(1)).split(" ")[0]);
		if (this.age < 2) {
			this.urgency++;
		}
		if ((Double) latestrecord.get(0) >= 39.0) {
			this.urgency++;
		}
		if (measurement.equals("systolic")) {
			if (bloodpressure >= 140) {
				this.urgency++;
			}
		}
		if (measurement.equals("diastolic")) {
			if (bloodpressure >= 90) {
				this.urgency++;
			}
		}
		if ((Integer) latestrecord.get(2) >= 100) {
			this.urgency++;
		}
		if ((Integer) latestrecord.get(2) <= 50) {
			this.urgency++;
		}
	}
	
	/**
	 * Return the string description of the prescription, including the name
	 * of the medication and the instructions.
	 * @return a string  description of prescription
	 */
	public String getPrescription() {
		return prescription;
	}
	
	/**
	 * Adds a prescription to Patient, naming the prescribed medication and
	 * its instructions.
	 * @param prescription
	 */
	public void setPrescription(String prescription) {
		this.prescription = prescription;
	}
}