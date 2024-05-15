package ku.cs.usecasedesigner.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import ku.cs.fxrouter.FXRouter;

import java.io.IOException;
import java.util.ArrayList;

public class LabelPageController {

    @FXML private Text labelText, errorText;

    @FXML private TextField labelTextField;

    public void handleConfirmButton(ActionEvent actionEvent) throws IOException {
        if (labelTextField.getText().isEmpty()) {
            errorText.setText("Please enter a label.");
        } else {
            errorText.setText("");

            // Send the label to the previous page
            ArrayList<Object> objects = new ArrayList<>();
            objects.add("label");
            objects.add(labelTextField.getText());

            FXRouter.goTo("HomePage", objects);

            // Close the current window
            Node source = (Node) actionEvent.getSource();
            Stage stage = (Stage) source.getScene().getWindow();
            stage.close();
        }
    }
}
