package br.com.daciosoftware.degustlanches.model;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Bebida extends ProdutoMenu implements ProdutoEntity<Bebida, BebidaPreco> {

    private List<BebidaPreco> precos = new ArrayList<>();

    public Bebida(JSONObject jsonObject) {
        this.id     = jsonObject.optInt("id");
        this.nome   = jsonObject.optString("nome");

        JSONArray jsonPrecos = jsonObject.optJSONArray("precos");
        for (int i = 0; i < jsonPrecos.length(); i++) {
            JSONObject jsonPreco = jsonPrecos.optJSONObject(i);
            if(jsonPreco.optBoolean("ativo")) {
                BebidaPreco bebidaPreco = new BebidaPreco(jsonPreco);
                precos.add(bebidaPreco);
            }
        }
    }

    @Override
    public int getCodigo() {
        return id;
    }

    @Override
    public ProdutoTipo getTipo() {
        return ProdutoTipo.BEBIDAS;
    }

    @Override
    public void setCodigo(int codigo) {
        this.id = codigo;
    }

    @Override
    public String getIngredientes() {
        return null;
    }

    @Override
    public String getFoto() {
        return null;
    }

    @Override
    public List<BebidaPreco> getPrecos() {
        return precos;
    }
}


