//Name: Mia Valderrama-Lopez
// ID: 261239153

#include <stdio.h>
#include <stdlib.h>

int main() {
	int a,b,c;
	char input[100]; // Buffer to store the input line
	char extra;
 	
	printf("Please input three numbers: ");
	// Read the entire input line
        fgets(input, sizeof(input), stdin);

	
	int num_inputs = sscanf(input, "%d %d %d %c", &a, &b, &c, &extra);

	// Check if exactly 3 inputs were provided
   	if (num_inputs != 3) {
		printf("Error: Invalid input\n");
        	return -1;
    	}

	// Check for invalid input
    	if (a == 0 || a < 0 || b < 0 || c < 0) {
        	printf("Error: Invalid input\n");
        	return -1;
    	}

	//Check if numbers are strictly increasing in order
	int is_increasing = (a < b) && (b < c);
	//Check if numbers are divisible by the first number
	int is_divisible = (b % a == 0) && (c % a == 0);

	//Determine results noww remember here no booleans, but 0 for false or 1 for true
	if (is_divisible && is_increasing) {
		printf("Divisible & Increasing\n");
		return 0;
	} else if (!is_divisible && is_increasing) {
		printf("Not divisible & Increasing\n");
		return 1;
	} else if (is_divisible && !is_increasing) {
		printf("Divisible & Not increasing\n");
		return 2;
  	} else {
		printf("Not Divisible & Not increasing\n");
		return 3;
	}
}
