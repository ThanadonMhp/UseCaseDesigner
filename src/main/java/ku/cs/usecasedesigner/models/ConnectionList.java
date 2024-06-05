package ku.cs.usecasedesigner.models;

import javafx.scene.Node;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

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

    //remove connection by id
    public void removeConnectionByID(int id) {
        for (Connection connection : connectionList) {
            if (connection.getConnectionID() == id) {
                connectionList.remove(connection);
                break;
            }
        }
    }

    public Node findNodeByPosition(double x, double y, Pane pane) {
        // search for an item in pane that is close to the given position (x, y) and return it as a Node
        for (Node node : pane.getChildren()) {
            if (node instanceof VBox) {
                //if VBox contains an ImageView, then the VBox is an Actor
                if (((VBox) node).getChildren().get(0) instanceof ImageView) {
                    double nodeMinX = node.getLayoutX();
                    double nodeMaxX = node.getLayoutX() + ((ImageView) ((VBox) node).getChildren().get(0)).getFitWidth();
                    double nodeMinY = node.getLayoutY();
                    double nodeMaxY = node.getLayoutY() + ((ImageView) ((VBox) node).getChildren().get(0)).getFitHeight();

                    if (x >= nodeMinX && x <= nodeMaxX && y >= nodeMinY && y <= nodeMaxY) {
                        return node;
                    }
                //if VBox contains rectangle
                } else {
                    double nodeMinX = node.getLayoutX();
                    double nodeMaxX = node.getLayoutX() + ((VBox) node).getPrefWidth();
                    double nodeMinY = node.getLayoutY();
                    double nodeMaxY = node.getLayoutY() + ((VBox) node).getPrefHeight();

                    if (x >= nodeMinX && x <= nodeMaxX && y >= nodeMinY && y <= nodeMaxY) {
                        return node;
                    }
                }
            } else if (node instanceof StackPane) {
                double nodeMinX = node.getLayoutX();
                double nodeMaxX = node.getLayoutX() + ((StackPane) node).getPrefWidth();
                double nodeMinY = node.getLayoutY();
                double nodeMaxY = node.getLayoutY() + ((StackPane) node).getPrefHeight();

                if (x >= nodeMinX && x <= nodeMaxX && y >= nodeMinY && y <= nodeMaxY) {
                    return node;
                }
            }
        }
        return null;
    }

    public int findLastConnectionID() {
        int lastConnectionID = 0;
        for (Connection connection : connectionList) {
            if (connection.getConnectionID() > lastConnectionID) {
                lastConnectionID = connection.getConnectionID();
            }
        }
        return lastConnectionID;
    }

    public void updateConnection(int ConnectionID, double startX, double startY, double endX, double endY) {
        for (Connection connection : connectionList) {
            if (connection.getConnectionID() == ConnectionID) {
                connection.setStartX(startX);
                connection.setStartY(startY);
                connection.setEndX(endX);
                connection.setEndY(endY);
            }
        }
    }

    public void clear() {
        connectionList.clear();
    }

    public void removeConnectionBySubSystemID(int id) {
        for (Connection connection : connectionList) {
            if (connection.getSubSystemID() == id) {
                connectionList.remove(connection);
            }
        }
    }

    public Connection findByConnectionID(int id) {
        for (Connection connection : connectionList) {
            if (connection.getConnectionID() == id) {
                return connection;
            }
        }
        return null;
    }

    public void updateConnectionType(int id, String connectionType) {
        for (Connection connection : connectionList) {
            if (connection.getConnectionID() == id) {
                connection.setConnectionType(connectionType);
            }
        }
    }
}
