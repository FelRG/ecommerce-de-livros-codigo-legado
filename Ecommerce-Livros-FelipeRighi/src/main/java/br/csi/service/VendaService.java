package br.csi.service;

import br.csi.dao.VendaDAO;
import br.csi.model.Livro;
import br.csi.model.Venda;

import java.util.ArrayList;

public class VendaService {

    public boolean inserirVenda(Venda venda, Livro livro){
      return new VendaDAO().inserirVenda(venda, livro);
    }

    public ArrayList<Venda> getVendas(){
        return new VendaDAO().getVendas();
    }


    public ArrayList<Venda> getVendasByIDCliente(int idCliente) {
        return new VendaDAO().getVendasByIDCliente(idCliente);
    }
}