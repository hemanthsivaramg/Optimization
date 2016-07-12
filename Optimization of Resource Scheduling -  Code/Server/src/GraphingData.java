import java.awt.*;

import java.awt.font.*;
import java.awt.geom.*;
import javax.swing.*;
 
public class GraphingData extends JPanel {
	static int[][] data1;
	static int[][] data2;
    final int PAD = 20;
    static int sum1=0;
    static int sum2=0;
    static String name[]= new String[20];
    String n1,n2;
    String string1="BRS - Cost : ";
    String string2="PSO on BRS - Cost : ";
    
    
    String str1;
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D)g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                            RenderingHints.VALUE_ANTIALIAS_ON);
        int w = getWidth();
        int h = getHeight();
        n1="0";
        n2="0";
        // Draw ordinate.
        g2.draw(new Line2D.Double(PAD, PAD, PAD, h-PAD));
        // Draw abcissa.
        g2.draw(new Line2D.Double(PAD, h-PAD, w-PAD, h-PAD));
        // Draw labels.
        Font font = g2.getFont();
        FontRenderContext frc = g2.getFontRenderContext();
        LineMetrics lm = font.getLineMetrics("0", frc);
        float sh = lm.getAscent() + lm.getDescent();
        // Ordinate label.
        String s = "Environmental Utilization";
        float sy = PAD + ((h - 2*PAD) - s.length()*sh)/2 + lm.getAscent();
        for(int i = 0; i < s.length(); i++) {
            String letter = String.valueOf(s.charAt(i));
            float sw = (float)font.getStringBounds(letter, frc).getWidth();
            float sx = (PAD - sw)/2;
            g2.drawString(letter, sx, sy);
            sy += sh;
        }
        // Abcissa label.
        s = "Data Size";
        sy = h - PAD + (PAD - sh)/2 + lm.getAscent();
        float sw = (float)font.getStringBounds(s, frc).getWidth();
        float sx = (w - sw)/2;
        g2.drawString(s, sx, sy);
        // Draw lines.
        double xInc = (double)(w - 2*PAD)/(data1.length-1);
        double xscale = (double)(w - 2*PAD)/getMax1();
        double yscale = (double)(h - 2*PAD)/getMax2();
 
        g2.setPaint(Color.green.darker());
        for(int i = 0; i < data1.length-1; i++) {
           // double x1 = PAD + i*xInc;
        	double x1 = PAD + xscale*data1[i][2];
        	double y1 = h - PAD - yscale*data1[i][1];
           // double x2 = PAD + (i+1)*xInc;
        	double x2 = PAD + xscale*data1[i+1][2];
        	double y2 = h - PAD - yscale*data1[i+1][1];
            if(x2==20) break;
        	g2.draw(new Line2D.Double(x1, y1, x2, y2));
        
        }
        
        g2.setPaint(Color.blue.darker());
        for(int i = 0; i < data2.length-1; i++) {
           // double x1 = PAD + i*xInc;
        	double x1 = PAD + xscale*data2[i][2];
        	double y1 = h - PAD - yscale*data2[i][1];
           // double x2 = PAD + (i+1)*xInc;
        	double x2 = PAD + xscale*data2[i+1][2];
        	double y2 = h - PAD - yscale*data2[i+1][1];
            if(x2==20) break;
        	g2.draw(new Line2D.Double(x1, y1, x2, y2));
        
        }
        
        
        // Mark data points.
        g2.setPaint(Color.red);
        String s1;
        int xx1=0,yy1=0;
        int xx2=0,yy2=0;
        for(int i = 0; i < data1.length; i++) {
            //double x = PAD + i*xInc;
            double x = PAD + xscale*data1[i][2];
            double y = h - PAD - yscale*data1[i][1];
            g2.fill(new Ellipse2D.Double(x-2, y-2, 4, 4));
            xx1= data1[i][2];
            yy1= data1[i][1];
            
            xx2=(int) x;
            yy2=(int) y;
            //String n2;
           
            n2=name[i];
         //   if(n2.equals("null"))
          //  	n2="0";
            s1="("+n2+":"+yy1+")";
            g2.drawString(s1, xx2-30, yy2+20);
        }
        for(int i=0;i<data1.length;i++)
        	sum1+=data1[i][1];
      
        string1+=sum1;
        g2.setPaint(Color.green.darker());
        g2.drawString(string1, 40, 20);
        string1=null;
        sum1=0;
        string1="BRS - Cost : ";
        
        
        
        //mark data points
        g2.setPaint(Color.red);
        String s11;
        int xx11=0,yy11=0;
        int xx22=0,yy22=0;
        for(int i = 0; i < data2.length; i++) {
            //double x = PAD + i*xInc;
            double x = PAD + xscale*data2[i][2];
            double y = h - PAD - yscale*data2[i][1];
            g2.fill(new Ellipse2D.Double(x-2, y-2, 4, 4));
            xx11= data2[i][2];
            yy11= data2[i][1];
            
            xx22=(int) x;
            yy22=(int) y;
            //String n1;
           

            n1=name[i];
           // if(n1.equals("null"))
            //	n1="0";
           
            s11="("+n1+":"+yy11+")";
            g2.drawString(s11, xx22-30, yy22+20);
        }
        for(int i=0;i<data2.length;i++)
        	sum2+=data2[i][1];
        
        
       string2+=sum2;
        g2.setPaint(Color.blue.darker());
        g2.drawString(string2, 40, 40);
        string2=null;
        sum2=0;
        string2="PSO on BRS - Cost : ";
        
       // g2.drawString(str1, 40, 35);
       // str1=null; 
        
    }
    
 
    private int getMax1() {
        int max = -Integer.MAX_VALUE;
        for(int i = 0; i < data1.length; i++) {
            if(data1[i][2] > max)
                max = data1[i][2];
        }
        return max;
    }
    private int getMax2() {
        int max1= -Integer.MAX_VALUE;
        int max2= -Integer.MAX_VALUE;
        for(int i = 0; i < data1.length; i++) {
            
            if(data1[i][1] > max1)
                max1 = data1[i][1];
        }
        for(int i = 0; i < data2.length; i++) {
            
            if(data2[i][1] > max2)
                max2 = data2[i][1];
        }
        if(max1>max2)
        	return max1;
        else
        	return max2;
    }
 
 GraphingData(){}
 public void graph1(int[][] dat1,int[][] dat2,String s1,String[] message1,int counter) {
	 data1=dat1;
	 data2=dat2;
	/* for(int i=0;i<data1.length;i++)
	 {
		 System.out.print("\n\nstring data1 0 :"+data1[i][0]);
		 System.out.print("\nstring data1 1 :"+data1[i][1]);
		 System.out.print("\nstring data1 2 :"+data1[i][2]);
		 
		 System.out.print("\n\nstring data2 0 :"+data2[i][0]);
		 System.out.print("\nstring data2 1 :"+data2[i][1]);
		 System.out.print("\nstring data2 2 :"+data2[i][2]); 
	 }*/
	 
	 str1=s1;
	 name=message1;
	 for(int i=0;i<counter;i++)
	 {
	 	if(name[i].equals("m1.small"))
	 	{
			name[i]="m1.s";
		//	System.out.print("\nname"+name[i]); 		
		}
	
		else if(name[i].equals("c1.medium"))
		{
			name[i]="c1.m";
			//System.out.print("\nname"+name[i]); 		
			
		}
		else if(name[i].equals("m1.large"))
		{
			name[i]="m1.l";
			//System.out.print("\nname"+name[i]); 		
			
		}
		else if(name[i].equals("m1.xlarge"))
		{
			name[i]="m1.xl";
			//System.out.print("\nname"+name[i]); 		
			
		}
		else if(name[i].equals("c1.large"))
		{
			name[i]="c1.l";
			//System.out.print("\nname"+name[i]); 		
			
		}
		else if(name[i].equals("c1.xlarge"))
		{
			name[i]="c1.xl";
			//System.out.print("\nname"+name[i]); 		
			
		}
	 	
	 }

     JFrame f = new JFrame();
     
     f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
     f.add(new GraphingData());
     f.setSize(1000,600);
     f.setLocation(320,120);
     f.setVisible(true);
 }
}
	 
 
 
 