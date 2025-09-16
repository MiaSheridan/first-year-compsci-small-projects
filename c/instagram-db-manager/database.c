#include "database.h"
#include <stdio.h>
#include <stdlib.h>
#include <string.h>

#include <assert.h>  //saw this one on the slides(Mia)
#define INITIAL_CAPACITY 4 //as specified 

//Sofia
//parses a single line of CSV data into Record 

Record parse_record(char const *line){
	Record record = {0}; //initialiazed at 0

	if (!line || *line == '\0'){
		fprintf(stderr, "error, empty line\n");
		return record; }

	char *line_copy = strdup(line);
	if (!line_copy){
		fprintf(stderr, "failed to allocate memory\n");
		return record;
	}

	// acc
	char *token = strtok(line_copy, ",");
	if (token){
		strncpy(record.handle, token, sizeof(record.handle) - 1);
		record.handle[sizeof(record.handle)-1] = '\0'; //null
							    }

	//followers
	token = strtok(NULL, ",");
    	if (token) {
        	record.followers = strtoul(token, NULL, 10);
    	}

	//comment
	token = strtok(NULL, ",");
    	if (token) {
        	strncpy(record.comment, token, sizeof(record.comment) - 1);
       		 record.comment[sizeof(record.comment) - 1] = '\0';
   	}

	// last modified
	token = strtok(NULL, ",");
    	if (token) {
        	record.last_modified = strtoul(token, NULL, 10);
    	}

    	free(line_copy);
    	return record;
}
 

//Mia

//Create empty database 

Database db_create(){

  Database db;
  db.size = 0;
  db.capacity = INITIAL_CAPACITY;//4
  db.records = malloc(db.capacity * sizeof(Record));

  //checking if malloc failed to allocate memory for db.records
  if(!db.records){

    fprintf(stderr, "Memory allocation failed\n");
    exit(EXIT_FAILURE); //can i do this instead of just return 1?  exiting due to a memory allocation error Prof.Joseph said it was okay

  }
  return db;


}

//Mia 
 //appends a record and resizes if needed

void db_append(Database *db, Record const *item){

  if(!db || !item){
  
    fprintf(stderr, "error:invalid input to db_append\n");
    return;
  }

  //resize if full
  if (db->size >= db->capacity){
    int new_capacity = db->capacity * 2; //doubling capacity
    Record *new_records = realloc(db->records, new_capacity * sizeof(Record));

    if (!new_records) {
    
      fprintf(stderr, "Oh no, failed to resize database\n");
      return;
    }
    db->records = new_records;
    db->capacity = new_capacity;
  }
  //copy record
  db->records[db->size] = *item;
  db->size++;
  //I need to check with the TA if I am doing the right thing??//yup the pointer thing *2 and * 2 not the same thing change thatt

}

//Mia 
//returns a pointer to the item in the database at the given index

Record *db_index(Database *db, int index) {
  int dbSize = (int) db->size;

  if (!db || index < 0 || index >= dbSize) {
    return NULL;//out of bounds
  }
  return &db->records[index];
}


//Mia 
// Returns a pointer to the first item in the database whose handle field equals the given value.

//search by handle(case sensitive)

Record *db_lookup(Database *db, char const *handle) {
  if (!db || !handle) return NULL;
  for (size_t i = 0; i < db->size; i++) {
    if (strcmp(db->records[i].handle, handle) == 0) {
      return &db->records[i];
    }
  }
  return NULL;
}


// Mia
// Releases the memory held by the underlying array. After calling this, the database can no longer be used.

void db_free(Database *db){
  if(db){
    free(db->records);//let it be free, release memory
    db->records = NULL;
    db->size=db->capacity =0;
  }
}



//sof

/ appends the records read from the file at 'path into already initialized 
//            database 'db'

void db_load_csv(Database *db, char const *path){
	
	if (!db || !path) return; //varible checking? idk if needed

	FILE *file = fopen(path, "r");
	if (!file){ //chekcing file 
		fprintf(stderr, "error: failed to open file\n");
		return;
	}

	char *line = NULL;
	size_t len = 0;

	while (getline(&line, &len, file) != -1){
			Record record;
			int fields = sscanf(line, "%31[^,],%lu,%63[^,],%lu", record.handle, 
				&record.followers, record.comment, &record.last_modified);

		if (fields == 4){
			db_append(db, &record);
		} else{
			fprintf(stderr, "error: invalid line format: %s", line);//adding error bad line formatting
		}

	}
	free(line);
	fclose(file);

}


//sof
//overwrite the file located at 'path' with the contents
//	      of the database, represented in CSV format
//

void db_write_csv(Database *db, char const *path){
	
	if (!db || !path) return; //varible checking, not sure if needed 
	
	FILE *file = fopen(path, "w");
	if (!file){ 
		fprintf(stderr, "error: failed to open filee\n");
		return;
	}

	for (size_t i = 0; i < db->size; i++){
		Record *r = &db->records[i];
		fprintf(file,"%s,%lu,%s,%lu\n", r->handle, r->followers, r->comment,
			       r->last_modified);
	}
	fclose(file);	

}







