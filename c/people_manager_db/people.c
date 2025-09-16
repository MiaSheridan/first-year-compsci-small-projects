#include <stdio.h>
#include <string.h>

typedef struct PERSON {
  // for a "real world" application you should look at "enum" types
  char type;   // 'p'=prof, 's'=student, 'f'=staff, '\0'=deleted (or just never made)
  char name[30];
  int age;
  char ID[10];
  union SPECIFIC_DATA {
    struct { float evaluation; } prof;
    struct { float GPA; int credits; } student;   
    struct { int level; int hours; } staff;
  } specific;
} person_t;

person_t people[1000] = { 0 };

//   You will have definitions for all of these functions;
// some are provided.
int main_menu(void);
void add_menu(void);
void find_menu(void);
void del_menu(void);
void list_menu(void);

person_t *add_generic(char type, char *name, int age, char *ID);
void add_prof(person_t *to, float evaluation);
void add_student(person_t *to, float GPA, int credits);
void add_staff(person_t *to, int level, int hours);

person_t *find(char *ID);

void del(person_t *p);

void list(char type);
void prettyprint(person_t *p);


int main(void) {
  int exitcode;
  do {
    exitcode = main_menu();
  } while (exitcode); // equivalently: } while (exitcode != 0);
}

int main_menu(void) {
  printf("0. Quit\n");
  printf("1. Add a person\n");
  printf("2. Find a person\n");
  printf("3. Remove a person\n");
  printf("4. List a group\n");
  printf("Select an option: ");


  int choice;


 // scanf("%d", &choice);
  if (scanf("%d", &choice) != 1) {
        // If input is not an integer, clear the input buffer
    while (getchar() != '\n');
    printf("Invalid input. Please enter a number between 0 and 4.\n");
    return -1;
  }

  //use switch to call the helper functions i want
  switch(choice){
    case 0:
      return 0;

    case 1:
      add_menu(); //add person
      break;  
  
    case 2:
      find_menu(); //find person
      break;

    case 3:
      del_menu(); //remove person
      break;

    case 4:
      list_menu(); //list a group
      break;
    default: //new thing
      printf("Invalid choice. Please enter a number between 0 and 4.\n");
      return -1;
  }
  return choice;  
  // return the value that was chosen at the end.
  // i.e. if user chose option 3, return 3
}

void add_menu(void) {
  
  char type;
  char name[30];
  int age;
  char ID[20];


  printf("Enter the type: ");
  scanf(" %c", &type); // Space before %c to consume newline from previous input
  while (getchar() != '\n'); // Clear input buffer


  printf("Enter the name: ");
  //getchar();
  fgets(name, sizeof(name), stdin);
  name[strcspn(name, "\n")] = '\0'; //remove newline character

  printf("Enter the age: ");
  scanf("%d", &age);
  while (getchar() != '\n'); // Clear input buffer

  printf("Enter the ID: ");
  //getchar();
  fgets(ID, sizeof(ID), stdin);
  ID[strcspn(ID, "\n")] = '\0'; //same thing for name

  // appropriately call add_generic(...)
  person_t *p = add_generic(type, name, age, ID);


  // i dont know if i should put this....
  if (p == NULL) {
      return;
  }

  switch(type) {
  case 'p':
    float evaluation;
    printf("Enter the evaluation: ");
    // appropriately call add_prof(...)
    scanf("%f", &evaluation);
    add_prof(p, evaluation);
    break;

  case 's':
    float GPA;
    int credits;
    printf("Enter the GPA: ");
    scanf("%f", &GPA);
    printf("Enter the credits: ");
    scanf("%d", &credits);
    // appropriately call add_student(...)
    add_student(p, GPA, credits);
    break;

  case 'f':
    // fill in this case from scratch
    int level, hours;
    printf("Enter the level: ");
    scanf("%d", &level);
    printf("Enter the hours: ");
    scanf("%d", &hours);
    add_staff(p, level, hours);
    break;

  default:
    // fill in this case from scratch
    printf("Invalid\n");
  
  }
}



// Implement the add_* functions:
person_t *add_generic(char type, char *name, int age, char *ID) {
  // TODO

  // Step 1: Check for duplicate ID
  if (find(ID) != NULL) {
    printf("Refusing to add duplicate person.\n");
    return NULL;
  }

  //2 find the first free slot in people[]
  for (int i = 0; i < 1000; i++) {
    if (people[i].type == '\0') {  // found unused slot
      // 3 Initialize the person's attributes
      people[i].type = type;
      strcpy(people[i].name, name);
      people[i].age = age;
      strcpy(people[i].ID, ID);

      //4 return pointer to the space
      return &people[i];
    }
  }

  //no available space found
  printf("Error: No available space to add a new person.\n");
  return NULL;
}

void add_prof(person_t *to, float evaluation) {
  // TODO
  if (to == NULL) return;
  to->type = 'p';
  to->specific.prof.evaluation = evaluation;
}
void add_student(person_t *to, float GPA, int credits) {
  // TODO
  if (to == NULL) return;
  to->type = 's';
  to->specific.student.GPA = GPA;
  to->specific.student.credits = credits;
}
void add_staff(person_t *to, int level, int hours) {
  // TODO
  if (to == NULL) return;
  to->type = 'f';
  to->specific.staff.level = level;
  to->specific.staff.hours = hours;
}

void find_menu(void) {
  char ID[10];
  printf("Enter the ID: ");
  scanf("%9s", ID); // You may assume that only valid IDs are entered.
  person_t *p = find(ID);
  if (p) { // "if we did find a person"
    prettyprint(p);
  } else {
    // fill this case in
    printf("No person found with ID %s.\n", ID); //is this okay just a print statement??
  }
}

person_t *find(char *ID) {
  // TODO: Implement this function.

  for (int i = 0; i < 1000; i++) {  // iterate over array people
    if (people[i].type != '\0' && strcmp(people[i].ID, ID) == 0) {
      return &people[i]; // Found the person, return a pointer
    }
  }
  return NULL; //no match
}

void del_menu(void) {
  char ID[10];
  printf("Enter the ID: ");
  scanf("%9s", ID); // You may assume that only valid IDs are entered.
  person_t *p = find(ID);
  if (p) { // "if we did find a person"
    del(p);
  }
  // If there is no matching person we are supposed to do nothing.
}

void del(person_t *p) {
  
  p->type = '\0';  // Mark this person as deleted
}

void list_menu(void) {
  
  char type;
  printf("Enter the type: ");
  scanf(" %c", &type);
  list(type);
}

void list(char type) {
  

  for (int i = 0; i < 1000; i++) {
    if (people[i].type == '\0') {
      continue; // Skip empty/deleted entries
    }
    if (type == 'a' || people[i].type == type) {
      prettyprint(&people[i]); // Print matching persons
    }
  }
}

void prettyprint(person_t *p) {



//indentation not sure if its good...

  //print the person name and ID
  printf("%s (%s):\n", p->name, p->ID);

  // switch person's type to print the right information
  switch (p->type){
    case 'p': //prof
      printf("  Prof, age %d\n", p->age);
      printf("  Evaluation is %.1f\n", p->specific.prof.evaluation);
      break;

    case 's': //student
      printf("  Student, age %d\n", p->age);
      printf("  GPA is %.2f, after %d credits\n", p->specific.student.GPA, p->specific.student.credits);
      break;
    case 'f': //staff
      printf("  Staff, age %d\n", p->age);
      printf("  Level is %d, allocated %d hours\n", p->specific.staff.level, p->specific.staff.hours);
      break;
    //not sure if i should handle a case where theres a case if a person is deleted or not initiliazed. ask TA
    default:
      printf("  No valid data available.\n");


  }


 
}

/*
int main() {
    person_t prof_max = {
        .type = 'p',
        .name = "Max Kopinsky",
        .age  = 25,
        .ID   = "123456789",
        .specific = {
            .prof = {
                .evaluation = 0.0
            }
            };

    person_t student_john = {
        .type = 's',
        .name = "John Doe",
        .age  = 20,
        .ID   = "987654321",
        .specific = {
            .student = {
                .GPA = 3.75,
                .credits = 60
            }
        }
    };

    person_t staff_hamza = {
        .type = 't',
        .name = "Hamza Javed",
        .age  = 40,
        .ID   = "555444333",
        .specific = {
            .staff = {
                .level = 5,
                .hours_allocated = 35
            }
        }
    };

    prettyprint(&prof_max);
    prettyprint(&student_john);
    prettyprint(&staff_hamza);

    return 0;
}


*/



