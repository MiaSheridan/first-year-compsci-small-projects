import java.util.Random;

public class Deck {
	public static String[] suitsInOrder = {"clubs", "diamonds", "hearts", "spades"};
	public static Random gen = new Random();

	public int numOfCards; // contains the total number of cards in the deck
	public Card head; // contains a pointer to the card on the top of the deck

	/* 
	 * TODO: Initializes a Deck object using the inputs provided
	 */
	public Deck(int numOfCardsPerSuit, int numOfSuits) {
		/**** ADD CODE HERE ****/
		if (numOfCardsPerSuit < 1 || numOfCardsPerSuit > 13) throw new IllegalArgumentException("Incorrect number of cards.");
		if (numOfSuits < 1 || numOfSuits > suitsInOrder.length) throw new IllegalArgumentException("Incorrect number of suits.");

		//String[] suits = {"C", "D", "H", "S"};
		for (int i = 0; i < numOfSuits; i++) {
			for (int j = 1; j <= numOfCardsPerSuit; j++) {
				addCard(new PlayingCard(suitsInOrder[i],j));
			}
		}
		addCard(new Joker("red"));
		addCard(new Joker("black"));

		// Debug: Print the deck to verify jokers are added
		System.out.println("Deck after adding jokers:");
		printDeck();

		numOfCards = numOfCardsPerSuit * numOfSuits + 2;

	}

	/* 
	 * TODO: Implements a copy constructor for Deck using Card.getCopy().
	 * This method runs in O(n), where n is the number of cards in d.
	 */
	public Deck(Deck d) {
		/**** ADD CODE HERE ****/

		Deck.Card node = d.head;
		while (node != null) {
			Deck.Card c = node.getCopy();
			this.addCard(c);
			node = node.next;
			if (node == d.head) {
				break;
			}
		}
	}
	
	/*
	 * For testing purposes we need a default constructor.
	 */
	public Deck() {}
	//newnmeyhod
	public void printDeck() {
		if (head == null) {
			System.out.println("Deck is empty");
			return;
		}
		Card current = head;
		do {
			
			System.out.print(current + " ");

			current = current.next;
		} while (current != head);
		System.out.println();
	}

	/* 
	 * TODO: Adds the specified card at the bottom of the deck. This 
	 * method runs in $O(1)$. 
	 */
	public void addCard(Card c) {
		/**** ADD CODE HERE ****/
		numOfCards++;
		if (head == null) {
			head = c;
			head.next = head;
			head.prev = head;
		}
		else {
			Card last = head.prev;
			last.next = c;
			c.prev = last;
			c.next = head;
			head.prev = c;
		}
	}

	/*
	 * TODO: Shuffles the deck using the algorithm described in the pdf. 
	 * This method runs in O(n) and uses O(n) space, where n is the total 
	 * number of cards in the deck.
	 */
	public void shuffle() { //this works because the redjoker is present after shuffling
		/**** ADD CODE HERE ****/
		Card[] deckArray = new Card[numOfCards];
		Card node = this.head;
		for (int i = 0; i < numOfCards; i++) {
			deckArray[i] = node;
			node = node.next;
	
			if (node == head) {
				break;
			}
		}
	
		//System.out.println("Deck before shuffle:");
		for (Card card : deckArray) {
			System.out.print(card + " ");
		}
		System.out.println();
	
		for (int i = numOfCards - 1; i >= 1; i--) {
			int j = gen.nextInt(i + 1);
			Card temporary = deckArray[i];
			deckArray[i] = deckArray[j];
			deckArray[j] = temporary;
		}
	
		//System.out.println("Deck after shuffle:");
		for (Card card : deckArray) {
			System.out.print(card + " ");
		}
		System.out.println();
	
		for (int k = 0; k < numOfCards; k++) {
			deckArray[k].next = deckArray[(k + 1) % numOfCards];
			deckArray[k].prev = deckArray[(k + numOfCards - 1) % numOfCards];
			head = deckArray[0];
		}
		/*Card[] deckArray = new Card[numOfCards];
		Card node = this.head;
		for (int i = 0; i < numOfCards; i++) {
			deckArray[i] = node;
			node = node.next;

			if (node == head) {
				break;
			}
		}
		for (int i = numOfCards - 1; i >= 1; i--) {
				int j = gen.nextInt(i + 1);
				Card temporary = deckArray[i];
				deckArray[i] = deckArray[j];
				deckArray[j] = temporary;
		}
		for (int k = 0; k < numOfCards; k++) {
			deckArray[k].next = deckArray[(k + 1)%numOfCards];
			deckArray[k].prev = deckArray[(k + numOfCards - 1)%numOfCards];
			head = deckArray[0];
		}*/

	}

	/*
	 * TODO: Returns a reference to the joker with the specified color in 
	 * the deck. This method runs in O(n), where n is the total number of 
	 * cards in the deck. 
	 */
	public Joker locateJoker(String color) {
		/**** ADD CODE HERE ****/
		if (head == null) {
			System.out.println("Deck is empty. Cannot locate joker."); // Debug: Print if deck is empty
			return null;
		}
	
		Card node = head;
		int cardsChecked = 0; // Counter to ensure we don't loop indefinitely
	
		do {
			System.out.println("Checking card: " + node); // Debug: Print the current card
			if (node instanceof Joker) {
				System.out.println("Found joker: " + node); // Debug: Print the found joker
				if (color.equals(((Joker) node).getColor())) {
					System.out.println("Joker found with matching color: " + node); // Debug: Print the matching joker
					return (Joker) node;
				}
			}
			node = node.next;
			cardsChecked++;
	
			// Break if we've checked all cards (to prevent infinite loops)
			if (cardsChecked > numOfCards) {
				System.out.println("Reached end of deck without finding joker."); // Debug: Print if we've checked all cards
				break;
			}
		} while (node != head); // Loop until we've checked all cards
	
		System.out.println("No joker found with color: " + color); // Debug: Print if no joker is found
		return null; // If no joker of the specified color is found


		/* 
		if (head == null) {
			System.out.println("Deck is empty. Cannot locate joker."); // Debug: Print if deck is empty
			return null;
		}
		Card node = head;
    	for (int i = 0; i < numOfCards; i++) {
			System.out.println("Checking card: " + node); // Debug: Print the current card
        	if (node instanceof Joker) {
				System.out.println("Found joker: " + node); // Debug: Print the found joker
            	if (color.equals(((Joker) node).getColor())) {
					System.out.println("Joker found with matching color: " + node); // Debug: Print the matching joker
                	return (Joker) node;
            	}
        	}
        	node = node.next;

			// Break if we've looped back to the head (circular deck)
			if (node == head) {
				break;
			}
    	}
		System.out.println("No joker found with color: " + color); // Debug: Print if no joker is found
    	return null; // If no joker of the specified color is found */
	

		/*
		Card node = head;
		for (int i = 0; i < numOfCards; i++) {
			int cardValue = node.getValue();
			if (cardValue == numOfCards - 1 && node instanceof Joker) {

				if (color.equals(((Deck.Joker) node).getColor())) {
					return (Deck.Joker) node;
				}
			}
			node = node.next;
		}
		return null;
		*/
	}

	/*
	 * TODO: Moved the specified Card, p positions down the deck. You can 
	 * assume that the input Card does belong to the deck (hence the deck is
	 * not empty). This method runs in O(p).
	 */
	public void verifyDeckStructure() {
		if (head == null) {
			System.out.println("Deck is empty.");
			return;
		}
	
		System.out.println("Verifying deck structure...");
		Card node = head;
		int cardsChecked = 0;
	
		do {
			System.out.println("Card: " + node + ", Next: " + node.next + ", Prev: " + node.prev);
			node = node.next;
			cardsChecked++;
	
			// Break if we've checked all cards (to prevent infinite loops)
			if (cardsChecked > numOfCards) {
				System.out.println("Deck structure is corrupted: Infinite loop detected.");
				break;
			}
		} while (node != head);
	
		if (cardsChecked == numOfCards) {
			System.out.println("Deck structure is valid.");
		} else {
			System.out.println("Deck structure is corrupted: Missing or extra cards.");
		}
	}
	public void moveCard(Card c, int p) {
		/**** ADD CODE HERE ****/

		Card node = c;
		int position = p;

			while (position != 0) {
				node = node.next;
				position--;
			}


		Card prev = c.prev; //saves reference to prev node of c
		prev.next = c.next; //updates 'next' of node before c to skip over c
		c.next.prev = prev; //updates 'prev' to skip over c
		c.next = node.next;
		node.next.prev = c;
		node.next = c;
		c.prev = node;

		if (c == head) {
			head = c;
		}
	}

	/*
	 * TODO: Performs a triple cut on the deck using the two input cards. You 
	 * can assume that the input cards belong to the deck and the first one is 
	 * nearest to the top of the deck. This method runs in O(1)
	 */
	public void tripleCut(Card firstCard, Card secondCard) {
		/**** ADD CODE HERE ****/
		

		if (head == null || firstCard == null || secondCard == null || firstCard == secondCard) {
			return;
		}
	
		// Ensure firstCard appears before secondCard in the deck
		Card current = head;
		boolean foundFirst = false, foundSecond = false;
		while (current != null) {
			if (current == firstCard) foundFirst = true;
			if (current == secondCard) {
				foundSecond = true;
				break;
			}
			current = current.next;
		}
		
		// Swap if necessary
		if (!foundFirst || !foundSecond) return; // Safety check
		if (!foundFirst) {
			Card temp = firstCard;
			firstCard = secondCard;
			secondCard = temp;
		}
	
		// Identify parts of the deck
		Card part1head = head;
		Card part1tail = firstCard.prev;
		Card part2head = firstCard;
		Card part2tail = secondCard;
		Card part3head = secondCard.next;
		Card part3tail = head.prev;
	
		// Handle cases where firstCard or secondCard are at the extremes
		if (part1tail != null) part1tail.next = null;
		if (part3head != null) part3head.prev = null;
		
		// Reconnect in triple-cut order: part3 -> part2 -> part1
		if (part3head != null) {
			part3tail.next = part2head;
			part2head.prev = part3tail;
		} else {
			part2head.prev = null;
		}
	
		part2tail.next = part1head;
		if (part1head != null) part1head.prev = part2tail;
	
		// Update head to the new first section (part3 if exists, otherwise part2)
		head = (part3head != null) ? part3head : part2head;


		/* 
		if (head == null || firstCard == null || secondCard == null || firstCard == secondCard) {
			return;
		}
	
		// Debug: Print the deck before the triple cut
		System.out.println("Before triple cut:");
		printDeck();
		verifyDeckStructure();
	
		// Ensure firstCard comes before secondCard in the deck
		if (firstCard.next == secondCard) {
			Card temp = firstCard;
			firstCard = secondCard;
			secondCard = temp;
		}
	
		// Identify the three sections of the deck:
		// 1. Cards before firstCard
		// 2. Cards between firstCard and secondCard (inclusive)
		// 3. Cards after secondCard
	
		Card part1head = head;
		Card part1tail = firstCard.prev;
		Card part2head = firstCard;
		Card part2tail = secondCard;
		Card part3head = secondCard.next;
		Card part3tail = head.prev;
	
		// Debug: Print the identified sections
		System.out.println("Part 1: " + part1head + " to " + part1tail);
		System.out.println("Part 2: " + part2head + " to " + part2tail);
		System.out.println("Part 3: " + part3head + " to " + part3tail);
	
		// Reconnect the deck based on the sections
		if (secondCard.next == head) {
			// Case 1: Part 3 is empty (secondCard is the last card)
			part1head.prev = part2tail;
			part2tail.next = part1head;
	
			part2head.prev = part1tail;
			part1tail.next = part2head;
	
			head = part2head;
		} else if (firstCard == head) {
			// Case 2: Part 1 is empty (firstCard is the first card)
			part3head.prev = part2tail;
			part2tail.next = part3head;
	
			part2head.prev = part3tail;
			part3tail.next = part2head;
	
			head = part3head;
		} else {
			// Case 3: General case (all three parts are non-empty)
			part3head.prev = part1tail;
			part1tail.next = part3head;
	
			part3tail.next = part2head;
			part2head.prev = part3tail;
	
			part2tail.next = part1head;
			part1head.prev = part2tail;
	
			head = part3head;
		}
		
	
		// Debug: Print the deck after the triple cut
		System.out.println("After triple cut:");
		printDeck();
		verifyDeckStructure();
		*/

		/*
		//edge cases

		Card last = head.prev;
		if (firstCard == head || secondCard ==last) {
			Card newHead = head;
			if (firstCard == newHead) {
				head = secondCard.next;
			}
			if (secondCard == newHead.prev) {
				head = firstCard;
			}
		}
		else {
			Card beforeFirst = firstCard.prev;
			Card initialHead = head;
			Card afterSecond = secondCard.next;
			Card initialTail = head.prev;

			afterSecond.prev = beforeFirst;
			beforeFirst.next = afterSecond;

			initialTail.next = firstCard;
			firstCard.prev = initialTail;

			secondCard.next = initialHead;
			initialHead.prev = secondCard;

			head = afterSecond;
		}*/
	}

	/*
	 * TODO: Performs a count cut on the deck. Note that if the value of the 
	 * bottom card is equal to a multiple of the number of cards in the deck, 
	 * then the method should not do anything. This method runs in O(n).
	 */
	public void countCut() {
		/**** ADD CODE HERE ****/
		
 
		Card last = head.prev;
		Card node = head;
		int value = head.prev.getValue() % numOfCards;
		if (value >= numOfCards - 1 || value == 0) return;

		for (int i = 1; i < value; i++) {
			node = node.next;
		}

		Card newHead = node.next;
		Card top = last.prev;
		node.next = last;
		last.prev = node;
		top.next = head;
		head.prev = top;
		newHead.prev = last;
		last.next = newHead;
		head = newHead;
		


	}

	/*
	 * TODO: Returns the card that can be found by looking at the value of the 
	 * card on the top of the deck, and counting down that many cards. If the 
	 * card found is a Joker, then the method returns null, otherwise it returns
	 * the Card found. This method runs in O(n).
	 */
	public Card lookUpCard() {
		/**** ADD CODE HERE ****/
		int value = head.getValue();
		Card node = head;
		while (value != 0) {
			node = node.next;
			value--;
		}
		if (node instanceof Joker) {
			return null;
		}
		return node;
	}

	
	/* TODO: Uses the Solitaire algorithm to generate one value for the keystream 
	 * using this deck. This method runs in O(n).
	 */
	/*public int generateNextKeystreamValue() {
		/**** ADD CODE HERE ****/
		public int generateNextKeystreamValue() {
			System.out.println("Starting keystream generation...");
    		verifyDeckStructure(); // Debug: Verify deck structure before starting
			// Step 1: Move the red joker one card down
			Joker redJoker = locateJoker("red");
			if (redJoker == null) {
				throw new IllegalStateException("Red joker not found in the deck.");
			}
			System.out.println("Moving red joker: " + redJoker); // Debug: Print the red joker being moved
			moveCard(redJoker, 1);
			verifyDeckStructure(); // Debug: Verify deck structure after moving red joker
		
			// Step 2: Move the black joker two cards down
			Joker blackJoker = locateJoker("black");
			if (blackJoker == null) {
				throw new IllegalStateException("Black joker not found in the deck.");
			}
			System.out.println("Moving black joker: " + blackJoker); // Debug: Print the black joker being moved
			moveCard(blackJoker, 2);
			verifyDeckStructure(); // Debug: Verify deck structure after moving black joker
		
			// Step 3: Perform a triple cut around the two jokers
			// Step 3: Perform a triple cut around the two jokers
			System.out.println("Performing triple cut between: " + redJoker + " and " + blackJoker); // Debug: Print the jokers for the triple cut
			Card firstJoker = redJoker;
			Card secondJoker = blackJoker;
			if (firstJoker.next == secondJoker) {
				firstJoker = blackJoker;
				secondJoker = redJoker;
			}
			tripleCut(firstJoker, secondJoker);
			verifyDeckStructure(); // Debug: Verify deck structure after triple cut
			// Step 4: Perform a count cut based on the value of the bottom card
			System.out.println("Performing count cut."); // Debug: Print the count cut
    		countCut();
    		verifyDeckStructure(); // Debug: Verify deck structure after count cut
		
			// Step 5: Find the keystream card based on the value of the top card
			System.out.println("Looking up keystream card."); // Debug: Print the keystream card lookup

			Card keystreamCard = lookUpCard();
			if (keystreamCard == null || keystreamCard instanceof Joker) {
				// If the card is a Joker, try again
				return generateNextKeystreamValue();
			}
			System.out.println("Keystream card found: " + keystreamCard); // Debug: Print the keystream card
			return keystreamCard.getValue();
		}


		/*
		Joker redJoker = locateJoker("red");
		moveCard(redJoker, 1);
		Joker blackJoker = locateJoker("black");
		moveCard(blackJoker, 2);

		Card top = head;
		while (!(top instanceof Joker)) top = top.next;
		if (top == redJoker) {
			tripleCut(redJoker, blackJoker);
		}
		else {
			tripleCut(blackJoker, redJoker);
		}
		countCut();
		Card keystreamCard = lookUpCard();
		if (keystreamCard == null) {
			return generateNextKeystreamValue();
		}
		return keystreamCard.getValue();
	}
		*/


	public abstract class Card { 
		public Card next;
		public Card prev;

		public abstract Card getCopy();
		public abstract int getValue();

	}
	

	public class PlayingCard extends Card {
		public String suit;
		public int rank;

		public PlayingCard(String s, int r) {
			this.suit = s.toLowerCase();
			this.rank = r;
		}

		public String toString() {
			String info = "";
			if (this.rank == 1) {
				//info += "Ace";
				info += "A";
			} else if (this.rank > 10) {
				String[] cards = {"Jack", "Queen", "King"};
				//info += cards[this.rank - 11];
				info += cards[this.rank - 11].charAt(0);
			} else {
				info += this.rank;
			}
			//info += " of " + this.suit;
			info = (info + this.suit.charAt(0)).toUpperCase();
			return info;
		}

		public PlayingCard getCopy() {
			return new PlayingCard(this.suit, this.rank);   
		}

		public int getValue() {
			int i;
			for (i = 0; i < suitsInOrder.length; i++) {
				if (this.suit.equals(suitsInOrder[i]))
					break;
			}

			return this.rank + 13*i;
		}

	}

	public class Joker extends Card{
		public String redOrBlack;

		public Joker(String c) {
			if (!c.equalsIgnoreCase("red") && !c.equalsIgnoreCase("black")) 
				throw new IllegalArgumentException("Jokers can only be red or black"); 

			this.redOrBlack = c.toLowerCase();
		}

		public String toString() {
			//return this.redOrBlack + " Joker";
			return (this.redOrBlack.charAt(0) + "J").toUpperCase();
		}

		public Joker getCopy() {
			return new Joker(this.redOrBlack);
		}

		public int getValue() {
			return numOfCards - 1;
		}

		public String getColor() {
			return this.redOrBlack;
		}
	}

}
