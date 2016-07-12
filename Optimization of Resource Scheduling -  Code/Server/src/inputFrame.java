import java.awt.*;
import java.util.Observable;

public class inputFrame extends Frame {
   TextArea proc,arriv,serv;
   Panel knobs;
   Choice alg;
   CheckboxGroup functions;
   Checkbox[] fun;
   Packet info;
   GUI vigil;
   String PrevBut;

   public inputFrame(AlgAnime parent,char[] arr1,char[] arr2,char[] arr3,int counter) {
      super("Input View");
      vigil = new GUI();
      vigil.addObserver((java.util.Observer)parent);

     String sampleP = asciitostring(arr3);
     String sampleA = asciitostring(arr1);
     String sampleS = asciitostring(arr2);
     
     sampleP=sampleP.trim();
     sampleA=sampleA.trim();
     sampleS=sampleS.trim();
      sampleA="0\n"+sampleA;
      
      proc = new TextArea(30,10);
      proc.setEditable(true);
      proc.appendText(sampleP);

      arriv = new TextArea(30,5);
      arriv.setEditable(true);
      arriv.appendText(sampleA);
     
      
      serv = new TextArea(30,5);
      serv.setEditable(true);
      serv.appendText(sampleS);

      alg = new Choice();
      alg.addItem("BRS");
      alg.addItem("PSO");
     // alg.addItem("PSO1");
      

      functions = new CheckboxGroup();
      fun = new Checkbox[5];
      fun[0] = new Checkbox("clear",functions,false);
      fun[1] = new Checkbox("run",functions,false);
      fun[2] = new Checkbox("pause",functions,false);
      fun[3] = new Checkbox("resume",functions,false);
      fun[4] = new Checkbox("quit",functions,false);
      PrevBut = ""; // init

      knobs = new Panel();
      knobs.setLayout(new FlowLayout(FlowLayout.CENTER));
      knobs.add(alg);
      knobs.add(fun[0]);
      knobs.add(fun[1]);
      knobs.add(fun[2]);
      knobs.add(fun[3]);
      knobs.add(fun[4]);

      Panel labels = new Panel();
      labels.setLayout(new BorderLayout());
      labels.add("West", new Label("Request no :"));     
      labels.add("Center", new Label("Arrivial time:"));
      labels.add("East", new Label("Service time :")); 

      this.setLayout(new BorderLayout());
      this.add("North",labels);
      this.add("West", proc);
      this.add("Center",arriv);
      this.add("East",serv);
      this.add("South", knobs);      
   } // set display
public String asciitostring(char[] arr)
{
	 
    String letter="";
    for (int i = 0; i < arr.length; i++)
	 {
  	  if((int)arr[i]!=0)
  	  {

  		  byte[] bytes = new byte[1];
  		  bytes[0] = (byte) arr[i];
  		  letter+=bytes[0];
	 //System.out.print(letter);
	 if(i<arr.length-1)
	  {
		  letter=letter+"\n";
	  }
  	  }
	  }
    return letter;
}
   public java.lang.String removeTrailingZeros( java.lang.String str ){
	   if (str == null){
	   return null;}
	   char[] chars = str.toCharArray();
	   int length,index ;
	   length = str.length();
	   index = length -1;
	   for (; index >=0;index--)
	   {
	   if (chars[index] !='0')
	   {
		   break;
	   }
	   }
	   
	   	return (index == length-1) ? str :str.substring(0,index+1);
	   }
/*--------------------------------------------------------
   Handle Event
PURPOSE:  To handle all events by dialog box
--------------------------------------------------------*/
   
   public boolean handleEvent (Event evtObj) {
      if (evtObj.id == Event.WINDOW_DESTROY) {
         this.dispose();
         return true;
      } // destroy button
      else if (evtObj.id==Event.ACTION_EVENT)
         if (evtObj.target==fun[0]) 
         {
        	proc.setText("");
        	arriv.setText("");
            serv.setText("");
            fun[1].enable();
            String cmd = functions.getCurrent().getLabel();
            vigil.input(cmd);
            return true;
         } // handle clear button
         else if (evtObj.target==fun[1]) {
            fun[1].disable();
            info = new Packet(proc.getText(), arriv.getText(), serv.getText(), alg.getSelectedItem());
            vigil.input(info);
            proc.setEditable(false);
            arriv.setEditable(false);
            serv.setEditable(false);
            return true;
         } // handle run button
         else if (evtObj.target==fun[2] || evtObj.target==fun[3]) {
            String cmd = functions.getCurrent().getLabel();
            if (PrevBut.equals(cmd))
               return false;
            else {
               PrevBut = cmd; // stagger for next event
               vigil.input(cmd);
               return true;
            } // balance pause to resume request
         } // handle pause/resume buttons
         else if (evtObj.target==fun[4]) {
            vigil.input("quit");
            return true;
         } // handle quit option
      return false;      
   } // handle event

   public void resetGUI() {
      proc.setEditable(true);
      serv.setEditable(true);
      arriv.setEditable(true);
      fun[1].enable();
   } // allow user input
} // input Frame
/*--------------------------------------------------------
Handle Event
PURPOSE:  To handle all events by dialog box
--------------------------------------------------------*/
