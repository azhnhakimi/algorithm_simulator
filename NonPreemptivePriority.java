package algorithms;

import component.Process;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class NonPreemptivePriority {

    List<Process> processList = new ArrayList<>();
    List<Process> finishedQueue = new ArrayList<>();

    List<Integer> stackHistory = new ArrayList<>();
    Map<Integer, Integer> turnaroundTimeData = new HashMap<>();
    Map<Integer, Integer> waitingTimeData = new HashMap<>();
    int processCount;

    int totalTurnaroundTime;
    int totalWaitingTime;
    double averageTurnaroundTime;
    double averageWaitingTime;

    public NonPreemptivePriority() {
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

    public Map<Integer, Integer> getTurnaroundTimeData() {
        return this.turnaroundTimeData;
    }

    public Map<Integer, Integer> getWaitingTimeData() {
        return this.waitingTimeData;
    }

    public int getProcessCount() {
        return this.processCount;
    }

    public void compute() {
        processList.sort((p1, p2) -> Integer.compare(p2.getPriority(), p1.getPriority()));

        int currentTime = 0;

        for (Process process : processList) {
            int startTime = currentTime;

            // Execute the process
            while (process.getBurstTime() > 0) {
                stackHistory.add(process.getProcessID());
                process.decrementBurstTime();
                currentTime++;
            }

            // Update turnaround time and waiting time data
            turnaroundTimeData.put(process.getProcessID(), currentTime - startTime);
            waitingTimeData.put(process.getProcessID(), startTime);

            // Update total turnaround and waiting time
            totalTurnaroundTime += currentTime - startTime;
            totalWaitingTime += startTime;

            // Move the process to the finished queue
            finishedQueue.add(process);
        }

        // Calculate average turnaround and waiting time
        averageTurnaroundTime = (double) totalTurnaroundTime / processCount;
        averageWaitingTime = (double) totalWaitingTime / processCount;
    }

    public List<Integer> getTimeStackHistory() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getTimeStackHistory'");
    }
}
