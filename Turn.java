package unoSimulator;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Turn extends Logger{

	// Variables
	private List<String> roundLogs = new ArrayList<>(); // Cards in hand
	private List<String> playerNames = new ArrayList<>(); // Player Names
	private List<Integer> playerOrder = new ArrayList<>(); // Player order
	private int totalRounds, curTurn, cardInPot, cardToPlay;
	private boolean statusEffect; // True means apply status effect
	private boolean statusEffectApplied; // True if Effect applied to previous player
	private boolean EOT; // End Of Turn

	/****************
	 * Constructor *
	 ****************/
	public Turn() {
		totalRounds = 0;
		curTurn = 0;
		cardInPot = 0;
	}
	
	/******************
	 * playerLoseTurn *
	 ******************/
	public void playerLoseTurn() {
		curTurn++;
	}
	
	/************
	 * getOrder *
	 ************/
	public int[] getOrder() {
		int[] pOrder = new int[playerOrder.size()];
		for (int i = 0; i < playerOrder.size(); i++) pOrder[i] = playerOrder.get(i);
		return pOrder;
	}
	
	/*****************
	 * getPlayerTurn *
	 *****************/
	public int getPlayerTurn() {
		return curTurn;
	}
	
	/*****************
	 * getPlayerName *
	 *****************/
	public String getPlayerName() {
		return playerNames.get(curTurn);
	}

	/*****************
	 * update Method *
	 *****************/
	// Set card in pot
	public int update(int pot, Card[] cardArray, Deck gDeck, Player[] playerArray) {

		cardToPlay = 3000; // reset card to play
		cardInPot = pot;

		// Check if a status effect has been applied on previous turn
		if (statusEffectApplied) {
			// Reset effect trackers
			statusEffect = false;
			statusEffectApplied = false;
		}
		// Check which status effect needs to be applied to current player turn
		else if (statusEffect) {
			statusEffectApplied = status(cardInPot, cardArray, gDeck, playerArray);
			EOT = statusEffectApplied;
			cardToPlay = 1000;
		}

		// Check for playable card
		if (cardToPlay != 1000) {
			cardToPlay = playableCard(cardArray, playerArray);
			if (cardToPlay != 2000) {
				EOT = true;
				statusEffect = applyEffect(cardArray);
			}
		}

		// Check if player turn is ending
		if (EOT) {
			updateLog(cardArray, playerArray);
			EOT();
			return cardToPlay;
		} else {
			return 2000; // 2000 means draw a card, 1000 means status effect applied so end turn
		}
	}

	/*************************
	 * setPlayerOrder Method *
	 *************************/
	// Set player order and player names
	public void setPlayerOrder(int[] order, String[] names) {
		for (int i : order) {
			playerOrder.add(i);
		}
		for (String j : names) {
			playerNames.add(j);
		}
	}

	/**********************
	 * applyEffect Method *
	 **********************/
	// Update log
	private boolean applyEffect(Card[] cardArray) {
		String[] details = cardArray[cardToPlay].details();
		if (details[3].equals("true")) {
			return true;
		} else {
			return false;
		}
	}

	/********************
	 * updateLog Method *
	 ********************/
	// Update log
	private void updateLog(Card[] cardArray, Player[] playerArray) {
		String cardString = "";

		if (cardToPlay == 2000) {
			cardString = "Draw a card";
		} else if (cardToPlay == 1000) {
			cardString = "Player Skipped";
		} else {
			cardString = Arrays.toString(cardArray[cardToPlay].details());
		}

		String log = "Name: " + playerArray[curTurn].myName() + " | Round: " + totalRounds + " | Player Position: " + playerArray[curTurn].position() + " | Turn: " + curTurn
				+ " | Card in Pot: " + cardInPot + " | Card Played: " + cardToPlay + " | Status Affect: " + statusEffect
				+ "\n";
		log += " | #" + cardInPot + " Card In Pot Details: " + "\n";
		log += Arrays.toString(cardArray[cardInPot].details()) + "\n";
		log += " | #" + cardToPlay + " Card Played Details: " + "\n";
		log += cardString + "\n";
		roundLogs.add(log);
		try {
			logWritter(log);
		} catch (IOException e) {
			System.out.println(e);
		}

		// System.out.println(log);
	}

	/*****************
	 * status Method *
	 *****************/
	// Determine status Effects and apply effect
	private boolean status(int currentPot, Card[] cardArray, Deck gDeck, Player[] playerArray) {

		String[] details = cardArray[currentPot].details();

		System.out.println("checking for specials");

		switch (details[2]) {
		// Wild +4 Card
		case "wdr4":
			for (int i = 0; i < 4; i++) {
				playerArray[curTurn].drCard(gDeck.sendCard());
				System.out.println("Draw 4");
			}
			return true;
		// Draw 2 card
		case "dr2":
			for (int i = 0; i < 2; i++) {
				System.out.println("Draw 2");
				playerArray[curTurn].drCard(gDeck.sendCard());
			}
			return true;
		// Reverse Card
		case "rvs":
			System.out.println("Reverse");
			reverseO();
			return true;
		// Skip Card
		case "skp":
			System.out.println("Skip");
			return true;
		default:
			return false;
		}
	}

	/***********************
	 * playableCard Method *
	 ***********************/
	// Determine playable card
	private int playableCard(Card[] cardArray, Player[] playerArray) {

		List<Integer> playable = new ArrayList<>();
		int[] playerHand = playerArray[playerOrder.get(curTurn)].getHand();
		String[] pot = cardArray[cardInPot].details();

		// The Great Filter... lol
		for (int i = 0; i < playerHand.length; i++) {

			// Details for card in player hand
			String[] hand = cardArray[playerHand[i]].details();

			// Check color
			if (pot[1].equals(hand[1]) && !hand[2].equals("wld") && !hand[0].equals("wdr4")) {
				playable.add(playerHand[i]);
			}
			// Check number
			else if (pot[2].equals(hand[2]) && !hand[2].equals("wld") && !hand[0].equals("wdr4")) {
				playable.add(playerHand[i]);
			}
			// Check Wild Cards
			else if (hand[2].equals("wld") && hand[0].equals("wdr4")) {
				playable.add(playerHand[i]);
			}
		}

		if (playable.size() != 0) {
			return playable.get((int) (Math.random() * (playable.size())));
		} else {
			return 2000;
		}

	}

	/******************
	 * reverse Method *
	 ******************/
	// Reverse player order
	private void reverseO() {
		Collections.reverse(playerOrder);
		Collections.reverse(playerNames);
	}

	/**************
	 * EOT Method *
	 **************/
	// Final updated before ending turn
	private void EOT() {
		// Update Round Count
		totalRounds++;

		EOT = false;

		// Update Turn
		if (curTurn == 3) {
			curTurn = 0;
		} else {
			curTurn++;
		}
	}

}
