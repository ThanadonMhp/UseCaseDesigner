package ku.cs.usecasedesigner.models;

public class Connection {
    private int connectionID;
    private String connectionType;
    private double startX;
    private double startY;
    private double endX;
    private double endY;
    private int subSystemID;

    public Connection(int connectionID, String connectionType, double startX, double startY, double endX, double endY) {
        this.connectionID = connectionID;
        this.connectionType = connectionType;
        this.startX = startX;
        this.startY = startY;
        this.endX = endX;
        this.endY = endY;
        this.subSystemID = 0;
    }

    public Connection(int connectionID, String connectionType, double startX, double startY, double endX, double endY, int subSystemID) {
        this.connectionID = connectionID;
        this.connectionType = connectionType;
        this.startX = startX;
        this.startY = startY;
        this.endX = endX;
        this.endY = endY;
        this.subSystemID = subSystemID;
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

    public int getSubSystemID() {
        return subSystemID;
    }

    public void setStartX(double startX) {
        this.startX = startX;
    }

    public void setStartY(double startY) {
        this.startY = startY;
    }

    public void setEndX(double endX) {
        this.endX = endX;
    }

    public void setEndY(double endY) {
        this.endY = endY;
    }

    public void setSubSystemID(int subSystemID) {
        this.subSystemID = subSystemID;
    }
}