package ku.cs.usecasedesigner.models;

public class Connection {
    private int connectionID;
    private String connectionType;
    private double startX;
    private double startY;
    private double endX;
    private double endY;

    public Connection(int connectionID, String connectionType, double startX, double startY, double endX, double endY) {
        this.connectionID = connectionID;
        this.connectionType = connectionType;
        this.startX = startX;
        this.startY = startY;
        this.endX = endX;
        this.endY = endY;
    }

    public int getConnectionID() {
        return connectionID;
    }

    public String getConnectionType() {
        return connectionType;
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