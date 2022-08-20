package br.com.daciosoftware.degustlanches;

import android.content.Context;
import android.content.DialogInterface;

import androidx.appcompat.app.AlertDialog;

import java.util.List;

import br.com.daciosoftware.degustlanches.dao.PedidoDAO;
import br.com.daciosoftware.degustlanches.dao.PedidoProdutoDAO;
import br.com.daciosoftware.degustlanches.model.Ingrediente;
import br.com.daciosoftware.degustlanches.model.Pedido;
import br.com.daciosoftware.degustlanches.model.PedidoProduto;

public class DialogAddIngrediente {

    private Context context;
    private List<Ingrediente> ingredientes;
    private PedidoProduto pedidoProduto;

    public DialogAddIngrediente(Context context, List<Ingrediente> ingredientes, PedidoProduto pedidoProduto) {
        this.context = context;
        this.ingredientes = ingredientes;
        this.pedidoProduto = pedidoProduto;
    }

    public void show() {

        PedidoDAO pedidoDAO = new PedidoDAO(this.context);
        Pedido pedido = pedidoDAO.getPedido();
        final PedidoProdutoDAO pedidoProdutoDAO = new PedidoProdutoDAO(this.context, pedido);
        final String[] arrayIngredientes = new String[ingredientes.size()];

        int i = 0;
        for (Ingrediente ingrediente : ingredientes) {
            arrayIngredientes[i] = ingrediente.getNome();
            i++;
        }

        final AlertDialog.Builder builder = new AlertDialog.Builder(this.context, R.style.MyAlertDialog);
        builder
                .setTitle(R.string.adicionar_ingrediente)
                .setItems(arrayIngredientes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int position) {

                        String observacoes = pedidoProduto.getObservacoes();
                        Ingrediente ingrediente = ingredientes.get(position);
                        if (observacoes != null) {
                            observacoes += "\nAdicionar 1 " + String.format("%s R$ %.2f", ingrediente.getNome(), ingrediente.getPreco());
                        } else {
                            observacoes = "Adicionar 1 " + String.format("%s R$ %.2f", ingrediente.getNome(), ingrediente.getPreco());
                        }

                        pedidoProduto.setObservacoes(observacoes);
                        pedidoProduto.setPreco(pedidoProduto.getPreco() + ingrediente.getPreco());
                        pedidoProdutoDAO.updade(pedidoProduto);
                        dialog.dismiss();
                        PedidoActivity activity = (PedidoActivity) context;
                        activity.setDadosPedido();

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
