package br.csi.dao;

import br.csi.model.Livro;
import br.csi.model.Usuario;
import br.csi.util.ConectaDBPostgres;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class LivroDAO {

    // Método para obter a lista de livros
    public ArrayList<Livro> getLivros() { // READ livro

        ConectaDBPostgres cdb = new ConectaDBPostgres();
        ArrayList<Livro> livros = new ArrayList<>();
        try (Connection con = cdb.getConexao();
             Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM livro")) {

            while (rs.next()) {
                Livro livro = new Livro(
                        rs.getInt("idlivro"),
                        rs.getString("titulo"),
                        rs.getString("autor"),
                        rs.getString("descricao"),
                        rs.getFloat("preco"),
                        rs.getBoolean("esta_a_venda"),
                        rs.getInt("id_cliente_dono")
                );

                livros.add(livro);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return livros;
    }

    // Método para obter a lista de livros de um usuario especifico
    public ArrayList<Livro> getLivrosByClienteId(int clienteId) {
        ConectaDBPostgres cdb = new ConectaDBPostgres();
        ArrayList<Livro> livros = new ArrayList<>();

        String query = "SELECT * FROM livro WHERE id_cliente_dono = ?";
        try (Connection con = cdb.getConexao();
             PreparedStatement pstmt = con.prepareStatement(query)) {

            pstmt.setInt(1, clienteId);

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    Livro livro = new Livro(
                            rs.getInt("idlivro"),
                            rs.getString("titulo"),
                            rs.getString("autor"),
                            rs.getString("descricao"),
                            rs.getFloat("preco"),
                            rs.getBoolean("esta_a_venda"),
                            rs.getInt("id_cliente_dono"),
                            rs.getString("url"),
                            rs.getInt("qntd_livros")
                    );

                    livros.add(livro);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return livros;
    }


    // Método para inserir um novo livro
    public boolean inserirLivro(Livro livro) { // CREATE livro
        String sql = "INSERT INTO livro (titulo, autor, descricao, preco, esta_a_venda, id_cliente_dono, url, qntd_livros) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        ConectaDBPostgres cdb = new ConectaDBPostgres();

        try (Connection con = cdb.getConexao();
             PreparedStatement stmt = con.prepareStatement(sql)) {

            stmt.setString(1, livro.getTitulo());
            stmt.setString(2, livro.getAutor());
            stmt.setString(3, livro.getDescricao());
            stmt.setFloat(4, livro.getPreco());
            stmt.setBoolean(5, livro.isEstaAVenda());
            stmt.setInt(6, livro.getIdClienteDono());
            stmt.setString(7, livro.getUrl());
            stmt.setInt(8, livro.getQntdLivros());

            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Método para excluir um livro pelo ID
    public boolean excluirLivro(int id) {
        String sqlVenda = "DELETE FROM venda WHERE idlivro = ?";
        String sqlLivro = "DELETE FROM livro WHERE idlivro = ?";
        ConectaDBPostgres cdb = new ConectaDBPostgres();

        try (Connection con = cdb.getConexao()) {
            con.setAutoCommit(false); // Iniciar a transação

            try (PreparedStatement stmtVenda = con.prepareStatement(sqlVenda);
                 PreparedStatement stmtLivro = con.prepareStatement(sqlLivro)) {

                // Executar exclusão na tabela venda
                stmtVenda.setInt(1, id);
                stmtVenda.executeUpdate();

                // Executar exclusão na tabela livro
                stmtLivro.setInt(1, id);
                stmtLivro.executeUpdate();

                con.commit(); // Confirmar a transação
                return true;
            } catch (SQLException e) {
                con.rollback(); // Reverter a transação em caso de erro
                e.printStackTrace();
                return false;
            } finally {
                con.setAutoCommit(true); // Restaurar o estado original de auto-commit
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Método para atualizar um livro
    public boolean atualizarLivro(Livro livro) { // UPDATE livro
        String sql = "UPDATE livro SET titulo = ?, autor = ?, descricao = ?, preco = ?, esta_a_venda = ?, url = ?, qntd_livros = ? WHERE idlivro = ?";
        ConectaDBPostgres cdb = new ConectaDBPostgres();

        try (Connection con = cdb.getConexao();
             PreparedStatement stmt = con.prepareStatement(sql)) {

            stmt.setString(1, livro.getTitulo());
            stmt.setString(2, livro.getAutor());
            stmt.setString(3, livro.getDescricao());
            stmt.setFloat(4, livro.getPreco());
            stmt.setBoolean(5, livro.isEstaAVenda());
            stmt.setString(6, livro.getUrl());
            stmt.setInt(7, livro.getQntdLivros());
            stmt.setInt(8, livro.getId());
            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public Livro getLivroById(int id) {
        ConectaDBPostgres cdb = new ConectaDBPostgres();
        Livro livro = null;

        String query = "SELECT * FROM livro WHERE idlivro = ?";
        try (Connection con = cdb.getConexao();
             PreparedStatement pstmt = con.prepareStatement(query)) {

            pstmt.setInt(1, id);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    livro = new Livro(
                            rs.getInt("idlivro"),
                            rs.getString("titulo"),
                            rs.getString("autor"),
                            rs.getString("descricao"),
                            rs.getFloat("preco"),
                            rs.getBoolean("esta_a_venda"),
                            rs.getInt("id_cliente_dono"),
                            rs.getString("url"),
                            rs.getInt("qntd_livros")
                    );
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return livro;
    }

    public ArrayList<Livro> getLivrosByEstaAVenda(boolean estaAVenda) {
        ConectaDBPostgres cdb = new ConectaDBPostgres();
        ArrayList<Livro> livros = new ArrayList<>();

        String query = "SELECT * FROM livro WHERE esta_a_venda = ?";
        try (Connection con = cdb.getConexao();
             PreparedStatement pstmt = con.prepareStatement(query)) {

            pstmt.setBoolean(1, estaAVenda);

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    Livro livro = new Livro(
                            rs.getInt("idlivro"),
                            rs.getString("titulo"),
                            rs.getString("autor"),
                            rs.getString("descricao"),
                            rs.getFloat("preco"),
                            rs.getBoolean("esta_a_venda"),
                            rs.getInt("id_cliente_dono"),
                            rs.getString("url"),
                            rs.getInt("qntd_livros")
                    );

                    livros.add(livro);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return livros;
    }

    public ArrayList<Livro> getLivrosByTitulo(boolean estaAVenda, String titulo) {
        ConectaDBPostgres cdb = new ConectaDBPostgres();
        ArrayList<Livro> livros = new ArrayList<>();

        String query = "SELECT * FROM livro WHERE esta_a_venda = ? AND LOWER(titulo) LIKE LOWER(?)";
        try (Connection con = cdb.getConexao();
             PreparedStatement pstmt = con.prepareStatement(query)) {

            pstmt.setBoolean(1, estaAVenda);
            pstmt.setString(2, "%" + titulo.toLowerCase() + "%");

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    Livro livro = new Livro(
                            rs.getInt("idlivro"),
                            rs.getString("titulo"),
                            rs.getString("autor"),
                            rs.getString("descricao"),
                            rs.getFloat("preco"),
                            rs.getBoolean("esta_a_venda"),
                            rs.getInt("id_cliente_dono"),
                            rs.getString("url"),
                            rs.getInt("qntd_livros")
                    );

                    livros.add(livro);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return livros;
    }
}

