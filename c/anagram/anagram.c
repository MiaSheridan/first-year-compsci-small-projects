//Name: Mia Valderrama-Lopez
// ID: 261239153

#include <stdio.h>
#include <stdlib.h>
#include <string.h>

#define NUM_CHARS 256 // number of possible ASCII characters


// check if two words are anagrams
int are_anagrams(const char *word1, const char *word2) {
    int count1[NUM_CHARS] = {0};
    int count2[NUM_CHARS] = {0};
    int i;

// if lengths are different they not anagrams
    if (strlen(word1) != strlen(word2)) {
        return 0;
    }

    // count frequency of each character in both words
    for (i = 0; word1[i] && word2[i]; i++) {
        count1[(unsigned char)word1[i]]++;
        count2[(unsigned char)word2[i]]++;
    }

// compare character counts
    for (i = 0; i < NUM_CHARS; i++) {
        if (count1[i] != count2[i]) {
            return 0;
        }
    }

    return 1;
}

//did a main method hoping that's okay
int main(int argc, char *argv[]) {
    // check if exactly two arguments are provided
    if (argc != 3) {
        printf("Usage: ./anagram WORD1 WORD2\n");
        return -1;
    }

    // get the two words from command line arguments
    char *word1 = argv[1];
    char *word2 = argv[2];

    // checking if they are anagrams
    if (are_anagrams(word1, word2)) {
        printf("Anagram\n");
        return 0;
    } else {
        printf("Not an anagram\n");
        return 1;
    }
}

