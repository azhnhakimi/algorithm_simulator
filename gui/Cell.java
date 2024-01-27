package gui;

import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;
import java.awt.Font;
import java.awt.Color;

public class Cell extends JLabel {
    
    Cell(){
        this.setBorder(new LineBorder(Color.BLACK, 1));
        this.setHorizontalAlignment(SwingConstants.CENTER);
        this.setBackground(new Color(0xFFFFFF));
        this.setOpaque(true);
        this.setFont(new Font("Arial", Font.PLAIN, 15));
    }
    
}
