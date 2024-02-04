package gui;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import algorithms.NonPreemptivePriority;
import algorithms.NonPreemptiveSJF;
import algorithms.PreemptiveSJF;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.font.TextAttribute;

import utils.ProcessContainer;
import component.Process;

public class BasicInputPanel extends JPanel {

    HeaderTitle headerTitle;

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
    
    BasicInputPanel(String algorithmSelected){
        this.setBackground(new Color(0xFFF8DC));
        this.setLayout(new BorderLayout());
        this.headerTitle = new HeaderTitle(algorithmSelected);

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
        CustomField field2 = new CustomField("Burst TIme");
        CustomField field3 = new CustomField("Priority");

        processContainerArray = new ArrayList<>();
        
        JButton addProcessBtn = new JButton("ADD");
        addProcessBtn.setFocusable(false);
        addProcessBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                BasicInputPanel.this.arrivalTime = field1.getValue();
                BasicInputPanel.this.burstTime = field2.getValue();
                BasicInputPanel.this.priority = field3.getValue();

                tableModel.addRow(new Object[]{tableModel.getRowCount(), arrivalTime, burstTime, priority});

                field1.reset();
                field2.reset();
                field3.reset();
                BasicInputPanel.this.requestFocusInWindow();
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
                            BasicInputPanel.this.oneofProcessId = (int) tableModel.getValueAt(row, col);
                        }else if(col == 1){
                            BasicInputPanel.this.oneofArrivalTime = (int) tableModel.getValueAt(row, col);
                        }else if(col == 2){
                            BasicInputPanel.this.oneofBurstTime = (int) tableModel.getValueAt(row, col);
                        }else if(col == 3){
                            BasicInputPanel.this.oneofPriority = (int) tableModel.getValueAt(row, col);
                        }
                    }
                    processContainerArray.add(new ProcessContainer(oneofProcessId, oneofArrivalTime, oneofBurstTime, oneofPriority));
                }

                if(algorithmSelected.equals("Non Preemptive SJF")){
                    runNonPreSJF();
                }else if(algorithmSelected.equals("Non Preemptive Priority")){
                    runNonPrePriority();
                }else if(algorithmSelected.equals("Preemptive SJF")){
                    runPreSJF();
                }

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

    public void runNonPrePriority(){
        NonPreemptivePriority npp = new NonPreemptivePriority();

        for (ProcessContainer process : processContainerArray) {
            npp.addProcess(new Process(process.getProcessId(), process.getArrivalTime(), process.getBurstTime(), process.getPriority()));
        }

        npp.compute();

        OutputScreen outputScreen = new OutputScreen(
                npp.getStackHistory(), npp.getTimeStackHistory(), npp.getTurnaroundTimeData(), npp.getWaitingTimeData(), npp.getProcessCount(), npp.getTotalTurnaroundTime(), npp.getTotalWaitingTime(), npp.getAverageTurnaroundTime(), npp.getAverageWaitingTime()
        );

        BasicInputPanel.this.removeAll();
        BasicInputPanel.this.add(outputScreen);
        BasicInputPanel.this.revalidate();
        BasicInputPanel.this.repaint();
    }

    private void runPreSJF(){
        PreemptiveSJF pes = new PreemptiveSJF();

        for (ProcessContainer process : processContainerArray) {
            pes.addProcess(new Process(process.getProcessId(), process.getArrivalTime(), process.getBurstTime(), process.getPriority()));
        }

        pes.compute();

        OutputScreen outputScreen = new OutputScreen(
                pes.getStackHistory(), pes.getTimeStackHistory(), pes.getTurnaroundTimeData(), pes.getWaitingTimeData(), pes.getProcessCount(), pes.getTotalTurnaroundTime(), pes.getTotalWaitingTime(), pes.getAverageTurnaroundTime(), pes.getAverageWaitingTime()
        );

        BasicInputPanel.this.removeAll();
        BasicInputPanel.this.add(outputScreen);
        BasicInputPanel.this.revalidate();
        BasicInputPanel.this.repaint();
    }

    private void runNonPreSJF() {
        NonPreemptiveSJF nps = new NonPreemptiveSJF();

        for (ProcessContainer process : processContainerArray) {
            nps.addProcess(new Process(process.getProcessId(), process.getArrivalTime(), process.getBurstTime(), process.getPriority()));
        }

        nps.compute();

        OutputScreen outputScreen = new OutputScreen(
                nps.getStackHistory(), nps.getTimeStackHistory(), nps.getTurnaroundTimeData(), nps.getWaitingTimeData(), nps.getProcessCount(), nps.getTotalTurnaroundTime(), nps.getTotalWaitingTime(), nps.getAverageTurnaroundTime(), nps.getAverageWaitingTime()
        );

        BasicInputPanel.this.removeAll();
        BasicInputPanel.this.add(outputScreen);
        BasicInputPanel.this.revalidate();
        BasicInputPanel.this.repaint();
    }
}
