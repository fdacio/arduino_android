package br.com.daciosoftware.degustlanches;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import br.com.daciosoftware.degustlanches.model.Pedido;
import br.com.daciosoftware.degustlanches.model.PedidoProduto;
import br.com.daciosoftware.degustlanches.util.MyDateUtil;


public class MeusPedidosAdapter extends RecyclerView.Adapter<MeusPedidosAdapter.MeusPedidosViewHolder> {

    private final OnClickItemPedido onClickItemPedido;
    private List<Pedido> listPedidos;
    private Context context;

    public MeusPedidosAdapter(List<Pedido> listPedidos, OnClickItemPedido onClickItemPedido, Context context) {
        this.listPedidos = listPedidos;
        this.onClickItemPedido = onClickItemPedido;
        this.context = context;
    }

    @NonNull
    @Override
    public MeusPedidosViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = (View) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_adapter_meus_pedidos, parent, false);

        return new MeusPedidosAdapter.MeusPedidosViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MeusPedidosViewHolder holder, int position) {

        Pedido pedido = listPedidos.get(position);
        holder.pedidoNumero.setText(String.format("%s", pedido.getId()));
        holder.pedidoSituacao.setText(String.format("%s", pedido.getSituacao().getDescricao()));
        holder.pedidoDataHora.setText(String.format("%s", MyDateUtil.calendarToDateTimeBr(pedido.getData_hora())));
        holder.pedidoFormaPagamento.setText(String.format("%s", pedido.getFormaPagamento().getDescricao()));

        if(pedido.getTrocaPara() > 0) {
            holder.pedidoTrocoPara.setVisibility(View.VISIBLE);
            holder.pedidoTrocoPara.setText(String.format("R$ %.2f", pedido.getTrocaPara()));
        } else {
            holder.pedidoTrocoPara.setVisibility(View.GONE);
        }

        holder.pedidoEnderecoEntrega.setText(pedido.getEnderecoEntrega().getEnderecoCompleto());
        holder.pedidoPrevisaoEntrega.setText(String.format("%d min.", pedido.getPrevisaoEntraga()));

        MeusPedidosProdutosAdapter adapter = new MeusPedidosProdutosAdapter(context, pedido.getPedidoProdutos());
        holder.listViewPedidoProdutos.setAdapter(adapter);

        holder.pedidoValorTotal.setText(String.format("Total R$ %.2f", pedido.getValorTotal()));
        holder.pedidoTaxaEntrega.setText(String.format("Taxa de Entrega R$ %.2f", pedido.getTaxaEntrega()));
        holder.pedidoDesconto.setText(String.format("Desconto R$ %.2f", pedido.getDesconto()));
        holder.pedidoValorFinal.setText(String.format("Valor Final R$ %.2f", pedido.getValorTotal() + pedido.getTaxaEntrega() - pedido.getDesconto()));

    }

    @Override
    public int getItemCount() {
        return listPedidos.size();
    }

    protected class MeusPedidosViewHolder extends RecyclerView.ViewHolder {

        public TextView pedidoNumero;
        public TextView pedidoSituacao;
        public TextView pedidoDataHora;
        public TextView pedidoFormaPagamento;
        public TextView pedidoTrocoPara;
        public TextView pedidoEnderecoEntrega;
        public TextView pedidoPrevisaoEntrega;
        public ImageButton btnCancelaPedido;
        public ListView listViewPedidoProdutos;

        public TextView pedidoValorTotal;
        public TextView pedidoTaxaEntrega;
        public TextView pedidoDesconto;
        public TextView pedidoValorFinal;

        public MeusPedidosViewHolder(@NonNull View itemView) {
            super(itemView);

            pedidoNumero = itemView.findViewById(R.id.pedido_numero);
            pedidoSituacao = itemView.findViewById(R.id.pedido_situacao);
            pedidoDataHora = itemView.findViewById(R.id.pedido_data_hora);
            pedidoFormaPagamento = itemView.findViewById(R.id.pedido_forma_pagamento);
            pedidoTrocoPara = itemView.findViewById(R.id.pedido_troco_para);
            pedidoEnderecoEntrega = itemView.findViewById(R.id.pedido_endereco_entrega);
            pedidoPrevisaoEntrega = itemView.findViewById(R.id.pedido_previsao_entrega);
            btnCancelaPedido = itemView.findViewById(R.id.btnCancelPedido);

            listViewPedidoProdutos = itemView.findViewById(R.id.listViewPedidoProdutos);

            pedidoValorTotal = itemView.findViewById(R.id.valorTotal);
            pedidoTaxaEntrega = itemView.findViewById(R.id.taxaEntrega);
            pedidoDesconto = itemView.findViewById(R.id.desconto);
            pedidoValorFinal = itemView.findViewById(R.id.valorFinal);

            btnCancelaPedido.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onClickItemPedido.onClickItem(listPedidos.get(getLayoutPosition()));
                }
            });
        }
    }
}