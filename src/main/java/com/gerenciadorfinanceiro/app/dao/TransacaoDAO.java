package com.gerenciadorfinanceiro.app.dao;

import com.gerenciadorfinanceiro.app.model.Transacao;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class TransacaoDAO {
    public static void inserir(Transacao t) {
        String sql = "INSERT INTO TRANSACAO (nome, tipo, valor, data, categoria, descricao) VALUES (?, ?, ?, ?, ?, ?)";

        try(Connection c = Database.conectar();
            PreparedStatement stmt = c.prepareStatement(sql)) {

            stmt.setString(1, t.getNome());
            stmt.setString(2, t.getTipo());
            stmt.setDouble(3, t.getValor());
            stmt.setString(4, t.getData().toString());
            stmt.setString(5, t.getCategoria());
            stmt.setString(6, t.getDescricao());
            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static List<Transacao> listar() {
        List<Transacao> lista = new ArrayList<>();
        String sql = "SELECT * FROM TRANSACAO ORDER BY data";

        try(Connection c = Database.conectar();
            Statement stmt = c.createStatement();
            ResultSet rs = stmt.executeQuery(sql)) {

            while(rs.next()) {
                int id = rs.getInt("id");
                String nome = rs.getString("nome");
                String tipo = rs.getString("tipo");
                double valor = rs.getDouble("valor");
                LocalDate data = LocalDate.parse(rs.getString("data"));
                String categoria = rs.getString("categoria");
                String descricao = rs.getString("descricao");

                Transacao t = new Transacao(nome, descricao, valor, tipo, data, categoria);

                lista.add(t);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return(lista);
    }

    public static void excluir(Transacao t) {
        String sql = "DELETE FROM TRANSACAO WHERE id = ?";

        try(Connection c = Database.conectar();
            PreparedStatement stmt = c.prepareStatement(sql)) {

            stmt.setInt(1, t.getId());
            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void editar(Transacao t) {
        String sql = """
                UPDATE transacao 
                SET tipo = ?, valor = ?, data = ?, descricao = ?, categoria = ?
                WHERE id = ?
            """;

        try(Connection c = Database.conectar();
            PreparedStatement stmt = c.prepareStatement(sql)) {

            stmt.setString(1, t.getTipo());
            stmt.setDouble(2, t.getValor());
            stmt.setString(3, t.getData().toString());
            stmt.setString(4, t.getDescricao());
            stmt.setString(5, t.getCategoria());
            stmt.setInt(6, t.getId());

            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static double calcularSaldo() {
        double total = 0;

        for (Transacao t : listar()) {
            if (t.getTipo().equalsIgnoreCase("Entrada")) {
                total += t.getValor();
            } else {
                total -= t.getValor();
            }
        }

        return total;
    }


}
