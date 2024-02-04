package algorithms;

import component.Process;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import utils.Toolbox;
import utils.TurnAroundTimeVal;
import utils.WaitingTimeVal;

public class NonPreemptivePriority {

    List<Process> processList = new ArrayList<>();
    List<Process> readyQueue = new ArrayList<>();
    List<Process> finishedQueue = new ArrayList<>();
    List<Process> currentlyProcessingQueue = new ArrayList<>();

    List<Integer> stackHistory = new ArrayList<>();
    List<Integer> timeStackHistory = new ArrayList<>();
    Map<Integer, TurnAroundTimeVal> turnaroundTimeData = new HashMap<>();
    Map<Integer, WaitingTimeVal> waitingTimeData = new HashMap<>();
    int processCount = 0;

    int totalTurnaroundTime;
    int totalWaitingTime;
    double averageTurnaroundTime;
    double averageWaitingTime;

    public void addProcess(Process process) {
        processList.add(process);
    }

    public void compute() {

        int currentTime = 0;
        this.processCount = processList.size();

        while (true) {

            // checks if all processes are complete
            if(processList.isEmpty() && readyQueue.isEmpty()){
                break;
            }

            // adds processes to the readyQueue as they arrive
            List<Process> updatedProcessList = new ArrayList<>();
            for(Process process : processList){
                if(process.getArrivalTime() == currentTime){
                    readyQueue.add(process);
                }else{
                    updatedProcessList.add(process);
                }
            }
            processList = updatedProcessList;

            // updates the currentTime
            currentTime++;
            
            // fetches new process if no process currently being processed
            if(currentlyProcessingQueue.isEmpty()){
                // case : no process have arrived yet
                if(readyQueue.isEmpty()){
                    continue;
                }else{
                    Process nextProcess = Collections.min(readyQueue, Comparator.comparingInt(Process::getPriority));
                    currentlyProcessingQueue.add(nextProcess);
                }
            }
            stackHistory.add(currentlyProcessingQueue.get(0).getProcessID());
            timeStackHistory.add(currentTime);

            // actual computation begins

            // deducting burst time
            Process currentProcess = currentlyProcessingQueue.get(0);
            int burstTime = currentProcess.getBurstTime();

            int newBurstTime = burstTime - 1;
            currentProcess.setBurstTime(newBurstTime);

            // checks if process has finished
            if(newBurstTime <= 0){
                
                // remove from currentlyProcessingQueue
                currentlyProcessingQueue.remove(0);

                // remove from readyQueue
                // add to finishedQueue
                List<Process> updatedReadyQueue = new ArrayList<>();
                for(Process process : readyQueue){
                    if(process.getProcessID() != currentProcess.getProcessID()){
                        updatedReadyQueue.add(process);
                    }else{
                        process.setFinishTime(currentTime);
                        finishedQueue.add(process);
                        turnaroundTimeData.put(
                            process.getProcessID(), new TurnAroundTimeVal(process.getProcessID(), process.getArrivalTime(), process.getFinishedTime(), Toolbox.calculateTurnaroundTime(process.getFinishedTime(), process.getArrivalTime())
                        ));
                    }
                }
                readyQueue = updatedReadyQueue;
                continue;
            }

        }

        // setting the results
        for(Process process : finishedQueue){
            process.setTurnaroundTime(Toolbox.calculateTurnaroundTime(process.getFinishedTime(), process.getArrivalTime()));
            process.setWaitingTime(Toolbox.calculateWaitingTime(process.getTurnaroundTime(), process.getInitialBurstTime()));

            waitingTimeData.put(
                process.getProcessID(), new WaitingTimeVal(process.getProcessID(), process.getInitialBurstTime(), process.getTurnaroundTime(), process.getWaitingTime())
            );
        }

        this.totalTurnaroundTime = Toolbox.calculateTotalTurnaroundTime(finishedQueue);
        this.totalWaitingTime = Toolbox.calculateTotalWaitingTime(finishedQueue);
        this.averageTurnaroundTime = Toolbox.calculateAverageTurnaroundTime(Toolbox.calculateTotalTurnaroundTime(finishedQueue), finishedQueue.size());
        this.averageWaitingTime = Toolbox.calculateAverageWaitingTime(Toolbox.calculateTotalWaitingTime(finishedQueue), finishedQueue.size());
    }

    // Rest of the getters...
    public List<Process> getFinishedQueue() {
        return finishedQueue;
    }

    public Map<Integer, TurnAroundTimeVal> getTurnaroundTimeData() {
        return turnaroundTimeData;
    }

    public Map<Integer, WaitingTimeVal> getWaitingTimeData() {
        return waitingTimeData;
    }

    public int getTotalTurnaroundTime() {
        return totalTurnaroundTime;
    }

    public int getTotalWaitingTime() {
        return totalWaitingTime;
    }

    public double getAverageTurnaroundTime() {
        return averageTurnaroundTime;
    }

    public double getAverageWaitingTime() {
        return averageWaitingTime;
    }

    public List<Integer> getStackHistory() {
        return stackHistory;
    }

    public List<Integer> getTimeStackHistory() {
        return timeStackHistory;
    }

    public int getProcessCount() {
        // System.out.println(this.processCount);
        return this.processCount;
    }
}
