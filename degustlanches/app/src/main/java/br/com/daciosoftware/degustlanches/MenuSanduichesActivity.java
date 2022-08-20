package br.com.daciosoftware.degustlanches;

import android.os.Bundle;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import br.com.daciosoftware.degustlanches.model.ProdutoEntity;
import br.com.daciosoftware.degustlanches.model.Sanduiche;
import br.com.daciosoftware.degustlanches.util.DialogBox;
import br.com.daciosoftware.degustlanches.webservice.ActionType;
import br.com.daciosoftware.degustlanches.webservice.CallBackTask;
import br.com.daciosoftware.degustlanches.webservice.SanduichesTask;

public class MenuSanduichesActivity extends MenuProdutosActivity implements CallBackTask {

    private List<ProdutoEntity> sanduichesList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SanduichesTask task = new SanduichesTask(MenuSanduichesActivity.this, ActionType.READ, MenuSanduichesActivity.this);
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

            if (jsonObject.has("sanduiches")) {

                JSONArray jsonSanduiches = jsonObject.getJSONArray("sanduiches");
                for (int i = 0; i < jsonSanduiches.length(); i++) {
                    JSONObject jsonSanduiche = jsonSanduiches.getJSONObject(i);
                    if (jsonSanduiche.getBoolean("ativo")) {
                        ProdutoEntity sanduiche = new Sanduiche(jsonSanduiche);
                        sanduichesList.add(sanduiche);
                    }
                }

                MenuProdutosAdapter adapter = new MenuProdutosAdapter(MenuSanduichesActivity.this, sanduichesList, MenuSanduichesActivity.this);
                recyclerViewMenu.setAdapter(adapter);
            }

        } catch (Exception e) {
            new DialogBox(MenuSanduichesActivity.this, DialogBox.DialogBoxType.INFORMATION, getResources().getString(R.string.app_name), e.getMessage(), false).show();
            e.printStackTrace();
        }

    }


}
