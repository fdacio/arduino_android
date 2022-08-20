package br.com.daciosoftware.degustlanches.model;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Variedade extends ProdutoMenu implements ProdutoEntity<Variedade, VariedadePreco> {

    private List<VariedadeIngrediente> ingredientes = new ArrayList<>();
    private List<VariedadePreco> precos = new ArrayList<>();
    private String foto;

    public Variedade(JSONObject jsonObject) {

        this.id     = jsonObject.optInt("id");
        this.codigo = jsonObject.optInt("codigo");
        this.nome   = jsonObject.optString("nome");
        this.foto   = jsonObject.optString("foto");

        JSONArray jsonIngredientes = jsonObject.optJSONArray("ingredientes");
        for (int i = 0; i < jsonIngredientes.length(); i++) {
            JSONObject jsonIngrediente = jsonIngredientes.optJSONObject(i);
            VariedadeIngrediente variedadeIngrediente = new VariedadeIngrediente(jsonIngrediente);
            ingredientes.add(variedadeIngrediente);
        }

        JSONArray jsonPrecos = jsonObject.optJSONArray("precos");
        for (int i = 0; i < jsonPrecos.length(); i++) {
            JSONObject jsonPreco = jsonPrecos.optJSONObject(i);
            if (jsonPreco.optBoolean("ativo")) {
                VariedadePreco variedadePreco = new VariedadePreco(jsonPreco);
                precos.add(variedadePreco);
            }
        }
    }

    private String ingredienteToString(List<VariedadeIngrediente> ingredientes) {
        String texto = "";
        for (VariedadeIngrediente ingrediente: ingredientes
        ) {
            //texto += String.format("%d %s, ", (long) ingrediente.getQuantidade(), ingrediente.getIngrediente().getNome());
            texto += String.format("%s, ", ingrediente.getIngrediente().getNome());
        }
        return texto.substring(0, texto.length()-2);
    }

    @Override
    public ProdutoTipo getTipo() {
        return ProdutoTipo.VARIEDADE;
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
    public List<VariedadePreco> getPrecos() {
        return precos;
    }

}
