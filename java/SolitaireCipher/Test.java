import java.util.Arrays;


public class Test {
    public static void main(String[] args) {/* 
        // Step 1: Create the deck
        Deck deck = new Deck(5, 2); // 5 cards per suit, 2 suits (clubs and diamonds)

        // Print the deck after initialization
        System.out.println("Deck after initialization:");
        printDeck(deck);

        // Step 2: Seed the random generator
        Deck.gen.setSeed(10);

        // Step 3: Shuffle the deck
        deck.shuffle();

        // Print the deck after shuffling
        System.out.println("Deck after shuffling:");
        printDeck(deck);

        // Step 4: Create the Solitaire Cipher
        SolitaireCipher cipher = new SolitaireCipher(deck);

        // Step 5: Encode the message
        String encodedMessage = cipher.encode("Is that you, Bob?");
        System.out.println("Encoded Message: " + encodedMessage); // Expected: MWIKDVZCKSFP

        // Step 6: Verify the keystream (optional, for debugging)
        int[] keystream = cipher.getKeystream(12);
        System.out.print("Keystream: ");
        for (int value : keystream) {
            System.out.print(value + " ");
        }
        System.out.println(); // Expected: 4 4 15 3 3 2 1 14 16 17 17 14

        // Step 7: Decode the message
        String decodedMessage = cipher.decode(encodedMessage);
        System.out.println("Decoded Message: " + decodedMessage); // Expected: ISTHATYOUBOB
    }

    // Helper method to print the deck
    private static void printDeck(Deck deck) {
        Deck.Card current = deck.head;
        do {
            System.out.print(current + " ");
            current = current.next;
        } while (current != deck.head);
        System.out.println();
*/ 
Deck deckaroo = new Deck(5,2);
deckaroo.printDeck();
//deckaroo.verifyDeckStructure();
Deck.gen.setSeed(10);
deckaroo.shuffle();

deckaroo.printDeck();
//deckaroo.verifyDeckStructure();
deckaroo.moveCard(deckaroo.locateJoker("black"), 2);deckaroo.moveCard(deckaroo.locateJoker("red"), 1);
deckaroo.printDeck();
//deckaroo.verifyDeckStructure();
deckaroo.tripleCut(deckaroo.locateJoker("black"), deckaroo.locateJoker("red"));
deckaroo.printDeck();
//deckaroo.verifyDeckStructure();
deckaroo.countCut();
deckaroo.printDeck();
//deckaroo.verifyDeckStructure();
// Step 3: Generate full keystream of 12 values
SolitaireCipher cipher = new SolitaireCipher(deckaroo);
int[] keystream = cipher.getKeystream(12);
System.out.println("Generated Keystream: " + Arrays.toString(keystream));

}

}
