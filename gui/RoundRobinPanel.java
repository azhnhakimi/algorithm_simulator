package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import component.Process;

import algorithms.RoundRobin;
import utils.ProcessContainer;

import java.awt.Font;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.font.TextAttribute;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class RoundRobinPanel extends JPanel {

    HeaderTitle headerTitle = new HeaderTitle("Round Robin"); 
    int quantumCount;

    int arrivalTime;
    int burstTime;
    int priority;

    DefaultTableModel tableModel;
    JTable table;

    int oneofArrivalTime;
    int oneofBurstTime;
    int oneofProcessId;
    int oneofPriority;
    ArrayList<ProcessContainer> processContainerArray;
    
    
    RoundRobinPanel(){
        this.setBackground(new Color(0xFFF8DC));
        this.setLayout(new BorderLayout());

        // Initialize the table model with headers
        tableModel = new DefaultTableModel();
        tableModel.setColumnIdentifiers(new String[]{"Process ID", "Arrival Time", "Burst Time", "Priority"});

        // Initialize the table with the table model
        table = new JTable(tableModel);

        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);

        // Apply the renderer to all columns
        for (int i = 0; i < table.getColumnCount(); i++) {
            table.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }

        // Customizing font for column headers
        Font headerFont = table.getTableHeader().getFont();
        Map<TextAttribute, Object> fontAttributesHeader = new HashMap<>();
        fontAttributesHeader.put(TextAttribute.WEIGHT, TextAttribute.WEIGHT_BOLD);
        fontAttributesHeader.put(TextAttribute.SIZE, 14); // Set font size
        table.getTableHeader().setFont(headerFont.deriveFont(fontAttributesHeader));

        // Customizing font for cells
        Font cellFont = table.getFont();
        Map<TextAttribute, Object> fontAttributesCell = new HashMap<>();
        fontAttributesCell.put(TextAttribute.SIZE, 14); // Set font size
        table.setFont(cellFont.deriveFont(fontAttributesCell));

        table.setBackground(Color.WHITE);

        JPanel inputPanel = new JPanel();
        inputPanel.setPreferredSize(new Dimension(400, 10));
        inputPanel.setLayout(new FlowLayout(FlowLayout.LEADING));
        inputPanel.setBackground(new Color(0xFFF8DC));

        CustomField quantumTimeField = new CustomField("Quantum Time");
        CustomField field1 = new CustomField("Arrival Time");
        CustomField field2 = new CustomField("Burst TIme");
        CustomField field3 = new CustomField("Priority");

        processContainerArray = new ArrayList<>();

        JButton addProcessBtn = new JButton("ADD");
        addProcessBtn.setFocusable(false);
        addProcessBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                RoundRobinPanel.this.arrivalTime = field1.getValue();
                RoundRobinPanel.this.burstTime = field2.getValue();
                RoundRobinPanel.this.priority = field3.getValue();

                tableModel.addRow(new Object[]{tableModel.getRowCount(), arrivalTime, burstTime, priority});

                field1.reset();
                field2.reset();
                field3.reset();
                RoundRobinPanel.this.requestFocusInWindow();
            }
        });


        JButton simulateBtn = new JButton("SIMULATE");
        simulateBtn.setFocusable(false);
        simulateBtn.setPreferredSize(new Dimension(390, 50));
        simulateBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                for (int row = 0; row < tableModel.getRowCount(); row++) {
                    for (int col = 0; col < tableModel.getColumnCount(); col++) {
                        if(col == 0){
                            RoundRobinPanel.this.oneofProcessId = (int) tableModel.getValueAt(row, col);
                        }else if(col == 1){
                            RoundRobinPanel.this.oneofArrivalTime = (int) tableModel.getValueAt(row, col);
                        }else if(col == 2){
                            RoundRobinPanel.this.oneofBurstTime = (int) tableModel.getValueAt(row, col);
                        }else if(col == 3){
                            RoundRobinPanel.this.oneofPriority = (int) tableModel.getValueAt(row, col);
                        }
                    }
                    processContainerArray.add(new ProcessContainer(oneofProcessId, oneofArrivalTime, oneofBurstTime, oneofPriority));
                }

                RoundRobin roundRobin = new RoundRobin(RoundRobinPanel.this.quantumCount);

                for(ProcessContainer process :  processContainerArray){
                    roundRobin.addProcess(new Process(process.getProcessId(), process.getArrivalTime(), process.getBurstTime(), process.getPriority()));
                }

                roundRobin.compute();

                OutputScreen outputScreen = new OutputScreen(
                    roundRobin.getStackHistory(), roundRobin.getTimeStackHistory(), roundRobin.getTurnaroundTimeData(), roundRobin.getWaitingTimeData(), roundRobin.getProcessCount(), roundRobin.getTotalTurnaroundTime(), roundRobin.getTotalWaitingTime(), roundRobin.getAverageTurnaroundTime(), roundRobin.getAverageWaitingTime()
                );

                RoundRobinPanel.this.removeAll();
                RoundRobinPanel.this.add(outputScreen);
                revalidate();
                repaint();
            }
        });


        JButton setQuantumCount = new JButton("SET");
        setQuantumCount.setFocusable(false);
        setQuantumCount.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                int result = quantumTimeField.getValue();
                if(result != 0){
                    RoundRobinPanel.this.quantumCount = result;
                    setQuantumCount.setEnabled(false);
                    quantumTimeField.setEditable(false);
                    RoundRobinPanel.this.requestFocusInWindow();

                    inputPanel.add(field1);
                    inputPanel.add(field2);
                    inputPanel.add(field3);
                    inputPanel.add(addProcessBtn);
                    inputPanel.add(simulateBtn);
                    inputPanel.add(Box.createRigidArea(new Dimension(0, 180)));

                    revalidate();
                    repaint();
                    
                }
            }
        });


        inputPanel.add(quantumTimeField);
        inputPanel.add(setQuantumCount);
        
        JScrollPane displayPanel = new JScrollPane(table);
        displayPanel.setBackground(Color.WHITE);
        
        this.add(headerTitle, BorderLayout.NORTH);
        this.add(inputPanel, BorderLayout.WEST);
        this.add(displayPanel, BorderLayout.CENTER);

        this.setOpaque(true); 
    }
    
}
