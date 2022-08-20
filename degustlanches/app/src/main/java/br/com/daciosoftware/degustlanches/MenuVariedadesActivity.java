package br.com.daciosoftware.degustlanches;

import android.os.Bundle;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import br.com.daciosoftware.degustlanches.model.ProdutoEntity;
import br.com.daciosoftware.degustlanches.model.Variedade;
import br.com.daciosoftware.degustlanches.util.DialogBox;
import br.com.daciosoftware.degustlanches.webservice.ActionType;
import br.com.daciosoftware.degustlanches.webservice.CallBackTask;
import br.com.daciosoftware.degustlanches.webservice.VariedadesTask;

public class MenuVariedadesActivity extends MenuProdutosActivity implements CallBackTask {

    private List<ProdutoEntity> variedadesList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        VariedadesTask task = new VariedadesTask(MenuVariedadesActivity.this, ActionType.READ, MenuVariedadesActivity.this);
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

            if (jsonObject.has("variedades")) {

                JSONArray jsonVariedades = jsonObject.getJSONArray("variedades");
                for (int i = 0; i < jsonVariedades.length(); i++) {
                    JSONObject jsonVariedade = jsonVariedades.getJSONObject(i);
                    if (jsonVariedade.getBoolean("ativo")) {
                        ProdutoEntity variedade = new Variedade(jsonVariedade);
                        variedadesList.add(variedade);
                    }
                }

                MenuProdutosAdapter adapter = new MenuProdutosAdapter(MenuVariedadesActivity.this, variedadesList, MenuVariedadesActivity.this);
                recyclerViewMenu.setAdapter(adapter);
            }

        } catch (Exception e) {
            new DialogBox(MenuVariedadesActivity.this, DialogBox.DialogBoxType.INFORMATION, getResources().getString(R.string.app_name), e.getMessage(), false).show();
            e.printStackTrace();
        }

    }


}
