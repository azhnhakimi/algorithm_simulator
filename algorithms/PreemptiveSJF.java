package algorithms;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;

import component.Process;
import utils.Toolbox;
import utils.TurnAroundTimeVal;
import utils.WaitingTimeVal;

public class PreemptiveSJF {

    List<Process> processList = new ArrayList<>();
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

    public PreemptiveSJF() {
        this.processCount = 0;
    }

    public void addProcess(Process process) {
        processList.add(process);
        processCount++;
    }

    public int getTotalTurnaroundTime() {
        return this.totalTurnaroundTime;
    }

    public int getTotalWaitingTime() {
        return this.totalWaitingTime;
    }

    public double getAverageTurnaroundTime() {
        return this.averageTurnaroundTime;
    }

    public double getAverageWaitingTime() {
        return this.averageWaitingTime;
    }

    public List<Integer> getStackHistory() {
        return this.stackHistory;
    }

    public List<Integer> getTimeStackHistory() {
        return this.timeStackHistory;
    }

    public Map<Integer, TurnAroundTimeVal> getTurnaroundTimeData() {
        return this.turnaroundTimeData;
    }

    public Map<Integer, WaitingTimeVal> getWaitingTimeData() {
        return this.waitingTimeData;
    }

    public int getProcessCount() {
        return this.processCount;
    }

    public void compute() {
        int currentTime = 0;
        this.processCount = processList.size();

        List<Process> readyQueue = new ArrayList<>();

        while (true) {

            // checks if all processes are complete
            if(processList.isEmpty() && readyQueue.isEmpty()){
                break;
            }

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

            if(currentlyProcessingQueue.isEmpty()){
                // case : no process have arrived yet
                if(readyQueue.isEmpty()){
                    continue;
                }else{
                    Process nextProcess = Collections.min(readyQueue, Comparator.comparingInt(Process::getBurstTime));
                    currentlyProcessingQueue.add(nextProcess);
                }
            }else{
                currentlyProcessingQueue.clear();
                Process nextProcess = Collections.min(readyQueue, Comparator.comparingInt(Process::getBurstTime));
                currentlyProcessingQueue.add(nextProcess);
            }


            stackHistory.add(currentlyProcessingQueue.get(0).getProcessID());
            timeStackHistory.add(currentTime);

            // Calculation occurs
            
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
}
