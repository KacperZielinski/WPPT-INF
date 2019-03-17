/*
 *  Queue (FIFO) based on unidirectional list 
 *  Author: Kacper Zieliński
 * 
 *  First pointer is HEAD pointer
 *  Last pointer is TAIL pointer
 */

#include <stdio.h>
#include "unidirectional_list.h"

typedef struct UnidirectionalList Queue;

bool isQueueEmpty(Queue* queue)
{
	Node* head = queue->first;
	if(head == queue->last && head == NULL)
		return true;
	return false;
}

// Add value at the end (TAIL)
void enqueue(int number, Queue* queue)
{		
	addValue(number, queue);
	printf("Enqueued: %d\n", number);
}

// Remove value from first node (HEAD)
void dequeue(Queue* queue)
{
	if(isQueueEmpty(queue))
		printf("Cannot remove element from queue. It's empty.\n");
		
	else
	{
		int value = queue->first->value;
		removeValue(value, queue);
		printf("Dequeued: %d\n", value);
	}
}

int main()
{
	Queue* queue = initializeList();
	enqueue(1, queue);
	dequeue(queue);
	enqueue(2, queue);
	enqueue(3, queue);
	enqueue(4, queue);
	enqueue(5, queue);
	enqueue(6, queue);
	dequeue(queue);
	dequeue(queue);
	dequeue(queue);
	dequeue(queue);
	dequeue(queue);
	dequeue(queue);
	dequeue(queue);
	print(queue);
	return 0;
}