package unoSimulator;

import java.util.Arrays;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

//Class
class MakePlayers {

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
	public void run(Player[] playerArray) {
		String[] names = { "Jeff", "Greg", "Scott", "Randy" };
		int[] playOrder = new int[names.length];

		// Set initial order
		for (int x = 0; x < names.length; x++) {
			playOrder[x] = x;
		}

		// Shuffle names and orders
		shuffle(names);
		shuffle(playOrder);

		// Create Players
		for (int i = 0; i < names.length; i++) {
			playerArray[i] = new Player(names[i], playOrder[i]);
		}

	}

}
