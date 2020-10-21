/*
  autor: mahesh s patil
    date: 19/10/2015
    program:Read n numbers and reverse them.
    Solve it first using second array and then using only one array

*/
#include<stdio.h>
#define MAX_SIZE 50

void read_int_array(int[], int );
void print_int_array(int[], int );
void reverse_array_1(int a[], int size);
void reverse_array_2(int a[], int size);
main()
{
     int  n,i;
     int int_Arry[MAX_SIZE];
     printf("enter the size \n ");
     scanf("%d", &n);
     printf("enter the elements of array**********\n");
     read_int_array( int_Arry, n );
     printf("array before reversing\n");
     print_int_array( int_Arry, n );

     reverse_array_1( int_Arry, n);

     printf("after reversing\n");
     print_int_array( int_Arry, n );

     printf("re-reversing array with second function reverse_array_2()**** \n");
     printf("array before reversing\n");
     print_int_array( int_Arry, n );

     reverse_array_2( int_Arry, n);

     printf("after reversing\n");
     print_int_array( int_Arry, n );




}

//function to reverse array with help of another array
void reverse_array_1(int a[], int size)
{
    int b[MAX_SIZE], i;
    //loop to store values in array b with reverse order
    for(i=0; i<size; i++)
        b[size-1-i]=a[i];
    //copy the array b into array a after reversing
    for(i=0; i<size; i++)
        a[i]=b[i];
}

//function to reverse array within.
void reverse_array_2(int a[], int size)
{
    int i, temp;
    //loop to store values in array b with reverse order
    for(i=0; i<size/2; i++)
    {
        //swap respective array elements
        temp=a[i];
        a[i]=a[size-1-i];
        a[size-1-i]=temp;
    }

}

void read_int_array(int a[], int size)
{
     int i;
     for(i=0; i<size; i++)
     {
         printf("enter element %d:\n", i);
         scanf("%d", &a[i]);

     }
}


void print_int_array(int a[], int size)
{
    int i;
    for(i=0; i<size; i++)
         printf("%d ", a[i]);
    printf("\n");

}


