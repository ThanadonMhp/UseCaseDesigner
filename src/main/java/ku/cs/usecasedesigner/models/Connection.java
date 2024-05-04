package ku.cs.usecasedesigner.models;

public class Connection {
    private double startNodeId;
    private double endNodeId;

    public Connection(double startNodeId, double endNodeId) {
        this.startNodeId = startNodeId;
        this.endNodeId = endNodeId;
    }

    public double getStartNodeId() {
        return startNodeId;
    }

    public double getEndNodeId() {
        return endNodeId;
    }
}