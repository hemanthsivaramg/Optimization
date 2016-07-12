/*****************************************************************
                        Statistics Frame
PURPOSE: This is the statistics view that displays empirical data from 
simulated CPU scheduling algorithm. This view is meant as a notebook for
students; therefore, this view is editable. 
*****************************************************************/
import java.awt.*;
import java.util.*;

public class statsFrame extends Frame {
   TextArea pad;
   Vector out;
   String P,A,S,F,Tq,Tqs;
   String convert;
   String P1;
   
   /*--------------------------------------------------------
                        Constructor
   PURPOSE:  To generate the statistics view frame
   --------------------------------------------------------*/
   public statsFrame() {
      super("Statistics View");
      this.setLayout(new BorderLayout());
      pad = new TextArea(30,30);
      pad.setEditable(true);
      this.add("Center", pad);
     
   } // set display

   /*--------------------------------------------------------
                    Report
   PURPOSE:  To report the data of a scheduling algorithm
   PARAMETERS: references the algorithm's title and finish queue
   --------------------------------------------------------*/
   public void report(Vector R,String title) {
      pad.appendText("\n"+title+"\n\n");
      out = R;
      display();
   } // report statistics to notepad

   /*--------------------------------------------------------
                    Handle Event
   PURPOSE:  To handle all events by dialog box
   --------------------------------------------------------*/
   public boolean handleEvent (Event evtObj) {
      if (evtObj.id == Event.WINDOW_DESTROY)
         this.dispose();     
      return true;
   } // handle destroy event

   /*--------------------------------------------------------
                    Display
   PURPOSE:  To append data to the notebook view
   --------------------------------------------------------*/
   private void display() {
      process temp;
      int TQ_arr[]=new int[20];
      int S_arr[]=new int[20];
      
      P = "Request  no :";
      A = "Arrival Time:";
      S = "Service Time:";
      F = "Finish Time :";
     Tq = "TA Time     :";
      Tqs = "Tq/Ts";
      buffer(P,A,S,F,Tq,Tqs);
      for (int j=0; j<out.size(); j++) {
         temp = (process)out.elementAt(j);
         
         P += temp.getName();
         A += temp.getArrival();
         S += temp.getService();
         F += temp.getFinish();
         TQ_arr[j]=temp.getTq();
         Tq += TQ_arr[j];
         //Tqs += temp.getTqs();
         buffer(P,A,S,F,Tq,Tqs);
      } // get info from each
      //System.out.print(P+"\n");
      P1=P;
      int l=P1.length();
      P1=P1.substring(13,l);
     // System.out.print(P1+"\n");
      P1=P1.trim();
      //System.out.print(P1+"\n");
      int arr1[];
      arr1=Vconvert(P1);
  //    for(int i1=0;i1<arr1.length;i1++)
    		// System.out.print(" num : "+arr1[i1]);

	   Th_p t= new Th_p();
	   t.callgraph1(TQ_arr,arr1);

      pad.appendText(P+"\n"+A+"\n"+S+"\n"+F+"\n"+Tq+"\n");
      //System.out.print("Turn around time :"+Tq);
    //  Tq.substring(arg0, arg1);
      
   } // display stats


   public int[] Vconvert(String toConvert)
   {
   	try
   	{
   	String[] splitedString=toConvert.split("\\s+");
   	int[] toReturn=new int[splitedString.length];
   	for (int i = 0; i < toReturn.length; i++) {
   		toReturn[i]=Integer.parseInt(splitedString[i]);
   	}
   	return toReturn;
   	}
   	catch (Exception e) {
   		System.out.println(e);
   	}
   	return null;
   	
   }

   /*--------------------------------------------------------
                    Buffer
   PURPOSE:  To buffer white space in order to create columns
   PARAMETERS: references data string of process's name, arrival time, 
service time, turnaround time, and turnaround ratio
   --------------------------------------------------------*/
   private void buffer(String p,String a, String s, String f, String tq, String tqs) {
      int max = Math.max(P.length(),Math.max(A.length(),Math.max(S.length(),Math.max(F.length(),
                Math.max(Tq.length(),Tqs.length())))));
      max += 5;
      P = space (P,max);
      A = space (A,max);
      S = space (S,max);
      F = space (F,max);
      Tq = space (Tq,max);
      Tqs = space (Tqs,max);
      
   } // format with buffer spaces, left justfied

   /*--------------------------------------------------------
                    Space
   PURPOSE:  To ensure all columns are of equal length
   --------------------------------------------------------*/
   private String space(String x, int m) {
      while (x.length() < m)
         x += " ";
      return x;
   } // pad with spaces

} // stats Frame class
