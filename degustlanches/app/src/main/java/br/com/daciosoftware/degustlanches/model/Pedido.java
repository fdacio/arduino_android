package br.com.daciosoftware.degustlanches.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class Pedido implements Serializable {

    private int id;
    private int codigo;
    private Calendar data_hora;
    private FormaPagamento formaPagamento;
    private PedidoSituacao situacao;
    private double trocaPara;
    private Cliente cliente;
    private List<PedidoProduto> pedidoProdutos = new ArrayList<>();
    private double taxaEntrega;
    private int previsaoEntraga;
    private EnderecoEntrega enderecoEntrega;
    private double desconto;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public Calendar getData_hora() {
        return data_hora;
    }

    public void setData_hora(Calendar data_hora) {
        this.data_hora = data_hora;
    }

    public FormaPagamento getFormaPagamento() {
        return formaPagamento;
    }

    public void setFormaPagamento(FormaPagamento formaPagamento) {
        this.formaPagamento = formaPagamento;
    }

    public PedidoSituacao getSituacao() {
        return situacao;
    }

    public void setSituacao(PedidoSituacao situacao) {
        this.situacao = situacao;
    }

    public double getTrocaPara() {
        return trocaPara;
    }

    public void setTrocaPara(double trocaPara) {
        this.trocaPara = trocaPara;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public List<PedidoProduto> getPedidoProdutos() {
        return pedidoProdutos;
    }

    public void setPedidoProdutos(List<PedidoProduto> pedidoProdutos) {
        this.pedidoProdutos = pedidoProdutos;
    }

    public void addProduto(PedidoProduto pedidoProduto) {
        pedidoProdutos.add(pedidoProduto);
    }

    public int getPrevisaoEntraga() {
        return previsaoEntraga;
    }

    public void setPrevisaoEntraga(int previsaoEntraga) {
        this.previsaoEntraga = previsaoEntraga;
    }

    @Override
    public String toString() {
        return "Pedido{" +
                "id=" + id +
                ", codigo=" + codigo +
                ", data_hora=" + data_hora +
                ", formaPagamento=" + formaPagamento +
                ", situacao=" + situacao +
                ", trocaPara=" + trocaPara +
                ", cliente=" + cliente +
                '}';
    }

    public int getQtdeItens() {

        return pedidoProdutos.size();
    }

    public double getValorTotal() {

        double total = 0;
        for (PedidoProduto pedidoProduto : getPedidoProdutos()) {
            total += pedidoProduto.getPreco() * pedidoProduto.getQuantidade();
        }
        return total;
    }

    public EnderecoEntrega getEnderecoEntrega() {
        return enderecoEntrega;
    }

    public void setEnderecoEntrega(EnderecoEntrega enderecoEntrega) {
        this.enderecoEntrega = enderecoEntrega;
    }

    public double getTaxaEntrega() {
        return taxaEntrega;
    }

    public void setTaxaEntrega(double taxaEntrega) {
        this.taxaEntrega = taxaEntrega;
    }

    public double getDesconto() {
        return desconto;
    }

    public void setDesconto(double desconto) {
        this.desconto = desconto;
    }
}
