package br.csi.controller;

import br.csi.dao.UsuarioDAO;
import br.csi.model.Usuario;
import br.csi.service.UsuarioService;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.io.PrintWriter;


@WebServlet("/usuario")
public class UsuarioServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        System.out.println("opção: "+req.getParameter("opcao"));

        String opcao = req.getParameter("opcao");

        if(opcao != null) {
            if (opcao.equals("cadastrar")) {
                try {
                    //lógica do cadastrar
                    String nome = req.getParameter("nome");
                    String email = req.getParameter("email");
                    String senha = req.getParameter("senha");

                    System.out.println("Nome: "+nome+" email: "+email+" senha: "+senha);

                    Usuario usuario = new Usuario(nome, email, senha);

                    if (new UsuarioService().inserir(usuario)){
                        req.setAttribute("retorno","CADASTRO FEITO COM SUCESSO");
                    } else {
                        req.setAttribute("retorno","ERRO AO CADASTRAR");
                    }
                    // FAZER o forward após configurar todos os atributos necessários
                    RequestDispatcher rd = req.getRequestDispatcher("cadastro.jsp");
                    rd.forward(req, resp);
                    return;

                } catch (Exception e) {
                    e.printStackTrace();
                    throw new ServletException("Erro ao cadastrar o usuário", e);
                }
            } else if(opcao.equals("editar")){
                try{
                    //lógica do editar
                    int id = Integer.parseInt(req.getParameter("id"));
                    String nome = req.getParameter("nome");
                    String email = req.getParameter("email");
                    String senha = req.getParameter("senha");

                    System.out.println("ID: " + id + " Nome: " + nome + " email: " + email + " senha: " + senha);

                    Usuario usuario = new Usuario(id, nome, email, senha);

                    if (new UsuarioService().atualizar(usuario)) {
                        req.setAttribute("retorno", "Os seus DADOS foram ATUALIZADOS");
                    } else {
                        req.setAttribute("retorno", "ERRO AO ATUALIZAR OS DADOS");
                    }

                    HttpSession sessao = req.getSession(false);

                    if (sessao != null) {
                        sessao.setAttribute("id", usuario.getId()); // id
                        sessao.setAttribute("usuario", usuario.getNome()); // nome
                        sessao.setAttribute("email", usuario.getEmail()); // email
                    }

                    // FAZER o forward após configurar todos os atributos necessários
                    RequestDispatcher rd = req.getRequestDispatcher("WEB-INF/pages/meusdados.jsp");
                    rd.forward(req, resp);
                    return;
                } catch (Exception e) {
                    e.printStackTrace();
                    throw new ServletException("Erro ao atualizar os dados do usuário", e);
                }
            }
        }
        // Se nenhuma opção ou uma opção inválida for fornecida, redirecione para o index
        RequestDispatcher rd =
                req.getRequestDispatcher("/");
        rd.forward(req, resp);
        return;

    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        System.out.println("opção: "+req.getParameter("opcao"));

        String opcao = req.getParameter("opcao");

        if(opcao != null) {
            if (opcao.equals("excluir")) {
                try{
                    //lógica do excluir
                    int id = Integer.parseInt(req.getParameter("id"));
                    new UsuarioService().excluir(id);

                    //req.setAttribute("retorno","Sua conta foi EXCLUÍDA com SUCESSO");

                    RequestDispatcher rd =
                            req.getRequestDispatcher("/logout");
                    rd.forward(req, resp);
                    return;
                }catch (NumberFormatException e){
                    e.printStackTrace();
                    throw new ServletException("Erro ao excluir usuário", e);
                }
            } else if (opcao.equals("redmeusdados")){
                RequestDispatcher rd =
                        req.getRequestDispatcher("WEB-INF/pages/meusdados.jsp");
                rd.forward(req, resp);
                return;
            } else if (opcao.equals("redexcluirconta")){
                RequestDispatcher rd =
                        req.getRequestDispatcher("WEB-INF/pages/excluirconta.jsp");
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