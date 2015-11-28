package Model;

import java.util.Random;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by oerlex on 2015-11-27.
 */
public class Philosopher implements Runnable{

    private enum State{
        thinking,
        eating,
        hungry
    }

    private State myState;
    private int id;
    private long stateTime;
    private final int MAX_STATE_TIME = 1500;
    private Lock leftChopstick;
    private Lock rightChopstick;
    private double time_Thinking;
    private double time_Hungry;
    private double time_Eating;
    private int temp;

    public Philosopher(int id, Lock chopstickLeft, Lock chopstickRight){
        this.myState = State.thinking;
        this.id = id;
        this.leftChopstick = chopstickLeft;
        this.rightChopstick = chopstickRight;
    }

    public void run(){
        try {
            while (true) {
                if(myState == State.hungry){
                    printState();
                    if (leftChopstick.tryLock()) {
                        System.out.println("Philosopher " + id + " is grabbing 1 chopstickchopstick.");
                        try {
                            if (rightChopstick.tryLock()) {
                                System.out.println("Philosopher " + id + " is grabbing r chopstick.");
                                try {
                                   eat();
                                }
                                finally {
                                   putRightChopStickOnTable();
                                }
                            }
                        }
                        finally {
                            putLeftChopStickOnTable();
                        }
                    }
                }else{
                    think();
                }
                Thread.sleep(500);
            }
        } catch (InterruptedException e) {
            System.out.println("Philosopher " + id + " was interrupted.\n");
        }
    }

    private void think() throws InterruptedException {
        calculateStateTime();
        myState = State.thinking;
        printState();
        Thread.sleep(stateTime);
        time_Thinking+=stateTime;
    }

    private void eat() throws InterruptedException {
        calculateStateTime();
        myState = State.eating;
        printState();
        Thread.sleep(stateTime);
        time_Eating+=stateTime;
    }

    private void hungry() throws InterruptedException{
        calculateStateTime();
        myState = State.hungry;
        printState();

    }

    private void putLeftChopStickOnTable(){
        System.out.println("Philosopher " + id + " is putting  l chopstick down.");
        leftChopstick.unlock();
    }

    private void putRightChopStickOnTable(){
        System.out.println("Philosopher " + id + " is putting  r chopstick down.");
        rightChopstick.unlock();
    }

    private void calculateStateTime(){
        Random r = new Random();
        this.stateTime = (MAX_STATE_TIME-r.nextInt(30));
    }


    public void printState(){
        System.out.println("Philosopher "+ id+" is "+myState);
    }

}
