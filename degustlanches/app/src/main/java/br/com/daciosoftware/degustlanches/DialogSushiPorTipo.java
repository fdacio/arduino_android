package br.com.daciosoftware.degustlanches;

import android.content.DialogInterface;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;

import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

import br.com.daciosoftware.degustlanches.dao.PedidoDAO;
import br.com.daciosoftware.degustlanches.dao.PedidoProdutoDAO;
import br.com.daciosoftware.degustlanches.dao.ProdutoDAO;
import br.com.daciosoftware.degustlanches.model.Pedido;
import br.com.daciosoftware.degustlanches.model.PedidoProduto;
import br.com.daciosoftware.degustlanches.model.Produto;
import br.com.daciosoftware.degustlanches.model.Sushi;
import br.com.daciosoftware.degustlanches.model.SushiPreco;
import br.com.daciosoftware.degustlanches.model.SushiTipoSushi;
import br.com.daciosoftware.degustlanches.util.DialogBox;

public class DialogSushiPorTipo {

    private AlertDialog dialog;
    private View view;
    private List<SushiTipoSushi> tipos;
    private MenuSushisActivity activity;
    private Sushi sushi;

    public DialogSushiPorTipo(MenuSushisActivity activity, Sushi sushi) {

        this.activity = activity;
        this.sushi = sushi;
        view = activity.getLayoutInflater().inflate(R.layout.content_dialog_sushi_tipos, null);
        TextView codigo = view.findViewById(R.id.codigo);
        TextView nome = view.findViewById(R.id.nome);
        TextView unidade = view.findViewById(R.id.unidade);
        TextView preco = view.findViewById(R.id.preco);
        ListView listViewTipos = view.findViewById(R.id.listViewTipos);
        codigo.setText(String.valueOf(sushi.getCodigo()));
        nome.setText(sushi.getNome());
        SushiPreco sushiPreco = (SushiPreco) sushi.getPrecos().get(0);
        unidade.setText(sushiPreco.getUnidadeProduto());
        preco.setText(String.format("R$ %.2f", sushiPreco.getPrecoProduto()));

        tipos = new ArrayList<>();
        for (SushiTipoSushi tipo : sushi.getTipos()) {
            tipos.add(tipo);
            tipo.setQuantidade(getQuantidadePadrao(sushi));
        }
        tipos.get(tipos.size() - 1).setQuantidade(getQuantidadeRestante(sushi));

        final MenuSushiTiposAdapter adapter = new MenuSushiTiposAdapter(activity, tipos);
        listViewTipos.setAdapter(adapter);

    }

    public void show() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(activity, R.style.MyAlertDialog);
        alertDialog.setTitle("Informe as quantidades");
        alertDialog.setCancelable(false);
        alertDialog.setPositiveButton("Registrar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
            }
        });
        alertDialog.setNeutralButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        alertDialog.setView(view);
        dialog = alertDialog.create();
        dialog.show();
        Button buttonRegistrar = dialog.getButton(AlertDialog.BUTTON_POSITIVE);
        buttonRegistrar.setOnClickListener(new RegistraSushiPedidoOnClickListener(sushi));
    }

    private int getQuantidadePadrao(Sushi sushi) {

        int quantidadePadrao = 2;
        boolean isCombo = (sushi.getQuatidadeCombo() > 0);
        if (isCombo) {
            quantidadePadrao = sushi.getQuatidadeCombo() / sushi.getTipos().size();
            if (quantidadePadrao % 2 > 0) {
                quantidadePadrao += 1;
            }
        }

        return quantidadePadrao;
    }

    private int getQuantidadeRestante(Sushi sushi) {

        int quantidadePadrao = this.getQuantidadePadrao(sushi);
        int quantidadeRestante = 2;
        boolean isCombo = (sushi.getQuatidadeCombo() > 0);
        if (isCombo) {

            int totalPadrao = quantidadePadrao * sushi.getTipos().size();
            if (totalPadrao > sushi.getQuatidadeCombo()) {
                int passou = totalPadrao - sushi.getQuatidadeCombo();
                quantidadeRestante = quantidadePadrao - passou;
            } else if (totalPadrao < sushi.getQuatidadeCombo()) {
                int faltou = sushi.getQuatidadeCombo() - totalPadrao;
                quantidadeRestante = quantidadePadrao + faltou;
            } else {
                quantidadeRestante = quantidadePadrao;
            }
        }

        return quantidadeRestante;
    }

    private class RegistraSushiPedidoOnClickListener implements View.OnClickListener {

        private final Sushi sushi;

        public RegistraSushiPedidoOnClickListener(Sushi sushi) {
            this.sushi = sushi;
        }

        @Override
        public void onClick(View view) {

            //validaçao
            int qtdeTotal = 0;
            for (SushiTipoSushi tipo : tipos) {
                qtdeTotal += tipo.getQuantidade();
            }

            boolean isCombo = (sushi.getQuatidadeCombo() > 0);
            boolean isValidade = true;
            String mensagem = "";
            if (isCombo) {
                if (qtdeTotal < sushi.getQuatidadeCombo()) {
                    isValidade = false;
                    int faltou = sushi.getQuatidadeCombo() - qtdeTotal;
                    mensagem = String.format("Informada %d unidades a menos", faltou);
                }
                if (qtdeTotal > sushi.getQuatidadeCombo()) {
                    isValidade = false;
                    int passou = qtdeTotal - sushi.getQuatidadeCombo();
                    mensagem = String.format("Informada %d unidades a mais", passou);
                }
            }

            if (!isValidade) {
                new DialogBox(activity, DialogBox.DialogBoxType.INFORMATION, activity.getResources().getString(R.string.app_name), mensagem, false).show();
                return;
            }

            //Aqui adicionar o produto ao pedido
            PedidoDAO pedidoDAO = new PedidoDAO(activity);
            Pedido pedido = pedidoDAO.getPedido();
            ProdutoDAO produtoDAO = new ProdutoDAO(activity);

            SushiPreco sushiPreco = (SushiPreco) sushi.getPrecos().get(0);
            Produto produto = new Produto();
            produto.setCodigo(sushiPreco.getProdutoId());
            produto.setTipo(sushi.getTipo());
            String nome = String.format("%s - %s (%s)", sushi.getCodigo(), sushi.getNome(), sushiPreco.getUnidadeProduto());
            produto.setNome(nome);
            produto.setPreco(sushiPreco.getPrecoProduto());
            produto.setIngredientes(sushi.getIngredientes());
            produtoDAO.save(produto);

            PedidoProduto pedidoProduto = new PedidoProduto();
            pedidoProduto.setId(produto.getCodigo());
            pedidoProduto.setPedido(pedido);
            pedidoProduto.setProduto(produto);

            int quantidade = (isCombo) ? 1 : qtdeTotal;

            pedidoProduto.setQuantidade(quantidade);
            pedidoProduto.setPreco(sushiPreco.getPrecoProduto());
            String observacoes = "";
            for (SushiTipoSushi tipo : tipos) {
                observacoes += String.format("%s: %d unidades", tipo.getSushiTipo().getNome(), tipo.getQuantidade()) + "\n";
            }
            pedidoProduto.setObservacoes(observacoes);

            pedido.addProduto(pedidoProduto);

            PedidoProdutoDAO pedidoProdutoDAO = new PedidoProdutoDAO(activity, pedido);
            pedidoProdutoDAO.save(pedidoProduto);
            dialog.dismiss();

            Snackbar.make(activity.findViewById(R.id.contentMenu), nome + " Adicionado ao Pedido ", Snackbar.LENGTH_SHORT)
                    .setAction("Action", null).show();

        }

    }

}
