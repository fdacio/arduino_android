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
import br.com.daciosoftware.degustlanches.util.DialogBox;
import br.com.daciosoftware.degustlanches.webservice.AcaisTask;
import br.com.daciosoftware.degustlanches.webservice.ActionType;
import br.com.daciosoftware.degustlanches.webservice.CallBackTask;

public class MenuAcaisActivity extends MenuProdutosActivity implements CallBackTask {

    private List<ProdutoEntity> acaisList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AcaisTask task = new AcaisTask(MenuAcaisActivity.this, ActionType.READ, MenuAcaisActivity.this);
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

            if (jsonObject.has("acais")) {

                JSONArray jsonAcais = jsonObject.getJSONArray("acais");
                for (int i = 0; i < jsonAcais.length(); i++) {
                    JSONObject jsonAcai = jsonAcais.getJSONObject(i);
                    if (jsonAcai.getBoolean("ativo")) {
                        ProdutoEntity acai = new Acai(jsonAcai);
                        acaisList.add(acai);
                    }
                }

                MenuProdutosAdapter adapter = new MenuProdutosAdapter(MenuAcaisActivity.this, acaisList, MenuAcaisActivity.this);
                recyclerViewMenu.setAdapter(adapter);
            }

        } catch (Exception e) {
            new DialogBox(MenuAcaisActivity.this, DialogBox.DialogBoxType.INFORMATION, getResources().getString(R.string.app_name), e.getMessage(), false).show();
            e.printStackTrace();
        }
    }

    @Override
    public void onClickItemProduto(ProdutoEntity produtoEntity) {
        Acai acai = (Acai) produtoEntity;
        Intent intent = new Intent(getApplicationContext(), MenuAcaiAcompanhamentosActivity.class);
        intent.putExtra("ACAI", acai);
        startActivity(intent);
    }

    @Override
    public void onClickItemPreco(ProdutoEntity produtoEntity, ProdutoPrecoEntity produtoPrecoEntity) {
        Acai acai = (Acai) produtoEntity;
        Intent intent = new Intent(getApplicationContext(), MenuAcaiAcompanhamentosActivity.class);
        intent.putExtra("ACAI", acai);
        startActivity(intent);
    }

}