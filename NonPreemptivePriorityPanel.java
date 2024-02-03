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
import algorithms.NonPreemptivePriority;
import utils.ProcessContainer;

import java.awt.Font;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.font.TextAttribute;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class NonPreemptivePriorityPanel extends JPanel {

    HeaderTitle headerTitle = new HeaderTitle("Non-Preemptive Priority");
    
    DefaultTableModel tableModel;
    JTable table;

    int arrivalTime;
    int burstTime;
    int priority;

    ArrayList<ProcessContainer> processContainerArray;

    NonPreemptivePriorityPanel() {
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

        CustomField field1 = new CustomField("Arrival Time");
        CustomField field2 = new CustomField("Burst Time");
        CustomField field3 = new CustomField("Priority");

        processContainerArray = new ArrayList<>();

        JButton addProcessBtn = new JButton("ADD");
        addProcessBtn.setFocusable(false);
        addProcessBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                NonPreemptivePriorityPanel.this.arrivalTime = field1.getValue();
                NonPreemptivePriorityPanel.this.burstTime = field2.getValue();
                NonPreemptivePriorityPanel.this.priority = field3.getValue();

                tableModel.addRow(new Object[]{tableModel.getRowCount(), arrivalTime, burstTime, priority});

                field1.reset();
                field2.reset();
                field3.reset();
                NonPreemptivePriorityPanel.this.requestFocusInWindow();
            }
        });

        JButton simulateBtn = new JButton("SIMULATE");
        simulateBtn.setFocusable(false);
        simulateBtn.setPreferredSize(new Dimension(390, 50));
        simulateBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                for (int row = 0; row < tableModel.getRowCount(); row++) {
                    for (int col = 0; col < tableModel.getColumnCount(); col++) {
                        if (col == 0) {
                            NonPreemptivePriorityPanel.this.arrivalTime = (int) tableModel.getValueAt(row, col);
                        } else if (col == 1) {
                            NonPreemptivePriorityPanel.this.burstTime = (int) tableModel.getValueAt(row, col);
                        } else if (col == 2) {
                            NonPreemptivePriorityPanel.this.priority = (int) tableModel.getValueAt(row, col);
                        }
                    }
                    processContainerArray.add(new ProcessContainer(row, NonPreemptivePriorityPanel.this.arrivalTime, NonPreemptivePriorityPanel.this.burstTime, NonPreemptivePriorityPanel.this.priority));
                }

                NonPreemptivePriority nonPreemptivePriority = new NonPreemptivePriority();

                for (ProcessContainer process : processContainerArray) {
                    nonPreemptivePriority.addProcess(new Process(process.getProcessId(), process.getArrivalTime(), process.getBurstTime(), process.getPriority()));
                }

                nonPreemptivePriority.compute();

                OutputScreen outputScreen = new OutputScreen(
                        nonPreemptivePriority.getStackHistory(), nonPreemptivePriority.getTimeStackHistory(), nonPreemptivePriority.getTurnaroundTimeData(), nonPreemptivePriority.getWaitingTimeData(), nonPreemptivePriority.getProcessCount(), nonPreemptivePriority.getTotalTurnaroundTime(), nonPreemptivePriority.getTotalWaitingTime(), nonPreemptivePriority.getAverageTurnaroundTime(), nonPreemptivePriority.getAverageWaitingTime()
                );

                NonPreemptivePriorityPanel.this.removeAll();
                NonPreemptivePriorityPanel.this.add(outputScreen);
                revalidate();
                repaint();
            }
        });

        inputPanel.add(field1);
        inputPanel.add(field2);
        inputPanel.add(field3);
        inputPanel.add(addProcessBtn);
        inputPanel.add(simulateBtn);
        inputPanel.add(Box.createRigidArea(new Dimension(0, 180)));

        JScrollPane displayPanel = new JScrollPane(table);
        displayPanel.setBackground(Color.WHITE);

        this.add(headerTitle, BorderLayout.NORTH);
        this.add(inputPanel, BorderLayout.WEST);
        this.add(displayPanel, BorderLayout.CENTER);

        this.setOpaque(true);
    }
}
