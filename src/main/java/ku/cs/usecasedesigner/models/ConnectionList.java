package ku.cs.usecasedesigner.models;

import java.util.ArrayList;

public class ConnectionList {
    private ArrayList<Connection> connectionList;

    public ConnectionList() {
        connectionList = new ArrayList<Connection>();
    }

    public ArrayList<Connection> getConnectionList() {
        return connectionList;
    }

    public void addConnection(Connection connection) {
        connectionList.add(connection);
    }

    public void removeConnection(Connection connection) {
        connectionList.remove(connection);
    }

    public Connection findByStartNodeId(double startNodeId) {
        for (Connection connection : connectionList) {
            if (connection.getStartNodeId() == startNodeId) {
                return connection;
            }
        }
        return null;
    }

    public Connection findByEndNodeId(double endNodeId) {
        for (Connection connection : connectionList) {
            if (connection.getEndNodeId() == endNodeId) {
                return connection;
            }
        }
        return null;
    }

    public double findLastConnectionId() {
        double lastConnectionId = 0;
        for (Connection connection : connectionList) {
            if (connection.getStartNodeId() > lastConnectionId) {
                lastConnectionId = connection.getStartNodeId();
            }
        }
        return lastConnectionId;
    }
}
