/*
* Course Name: CST8284_303
* Student Name: Duy Pham
* Class Name: RegControl
* Date: July 26, 2020
*/

package cst8284.asgmt3.landRegistry;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.PrimitiveIterator.OfDouble;

import javax.swing.Popup;

import java.io.EOFException;
import java.io.File;
import java.io.PrintWriter;

/**
 * 
 * @author Duy Pham
 * @version 1.02
 *
 */
public class RegControl {
	/**
	 * The registrants ArrayList contains all the Registrant registered
	 */
	private ArrayList<Registrant> registrants = new ArrayList<Registrant>();
	
	/**
	 * The properties ArrayList contains all the Properties registered
	 */
	private ArrayList<Property> properties = new ArrayList<Property>();

	/**
	 * This is the getter for the registrants ArrayList
	 * @return registrants the ArrayList of registered Registrant
	 */
	private ArrayList<Registrant> getRegistrants() {
		return registrants;
	}

	/**
	 * The getter of the properties ArrayList
	 * @return properties the ArrayList of registered Property
	 */
	private ArrayList<Property> getProperties() {
		return properties;
	}
	
	/**
	 * This method clear the current properties ArrayList and add all the 
	 * properties backup in the file
	 */
	public void refreshProperties() {
		ArrayList<Property> loadedProperties = loadFromFile(RegView.PROPERTIES_FILE);
		if (loadedProperties != null) {
			getProperties().clear();
			for (Property prop : loadedProperties) {
				addNewProperty(prop); 
			}
		}
	}
	
	/**
	 * This method clear the current registrants ArrayList and add all the 
	 * registrants backup in the file
	 */
	public void refreshRegistrants() {
		ArrayList<Registrant> loadedRegistrants = loadFromFile(RegView.REGISTRANTS_FILE);
		if (loadedRegistrants != null) {
			getRegistrants().clear();
			for (Registrant reg : loadedRegistrants) {
				addNewRegistrant(reg); 
			}
		}
	}

	/**
	 * This method add a new Registrant to the current registrants ArrayList
	 * @param reg the Registrant to be added
	 * @return the given registrant in parameter
	 */
	public Registrant addNewRegistrant(Registrant reg) {
		getRegistrants().add(reg);
		return reg;
	}

	/**
	 * This method find the Registrant with given regNum by calling getRegistrants
	 * @param regNum the given regNum to find the Registrant
	 * @return the found Registrant if available. If not, return null
	 */
	public Registrant findRegistrant(int regNum) {
		for (Registrant registrant: getRegistrants()) {
			if (registrant.getRegNum() == regNum)
				return registrant;
		}
		return null;
	}

	/**
	 * This list all the Registrant in the registrants ArrayList using for loop
	 * @return the ArrayList of all Registrant registered
	 */
	public ArrayList<Registrant> listOfRegistrants() {
		ArrayList<Registrant> listRegistrants = new ArrayList<Registrant>();
		for (Registrant registrant: getRegistrants()) {
			if (registrant != null) {
				listRegistrants.add(registrant);
			}
		}
		return listRegistrants;
	}

	/**
	 * This method delete a Registrant using ArrayList.remove() built-in method
	 * @param regNum the regNum of the Registrant to be deleted
	 * @return the found Registrant, if not found, it is null to be returned
	 */
	public Registrant deleteRegistrant(int regNum) {
		// find the registrant in the ArrayList then remove it out of the Array if it is
		// not null
		Registrant foundRegistrant = findRegistrant(regNum);
		if (foundRegistrant != null) {
			getRegistrants().remove(foundRegistrant);
		}
		return foundRegistrant;
	}

	/**
	 * This method add a new Property to the properties ArrayList
	 * This first check the given Property overlaps other ones using propertyOverlaps method
	 * @param prop the Property to be added
	 * @return the input property if it does not overlap. Else, return the Property being overlaped
	 */
	public Property addNewProperty(Property prop) {
		// check if the input prop overlaps another one
		Property prop_overlap = propertyOverlaps(prop);
		if (prop_overlap != null) {
			prop = prop_overlap;
		} else {
			// just add it at the end of the array using ArrayList.add() method
			getProperties().add(prop);
		}
		return prop;
	}

	/**
	 * This method delete all Property given in the input ArrayList
	 * using ArrayList.removeAll() 
	 * It first check if their is no Property given in the input ArrayList
	 * @param properties all Property to be deleted in properties ArrayList variable 
	 * @return true if the procedure is done, return false if it fails
	 */
	public boolean deleteProperties(ArrayList<Property> properties) {
		// if the array size is 0, there is no property
		if (properties.size() == 0) {
			return false;
		}
		// remove all properties in the array using ArrayList.removeAll()
		// Reference: https://docs.oracle.com/javase/7/docs/api/java/util/ArrayList.html#removeAll(java.util.Collection)
		getProperties().removeAll(properties);
		return true;
	}

	/**
	 * This method change regNum of all given property with the same regNum
	 * using ArrayList.set() method
	 * @param oldRegNumPropertyArrayList the ArrayList contains all Property with old regNum
	 * @param newRegNum the newRegNum to be set to all old Property
	 * @return the new ArrayList contains all Property that has the new regNum. If the 
	 * procedure failed, return null
	 */
	public ArrayList<Property> changePropertyRegistrant(ArrayList<Property> oldRegNumPropertyArrayList, int newRegNum) {
		// create a new ArrayList that store properties whose regNums were changed
		ArrayList<Property> newRegNumPropertyArrayList = new ArrayList<Property>();
		if (oldRegNumPropertyArrayList.size() > 0) {
			// if their are more than 0 property in the input ArrayList, this change regNum
			// of each property then add to the new ArrayList
			for (Property property: oldRegNumPropertyArrayList) {
				Property newProperty = new Property(property, newRegNum);
				// Reference: method ArrayList.set() from 
				// https://docs.oracle.com/javase/7/docs/api/java/util/ArrayList.html#set(int,%20E)
				getProperties().set(getProperties().indexOf(property), newProperty);
				newRegNumPropertyArrayList.add(newProperty);
			}
			return newRegNumPropertyArrayList;
		} else { // return null if there is 0 property in the old ArrayList
			return null;
		}
	}

	/**
	 * This method list all the properties registered which have the same given regNum 
	 * if regNum given is 0, list all the properties registered in the properties ArrayList
	 * @param regNum the regNum to find Property
	 * @return return the list of properties found with the given regNum
	 */
	public ArrayList<Property> listOfProperties(int regNum) {
		if (regNum == 0) {
			return listOfAllProperties();
		} else {
			// loop through all properties then check each one has the same regNum
			ArrayList<Property> prop_list = new ArrayList<Property>();
			for (Property prop : listOfAllProperties())
				if (prop.getRegNum() == regNum) {
					prop_list.add(prop);
				}
			return prop_list;
		}
	}

	/**
	 * List of all Property in the properties ArrayList
	 * @return all the Property in the properties ArrayList
	 */
	public ArrayList<Property> listOfAllProperties() {
		// return all properties
		ArrayList<Property> allProperties = new ArrayList<Property>();
		for (Property property: getProperties()) {
			if (property != null) {
				allProperties.add(property);
			}
		}
		return allProperties;
	}

	/**
	 * check if the given property overlaps another by looping through all
	 * @param prop the Property to check if it overlaps another one
	 * @return the property being overlaps in the ArrayList, if not, return null
	 */
	public Property propertyOverlaps(Property prop) {
		// loop through all the properties and use overLaps() method to check if the
		// prop overlaps each one
		for (Property property : listOfAllProperties()) {
			if (property.overLaps(prop)) {
				return property;
			}
		}
		return null;
	}
	
	/**
	 * This method save the T objects in the source ArrayList to the file with given fileName
	 * @param <T> the generic type of the input ArrayList
	 * @param source the ArrayList to be saved to file
	 * @param fileName the filename to be set
	 * @return true if the procedure is finished. Else, return false
	 */
	public <T> boolean saveToFile(ArrayList<T> source, String fileName) {
		// From Assignment 3 Starter Code by Dave Houtman
		File file = new File (fileName);
		if (file.exists()) file.delete();
     	try (FileOutputStream objectFileStream = new FileOutputStream(fileName);
    		ObjectOutputStream oos = new ObjectOutputStream(objectFileStream);
    	) {
     		for (T obj: source) oos.writeObject(obj);
     		return true;
    	} catch(Exception e) { 
        	return false;
    	}
	}

	@SuppressWarnings("unchecked")
	/**
	 * This method load the items from the ArrayList which were saved to the file with the given fileName
	 * @param <T> The generic type of the output ArrayList
	 * @param fileName of the file to be loaded
	 * @return the ArrayList of type <T> to be loaded 
	 */
	public <T> ArrayList<T> loadFromFile(String fileName) {		
		// From Assignment 3 Starter Code by Dave Houtman
		if (!new File (fileName).exists()) return null; 	
		ArrayList <T> al = new ArrayList<>();
    	try (
       		FileInputStream objectFileStream = new FileInputStream(fileName);
    		ObjectInputStream ois = new ObjectInputStream(objectFileStream);
    	) {
    		while (true) al.add((T)ois.readObject());
     	} catch(EOFException e) { 
        	return al;
    	} catch(Exception e) {
    		return null;
    	}
	}
	
	/**
	 * This method check if the given String contains only numbers
	 * it first create the ArrayList of numbers from 0 to 9
	 * then it checks if each character in the string is contained in the numbers ArrayList
	 * @param str the input String to be checked
	 * @return true if the string contains only number, else return false
	 */
	public static boolean doesStringContainOnlyNumber(String str) {
		ArrayList<String> numbers = new ArrayList<String>();
		for (int i=0; i < 10; i++) {
			numbers.add(Integer.toString(i));
		}
		for (int i = 0; i < str.length(); i++) {
			if (!numbers.contains(String.valueOf(str.charAt(i)))) {
				return false;
			}
		}
		return true;
	}
	
	/**
	 * Checks if the given registrant ArrayList is empty
	 * @param listRegistrants the ArrayList to be check
	 * @return true if it is empty
	 */
	public static boolean isRegistrantsEmpty(ArrayList<Registrant> listRegistrants) {
		return listRegistrants.isEmpty();
	}
	
	/**
	 * Check if a Registrant registered with the given regNum
	 * by checking if the regNum is larger or smaller than the current maximum regNum
	 * @param regNum the regNum to be checked
	 * @param listRegistrants the list of Registrant that we use to check the regNum
	 * @return true if the regNum is already registrered, else false
	 */
	public static boolean isRegNumRegistered(int regNum, ArrayList<Registrant> listRegistrants) {
		int maxRegNum = 999;
		for (Registrant registrant: listRegistrants) {
			if (registrant.getRegNum() > maxRegNum) {
				maxRegNum = registrant.getRegNum();
			}
		}
		return regNum <= maxRegNum;
	}
	
	/**
	 * check if the property exceed the size
	 * if the xRight (=xLeft + xLength) > 1000 or the yBottom (=yTop + yWidth) > 1000
	 * @param xLeft
	 * @param yTop
	 * @param xLength
	 * @param yWidth
	 * @return true if the property exceed the max size
	 */
	public static boolean doesPropertyExceedSize(int xLeft, int yTop, int xLength, int yWidth) {
		if (xLeft + xLength > 1000 || yTop + yWidth > 1000) {
			return true;
		}
		return false;
	}
	
	/**
	 * Check if the property is below the minimum size
	 * @param xLength check if this is < 20
	 * @param yWidth check if this is < 10
	 * @return true if the property is below the minimum size
	 */
	public static boolean isPropertyBelowMinimumSize(int xLength, int yWidth) {
		if (xLength < 20 || yWidth < 10) {
			return true;
		}
		return false;
	}
	
	/**
	 * check if the input String is empty
	 * @param input the String to be checked
	 * @return true if the input String is empty
	 */
	public static boolean isInputEmpty(String input) {
		return input.equals("");
	}
	
	/**
	 * check if the input String is null
	 * @param input the String to be checked
	 * @return true if the input is null, else false
	 */
	public static boolean isInputNull(String input) {
		return input == null;
	}
}
