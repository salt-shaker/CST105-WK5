package unoSimulator;

/* ************************ */
/* *** Uno Simulator ****** */
/* *** By: Sashae Owens *** */
/* ************************ */

public class UnoSimulator {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		/**********************
		 * Card Array Builder *
		 **********************/
		// Variables
		Card[] cardArray = new Card[108];

		// Variables
		Player[] playerArray = new Player[4];

		// Make Cards in Deck, Create deck, and Shuffle
		Deck gDeck = new Deck(cardArray);

		// Make Players
		new MakePlayers().run(playerArray);

		// Create Deck and Shuffle
		Game uno = new Game(500);
		uno.getPlayerOrder(playerArray);
		uno.dealCards(playerArray, gDeck);

		/*
		 * // Show player hand for(int y = 0; y < playerArray.length; y++) {
		 * playerArray[y].showHand(); }
		 */

		/*************
		 * Main Loop *
		 *************/
		while (true) {

			// Call next turn
			boolean winner = uno.newTurn(playerArray, gDeck, cardArray);

			// If winner true, break loop
			if (winner) {
				break;
			}

		}

	} // EOM
}// EOC

/*
 * Player Object To test probability use a random number and threshold
 * 
 * eg.
 * 
 * // Set the initial probability // Then roll on each turn // if roll is more
 * than probability, then player will not do that action
 * 
 * int tLoop = 20; int myValue;
 * 
 * for (int i = 0; i < tLoop; i++) { myValue = (int) (Math.random()*10)+1;
 * System.out.println(myValue); }
 * 
 */

/*
 * Uno Rules http://play-k.kaserver5.org/Uno.html
 * 
 * Method in a method
 * https://www.geeksforgeeks.org/method-within-method-in-java/
 * 
 * Random shuffling of an array
 * https://stackoverflow.com/questions/1519736/random-shuffling-of-an-array
 * 
 * Java with Us http://www.javawithus.com/tutorial/array-of-objects
 * 
 * Method in a method in java
 * https://www.geeksforgeeks.org/method-within-method-in-java/
 * 
 * Different Ways to create objects in Java
 * https://www.geeksforgeeks.org/different-ways-create-objects-java/
 * 
 * 
 * 
 */
