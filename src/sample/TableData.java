package sample;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;

public class TableData {
    private DoubleProperty averageThinking;
    private DoubleProperty averageEating;
    private DoubleProperty averageHungry;


    public void setAverageThinking(Double value) { averageThinkingProperty().set(value); }
    public DoubleProperty averageThinkingProperty() {
        if (averageThinking == null) averageThinking = new SimpleDoubleProperty(this, "averageThinking");
        return averageThinking;
    }

    public void setAverageEating(Double value) { averageEatingProperty().set(value); }
    public DoubleProperty averageEatingProperty() {
        if (averageEating == null) averageEating = new SimpleDoubleProperty(this, "averageEating");
        return averageEating;
    }

    public void setAverageHungry(Double value) { averageHungryProperty().set(value); }
    public DoubleProperty averageHungryProperty() {
        if (averageHungry == null) averageHungry = new SimpleDoubleProperty(this, "averageHungry");
        return averageHungry;
    }

}