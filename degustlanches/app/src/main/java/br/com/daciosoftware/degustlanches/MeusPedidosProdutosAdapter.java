package br.com.daciosoftware.degustlanches;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import br.com.daciosoftware.degustlanches.model.PedidoProduto;

public class MeusPedidosProdutosAdapter extends BaseAdapter {

    private Context context;
    private List<PedidoProduto> listaPedidoProdutos;


    public MeusPedidosProdutosAdapter(Context context, List<PedidoProduto> listaPedidoProdutos) {
        this.context = context;
        this.listaPedidoProdutos = listaPedidoProdutos;
    }


    @Override
    public int getCount() {
        return listaPedidoProdutos.size();
    }

    @Override
    public PedidoProduto getItem(int i) {
        return listaPedidoProdutos.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View convertView, ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.row_adapter_meus_pedidos_produtos, parent, false);
        PedidoProduto pedidoProduto = listaPedidoProdutos.get(i);

        TextView nome = view.findViewById(R.id.nome);
        TextView ingredientes = view.findViewById(R.id.ingredientes);
        TextView observacoes = view.findViewById(R.id.observacoes);
        TextView preco = view.findViewById(R.id.preco);

        nome.setText(pedidoProduto.getProduto().getNome());
        if(!pedidoProduto.getProduto().getIngredientes().isEmpty()) {
            ingredientes.setText(pedidoProduto.getProduto().getIngredientes());
            ingredientes.setVisibility(View.VISIBLE);
        } else {
            ingredientes.setVisibility(View.GONE);
        }
        if(!pedidoProduto.getObservacoes().isEmpty()) {
            observacoes.setText(pedidoProduto.getObservacoes());
            observacoes.setVisibility(View.VISIBLE);
        } else {
            observacoes.setVisibility(View.GONE);
        }

        preco.setText(String.format("R$ %.2f", pedidoProduto.getPreco()));

        return view;
    }
}
