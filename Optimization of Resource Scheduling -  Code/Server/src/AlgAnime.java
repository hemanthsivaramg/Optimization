import java.awt.*;
import java.applet.*;
import java.util.Arrays;
import java.util.Vector;
import java.util.Observer;
import java.util.Observable;

/* <applet code="CPU" width=10 height=10></applet> virtually not embedded in web page*/

public class AlgAnime extends Applet implements Observer {
   animeFrame anime;
   statsFrame stats;
   inputFrame input;

   Scheduler lesson;
   Vector Q;
   int temp[][] = new int[20][6];
   int wait[][] = new int[20][6];
	char cc[] = new char[20];
   int count,dsize,rsize,pe,wcount;

  // @SuppressWarnings("deprecation")
   public void init(int[] ar_t,int[][] mat,int counter,int[] counter1,int disk,int ram, int p)
   {
		System.out.print("Runnin init\n\n");
		//int count;
		count=counter;
		dsize=disk;
		rsize=ram;
		pe=p;
	
		//wcount=wc;
    for(int i=0;i<counter;i++)
	{
		temp[i]=mat[i];
	}	
		
	char a_t[]=new char[20];
	for(int i=0;i<counter;i++)
	{
		a_t[i]= (char)ar_t[i];
	}
	
	//for(int i=0;i<wc;i++)
	//{
//		wait[i]=temp1[i];
	//}
	
	char dltime[]=new char[20];
	for(int i=0;i<counter;i++)
	{
		dltime[i]=(char)mat[i][2];
	}
		
	//String arr1;
	//String arr2; 
	//String arr3;
	for(int i=0;i<counter;i++)
	{
		 cc[i] = (char) counter1[i];
	}
	//arr1 = arrayToString(a_t);
	//arr2 = arrayToString(dltime);
    //arr3 = arrayToString1(cc);
	anime = new animeFrame();
      anime.show();
      anime.setLocation(320,0);
      anime.resize(400,300);

      stats = new statsFrame();
      stats.pack();
      stats.show();
      stats.setLocation(740,0);
      stats.resize(300,300);

      input = new inputFrame(this,a_t,dltime,cc,counter);
      input.show();
      input.setLocation(0,0);
      input.resize(300,300);

      lesson=null;
      Q = new Vector(1,1);
   }
// set up lecture
	public String arrayToString(int[] stringarray){
	  String str= "";
	  for (int i = 0; i < stringarray.length; i++) {
	  str = str+stringarray[i];
	  if(i<stringarray.length-1)
	  {
		  str=str+"\n";
	  }
	  }
	  return str;
}
	/*public String arrayToString1(char[] stringarray){
		String letter="";
		 for (int i = 0; i < stringarray.length; i++)
		 {
		 byte[] bytes = new byte[1];
		 bytes[0] = (byte) stringarray[i];
		 letter+=bytes[0];
		 //System.out.print(letter);
		 }
		 System.out.print("\n");
		 }
	}*/

   /*--------------------------------------------------------
                        Update 
   PURPOSE:  To respond to user input via GUIs; overriden version of system event handler
   PARAMTERS: references argument specified by user, and associates GUI handler
   --------------------------------------------------------*/
   public void update(Observable obj, Object arg) {
      if (arg instanceof String) {
         if (((String)arg).equals("pause") && lesson!=null)
            lesson.thread.suspend();
         else if (((String)arg).equals("resume") && lesson!=null)
            lesson.thread.resume();
         else if (((String)arg).equals("clear")) {
            input.resetGUI();
            Q.setSize(0);
            lesson.resetQ();
            lesson.thread.stop();
            lesson = null;               
         } // reset all vectors
         else if (((String)arg).equals("quit")) {
            input.hide();   input.dispose();
            anime.hide();   anime.dispose();
            stats.hide();   stats.dispose();
         } // quit applet
      } // string event
      else if (arg instanceof Packet) {
         getParms((Packet)arg);
         anime.cleargrid();
         int click = anime.setgrid(Q);
String s1,s2;
s1="BRS";
s2="PSO";
//s3="Optimization Techinque1";
Th_p t= new Th_p();

	

	if (((Packet)arg).getAlg().equals("BRS")) {
            anime.setTitle(s1);
            //Th_p t= new Th_p();
            t.string1(s1);
            lesson = new BRS(Q, stats, anime, input,click,temp,count,dsize,rsize,pe);
         } // do NONPSO
         else if (((Packet)arg).getAlg().equals("PSO")) {
            anime.setTitle(s2);
            t.string1(s2);
            lesson = new PSO(Q, stats, anime, input,click,temp,count,dsize,rsize,pe);
         } // do PSO
         /*else if (((Packet)arg).getAlg().equals("PSO1")) {
             anime.setTitle(s3);
             t.string1(s3);
             lesson = new PSO1(Q, stats, anime, input,click,temp,count,dsize,rsize,pe);
          } // do SRT
        
         else if (((Packet)arg).getAlg().equals("FCFS")) {            
        	 anime.setTitle("First Come First Serve Scheduling");
        	 lesson = new FCFS (Q, stats, anime,input,click);
        	 } // do FCFS*/
         } // hefty data packet->run
   } // handle event

   /*--------------------------------------------------------
                        Get Parms utility
   PURPOSE:  To sort out input given by user and set up input queue
   PARAMETERS: information packet GUI given by user
   --------------------------------------------------------*/
   private void getParms(Packet in) {  
      String name, t1, t2, t3, mark ="\n";
      Integer I = new Integer(0);
      int pos, a, s;
      boolean empty=false;
      t1 = in.getProc();
      t2 = in.getArriv();
      t3 = in.getServ();
 
      do {
         pos = t1.indexOf(mark);
         if (pos<0) {
            name = t1;
            empty = true;
         }
         else
            name = t1.substring(0,pos);
         t1 = t1.substring(pos+1);

         pos = t2.indexOf(mark);
         if (pos<0) {
            a = I.parseInt(t2);
            empty = true;
         }
         else
            a = I.parseInt(t2.substring(0,pos));
         t2 = t2.substring(pos+1);

         pos = t3.indexOf(mark);
         if (pos<0) {
            s = I.parseInt(t3);
            empty = true;
         }
         else
            s = I.parseInt(t3.substring(0,pos));
         t3 = t3.substring(pos+1);

         process temp = new process(name, a, s);
         Q.addElement(temp);         
      } while (!empty);      
   } // set up Queue

} // algorithm animation main driver
