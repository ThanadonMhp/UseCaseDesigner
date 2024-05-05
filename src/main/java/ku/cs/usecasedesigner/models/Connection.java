package ku.cs.usecasedesigner.models;

import javafx.scene.layout.Pane;

public class Connection {
    private double startX;
    private double startY;
    private double endX;
    private double endY;

    public Connection(double startX, double startY, double endX, double endY) {
        this.startX = startX;
        this.startY = startY;
        this.endX = endX;
        this.endY = endY;
    }

    public double getStartX() {
        return startX;
    }

    public double getStartY() {
        return startY;
    }

    public double getEndX() {
        return endX;
    }

    public double getEndY() {
        return endY;
    }

}