package br.csi.controller;

import br.csi.model.Livro;
import br.csi.model.Usuario;
import br.csi.service.LivroService;
import br.csi.service.LoginService;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.ArrayList;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String email = req.getParameter("email");
        String senha = req.getParameter("senha");

        Usuario usuario = new LoginService().logar(email, senha);

        if (usuario != null) {
            HttpSession sessao = req.getSession(true);

            // pega os atributos do usuario
            sessao.setAttribute("id", usuario.getId()); // id
            sessao.setAttribute("usuario", usuario.getNome()); // nome
            sessao.setAttribute("email", usuario.getEmail()); // email
            sessao.setAttribute("senha", usuario.getSenha()); // senha
            System.out.println("Usuário autenticado: " + usuario.getNome());
			
			// Cria um ArrayList para armazenar livros
            ArrayList<Livro> carrinho = new ArrayList<>();
            sessao.setAttribute("carrinho", carrinho);

            RequestDispatcher rd = req.getRequestDispatcher("/WEB-INF/pages/dashbord.jsp");
            rd.forward(req, resp);
            return;
        } else {
            RequestDispatcher rd = req.getRequestDispatcher("login.jsp");
            req.setAttribute("erro", "USUÁRIO OU SENHA INCORRETOS");
            rd.forward(req, resp);
            return;
        }
    }
}
