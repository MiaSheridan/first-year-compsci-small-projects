CC=gcc
OPTS=

people: people.o
	$(CC) $(OPTS) -o people people.o

people.o: people.c
	$(CC) $(OPTS) -c people.c

clean:
	$(RM) people people.o
