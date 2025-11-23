package com.gerenciadorfinanceiro.app.components;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Side;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.CustomMenuItem;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.util.List;
import java.util.stream.Collectors;

public class AutoCompleteTextField extends TextField {

    private final ContextMenu suggestions = new ContextMenu();
    private ObservableList<String> data = FXCollections.observableArrayList();

    public void setSuggestions(List<String> list) {
        data.setAll(list);
    }

    public AutoCompleteTextField() {
        textProperty().addListener((obs, old, novo) -> {
            if (novo == null || novo.isEmpty()) {
                suggestions.hide();
            } else {
                List<String> filtradas = data.stream()
                        .filter(s -> s.toLowerCase().contains(novo.toLowerCase()))
                        .limit(10)
                        .collect(Collectors.toList());

                if (filtradas.isEmpty()) {
                    suggestions.hide();
                } else {
                    montarMenu(filtradas);
                    if (!suggestions.isShowing()) {
                        suggestions.show(this, Side.BOTTOM, 0, 0);
                    }
                }
            }
        });

        focusedProperty().addListener((obs, old, focus) -> {
            if (!focus) suggestions.hide();
        });
    }

    private void montarMenu(List<String> entradas) {
        suggestions.getItems().clear();

        for (String entrada : entradas) {
            Label label = new Label(entrada);
            CustomMenuItem item = new CustomMenuItem(label, true);
            item.setOnAction(e -> {
                setText(entrada);
                positionCaret(entrada.length());
                suggestions.hide();
            });
            suggestions.getItems().add(item);
        }
    }
}
