package com.gerenciadorfinanceiro.app.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Database {
    private static final String URL = "jdbc:sqlite:financeiro.sqlite";

    public static void inicializar() {
        System.out.println("Banco: " + new java.io.File("financeiro.sqlite").getAbsolutePath());
        criarTabelas();
    }

    public static Connection conectar() throws SQLException {
        return DriverManager.getConnection(URL);
    }

    private static void criarTabelas() {
        String sql = """
                CREATE TABLE IF NOT EXISTS transacao (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                nome TEXT NOT NULL,
                tipo TEXT NOT NULL,
                valor REAL NOT NULL,
                data TEXT NOT NULL,
                categoria TEXT,
                descricao TEXT 
                );
        """;

        try(Connection c = conectar()) {
            c.createStatement().executeUpdate(sql);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
