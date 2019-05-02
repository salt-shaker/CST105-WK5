package unoSimulator;

import java.util.List;
import java.util.ArrayList;

/****************
 * Player Class *
 ****************/
class Player {
	// Variables
	private String pName; // Player Name
	private int pPosition; // Player Position
	List<Integer> cHand = new ArrayList<>(); // Cards in hand

	// Player Stats
	private int pNumbers; // Probability to play numbers
	private int pSpecial; // Probability to play special
	private int pWild; // Probability to play wild's
	private int coUno; // Probability to call uno on others
	private int caUno; // Probability to say uno on last card

	// Constructor
	public Player(String sName, int sPosition) {
		pNumbers = setStats();
		pSpecial = setStats();
		pWild = setStats();
		coUno = setStats();
		caUno = setStats();
		pName = sName;
		pPosition = sPosition;
	}

	// Draw Card
	public void drCard(int addCard) {
		cHand.add(addCard);
	}

	// Remove card from hand
	public void rmCard(int x) {
		
		//Find mathcing value and remove from list
		for (int i=0; i < cHand.size(); i++) {
			if (cHand.get(i).equals(x)) {
				cHand.remove(i);
			}
		}
		
	}

	// Return Player Hand
	public int[] getHand() {
		int[] handSize = new int[cHand.size()];
		int counter = 0;
		
		for (int i : cHand) {
			handSize[counter] = i;
			counter++;
		}
		return handSize;
	}

	// Player Position
	public int position() {
		return pPosition;
	}

	// Player Name
	public String myName() {
		return pName;
	}

	// Set probability between 5 and 10
	private int setStats() {
		return (int) (Math.random() * 5) + 5;
	}

	// Show Hand
	public void showHand() {
		System.out.println("Player " + pName + " has the following cards in their hand");
		for (int i : cHand) {
			System.out.println(i);
		}

	}

}
