package br.csi.dao;

import br.csi.model.Livro;
import br.csi.model.Usuario;
import br.csi.model.Venda;
import br.csi.util.ConectaDBPostgres;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class VendaDAO {

    // Método para obter todas as vendas
    public ArrayList<Venda> getVendas() {

        ConectaDBPostgres cdb = new ConectaDBPostgres();
        ArrayList<Venda> vendas = new ArrayList<>();
        String sql = "SELECT idvenda, id_cliente_comprador, idlivro FROM venda";


        try{
            Statement stmt = cdb.getConexao().createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {
                Venda v =
                        new Venda(
                rs.getInt("idvenda"),
                rs.getInt("id_cliente_comprador"),
                rs.getInt("idlivro"));
                vendas.add(v);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return vendas;
    }

    // Método para inserir uma nova venda
    public boolean inserirVenda(Venda venda, Livro livro) {
        String sqlCheckEstoque = "SELECT qntd_livros, id_cliente_dono FROM livro WHERE idlivro = ?";
        String sqlInsertVenda = "INSERT INTO venda (datavenda, id_cliente_comprador, idlivro, valor_total, quantidade) VALUES (current_date, ?, ?, ?, ?)";
        String sqlUpdateEstoqueVendedor = "UPDATE livro SET qntd_livros = qntd_livros - ? WHERE idlivro = ?";
        String sqlInsertLivroComprador = "INSERT INTO livro (titulo, autor, descricao, preco, id_cliente_dono, url, qntd_livros, esta_a_venda) VALUES (?, ?, ?, ?, ?, ?, ?, false)";
        String sqlUpdateLivroVenda = "UPDATE livro SET esta_a_venda = false WHERE idlivro = ?";

        try (Connection connection = new ConectaDBPostgres().getConexao()) {
            connection.setAutoCommit(false);

            try (PreparedStatement pstmtCheckEstoque = connection.prepareStatement(sqlCheckEstoque);
                 PreparedStatement pstmtInsertVenda = connection.prepareStatement(sqlInsertVenda);
                 PreparedStatement pstmtUpdateEstoqueVendedor = connection.prepareStatement(sqlUpdateEstoqueVendedor);
                 PreparedStatement pstmtInsertLivroComprador = connection.prepareStatement(sqlInsertLivroComprador);
                 PreparedStatement pstmtUpdateLivroVenda = connection.prepareStatement(sqlUpdateLivroVenda))
            {

                // Verificar o estoque disponível
                pstmtCheckEstoque.setInt(1, venda.getIdLivro());
                try (ResultSet rsEstoque = pstmtCheckEstoque.executeQuery()) {
                    if (rsEstoque.next()) {
                        int estoqueDisponivel = rsEstoque.getInt("qntd_livros"); //qntd_livros qntdLivros
                        int idClienteDono = rsEstoque.getInt("id_cliente_dono");
                        if (estoqueDisponivel < venda.getQuantidade()) {
                            throw new SQLException("Quantidade insuficiente no estoque.");
                        }

                        // Inserir a venda
                        pstmtInsertVenda.setInt(1, venda.getIdClienteComprador());
                        pstmtInsertVenda.setInt(2, venda.getIdLivro());
                        pstmtInsertVenda.setFloat(3, venda.getValorTotal());
                        pstmtInsertVenda.setInt(4, venda.getQuantidade());
                        pstmtInsertVenda.executeUpdate();


                        // Atualizar o estoque do vendedor
                        pstmtUpdateEstoqueVendedor.setInt(1, venda.getQuantidade());
                        pstmtUpdateEstoqueVendedor.setInt(2, venda.getIdLivro());
                        pstmtUpdateEstoqueVendedor.executeUpdate();
                        estoqueDisponivel = estoqueDisponivel -  venda.getQuantidade();

                        // Adicionar os livros ao comprador
                        pstmtInsertLivroComprador.setString(1, livro.getTitulo());
                        pstmtInsertLivroComprador.setString(2, livro.getAutor());
                        pstmtInsertLivroComprador.setString(3, livro.getDescricao());
                        pstmtInsertLivroComprador.setFloat(4, livro.getPreco());
                        pstmtInsertLivroComprador.setInt(5, venda.getIdClienteComprador());
                        pstmtInsertLivroComprador.setString(6, livro.getUrl());
                        pstmtInsertLivroComprador.setInt(7, venda.getQuantidade());
                        pstmtInsertLivroComprador.executeUpdate();
                        // String sqlInsertLivroComprador = "INSERT INTO livro (titulo, autor, descricao, preco, id_cliente_dono, url, qntd_livros, esta_a_venda) VALUES (?, ?, ?, ?, ?, ?, ?, false)";

                        // Se o estoque do vendedor ficar zerado, marcar o livro como não à venda
                        if (estoqueDisponivel <= 0) {
                            pstmtUpdateLivroVenda.setInt(1, venda.getIdLivro());
                            pstmtUpdateLivroVenda.executeUpdate();
                        }

                        connection.commit();
                        return true;
                    } else {
                        throw new SQLException("Livro não encontrado.");
                    }
                }

            } catch (SQLException e) {
                connection.rollback();
                e.printStackTrace();
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public ArrayList<Venda> getVendasByIDCliente(int idCliente) {
        ConectaDBPostgres cdb = new ConectaDBPostgres();
        ArrayList<Venda> vendas = new ArrayList<>();
        String sql = "SELECT venda.idvenda, venda.id_cliente_comprador, venda.idlivro, livro.titulo, venda.datavenda, venda.valor_total, venda.quantidade " +
                "FROM cliente " +
                "JOIN venda ON venda.id_cliente_comprador = cliente.idcliente " +
                "JOIN livro ON venda.idLivro = livro.idLivro " +
                "WHERE cliente.idcliente = ?;";

        try (Connection conn = cdb.getConexao();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, idCliente);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    Date sqlDate = rs.getDate("datavenda");
                    java.util.Date utilDate = new java.util.Date(sqlDate.getTime());
                    Venda v = new Venda(
                            rs.getInt("idvenda"),
                            rs.getInt("id_cliente_comprador"),
                            rs.getInt("idlivro"),
                            rs.getString("titulo"),
                            utilDate,
                            rs.getFloat("valor_total"),
                            rs.getInt("quantidade")
                    );
                    vendas.add(v);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Você pode adicionar mais tratamento de exceções aqui, como logs ou mensagens específicas.
        }
        return vendas;
    }
}
