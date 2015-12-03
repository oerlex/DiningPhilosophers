package sample;

import Model.DiningTable;
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

public class Controller implements Initializable{

    DiningTable t1;
    Circle[] allPhilosopherCircles;

    Thread tableThread;

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
    private Button startButton;

    @FXML
    void startDining(ActionEvent event) {
            for (Thread t : t1.getPhilosophersThreads()) {
                if (t.isAlive())
                    t.interrupt();
            }
            tableThread.interrupt();
            writeToFile(t1.getSb());

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        allPhilosopherCircles =  new Circle[]{circle1, circle2, circle3, circle4, circle5};

        t1 = new DiningTable(this);
        TableColumn<TableData,Double> avgThinkingColumn = new TableColumn<TableData,Double>("averageThinking");
        avgThinkingColumn.setCellValueFactory(new PropertyValueFactory("averageThinking"));

        TableColumn<TableData,Double> avgEatingColumn = new TableColumn<TableData,Double>("averageEating");
        avgEatingColumn.setCellValueFactory(new PropertyValueFactory("averageEating"));

        TableColumn<TableData,Double> avgHungerColumn = new TableColumn<TableData,Double>("averageHungry");
        avgHungerColumn.setCellValueFactory(new PropertyValueFactory("averageHungry"));


        table.getColumns().setAll(avgThinkingColumn, avgEatingColumn, avgHungerColumn);
        table.setItems(t1.tableData);
        tableThread = new Thread(t1);
        tableThread.start();
    }


    private void writeToFile(StringBuilder stringBuilder ){
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

    public Circle[] getAllPhilosopherCircles() {
        return allPhilosopherCircles;
    }

    public Circle getCircle5() {
        return circle5;
    }

    public void setCircle5(Circle circle5) {
        this.circle5 = circle5;
    }

    public Circle getCircle2() {
        return circle2;
    }

    public void setCircle2(Circle circle2) {
        this.circle2 = circle2;
    }

    public Circle getCircle3() {
        return circle3;
    }

    public void setCircle3(Circle circle3) {
        this.circle3 = circle3;
    }

    public Circle getCircle1() {
        return circle1;
    }

    public void setCircle1(Circle circle1) {
        this.circle1 = circle1;
    }

    public Circle getCircle4() {
        return circle4;
    }

    public void setCircle4(Circle circle4) {
        this.circle4 = circle4;
    }

    public Rectangle getChopstick3() {
        return chopstick3;
    }

    public void setChopstick3(Rectangle chopstick3) {
        this.chopstick3 = chopstick3;
    }

    public Rectangle getChopstick2() {
        return chopstick2;
    }

    public void setChopstick2(Rectangle chopstick2) {
        this.chopstick2 = chopstick2;
    }

    public Rectangle getChopstick5() {
        return chopstick5;
    }

    public void setChopstick5(Rectangle chopstick5) {
        this.chopstick5 = chopstick5;
    }

    public Rectangle getChopstick1() {
        return chopstick1;
    }

    public void setChopstick1(Rectangle chopstick1) {
        this.chopstick1 = chopstick1;
    }

    public Rectangle getChopstick4() {
        return chopstick4;
    }

    public void setChopstick4(Rectangle chopstick4) {
        this.chopstick4 = chopstick4;
    }
}
