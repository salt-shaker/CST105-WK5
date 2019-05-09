package unoSimulator;

import java.io.IOException;

/* ************************ */
/* *** Uno Simulator ****** */
/* *** By: Sashae Owens *** */
/* ************************ */

public class UnoSimulator {

	public static void main(String[] args) {

		// Create new logger
		Logger logger = new Logger();

		try {
			logger.logWritter("Game Start ");
		} catch (IOException e) {
			System.out.println(e);
		}

		// Create Cards
		Card[] cardArray = new Card[108];

		// Create Players
		Player[] playerArray = new Player[4];

		// Create Deck
		Deck gDeck = new Deck(cardArray);

		// Create Turn
		Turn playerTurn = new Turn();

		// Assign player names and set turn
		new MakePlayers().run(playerArray, playerTurn);

		// Create Game
		Game uno = new Game(500, playerArray, gDeck, playerTurn);

		/*************
		 * Main Loop *
		 *************/
		while (true) {

			// Call next turn
			boolean winner = uno.newTurn(playerArray, gDeck, cardArray, playerTurn);

			// If winner true, break loop
			if (winner) {
				System.out.println("We have a winner");
				break;
			}

		}

	} // EOM
}// EOC

/*
 * 
 * Big Issue:
 * After switching over to linked list to store arrays i couldn't figure out why
 * the player order was off and why the loops didn't seem to match i forgot to
 * switch from using the playerArray order to using the playerTurn order.
 * 
 * Ended up i was tracking the player turn independently of the play objects the
 * player object contained its own turn counter the turn object also had a turn
 * counter this was causing a mismatch.
 * 
 * What i learned
 * 
 * Big projects should be planned throughly
 * changing formats half way through is a bad idea
 * classes help organize large code
 * returning values is awesome
 * method signatures are awesome
 * extending a class is awesome
 * Seriously, plan and make it work on paper
 * take notes, make comments
 * take notes, make comments even more
 * Make blocks of code easy to identify
 * 
 */