 /*Author: Goutam Naik  (1239)
   Date: 10-3-17
   Program to check whether a number is palindrome or not*/

 #include<stdio.h>
 #include<math.h>

  void palindrome(int n, int d)   //function
 {
   int r,m=n,i=(d-1),s=0;
 
 while(i>=0)
 {r=n%10;
  s=s+(r*(pow(10,i)));
  n=n/10;
  i--;
 }
  if(s==m)
   {printf("%d is a palindrome\n",m);}  
 else 
   {printf("%d is not a palindrome\n ",m);}
 }

 main()
 { int num,dig,count=0;
  printf("Enter the number to be checked\n");
 scanf("%d",&num);    //input the number
 int number=num;
 
 while(num!=0)
 {int rem=num%10;
  num=num/10;
  count+=1;
  }                           //loop to claculate no. of digits

 dig=count;
 palindrome(number,dig);         //function call
 }


