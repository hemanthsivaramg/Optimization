import java.io.*;
 import java.util.Scanner;
import java.net.*;

import javax.swing.JOptionPane;
public class Requester{
	Socket requestSocket;
	ObjectOutputStream out;
 	ObjectInputStream in;
 	String message;
 	String message1;
 	int pro;
 	int id=1;
 	int exit;
 	//Integer pro = new Integer(0);
 	boolean flag=false;

	int mat[][]=new int[20][5];
	Requester(){}
	void run()
	{
		try{
			//1. creating a socket to connect to the server
			requestSocket = new Socket("192.168.1.117", 2004);
			System.out.println("Connected to localhost in port 2004");
			//2. get Input and Output streams
			out = new ObjectOutputStream(requestSocket.getOutputStream());
			out.flush();
			in = new ObjectInputStream(requestSocket.getInputStream());
			//3: Communicating with the server
			int i=0;
			try{
			Scanner scan1 =new Scanner(System.in);//for integer
			message = (String)in.readObject();
			
			
					System.out.println("server>" + message);
					sendMessage("Hi my server");
					//JOptionPane.showMessageDialog(null,"connected to server");
					send(id);
			do{
						
						System.out.print("enter the RAM Space needed(MB)			 : ");
						mat[i][0]=scan1.nextInt();
						while(mat[i][0]<=0 || mat[i][0]>4096)
						{
							System.out.print("!!!Invalid input. . Enter again!!!		 : ");
							mat[i][0]=scan1.nextInt();
						}
					
					
					//send(mat[i][0]);
						System.out.print("enter a number of Processing Elements needed 	 : ");
						mat[i][1]=scan1.nextInt();
						while(flag==false)
						{
							flag=isPowerOfTwo(mat[i][1]);
							if(flag == false)
							{
								System.out.print("Invalid input... Enter again\n");
								mat[i][1]=scan1.nextInt();
							}
							
						}

						System.out.print("Resource utilization time(sec)		 	 : ");
						mat[i][2]=scan1.nextInt();
						while(mat[i][2]<0)
						{
							System.out.print("!!!Invalid input. . Enter again!!!		 : ");
							mat[i][2]=scan1.nextInt();
						}
						System.out.print("enter a disk space needed(GB)			 : ");
						mat[i][3]=scan1.nextInt();
						
						while(mat[i][3]<=0 || mat[i][3]>500)
						{
							System.out.print("!!!Invalid input. . Enter again!!!		 : ");
							mat[i][3]=scan1.nextInt();
						}
						//send(mat[i][1]);
						mat[i][4]=id;// this one denotes the client id
						send(mat[i]);
					//	i++;
						
						if(mat[i][0]>0 && mat[i][0]<=128)
						{
							message1="m1.small";
						//	System.out.print("\n"+message1);
						}
					
						else if(mat[i][0]>128 && mat[i][0]<=256)
						{
							message1="c1.Medium";
						//	System.out.print("\n"+message1);
							
						}
					
						else if(mat[i][0]>256 && mat[i][0]<=512)
						{
							message1="m1.large";
						//	System.out.print("\n"+message1);
						}
					
						else if(mat[i][0]>512 && mat[i][0]<=1024)
						{
							message1="m1.xlarge";
						//	System.out.print("\n"+message1);
						}
						else if(mat[i][0]>1024 && mat[i][0]<=2048)
						{
							message1="c1.large";
							//System.out.print("\n"+message1);
						}
						else if(mat[i][0]>2048 && mat[i][0]<=4096)
						{
							message1="c1.xlarge";
						//	System.out.print("\n"+message1);
						
						}
						
						System.out.print("\nThe above requirements of request "+(i+1)+" belongs to : "+message1+"\n");
						
					//System.out.print("process no\t\tRAM Space    \t\tPE \t\tInstructions(Million) \t\tDisk Space(GB) \n");
					//{
					//	System.out.print("process "+(i+1)+"\t\t"+message1+"\t\t"+mat[i][1]+"\t\t\t"+mat[i][2]+"\t\t\t"+mat[i][3]+"\n");
					//}
					i++;
					System.out.print("enter Y to continue and N to terminate");
					message = scan1.next();
					//sendMessage(message);
					if(message=="N")
					{
						System.out.print("bye....");
						//sendMessage(message);
						
					} 
					}
			while(!message.equals("N"));
			in.close();
			out.close();
			requestSocket.close();
		}
				catch(ClassNotFoundException classNot){
					System.err.println("data received in unknown format");
				}
			}
		catch(UnknownHostException unknownHost){
			System.err.println("You are trying to connect to an unknown host!");
		}
		catch(IOException ioException){
			ioException.printStackTrace();
		}
		finally{
			//4: Closing connection
			try{
				in.close();
				out.close();
				requestSocket.close();
			}
			catch(IOException ioException){
				ioException.printStackTrace();
			}
		}
	}
		void send(int msg)
	{
		
		try{
			out.writeObject(msg);
			out.flush();
			System.out.println("client>" + msg);
		}
		catch(IOException ioException){
			ioException.printStackTrace();
		}
	}
	
	void send(int msg[])
	{
		
		try{
			out.writeObject(msg);
			out.flush();
		//	System.out.println("client sjdhsds23>" + msg);
		}
		catch(IOException ioException){
			ioException.printStackTrace();
		}
	}
	
	void sendMessage(String msg)
	{
		try{
			out.writeObject(msg);
			out.flush();
			System.out.println("client>" + msg);
		}
		catch(IOException ioException){
			ioException.printStackTrace();
		}
	}

	private static boolean isPowerOfTwo(int n) {
		double logNbase2 =  Math.log(n)/Math.log(2);	
		int logNbase2Integer = (int) (Math.floor(logNbase2));
		
		if(logNbase2-logNbase2Integer==0 && n<8 && n>=1)
			return true;
		else
			return false;
	}
	public static void main(String args[])
	{
		Requester c1 = new Requester();

		c1.run();
	}
}