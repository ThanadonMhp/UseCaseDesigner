package ku.cs.usecasedesigner.models;

public class Connection {
    private int connectionID;
    private double startX;
    private double startY;
    private double endX;
    private double endY;
    private String label;
    private String arrowHead;
    private String lineType;
    private String arrowTail;
    private String note;
    private int subSystemID;

    public Connection(int connectionID, double startX, double startY, double endX, double endY, String label, String arrowHead, String lineType, String arrowTail, String note, int subSystemID) {
        this.connectionID = connectionID;
        this.startX = startX;
        this.startY = startY;
        this.endX = endX;
        this.endY = endY;
        this.label = label;
        this.arrowHead = arrowHead;
        this.lineType = lineType;
        this.arrowTail = arrowTail;
        this.note = note;
        this.subSystemID = subSystemID;
    }

    public int getConnectionID() {
        return connectionID;
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

    public String getArrowHead() {
        return arrowHead;
    }

    public String getLineType() {
        return lineType;
    }

    public String getArrowTail() {
        return arrowTail;
    }


    public String getNote() {
        return note;
    }

    public int getSubSystemID() {
        return subSystemID;
    }

    public void setConnectionID(int connectionID) {
        this.connectionID = connectionID;
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

    public void setLabel(String label) {
        this.label = label;
    }

    public void setArrowHead(String arrowHead) {
        this.arrowHead = arrowHead;
    }

    public void setLineType(String lineType) {
        this.lineType = lineType;
    }

    public void setArrowTail(String arrowTail) {
        this.arrowTail = arrowTail;
    }

    public void setSubSystemID(int subSystemID) {
        this.subSystemID = subSystemID;
    }

    public void setNote(String note) {
        this.note = note;
    }
}