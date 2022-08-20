package br.com.daciosoftware.degustlanches;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.snackbar.Snackbar;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import br.com.daciosoftware.degustlanches.dao.PedidoDAO;
import br.com.daciosoftware.degustlanches.dao.PedidoProdutoDAO;
import br.com.daciosoftware.degustlanches.dao.ProdutoDAO;
import br.com.daciosoftware.degustlanches.model.Acai;
import br.com.daciosoftware.degustlanches.model.Pedido;
import br.com.daciosoftware.degustlanches.model.PedidoProduto;
import br.com.daciosoftware.degustlanches.model.Produto;
import br.com.daciosoftware.degustlanches.model.ProdutoEntity;
import br.com.daciosoftware.degustlanches.model.ProdutoPrecoEntity;
import br.com.daciosoftware.degustlanches.model.Sushi;
import br.com.daciosoftware.degustlanches.util.DialogBox;
import br.com.daciosoftware.degustlanches.webservice.ActionType;
import br.com.daciosoftware.degustlanches.webservice.CallBackTask;
import br.com.daciosoftware.degustlanches.webservice.SushisTask;

public class MenuSushisActivity extends MenuProdutosActivity implements CallBackTask {

    private List<ProdutoEntity> sushisList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SushisTask task = new SushisTask(MenuSushisActivity.this, ActionType.READ, MenuSushisActivity.this);
        task.execute();
    }

    @Override
    public void post(JSONObject jsonObject) {
    }

    @Override
    public void get(JSONObject jsonObject) {

        try {

            if (jsonObject == null) {
                throw new Exception(getResources().getString(R.string.error_geral));
            }

            if (!jsonObject.getBoolean("success")) {
                throw new Exception(jsonObject.getString("message"));
            }

            if (jsonObject.has("sushis")) {

                JSONArray jsonSushis = jsonObject.getJSONArray("sushis");
                for (int i = 0; i < jsonSushis.length(); i++) {
                    JSONObject jsonSushi = jsonSushis.getJSONObject(i);
                    if (jsonSushi.getBoolean("ativo")) {
                        ProdutoEntity sushi = new Sushi(jsonSushi);
                        sushisList.add(sushi);
                    }
                }

                MenuProdutosAdapter adapter = new MenuProdutosAdapter(MenuSushisActivity.this, sushisList, MenuSushisActivity.this);
                recyclerViewMenu.setAdapter(adapter);
            }

        } catch (Exception e) {
            new DialogBox(MenuSushisActivity.this, DialogBox.DialogBoxType.INFORMATION, getResources().getString(R.string.app_name), e.getMessage(), false).show();
            e.printStackTrace();
        }
    }

    @Override
    public void onClickItemProduto(ProdutoEntity produtoEntity) {

        Sushi sushi = (Sushi) produtoEntity;
        if (sushi.getTipos().size() > 0) {
            //DialogSushiPorTipo dialogSushiPorTipo = new DialogSushiPorTipo(MenuSushisActivity.this, sushi);
            //dialogSushiPorTipo.show();
            Intent intent = new Intent(getApplicationContext(), MenuSushiTiposActivity.class);
            intent.putExtra("SUSHI", sushi);
            startActivity(intent);
        } else if (sushi.getRecheios().size() > 0) {
            DialogShushiRecheios dialogShushiRecheios = new DialogShushiRecheios(MenuSushisActivity.this, sushi.getRecheios(), produtoEntity, sushi.getPrecos().get(0));
            dialogShushiRecheios.show();
        } else {
            addShushiPedido(produtoEntity, sushi.getPrecos().get(0), null);
        }
    }

    @Override
    public void onClickItemPreco(ProdutoEntity produtoEntity, ProdutoPrecoEntity produtoPrecoEntity) {

        Sushi sushi = (Sushi) produtoEntity;
        if (sushi.getTipos().size() > 0) {
            DialogSushiPorTipo dialogSushiPorTipo = new DialogSushiPorTipo(MenuSushisActivity.this, sushi);
            dialogSushiPorTipo.show();
        } else if (sushi.getRecheios().size() > 0) {
            DialogShushiRecheios dialogShushiRecheios = new DialogShushiRecheios(MenuSushisActivity.this, sushi.getRecheios(), produtoEntity, sushi.getPrecos().get(0));
            dialogShushiRecheios.show();
        } else {
            addShushiPedido(produtoEntity, produtoPrecoEntity, null);
        }
    }

    public void addShushiPedido(ProdutoEntity produtoEntity, ProdutoPrecoEntity produtoPrecoEntity, String recheios) {

        PedidoDAO pedidoDAO = new PedidoDAO(MenuSushisActivity.this);
        Pedido pedido = pedidoDAO.getPedido();

        ProdutoDAO produtoDAO = new ProdutoDAO(MenuSushisActivity.this);
        Produto produto = new Produto();
        produto.setCodigo(produtoPrecoEntity.getProdutoId());
        produto.setTipo(produtoEntity.getTipo());
        String nome = String.format("%s - %s (%s)", produtoEntity.getCodigo(), produtoEntity.getNome(), produtoPrecoEntity.getUnidadeProduto());
        produto.setNome(nome);
        produto.setPreco(produtoPrecoEntity.getPrecoProduto());
        produto.setIngredientes(produtoEntity.getIngredientes());
        produtoDAO.save(produto);

        PedidoProduto pedidoProduto = new PedidoProduto();
        pedidoProduto.setId(produto.getCodigo());
        pedidoProduto.setPedido(pedido);
        pedidoProduto.setProduto(produto);
        pedidoProduto.setQuantidade(1);
        pedidoProduto.setPreco(produtoPrecoEntity.getPrecoProduto());
        if (recheios != null) {
            pedidoProduto.setObservacoes(recheios);
        }
        pedido.addProduto(pedidoProduto);
        PedidoProdutoDAO pedidoProdutoDAO = new PedidoProdutoDAO(MenuSushisActivity.this, pedido);
        pedidoProdutoDAO.save(pedidoProduto);

        Snackbar.make(findViewById(R.id.contentMenu), nome + " Adicionado ao Pedido ", Snackbar.LENGTH_SHORT)
                .setAction("Action", null).show();

    }

}
