package com.gerenciadorfinanceiro.gerenciadorfinanceiro.controller;

import javafx.fxml.FXML;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.text.Text;

public class TelaPrincipalController {

    // SALDO
    @FXML private Text labelSaldo;
    @FXML private TableView<?> tabelaExtrato;

    // COLUNAS
    @FXML private TableColumn<?, ?> colNome;
    @FXML private TableColumn<?, ?> colTipo;
    @FXML private TableColumn<?, ?> colValor;
    @FXML private TableColumn<?, ?> colData;
    @FXML private TableColumn<?, ?> colCategoria;

    // GERENCIAR TRANSACOES
    @FXML private Button btnAdicionar;
    @FXML private Button btnRemover;
    @FXML private TableView<?> tabelaTransacoesGerenciar;

    // GRAFICOS
    @FXML private PieChart pieChartCategorias;
    @FXML private LineChart<String, Number> lineChartSaldo;

    @FXML
    public void initialize() {
        System.out.println("FXML carregado com sucesso.");
    }

    @FXML
    private void onAdicionar() {
        System.out.println("Adicionar clicado");
    }

    @FXML
    private void onRemover() {
        System.out.println("Remover clicado");
    }
}
