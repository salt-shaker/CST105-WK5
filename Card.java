package unoSimulator;

/*************
 * Card Class*
 *************/
public class Card {
	// Variables
	private String type;
	private String color;
	private String value;
	private String statusEffect;

	/***************
	 * Constructor *
	 ***************/
	public Card() {
	
	}

	/***********
	 * setCard *
	 ***********/
	// Set card type, color, and value
	public void setCard(String xType, String xColor, String xValue, String xStatus) {
		type = xType;
		color = xColor;
		value = xValue;
		statusEffect = xStatus;
	}
	
	/************
	 * setColor *
	 ************/
	public void setColor(String xColor) {
		color = xColor;
	}

	/***********
	 * details *
	 ***********/
	public String[] details() {
		return new String[] { type, color, value, statusEffect} ;
	}

}
