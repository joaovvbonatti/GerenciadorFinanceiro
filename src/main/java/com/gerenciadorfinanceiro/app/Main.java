package com.gerenciadorfinanceiro.app;

import atlantafx.base.theme.PrimerDark;
import com.gerenciadorfinanceiro.app.dao.Database;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.util.Objects;

public class Main extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        Database.inicializar();

        FXMLLoader loader = new FXMLLoader(getClass().getResource("interface.fxml"));
        Scene scene = new Scene(loader.load());

        Application.setUserAgentStylesheet(new PrimerDark().getUserAgentStylesheet()); //tema escuro

        stage.getIcons().add( //ícone da aplicação
                new Image(Objects.requireNonNull(
                        getClass().getResourceAsStream("/com/gerenciadorfinanceiro/app/icon.png")
                ))
        );


        stage.setScene(scene);
        stage.setTitle("Gerenciador Financeiro");
        stage.setWidth(1200);
        stage.setHeight(800);
        stage.setResizable(true);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
