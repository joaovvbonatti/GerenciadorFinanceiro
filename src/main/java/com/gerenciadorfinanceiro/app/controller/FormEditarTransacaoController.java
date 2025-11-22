package com.gerenciadorfinanceiro.app.controller;

import com.gerenciadorfinanceiro.app.model.Transacao;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.time.LocalDate;

public class FormEditarTransacaoController {

    @FXML private TextField campoNome;
    @FXML private TextField campoDesc;
    @FXML private TextField campoValor;
    @FXML private ChoiceBox<String> escolhaCategoria;
    @FXML private ChoiceBox<String> escolhaTipo;
    @FXML private DatePicker campoData;

    private boolean salvo = false;
    private Transacao transacao;

    public void carregarTransacao(Transacao t) {
        this.transacao = t;

        campoNome.setText(t.getNome());
        campoDesc.setText(t.getDescricao());
        campoValor.setText(String.valueOf(t.getValor()));
        escolhaCategoria.setValue(t.getCategoria());
        escolhaTipo.setValue(t.getTipo());
        campoData.setValue(t.getData());
    }

    public void setCategorias() {
        escolhaCategoria.getItems().setAll("Salário", "Investimento", "Essencial", "Lazer", "Saúde");
    }

    public void setTipos() {
        escolhaTipo.getItems().setAll("Entrada", "Saída");
    }

    @FXML
    private void onSalvar() {
        try {
            transacao.setNome(campoNome.getText());
            transacao.setDescricao(campoDesc.getText());
            transacao.setValor(Double.parseDouble(campoValor.getText()));
            transacao.setCategoria(escolhaCategoria.getValue());
            transacao.setTipo(escolhaTipo.getValue());
            transacao.setData(campoData.getValue());

            salvo = true;
            fechar();

        } catch (Exception e) {
            System.out.println("Erro ao salvar edição");
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

    public boolean isSalvo() {
        return salvo;
    }
}
