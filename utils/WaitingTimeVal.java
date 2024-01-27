package utils;

public class WaitingTimeVal {
    
    int processID;
    int burstTime;
    int turnaroundTime;
    int waitingTime;

    public WaitingTimeVal(int processID, int burstTime, int turnaroundTime, int waitingTime){
        this.processID = processID;
        this.burstTime = burstTime;
        this.turnaroundTime = turnaroundTime;
        this.waitingTime = waitingTime;
    }

    public int getProcessID(){
        return this.processID;
    }
    
    public int getBurstTime(){
        return this.burstTime;
    }

    public int getTurnaroundTime(){
        return this.turnaroundTime;
    }

    public int getWaitingTime(){
        return this.waitingTime;
    }
    
}
