package com.gerenciadorfinanceiro.app.controller;

import com.gerenciadorfinanceiro.app.dao.TransacaoDAO;
import com.gerenciadorfinanceiro.app.model.Transacao;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.*;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.StringConverter;

import java.io.IOException;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class MainController {
    @FXML public void initialize() {
        tabelaExtrato.setItems(listaTransacoes);
        tabelaGerenciar.setItems(listaTransacoes);
        configurarColunas();
        carregarTransacoesIniciais();
        atualizarSaldo();
        atualizarGraficoExtrato();
        atualizarGraficoSaldo();

        botaoRemoverTransacao.disableProperty().bind(
                tabelaGerenciar.getSelectionModel().selectedItemProperty().isNull()
        );
        botaoEditarTransacao.disableProperty().bind(
                tabelaGerenciar.getSelectionModel().selectedItemProperty().isNull()
        );
    }

    private ObservableList<Transacao> listaTransacoes = FXCollections.observableArrayList();

    private void carregarTransacoesIniciais() {
        listaTransacoes.setAll(TransacaoDAO.listar());
        System.out.println("Carregadas " + listaTransacoes.size() + " transações");
    }

    private void configurarColunas() {
        // extrato
        colNomeExtrato.setCellValueFactory(new PropertyValueFactory<>("nome"));
        colDescExtrato.setCellValueFactory(new PropertyValueFactory<>("descricao"));
        colTipoExtrato.setCellValueFactory(new PropertyValueFactory<>("tipo"));
        colValorExtrato.setCellValueFactory(new PropertyValueFactory<>("valor"));
        colDataExtrato.setCellValueFactory(new PropertyValueFactory<>("data"));
        colCategoriaExtrato.setCellValueFactory(new PropertyValueFactory<>("categoria"));

        // gerenciar
        colNomeGerenciar.setCellValueFactory(new PropertyValueFactory<>("nome"));
        colDescGerenciar.setCellValueFactory(new PropertyValueFactory<>("descricao"));
        colTipoGerenciar.setCellValueFactory(new PropertyValueFactory<>("tipo"));
        colValorGerenciar.setCellValueFactory(new PropertyValueFactory<>("valor"));
        colDataGerenciar.setCellValueFactory(new PropertyValueFactory<>("data"));
        colCategoriaGerenciar.setCellValueFactory(new PropertyValueFactory<>("categoria"));

        tabelaExtrato.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        tabelaGerenciar.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
    }


    //Aba saldo e extrato
    @FXML private Text textoNumeroSaldo;
    @FXML private Label labelSaldo;

    private void atualizarSaldo() { //atualizar o saldo sem precisar consultar a database
        listaTransacoes.setAll(TransacaoDAO.listar());
        double saldo = TransacaoDAO.calcularSaldo();


        labelSaldo.setText(String.format("R$ %.2f", saldo));
    }

    @FXML private TableView<Transacao> tabelaExtrato;
    @FXML private TableColumn<Transacao, String> colNomeExtrato;
    @FXML private TableColumn<Transacao, String> colDescExtrato;
    @FXML private TableColumn<Transacao, String> colTipoExtrato;
    @FXML private TableColumn<Transacao, Double> colValorExtrato;
    @FXML private TableColumn<Transacao, LocalDate> colDataExtrato;
    @FXML private TableColumn<Transacao, String> colCategoriaExtrato;


    //Aba gerenciar transações
    @FXML private Button botaoAdicionarTransacao;

    @FXML private void adicionarTransacao() {
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
                TransacaoDAO.inserir(t);
                listaTransacoes.add(t);
                atualizarSaldo();
                atualizarGraficoExtrato();
                atualizarGraficoSaldo();
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML private TableView<Transacao> tabelaGerenciar;
    @FXML private TableColumn<Transacao, String> colNomeGerenciar;
    @FXML private TableColumn<Transacao, String> colDescGerenciar;
    @FXML private TableColumn<Transacao, String> colTipoGerenciar;
    @FXML private TableColumn<Transacao, Double> colValorGerenciar;
    @FXML private TableColumn<Transacao, LocalDate> colDataGerenciar;
    @FXML private TableColumn<Transacao, String> colCategoriaGerenciar;

    @FXML private Button botaoRemoverTransacao;
    @FXML private void removerTransacao() {
        Transacao selecionada = tabelaGerenciar.getSelectionModel().getSelectedItem();
        if (selecionada == null) return;

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmar remoção");
        alert.setHeaderText("Tem certeza que deseja remover esta transação?");
        alert.setContentText("Nome: " + selecionada.getNome() + "\nValor: R$ " + selecionada.getValor());

        ButtonType confirmar = new ButtonType("Remover");
        ButtonType cancelar = new ButtonType("Cancelar", ButtonBar.ButtonData.CANCEL_CLOSE);

        alert.getButtonTypes().setAll(confirmar, cancelar);

        alert.showAndWait().ifPresent(btn -> {
            if (btn == confirmar) {
                TransacaoDAO.excluir(selecionada);
                listaTransacoes.remove(selecionada);
                atualizarSaldo();
                atualizarGraficoExtrato();
                atualizarGraficoSaldo();
            }
        });
    }

    @FXML private Button botaoEditarTransacao;
    @FXML private void editarTransacao() {
        Transacao t = tabelaGerenciar.getSelectionModel().getSelectedItem();
        if (t == null) return;

        try {
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("/com/gerenciadorfinanceiro/app/formEditarTransacao.fxml")
            );
            Parent root = loader.load();

            FormEditarTransacaoController ctrl = loader.getController();

            ctrl.setTipos();
            ctrl.setCategorias();
            ctrl.carregarTransacao(t);

            Stage dialog = new Stage();
            dialog.initModality(Modality.APPLICATION_MODAL);
            dialog.setScene(new Scene(root));
            dialog.setTitle("Editar transação");
            dialog.showAndWait();

            if (ctrl.isSalvo()) {
                TransacaoDAO.editar(t);
                tabelaExtrato.refresh();
                tabelaGerenciar.refresh();
                atualizarSaldo();
                atualizarGraficoExtrato();
                atualizarGraficoSaldo();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    //Aba gráfico extrato
    @FXML private PieChart graficoExtrato;
    @FXML private DatePicker dataInicioExtrato;
    @FXML private DatePicker dataFimExtrato;


    private void atualizarGraficoExtrato() {
        atualizarGraficoExtrato(listaTransacoes);
    }

    private void atualizarGraficoExtrato(List<Transacao> transacoes) {
        ObservableList<PieChart.Data> dados = FXCollections.observableArrayList();

        transacoes.stream()
                .filter(t -> t.getTipo().equalsIgnoreCase("Saída"))
                .collect(Collectors.groupingBy(
                        Transacao::getCategoria,
                        Collectors.summingDouble(Transacao::getValor)
                ))
                .forEach((categoria, total) -> {
                    dados.add(new PieChart.Data(categoria, total));
                });

        graficoExtrato.setData(dados);
    }

    @FXML private void filtrarGraficoExtrato() {
        LocalDate ini = dataInicioExtrato.getValue();
        LocalDate fim = dataFimExtrato.getValue();

        if (ini == null || fim == null) return;

        List<Transacao> filtradas = listaTransacoes.stream()
                .filter(t -> !t.getData().isBefore(ini) && !t.getData().isAfter(fim))
                .toList();

        atualizarGraficoExtrato(filtradas);
    }





    //Aba gráfico saldo
    @FXML private LineChart graficoSaldo;
    @FXML private DatePicker dataInicioSaldo;
    @FXML private DatePicker dataFimSaldo;

    private void atualizarGraficoSaldo() {
        atualizarGraficoSaldo(listaTransacoes);
    }

    @FXML private NumberAxis eixoXSaldo;
    @FXML private NumberAxis eixoYSaldo;

    private void atualizarGraficoSaldo(List<Transacao> lista) {
        graficoSaldo.getData().clear();

        if (lista.isEmpty()) return;

        // ordenar por data
        lista = lista.stream()
                .sorted(Comparator.comparing(Transacao::getData))
                .toList();

        // converter data para número de dias desde a primeira
        LocalDate base = lista.get(0).getData();

        XYChart.Series<Number, Number> serie = new XYChart.Series<>();

        double saldo = 0;
        List<LocalDate> datas = new ArrayList<>();

        for (Transacao t : lista) {
            saldo += t.getTipo().equals("Entrada") ? t.getValor() : -t.getValor();

            long dia = ChronoUnit.DAYS.between(base, t.getData());
            serie.getData().add(new XYChart.Data<>(dia, saldo));
            datas.add(t.getData());
        }

        graficoSaldo.getData().add(serie);

        configurarEixoXSaldo(base, datas);
    }



    @FXML
    private void filtrarGraficoSaldo() {
        LocalDate ini = dataInicioSaldo.getValue();
        LocalDate fim = dataFimSaldo.getValue();

        if (ini == null || fim == null) return;

        List<Transacao> filtradas = listaTransacoes.stream()
                .filter(t -> !t.getData().isBefore(ini) && !t.getData().isAfter(fim))
                .toList();

        atualizarGraficoSaldo(filtradas);
    }

    private void configurarEixoXSaldo(LocalDate base, List<LocalDate> datas) {
        eixoXSaldo.setAutoRanging(true);

        eixoXSaldo.setTickLabelFormatter(new StringConverter<Number>() {
            @Override
            public String toString(Number value) {
                LocalDate d = base.plusDays(value.longValue());
                return d.toString();
            }

            @Override
            public Number fromString(String s) {
                return 0; // não usado
            }
        });

        eixoXSaldo.setTickUnit(10); // mostra uma label a cada 10 dias
    }




}
