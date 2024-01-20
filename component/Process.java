package component;
public class Process {
    
    private int processID;
    private int arrivalTime;
    private int currentBurstTime;
    private int priority = -1;
    private int finishTime = -1;
    private int turnaroundTime = -1;
    private int waitingTime = -1;
    private final int initialBurstTime;

    public Process(int processID, int arrivalTime, int burstTime){
        this.processID = processID;
        this.arrivalTime = arrivalTime;
        this.currentBurstTime = burstTime;
        this.initialBurstTime = burstTime;
    }

    public Process(int processID, int arrivalTime, int burstTime, int priority){
        this.processID = processID;
        this.arrivalTime = arrivalTime;
        this.currentBurstTime = burstTime;
        this.priority = priority;
        this.initialBurstTime = burstTime;
    }

    public int getProcessID(){
        return this.processID;
    }

    public int getArrivalTime(){
        return this.arrivalTime;
    }

    public int getBurstTime(){
        return this.currentBurstTime;
    }

    public int getPriority(){
        return this.priority;
    }

    public int getFinishedTime(){
        return this.finishTime;
    }

    public int getTurnaroundTime(){
        return this.turnaroundTime;
    }

    public int getWaitingTime(){
        return this.waitingTime;
    }

    public int getInitialBurstTime(){
        return this.initialBurstTime;
    }

    public void setTurnaroundTime(int turnaroundTime){
        this.turnaroundTime = turnaroundTime;
    }

    public void setWaitingTime(int waitingTime){
        this.waitingTime = waitingTime;
    }

    public void setBurstTime(int newBurstTime){
        this.currentBurstTime = newBurstTime;
    }

    public void setFinishTime(int finishTime){
        this.finishTime = finishTime;
    }

}
