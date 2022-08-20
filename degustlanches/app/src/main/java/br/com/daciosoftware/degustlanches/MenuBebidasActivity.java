package br.com.daciosoftware.degustlanches;

import android.os.Bundle;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import br.com.daciosoftware.degustlanches.model.Bebida;
import br.com.daciosoftware.degustlanches.model.ProdutoEntity;
import br.com.daciosoftware.degustlanches.util.DialogBox;
import br.com.daciosoftware.degustlanches.webservice.ActionType;
import br.com.daciosoftware.degustlanches.webservice.BebidasTask;
import br.com.daciosoftware.degustlanches.webservice.CallBackTask;

public class MenuBebidasActivity extends MenuProdutosActivity implements CallBackTask {

    private List<ProdutoEntity> bebidasList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        BebidasTask task = new BebidasTask(MenuBebidasActivity.this, ActionType.READ, MenuBebidasActivity.this);
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

            if (jsonObject.has("bebidas")) {

                JSONArray jsonBebidas = jsonObject.getJSONArray("bebidas");
                for (int i = 0; i < jsonBebidas.length(); i++) {
                    JSONObject jsonBebida = jsonBebidas.getJSONObject(i);
                    if (jsonBebida.getBoolean("ativo")) {
                        ProdutoEntity bebida = new Bebida(jsonBebida);
                        bebidasList.add(bebida);
                    }
                }

                MenuProdutosAdapter adapter = new MenuProdutosAdapter(MenuBebidasActivity.this, bebidasList, MenuBebidasActivity.this);
                recyclerViewMenu.setAdapter(adapter);
            }

        } catch (Exception e) {
            new DialogBox(MenuBebidasActivity.this, DialogBox.DialogBoxType.INFORMATION, getResources().getString(R.string.app_name), e.getMessage(), false).show();
            e.printStackTrace();
        }
    }
}
