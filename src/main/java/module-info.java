module com.gerenciadorfinanceiro.gerenciadorfinanceiro {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens com.gerenciadorfinanceiro.gerenciadorfinanceiro to javafx.fxml;
    exports com.gerenciadorfinanceiro.gerenciadorfinanceiro;
    exports com.gerenciadorfinanceiro.gerenciadorfinanceiro.controller;
    opens com.gerenciadorfinanceiro.gerenciadorfinanceiro.controller to javafx.fxml;
}