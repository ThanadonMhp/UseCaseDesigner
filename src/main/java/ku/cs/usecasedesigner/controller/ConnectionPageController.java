package ku.cs.usecasedesigner.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
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

    @FXML
    private ChoiceBox<String> startChoiceBox, lineChoiceBox, endChoiceBox;

    @FXML
    private TextField labelTextField;

    @FXML
    private TextArea noteTextArea;

    private DataSource<ConnectionList> connectionListDataSource;


    @FXML private RadioButton associationRadioButton, generalizationRadioButton, includeRadioButton, extendRadioButton, userDefinedRadioButton;

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

            // set noteTextArea to the connection note
            if (!connection.getNote().equals("!@#$%^&*()_+")) {
                noteTextArea.setText(connection.getNote());
            }

            // Set the radio button to the connection type
            switch (connection.getLabel()) {
                case "Association":
                    associationRadioButton.setSelected(true);
                    break;
                case "Generalization":
                    generalizationRadioButton.setSelected(true);
                    break;
                case "<<Include>>":
                    includeRadioButton.setSelected(true);
                    break;
                case "<<Extend>>":
                    extendRadioButton.setSelected(true);
                    break;
                default:
                    userDefinedRadioButton.setSelected(true);
                    break;
            }

            // make sure only one radio button is selected
            associationRadioButton.setOnAction(e -> {
                generalizationRadioButton.setSelected(false);
                includeRadioButton.setSelected(false);
                extendRadioButton.setSelected(false);
                userDefinedRadioButton.setSelected(false);
            });

            generalizationRadioButton.setOnAction(e -> {
                associationRadioButton.setSelected(false);
                includeRadioButton.setSelected(false);
                extendRadioButton.setSelected(false);
                userDefinedRadioButton.setSelected(false);
            });

            includeRadioButton.setOnAction(e -> {
                associationRadioButton.setSelected(false);
                generalizationRadioButton.setSelected(false);
                extendRadioButton.setSelected(false);
                userDefinedRadioButton.setSelected(false);
            });

            extendRadioButton.setOnAction(e -> {
                associationRadioButton.setSelected(false);
                generalizationRadioButton.setSelected(false);
                includeRadioButton.setSelected(false);
                userDefinedRadioButton.setSelected(false);
            });

            userDefinedRadioButton.setOnAction(e -> {
                associationRadioButton.setSelected(false);
                generalizationRadioButton.setSelected(false);
                includeRadioButton.setSelected(false);
                extendRadioButton.setSelected(false);
            });

            startChoiceBox.getItems().addAll("none", "<", "⨞");

            lineChoiceBox.getItems().addAll("――――――――――", "― ― ― ― ― ― ―");

            endChoiceBox.getItems().addAll("none", ">", "▷");
        }
    }

    public void handleConfirmButton(ActionEvent actionEvent) throws IOException {
        if (associationRadioButton.isSelected()) {
            connection.setArrowHead("none");
            connection.setLabel("Association");
            connection.setLineType("line");
            connection.setArrowTail("none");
        } else if (includeRadioButton.isSelected()) {
            connection.setArrowHead("none");
            connection.setLabel("<<Include>>");
            connection.setLineType("dash");
            connection.setArrowTail("open");
        } else if (extendRadioButton.isSelected()) {
            connection.setArrowHead("open");
            connection.setLabel("<<Extend>>");
            connection.setLineType("dash");
            connection.setArrowTail("none");
        } else if (generalizationRadioButton.isSelected()) {
            connection.setArrowHead("close");
            connection.setLabel("Generalization");
            connection.setLineType("line");
            connection.setArrowTail("none");
        } else {
            if (startChoiceBox.getValue().equals("<")) {
                connection.setArrowHead("open");
            } else if (startChoiceBox.getValue().equals("⨞")) {
                connection.setArrowHead("close");
            } else {
                connection.setArrowHead("none");
            }

            if (endChoiceBox.getValue().equals(">")) {
                connection.setArrowTail("open");
            } else if (endChoiceBox.getValue().equals("▷")) {
                connection.setArrowTail("close");
            } else {
                connection.setArrowTail("none");
            }

            if (lineChoiceBox.getValue().equals("――――――――――")) {
                connection.setLineType("line");
            } else {
                connection.setLineType("dash");
            }

            if (labelTextField.getText().isEmpty()) {
                connection.setLabel("!@#$%^&*()_+");
            } else {
                connection.setLabel(labelTextField.getText());
            }
        }

        if (noteTextArea.getText().isEmpty()) {
            connection.setNote("!@#$%^&*()_+");
        } else {
            connection.setNote(noteTextArea.getText());
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
