/*
* Course Name: CST8284_303
* Student Name: Duy Pham
* Class Name: Property
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
public class Property implements Serializable {
	/**
	 * Declare serialVersionUID
	 */
	public static final long serialVersionUID = 1L;

	/**
	 * Declare the tax rate per meter square
	 */
	private static final double TAX_RATE_PER_M2 = 12.50;
	
	/**
	 * Declare the default regNum which is 999 < the first regNum 1000
	 */
	private static final int DEFAULT_REGNUM = 999;

	/**
	 * Declare the xLeft coordinate to the x-axis
	 */
	private int xLeft;

	/**
	 * Declare the yTop coordinate to the y-axis
	 */
	private int yTop;

	/**
	 * Declare length of the side on x-axis
	 */
	private int xLength;

	/**
	 * Declare the width of the side y-axis
	 */
	private int yWidth;

	/**
	 * Declare the default regNum
	 */
	private int regNum = DEFAULT_REGNUM;

	/**
	 * Declare area variable
	 */
	private int area;
	
	/**
	 * Declare area variable
	 */
	private double taxes;

	/**
	 * default Property constructor; it will chain automatically to the (int, int,
	 * int, int) constructor
	 */
	public Property() {
		this(0, 0, 0, 0);
	}


	/**
	 * Property Constructor take value xLength, yWidth, xLeft, yTop from user input
	 * and default int REGNUM,
	 * this one will chain automatic (Property pro, int) constructor
	 * 
	 * @param xLength the length of the side on x-axis (horizontal)
	 * @param yWidth the width of the side on y-axis (vertical)
	 * @param xLeft the x coordinate of the property
	 * @param yTop the y coordinate of the property
	 */
	public Property(int xLength, int yWidth, int xLeft, int yTop) {
		this(xLength, yWidth, xLeft, yTop, DEFAULT_REGNUM);
	}


	/**
	 * Property copy constructor which only changes the regNum
	 * 
	 * @param prop the property that we want to copy from
	 * @param regNum the new regNum to assign to the new property
	 */
	public Property(Property prop, int regNum) {
		// this code provided by Dave Houtman [2020] personal communication
		this(prop.getXLength(), prop.getYWidth(), prop.getXLeft(), prop.getYTop(), regNum);
	}

	// Property constructor set value of xLength, yWidth, xLeft, yTop and regNum and
	// store in integer
	/**
	 * The parameterized constructor that take the 4 values for position and size to set
	 * to variables
	 * 
	 * @param xLength the new length of the side on x-axis (horizontal)
	 * @param yWidth the new width of the side on y-axis (vertical)
	 * @param xLeft the new x coordinate of the property
	 * @param yTop the new y coordinate of the property
	 * @param regNum the new regNum to set to the new Property
	 */
	public Property(int xLength, int yWidth, int xLeft, int yTop, int regNum) {
		setXLength(xLength);
		setYWidth(yWidth);
		setXLeft(xLeft);
		setYTop(yTop);
		setRegNum(regNum);
	}

	/**
	 * The getter that returns xLeft coordinate
	 * @return xLeft coordinate on the x-axis
	 */
	public int getXLeft() {
		return xLeft;
	}

	/**
	 * The setter for xLeft
	 * @param left new xLeft value
	 */
	public void setXLeft(int left) {
		this.xLeft = left;
	}

	/**
	 * The getter that returns xRight coordinate
	 * @return total of xLeft and xLength which is xRight
	 */
	public int getXRight() {
		return getXLeft() + getXLength();
	}

	/**
	 * The getter that returns yTop coordinate
	 * @return yTop
	 */
	public int getYTop() {
		return yTop;
	}

	/**
	 * The setter for yTop coordinate
	 * @param top the new yTop value
	 */
	public void setYTop(int top) {
		this.yTop = top;
	}

	/**
	 * The getter that returns xRight coordinate
	 * @return total of xLeft and xLength which is xRight
	 */
	public int getYBottom() {
		return getYTop() + getYWidth();
	}

	/**
	 * The getter that returns yWidth value
	 * @return yWidth
	 */
	public int getYWidth() {
		return yWidth;
	}
	
	/**
	 * The setter for yWidth size
	 * @param width the new yWidth 
	 */
	public void setYWidth(int width) {
		this.yWidth = width;
	}

	/**
	 * The getter that returns xLength size
	 * @return xLength
	 */
	public int getXLength() {
		return xLength;
	}

	/**
	 * The setter for xLength size
	 * @param length the new xLength
	 */
	public void setXLength(int length) {
		this.xLength = length;
	}

	/**
	 * The getter that returns regNum value
	 * @return regNum
	 */
	public int getRegNum() {
		return regNum;
	}

	/**
	 * The setter for the regNum
	 * @param regNum the new regNum to be set
	 */
	private void setRegNum(int regNum) {
		this.regNum = regNum;
	}

	/**
	 * return Area of property using formula by length * width
	 * @return the multiply of xLength and yWidth
	 */
	public int getArea() {
		return (getXLength() * getYWidth());
	}

	/**
	 * calculate tax of Property by area * TAX_RATE_PER_M2
	 * @return the taxes by multiply area with tax per meter square
	 */
	public double getTaxes() {
		return getArea() * TAX_RATE_PER_M2;
	}

	/**
	 * The toString method that returns the string to print out
	 */
	@Override
	public String toString() {
		// using String.format to display out put value and infomation of Property
		return String.format(
				"\nCoordinates: %d, %d\n" + "Length: %d m  Width: %d m\n" + "Registrant: #%d\nArea: %d m2\n"
						+ "Property Taxes : $%.1f",
				this.getXLeft(), this.getYTop(), this.getXLength(), this.getYWidth(), this.getRegNum(), this.getArea(),
				this.getTaxes());
	}

	/**
	 * check if the xLeft and yTop of this is equal to obj
	 * @param obj the other object that we want to compare to this object
	 */
	@Override
	public boolean equals(Object obj) {
		// this code provided by Dave Houtman [2020] personal communication
		if (!(obj instanceof Property))
			return false;
		Property prop = (Property) obj;
		return this.getXLeft() == prop.getXLeft() && this.getYTop() == prop.getYTop() && hasSameSides(prop);
	}

	/**
	 * check if the xLength and yWidth of this is equal to obj
	 * @param prop the other prop that we use to compare its sides
	 * @return the boolean that indicates the two properties have the same sides or not
	 */
	public boolean hasSameSides(Property prop) {
		// this code provided by Dave Houtman [2020] personal communication
		return this.getXLength() == prop.getXLength() && this.getYWidth() == prop.getYWidth();
	}

	
	/**
	 * ThePatelGuy(2019). Check if two rectangles overlap at any point.[Webpage].
	 * Retrieved from
	 * https://stackoverflow.com/questions/23302698/java-check-if-two-rectangles-overlap-at-any-point
	 * @param prop the other property to check if this property overlaps it
	 * @return if the current property overlaps the given property
	 */
	public boolean overLaps(Property prop) {
		if (this.getXRight() <= prop.getXLeft() || this.getXLeft() >= prop.getXRight()
				|| this.getYTop() >= prop.getYBottom() || this.getYBottom() <= prop.getYTop()) {
			return false;
		}
		return true;
	}
}
