package br.com.daciosoftware.degustlanches;

import android.os.Bundle;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import br.com.daciosoftware.degustlanches.model.Combo;
import br.com.daciosoftware.degustlanches.model.ProdutoEntity;
import br.com.daciosoftware.degustlanches.util.DialogBox;
import br.com.daciosoftware.degustlanches.webservice.ActionType;
import br.com.daciosoftware.degustlanches.webservice.CallBackTask;
import br.com.daciosoftware.degustlanches.webservice.CombosTask;

public class MenuCombosActivity extends MenuProdutosActivity implements CallBackTask {

    private List<ProdutoEntity> combosList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        CombosTask task = new CombosTask(MenuCombosActivity.this, ActionType.READ, MenuCombosActivity.this);
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

            if (jsonObject.has("combos")) {

                JSONArray jsonCombos = jsonObject.getJSONArray("combos");
                for (int i = 0; i < jsonCombos.length(); i++) {
                    JSONObject jsonCombo = jsonCombos.getJSONObject(i);
                    if (jsonCombo.getBoolean("ativo")) {
                        ProdutoEntity combo = new Combo(jsonCombo);
                        combosList.add(combo);
                    }
                }
                MenuProdutosAdapter adapter = new MenuProdutosAdapter(MenuCombosActivity.this, combosList, MenuCombosActivity.this);
                recyclerViewMenu.setAdapter(adapter);
            }

        } catch (Exception e) {
            new DialogBox(MenuCombosActivity.this, DialogBox.DialogBoxType.INFORMATION, getResources().getString(R.string.app_name), e.getMessage(), false).show();
            e.printStackTrace();
        }
    }
}
