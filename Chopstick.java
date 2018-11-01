package Model;

import javafx.scene.paint.Color;

import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by oerlex on 2015-12-08.
 */
public class Chopstick extends ReentrantLock {

    private int id;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
