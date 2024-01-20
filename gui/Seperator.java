package gui;

import java.awt.Dimension;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;
import java.awt.Color;

public class Seperator extends JPanel{

    int width;
    int height;
    
    public Seperator(int height){
        this.height = height;
        setPreferredSize(new Dimension(1, height));
        setBorder(new LineBorder(Color.BLACK, 2));
    }
    
}
