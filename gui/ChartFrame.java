package gui;

import javax.swing.JPanel;

import java.awt.Color;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.util.List;

public class ChartFrame extends JPanel {

    List<Integer> stackHistory;
    
    public ChartFrame(List<Integer> stackHistory){

        this.setOpaque(true);
        this.setPreferredSize(new Dimension(1250, 120));
        this.setBackground(new Color(0xFFF8DC));
        this.setBackground(new Color(0xFFFfff));
        this.stackHistory = stackHistory;
        // this.setLayout(new GridLayout(1,stackHistory.size(), 0, 0));
        this.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0));

        int width = (int) Math.floor(1000 / (stackHistory.size()));
        for(int i = 0; i < stackHistory.size(); i++){

            // if(i == 0 || i == stackHistory.size()){
            //     Seperator countTile = new Seperator(100);
            //     this.add(countTile);
            //     if(i != 0){
            //         break;
            //     }
            // }

            // if(i != 0 && stackHistory.get(i) != stackHistory.get(i - 1)){
            //     Seperator countTile = new Seperator(100);
            //     this.add(countTile);
            // }
            
            int processID = stackHistory.get(i);
            Tile processTile = new Tile(width, processID);
            this.add(processTile);
        }  
    }
    
}