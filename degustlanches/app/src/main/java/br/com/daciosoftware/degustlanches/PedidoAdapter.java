package br.com.daciosoftware.degustlanches;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import br.com.daciosoftware.degustlanches.model.PedidoProduto;
import br.com.daciosoftware.degustlanches.model.ProdutoTipo;

public class PedidoAdapter extends RecyclerView.Adapter<PedidoAdapter.PedidoViewHolder> {

    private final OnClickItemProdutoPedido onClickItemProdutoPedido;
    private List<PedidoProduto> listPedidoProdutos;

    public PedidoAdapter(List<PedidoProduto> listPedidoProdutos, OnClickItemProdutoPedido onClickItemProdutoPedido) {
        this.listPedidoProdutos = listPedidoProdutos;
        this.onClickItemProdutoPedido = onClickItemProdutoPedido;
    }

    @Override
    public PedidoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = (View) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_adapter_pedido, parent, false);

        return new PedidoAdapter.PedidoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PedidoViewHolder holder, int position) {

        PedidoProduto pedidoProduto = listPedidoProdutos.get(position);
        holder.textViewNomeTipo.setText(pedidoProduto.getProduto().getTipo().getDescricao());
        holder.textViewNome.setText(pedidoProduto.getProduto().getNome());

        holder.textViewPreco.setText(String.format("%d x R$ %.2f = R$ %.2f", pedidoProduto.getQuantidade(), pedidoProduto.getPreco(),  pedidoProduto.getQuantidade() * pedidoProduto.getPreco()));

        if (pedidoProduto.getProduto().getIngredientes() != null) {
            holder.textViewIngredientes.setText(pedidoProduto.getProduto().getIngredientes());

        } else {
            holder.imageButtonDelIngrediente.setVisibility(View.GONE);
            holder.imageButtonAddIngrediente.setVisibility(View.GONE);
            holder.textViewIngredientes.setVisibility(View.GONE);
        }

        holder.textViewObservacoes.setText((pedidoProduto.getObservacoes() != null)?pedidoProduto.getObservacoes():"");
        holder.linearLayoutObservacoes.setVisibility((pedidoProduto.getObservacoes() == null) ? View.GONE : View.VISIBLE);

        if ((pedidoProduto.getProduto().getTipo() == ProdutoTipo.SUSHI) || (pedidoProduto.getProduto().getTipo() == ProdutoTipo.ACAI)) {
            holder.imageButtonDelObs.setVisibility(View.GONE);
            holder.imageButtonAddObs.setVisibility(View.GONE);
            holder.imageButtonDelIngrediente.setVisibility(View.GONE);
            holder.imageButtonAddIngrediente.setVisibility(View.GONE);
        }

        if ((pedidoProduto.getProduto().getTipo() == ProdutoTipo.VARIEDADE)) {
            holder.imageButtonAddObs.setVisibility(View.GONE);
            holder.imageButtonAddIngrediente.setVisibility(View.GONE);
        }


    }

    @Override
    public int getItemCount() {
        return listPedidoProdutos.size();
    }

    protected class PedidoViewHolder extends RecyclerView.ViewHolder {

        public TextView textViewNomeTipo;
        public TextView textViewNome;
        public TextView textViewIngredientes;
        public LinearLayout linearLayoutObservacoes;
        public TextView textViewObservacoes;
        public TextView textViewPreco;
        public ImageButton imageButtonDelItem;
        public ImageButton imageButtonDelIngrediente;
        public ImageButton imageButtonAddIngrediente;
        public ImageButton imageButtonDelObs;
        public ImageButton imageButtonAddObs;

        public PedidoViewHolder(final View itemView) {

            super(itemView);
            textViewNomeTipo = itemView.findViewById(R.id.textViewNomeTipo);
            textViewNome =  itemView.findViewById(R.id.textViewNome);
            textViewIngredientes = itemView.findViewById(R.id.textViewIngredientesTexto);
            textViewObservacoes = itemView.findViewById(R.id.textViewObservacoes);
            linearLayoutObservacoes =  itemView.findViewById(R.id.linearLayoutObservacoes);
            textViewPreco =  itemView.findViewById(R.id.textViewPreco);
            imageButtonDelItem =  itemView.findViewById(R.id.imageButtonDelItem);
            imageButtonDelIngrediente =  itemView.findViewById(R.id.imageButtonDelIngrediente);
            imageButtonAddIngrediente = itemView.findViewById(R.id.imageButtonAddIngrediente);
            imageButtonDelObs =  itemView.findViewById(R.id.imageButtonDelObs);
            imageButtonAddObs = itemView.findViewById(R.id.imageButtonAddObservacao);

            imageButtonDelItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onClickItemProdutoPedido.deletePedidoProduto(listPedidoProdutos.get(getLayoutPosition()));
                }
            });

            imageButtonAddIngrediente.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onClickItemProdutoPedido.addIngrediente(listPedidoProdutos.get(getLayoutPosition()));
                }
            });

            imageButtonDelIngrediente.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onClickItemProdutoPedido.delIngrediente(listPedidoProdutos.get(getLayoutPosition()));
                }
            });

            imageButtonDelObs.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onClickItemProdutoPedido.delObservacoes(listPedidoProdutos.get(getLayoutPosition()));
                }
            });

            imageButtonAddObs.setOnClickListener( new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onClickItemProdutoPedido.addObservacoes(listPedidoProdutos.get(getLayoutPosition()));
                }
            });
        }
    }

}
