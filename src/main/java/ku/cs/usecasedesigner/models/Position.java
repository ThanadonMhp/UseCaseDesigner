package ku.cs.usecasedesigner.models;

public class Position {
    private int positionID;
    private double xPosition;
    private double yPosition;
    private double fitWidth;
    private double fitHeight;
    private double rotation;
    private int subSystemID;

    public Position(int positionID, double xPosition, double yPosition, double fitWidth, double fitHeight, double rotation) {
        this.positionID = positionID;
        this.xPosition = xPosition;
        this.yPosition = yPosition;
        this.fitWidth = fitWidth;
        this.fitHeight = fitHeight;
        this.rotation = rotation;
        this.subSystemID = 0;
    }

    public Position(int positionID, double xPosition, double yPosition, double fitWidth, double fitHeight, double rotation, int subSystemID) {
        this.positionID = positionID;
        this.xPosition = xPosition;
        this.yPosition = yPosition;
        this.fitWidth = fitWidth;
        this.fitHeight = fitHeight;
        this.rotation = rotation;
        this.subSystemID = subSystemID;
    }

    public int getPositionID() {
        return positionID;
    }

    public double getXPosition() {
        return xPosition;
    }

    public double getYPosition() {
        return yPosition;
    }

    public double getFitWidth() {
        return fitWidth;
    }

    public double getFitHeight() {
        return fitHeight;
    }

    public double getRotation() {
        return rotation;
    }

    public int getSubSystemID() {
        return subSystemID;
    }

    public void setPositionID(int positionID) {
        this.positionID = positionID;
    }

    public void setXPosition(double xPosition) {
        this.xPosition = xPosition;
    }

    public void setYPosition(double yPosition) {
        this.yPosition = yPosition;
    }

    public void setFitWidth(double fitWidth) {
        this.fitWidth = fitWidth;
    }

    public void setFitHeight(double fitHeight) {
        this.fitHeight = fitHeight;
    }

    public void setRotation(double rotation) {
        this.rotation = rotation;
    }
}
