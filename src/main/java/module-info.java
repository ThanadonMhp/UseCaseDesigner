module ku.cs.usecasedesigner {
    requires javafx.controls;
    requires javafx.fxml;


    opens ku.cs.usecasedesigner to javafx.fxml;
    exports ku.cs.usecasedesigner;
}