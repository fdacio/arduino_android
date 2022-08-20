package br.com.daciosoftware.degustlanches;

import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import androidx.appcompat.app.AlertDialog;

import java.util.ArrayList;
import java.util.List;

import br.com.daciosoftware.degustlanches.dao.PedidoDAO;
import br.com.daciosoftware.degustlanches.dao.PedidoProdutoDAO;
import br.com.daciosoftware.degustlanches.model.Pedido;
import br.com.daciosoftware.degustlanches.model.PedidoProduto;
import br.com.daciosoftware.degustlanches.util.MaskMoneyEditInput;

public class DialogAddObservacoes {

    private Context context;
    private EditText edtTextObservacoes;
    private PedidoProduto pedidoProduto;

    public DialogAddObservacoes(Context context, PedidoProduto pedidoProduto) {
        this.context = context;
        this.pedidoProduto = pedidoProduto;
    }

    public void show() {

        PedidoDAO pedidoDAO = new PedidoDAO(this.context);
        final Pedido pedido = pedidoDAO.getPedido();
        final PedidoProdutoDAO pedidoProdutoDAO = new PedidoProdutoDAO(this.context, pedido);

        final LayoutInflater inflater = LayoutInflater.from(context);
        final View view = inflater.inflate(R.layout.produto_observacoes_edit, null);
        edtTextObservacoes = view.findViewById(R.id.editTextObservacoes);
        String observacoesOld = (pedidoProduto.getObservacoes() != null)?pedidoProduto.getObservacoes():"";
        edtTextObservacoes.setText(String.format("%s", observacoesOld));


        final AlertDialog.Builder builder = new AlertDialog.Builder(this.context, R.style.MyAlertDialog);
        builder
                .setTitle(R.string.editar_observacoes)
                .setView(view)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String observacoes = edtTextObservacoes.getText().toString();
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
                .setNeutralButton("Cancelar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

        builder.create().setOnShowListener(new DialogInterface.OnShowListener() {

            @Override
            public void onShow(DialogInterface dialog) {
                InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.showSoftInput(edtTextObservacoes, InputMethodManager.SHOW_IMPLICIT);
            }
        });

        builder.create().show();

        view.requestFocus();
        edtTextObservacoes.requestFocus();

    }

}
