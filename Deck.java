package unoSimulator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

/**************
 * Deck Class *
 **************/
class Deck {
	// Variables
	private List<Integer> pile = new ArrayList<>(); // Cards in deck
	private List<Integer> pot = new ArrayList<>(); // Cards in pot

	private int potCount = 0;
	private int counter = 0;

	// Set Deck
	private void set(Card[] cardArray) {

	}

	// Give card to player
	public int sendCard() {
		// find last
		int last = pile.get(pile.size()-1);
		// remove last
		pile.remove(pile.size()-1);
		// return last
		return last;
	}

	// Put card in pot
	public void recieveCard(int x) {
		// Add card to pot
		pot.add(x);
	}

	public int checkPot() {
		// Get last card in pot
		int last = pot.get(pot.size() - 1);
		// return last card in pot
		return last;
	}

	// Implementing Fisher–Yates shuffle. Actually The Durstenfeld shuffle
	public void shuffle() {
		
		for (int i = 0; i < 108; i++) {
			pile.add(i);
		}
		
		// If running on Java 6 or older, use `new Random()` on RHS here
		Random rnd = ThreadLocalRandom.current();
		for (int i = 0; i < pile.size() - 1; i++) {
			int index = rnd.nextInt(i + 1);
			// Simple swap
			int a = pile.get(index);
			pile.set(index, pile.get(i));
			pile.set(i, a);
		}
		//System.out.println(Arrays.toString(pile));
	}

	// Constructor
	public Deck(Card[] cardArray) {
		run(cardArray);
		set(cardArray);
		shuffle();

	}

	public void run(Card[] cardArray) {

		String[] types = { "number", "special" };
		String[] colors = { "red", "blue", "green", "yellow" };
		String[] cards = { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "dr2", "skp", "rvs", "wld", "wdr4" };
		// int[] cardCount = { 4, 12, 20, 28, 36, 44, 52, 60, 68, 76, 84, 92, 100, 104,
		// 108 };

		for (int i = 0; i < cardArray.length; i++) {

			cardArray[i] = new Card();

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
