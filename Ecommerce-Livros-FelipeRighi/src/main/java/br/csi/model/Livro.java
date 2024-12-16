package br.csi.model;

public class Livro {
    private int id;
    private String titulo;
    private String autor;
    private String descricao;
    private float preco;
    private boolean estaAVenda;
    private int idClienteDono;
    private String url;
    private int qntdLivros;

    public Livro(int id, String titulo, String autor, String descricao, float preco, boolean estaAVenda, int idClienteDono) {
        this.id = id;
        this.titulo = titulo;
        this.autor = autor;
        this.descricao = descricao;
        this.preco = preco;
        this.estaAVenda = estaAVenda;
        this.idClienteDono = idClienteDono;
    }

    public Livro(int id, String titulo, String autor, String descricao, float preco, boolean estaAVenda) {
        this.id = id;
        this.titulo = titulo;
        this.autor = autor;
        this.descricao = descricao;
        this.preco = preco;
        this.estaAVenda = estaAVenda;
    }

    public Livro(String titulo, String autor, String descricao, float preco, boolean estaAVenda, int idClienteDono) {
        this.titulo = titulo;
        this.autor = autor;
        this.descricao = descricao;
        this.preco = preco;
        this.estaAVenda = estaAVenda;
        this.idClienteDono = idClienteDono;
    }

    public Livro(int id, String titulo, String autor, String descricao, float preco, boolean estaAVenda, int idClienteDono, String url, int qntdLivros) {
        this.id = id;
        this.titulo = titulo;
        this.autor = autor;
        this.descricao = descricao;
        this.preco = preco;
        this.estaAVenda = estaAVenda;
        this.idClienteDono = idClienteDono;
        this.url = url;
        this.qntdLivros = qntdLivros;
    }

    public Livro(String titulo, String autor, String descricao, float preco, boolean estaAVenda, int idClienteDono, String url, int qntdLivros) {
        this.titulo = titulo;
        this.autor = autor;
        this.descricao = descricao;
        this.preco = preco;
        this.estaAVenda = estaAVenda;
        this.idClienteDono = idClienteDono;
        this.url = url;
        this.qntdLivros = qntdLivros;
    }

    public Livro(int id, String titulo, String autor, String descricao, float preco, boolean estaAVenda, String url, int qntdLivros) {
        this.id = id;
        this.titulo = titulo;
        this.autor = autor;
        this.descricao = descricao;
        this.preco = preco;
        this.estaAVenda = estaAVenda;
        this.url = url;
        this.qntdLivros = qntdLivros;
    }

    public Livro(String titulo, String autor, String descricao, float preco, boolean estaAVenda, String url, int qntdLivros) {
        this.titulo = titulo;
        this.autor = autor;
        this.descricao = descricao;
        this.preco = preco;
        this.estaAVenda = estaAVenda;
        this.url = url;
        this.qntdLivros = qntdLivros;
    }

    public Livro(String titulo, String autor, String descricao, float preco, String url) {
        this.titulo = titulo;
        this.autor = autor;
        this.descricao = descricao;
        this.preco = preco;
        this.url = url;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getQntdLivros() {
        return qntdLivros;
    }

    public void setQntdLivros(int qntdLivros) {
        this.qntdLivros = qntdLivros;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public float getPreco() {
        return preco;
    }

    public void setPreco(float preco) {
        this.preco = preco;
    }

    public boolean isEstaAVenda() {
        return estaAVenda;
    }

    public void setEstaAVenda(boolean estaAVenda) {
        this.estaAVenda = estaAVenda;
    }

    public int getIdClienteDono() {
        return idClienteDono;
    }

    public void setIdClienteDono(int idClienteDono) {
        this.idClienteDono = idClienteDono;
    }
}
