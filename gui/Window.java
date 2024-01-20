package gui;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class Window extends JFrame{

    JPanel redPanel;
    JPanel bluePanel;
    
    public Window(List<Integer> stackHistory){
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
        ImageIcon downIcon = new ImageIcon("down.png");
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

        OutputScreen outputScreen = new OutputScreen(stackHistory);
        getContentPane().add(outputScreen);


        // for testing purposes
        redPanel = new JPanel();
        redPanel.setBackground(Color.red);
        redPanel.setOpaque(true);

        bluePanel = new JPanel();
        bluePanel.setBackground(Color.blue);
        bluePanel.setOpaque(true);



        // set visibility to last
        this.setVisible(true);
    }

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
            getContentPane().add(bluePanel);
            revalidate();
            repaint();
        }
    }
}
