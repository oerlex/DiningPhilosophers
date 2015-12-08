package Model;


import javafx.scene.paint.Color;
import sample.Controller;
import java.util.Date;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;

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
    private final int MAX_STATE_TIME = 5000;
    private Lock leftChopstick;
    private Lock rightChopstick;
    private double time_Thinking;
    private double time_Hungry;
    private double time_Eating;
    private int counterEating;
    private int counterThinking;
    private int counterHungry;
    private Date startHungry = new Date();
    private Controller controller;
    private StringBuilder stringBuilder;

    public Philosopher(int id, Lock chopstickLeft, Lock chopstickRight, Controller c, StringBuilder sb){
        this.myState = State.thinking;
        this.id = id;
        this.leftChopstick = chopstickLeft;
        this.rightChopstick = chopstickRight;
        this.controller = c;
        this.stringBuilder = sb;
    }

    public void run(){
        try {
            while (true) {
                if(myState == State.hungry){
                    printState();
                    if (leftChopstick.tryLock(1, TimeUnit.SECONDS)) {
                        toString();
                        stringBuilder.append("Philosopher " + id + " is grabbing 1 chopstickchopstick.\n");
                        try {
                            if (rightChopstick.tryLock(1, TimeUnit.SECONDS)) {
                                stringBuilder.append("Philosopher " + id + " is grabbing r chopstick.\n");
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
            }
        } catch (InterruptedException e) {
            stringBuilder.append("Philosopher " + id + " was interrupted.\n");
        }
    }

    private void think() throws InterruptedException {
        counterThinking++;
        calculateStateTime();
        switch(id){
            case 0: controller.getCircle1().setFill(Color.RED);
                    controller.getChopstick1().setFill(Color.WHITESMOKE);
                    controller.getChopstick5().setFill(Color.WHITESMOKE);
                    break;
            case 1: controller.getCircle2().setFill(Color.RED);
                    controller.getChopstick1().setFill(Color.WHITESMOKE);
                    controller.getChopstick2().setFill(Color.WHITESMOKE);
                    break;
            case 2: controller.getCircle3().setFill(Color.RED);
                    controller.getChopstick2().setFill(Color.WHITESMOKE);
                    controller.getChopstick3().setFill(Color.WHITESMOKE);
                    break;
            case 3: controller.getCircle4().setFill(Color.RED);
                    controller.getChopstick3().setFill(Color.WHITESMOKE);
                    controller.getChopstick4().setFill(Color.WHITESMOKE);
                    break;
            case 4: controller.getCircle5().setFill(Color.RED);
                    controller.getChopstick4().setFill(Color.WHITESMOKE);
                    controller.getChopstick5().setFill(Color.WHITESMOKE);
                    break;
        }
        myState = State.thinking;
        printState();
        Thread.sleep(stateTime);
        time_Thinking+=stateTime;
        hunger();
    }

    private void eat() throws InterruptedException {
        counterEating++;
        Date endHungry = new Date();
        time_Hungry += endHungry.getTime()-startHungry.getTime();
        calculateStateTime();
        myState = State.eating;
        switch(id){
            case 0: controller.getCircle1().setFill(Color.GREEN);
                    controller.getChopstick1().setFill(Color.PURPLE);
                    controller.getChopstick5().setFill(Color.PURPLE);
                    ;break;
            case 1: controller.getCircle2().setFill(Color.GREEN);
                    controller.getChopstick1().setFill(Color.PURPLE);
                    controller.getChopstick2().setFill(Color.PURPLE);
                    ;break;
            case 2: controller.getCircle3().setFill(Color.GREEN);
                    controller.getChopstick2().setFill(Color.PURPLE);
                    controller.getChopstick3().setFill(Color.PURPLE);
                    ;break;
            case 3: controller.getCircle4().setFill(Color.GREEN);
                    controller.getChopstick3().setFill(Color.PURPLE);
                    controller.getChopstick4().setFill(Color.PURPLE);
                    ;break;
            case 4: controller.getCircle5().setFill(Color.GREEN);
                    controller.getChopstick4().setFill(Color.PURPLE);
                    controller.getChopstick5().setFill(Color.PURPLE);
                    ;break;
        }
        printState();
        Thread.sleep(stateTime);
        time_Eating+=stateTime;
    }

    private void hunger(){
        counterHungry++;
        switch(id){
            case 0: controller.getCircle1().setFill(Color.YELLOW);break;
            case 1: controller.getCircle2().setFill(Color.YELLOW);break;
            case 2: controller.getCircle3().setFill(Color.YELLOW);break;
            case 3: controller.getCircle4().setFill(Color.YELLOW);break;
            case 4: controller.getCircle5().setFill(Color.YELLOW);break;
        }
        myState = State.hungry;
        startHungry.getTime();
    }

    private void putLeftChopStickOnTable(){
        stringBuilder.append("Philosopher " + id + " is putting  l chopstick down.\n");
        leftChopstick.unlock();
    }

    private void putRightChopStickOnTable(){
        stringBuilder.append("Philosopher " + id + " is putting  r chopstick down.\n");
        rightChopstick.unlock();
    }

    private void calculateStateTime(){
        Random r = new Random();
        this.stateTime = (MAX_STATE_TIME-r.nextInt(3000));
    }

    public void printState(){
        stringBuilder.append("Philosopher "+ id+" is "+myState+"\n");
    }


    public double getAverageThinking(){
        return time_Thinking/counterThinking;
    }
    public double getAverageEating(){
        return time_Eating/counterEating;
    }
    public double getAverageHungry(){
        return time_Hungry/counterHungry;
    }

    public int getId() {
        return id;
    }

/*
    switch(id){
        case 1: controller.getCircle1().setFill(Color.GREEN);
            controller.getChopstick1().setFill(Color.WHITESMOKE);
            controller.getLabel1Chopstick().setText("Used by 1");
            controller.getChopstick5().setFill(Color.WHITESMOKE);
            controller.getLabel5Chopstick().setText("Used by 1");
            ;break;
        case 2: controller.getCircle2().setFill(Color.GREEN);
            controller.getChopstick1().setFill(Color.WHITESMOKE);
            controller.getLabel1Chopstick().setText("Used by 2");
            controller.getChopstick2().setFill(Color.WHITESMOKE);
            controller.getLabel2Chopstick().setText("Used by 2");
            ;break;
        case 3: controller.getCircle3().setFill(Color.GREEN);
            controller.getChopstick2().setFill(Color.WHITESMOKE);
            controller.getLabel2Chopstick().setText("Used by 3");
            controller.getChopstick3().setFill(Color.WHITESMOKE);
            controller.getLabel3Chopstick().setText("Used by 3");
            ;break;
        case 4: controller.getCircle4().setFill(Color.GREEN);
            controller.getChopstick3().setFill(Color.WHITESMOKE);
            controller.getLabel3Chopstick().setText("Used by 4");
            controller.getChopstick4().setFill(Color.WHITESMOKE);
            controller.getLabel4Chopstick().setText("Used by 4");
            ;break;
        case 5: controller.getCircle5().setFill(Color.GREEN);
            controller.getChopstick4().setFill(Color.WHITESMOKE);
            controller.getLabel4Chopstick().setText("Used by 5");
            controller.getChopstick5().setFill(Color.WHITESMOKE);
            controller.getLabel5Chopstick().setText("Used by 5");
            ;break;

    }*/

}
