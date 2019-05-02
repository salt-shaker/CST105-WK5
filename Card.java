package unoSimulator;

/*************
 * Card Class*
 *************/
public class Card {
	// Variables
	private String type;
	private String color;
	private String value;

	// Constructor
	public Card() {
	
	}

	// Set card type, color, and value
	public void setCard(String xType, String xColor, String xValue) {
		type = xType;
		color = xColor;
		value = xValue;
	}
	
	public void setColor(String xColor) {
		color = xColor;
	}

	public String[] details() {
		return new String[] { type, color, value } ;
	}

}
