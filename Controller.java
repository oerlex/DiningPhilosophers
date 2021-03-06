package Model;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;

import java.io.*;
import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable {

    DiningTable diningTable;
    Thread tableThread;


    @FXML
    void stopDining(ActionEvent event) {
        for (Philosopher p: diningTable.getPhilosophers()) {
            if (p.isRunning()){
                p.setRunning(false);
            }
        }

        for(Thread t: diningTable.getPhilosophersThreads()){
            if(t.isAlive()){
                t.interrupt();
            }
        }
        writeToFile(diningTable.getStringBuilder());

    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {

        diningTable = new DiningTable(this);
        TableColumn<TableData, Double> avgThinkingColumn = new TableColumn<TableData, Double>("averageThinking");
        avgThinkingColumn.setCellValueFactory(new PropertyValueFactory("averageThinking"));

        TableColumn<TableData, Double> avgEatingColumn = new TableColumn<TableData, Double>("averageEating");
        avgEatingColumn.setCellValueFactory(new PropertyValueFactory("averageEating"));

        TableColumn<TableData, Double> avgHungerColumn = new TableColumn<TableData, Double>("averageHungry");
        avgHungerColumn.setCellValueFactory(new PropertyValueFactory("averageHungry"));

        table.getColumns().setAll(avgThinkingColumn, avgEatingColumn, avgHungerColumn);
        table.setItems(diningTable.tableData);

        tableThread = new Thread(diningTable);
        tableThread.start();
    }


    private void writeToFile(StringBuilder stringBuilder) {
        PrintWriter writer = null;
        try {
            writer = new PrintWriter("diningLog.txt", "UTF-8");
            writer.write(stringBuilder.toString());
            writer.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    public synchronized Circle getCircle3() {
        return circle3;
    }

    public synchronized Circle getCircle5() {
        return circle5;
    }

    public synchronized Circle getCircle2() {
        return circle2;
    }

    public synchronized Circle getCircle4() {
        return circle4;
    }

    public synchronized Circle getCircle1() {
        return circle1;
    }

    public synchronized Rectangle getChopstick4() {    return chopstick4;   }

    public synchronized Rectangle getChopstick3() {    return chopstick3;   }

    public synchronized Rectangle getChopstick1() {    return chopstick1;   }

    public synchronized  Rectangle getChopstick5() {    return chopstick5;   }

    public synchronized Rectangle getChopstick2() {    return chopstick2;   }

    @FXML
    private Circle circle5;

    @FXML
    private Circle circle2;

    @FXML
    private Circle circle3;

    @FXML
    private Circle circle4;

    @FXML
    private Circle circle1;

    @FXML
    private Rectangle chopstick3;

    @FXML
    private Rectangle chopstick1;

    @FXML
    private Rectangle chopstick5;

    @FXML
    private Rectangle chopstick2;

    @FXML
    private Rectangle chopstick4;

    @FXML
    private TableView<TableData> table;

    @FXML
    private Button stopButton;



}