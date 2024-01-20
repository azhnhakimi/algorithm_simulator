package gui;
import javax.swing.JLabel;
import javax.swing.border.EmptyBorder;

import java.awt.Color;
import java.awt.Font;

public class HeaderTitle extends JLabel {
    
    HeaderTitle(String str){
        this.setText(str);
        this.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 24));
        this.setBackground(new Color(0x00C4FF));
        this.setForeground(Color.BLACK);
        this.setBorder(new EmptyBorder(8, 12, 8, 12));
        this.setOpaque(true);
    }
    
}
