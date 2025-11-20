package com.gerenciadorfinanceiro.app.controller;

import com.gerenciadorfinanceiro.app.dao.TransacaoDAO;
import com.gerenciadorfinanceiro.app.model.Transacao;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class MainController {
    @FXML
    public void initialize() {
        //atualizarExtrato(); //TODO
        atualizarSaldo();
        //atualizarGrafico(); //TODO
    }

    //Aba saldo e extrato
    @FXML
    private Text textoNumeroSaldo;
    @FXML
    private Label labelSaldo;

    private void atualizarSaldo() {
        double saldo = TransacaoDAO.calcularSaldo();

        // formatação bonitinha
        labelSaldo.setText(String.format("R$ %.2f", saldo));
    }


    //Aba gerenciar transações
    @FXML
    private Button botaoAdicionarTransacao;

    @FXML
    private void abrirFormularioAdicionar() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/gerenciadorfinanceiro/app/formAdicionarTransacao.fxml"));
            Parent root = loader.load();

            // obter o controller do formulário pra passar dados
            FormAdicionarTransacaoController formCtrl = loader.getController();
            //formCtrl.setCategorias(categoriaService.listarCategorias());
            formCtrl.setTipos();
            formCtrl.setCategorias();

            // montar a stage modal
            Stage dialog = new Stage();
            dialog.setTitle("Adicionar Transação");
            dialog.initModality(Modality.APPLICATION_MODAL); // bloqueia a janela principal
            dialog.setScene(new Scene(root));
            dialog.showAndWait(); // espera o usuário fechar o dialog

            // após fechar, checar resultado
            if (formCtrl.isSalvo()) {
                Transacao t = formCtrl.getResultado();
                TransacaoDAO.inserir(t); // adapte pro seu método
                // atualiza UI: tabela, saldo, gráfico...
                atualizarSaldo();
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    private Button botarRemoverTransacao;

    //Aba gráfico extrato
    @FXML
    private PieChart graficoExtrato;

    //Aba gráfico saldo
    @FXML
    private LineChart graficoSaldo;
}
