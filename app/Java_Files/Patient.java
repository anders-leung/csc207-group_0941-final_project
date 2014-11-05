package Maps;

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

public class Patient {	
	
	/**
	 * The identifier of the Patient.
	 */
	protected String hcn;
	protected String name;
	protected String dob;
	protected String toa;
	
	/**
	 * A list that takes a list of numbers that represent the temperature,
	 * blood pressure and heart rate of the patient.
	 */
	private Map<String, ArrayList<Number>> vitalsigns;
	
	/**
	 * Constructs Patient with identifier "hcn" final attributes like
	 * name, date of birth, and time of arrival and mutable attributes
	 * under vital signs.
	 */
	public Patient(String Name, String DoB, String HCN, String ToA, 
			double temp, int bloodpressure, int heartrate) {
		this.name = Name;
		this.dob = DoB;
		this.hcn = HCN;
		this.toa = ToA;
		this.vitalsigns = new HashMap<String, ArrayList<Number>>();
		this.setVitalsigns(ToA, temp, bloodpressure, heartrate);
	}
	
	/**
	 * Takes a patient and returns true iff the health card numbers
	 * are the same for both patients.
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
	 * Returns a list containing all recorded data for a
	 * patient's vital signs.
	 * 
	 * 
	 * @return - a list of all the collected data.
	 */
	public Map<String, ArrayList<Number>> getVitalsigns() {
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
	public void setVitalsigns(String ToA, double temp, 
			int bloodpressure, int heartrate) {
		ArrayList<Number> vitals = new ArrayList<Number>();
		vitals.add(temp);
		vitals.add(bloodpressure);
		vitals.add(heartrate);
		this.vitalsigns.put(ToA, vitals);
	}
}
