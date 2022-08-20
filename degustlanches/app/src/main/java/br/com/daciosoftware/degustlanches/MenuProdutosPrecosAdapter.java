package br.com.daciosoftware.degustlanches;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import br.com.daciosoftware.degustlanches.model.ProdutoPrecoEntity;

public class MenuProdutosPrecosAdapter extends BaseAdapter {

    private Context context;
    private OnClickItemProduto onClickItemProduto;
    private List<ProdutoPrecoEntity> produtoPrecoEntities;
    private LayoutInflater inflater;

    public MenuProdutosPrecosAdapter(Context context, List<ProdutoPrecoEntity> produtoPrecoEntities, OnClickItemProduto onClickItemProduto) {
        this.context = context;
        this.onClickItemProduto = onClickItemProduto;
        this.produtoPrecoEntities = produtoPrecoEntities;
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
    public View getView(int position, View view, ViewGroup viewGroup) {

        view = inflater.inflate(R.layout.row_adapter_menu_precos, null);
        TextView unidade = view.findViewById(R.id.unidade);
        TextView preco = view.findViewById(R.id.preco);

        ProdutoPrecoEntity produtoPrecoEntity = produtoPrecoEntities.get(position);
        unidade.setText(String.valueOf(produtoPrecoEntity.getUnidadeProduto()));
        preco.setText(String.format("R$ %.2f", produtoPrecoEntity.getPrecoProduto()));

        return view;
    }

}
