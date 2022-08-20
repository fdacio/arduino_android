package br.com.daciosoftware.degustlanches;

import android.content.Context;
import android.content.DialogInterface;

import androidx.appcompat.app.AlertDialog;

import java.util.ArrayList;
import java.util.List;

import br.com.daciosoftware.degustlanches.dao.PedidoDAO;
import br.com.daciosoftware.degustlanches.dao.PedidoProdutoDAO;
import br.com.daciosoftware.degustlanches.model.Pedido;
import br.com.daciosoftware.degustlanches.model.PedidoProduto;

public class DialogDelIngredienteCombo {

    private Context context;
    private List<String> ingredientes;
    private PedidoProduto pedidoProduto;

    public DialogDelIngredienteCombo(Context context, PedidoProduto pedidoProduto) {
        this.context = context;
        List<String> ingredientes = new ArrayList<>();
        ingredientes.add("Alface");
        ingredientes.add("Tomate");
        ingredientes.add("Queijo");
        ingredientes.add("Ovo");
        this.ingredientes = ingredientes;
        this.pedidoProduto = pedidoProduto;
    }

    public void show() {

        PedidoDAO pedidoDAO = new PedidoDAO(this.context);
        final Pedido pedido = pedidoDAO.getPedido();
        final PedidoProdutoDAO pedidoProdutoDAO = new PedidoProdutoDAO(this.context, pedido);

        final String[] arrayIngredientes = new String[ingredientes.size()];
        int i = 0;
        for (String ingrediente : ingredientes) {
            arrayIngredientes[i] = ingrediente;
            i++;
        }

        final AlertDialog.Builder builder = new AlertDialog.Builder(this.context, R.style.MyAlertDialog);
        builder
                .setTitle(R.string.retirar_ingrediente)
                .setItems(arrayIngredientes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int position) {

                        String observacoes = pedidoProduto.getObservacoes();
                        String ingrediente = ingredientes.get(position);
                        if (observacoes != null) {
                            observacoes += "\nRetirar " + String.format("%s", ingrediente);
                        } else {
                            observacoes = "Retirar: " + String.format("%s", ingrediente);
                        }

                        pedidoProduto.setObservacoes(observacoes);
                        pedidoProdutoDAO.updade(pedidoProduto);
                        pedido.addProduto(pedidoProduto);
                        dialog.dismiss();
                        if (context instanceof PedidoActivity) {
                            PedidoActivity activity = (PedidoActivity) context;
                            activity.setDadosPedido();
                        }
                    }
                })
                .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int i) {
                        dialog.dismiss();
                    }
                });
        builder.create().show();

    }

}
