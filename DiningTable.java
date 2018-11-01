package Model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * Created by oerlex on 2015-11-27.
 */
public class DiningTable implements Runnable {

    private final int MAX_PHILOSOPHERS = 5;
    private Philosopher[] philosophers = new Philosopher[MAX_PHILOSOPHERS];
    public ObservableList<TableData> tableData = FXCollections.observableArrayList();
    private Controller controller;
    private StringBuilder stringBuilder = new StringBuilder();
    private Thread[] philosophersThreads = new Thread[MAX_PHILOSOPHERS];
    private Chopstick[] chopsticks;


    public DiningTable(Controller c){
        this.controller = c;
        chopsticks = new Chopstick[MAX_PHILOSOPHERS];

        for (int i = 0; i < MAX_PHILOSOPHERS; i++) {
            chopsticks[i] = new Chopstick();
            chopsticks[i].setId(i);
        }

        for (int i = 0; i < MAX_PHILOSOPHERS; i++) {
            philosophers[i] = new Philosopher(i, chopsticks[i], chopsticks[(i+1)%MAX_PHILOSOPHERS], controller, stringBuilder);
            philosophersThreads[i] = new Thread(philosophers[i]);
            philosophersThreads[i].start();
            TableData td = new TableData();
            td.setAverageThinking(philosophers[i].getAverageThinking());
            td.setAverageEating(philosophers[i].getAverageEating());
            td.setAverageHungry(philosophers[i].getAverageHungry());
            tableData.add(td);
        }
        System.out.println("Initialization finished");
    }


    @Override
    public void run() {
        while(true){
            try {
                Thread.sleep(3000);
                for(int i=0;i<=MAX_PHILOSOPHERS-1;i++){
                    tableData.get(i).setAverageThinking(philosophers[i].getAverageThinking());
                    tableData.get(i).setAverageEating(philosophers[i].getAverageEating());
                    tableData.get(i).setAverageHungry(philosophers[i].getAverageHungry());
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public Thread[] getPhilosophersThreads() {
        return philosophersThreads;
    }

    public StringBuilder getStringBuilder() {
        return stringBuilder;
    }

    public Philosopher[] getPhilosophers() { return philosophers; }
}
