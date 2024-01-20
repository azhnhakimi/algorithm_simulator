package gui;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;

public class CountTile extends JPanel {

    int width;
    int height = 50;

    public CountTile(int width) {

        this.width = width;
        this.setBackground(new Color(0xFFFfff));

        // Use FlowLayout with right alignment
        setLayout(new FlowLayout(FlowLayout.CENTER));

        setOpaque(true);
        setPreferredSize(new Dimension(width, height));
        // setBorder(new LineBorder(Color.BLACK, 2));

        JLabel label = new JLabel("0");
        add(label);
    }
}
