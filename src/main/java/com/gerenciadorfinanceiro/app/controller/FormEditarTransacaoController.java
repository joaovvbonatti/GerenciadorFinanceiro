package com.gerenciadorfinanceiro.app.controller;

import com.gerenciadorfinanceiro.app.components.AutoCompleteTextField;
import com.gerenciadorfinanceiro.app.dao.TransacaoDAO;
import com.gerenciadorfinanceiro.app.model.Transacao;

import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.util.List;

public class FormEditarTransacaoController {
    @FXML private TextField campoNome;
    @FXML private TextField campoDesc;
    @FXML private TextField campoValor;
    @FXML private AutoCompleteTextField campoCategoria;
    @FXML private ChoiceBox<String> escolhaTipo;
    @FXML private DatePicker campoData;

    private boolean salvo = false;
    private Transacao transacao;

    public void carregarTransacao(Transacao t) {
        this.transacao = t;

        campoNome.setText(t.getNome());
        campoDesc.setText(t.getDescricao());
        campoValor.setText(String.valueOf(t.getValor()));
        campoCategoria.setText(t.getCategoria());      // CORRETO
        escolhaTipo.setValue(t.getTipo());
        campoData.setValue(t.getData());
    }

    @FXML
    private void onSalvar() {
        try {
            transacao.setNome(campoNome.getText());
            transacao.setDescricao(campoDesc.getText());
            transacao.setValor(Double.parseDouble(campoValor.getText()));
            transacao.setCategoria(campoCategoria.getText());   // CORRETO
            transacao.setTipo(escolhaTipo.getValue());
            transacao.setData(campoData.getValue());

            salvo = true;
            fechar();

        } catch (Exception e) {
            System.out.println("Erro ao salvar edição");
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

    @FXML
    public void initialize() {
        escolhaTipo.getItems().setAll("Entrada", "Saída");

        List<String> categorias = TransacaoDAO.listar().stream()
                .map(Transacao::getCategoria)
                .distinct()
                .sorted()
                .toList();

        campoCategoria.setSuggestions(categorias);
    }
}
