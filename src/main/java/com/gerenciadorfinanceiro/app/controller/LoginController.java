package com.gerenciadorfinanceiro.app.controller;

import com.gerenciadorfinanceiro.app.auth.LoginService;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

public class LoginController {

    @FXML private PasswordField campoSenha;
    @FXML private Label labelErro;

    @FXML
    private void onEntrar() {
        String senha = campoSenha.getText().trim();

        if (LoginService.autenticar(senha)) {
            abrirMain();
        } else {
            labelErro.setText("Senha incorreta.");
        }
    }

    public void abrirMain() {
        try {
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("/com/gerenciadorfinanceiro/app/interface.fxml")
            );
            Scene mainScene = new Scene(loader.load());

            // pega a janela atual (do login)
            Stage stage = (Stage) campoSenha.getScene().getWindow();

            stage.setScene(mainScene);
            stage.setTitle("Gerenciador Financeiro");
            stage.setWidth(1200);
            stage.setHeight(800);
            stage.setResizable(true);
            stage.centerOnScreen();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("ERRO ao abrir a interface principal");
        }
    }


    @FXML private TextField campoNovaSenha;
    @FXML private TextField campoConfirmacao;
    @FXML private Label feedbackSenha;

    @FXML
    private void onRedefinirSenha() {

        String nova = campoNovaSenha.getText().trim();
        String confirm = campoConfirmacao.getText().trim();

        if (nova.isEmpty() || confirm.isEmpty()) {
            feedbackSenha.setText("Preencha os campos.");
            feedbackSenha.setStyle("-fx-text-fill: #ff6666;");
            return;
        }

        if (!nova.equals(confirm)) {
            feedbackSenha.setText("As senhas não coincidem.");
            feedbackSenha.setStyle("-fx-text-fill: #ff6666;");
            return;
        }

        try {
            LoginService.redefinirSenha(nova);  // <<< AQUI USA LOGIN SERVICE
            feedbackSenha.setText("Senha alterada com sucesso.");
            feedbackSenha.setStyle("-fx-text-fill: #66ff66;");
            campoNovaSenha.clear();
            campoConfirmacao.clear();

        } catch (Exception e) {
            feedbackSenha.setText("Erro ao salvar a senha.");
            feedbackSenha.setStyle("-fx-text-fill: #ff6666;");
        }
    }


}
