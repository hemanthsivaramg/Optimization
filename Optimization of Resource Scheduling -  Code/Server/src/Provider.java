import java.io.*;
import java.net.*;
import java.util.Scanner;
import java.util.Date;

import javax.swing.JOptionPane;

//import java.util.Scanner;
 class Th_p implements Runnable
{
	public Thread t;
	ObjectOutputStream out;
	ObjectInputStream in;
	 public static volatile  int mat[][]=new int[20][5];
	 static int temp2[] = new int [20];
	int i=0,m=0,k=1;
	static int flag=0;
	
	static int cost[] = new int [20];
		
	static int temp,no;
	double d;
	String message;
	static int counter=0;
	static String message1[] = new String[20];
	static String message2;
	static int d_size = 500 ,r_size = 4096, pe=8, mips ;
	static int res[]=new int[3];
	static int temp1[]=new int[3];
	static int temp4[]=new int[2];
	static int sarr[][]=new int[20][3];
	static int sarr1[][]=new int[20][3];
	
	static int lStartTime[] = new int[20];
	static int a_time[] = new int[20];
	String choice;
	Socket socket;
	Integer I;
	Th_p(){}
	Th_p(Socket conn,int re[]) throws IOException
	{
		res=re;
		socket=conn;
		t = new Thread(this);
		out = new ObjectOutputStream(socket.getOutputStream());
		in = new ObjectInputStream(socket.getInputStream());
		t.start();
	}
	public void run()
	{
		try{
			System.out.println("Connected to : " + socket.getInetAddress().getHostName());
		//	System.out.println("thread : "+Thread.currentThread().getName());

			out.flush();
			sendMessage("Connection successful");
			
			//JOptionPane.showMessageDialog(null,"Connected to : " + socket.getInetAddress().getHostName());

			capture();
			
			}
			catch (Exception E)
			{
				
			}
		//	JOptionPane.showMessageDialog(null," client id ");

		//JOptionPane.showMessageDialog(null," client id : "+m);

	}
	synchronized void capture()
	{
		try{
		message = (String)in.readObject();
		m=(Integer)in.readObject();				
		System.out.println("client in server>" + message+"ID"+m);
		JOptionPane.showMessageDialog(null,"Connected to : " + socket.getInetAddress().getHostName()+"\nclient id : "+m);

		do
		{
			int temp[];
		//	int temp1[][]= new int[20][6];
	//		int wc=0;
			
			//int k=1;
			System.out.println("\nReading...");
			temp=(int[]) in.readObject();
			
			 lStartTime[counter] = (int) new Date().getTime(); //start time
		//	 System.out.println("System time : "+lStartTime[counter]);
			 if(counter==0)
			 {
//				 System.out.println("System time : "+lStartTime[counter]);
				 a_time[counter]=0;
			 	 System.out.println("Arrival time : "+a_time[counter]);
			 }
			 else
			 {
	//			 System.out.println("System time : "+lStartTime[counter]);
				 d = (lStartTime[counter]-lStartTime[0])/1000;
				 a_time[counter]=(int) (d);
				 System.out.println("Arrival Time : "+a_time[counter]);
			 }
			
			mat[counter]=temp;
			
			
			if(mat[counter][0]>0 && mat[counter][0]<=128)
			{
				message1[counter]="m1.small";
				cost[counter]=10;
			//	System.out.print("\n"+message1);
			}
		
			else if(mat[counter][0]>128 && mat[counter][0]<=256)
			{
				message1[counter]="c1.medium";
				cost[counter]=15;
			//	System.out.print("\n"+message1);
				
			}
		
			else if(mat[counter][0]>256 && mat[counter][0]<=512)
			{
				cost[counter]=20;
				message1[counter]="m1.large";
			//	System.out.print("\n"+message1);
			}
		
			else if(mat[counter][0]>512 && mat[counter][0]<=1024)
			{
				cost[counter]=25;
				message1[counter]="m1.xlarge";
			//	System.out.print("\n"+message1);
			}
			else if(mat[counter][0]>1024 && mat[counter][0]<=2048)
			{
				cost[counter]=30;
				message1[counter]="c1.large";
				//System.out.print("\n"+message1);
			}
			else if(mat[counter][0]>2048 && mat[counter][0]<=4096)
			{
				cost[counter]=40;
				message1[counter]="c1.xlarge";
			//	System.out.print("\n"+message1);
			
			}
		
			
			System.out.print("Request : "+(counter+1));
			System.out.print("\t\tClient id: "+mat[counter][4]+"\t\tType of Res : "+message1[counter]+"\t\tPE : "+mat[counter][1]+"\t\tRes utilization time : "+mat[counter][2]+"\t\tDisk Space(GB) : "+mat[counter][3]+"\t\t\n");
			
			
			counter++;
			int counter1[] =new int[20];
			for(int i=0;i<counter;i++)
			{
				counter1[i]=i+1;
			}
			Scanner scan = new Scanner(System.in);
			System.out.print("allocation (y/n):");	
			choice = scan.nextLine();
			//System.out.print("entered input is : "+choice);
			if(choice.equals("y"))
			{
				System.out.print("calling applet...");
				AlgAnime alg = new AlgAnime();
				alg.init(a_time,mat,counter,counter1,d_size,r_size,pe);
			}
			//printer();
		}while(counter<20);	
		}
		catch(Exception e)
		{
			
		}
	}

	void sendMessage(String msg)
	{
		try{
			out.writeObject(msg);
			out.flush();
			System.out.println("server>" + msg);
		}
		catch(IOException ioException){
			ioException.printStackTrace();
		}
	}
public void string1(String s1)
	{
//	System.out.print("string provider1:"+s1);
		message2=s1;
//	System.out.print("string provider2:"+message2);
		
	}
	
	
public void callgraph1(int[] arr,int[] order)
		{
			if(flag==0)
			{
			for(int i=0;i<counter;i++)
			{
				
				sarr[i][0]=order[i];
				sarr[i][1]=arr[i];	
			}
						
			int in, out;
			 for(out=0; out<counter; out++) // out is dividing line
		     {

			    temp4[0] = sarr[out][0];
			    temp4[1] = sarr[out][1];    // remove marked person
			  
			    in = out;          // start shifting at out

		     while(in>0 && sarr[in-1][0]>temp4[0])
		      {
		    	 sarr[in][0]=sarr[in-1][0];
		    	 sarr[in][1] =sarr[in-1][1]; // shift item to the right
		      --in;          // go left one position
		      }
		     sarr[in][0] = temp4[0];
		     sarr[in][1] = temp4[1];
		     } // end for
			for(int i=0;i<counter;i++)
			{
				
				//sarr[i][1]*=mat[i][0];
				sarr[i][2]=mat[i][0];
			sarr[i][1]*=cost[i];
								
			}
			 
			int in1, out1;
			 for(out1=0; out1<counter; out1++) // out is dividing line
		     {

			    temp4[0] = sarr[out1][1];
			    
			    temp4[1] = sarr[out1][2];    // remove marked person
			  
			    in1 = out1;          // start shifting at out

		     while(in1>0 && sarr[in1-1][2]>temp4[1])
		      {
		    	 sarr[in1][2]=sarr[in1-1][2];
		    	 sarr[in1][1] =sarr[in1-1][1]; // shift item to the right
		      --in1;          // go left one position
		      }
		     sarr[in1][1] = temp4[0];
		     sarr[in1][2] = temp4[1];
		     } // end for
			 
			}
					 
			 if(flag==1)
			 {
				
				 for(int i=0;i<counter;i++)
					{
						
						sarr1[i][0]=order[i];
						sarr1[i][1]=arr[i];							
					}
								
					int in, out;
					 for(out=0; out<counter; out++) // out is dividing line
				     {

					    temp4[0] = sarr1[out][0];
					    temp4[1] = sarr1[out][1];    // remove marked person
					  
					    in = out;          // start shifting at out

				     while(in>0 && sarr1[in-1][0]>temp4[0])
				      {
				    	 sarr1[in][0]=sarr1[in-1][0];
				    	 sarr1[in][1] =sarr1[in-1][1]; // shift item to the right
				      --in;          // go left one position
				      }
				     sarr1[in][0] = temp4[0];
				     sarr1[in][1] = temp4[1];
				     } // end for
					for(int i=0;i<counter;i++)
					{
						
						//sarr[i][1]*=mat[i][0];
						sarr1[i][2]=mat[i][0];
					sarr1[i][1]*=cost[i];
										
					}
					 
					int in1, out1;
					 for(out1=0; out1<counter; out1++) // out is dividing line
				     {

					    temp4[0] = sarr1[out1][1];
					    
					    temp4[1] = sarr1[out1][2];    // remove marked person
					  
					    in1 = out1;          // start shifting at out

				     while(in1>0 && sarr1[in1-1][2]>temp4[1])
				      {
				    	 sarr1[in1][2]=sarr1[in1-1][2];
				    	 sarr1[in1][1] =sarr1[in1-1][1]; // shift item to the right
				      --in1;          // go left one position
				      }
				     sarr1[in1][1] = temp4[0];
				     sarr1[in1][2] = temp4[1];
				     } // end for
					 
					 System.out.print("\n********************************************** SUMMARY **********************************************\n");
						
						System.out.print("\nRequest \tClient ID \tType of Res \t\tBRS-Cost(Rs)\tPSO on BRS-Cost(Rs)\n");
						int sum1=0,sum2=0;
						for(int i=0;i<counter;i++)
						{
						System.out.print((i+1)+"\t\t"+mat[i][4]+"\t\t"+message1[i]+"\t\t"+sarr[i][1]+"\t\t"+sarr1[i][1]+"\n");
								sum1+=sarr[i][1];
								sum2+=sarr1[i][1];
								
						}
						
						System.out.print("\n\t\t\t\t\t\tTOTAL \t"+sum1+"\t\t"+sum2+"\n"); 
						 flag=0;
					
					 
				 GraphingData g = new GraphingData();
				 g.graph1(sarr,sarr1,message2,message1,counter);
			 }
			 flag=flag+1;
			 
			
		}

	
}

	
	public class Provider{
	ServerSocket providerSocket;
	Socket connection = null;
static int counter;
Th_p p[];
	ObjectOutputStream out;
	ObjectInputStream in;
	int mat[][] =new int[10][10];
	int temp,m,temp1,no;
	String message;
	//String message1;
	int res[]=new int[3];
	int temp2[]=new int[3];
	Provider(){}
	void run()
	{
		
		int d_size = 500 ,r_size = 4096, pe=8;
		System.out.print("\nResources....\n");
	
			System.out.print("Ram Size(MB) : "+r_size+"\t\t Disk Size(GB) : "+d_size+"\t\tPE : "+pe+"\t\t\n");
			
		
		
		try{
			p=new  Th_p[20];
			providerSocket = new ServerSocket(2004, 10);
				while(true)
			{
					
			System.out.println("Waiting for connection");
			p[counter]=new Th_p(providerSocket.accept(),res);
			counter++;
			}	
		}
		catch(IOException ioException){
			ioException.printStackTrace();
		}
		finally{
			//4: Closing connection
			try{
				in.close();
				out.close();
				providerSocket.close();
			}
			catch(IOException ioException){
				ioException.printStackTrace();
			}
		}
	}

	public static void main(String args[])
	{
		
		Provider server = new Provider();
		
		while(true){
			server.run();
		}
	}
}