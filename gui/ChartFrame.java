package gui;

import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.util.List;

public class ChartFrame extends JPanel {

    List<Integer> stackHistory;
    
    public ChartFrame(List<Integer> stackHistory, List<Integer> timeStackHistory){

        this.setOpaque(true);
        this.setPreferredSize(new Dimension(1450, 150));
        this.setBackground(new Color(0xFFF8DC));
        // this.setBackground(new Color(0xFFFfff));
        this.stackHistory = stackHistory;
        // this.setLayout(new GridLayout(1,stackHistory.size(), 0, 0));
        this.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0));

        for(int i = 0; i < stackHistory.size(); i++){
            
            int processID = stackHistory.get(i);
            int currentTime = timeStackHistory.get(i);

            // System.out.println(processID + " " + currentTime);
            
            Tile processTile = new Tile(45, processID, currentTime);
            this.add(processTile);
        }  
    }
    
}