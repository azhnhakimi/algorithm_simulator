package gui;
import java.awt.Color;
import javax.swing.JPanel;

public class RoundRobinPanel extends JPanel {

    HeaderTitle headerTitle = new HeaderTitle("Round Robin");
    
    RoundRobinPanel(){
        this.setOpaque(true);
        // this.setBackground(new Color(0x001A4B));
        this.setBackground(new Color(0xD9D9D9));

        this.add(headerTitle);
        this.add(new InputReceive("Number of Processes: "));
    }
    
}
