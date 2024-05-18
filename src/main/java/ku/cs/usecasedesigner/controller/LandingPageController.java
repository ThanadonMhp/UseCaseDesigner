package ku.cs.usecasedesigner.controller;

import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import ku.cs.fxrouter.FXRouter;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class LandingPageController {
    public void handleOpenButton(ActionEvent actionEvent) throws IOException {
        // Open file chooser
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Project");

        // Set extension filter
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("Use Case Designer files (*.csv)", "*.csv");
        fileChooser.getExtensionFilters().add(extFilter);

        // Show open file dialog
        File file = fileChooser.showOpenDialog(null);
        if (file != null) {
            System.out.println("Opening file: " + file.getName());

            // Get the project name from the file name
            String projectName = file.getName().substring(0, file.getName().lastIndexOf("."));

            // Get the directory from the file path
            String directory = file.getParent();

            //send the project name and directory to HomePage
            ArrayList<Object> objects = new ArrayList<>();
            objects.add("loadProject");
            objects.add(projectName);
            objects.add(directory);
            FXRouter.goTo("HomePage" ,objects);

            // Close the current window
            Node source = (Node) actionEvent.getSource();
            Stage stage = (Stage) source.getScene().getWindow();
            stage.close();

        }else {
            System.out.println("No file selected.");
        }
    }

    public void handleNewButton(ActionEvent actionEvent) throws IOException {
        // Show new project window
        System.out.println("New project button clicked.");
        FXRouter.popup("NewProjectPage");

        // Close the current window
        Node source = (Node) actionEvent.getSource();
        Stage stage = (Stage) source.getScene().getWindow();
        stage.close();
    }
}
