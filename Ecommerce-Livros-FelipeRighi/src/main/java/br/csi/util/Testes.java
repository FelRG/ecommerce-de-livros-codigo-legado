package br.csi.util;

import br.csi.dao.UsuarioDAO;
import br.csi.model.Usuario;

import java.util.ArrayList;

public class Testes {

    public static void main(String[] args) {

        UsuarioDAO dao = new UsuarioDAO();
        ArrayList<Usuario> usuarios = dao.getUsuarios();

        for(Usuario u : usuarios){
            System.out.println("ID: "+u.getId()
                              +" NOME: "+u.getNome());
        }
    }


}
