package gui;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.util.Map;

import javax.swing.JPanel;

import java.awt.Font;
import utils.WaitingTimeVal;

public class WaitingTable extends JPanel {
    
    int row;
    int column;
    int cellWidth = 150;
    int cellHeight = 40;
    Cell[][] cellArray;
    Map<Integer, WaitingTimeVal> waitingTimeData;

    WaitingTable(int row, int column, Map<Integer, WaitingTimeVal> waitingTimeData){
        this.row = row;
        this.column = column;
        this.waitingTimeData = waitingTimeData;


        cellArray = new Cell[row][column];
        this.setLayout(new GridLayout(row, column));

        int xLength = cellWidth * column;
        int yLength = cellHeight * row;
        this.setPreferredSize(new Dimension(xLength, yLength));

        // initialze the cells
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < column; j++){
                Cell cell = new Cell();
                cellArray[i][j] = cell ;
                this.add(cell);
            }
        }

        // intialize the headers
        for (int i = 0; i < column; i++){
            if(i == 0){
                cellArray[0][i].setText("Process ID");
                cellArray[0][i].setFont(new Font("Arial", Font.BOLD, 15));
            }else if(i == 1){
                cellArray[0][i].setText("Burst Time");
                cellArray[0][i].setFont(new Font("Arial", Font.BOLD, 15));
            }else if(i == 2){   
                cellArray[0][i].setText("Turnaround Time");
                cellArray[0][i].setFont(new Font("Arial", Font.BOLD, 15));
            }else if(i == 3){
                cellArray[0][i].setText("Waiting Time");
                cellArray[0][i].setFont(new Font("Arial", Font.BOLD, 15));
            }
        }
        
        // populate the table with data
        for(int i = 1; i < row; i++){
            for(int j = 0; j < column; j++){
                WaitingTimeVal values = this.waitingTimeData.get(i - 1);
                if(j == 0){
                    String text = String.valueOf(values.getProcessID());
                    cellArray[i][j].setText(text);
                }else if(j == 1){
                    String text = String.valueOf(values.getBurstTime());
                    cellArray[i][j].setText(text);
                }else if(j == 2){
                    String text = String.valueOf(values.getTurnaroundTime());
                    cellArray[i][j].setText(text);
                }else if(j == 3){
                    String text = String.valueOf(values.getWaitingTime());
                    cellArray[i][j].setText(text);
                }
            }
        }
        
    }
    
}
