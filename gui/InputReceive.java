package gui;
import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.border.EmptyBorder;

import java.awt.Font;

public class InputReceive extends JPanel {

    private JLabel label;
    private JTextArea textArea;
    
    InputReceive(String labelString){
        label = new JLabel(labelString);
        label.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 18));
        textArea = new JTextArea();
        textArea.setPreferredSize(new Dimension(45, 35));
        textArea.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 16));
        textArea.setBorder(new EmptyBorder(8, 10, 0, 10));


        this.setOpaque(false);
        this.setBorder(new EmptyBorder(8, 12, 8, 12));
        this.setLayout(new FlowLayout(FlowLayout.LEADING));
        this.add(label);
        this.add(textArea);
    }
    
}
