package ku.cs.usecasedesigner.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;
import ku.cs.fxrouter.FXRouter;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class NewProjectPageController {
    private String directory;

    @FXML private Button selectButton;

    @FXML private TextField SystemNameTextField;

    @FXML private Text systemNameErrorText;

    @FXML private Text directoryErrorText;

    public void handleConfirmButton(ActionEvent actionEvent) throws IOException {
        System.out.println("Confirm button clicked.");

        // Check if the system name and directory are empty
        if(SystemNameTextField.getText().isEmpty()){
            systemNameErrorText.setText("Please enter a name.");
            return;
        } else {
            systemNameErrorText.setText("");
        }

        if(directory == null){
            directoryErrorText.setText("Please select a directory.");
            return;
        } else {
            directoryErrorText.setText("");
        }

        // Set value for projectName
        String projectName = SystemNameTextField.getText();

        //send the project name and directory to HomePage
        ArrayList<Object> objects = new ArrayList<>();
        objects.add("loadProject");
        objects.add(projectName);
        objects.add(directory);
        FXRouter.goTo("HomePage", objects);

        // Close the current window
        Node source = (Node) actionEvent.getSource();
        Stage stage = (Stage) source.getScene().getWindow();
        stage.close();
    }

    public void handleSelectButton(ActionEvent actionEvent){
        System.out.println("Select button clicked.");
        // Create directory chooser
        DirectoryChooser directoryChooser = new DirectoryChooser();
        directoryChooser.setTitle("Select Directory");

        // Show directory chooser
        File file = directoryChooser.showDialog(null);
        if (file != null) {
            System.out.println("Selected directory: " + file.getAbsolutePath());
            directory = file.getAbsolutePath();
            selectButton.setText(directory);
        } else {
            System.out.println("No directory selected.");
        }
    }
}
