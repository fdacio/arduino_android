package br.com.daciosoftware.degustlanches;

import br.com.daciosoftware.degustlanches.model.PedidoProduto;

public interface OnClickItemProdutoPedido {

    void deletePedidoProduto(PedidoProduto pedidoProduto);

    void delIngrediente(PedidoProduto pedidoProduto);

    void addIngrediente(PedidoProduto pedidoProduto);

    void delObservacoes(PedidoProduto pedidoProduto);

    void addObservacoes(PedidoProduto pedidoProduto);

}
