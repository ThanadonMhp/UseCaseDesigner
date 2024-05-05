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

    public Node findNodeByPosition(double x, double y, Pane pane) {
        // search for an item in pane that is close to the given position (x, y) and return it as a Node
        for (Node node : pane.getChildren()) {
            if (node instanceof VBox) {
                double nodeMinX = node.getLayoutX();
                double nodeMaxX = node.getLayoutX() + ((ImageView) ((VBox) node).getChildren().get(0)).getFitWidth();
                double nodeMinY = node.getLayoutY();
                double nodeMaxY = node.getLayoutY() + ((ImageView) ((VBox) node).getChildren().get(0)).getFitHeight();

                if (x >= nodeMinX && x <= nodeMaxX && y >= nodeMinY && y <= nodeMaxY) {
                    return node;
                }
            } else if (node instanceof StackPane) {
                double nodeMinX = node.getLayoutX();
                double nodeMaxX = node.getLayoutX() + ((ImageView) ((StackPane) node).getChildren().get(0)).getFitWidth();
                double nodeMinY = node.getLayoutY();
                double nodeMaxY = node.getLayoutY() + ((ImageView) ((StackPane) node).getChildren().get(0)).getFitHeight();

                if (x >= nodeMinX && x <= nodeMaxX && y >= nodeMinY && y <= nodeMaxY) {
                    return node;
                }
            }
        }
        return null;
    }
}
