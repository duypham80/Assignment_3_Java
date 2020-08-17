/*
* Course Name: CST8284_303
* Student Name: Duy Pham
* Class Name: BadLandRegistryException
* Date: July 26, 2020
*/

package cst8284.asgmt3.landRegistry;

/**
* @author Duy Pham
* @version 1.02
*/
public class BadLandRegistryException extends RuntimeException {
	/**
	 * Declare serialVersionUID  
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Declare header of the exception
	 */
	private String header;
	
	/**
	 * This is the default constructor of BadLandRegistryException class
	 * that chains to the parameterized constructor
	 */
	public BadLandRegistryException() {
		this("Bad land registry data entered", "Please try again");
	}
	
	/**
	 * This is the parameterized constructor taking header and message as inputs
	 * and return nothing
	 * @param header represents the header of the exception that is set to header variable
	 * @param message represents the message to be print out of the exception by passing to the super class
	 */
	public BadLandRegistryException(String header, String message) {
		super(message);
		setHeader(header);
	}
	
	/**
	 * This is the getter that returns the header
	 * @return the header of the exception
	 */
	public String getHeader() {
		return header;
	}
	
	/**
	 * This is the setter that set the new header using the input
	 * @param header the new header that we want to set 
	 */
	public void setHeader(String header) {
		this.header = header;
	}
}
