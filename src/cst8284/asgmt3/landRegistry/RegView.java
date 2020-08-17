/*
* Course Name: CST8284_303
* Student Name: Duy Pham
* Class Name: RegView
* Date: July 26, 2020
*/

package cst8284.asgmt3.landRegistry;

import java.util.ArrayList;
import java.util.Scanner;

/**
 * 
 * @author Duy Pham
 * @version 1.02
 *
 */
public class RegView {

	/**
	 * Declare the Scanner object
	 */
	private static Scanner scan = new Scanner(System.in);
	
	/**
	 * Declare the RegControl object
	 */
	private static RegControl rc;

	/**
	 * Declare the two file names of Property and Registrant
	 */
	public static final String PROPERTIES_FILE = "LandRegistry.prop";
	public static final String REGISTRANTS_FILE = "LandRegistry.reg";

	/**
	 * All the selections which can be chosen in the displayMenu method
	 */
	private static final int ADD_NEW_REGISTRANT = 1;
	private static final int FIND_REGISTRANT = 2;
	private static final int LIST_REGISTRANTS = 3;
	private static final int DELETE_REGISTRANT = 4;
	private static final int ADD_NEW_PROPERTY = 5;
	private static final int DELETE_PROPERTY = 6;
	private static final int CHANGE_PROPERTY_REGISTRANT = 7;
	private static final int LIST_PROPERTY_BY_REGNUM = 8;
	private static final int LIST_ALL_PROPERTIES = 9;
	private static final int LOAD_LAND_REGISTRY_FROM_BACKUP = 10;
	private static final int SAVE_LAND_REGISTRY_TO_BACKUP = 11;
	private static final int EXIT = 0;

	/**
	 * this default constructor create new RegControl when a new instance is created
	 * this also load the land registry from backup
	 */
	public RegView() {
		rc = new RegControl();
		viewLoadLandRegistryFromBackUp();
	}

	/**
	 * This code provided by Dave Houtman [2020] personal communication
	 * @param s the String to be display to the console
	 * @return the input String which is got from the Scanner variable
	 * @throws the exception which describe the empty input from Scanner
	 * @throws the exception which describe the null String input from console
	 */
	private static String getResponseTo(String s) { // get user input as a string
		System.out.print(s);
		String inputString = scan.nextLine();
		if (RegControl.isInputEmpty(inputString)) {
			throw new BadLandRegistryException("Missing value", "Missing an input value");
		} else if (RegControl.isInputNull(inputString)) {
			throw new BadLandRegistryException("Null value entered", "An attempt was made to pass a null value to a variable.");
		}
		return inputString;
	}

	/**
	 * the getter for the RegControl variable
	 * @return rc the instanciated RegControl variable
	 */
	private static RegControl getRegControl() {
		return rc;
	}

	/**
	 * This method is used to launch the whole program
	 * This consists of 2 do-while loop. 
	 * The first one check if there is any exception in the main code. If there is no exception, the loop stops if the main code is done
	 * The second one check if the choice is 0, it stops the loop
	 */
	public static void launch() {
		int choice;
		boolean repeat = true;
		do {
			try {
				do {
					choice = displayMenu();
					executeMenuItem(choice);
				} while (choice != EXIT);
				repeat = false;
			}catch (BadLandRegistryException ex) {
				System.out.println(ex.getMessage() + "; please re-enter\n"); // From Lab 7
			} catch (Exception ex) {
				scan.next();
				System.out.println("General exception thrown; source unknown\n"); // From Lab 7
			}
		} while (repeat);
	}
 
	/**
	 * Display Menu to user to choose using scanner created
	 * @return the String choice of the user
	 */
	private static int displayMenu() { // From Assignment2 Starter Code by Dave Houtman
		System.out.println("Enter a selection from the following menu:");
		System.out.println(ADD_NEW_REGISTRANT    + ". Enter a new registrant\n" 
				+ FIND_REGISTRANT				 + ". Find registrant by registration number\n" 
				+ LIST_REGISTRANTS 				 + ". Display list of registrants\n"
				+ DELETE_REGISTRANT 			 + ". Delete a registrant\n" 
				+ ADD_NEW_PROPERTY 				 + ". Register a new property\n"
				+ DELETE_PROPERTY 				 + ". Delete property\n" 
				+ CHANGE_PROPERTY_REGISTRANT 	 + ". Change a property's registrant\n" 
				+ LIST_PROPERTY_BY_REGNUM		 + ". Display all properties with the same registration number\n" 
				+ LIST_ALL_PROPERTIES			 + ". Display all registered properties\n" 
				+ LOAD_LAND_REGISTRY_FROM_BACKUP + ". Load land registry from backup\n" 
				+ SAVE_LAND_REGISTRY_TO_BACKUP	 + ". Save land registry to backup\n" 
				+ EXIT 							 + ". Exit program\n");
		int ch = scan.nextInt();
		scan.nextLine(); // 'eat' the next line in the buffer
		return ch;
	}

	/**
	 * This method execute the choice given by the displayMenu method
	 * this uses switch to check the choice is which case
	 * Then it execute the right view method
	 * @param choice
	 */
	private static void executeMenuItem(int choice) { // From Assignment2 Starter Code by Dave Houtman
		switch (choice) {
			case ADD_NEW_REGISTRANT:		viewAddNewRegistrant();		break;
			case FIND_REGISTRANT:			viewFindRegistrant();		break;
			case LIST_REGISTRANTS:			viewListOfRegistrants();	break;
			case DELETE_REGISTRANT:			viewDeleteRegistrant();		break;
			case ADD_NEW_PROPERTY:			viewAddNewProperty();		break;
			case DELETE_PROPERTY:			viewDeleteProperty();		break;
			case CHANGE_PROPERTY_REGISTRANT:viewChangePropertyRegistrant();	break;
			case LIST_PROPERTY_BY_REGNUM:	viewListPropertyByRegNum();	break;
			case LIST_ALL_PROPERTIES:		viewListAllProperties();	break;
			case LOAD_LAND_REGISTRY_FROM_BACKUP:
			// Check if the user wants to overwrite the existing data
				System.out.print("You are about to overwrite existing records;"
								+ "do you wish to continue? (Enter ‘Y’ to proceed.) ");
				String input = getResponseTo("");
				if (input.equals("Y") || input.equals("y")) {
				viewLoadLandRegistryFromBackUp();
				System.out.println("Land Registry has been loaded from backup file");
				}
					break;
			case SAVE_LAND_REGISTRY_TO_BACKUP:	
				viewSaveLandRegistryToBackUp();
				break;
			case EXIT:
				// backup the land registry to file before closing the program
				System.out.println("Exiting Land Registry\n");
				getRegControl().saveToFile(getRegControl().listOfRegistrants(), REGISTRANTS_FILE);
				getRegControl().saveToFile(getRegControl().listOfAllProperties(), PROPERTIES_FILE);
				break;
			default:
				System.out.println("Invalid choice: try again. (Select " + EXIT + " to exit.)\n");
		}
		System.out.println(); // add blank line after each output
	}
	
	/**
	 * This method return a string which is the display of the given ArrayList on the console
	 * @param <T> the generic type of the elements in the ArrayList given
	 * @param displayList the ArrayList to be displayed to String
	 * @return the output String to be printed out the console
	 */
	
	private static <T> String toString(ArrayList<T> displayList) {
		String outputString = "";
		for (T item: displayList) {
			outputString += item;
		}
		return outputString;
	}

	/**
	 * This method ask the user for a registration numbers
	 * @return the integer regNum input by the user
	 * @throws exception that indicates a non digit character contained in the input
	 * @throws exception that indicates that there is no registrant contained in the ArrayList
	 * @throws exception that indicates the regNum input is not registered yet
	 */
	private static int requestRegNum() { // From Assignment2 Starter Code by Dave Houtman
		String input = getResponseTo("Enter registration number : ");
		if (!RegControl.doesStringContainOnlyNumber(input)) {
			throw new BadLandRegistryException("Invalid Registration number",
					"Registration number must contain digits only; alphabetic and special characters are prohibited");
		} else if (RegControl.isRegistrantsEmpty(getRegControl().listOfRegistrants())) {
			throw new BadLandRegistryException("No registrants available", "There are no registrants currently listed");
		} else if (!RegControl.isRegNumRegistered(Integer.parseInt(input), getRegControl().listOfRegistrants())) {
			throw new BadLandRegistryException("Unregistered value", "There is no registrant having that registration number");
		}
		return Integer.parseInt(input);
	}

	/**
	 * This method make new property from user input
	 * This method using requestRegnum method to get the regNum input
	 * Then it use Scanner to get the size values
	 * @throws the exception which indicates the size given exceeds the max size
	 * @throws the exception which indicates the size given is below the minimum size
	 * @return the new property created created from the inputs
	 */
	private static Property makeNewPropertyFromUserInput() {
		// all the parseInt to convert String to integer (the getResponseTo() method
		// return String)
		int regNum = requestRegNum();
		// check if a registrant with the regNum is available
//		String coordinateString = getResponseTo("Enter top and left coordinates of property (as X, Y): ");
		System.out.print("Enter top and left coordinates of property (as X, Y): ");
		String coordinateString = scan.nextLine();
		// split the xLeft and yTop from the string: "xLeft, yTop"
		String[] coordinates = coordinateString.split(",");
//		String dimensionString = getResponseTo("Enter length and width of property (as length, width): ");
		System.out.print("Enter length and width of property (as length, width): ");
		String dimensionString = scan.nextLine();
		// split the xLength and yWidth from the string: "xLength, yWidth"
		String[] dimensions = dimensionString.split(",");
		// convert all string in the lists to int and create a new Property object with them
		int xLeft = Integer.parseInt(coordinates[0].trim());
		int yTop = Integer.parseInt(coordinates[1].trim());
		int xLength = Integer.parseInt(dimensions[0].trim());
		int yWidth = Integer.parseInt(dimensions[1].trim());
		if (RegControl.doesPropertyExceedSize(xLeft, yTop, xLength, yWidth)) {
			throw new BadLandRegistryException("Property exceeds available size",
					"The property requested extends beyond the boundary of the available land");
		} else if (RegControl.isPropertyBelowMinimumSize(xLength, yWidth)) {
			throw new BadLandRegistryException("Property below minimum size",
					"The minimum property size entered must have a length of at least 20 m and a width of 10 m");
		}
		Property new_prop = new Property(xLength, yWidth, xLeft, yTop, regNum);
		return new_prop;
	}

	/**
	 * This method make new registrant form user input using getResponseTo to get the 
	 * registrant's full name
	 * @return the new Registrant created
	 */
	private static Registrant makeNewRegistrantFromUserInput() {
		// get user's names as String "firstName lastName", and create a new Registrant
		// object with it
		String input_name = getResponseTo("Enter registant's first and Last name: ");
		Registrant new_Registrant = new Registrant(input_name);
		return new_Registrant;
	}

	/**
	 * This method is the view of addNewRegistrant. It calls addNewRegistrant from RegControl
	 * It display some message for input and says if the registrant is added or not
	 */
	public static void viewAddNewRegistrant() {
		Registrant new_reg = makeNewRegistrantFromUserInput();
		getRegControl().addNewRegistrant(new_reg);
		System.out.println("Registrant added:");
		System.out.println(new_reg.toString());
	}

	/**
	 * This method is the view of findRegistrant. It calls findRegistrant from RegControl
	 * It display some message for input and says if the registrant is available of not
	 */
	public static void viewFindRegistrant() { // From Assignment2 Starter Code by Dave Houtman
		int regNum = requestRegNum();
		Registrant reg = rc.findRegistrant(regNum);
		System.out.println("" + ((reg == null) ? // Registrant does not exist
				"A registrant having the registration number\n" + regNum
						+ " could not be found in the registrants list."
				: // Registrant found
				"The registrant associated with that registration number " + "is\n" + reg.toString() + "\n"));
	}

	/**
	 * This method is the view of listOfRegistrant. It calls listOfRegistrants from RegControl
	 * It display all the registrants in the registrants ArrayList.
	 * If there is no registrant, it displays a fail message
	 */
	public static void viewListOfRegistrants() {
		// call listOfRegistrants() and see the output. If the output's length is 0,
		// there are no Registrant in the list
		// else, print all of them using toString()
		ArrayList<Registrant> list_reg = getRegControl().listOfRegistrants();
		if (list_reg.size() == 0) {
			System.out.println("No Registrants loaded yet\n");
		} else {
			System.out.println("List of registrants:\n");
			System.out.println(toString(list_reg));
		}
	}

	/**
	 * This method is the view of deleteRegistrant. It calls deleteRegistrant from RegControl
	 * It gets input from use, displays a message to check if the user is sure
	 * If yes, it delete the registrant with all the properties which have the same regNum
	 */
	public static void viewDeleteRegistrant() {
		// get input regNum from user then call deleteRegistrant method with parameter
		// is the given regNum
		String input = getResponseTo("Enter registrants number to delete: ");
		if (!RegControl.doesStringContainOnlyNumber(input)) {
			throw new BadLandRegistryException("Invalid Registration number",
					"Registration number must contain digits only; alphabetic and special characters are prohibited");
		} else if (RegControl.isRegistrantsEmpty(getRegControl().listOfRegistrants())) {
			throw new BadLandRegistryException("No registrants available", "There are no registrants currently listed");
		} else if (!RegControl.isRegNumRegistered(Integer.parseInt(input), getRegControl().listOfRegistrants())) {
			throw new BadLandRegistryException("Unregistered value", "There is no registrant having that registration number");
		}
		int regNum = Integer.parseInt(input);
		String choice = getResponseTo("You are about to delete a registrant number and all the its "
				+ "associated properties;\nDo you wish to continue? (Enter ‘Y’ to proceed.) ");
		if (choice.equals("Y") || choice.equals("y")) {
			Registrant deletedRegistrant = getRegControl().deleteRegistrant(regNum);
			if (deletedRegistrant == null) {
				System.out.println("No registrations number found.");
			} else {
				ArrayList<Property> prop_list = rc.listOfProperties(regNum);
				rc.deleteProperties(prop_list);
				System.out.println("Registrant and related properties deleted.");
			}
		} else { // if deleteRegistrant return null, the program failed to delete (no registrant
					// with given regNum exists)
			System.out.printf("The registrant with registrant number %d was not deleted.", regNum);
		}
	}

	/**
	 * This method is the view of addNewProperty. It calls addNewProperty from RegControl
	 * It call makeNewPropertyFromUserInput to create a new Property from user input.
	 * It then checks if the created property overlaps another.
	 * If yes, it prints the failed message
	 * If not, it add the property to the properties ArrayList and print the successful message
	 */
	public static void viewAddNewProperty() {
		Property new_prop = makeNewPropertyFromUserInput();
		if (new_prop != null) {
			// added_prop store the output of the method addNewProperty()
			Property added_prop = getRegControl().addNewProperty(new_prop);
			
			// if added_prop == null, the array of Property is full
			// if added_prop is the same as new_prop, the method successfully added the
			// Property
			// if added_prop is different from new_prop, the new_prop overlaps another
			// Property in the list
			if (new_prop == added_prop) {
				System.out.println("New property has been registered as:");
			} else {
				throw new BadLandRegistryException("Property overlap",
						"The property entered overlaps with an existing property with mcoordinates " +
							added_prop.getXLeft() + " " + added_prop.getYTop() + " and size " + added_prop.getXLength() + ", " + added_prop.getYWidth());
			}
			System.out.println(added_prop.toString());
		}
	}

	/**
	 * This method is the view of changePropertyRegistrant. It calls changePropertyRegistrant from RegControl
	 * It get the old regNum from the user input and check if the registrant is available
	 * 		if not, return null
	 * Else, it get the input from user the new regNum, lists all the properties with the
	 * old regNum, then it changes all the regNum of properties in the ArrayList
	 */
	public static void viewChangePropertyRegistrant() {
		// From Assignment2 Starter Code by Dave Houtman
//		int original_regNum = Integer.parseInt(getResponseTo("Enter original registrants number: "));
		String input = getResponseTo("Enter original registrants number: ");
		if (!RegControl.doesStringContainOnlyNumber(input)) {
			throw new BadLandRegistryException("Invalid Registration number",
					"Registration number must contain digits only; alphabetic and special characters are prohibited");
		} else if (RegControl.isRegistrantsEmpty(getRegControl().listOfRegistrants())) {
			throw new BadLandRegistryException("No registrants available", "There are no registrants currently listed");
		} else if (!RegControl.isRegNumRegistered(Integer.parseInt(input), getRegControl().listOfRegistrants())) {
			throw new BadLandRegistryException("Unregistered value", "There is no registrant having that registration number");
		}
		int original_regNum = Integer.parseInt(input);
		// get all Properties which have the same original_regNum
		// if there is no Property in prop_list, not Property with the given regNum is
		// found
		if (getRegControl().findRegistrant(original_regNum) == null) {
			System.out.println("The original registrants number not found");
			return;
		}
		// From Assignment2 Starter Code by Dave Houtman
		input = getResponseTo("Enter new registrants number: ");
		if (!RegControl.doesStringContainOnlyNumber(input)) {
			throw new BadLandRegistryException("Invalid Registration number",
					"Registration number must contain digits only; alphabetic and special characters are prohibited");
		} else if (RegControl.isRegistrantsEmpty(getRegControl().listOfRegistrants())) {
			throw new BadLandRegistryException("No registrants available", "There are no registrants currently listed");
		} else if (!RegControl.isRegNumRegistered(Integer.parseInt(input), getRegControl().listOfRegistrants())) {
			throw new BadLandRegistryException("Unregistered value", "There is no registrant having that registration number");
		}
		int newRegNum = Integer.parseInt(input);
		// loop through all found Properties to change their regNum
		ArrayList<Property> propertiesWithRegNum = getRegControl().listOfProperties(original_regNum);
		ArrayList<Property> newRegNumProperties = getRegControl().changePropertyRegistrant(propertiesWithRegNum, newRegNum);
		if (newRegNumProperties == null) {
			System.out.println("Operation failed.");
		}
		System.out.printf(
				"Operation completed; the new registration number, %d, has replaced %d in all affected properties.\n",
				original_regNum, newRegNum);
	}

	/**
	 * This method is the view of deleteProperties. It calls deleteProperties from RegControl
	 * It get regNum from user input, list all the properties with the regNum.
	 * If the list contains nothing, it displays the failed message
	 * Else, it delete all the properties
	 */
	public static void viewDeleteProperty() {
		// get input regNum from user
		String input = getResponseTo("Enter registrants number of property to delete: ");
		if (!RegControl.doesStringContainOnlyNumber(input)) {
			throw new BadLandRegistryException("Invalid Registration number",
					"Registration number must contain digits only; alphabetic and special characters are prohibited");
		} else if (RegControl.isRegistrantsEmpty(getRegControl().listOfRegistrants())) {
			throw new BadLandRegistryException("No registrants available", "There are no registrants currently listed");
		} else if (!RegControl.isRegNumRegistered(Integer.parseInt(input), getRegControl().listOfRegistrants())) {
			throw new BadLandRegistryException("Unregistered value", "There is no registrant having that registration number");
		}
		int regNum = Integer.parseInt(input);
		// ArrayList<Property> prop_list = rc.listOfProperties(regNum);
		ArrayList<Property> prop_list = rc.listOfProperties(regNum);
		// check if the size of ArrayList is 0, else delete the property with given
		// regNum
		System.out.println("Properties are associated with that registration number are:\n");
		System.out.println(toString(prop_list));
		System.out.printf(
				"You are about delete %d properties; do you wish to continue?\n" + "(Enter 'Y' to proceed) ",
				prop_list.size());
		String choice = getResponseTo("");
		if (choice.equals("Y") || choice.equals("y")) {
			rc.deleteProperties(prop_list);
			System.out.println("Property/ies deleted");
		} else {
			System.out.println("Properties are associated with that registration number were not deleted");
		}
	}

	/**
	 * This method is the view of listOfProperties. It calls listOfProperties from RegControl
	 * This print all the properties with the given regNum to the output
	 */
	public static void viewListPropertyByRegNum() {
		int input_regNum = requestRegNum();
		// get the property list by the given regNum
		ArrayList<Property> found_prop_list = getRegControl().listOfProperties(input_regNum);
		// if the found_prop_list is empty, print out the warning message and stop the
		// method
		if (found_prop_list.size() == 0) {
			System.out.println("No Property with the given registration number found");
			return;
		}
		System.out.printf("\nList of properties by registration number %d:\n", input_regNum);
		// loop through all found properties and print out their toString()
		System.out.println(toString(found_prop_list));
	}

	/**
	 * This method is the view of listOfAllProperties. It calls listOfAllProperties from RegControl
	 * it uses the toString method to print out all of the properties to console
	 */
	public static void viewListAllProperties() {
		// using the method listOfAllProperties() to get a list of all registered
		// properties
		ArrayList<Property> prop_list = getRegControl().listOfAllProperties();
		// if the prop_list is empty, print out the warning message and stop the method
		if (prop_list.size() == 0) {
			System.out.println("Property Registry empty; no properties to display\n");
			return;
		}
		// loop through all found properties and print out their toString()
		for (Property property : prop_list) {
			System.out.println(property.toString());
		}
	}

	/**
	 * This method is the view of loadProperties. It calls refreshProperties and refreshRegistrants from RegControl
	 * This method clear the ArrayList's and add all the properties to the ArrayList
	 */
	public static void viewLoadLandRegistryFromBackUp() {
		// call method loadFromFile for registrants and properties and get the ArrayList
		// of both
		rc.refreshProperties();
		rc.refreshRegistrants();
	}

	/**
	 * This method is the view of saveToFile. It calls saveToFile from RegControl
	 * This method save the data from ArrayLists to te file
	 */
	public static void viewSaveLandRegistryToBackUp() {
		// call method saveToFile twice, one for registrants, one for properties and get
		// the ArrayList of data
		boolean save_reg = getRegControl().saveToFile(getRegControl().listOfRegistrants(), REGISTRANTS_FILE);
		boolean save_prop = getRegControl().saveToFile(getRegControl().listOfAllProperties(), PROPERTIES_FILE);
		// check if one of them failed or not
		if (save_prop == false || save_reg == false) {
			System.out.println("Unable to save land registry.");
		} else {
			System.out.println("Land Registry has been backed up to file.");
		}
	}
}
