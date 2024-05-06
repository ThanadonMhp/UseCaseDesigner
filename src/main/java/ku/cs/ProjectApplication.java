package ku.cs;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.StageStyle;
import ku.cs.fxrouter.FXRouter;
import javafx.application.Application;
import javafx.stage.Stage;
import java.io.IOException;

public class ProjectApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXRouter.bind(this, stage, 1280,720);
        configRoute();
        FXRouter.goTo("HomePage");
        FXRouter.popup("LandingPage");


//        // Create a new stage
//        Stage popupStage = new Stage();
//        popupStage.initStyle(StageStyle.UNDECORATED); // Remove window decorations
//
//// Load the FXML file
//        FXMLLoader loader = new FXMLLoader(getClass().getResource("src/main/resources/ku/cs/usecasedesigner/landing-page.fxml"));
//        System.out.println(loader);
//        Parent root = loader.load();
//
//// Set the scene and show the popup
//        Scene scene = new Scene(root);
//        popupStage.setScene(scene);
//        popupStage.setResizable(false); // Disable resizing
//        popupStage.show();
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
