package br.com.daciosoftware.degustlanches;

import android.os.Bundle;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import br.com.daciosoftware.degustlanches.model.ProdutoEntity;
import br.com.daciosoftware.degustlanches.model.SucoVitamina;
import br.com.daciosoftware.degustlanches.util.DialogBox;
import br.com.daciosoftware.degustlanches.webservice.ActionType;
import br.com.daciosoftware.degustlanches.webservice.CallBackTask;
import br.com.daciosoftware.degustlanches.webservice.SucosVitaminasTask;

public class MenuSucosVitaminasActivity extends MenuProdutosActivity implements CallBackTask {

    private List<ProdutoEntity> sucosVitaminasList = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SucosVitaminasTask task = new SucosVitaminasTask(MenuSucosVitaminasActivity.this, ActionType.READ, MenuSucosVitaminasActivity.this);
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

            if (jsonObject.has("sucos_vitaminas")) {

                JSONArray jsonSucosVitaminas = jsonObject.getJSONArray("sucos_vitaminas");
                for (int i = 0; i < jsonSucosVitaminas.length(); i++) {
                    JSONObject jsonSucoVitamina = jsonSucosVitaminas.getJSONObject(i);
                    if(jsonSucoVitamina.getBoolean("ativo")) {
                        ProdutoEntity sucoVitamina = new SucoVitamina(jsonSucoVitamina);
                        sucosVitaminasList.add(sucoVitamina);
                    }
                }

                MenuProdutosAdapter adapter = new MenuProdutosAdapter(MenuSucosVitaminasActivity.this, sucosVitaminasList, MenuSucosVitaminasActivity.this);
                recyclerViewMenu.setAdapter(adapter);
            }

        } catch (Exception e) {
            new DialogBox(MenuSucosVitaminasActivity.this, DialogBox.DialogBoxType.INFORMATION, getResources().getString(R.string.app_name), e.getMessage(), false).show();
            e.printStackTrace();
        }

    }



}
