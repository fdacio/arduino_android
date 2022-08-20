package br.com.daciosoftware.degustlanches;


import br.com.daciosoftware.degustlanches.model.ProdutoEntity;
import br.com.daciosoftware.degustlanches.model.ProdutoPrecoEntity;

public interface OnClickItemPreco {
    void onClickItemPreco(ProdutoEntity produtoEntity, ProdutoPrecoEntity produtoPrecoEntity);
}
