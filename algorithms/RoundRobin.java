package algorithms;
import java.util.ArrayList;
import java.util.List;

import component.Process;
import utils.Printer;
import utils.Toolbox;

public class RoundRobin {

    int QUANTUM_TIME;


    List<Process> processList = new ArrayList<>();
    List<Process> readyQueue = new ArrayList<>();
    List<Process> finishedQueue = new ArrayList<>();
    List<Process> currentlyProcessingQueue = new ArrayList<>();
    List<Integer> stackHistory = new ArrayList<>();
    
    public RoundRobin(int QUANTUM_TIME){
        this.QUANTUM_TIME = QUANTUM_TIME;
    }

    public void addProcess(Process process){
        processList.add(process);
    }

    public List<Integer> getStackHistory(){
        return this.stackHistory;
    }

    public void compute(){
        
        int currentTime = 0;  // references current time of algorithm    
        int quantumCount = 0;  // references the current quantum time to track

        while(true){

            // checks if all processes are complete
            if(processList.isEmpty() && readyQueue.isEmpty()){
                Printer.printCurrentTime(String.valueOf(currentTime));
                Printer.printQuantumTime(String.valueOf(quantumCount));
                Printer.printReadyQueue(readyQueue);
                System.out.println();
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
            Printer.printCurrentTime(String.valueOf(currentTime));
            currentTime++;

            Printer.printQuantumTime(String.valueOf(quantumCount));
            quantumCount++;

            // prints the processes in the readyQueue
            Printer.printReadyQueue(readyQueue);

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


            // actual computation begins

            // deducting burst time
            Process currentProcess = currentlyProcessingQueue.get(0);
            int burstTime = currentProcess.getBurstTime();

            Printer.printBurstTime(String.valueOf(currentProcess.getProcessID()), String.valueOf(currentProcess.getBurstTime()));

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

        // printing out the results
        for(Process process : finishedQueue){
            process.setTurnaroundTime(Toolbox.calculateTurnaroundTime(process.getFinishedTime(), process.getArrivalTime()));
            process.setWaitingTime(Toolbox.calculateWaitingTime(process.getTurnaroundTime(), process.getInitialBurstTime()));
            System.out.println("ID: " +  process.getProcessID() + " Finish Time: " + process.getFinishedTime() + " Turnaround Time: " + process.getTurnaroundTime() + " Waiting Time: " + process.getWaitingTime());
        }
        System.out.println();

        System.out.println("Total Turnaround Time : " + Toolbox.calculateTotalTurnaroundTime(finishedQueue));
        System.out.println("Average Turnaround Time : " + Toolbox.calculateAverageTurnaroundTime(Toolbox.calculateTotalTurnaroundTime(finishedQueue), finishedQueue.size()));
        
        System.out.println();
        
        System.out.println("Total Waiting Time : " + Toolbox.calculateTotalWaitingTime(finishedQueue));
        System.out.println("Average Waiting Time : " + Toolbox.calculateAverageWaitingTime(Toolbox.calculateTotalWaitingTime(finishedQueue), finishedQueue.size()));
        
        System.out.println();
        System.out.println();
        
    }

    
}
