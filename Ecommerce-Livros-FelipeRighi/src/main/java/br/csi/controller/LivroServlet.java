package br.csi.controller;

import br.csi.dao.LivroDAO;
import br.csi.model.Livro;
import br.csi.service.LivroService;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.ArrayList;

@WebServlet("/livro")
public class LivroServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        System.out.println("opção: "+req.getParameter("opcao"));

        String opcao = req.getParameter("opcao");

        if(opcao != null) {
            if (opcao.equals("cadastrar")) {
                try {
                    //lógica do cadastrar
                    String titulo = req.getParameter("titulo");
                    String autor = req.getParameter("autor");
                    String descricao = req.getParameter("descricao");
                    float preco = Float.parseFloat(req.getParameter("preco"));
                    boolean estaAVenda = Boolean.parseBoolean(req.getParameter("estaAVenda"));
                    int idClienteDono = Integer.parseInt(req.getParameter("idClienteDono"));
                    String url = req.getParameter("url");
                    int quantidadeLivros = Integer.parseInt(req.getParameter("qntd_livros"));

                    System.out.println("titulo: " + titulo + " autor: " + autor + " descricao: " + descricao + " preco: " + preco + " estaAVenda: " + estaAVenda + " idClienteDono: " + idClienteDono);
                    System.out.println(url);
                    System.out.println(quantidadeLivros);

                    // Criando um objeto Livro com os dados do formulário
                    Livro livro = new Livro(titulo, autor, descricao, preco, estaAVenda, idClienteDono, url, quantidadeLivros);

                    // Chamando o serviço para inserir o livro no banco de dados
                    if (new LivroService().inserirLivro(livro)) {
                        req.setAttribute("retorno", "Livro inserido com sucesso.");
                    } else {
                        req.setAttribute("retorno", "Erro ao inserir o livro.");
                    }

                    // Encaminhe para a página adequada
                    RequestDispatcher rd = req.getRequestDispatcher("/WEB-INF/pages/adicionarlivro.jsp");
                    rd.forward(req, resp);
                    return;
                } catch (Exception e) {
                    e.printStackTrace();
                    throw new ServletException("Erro ao cadastrar o livro", e);
                }
            } else if(opcao.equals("editar")){
                try{
                    //lógica do editar
                    int id = Integer.parseInt(req.getParameter("idLivro"));
                    String titulo = req.getParameter("titulo");
                    String autor = req.getParameter("autor");
                    String descricao = req.getParameter("descricao");
                    float preco = Float.parseFloat(req.getParameter("preco"));
                    boolean estaAVenda = Boolean.parseBoolean(req.getParameter("estaAVenda"));
                    String url = req.getParameter("url");
                    int quantidadeLivros = Integer.parseInt(req.getParameter("qntd_livros"));

                    System.out.println(" idLivro: " + id + " titulo: " + titulo + " autor: " + autor + " descricao: " + descricao + " preco: " + preco + " estaAVenda: " + estaAVenda);
                    System.out.println(url);
                    System.out.println(quantidadeLivros);

                    // Criando um objeto Livro com os dados do formulário
                    Livro livro = new Livro(id, titulo, autor, descricao, preco, estaAVenda, url, quantidadeLivros);

                    // Chamando o serviço para alterar os dados do livro no banco de dados e atualiza na página
                    if(quantidadeLivros > 0){
                        if (new LivroService().atualizarLivro(livro)) {
                            req.setAttribute("retorno", "Dados do livro alterado com sucesso.");
                            req.setAttribute("livro", livro);
                        } else {
                            req.setAttribute("retorno", "Erro ao alterar dados do livro.");
                        }
                    } else if(estaAVenda == false){
                        if (new LivroService().atualizarLivro(livro)) {
                            req.setAttribute("retorno", "Dados do livro alterado com sucesso.");
                            req.setAttribute("livro", livro);
                        } else {
                            req.setAttribute("retorno", "Erro ao alterar dados do livro.");
                        }
                    } else if(estaAVenda == true){
                        req.setAttribute("retorno", "NÃO é possível colocar o livro a venda pois há 0 em estoque");
                        req.setAttribute("livro", livro);
                    }
                    // Encaminhe para a página adequada
                    RequestDispatcher rd = req.getRequestDispatcher("/WEB-INF/pages/editarlivro.jsp");
                    rd.forward(req, resp);
                    return;

                } catch (Exception e) {
                    e.printStackTrace();
                    throw new ServletException("Erro ao atualizar os dados do livro", e);
                }
            }
        }


    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        System.out.println("opção: "+req.getParameter("opcao"));

        String opcao = req.getParameter("opcao");

        if (opcao != null){
            if (opcao.equals("excluir")){
                try {
                    int id = Integer.parseInt(req.getParameter("idLivro"));
                    int clienteId = Integer.parseInt(req.getParameter("idClienteDono"));

                    // Exclui o livro
                    new LivroService().excluirLivro(id);
                    req.setAttribute("retorno","Livro EXCLUÍDO com SUCESSO");

                    // Recarregar a lista de livros após exclusão
                    ArrayList<Livro> livros = new LivroService().getLivrosByClienteId(clienteId);
                    req.setAttribute("livros", livros);

                    RequestDispatcher rd =
                            req.getRequestDispatcher("WEB-INF/pages/meuslivros.jsp");
                    rd.forward(req, resp);
                    return;

                } catch (NumberFormatException e){
                    e.printStackTrace();
                    throw new ServletException("Erro ao excluir o livro", e);
                }
            } else if (opcao.equals("leitura")){
                int clienteId = Integer.parseInt(req.getParameter("id"));
                ArrayList<Livro> livros = LivroService.getLivrosByClienteId(clienteId);
                req.setAttribute("livros", livros);

                // vai para a pagina dos livros do usuario
                RequestDispatcher dispatcher = req.getRequestDispatcher("WEB-INF/pages/meuslivros.jsp");
                dispatcher.forward(req, resp);
                return;
            } else if (opcao.equals("detalheslivro")) {
                // mostrar detalhes de um livro; obs: copiar o que está em RedEditarLivroServlet, alterar a view e depois removê-lo
                try {
                    //Recebe o ID do livro

                    int id = Integer.parseInt(req.getParameter("idLivro"));

                    // Exemplo de chamada ao serviço que retorna as informações de um livro
                    Livro livro = LivroService.getLivroById(id);

                    // Adiciona as informações do livro como atributo da requisição
                    req.setAttribute("livro", livro);

                    RequestDispatcher rd =
                            req.getRequestDispatcher("WEB-INF/pages/editarlivro.jsp");
                    rd.forward(req, resp);
                    return;
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                    throw new ServletException("Erro ao mostrar os livros do cliente", e);
                }
            } else if (opcao.equals("mostrarlivrosavenda")){
                boolean estaAVenda = true;
                ArrayList<Livro> livros = LivroService.getLivrosByEstaAVenda(estaAVenda);
                req.setAttribute("livros", livros);

                // vai para a pagina dos livros a que estao a venda
                RequestDispatcher dispatcher = req.getRequestDispatcher("WEB-INF/pages/comprarlivro.jsp");
                dispatcher.forward(req, resp);
                return;

            } else if (opcao.equals("buscarLivroFiltro")){
                String titulo = req.getParameter("titulo");
                boolean estaAVenda = true;
                ArrayList<Livro> livros = LivroService.getLivrosByTitulo(estaAVenda, titulo);
                req.setAttribute("livros", livros);

                // Encaminhe para a página adequada
                RequestDispatcher rd = req.getRequestDispatcher("/WEB-INF/pages/comprarlivro.jsp");
                rd.forward(req, resp);
                return;
            } else if (opcao.equals("redaddlivro")){
                RequestDispatcher rd =
                        req.getRequestDispatcher("WEB-INF/pages/adicionarlivro.jsp");
                rd.forward(req, resp);
                return;
            }
        }
        // Se nenhuma opção ou uma opção inválida for fornecida, redirecione para o index
        RequestDispatcher rd =
                req.getRequestDispatcher("/");
        rd.forward(req, resp);
        return;
    }
}
