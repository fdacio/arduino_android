package br.com.daciosoftware.degustlanches.model;

import org.json.JSONObject;

public class ComboPreco implements ProdutoPrecoEntity {

    private int id;
    private int combos_id;
    private int paes_id;
    private int produtos_id;
    private double preco;
    private Pao pao;

    public ComboPreco(JSONObject jsonObject) {
        this.id = jsonObject.optInt("id");
        this.combos_id = jsonObject.optInt("combos_id");
        this.paes_id = jsonObject.optInt("paes_id");
        this.produtos_id = jsonObject.optInt("produtos_id");
        this.preco = jsonObject.optDouble("preco");
        this.pao = new Pao(jsonObject.optJSONObject("pae"));
    }

    @Override
    public int getProdutoId() {
        return produtos_id;
    }

    @Override
    public String getUnidadeProduto() {
        return pao.getNome();
    }

    @Override
    public double getPrecoProduto() {
        return preco;
    }
}
