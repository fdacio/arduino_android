package br.com.daciosoftware.degustlanches;

import android.content.Context;
import android.content.DialogInterface;
import android.view.View;

import androidx.appcompat.app.AlertDialog;

import br.com.daciosoftware.degustlanches.dao.PedidoDAO;
import br.com.daciosoftware.degustlanches.model.FormaPagamento;
import br.com.daciosoftware.degustlanches.model.Pedido;

public class DialogFormaPagamento implements View.OnClickListener {

    private Context context;

    public DialogFormaPagamento(Context context) {
        this.context = context;
    }

    @Override
    public void onClick(View view) {

        String[] arrayFormasPagamento = new String[FormaPagamento.values().length-1];

        int i = 0;
        for (FormaPagamento formaPagamento : FormaPagamento.values()) {
            if (formaPagamento != FormaPagamento.AINFORMAR) {
                arrayFormasPagamento[i] = formaPagamento.getDescricao();
                i++;
            }
        }

        final AlertDialog.Builder builder = new AlertDialog.Builder(context, R.style.MyAlertDialog);

        builder
                .setTitle(R.string.forma_pagamento_dialog)
                .setCancelable(false)
                .setItems(arrayFormasPagamento, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int position) {
                        PedidoDAO pedidoDAO = new PedidoDAO(context);
                        Pedido pedido = pedidoDAO.getPedido();
                        pedido.setFormaPagamento(FormaPagamento.fromOrdinal(position+1));

                        if (pedido.getFormaPagamento() != FormaPagamento.DINHEIRO) {
                            pedido.setTrocaPara(0.00);
                        }
                        pedidoDAO.save(pedido);
                        builder.create().dismiss();
                        PedidoActivity activity = (PedidoActivity) context;
                        activity.setDadosPedido();
                    }
                });

        builder.create().show();

    }
}
