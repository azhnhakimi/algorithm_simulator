package gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

public class CustomField extends JPanel {
    
    JTextField textField;
    
    CustomField(String text){

        this.setBackground(new Color(0xFFF8DC));
        this.setPreferredSize(new Dimension(320, 40));
        this.setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
        this.setBorder(new EmptyBorder(8, 12, 8, 12));


        JLabel label = new JLabel(text + " :  ");
        label.setFont(new Font("Arial", Font.PLAIN, 15));

        textField = new JTextField(10);
        textField.setFont(new Font("Arial", Font.PLAIN, 15));
        textField.setHorizontalAlignment(SwingConstants.CENTER);
        textField.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));

        this.add(label);
        this.add(textField);
    }
    
    public int getValue(){
        return Integer.parseInt(this.textField.getText());
    }

    public void setEditable(boolean bool){
        this.textField.setEditable(bool);
    }

    public void reset(){
        this.textField.setText("");
    }

}
