package ku.cs;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import ku.cs.fxrouter.FXRouter;
import javafx.application.Application;
import javafx.stage.Stage;
import java.io.IOException;

public class ProjectApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXRouter.bind(this, stage, 1280,720);
        configRoute();
        FXRouter.goTo("Homepage");
        Stage dialog = new Stage();
        dialog.initModality(Modality.APPLICATION_MODAL);
        dialog.setTitle("Welcome!");

        Label label = new Label("คุณอยากจะทำอะไร");

        Button newProjectButton = new Button("สร้างโครงการ");
        Button openProjectButton = new Button("เปิดโครงการ");
        Button closeButton = new Button("Close");

        closeButton.setOnAction(e -> dialog.close());

        openProjectButton.setOnAction(e -> {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Open Project");
            // Set other properties of the FileChooser as needed
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