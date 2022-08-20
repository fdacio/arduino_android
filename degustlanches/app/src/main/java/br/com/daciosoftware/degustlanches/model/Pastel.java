package br.com.daciosoftware.degustlanches.model;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Pastel extends ProdutoMenu implements ProdutoEntity<Pastel, PastelPreco> {

    private List<PastelIngrediente> ingredientes = new ArrayList<>();
    private List<PastelPreco> precos = new ArrayList<>();
    private String foto;

    public Pastel(JSONObject jsonObject) {

        this.id     = jsonObject.optInt("id");
        this.codigo = jsonObject.optInt("codigo");
        this.nome   = jsonObject.optString("nome");
        this.foto   = jsonObject.optString("foto");

        JSONArray jsonIngredientes = jsonObject.optJSONArray("ingredientes");
        for (int i = 0; i < jsonIngredientes.length(); i++) {
            JSONObject jsonIngrediente = jsonIngredientes.optJSONObject(i);
            PastelIngrediente pastelIngrediente = new PastelIngrediente(jsonIngrediente);
            ingredientes.add(pastelIngrediente);
        }

        JSONArray jsonPrecos = jsonObject.optJSONArray("precos");
        for (int i = 0; i < jsonPrecos.length(); i++) {
            JSONObject jsonPreco = jsonPrecos.optJSONObject(i);
            if (jsonPreco.optBoolean("ativo")) {
                PastelPreco pastelPreco = new PastelPreco(jsonPreco);
                precos.add(pastelPreco);
            }
        }
    }

    private String ingredienteToString(List<PastelIngrediente> ingredientes) {
        String texto = "";
        for (PastelIngrediente ingrediente: ingredientes
        ) {
            //texto += String.format("%d %s, ", (long) ingrediente.getQuantidade(), ingrediente.getIngrediente().getNome());
            texto += String.format("%s, ", ingrediente.getIngrediente().getNome());
        }
        return texto.substring(0, texto.length()-2);
    }

    @Override
    public ProdutoTipo getTipo() {
        return ProdutoTipo.PASTEIS;
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
    public List<PastelPreco> getPrecos() {
        return precos;
    }
}
