package ku.cs.usecasedesigner;

import fxrouter.FXRouter;
import javafx.application.Application;
import javafx.stage.Stage;

import java.io.IOException;

public class ProjectApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXRouter.bind(this, stage, "UseCaseDesigner | Homepage", 1280, 720);
        configRoute();
        FXRouter.goTo("Homepage");
    }

    private static void configRoute() {
        String packageStr = "ku/cs/usecasedesigner/";

        FXRouter.when("UseCaseDesigner", packageStr + "CubicCurveDemo.fxml", "UseCaseDesigner | UseCaseDesigner");
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