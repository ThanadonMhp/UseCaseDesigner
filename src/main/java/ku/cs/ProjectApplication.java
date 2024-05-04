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
    @Override
    public void start(Stage stage) throws IOException {
        FXRouter.bind(this, stage, 1280,720);
        configRoute();
        FXRouter.goTo("HomePage");
        FXRouter.popup("LandingPage");
    }

    private static void configRoute() {
        String packageStr = "ku/cs/usecasedesigner/";

        // Config route
        FXRouter.when("HomePage", packageStr + "home-page.fxml", "UseCaseDesigner");
        FXRouter.when("LandingPage", packageStr + "landing-page.fxml", "UseCaseDesigner | LandingPage");
        FXRouter.when("NewProjectPage", packageStr + "new-project-page.fxml", "UseCaseDesigner | New Project");
    }
    public static void main(String[] args) {
        launch(args);
    }
}
