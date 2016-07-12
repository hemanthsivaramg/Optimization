import java.util.Vector;

public class BRS extends Scheduler implements Runnable {

	int temp[][] = new int[20][5];
	int waitq[][] = new int[20][6];
	int waitn[][] = new int [1][6];
	int counter,dsize,rsize,pe,wcount;
   public BRS(Vector q, statsFrame s, animeFrame a, inputFrame i, int c,int[][]mat,int count,int di,int ra,int p) {
      super(q,s,a,i,c);
      counter=count;
    	dsize=di;
    	rsize=ra;
    	pe=p;
    	wcount=0;
    	
    	for(int j=0;j<counter;j++)
    	{
    		temp[j]=mat[j];
    	}
    	for(int j=0;j<6;j++)
    	{
    		waitn[0][j]=0;
    	}
    
      thread = new Thread(this,"BRS");
      thread.start();
   } // constructor

   /*--------------------------------------------------------
                        Thread Run
   PURPOSE:  To run light-weight thread simulation
   --------------------------------------------------------*/
   public void run() {
      int all=Q.size(), interval=0, next=0;
      boolean interrupt = false;      
  	System.out.print("\n\n ************* Best Resource Selection ************** \n\n");

      do {
         clock++;
         T = processready(clock);
         if (T != null) {
            readyQ.addElement(T);
            Q.removeElement(T);
            an.upstatus("Time "+T.getArrival()+":Request "+T.getName()+" ready.");
            int p = Integer.parseInt(T.getName());
            
            if(dsize>=temp[p-1][3] && rsize>=temp[p-1][0] && pe>=temp[p-1][1])
            {
            	dsize-=Math.pow(2,Math.ceil(Math.log(temp[p-1][3])/Math.log(2)));
            	//dsize-=temp[p-1][3];
            	rsize-=Math.pow(2,Math.ceil(Math.log(temp[p-1][0])/Math.log(2)));
            	//System.out.print("\nSize\n"+rsize);
            	pe-=temp[p-1][1];
            	System.out.print("Resources for the Request "+p+" of Client : "+temp[p-1][4]+" is being allocated\n");
            	System.out.print("Resources after allocation.....\n");
            	System.out.print("\t\tRAM Space(MB) : "+rsize+"\t\tPE : "+pe+"\t\tDisk Space(GB) : "+dsize+"\t\t\n");
            }
            else
            {	
				System.out.print("Resources are being used by other processes...\n");
				System.out.print("Request"+(counter+1)+"buffered in waiting queue...\n");
				for(int i=0;i<5;i++)
				{
					waitq[wcount][i]=temp[p-1][i];
				}
				waitq[wcount][5]=p-1;
				wcount++;
            }
            try { Thread.sleep(1000); } catch (InterruptedException e) {};
         } // put in ready queue
         if (idle || interrupt) {
            if (interrupt) {
               interrupt = false;
               interval = 0;
            } // reset interval
            if (readyQ.size()==0)               
               continue;
            if (next < readyQ.size()-1)
               next++;
            else
               next=0;
            P = (process)readyQ.elementAt(next);
            idle = false;                       
         } // get next process
         P.servicing();
         interval++;
         an.drawbar(P,clock);
         an.upstatus("Time "+clock+":Serving process "+P.getName()+".");
         try { Thread.sleep(1000); } catch (InterruptedException ex) {};
         if (P.getTminus()==0) {
            an.upstatus("Time "+(clock+1)+":Request "+P.getName()+" done.");
            int p = Integer.parseInt(P.getName());
            dsize+=Math.pow(2,Math.ceil(Math.log(temp[p-1][3])/Math.log(2)));
            rsize+=Math.pow(2,Math.ceil(Math.log(temp[p-1][0])/Math.log(2)));
            pe+=temp[p-1][1];
            System.out.print("Request "+p+" done.\n");
            System.out.print("Resources after deallocation.....\n");
			System.out.print("\t\tRAM Space(MB) : "+rsize+"\t\tPE : "+pe+"\t\tDisk Space(GB) : "+dsize+"\t\t\n");
			
			for(int i=0;i<wcount;i++)
			{
				if(waitq[i] != waitn[0])
				{
					if(dsize>=waitq[i][3] && rsize>=waitq[i][0] && pe>=waitq[i][1])
					{
						dsize-=Math.pow(2,Math.ceil(Math.log(waitq[i][3])/Math.log(2)));
						//dsize-=waitq[i][3];
						rsize-=Math.pow(2,Math.ceil(Math.log(waitq[i][0])/Math.log(2)));
						//rsize-=waitq[i][0];
						pe-=waitq[i][1];
		            
						System.out.print("Request "+waitq[i][5]+" has been taken for allocation from waitin queue\n");
						System.out.print("Resources after allocation.....\n");
						System.out.print("\t\tRAM Space(MB) : "+rsize+"\t\tPE : "+pe+"\t\tDisk Space(GB) : "+dsize+"\t\t\n");
						
						waitq[i] = waitn[0];
		            }
				}
			}
            try { Thread.sleep(1000); } catch (InterruptedException e) {};
            P.report(clock+1); // anticipate completion
            finishQ.addElement(P);
            readyQ.removeElement(P);
            next--; // recenter ptr
            idle = true;            
            interval=0;
         } // put in finish queue
         else if (interval==6)
            interrupt = true;                    
      } while (finishQ.size()<all);
      an.upstatus("Algorithm finished.");
      st.report(finishQ,"Best Resourse Selection");
      in.resetGUI();
   } // run thread

} // BRS class
