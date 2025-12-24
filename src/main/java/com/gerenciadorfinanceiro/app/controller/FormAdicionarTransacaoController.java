package com.gerenciadorfinanceiro.app.controller;

import com.gerenciadorfinanceiro.app.components.AutoCompleteTextField;
import com.gerenciadorfinanceiro.app.model.Transacao;
import com.gerenciadorfinanceiro.app.dao.TransacaoDAO;

import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.time.LocalDate;
import java.util.List;

public class FormAdicionarTransacaoController {
    @FXML private TextField campoNome;
    @FXML private TextField campoDesc;
    @FXML private TextField campoValor;
    @FXML private AutoCompleteTextField campoCategoria;
    @FXML private ChoiceBox<String> escolhaTipo;
    @FXML private DatePicker campoData;

    private boolean salvo = false;
    private Transacao resultado;

    @FXML
    private void onSalvar() {
        try {
            String nome = campoNome.getText().trim();
            String desc = campoDesc.getText().trim();
            double valor = Double.parseDouble(campoValor.getText().trim());
            String cat = campoCategoria.getText().trim();
            String tipo = escolhaTipo.getValue();
            LocalDate data = campoData.getValue();

            if (data == null) data = LocalDate.now();

            resultado = new Transacao(nome, desc, valor, tipo, data, cat);
            salvo = true;

            fechar();
        } catch (Exception e) {
            System.out.println("Erro ao salvar");
            e.printStackTrace();
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

    @FXML
    public void initialize() {
        campoData.setValue(LocalDate.now());
        escolhaTipo.getItems().setAll("Entrada", "Saída");

        List<String> categorias = TransacaoDAO.listar().stream()
                .map(Transacao::getCategoria)
                .distinct()
                .sorted()
                .toList();

        campoCategoria.setSuggestions(categorias);
    }
}
