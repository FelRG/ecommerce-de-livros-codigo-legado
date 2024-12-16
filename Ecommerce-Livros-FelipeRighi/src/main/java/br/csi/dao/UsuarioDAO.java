package br.csi.dao;

import br.csi.model.Usuario;
import br.csi.util.ConectaDBPostgres;

import java.sql.*;
import java.util.ArrayList;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class UsuarioDAO { // mudar o inserir e o getUsuarios com prepareStatement


    public ArrayList<Usuario> getUsuarios(){ // READ cliente

        ConectaDBPostgres cdb = new ConectaDBPostgres();
        ArrayList<Usuario> usuarios = new ArrayList<>();
        try{
            Statement stmt = cdb.getConexao().createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM cliente");
            while (rs.next()){
                Usuario u =
                        new Usuario(
                                rs.getInt("idcliente"),
                                rs.getString("nome"),
                                rs.getString("email"),
                                rs.getString("senha"),
                                rs.getBoolean("ativo"));
                usuarios.add(u);
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return usuarios;
    }


    public Usuario autenticar(String email, String senha) { // Método Autenticar
        try {
            Connection con = new ConectaDBPostgres().getConexao();
            String sql = "SELECT * FROM cliente WHERE email = ? AND senha = ?";
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setString(1, email);
            stmt.setString(2, senha);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return new Usuario(
                        rs.getInt("idcliente"),
                        rs.getString("nome"),
                        rs.getString("email"),
                        rs.getString("senha"),
                        rs.getBoolean("ativo")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /*public boolean inserir(Usuario usuario) { // CREATE cliente

        try{
            Connection con = new ConectaDBPostgres().getConexao();
            Statement stmt = con.createStatement();
            String sql =
                    "INSERT INTO cliente(nome, email, senha, datacadastro, ativo) "+
                    "VALUES ('"+usuario.getNome()
                    +"','"+usuario.getEmail()
                    +"','"+usuario.getSenha()+"', CURRENT_DATE, true)";

            stmt.execute(sql);
            System.out.println("SQL: "+sql);
            return true;
        }catch (SQLException e){
            e.printStackTrace();
            return false;
        }
    }*/

    public boolean inserir(Usuario usuario) { // CREATE cliente
        String sql = "INSERT INTO cliente(nome, email, senha, datacadastro, ativo) VALUES (?, ?, ?, CURRENT_DATE, true)";

        try (Connection con = new ConectaDBPostgres().getConexao();
             PreparedStatement pstmt = con.prepareStatement(sql)) {

            pstmt.setString(1, usuario.getNome());
            pstmt.setString(2, usuario.getEmail());
            pstmt.setString(3, usuario.getSenha());

            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("SQL: " + sql);
                return true;
            }
            return false;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }


    public boolean excluir(int id) { // DELETE cliente
        Connection con = null;
        PreparedStatement stmtVenda = null;
        PreparedStatement stmtLivro = null;
        PreparedStatement stmtCliente = null;
        try {
            con = new ConectaDBPostgres().getConexao();
            con.setAutoCommit(false); // Iniciar transação

            // DELETE FROM venda WHERE id_cliente_comprador = ?
            String sqlVenda = "DELETE FROM venda WHERE id_cliente_comprador = ?";
            stmtVenda = con.prepareStatement(sqlVenda);
            stmtVenda.setInt(1, id);
            stmtVenda.executeUpdate();

            // DELETE FROM livro WHERE idlivro IN (SELECT idlivro FROM livro WHERE id_cliente_dono = ?)
            String sqlLivro = "DELETE FROM livro WHERE idlivro IN (SELECT idlivro FROM livro WHERE id_cliente_dono = ?)";
            stmtLivro = con.prepareStatement(sqlLivro);
            stmtLivro.setInt(1, id);
            stmtLivro.executeUpdate();

            // DELETE FROM cliente WHERE idcliente = ?
            String sqlCliente = "DELETE FROM cliente WHERE idcliente = ?";
            stmtCliente = con.prepareStatement(sqlCliente);
            stmtCliente.setInt(1, id);
            stmtCliente.executeUpdate();

            con.commit(); // Confirmar transação
            return true;
        } catch (SQLException e) {
            if (con != null) {
                try {
                    con.rollback(); // Reverter transação em caso de erro
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
            e.printStackTrace();
            return false;
        } finally {
            try {
                if (stmtVenda != null) stmtVenda.close();
                if (stmtLivro != null) stmtLivro.close();
                if (stmtCliente != null) stmtCliente.close();
                if (con != null) con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

        public boolean atualizar(Usuario usuario) { // UPDATE cliente
        Connection con = null;
        PreparedStatement stmt = null;
        try {
            con = new ConectaDBPostgres().getConexao();

            String sql = "UPDATE cliente SET nome = ?, email = ?, senha = ? WHERE idcliente = ?";
            stmt = con.prepareStatement(sql);
            stmt.setString(1, usuario.getNome());
            stmt.setString(2, usuario.getEmail());
            stmt.setString(3, usuario.getSenha());
            stmt.setInt(4, usuario.getId());

            int rowsUpdated = stmt.executeUpdate();
            return rowsUpdated > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            try {
                if (stmt != null) stmt.close();
                if (con != null) con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
