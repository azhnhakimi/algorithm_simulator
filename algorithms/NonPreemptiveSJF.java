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

public class NonPreemptiveSJF {

    private List<Process> processList = new ArrayList<>();
    private List<Process> finishedQueue = new ArrayList<>();
    private Map<Integer, TurnAroundTimeVal> turnaroundTimeData = new HashMap<>();
    private Map<Integer, WaitingTimeVal> waitingTimeData = new HashMap<>();
    private int totalTurnaroundTime = 0;
    private int totalWaitingTime = 0;
    private double averageTurnaroundTime = 0;
    private double averageWaitingTime = 0;

    // For Gantt chart visualization
    private List<Integer> stackHistory = new ArrayList<>();
    private List<Integer> timeStackHistory = new ArrayList<>();

    public void addProcess(Process process) {
        processList.add(process);
    }

    public void compute() {
        int currentTime = 0;
        while (!processList.isEmpty()) {
            List<Process> availableProcesses = new ArrayList<>();
            for (Process process : processList) {
                if (process.getArrivalTime() <= currentTime) {
                    availableProcesses.add(process);
                }
            }

            if (availableProcesses.isEmpty()) {
                currentTime++; // No processes available at the current time
                continue;
            }

            Process nextProcess = Collections.min(availableProcesses, Comparator.comparingInt(Process::getBurstTime));
            processList.remove(nextProcess);

            // Simulate process execution
            int finishTime = currentTime + nextProcess.getBurstTime();
            nextProcess.setFinishTime(finishTime);
            finishedQueue.add(nextProcess);

            // Track the process execution for Gantt chart
            stackHistory.add(nextProcess.getProcessID());
            timeStackHistory.add(currentTime);

            // Calculate turnaround and waiting times
            int turnaroundTime = finishTime - nextProcess.getArrivalTime();
            int waitingTime = turnaroundTime - nextProcess.getBurstTime();
            totalTurnaroundTime += turnaroundTime;
            totalWaitingTime += waitingTime;

            // Update data structures for GUI display
            turnaroundTimeData.put(nextProcess.getProcessID(), new TurnAroundTimeVal(nextProcess.getProcessID(), nextProcess.getArrivalTime(), finishTime, turnaroundTime));
            waitingTimeData.put(nextProcess.getProcessID(), new WaitingTimeVal(nextProcess.getProcessID(), nextProcess.getBurstTime(), turnaroundTime, waitingTime));

            currentTime = finishTime; // Move the current time forward to the end of the execution
        }

        // Calculate the average values
        averageTurnaroundTime = Toolbox.calculateAverageTurnaroundTime(totalTurnaroundTime, finishedQueue.size());
        averageWaitingTime = Toolbox.calculateAverageWaitingTime(totalWaitingTime, finishedQueue.size());
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
        return finishedQueue.size();
    }
}
