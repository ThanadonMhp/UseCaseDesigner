package ku.cs.usecasedesigner.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.RadioButton;
import javafx.stage.Stage;
import ku.cs.fxrouter.FXRouter;
import ku.cs.usecasedesigner.models.Connection;
import ku.cs.usecasedesigner.models.ConnectionList;
import ku.cs.usecasedesigner.services.ConnectionListFileDataSource;
import ku.cs.usecasedesigner.services.DataSource;

import java.io.IOException;
import java.util.ArrayList;

public class ConnectionPageController {

    private String projectName, directory;

    private Integer connectionID, subSystemID;

    private Connection connection;

    private ConnectionList connectionList;

    private DataSource<ConnectionList> connectionListDataSource;


    @FXML private RadioButton associationRadioButton, generalizationRadioButton, includeRadioButton, extendRadioButton;

    @FXML void initialize() {
        if (FXRouter.getData() != null) {
            ArrayList<Object> objects = (ArrayList) FXRouter.getData();
            projectName = (String) objects.get(0);
            directory = (String) objects.get(1);
            subSystemID = (Integer) objects.get(2);
            connectionID = (Integer) objects.get(3);

            // Read the connection list from the file
            connectionListDataSource = new ConnectionListFileDataSource(directory, projectName + ".csv");
            connectionList = connectionListDataSource.readData();

            // Find the connection by connectionID
            connection = connectionList.findByConnectionID(connectionID);

            // Set the radio button to the connection type
            switch (connection.getConnectionType()) {
                case "Association":
                    associationRadioButton.setSelected(true);
                    break;
                case "Generalization":
                    generalizationRadioButton.setSelected(true);
                    break;
                case "Include":
                    includeRadioButton.setSelected(true);
                    break;
                case "Xxtend":
                    extendRadioButton.setSelected(true);
                    break;
            }

            // make sure only one radio button is selected
            associationRadioButton.setOnAction(e -> {
                generalizationRadioButton.setSelected(false);
                includeRadioButton.setSelected(false);
                extendRadioButton.setSelected(false);
            });

            generalizationRadioButton.setOnAction(e -> {
                associationRadioButton.setSelected(false);
                includeRadioButton.setSelected(false);
                extendRadioButton.setSelected(false);
            });

            includeRadioButton.setOnAction(e -> {
                associationRadioButton.setSelected(false);
                generalizationRadioButton.setSelected(false);
                extendRadioButton.setSelected(false);
            });

            extendRadioButton.setOnAction(e -> {
                associationRadioButton.setSelected(false);
                generalizationRadioButton.setSelected(false);
                includeRadioButton.setSelected(false);
            });
        }
    }

    public void handleConfirmButton(ActionEvent actionEvent) throws IOException {
        if (associationRadioButton.isSelected()) {
            connection.setConnectionType("Association");
        } else if (generalizationRadioButton.isSelected()) {
            connection.setConnectionType("Generalization");
        } else if (includeRadioButton.isSelected()) {
            connection.setConnectionType("Include");
        } else if (extendRadioButton.isSelected()) {
            connection.setConnectionType("Extend");
        }

        // Update the connection list
        connectionList.updateConnection(connection);

        // Write the connection list to the file
        connectionListDataSource.writeData(connectionList);

        // send the data back to the previous page
        ArrayList<Object> objects = new ArrayList<>();
        objects.add(projectName);
        objects.add(directory);
        objects.add(subSystemID);

        FXRouter.goTo("HomePage", objects);

        // Close the current window
        Node source = (Node) actionEvent.getSource();
        Stage stage = (Stage) source.getScene().getWindow();
        stage.close();
    }
}
