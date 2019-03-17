#include <stdbool.h>

typedef struct UnidirectionalList UnidirectionalList;
typedef struct Node Node;

struct UnidirectionalList
{
	Node *first;
	Node *last;
};
 
struct Node
{
	int value;
	Node *next;
};

bool isEmpty(UnidirectionalList* ul);
void addValue(int value, UnidirectionalList* ul);
void removeValue(int value, UnidirectionalList* ul);
int getValue(int value, UnidirectionalList* ul);
int getValueFromIndex(unsigned int index, UnidirectionalList* ul);
void print(UnidirectionalList* ul);
UnidirectionalList* initializeList();
UnidirectionalList* copyList(UnidirectionalList* ul);
UnidirectionalList* merge(UnidirectionalList* l1, UnidirectionalList* l2);