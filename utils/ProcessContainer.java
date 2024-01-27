package utils;

public class ProcessContainer {
    
    int processID;
    int arrivalTime;
    int burstTime;
    int priority;

    public ProcessContainer(int processID, int arrivalTime, int burstTime, int priority){
        this.processID = processID;
        this.arrivalTime = arrivalTime;
        this.burstTime = burstTime;
        this.priority = priority;
    }

    public int getProcessId(){
        return this.processID;
    }
    
    public int getArrivalTime(){
        return this.arrivalTime;
    }

    public int getBurstTime(){
        return this.burstTime;
    }

    public int getPriority(){
        return this.priority;
    }
    
}
