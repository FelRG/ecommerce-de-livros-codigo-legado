package br.csi.service;

import br.csi.dao.UsuarioDAO;
import br.csi.model.Usuario;

import java.util.ArrayList;

public class UsuarioService {

    public boolean inserir(Usuario usuario){
      return  new UsuarioDAO().inserir(usuario);
    }

    public boolean excluir(int id) {
        return new UsuarioDAO().excluir(id);
    }

    public boolean atualizar(Usuario usuario) {
        return new UsuarioDAO().atualizar(usuario);
    }

    public ArrayList<Usuario> getUsuarios(){
        return new UsuarioDAO().getUsuarios();
    }
}
