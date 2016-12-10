/* Mohammed Hosny Abd-Allah 
CS Dep.
*/
package os.project;


import java.io.PrintWriter;

public class OSProject
{
    public static void main(String[] args) throws Exception{
        processor fcfs =new processor();
        processor sjf =new processor();
        processor psa =new processor();
        processor rr =new processor();
                
        //First Come First Served scheduling algorithm (FCFS)
        fcfs.FCFS();
        
        //Shortest Job Frist scheduling algorithm (SJF)
        sjf.SJF();
        
        //Priority scheduling algorithm (PSA)
        psa.PSA();
        
        //Round robin scheduling algorithm (RR)
        rr.RR();
        
        //Print to output files
        fcfs.show("First come first served.txt");
        sjf.show("Shortest job first.txt");
        psa.show("Priority algorithm.txt");
        rr.show("Round robin.txt");
    }
}
class process{
   private  int arrivalTime ,burstTime,priority,index,waitingTime,turnarrooundTime;
  public static int count=0;
public process ()
{
    arrivalTime=(int)(Math.random()*1000);
    burstTime=(int)(Math.random()*1000);
    priority=(int)(Math.random()*1000);
    count++;
    index=count;
}
public int getarrivalTime()
{
    return arrivalTime;
}
public void setarrivalTime(int t)
{
    this.arrivalTime=t;
}
public int getburstTime()
{
    return this.burstTime;
}
public void setburstTime(int t)
{
    this.burstTime=t;
}
public int getpriority()
{
    return this.priority;
}
public void setpriority(int t)
{
    this.priority=t;
}
public int getIndex()
{
    return this.index;
}
public void setIndex(int index) {
        this.index = index;
    }
public int getWaitingTime() {
        return waitingTime;
    }

public void setWaitingTime(int waitingTime) {
        this.waitingTime = waitingTime;
    }
 public int getTurnarrooundTime() 
 {
    return turnarrooundTime;
    }
public void setTurnarrooundTime(int turnarrooundTime) 
{
    this.turnarrooundTime = turnarrooundTime;
}
}

class processor
{ 
    private process []  processes=new process[1000];
    private double  totalwait, totalturnarround; 
    public processor()
    {
        for(int i=0;i<1000;i++)
            processes [i]=new process();
    }
    private void solve() //to calculate 
   {
       processes[0].setWaitingTime(0);
       processes[0].setTurnarrooundTime(processes[0].getburstTime());

       for(int i=1;i<processes.length;i++)
       {
           int temp=processes[i-1].getarrivalTime()+processes[i-1].getWaitingTime()+processes[i-1].getburstTime();
                     if(processes[i].getarrivalTime()>temp)
                     {
                          processes[i].setWaitingTime(0);

                     }
           processes[i].setWaitingTime(temp-processes[i].getarrivalTime());
           processes[i].setTurnarrooundTime(processes[i].getburstTime()+processes[i].getWaitingTime());
       }
   }
    
    public  void FCFS ()
    {
       for (int i=0;i<processes.length;i++)
       {
           for(int j=i+1;j<processes.length;j++)
           {
              if(processes[j].getarrivalTime()<processes[i].getarrivalTime())
               {
                   process temp=processes[i];
                   processes[i]=processes[j];
                   processes[j]=temp;
               }
           }
       }
       solve();
       for(int i=0;i<1000;i++)
       {
           totalwait+=processes[i].getWaitingTime();
           totalturnarround+=processes[i].getTurnarrooundTime();
       }
       totalturnarround/=1000;
       totalwait/=1000;
           

    }
    
    public void SJF ()
          
    {
       for (int i=0;i<processes.length-1;i++)
       {
           for(int j=i+1;j<processes.length;j++)
           {
               if(processes[i].getburstTime()<processes[j].getburstTime())
               {
                  process temp=processes[i];
                   processes[i]=processes[j];
                   processes[j]=temp;
               }
           }
       }   
       solve();
       for(int i=0;i<1000;i++)
       {
           totalwait+=processes[i].getWaitingTime();
           totalturnarround+=processes[i].getTurnarrooundTime();
       }
       totalturnarround/=1000;
       totalwait/=1000;
    }
    
    public void PSA ()
    {
       for (int i=0;i<processes.length-1;i++)
       {
           for(int j=i+1;j<processes.length;j++)
           {
               if(processes[i].getpriority()>processes[j].getpriority())
               {
                 process temp=processes[i];
                   processes[i]=processes[j];
                   processes[j]=temp;
               }
           }
       } 
       solve();
       for(int i=0;i<1000;i++)
       {
           totalwait+=processes[i].getWaitingTime();
           totalturnarround+=processes[i].getTurnarrooundTime();
       }
       totalturnarround/=1000;
       totalwait/=1000;
       
    }
    
    public void RR()
    {
        process[] template = new process[processes.length];
        for(int i=0;i<processes.length; i++){
            template[i] = new process();
            template[i].setarrivalTime(processes[i].getarrivalTime());
            template[i].setburstTime(processes[i].getburstTime());
            template[i].setpriority(processes[i].getpriority());
        }
        
        boolean stop;
		do{
			stop = false;
			for(int i=0;i<template.length;i++)
			{
				if(template[i].getburstTime()>= 1)
				{
					for(int j=0;j<template.length;j++)
					{
						if(j==i)
							template[i].setburstTime(template[i].getburstTime()-1);
						else if(template[j].getburstTime() > 0)
							processes[j].setWaitingTime(processes[j].getWaitingTime() + 1);        
					}
				}
			}
			for(int i=0;i<template.length;i++)
				if(template[i].getburstTime() > 0)
					stop = true;
		}while(stop == true);
                
                for(int i = 0; i < processes.length; i++){
                    processes[i].setTurnarrooundTime(processes[i].getburstTime() + processes[i].getWaitingTime());
                }
                
                for(int i=0;i<1000;i++)
                {
                    totalwait+=processes[i].getWaitingTime();
                    totalturnarround+=processes[i].getTurnarrooundTime();
                }
                totalturnarround/=1000;
                totalwait/=1000;
                
    }
    
    public void show(String s) throws Exception
    {
        PrintWriter output = new PrintWriter(s);
                   output.println("index    ||  arrival  ||  burst  ||  priority  ||  waiting  ||  turnAround");
        for(int i=0;i<1000;i++)
       {
           output.println(processes[i].getIndex()+"           "+processes[i].getarrivalTime()+"          "+processes[i].getburstTime()+"           "+processes[i].getpriority()+"          "+processes[i].getWaitingTime()+ "            "+processes[i].getTurnarrooundTime());
       }
        output.println("Average waiting time: " + totalwait);
        output.println("Average turn around: " + totalturnarround);
        output.close();
    }
}