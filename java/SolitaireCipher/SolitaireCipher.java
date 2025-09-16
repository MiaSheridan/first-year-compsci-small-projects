public class SolitaireCipher {
	public Deck key;

	public SolitaireCipher (Deck key) {
		this.key = new Deck(key); // deep copy of the deck
	}

	/* 
	 * TODO: Generates a keystream of the given size
	 */
	public int[] getKeystream(int size) {
		/**** ADD CODE HERE ****/
		int[] keystream = new int[size];
		for (int i = 0; i < size; i++) {
			keystream[i] = key.generateNextKeystreamValue();
		}
		return keystream;
	}

	/* 
	 * TODO: Encodes the input message using the algorithm described in the pdf.
	 */
	public String encode(String msg) {
		/**** ADD CODE HERE ****/
		String modify1 = msg.toUpperCase();
		String modify2 = modify1.replaceAll("\\s", "").replaceAll("[^a-zA-Z0-9\\s]", "");

		int [] keystream = getKeystream(modify2.length());

		StringBuilder cipherText = new StringBuilder();
		for (int i = 0; i < modify2.length(); i++) {
			char currentChar = modify2.charAt(i);
			int shift = keystream[i];
			char base = 'A';
			char encrypted = (char) ((currentChar - base + shift) % 26 + base);
			cipherText.append(encrypted);
		}
		return cipherText.toString();
	}

	/* 
	 * TODO: Decodes the input message using the algorithm described in the pdf.
	 */
	private char characterShift(char input, int n) {
		char output = (char) (input - n);
		if (input - n > 'Z') {
			output = characterShift('A', n - ('Z' - input - 1));
		}
		if (input - n < 'A') {
			output = characterShift('Z', n - (input - 'A' + 1));
		}
		return output;
	}
	public String decode(String msg) {
		/**** ADD CODE HERE ****/
		int [] keystream = getKeystream(msg.length());
		StringBuilder decodedText = new StringBuilder();

		for (int i = 0; i < msg.length(); i++) {
			char currentChar = msg.charAt(i);
			int shift = keystream[i];

				decodedText.append(characterShift(currentChar, shift));
		}
		return decodedText.toString();
	}

}
