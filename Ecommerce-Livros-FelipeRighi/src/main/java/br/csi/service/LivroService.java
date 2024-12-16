package br.csi.service;

import br.csi.dao.LivroDAO;
import br.csi.dao.UsuarioDAO;
import br.csi.model.Livro;
import br.csi.model.Usuario;

import java.util.ArrayList;

public class LivroService {
    public static ArrayList<Livro> getLivrosByClienteId(int clienteId) {
        return new LivroDAO().getLivrosByClienteId(clienteId);
    }

    public static Livro getLivroById(int id) {
        return new LivroDAO().getLivroById(id);
    }

    public static ArrayList<Livro> getLivrosByEstaAVenda(boolean estaAVenda) {
        return new LivroDAO().getLivrosByEstaAVenda(estaAVenda);
    }

    public static ArrayList<Livro> getLivrosByTitulo(boolean estaAVenda, String titulo) {
        return new LivroDAO().getLivrosByTitulo(estaAVenda, titulo);
    }

    public boolean inserirLivro(Livro livro){

        return new LivroDAO().inserirLivro(livro);
    }

    public boolean excluirLivro(int id) {

        return new LivroDAO().excluirLivro(id);
    }

    public boolean atualizarLivro(Livro livro) {

        return new LivroDAO().atualizarLivro(livro);
    }

    public ArrayList<Livro> getLivros(){
        return new LivroDAO().getLivros();
    }
}
