 /*Author: Goutam Naik  (1239)
   Date: 10-3-17
   Program to reverse a number*/

 #include<stdio.h>
 #include<math.h>
 int revnum(int n,int d);  //function declartion

 main()
 { int num,count=0;
 printf("Enter the number to be reversed\n");
 scanf("%d",&num);    //input the number
 int number=num;

 while(num!=0)
 {int rem=num%10;
  num=num/10;
  count+=1;
  }

 int dig=count;
 int r= revnum(number,dig);         //function call
 printf("Reversed number = %d\n ",r);

 }

 int revnum(int n,int d)   //function
 
 { int r,s=0,i=(d-1);
 while(i>=0)
 {r=n%10;
 s=s+(r*(pow(10,i)));
 n=n/10;
 i--;
 }    
 return s; 

 }
