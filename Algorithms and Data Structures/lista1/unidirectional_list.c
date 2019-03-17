/**
 *  Unidirectional non-cyclic list based on pointers
 *  Author: Kacper Zieliński
 */

#define _CRT_RAND_S  
#include <stdio.h>
#include <stdlib.h>
#include <windows.h>
#include "unidirectional_list.h"

bool isEmpty(UnidirectionalList* ul)
{
	if(ul->first == NULL)
		return true;
	return false;
}

/**
 * Add value to list
 */
void addValue(int value, UnidirectionalList* ul)
{
	Node* newNode = malloc(sizeof(Node));
	newNode->value = value;			// (*newNode).value
	newNode->next = NULL;
	
	// add a first node 
	if(isEmpty(ul))
	{
		ul->first = newNode;
		ul->last = newNode;
	}
	else
	{
		ul->last->next = newNode;
		ul->last = newNode;
	}
}

/**
 * Remove value from list
 */
void removeValue(int value, UnidirectionalList* ul)
{
	Node* tmp = ul->first;
	Node* previous = tmp;
	
	if(isEmpty(ul))
	{
		printf("Cannot remove element ' %d ' from list. It's empty.\n", value);
		return;
	}
	
	while(tmp->value != value)
	{
		previous = tmp;
		
		if(tmp->next != NULL)
			tmp = tmp->next;
		else
		{
			printf("Cannot remove element ' %d ' from list. It doesn't exists!\n", value);
			return;
		}
	}
	// Value was found! tmp is pointing to this node
	// If there is more than 1 node, then previous is pointing to the previous node.
	
	// If only 1 node exists
	if(ul->first == ul->last)
	{
		free(tmp);
		ul->first = NULL;
		ul->last = NULL;
	}
	// If 2 nodes exists
	else if(ul->first->next == ul->last)
	{
		free(tmp);
		
		if(ul->first == tmp)
			ul->first = ul->last;
		else
		{
			ul->first->next = NULL;
			ul->last = ul->first;
		}
	}
	// If we revome a first Node
	else if(ul->first == tmp)
	{
		ul->first = tmp->next;
		free(tmp);
	}
	// Remove middle or last node when list size is >2
	else
	{
		previous->next = tmp->next;
		
		//removing last element
		if(ul->last == tmp)
			ul->last = previous;
			
		free(tmp);
	}
}


int getValue(int value, UnidirectionalList* ul)
{
	Node* tmp = ul->first;
	
	while(tmp != NULL)
	{
		int val = tmp->value;
		
		if(val == value)
			return val;
			
		tmp = tmp->next;
	}
	
	printf("Cannot find element ' %d '. It doesn't exists!\n", value);
	
	return 0;
}

// Index starts from 0
int getValueFromIndex(unsigned int index, UnidirectionalList* ul)
{
	Node* tmp = ul->first;
	
	for(int i=0; i<index; i++)
	{
		if(tmp == NULL)
		{
			printf("There is no such index\n");
			return 0;
		}
		tmp = tmp->next;
	}
	
	return tmp->value;
}

void print(UnidirectionalList* ul)
{
	Node* tmp = ul->first;
	
	if(isEmpty(ul))
	{
		printf("It's empty.. \n");
		return;
	}
	
	printf("%d --> ", tmp->value);
		
	while(tmp->next != NULL)
	{
		tmp = tmp->next;
		printf("%d --> ", tmp->value);
	}
	
	printf("NULL \n");
}

UnidirectionalList* initializeList()
{
	UnidirectionalList* ul = malloc(sizeof(UnidirectionalList));
	ul->first = NULL;
	ul->last = NULL;
	
	return ul;
}

UnidirectionalList* copyList(UnidirectionalList* ul)
{
	UnidirectionalList* copiedList = initializeList();
	Node* tmp = ul->first;
	
	while(tmp != NULL)
	{
		addValue(tmp->value, copiedList);
		tmp = tmp->next;
	}
	
	return copiedList;
}

UnidirectionalList* merge(UnidirectionalList* l1, UnidirectionalList* l2)
{
	if(isEmpty(l1) && !isEmpty(l2))
	{
		UnidirectionalList* tmp;
		tmp = l1;
		l1 = l2;
		l2 = tmp;
	}
	
	UnidirectionalList* mergedList = initializeList();
	Node* l1Last = l1->last;
	
	l1->last->next = l2->first;
	l1->last = l2->last;
	mergedList = copyList(l1);

	l1->last = l1Last;
	l1->last->next = NULL;
	
	return mergedList;
}


void test1()
{
	UnidirectionalList* ul = initializeList();
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
	UnidirectionalList* ul = initializeList();
//	addValue(1, ul);
//	addValue(2, ul);
//	addValue(3, ul);
//	addValue(4, ul);
//	addValue(5, ul);
//	addValue(6, ul);
	removeValue(4, ul);
	print(ul);
	
	UnidirectionalList* u = initializeList();
	addValue(1, u);
	addValue(2, u);
	addValue(3, u);
	addValue(4, u);
	addValue(5, u);
	addValue(6, u);
	addValue(7, u);
	removeValue(1, u);
	print(u);
	
	UnidirectionalList* merged = merge(ul, u);
	print(merged);
	
	printf("By Value: %d\n", getValue(27, u));
	printf("Value from index: %d\n", getValueFromIndex(213, u));

}

void test3()
{
	UnidirectionalList* ul = initializeList();
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

void generateNodes(UnidirectionalList* tl, int k)
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
	
	UnidirectionalList* tl = initializeList();
	generateNodes(tl, listSize);
	
    QueryPerformanceFrequency(&frequency);
    QueryPerformanceCounter(&start);

	for(int i=0; i<check; i++)
		getValueFromIndex(chosenIndex, tl);

    QueryPerformanceCounter(&end);
    interval = (double) (end.QuadPart - start.QuadPart) / frequency.QuadPart;

	printf("--------- CHOSEN INDEX --------\n");
	printf("   listType = UnidirectionalList\n"); 
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
	
	UnidirectionalList* tl = initializeList();
	generateNodes(tl, listSize);
	
    QueryPerformanceFrequency(&frequency);
    QueryPerformanceCounter(&start);
	
	for(int i=0; i<check; i++)
		getValueFromIndex(chosenIndex, tl);
		
	QueryPerformanceCounter(&end);
    interval = (double) (end.QuadPart - start.QuadPart) / frequency.QuadPart;

	printf("--------- RANDOMIZE -----------\n");
	printf("   listType = UnidirectionalList\n"); 
	printf("   listSize = %d\n", listSize); 
	printf("   attempts = %d\n", check);
	printf("   for index = %d\n\n", chosenIndex);
	printf("Overall time: %.6f s\n", interval);
	printf("Average time for one element: %.6f ms\n", interval*1000/check);
	printf("-------------------------------\n\n");
}

int main()
{
//	test1();
//	test2();
//	test3();
	testTheSameElement();
	testRandomElement();
	return 0;
}