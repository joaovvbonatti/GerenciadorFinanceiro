module com.gerenciadorfinanceiro.app {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires atlantafx.base;


    opens com.gerenciadorfinanceiro.app to javafx.fxml;
    exports com.gerenciadorfinanceiro.app;
    exports com.gerenciadorfinanceiro.app.controller;
    opens com.gerenciadorfinanceiro.app.controller to javafx.fxml;
    opens com.gerenciadorfinanceiro.app.model to javafx.base;
}