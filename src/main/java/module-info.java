module com.gerenciadorfinanceiro.gerenciadorfinanceiro {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.gerenciadorfinanceiro.gerenciadorfinanceiro to javafx.fxml;
    exports com.gerenciadorfinanceiro.gerenciadorfinanceiro;
}