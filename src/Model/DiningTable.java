package Model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import sample.Controller;
import sample.TableData;

import java.util.Queue;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by oerlex on 2015-11-27.
 */
public class DiningTable implements Runnable {

    private final int MAX_PHILOSOPHERS = 5;
    private Philosopher[] philosophers = new Philosopher[MAX_PHILOSOPHERS];
    public ObservableList<TableData> tableData = FXCollections.observableArrayList();
    private Controller controller;
    private StringBuilder sb = new StringBuilder();
    private Thread[] philosophersThreads = new Thread[MAX_PHILOSOPHERS];


    public DiningTable(Controller c){
        this.controller = c;
        // Model each chopstick with a lock
        Lock[] chopsticks = new ReentrantLock[MAX_PHILOSOPHERS];

        for (int i = 0; i < MAX_PHILOSOPHERS; i++) {
            chopsticks[i] = new ReentrantLock();
        }

        for (int i = 0; i < MAX_PHILOSOPHERS; i++) {
            philosophers[i] = new Philosopher(i, chopsticks[i], chopsticks[(i+1)%MAX_PHILOSOPHERS], controller, sb);
            philosophersThreads[i] = new Thread(philosophers[i]);
            philosophersThreads[i].start();
            TableData td = new TableData();
            td.setAverageThinking(philosophers[i].getAverageThinking());
            td.setAverageEating(philosophers[i].getAverageEating());
            td.setAverageHungry(philosophers[i].getAverageHungry());
            tableData.add(td);
        }
    }


    @Override
    public void run() {
        while(true){
            try {
                Thread.sleep(1000);
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

    public StringBuilder getSb() {
        return sb;
    }
}
