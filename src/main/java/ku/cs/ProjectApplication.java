package ku.cs;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.StageStyle;
import ku.cs.fxrouter.FXRouter;
import javafx.application.Application;
import javafx.stage.Stage;
import ku.cs.usecasedesigner.services.DataSource;
import ku.cs.usecasedesigner.services.UseCaseSystemListFileDataSource;

import java.io.IOException;

public class ProjectApplication extends Application {
//    private DataSource<UseCaseSystemListFileDataSource> dataSource;
//    private UseCaseSystemListFileDataSource UseCaseSystemList;
//    public void initialize(){
//        dataSource = new UseCaseSystemListFileDataSource("src/main/java","nattest.csv");
//        UseCaseSystemList = dataSource.readData();
//    }
    @Override
    public void start(Stage stage) throws IOException {
        FXRouter.bind(this, stage, 1280,720);
        configRoute();
        FXRouter.setTheme(1);
        FXRouter.goTo("HomePage");
        FXRouter.popup("LandingPage", true);
        setWindowTitle("UseCaseDesigner/////////////");
    }
    private void setWindowTitle(String title) {
        Stage stage = FXRouter.getStage();
        stage.setTitle(title);
    }

    private static void configRoute() {
        String packageStr = "ku/cs/usecasedesigner/";

        // Config route
        FXRouter.when("HomePage", packageStr + "home-page.fxml", "UseCaseDesigner");
        FXRouter.when("LandingPage", packageStr + "landing-page.fxml", "Welcome!");
        FXRouter.when("NewProjectPage", packageStr + "new-project-page.fxml", "New Project");
        FXRouter.when("LabelPage", packageStr + "label-page.fxml", "Label");
        FXRouter.when("PreferencePage", packageStr + "preference-page.fxml", "Preference");
        FXRouter.when("UseCasePage", packageStr + "use-case-page.fxml", "Use Case");
    }
    public static void main(String[] args) {
        launch(args);
    }
}
