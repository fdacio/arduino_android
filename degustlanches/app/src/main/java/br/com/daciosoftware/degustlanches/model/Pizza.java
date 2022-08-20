package br.com.daciosoftware.degustlanches.model;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Pizza extends ProdutoMenu implements ProdutoEntity<Pizza, PizzaPreco> {

    private List<PizzaIngrediente> ingredientes = new ArrayList<>();
    private List<PizzaPreco> precos = new ArrayList<>();
    private String foto;

    public Pizza(JSONObject jsonObject) {

        this.id     = jsonObject.optInt("id");
        this.codigo = jsonObject.optInt("codigo");
        this.nome   = jsonObject.optString("nome");
        this.foto   = jsonObject.optString("foto");

        JSONArray jsonIngredientes = jsonObject.optJSONArray("ingredientes");
        for (int i = 0; i < jsonIngredientes.length(); i++) {
            JSONObject jsonIngrediente = jsonIngredientes.optJSONObject(i);
            PizzaIngrediente pizzaIngrediente = new PizzaIngrediente(jsonIngrediente);
            ingredientes.add(pizzaIngrediente);
        }

        JSONArray jsonPrecos = jsonObject.optJSONArray("precos");
        for (int i = 0; i < jsonPrecos.length(); i++) {
            JSONObject jsonPreco = jsonPrecos.optJSONObject(i);
            if (jsonPreco.optBoolean("ativo")) {
                PizzaPreco pizzaPreco = new PizzaPreco(jsonPreco);
                precos.add(pizzaPreco);
            }
        }
    }

    private String ingredienteToString(List<PizzaIngrediente> ingredientes) {
        String texto = "";
        for (PizzaIngrediente ingrediente: ingredientes
        ) {
            //texto += String.format("%d %s, ", (long) ingrediente.getQuantidade(), ingrediente.getIngrediente().getNome());
            texto += String.format("%s, ", ingrediente.getIngrediente().getNome());
        }
        return texto.substring(0, texto.length()-2);
    }

    @Override
    public ProdutoTipo getTipo() {
        return ProdutoTipo.PIZZAS;
    }

    @Override
    public String getIngredientes() {
        return (ingredientes.size() > 0)?ingredienteToString(ingredientes):"";
    }

    @Override
    public String getFoto() {
        return this.foto;
    }

    @Override
    public List<PizzaPreco> getPrecos() {
        return precos;
    }

}
