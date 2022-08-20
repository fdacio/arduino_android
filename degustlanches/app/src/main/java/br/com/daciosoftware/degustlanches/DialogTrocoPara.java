package br.com.daciosoftware.degustlanches;

import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import androidx.appcompat.app.AlertDialog;

import br.com.daciosoftware.degustlanches.dao.PedidoDAO;
import br.com.daciosoftware.degustlanches.model.FormaPagamento;
import br.com.daciosoftware.degustlanches.model.Pedido;
import br.com.daciosoftware.degustlanches.util.MaskMoneyEditInput;

public class DialogTrocoPara implements View.OnClickListener {

    private Context context;
    private EditText edtTroco;

    public DialogTrocoPara(Context context) {
        this.context = context;
    }

    @Override
    public void onClick(View view) {

        if (new PedidoDAO(context).getPedido().getFormaPagamento() != FormaPagamento.DINHEIRO) {
            return;
        }

        final PedidoDAO pedidoDAO = new PedidoDAO(context);
        final Pedido pedido = pedidoDAO.getPedido();

        final LayoutInflater inflater = LayoutInflater.from(context);
        final View viewTrocoPara = inflater.inflate(R.layout.troco_para_edit, null);
        edtTroco = viewTrocoPara.findViewById(R.id.editTextTroco);

        edtTroco.setText(String.format("R$%.2f", pedido.getTrocaPara()));
        edtTroco.addTextChangedListener(MaskMoneyEditInput.mask(edtTroco));
        edtTroco.requestFocus();

        final AlertDialog.Builder builder = new AlertDialog.Builder(context, R.style.MyAlertDialog);
        builder.setTitle(R.string.troco_para_dialog)
                .setView(viewTrocoPara)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String cleanString = edtTroco.getText().toString().replaceAll("[R$,.]", "");
                        cleanString = MaskMoneyEditInput.getOnlyNumbers(cleanString);
                        Double parsed = Double.parseDouble(cleanString);
                        pedido.setTrocaPara(parsed / 100);
                        pedidoDAO.save(pedido);
                        PedidoActivity activity = (PedidoActivity) context;
                        activity.setDadosPedido();
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
                imm.showSoftInput(edtTroco, InputMethodManager.SHOW_IMPLICIT);
            }
        });

        builder.create().show();
    }
}
