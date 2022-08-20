package br.com.daciosoftware.degustlanches;

import android.os.Bundle;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import br.com.daciosoftware.degustlanches.model.Pastel;
import br.com.daciosoftware.degustlanches.model.ProdutoEntity;
import br.com.daciosoftware.degustlanches.util.DialogBox;
import br.com.daciosoftware.degustlanches.webservice.ActionType;
import br.com.daciosoftware.degustlanches.webservice.CallBackTask;
import br.com.daciosoftware.degustlanches.webservice.PasteisTask;

public class MenuPasteisActivity extends MenuProdutosActivity implements CallBackTask {

    private List<ProdutoEntity> pasteisList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        PasteisTask task = new PasteisTask(MenuPasteisActivity.this, ActionType.READ, MenuPasteisActivity.this);
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

            if (jsonObject.has("pasteis")) {

                JSONArray jsonPasteis = jsonObject.getJSONArray("pasteis");
                for (int i = 0; i < jsonPasteis.length(); i++) {
                    JSONObject jsonPastel = jsonPasteis.getJSONObject(i);
                    if (jsonPastel.getBoolean("ativo")) {
                        ProdutoEntity pastel = new Pastel(jsonPastel);
                        pasteisList.add(pastel);
                    }
                }

                MenuProdutosAdapter adapter = new MenuProdutosAdapter(MenuPasteisActivity.this, pasteisList, MenuPasteisActivity.this);
                recyclerViewMenu.setAdapter(adapter);
            }

        } catch (Exception e) {
            new DialogBox(MenuPasteisActivity.this, DialogBox.DialogBoxType.INFORMATION, getResources().getString(R.string.app_name), e.getMessage(), false).show();
            e.printStackTrace();
        }
    }
}