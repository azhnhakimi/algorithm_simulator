import java.util.List;

import algorithms.RoundRobin;
import component.Process;
import gui.Window;

public class Simulator {

    Window window;
    List<Integer> stackHistory;

    
    Simulator(){
        runSimulation();
        this.window = new Window(stackHistory);
    }


    public void runSimulation(){

        // example using the roundRobin algorithm
        // actual use case : make options to choose algorithm

        // setup
        RoundRobin roundRobin = new RoundRobin(3);
        roundRobin.addProcess(new Process(1, 1, 4, 0));
        roundRobin.addProcess(new Process(0, 0, 6, 0));
        roundRobin.addProcess(new Process(2, 5, 6, 0));
        roundRobin.addProcess(new Process(4, 7, 6, 0));
        roundRobin.addProcess(new Process(5, 8, 6, 0));
        roundRobin.addProcess(new Process(3, 6, 6, 0));

        // compute
        roundRobin.compute();

        setStackHistory(roundRobin.getStackHistory());

    }

    public void setStackHistory(List<Integer> stackHistory){
        this.stackHistory = stackHistory;
    }
    
}
