package unoSimulator;

/**************
 * Game class *
 **************/
class Game {

	// Track Game States
	// Maybe later

	private int scoreCap; // Game Score Cap
	private int currentTurn = 0; // Player turn counter
	private int[] pOrder; // Player Position
	private int currentPot; // Current card in Pot
	private int cardToPlay; // Card to play
	private boolean cardEffectActive;

	// Constructor
	public Game(int x) {
		scoreCap = x;
	}

	// Deal 7 cards to each player
	public void dealCards(Player[] playerArray, Deck gDeck) {
		for (int i = 0; i < 7; i++) {
			for (int x = 0; x < playerArray.length; x++) {
				playerArray[x].drCard(gDeck.sendCard());
			}
		}

		// Put 1 card in Pot
		gDeck.recieveCard(gDeck.sendCard());
	}

	// Get player order
	public void getPlayerOrder(Player[] playerArray) {

		// Set pOrder array size
		pOrder = new int[playerArray.length];

		// Get position from player object
		for (int x = 0; x < playerArray.length; x++) {
			pOrder[x] = playerArray[x].position();
		}
	}

	// Start of new turn
	public boolean newTurn(Player[] playerArray, Deck gDeck, Card[] cardArray) {

		// System.out.println(playerArray[pOrder[currentTurn]].myName());

		// Get current card in pot
		currentPot = checkPot(gDeck);

		// Get cards from players hand
		int[] playerHand = pHand(playerArray);

		// Compare Hand for numbers
		while (true) {
			// If effect of card has not been applied
			if (!cardEffectActive) {
				// Check pot and apply necessary action
				// System.out.println("choosing effect");

				// Wild +4 Card
				if (pd4(cardArray[currentPot].details())) {
					cardToPlay = 1000;
					cardEffectActive = true;

					for (int i = 0; i < 5; i++) {
						playerArray[currentTurn].drCard(gDeck.sendCard());
					}
					break;
				}

				// Draw 2 card
				if (pd2(cardArray[currentPot].details())) {
					cardToPlay = 1000;
					cardEffectActive = true;

					for (int i = 0; i < 3; i++) {
						playerArray[currentTurn].drCard(gDeck.sendCard());
					}
					break;
				}

				// Reverse Card
				if (pRev(cardArray[currentPot].details())) {
					cardToPlay = 1000;
					cardEffectActive = true;
					break;
				}

				// Skip Card
				if (pSkip(cardArray[currentPot].details())) {
					cardToPlay = 1000;
					cardEffectActive = true;
					break;
				}
			} else {
				// Remove card effect
				cardEffectActive = false;
			}

			// Check for numbers
			boolean cphn = compareHandNumber(currentPot, playerHand, cardArray);
			if (cphn)
				break;
			// Check for special cards
			boolean cphs = compareHandSpecial(currentPot, playerHand, cardArray);
			if (cphs)
				break;
			// Check for pot for wilds
			boolean cphw = compareHandWild(currentPot, playerHand, cardArray);
			if (cphw)
				break;
			// Check for hand for wilds
			boolean cphpw = compareHandPlayWild(playerHand, cardArray);
			if (cphpw)
				break;

			// If no valid options, to card to play to 2000
			cardToPlay = 2000;
			break;
		}

		System.out.println(
				currentPot + " is Current Pot. " + cardToPlay + " is card to play." + " Player turn: " + currentTurn);

		if (cardToPlay != 1000 && cardToPlay != 2000) {

			// Send card to pot
			playCard(gDeck, playerArray, cardToPlay);

			// Check for Uno before end of turn
			if (unoCheck(playerHand)) {
				// Do something
			}

			// Check if winner
			if (winner(pHand(playerArray))) {
				return true;
			}

			// Maintain turn order
			if (currentTurn == 3) {
				currentTurn = 0;
			} else {
				currentTurn++;
			}

		}

		if (cardToPlay == 1000) {

			// Maintain turn order
			if (currentTurn == 3) {
				currentTurn = 0;
			} else {
				currentTurn++;
			}
		}

		if (cardToPlay == 2000) {
			playerArray[currentTurn].drCard(gDeck.sendCard());
		}

		// If no winner found
		cardToPlay = 0;
		return false;

	}

	// Check players hand
	private int[] pHand(Player[] playerArray) {
		return playerArray[currentTurn].getHand();
	}

	// Get current card from pot and if player has valid card to play
	private int checkPot(Deck gDeck) {
		return gDeck.checkPot();
	}

	// Check for numbers
	private boolean compareHandNumber(int currentPot, int[] playerHand, Card[] cardArray) {
		return chkNumbers(currentPot, playerHand, cardArray);
	}

	// Check for specials
	private boolean compareHandSpecial(int currentPot, int[] playerHand, Card[] cardArray) {
		return chkSpecial(currentPot, playerHand, cardArray);
	}

	// Check for Wilds
	private boolean compareHandWild(int currentPot, int[] playerHand, Card[] cardArray) {
		return chkWild(currentPot, playerHand, cardArray);
	}

	// Check for Wilds
	private boolean compareHandPlayWild(int[] playerHand, Card[] cardArray) {
		return plWild(playerHand, cardArray);
	}

	// Check for numbers
	private boolean chkNumbers(int currentPot, int[] playerHand, Card[] cardArray) {
		String[] cp = cardArray[currentPot].details();

		for (int x : playerHand) {
			String[] ph = cardArray[x].details();
			// Check if same type
			if (cp[0].equals(ph[0]) && !cp[0].equals("special") && !ph[0].equals("special")) {
				// Check if same color
				if (cp[1].equals(ph[1])) {
					cardToPlay = x;
					return true;
				} else {
					// Check if same number
					if (cp[2].equals(ph[2])) {
						cardToPlay = x;
						return true;
					} else {
						return false;
					}
				}
			} else {
				return false;
			}
		}
		return false;
	}

	// Check for specials cards
	private boolean chkSpecial(int currentPot, int[] playerHand, Card[] cardArray) {
		String[] cp = cardArray[currentPot].details();

		for (int x : playerHand) {
			String[] ph = cardArray[x].details();
			// Check if same type
			if (cp[0].equals(ph[0]) && cp[0].equals("special") && ph[0].equals("special")) {
				// Check if same color
				if (cp[1].equals(ph[1]) && !ph[2].equals("wld") && !ph[2].equals("wdr4")) {
					cardToPlay = x;
					return true;
				} else {
					// Check if same number
					if (cp[2].equals(ph[2]) && !ph[2].equals("wld") && !ph[2].equals("wdr4")) {
						cardToPlay = x;
						return true;
					} else {
						return false;
					}
				}
			} else {
				return false;
			}
		}
		return false;
	}

	// Check wild card in pot
	private boolean chkWild(int currentPot, int[] playerHand, Card[] cardArray) {
		String[] cp = cardArray[currentPot].details();

		for (int x : playerHand) {
			String[] ph = cardArray[x].details();
			// Check if same type
			if (cp[0].equals("special")) {

				// Check if wild
				if (cp[2].equals("wld") || cp[2].equals("wdr4")) {

					// if matching color
					if (cp[1].equals(ph[1])) {
						cardToPlay = x;
						return true;
					}
				}

			}
		}
		return false;
	}

	// Check hand for wild card
	private boolean plWild(int[] playerHand, Card[] cardArray) {

		for (int x : playerHand) {
			String[] ph = cardArray[x].details();
			// Check if same type
			if (ph[0].equals("special")) {
				// Check if wild
				if (ph[2].equals("wld") || ph[2].equals("wdr4")) {

					// Set random color
					String[] colors = { "red", "blue", "green", "yellow" };
					int rndColor = (int) (Math.random() * 3.5);

					cardArray[x].setColor(colors[rndColor]);
					cardToPlay = x;
					return true;
				}
			}
		}
		return false;
	}

	/*
	 * // Check for special private boolean chkSpecial(String[] details1, String[]
	 * details2) {
	 * 
	 * // Check if same type if (details1[0].equals(details2[0])) { // Check if same
	 * color if (details1[1].equals(details2[1])) { return true; } else { // Check
	 * if same number if (details1[2].equals(details2[2])) { return true; } else {
	 * return false; } } } else { return false; }
	 * 
	 * }
	 */

	// Tell player to draw 4 and skip player
	private boolean pd4(String[] details) {
		if (details[2].equals("wdr4")) {
			return true;
		}
		return false;
	}

	// Tell player to draw 2 and skip player
	private boolean pd2(String[] details) {
		if (details[2].equals("dr2")) {
			return true;
		}
		return false;
	}

	// Reverse order of players
	private boolean pRev(String[] details) {
		if (details[2].equals("rvs")) {
			return true;
		}
		return false;
	}

	// Skip player
	private boolean pSkip(String[] details) {
		if (details[2].equals("skp")) {
			return true;
		}
		return false;
	}

	// Play cards
	private void playCard(Deck gDeck, Player[] playerArray, int cardToPlay) {
		// submit card to pot
		gDeck.recieveCard(cardToPlay);
		// send to player and have player remove card from hand
		playerArray[currentTurn].rmCard(cardToPlay);
	}

	// Check if any players have uno
	private boolean unoCheck(int[] playerHand) {
		if (playerHand.length == 1) {
			return true;
		} else {
			return false;
		}

	}

	// Check if any players have won
	private boolean winner(int[] playerHand) {
		if (playerHand.length == 0) {
			return true;
		} else {
			return false;
		}
	}
}
