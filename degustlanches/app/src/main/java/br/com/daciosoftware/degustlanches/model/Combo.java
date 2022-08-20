package br.com.daciosoftware.degustlanches.model;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Combo extends ProdutoMenu implements ProdutoEntity<Combo, ComboPreco> {

    private List<ComboIngrediente> ingredientes = new ArrayList<>();
    private List<ComboPreco> precos = new ArrayList<>();
    private String foto;

    public Combo(){}

    public Combo(JSONObject jsonObject) {

        this.id     = jsonObject.optInt("id");
        this.codigo = jsonObject.optInt("codigo");
        this.nome   = jsonObject.optString("nome");
        this.foto   = jsonObject.optString("foto");

        JSONArray jsonIngredientes = jsonObject.optJSONArray("ingredientes");
        for (int i = 0; i < jsonIngredientes.length(); i++) {
            JSONObject jsonIngrediente = jsonIngredientes.optJSONObject(i);
            ComboIngrediente comboIngrediente = new ComboIngrediente(jsonIngrediente);
            ingredientes.add(comboIngrediente);
        }

        JSONArray jsonPrecos = jsonObject.optJSONArray("precos");
        for (int i = 0; i < jsonPrecos.length(); i++) {
            JSONObject jsonPreco = jsonPrecos.optJSONObject(i);
            if (jsonPreco.optBoolean("ativo")) {
                ComboPreco comboPreco = new ComboPreco(jsonPreco);
                precos.add(comboPreco);
            }
        }

    }

    private String ingredienteToString(List<ComboIngrediente> ingredientes) {
        String texto = "";
        for (ComboIngrediente ingrediente: ingredientes
        ) {
            texto += String.format("%d %s, ", (long) ingrediente.getQuantidade(), ingrediente.getIngrediente().getNome());
        }
        return texto.substring(0, texto.length()-2);
    }

    @Override
    public ProdutoTipo getTipo() {
        return ProdutoTipo.COMBO;
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
    public List<ComboPreco> getPrecos() {
        return precos;
    }

}
