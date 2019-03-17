/**
 *  Two-way cyclic list based on pointers
 *  Author: Kacper Zieliński
 */

#define _CRT_RAND_S  
#include <stdio.h>
#include <stdbool.h>
#include <stdlib.h> 
#include <windows.h>

typedef struct TwoWayList TwoWayList;
typedef struct Node Node;

struct TwoWayList
{
	int size;
	Node *start;
};
 
struct Node
{
	int value;
	Node *next;
	Node *prev;
};

bool isEmpty(TwoWayList* tl)
{
	if(tl->size == 0)
		return true;
	return false;
}

/**
 * Add value to list
 */
void addValue(int value, TwoWayList* tl)
{
	Node* newNode = malloc(sizeof(Node));
	Node* previous = tl->start;
	
	newNode->value = value;			// (*newNode).value
	newNode->next = tl->start;
	
	// add a first node 
	if(isEmpty(tl))
	{
		newNode->prev = newNode;
		newNode->next = newNode;
		tl->start = newNode;
	}
	else
	{
		previous = tl->start->prev;
		newNode->prev = previous;
		tl->start->prev = newNode;
		previous->next = newNode;
	}
	
	tl->size = tl->size + 1;
}

void removeValue(int value, TwoWayList* tl)
{
	Node* tmp = tl->start;
		
	while(tmp->next != tl->start)
	{
		if(tmp->value == value)
		{
			Node* previous;
			Node* following;
			previous = tmp->prev;
			following = tmp->next;
			following->prev = previous;
			previous->next = following;
			tl->size = tl->size - 1;

			if(tmp == tl->start)
				tl->start = following;
				
			free(tmp);
			return;
		}
		tmp = tmp->next;
	}
	
	if(tmp->value == value)
	{
		// if there is only 1 value
		if(tl->size == 1)
		{
			free(tmp);
			tl->start = NULL;	
		}
		// if value was found on last position
		else
		{
			Node* previous;
			Node* following;
			previous = tmp->prev;
			following = tl->start;
			previous->next = following;
			following->prev = previous;
			free(tmp);
		}
		
		tl->size = tl->size - 1;
		return;
	}
	printf("Cannot remove value!\n");
}

int getValue(int value, TwoWayList* tl)
{
	Node* tmp = tl->start;
		
	while(tmp->next != tl->start)
	{
		if(tmp->value == value)
			return value;
	
		tmp = tmp->next;
	}
	
	if(tmp->value == value)
		return value;
	else
		printf("Cannot find element ' %d '. It doesn't exists!\n", value);
	
	return 0;
}

// Index starts from 0
int getValueFromIndex(unsigned int index, TwoWayList* tl)
{
	Node* tmp = tl->start;
	
	if(index >= tl->size)
	{
		printf("There is no such index\n");
		return 0;
	}
	
	if(index == 0)
		return tmp->value;
	
	if((tl->size)/2 > index)
	{
		for(int i=0; i<index; i++)
			tmp = tmp->next;
	}
	else
	{
		index = tl->size - index;
		
		for(int i=0; i<index; i++)
			tmp = tmp->prev;
	}
	
	return tmp->value;
}

void print(TwoWayList* tl)
{
	Node* tmp = tl->start;
	
	if(isEmpty(tl))
	{
		printf("It's empty.. \n");
		return;
	}
	
	printf("... --> %d --> ", tmp->value);
		
	while(tmp->next != tl->start)
	{
		tmp = tmp->next;
		printf("%d --> ", tmp->value);
	}
	
	printf("... \n");
}

TwoWayList* initializeList()
{
	TwoWayList* tl = malloc(sizeof(TwoWayList));
	tl->start = NULL;
	tl->size = 0;
	
	return tl;
}

TwoWayList* copyList(TwoWayList* tl)
{
	TwoWayList* copiedList = initializeList();
	Node* tmp = tl->start;
	
	if(tl->size != 0)
	{
		addValue(tmp->value, copiedList);
		tmp = tmp->next;
	}
	
	while(tmp != tl->start)
	{
		addValue(tmp->value, copiedList);
		tmp = tmp->next;
	}
	
	return copiedList;
}

TwoWayList* merge(TwoWayList* l1, TwoWayList* l2)
{
	if(isEmpty(l1))
		return l2;
	else if(isEmpty(l2))
		return l1;
	
	TwoWayList* mergedList = initializeList();
	TwoWayList* mergedList1 = copyList(l1);
	TwoWayList* mergedList2 = copyList(l2);	
	Node* l1Previous = mergedList1->start->prev;
	Node* l2Previous = mergedList2->start->prev;
	
	l1Previous->next = mergedList2->start;
	mergedList1->start->prev = l2Previous;
	l2Previous->next = mergedList1->start;
	mergedList2->start->prev = l1Previous;
	
	mergedList->start = mergedList1->start;
	mergedList->size = l1->size + l2->size;
		
	return mergedList;
}

void generateNodes(TwoWayList* tl, int k)
{
	for(int i=0; i<k; i++)
	{ 
		unsigned int number;  
		errno_t err;  
		
		err = rand_s(&number);  
		
        if (err != 0)  
            printf("The rand_s function failed!\n");  
			
		addValue(number, tl);
	}
}

void testTheSameElement()
{
	const int check = 10000;
	const int listSize = 100000;
    double interval;
	LARGE_INTEGER frequency;
    LARGE_INTEGER start;
    LARGE_INTEGER end;
	
//	const int chosenIndex = 1;
	const int chosenIndex = 998;
//	const int chosenIndex = 500;
//	const int chosenIndex = 123;
//	const int chosenIndex = 745;
	
	TwoWayList* tl = initializeList();
	generateNodes(tl, listSize);
	
    QueryPerformanceFrequency(&frequency);
    QueryPerformanceCounter(&start);

	for(int i=0; i<check; i++)
		getValueFromIndex(chosenIndex, tl);

    QueryPerformanceCounter(&end);
    interval = (double) (end.QuadPart - start.QuadPart) / frequency.QuadPart;

	printf("--------- CHOSEN INDEX --------\n");
	printf("   listType = Two-Way List\n"); 
	printf("   listSize = %d\n", listSize); 
	printf("   attempts = %d\n", check);
	printf("   for index = %d\n\n", chosenIndex);
	printf("Overall time: %.6f s\n", interval);
	printf("Average time for one element: %.6f ms\n", interval*1000/check);
	printf("-------------------------------\n\n");
}

void testRandomElement()
{
	const int check = 10000;
	const int listSize = 100000;
    double interval;
	unsigned int number;  
	errno_t err = rand_s(&number);    
	LARGE_INTEGER frequency;
    LARGE_INTEGER start;
    LARGE_INTEGER end;
	
	if (err != 0)  
		printf("The rand_s function failed!\n");  
		
	int chosenIndex = number % listSize;
	
	TwoWayList* tl = initializeList();
	generateNodes(tl, listSize);
	
    QueryPerformanceFrequency(&frequency);
    QueryPerformanceCounter(&start);
	
	for(int i=0; i<check; i++)
		getValueFromIndex(chosenIndex, tl);
		
	QueryPerformanceCounter(&end);
    interval = (double) (end.QuadPart - start.QuadPart) / frequency.QuadPart;

	printf("--------- RANDOMIZE -----------\n");
	printf("   listType = Two-Way List\n"); 
	printf("   listSize = %d\n", listSize); 
	printf("   attempts = %d\n", check);
	printf("   for index = %d\n\n", chosenIndex);
	printf("Overall time: %.6f s\n", interval);
	printf("Average time for one element: %.6f ms\n", interval*1000/check);
	printf("-------------------------------\n\n");
}

void test1()
{
	TwoWayList* ul = initializeList();
	print(ul);
	addValue(1, ul);
	addValue(2, ul);
	addValue(3, ul);
	addValue(4, ul);
	addValue(5, ul);
	addValue(6, ul);
	print(ul);
	removeValue(4, ul);
	print(ul);
	removeValue(1, ul);
	print(ul);
	removeValue(6, ul);
	print(ul);
	removeValue(2, ul);
	print(ul);
	removeValue(3, ul);
	print(ul);
	removeValue(5, ul);
	print(ul);
	addValue(2123, ul);
	addValue(332121, ul);
	addValue(4322, ul);
	print(ul);
	removeValue(18, ul);
	print(ul);
	addValue(1, ul);
	addValue(2, ul);
	addValue(3, ul);
	removeValue(3, ul);
	removeValue(2, ul);
	print(ul);
}

void test2()
{
	TwoWayList* ul = initializeList();
	addValue(1, ul);
	addValue(2, ul);
	addValue(3, ul);
	addValue(4, ul);
	addValue(5, ul);
	addValue(6, ul);
	removeValue(4, ul);
	print(ul);
	
	TwoWayList* u = initializeList();
	addValue(1, u);
	addValue(2, u);
	addValue(3, u);
	addValue(4, u);
	addValue(5, u);
	addValue(6, u);
	addValue(7, u);
	removeValue(1, u);
	print(u);
	
	
	printf("By Value: %d\n", getValue(6, u));
	printf("By Value: %d\n", getValue(6, u));
	printf("By Value: %d\n", getValue(6, u));
	printf("By Value: %d\n", getValue(6, u));
	printf("By Value: %d\n", getValue(6, u));
	printf("Value from index: %d\n", getValueFromIndex(0, u));
	printf("Value from index: %d\n", getValueFromIndex(1, u));
	printf("Value from index: %d\n", getValueFromIndex(2, u));
	printf("Value from index: %d\n", getValueFromIndex(3, u));
	printf("Value from index: %d\n", getValueFromIndex(4, u));
	printf("Value from index: %d\n", getValueFromIndex(5, u));
	printf("Value from index: %d\n", getValueFromIndex(6, u));

	TwoWayList* merged = merge(ul, u);
	print(merged);
}

void test3()
{
	TwoWayList* ul = initializeList();
	addValue(1, ul);
	addValue(2, ul);
	addValue(3, ul);
	addValue(4, ul);
	addValue(5, ul);
	addValue(6, ul);
	print(ul);
	removeValue(1, ul);
	print(ul);
	removeValue(2, ul);
	print(ul);
	removeValue(3, ul);
	print(ul);
	removeValue(4, ul);
	print(ul);
	removeValue(5, ul);
	print(ul);
	removeValue(6, ul);
	print(ul);
}

int main(void)
{
	testTheSameElement();
	testRandomElement();
	return 0;
}