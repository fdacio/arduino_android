package br.com.daciosoftware.degustlanches.model;

import org.json.JSONObject;

import java.io.Serializable;

public class SushiPreco implements ProdutoPrecoEntity, Serializable {

    private int id;
    private int sushis_id;
    private int sushis_unidades_id;
    private int produtos_id;
    private double preco;
    private SushiUnidade unidade;

    public SushiPreco(JSONObject jsonObject) {
        this.id = jsonObject.optInt("id");
        this.sushis_id = jsonObject.optInt("sushis_id");
        this.sushis_unidades_id = jsonObject.optInt("sushis_unidades_id");
        this.produtos_id = jsonObject.optInt("produtos_id");
        this.preco = jsonObject.optDouble("preco");
        this.unidade = new SushiUnidade(jsonObject.optJSONObject("sushis_unidade"));
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
