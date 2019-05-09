package unoSimulator;

import java.util.List;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

/****************
 * Player Class *
 ****************/
class Player extends Logger {
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

	/***************
	 * Constructor *
	 ***************/
	public Player(String sName, int sPosition) {
		pNumbers = setStats();
		pSpecial = setStats();
		pWild = setStats();
		coUno = setStats();
		caUno = setStats();
		pName = sName;
		pPosition = sPosition;

		try {
			logWritter("Player " + pName + " Created. Player Position " + pPosition + ".");
			logWritter("Player Stats" + " | Play Numbers - " + pNumbers + " | Play Special - " + pSpecial
					+ " | Play Wilds - " + pWild + " | Call Uno on other - " + coUno + " | Call Uno on self - "
					+ caUno);
		} catch (IOException e) {
			System.out.println(e);
		}
	}

	/**********
	 * drCard *
	 **********/
	// Draw Card
	public boolean drCard(int addCard) {
		// Code 4000 means no cards to draw, player will lose his turn
		if (addCard == 4000) {
			// Update Log
			try {
				logWritter("Player " + pName + " unable to draw cards. Deck Empty");
			} catch (IOException e) {
				System.out.println(e);
			}
			return false;
		} else {
			cHand.add(addCard);
			// Update Log
			try {
				logWritter("Player " + pName + " added " + addCard + " to hand.");
			} catch (IOException e) {
				System.out.println(e);
			}
			return true;
		}
	}

	/**********
	 * rmCard *
	 **********/
	// Remove card from hand
	public void rmCard(int x) {
		int cardRm = 0;

		// Find mathcing value and remove from list
		for (int i = 0; i < cHand.size(); i++) {

			// System.out.println(cHand.get(i));
			if (cHand.get(i).equals(x)) {

				cardRm = i;
			}
		}

		// System.out.println(cardRm);
		cHand.remove(cardRm);

		try {
			logWritter("Player " + pName + " removed " + cardRm + " from hand.");
		} catch (IOException e) {
			System.out.println(e);
		}

	}

	/***********
	 * getHand *
	 ***********/
	// Return Player Hand
	public int[] getHand() {
		int[] handSize = new int[cHand.size()];
		
		try {
			for (int i = 0; i < cHand.size() - 1; i++) {
				handSize[i] = cHand.get(i);
			}
		} catch (Exception e) {
			System.out.println("cHand size is: " + cHand.size());
			System.exit(0);
		}
		return handSize;
	}

	/************
	 * position *
	 ************/
	// Player Position
	public int position() {
		return pPosition;
	}

	/**********
	 * myName *
	 **********/
	// Player Name
	public String myName() {
		return pName;
	}

	/************
	 * setStats *
	 ************/
	// Set probability between 5 and 10
	private int setStats() {
		return (int) (Math.random() * 5) + 5;
	}

	/************
	 * showHand *
	 ************/
	// Show Hand
	public void showHand() {
		System.out.println("Player " + pName + " has the following cards in their hand");
		System.out.println(Arrays.toString(this.cHand.toArray()));

	}

}
