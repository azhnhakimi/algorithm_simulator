package utils;
import java.util.List;

import component.Process;

public class Printer {
    
    public static void printCurrentTime(String currentTime){
        System.out.println("Time : " + currentTime);
    }

    public static void printQuantumTime(String quantumTime){
        System.out.println("Quantum Time : " + quantumTime);
    }

    public static void printReadyQueue(List<Process> readyQueue){
        System.out.print("Ready Queue : ");
        for(Process process : readyQueue){
            System.out.print(process.getProcessID() + " ");
        }
        System.out.println();
    }

    public static void printBurstTime(String processId, String burstTime){
        System.out.print("ID : " + processId + " ");
        System.out.print("Burst Time : " + burstTime);
        System.out.println();
        System.out.println();
    }
    
}
