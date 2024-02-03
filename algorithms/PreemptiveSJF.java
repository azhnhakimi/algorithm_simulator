package algorithms;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;
import java.util.HashMap;
import java.util.Map;

import component.Process;

public class PreemptiveSJF {

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

    int timeQuantum = 1; // Adjust the time quantum as needed

    public PreemptiveSJF() {
        this.processCount = 0;
    }

    public void addProcess(Process process) {
        processList.add(process);
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

        PriorityQueue<Process> readyQueue = new PriorityQueue<>(Comparator.comparingInt(Process::getBurstTime));

        while (!readyQueue.isEmpty() || !processList.isEmpty()) {
            // Add processes to the ready queue that have arrived by the current time
            while (!processList.isEmpty() && processList.get(0).getArrivalTime() <= currentTime) {
                readyQueue.add(processList.remove(0));
            }

            // If the ready queue is not empty, execute the process with the shortest burst time
            if (!readyQueue.isEmpty()) {
                Process currentProcess = readyQueue.poll();
                currentlyProcessingQueue.add(currentProcess);

                // Update waiting time for the current process
                int waitingTime = currentTime - currentProcess.getArrivalTime();
                waitingTimeData.put(currentProcess.getProcessID(), new WaitingTimeVal(waitingTime));

                // Execute the process for a time quantum (or until completion)
                int executionTime = Math.min(currentProcess.getBurstTime(), timeQuantum);
                stackHistory.add(currentProcess.getProcessID());
                timeStackHistory.add(executionTime);

                currentTime += executionTime;
                currentProcess.setBurstTime(currentProcess.getBurstTime() - executionTime);

                if (currentProcess.getBurstTime() == 0) {
                    // Update turnaround time for the completed process
                    int turnaroundTime = currentTime - currentProcess.getArrivalTime();
                    turnaroundTimeData.put(currentProcess.getProcessID(), new TurnAroundTimeVal(turnaroundTime));
                    finishedQueue.add(currentProcess);
                    currentlyProcessingQueue.remove(currentProcess);
                } else {
                    // Put the process back in the ready queue if it's not completed
                    readyQueue.add(currentProcess);
                }
            } else {
                // No processes in the ready queue, move time forward
                currentTime++;
            }
        }

        // Calculate total turnaround time and waiting time
        for (Process process : finishedQueue) {
            totalTurnaroundTime += turnaroundTimeData.get(process.getProcessID()).getTurnaroundTime();
            totalWaitingTime += waitingTimeData.get(process.getProcessID()).getWaitingTime();
        }

        // Calculate average turnaround time and waiting time
        averageTurnaroundTime = (double) totalTurnaroundTime / processCount;
        averageWaitingTime = (double) totalWaitingTime / processCount;
    }

    public static class TurnAroundTimeVal {
        private int turnaroundTime;

        public TurnAroundTimeVal(int turnaroundTime) {
            this.turnaroundTime = turnaroundTime;
        }

        public int getTurnaroundTime() {
            return turnaroundTime;
        }
    }

    public static class WaitingTimeVal {
        private int waitingTime;

        public WaitingTimeVal(int waitingTime) {
            this.waitingTime = waitingTime;
        }

        public int getWaitingTime() {
            return waitingTime;
        }
    }
}
