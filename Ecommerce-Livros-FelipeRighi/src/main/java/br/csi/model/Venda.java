package br.csi.model;


import java.util.Date;

public class Venda {
    private int id;
    private int idClienteComprador;
    private int idLivro;
    private String tituloLivro;
    private Date dataVenda;
    private float valorTotal;
    private int quantidade;

    // Construtor completo
    public Venda(int id, int idClienteComprador, int idLivro) {
        this.id = id;
        this.idClienteComprador = idClienteComprador;
        this.idLivro = idLivro;
    }

        // Construtor 2
    public Venda(int idClienteComprador, int idLivro) {
        this.idClienteComprador = idClienteComprador;
        this.idLivro = idLivro;
    }

    public Venda(int id, int idClienteComprador, int idLivro, String tituloLivro, Date dataVenda) {
        this.id = id;
        this.idClienteComprador = idClienteComprador;
        this.idLivro = idLivro;
        this.tituloLivro = tituloLivro;
        this.dataVenda = dataVenda;
    }

    public Venda(int id, int idClienteComprador, int idLivro, String tituloLivro, Date dataVenda, float valorTotal, int quantidade) {
        this.id = id;
        this.idClienteComprador = idClienteComprador;
        this.idLivro = idLivro;
        this.tituloLivro = tituloLivro;
        this.dataVenda = dataVenda;
        this.valorTotal = valorTotal;
        this.quantidade = quantidade;
    }

    public Venda(int idClienteComprador, int idLivro, String tituloLivro, Date dataVenda, float valorTotal, int quantidade) {
        this.idClienteComprador = idClienteComprador;
        this.idLivro = idLivro;
        this.tituloLivro = tituloLivro;
        this.dataVenda = dataVenda;
        this.valorTotal = valorTotal;
        this.quantidade = quantidade;
    }

    public Venda(int idClienteComprador, int idLivro, float valorTotal, int quantidade) {
        this.idClienteComprador = idClienteComprador;
        this.idLivro = idLivro;
        this.valorTotal = valorTotal;
        this.quantidade = quantidade;
    }

    public float getValorTotal() {
        return valorTotal;
    }

    public void setValorTotal(float valorTotal) {
        this.valorTotal = valorTotal;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    // Getters e Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdClienteComprador() {
        return idClienteComprador;
    }

    public void setIdClienteComprador(int idClienteComprador) {
        this.idClienteComprador = idClienteComprador;
    }

    public int getIdLivro() {
        return idLivro;
    }

    public void setIdLivro(int idLivro) {
        this.idLivro = idLivro;
    }

    public String getTituloLivro() {
        return tituloLivro;
    }

    public void setTituloLivro(String tituloLivro) {
        this.tituloLivro = tituloLivro;
    }

    public Date getDataVenda() {
        return dataVenda;
    }

    public void setDataVenda(Date dataVenda) {
        this.dataVenda = dataVenda;
    }
}
