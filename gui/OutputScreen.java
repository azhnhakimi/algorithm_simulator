package gui;

import java.awt.Color;
import java.util.List;

import javax.swing.JPanel;


public class OutputScreen extends JPanel {

    ChartFrame chartFrame;
    CountFrame countFrame;
    
    public OutputScreen(List<Integer> stackHistory){
        this.setOpaque(true);
        this.setBackground(new Color(0xFFF8DC));
        this.setBackground(new Color(0xFFFFFF));

        this.chartFrame = new ChartFrame(stackHistory);
        this.add(chartFrame);

        // this.countFrame = new CountFrame(stackHistory);
        // this.add(countFrame);

    }
    
}
