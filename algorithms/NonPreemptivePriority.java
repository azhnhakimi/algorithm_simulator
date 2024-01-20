package algorithms;

import java.util.ArrayList;
import java.util.List;

public class NonPreemptivePriority {
    
    List<Process> processList = new ArrayList<>();
    List<Process> readyQueue = new ArrayList<>();
    List<Process> finishedQueue = new ArrayList<>();
    List<Process> currentlyProcessingQueue = new ArrayList<>();

    public NonPreemptivePriority(){

    }

    public void addProcess(Process process){
        processList.add(process);
    }

    public void compute(){
        
    }
    
}
