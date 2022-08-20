package br.com.daciosoftware.degustlanches.model;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Sanduiche extends ProdutoMenu implements ProdutoEntity<Sanduiche, SanduichePreco> {

    private List<SanduicheIngrediente> ingredientes = new ArrayList<>();
    private List<SanduichePreco> precos = new ArrayList<>();
    private String foto;

    public Sanduiche(JSONObject jsonObject) {

        this.id     = jsonObject.optInt("id");
        this.codigo = jsonObject.optInt("codigo");
        this.nome   = jsonObject.optString("nome");
        this.foto   = jsonObject.optString("foto");

        JSONArray jsonIngredientes = jsonObject.optJSONArray("ingredientes");
        for (int i = 0; i < jsonIngredientes.length(); i++) {
            JSONObject jsonIngrediente = jsonIngredientes.optJSONObject(i);
            SanduicheIngrediente sanduicheIngrediente = new SanduicheIngrediente(jsonIngrediente);
            ingredientes.add(sanduicheIngrediente);
        }

        JSONArray jsonPrecos = jsonObject.optJSONArray("precos");
        for (int i = 0; i < jsonPrecos.length(); i++) {
            JSONObject jsonPreco = jsonPrecos.optJSONObject(i);
            if (jsonPreco.optBoolean("ativo")) {
                SanduichePreco sanduichePreco = new SanduichePreco(jsonPreco);
                precos.add(sanduichePreco);
            }
        }
    }

    private String ingredienteToString(List<SanduicheIngrediente> ingredientes) {
        String texto = "";
        for (SanduicheIngrediente ingrediente: ingredientes
        ) {
            texto += String.format("%d %s, ", (long) ingrediente.getQuantidade(), ingrediente.getIngrediente().getNome());
        }
        return texto.substring(0, texto.length()-2);
    }

    @Override
    public ProdutoTipo getTipo() {
        return ProdutoTipo.SANDUICHE;
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
    public List<SanduichePreco> getPrecos() {
        return precos;
    }

}
