package br.com.daciosoftware.degustlanches.model;

import org.json.JSONObject;

public class BebidaPreco implements ProdutoPrecoEntity {

    private int id;
    private int bebidas_id;
    private int unidades_id;
    private Unidade unidade;
    private int produtos_id;
    private double preco;

    public BebidaPreco(JSONObject jsonObject) {
        this.id = jsonObject.optInt("id");
        this.bebidas_id = jsonObject.optInt("bebidas_id");
        this.unidades_id = jsonObject.optInt("unidades_id");
        this.produtos_id = jsonObject.optInt("produtos_id");
        this.preco = jsonObject.optDouble("preco");
        this.unidade = new Unidade(jsonObject.optJSONObject("unidade"));
    }

    @Override
    public int getProdutoId() {
        return produtos_id;
    }

    @Override
    public String getUnidadeProduto() {
        return unidade.getNome();
    }

    @Override
    public double getPrecoProduto() {
        return preco;
    }
}
