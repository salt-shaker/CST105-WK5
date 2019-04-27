package unoSimulator;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

/* ************************ */
/* *** Uno Simulator ****** */
/* *** By: Sashae Owens *** */
/* ************************ */

public class UnoSimulator {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		/***************
		 * Card Class **
		 ***************/
		class card {
			// Variables
			private String type;
			private String color;
			private String value;

			// Constructor
			public card() {
			}

			// Set card type, color, and value
			public void setCard(String xType, String xColor, String xValue) {
				type = xType;
				color = xColor;
				value = xValue;
			}

		}

		/**************
		 * Deck Class *
		 **************/
		class deck {
			// Variables
			private int[] pile = new int[108]; // Cards in deck
			private int[] pot = new int[108]; // Cards in pot
			private int pileCount = pile.length-1;
			private int potCount = 0;
			private int counter = 0;

			// Set Deck
			public void set() {
				for (int i = 0; i < pile.length; i++) {
					pile[i] = counter;
					counter++;
				}
				// System.out.println(Arrays.toString(pile));
			}

			public int sendCard() {
				int x = pile[pileCount];
				pileCount--;
				return x;
			}

			public int recieveCard() {
				return counter;

			}

			// Implementing Fisher–Yates shuffle. Actually The Durstenfeld shuffle
			public void shuffle() {
				// If running on Java 6 or older, use `new Random()` on RHS here
				Random rnd = ThreadLocalRandom.current();
				for (int i = pile.length - 1; i > 0; i--) {
					int index = rnd.nextInt(i + 1);
					// Simple swap
					int a = pile[index];
					pile[index] = pile[i];
					pile[i] = a;
				}
				System.out.println(Arrays.toString(pile));
			}

			// Constructor
			public deck() {

			}

		}

		/****************
		 * Player Class *
		 ****************/
		class player {
			// Variables
			private String pName; // Player Name
			private int pPosition; // Player Position
			private int[] cHand = new int[108]; // Cards in hand
			private int cAmount = -1;

			// Player Stats
			private int pNumbers; // Probability to play numbers
			private int pSpecial; // Probability to play special
			private int pWild; // Probability to play wild's
			private int coUno; // Probability to call uno on others
			private int caUno; // Probability to say uno on last card

			// Constructor
			public player(String sName, int sPosition) {
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
				cHand[cAmount + 1] = addCard;
				cAmount++;
			}

			// Play card
			public void plCard() {

			}

			// Set probability between 5 and 10
			private int setStats() {
				return (int) (Math.random() * 5) + 5;
			}
			
			//Show Hand
			public void showHand() {
				System.out.println("Player " + pName + " has the following cards in their hand");
				for(int i = 0; i < cAmount+1; i++) {
					System.out.println(cHand[i]);	
				}
				
			}

		}

		/**********************
		 * Card Array Builder *
		 **********************/

		// Variables
		card[] cardArray = new card[108];

		// Class
		class makeDeck {
			public void run() {

				String[] types = { "number", "special" };
				String[] colors = { "red", "blue", "green", "yellow" };
				String[] cards = { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "dr2", "skp", "rvs", "wld",
						"wdr4" };
				// int[] cardCount = { 4, 12, 20, 28, 36, 44, 52, 60, 68, 76, 84, 92, 100, 104,
				// 108 };

				for (int i = 0; i < cardArray.length; i++) {

					cardArray[i] = new card();

					// Zeros
					if (i < 4) {
						cardArray[i].setCard(types[0], colors[i % 4], cards[0]);
					}
					// Ones
					if (4 <= i && i < 12) {
						cardArray[i].setCard(types[0], colors[i % 4], cards[1]);
					}
					// Twos
					if (12 <= i && i < 20) {
						cardArray[i].setCard(types[0], colors[i % 4], cards[2]);
					}
					// Threes
					if (20 <= i && i < 28) {
						cardArray[i].setCard(types[0], colors[i % 4], cards[3]);
					}
					// Fours
					if (28 <= i && i < 36) {
						cardArray[i].setCard(types[0], colors[i % 4], cards[4]);
					}
					// Fives
					if (36 <= i && i < 44) {
						cardArray[i].setCard(types[0], colors[i % 4], cards[5]);
					}
					// Sixes
					if (44 <= i && i < 52) {
						cardArray[i].setCard(types[0], colors[i % 4], cards[6]);
					}
					// Sevens
					if (52 <= i && i < 60) {
						cardArray[i].setCard(types[0], colors[i % 4], cards[7]);
					}
					// Eights
					if (60 <= i && i < 68) {
						cardArray[i].setCard(types[0], colors[i % 4], cards[8]);
					}
					// Nines
					if (68 <= i && i < 76) {
						cardArray[i].setCard(types[0], colors[i % 4], cards[9]);
					}
					// Draw 2
					if (76 <= i && i < 84) {
						cardArray[i].setCard(types[1], colors[i % 4], cards[10]);
					}
					// Skips
					if (84 <= i && i < 92) {
						cardArray[i].setCard(types[1], colors[i % 4], cards[11]);
					}
					// Reverse
					if (92 <= i && i < 100) {
						cardArray[i].setCard(types[1], colors[i % 4], cards[12]);
					}
					// Wild
					if (100 <= i && i < 104) {
						cardArray[i].setCard(types[1], colors[i % 4], cards[13]);
					}
					// Wild +4
					if (104 <= i && i < 108) {
						cardArray[i].setCard(types[1], colors[i % 4], cards[14]);
					}
				}
			}
		}

		// Variables
		player[] playerArray = new player[4];

		// Class
		class makePlayers {

			// Implementing Fisher–Yates shuffle. Actually The Durstenfeld shuffle
			private void shuffle(int[] ar) {
				// If running on Java 6 or older, use `new Random()` on RHS here
				Random rnd = ThreadLocalRandom.current();
				for (int i = ar.length - 1; i > 0; i--) {
					int index = rnd.nextInt(i + 1);
					// Simple swap
					int a = ar[index];
					ar[index] = ar[i];
					ar[i] = a;
				}
				System.out.println(Arrays.toString(ar));
			}

			// Implementing Fisher–Yates shuffle. Actually The Durstenfeld shuffle
			private void shuffle(String[] ar) {
				// If running on Java 6 or older, use `new Random()` on RHS here
				Random rnd = ThreadLocalRandom.current();
				for (int i = ar.length - 1; i > 0; i--) {
					int index = rnd.nextInt(i + 1);
					// Simple swap
					String a = ar[index];
					ar[index] = ar[i];
					ar[i] = a;
				}
				System.out.println(Arrays.toString(ar));
			}

			// Create Players
			public void run() {
				String[] names = { "Jeff", "Greg", "Scott", "Randy" };
				int[] playOrder = new int[names.length];

				// Set initial order
				for (int x = 0; x < names.length; x++) {
					playOrder[x] = x;
				}

				// Shuffle names and orders
				shuffle(names);
				shuffle(playOrder);

				// Create Platers
				for (int i = 0; i < names.length; i++) {

					playerArray[i] = new player(names[i], playOrder[i]);
				}

			}

		}

		/**************
		 * Game class *
		 **************/

		// Make Cards in Deck
		new makeDeck().run();

		// Create Deck and Shuffle
		deck gDeck = new deck();
		gDeck.set();
		gDeck.shuffle();

		// Make Players
		new makePlayers().run();
		
		// Game Class
		class game {

			// Track Game States
			// Maybe later

			// Player position
			private int[] pOrder = new int[4]; // Check length of players array once i set it up

			public void dealCards() {
				for (int i = 0; i < 7; i++) {
					for (int x = 0; x < playerArray.length; x++) {
						playerArray[x].drCard(gDeck.sendCard());
					}
				}
			}

			public void getCard() {

			}

			// Check players hand
			public void pHand() {

			}
			
			

			// Get current card from pot and if player has valid card to play
			private void checkPot() {

			}

			// Check if any players have uno
			private void unoCheck() {

			}

			// Check if any players have won
			private void winner() {

			}

			// Tell next player to draw 4 and skip that player
			private void pd4() {

			}

			// Tell next player to draw 2 and skip that player
			private void pd2() {

			}

			// Reverse order of players
			private void pRev() {

			}

			// Skip next player
			private void pSkip() {

			}
		}
		
		// Create Deck and Shuffle
		game newGame = new game();
		newGame.dealCards();
		
		for(int y = 0; y < playerArray.length; y++) {
			playerArray[y].showHand();
		}
		

		/*************
		 * Main Loop *
		 *************/
		boolean mainLoop = true;
		int counter = 0;

		while (mainLoop) {
			System.out.println("hello World");
			counter++;
			if (counter == 10)
				mainLoop = false;

		}

	} // EOM
}// EOC

