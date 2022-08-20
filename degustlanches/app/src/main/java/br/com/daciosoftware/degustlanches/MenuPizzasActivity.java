package br.com.daciosoftware.degustlanches;

import android.os.Bundle;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import br.com.daciosoftware.degustlanches.model.Pastel;
import br.com.daciosoftware.degustlanches.model.Pizza;
import br.com.daciosoftware.degustlanches.model.ProdutoEntity;
import br.com.daciosoftware.degustlanches.util.DialogBox;
import br.com.daciosoftware.degustlanches.webservice.ActionType;
import br.com.daciosoftware.degustlanches.webservice.CallBackTask;
import br.com.daciosoftware.degustlanches.webservice.PizzasTask;

public class MenuPizzasActivity extends MenuProdutosActivity implements CallBackTask {

    private List<ProdutoEntity> pizzasList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        PizzasTask task = new PizzasTask(MenuPizzasActivity.this, ActionType.READ, MenuPizzasActivity.this);
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

            if (jsonObject.has("pizzas")) {

                JSONArray jsonPasteis = jsonObject.getJSONArray("pizzas");
                for (int i = 0; i < jsonPasteis.length(); i++) {
                    JSONObject jsonPizza = jsonPasteis.getJSONObject(i);
                    if (jsonPizza.getBoolean("ativo")) {
                        ProdutoEntity pizza = new Pizza(jsonPizza);
                        pizzasList.add(pizza);
                    }
                }

                MenuProdutosAdapter adapter = new MenuProdutosAdapter(MenuPizzasActivity.this,  pizzasList, MenuPizzasActivity.this);
                recyclerViewMenu.setAdapter(adapter);
            }

        } catch (Exception e) {
            new DialogBox(MenuPizzasActivity.this, DialogBox.DialogBoxType.INFORMATION, getResources().getString(R.string.app_name), e.getMessage(), false).show();
            e.printStackTrace();
        }

    }
}
