package br.com.daciosoftware.degustlanches;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import br.com.daciosoftware.degustlanches.model.ProdutoEntity;
import br.com.daciosoftware.degustlanches.model.ProdutoPrecoEntity;

public class MenuProdutosPrecosNestedAdapter extends BaseAdapter {

    private Context context;
    private OnClickItemPreco onClickItemPreco;
    private List<ProdutoPrecoEntity> produtoPrecoEntities;
    private ProdutoEntity produtoEntity;
    private LayoutInflater inflater;

    public MenuProdutosPrecosNestedAdapter(Context context, List<ProdutoPrecoEntity> produtoPrecoEntities, ProdutoEntity produtoEntity, OnClickItemPreco onClickItemPreco) {
        this.context = context;
        this.produtoPrecoEntities = produtoPrecoEntities;
        this.produtoEntity = produtoEntity;
        this.onClickItemPreco = onClickItemPreco;
        inflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return produtoPrecoEntities.size();
    }

    @Override
    public ProdutoPrecoEntity getItem(int i) {
        return produtoPrecoEntities.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(final int position, View view, ViewGroup viewGroup) {

        view = inflater.inflate(R.layout.row_adapter_menu_precos, null);
        TextView unidade = view.findViewById(R.id.unidade);
        TextView preco = view.findViewById(R.id.preco);

        ProdutoPrecoEntity produtoPrecoEntity = produtoPrecoEntities.get(position);
        unidade.setText(String.valueOf(produtoPrecoEntity.getUnidadeProduto()));
        preco.setText(String.format("R$ %.2f", produtoPrecoEntity.getPrecoProduto()));
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickItemPreco.onClickItemPreco(produtoEntity, produtoPrecoEntities.get(position));
            }
        });
        return view;
    }

}
