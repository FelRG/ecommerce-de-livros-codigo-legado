package br.csi.service;

import br.csi.dao.UsuarioDAO;
import br.csi.model.Usuario;

public class LoginService {

    public Usuario logar(String email, String senha) {
        UsuarioDAO usuarioDAO = new UsuarioDAO();
        return usuarioDAO.autenticar(email, senha);
    }

}
