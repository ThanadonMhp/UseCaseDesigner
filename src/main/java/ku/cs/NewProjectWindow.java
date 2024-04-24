package ku.cs;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.time.LocalDate;

public class NewProjectWindow {

    public void showNewProjectWindow(Stage primaryStage) {
        Stage newProjectStage = new Stage();
        newProjectStage.setTitle("โครงการใหม่");

        // Create UI elements
        Label projectNameLabel = new Label("ชื่อโครงการ:");
        TextField projectNameField = new TextField();
        Label managerLabel = new Label("ผู้จัดการ:");
        TextField managerField = new TextField();
        Label startDateLabel = new Label("วันที่เริ่มต้น:");
        DatePicker startDatePicker = new DatePicker();
        startDatePicker.setValue(LocalDate.now());
        Label descriptionLabel = new Label("คำอธิบาย:");
        TextField descriptionField = new TextField();

        Button okButton = new Button("ตกลง");
        Button cancelButton = new Button("ยกเลิก");
        cancelButton.setOnAction(e -> {
            newProjectStage.close();
        });
        Button helpButton = new Button("ช่วยเหลือ");

        // Add elements to GridPane
        GridPane gridPane = new GridPane();
        gridPane.setPadding(new Insets(10));
        gridPane.setHgap(10);
        gridPane.setVgap(10);

        gridPane.add(projectNameLabel, 0, 0);
        gridPane.add(projectNameField, 1, 0);
        gridPane.add(managerLabel, 0, 1);
        gridPane.add(managerField, 1, 1);
        gridPane.add(startDateLabel, 0, 2);
        gridPane.add(startDatePicker, 1, 2);
        gridPane.add(descriptionLabel, 0, 3);
        gridPane.add(descriptionField, 1, 3);
        gridPane.add(okButton, 0, 4);
        gridPane.add(cancelButton, 1, 4);
        gridPane.add(helpButton, 2, 4);

        // Create scene and show stage
        Scene scene = new Scene(gridPane);
        newProjectStage.setScene(scene);
        newProjectStage.show();
    }
}
