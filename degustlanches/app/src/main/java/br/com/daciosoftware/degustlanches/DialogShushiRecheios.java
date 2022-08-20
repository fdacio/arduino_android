package br.com.daciosoftware.degustlanches;

import android.content.DialogInterface;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;

import java.util.ArrayList;
import java.util.List;

import br.com.daciosoftware.degustlanches.model.ProdutoEntity;
import br.com.daciosoftware.degustlanches.model.ProdutoPrecoEntity;
import br.com.daciosoftware.degustlanches.model.SushiRecheioSushi;
import br.com.daciosoftware.degustlanches.util.DialogBox;

public class DialogShushiRecheios {

    private MenuSushisActivity activity;
    private List<SushiRecheioSushi> recheios;
    private ProdutoEntity produtoEntity;
    private ProdutoPrecoEntity produtoPrecoEntity;

    public DialogShushiRecheios(MenuSushisActivity activity, List<SushiRecheioSushi> recheios, ProdutoEntity produtoEntity, ProdutoPrecoEntity produtoPrecoEntity) {
        this.activity = activity;
        this.recheios = recheios;
        this.produtoEntity = produtoEntity;
        this.produtoPrecoEntity = produtoPrecoEntity;
    }

    public void show() {

        final String[] arrayRecheios = new String[recheios.size()];
        boolean[] checkboxes = new boolean[recheios.size()];
        final List<String> listaRecheiosSelecionados = new ArrayList<>();

        int i = 0;
        for (SushiRecheioSushi recheio : recheios) {
            arrayRecheios[i] = recheio.getSushiRecheio().getNome();
            i++;
        }

        final AlertDialog.Builder builder = new AlertDialog.Builder(this.activity, R.style.MyAlertDialog);
        builder
                .setTitle(R.string.informar_recheio)
                .setCancelable(false)
                .setMultiChoiceItems(arrayRecheios, checkboxes, new DialogInterface.OnMultiChoiceClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                        if (isChecked) {
                            listaRecheiosSelecionados.add(arrayRecheios[which]);
                        } else {
                            listaRecheiosSelecionados.remove(arrayRecheios[which]);
                        }

                    }
                })
                .setPositiveButton("Registrar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if ((listaRecheiosSelecionados.size() == 0) || (listaRecheiosSelecionados.size() > 2)) {
                            String mensagem = "Informe até 2 recheios";
                            new DialogBox(activity, DialogBox.DialogBoxType.INFORMATION, activity.getResources().getString(R.string.app_name), mensagem, false).show();
                            return;
                        }
                        //Adicionar produto ao pedido
                        String observacoes = "";
                        for (String nomeRecheio : listaRecheiosSelecionados) {
                            if (observacoes.isEmpty()) {
                                observacoes += "Recheio: " + nomeRecheio;
                            } else {
                                observacoes = "\nRecheio: " + nomeRecheio;
                            }
                        }
                        activity.addShushiPedido(produtoEntity, produtoPrecoEntity, observacoes);
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
