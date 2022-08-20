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

public class DialogAddCoberturaCombo {

    private Context context;
    private List<String> coberturas;
    private PedidoProduto pedidoProduto;

    public DialogAddCoberturaCombo(Context context, PedidoProduto pedidoProduto) {
        this.context = context;
        List<String> coberturas = new ArrayList<>();
        coberturas.add("CATUPIRY");
        coberturas.add("CHEDDER");
        coberturas.add("NENHUMA");
        this.coberturas = coberturas;
        this.pedidoProduto = pedidoProduto;
    }

    public void show() {

        PedidoDAO pedidoDAO = new PedidoDAO(this.context);
        final Pedido pedido = pedidoDAO.getPedido();
        final PedidoProdutoDAO pedidoProdutoDAO = new PedidoProdutoDAO(this.context, pedido);

        final String[] arrayCoberturas = new String[coberturas.size()];
        int i = 0;
        for (String cobertura : coberturas) {
            arrayCoberturas[i] = cobertura;
            i++;
        }

        final AlertDialog.Builder builder = new AlertDialog.Builder(this.context, R.style.MyAlertDialog);
        builder
                .setTitle(R.string.adicionar_cobertura_batata)
                .setItems(arrayCoberturas, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int position) {

                        String observacoes = pedidoProduto.getObservacoes();
                        if (observacoes != null) {
                            observacoes = observacoes.replace("Cobertura Batata: NENHUMA", "\0");
                            observacoes = observacoes.replace("Cobertura Batata: CATUPIRY", "\0");
                            observacoes = observacoes.replace("Cobertura Batata: CHEDDER", "\0");
                        }
                        String cobertura = coberturas.get(position);
                        if (observacoes != null) {
                            observacoes += "\nCobertura Batata: " + String.format("%s", cobertura);
                        } else {
                            observacoes = "Cobertura Batata: " + String.format("%s", cobertura);
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
