package utils;
import java.util.*;

import component.Process;

public class Toolbox {
    
    private Toolbox(){};

    public static int calculateTurnaroundTime(int finishTime, int arrivalTime){
        return finishTime - arrivalTime;
    }

    public static int calculateWaitingTime(int turnaroundTime, int burstTime){
        return turnaroundTime - burstTime;
    }

    public static int calculateTotalTurnaroundTime(List<Process> processes){
        int totalTurnaroundTime = 0;
        for(Process process : processes){
            totalTurnaroundTime = totalTurnaroundTime + process.getTurnaroundTime();
        }
        return totalTurnaroundTime;
    }

    public static int calculateTotalWaitingTime(List<Process> processes){
        int totalWaitingTime = 0;
        for(Process process : processes){
            totalWaitingTime = totalWaitingTime + process.getWaitingTime();
        }
        return totalWaitingTime;
    }

    public static double calculateAverageTurnaroundTime(double totalTurnaroundTime, double size){
        double unformattedResult =  totalTurnaroundTime / size;
        String formattedResult = String.format("%.2f", unformattedResult);
        return Double.parseDouble(formattedResult);
        
    }

    public static double calculateAverageWaitingTime(double totalWaitingTime, double size){
        double unformattedResult =  totalWaitingTime / size;
        String formattedResult = String.format("%.2f", unformattedResult);
        return Double.parseDouble(formattedResult);
        
    }
    
}
