package com.example.triage;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.TreeMap;

/**
 * A patient that has permanent values that are name, date of birth, health card
 * number, and time of arrival. A patient also has a list of vital signs which
 * can be added to.
 * 
 * @author c3leungb
 *
 */

public class Patient implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -955893203782830343L;
	/**
	 * The identifier of the Patient.
	 */
	protected String hcn;
	protected String name;
	protected String dob;
	protected int age;
	private int urgency;
	private String prescription = "";

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
	 * A list that takes a list of numbers that represent the temperature, blood
	 * pressure and heart rate of the patient.
	 */
	private TreeMap<String, TreeMap<String, ArrayList<Object>>> vitalsigns = new TreeMap<String, TreeMap<String, ArrayList<Object>>>();

	/**
	 * Constructs Patient with identifier "hcn" final attributes like name, date
	 * of birth, and time of arrival and mutable attributes under vital signs.
	 * Also create the list for seenbydoctor and the map for symptoms and
	 * urgency with value 0.
	 */
	public Patient(String Name, String DoB, String HCN) {
		this.name = Name;
		this.dob = DoB;
		this.hcn = HCN;
		this.setAge();
		this.vitalsigns = new TreeMap<String, TreeMap<String, ArrayList<Object>>>();
		this.seenbydoctor = new ArrayList<String>();
		this.symptoms = new TreeMap<String, String>();
		this.urgency = 0;
	}

	/**
	 * Returns a list containing all recorded data for a patient's vital signs.
	 * 
	 * @return - a list of all the collected data.
	 */
	public TreeMap<String, TreeMap<String, ArrayList<Object>>> getVitalsigns() {
		return vitalsigns;
	}

	/**
	 * Add a list of objects that represent the patient's vital signs to
	 * vitalsigns. After adding the vital signs, also determine Patient.urgency
	 * based on the latest information provided.
	 * 
	 * @param temp
	 *            - the temperature of the patient Add a list of doubles that
	 *            represent the patient's vital signs to vitalsigns.
	 * 
	 * @param bloodpressure
	 *            - the blood pressure of the patient
	 * @param heartrate
	 *            - the heart rate of the patient
	 */
	public void setVitalsigns(String ToA, String vitaltime, Double temp,
			Integer bloodpressure, String measurement, Integer heartrate) {
		ToA = ToA.substring(0, 10) + "-" + ToA.substring(11, ToA.length());
		vitaltime = vitaltime.substring(0, 10) + "-"
				+ vitaltime.substring(11, vitaltime.length());
		if (this.vitalsigns.containsKey(ToA)) {
			ArrayList<Object> vitals = new ArrayList<Object>();
			vitals.add(temp);
			vitals.add(bloodpressure);
			vitals.add(measurement);
			vitals.add(heartrate);
			this.vitalsigns.get(ToA).put(vitaltime, vitals);
		} else {
			TreeMap<String, ArrayList<Object>> v = new TreeMap<String, ArrayList<Object>>();
			ArrayList<Object> vitals = new ArrayList<Object>();
			vitals.add(temp);
			vitals.add(bloodpressure);
			vitals.add(measurement);
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
		if (this.seenbydoctor.isEmpty()) {
			return "";
		} else {
			String times = "";
			for (String time : this.seenbydoctor) {
				times += time + ", ";
			}
			return times.substring(0, times.length() - 2);
		}
	}

	/**
	 * Record the last time that Patient was seen by a doctor.
	 * 
	 * @param timeseenbydoctor
	 *            - A string representation of the time Patient was seen by
	 *            doctor.
	 */
	public void setSeenbydoctor(String timeseenbydoctor) {
		if (timeseenbydoctor.trim().isEmpty()) {
			return;
		} else {
			timeseenbydoctor = timeseenbydoctor.substring(0, 10) + "-"
					+ timeseenbydoctor.substring(11, timeseenbydoctor.length());
			this.seenbydoctor.add(0, timeseenbydoctor);
		}
	}

	/**
	 * Return the recorded description of Patients symptoms at specific time.
	 * 
	 * @param time
	 *            - String of the desired time that Patients symptoms were
	 *            recorded.
	 * 
	 * @return A String that describes Patients symptoms at given time.
	 */
	public String getSymptoms(String time) {
		return symptoms.get(time);
	}

	public TreeMap<String, String> getSymptoms() {
		return symptoms;
	}

	/**
	 * Records symptoms of Patient for a given time.
	 * 
	 * @param time
	 *            - A string representation of the time that Patients symptoms
	 *            are being updated.
	 * @param description
	 *            - The description of Patients symptoms.
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
			if (birthdate <= 27) {
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
	 * 
	 * @return an integer referring to the number of points of urgency Patient
	 *         has.
	 */
	public int getUrgency() {
		return urgency;
	}

	/**
	 * Set Patient.urgency based on the hospital policies: 1 point for: - age <
	 * 2 years - temperature >= 39.0 degrees Celsius - blood pressure: systolic
	 * >= 140, diastolic >= 90 - heart rate >= 100 or heart rate <= 50
	 */
	public void setUrgency() {
		String latestvisit = this.getVitalsigns().lastKey();
		String latestvitaltime = this.getVitalsigns().get(latestvisit)
				.lastKey();
		ArrayList<Object> latestrecord = this.getVitalsigns().get(latestvisit)
				.get(latestvitaltime);
		if (this.age < 2) {
			this.urgency++;
		}
		if ((Double) latestrecord.get(0) >= 39.0) {
			this.urgency++;
		}
		if (latestrecord.get(2).equals("systolic")) {
			if ((Integer) latestrecord.get(1) >= 140) {
				this.urgency++;
			}
		}
		if (latestrecord.get(2).equals("diastolic")) {
			if ((Integer) latestrecord.get(1) >= 90) {
				this.urgency++;
			}
		}
		if ((Integer) latestrecord.get(3) >= 100) {
			this.urgency++;
		}
		if ((Integer) latestrecord.get(3) <= 50) {
			this.urgency++;
		}
	}

	/**
	 * Return the string description of the prescription, including the name of
	 * the medication and the instructions.
	 * 
	 * @return a string description of prescription
	 */
	public String getPrescription() {
		return prescription;
	}

	/**
	 * Adds a prescription to Patient, naming the prescribed medication and its
	 * instructions.
	 * 
	 * @param prescription
	 */
	public void setPrescription(String prescription) {
		this.prescription = prescription;
	}

	@Override
	public String toString() {
		String string = "Name: ";
		string += this.name + System.getProperty("line.separator")
				+ "Date of Birth: " + this.dob
				+ System.getProperty("line.separator") + "Health Card Number: "
				+ this.hcn;
		return string;
	}

	public String getVitalSignsString() {
		TreeMap<String, TreeMap<String, ArrayList<Object>>> vitals = this
				.getVitalsigns();
		String vitalsigns = "";
		for (String arrival : vitals.keySet()) {
			vitalsigns += "Time of Arrival: " + arrival.substring(0, 10) + " "
					+ arrival.substring(11, arrival.length())
					+ System.getProperty("line.separator");
			for (String vital : vitals.get(arrival).keySet()) {
				ArrayList<Object> vitalsign = vitals.get(arrival).get(vital);
				vitalsigns += "            " + "Time Vitals Taken: "
						+ vital.substring(0, 10) + " "
						+ vital.substring(11, vital.length())
						+ System.getProperty("line.separator")
						+ "                        Temperature: "
						+ vitalsign.get(0)
						+ System.getProperty("line.separator")
						+ "                        Blood Pressure: "
						+ vitalsign.get(1) + " " + vitalsign.get(2)
						+ System.getProperty("line.separator")
						+ "                        Heart Rate: "
						+ vitalsign.get(3)
						+ System.getProperty("line.separator");
			}
			vitalsigns += ",";
		}
		return vitalsigns;
	}

	public Object getLastVitalValue(int i) {
		String arrivalTime = this.getVitalsigns().lastKey();
		String vitalTime = this.getVitalsigns().get(arrivalTime).lastKey();
		return this.getVitalsigns().get(arrivalTime).get(vitalTime).get(i);
	}

	public static void main(String[] args) throws IOException {
		Patient patient = new Patient("Anders Leung", "1995-06-16", "123123");
		patient.setSeenbydoctor("2014-11-12 11:35");
		System.out.println(patient.seenbydoctor.get(0));
		System.out.println(patient.getSeenbydoctor());
		String timeseenbydoctor = "2014-11-11 11:11";
		timeseenbydoctor = timeseenbydoctor.substring(0, 10) + "-"
				+ timeseenbydoctor.substring(11, timeseenbydoctor.length());
	}
}