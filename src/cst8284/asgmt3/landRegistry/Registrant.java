/*
* Course Name: CST8284_303
* Student Name: Duy Pham
* Class Name: Registrant
* Date: July 26, 2020
*/

package cst8284.asgmt3.landRegistry;

import java.io.Serializable;

/**
 * 
 * @author Duy Pham
 * @version 1.02
 *
 */
public class Registrant implements Serializable {
	/**
	 * Declare serialVersionUID = 1L
	 */
	public static final long serialVersionUID = 1L;
	
	/**
	 * Declare the start regNum as 1000
	 */
	private static final int REGNUM_START = 1000;
	
	/**
	 * Declare the currentRegNum equals to REGNUM_START
	 */
	private static int currentRegNum = REGNUM_START;
	
	/**
	 * Declare the regNum equals to currentRegNum
	 */
	private final int REGNUM = currentRegNum;
	
	/**
	 * Declare the first name of the registrant
	 */
	private String firstName;
	
	/**
	 * Declare the last name of the registrant
	 */
	private String lastName;

	/**
	 * default constructor which chains the parameterize constructor below with the
	 * parameter is "unknown unknown"
	 */
	public Registrant() {
		this("unknown unknown");
	}

	/**
	 * The parameterized constructor that takes the full name of registrant as input
	 * @param firstLastName the full name string of the registrant
	 */
	public Registrant(String firstLastName) {
		// split the first and last name by the space " " and store them into
		// coresponding variables using setters
		String[] nameStrings = firstLastName.split(" ");
		setFirstName(nameStrings[0]);
		if (nameStrings.length == 2) {
			setLastName(nameStrings[1]);
		} else {
			setLastName("");
		}
		// increase currentRegNum by 1 each time we create a new Registrant object
		incrToNextRegNum();
	}

	/**
	 * the getter of regNum
	 * @return REGNUM the regNum of the current registrant
	 */
	public int getRegNum() {
		return REGNUM;
	}
	
	/**
	 * This method increases the current regNum by 1 (to be used in constructor)
	 */
	private static void incrToNextRegNum() {
		currentRegNum++;
	}

	/**
	 * The getter of the first name
	 * @return firstName of the registrant
	 */
	public String getFirstName() {
		return firstName;
	}

	/**
	 * The setter of the first name
	 * @param firstName the new first name to be set
	 */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	/**
	 * the getter of the last name 
	 * @return the last name of the current registrant
	 */
	public String getLastName() {
		return lastName;
	}
	
	/**
	 * the setter of the last name 
	 * @param lastName the new last name to be set
	 */
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	/**
	 * This method check if 2 objects (Registrant) are equals by checking their 
	 * firstName, lastName and regNum
	 * @param obj the other object to be compared
	 */
	public boolean equals(Object obj) {
		if (!(obj instanceof Registrant)) return false;
		Registrant reg = (Registrant)obj;
		// compare firstname, lastname and regNum of this with obj
		//this code provided by Dave Houtman [2020] personal communication
		return this.getFirstName() == reg.getFirstName()
				&& this.getLastName() == reg.getLastName()
				&& this.getRegNum() == reg.getRegNum();
	}

	/**
	 * The toString method of the Registrant class
	 */
	public String toString() {
		return String.format("Name: %s %s\nRegistration Number: #%d\n", getFirstName(), getLastName(), getRegNum());
	}
}
