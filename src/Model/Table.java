package Model;

import java.util.Queue;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by oerlex on 2015-11-27.
 */
public class Table {

    private Queue<Philosopher> waitingQueue;
    private final int MAX_PHILOSOPHERS = 5;

    public Table(){
        simulate();
    }




    public void simulate(){
        // Model each chopstick with a lock
        Lock[] chopsticks = new ReentrantLock[MAX_PHILOSOPHERS];

        for (int i = 0; i < MAX_PHILOSOPHERS; i++) {
            chopsticks[i] = new ReentrantLock();
        }

        // Create the philosophers and start each running in its own thread.
        Philosopher[] philosophers = new Philosopher[MAX_PHILOSOPHERS];

        for (int i = 0; i < MAX_PHILOSOPHERS; i++) {
            philosophers[i] = new Philosopher(i, chopsticks[i], chopsticks[(i+1)%MAX_PHILOSOPHERS]);
            new Thread(philosophers[i]).start();
        }
    }

}
