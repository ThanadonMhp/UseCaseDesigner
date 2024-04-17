package ku.cs.fxrouter;

import javafx.animation.FadeTransition;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.util.AbstractMap;
import java.util.HashMap;

public class FXRouter {
    public static final String WINDOW_TITLE = "";
    public static final Double WINDOW_WIDTH = 800.0D;
    public static final Double WINDOW_HEIGHT = 600.0D;
    public static final Double FADE_ANIMATION_DURATION = 800.0D;
    public static FXRouter router;
    public static Object mainRef;
    public static Stage window;
    public static String windowTitle;
    public static Double windowWidth;
    public static Double windowHeight;
    public static String animationType;
    public static Double animationDuration;
    public static AbstractMap<String, RouteScene> routes = new HashMap();
    public static int themeType;
    public static RouteScene currentRoute;

    public FXRouter() {
    }

    public static void bind(Object ref, Stage win) {
        checkInstances(ref, win);
    }

    public static void bind(Object ref, Stage win, String winTitle) {
        checkInstances(ref, win);
        windowTitle = winTitle;
    }

    public static void bind(Object ref, Stage win, double winWidth, double winHeight) {
        checkInstances(ref, win);
        windowWidth = winWidth;
        windowHeight = winHeight;
    }

    public static void bind(Object ref, Stage win, String winTitle, double winWidth, double winHeight) {
        checkInstances(ref, win);
        windowTitle = winTitle;
        windowWidth = winWidth;
        windowHeight = winHeight;
    }

    public static void checkInstances(Object ref, Stage win) {
        if (mainRef == null) {
            mainRef = ref;
        }

        if (router == null) {
            router = new FXRouter();
        }

        if (window == null) {
            window = win;
        }

    }

    public static void when(String routeLabel, String scenePath) {
        RouteScene routeScene = new RouteScene(scenePath);
        routes.put(routeLabel, routeScene);
    }

    public static void when(String routeLabel, String scenePath, String winTitle) {
        RouteScene routeScene = new RouteScene(scenePath, winTitle);
        routes.put(routeLabel, routeScene);
    }

    public static void when(String routeLabel, String scenePath, double sceneWidth, double sceneHeight) {
        RouteScene routeScene = new RouteScene(scenePath, sceneWidth, sceneHeight);
        routes.put(routeLabel, routeScene);
    }

    public static void when(String routeLabel, String scenePath, String winTitle, double sceneWidth, double sceneHeight) {
        RouteScene routeScene = new RouteScene(scenePath, winTitle, sceneWidth, sceneHeight);
        routes.put(routeLabel, routeScene);
    }
    public static void goTo(String routeLabel) throws IOException {
        RouteScene route = (RouteScene)routes.get(routeLabel);
        loadNewRoute(route);
    }

    public static void goTo(String routeLabel, Object data) throws IOException {
        RouteScene route = (RouteScene)routes.get(routeLabel);
        route.data = data;
        loadNewRoute(route);
    }
    public static void popup(String routeLabel) throws IOException {
        RouteScene route = (RouteScene)routes.get(routeLabel);
        popupNewRoute(route);
    }
    public static void popup(String routeLabel, Object data) throws IOException {
        RouteScene route = (RouteScene)routes.get(routeLabel);
        route.data = data;
        popupNewRoute(route);
    }

    public static void loadNewRoute(RouteScene route) throws IOException {
        currentRoute = route;
        String scenePath = "/" + route.scenePath;
        Parent resource = (Parent)FXMLLoader.load((new Object() {
        }).getClass().getResource(scenePath));
        Scene scene = new Scene(resource, route.sceneWidth, route.sceneHeight);

        if(themeType == 1){
            scene.getStylesheets().clear();
            scene.getStylesheets().setAll((new Object() {}).getClass().getResource("/style/darkMode.css").toExternalForm());
        } else if (themeType == 2) {
            scene.getStylesheets().clear();
            scene.getStylesheets().setAll((new Object() {}).getClass().getResource("/style/kawaiiMode.css").toExternalForm());
        }

        window.setTitle(route.windowTitle);
        window.setScene(scene);
        window.show();
        routeAnimation(resource);
    }

    public static void popupNewRoute(RouteScene route) throws IOException {
        currentRoute = route;
        String scenePath = "/" + route.scenePath;
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation((new Object() {}).getClass().getResource(scenePath));
        Parent resource = (Parent)fxmlLoader.load((new Object() {
        }).getClass().getResource(scenePath));

        Stage stage = new Stage();
        Scene scene = new Scene(resource);

        if (themeType == 1){
            scene.getStylesheets().clear();
            scene.getStylesheets().setAll((new Object() {}).getClass().getResource("/style/darkMode.css").toExternalForm());
        } else if (themeType == 2) {
            scene.getStylesheets().clear();
            scene.getStylesheets().setAll((new Object() {}).getClass().getResource("/style/kawaiiMode.css").toExternalForm());
        }

        stage.setScene(scene);
        stage.show();
        routeAnimation(resource);
    }


    public static void startFrom(String routeLabel) throws Exception {
        goTo(routeLabel);
    }

    public static void startFrom(String routeLabel, Object data) throws Exception {
        goTo(routeLabel, data);
    }

    public static void setAnimationType(String anType) {
        animationType = anType;
    }
    public static void setTheme(int theme) {
        themeType = theme;
    }

    public static void setAnimationType(String anType, double anDuration) {
        animationType = anType;
        animationDuration = anDuration;
    }
    public static void routeAnimation(Parent node) {
        String anType = animationType != null ? animationType.toLowerCase() : "";
        byte var3 = -1;
        switch(anType.hashCode()) {
            case 3135100:
                if (anType.equals("fade")) {
                    var3 = 0;
                }
            default:
                switch(var3) {
                    case 0:
                        Double fd = animationDuration != null ? animationDuration : FADE_ANIMATION_DURATION;
                        FadeTransition ftCurrent = new FadeTransition(Duration.millis(fd), node);
                        ftCurrent.setFromValue(0.0D);
                        ftCurrent.setToValue(1.0D);
                        ftCurrent.play();
                    default:
                }
        }
    }

    public static Object getData() {
        return currentRoute.data;
    }

    public static class RouteScene {
        public String scenePath;
        private String windowTitle;
        private double sceneWidth;
        private double sceneHeight;
        private Object data;

        public RouteScene(String scenePath) {
            this(scenePath, getWindowTitle(), getWindowWidth(), getWindowHeight());
        }

        public RouteScene(String scenePath, String windowTitle) {
            this(scenePath, windowTitle, getWindowWidth(), getWindowHeight());
        }

        public RouteScene(String scenePath, double sceneWidth, double sceneHeight) {
            this(scenePath, getWindowTitle(), sceneWidth, sceneHeight);
        }

        public RouteScene(String scenePath, String windowTitle, double sceneWidth, double sceneHeight) {
            this.scenePath = scenePath;
            this.windowTitle = windowTitle;
            this.sceneWidth = sceneWidth;
            this.sceneHeight = sceneHeight;
        }

        public static String getWindowTitle() {
            return FXRouter.windowTitle != null ? FXRouter.windowTitle : "";
        }

        public static double getWindowWidth() {
            return FXRouter.windowWidth != null ? FXRouter.windowWidth : FXRouter.WINDOW_WIDTH;
        }

        public static double getWindowHeight() {
            return FXRouter.windowHeight != null ? FXRouter.windowHeight : FXRouter.WINDOW_HEIGHT;
        }
    }
}


