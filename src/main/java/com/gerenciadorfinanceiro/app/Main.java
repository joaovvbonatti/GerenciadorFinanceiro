package com.gerenciadorfinanceiro.app;

import atlantafx.base.theme.PrimerDark;
import com.gerenciadorfinanceiro.app.auth.AuthFile;
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
        // cria o arquivo de senha se não existir
        AuthFile.criarArquivoSeNaoExistir("admin");

        // inicializa banco
        Database.inicializar();

        // --- altera apenas ESTA linha ---
        FXMLLoader loader = new FXMLLoader(
                getClass().getResource("/com/gerenciadorfinanceiro/app/login.fxml")
        );
        Scene scene = new Scene(loader.load());

        // tema escuro
        Application.setUserAgentStylesheet(new PrimerDark().getUserAgentStylesheet());

        // ícone da aplicação
        stage.getIcons().add(
                new Image(Objects.requireNonNull(
                        getClass().getResourceAsStream(
                                "/com/gerenciadorfinanceiro/app/icon.png"
                        )
                ))
        );

        stage.setScene(scene);
        stage.setTitle("Login");
        stage.setWidth(450);
        stage.setHeight(300);
        stage.setResizable(false);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
