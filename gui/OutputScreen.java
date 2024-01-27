package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.util.List;
import java.util.Map;
import javax.swing.*;
import java.awt.Font;
import utils.TurnAroundTimeVal;
import utils.WaitingTimeVal;


public class OutputScreen extends JPanel {

    ChartFrame chartFrame;
    
    public OutputScreen(
            List<Integer> stackHistory, List<Integer> timeStackHistory, Map<Integer, TurnAroundTimeVal> turnaroundTimeData, Map<Integer, WaitingTimeVal> waitingTimeData, int processCount, int totalTurnaroundTime, int totalWaitingTime, double averageTurnaroundTime, double averageWaitingTime
        ){
            this.setBackground(new Color(0xFFF8DC));
            this.setOpaque(true);

            FlowLayout flowLayout = new FlowLayout(FlowLayout.CENTER);
            flowLayout.setVgap(30);
            flowLayout.setHgap(50);
            this.setLayout(flowLayout);

        JPanel turnaroundTimePanel = new JPanel();
        turnaroundTimePanel.setLayout(new BorderLayout(0,15));
        turnaroundTimePanel.setBackground(new Color(0xFFF8DC));
        
        JPanel waitingTimePanel = new JPanel();
        waitingTimePanel.setLayout(new BorderLayout(0, 15));
        waitingTimePanel.setBackground(new Color(0xFFF8DC));
        
        
        TurnaroundTable turnaroundTable = new TurnaroundTable(processCount + 1, 4, turnaroundTimeData);
        
        JLabel totalTurnaroundTimeLabel = new JLabel();
        totalTurnaroundTimeLabel.setText("Total Turnaround Time : " + String.valueOf(totalTurnaroundTime));
        totalTurnaroundTimeLabel.setFont(new Font("Arial", Font.BOLD, 15));

        JLabel averageTurnaroundTimeLabel = new JLabel();
        averageTurnaroundTimeLabel.setText("Average Turnaround TIme : " + String.valueOf(averageTurnaroundTime));
        averageTurnaroundTimeLabel.setFont(new Font("Arial", Font.BOLD, 15));


        turnaroundTimePanel.add(turnaroundTable, BorderLayout.NORTH);
        turnaroundTimePanel.add(totalTurnaroundTimeLabel, BorderLayout.CENTER);
        turnaroundTimePanel.add(averageTurnaroundTimeLabel, BorderLayout.SOUTH);
        

        WaitingTable waitingTable = new WaitingTable(processCount + 1, 4, waitingTimeData);

        JLabel totalWaitingTimeLabel = new JLabel();
        totalWaitingTimeLabel.setText("Total Waiting Time : " + String.valueOf(totalWaitingTime));
        totalWaitingTimeLabel.setFont(new Font("Arial", Font.BOLD, 15));

        JLabel averageWaitingTimeLabel = new JLabel();
        averageWaitingTimeLabel.setText("Average Waiting Time : " + String.valueOf(averageWaitingTime));
        averageWaitingTimeLabel.setFont(new Font("Arial", Font.BOLD, 15));

        waitingTimePanel.add(waitingTable, BorderLayout.NORTH);
        waitingTimePanel.add(totalWaitingTimeLabel, BorderLayout.CENTER);
        waitingTimePanel.add(averageWaitingTimeLabel, BorderLayout.SOUTH);


        this.add(turnaroundTimePanel);
        this.add(waitingTimePanel);

        this.chartFrame = new ChartFrame(stackHistory, timeStackHistory);
        this.add(chartFrame);
    }
    
}
