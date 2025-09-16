#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <time.h>

#include "database.h"

int modified = 0;//State of the DB

//Command functions
void adds(Database *db, char *input);

void updates(Database *db, char *input);

void lists(Database *db);

void saves(Database *db);

void sorts(Database *db);

void finds(Database *db, char *input);

void swaps(Database *db, char *input);

//Programmer: Youcef Antar Antar
//Created   : 2025/04/07
//Purpose   : Handles inner recursive calls and command selection

int main_loop(Database *db)
{
    // Setting up the user input
    char input[151];
    printf("> "); // Prompt cursor

    fgets(input, sizeof(input), stdin); // Getting the input
    input[strcspn(input, "\n")] = '\0'; // Removing the newline

    // Command Handling
    if (strcmp(input, "exit") == 0) { // Soft exit
        if (modified == 0) { // Changes saved
            return 0;
        } else { // Changes unsaved
            printf("Error: you did not save your changes. Use `exit fr` to force exiting anyway.\n");
            return main_loop(db);
        }

    } else if (strcmp(input, "exit fr") == 0) { // Forced exit
        return 0;

    } else if (strncmp(input, "add", 3) == 0 && (input[3] == '\0' || input[3] == ' ')) { // Adds
        adds(db, input);
        return main_loop(db);

    } else if (strncmp(input, "update", 6) == 0 && (input[6] == '\0' || input[6] == ' ')) { // Updates
        updates(db, input);
        return main_loop(db);

    } else if (strcmp(input, "list") == 0) { // List
        lists(db);
        return main_loop(db);

    } else if (strcmp(input, "save") == 0) { // Save
        saves(db);
        return main_loop(db);

    } else if (strcmp(input, "sort") == 0) { // Sort
        sorts(db);
        return main_loop(db);

    } else if (strncmp(input, "find", 4) == 0 && (input[4] == '\0' || input[4] == ' ')) { // Find
        finds(db, input);
        return main_loop(db);

    } else if (strncmp(input, "swap", 4) == 0 && (input[4] == '\0' || input[4] == ' ')) { // Swap
        swaps(db, input);
        return main_loop(db);

    }
     else { // Catch-all for unknown commands
        printf("Error: unknown command.\n");
        return main_loop(db);
    }

    return 0; // Fallback
}


//Programmer: Youcef Antar Antar
//Created   : 2025/04/07
//Purpose   : Adds a new entry to the end of the database

void adds(Database *db, char *input) {
    //Splitting up input array
    char *add = strtok(input, " ");
    char *handle = strtok(NULL, " ");
    char *followers = strtok(NULL, " ");
    char *netting = strtok(NULL, " ");

    //Clearing invalid user input
    if (!add || !handle || !followers || netting) {//Wrong initial input
        printf("Error: usage: add HANDLE FOLLOWERS.\n");
        return;
    
    } else if (strlen(handle) >= 32) {//Invalid handle lenght
    	printf("Error: handle too long.\n");
	return;

    } else if (db_lookup(db, handle)) {//Existing handle
    	printf("Error: entry %s already exists in database.\n", handle);
	return;
    }

    char *endptr; //Converting follower count into int
    long int followerCount = strtol(followers, &endptr, 10);

    if (endptr == followers || *endptr != '\0' || followerCount < 0) {
    	printf("Error: follower count must be a positive integer.\n");
	return;
    }
    
    //Input comment
    char comment[64];
    
    printf("Comment> ");//Prompt cursor
    
    fgets(comment, sizeof(comment), stdin);//Getting the input

    comment[strcspn(comment, "\n")] = '\0';//Removing the newline
    
    //Clearing invalid comment input
    if (strchr(comment, ',')) {
        printf("Error: comment cannot contain commas.\n");
	return;
    }

    //Creating the record
    Record r;
    r.followers = followerCount;//Adding the follower count

    strncpy(r.handle, handle, sizeof(r.handle));//Adding+padding the handle
    r.handle[sizeof(r.handle) - 1] = '\0';
    
    strncpy(r.comment, comment, sizeof(r.comment));//Adding+padding the comment
    r.comment[sizeof(r.comment) - 1] = '\0'; 

    r.last_modified = time(NULL);//Adding the timestamp

    //Finishing steps
    db_append(db, &r);//appending the record
    
    modified = 1;//Keeping track of the state of the database
}

//Programmer: Youcef Antar Antar
//Created   : 2025/04/08
//Purpose   : Updates an existing entry for the given handle

void updates(Database *db, char *input) {
    //Splitting up input array
    char *update = strtok(input, " ");
    char *handle = strtok(NULL, " ");
    char *followers = strtok(NULL, " ");
    char *netting = strtok(NULL, " ");

    //Clearing invalid user input
    if (!update || !handle || !followers || netting) {//Wrong initial input
        printf("Error: usage: update HANDLE FOLLOWERS.\n");
        return;

    } else if (strlen(handle) >= 32) {//Invalid handle lenght
        printf("Error: handle too long.\n");
        return;
    }

    char *endptr; //Converting follower count into int
    long int followerCount = strtol(followers, &endptr, 10);

    if (endptr == followers || *endptr != '\0' || followerCount < 0) {
        printf("Error: follower count must be a positive integer.\n");
        return;
    
    } else if (!db_lookup(db, handle)) {//Unexisting handle
        printf("Error: no entry with handle %s.\n", handle);
        return;
    }

    //Input comment
    char comment[64];

    printf("Comment> ");//Prompt cursor

    fgets(comment, sizeof(comment), stdin);//Getting the input

    comment[strcspn(comment, "\n")] = '\0';//Removing the newline

    //Clearing invalid comment input
    if (strchr(comment, ',')) {
        printf("Error: comment cannot contain commas.\n");
        return;
    }
    
    //Modifying the record
    Record *r = db_lookup(db, handle);
    
    r->followers = followerCount;//Updating the follower count

    strncpy(r->comment, comment, sizeof(r->comment));//Updating+padding the comment
    r->comment[sizeof(r->comment) - 1] = '\0';

    r->last_modified = time(NULL);//Updating the timestamp

    modified = 1;//Keeping track of the state of the database

}

//Programmer: Youcef Antar Antar
//Created   : 2025/04/08
//Purpose   : Prints out the whole database formatted as a table.

void lists(Database *db){
    //Printing header
    printf("          HANDLE          |  FOLLOWERS  |   LAST MODIFIED   |            COMMENT\n");
    printf("--------------------------|-------------|-------------------|------------------------------\n");
    
    //Iterating through the database
    for (size_t i = 0 ; i < db->size ; i++) {
        Record *r = db_index(db, i);//Setting up record
	
	//Setting up TIME
	time_t compass = r->last_modified;
	struct tm *timerz = localtime(&compass);
	char bottle[18];
    	strftime(bottle, sizeof(bottle), "%Y-%m-%d %H:%M",timerz);

	//Printing iteration
	printf("%-25.25s | %-11lu | %-17s | %s\n", r->handle, r->followers, bottle, r->comment);
	
	
    }
}
//Programmer: Youcef Antar Antar
//Created   : 2025/04/08
//Purpose   : Writes the database out to the csv database file

void saves(Database *db){
    //Writes the files
    db_write_csv(db, "database.csv");
    
    //Updates the state of the database
    modified = 0;

    //Prints confirmation message
    printf("Wrote %lu records.\n", db->size);
}

//Programmer: Youcef Antar Antar
//Created   : 2025/04/08
//Purpose   : Writes the database out to the csv database in order of followers count

void sorts(Database *db){
    //Sorting the array
    for (size_t i = 0 ; i < db->size ; i++) {
        for (size_t j = 0 ; j < db->size - i - 1 ; j++ ) {
	    if (db->records[j].followers < db->records[j+1].followers) {
	        Record temp = db->records[j+1];
	    	db->records[j+1] = db->records[j];
		db->records[j] = temp;

	    }
	}
    }
    //Writing the file
    saves(db);
}

//Programmer: Taym Atrach
//Created   : 2025/04/09
//Purpose   : Finds a record in the database by handle 

void finds(Database *db, char *input) {
    // Duplicate input to avoid mutation
    char *copy = strdup(input);
    if (!copy) {
        fprintf(stderr, "Memory allocation failed\n");
        return;
    }

    // Skip the command
    char *token = strtok(copy, " ");
    if (!token) {
        free(copy);
        printf("Error: usage find HANDLE\n");
        return;
    }

    // Get handle (quoted or not)
    char *handle = NULL;
    token = strtok(NULL, "\"");
    if (token && *token != ' ') {
        handle = strdup(token);
    } else {
        token = strtok(NULL, "\"");
        if (token) handle = strdup(token);
    }

    if (!handle) {
        printf("Error: usage find HANDLE (wrap spaces in quotes if needed)\n");
        free(copy);
        return;
    }

    Record *r = db_lookup(db, handle);

    if (!r) {
        printf("Not Found\n");
        free(copy);
        free(handle);
        return;
    }

    // Format timestamp
    time_t t = r->last_modified;
    struct tm *tmz = localtime(&t);
    char formatted[18];
    strftime(formatted, sizeof(formatted), "%Y-%m-%d %H:%M", tmz);

    // Print header + result
    printf("_____________________________________________________________\n");
    printf("       HANDLE       |  FOLLOWERS  |  LAST MODIFIED  |  COMMENT\n");
    printf("-------------------------------------------------------------\n");
    printf("%-18s | %-11lu | %-15s | %s\n", r->handle, r->followers, formatted, r->comment);

    free(copy);
    free(handle);
}



//Programmer: Taym Atrach
//Created   : 2025/04/10
//Purpose   : swaps positions of two records in database by their handle

void swaps(Database *db, char *input) {
    char *copy = strdup(input);
    if (!copy) {
        fprintf(stderr, "Memory allocation failed\n");
        return;
    }
    
   

    // Parse tokens using plain space as separator
    (void) strtok(copy, " ");
    char *handle1 = strtok(NULL, " ");
    char *handle2 = strtok(NULL, " ");
    char *extra = strtok(NULL, " "); // For catching excess args

    // Error handling
    if (!handle1 || !handle2 || extra != NULL ||
        strchr(handle1, '"') || strchr(handle2, '"')) {
        printf("Error: usage swap HANDLE1 HANDLE2 (no quotes allowed)\n");
        free(copy);
        return;


	} else if (strlen(handle1) >= 32) {//Invalid handle 2 lenght
        printf("Error: first handle too long.\n");
        return;
       } else if (strlen(handle2) >= 32) {//Invalid handle 1 lenght
        printf("Error: second handle too long.\n");
        return;
    }

    int index1 = -1, index2 = -1;

    for (size_t i = 0; i < db->size; i++) {
        if (strcmp(db->records[i].handle, handle1) == 0) {
            index1 = i;
        }
        if (strcmp(db->records[i].handle, handle2) == 0) {
            index2 = i;
        }
    }

    if (index1 == -1 || index2 == -1) {
        printf("Not Found\n");




    } else {
        Record temp = db->records[index1];
        db->records[index1] = db->records[index2];
        db->records[index2] = temp;
        printf("Swapped %s and %s\n", handle1, handle2);
    }

    free(copy);

    modified = 1;//Updating the state of the database
		 
   


}
 




//Programmer: Youcef Antar Antar
//Created   : 2025/04/06
//Purpose   : Initially loads the database and starts the loop

int main()
{
    Database db = db_create();
    db_load_csv(&db, "database.csv");
    
    //Loading the number of recordss
    int startingRecords = db.size;
    printf("Loaded %d records.\n", startingRecords);

    int function = main_loop(&db);

    db_free(&db);//Preventing memory leaks

    return function;
}
