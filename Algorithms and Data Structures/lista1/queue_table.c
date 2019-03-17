/*
 *  Queue (FIFO) based on table of size n with at most n elements
 *  We assume that the table is a cycle
 *  Author: Kacper Zieliński, based on Introduction to Algorithms, Cormen Thomas H., Leiserson Charles E., Rivest Ronald L, Clifford Stein
 */

#include <stdio.h>
#include <stdbool.h>
#include <stdlib.h>

// 2^31-1
const int INT32_MAX = 2147483647;

// start, indicates first element to out
int* head = NULL;
// end, indicates first place to go in
int* tail = NULL;
int* tab = NULL;
int size = 0;
int numberOfElements = 0;

bool isFull()
{
	if(head == tail && numberOfElements != 0)
		return true;
	return false;
}

bool isEmpty()
{
	if(tail == head && numberOfElements == 0)
		return true;
	return false;
}

// push
void enqueue(int number)
{
	if(isFull())
		printf("Cannot add element '%d' to queue. It's full.\n", number);
		
	else
	{
		numberOfElements++;

		*tail = number;
		
		// jump to the beginning of the table
		if(tail == (tab+(size-1)))
			tail = tab;
		else
			tail++;
			
		
		printf("Enqueued: %d\n", number);
	}
}

// pop
void dequeue()
{
	if(isEmpty())
		printf("Cannot remove element from queue. It's empty.\n");
		
	else
	{
		numberOfElements--; 
		
		int x = *head;
		*head = 0;
		
		// jump to the beginning of the table
		if(head == (tab+(size-1)))
			head = tab;
		else
			head++;
		
		printf("Dequeued: %d\n", x);
	}
}

void testQueue()
{
	enqueue(1);
	enqueue(2);
	enqueue(3);
	enqueue(4);
	enqueue(5);
	enqueue(12);
	dequeue();
	enqueue(12);
	dequeue();
	dequeue();
	dequeue();
	enqueue(69);
	enqueue(69);
	enqueue(69);
	dequeue();
	dequeue();
}

int main(int argc, char **argv)
{
	printf("Enter the size of the queue: \n");
	scanf ("%d", &size);
	
	if(size <=0 || size >= INT32_MAX)
		return 0;
		
	printf("OK, creating queue of size %d ... \n", size);
	
	tab = malloc(sizeof(int) * size);
	head = tab;
	tail = tab;
	
	for(int i=0; i<size; i++)
		tab[i] = 0;
		
	testQueue();
	
	for(int i=0; i<size; i++)
	{
		if(&tab[i] == head && &tab[i] == tail)
			printf("Table position %d value: %d \t<-- HEAD && TAIL\n", i, tab[i]);
		else if(&tab[i] == head)
			printf("Table position %d value: %d \t<-- HEAD\n", i, tab[i]);
		else if(&tab[i] == tail)
			printf("Table position %d value: %d \t<-- TAIL\n", i, tab[i]);
		else
			printf("Table position %d value: %d\n", i, tab[i]);
	}
	
	free(tab);
	
	return 0;
}
