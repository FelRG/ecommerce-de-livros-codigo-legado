package br.csi.controller;

import br.csi.dao.VendaDAO;
import br.csi.model.Livro;
import br.csi.model.Venda;
import br.csi.service.LivroService;
import br.csi.service.VendaService;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.ArrayList;

@WebServlet("/venda")
public class VendaServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // Obtenha os parâmetros do formulário
        /*int idLivro = Integer.parseInt(req.getParameter("idLivro"));
        int idClienteComprador = Integer.parseInt(req.getParameter("idClienteComprador"));


        // Crie um objeto Venda com os dados do formulário
        Venda venda = new Venda(idClienteComprador, idLivro);

        // Chame o serviço para inserir a venda no banco de dados
        VendaService vendaService = new VendaService();
        if (vendaService.inserirVenda(venda)) {
            req.setAttribute("retorno", "Venda inserida com sucesso.");
        } else {
            req.setAttribute("retorno", "Erro ao inserir a venda.");
        }
        */
        // Encaminhe para a página adequada
        RequestDispatcher rd = req.getRequestDispatcher("/WEB-INF/pages/dashbord.jsp"); // ver uma pagina melhor
        rd.forward(req, resp);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        System.out.println("opção: "+req.getParameter("opcao"));

        String opcao = req.getParameter("opcao");

        if (opcao != null){
            if (opcao.equals("mostrarlivrovenda")){
                try {
                    //Recebe o ID do livro

                    int id = Integer.parseInt(req.getParameter("idLivro"));

                    // Exemplo de chamada ao serviço que retorna as informações de um livro
                    Livro livro = LivroService.getLivroById(id);

                    // Adiciona as informações do livro como atributo da requisição
                    req.setAttribute("livro", livro);

                    RequestDispatcher rd =
                            req.getRequestDispatcher("WEB-INF/pages/confirmarcompra.jsp");
                    rd.forward(req, resp);
                    return;
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                    throw new ServletException("Erro ao mostrar livros a venda", e);
                }
            } else if (opcao.equals("comprarlivro")){
                try {
                    //Recebe parametros da venda
                    int idClienteComprador = Integer.parseInt(req.getParameter("idClienteComprador"));
                    int idLivro = Integer.parseInt(req.getParameter("idLivro"));
                    int quantidade = Integer.parseInt(req.getParameter("qntd_livros"));
                    float valorTotal = Float.parseFloat(req.getParameter("valor_total"));

                    //Recebe parametros das info do livro
                    String titulo = req.getParameter("titulo");
                    String autor = req.getParameter("autor");
                    String descricao = req.getParameter("descricao");
                    float preco = Float.parseFloat(req.getParameter("preco"));
                    String url = req.getParameter("url");

                    // Criando um objeto Livro com os dados do parâmetro
                    Livro livro = new Livro(titulo, autor, descricao, preco, url);

                    // Criando um objeto Venda com os dados do parâmetro
                    Venda venda = new Venda(idClienteComprador, idLivro, valorTotal, quantidade);

                    // Passando a venda e as informações do livro por parâmetro

                    if (new VendaService().inserirVenda(venda, livro)) {
                        req.setAttribute("retorno", "COMPRA realizada com SUCESSO");
                    } else {
                        req.setAttribute("retorno", "ERRO ao COMPRAR LIVRO");
                    }

                    RequestDispatcher rd =
                            //req.getRequestDispatcher("WEB-INF/pages/comprarlivro.jsp");
                            req.getRequestDispatcher("/livro?opcao=mostrarlivrosavenda");
                    rd.forward(req, resp);
                    return;
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                    throw new ServletException("Erro ao comprar um livro", e);
                }


            } else if (opcao.equals("mostrarpedidos")){ // mostrar pedidos de um usuário específico
                try {
                    //Recebe o ID do cliente

                    int idCliente = Integer.parseInt(req.getParameter("idCliente"));

                    ArrayList<Venda> vendas = new VendaService().getVendasByIDCliente(idCliente);
                    req.setAttribute("vendas", vendas);

                    RequestDispatcher rd =
                            req.getRequestDispatcher("WEB-INF/pages/meuspedidos.jsp");
                    rd.forward(req, resp);
                    return;
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                    throw new ServletException("Erro ao mostrar pedidos", e);
                }
            }
        }

    }
}