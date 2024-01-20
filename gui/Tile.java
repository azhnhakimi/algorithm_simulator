package gui;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;

import utils.CustomColor;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;

public class Tile extends JPanel {

    int width;
    int height = 50;
    int fontSize;
    
public Tile(int width, int processID) {
        this.width = width;
        fontSize = 15;

        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS)); 

        setOpaque(true);
        setPreferredSize(new Dimension(width, height));
        setBorder(new LineBorder(Color.BLACK, 2));

        int color = CustomColor.getColor(processID);
        setBackground(new Color(color));

        JLabel label = new JLabel("P" + String.valueOf(processID));
        Font font = label.getFont();
        label.setFont(new Font(font.getName(), font.getStyle(), fontSize));
        label.setForeground(new Color(CustomColor.getForegroundColor(color)));
        label.setAlignmentX(Component.CENTER_ALIGNMENT); 

        add(Box.createVerticalGlue()); 
        add(label);
        add(Box.createVerticalGlue()); 
    }
    
}
