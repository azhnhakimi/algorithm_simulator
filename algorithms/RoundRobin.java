package algorithms;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import component.Process;

import utils.Toolbox;
import utils.TurnAroundTimeVal;
import utils.WaitingTimeVal;

public class RoundRobin {

    int QUANTUM_TIME;

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
    
    public RoundRobin(int QUANTUM_TIME){
        this.QUANTUM_TIME = QUANTUM_TIME;
        this.processCount = 0;
    }

    public void addProcess(Process process){
        processList.add(process);
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

    public void compute(){
        
        int currentTime = 0;  // references current time of algorithm    
        int quantumCount = 0;  // references the current quantum time to track

        while(true){

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

            // updates the currentTime and quantumCount
            currentTime++;
            quantumCount++;

            // fetches new process if no process currently being processed
            if(currentlyProcessingQueue.isEmpty()){
                // case : no process have arrived yet
                if(readyQueue.isEmpty()){
                    continue;
                }else{
                    currentlyProcessingQueue.add(readyQueue.get(0));
                }
            }
            stackHistory.add(readyQueue.get(0).getProcessID());
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
                        processCount++;
                    }
                }
                readyQueue = updatedReadyQueue;

                // reset the quantumCount
                quantumCount = 0;
                continue;
            }

            // check if context swithcing occurs
            if(quantumCount == QUANTUM_TIME && currentTime != 0){

                // adds newly arrived processes
                List<Process> newProcessList = new ArrayList<>();
                for(Process process : processList){
                    if(process.getArrivalTime() == currentTime){
                        readyQueue.add(process);
                    }else{
                        newProcessList.add(process);
                    }
                }
                processList = newProcessList;

                // remove currentlyProcessedQueue and switches with a new process
                // adds the removed process back to the readyQueue
                currentlyProcessingQueue.remove(0);
                List<Process> updatedReadyQueue = new ArrayList<>();
                for(Process process : readyQueue){
                    if(process.getProcessID() != currentProcess.getProcessID()){
                        updatedReadyQueue.add(process);
                    }
                }
                updatedReadyQueue.add(currentProcess);
                readyQueue = updatedReadyQueue;

                // resets quantumCount
                quantumCount = 0;
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
