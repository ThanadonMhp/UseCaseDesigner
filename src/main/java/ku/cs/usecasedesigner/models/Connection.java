package ku.cs.usecasedesigner.models;

public class Connection {
    private String type;
    private double startX;
    private double startY;
    private double endX;
    private double endY;
    private String label;

    public Connection(double startX, double startY, double endX, double endY, String label) {
        this.type = "connection";
        this.startX = startX;
        this.startY = startY;
        this.endX = endX;
        this.endY = endY;
        this.label = label;
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

    public String getLabel() {
        return label;
    }
}