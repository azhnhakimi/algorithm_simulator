package gui;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Window extends JFrame{
    
    public Window(){
        this.setSize(new Dimension(1500,800));
        this.setTitle("Process Scheduling Simulation");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.getContentPane().setBackground(new Color(0xD9D9D9));


        // creating the menu to change the algorithms
        JMenuBar dropdownContainer = new JMenuBar();
        JMenu algorithmOptions = new JMenu("Scheduling Algorithms");

        JMenuItem roundRobin = new JMenuItem("Round Robin");
        JMenuItem preEmptiveSJF = new JMenuItem("Preemptive SJF");
        JMenuItem nonPreEmptiveSJF = new JMenuItem("Non Preemptive SJF");
        JMenuItem preemptivePriority = new JMenuItem("Preemptive Priority");
        JMenuItem nonPreemptivePriority = new JMenuItem("Non Preemptive Priority");

        // adding action listeners for each of the menu items
        roundRobin.addActionListener(new RoundRobinAction());
        preEmptiveSJF.addActionListener(new PreEmptiveSJFAction());

        // resizing the image icon for the drop down menu
        ImageIcon downIcon = new ImageIcon("assets/down.png");
        Image scaledIcon = downIcon.getImage().getScaledInstance(15, 15, Image.SCALE_SMOOTH);
        algorithmOptions.setIcon(new ImageIcon(scaledIcon));

        // adding item menu to the menu
        algorithmOptions.add(roundRobin);
        algorithmOptions.add(preEmptiveSJF);
        algorithmOptions.add(nonPreEmptiveSJF);
        algorithmOptions.add(preemptivePriority);
        algorithmOptions.add(nonPreemptivePriority);

        dropdownContainer.add(algorithmOptions);
        this.setJMenuBar(dropdownContainer);

        // set visibility to last
        this.setVisible(true);
    }

    // add all respective algorithm panels here

    private class RoundRobinAction implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            getContentPane().removeAll();
            getContentPane().add(new RoundRobinPanel());
            revalidate();
            repaint();
        }
    }

    private class PreEmptiveSJFAction implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            getContentPane().removeAll();
            // getContentPane().add();
            revalidate();
            repaint();
        }
    }
}
