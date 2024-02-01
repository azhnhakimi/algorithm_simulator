package algorithms;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import utils.TurnAroundTimeVal;
import utils.WaitingTimeVal;

public class PreemptivePriority {

    List<Process> processList = new ArrayList<>();
    List<Process> readyQueue = new ArrayList<>();
    List<Process> finishedQueue = new ArrayList<>();
    List<Process> currentlyProcessingQueue = new ArrayList<>();

    List<Integer> stackHistory = new ArrayList<>();
    List<Integer> timeStackHistory = new ArrayList<>();
    Map<Integer, TurnAroundTimeVal> turnaroundTimeData = new HashMap<>();
    Map<Integer, WaitingTimeVal> waitingTimeData = new HashMap<>();
    int processCount;

    int totalTurnaroundTime;
    int totalWaitingTime;
    double averageTurnaroundTime;
    double averageWaitingTime;
    

    public PreemptivePriority(){
        this.processCount = 0;
    }

    public int getTotalTurnaroundTime(){
        return this.totalTurnaroundTime;
    }

    public int getTotalWaitingTime(){
        return this.totalWaitingTime;
    }

    public double getAverageTurnaroundTime(){
        return this.averageTurnaroundTime;
    }

    public double getAverageWaitingTime(){
        return this.averageWaitingTime;
    }

    public List<Integer> getStackHistory(){
        return this.stackHistory;
    }

    public List<Integer> getTimeStackHistory(){
        return this.timeStackHistory;
    }

    public Map<Integer, TurnAroundTimeVal> getTurnaroundTimeData(){
        return this.turnaroundTimeData;
    }

    public Map<Integer, WaitingTimeVal> getWaitingTimeData(){
        return this.waitingTimeData;
    }

    public int getProcessCount(){
        return this.processCount;
    }

    public void addProcess(Process process){
        processList.add(process);
    }

    public void compute(){
        
    }
    
}
