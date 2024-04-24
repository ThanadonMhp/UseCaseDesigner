package ku.cs;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import ku.cs.fxrouter.FXRouter;
import javafx.application.Application;
import javafx.stage.Stage;
import java.io.IOException;
import java.time.LocalDate;

public class ProjectApplication extends Application {
    private NewProjectWindow newProjectWindow;
    @Override
    public void start(Stage stage) throws IOException {
        FXRouter.bind(this, stage, 1280,720);
        configRoute();
        FXRouter.goTo("Homepage");

        newProjectWindow = new NewProjectWindow();

        Stage dialog = new Stage();
        dialog.initModality(Modality.APPLICATION_MODAL);
        dialog.setTitle("Welcome!");

        Label label = new Label("คุณอยากจะทำอะไร");

        Button newProjectButton = new Button("สร้างโครงการ");
        newProjectButton.setOnAction(e -> newProjectWindow.showNewProjectWindow(stage));

        Button openProjectButton = new Button("เปิดโครงการ");
        Button closeButton = new Button("ปิด");

        closeButton.setOnAction(e -> dialog.close());

        openProjectButton.setOnAction(e -> {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Open Project");
            fileChooser.showOpenDialog(stage);
        });

        VBox layout = new VBox(10, label, newProjectButton, openProjectButton, closeButton);
        layout.setAlignment(Pos.CENTER);
        Scene dialogScene = new Scene(layout, 300, 150);
        dialog.setScene(dialogScene);

        // Show the dialog when the application starts
        dialog.showAndWait();
    }

    private static void configRoute() {
        String packageStr = "ku/cs/usecasedesigner/";

        FXRouter.when("CubicCurveDemo", packageStr + "CubicCurveDemo.fxml", "UseCaseDesigner | CubicCurveDemo");
        FXRouter.when("DraggableNode", packageStr + "DraggableNode.fxml", "UseCaseDesigner | DraggableNode");
        FXRouter.when("DragIcon", packageStr + "DragIcon.fxml", "UseCaseDesigner | DragIcon");
        FXRouter.when("NodeLink", packageStr + "NodeLink.fxml", "UseCaseDesigner | NodeLink");
        FXRouter.when("RootLayout", packageStr + "RootLayout.fxml", "UseCaseDesigner | RootLayout");

        FXRouter.when("Homepage", packageStr + "Homepage.fxml", "UseCaseDesigner | Homepage");

    }
    public static void main(String[] args) {
        launch(args);
    }
}
