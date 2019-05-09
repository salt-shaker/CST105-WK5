package unoSimulator;

import java.io.IOException;

/**************
 * Game class *
 **************/
class Game extends Logger {

	private int scoreCap; // Game Score Cap
	private int currentTurn = 0; // Player turn counter
	private int currentPot; // Current card in Pot
	private int cardToPlay; // Card to play

	/***************
	 * Constructor *
	 ***************/
	public Game(int x, Player[] playerArray, Deck gDeck, Turn playerTurn) {
		// Update Log
		scoreCap = x;
		log("Score Cap Set: " + scoreCap);
		
		// Deal Cards
		dealCards(playerArray, gDeck, playerTurn);
	}

	/*********************
	 * Deal Cards Method *
	 *********************/
	// Deal 7 cards to each player
	public void dealCards(Player[] playerArray, Deck gDeck, Turn playerTurn) {
		
		int[] playerOrder = playerTurn.getOrder();
		
		for (int i = 0; i < 7; i++) {
			for (int x = 0; x < playerOrder.length; x++) {
				playerArray[playerOrder[x]].drCard(gDeck.sendCard());
			}
		}

		// Put 1 card in Pot
		gDeck.recieveCard(gDeck.sendCard());

		// Update Log
		log("Cards have been delt to players");
	}

	/******************
	 * newTurn Method *
	 ******************/
	// Start of new turn
	public boolean newTurn(Player[] playerArray, Deck gDeck, Card[] cardArray, Turn playerTurn) {
		
		// Get Current Player Turn
		currentTurn = playerTurn.getPlayerTurn();

		// Get current card in pot
		currentPot = checkPot(gDeck);

		// Get cards from players hand
		int[] playerHand = pHand(playerArray);

		// Get card to play
		cardToPlay = playerTurn.update(currentPot, cardArray, gDeck, playerArray);

		// If not card code 1000 or 2000
		if (cardToPlay != 1000 && cardToPlay != 2000 && cardToPlay != 3000 && cardToPlay != 4000) {

			// Send card to pot
			playCard(gDeck, playerArray, cardToPlay);

			// Check for Uno before end of turn
			if (unoCheck(playerHand)) {
				log(playerArray[currentTurn].myName() + " called Uno!");
			}
			// Check if winner
			if (winner(pHand(playerArray))) {
				log(playerArray[currentTurn].myName() + " has won the great game of Uno!");
				return true;
			}

		}

		// Player draws card
		if (cardToPlay == 2000) {
			log(playerArray[currentTurn].myName() + " needs to draw a card");
			boolean loseTurn = playerArray[currentTurn].drCard(gDeck.sendCard());
			if (!loseTurn) {
				log(playerArray[currentTurn].myName() + " is losing turn");
				playerTurn.playerLoseTurn();
			}
		}

		// If no winner found
		return false;

	}

	/******************
	 * pHand Method *
	 ******************/
	// Check players hand
	private int[] pHand(Player[] playerArray) {
		int[] x = playerArray[currentTurn].getHand();
		return x;
	}

	/******************
	 * checkPot Method *
	 ******************/
	// Get current card from pot and if player has valid card to play
	private int checkPot(Deck gDeck) {
		return gDeck.checkPot();
	}

	/******************
	 * playCard Method *
	 ******************/
	// Play cards
	private void playCard(Deck gDeck, Player[] playerArray, int cardToPlay) {
		// submit card to pot
		log("Card to play is: " + cardToPlay);
		gDeck.recieveCard(cardToPlay);
		// send to player and have player remove card from hand
		playerArray[currentTurn].rmCard(cardToPlay);
	}

	/******************
	 * unoCheck Method *
	 ******************/
	// Check if any players have uno
	private boolean unoCheck(int[] playerHand) {
		if (playerHand.length == 1) {
			return true;
		} else {
			return false;
		}
	}

	/******************
	 * winner Method *
	 ******************/
	// Check if any players have won
	private boolean winner(int[] playerHand) {
		if (playerHand.length == 0) {
			return true;
		} else {
			return false;
		}
	}

	/******************
	 * log Method *
	 ******************/
	private void log(String data) {
		try {
			logWritter(data);
		} catch (IOException e) {
			System.out.println(e);
		}
	}

}
