package com.gerenciadorfinanceiro.app.controller;

import com.gerenciadorfinanceiro.app.model.Transacao;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.time.LocalDate;

public class FormAdicionarTransacaoController {

    @FXML private TextField campoNome;
    @FXML private TextField campoDesc;
    @FXML private TextField campoValor;
    @FXML private ChoiceBox<String> escolhaCategoria;
    @FXML private ChoiceBox<String> escolhaTipo;

    private boolean salvo = false;
    private Transacao resultado;

    @FXML
    private void onSalvar() {
        try {
            String nome = campoNome.getText().trim();
            String desc = campoDesc.getText().trim();
            double valor = Double.parseDouble(campoValor.getText().trim());
            String cat = escolhaCategoria.getValue();
            String tipo = escolhaTipo.getValue();
            LocalDate data = campoData.getValue();

            if (data == null) {
                data = LocalDate.now();
            }

            resultado = new Transacao(nome, desc, valor, tipo, data, cat);
            salvo = true;

            fechar();
        } catch (Exception e) {
            System.out.println("Erro ao salvar");
        }
    }

    @FXML
    private void onCancelar() {
        salvo = false;
        fechar();
    }

    private void fechar() {
        Stage stage = (Stage) campoNome.getScene().getWindow();
        stage.close();
    }

    public boolean isSalvo() { return salvo; }
    public Transacao getResultado() { return resultado; }

    public void setTipos() {
        escolhaTipo.getItems().setAll("Entrada", "Saída");
    }

    public void setCategorias() {
        escolhaCategoria.getItems().setAll("Salário", "Investimento", "Essencial", "Lazer", "Saúde");
    }

    @FXML private DatePicker campoData;

    @FXML public void initialize() {
        campoData.setValue(LocalDate.now());
    }


}
