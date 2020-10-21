import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;


public class Main {
	public static void main(String args[]) throws UnknownHostException, IOException{
		int number,temp;
		Scanner sc= new Scanner(System.in);
		Socket s= new Socket("10.1.4.60",1342);
		Scanner sc1 =new  Scanner(s.getInputStream());
		System.out.print("Enter any number : ");
		number=sc.nextInt();
		PrintStream p= new PrintStream(s.getOutputStream());
		p.println(number);
		temp=sc1.nextInt();
		System.out.println(temp);
		
	}

}
