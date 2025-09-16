#ifndef DB_H
#define DB_H

// Mia
//Just initializing fields and functions have right return type

#include <stddef.h> //saw it in the ppoints for size_t



typedef struct Record { 
/* TODO */

  char handle[32]; //instagram handle (31 chars + null terminator)
  long unsigned followers; //i can pick any field name right?? follower count
  char comment[64]; // User comment (63 chars + null terminator)
  long unsigned last_modified;// UNIX timestamp

} Record;

typedef struct Database { 
/* TODO */

  Record *records;// Dynamic array of records
  size_t size;//current number of record   
  size_t capacity;//allocated capacity 

} Database;

//Core functions core database API
Database db_create(); //initialize database

void db_append(Database *db, Record const *item);

Record *db_index(Database *db, int index);

Record *db_lookup(Database *db, char const *handle);

void db_free(Database *db);

//CSV Functions
void db_load_csv(Database *db, char const *path);

void db_write_csv(Database *db, char const *path);

#endif
