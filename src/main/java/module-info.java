module com.gerenciadorfinanceiro.app {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens com.gerenciadorfinanceiro.app to javafx.fxml;
    exports com.gerenciadorfinanceiro.app;
    exports com.gerenciadorfinanceiro.app.controller;
    opens com.gerenciadorfinanceiro.app.controller to javafx.fxml;
}