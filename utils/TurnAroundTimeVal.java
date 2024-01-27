package utils;

public class TurnAroundTimeVal {

    int processID;
    int arrivalTime;
    int finishTime;
    int turnaroundTime;

    
    public TurnAroundTimeVal(int processID, int arrivalTime, int finishTime, int turnaroundTime){
        this.processID = processID;
        this.arrivalTime = arrivalTime;
        this.finishTime = finishTime;
        this.turnaroundTime = turnaroundTime;
    }

    public int getArrivalTime(){
        return this.arrivalTime;
    }

    public int getFinishTime(){
        return this.finishTime;
    }

    public int getTurnaroundTime(){
        return this.turnaroundTime;
    }

    public int getProcessID(){
        return this.processID;
    }

}
