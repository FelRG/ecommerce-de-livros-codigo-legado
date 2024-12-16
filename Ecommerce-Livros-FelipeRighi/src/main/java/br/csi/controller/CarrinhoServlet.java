package br.csi.controller;

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
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

@WebServlet("carrinho")
public class CarrinhoServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("opção: "+req.getParameter("opcao"));

        String opcao = req.getParameter("opcao");

        if (opcao != null) {
            if (opcao.equals("visualizarcarrinho")) {
                RequestDispatcher rd = req.getRequestDispatcher("/WEB-INF/pages/visualizarcarrinho.jsp");

                // Recupera a sessão e o carrinho
                HttpSession sessao = req.getSession();
                ArrayList<Livro> carrinho = (ArrayList<Livro>) sessao.getAttribute("carrinho");

                req.setAttribute("livros", carrinho);
                rd.forward(req, resp);
                return;
            } else if (opcao.equals("rmlivrocarrinho")){
                int id = Integer.parseInt(req.getParameter("idLivro"));

                // Recupera a sessão e o carrinho
                HttpSession sessao = req.getSession();
                ArrayList<Livro> carrinho = (ArrayList<Livro>) sessao.getAttribute("carrinho");

                if (carrinho != null) {
                    // Itera sobre o carrinho para encontrar o livro com o ID correspondente
                    Iterator<Livro> iterator = carrinho.iterator();
                    while (iterator.hasNext()) {
                        Livro l = iterator.next();
                        if (l.getId() == id) {
                            // Remove o livro do carrinho
                            iterator.remove();
                            req.setAttribute("retorno", "Livro removido do carrinho!");
                            break;
                        }
                    }
                }
                RequestDispatcher rd = req.getRequestDispatcher("carrinho?opcao=visualizarcarrinho");
                rd.forward(req, resp);
                return;
            } else if (opcao.equals("compraslivroscarrinho")){
                try {
                    // Recupera a sessão e o carrinho
                    HttpSession sessao = req.getSession();
                    ArrayList<Livro> carrinho = (ArrayList<Livro>) sessao.getAttribute("carrinho");

                    if (carrinho != null){
                        int idClienteComprador = Integer.parseInt(req.getParameter("idClienteComprador"));
                        boolean compraSucesso = true;

                        for (Livro livro : carrinho) {
                            int quantidade = Integer.parseInt(req.getParameter("qntd_livros"));
                            float valorTotal = livro.getPreco() * quantidade;

                            Venda venda = new Venda(idClienteComprador, livro.getId(), valorTotal, quantidade);

                            // Chama o serviço de venda para inserir a venda
                            if (!new VendaService().inserirVenda(venda, livro)) {
                                compraSucesso = false;
                            }
                        }

                        if (compraSucesso) {
                            sessao.removeAttribute("carrinho");
                            req.setAttribute("retorno", "COMPRA realizada com SUCESSO");
                        } else {
                            req.setAttribute("retorno", "ERRO ao COMPRAR LIVROS");
                        }
                    }

                    RequestDispatcher rd = req.getRequestDispatcher("carrinho?opcao=visualizarcarrinho");
                    rd.forward(req, resp);
                    return;
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                    throw new ServletException("Erro ao comprar os livros/livro no carrinho", e);
                }
            } else if (opcao.equals("addlivrocarrinho")){
                int id = Integer.parseInt(req.getParameter("idLivro"));

                // Criando um objeto Livro com os dados do formulário
                Livro livro = LivroService.getLivroById(id);

                // Recupera a sessão e o carrinho
                HttpSession sessao = req.getSession();
                ArrayList<Livro> carrinho = (ArrayList<Livro>) sessao.getAttribute("carrinho");

                if (carrinho == null) {
                    carrinho = new ArrayList<>();
                    sessao.setAttribute("carrinho", carrinho);
                }

                // Verifica se o livro já está no carrinho
                boolean livroJaNoCarrinho = false;
                for (Livro l : carrinho) {
                    if (l.getId() == livro.getId()) {
                        livroJaNoCarrinho = true;
                        break;
                    }
                }

                if (livroJaNoCarrinho) {
                    // Se o livro já estiver no carrinho, informe o usuário
                    req.setAttribute("retorno", "O livro já está no carrinho!");
                } else {
                    // Adiciona o livro ao carrinho
                    carrinho.add(livro);
                    req.setAttribute("retorno", "Livro adicionado ao carrinho!");
                }

                RequestDispatcher rd = req.getRequestDispatcher("/livro?opcao=mostrarlivrosavenda");
                rd.forward(req, resp);
                return;
            }
            RequestDispatcher rd = req.getRequestDispatcher("reddashbord");
            rd.forward(req, resp);
            return;
        }
    }
}
