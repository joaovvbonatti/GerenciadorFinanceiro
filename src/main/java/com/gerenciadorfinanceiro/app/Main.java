package com.gerenciadorfinanceiro.app;

import com.gerenciadorfinanceiro.app.dao.Database;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        Database.inicializar();

        FXMLLoader loader = new FXMLLoader(getClass().getResource("interface.fxml"));
        Scene scene = new Scene(loader.load());
        stage.setScene(scene);
        stage.setTitle("Gerenciador Financeiro");
        stage.setWidth(1200);
        stage.setHeight(800);
        stage.setResizable(false);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
