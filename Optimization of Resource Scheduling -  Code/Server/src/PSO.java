import java.util.Vector;

public class PSO extends Scheduler implements Runnable {
	
	int temp[][] = new int[20][5];
	int waitq[][] = new int[20][6];
	int waitn[][] = new int [1][6];
	int counter,dsize,rsize,pe,wcount;
	int pbest, gbest,fit=0;
	public PSO(Vector q, statsFrame s, animeFrame a, inputFrame i, int c,int[][]mat,int count,int di,int ra,int p) {
	super(q,s,a,i,c);
    counter=count;
  	dsize=di;
  	rsize=ra;
  	pe=p;
  	wcount=0;
  	//wcount=waitc;
  	for(int j=0;j<counter;j++)
  	{
  		temp[j]=mat[j];
  	}
  	for(int j=0;j<6;j++)
  	{
  		waitn[0][j]=0;
  	}
      thread = new Thread(this,"PSO");
      thread.start();
   } // constructor

   public void run() {
      int all=Q.size();
      boolean interrupt=false;
      process X=null; // preempted process
   	System.out.print("\n\n ************* Particle Swarm Optimization ************** \n\n");

      for(int i=0;i<all;i++)
      {
    	  fit+=((process)Q.elementAt(i)).getService();
      }
      fit=fit/all;
      do {
         clock++;
         T = processready(clock);
         if (T != null) {
            readyQ.addElement(T);
            Q.removeElement(T);
            interrupt = true;
            an.upstatus("Time "+T.getArrival()+":Request "+T.getName()+" ready.");
            int p = Integer.parseInt(T.getName());
            if(p==1)
            {
            	//p[]
            	pbest=gbest=T.getService();
            }
            if(dsize>=temp[p-1][3] && rsize>=temp[p-1][0] && pe>=temp[p-1][1])
            {
            	dsize-=Math.pow(2,Math.ceil(Math.log(temp[p-1][3])/Math.log(2)));
            	rsize-=Math.pow(2,Math.ceil(Math.log(temp[p-1][0])/Math.log(2)));
            	//rsize-=temp[p-1][0];
            	pe-=temp[p-1][1];
            	//System.out.print("Proces "+p+" done.\n");
            	System.out.print("Resources after allocation.....\n");
            	System.out.print("\t\tRAM Space(MB) : "+rsize+"\t\tPE : "+pe+"\t\tDisk Space(GB) : "+dsize+"\t\t\n");
            }
            else
            {	
				System.out.print("Resources are being used by other processes...\n");
				System.out.print("Process"+(counter+1)+"buffered in waiting queue...\n");
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
               interrupt=false;
               if (X!=null)
                  readyQ.addElement(X);
            } // reenter process
            if (readyQ.size()==0)
               continue;
            if (idle)
               idle = false;
            
            P = (process)readyQ.firstElement();
            
            //int sr = P.getService();
            for (int j=1; j<readyQ.size(); j++)
            {
            	pbest=((process)readyQ.elementAt(j)).getTminus();
               if (gbest> pbest) {
                     P = (process)readyQ.elementAt(j);
                     gbest = P.getTminus();
               }
               } // find earliest process with shortest remaining time
            int sr= gbest;
            for (int j=1; j<readyQ.size(); j++)
                if (sr> ((process)readyQ.elementAt(j)).getService()) {
                      P = (process)readyQ.elementAt(j);
                      sr = P.getService();
                }
            
            readyQ.removeElement(P);            
         } // put in run state
         P.servicing();
         an.drawbar(P,clock);
         an.upstatus("Time "+clock+":Serving Request "+P.getName()+".");
         try { Thread.sleep(1000); } catch (InterruptedException ex) {};
         if (P.getTminus()==0) {
            an.upstatus("Time "+(clock+1)+":Request "+P.getName()+" done.");
            int p = Integer.parseInt(P.getName());
            dsize+=Math.pow(2,Math.ceil(Math.log(temp[p-1][3])/Math.log(2)));
            rsize+=Math.pow(2,Math.ceil(Math.log(temp[p-1][0])/Math.log(2)));
            //rsize+=temp[p-1][0];
            pe+=temp[p-1][1];
            System.out.print("Request "+p+" done.\n");
            System.out.print("Resources after deallocation.....\n");
			System.out.print("\t\tRAM Space (MB): "+rsize+"\t\tPE : "+pe+"\t\tDisk Space(GB) : "+dsize+"\t\t\n");
			
			for(int i=0;i<wcount;i++)
			{
				if(waitq[i] != waitn[0])
				{
					if(dsize>=waitq[i][3] && rsize>=waitq[i][0] && pe>=waitq[i][1])
					{
						dsize-=Math.pow(2,Math.ceil(Math.log(waitq[i][3])/Math.log(2)));
						rsize-=Math.pow(2,Math.ceil(Math.log(waitq[i][0])/Math.log(2)));
						//dsize-=waitq[i][3];
						//rsize-=waitq[i][0];
						pe-=waitq[i][1];
		            
						System.out.print("Request "+waitq[i][5]+" has been taken for allocation from waitin queue\n");
						System.out.print("Resources after allocation.....\n");
						System.out.print("\t\tRAM Space : "+rsize+"\t\tPE : "+pe+"\t\tDisk Space : "+dsize+"\t\t\n");
						
						waitq[i] = waitn[0];
		            }
				}
			}
            /*for(int i=0;i<counter;i++)
            {
            	if(i+1 == p)
            	{
            		dsize+=temp[]
            	}
            }*/
            	
			
            try { Thread.sleep(1000); } catch (InterruptedException e) {};
            P.report(clock+1); // anticipate completion
            finishQ.addElement(P);
            idle = true;
            X=null;
         } // put in finish queue
         else
            X=P;         
      } while (finishQ.size()<all);
      
     
      an.upstatus("Algorithm finished.");
      st.report(finishQ,"PSO");
      in.resetGUI();
   } // run thread
   
   public void particle(int[][] temp)
   {
	   int localParticles[][] = temp;    
	   int best=0;
       for (int i = 0; i < localParticles.length; i++)
       {
           CalculateCost();
           best=UpdateHistory(localParticles[i][3]);
       }
       for (int i = 0; i < localParticles.length; i++)
           if (best>0)
               UpdateVelocityAndPosition(best);
          
   }

private void UpdateVelocityAndPosition(int best) {
	// TODO Auto-generated method stub
	
}

private int UpdateHistory(int local) {
	// TODO Auto-generated method stub
	 // Update history of the swarm:
	int BestCost = 3;
	int Cost = local;
    if (Cost < BestCost)
    {
        BestCost = Cost;
        //BestPosition = this[0].BestPosition;
    }
	return BestCost;
}

private void CalculateCost() {
	// TODO Auto-generated method stub
	
}
   }

 // PSO class
