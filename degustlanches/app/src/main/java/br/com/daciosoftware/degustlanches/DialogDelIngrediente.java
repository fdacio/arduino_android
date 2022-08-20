package br.com.daciosoftware.degustlanches;

import android.content.Context;
import android.content.DialogInterface;

import androidx.appcompat.app.AlertDialog;

import java.util.ArrayList;
import java.util.List;

import br.com.daciosoftware.degustlanches.dao.PedidoDAO;
import br.com.daciosoftware.degustlanches.dao.PedidoProdutoDAO;
import br.com.daciosoftware.degustlanches.model.Ingrediente;
import br.com.daciosoftware.degustlanches.model.Pedido;
import br.com.daciosoftware.degustlanches.model.PedidoProduto;

public class DialogDelIngrediente {

    private Context context;
    private List<Ingrediente> ingredientes;
    private PedidoProduto pedidoProduto;

    public DialogDelIngrediente(Context context, List<Ingrediente> ingredientes, PedidoProduto pedidoProduto) {
        this.context = context;
        this.ingredientes = ingredientes;
        this.pedidoProduto = pedidoProduto;
    }

    public void show() {

        PedidoDAO pedidoDAO = new PedidoDAO(this.context);
        Pedido pedido = pedidoDAO.getPedido();
        final PedidoProdutoDAO pedidoProdutoDAO = new PedidoProdutoDAO(this.context, pedido);
        final String[] arrayIngredientes = new String[ingredientes.size()];
        boolean[] checkboxes = new boolean[ingredientes.size()];
        final List<String> listaIngredientesSelecionados = new ArrayList<>();

        int i = 0;
        for (Ingrediente ingrediente : ingredientes) {
            arrayIngredientes[i] = ingrediente.getNome();
            i++;
        }

        final AlertDialog.Builder builder = new AlertDialog.Builder(this.context, R.style.MyAlertDialog);
        builder
                .setTitle(R.string.retirar_ingrediente)
                .setMultiChoiceItems(arrayIngredientes, checkboxes, new DialogInterface.OnMultiChoiceClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                        if (isChecked) {
                            listaIngredientesSelecionados.add(arrayIngredientes[which]);
                        } else {
                            listaIngredientesSelecionados.remove(arrayIngredientes[which]);
                        }

                    }
                })
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String observacoes = pedidoProduto.getObservacoes();
                        for (String nomeIngrediente : listaIngredientesSelecionados) {
                            if (observacoes != null) {
                                observacoes += "\nRetirar " + nomeIngrediente;
                            } else {
                                observacoes = "Retirar " + nomeIngrediente;
                            }
                        }

                        pedidoProduto.setObservacoes(observacoes);
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
