package Model;


import javafx.scene.paint.Color;

import java.util.Date;
import java.util.Random;
import java.util.concurrent.TimeUnit;

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
    private final int Multiplier = 500;
    private Chopstick leftChopstick;
    private Chopstick rightChopstick;
    private double time_Thinking;
    private double time_Hungry;
    private double time_Eating;
    private int counterEating;
    private int counterThinking;
    private int counterHungry;
    private Date startHungry;
    private Controller controller;
    private StringBuilder stringBuilder;
    private boolean isRunning = true;

    public Philosopher(int id, Chopstick chopstickLeft, Chopstick chopstickRight, Controller c, StringBuilder sb){
        this.myState = State.thinking;
        this.id = id;
        this.leftChopstick = chopstickLeft;
        this.rightChopstick = chopstickRight;
        this.controller = c;
        this.stringBuilder = sb;
        isRunning = true;
    }

    public void run(){
        try {
                while (isRunning) {
                    if (myState == State.hungry) {
                        if (leftChopstick.tryLock(1,TimeUnit.SECONDS)) {
                            stringBuilder.append("Philosopher " + id + " is grabbing "+leftChopstick.getId()+" chopstick.\n");
                            try {
                                if (rightChopstick.tryLock(3,TimeUnit.SECONDS)) {
                                    stringBuilder.append("Philosopher " + id + " is grabbing "+rightChopstick.getId()+" chopstick.\n");
                                    try {
                                        eat();
                                    } finally {
                                        putRightChopStickOnTable();
                                    }
                                } else {
                                    Thread.sleep(500);
                                }
                            } finally {
                                putLeftChopStickOnTable();
                            }
                        } else {
                           Thread.sleep(500);
                        }
                    } else think();
                }
        } catch (InterruptedException e) {
            stringBuilder.append("Philosopher " + id + " was interrupted.\n");
        }
    }

    private void think() throws InterruptedException {
        counterThinking++;
        long thinkingTime = 0;
        thinkingTime =calculateStateTime();
        myState = State.thinking;
        printState();
        switch(id){
            case 0: controller.getCircle1().setFill(Color.RED);
                    controller.getChopstick1().setFill(Color.WHITESMOKE);
                    controller.getChopstick5().setFill(Color.WHITESMOKE);
                    break;

            case 1: controller.getCircle2().setFill(Color.RED);
                    controller.getChopstick2().setFill(Color.WHITESMOKE);
                    controller.getChopstick1().setFill(Color.WHITESMOKE);
                    break;
            case 2: controller.getCircle3().setFill(Color.RED);
                    controller.getChopstick3().setFill(Color.WHITESMOKE);
                    controller.getChopstick2().setFill(Color.WHITESMOKE);
                    break;
            case 3: controller.getCircle4().setFill(Color.RED);
                    controller.getChopstick4().setFill(Color.WHITESMOKE);
                    controller.getChopstick3().setFill(Color.WHITESMOKE);
                    break;
            case 4: controller.getCircle5().setFill(Color.RED);
                    controller.getChopstick5().setFill(Color.WHITESMOKE);
                    controller.getChopstick4().setFill(Color.WHITESMOKE);
                    break;
        }
        Thread.sleep(thinkingTime);
        time_Thinking+=thinkingTime;
        hunger();
        myState = State.hungry;
        printState();
    }

    private void eat() throws InterruptedException {
        counterEating++;
        Date endHungry = new Date();
        time_Hungry += endHungry.getTime()-startHungry.getTime();
        long eatingTime = 0;
        eatingTime =calculateStateTime();
        calculateStateTime();
        myState = State.eating;
        switch(id){
            case 0: controller.getCircle1().setFill(Color.GREEN);
                    controller.getChopstick1().setFill(Color.PURPLE);
                    controller.getChopstick5().setFill(Color.PURPLE);
                    break;
            case 1: controller.getCircle2().setFill(Color.GREEN);
                    controller.getChopstick1().setFill(Color.PURPLE);
                    controller.getChopstick2().setFill(Color.PURPLE);
                    break;
            case 2: controller.getCircle3().setFill(Color.GREEN);
                    controller.getChopstick2().setFill(Color.PURPLE);
                    controller.getChopstick3().setFill(Color.PURPLE);
                    break;
            case 3: controller.getCircle4().setFill(Color.GREEN);
                    controller.getChopstick3().setFill(Color.PURPLE);
                    controller.getChopstick4().setFill(Color.PURPLE);
                    break;
            case 4: controller.getCircle5().setFill(Color.GREEN);
                    controller.getChopstick4().setFill(Color.PURPLE);
                    controller.getChopstick5().setFill(Color.PURPLE);
                    break;
        }
        printState();
        Thread.sleep(eatingTime);

        time_Eating+=eatingTime;

    }

    private void hunger(){
        counterHungry++;
        switch(id){
            case 0: controller.getCircle1().setFill(Color.YELLOW);
                    break;
            case 1: controller.getCircle2().setFill(Color.YELLOW);
                    break;
            case 2: controller.getCircle3().setFill(Color.YELLOW);
                    break;
            case 3: controller.getCircle4().setFill(Color.YELLOW);
                    break;
            case 4: controller.getCircle5().setFill(Color.YELLOW);
                    break;
        }

        startHungry = new Date();
    }

    private void putLeftChopStickOnTable(){
        stringBuilder.append("Philosopher " + id + " is putting  "+leftChopstick.getId()+" chopstick down.\n");
        leftChopstick.unlock();
    }

    private void putRightChopStickOnTable(){
        stringBuilder.append("Philosopher " + id + " is putting  "+rightChopstick.getId()+" chopstick down.\n");
        rightChopstick.unlock();
    }



    private long calculateStateTime(){
        Random r = new Random();
        return(Multiplier*(r.nextInt(30)+1));
    }

    public void printState(){
        stringBuilder.append("Philosopher "+ id+" is "+myState+"\n");
    }


    public double getAverageThinking(){
        return (time_Thinking/counterThinking)/1000;
    }
    public double getAverageEating(){
        return (time_Eating/counterEating)/1000;
    }
    public double getAverageHungry(){  return (time_Hungry/counterHungry)/1000; }
    public void setRunning(boolean running) {  isRunning = running;   }

    public boolean isRunning() {  return isRunning;  }
}
