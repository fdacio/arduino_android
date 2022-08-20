package br.com.daciosoftware.degustlanches.model;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Sushi extends ProdutoMenu implements ProdutoEntity<Sushi, SushiPreco>, Serializable {

    private List<SushiIngrediente> ingredientes = new ArrayList<>();
    private List<SushiPreco> precos = new ArrayList<>();
    private List<SushiTipoSushi> tipos = new ArrayList<>();
    private List<SushiRecheioSushi> recheios = new ArrayList<>();
    private String foto;
    private int quatidadeCombo;

    public Sushi(JSONObject jsonObject) {

        this.id     = jsonObject.optInt("id");
        this.codigo = jsonObject.optInt("id");
        this.nome   = jsonObject.optString("nome");
        this.foto   = jsonObject.optString("foto");
        this.quatidadeCombo = jsonObject.optInt("quantidade_combo");

        JSONArray jsonIngredientes = jsonObject.optJSONArray("ingredientes");
        for (int i = 0; i < jsonIngredientes.length(); i++) {
            JSONObject jsonIngrediente = jsonIngredientes.optJSONObject(i);
            SushiIngrediente sushiIngrediente = new SushiIngrediente(jsonIngrediente);
            ingredientes.add(sushiIngrediente);
        }

        JSONArray jsonPrecos = jsonObject.optJSONArray("precos");
        for (int i = 0; i < jsonPrecos.length(); i++) {
            JSONObject jsonPreco = jsonPrecos.optJSONObject(i);
            if (jsonPreco.optBoolean("ativo")) {
                SushiPreco sushiPreco = new SushiPreco(jsonPreco);
                precos.add(sushiPreco);
            }
        }

        JSONArray jsonTipos = jsonObject.optJSONArray("tipos");
        for (int i = 0; i < jsonTipos.length(); i++) {
            JSONObject jsonTipo = jsonTipos.optJSONObject(i);
            SushiTipoSushi sushiTipoSushi = new SushiTipoSushi(jsonTipo);
                tipos.add(sushiTipoSushi);
        }

        JSONArray jsonRecheios = jsonObject.optJSONArray("recheios");
        for (int i = 0; i < jsonRecheios.length(); i++) {
            JSONObject jsonRecheio = jsonRecheios.optJSONObject(i);
            SushiRecheioSushi sushiRecheioSushi = new SushiRecheioSushi(jsonRecheio);
            recheios.add(sushiRecheioSushi);
        }

    }

    private String ingredienteToString(List<SushiIngrediente> ingredientes) {
        String texto = "";
        for (SushiIngrediente ingrediente: ingredientes
        ) {
            texto += String.format("%s, ", ingrediente.getIngrediente().getNome());
        }
        return texto.substring(0, texto.length()-2);
    }

    @Override
    public ProdutoTipo getTipo() {
        return ProdutoTipo.SUSHI;
    }

    @Override
    public String getIngredientes() {
        return (ingredientes.size() > 0)?ingredienteToString(ingredientes):"";
    }

    @Override
    public String getFoto() {
        return foto;
    }

    @Override
    public List<SushiPreco> getPrecos() {
        return precos;
    }

    public List<SushiTipoSushi> getTipos() { return tipos;}

    public List<SushiRecheioSushi> getRecheios() { return recheios;}

    public int getQuatidadeCombo() {
        return quatidadeCombo;
    }

    public void setQuatidadeCombo(int quatidadeCombo) {
        this.quatidadeCombo = quatidadeCombo;
    }

}
